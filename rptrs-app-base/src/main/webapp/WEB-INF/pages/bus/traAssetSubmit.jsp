<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>TraAsset</title>
    <style>
        .form-group .col-xs-4 {
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

        .select2-dropdown, .has-success .select2-selection {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="traAssetBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${traAsset.pid}"/>
            <bf:hidden id="transNum" name="transNum" value="${traAsset.transNum}"/>
            <bf:hidden id="relId" name="relId" value="${relId}"/>
            <bf:hidden id="relType" name="relType" value="${relType}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="关联方名称" col="5" formControl="true"/>
                    <bf:text id="relatedName" name="relatedName" col="7" value="${traAsset.relatedName}"
                             disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="证件类型" col="5" formControl="true"/>
                    <bf:select id="identifyType" name="identifyType" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-IdentifyType" col="7" disabled="true"
                               value="${traAsset.identifyType}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="证件号码" col="5" formControl="true"/>
                    <bf:text id="identNumber" name="identNumber" col="7" value="${traAsset.identNumber}"
                             disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="持股占比" col="5" formControl="true"/>
                    <bf:text id="shareholdingProportion" name="shareholdingProportion" col="7"
                             value="${traAsset.shareholdingProportion}"
                             disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="表决权占比" col="5" formControl="true"/>
                    <bf:text id="voteProportion" name="voteProportion" col="7" value="${traAsset.voteProportion}"
                             disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易机构" col="5" formControl="true"/>
                    <bf:select id="manageOrg" name="manageOrg" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-GHOrg" col="7" disabled="true"
                               value="${traCredit.manageOrg}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="产品名称" col="5" formControl="true"/>
                    <bf:text id="productName" name="productName" col="7" value="${traAsset.productName}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="一级业务种类" col="5" formControl="true"/>
                    <bf:text id="typeOneBus" name="typeOneBus" col="7" value="${traAsset.typeOneBus}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="二级业务种类" col="5" formControl="true"/>
                    <bf:text id="typeTwoBus" name="typeTwoBus" col="7" value="${traAsset.typeTwoBus}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="三级业务种类" col="5" formControl="true"/>
                    <bf:text id="typeThreeBus" name="typeThreeBus" col="7" value="${traAsset.typeThreeBus}"
                             disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="四级业务种类" col="5" formControl="true"/>
                    <bf:text id="typeFourBus" name="typeFourBus" col="7" value="${traAsset.typeFourBus}"
                             disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务表内外属性" col="5" formControl="true"/>
                    <bf:text id="tableAttrBus" name="tableAttrBus" col="7" value="${traAsset.tableAttrBus}"
                             disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务条线" col="5" formControl="true"/>
                    <bf:text id="busLine" name="busLine" col="7" value="${traAsset.busLine}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易标的" col="5" formControl="true"/>
                    <bf:text id="tranTarget" name="tranTarget" col="7" value="${traAsset.tranTarget}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易发生金额" col="5" formControl="true"/>
                    <bf:text id="transationAmount" name="transationAmount" col="7" value="${traAsset.transationAmount}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="年利率/费率%" col="5" formControl="true"/>
                    <bf:text id="rate" name="rate" col="7" value="${traAsset.rate}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="合同编号" col="5" formControl="true"/>
                    <bf:text id="contractNo" name="contractNo" col="7" value="${traAsset.contractNo}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="合同签署日期" col="5" formControl="true"/>
                    <bf:datebox id="contractSignDate" name="contractSignDate" col="7" disabled="true"
                                value="${traAsset.contractSignDate}"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易价格" col="5" formControl="true"/>
                    <bf:text id="tranPrice" name="tranPrice" col="7" value="${traAsset.tranPrice}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易币种" col="5" formControl="true"/>
                    <bf:select id="bz" name="bz" dataSource="custom" url="sys/dictItem/getDictItems/BUS-Currency" disabled="true"
                               col="7" value="${traAsset.bz}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="合同有效期" col="5" formControl="true"/>
                    <bf:datebox id="validityContractDate" name="validityContract" col="7" disabled="true"
                                value="${traAsset.validityContract}"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="审批通过时间" col="5" formControl="true"/>
                    <bf:datebox id="approvalOverTime" name="approvalTime" col="7" value="${traAsset.approvalTime}" disabled="true"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易完成时间" col="5" formControl="true"/>
                    <bf:datebox id="tranOverDate" name="tranDate" col="7" value="${traAsset.tranDate}" disabled="true"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务起始时间" col="5" formControl="true"/>
                    <bf:datebox id="businessStartTime" name="businessStartTime" col="7" disabled="true"
                                value="${traAsset.businessStartTime}"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务完成时间" col="5" formControl="true"/>
                    <bf:datebox id="businessEndTime" name="businessEndTime" col="7" disabled="true"
                                value="${traAsset.businessEndTime}"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务类型" col="5" formControl="true"/>
                    <bf:select id="businessType" name="businessType" dataSource="custom" disabled="true"
                               url="sys/dictItem/getDictItems/BUS-AssetTransfer" col="7"
                               value="${traAsset.businessType}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="资产评估价" col="5" formControl="true"/>
                    <bf:text id="appraisalAssets" name="appraisalAssets" col="7" value="${traAsset.appraisalAssets}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="统计口径" col="5" formControl="true"/>
                    <bf:select id="regulatorOth1" name="regulatorOth1" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Caliber" col="7" disabled="true"
                               value="${traAsset.regulatorOth1}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
            </bf:formGroup>

            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="12">
                    <bc:label text="定价政策" col="0" formControl="true"/>
                    <bf:text id="pricingStrategy" name="pricingStrategy" col="7" disabled="true"
                             multiple="true" value="${traAsset.pricingStrategy}">
                    </bf:text>
                </bc:col>

                <bc:col col="12">
                    <bc:label text="其他交易信息" col="0" formControl="true"/>
                    <bf:text id="remark" name="remark" col="7" disabled="true"
                             multiple="true" value="${traAsset.remark}">
                    </bf:text>
                </bc:col>

            </bf:formGroup>
            <div id="tableDiv" style="width:95%;margin:0 auto">
                <bc:table id="docTable" idField="pid" url="${base}/bus/traEnclosure" multiple="true"
                          selectFirst="false" queryUrl="queryList?transNum=${traAsset.transNum}">
                    <%--                    <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>--%>
                    <%--                    <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>--%>
                    <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
                    <bc:column field="pid" title="主键" width="100" visible="false"/>
                    <bc:column field="relatedName" title="文件名" width="100"/>
                    <bc:column field="fileType" title="文件类型" width="100"/>
                    <bc:column field="fileSize" title="大小" width="100"/>
                    <bc:column field="uploadOrg" title="上传机构" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-GHOrg"/>
                    <bc:column field="uploadDept" title="上传部门" width="100"/>
                    <bc:column field="uploadTime" title="上传时间" width="100"/>
                </bc:table>
            </div>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="12">
                    <bc:label text="提交备注" col="0" formControl="true"/>
                    <bf:text id="statusComment" name="statusComment" col="7"
                             multiple="true" value="${traAsset.statusComment}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding: 3% 0 0 0">
                    <bc:button type="danger" text="确认提交" icon="save" click="sub()">
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/traAsset/list';
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
                            <a href="javascript:;" class="file">选择文件
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
            url: '${base}/bus/traEnclosure/uploadFile', //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                $("#close").click();
                alert(data.msg);
                if (data.success) {
                    //处理数据
                    var files = data.traEnclosure;
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
            + encodeURIComponent('${base}/bus/traEnclosure/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
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
                url: '${base}/bus/traEnclosure/removeFile/' + pids,
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
        var relatedName = $("#relatedName").val();
        var identifyType = $("#identifyType").val();
        var identNumber = $("#identNumber").val();
        var obj = {
            'relatedName': relatedName,
            'identifyType': identifyType,
            'identNumber': identNumber
        }
        $.ajax({
            type: 'post',
            url: '${base}/bus/traCredit/getRelInfo',
            dataType: 'json',
            data: obj,
            success: function (data) {
                if (data.success != undefined) {
                    if (data.success) {
                        var relType = data.relType;
                        var relId = data.relId;
                        var voteProportion = data.voteProportion;
                        var shareholdingProportion = data.shareholdingProportion;
                        $('#relType').val(relType);
                        $('#relId').val(relId);
                        $('#voteProportion').val(voteProportion);
                        $('#shareholdingProportion').val(shareholdingProportion);
                    }
                    if (!data.success) {
                        alert(data.msg);
                        $("#identNumber").val('');
                        $("#relatedName").val('');
                        $("#identifyType").val('');
                    }
                }
            }
        })
    }

    function sub() {
        var pid = $('#pid').val();
        var statusComment = $('#statusComment').val();
        var obj = {
            statusComment: statusComment
        };
        $.ajax({
            url: "${base}/bus/traAsset/changeApplyStatus/" + pid,
            data: obj,
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    var formData = $('#editForm').serializeJson();
                    var myDate = new Date();
                    formData.id = myDate.getTime();
                    formData.type = "ASSET";
                    data = JSON.stringify(formData);
                    var formData = $.helper.base64Encode(data);
                    var obj = {
                        procDefCode: 'trancredit',
                        oldData: '',
                        newData: formData,
                        uploadSessions: '',
                        procInstId: ''
                    }
                    $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
                        if (data.success) {
                            $.messager.alert('提示', '提交成功。', 'info', function () {
                                window.location = '${base}/workflow/process/runingList';
                            });
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });
                } else {
                    alert(data.msg);
                }
            }
        })
    }

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

</script>

<script type="text/javascript">
    $("#approvalTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });

    $("#applicaSubTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });


    $("#tranDate").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });

    $("#validityContract").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });

</script>

</body>
</html>
