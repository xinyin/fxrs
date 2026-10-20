/**
 * String 的原型处理，增加 trim 方法
 */
String.prototype.trim = function () {
    var t = new String(this);
    t = t.replace(/(^\s*)|(\s*$)/g, "");
    return t.replace(/(^\u3000*)|(\u3000*$)/g, "");
};

String.prototype.toFirstUpperCase = function () {
    return this.substring(0, 1).toUpperCase() + this.substring(1);
};

String.prototype.isEmpty = function () {
    return this.trim().length == 0;
};

String.prototype.endsWith = function (suffix) {
    if (this.isEmpty()) return false;
    return this.substring(this.length - suffix.length) === suffix;

};

/**
 * 驼峰转减号
 */
String.prototype.camel2minus = function () {
    return this.replace(/([A-Z])/g, "-$1").toLowerCase();
}

/**
 * 数组扩展方法
 */
Array.prototype.unique = function () {
    var res = [];
    var json = {};
    for (var i = 0; i < this.length; i++) {
        if (!json[this[i]]) {
            res.push(this[i]);
            json[this[i]] = 1;
        }
    }
    return res;
}

Array.prototype.contains = function (p) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == p) {
            return true;
        }
    }
    return false;
}

/**
 * Number 的原型方法，去掉末尾的 0
 * @author Michael
 */
Number.prototype.rtrim = function () {
    var _fn = new String(this);
    if (_fn.indexOf('.') < 0) return _fn;

    if (/(\-?\d+)(.?)(\d*)/gi.test(_fn)) {
        var _fv = RegExp.$1;
        var _p = RegExp.$2;
        var _bv = RegExp.$3;
        if (_bv != '') {
            _fv += _p;
            if (/(0*)([1-9]+)/gi.test(_bv)) {
                _fv += _bv;
            } else if (/([1-9]+)(0*)/gi.test(_bv)) {
                _fv += _bv.substring(0, _bv.indexOf('0'));
            } else if (/(0+)([1-9]+)(0+)/gi.test(_bv)) {
                _fv += RegExp.$1 + RegExp.$2;
            }
        }
        return _fv;
    }
    return _fn;
};


/*!  
Math.uuid.js (v1.4)  
http://www.broofa.com  
mailto:robert@broofa.com  
   
Copyright (c) 2010 Robert Kieffer  
Dual licensed under the MIT and GPL licenses.  
*/

/*  
 * Generate a random uuid.  
 *  
 * USAGE: Math.uuid(length, radix)  
 *   length - the desired number of characters  
 *   radix  - the number of allowable values for each character.  
 *  
 * EXAMPLES:  
 *   // No arguments  - returns RFC4122, version 4 ID  
 *   >>> Math.uuid()  
 *   "92329D39-6F5C-4520-ABFC-AAB64544E172"  
 *  
 *   // One argument - returns ID of the specified length  
 *   >>> Math.uuid(15)     // 15 character ID (default base=62)  
 *   "VcydxgltxrVZSTV"  
 *  
 *   // Two arguments - returns ID of the specified length, and radix. (Radix must be <= 62)  
 *   >>> Math.uuid(8, 2)  // 8 character ID (base=2)  
 *   "01001010"  
 *   >>> Math.uuid(8, 10) // 8 character ID (base=10)  
 *   "47473046"  
 *   >>> Math.uuid(8, 16) // 8 character ID (base=16)  
 *   "098F4D35"  
 */
