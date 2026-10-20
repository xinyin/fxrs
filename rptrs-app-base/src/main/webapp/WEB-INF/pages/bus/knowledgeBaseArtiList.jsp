<%--
  Created by IntelliJ IDEA.
  User: xin
  Date: 2021/7/6
  Time: 9:41
  专业文章
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>knowledgeBaseArtiList</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="文件名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_fileName" col="7"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="上传部门" col="5" formControl="true"/>
                        <%--                        <bf:select name="__EQS_uploadDeptName" col="7" dataSource="custom"--%>
                        <%--                                   url="sys/dictItem/getDictItems/BUS_DispatchDep">--%>
                        <%--                            <bf:option text="请选择上传部门" value=""></bf:option>--%>
                        <%--                            <be:event type="select">--%>
                        <%--                                var input = $("#__LIKES_uploadDeptName");--%>
                        <%--                                input.focus();--%>
                        <%--                                input.blur();--%>
                        <%--                            </be:event>--%>
                        <%--                        </bf:select>--%>
                        <bf:text name="__LIKES_department" col="7"/>
                    </bf:formGroup>

                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="knowledgeBaseTable" idField="pid" url="${base}/bus/knowledgeBaseArti">
                <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
                <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="downloadFile" text="下载文件" type="button" handler="downloadFile"/>
                <bc:column field="fileName" title="文件名称" width="100"/>
                <bc:column field="fileType" title="文件类型" width="100"/>
                <bc:column field="fileSize" title="文件大小" width="100"/>
                <bc:column field="department" title="上传部门" width="100"/>
                <bc:column field="uploadTimeString" title="上传时间" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<div>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"
            id="hiddenBtn"
            style="display: none">
        文件上传按钮
    </button>

    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalCenterTitle">文件上传</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div style="text-align: center" id="loading"></div>
                    <form method="post" enctype="multipart/form-data" id="uploadForm"
                          style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;" action="${base}/bus/knowledgeBaseArti/upload/file">
                        <div class="form-group" style="text-align: center;vertical-align: middle;">
                            <label for="file"></label>
                            <img src="${base}/static/img/sendfile.png"/>
                            <a href="javascript:;" class="file">
                                <input type="file" class="form-control" id="file" name="file" multiple
                                       style="padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
                            </a>
                        </div>

                    </form>
                    <div class="form-group"
                         style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
                        <button type="submit" class="btn btn-primary" id="upload">上传</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script>
    //文件上传
    $("#upload").click(function () {
        var obj = document.getElementById("file");
        for (var i = 0; i < obj.files.length; i++) {
            var temp = obj.files[i].name;
            var temArr = temp.split('.');
            var fileLast = temArr[temArr.length - 1];
        }
        var formData = new FormData($('#uploadForm')[0]);
        $.ajax({
            type: 'post',
            url: '${base}/bus/knowledgeBaseArti/upload/file', //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            beforeSend: function (XMLHttpRequest) {
                //alert('远程调用开始...');
                $("#loading").html("<p style=\"color: dodgerblue\">正在上传，请稍后操作...</p>");
            },
            success: function (data) {
                $("#close").click();
                alert(data.msg);
                if (data.success) {
                    window.location = "${base}/bus/knowledgeBaseArti/list"
                }
            },
            error: function () {
                $("#close").click();
                alert("上传失败,请重试");
                $("#loading").empty();
            }
        })
    });

    //表格中选中数据的id值
    function getIdSelections() {
        return $.map($('#knowledgeBaseTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    //删除数据
    function removeHandler() {
        var pid = getIdSelections();
        if (pid == '' || pid == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }

        $.messager.confirm('提示', '确定要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    url: "${base}/bus/knowledgeBaseArti/remove/" + pid,
                    method: "GET",
                    data: {"": ""},
                    dataType: "JSON",
                    traditional: true,
                    success: function (data) {
                        if (data.success) {
                            $.messager.alert('提示', data.msg, 'info', function () {
                                window.location = "${base}/bus/knowledgeBaseArti/list"
                            })
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    }
                })
            }
        })
    }

    //文件下载
    function downloadFile() {
        var tables = $('#knowledgeBaseTable').jTable('getCheckData')
        var data = []
        tables.forEach(v => {
            data.push(v.pid)
        })
        console.log(data)
        var params = {
            pid: data
        };
        console.log(params);
        var form = document.createElement('form')
        form.id = 'form'
        document.body.appendChild(form)
        for (var obj in params) {
            if (params.hasOwnProperty(obj)) {
                var input = document.createElement('input')
                input.tpye = 'hidden'
                input.name = obj;
                input.value = params[obj]
                form.appendChild(input)
            }
        }
        form.method = "GET" //请求方式
        form.action = '${base}/bus/knowledgeBaseArti/download'
        form.submit()
        document.body.removeChild(form)
    }

    function getIdSelections() {
        return $.map($('#knowledgeBaseTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function exportIn() {
        $("#hiddenBtn").click();
    }
</script>
</body>
</html>
