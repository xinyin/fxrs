<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>RiskRatio</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
			</bf:form>
			<bc:table id="riskRatioTable" idField="pid" url="${base}/bus/riskRatio">
				<bc:toolbar id="adddoc" text="新增" type="button" handler="addHandler"/>
				<bc:toolbar id="editdoc" text="编辑" type="button" handler="editHandler"/>
				<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
				<bc:column field="pid" title="序号" width="100"/>
				<bc:column field="createTimeString" title="上传时间" width="100"/>
				<bc:column field="warningValue" title="预警比例(%)" width="100"/>
				<bc:column field="markedWords" title="预警提示语" width="100"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>

<script>
	//编辑
	function editHandler() {
		var pid = getIdSelections();
		if (pid == '' || pid == null || pid.length != 1) {
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
		window.location = "${base}/bus/riskRatio/edit/" + pid;
	}
	//新增
	function addHandler() {
		console.log('addhandler');
		$.ajax({
			url: "${base}/bus/riskRatio/count",
			method: "GET",
			data: {"": ""},
			dataType: "JSON",
			traditional: true,
			success: function (data) {
				console.log(data)
				if (data.success) {
					window.location = "${base}/bus/riskRatio/add";
				} else {
					$.messager.alert('错误', "最多只能有一条预警信息", 'error');
				}
			}
		})
	}
	//删除数据
	function removeHandler() {
		var pid = getIdSelections();
		if (pid == '' || pid == null) {
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}

		$.messager.confirm('提示', '确定要删除吗？', function (r) {
			if (r) {
				$.ajax({
					url: "${base}/bus/riskRatio/remove/" + pid,
					method: "GET",
					data: {"": ""},
					dataType: "JSON",
					traditional: true,
					success: function (data) {
						if (data.success) {
							$.messager.alert('提示', data.msg, 'info', function () {
								window.location = "${base}/bus/riskRatio/list"
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
		return $.map($('#riskRatioTable').bootstrapTable('getSelections'), function (row) {
			return row.pid//返回数据行中的id值
		});
	}
</script>
</html>