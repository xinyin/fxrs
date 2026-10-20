<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp" %>
	<br:css pack="list"/>
	<title>DelRel</title>
	<script>
		//重置
		function frmReset() {

			window.location = '${base}/bus/delRelOrg/list';
		}
	</script>
	<style>
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
					<bf:formGroup col="4">
						<bc:label text="关联方名称" col="4" formControl="true"/>
						<bf:text name="__LIKES_name" col="8" placeholder="关联方名称"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="证件类型" col="4" formControl="true"/>
						<bf:select name="__LIKES_identifyType" col="8" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-IdentifyType">
							<bf:option text="ALL" value="%"></bf:option>
							<be:event type="select">
								var input = $("#__LIKES_identifyType");
								input.focus();
								input.blur();
							</be:event>
						</bf:select>

					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text name="__LIKES_identifyNumber" col="8" placeholder="证件号码"/>
					</bf:formGroup>

					<bf:formGroup col="4">
						<bc:label text="机构名称" col="4" formControl="true"/>
						<bf:select name="__LIKES_mgerOrg" col="8" dataSource="custom"
								   url="sys/unit/selectUnit">
							<bf:option text="ALL" value=""></bf:option>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="解除口径" col="4" formControl="true"/>
						<bf:select name="__LIKES_regulator" col="8" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-Caliber">
							<bf:option text="ALL" value=""></bf:option>
						</bf:select>
					</bf:formGroup>
					<%--<bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
                    </bc:col>--%>
				</bc:row>
				<bc:row cls="m-b">
					<div class="col-sm-12 col-sm-offset-5">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					</div>
				</bc:row>
			</bf:form>
			<bc:table id="delRelTable" idField="pid" url="${base}/bus/delRelOrg">
				<%--				<bc:toolbar id="add" text="新增" type="button"/>--%>
				<bc:column field="pid" title="pid" width="100" visible="false"/>
				<bc:column field="name" title="姓名" width="100"/>
				<bc:column field="domesticOrForeign" title="境内外标识" width="100"
						   formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
				<bc:column field="mgerOrg" title="机构名称" width="100"
						   formatter="custom:sys/unit/selectUnit"/>
				<bc:column field="identifyType" title="证件类型" width="100"
						   formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
				<bc:column field="identifyNumber" title="证件号码" width="100"/>
				<bc:column field="regulator" title="解除口径" width="100"
						   formatter="custom:sys/dictItem/getDictItems/BUS-regulator"/>
				<bc:operator id="view" icon="search" title="查看"/>
				<%--				<bc:operator id="edit" icon="edit" title="编辑"/>--%>
				<%--				<bc:operator id="remove" icon="trash" title="移除"/>--%>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

	$(document).ready(function () {

	});

	function getAll() {
		alert('aaa');

	}


</script>
</body>
</html>