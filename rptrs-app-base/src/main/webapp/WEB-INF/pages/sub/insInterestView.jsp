<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>利益转移信息报表</title>
    <style>
        table{
            table-layout: fixed;
        }
        td{
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="利益转移信息报表" subTitle="" col="12" collapsible="true">
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="生成报送文件日期：${date}" col="12" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="报送机构：复星保德信人寿保险有限公司" col="12" formControl="true"/>
                </bf:formGroup>
            </bf:formGroup>
            <bc:table id="traInterestTable" idField="pid" url="${base}/sub/insInterest" queryUrl="viewHandler/${pid}" multipleVisible="false">
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="tranType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
                <bc:column field="busType" title="业务类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INT-BusinessTypes"/>
                <bc:column field="contractNo" title="合同编号" width="100" />
                <bc:column field="tranDate" title="合同签订日期" width="150" />
                <bc:column field="busStartTime" title="业务起始日期" width="150" />
                <bc:column field="busEndTime" title="业务结束日期" width="150" />
                <bc:column field="tradeAmount" title="交易金额（万元）" width="150" />
                <bc:column field="tradingDirection" title="交易方向" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-Tra-Directions"/>
                <bc:column field="tradePrice" title="交易价格" width="200" />
                <bc:column field="pricingPolicy" title="定价政策" width="200" />
                <bc:column field="tranName" title="交易概述" width="200" />
                <bc:column field="tradeSubject" title="交易标的" width="200" />
                <bc:column field="relType" title="关联方类别" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="relName" title="关联方名称" width="100" />
                <bc:column field="relNumber" title="关联方代码" width="100" />
                <bc:column field="singleRelatedTranType" title="单笔关联交易类型" width="150"
                           formatter="custom:sys/dictItem/getDictItems/INS-SingleRelatedTranType"/>
                <bc:column field="majorType" title="达到重大关联交易的情形" width="200"
                           formatter="custom:sys/dictItem/getDictItems/INS-majorType"/>
                <bc:column field="guaranteeConditions" title="担保条件" width="200"/>
                <bc:column field="remark" title="其他事项说明" width="200" />
            </bc:table>
            <bc:button  type="primary" text="返回" icon="arrow-circle-left">
                <be:event>
                    window.location = '${base}/sub/sublog/traInterestList';
                </be:event>
            </bc:button>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>