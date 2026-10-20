<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>Doc</title>
</head>
<body class="gray-bg">

<style>
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


    .form-group {
        margin: 0.3% 0 !important;
    }

    .form-group .col-xs-12 {
        margin: 0.3% 0 !important;
    }

    .form-group .col-xs-12 label {
        display: inline-block;
        float: left;
        margin: 0 0 0 3%;
        padding: 0 2% 0 0;
    }

    .form-group .col-xs-12 label {
        display: inline-block;
        float: left;
        margin: 0 0 0 3%;
        padding: 0 2% 0 0;
        width: 10%;
    }

    .has-success .form-control, .boxse .has-success .form-control:focus {
        border-color: #CCCCCC !important;
    }

    .gray-bg {
        background: white !important;
    }
</style>
<div id="wrapper" class="animated fadeInRight">

    <div class="row">
        <div>
            <button type="button" class="btn btn-primary" onclick="changeDiv1()" id="bt1">制度信息</button>
        </div>
        <bf:form id="editForm" method="post" validation="true">
            <%--制度信息--%>
            <div id="d1">
                <bc:ibox title="" subTitle="" col="12" collapsible="true">
                    <bf:hidden name="opt" id="opt" value="${doc.pid}"></bf:hidden>
                    <bf:formGroup hr="dashed" hrp="bottom">
                        <bc:row cls="m-b">
                            <bf:formGroup col="4">
                                <bc:label text="制度名称" col="4" formControl="true"/>
                                <bf:text name="sysName" col="7" placeholder="制度名称" value="${doc.sysName}" id="sysName"/>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="文号" col="4" formControl="true"/>
                                <bf:text id="articleNumber" name="articleNumber" col="7" placeholder="文号"
                                         value="${doc.articleNumber}"/>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="颁布单位" col="4" formControl="true"/>
                                <bf:select id="issueOrg" name="issueOrg" col="7" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS_IssueOrg" value="${doc.issueOrg}">
                                    <bf:option text="请选择"></bf:option>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="时效性" col="4" formControl="true"/>
                                <bf:select id="isVal" name="isVal" col="7" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS_IsValid" value="${doc.isVal}">
                                    <bf:option text="请选择"></bf:option>
                                </bf:select>
                            </bf:formGroup>

                            <bf:formGroup col="4" type="success">
                                <bc:label text="生效时间" col="4" formControl="true"></bc:label>
                                <bf:datebox name="beginDate" id="beginDate" col="7" format="date"
                                            value="${doc.effectiveTime}">
                                </bf:datebox>
                            </bf:formGroup>
                            <bf:formGroup col="4" type="success">
                                <bc:label text="失效时间" col="4" formControl="true"></bc:label>
                                <bf:datebox id="endDate" name="endDate" col="7" format="date" value="${doc.expiryTime}">
                                </bf:datebox>
                            </bf:formGroup>
                        </bc:row>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:col col="4">
                            <bc:label text="制度文件" col="4" formControl="true"/>
                        </bc:col>
                    </bf:formGroup>
                    <div id="tableDiv" style="">
                        <bf:formGroup hrp="bottom">
                            <bc:table id="docTable" idField="pid" url="${base}/bus/docFile" selectFirst="false"
                                      multiple="true">
                                <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
                                <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>
                                <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
                                <bc:column field="pid" title="主键" width="100" visible="false"/>
                                <bc:column field="fileName" title="文件名" width="100"/>
                                <bc:column field="fileType" title="文件类型" width="100"/>
                                <bc:column field="fileSize" title="文件大小" width="100"/>
                                <bc:column field="uploadOrg" title="上传机构" width="100"/>
                                <bc:column field="uploadDep" title="上传部门" width="100"/>
                                <bc:column field="createTime" title="上传时间" width="100"/>
                            </bc:table>
                        </bf:formGroup>
                    </div>
                </bc:ibox>
            </div>
        </bf:form>
        <%--
                <bc:ibox title="行外制度" subTitle="" col="12" collapsible="true">
                    <bf:form id="editForm" validation="true">
                        <bf:hidden name="opt" id="opt" value="${opt}"></bf:hidden>
                        <bf:formGroup hr="dashed" hrp="bottom">
                            <bc:row cls="m-b">
                                <bf:formGroup col="3">
                                    <bc:label text="制度名称" col="4" formControl="true"/>
                                    <bf:text name="sysName" col="8" placeholder="制度名称" value="${doc.sysName}" id="sysName"/>
                                </bf:formGroup>
                                <bf:formGroup col="3">
                                    <bc:label text="文号" col="4" formControl="true"/>
                                    <bf:text id="articleNumber" name="articleNumber" col="8" placeholder="文号"
                                             value="${doc.articleNumber}"/>
                                </bf:formGroup>
                                <bf:formGroup col="3">
                                    <bc:label text="颁布单位" col="4" formControl="true"/>
                                    <bf:select id="dispatchDep" name="dispatchDep" col="8" dataSource="custom"
                                               url="sys/dictItem/getDictItems/BUS_DispatchDep">
                                        <bf:option text="ALL"></bf:option>
                                    </bf:select>
                                </bf:formGroup>
                                <bf:formGroup col="3">
                                    <bc:label text="时效性" col="4" formControl="true"/>
                                    <bf:select id="isVal" name="isVal" col="8" dataSource="custom"
                                               url="sys/dictItem/getDictItems/BUS_IsValid">
                                        <bf:option text="ALL"></bf:option>
                                    </bf:select>
                                </bf:formGroup>
                            </bc:row>
                        </bf:formGroup>
                        <bf:formGroup hr="dashed" hrp="bottom">
                            <bc:row cls="m-b">
                                <bf:formGroup col="4" type="success">
                                    <bc:label text="生效时间" col="3" formControl="true"></bc:label>
                                    <bf:datebox name="beginDate" id="beginDate" col="8" format="date">
                                    </bf:datebox>
                                </bf:formGroup>
                                <bf:formGroup col="4" type="success">
                                    <bc:label text="失效时间" col="3" formControl="true"></bc:label>
                                    <bf:datebox id="endDate" name="endDate" col="8" format="date">
                                    </bf:datebox>
                                </bf:formGroup>
                            </bc:row>
                        </bf:formGroup>
                        <bf:formGroup hr="dashed" hrp="bottom">
                            <bc:row cls="m-b">
                                <bc:col col="4">
                                    <bc:label text="制度文件" col="4" formControl="true"/>
                                </bc:col>
                                &lt;%&ndash;<button type="button" class="btn btn-primary" onclick="exportIn()"
                                        style="padding-bottom: 0px;padding-top: 0px">上传文件
                                </button>&ndash;%&gt;

                                <div id="tableDiv" style="width: 80%;margin-left: 12%">
                                    <bc:table id="docTable" idField="pid" url="${base}/bus/docFile" selectFirst="false"
                                              multiple="true">
                                        <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
                                        <bc:column field="pid" title="主键" width="100" visible="false"/>
                                        <bc:column field="fileName" title="文件名" width="100"/>
                                        <bc:column field="fileType" title="文件类型" width="50"/>
                                        <bc:column field="fileSize" title="文件大小" width="50"/>
                                        <bc:column field="uploadOrg" title="上传机构" width="100"/>
                                        <bc:column field="uploadDep" title="上传部门" width="100"/>
                                        <bc:column field="createTime" title="上传时间" width="100"/>
                                        <bc:operator id="view" icon="search" title="查看"/>
                                        <bc:operator id="remove" icon="trash" title="移除"/>
                                    </bc:table>
                                </div>
                            </bc:row>
                        </bf:formGroup>

                    </bf:form>
        --%>
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5">
                    <bc:button type="danger" text="保存" icon="save" click="save()">
                        <%--<be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            alert(formData);
                            $.mask.post('${base}/bus/docForOutside/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/bus/docForOutside/list';
                            });
                            }
                            else
                            {
                            $.messager.alert('错误', data.msg, 'error');
                            }
                            });
                            }
                        </be:event>--%>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/docForOutside/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
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

    function exportIn() {
        $("#hiddenBtn").click();
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
    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        alert(fileName);
        if (pid == '' || pid == undefined) {
            alert("请选中一个文件");
            return;
        }
        if (pid.indexOf(',') != -1) {
            alert("只能选中一个文件");
            return;
        }

        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/bus/docFile/preview?pid=' + pid));
    }
    //文件上传
    $("#upload").click(function () {
        var obj = document.getElementById("file");
        for (var i = 0; i < obj.files.length; i++) {
            var temp = obj.files[i].name;
            var temArr = temp.split('.');
            var fileLast = temArr[temArr.length - 1];
            if (fileLast != 'pdf') {
                alert("请上传pdf文件");
                return;
            }
        }
        var formData = new FormData($('#uploadForm')[0]);
        $.ajax({
            type: 'post',
            url: '${base}/bus/docFile/uploadDocFile', //上传文件的请求路径必须是绝对路劲
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


    function getIdSelections() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    $(function () {
        var url = window.location.href;
        if (url.indexOf('add') != -1) {
            return;
        }
        var arr = url.split('/')
        pid = arr[arr.length - 1];
        $.ajax({
            type: 'get',
            dataType: 'json',
            url: '${base}/bus/docFile/queryFileByDocId/' + pid,
            success: function (res) {
                if (res.success) {
                    var docs = res.fileData;
                    var docPids = [];
                    for (var i = 0; i < docs.length; i++) {
                        docPids[i] = docs[i].pid;
                    }
                    var filePids = res.docFilePids;
                    if (filePids != undefined && filePids.length > 0) {
                        $('#docTable').on('load-success.bs.table', function (docPids) {
                            //设置默认值方法
                            $('#docTable').bootstrapTable("checkBy", {field: 'pid', values: docPids});
                        });
                    }
                }
            }
        })

    })
    //保存
    save = function () {
        //获取输入框的值
        var pid = '';
        var url = window.location.href;
        if (url.indexOf('add') != -1) {
            //新增数据，无需查询
            pid = '';
        } else {
            //编辑操作
            var arr = url.split('/');
            pid = arr[arr.length - 1];
        }

        var form = $("#editForm").serializeJson();

        //获取表单数据
        var opt = pid;
        var sysName = $('#sysName').val();
        var articleNumber = $('#articleNumber').val();
        var isVal = $('#isVal').find("option:selected").text();
        var issueOrg = $('#issueOrg').find("option:selected").text();

        var beginDate = form['beginDate'];
        var endDate = form['endDate'];

        //判断制度名称是否为空
        if (sysName == "") {
            alert("制度名称不能为空！")
            return;
        }

        //判断文号是否为空
        if (articleNumber == "") {
            alert("文号不能为空！");
            return;
        }

        if (issueOrg == "ALL") {
            alert("颁布单位不能为空！");
            return;
        }

        if (isVal == "ALL") {
            alert("时效性不能为空！");
            return;
        }

        // 对生效日期和失效日期输入进行对比
        if (beginDate >= endDate) {
            alert("请正确填写日期");
            return;
        }


        //判断是否上传文件
        var docInnerPids = getIdSelections();
        if (docInnerPids == '' || docInnerPids == undefined) {
            alert("文件不能为空,请选中或上传文件");
            return;
        }
        var obj = {
            'opt': opt,
            'sysName': sysName,
            'articleNumber': articleNumber,
            'isVal': isVal,
            'issueOrg': issueOrg,
            'beginDate': beginDate,
            'endDate': endDate,
            'docTableSelectItem': docInnerPids
        };
        var data = JSON.stringify(obj);
        $.ajax({
            contentType: 'application/json',
            url: '${base}/bus/docForOutside/save',
            type: 'post',
            dataType: 'json',
            data: data,
            success: function (res) {

                if (res.success) {
                    alert(res.msg)
                    window.location = '${base}/bus/docForOutside/list';
                } else {
                    alert("保存失败");
                }
            }
        })
    }
    //页面初始化时展示文件列表数据和关联外规的数据
    $(function () {
        var url = window.location.href;
        if (url.indexOf('/') == -1) {
            //新增数据，无需查询
            return;
        }
        //编辑操作
        var arr = url.split('/');
        var docPid = arr[arr.length - 1];
        //查询文件表数据
        $.ajax({
            type: 'get',
            url: '${base}/bus/docFile/query?docPid=' + docPid,
            dataType: 'json',
            success: function (res) {
                $('#docTable').bootstrapTable('load', res);
            }
        })
    })

    function getData() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row
        });
    }

    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };

    //文件表所有选中的数据
    function getselectData() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }

</script>
</body>
</html>