<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <br:css pack="list"/>
    <title>关联交易表（保险）-资金运用类</title>
    <style>
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
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="关联方名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_relName" col="7" placeholder="关联方名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="关联方类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_relType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-relType">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_relType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件号码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_relNumber" col="7" placeholder="证件号码"/>
                    </bf:formGroup>
                    </bf:formGroup>
                    <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="状态" col="5" formControl="true"/>
                        <bf:select name="__LIKES_status" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Applystatus">
                            <bf:option text="ALL" value=""></bf:option>

                            <be:event type="select">
                                var input = $("#__LIKES_status");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="业务类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_busType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INT-BusinessType">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_busType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="口径" col="5" formControl="true"/>
                        <bf:select name="__LIKES_caliber" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Caliber" value="A">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_caliber");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    </bf:formGroup>
                    <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                        <bf:select name="__LIKES_majorTrade" col="7" dataSource="fixedDict"
                                   url="IsStatusDict">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_majorTrade");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="交易机构" col="5" formControl="true"/>
                        <bf:select name="__LIKES_tradeOrg" col="7" dataSource="custom"
                                   url="sys/unit/selectUnit">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_tradeOrg");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="数据来源" col="5" formControl="true"/>
                        <bf:select name="__LIKES_dataSources" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-DataSource">
                            <bf:option text="ALL" value="%"></bf:option>
                        </bf:select>
                    </bf:formGroup>
                    </bf:formGroup>
                    <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="交易定价是否为国家规定" col="5" formControl="true"/>
                        <bf:select name="__LIKES_isDealPrice" col="7" dataSource="fixedDict" url="IsStatusDict">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_isDealPrice");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="交易开始时间" col="5" formControl="true"/>
                        <bf:datebox id="startDate" name="__GES_tranDate" col="7" format="date"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="交易截止时间" col="5" formControl="true"/>
                        <bf:datebox id="endDate" name="__LTS_tranDate" col="7" format="date"/>
                    </bf:formGroup>
                    </bf:formGroup>
