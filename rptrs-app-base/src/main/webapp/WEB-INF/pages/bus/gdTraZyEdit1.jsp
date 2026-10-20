<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>GdTraZy</title>
    <style>
        .form-group .col-xs-4{
            margin: 0.3% 0!important;
            padding-top:5px;
        }
        .form-group .col-xs-4>.col-xs-7{
            height: 34px !important;
        }
        .form-group .input-group.date,
        .form-group .select2{
            width: 100% !important;
        }
        .form-group {
            margin-bottom: 8px !important;
        }

        .hr-line-dashed {
            border-top: 1px dashed #e7eaec;
            color: #ffffff;
            background-color: #ffffff;
            height: 1px;
            margin: 7px 0 !important;
        }

        .bootstrap-table {
            margin: 0 5%;
        }

        .filesup .btn {
            margin: 0 0 -18px 16% !important;
        }

        .fixed-table-toolbar .bs-bars, .fixed-table-toolbar .columns, .fixed-table-toolbar .search {
            position: relative;
            margin: 0 0 5px 0 !important;
            line-height: 34px;
            display: flex !important;
        }
    </style>
</head>
<body class="gray-bg" style="background:white">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="editForm" method="post" validation="true">
                <bf:hidden id="pid" name="pid" value="${gdTraZy.pid}"/>
                <bf:hidden id="domesticOrForeign" name="domesticOrForeign" value="${gdTraZy.domesticOrForeign}"/>
                <bf:hidden id="stateOwned" name="stateOwned" value="${gdTraZy.stateOwned}"/>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label cls="required" text="股东代码" col="5" formControl="true"/>
                        <bf:text id="shareholdersCode" name="shareholdersCode" col="7" placeholder="股东代码" value="${gdTraZy.shareholdersCode}"
                                 change="getInfo()"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="股东名称" col="5" formControl="true"/>
                        <bf:text id="shareholdersName" name="shareholdersName" col="7" placeholder="股东名称" value="${gdTraZy.shareholdersName}"
                                 change="getInfo"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="股东类型" col="5" formControl="true"/>
                        <bf:select id="shareholdersType" name="shareholdersType" col="7" dataSource="custom" value="${gdTraZy.shareholdersType}"
                                   url="sys/dictItem/getDictItems/BUS-RelatedPartiesType" disabled="true">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#shareholderType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="总持有股数" col="5" formControl="true"/>
                        <bf:text id="holeShareAmount" name="holeShareAmount" col="7" placeholder="总持有股数" value="${gdTraZy.holeShareAmount}"
                                 disabled="true"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="总质押股数" col="5" formControl="true"/>
                        <bf:text id="pledgedShareAmount" name="pledgedShareAmount" col="7" placeholder="总质押股数" value="${gdTraZy.pledgedShareAmount}"
                                 disabled="true"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="质押股数" col="5" formControl="true"/>
                        <bf:text name="operationAmount" col="7" placeholder="质押股数" value="${gdTraZy.operationAmount}"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="质押生效时间" col="5" formControl="true"/>
                        <bf:datebox id="effectiveTime" name="effectiveTime" col="7" format="date"
                                    value="${gdTraZy.effectiveTime}"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="质押合同号" col="5" formControl="true"/>
                        <bf:text name="pledgedContract" col="7" placeholder="质押合同号" disabled="true" value="${gdTraZy.pledgedContract}"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="质押融资额" col="5" formControl="true"/>
                        <bf:text name="pledgedAmount" col="7" placeholder="质押融资额" value="${gdTraZy.pledgedAmount}"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="质权人名称" col="5" formControl="true"/>
                        <bf:text name="name" col="7" placeholder="质权人名称" value="${gdTraZy.name}"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="质权人证件类型" col="5" formControl="true"/>
                        <bf:select id="identifyType" name="identifyType" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-IdentifyType" col="7"
                                   value="${gdTraZy.identifyType}">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_identifyType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="质权人证件号码" col="5" formControl="true"/>
                        <bf:text name="identifyNumber" col="7" placeholder="质权人证件号码" value="${gdTraZy.identifyNumber}"/>
                    </bc:col>
                </bf:formGroup>

                <div id="tableDiv" style="width: 95%;margin:0 auto">
                    <bc:table id="docTable" idField="pid" url="${base}/bus/gdFile" multiple="true"
                              selectFirst="false" queryUrl="queryList?traSerial=${gdTraJy.frozenSerial}">
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
                    <div class="col-sm-12 col-sm-offset-5" style="padding:3% 0 0 0;">
                        <bc:button type="danger" text="保存" icon="save">
                            <be:event>
                                $('#editForm').data('formValidation').validate();
                                var isValid = $('#editForm').data('formValidation').isValid();
                                if(isValid)
                                {
                                var formData = $('#editForm').serializeAll();
                                $.mask.post('${base}/bus/gdTraZy/save/1', formData, function(data){
                                if(data.success)
                                {
                                $.messager.alert('提示', data.msg, 'info', function(){
                                window.location = '${base}/bus/gdTraZy/list';
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
                                window.location = '${base}/bus/gdTraZy/list';
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
                            <input type="file" class="form-control" id="file" name="file" multiple
                                   style="padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
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
<script src="../../static/js/jquery.dialog.js"></script>

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
                }
            },
            error: function () {
                $("#close").click();
                alert("上传失败,请重试");
            }
        })
    });

    //文件表数据
    function getData() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row
        });
    }

    //表单提交数据
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeAll();
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

    //保存
    save = function () {
        var obj1 = $('#editForm').serializeJson();
        var pid = $('#pid').val();
        var opt = $('#opt').val();
        var domesticOrForeign = $('#domesticOrForeign').val();
        var shareholdersCode = $('#shareholdersCode').val();
        var shareholdersName = $('#shareholdersName').val();
        var shareholdersType = $('#shareholdersType').val();
        var holeShareAmount = $('#holeShareAmount').val();
        var frozenShareAmount = $('#frozenShareAmount').val();
        var operationAmount = $('#operationAmount').val();
        var frozenOrg = $('#frozenOrg').val();
        var frozenSerial = $('#frozenSerial').val();
        var frozenReason = $('#frozenReason').val();
        var continueFrozenReason = $('#continueFrozenReason').val();
        var effectiveTime = obj1['effectiveTime'];
        var failureTime = obj1['failureTime'];
        var continueFrozenTime = obj1['continueFrozenTime'];

        //获取表格1数据
        var docInnerPids = getAllIdSelections();
        if (docInnerPids == '' || docInnerPids == undefined) {
            alert("文件不能为空,请选中或上传文件");
            return;
        }
        var obj = {
            'opt': opt,
            'docTableSelectItem': docInnerPids,
            'pid': pid,
            'domesticOrForeign': domesticOrForeign,
            'shareholdersCode': shareholdersCode,
            'shareholdersName': shareholdersName,
            'shareholdersType': shareholdersType,
            'holeShareAmount': holeShareAmount,
            'frozenShareAmount': frozenShareAmount,
            'frozenSerial': frozenSerial,
            'operationAmount': operationAmount,
            'effectiveTime': effectiveTime,
            'failureTime': failureTime,
            'frozenOrg': frozenOrg,
            'continueFrozenTime': continueFrozenTime,
            'frozenReason': frozenReason,
            'continueFrozenReason': continueFrozenReason
        };
        var objJson = JSON.stringify(obj);
    debugger;
        $.ajax({
            contentType: 'application/json',
            url: '${base}/bus/gdTraJy/save/jy',
            type: 'post',
            dataType: 'json',
            data: objJson,
            success: function (res) {
                alert(res.msg);
                if (res.success) {
                    window.location = '${base}/bus/gdTraJy/list';
                }
            }
        })
    }

    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        if (pid == '' || pid == undefined) {
            alert("请选中一个文件");
            return;
        }
        if (pid.indexOf(',') != -1) {
            alert("只能选中一个文件");
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
            alert("请先选中数据");
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

    function getInfo() {
        var shareholdersCode = $('#shareholdersCode').val();
        var shareholdersName = $('#shareholdersName').val();
        var obj = {
            shareholdersCode: shareholdersCode,
            shareholdersName: shareholdersName
        };
        $.ajax({
            url: '${base}/bus/gdTraZy/getShareholderInfo',
            dataType: 'json',
            data: obj,
            type: 'post',
            success: function (data) {
                if (data) {
                    $('#shareholdersName').val(data.name);
                    $('#holeShareAmount').val(data.holeShareAmount);
                    $('#pledgedShareAmount').val(data.pledgedShareAmount);
                    $("#shareholdersType").val(data.shareholdersType).trigger("change");   //设置默认值
                    $('#domesticOrForeign').val(data.domesticOrForeign);
                    $('#stateOwned').val(data.stateOwned);
                } else {
                    alert(data.msg);
                }
            }
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