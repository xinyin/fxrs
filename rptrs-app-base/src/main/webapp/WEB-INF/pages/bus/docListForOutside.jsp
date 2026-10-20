<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">
        #docTable_tb_exportIn, #docTable_tb_removeDoc,
        #docTable_tb_downFileOnclick
        {
            display: none;

        }
    </style>
    <title>Doc</title>
</head>
<style type="text/css">
    #docTable {
        text-align: center;
        vertical-align: middle;
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
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bf:hidden name="systemType" id="systemType" value="O"></bf:hidden>
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="文件名称" col="4" formControl="true"/>
                        <bf:text name="__LIKES_fileName" col="7" placeholder="文件名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="上传时间" col="4" formControl="true"/>
                        <bf:datebox id="__GES_createTime" name="__LIKES_createTime" col="7"/>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="docTable" idField="pid" url="${base}/bus/docFile" queryUrl="/query?flag=2" selectFirst="false">
                <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
                <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>
                <bc:toolbar id="downFileOnclick" text="下载文件" type="button" handler="downFileOnclick"/>
<%--                <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>--%>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="fileName" title="文件名" width="100"/>
                <bc:column field="fileType" title="文件类型" width="100"/>
                <bc:column field="fileSize" title="大小" width="100"/>
<%--                <bc:column field="uploadOrgName" title="上传机构" width="100"/>--%>
                <bc:column field="uploadDeptName" title="上传部门" width="100"/>
                <bc:column field="createTimeString" title="上传时间" width="100"/>
                <bc:operator id="viewFile" icon="eye" title="操作/查看" handler="viewFile"/>
            </bc:table>
        </bc:ibox>
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

        //根据权限显示按钮
        $(function () {
            var code = "${code }";
            if (code.search("021") != -1) {
                $('#docTable_tb_exportIn').show();
            }
            if (code.search("03,") != -1) {
                $('#docTable_tb_removeDoc').show();
            }
            if (code.search("013") != -1) {
                $('#docTable_tb_downFileOnclick').show();
            }

        })


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
        //文件上传
        $("#upload").click(function () {
            var obj = document.getElementById("file");
            for (var i = 0; i < obj.files.length; i++) {
                var temp = obj.files[i].name;
                var temArr = temp.split('.');
                var fileLast = temArr[temArr.length - 1];
                // if (fileLast != 'pdf') {
                //     alert("请上传pdf文件");
                //     return;
                // }
            }
            var formData = new FormData($('#uploadForm')[0]);
            $.ajax({
                type: 'post',
                url: '${base}/bus/docFile/uploadDocFile?flag=2', //上传文件的请求路径必须是绝对路劲
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

        //文件表所有选中的数据
        function getselectData() {
            return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
                return row//返回数据行中的id值
            });
        }

        /******************************************/

        //编辑
        function edit() {
            //获取pid
            var pid = getIdSelections();
            if (pid == '' || pid == undefined) {
                alert("请选择一条数据");
                return;
            }
            if (pid.indexOf(",") != -1) {
                alert("最多选择一条数据");
                return;
            } else {
                window.location = '${base}/bus/docForOutside/edit/' + pid;
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
        // function downloadFild() {
        //     //判断是否有文件
        //     var tables = $('#docTable').jTable('getCheckData')
        //     var data = []
        //     tables.forEach(v => {
        //         data.push(v.pid)
        //     })
        //     console.log(data)
        //     var params = {
        //         pid: data
        //     };
        //     var form = document.createElement('form')
        //     form.id = 'form'
        //     form.name = 'form'
        //     document.body.appendChild(form)
        //     for (var obj in params) {
        //         if (params.hasOwnProperty(obj)) {
        //             var input = document.createElement('input')
        //             input.tpye = 'hidden'
        //             input.name = obj;
        //             input.value = params[obj]
        //             form.appendChild(input)
        //         }
        //     }
        //     form.method = "GET" //请求方式
        //     form.action = '/bus/docFile/download/'
        //     form.submit()
        //     document.body.removeChild(form)
        // }

        //删除
        function remove() {
            var pids = getIdSelections();

            if (pids == "" || pids == undefined) {
                alert("请选中一条数据")

            } else {
                var r = confirm("确定要删除数据？");
                if (r) {
                    $.ajax({
                        url: '${base}/bus/docForOutside/remove/' + pids,
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

        //跳出选择外规页面

        function getIdSelections() {
            return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
                return row.pid//返回数据行中的id值
            });
        }
        /*
        重置
        */
        function frmReset(){
            window.location = '${base}/bus/docForOutside/list';
        }

    </script>
</body>
</html>