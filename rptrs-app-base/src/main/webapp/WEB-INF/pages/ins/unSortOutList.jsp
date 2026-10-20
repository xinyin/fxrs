<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
	<style type="text/css">
		#unSortOutTable_tb_addType, #unSortOutTable_tb_remove
		{
			display: none;

		}
	</style>
<title>UnSortOut</title>
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
							<bc:label text="采集日期" col="5" formControl="true"/>
							<bf:datebox id="collectionDate" name="__LIKES_collectionDate" col="7" format="date"/>
						</bf:formGroup>
					</bf:formGroup>
					<bc:row cls="m-b">
						<div class="col-sm-12 col-sm-offset-5">
							<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
							<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
						</div>
					</bc:row>
				</bc:row>
			</bf:form>
			<bc:table id="unSortOutTable" idField="pid" url="${base}/ins/traUnSortOut"   selectFirst="true">
				<bc:toolbar id="addType" text="交易类型确认" type="button" handler="addType"/>
				<bc:toolbar id="remove" text="删除" type="button" handler="removeDoc"/>
				<bc:column field="relName" title="关联方名称" width="100"/>
				<bc:column field="relNumber" title="关联方证件号码" width="100"/>
				<bc:column field="relTradeAmount" title="关联交易金额（万元）" width="100"/>
				<bc:column field="collectionDate" title="采集时间" width="100"/>
				<bc:column field="sourceSystem" title="来源系统" width="100"/>
				<bc:column field="sourceModule" title="来源模块" width="100"/>
				<bc:column field="uniqueCode" title="来源系统唯一编码" width="100"/>
				<bc:column field="scene" title="场景" width="100"/>

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
		if (code.search("041,") != -1) {
			$('#unSortOutTable_tb_addType').show();
		}
		if (code.search("03,") != -1) {
			$('#unSortOutTable_tb_remove').show();
		}
	})

	//确认交易类型
	function addType() {
		var pids = getIdSelections();
		var selectRows = getselectData();
		if (selectRows.length == 0) {
			alert("请先选中数据");
			return;
		}
		window.location = '${base}/ins/traUnSortOut/add/'+pids;
	}

	function getData() {
		return $.map($('#unSortOutTable').bootstrapTable('getData'), function (row) {
			return row
		});
	}
	function getIdSelections() {
		return $.map($('#unSortOutTable').bootstrapTable('getSelections'), function (row) {
			return row.pid//返回数据行中的id值
		});
	}
	//文件表所有选中的数据
	function getselectData() {
		return $.map($('#unSortOutTable').bootstrapTable('getSelections'), function (row) {
			return row//返回数据行中的id值
		});
	}

	//删除
	removeDoc = function () {
		//删除前数据
		var rows = getData();
		//删除数据主键
		var pids = getIdSelections();
		//选中的数据
		var selectRows = getselectData();
		if (selectRows.length == 0) {
			alert("请先选中数据");
			return;
		}
		if (confirm("确认删除？")) {
			$.ajax({
				type: 'get',
				url: '${base}/ins/traUnSortOut/remove/' + pids,
				dataType: 'json',
				success: function (res) {
					alert(res.msg);
					if (res.success) {
						//从数据中移除
						for (var i = 0; i < selectRows.length; i++) {
							var row = selectRows[i];
							rows.splice(rows.indexOf(row), 1);
						}
						var showData = {'total': rows.length, 'rows': rows};
						$('#unSortOutTable').bootstrapTable('load', showData);
					}
				}

			})
		}
	}


	//刷新表格
	function loadCallback() {
		$('#unSortOutTable').jTable('refresh');
	}

	//重置
	function frmReset() {
		window.location = '${base}/ins/traUnSortOut/list';
	}
</script>
</body>
</html>