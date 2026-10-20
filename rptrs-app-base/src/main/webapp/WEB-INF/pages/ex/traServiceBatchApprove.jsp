<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>提供货物或服务类</title>
    <style>
        .form-group .input-group.date,
        .form-group .select2{
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
        .bs-checkbox{
            display: none;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="traFundsBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true" >
            <div id="tableDiv" style="width:95%;margin:0 auto">
                <bc:table id="traFundsTable" idField="pid" url="${base}/ins/traService" queryUrl="queryPidsByConditions/${pids}"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="relId" title="关联方ID" width="100" visible="false"/>
                <bc:column field="transNum" title="交易流水号" width="100"/>
                <bc:column field="tranName" title="交易名称" width="100"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="relNumber" title="证照号码" width="100"/>
                <bc:column field="busType" title="业务类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-ServiceType"/>
                <bc:column field="majorTrade" title="是否重大关联交易" width="100" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="relTradeAmount" title="关联交易金额（万元/人民币）" width="100"/>
                <bc:column field="pracTradeAmount" title="实际交易金额" width="100" visible="false"/>
                <bc:column field="contractDateString" title="合同签署日期" width="100"/>
                <bc:column field="considerType" title="审议类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-considerType"/>
                <bc:column field="status" title="交易状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:operator id="view" icon="eye" title="操作/查看"/>
            </div>
        </bf:form>
    </bc:ibox>

    <bf:formGroup>
        <div class="col-sm-4 col-sm-offset-5">

            <bf:formGroup hrp="bottom">
                <bc:button type="primary" text="返回" icon="arrow-circle-left">
                    <be:event>
                        window.history.go(-1)
                    </be:event>
                </bc:button>
            </bf:formGroup>
        </div>
    </bf:formGroup>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">
    //跟据审议类型规则给定审议类型得值
    function isConsiderType(){
        //是否为重大关联交易
        var isMajorTrade = $('#majorTrade').val();
        //是否为国家定价
        var isDealPrice = $('#isDealPrice').val();
        //交易金额
        var tradeAmount = $('#tradeAmount').val();
        //关联方类型
        var relType = $('#relType').val();
        //如果是重大关联交易审议类型为逐笔审议
        if (isMajorTrade != null && isMajorTrade != undefined && isMajorTrade != ''){
            if(isMajorTrade == "0"){
                $('#considerType').val("3");
                $('#considerType').select2({value: "3"});
                return;
            }
        }
        //如果是国家定价重大关联交易审议类型为免予审议
        if(isDealPrice != null && isDealPrice != undefined && isDealPrice != ''){
            if(isDealPrice == "0"){
                $('#considerType').val("1");
                $('#considerType').select2({value: "1"});
                return;
            }
        }
        //根据关联方规则判断审议类型
        if(tradeAmount != null && tradeAmount != undefined && tradeAmount != '' && relType != null && relType != undefined && relType != ''){
            if(relType == "PSN" && tradeAmount <= "50"){
                $('#considerType').val("1");
                $('#considerType').select2({value: "1"});
                return;
            }
            if(relType == "ORG" && tradeAmount <= "500"){
                $('#considerType').val("1");
                $('#considerType').select2({value: "1"});
                return;
            }
        }
        $('#considerType').val("2");
        $('#considerType').select2({value: "2"});
    }

    //判断是否为控股子公司来决定子公司名称证件号能不能添加
    function HoldingSubsidiarySub(value){
        var value = value
        if(value == '1'){
            $('#holdingSubsidiaryNumber').attr("disabled","false");
            $('#holdingSubsidiaryName').attr("disabled","false");

        }
        if(value == '0'){
            $('#holdingSubsidiaryNumber').removeAttr("disabled");
            $('#holdingSubsidiaryName').removeAttr("disabled");

        }

    };


    var opt = '${opt}';
    $(document).ready(function () {
        if (opt == 'view'){
            $("#save1").css("display", "none");
            $("#docTable_tb_exportIn").css("display", "none");
            $("#docTable_tb_removeDoc").css("display", "none");
        }else {
            var val = $('#isHoldingSubsidiary').val();
            if (val != null && val != undefined && val != ''){
                HoldingSubsidiarySub(val)
            }
        }
        $('#tranType').val("02");
        $('#tranType').select2({value: "02"});
        var investmentType = $('#investmentType').val();
        // var count = 1;
        for (var i = 1; i < 6; i++) {
            if (investmentType == '0'+i) {
                $('.Type' + i).show();
            }
            //count++;
        }
    });
    function onSelect() {
        var proName = $("input[name=proName]").val();
        $.ajax({
            type: "post",
            url: "${base}/ins/traFunds/selectbuinessL",
            data: {"unitCodes": proName},
            success: function (data) {
                if (data.success) {
                    var investmentType = data.investmentType;
                    $('#investmentType').val(investmentType);
                    $('#investmentType').select2({value: investmentType});
                    var val = investmentType
                    if (val == '224') {
                        $("#1").css("display", "block");
                        $("#2").css("display", "none");
                        $("#3").css("display", "none");
                        $("#4").css("display", "none");
                        $("#5").css("display", "none");
                        $("#6").css("display", "none");
                    }
                    if (val == '225') {
                        $("#1").css("display", "none");
                        $("#2").css("display", "block");
                        $("#3").css("display", "none");
                        $("#4").css("display", "none");
                        $("#5").css("display", "none");
                        $("#6").css("display", "none");
                    }
                    if (val == '226') {
                        $("#1").css("display", "none");
                        $("#2").css("display", "none");
                        $("#3").css("display", "block");
                        $("#4").css("display", "none");
                        $("#5").css("display", "none");
                        $("#6").css("display", "none");
                    }
                    if (val == '227') {
                        $("#1").css("display", "none");
                        $("#2").css("display", "none");
                        $("#3").css("display", "none");
                        $("#4").css("display", "block");
                        $("#5").css("display", "none");
                        $("#6").css("display", "none");
                    }
                    if (val == '228') {
                        $("#1").css("display", "none");
                        $("#2").css("display", "none");
                        $("#3").css("display", "none");
                        $("#4").css("display", "none");
                        $("#5").css("display", "block");
                        $("#6").css("display", "none");
                    }
                    if (val == '229') {
                        $("#1").css("display", "none");
                        $("#2").css("display", "none");
                        $("#3").css("display", "none");
                        $("#4").css("display", "none");
                        $("#5").css("display", "none");
                        $("#6").css("display", "block");
                    }
                } else {
                    $.messager.alert('提示',data.msg,'error');
                }
            }
        });
    }
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

    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        if (pid == '' || pid == undefined) {
            alert("请选中一个文件");
            return;
        }
        if (pid.length > 1) {
            alert("只能选中一个文件");
            return;
        }
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/ins/traInsenclosure/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
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
                url: '${base}/ins/traInsenclosure/removeFile/' + pids,
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
            url: '${base}/ins/traInsenclosure/uploadFile?businessType=0', //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            beforeSend:function(XMLHttpRequest){
                //alert('远程调用开始...');
                $("#loading").html("<p style=\"color: dodgerblue\">正在上传，请稍后操作...</p>");
            },
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

    //文件表数据
    function getData() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row
        });
    }

    function selectInvestmentType(value) {
        var investmentType = $('#investmentType').val();
        for (var i = 1; i < 7; i++) {
            if (investmentType == i) {
                $('#' + i).show();
            } else {
                $('#' + i).hide();
            }
        }
    }

    function getRelInfo() {
        var relNumber = $('#relNumber').val();
        var relName = $('#relName').val();
        var obj = {
            relNumber: relNumber,
            relName: relName
        };
        $.ajax({
            url: '${base}/ins/traFunds/getRelInfo',
            data: obj,
            type: 'post',
            jsonType: 'json',
            success: function (data) {
                if (data.success) {
                    var relName = data.relName;
                    var relType = data.relType;
                    var relId = data.relId;
                    var relNumber = data.relNumber;
                    var caliber = data.caliber;
                    var totalTradeAmount = data.totalTradeAmount;
                    $('#relName').val(relName);
                    $('#relNumber').val(relNumber);
                    $('#relId').val(relId);
                    $('#relType').val(relType);
                    $('#relType').select2({value: relType});
                    $('#caliber').val(caliber);
                    $('#caliber').select2({value: caliber});
                    $('#totolTradeAmount').val(totalTradeAmount);
                    $('#total').val(totalTradeAmount);
                } else {
                    alert(data.msg);
                }
            }
        });
    }

    //判断是否重大关联交易
    function isGreatTrade() {
        var relId = $('#relId').val();
        var relType = $('#relType').val();
        var tradeAmount = $('#tradeAmount').val();
        var totolTradeAmount = $('#totolTradeAmount').val();
        $('#totolTradeAmount').val(parseFloat(totolTradeAmount) + parseFloat(tradeAmount));
        if (${opt == 'edit'}) {
            var trade = $('#amount').val();
            totolTradeAmount = $('#total').val();
            $('#totolTradeAmount').val(parseFloat(totolTradeAmount) + (parseFloat(tradeAmount) - parseFloat(trade)));
        } else {
            var total = $('#total').val();
            $('#totolTradeAmount').val(parseFloat(total) + parseFloat(tradeAmount));
        }

        if (tradeAmount == '' || relNumber == null) {
            return;
        }
        if (relType == '' || relType == null) {
            return;
        }
        if (relId == '' || relId == null) {
            return;
        }
        var obj = {
            relId: relId,
            relType: relType,
            tradeAmount: tradeAmount
        };
        $.ajax({
            url: '${base}/ins/traFunds/GreatCheck',
            data: obj,
            type: 'post',
            jsonType: 'json',
            success: function (data) {
                if (data.success) {
                    $('#majorTrade').val(data.isGreatTrade);
                    $('#majorTrade').select2({value: (data.isGreatTrade)})
                    isConsiderType();
                } else {
                    alert(data.msg);
                }
            }
        });
    }
    //加上红点
    $(document).ready(function(){
        $(".required").each(function() {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>"+$this.html());
        });
    });
</script>
</body>
</html>
