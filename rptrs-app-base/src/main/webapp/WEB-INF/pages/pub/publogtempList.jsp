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
                        <bc:label text="模板名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_tempFileName" col="7" placeholder="文件名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="模板类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_tempFileTepe" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/PUB-TEMPLATETYPE">
                            <bf:option text="ALL" value="%"></bf:option>
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
            <bc:table id="docTable" idField="pid" url="${base}/pub/pubfile">
                <bc:toolbar id="" text="新增" type="button" handler="exportIn"/>
<%--                <bc:toolbar id="edit" text="编辑" type="button" handler="editOnLine"/>--%>
                <bc:toolbar id="remove" text="删除" type="button" handler="remove"/>
                <bc:toolbar id="subDownFile" type="button" text="下载文件" handler="download"/>
<%--                <bc:column field="pid" title="pid" width="100" visible="false"/>--%>
                <bc:column field="tempFileName" title="模板名称" width="100"/>
                <bc:column field="tempFileTepe" title="模板类型" width="100" formatter="custom:sys/dictItem/getDictItems/PUB-TEMPLATETYPE"/>
                <bc:column field="tempUploadDepname" title="更新部门" width="100"/>
                <bc:column field="createByName" title="操作员" width="100"/>
                <bc:column field="createTimeStr" title="上传时间" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>

<div>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"
            id="hiddenBtn"
            style="display: none">
        选择模板
    </button>
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">

        <div class="modal-dialog modal-dialog-centered" role="document">

            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalCenterTitle">选择上传文件</h5>

                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-header">
                    <label>模板类型</label>&nbsp;&nbsp;&nbsp;&nbsp;
                    <select style="height: 20px" name="select" id="stype">
                        <option value="0" selected="selected">请选择模板类型</option>
                    </select>
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
                        <button type="button" class="btn btn-primary" id="upload">上传</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>

</body>

<script type="text/javascript">
    var flag = 0;
    $(function () {
        $.get("${base}/sys/dictItem/getDictItems/PUB-TEMPLATETYPE", {}, function (data) {
            console.log(data)
            for (var i = 0; i <= data.length; i++) {
                console.log(data[i].value + "---------" + data[i].name)
                $("#stype").append('<option value=' + data[i].value + '>' + data[i].name + '</option>');
            }
        })
    })

    //获取审批记录
    function send() {
        // 获取审批记录
        $.ajax({
            url: "${base}/pub/publog/getApprovalLog",
            method: "GET",
            dataType: "JSON",
            traditional: true,
            success: function (data) {
                if (data.success) {
                    $.messager.alert('提示', data.msg, 'info');
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        })
    }

    function frmReset() {
        window.location = '${base}/pub/pubfile/listtemp';
    }

    //文件下载
    function download() {
        var tables = $('#docTable').jTable('getCheckData')
        var data = []
        tables.forEach(v => {
            data.push(v.pid)
        });
        var params = {
            pid: data
        };
        var form = document.createElement('form')
        form.id = 'form'
        form.name = 'form'
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
        form.action = '${base}/pub/pubfile/download'
        form.submit()
        document.body.removeChild(form)
    }

    //删除
    function remove() {
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        } else {
            $.messager.confirm('提示', '确定要删除吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "${base}/pub/pubfile/remove/" + pid,
                        method: "GET",
                        data: {},
                        dataType: "JSON",
                        traditional: true,
                        success: function (data) {
                            if (data.success) {
                                $.messager.alert('提示', data.msg, 'info', function () {
                                    window.location = '${base}/pub/pubfile/listtemp';
                                })
                            } else {
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        }
                    })
                }
            })
        }
    }

    function exportIn() {
        $("#hiddenBtn").click();
    }

    $("#upload").click(function () {
        //如果没有选择就报错
        var sval = $("#stype option:selected").val();
        if (sval === '0') {
            $.messager.alert('错误', "请选择模板类型！", 'error');
            return;
        }
        var obj = document.getElementById("file");
        console.log(obj.files.length)
        if (obj.files.length == 0) {
            $.messager.alert('错误', "请选择一个文件！", 'error');
            return;
        }
        for (var i = 0; i < obj.files.length; i++) {
            var temp = obj.files[i].name;
            var temArr = temp.split('.');
            var fileLast = temArr[temArr.length - 1];
            if (fileLast != 'xls' && fileLast != 'xlsx' && fileLast != 'doc' && fileLast != 'docx') {
                $.messager.alert('错误', "请上传doc、docx或者xls、xlsx格式的文件！", 'error');
                return;
            }
        }
        var formData = new FormData($('#uploadForm')[0]);
        var sval = $("#stype option:selected").val();
        var pid = getIdSelections();
        if (pid.length == 0) {
            // 无意义,防止程序报错
            pid = 0;
        }

        $.ajax({
            type: 'post',
            url: '${base}/pub/pubfile/uploadTemplateFile/' + flag + "/" + sval + "/" + pid, //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            beforeSend: function (XMLHttpRequest) {
                $("#loading").html("<p style=\"color: dodgerblue\">正在上传，请稍后操作...</p>");
            },
            success: function (data) {
                flag = 0;
                $("#close").click();
                if (data.success) {
                    // //处理数据
                    // var files = data.fileData;
                    // var count = files.length;
                    // //获取原有表单的数据
                    // var rows = getData();
                    // rows = rows.concat(files);
                    // //构造表格显示数据
                    // var total = rows.length;
                    // var showData = {'total': total, 'rows': rows}
                    // $('#docTable').bootstrapTable('load', showData);
                    // $("#loading").empty();
                    window.location.href = "${base}/pub/pubfile/listtemp";
                }
            },
            error: function () {
                $("#close").click();
                alert("上传失败,请重试");
                $("#loading").empty();
            }
        })
    });

    function getIdSelections() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function getAllIdSelections() {
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

    function editRow() {
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        flag++;
        exportIn();
    }

    //在线编辑页面
    function editOnLine() {
        //选中的文件
        var pid = getIdSelections();
        if (pid == '' || pid == undefined) {
            $.messager.alert('错误', "请选择需要编辑的文件!", 'error');
            return;
        }
        if (pid.length > 1) {
            $.messager.alert('错误', "只能选中一个文件!", 'error');
            return;
        }
        var obj = {
            id:pid[0],
            mode:"edit",
            type:"template"
        };
        $.ajax({
            url: '${base}/pub/publog/editOnLine',
            data: obj,
            type: 'post',
            jsonType: 'json',
            success: function (data) {
                if (data.success) {
                    var html = data.msg;
                    openwindow = window.open();
                    openwindow.document.write(html)
                    openwindow.document.close()
                } else {
                    var msg = data.msg;
                    $.messager.alert('错误', msg, 'error');
                }
            }
        });
    }

    //在线编辑页面
    function viewOnLine() {
        //选中的文件
        var pid = getIdSelections();
        if (pid == '' || pid == undefined) {
            $.messager.alert('错误', "请选择需要编辑的文件!", 'error');
            return;
        }
        if (pid.length > 1) {
            $.messager.alert('错误', "只能选中一个文件!", 'error');
            return;
        }
        var obj = {
            id:pid[0],
            mode:"view",
            type:"template"
        };
        $.ajax({
            url: '${base}/pub/publog/editOnLine',
            data: obj,
            type: 'post',
            jsonType: 'json',
            success: function (data) {
                if (data.success) {
                    var html = data.msg;
                    openwindow = window.open();
                    openwindow.document.write(html)
                    openwindow.document.close()
                } else {
                    var msg = data.msg;
                    $.messager.alert('错误', msg, 'error');
                }
            }
        });
    }

</script>
</html>