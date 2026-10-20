<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>RelPar</title>
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
		#glgxTable_tb_addNewFile,#glgxTable_tb_remove, #glgxTable_tb_downFile{
			display: none;
		}
	</style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="关联关系表" subTitle="" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="5">
						<bc:label text="文件名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_fileName" col="7" placeholder=""/>
					</bf:formGroup>
					<%--<bf:formGroup col="4">
						<bc:label text="是否已推送" col="5" formControl="true"/>
						<bf:select name="__EQS_status" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/INS-EastPushStatus">
							<bf:option text="ALL" value=""></bf:option>getDictItems
						</bf:select>
					</bf:formGroup>--%>
					<%--<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>--%>
					<bc:row cls="m-b">
						<div class="col-sm-12 col-sm-offset-5">
							<bc:button id="btnQuery" type="danger" text="查询" icon="search" />
							<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
						</div>
					</bc:row>
				</bc:row>
			</bf:form>
			<bc:table id="glgxTable" idField="pid" url="${base}/sub/sublog" queryUrl="queryEast/E3">
				<bc:toolbar id="addNewFile" text="新增" type="button" handler="addHandler"/>
				<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
				<bc:toolbar id="downFile" text="下载文件" type="button" handler="downFileOnclick"/>
				<bc:column field="id" title="id" width="100" visible="false"/>
				<bc:column field="fileName" title="文件名称" width="100"/>
				<bc:column field="operatorName" title="操作员" width="100"/>
				<bc:column field="maxPercentInNetAssets" title="生成报送文件时间" width="100" />
				<%--<bc:operator id="view" icon="eye" title="查看"/>--%>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
	$(function () {
		var code = "${code }";

		//新增
		if (code.search("01,") != -1) {
			$('#glgxTable_tb_addNewFile').show();
		}
		//删除
		if (code.search("03,") != -1) {
			$('#glgxTable_tb_remove').show();
		}
		//下载
		if (code.search("013,")!=-1){
			$('#glgxTable_tb_downFile').show();
		}
	});

	function addHandler() {
		window.location = '${base}/east/glgx/add';
	}
	//删除文件
	function removeHandler() {
		$.messager.confirm('提示', '确定要删除吗？', function (r) {
			if (r) {
				var pid = getIdSelections();
				if (pid == '' || pid == null || pid.length != 1) {
					$.messager.alert('错误', "请选择一条数据", 'error');
					return;
				}
				$.ajax({
					url: '${base}/east/glgx/remove/' + pid,
					type: 'json',
					success: function (data) {
						if (data.success) {
							$.messager.alert('提示', data.msg, 'info', function () {
								window.location = '${base}/east/glgx/list';
							})
						} else {
							$.messager.alert('错误', data.msg, 'error');
						}
					}
				});
			}
		});
	}

	function getIdSelections() {
		return $.map($('#glgxTable').bootstrapTable('getSelections'), function (row) {
			return row.pid;
		});
	}
	//下载文件
	function downFileOnclick() {
		var pid = getIdSelections();
		if (pid == '' || pid == null || pid.length != 1) {
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
		var url = '${base}/sub/credit/subEastDownFile/' + pid;
		if (true) {
			var elemIF = document.createElement("iframe");
			elemIF.src = url;
			elemIF.style.display = "none";
			document.body.appendChild(elemIF);
		} else {
			$.messager.alert('警告', "当前状态不支持下载，请查看！", 'warning');
			//当状态未通过时按钮置灰 不可点击
			document.getElementById("downFile").setAttribute("disabled", true);
		}
	}

</script>
</body>
</html>