(function () {
    // Private array of chars to use
    var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');

    Math.uuid = function (len, radix) {
        var chars = CHARS, uuid = [], i;
        radix = radix || chars.length;

        if (len) {
            // Compact form
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
        } else {
            // rfc4122, version 4 form
            var r;

            // rfc4122 requires these characters
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';

            // Fill in random data.  At i==19 set the high bits of clock sequence as
            // per rfc4122, sec. 4.1.5
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random() * 16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }

        return uuid.join('');
    };

    // A more performant, but slightly bulkier, RFC4122v4 solution.  We boost performance
    // by minimizing calls to random()
    Math.uuidFast = function () {
        var chars = CHARS, uuid = new Array(36), rnd = 0, r;
        for (var i = 0; i < 36; i++) {
            if (i == 8 || i == 13 || i == 18 || i == 23) {
                uuid[i] = '-';
            } else if (i == 14) {
                uuid[i] = '4';
            } else {
                if (rnd <= 0x02) rnd = 0x2000000 + (Math.random() * 0x1000000) | 0;
                r = rnd & 0xf;
                rnd = rnd >> 4;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
        return uuid.join('');
    };

    // A more compact, but less performant, RFC4122v4 solution:
    Math.uuidCompact = function () {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    };
})();

/**
 * 解决表单提交时，disabled的属性无法一同提交的问题
 */
(function ($) {

    $.fn.serializeAll = function () {
        var data = $(this).serializeArray();
        $(':disabled[name]', this).each(function () {
            var $val = $(this).val();
            if ($val) {
                data.push({name: this.name, value: $val});
            }
        });
        return data;
    };

})(jQuery);

/**
 * 遮罩层
 */
jQuery.mask = {

    /**
     * post
     */
    post: function (url, data, fn) {
        var _data, _fn;
        if (data) {
            if ($.isFunction(data)) {
                _fn = data;
            } else {
                _data = data;
                if (fn && $.isFunction(fn)) {
                    _fn = fn;
                }
            }
        }

        //显示遮罩层
        $.mask.show();
        $.post(url, _data, function (data) {
            //移除遮罩层
            $.mask.remove();
            if (_fn) _fn.call(this, data);
        });
    },

    /**
     * 显示遮罩层
     */
    show: function (msg, closeOnClick) {
        msg = msg || '正在处理，请稍后......';
        var $doc = $(top.document.body);
        var $backdrop = top.$('<div class="jep-backdrop fade in jep-loading-box"></div>').appendTo($doc);
        var $container = top.$('<div class="jep-loading-container"></div>').appendTo($backdrop);
        var $load = top.$('<div class="sk-spinner sk-spinner-circle jep-loading"></div>').appendTo($container);
        for (var i = 1; i <= 12; i++) {
            top.$('<div class="sk-circle' + i + ' sk-circle"></div>').appendTo($load);
        }
        $('<span class="jep-loading-text">' + msg + '</span>').appendTo($container);

        /*单击遮罩层的事件处理*/
//		if(closeOnClick)
//		{
//			$backdrop.off('click').on('click', function(e){
//				$backdrop.off('click');
//				$.mask.remove();
//			});
//		}

    },

    /**
     * 移除遮罩层
     */
    remove: function () {
        var $doc = $(top.document.body);
        var $box = $doc.find('.jep-loading-box');
        $box.remove();
    }
};

jQuery.importData = {
    /*导入*/
    doImport: function (code, url, callback, params) {
        var args = {};
        args.templateCode = code;
        args.url = url;
        //if(template != undefined) args.template = template;
        if (callback != undefined) args.callback = callback;
        if (params != undefined) args.params = params;

        var title = '导入数据';
        $.ajax({
            async: false,
            type: 'POST',
            url: base + '/sys/import/getParam',
            data: {code: code},
            success: function (data) {
                title = '导入' + data.title;
                //打开导入页面
                $.messager.modal({
                    winId: 'wImport',
                    width: 650,
                    height: 350,
                    title: title,
                    args: args,
                    url: base + '/sys/import/import',
                    onClose: function () {
                    }
                });
            }
        });

    },
    addTemplate: function (template) {
        /*转换成 json 串*/
        var data = $.toJSON(template);
        var success = false;
        $.ajax({
            async: false,
            type: 'POST',
            url: contextPath + '/bas/import/addTemplate',
            data: {data: data},
            success: function (data) {
                success = data.success;
            }
        });
        return success;
    }
};


/**
 * base64 编解码函数
 * @returns
 */
function Base64() {

    // private property
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    // public method for encoding
    this.encode = function (input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;
        input = _utf8_encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output +
                _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
                _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
        }
        return output;
    }

    // public method for decoding
    this.decode = function (input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        while (i < input.length) {
            enc1 = _keyStr.indexOf(input.charAt(i++));
            enc2 = _keyStr.indexOf(input.charAt(i++));
            enc3 = _keyStr.indexOf(input.charAt(i++));
            enc4 = _keyStr.indexOf(input.charAt(i++));
            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;
            output = output + String.fromCharCode(chr1);
            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }
        }
        output = _utf8_decode(output);
        return output;
    }

    // private method for UTF-8 encoding
    _utf8_encode = function (string) {
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }
        return utftext;
    }

    // private method for UTF-8 decoding
    _utf8_decode = function (utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;
        while (i < utftext.length) {
            c = utftext.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }
}

/**
 * helper
 */
