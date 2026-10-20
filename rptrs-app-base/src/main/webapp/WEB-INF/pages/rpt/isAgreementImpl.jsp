<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>ReportAnnual</title>
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
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="统计分析" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="关联方名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_relName" col="7" placeholder="关联方名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="关联方证件号码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_relNumber" col="7" placeholder="关联方证件号码"/>
                    </bf:formGroup>
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" cls="col-sm-offset-1" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="traInterestTable" idField="pid" url="${base}/bus/protocol" queryUrl="isAgreementImpl">
                <bc:toolbar id="out" text="导出" type="button" handler="out"/>
                <bc:column field="contractDateStr" title="签订日期" width="100"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="relType" title="关联方类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="relNumber" title="关联方证件号码" width="100"/>
                <bc:column field="tranType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
                <bc:column field="busType" title="业务类型" width="100"/>
                <bc:column field="contractNo" title="合同编号" width="100"/>
                <bc:column field="agreementNum" title="协议编号" width="100"/>
                <bc:column field="contractAmount" title="合同金额(万元)" width="100"/>
                <bc:column field="pracTradeAmount" title="实际交易金额(万元)" width="100"/>
                <bc:column field="rat" title="实际交易金额占比(%)" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    var opt = '${opt}';
    $(document).ready(function () {
        if (opt == 'list') {
            $("#reback").css("display", "none");
        }
    })

    //导出
    function out() {
        var relName = $("[name='relName']").val();
        var relNumber = $("[name='relNumber']").val();
        var data = {
            "relName": relName,
            "relNumber": relNumber
        }
        //console.log(JSON.stringify(data))
        var url = '${base}/sys/xlsie/IsAgreementImplExportCallBackImpl/' + JSON.stringify(data);
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    function getTraSubName() {
        return $("[name='traSubName']").val();
    }

    //获取表单选择的交易编号
    function getIdSelections() {
        return $.map($('#traInfo').bootstrapTable('getSelections'), function (row) {
            return row.transNum;
        });
    }

    //重置
    function frmReset() {
        window.location = '${base}/report/design/isAgreementImpl';
    }

    $("#year").datetimepicker({
        format: "yyyy",
        autoclose: true,
        startView: 4,
        minView: 4,
        todayHighlight: true,
    });
</script>
</body>
</html>