<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>待生成报送文件数据</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
<div class="row">
<bc:ibox title="待生成报送文件数据" subTitle="" col="12" collapsible="true">
    <bf:form id="queryForm" validation="true">
        <bc:row cls="m-b">
            <bf:formGroup col="4">
                <bc:label text="报送机构" col="5" formControl="true"/>
                <bf:text id="subOrg" name="subOrg" col="7"
                         value="${subOrg}"
                         disabled="true">
                </bf:text>
            </bf:formGroup>
        </bc:row>
    </bf:form>
    <bc:table id="subTraFundsTable" idField="pid" url="" queryUrl="">
        <bc:toolbar id="subFile" text="生成报送文件" type="button" />
        <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
        <bc:column field="relName" title="关联方名称" width="100" />
        <bc:column field="relCode" title="关联方代码" width="100" />
        <bc:column field="relType" title="关联方类别" width="100"
                   formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
        <bc:column field="tradeType" title="交易类型" width="100" />
        <bc:column field="bussinesType" title="业务类型" width="100" />
        <bc:column field="contractNum" title="合同编号" width="100" />
        <bc:column field="tradeAmount" title="交易金额(万元/人民币)" width="100" />
        <bc:column field="verifyResult" title="数据校验结果" width="100" />
        <bc:column field="subStatus" title="报送文件状态" width="100" />
    </bc:table>
</bc:ibox>
</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>