jQuery.helper = {

    DEFAULT_MIN_HEIGHT: 600,

    /**
     * 生成全局UUID
     */
    uuid: function () {
        return Math.uuid(8, 30);
    },

    /**
     * iframe 宽高自适应
     */
    autoResizeIframe: function (iframeId, minHeight) {
        var min = minHeight || this.DEFAULT_MIN_HEIGHT;

        var resizeIframe = function (iframeId, minHeight) {
            var browserVersion = window.navigator.userAgent.toUpperCase();
            var isOpera = browserVersion.indexOf("OPERA") > -1 ? true : false;
            var isFireFox = browserVersion.indexOf("FIREFOX") > -1 ? true : false;
            var isChrome = browserVersion.indexOf("CHROME") > -1 ? true : false;
            var isSafari = browserVersion.indexOf("SAFARI") > -1 ? true : false;
            var isIE = (!!window.ActiveXObject || "ActiveXObject" in window);
            var isIE9More = (!-[1,] == false);

            return function () {
                try {
                    var iframe = document.getElementById(iframeId);
                    var bHeight = 0;
                    if (isChrome == false && isSafari == false)
                        bHeight = iframe.contentWindow.document.body.scrollHeight;

                    var dHeight = 0;
                    if (isFireFox == true)
                        dHeight = iframe.contentWindow.document.documentElement.offsetHeight + 2;
                    else if (isIE == false && isOpera == false)
                        dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
                    else if (isIE == true && isIE9More) {// ie9+
                        var heightDeviation = bHeight
                            - eval("window.['IE9MoreRealHeight" + iframeId + "']");
                        if (heightDeviation == 0) {
                            bHeight += 3;
                        } else if (heightDeviation != 3) {
                            eval("window.['IE9MoreRealHeight" + iframeId + "']=" + bHeight);
                            bHeight += 3;
                        }
                    } else
                        // ie[6-8]、OPERA
                        bHeight += 3;

                    var height = Math.max(bHeight, dHeight);
                    if (height < minHeight)
                        height = minHeight;
                    iframe.style.height = height + "px";
                } catch (ex) {
                }
            };
        }(iframeId, minHeight);

        eval("window['IE9MoreRealHeight" + iframeId + "']=0");
        window.setInterval(resizeIframe, 100);
    },
    /*连接多个url， 并在url前加上上下文路径*/
    curl: function () {
        var $url = '';
        $.each(arguments, function () {
            $url += '/' + this;
        });
        $url + '/';
        return $url.replace(/\/\//g, '/');
    },
    /*base64编码*/
    base64Encode: function (str) {
        var b = new Base64();
        return b.encode(str);
    },
    /*base64解码*/
    base64Decode: function (str) {
        var b = new Base64();
        return b.decode(str);
    }


};

/**
 * 模态框
 */
jQuery.messager = {

    /**
     * 基本参数
     */
    defaults: {
        title: '标题',
        size: 'auto',
        closable: true
    },

    /**
     * 按钮的默认处理程序，默认关闭窗口
     */
    defaultHandler: function () {
        return true;
    },

    /**
     * 取消按钮
     */
    BTN_CANCEL: {
        text: '关闭',
        icon: 'times',
        type: 'default',
        value: false
    },

    BTN_NO: {
        text: '否',
        icon: 'times',
        type: 'default',
        value: false
    },

    BTN_YES: {
        text: '是',
        icon: 'check',
        type: 'default',
        value: true
    },

    /**
     * 确定按钮
     */
    BTN_OK: {
        text: '确定',
        icon: 'check',
        type: 'primary',
        value: true
    },

    /**
     * 显示 toastr 对话框
     */
    toastr: function (title, content, type) {
        if (!type) type = 'info';
        top.toastr.options = {
            "closeButton": true,
            "debug": false,
            "progressBar": false,
            "preventDuplicates": false,
            "positionClass": "toast-top-center",
            "onclick": null,
            "showDuration": "400",
            "hideDuration": "1000",
            "timeOut": "2000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        };
        top.toastr[type](content, title);
    },

    /**
     * 警告对话框
     * @param title        标题
     * @param content    提示内容
     * @param type        对话框类型， info | warning | error
     * @param fn        回调函数，对话框关闭后执行，回调函数接受一个参数，即按钮的value
     */
    alert: function (title, content, type, fn) {
        /*检查参数*/
        if (!title || !content) return;

        var $icon, $iconType, $fn;

        if (typeof type == 'string') {
            switch (type) {
                case 'info':
                    $icon = 'info';
                    $iconType = 'success';
                    break;
                case 'warning':
                    $icon = 'warning';
                    $iconType = 'warning';
                    break;
                case 'error':
                    $icon = 'times';
                    $iconType = 'danger';
                    break;
            }
        } else if (typeof type == 'function') {
            $fn = type;
        }

        /*如果提供了fn参数，以fn为准*/
        if (typeof fn == 'function') {
            $fn = fn;
        }


        /*生成代理函数，返回true可关闭对话框*/
        var $handler = $fn ? function (r) {
            $fn(r);
            return true;
        } : this.defaultHandler;

        /*重写函数的handler*/
        var $btnOk = $.extend({}, this.BTN_OK, {handler: $handler});

        /*显示模态对话框*/
        this.modal({
            title: title,
            content: content,
            size: 'small',			//小号对话框
            closable: true,		//不可关闭
            icon: $icon,			//图标
            iconType: $iconType,	//图标类型
            buttons: [				//显示一个确定按钮
                $btnOk
            ]
        });
    },

    /**
     * 确认对话框
     * @param title        标题
     * @param content    提示内容
     * @param fn        回调函数，对话框关闭后执行，回调函数接受一个参数，取消(false)，确认(true)
     */
    confirm: function (title, content, fn) {
        /*检查参数*/
        if (!title || !content) return;

        /*生成回调函数，返回true关闭对话框*/
        var $handler = fn ? function (r) {
            fn(r);
            return true;
        } : this.defaultHandler;

        /*扩展取消和确认按钮，重写回调函数*/
        var $btnCancel = $.extend({}, this.BTN_CANCEL, {handler: $handler});
        var $btnOk = $.extend({}, this.BTN_OK, {handler: $handler});

        /*显示模态对话框*/
        this.modal({
            title: title,
            content: content,
            size: 'small',			//小号对话框
            closable: false,		//不可关闭
            icon: 'question',		//图标
            iconType: 'success',	//图标类型
            buttons: [				//显示取消和确定按钮
                $btnCancel,
                $btnOk
            ]
        });
    },



    yesOrNo: function (title, content, fn) {
        /*检查参数*/
        if (!title || !content) return;

        /*生成回调函数，返回true关闭对话框*/
        var $handler = fn ? function (r) {
            fn(r);
            return true;
        } : this.defaultHandler;

        /*扩展取消和确认按钮，重写回调函数*/
        var $btnCancel = $.extend({}, this.BTN_NO, {handler: $handler});
        var $btnOk = $.extend({}, this.BTN_YES, {handler: $handler});

        /*显示模态对话框*/
        this.modal({
            title: title,
            content: content,
            size: 'small',			//小号对话框
            closable: false,		//不可关闭
            icon: 'question',		//图标
            iconType: 'success',	//图标类型
            buttons: [				//显示取消和确定按钮
                $btnCancel,
                $btnOk
            ]
        });
    },

    /**
     * 将一个按钮定义转换为html代码
     */
    _wrapButton: function (btn) {
        var str = '<button type="button" class="btn btn-' + btn.type + '">';
        if (btn.icon) {
            str += '<i class="fa fa-' + btn.icon + '"></i> ';
        }
        str += btn.text + '</button>';
        return str;
    },

    /**
     * 关闭一个窗口
     */
    hide: function (winId) {
        var $modal = top.$('#' + winId);
        if ($modal[0]) {
            $modal.modal('hide');
        }
    },

    /**
     * 获取窗口的参数
     */
    args: function (winId) {
        var $modal = top.$('#' + winId);
        if ($modal[0]) {
            return $modal.data('jep.modal.args');
        }
    },

    /**
     * 显示一个模态对话框
     */
    modal: function (options) {
        /*控制最多只能有6个对话框弹出*/
        if (!top.jepModalCount) {
            top.jepModalCount = 0;
        }
        top.jepModalCount++;

        /*合并选项*/
        var $option = $.extend({}, this.defaults, typeof options == 'object' && options);

        /*如果没有提供winId，生成一个uuid*/
        var $winId;
        if ($option.winId && typeof $option.winId == 'string') {
            $winId = $option.winId;
        } else {
            $winId = 'modal' + $.helper.uuid();
        }

        /*创建一个新的模态对话框*/
        var $doc = $(top.document.body);
        var $modal = top.$('<div class="jepModal-' + top.jepModalCount + ' modal fade" id="' + $winId + '" data-backdrop="static"></div>').appendTo($doc);
        var $dialog = top.$('<div class="modal-dialog"></div>').appendTo($modal);
        var $content = top.$('<div class="modal-content"></div>').appendTo($dialog);

        /*标题，判断是否可关闭*/
        var $header = top.$('<div class="modal-header"></div>').appendTo($content);
        if ($option.closable) {
            $('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>').appendTo($header);
        }
        $('<h4 class="modal-title">' + $option.title + '</h4>').appendTo($header);

        /*对话框内容*/
        var $body = top.$('<div class="modal-body"></div>').appendTo($content);


        /*设置对话框大小*/
        var $size = $option.size;
        var $width = $option.width;
        var $height = $option.height;

        /*
         * 定义了宽高，以宽高为准，否则以size为准，宽高必须同时设置才有效
         * 高度是body的高度，不是整个modal的高度。
         */
        if ($width && $height) {
            $dialog.width($width);
            $body.height($height);
        } else if ($size) {
            /*small 不设置高度*/
            if ($size == 'small') {
                $dialog.width(450);
            }
            /*large 设置高度为450*/
            else if ($size == 'large') {
                $dialog.width(900);
                $body.height(450);
            }
        }

        /*判断是content还是url， url 使用内嵌 iframe实现，content直接设置文本*/
        var $url = $option.url;
        if ($url) {
            var $iframe = top.$('<iframe frameborder="0" style="margin:0;padding:0;width:100%;" scrolling="yes"></iframe>').appendTo($body);
            /*使用iframe， 去掉边距*/
            $body.css({'padding': '0px'});
            $iframe.height($body.height());
            $iframe.attr('src', $url);
        } else {
            /*是否定义了图标和图标样式*/
            if ($option.icon && $option.iconType) {
                top.$('<div class="row"><div class="col-md-2"><span class="label label-' + $option.iconType + ' label-lg label-circle"><i class="fa fa-' + $option.icon + '"></i></span></div><div class="col-md-10">' + $option.content + '</div></div>').appendTo($body);
                /*增加了row和col后，边距会加大，重新设置body的边距*/
                $body.css({'padding': '20px'});
            } else {
                /*设置文本内容*/
                $body.html($option.content);
            }
        }


        /*增加按钮*/
        if ($option.buttons) {
            /*增加 footer*/
            var $footer = top.$('<div class="modal-footer"></div>').appendTo($content);

            /*遍历所有的按钮*/
            for (i = 0; i < $option.buttons.length; i++) {
                var $button = $option.buttons[i];
                var $btn = top.$(this._wrapButton($button)).appendTo($footer);

                var $this = this;
                $btn.on('click', function (button, modal) {
                    return function () {
                        /*先关闭对话框*/
                        modal.modal('hide');
                        /*将按钮上定义的value传递给handler*/
                        var $handler = button.handler ? button.handler : $this.defaultHandler;
                        $handler(button.value);
                    };
                }($button, $modal));
            }
        }
        /*没有按钮，增加高度*/
        else {
            //$footer.height(20);
        }

        /*保存对话框参数*/
        var $args = $.extend({}, $modal.data(), $option.args);
        $modal.data('jep.modal.args', $args);

        /*显示对话框*/
        $modal.modal({
            backdrop: 'static',
            keyboard: true
        });

        //关闭时移除modal
        $modal.on('hide.bs.modal', function (e) {
            top.jepModalCount--;

            // $.mask.remove();
            if (this.remove){
                this.remove(true);
            }else {
                this.removeNode(true);
            }


        });

        //关闭后移除modal
        $modal.on('hidden.bs.modal', function (e) {
            if (typeof $option.onClose === 'function') {
                $option.onClose.apply(this);
            }
        });

    }

};


/**
 * 增加格式化函数
 */
jQuery.format = {

    /**
     * 将一个数字格式化为千分位。
     */
    commafy: function (num) {
        num = num + '';
        var re = /(\-?\d+)(\d{3})/;
        while (re.test(num)) {
            num = num.replace(re, "$1,$2");
        }
        return num;
    },
    /**
     * 将一个数字格式化为千分位，四舍五入。
     */
    roundCommafy: function (num, n) {
        var a = parseFloat(num + '');
        if (isNaN(a)) return num;

        var b = Math.pow(10, n);
        a = Math.round(a * b) / b;
        return $.format.commafy(a);
    }
};


/**
 * 日期命名空间
 */
jQuery.date = {
    DATE_FORMAT_1: 'yyyy-MM-dd HH:mm:ss',
    DATE_FORMAT_2: 'yyyy-MM-dd HH:mm',
    DATE_FORMAT_3: 'HH:mm',
    DATE_FORMAT_4: 'yyyy-MM-dd',
    DATE_FORMAT_5: 'h:mmtt',
    DATE_FORMAT_6: 'yyyyMMdd',
    /**
     * 将指定日期根据格式，格式化为字符串
     * param date 日期
     * param format 日期格式
     */
    format: function (date, format) {
        var o = {
            "M+": date.getMonth() + 1, //month
            "d+": date.getDate(), //day
            "h+": date.getHours() >= 12 ? date.getHours() - 12 : date.getHours(), //hour
            "H+": date.getHours(),
            "m+": date.getMinutes(), //minute
            "s+": date.getSeconds(), //second
            "q+": Math.floor((date.getMonth() + 3) / 3), //quarter
            "S": date.getMilliseconds(),
            "tt": date.getHours() < 12 ? 'am' : 'pm',
            "t": date.getHours() < 12 ? 'a' : 'p',
            "TT": date.getHours() < 12 ? 'AM' : 'PM',
            "T": date.getHours() < 12 ? 'A' : 'P'
        };

        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        }

        for (var k in o) {
            if (new RegExp("(" + k + ")", "g").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    },
    /**
     * 将指定字符串，按照一个格式，转换为日期对象
     * param dateStr 日期字符串
     * param format 格式
     * return 如果格式错误，或者与字符串不匹配，返回 undefined
     */
    parse: function (dateStr, format) {
        var dateformat = format;
        if (dateformat == undefined) {
            dateformat = 'YYYY-MM-dd hh:mm:ss';
        }

        /*第一步，先解析格式，识别特殊字符并加上括号*/
        if (/(d+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '(' + RegExp.$1.toUpperCase() + ')');
        }
        if (/(y+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '(' + RegExp.$1.toUpperCase() + ')');
        }
        if (/(M+)/g.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '(' + RegExp.$1 + ')');
        }
        if (/(h+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '(' + RegExp.$1.toUpperCase() + ')');
        }
        if (/(m+)/g.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '(' + RegExp.$1 + ')');
        }
        if (/(s+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '(' + RegExp.$1 + ')');
        }

        /*第二步，解析修改后的格式，识别位置*/
        var position = {
            year: -1,
            month: -1,
            date: -1,
            hour: -1,
            minute: -1,
            second: -1
        };

        var reg = new RegExp('(\(\\w+\))', 'g');
        var result = dateformat.match(reg);
        for (var i = 0; i < result.length; i++) {
            switch (result[i]) {
                case 'YYYY':
                    position.year = i + 1;
                    break;
                case 'MM':
                    position.month = i + 1;
                    break;
                case 'DD':
                    position.date = i + 1;
                    break;
                case 'HH':
                    position.hour = i + 1;
                    break;
                case 'mm':
                    position.minute = i + 1;
                    break;
                case 'ss':
                    position.second = i + 1;
                    break;
            }
        }

        /*第三步，生成最终的正则表达式*/
        if (/(d+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '\\d{' + RegExp.$1.length + '}');
        }
        if (/(y+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '\\d{' + RegExp.$1.length + '}');
        }
        if (/(M+)/g.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '\\d{' + RegExp.$1.length + '}');
        }
        if (/(h+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '\\d{' + RegExp.$1.length + '}');
        }
        if (/(m+)/g.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '\\d{' + RegExp.$1.length + '}');
        }
        if (/(s+)/gi.test(dateformat)) {
            dateformat = dateformat.replace(RegExp.$1, '\\d{' + RegExp.$1.length + '}');
        }

        if (new RegExp(dateformat).test(dateStr)) {
            var year = eval('RegExp.$' + position.year);
            var month = eval('RegExp.$' + position.month);
            var day = eval('RegExp.$' + position.date);
            var hour = eval('RegExp.$' + position.hour);
            var minute = eval('RegExp.$' + position.minute);
            var second = eval('RegExp.$' + position.second);

            var date = new Date();
            var value = null;
            if (position.year != -1) {
                value = parseInt(year.replace(/^0+/, ''));
                date.setFullYear(value);
            }

            if (position.month != -1) {
                value = parseInt(month.replace(/^0/, ''));
                date.setMonth(value - 1);
            }

            if (position.date != -1) {
                value = parseInt(day.replace(/^0/, ''));
                date.setDate(value);
            }

            if (position.hour != -1) {
                value = parseInt(hour.replace(/^0/, ''));
                date.setHours(value);
            }

            if (position.minute != -1) {
                value = parseInt(minute.replace(/^0/, ''));
                date.setMinutes(value);
            }

            if (position.second != -1) {
                value = parseInt(second.replace(/^0/, ''));
                date.setSeconds(value);
            }
            return date;
        }
    },
    /*增加日期*/
    dateAdd: function (date, interval, offset) {
        var number = 0;
        switch (interval) {
            case 's':
                number = 1000;
                break;
            case 'n':
                number = 1000 * 60;
                break;
            case 'h':
                number = 1000 * 60 * 60;
                break;
            case 'd':
                number = 1000 * 3600 * 24;
                break;
            case 'm':
                number = 1000 * 3600 * 24 * 30;
                break;
            case 'y':
                number = 1000 * 3600 * 24 * 365;
                break;
            default:
                number = 0;
        }
        var time = date.getTime();
        time += number * offset;
        return new Date(time);
    }

};

