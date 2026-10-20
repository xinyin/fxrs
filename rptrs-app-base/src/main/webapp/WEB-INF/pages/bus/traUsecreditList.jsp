<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>关联交易表-用信类</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="关联方名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_relatedName" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="关联方类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_relType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelatedPartiesType">
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
                        <bf:text name="__LIKES_identNumber" col="7" placeholder=""/>
                    </bf:formGroup>
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
                        <bc:label text="银行内部审批编号" col="5" formControl="true"/>
                        <bf:text name="__LIKES_internalApplyNo" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="业务类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_businessType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-UseCredit">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_businessType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="交易机构" col="5" formControl="true"/>
                        <bf:select name="__EQS_manageOrg" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-GHOrg">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__EQS_manageOrg");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="用信编号" col="5" formControl="true"/>
                        <bf:text name="__LIKES_useCreditNum" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="数据来源" col="5" formControl="true"/>
                        <bf:select name="__LIKES_dataSources" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-DataSources">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_dataSources");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <%-- <bc:col col="3" colOffset="1">
                         <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                         <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="refresh()"/>
                     </bc:col>--%>
                </bc:row>
                <div class="col-sm-10 col-sm-offset-5">
                    <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                    <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                </div>

            </bf:form>
            <bc:table id="traUsecreditTable" idField="pid" url="${base}/bus/traUsecredit">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="export" text="导出" type="button" handler="exportHandler"/>
                <bc:toolbar id="task" text="任务下发" type="button" handler="taskHandler"/>
                <bc:toolbar id="sub" text="提交审批" type="button" handler="subHandler"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="relId" title="关联方id" width="100" visible="false"/>
                <bc:column field="relatedName" title="关联方名称" width="100"/>
                <bc:column field="relType" title="关联方类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-RelatedPartiesType"/>
                <bc:column field="identifyType" title="证件类型" width="100" visible="false"/>
                <bc:column field="identNumber" title="证件号码" width="100"/>
                <bc:column field="internalApplyNo" title="银行内部审批编号" width="100"/>
                <bc:column field="useCreditNum" title="用信编号" width="100"/>
                <bc:column field="businessType" title="业务类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-UseCredit"/>
                <bc:column field="manageOrg" title="交易机构" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-GHOrg"/>
                <bc:column field="contractSignDate" title="合同签署日期" width="100" visible="false"/>
                <bc:column field="businessStartTime" title="业务起始日期" width="100"/>
                <bc:column field="businessEndTime" title="业务终止日期" width="100"/>
                <bc:column field="thisTraAmount" title="本笔用信金额" width="100"/>
                <bc:column field="thisTraBalance" title="本笔交易余额" width="100"/>
                <bc:column field="traPriceType" title="交易价格类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-traPrice"/>
                <bc:column field="floatRateDiscountRate" title="浮动利率折扣率" width="100" visible="false"/>
                <bc:column field="traPrice" title="交易价格" width="100"/>
                <bc:column field="dataSources" title="数据来源" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DataSources"/>
                <bc:column field="status" title="状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="statusComment" title="状态说明" width="100"/>
                <bc:column field="floatRateBench" title="浮动利率基准" width="100" visible="false"/>
                <bc:column field="floatInterestRateSpread" title="浮动利率利差" width="100" visible="false"/>
                <bc:column field="rateType" title="利率类型" width="100" visible="false"/>
                <bc:column field="thisTraDiff" title="本笔交易差额" width="100" visible="false"/>
                <bc:column field="productRegNum" title="产品批注/注册/备案号" width="100" visible="false"/>
                <bc:column field="financialProduct" title="金融产品名称" width="100" visible="false"/>
                <bc:column field="capitalInvestment" title="资金投向" width="100" visible="false"/>
                <bc:column field="isEffective" title="是否生效" width="100" visible="false"/>
                <bc:column field="investmentAmount" title="投资关联方金额" width="100" visible="false"/>
                <bc:column field="pricingStrategy" title="是否生效" width="100" visible="false"/>
                <bc:column field="fundsUse" title="资金用途" width="100" visible="false"/>
                <bc:column field="targetsInvestmentDesc" title="投资标的的描述" width="100" visible="false"/>
                <bc:column field="remark" title="其他说明事项" width="100" visible="false"/>
                <bc:column field="transNum" title="交易编号" width="100" visible="false"/>
                <bc:column field="tranType" title="交易类型" width="100" visible="false"/>

                <bc:operator id="view" icon="search" title="查看"/>
                <%--                <bc:operator id="edit" icon="edit" title="编辑"/>--%>
                <%--                <bc:operator id="remove" icon="trash" title="移除"/>--%>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script>
    function refresh() {
        window.location = '${base}/bus/traUsecredit/list';
    }

    function removeHandler() {
        var status = getStatusSelections();
        if ('4' == status) {
            alert('数据正在审批中');
            return false;
        }
        if ('6' == status) {
            alert('数据已审批通过');
            return false;
        }
        if (confirm("确认删除?")) {
            var pid = getIdSelections();
            $.ajax({
                type: 'post',
                url: '${base}/bus/traUsecredit/remove/' + pid,
                data: 'json',
                success: function (data) {
                    if (data.success) {
                        alert(data.msg);
                        window.location = '${base}/bus/traUsecredit/list';
                    }
                }
            });
        }
    }

    function getIdSelections() {
        return $.map($('#traUsecreditTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;//返回数据行中的id值
        });
    }

    function getStatusSelections() {
        return $.map($('#traUsecreditTable').bootstrapTable('getSelections'), function (row) {
            return row.status;//返回数据行中的id值
        });
    }

    function editHandler() {
        if ('4' == status) {
            alert('数据正在审批中');
            return false;
        }
        var pid = getIdSelections();
        window.location = '${base}/bus/traUsecredit/edit/' + pid;
    }

    function subHandler() {
        var pid = getIdSelections();
        window.location = '${base}/bus/traUsecredit/submit/' + pid;
    }
</script>
</body>
</html>