<%--                    <bc:col col="3" colOffset="5">--%>
<%--                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>--%>
<%--                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>--%>
<%--                    </bc:col>--%>
                    <bc:row cls="m-b">
                        <div class="col-sm-12 col-sm-offset-5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                        </div>

                    </bc:row>
                </bc:row>
            </bf:form>
            <bc:table id="filFundsTable" idField="pid" selectFirst="false" multiple="false" url="${base}/ins/filFunds" queryUrl="queryPidsByConditions/${pids}">
                <bc:toolbar id="" text="查看" type="button" handler="viewFilFunds"/>
                <bc:toolbar id="compared" text="查看变更" handler="compared" type="button"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="filNum" title="备案编号" width="100"/>
                <bc:column field="relId" title="关联方ID" width="100" visible="false"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="tradeOrg" title="交易机构" width="100"
                           formatter="custom:sys/unit/selectUnit"/>
                <bc:column field="relType" title="关联方类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-RelatedPartiesType"/>
                <bc:column field="relNumber" title="证件号码" width="100"/>
                <bc:column field="tranType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
                <bc:column field="tradeAmount" title="交易金额（万元）" width="100"/>
                <bc:column field="majorTrade" title="是否重大关联交易" width="100"
                           formatter="fixedDict:IsStatusDict"/>
                <bc:column field="isDealPrice" title="交易定价是否为国家规定" width="100"
                           formatter="fixedDict:IsStatusDict"/>
                <bc:column field="caliber" title="统计口径" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Caliber"/>
                <bc:column field="dataSources" title="数据来源" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DataSource"/>
                <bc:column field="status" title="状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="statusComment" title="状态说明" width="100"/>
                <bc:column field="tranDateString" title="交易日期" width="100" visible="false"/>
                <bc:column field="totolTradeAmount" title="本年度与该关联方发生的关联交易累计金额（万元）" width="100" visible="false"/>
                <bc:column field="contractNo" title="合同编号" width="100" visible="false"/>
                <bc:column field="busStartTimeString" title="业务起始日期" width="100" visible="false"/>
                <bc:column field="busEndTimeString" title="业务终止日期" width="100" visible="false"/>
                <bc:column field="investmentType" title="投资类别" width="100" visible="false"/>
                <bc:column field="investmentMethod" title="投资方式" width="100" visible="false"/>
                <bc:column field="investedEnterprises" title="被投资企业情况" width="100" visible="false"/>
                <bc:column field="lastTradePrice" title="前次交易价格" width="100" visible="false"/>
                <bc:column field="subjectMatter" title="标的情况" width="100" visible="false"/>
                <bc:column field="shareDebtRatio" title="股债比" width="100" visible="false"/>
                <bc:column field="financialAssstsType" title="金融资产类别" width="100" visible="false"/>
                <bc:column field="financialAssstsNum" title="金融资产编号" width="100" visible="false"/>
                <bc:column field="financialProScale" title="金融产品发行规模" width="100" visible="false"/>
                <bc:column field="financialProRatio" title="购买金融产品份额占比（%）" width="100" visible="false"/>
                <bc:column field="underlyingAsset" title="底层资产" width="100" visible="false"/>
                <bc:column field="underIsRel" title="底层资产是否为关联方" width="100" visible="false"/>
                <bc:column field="underInvolveRel" title="底层基础资产是否涉及控股股东或控股股东的关联方" width="100" visible="false"/>
                <bc:column field="underRel" title="底层资产关联关系" width="100" visible="false"/>
                <bc:column field="investedEnterprisesName" title="被投企业名称" width="100" visible="false"/>
                <bc:column field="busModel" title="业务模式" width="100" visible="false"/>
                <bc:column field="earningsExpectaions" title="盈利预期" width="100" visible="false"/>
                <bc:column field="investmentRatio" title="投资比例（%）" width="100" visible="false"/>
                <bc:column field="investmentAllocation" title="投资收益分配情况" width="100" visible="false"/>
                <bc:column field="lossShare" title="损失分担情况" width="100" visible="false"/>
                <bc:column field="tradePrice" title="交易价格" width="100" visible="false"/>
                <bc:column field="pricingPolicy" title="定价政策" width="100" visible="false"/>
                <bc:column field="tradeSubject" title="交易标的" width="100" visible="false"/>
                <bc:column field="remark" title="其他事项说明" width="100" visible="false"/>
                <bc:column field="transNum" title="交易编号" width="100" visible="false"/>
<%--                <bc:operator id="view" icon="search" title="查看"/>--%>
                <%--                <bc:operator id="edit" icon="edit" title="编辑"/>--%>
                <%--                <bc:operator id="remove" icon="trash" title="移除"/>--%>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    <%--function viewFilFunds() {--%>
    <%--    var pid = getIdSelections();--%>
    <%--    alert(pid)--%>
    <%--    window.location = '${base}/ins/filFunds/view/'+pid;--%>
    <%--}--%>
    /*查看当前数据并判断选中个数*/
    function viewFilFunds() {
        var pid = getIdSelections();
        if(pid.length==1){
            window.location = '${base}/ins/filFunds/view/'+pid;
        }else{
            $.messager.alert('提示', '请选择一个关联方。', 'info');
            return "";
        }
    }
    function compared() {
        var pid = getIdSelections();
        if(pid.length==1){
            window.location = '${base}/ins/filFunds/compared/' + pid+'?';
        }else {
            $.messager.alert('提示', '请选择一个关联方。', 'info');
            return "";
        }
    }




    /*
         * 重置
         * */
    function frmReset(){
        window.location = '${base}/ins/filFunds/list';
    }
    function getStatusSelections() {
        return $.map($('#filFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    function getIdSelections() {
        return $.map($('#filFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    function getDataSourcesSelections() {
        return $.map($('#filFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.dataSources;
        });
    }


</script>
</body>
</html>