/**
 * 排序法命名空间
 */
jQuery.sort = {

    /**
     * 默认的升序比较接口，提供基本类型比较大小结果
     * @param o1
     * @param o2
     * @returns 小于0表示 o1 < o2, 等于0表示 o1 = o2, 大于0表示 o1 > o2
     */
    ascComparator: function (o1, o2) {
        if (typeof o1 == 'string' && typeof o2 == 'string') {
            if (o1 < o2) return -1;
            if (o1 > o2) return 1;
            return 0;
        } else if (typeof o1 == 'number' && typeof o2 == 'number') {
            if (o1 < o2) return -1;
            if (o1 > o2) return 1;
            return 0;
//			return o1 - o2;
        }
        return 0;
    },
    /**
     * 默认的降序比较接口，提供基本类型比较大小结果
     * @param o1
     * @param o2
     * @returns 小于0表示 o1 < o2, 等于0表示 o1 = o2, 大于0表示 o1 > o2
     */
    descComparator: function (o1, o2) {
        if (typeof o1 == 'string' && typeof o2 == 'string') {
            if (o1 > o2) return -1;
            if (o1 < o2) return 1;
            return 0;
        } else if (typeof o1 == 'number' && typeof o2 == 'number') {
            return o2 - o1;
        }
        return 0;
    },
    /**
     * 冒泡算法的实现
     * @param array 数组
     * @param comparator 比较接口
     */
    bubbleSort: function (array, comparator) {
        if (comparator == undefined) comparator = $.sort.ascComparator;
        var comp;
        for (var i = 0; i < array.length - 1; i++) {
            for (var j = array.length - 1; j > 0; j--) {
                comp = comparator(array[j - 1], array[j]);
                if (comp > 0) {
                    var temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                }
            }
        }
    },
    /**
     * 插入排序法，性能略好于冒泡排序
     * @param array
     * @param comparator
     */
    insertSort: function (array, comparator) {
        if (comparator == undefined) comparator = $.sort.ascComparator;
        var temp, comp;
        for (var i = 1; i < array.length; i++) {
            var temp = array[i];
            for (var j = i; j > 0; j--) {
                comp = comparator(array[j - 1], temp);
                if (comp > 0) {
                    array[j] = array[j - 1];
                    continue;
                }
                break;
            }
            array[j] = temp;
        }
    },
    /**
     * 选择排序
     * @param array
     * @param comparator
     */
    selectSort: function (array, comparator) {
        if (comparator == undefined) comparator = $.sort.ascComparator;
        var min, temp, comp;
        for (var i = 0; i < array.length; i++) {
            min = i;
            for (var j = i + 1; j < array.length; j++) {
                comp = comparator(array[min], array[j]);
                if (comp > 0)
                    min = j;
            }
            if (min != i) {
                temp = array[i];
                array[i] = array[min];
                array[min] = temp;
            }
        }
    }
};


