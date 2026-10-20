<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>导入</title>
    <meta name="description"
          content="File Upload widget with multiple file selection, drag&amp;drop support and progress bar for jQuery. Supports cross-domain, chunked and resumable file uploads. Works with any server-side platform (PHP, Python, Ruby on Rails, Java, Node.js, Go etc.) that supports standard HTML form file uploads.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body class="gray-bg" style="overflow: hidden;">
<div class="row" style="overflow: hidden;">
    <bc:ibox title="" subTitle="" col="12" collapsible="true">
        <div style="height: 340px">
            <bf:formGroup>
                <bc:col col="12">
                    <bc:col col="2">
						<span class="btn btn-success fileinput-button">
					        <i class="glyphicon glyphicon-plus"></i>
					        <span></span>
					        <input id="fileupload" type="file" name="files[]">
					    </span>
                    </bc:col>
                    <bc:button text="下载模板" icon="save" click="btnDownload();"></bc:button>
                    <bc:button text="导入" icon="save" click="btnSave();" id="btnsave"></bc:button>
                    <bc:button text="返回">
                        <be:event>
                            $.messager.args('wImport').callback();
                            $.messager.hide('wImport');
                        </be:event>
                    </bc:button>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="12">
                    <div id="progress">
                        <div class="bar" style="width: 0%;"></div>
                    </div>
                    <span id="fileDesc" style="height:100%;margin:0px;padding:0px;vertical-align:middle"></span>
                    <span id="uploadQueue" style="height:100%;padding:0px;margin:0px;vertical-align:top"></span>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <table id="optionTable">
                </table>
            </bf:formGroup>
        </div>
    </bc:ibox>
</div>
<iframe id="ifrDownload" src="about:blank" frameborder="0" scrolling="auto"
        style="display:none;z-index: 1;width:0px;height:0px; overflow: hidden;"></iframe>
