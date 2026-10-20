<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>Publog</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="文件名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_pubFileName" col="7" placeholder="文件名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="下载状态" col="5" formControl="true"/>
                        <bf:select name="__EQS_downType" col="7" dataSource=""
                                   url="">
                            <bf:option text="ALL" value=""></bf:option>
                            <bf:option text="已下载" value=""></bf:option>
                            <bf:option text="未下载" value=""></bf:option>
                            <be:event type="select">
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="披露状态" col="5" formControl="true"/>
                        <bf:select name="__EQS_tempFileTepe" col="7" dataSource=""
                                   url="">
                            <bf:option text="ALL" value=""></bf:option>
                            <bf:option text="以披露" value=""></bf:option>
                            <bf:option text="未披露" value=""></bf:option>
                            <be:event type="select">
                            </be:event>
                        </bf:select>
                    </bf:formGroup>

                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="docTable" idField="pid" url="${base}/pub/publogYear" >
                <bc:toolbar id="add" text="新增" type="button" />
                <bc:toolbar id="remove" text="删除" type="button" handler="remove"/>
                <bc:toolbar id="DownLoadFile" type="button" text="下载" handler="download"/>
                <bc:toolbar id="subDownFile" type="button" text="确认披露" handler="publogHandler"/>
                <bc:column field="pid" title="pid" width="100" visible="false"/>
                <bc:column field="pubFileName" title="文件名称" width="100"/>
                <bc:column field="pubSubject" title="更新部门" width="100"/>
                <bc:column field="createByName" title="操作员" width="100"/>
                <bc:column field="createTimeString" title="文件生成时间" width="100"/>
                <bc:column field="status" title="披露状态" width="100" formatter="custom:sys/dictItem/getDictItems/PUBLOG-status"/>
                <bc:column field="archDateString" title="披露日期" width="100"/>
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
                    <div class="boxse">
                        <bf:form id="editForm" method="post" validation="true">
                            <bf:formGroup type="success">
                                <bc:col col="12">
                                    <bc:label cls="required" text="报送日期" col="3" formControl="true"/>
                                    <bf:datebox name="archDate" id="archDate" col="6" format="date"
                                                value="${publog.archDate}">
                                        <bv:notEmpty/>
                                    </bf:datebox>
                                </bc:col>
                            </bf:formGroup>
                        </bf:form>
                    </div>
                </div>
                <div class="modal-body">
                    <div style="text-align: center" id="loading"></div>
                    <form method="post" enctype="multipart/form-data" id="uploadForm"
                          style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
                        <div class="form-group" style="text-align: center;vertical-align: middle;">
                            <label for="file"></label>
                            <img src="${base}/static/img/sendfile.png"/>
                            <a href="javascript:;" class="file">
                                <input type="file" class="form-control" id="file" name="file" multiple="multiple"
                                       style="padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
                            </a>
                        </div>

                    </form>
                    <div class="form-group"
                         style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
                        <button type="button" class=" btn btn-danger" id="upload"><i class="fa fa-save"></i> 确定</button>
                        <button type="button" class=" btn btn-primary" data-dismiss="modal" aria-label="Close"><i
                                class="fa fa-arrow-circle-left"></i> 返回
                        </button>
                        <%--                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>--%>
                        <%--                        <button type="submit" class="btn btn-primary" id="upload">上传</button>--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {
        $("#archDate").datetimepicker("setStartDate", new Date());
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
    });
    /*获取pid*/
    function getIdSelections() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    function publogHandler() {
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        //已下载&未报送
        <%--window.location = '${base}/sub/sublog/submission/' + pid;--%>
        $("#hiddenBtn").click();
    }
    function remove(){
        var status=getStatusSelections();
        if(status=='1'){
            $.messager.alert('错误','该数据已披露无法删除','error');
            return;
        }
        var pid=getIdSelections();
        $.messager.confirm('提示', '确定要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    url: "${base}/pub/publogQuarter/remove/" + pid,
                    method: "POST",
                    dataType: "JSON",
                    success: function (data) {
                        if (data.success) {
                            $.messager.alert('成功', data.msg, 'info');
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    }
                })
            }
        })
    }
    //确认披露
    $("#upload").click(function () {
        var obj = document.getElementById("file");
        for (var i = 0; i < obj.files.length; i++) {
            var temp = obj.files[i].name;
            var temArr = temp.split('.');
            var fileLast = temArr[temArr.length - 1];
            if (fileLast != 'pdf' && fileLast != 'doc' && fileLast != 'docx' && fileLast != 'xlsx'&& fileLast != 'xls') {
                $.messager.alert('错误',"上传文件格式为pdf、doc、docx、xls、xlsx","error");
                return;
            }
        }
        var formData = new FormData($('#uploadForm')[0]);
        var pid = getIdSelections();
        var fileDate=$('#editForm').serializeAll()[0].value;
        $.ajax({
            type: 'post',
            url: '${base}/pub/publogYear/uploadFile?pid=' + pid+'&fileDate='+fileDate, //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            beforeSend: function (XMLHttpRequest) {
                //alert('远程调用开始...');
                $("#loading").html("<p style=\"color: dodgerblue\">正在上传，请稍后操作...</p>");
            },
            success: function (data) {
                if (data.success) {
                    $.messager.alert('提示', '上传成功。', 'info', function () {
                        window.location = '${base}/pub/publogQuarter/list';
                    });
                }
            },
            error: function () {
                $("#close").click();
                alert("上传失败,请重试");
                $("#loading").empty();
            }
        })
    });

    //文件下载
    function download() {
        debugger;
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        var url = '${base}/pub/publogRegular/downloadFile/' + pid;
        if (true) {
            // if (status == "03") {
            var elemIF = document.createElement("iframe");
            elemIF.src = url;
            elemIF.style.display = "none";
            document.body.appendChild(elemIF);
        } else {
            $.messager.alert('警告', "当前状态不支持下载，请查看！", 'warning');
            //  alert("当前状态不支持下载，请查看！");
            //当状态未通过时按钮置灰 不可点击
            document.getElementById("DownLoadFile").setAttribute("disabled", true);
        }
    }

</script>

</body>
</html>