/**
 * 字典命名空间
 */
jQuery.dict = {

    /**
     * baseurl 属性：访问服务器取得字典数据的 url, 如果有变化，可以在脚本开始时用 : $.dict.baseurl = 'myUrl' 来重新设置。
     */
    baseurl: base + '/sys/dictItem/getDictItems',
    /**
     * 字典中的数据集合，默认没有任何数据
     */
    values: {},
    /**
     * 初始化一个字典
     * @param dictName 字典的名称
     */
    init: function (dictCode) {
        var baseurl = $.dict.baseurl;
        if (baseurl.substring(baseurl.length - 1, baseurl.length) != '/') baseurl = baseurl + '/';
        $.ajax({
            async: false,
            type: "POST",
            url: baseurl + dictCode,
            dataType: "json",
            success: function (data) {
                $.dict.set(dictCode, data);
            }
        });
    },
    /**
     * 获得一个字典数据，类型为数组, 数组中的对象属性为 name 和 value.
     * @param dictName 字典名称
     * @returns 对象数组
     */
    get: function (dictCode) {
        if ($.dict.values[dictCode] == undefined) {
            $.dict.init(dictCode);
        }
        return $.dict.values[dictCode];
    },
    /**
     * 设置一个字典数据，类型为数据
     * @param dictName 字典名称
     * @param data  数据
     */
    set: function (dictCode, data) {
        if (data == undefined || data == null) {
            $.dict.values[dictCode] = {};
        } else {
            $.dict.values[dictCode] = data;
        }
    },
    /**
     * 从字典中获得和指定值对应的 label
     * @param dictName  字典名称
     * @param value       值
     * @returns              字典的label
     */
    getLabel: function (dictCode, value) {
        var data = $.dict.get(dictCode);
        for (var i = 0; i < data.length; i++) {
            if (value == data[i].value) {
                return data[i].name;
            }
        }
    }
};

