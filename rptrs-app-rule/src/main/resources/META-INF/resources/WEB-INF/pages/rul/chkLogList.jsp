<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>规则历史记录表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="规则历史记录表" subTitle="请输入规则历史记录表查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="流水号" col="5" formControl="true"/>
						<bf:text name="__LIKES_serNum" col="7" placeholder="流水号"/>
					</bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="渠道编码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_channelCode" col="7" placeholder="渠道编码"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="业务结果" col="5" formControl="true"/>
                       <%-- <bf:text name="__LIKES_serNum" col="7" placeholder="流水号"/>--%>
                        <bf:select name="__LIKES_busCode"  col="4" dataSource="custom"  url="sys/dictItem/getDictItems/RUL_Credit" >
                        <bf:option text="ALL" value="%"></bf:option>
                        <be:event type="select">
                            var input = $("#busCode");
                            input.focus();
                            input.blur();
                        </be:event>
                        </bf:select>
                    </bf:formGroup>

                    <bf:formGroup col="4">
                        <bc:label text="交易结果" col="5" formControl="true"/>
                        <bf:select name="__LIKES_traCode"  col="4" dataSource="custom"  url="sys/dictItem/getDictItems/RUL_TranCode" >
                        <bf:option text="ALL" value="%"></bf:option>
                        <be:event type="select">
                            var input = $("#traCode");
                            input.focus();
                            input.blur();
                        </be:event>
                        </bf:select>
                    </bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
            <bc:table selectFirst="false"   id="chkLogCopyTable"   idField="id" url="${base}/rul/chkLog" >
			<%--	<bc:toolbar id="add" text="新增" type="button"/>--%>
				<bc:column field="id" title="编号" width="100" visible="false" />
				<bc:column field="serNum" title="流水号" width="100"/>
                <bc:column field="channelCode" title="渠道编码" width="100"/>
                <bc:column field="busCode" title="业务结果" width="100" formatter="custom:sys/dictItem/getDictItems/RUL_Credit"/>
                <bc:column field="traCode" title="交易结果" width="100" formatter="custom:sys/dictItem/getDictItems/RUL_TranCode"/>
				<bc:column field="ruleId" title="校验规则" width="100"/>
				<%--<bc:column field="traType" title="交易类型" width="100"/>
				<bc:column field="param1" title="交易参数1" width="100"/>
				<bc:column field="param2" title="交易参数2" width="100"/>
				<bc:operator id="view" icon="search" title="查看"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>--%>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>

<script type="text/html">
    $("#chkLogCopyTable").bootstrapTable('hideColumn','column')
</script>
</body>
</html>