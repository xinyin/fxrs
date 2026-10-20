<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>利益转移信息报表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="利益转移信息报表" subTitle="" col="12" collapsible="true">
            <bf:formGroup col="4">
                <bc:label text="生成报送文件日期：${date}" col="8" formControl="true"/>
                <bc:label text="报送机构：${subOrg}" col="5" formControl="true"/>
                <bc:label text="操作人：${operator}" col="5" formControl="true"/>
            </bf:formGroup>
            <bc:table id="traInterestTable" idField="pid" url="${base}/sub/insInterest" queryUrl="viewHandler/${pid}">
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="interestSeq" title="利益转移流水号" width="100" visible="false"/>
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
                <bc:column field="objectAssetCondition" title="标的资产情况" width="100" />
                <bc:column field="lastBargainPrice" title="前次交易价格" width="100" />
                <bc:column field="lastBargainTime" title="前次交易时间" width="100" />
                <bc:column field="objectCondition" title="标的情况" width="100" />
                <bc:column field="guaranteeCondition" title="担保情况" width="100" />
                <bc:column field="tradeOccur" title="交易发生原因及合同,证明文件等" width="100" />
                <bc:column field="isAgreement" title="是否统为统一交易协议" width="100" />
                <bc:column field="remark" title="其它说明事项" width="100" />
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>