/**
 * 数据字典的命名空间
 */
jQuery.fixedDict = {
    /**
     * baseurl 属性：访问服务器取得字典数据的 url, 如果有变化，可以在脚本开始时用 : $.fixedDict.baseurl = 'myUrl' 来重新设置。
     */
    baseurl: base + '/fixedDict/',
    /**
     * 字典中的数据集合，默认没有任何数据
     */
    values: {},
    /**
     * 初始化一个字典
     * @param dictName 字典的名称
     */
    init: function (dictName) {
        var baseurl = $.fixedDict.baseurl;
        if (baseurl.substring(baseurl.length - 1, baseurl.length) != '/') baseurl = baseurl + '/';
        $.ajax({
            async: false,
            type: "POST",
            url: baseurl + dictName,
            dataType: "json",
            success: function (data) {
                $.fixedDict.set(dictName, data);
            }
        });
    },
    /**
     * 获得一个字典数据，类型为数组, 数组中的对象属性为 name 和 value.
     * @param dictName 字典名称
     * @returns 对象数组
     */
    get: function (dictName) {
        if ($.fixedDict.values[dictName] == undefined) {
            $.fixedDict.init(dictName);
        }
        return $.fixedDict.values[dictName];
    },
    /**
     * 设置一个字典数据，类型为数据
     * @param dictName 字典名称
     * @param data  数据
     */
    set: function (dictName, data) {
        if (data == undefined || data == null) {
            $.fixedDict.values[dictName] = {};
        } else {
            $.fixedDict.values[dictName] = data;
        }
    },
    /**
     * 从字典中获得和指定值对应的 label
     * @param dictName  字典名称
     * @param value       值
     * @returns              字典的label
     */
    getLabel: function (dictName, value) {
        var data = $.fixedDict.get(dictName);
        for (var i = 0; i < data.length; i++) {
            if (value == data[i].value) {
                return data[i].name;
            }
        }
    }
};