<br:js pack="form"/>
<link rel="stylesheet" href="${base}/static/js/plugins/jqFileUpload/css/jquery.fileupload.css">
<script src="${base}/static/js/plugins/jqFileUpload/js/vendor/jquery.ui.widget.js"></script>
<script src="${base}/static/js/plugins/jqFileUpload/js/jquery.iframe-transport.js"></script>
<script src="${base}/static/js/plugins/jqFileUpload/js/jquery.fileupload.js"></script>
<be:processEvent/>
<script type="text/javascript">
    /*导入模板代码，标题*/
    var templateCode, title, uuid;
    /*要处理的 controller url*/
    var url = null;
    /*模板定义的列*/
    var columns = null;
    /*当前文件中的总行数*/
    var rowCount = 0;
    /*需要传递到controller中的参数*/
    var params = null;
    $(document).ready(function () {
        var args = $.messager.args('wImport');
        /*获得代码*/
        templateCode = args.templateCode;
        /*获得 url*/
        url = args.url;
        /*将页面参数保存起来*/
        if (args.params != undefined && args.params != null) {
            params = args.params;
        }
        //禁用导入按钮
        $('#btnsave').attr('disabled', true);
        //初始化上传控件
        init();
    });

    //下载模板
    function btnDownload() {
        var url = '${base}/sys/import/download?code=' + templateCode + '&version=2003';
        $('#ifrDownload').attr('src', url);
    }

    //初始化上传控件
    function init() {
        /*获取参数*/
        $.ajax({
            async: false,
            type: 'POST',
            url: '${base}/sys/import/getParam',
            data: {code: templateCode},
            success: function (data) {
                if (data.success) initFileUpload(data);
                else {
                    $.messager.toastr('提示', data.msg, 'error');
                    $.messager.hide('wImport');
                }
            },
            error: function () {
                $.messager.toastr('提示', '获取参数出错。', 'error');
                $.messager.hide('wImport');
            }
        });
    }

    //初始化上传控件
    function initFileUpload(result) {
        /*初始化上传控件*/
        $('#fileupload').fileupload({
            url: '${base}/sys/import/upload',
            // dataType: 'JSON',
            autoUpload: true,
            add: function (e, data) {
                //判断文件类型
                var fileName = data.originalFiles[0]['name'];
                var type = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (type.toLocaleLowerCase() != 'xls' && type.toLocaleLowerCase() != 'xlsx') {
                    $.messager.toastr('提示', '上传文件类型不对。', 'error');
                    return;
                }
                //判断文件大小
                if (data.originalFiles[0]['size'].length && data.originalFiles[0]['size'] > result.uploadSizeLimit) {
                    $.messager.toastr('提示', '上传文件大小不能超过 ' + result.uploadSizeLimit + ' 。', 'error');
                    return;
                }
                data.submit();
            },
            fail: function (e, data) {
                $.messager.toastr('提示', '上传文件失败。', 'error');
            },
            done: function (e, data) {
                var result = data.result;
                /*上传失败*/
                if (result == '0') {
                    $.messager.alert('系统提示', '上传失败。', 'info');
                    var ksize = Math.round(data.total / 1024 * 100) / 100;
                    title = data.originalFiles[0]['name'];
                    var html = '<span style="height:100%;vertical-align:middle;font-weight:bold">上传失败：' + title + '(' + ksize + 'K)</span>';
                    $('#fileDesc').html(html);
                } else {
                    title = data.originalFiles[0]['name'];
                    var ksize = Math.round(data.total / 1024 * 100) / 100;
                    var html = '<span style="height:100%;vertical-align:middle;font-weight:bold">已上传：' + title + '(' + ksize + 'K)</span>';
                    $('#fileDesc').html(html);
                    uuid = result;
                    analysis(result);
                }

            }
        });
    }

    //解析数据
    function analysis() {
        /*获取参数*/
        $.ajax({
            type: 'POST',
            url: '${base}/sys/import/getFileData',
            data: {code: templateCode, uuid: uuid},
            beforeSend: function () {
                $.mask.show();
            },
            success: function (data) {
                $.mask.remove();
                if (data.success) {
                    /*解析返回的数据*/
                    if (data.rowCount == 0 || data.columnCount == 0) {
                        $.messager.toastr('提示', '无数据。', 'error');
                        return;
                    }

                    //打开导入数据按钮
                    $('#btnsave').attr('disabled', false);
                    columnCount = data.columnCount;
                    rowCount = data.totalRowCount;
                    columnNames = data.columnNames;
                    /*生成数据表格的列定义*/
                    var columns = [];
                    columns[0] = [];
                    for (var i = 0; i < data.columnCount; i++) {
                        var columnName = data.columnNames[i].value;
                        var title = data.columnNames[i].name;
                        var column = {field: columnName, title: title, width: 60};
                        columns[0].push(column);
                    }

                    /*生成数据表格*/
                    $("#optionTable").bootstrapTable({
                        idField: "",
                        pagination: false,
                        height: 200,
                        search: false,
                        showRefresh: false,
                        clickToSelect: true,
                        columns: columns
                    });
                    $("#optionTable").bootstrapTable('load', data.content);
                } else {
                    $.messager.toastr('提示', data.msg, 'error');
                }
            },
            error: function () {
                $.mask.remove();
                $.messager.toastr('提示', '未知错误。', 'error');
            }
        });
    }

    //导入数据
    function btnSave() {
        /*获取文件路径*/
        $.ajax({
            async: false,
            type: 'POST',
            url: '${base}/sys/import/getFilePath',
            data: {uuid: uuid},
            success: function (data) {
                if (data.success) {
                    importData(data.path);
                } else {
                    $.messager.toastr('提示', '获取文件路径失败。', 'error');
                    return;
                }
            },
            error: function () {
                $.messager.toastr('提示', '获取文件路径失败。', 'error');
                return;
            }
        });

    }

    //导入数据
    function importData(path) {
        var data = {};
        data.code = templateCode;
        data.path = path;
        data.ignoreLines = 1;

        //参数不等于空， 进行json
        if (params != null) {
            data.params = JSON.stringify(params);
        }

        /*处理导入*/
        $.ajax({
            type: 'POST',
            url: url + '/importData',
            data: data,
            beforeSend: function () {
                $.mask.show();
            },
            success: function (data) {
                $.mask.remove();
                if (data.success) {
                    var html = '<span style="height:100%;vertical-align:middle;font-weight:bold">成功导入 (“' + title + '”' + data.count + '条。)</span>';
                    $('#fileDesc').html(html);
                    //禁用导入按钮
                    $('#btnsave').attr('disabled', true);
                } else {
                    $.messager.toastr('提示', data.msg, 'info');
                }
            },
            error: function (jqXHR, status) {
                $.mask.remove();
                $.messager.toastr('提示', '导入失败。', 'info');
            }
        });

    }
</script>
</body>
</html>
