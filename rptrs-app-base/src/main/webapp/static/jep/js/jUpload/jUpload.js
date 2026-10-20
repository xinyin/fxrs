/**
 * @author min ma <min.ma@yzj.com>
 * version: 1.0
 *
 */


+function ($) {
    'use strict';

    const OPT_FILES = 10;
    const OPT_FILE_SIZE = -1;
    const OPT_MAX_FILES = 0x7fffffff;
    const OPT_MAX_FILE_SIZE = 0x7fffffff;

    var JUpload = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        this.$id = this.$el.attr("id");
        this.$formId = this.$id + "Form";
        this.$msgel = null;
        this.$dropzone = null;
        /*文件列表*/
        this.$files = [];
        this.init();
    };

    var calculateSize = function (size) {
        return Math.ceil(size / 1024 / 1024 * 100) / 100;
    }

    var calculateFunc = function (name) {
        var func = name;

        if (typeof name === 'string') {
            // support obj.func1.func2
            var names = name.split('.');

            if (names.length > 1) {
                func = window;
                $.each(names, function (i, f) {
                    func = func[f];
                });
            } else {
                func = window[name];
            }
        }

        if (typeof func === 'function') return func;
        return undefined;
    }

    /**
     * 默认属性，从 BootstrapTable 的默认属性扩展而来
     */
    JUpload.DEFAULTS = {
        /*对象id*/
        entityId: /*'ENTITY_ID_NOT_ASSIGNED',*/'_tmp_' + Math.uuid(32, 26),  //添加统一前缀，为清除垃圾数据
        /*附件类型code*/
        entityType: undefined,
        /*base url， 可修改*/
        url: $.helper.curl(base, '/sys/attach'),
        /*初始化参数的url*/
        paramsUrl: '/params',
        /*上传的url*/
        uploadUrl: '/upload',
        /*查询的url*/
        queryUrl: '/query',
        /*下载的url*/
        downUrl: '/download',
        /*删除的url*/
        removeUrl: '/remove',
        /*关联的url*/
        associateUrl: 'associate',
        /*允许上传*/
        uploadEnable: true,
        /*允许删除*/
        removeEnable: true,
        /*允许下载*/
        downloadEnable: true,
        /*允许预览*/
        previewEnable: true,
        /*每行显示文件数*/
        filesPerRow: 3
    };

    JUpload.EVENTS = [];


    /**
     * 初始化DOM结构
     */
    JUpload.prototype.init = function () {
        var $this = this, $opt = this.options, html = [];
        /*根元素增加样式*/
        this.$el.addClass('upload');
        /*保存实体ID*/
        this.$entityId = $opt.entityId;
        if (!$opt.filesPerRow || $opt.filesPerRow <= 0 || $opt.filesPerRow > 4 || isNaN($opt.filesPerRow)) {
            $opt.filesPerRow = 3;
        }
        $opt.filesPerRow = parseInt($opt.filesPerRow);

        /*已上传的文件列表*/
        html.push('<ul class="col-sm-12 upload-files"></ul>');

        /*消息提示区*/
        html.push('<div class="col-sm-12 upload-message">');
        html.push('</div>');

        /*上传表单， dropzone 区域*/
        if ($opt.uploadEnable) {
            /*禁止dropzone自动扫描*/
            Dropzone.autoDiscover = false;
            /*获取初始化参数，生成form表单*/
            $.ajax({
                url: $.helper.curl($opt.url, $opt.paramsUrl),
                type: 'post',
                async: false,
                data: {},
                success: function (data) {
                    $this.$session = data.uploadSession || Math.uuid(32, 26);

                    $this.$maxFiles = $opt.maxFiles || data.maxFiles || OPT_FILES;
                    $this.$remainFiles = $this.$maxFiles;
                    $this.$maxFileSize = $opt.maxFileSize || data.maxFileSize || OPT_FILE_SIZE;

                    if ($this.$maxFiles == -1) {
                        $this.$maxFiles = OPT_MAX_FILES;
                        $this.$remainFiles = $this.$maxFiles;
                    }
                    if ($this.$maxFileSize == -1) {
                        $this.$maxFileSize = OPT_MAX_FILE_SIZE;
                    }

                    $this.$includes = data.includes;
                    $this.$excludes = data.excludes;
                },
                error: function (xhr, e) {
                    $this.$session = Math.uuid(32, 26);
                    $this.$maxFiles = OPT_MAX_FILES;
                    $this.$remainFiles = $this.$maxFiles;
                    $this.$maxFileSize = OPT_MAX_FILE_SIZE;
                    $this.$includes = '';
                    $this.$excludes = '';
                }
            });

            html.push('<div id="' + this.$formId + '" method="post" role="form" class="col-sm-12 upload-dropzone dropzone">');
            /*html.push('<form id="' + this.$formId + '" name="' + this.$formId + '" method="post" role="form" class="form-horizontal dropzone">');*/
//	    	html.push('<input type="hidden" class="form-control" name="attachTypeCode" value="' + $opt.entityType + '">');
//	    	html.push('<input type="hidden" class="form-control" name="entityId" value="' + this.$entityId + '">');
//	    	html.push('<input type="hidden" class="form-control" name="uploadSession" value="' + this.$session + '">');
            /*html.push('</form>');*/
            html.push('</div>');
        }


        /*下载区域*/
        if ($opt.downloadEnable) {
            html.push('<div class="col-sm-12 upload-down"><iframe border="0" class="upload-down-frame"></iframe></div>');
        }

        html.push('</div>');
        this.$el.html(html.join(''));

        /*消息提示区*/
        this.$msgel = $('.upload-message', this.$el);

        /*开启上传*/
        if ($opt.uploadEnable) {
            /*初始化dropzone*/
            this.$dropzone = new Dropzone('#' + this.$formId, {
                url: $.helper.curl($opt.url, $opt.uploadUrl),
                method: 'post',
                timeout: 60000,
                parallelUploads: 2, //同时进行上传文件的个数
                uploadMultiple: 'no', //是否允许在一个请求中发送多个文件
                addRemoveLinks: false,
                previewsContainer: false,
                params: {
                    attachTypeCode: $opt.entityType,
                    entityId: this.$entityId,
                    uploadSession: this.$session
                },
                accept: function (file, done) {
                    /*判断文件个数*/
                    if ($this.$remainFiles <= 0) {
                        $this.alert('最多只能上传' + $this.$maxFiles + '个文件。', 'warning');
                        done('error');
                        return;
                    }
                    /*判断文件大小*/
                    if (file.size > $this.$maxFileSize * 1024 * 1024) {
                        $this.alert('文件大小[' + calculateSize(file.size) + '(MB)]超过限制[' + $this.$maxFileSize + '(MB)]。', 'warning');
                        done('error');
                        return;
                    }
                    /*验证文件类型*/
                    if (!$this.isAcceptType(file)) {
                        $this.alert('不允许上传的文件类型。', 'warning');
                        done('error');
                        return;
                    }

                    done();
                },
                dictDefaultMessage: "拖拽文件到此上传，或者单击打开文件选择框",
                dictFallbackMessage: "您的浏览器不支持拖放操作",
                dictFallbackText: "Please use the fallback form below to upload your files like in the olden days",
                dictFileTooBig: "文件大小 ({{filesize}}MiB) 超出限制: {{maxFilesize}}MB",
                dictInvalidFileType: "禁止上传的文件类型",
                dictResponseError: "服务器错误 {{statusCode}}",
                dictCancelUpload: "取消上传",
                dictCancelUploadConfirmation: "确定要取消上传吗?",
                dictRemoveFile: "删除文件",
                dictRemoveFileConfirmation: null,
                dictMaxFilesExceeded: "不能再上传更多文件"

            });

            /*上传成功*/
            this.$dropzone.on('success', function (file, data) {
                $.mask.remove();
                if (!data.success) {
                    $this.alert(data.msg, 'warning');
                    $this.$dropzone.removeFile(file);
                } else {
                    $this.alert(data.msg);
                    $.each(data.files, function () {
                        $this.appendFile(this);
                    })
                }
            });

            /*上传成功*/
            this.$dropzone.on('processing', function (file) {
                $.mask.show('正在上传, 请稍后...');
            });
        }

        /*初始化已有文件*/
        this.initFiles();

    };

    /**
     * 判断文件是否是允许的文件类型
     */
    JUpload.prototype.isAcceptType = function (file) {
        var $this = this, $opt = this.options, includes = this.$includes, excludes = this.$excludes;
        var ext = file.name.substring(file.name.lastIndexOf('.') + 1);

        if (includes && includes.trim().length > 0 && includes != '-1') {
            var inc = includes.split(',');
            if (!$.inArray(ext, inc) < 0) {
                return false;
            }
        }

        if (excludes && excludes.trim().length > 0 && excludes != '-1') {
            var exc = excludes.split(',');
            if ($.inArray(ext, exc) >= 0) {
                return false;
            }
        }

        return true;
    };

    /**
     * 显示提示消息
     */
    JUpload.prototype.alert = function (msg, type) {
        var $this = this, $opt = this.options;
        var icon = type || 'info';
        icon = /info|warning|error/g.test(type) ? type : 'info';
        var cls = 'text-' + (icon == 'error' ? 'danger' : icon);
        if (icon == 'error') icon = 'times';
        if (icon == 'info') icon = 'info-circle';

        var $msg = $('<div class="upload-msg"><i class="fa fa-' + icon + '"></i>&nbsp;' + msg + '</div>').appendTo(this.$msgel);
        $msg.addClass(cls);
        $msg.fadeIn(1000);
        setTimeout(function () {
            $msg.fadeOut(1000, function () {
                $msg.remove();
            });
        }, 3000);
    };

    /**
     * 初始化已有的文件
     */
    JUpload.prototype.initFiles = function () {
        var $this = this, $opt = this.options;
        /*清空数组*/
        this.$files = [], this.$remainFiles = this.$maxFiles;
        /*清除列表区域*/
        $('.upload-files', this.$el).html('');
        /*重新请求文件数据*/
        $.post($.helper.curl($opt.url, $opt.queryUrl),
            {entityId: $opt.entityId, uploadSession: this.$session, entityType: $opt.entityType},
            function (data) {
                if (data.success) {
                    $.each(data.files, function () {
                        $this.appendFile(this);
                    });
                }
            }
        );

    };

    /**
     * 删除数组中的文件
     * @param file 删除的文件
     */
    JUpload.prototype.removeFile = function (file) {
        var f = null;
        for (var i = 0; i < this.$files.length; i++) {
            f = this.$files[i];
            if (f.id === file.id) {
                this.$files.splice(i, 1);
                this.$remainFiles++;
                return;
            }
        }
    };


    /**
     * 增加已上传的文件
     * @param file 增加的文件
     */
    JUpload.prototype.appendFile = function (file) {
        var $this = this, $opt = this.options, html = [];
        var $fc = $('.upload-files', $this.$el);
        var col = 'col-' + (12 / $opt.filesPerRow);
        html.push('<li class="' + col + ' animated fadeInUp">');
        if ($opt.removeEnable) {
            html.push('<a class="file-remove"><i class="fa fa-lg fa-times" title="删除"></i></a>');
        }
        if ($opt.downloadEnable) {
            html.push('<a class="file-down"><i class="fa fa-lg fa-arrow-circle-o-down" title="下载"></i></a>');
        }
        html.push('<span class="file-icon icon-rar"></span>');
        if ($opt.previewEnable && this.isPreview(file) != -1) {
            html.push('<a class="file-name" title="' + file.srcFileName + '">' + file.srcFileName + '.' + file.fileFormat + '</a>');
        } else {
            html.push(file.srcFileName + '.' + file.fileFormat);
        }
        html.push('</li>');
        var $fi = $(html.join('')).appendTo($fc);
        this.$files.push(file);


        /*删除事件处理*/
        if ($opt.removeEnable) {
            $('.file-remove', $fi).off('click').on('click', function (e) {
                $.messager.confirm('提示', '确定要删除文件[' + file.srcFileName + '.' + file.fileFormat + ']吗？', function (r) {
                    if (r) {
                        var url = $.helper.curl($opt.url, $opt.removeUrl, '/', file.id);
                        $.mask.post(url, {}, function (data) {
                            if (data.success) {
                                /*先去掉动画样式，否则淡出不生效*/
                                $fi.removeClass('fadeInUp');
                                $fi.removeClass('animated');
                                /*提示消息*/
                                $this.alert("删除文件[" + file.srcFileName + "]成功.");
                                /*淡出后删除*/
                                $fi.fadeOut(1000, function () {
                                    $fi.remove();
                                });
                                /*删除一个文件*/
                                $this.removeFile(file);
                            } else {
                                /*删除失败，提示错误消息*/
                                $this.alert(data.msg, 'error');
                            }
                        });

                        /*移除各个按钮事件*/
                        $('.file-remove', $fi).off('click');
                        $('.file-down', $fi).off('click');
                        $('.file-name', $fi).off('click');
                    }
                });
            });
        }

        /*下载事件处理*/
        if ($opt.downloadEnable) {
            $('.file-down', $fi).off('click').on('click', function (e) {
                var url = $.helper.curl($opt.url, $opt.downUrl, '/', file.id);
                $('.upload-down-frame', $this.$el).attr('src', url);
            });
        }

        /*预览事件处理*/
        if ($opt.previewEnable) {
            $('.file-name', $fi).off('click').on('click', function (e) {
                $this.preview(file);
            });
        }

        $this.$remainFiles--;
    };

    /**
     * 文件是否可预览
     * @param file 文件
     * @return -1 - 不可预览， 0 - 图片， 1 - 文本
     */
    JUpload.prototype.isPreview = function (file) {
        if (this.isImage(file)) return 0;
        else if (this.isText(file)) return 1;
        else return -1;
    };

    /**
     * 判断文件是否为图片
     * @param file 文件
     */
    JUpload.prototype.isImage = function (file) {
        return /^(jpg|jpeg|png|gif)$/g.test(file.fileFormat);
    };

    /**
     * 判断文件是否为文本
     * @param file 文件
     */
    JUpload.prototype.isText = function (file) {
        return /^(txt|xml|java|js|css)$/g.test(file.fileFormat);
    };


    /**
     * 预览文件
     * @param file 显示的文件
     */
    JUpload.prototype.preview = function (file) {
        var $this = this, $opt = this.options;
        var html = [], ind = [], inner = [];
        var carId = this.$id + 'Carousel';

        /*预览区*/
        var $body = $(document.body);
        $('<div class="upload-preview"><div class="upload-preview-dialog"><div class="upload-preview-content"></div></div></div><div class="upload-preivew-backdrop fade in"></div>').appendTo($body);

        /*预览区*/
        var $preview = $('.upload-preview', $body);
        var $content = $('.upload-preview-content', $body);
        var $backdrop = $('.upload-preivew-backdrop', $body);
        var pw = $preview.width(), ph = $preview.height();
        var w = pw * 0.65, h = w * 0.6;

        html.push('<div class="carousel slide upload-slider" id="' + carId + '">');
        var i = 0;
        $.each(this.$files, function () {
            var type = $this.isPreview(this);
            var $that = this;
            /*可预览格式，增加指示器*/
            if (type != -1) {
                ind.push('<li data-slide-to="' + i + '" data-target="#' + carId + '"');
                if (file.id === this.id) ind.push(' class="active"');
                ind.push('></li>');

                /*追加轮播内容*/
                inner.push('<div class="item ');
                if (file.id === this.id) inner.push(' active ');
                inner.push('upload-slider-item">');
                if (type == 0) {
                    var imgId = carId + 'Img' + i;
                    var url = $.helper.curl(base, '/sys/attach/image/', this.id);
                    var $img = $("<img/>");
                    $img.attr('src', url).on('load', function () {
                        if (this.width > w) {
                            $('#' + imgId).css({top: '0px', left: '0px'});
                        } else {
                            $('#' + imgId).css({top: (h - this.height) / 2, left: (w - this.width) / 2});
                        }
                    });
                    inner.push('<div class="upload-slider-image"><img id="' + imgId + '" style="position:absolute" alt="' + this.srcFileName + '" class="img-responsive" src="' + url + '"></div>');
                } else {
                    var txtId = carId + 'txt' + i;
                    var url = $.helper.curl(base, 'sys/attach/text/', this.id);
                    inner.push('<div id="' + txtId + '" class="upload-slider-text"></div>');
                    $.post(url, {}, function (data) {
                        if (data.success) {
                            $('#' + txtId).text(data.content);
                        } else {
                            $('#' + txtId).text('文件加载失败。');
                        }
                    });
                }
                inner.push('<div class="carousel-caption"><p>');
                inner.push(this.srcFileName);
                inner.push('</p></div>');
                inner.push('</div>');

                i++;
            }
        });

        /*指示器*/
        html.push('<ol class="carousel-indicators">');
        Array.prototype.push.apply(html, ind);
        html.push('</ol>');

        /*内容*/
        html.push('<div class="carousel-inner">');
        Array.prototype.push.apply(html, inner);
        html.push('</div>');

        /*左右箭头*/
        html.push('<a data-slide="prev" href="#' + carId + '" class="left carousel-control"><span class="icon-prev"></span></a>');
        html.push('<a data-slide="next" href="#' + carId + '" class="right carousel-control"><span class="icon-next"></span></a>');
        html.push('</div>');

        $content.width(w);
        $content.height(h);

        //50 为修正值，原因在于页面是嵌入在 index.jsp 页面的 iframe 中的，页面顶部信息栏高度的影响，可能需要修改。
        var t = (document.body.clientHeight - ph) / 2 + top.document.body.scrollTop + 50;
        /*如果页面可视区域的高度不足以显示, 将 content 显示在页面最底部*/
        if (h > (document.documentElement.clientHeight - top.document.body.scrollTop - 50)) {
            t -= (h - (document.documentElement.clientHeight - top.document.body.scrollTop - 50) + 10);
        }
        $content.css({top: t, left: (pw - w) / 2});
        $(html.join('')).appendTo($content);

        /*关闭按钮及其事件处理*/
        var $close = $('<button type="button" class="upload-preview-close" aria-hidden="true">&times;</button>').appendTo($content);
        $('.carousel-inner', $content).css({height: h});
        $close.css({left: w - 26});
        $close.off('click').on('click', function () {
            $preview.remove();
            $backdrop.remove();
        });
        $preview.show();
    };

    /**
     * 将本次上传(uploadSession)的文件与指定的 entityId 相关联
     */
    JUpload.prototype.associate = function (entityId, callback) {
        var $this = this, $opt = this.options;
        $.post($.helper.curl($opt.url, $opt.associateUrl)
            , {entityId: entityId, uploadSession: this.$session}
            , function (data) {
                $opt.entityId = entityId;
                if (typeof callback == 'function') {
                    callback.apply($this, [data]);
                }
            }
        );
    };

    /**
     * 重新设置 entityId
     */
    JUpload.prototype.setEntityId = function (entityId) {
        this.$entityId = entityId;
        this.options.entityId = entityId;
        if (this.options.uploadEnable) {
            $('input[type=hidden][name=entityId]', this.$el).val(entityId);
        }
        this.initFiles();
    };

    /**
     * 返回当次上传的 uploadSession
     */
    JUpload.prototype.getSession = function () {
        return this.$session;
    };

    /**
     * 返回所有文件
     */
    JUpload.prototype.getFiles = function () {
        return this.$files;
    };

    /**
     * 可调用的方法
     */
    var allowedMethods = ['associate', 'setEntityId', 'getSession', 'getFiles'];

    $.fn.jUpload = function (option) {
        var value,
            args = Array.prototype.slice.call(arguments, 1);

        this.each(function () {
            var $this = $(this),
                data = $this.data('bootstrap.jUpload'),
                options = $.extend({}, JUpload.DEFAULTS, $this.data(),
                    typeof option === 'object' && option);

            if (typeof option === 'string') {
                if ($.inArray(option, allowedMethods) < 0) {
                    throw new Error("Unknown method: " + option);
                }

                if (!data) {
                    return;
                }

                value = data[option].apply(data, args);
                if (option === 'destroy') {
                    $this.removeData('bootstrap.jUpload');
                }
            }

            if (!data) {
                $this.data('bootstrap.jUpload', (data = new JUpload(this, options)));
            }
        });

        return typeof value === 'undefined' ? this : value;
    };


    $.fn.jUpload.Constructor = JUpload;
    $.fn.jUpload.defaults = JUpload.DEFAULTS;
    $.fn.jUpload.methods = allowedMethods;

    $(function () {
        $('[data-toggle="jUpload"]').jUpload();
    });

}(jQuery);