/**
 * 页面数据缓存，在 dict 和 fixedDict 的基础上扩展，
 * 支持自定义的数据来源, 支持前端过滤
 * 可以取代 dict 和 fixedDict
 */
jQuery.cache = {

    /*存储数据的对象*/
    _values: {},

    /*运算符*/
    _opers: {
        'EQ': '==',
        'NE': '!=',
        'GT': '>',
        'LT': '<',
        'GE': '>=',
        'LE': '<='
    },

    /**
     * 缓存数据，请求是同步的。
     * @param type         数据类型， dict/fixedDict/custom
     * @param url          对于 dict， 为字典名称，对于 fixedDict 为类名，对于 custom， 为url
     * @param code         缓存中的键值，如果不提供，默认等于 url
     */
    cache: function (type, url, code) {
        if (!type || !url) return;

        var _url = null;
        var _c = code ? code : url;
        if (this._values[_c]) {
            return;
        }

        if (type === 'dict') {
            _url = base + '/sys/dictItem/getDictItems/' + url;
        } else if (type === 'fixedDict') {
            _url = base + '/fixedDict/' + url;
        } else {
            _url = base + '/' + url;
        }

        if (_url == null) return;

        var _s = this;
        $.ajax({
            async: false,
            type: "POST",
            url: _url,
            dataType: "json",
            success: function (data) {
                _s._values[_c] = data;
            }
        });
    },

    /**
     * 从缓存中获取数据,dict/fixedDict的数据均为 {name:xxx, value:xxx} 格式, custom 的为对象原有格式
     * @param code 缓存名称
     * @param value 需要过滤的字段
     * @param includes 需要包括的值
     * @param excludes 需要排除的值
     */
    getFilteredCache: function (code, valueField, includes, excludes) {
        if (!valueField) return this.getCache(code);

        var _fs = [];
        if (excludes && excludes != '' && excludes != 'null') {
            var _exc = excludes.split(',');
            if (_exc && _exc.length > 0) {
                for (var i = 0; i < _exc.length; i++) {
                    var _eo = {
                        name: 'NE_' + valueField,
                        value: _exc[i]
                    };
                    _fs.push(_eo);
                }
            }
        }

        var _fv = this.getCache(code, _fs);
        var _nfv = [];

        if (includes && includes != '' && includes != 'null') {
            var _inc = includes.split(',');
            for (var i = 0; i < _fv.length; i++) {
                var _v = _fv[i];
                if (_v[valueField] === '') {
                    _nfv.push(_v);
                    continue;
                }

                for (var j = 0; j < _inc.length; j++) {
                    if (_v[valueField] === _inc[j]) _nfv.push(_v);
                }
            }
        } else {
            _nfv = _fv;
        }

        return _nfv;
    },

    /**
     * 从缓存中获取数据，dict/fixedDict的数据均为 {name:xxx, value:xxx} 格式, custom 的为对象原有格式
     * @param code       缓存的名称
     * @param filters    数组，需过滤数据的条件。格式：[{name:'EQ_controlType', value:'xxx'}]
     */
    getCache: function (code, filters) {
        if (!code) return;

        var _vs = this._values[code];

        if (!filters) return _vs;

        if (!_vs || !_vs.length) return;

        var _fs = [];
        for (var i = 0; i < filters.length; i++) {
            var _f = filters[i];
            var _n = _f.name;
            var _v = _f.value;
            if (!/^(\w{2})\_(\w+)$/g.test(_n)) continue;
            var _p = _n.split('_');
            var _o = this._opers[_p[0]];
            var _pn = _p[1];
            _fs.push(_pn + ' ' + _o + ' \'' + _v + '\'');
        }

        var _rvs = [];
        for (var i = 0; i < _vs.length; i++) {
            var _o = _vs[i];
            var _val = true;
            for (var j = 0; j < _fs.length; j++) {
                try {
                    _val = Boolean(eval('with(_o){' + _fs[j] + '}'));
                    if (!_val) break;
                } catch (e) {
                }
            }
            if (!_val) continue;
            _rvs.push(_o);
        }
        return _rvs;
    },

    /**
     * 从缓存中获取指定的缓存数据，用来为 datagrid 提供 formatter 函数
     * @param code          缓存代码
     * @param value         值
     * @param nameField     名称字段， dict/fixedDict 无需提供
     * @param valueField    值字段， dict/fixedDict 无需提供
     */
    getLabel: function (code, value, nameField, valueField) {
        if (!code || !value) return;
        var _nf = nameField ? nameField : 'name';
        var _vf = valueField ? valueField : 'value';
        var _vs = this._values[code];

        if (!_vs || !_vs.length) return;
        for (var i = 0; i < _vs.length; i++) {
            if (value == _vs[i][_vf]) return _vs[i][_nf];
        }
    }
};


