<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>提供服务类信息报表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="其他类信息报表" subTitle="" col="12" collapsible="true">
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="生成报送文件日期：${date}" col="12" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="报送机构：复星保德信人寿保险有限公司" col="12" formControl="true"/>
                </bf:formGroup>
            </bf:formGroup>
            <bc:table id="traOtherTable" idField="pid" url="${base}/sub/insOther" queryUrl="viewHandler/${pid}" multipleVisible="false">
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="tranType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
                <bc:column field="busType" title="业务类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-otherType"/>
                <bc:column field="contractNo" title="合同编号" width="100" />
                <bc:column field="tranDate" title="合同签署日期" width="100" />
                <bc:column field="busStartTime" title="业务起始日期" width="100" />
                <bc:column field="busEndTime" title="业务结束日期" width="100" />
                <bc:column field="tradeAmount" title="关联交易金额（万元/人民币）" width="100" />
                <bc:column field="tradePrice" title="交易价格" width="100" />
                <bc:column field="pricingPolicy" title="定价政策" width="100" />
                <bc:column field="financialImpact" title="对财务状况及经营成果的影响" width="100" />
                <bc:column field="tradeSubject" title="交易标的" width="100" />
                <bc:column field="relType" title="关联方类别" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="relName" title="关联方名称" width="100" />
                <bc:column field="relNumber" title="关联方代码" width="100" />
                <bc:column field="majorType" title="达到重大关联交易的情形" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-majorType"/>
                <bc:column field="totolTradeAmount" title="本年度与该关联方发生的关联交易累计金额（万元/人民币）" width="100" />
<%--                <bc:column field="isTradeAgreement" title="是否为统一交易协议" width="100"--%>
<%--                           formatter="fixedDict:IsStatusDict"/>--%>
                <bc:column field="isHoldingSubsidiary" title="是否为控股子公司" width="100"
                           formatter="fixedDict:IsStatusDict"/>
                <bc:column field="holdingSubsidiaryName" title="控股子公司全称" width="100" />
                <bc:column field="holdingSubsidiaryNumber" title="控股子公司证件号码" width="100" />
                <bc:column field="remark" title="其他事项说明" width="100" />
            </bc:table>
            <bc:button  type="primary" text="返回" icon="arrow-circle-left">
                <be:event>
                    window.location = '${base}/sub/sublog/traOtherList';
                </be:event>
            </bc:button>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>