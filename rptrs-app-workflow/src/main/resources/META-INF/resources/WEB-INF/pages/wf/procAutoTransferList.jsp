<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>流程自动转办定义</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="自动转办设置" subTitle="请输入转办用户查询信息" col="12" collapsible="true">
<%--			<bf:form id="queryForm" validation="true">--%>
<%--				<bc:row cls="m-b">--%>
<%--					<bf:formGroup col="4">--%>
<%--						<div title="创建日期">--%>
<%--							<bc:label cls="required" text="创建日期" col="5" formControl="true"/>--%>
<%--							<bf:datebox id="createTime" name="createTime" col="7" value=""--%>
<%--										disabled="${opt == 'view'}">--%>
<%--								<bv:notEmpty/>--%>
<%--							</bf:datebox>--%>
<%--						</div>--%>
<%--					</bf:formGroup>--%>

<%--					<bc:col col="3" colOffset="1">--%>
<%--						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>--%>
<%--						<bc:button id="btnReset" text="重置" icon="refresh" click="frmReset()"/>--%>
<%--					</bc:col>--%>
<%--				</bc:row>--%>
<%--			</bf:form>--%>
			<h3>我的工作委托</h3>
			<bc:table id="procAutoTransferTable" idField="id" url="${base}/wf/procAutoTransfer">
				<bc:toolbar id="affirm" text="取消委托" type="button" handler="removeProc"/>
				<bc:column field="id" title="任务编号" width="100"/>
				<bc:column field="staffName" title="委托人" width="100"/>
				<bc:column field="staffDep" title="委托人所属部门" width="100"/>
				<bc:column field="transferStaffName" title="被委托人" width="100"/>
				<bc:column field="transferStaffDep" title="被委托人所属部门" width="100"/>
				<bc:column field="startTimeString" title="开始时间" width="100"/>
				<bc:column field="endTimeString" title="结束时间" width="100"/>
				<bc:column field="createTime" title="创建时间" width="100"/>
				<bc:column field="status" title="委托状态" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DelegateStatus"/>
<%--				<bc:operator id="view" icon="search" title="查看"/>--%>
			</bc:table>

			<h3>我被委托的工作委托</h3>
			<bc:table id="myprocAutoTransferTable" idField="approvalId" url="${base}/wf/myprepareQuery">
				<bc:column field="id" title="任务编号" width="100"/>
				<bc:column field="staffName" title="委托人" width="100"/>
				<bc:column field="staffDep" title="委托人所属部门" width="100"/>
				<bc:column field="transferStaffName" title="被委托人" width="100"/>
				<bc:column field="transferStaffDep" title="被委托人所属部门" width="100"/>
				<bc:column field="startTimeString" title="开始时间" width="100"/>
				<bc:column field="endTimeString" title="结束时间" width="100"/>
				<bc:column field="createTime" title="创建时间" width="100"/>
				<bc:column field="status" title="委托状态" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DelegateStatus"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>


<script type="text/javascript">
	//根据权限显示按钮
	$(function () {
		var code = "${code }";
		//确定
		if (code.search("037,") != -1) {
			$('#procAutoTransferTable_tb_affirm').show();
		}
		if (code.search("07,") != -1) {
			$('#procAutoTransferTable_tb_send').show();
		}
	})

	//取消委托
	function removeProc() {
		var id = getIdSelections();
		if (id == '' || id == null || id.length != 1) {
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
		var status = getStatusSelections();
		if (status != '1' &&  status != '0') {
			$.messager.alert('提示', '该状态数据不可取消', 'info');
			return false;
		}
		$.messager.confirm('提示', '确定要取消吗？', function (r) {
			if (r) {
				$.ajax({
					url: "${base}/wf/procAutoTransfer/cancel/" + id,
					method: "GET",
					dataType: "JSON",
					traditional: true,
					success: function (data) {
						if (data.success) {
							window.location = '${base}/wf/procAutoTransfer/list/';
						} else {
							$.messager.alert('错误', data.msg, 'error');
						}
					}
				})
			}
		})
	}


	/*获取状态*/
	function getStatusSelections() {
		return $.map($('#procAutoTransferTable').bootstrapTable('getSelections'), function (row) {
			return row.status;
		});
	}

	/*获取pid*/
	function getIdSelections() {
		return $.map($('#procAutoTransferTable').bootstrapTable('getSelections'), function (row) {
			return row.id;
		});
	}


	//刷新表格
	function loadCallback() {
		$('#procAutoTransferTable').jTable('refresh');
	}

	//重置
	function frmReset() {
		window.location = '${base}/wf/procAutoTransfer/list';
	}
</script>
</body>
</html>