/**
 * 消息格式化的命名空间
 */
jQuery.message = {
    /**
     * 格式化一个消息文本
     * 可以传递多个附加参数给该函数
     * format 函数将根据参数的顺序对 msg 中的占位符进行格式化
     * msg 中的占位符格式 ： {0}, {1} ....
     * 除第一个参数外，还可以传递多个参数，按参数的顺序进行替换
     * 如果参数列表的第二个参数为数组，则忽略其他参数，
     * 用数组中的元素按顺序替换
     * @param msg
     */
    format: function (msg) {
        if (msg == undefined) return;
        if (typeof msg != 'string') return;

        /*获得除msg 之外的其他参数*/
        var args = Array.prototype.slice.call(arguments, 1);
        if (args == undefined || args == null || args.length == 0) return msg;
        if ($.isArray(args[0])) {
            var arr = args.pop();
            args = [];
            args = args.concat(arr);
        }

        var formatMsg = msg;
        /*定义正则表达式*/
        var reg = /(\{\s*\d+\s*})/g;
        var result = msg.match(reg);
        /*如果能匹配字符串*/
        if (result != null && $.isArray(result) && result.length > 0) {
            var nums = [];
            for (var i = 0; i < result.length; i++) {
                var value = result[i];
                value = value.replace(/\s+/g, '');
                value = value.replace(/\{|\}/g, '');
                value = parseInt(value);
                nums.push(value);
            }
            /*排序*/
            $.sort.selectSort(nums);
            for (var i = 0; i < nums.length; i++) {
                if (args[nums[i]] == undefined || args[nums[i]] == null) continue;
                reg = new RegExp('\\{\\s*' + nums[i] + '\\s*\\}');
                formatMsg = formatMsg.replace(reg, args[nums[i]]);
            }
        }
        return formatMsg;
    },
    /**
     * 处理文本中的占位符
     * 返回处理后的字符串，原串不进行处理
     * 占位符的表示方法为：{place} = record.place, {holder} = record.holder
     * 占位符中只允许出现大小写英文字符，数字，下划线和 $
     * 占位符中，大括号与属性名之间的空格将被忽略
     * 同样的占位符出现多次，都将被替换
     * 使用record对象中的同名属性替换字符串中的占位符，并返回
     * record的属性只支持 string， number 和 array
     * 如果是array类型，则将array中的所有元素用 , 分隔连接成一个字符串，替换掉相应的占位符。
     * @param str             要处理的字符串
     * @param record      进行替换的对象
     * @returns                  替换后的字符串
     */
    resolvePlaceholder: function (str, record) {
        /*要求第一个参数必须为 string, 第二个参数必须提供*/
        if (typeof str != 'string') return;
        if (record == undefined || record == null) return str;
        /*新字符串用于替换*/
        var newStr = str;
        /*如果包含占位符，进行处理*/
        if (hasPlaceholder(str)) {
            var reg = /(\{\s*[\w\$]+\s*\})/g;
            var result = str.match(reg);
            if (result != undefined && result != null) {
                for (var i = 0; i < result.length; i++) {
                    var pattern = result[i];
                    var property = pattern.replace(/\{|\}/g, '').trim();
                    if (record[property] == undefined || record[property] == null) continue;
                    var value = resolveProperty(record[property]);
                    if (value == null) continue;
                    newStr = newStr.replace(pattern, value);
                }
            }
        }
        return newStr;

        /*内部函数，判断字符串中是否包含占位符*/
        function hasPlaceholder(str) {
            var reg = /\{\s*[\w\$]+\s*\}/g;
            return reg.test(str);
        };

        /*将参数转换为字符串*/
        function resolveProperty(value) {
            if (typeof value == 'string') return value;
            if (typeof value == 'number') return String(value);
            if ($.isArray(value)) {
                return value.toString();
            }
            return null;
        };
    }
};