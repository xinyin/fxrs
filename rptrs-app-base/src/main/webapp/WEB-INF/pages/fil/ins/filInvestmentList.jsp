<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="list"/>
	<style type="text/css">

		#traInvestmentTable_tb_add,#traInvestmentTable_tb_edit,
		#traInvestmentTable_tb_remove,#traInvestmentTable_tb_import,
		#traInvestmentTable_tb_issued,#traInvestmentTable_tb_downTmp,
		#traInvestmentTable_tb_approve,#traInvestmentTable_tb_export {
			display: none;
		}
	</style>
	<title>关联交易表（保险）-投资入股类</title>
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
<%--					<bc:col col="3" colOffset="5">--%>
<%--						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>--%>
<%--						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>--%>
<%--					</bc:col>--%>
					<bc:row cls="m-b">
						<div class="col-sm-12 col-sm-offset-5">
							<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
							<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
						</div>

					</bc:row>
				</bc:row>
			</bf:form>
			<bc:table id="filInvestmentTable" selectFirst="false" idField="pid" multiple="false" url="${base}/ins/filInvestment" queryUrl="queryPidsByConditions/${pids}">
<%--				<bc:toolbar id="add" text="新增" type="button"/>--%>
				<bc:toolbar id="" text="查看" type="button" handler="viewFilInvestment"/>
				<bc:toolbar id="compared" text="查看变更" handler="compared" type="button"/>
				<bc:column field="pid" title="主键" width="100" visible="false"/>
				<bc:column field="filNum" title="备案编号" width="100" />
				<bc:column field="relId" title="关联方ID" width="100" visible="false"/>
				<bc:column field="relName" title="关联方名称" width="100"/>
				<bc:column field="tradeOrg" title="交易机构" width="100" formatter="custom:sys/unit/selectUnit"/>
				<bc:column field="relType" title="关联方类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
				<bc:column field="relNumber" title="证件号码" width="100"/>
				<bc:column field="tranType" title="交易类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
				<bc:column field="tradeAmount" title="交易金额（万元）" width="100"/>
				<bc:column field="majorTrade" title="是否重大关联交易" width="100" formatter="fixedDict:IsStatusDict"/>
				<bc:column field="isCashSubscript" title="是否现金认购" width="100" formatter="fixedDict:IsStatusDict"/>
				<bc:column field="caliber" title="统计口径" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Caliber"/>
				<bc:column field="dataSources" title="数据来源" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DataSource"/>
				<bc:column field="isDealPrice" title="交易定价是否为国家规定" width="100" formatter="fixedDict:IsStatusDict"/>
				<bc:column field="status" title="状态" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
				<bc:column field="statusComment" title="状态说明" width="100"/>
				<bc:column field="tranDateString" title="交易日期" width="100" visible="false"/>
<%--				<bc:operator id="view" icon="search" title="查看"/>--%>
				<%--<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>--%>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

	// viewFilInvestment
	<%--function viewFilInvestment() {--%>
	<%--	var pid = getIdSelections();--%>
	<%--	alert(pid)--%>
	<%--	window.location = '${base}/ins/filInvestment/view/'+pid;--%>
	<%--}--%>
	/*查看当前数据并判断选中个数*/
	function viewFilInvestment() {
		var pid = getIdSelections();
		if(pid.length==1){
			window.location = '${base}/ins/filInvestment/view/'+pid;
		}else{
			$.messager.alert('提示', '请选择一个关联方。', 'info');
			return "";
		}
	}
	function compared() {
		var pid = getIdSelections();
		if(pid.length==1){
			window.location = '${base}/ins/filInvestment/compared/' + pid+'?';
		}else {
			$.messager.alert('提示', '请选择一个关联方。', 'info');
			return "";
		}
	}
	/*
     * 重置
     * */
	function frmReset(){
		window.location = '${base}/ins/filInvestment/list';
	}
    function getRowSelections() {
        return $.map($('#filInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row;
        });
    }

    function getStatusSelections() {
        return $.map($('#filInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    function getIdSelections() {
        return $.map($('#filInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
</script>
</body>
</html>