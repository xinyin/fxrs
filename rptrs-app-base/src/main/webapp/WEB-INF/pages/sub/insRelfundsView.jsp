<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>与单一法人主体资金运用类关联交易信息报表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="与单一法人主体资金运用类关联交易统计表" subTitle="" col="12" collapsible="true">
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="生成报送文件日期：${date}" col="12" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="报送机构：复星保德信人寿保险有限公司" col="12" formControl="true"/>
                </bf:formGroup>
            </bf:formGroup>
            <bc:table id="traBusinessTable" idField="pid" url="${base}/sub/insRelfunds"  multipleVisible="false" queryUrl="viewHandler/${pid}">
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="relType" title="关联方类别" width="100" formatter="custom:sys/dictItem/getDictItems/Rel_POP"/>/
                <bc:column field="relName" title="关联方名称" width="100"/>/
                <bc:column field="relNumber" title="关联方证件号码" width="100"/>
                <bc:column field="corpInvestFund" title="资金运用投资余额（万元)" width="100"/>
<%--                <bc:column field="corpTotalFund" title="关联方总资产投资（万元)" width="100"/>--%>
            </bc:table>
            <bc:button  type="primary" text="返回" icon="arrow-circle-left">
                <be:event>
                    window.location = '${base}/sub/insRelfunds/list';
                </be:event>
            </bc:button>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>
