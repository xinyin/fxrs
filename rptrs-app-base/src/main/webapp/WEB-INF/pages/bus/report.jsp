<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>Doc</title>
</head>
<style type="text/css">
    #docTable {
        text-align: center;
    }

    .form-group .input-group.date,
    .form-group .select2 {
        width: 100% !important;
    }

    .boxse .form-group .col-xs-4 {
        margin: 0.3% 0 !important;
    }

    .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
        border-color: #CCCCCC !important;
    }
</style>

<script>

    //重置
    function frmReset() {
        window.location = '${base}/bus/document/list';
    }
</script>


<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bf:hidden name="systemType" id="systemType" value="I"></bf:hidden>
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="文件名称" col="4" formControl="true"/>
                        <bf:text name="__LIKES_fileName" col="7" placeholder="文件名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="上传时间" col="4" formControl="true"/>
                        <bf:datebox id="__GES_createTime" name="__LIKES_createTime" col="7"/>
                    </bf:formGroup>

                    <%-- <div style="margin: 5% 0 0 10%">
                         <bc:col col="3">
                             <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                             <bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
                         </bc:col>
                     </div>
 --%>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="docTable" idField="pid" url="${base}/bus/docFile" queryUrl="/query?flag=4"
                      selectFirst="false">
                <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
                <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>
                <bc:toolbar id="downFileOnclick" text="下载文件" type="button" handler="downFileOnclick"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="fileName" title="文件名" width="100"/>
                <bc:column field="fileType" title="文件类型" width="100"/>
                <bc:column field="fileSize" title="大小" width="100"/>
                <%--                <bc:column field="uploadOrgName" title="上传机构" width="100"/>--%>
                <bc:column field="uploadDeptName" title="上传部门" width="100"/>
                <bc:column field="createTimeString" title="上传时间" width="100"/>
              <%--  <bc:operator id="viewFile" icon="eye" title="操作/查看" handler="viewFile"/>--%>
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
                          style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
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

    //删除
    function remove() {
        var pids = getIdSelections();
        if (pids == "" || pids == undefined) {
            alert("请选中数据")
        } else {
            var r = confirm("确定要删除数据？");
            if (r) {
                $.ajax({
                    url: '${base}/bus/document/remove/' + pids,
                    type: 'get',
                    dataType: "json",
                    success: function (res) {
                        alert(res.msg);
                        if (res.success) {
                            window.location.reload();
                        }
                    }
                })
            }
        }
    }


    //文件下载
    function downFileOnclick() {
        //选中的文件
        var pids = getIdSelections();
        if (pids == '' || pids == undefined) {
            $.messager.alert('错误', "请选择需要下载的文件!", 'error');
            return;
        }
        var url = '${base}/bus/docFile/download/' + pids;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    function getIdSelections() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function exportIn() {
        $("#hiddenBtn").click();
    }

    //查看外规
    viewOutsideDoc = function () {
        var pid = getIdSelections1();
        if (pid == '' || pid == undefined) {
            alert("选择一条数据");
            return;
        }
        if (pid.indexOf(',') != -1) {
            alert("只能选择一条数据")
            return;
        }
        window.location = '${base}/bus/docForOutside/view/' + pid;
    }

    //移除文件
    removeDoc = function () {
        var rows = getData();//删除前数据
        var pids = getIdSelections();//删除数据主键
        var selectRows = getselectData();//选中的数据
        if (selectRows.length == 0) {
            alert("请先选中数据");
            return;
        }
        if (confirm("确认删除？")) {
            $.ajax({
                type: 'get',
                url: '${base}/bus/docFile/removeDoc/' + pids,
                dataType: 'json',
                success: function (res) {
                    alert(res.msg);
                    if (res.success) {
                        //从数据中移除
                        for (var i = 0; i < selectRows.length; i++) {
                            var row = selectRows[i];
                            rows.splice(rows.indexOf(row), 1);
                        }
                        var showData = {'total': rows.length, 'rows': rows};
                        $('#docTable').bootstrapTable('load', showData);
                    }
                }

            })
        }
    }


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
            url: '${base}/bus/docFile/uploadDocFileAndEmails?flag=4', //上传文件的请求路径必须是绝对路劲
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
                    //处理数据
                    var files = data.fileData;
                    var count = files.length;
                    //获取原有表单的数据
                    var rows = getData();
                    rows = rows.concat(files);
                    //构造表格显示数据
                    var total = rows.length;
                    var showData = {'total': total, 'rows': rows}
                    $('#docTable').bootstrapTable('load', showData);
                    $("#loading").empty();
                }
            },
            error: function () {
                $("#close").click();
                alert("上传失败,请重试");
                $("#loading").empty();
            }
        })
    });

    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        var fileType = row[0].fileType;
        if (pid == '' || pid == undefined) {
            alert("请选中一个文件");
            return;
        }
        if (pid.indexOf(',') != -1) {
            alert("只能选中一个文件");
            return;
        }
        if (fileType != 'pdf') {
            alert("只能预览PDF格式文件，其他文件请下载后查看！");
            return;
        }

        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/bus/docFile/preview?pid=' + pid));
    }


    function getIdSelections() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }


    //展示数据方法
    function showTableData(data) {

        $('#docTable').bootstrapTable('load', data);
    }

    //获取表格中所有的数据主键
    function getAllDataId() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    //文件表所有选中的数据
    function getselectData() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }

    //文件表数据
    function getData() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row
        });
    }

</script>
</body>
</html>