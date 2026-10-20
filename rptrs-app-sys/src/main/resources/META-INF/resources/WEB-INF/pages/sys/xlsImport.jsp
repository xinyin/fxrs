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
<body class="gray-bg">
<div class="row" style="overflow: hidden;">
    <div style="padding: 15px;background-color: #ffffff;padding-bottom: 10px">
        <bf:form id="formExcelImport" action="" method="post" enctype="multipart/form-data">
            <bf:formGroup>
                <bc:col col="12">
                    <bc:col col="2">
						<span class="btn btn-success fileinput-button">
					        <i class="glyphicon glyphicon-plus"></i>
					        <span></span>
					        <input id="fileupload" type="file" name="files[]">
					    </span>
                    </bc:col>
                    <bc:button text="返回">
                        <be:event>
                            $.messager.args('wImportFromExcel').callback();
                            $.messager.hide('wImportFromExcel');
                        </be:event>
                    </bc:button>
                </bc:col>
            </bf:formGroup>
        </bf:form>
    </div>
    <bc:ibox title="导入结果" subTitle="" col="12" collapsible="false">
        <bf:formGroup>
            <ul id="msgtips" style="height:150px;width:400px;overflow:auto;"></ul>
        </bf:formGroup>
    </bc:ibox>
</div>
</div>
<br:js pack="form"/>
<link rel="stylesheet" href="${base}/static/js/plugins/jqFileUpload/css/jquery.fileupload.css">
<script src="${base}/static/js/plugins/jqFileUpload/js/vendor/jquery.ui.widget.js"></script>
<script src="${base}/static/js/plugins/jqFileUpload/js/jquery.iframe-transport.js"></script>
<script src="${base}/static/js/plugins/jqFileUpload/js/jquery.fileupload.js"></script>
<be:processEvent/>
<script type="text/javascript">
    //上传文件大小
    var maxSize = '${maxSize}';
    $(document).ready(function () {

        //设置上传路径
        var url = '${base}/sys/xlsie/upload?a=&bean=${bean}&rand=' + (Math.random() * 10000);
        initFileUpload(url);
    });

    //初始化上传控件
    function initFileUpload(url) {
        /*初始化上传控件*/
        $('#fileupload').fileupload({
            url: url,
            autoUpload: true,
            add: function (e, data) {
                $.mask.show();
                //判断文件类型
                /*var fileName = data.originalFiles[0]['name'];
                var type = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (type.toLocaleLowerCase() != 'xls' && type.toLocaleLowerCase() != 'xlsx') {
                    //$.messager.toastr('提示', '上传文件类型不对。', 'error');
                    return;
                }*/
                //判断文件大小
                if (data.originalFiles[0]['size'].length && data.originalFiles[0]['size'] > maxSize) {
                    $.messager.toastr('提示', '上传文件大小不能超过 ' + maxSize + ' K。', 'error');
                    return;
                }
                data.submit();
            },
            fail: function (e, data) {
                $.mask.remove();
                $.messager.toastr('提示', '上传文件失败。', 'error');
            },
            done: function (e, data) {
                $.mask.remove();
                var result = $.parseJSON(data.result);
                var els = [];
                $.each(result.msg, function (idx, itr) {
                    els.push('<li>' + itr.message + ' ' + (itr.exception == undefined ? '' : itr.exception) + '</li>');
                });
                $('#msgtips').html(els.join(''));
            }
        });
    }
</script>
</body>
</html>
