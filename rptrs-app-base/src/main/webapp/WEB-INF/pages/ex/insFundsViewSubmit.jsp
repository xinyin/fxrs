<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>资金运用信息报表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="资金运用信息报表" subTitle="" col="12" collapsible="true">
            <bf:formGroup col="4">
                <bc:label text="生成报送文件日期：${date}" col="8" formControl="true"/>
                <bc:label text="报送机构：${subOrg}" col="5" formControl="true"/>
                <bc:label text="操作人：${operator}" col="5" formControl="true"/>
            </bf:formGroup>
            <bc:table id="traFundsTable" idField="pid" url="${base}/sub/insFunds" queryUrl="viewHandler/${pid}">
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="fundsSeq" title="资金运用流水号" width="100" visible="false"/>
                <bc:column field="relName" title="关联方名称" width="100" />
                <bc:column field="relNumber" title="关联方代码" width="100" />
                <bc:column field="relType" title="关联方类别" width="100" />
                <bc:column field="tranType" title="交易类型" width="100" />
                <bc:column field="busType" title="业务类型" width="100" />
                <bc:column field="contractNo" title="合同/交易编号" width="100" />
                <bc:column field="contractTime" title="合同/交易发生日期" width="100" />
                <bc:column field="busStartTime" title="业务起始日期" width="100" />
                <bc:column field="busEndTime" title="业务结束日期" width="100" />
                <bc:column field="tradeAmount" title="交易金额(万元/人民币)" width="100" />
                <bc:column field="tradePrice" title="交易价格" width="100" />
                <bc:column field="pricingPolicy" title="定价政策" width="100" />
                <bc:column field="tradeSubject" title="交易标的" width="100" />
                <bc:column field="totolTradeAmount" title="本年度与该关联方发生的关联交易累计金额(万元/人民币)" width="100" />
                <bc:column field="isOverseasInvestment" title="是否境外投资" width="100" />
                <bc:column field="investedEnterprises" title="被投资企业情况" width="100" />
                <bc:column field="imLastTradePrice" title="不动产前次交易价格" width="100" />
                <bc:column field="imSubjectMatter" title="不动产标的情况" width="100" />
                <bc:column field="investmentMethod" title="投资方式" width="100" />
                <bc:column field="shareDebtRatio" title="债股比(%)" width="100" />
                <bc:column field="productName" title="金融产品名称" width="100" />
                <bc:column field="register" title="产品批准/注册/备案号" width="100" />
                <bc:column field="financialProScale" title="金融产品发行规模" width="100" />
                <bc:column field="investProductRate" title="投资金融产品份额占比(%)" width="100" />
                <bc:column field="isUnderlyAssetRel" title="底层资产是否涉及关联方" width="100" />
                <bc:column field="underlyAssetDescribe" title="底层资产具体描述" width="100" />
                <bc:column field="underInvolveRel" title="底层基础资产是否涉及控股股东或控股股东的关联方" width="100" />
                <bc:column field="underRel" title="底层资产关联关系" width="100" />
                <bc:column field="investmentProportion" title="投资比例(%)" width="100" />
                <bc:column field="investedEnterprisesName" title="被投企业名称" width="100" />
                <bc:column field="busModel" title="业务模式" width="100" />
                <bc:column field="earningsExpectaions" title="盈利预期" width="100" />
                <bc:column field="investmentAllocation" title="投资收益分配情况" width="100" />
                <bc:column field="lossShare" title="损失分担情况" width="100" />
                <bc:column field="remark" title="其它说明事项" width="100" />
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>