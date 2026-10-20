<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>TraCramcInfo</title>
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
		.form-group .select2 {
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
							<bc:label text="交易主体名称" col="5" formControl="true"/>
							<bf:text name="__LIKES_vcEntity" col="7" placeholder="交易主体名称"/>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="交易对手名称" col="5" formControl="true"/>
							<bf:text name="__LIKES_vcCounterparty" col="7" placeholder="交易对手名称"/>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="交易日期" col="5" formControl="true"/>
							<bf:datebox  name="__LIKES_dtradeDate" col="7">
							</bf:datebox>
						</bf:formGroup>
					</bf:formGroup>
				<bc:row cls="m-b">
					<div class="col-sm-12 col-sm-offset-5">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</div>
				</bc:row>
				</bc:row>
			</bf:form>
			<bc:table id="traCramcInfoTable" idField="pid" url="${base}/ins/traCramcInfo" queryUrl="query/${transNum}">
				<bc:toolbar id="relation" text="关联" type="button" handler="relation"/>
				<bc:toolbar id="removerel" text="撤销关联" type="button" handler="removerel"/>
				<bc:column field="pid" title="pid" width="100" visible="false"/>
				<bc:column field="frefcon" title="交易编号" width="100"/>
				<bc:column field="vcEntity" title="交易主主体名称" width="100"/>
				<bc:column field="vcCounterparty" title="交易对手名称" width="100"/>
				<bc:column field="vcName" title="证券名称" width="100"/>
				<bc:column field="vcTypeLevel1" title="资产分类" width="100"/>
				<bc:column field="famount" title="成交金额" width="100"/>
				<bc:column field="dtradeDate" title="交易日期" width="100"/>
			</bc:table>
			<bc:row cls="m-b">
				<div class="col-sm-12 col-sm-offset-5">
					<bc:button type="primary" text="返回"   click="back()" icon="arrow-circle-left">
					</bc:button>
				</div>
			</bc:row>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

	//点击关联按钮
	function relation(){
			var transNum
			var pid;
			$.messager.modal({
				winId: 'relationCramc',
				width: 700,
				height: 420,
				title: '实际交易信息关联',
				url: '${base}/ins/traCramcInfo/win',
				args: {
					result: function (obj) {
						pid = obj["pid"] + "";
						transNum = '${transNum}';
						$.ajax({
							type: "POSt",
							url: '${base}/ins/traCramcInfo/relationCramc',
							data: {
								transNum:transNum,
								pid:pid
							},
							success: function (data) {
								if (data.success) {
									$.messager.alert('提示', data.msg, 'info', function () {
										$('#btnReset').click();
									})
								} else {
									$.messager.alert('错误', data.msg, 'error');
								}
							}
						});

					}
				},
				onClose: function(){
				}
			});
		}

	function back(){
		javascript:history.back(-1);
	}


	function removerel() {
		var pid = getIdSelections();

		if (pid == '' || pid == null || pid.length != 1) {
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}

		$.messager.confirm('提示', '确定要撤销关联吗？', function (r) {
			if (r) {
				$.ajax({
					url: "${base}/ins/traCramcInfo/remove/" + pid,
					method: "GET",
					data: {"": ""},
					dataType: "JSON",
					traditional: true,
					success: function (data) {
						if (data.success) {
							$.messager.alert('提示', data.msg, 'info', function () {
								$('#btnReset').click();
							})
						} else {
							$.messager.alert('错误', data.msg, 'error');
						}
					}
				})
			}
		})
	}

	function getIdSelections() {
		return $.map($('#traCramcInfoTable').bootstrapTable('getSelections'), function (row) {
			return row.pid;
		});
	}
</script>
</body>
</html>