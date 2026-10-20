<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>GdTraZr</title>
    <script>
        function getFromShareholder() {
            var fromShareholdersCode = $('#fromShareholdersCode').val();
            $.ajax({
                type: 'GET',
                url: '${base}/bus/gdTraZr/getShareholder',
                data: {code: fromShareholdersCode},
                dataType: 'json',
                success: function (data) {
                    if (!data.success) {
                        $.messager.alert('没有这个股东');
                        $('#fromShareholdersCode').val('');
                        $('#fromShareholdersName').val('');
                        $('#beforeFromAmount').val('');
                        $('#beforeFromProportion').val('');
                    } else {
                        $('#fromShareholdersName').val(data.name);
                        $('#beforeFromAmount').val(data.holeShareAmount);
                        $('#beforeFromProportion').val(data.shareholdingProportion);
                        $('#fromShareholdersType').val(data.shareholdersType);
                    }
                    var beforeToAmount = $('#beforeToAmount').val();
                    var operationAmount = $('#operationAmount').val();
                    if (operationAmount != null && operationAmount != undefined && operationAmount != '' &&
                        beforeToAmount != null && beforeToAmount != undefined && beforeToAmount != '') {
                        getToFromAmount();
                    }
                }
            });
        }

        function getToShareholder() {
            var toShareholdersCode = $('#toShareholdersCode').val()
            $.ajax({
                type: 'GET',
                url: '${base}/bus/gdTraZr/getShareholder',
                data: {code: toShareholdersCode},
                dataType: 'json',
                success: function (data) {
                    var toShareholdersCode = data.shareHolder;
                    if (!data.success) {
                        $.messager.alert('没有这个股东');
                        $('#toShareholdersCode').val('')
                        $('#toShareholdersName').val('');
                        $('#beforeToAmount').val('');
                        $('#beforeToProportion').val('');
                    } else {
                        $('#toShareholdersName').val(data.name);
                        $('#beforeToAmount').val(data.holeShareAmount);
                        $('#beforeToProportion').val(data.shareholdingProportion);
                        $('#toShareholdersType').val(data.shareholdersType);
                    }
                    var beforeFromAmount = $('#beforeFromAmount').val();
                    var operationAmount = $('#operationAmount').val();
                    if (operationAmount != null && operationAmount != undefined && operationAmount != '' &&
                        beforeFromAmount != null && beforeFromAmount != undefined && beforeFromAmount != '') {
                        getToFromAmount();
                    }
                }
            });
        }

        function getToFromAmount() {
            var operationAmount = parseFloat($('#operationAmount').val());
            if ($('#operationAmount').val() == '') {
                operationAmount = 0;
            }
            var toamount = $('#beforeToAmount').val();
            var fromamount = $('#beforeFromAmount').val();
            if (toamount == null || toamount == undefined || fromamount == null ||
                fromamount == undefined || fromamount == '' || toamount == '') {
                return false;
            }
            var beforeFromAmount = parseFloat($('#beforeFromAmount').val());
            var beforeToAmount = parseFloat($('#beforeToAmount').val());
            var afterFromAmount = beforeFromAmount - operationAmount;
            var afterToAmount = beforeToAmount + operationAmount;
            $.ajax({
                url: '${base}/bus/gdTraZr/getProportion',
                type: 'post',
                data: {
                    amount: afterFromAmount + ',' + afterToAmount
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $('#afterFromAmount').val(afterFromAmount);
                        $('#afterToAmount').val(afterToAmount);
                        var fromProportion = data.fromProportion;
                        var toProportion = data.toProportion;
                        $('#afterFromProportion').val(fromProportion);
                        $('#afterToProportion').val(toProportion);
                    }
                }
            });
        }

        function getAllIdSelections() {
            return $.map($('#docTable').bootstrapTable('getData'), function (row) {
                return row.pid//返回数据行中的id值
            });
        }
    </script>
    <style>
        .center div {
            float: none;
            margin: 1% 0;
        }

        .lefts, .rights {
            border: 1px solid #cccccc;
            width: 40%;
        }

        .lefts p, .rights p {
            width: 16%;
            margin: 3% auto;
            padding: 2%;
            background: #0d8ddb;
            color: white;
            border-radius: 6px;
        }

        .lefts div, .rights div {
            float: none;
            margin: 2% 0;
            display: flex;

        }

        .center {
            margin: 10% 0 0 0;
            width: 20%;
        }

        .center label {
            text-align: center !important;
        }

        .box {
            display: flex;
            justify-content: space-around;
            width: 80%;
            margin: 0 auto;
        }

        .file {
            position: absolute;
            top: 100px;
            left: 44%;
            display: inline-block;
            background: #D0EEFF;
            border: 1px solid #99D3F5;
            border-radius: 4px;
            padding: 4px 12px;
            overflow: hidden;
            color: #1E88C7;
            text-decoration: none;
            text-indent: 0;
            line-height: 20px;
        }

        .file input {
            position: absolute;
            font-size: 100px;
            right: 0;
            top: 0;
            opacity: 0;
        }

        .file:hover {
            background: #AADFFD;
            border-color: #78C3F3;
            color: #004974;
            text-decoration: none;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="gdTraZrBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${gdTraZr.pid}"/>
            <bf:hidden id="pid" name="pid" value="${gdTraZr.pid}"/>
            <bf:hidden id="fromShareholdersType" name="fromShareholdersType" value="${gdTraZr.fromShareholdersType}"/>
            <bf:hidden id="toShareholdersType" name="toShareholdersType" value="${gdTraZr.toShareholdersType}"/>
            <bf:hidden id="traSerial" name="traSerial" value="${gdTraZr.traSerial}"/>
            <div class="box">
                <div class="lefts">
                    <p>转让人</p>
                    <bc:col col="12">
                        <bc:label cls="required" text="赠与人股东代码" col="6" formControl="true"/>
                        <bf:text id="fromShareholdersCode" name="fromShareholdersCode" col="8"
                                 value="${gdTraZr.fromShareholdersCode}" change="getFromShareholder()">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label cls="required" text="赠与人名称" col="6" formControl="true"/>
                        <bf:text id="fromShareholdersName" name="fromShareholdersName" col="8"
                                 value="${gdTraZr.fromShareholdersName}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="赠与人变更前持股数量（股）" col="6" formControl="true"/>
                        <bf:text id="beforeFromAmount" name="beforeFromAmount" col="8"
                                 value="${gdTraZr.beforeFromAmount}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="赠与人变更前持股占比（%）" col="6" formControl="true"/>
                        <bf:text id="beforeFromProportion" name="beforeFromProportion" col="8"
                                 value="${gdTraZr.beforeFromProportion}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="赠与人变更后持股数量（股）" col="6" formControl="true"/>
                        <bf:text id="afterFromAmount" name="afterFromAmount" col="8" value="${gdTraZr.afterFromAmount}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="赠与人变更后持股数占比（%)" col="6" formControl="true"/>
                        <bf:text id="afterFromProportion" name="afterFromProportion" col="8"
                                 value="${gdTraZr.afterFromProportion}" disabled="true">
                        </bf:text>
                    </bc:col>
                </div>
                <div class="center">
                    <bc:col col="12">
                        <bc:label text="赠与价格（元）" col="12" formControl="true"/>
                        <bf:text id="operationPrice" name="operationPrice" col="12" value="${gdTraZr.operationPrice}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label cls="required" text="赠与股数（股）" col="12" formControl="true"/>
                        <bf:text id="operationAmount" name="operationAmount" col="12" value="${gdTraZr.operationAmount}"
                                 keyup="getToFromAmount()">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                </div>
                <div class="rights">
                    <p>受让人</p>
                    <bc:col col="12">
                        <bc:label cls="required" text="受赠人股东代码" col="6" formControl="true"/>
                        <bf:text id="toShareholdersCode" name="toShareholdersCode" col="8"
                                 value="${gdTraZr.toShareholdersCode}" change="getToShareholder()">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label cls="required" text="受赠人股东名称" col="6" formControl="true"/>
                        <bf:text id="toShareholdersName" name="toShareholdersName" col="8"
                                 value="${gdTraZr.toShareholdersName}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="受赠人现持股数量（股）" col="6" formControl="true"/>
                        <bf:text id="beforeToAmount" name="beforeToAmount" col="8" value="${gdTraZr.beforeToAmount}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="受赠人现持股占比（%）" col="6" formControl="true"/>
                        <bf:text id="beforeToProportion" name="beforeToProportion" col="8"
                                 value="${gdTraZr.beforeToProportion}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="受赠人变更后持股数量（股）" col="6" formControl="true"/>
                        <bf:text id="afterToAmount" name="afterToAmount" col="8"
                                 value="${gdTraZr.afterToAmount}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="受赠人变更后持股数占比（%）" col="6" formControl="true"/>
                        <bf:text id="afterToProportion" name="afterToProportion" col="8"
                                 value="${gdTraZr.afterToProportion}" disabled="true">
                        </bf:text>
                    </bc:col>
                </div>
            </div>
            <div id="tableDiv" style="width: 80%;margin: 0 auto;">
                <bc:table id="docTable" idField="pid" url="${base}/bus/gdFile" multiple="true"
                          selectFirst="false" queryUrl="queryList?traSerial=${gdTraZr.traSerial}">
                    <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
                    <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>
                    <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
                    <bc:column field="pid" title="主键" width="100" visible="false"/>
                    <bc:column field="fileName" title="文件名" width="100"/>
                    <bc:column field="fileType" title="文件类型" width="100"/>
                    <bc:column field="fileSize" title="大小" width="100"/>
                    <bc:column field="uploadOrg" title="上传机构" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-GHOrg"/>
                    <bc:column field="uploadDep" title="上传部门" width="100"/>
                    <bc:column field="createTime" title="上传时间" width="100"/>
                </bc:table>
            </div>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding:2% 0 0 0">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            var docInnerPids = getAllIdSelections();
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            var docTableSelectItem = {
                            'name':'docTableSelectItem',
                            'value':docInnerPids
                            };
                            formData.push(docTableSelectItem);
                            $.mask.post('${base}/bus/gdTraZr/save/2', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/bus/gdTraZr/giftList';
                            });
                            }
                            else
                            {
                            $.messager.alert('错误', data.msg, 'error');
                            }
                            });
                            }
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/gdTraZr/giftList';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
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
                    <form method="post" enctype="multipart/form-data" id="uploadForm"
                          style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
                        <div class="form-group" style="text-align: center;vertical-align: middle;">
                            <label for="file"></label>
                            <img src="../../static/img/sendfile.png"/>
                            <a href="javascript:void(0);" class="file">选择文件
                                <input type="file" class="form-control" id="file" name="file" multiple
                                       style="z-index: 1;padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
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
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {

    });

    function exportIn() {
        $("#hiddenBtn").click();
    }

    function getIdSelections() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    //文件表所有选中的数据
    function getselectData() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }

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
            url: '${base}/bus/gdFile/uploadDocFile', //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                $("#close").click();
                $.messager.alert(data.msg);
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
                }
            },
            error: function () {
                $("#close").click();
                $.messager.alert("上传失败,请重试");
            }
        })
    });

    //文件表数据
    function getData() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row
        });
    }

    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        if (pid == '' || pid == undefined) {
            $.messager.alert("请选中一个文件");
            return;
        }
        if (pid.indexOf(',') != -1) {
            $.messager.alert("只能选中一个文件");
            return;
        }
        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/bus/gdFile/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
    }
    //移除文件
    removeDoc = function () {
        var rows = getData();//删除前数据
        var pids = getIdSelections();//删除数据主键
        var selectRows = getselectData();//选中的数据
        if (selectRows.length == 0) {
            $.messager.alert("请先选中数据");
            return;
        }
        if (confirm("确认删除？")) {
            $.ajax({
                type: 'get',
                url: '${base}/bus/gdFile/removeFile/' + pids,
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

    function getAllIdSelections() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    $(document).ready(function(){
        $(".required").each(function() {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>"+$this.html());
        });
    });

</script>
</body>
</html>
