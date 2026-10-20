<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 加载systemInfo配置文件 -->
<fmt:setBundle basename="jep" var="sysInfo" />
<!-- 读取配置值AppName，并赋值给变量appName -->
<fmt:message key="file.path.down" var="filePath" bundle="${sysInfo}" />
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>Publog</title>
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
						<bc:label text="文件名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_pubFileName" col="7" placeholder="文件名称"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="披露主体" col="5" formControl="true"/>
						<bf:select name="__LIKES_pubSubject" col="7" dataSource="custom"
								   url="bus/limit/getTranOrgItems" value="${pubSubject}">
							<be:event type="select">
								var input = $("#__LIKES_pubSubject");
								input.focus();
								input.blur();
							</be:event>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="是否已下载" col="5" formControl="true"/>
						<bf:select name="__LIKES_downStatus" col="7" dataSource="fixedDict"
								   url="IsStatusDict">
							<bf:option text="ALL" value="%"></bf:option>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="状态" col="5" formControl="true"/>
						<bf:select name="__LIKES_status" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/SUBMIT-status">
							<bf:option text="ALL" value="%"></bf:option>
							<be:event type="select">
								var input = $("#__LIKES_status");
								input.focus();
								input.blur();
							</be:event>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="年度" col="5" formControl="true"/>
						<bf:datebox id="pubYear" name="__LIKES_pubYear" col="7" format="date" />
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="生成版本号" col="5" formControl="true"/>
						<bf:text name="__LIKES_pubVersion" col="7" placeholder="生成版本号"/>
					</bf:formGroup>
				</bc:row>
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="开始时间" col="5" formControl="true"/>
						<bf:datebox id="startDate" name="__GES_maxPercentInNetAssets" col="7" format="date"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="截止时间" col="5" formControl="true"/>
						<bf:datebox id="endDate" name="__LTS_maxPercentInNetAssets" col="7" format="date"/>
					</bf:formGroup>
					<bc:row cls="m-b">
						<div class="col-sm-12 col-sm-offset-5">
							<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
							<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
						</div>
					</bc:row>
				</bc:row>
			</bf:form>
			<bc:table id="publogTable" idField="pid" url="${base}/pub/publog">
				<bc:toolbar id="add" text="新增" type="button" handler="sendAdd"/>
				<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
				<bc:toolbar id="downFile" text="下载文件" type="button" handler="downFileOnclick"/>
				<bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
				<bc:toolbar id="viewFile" text="在线编辑" type="button" handler="viewFile"/>
				<bc:toolbar id="send" text="提交审批" handler="send" type="button"/>
				<bc:column field="pid" title="pid" width="100" visible="false"/>
				<bc:column field="pubFileName" title="文件名称" width="100"/>
				<bc:column field="pubSubject" title="披露主体" width="100"/>
				<bc:column field="pubYear" title="年度" width="100"/>
				<bc:column field="pubQuarter" title="季度" width="100" visible="false"/>
				<bc:column field="maxPercentInNetAssets" title="生成披露文件时间" width="100"/>
				<bc:column field="pubVersion" title="生成版本号" width="100"/>
				<bc:column field="downAmt" title="下载次数" width="100" visible="false"/>
				<bc:column field="status" title="状态" width="100" formatter="custom:sys/dictItem/getDictItems/SUBMIT-status"/>
				<bc:column field="statusComment" title="状态说明" width="100" visible="false"/>
				<bc:column field="downStatus" title="是否已下载" width="100" formatter="fixedDict:IsStatusDict"/>
				<%--<bc:operator id="view" icon="search" title="查看" handler="viewFile"/>--%>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
	$("#pubYear").datetimepicker({
		format:"yyyy",
		autoclose: true,
		startView:4,
		minView:4,
		todayHighlight:true
	});
	/* 新增披露文件 */
	function sendAdd(){
		var tables =  $('#publogTable').jTable('getCheckData');
			$.messager.modal({
				winId: 'pubFileAdd',
				width: 700,
				height: 420,
				title: '生成披露文件',
				url: '${base}/pub/publog/pubFileAdd',
				args: {traId: tables[0].pid},
				onClose: function(){
					$('#publogTable').jTable('clearCheckData');
					$('#publogTable').jTable('refresh');
				}
			});
		}

	//删除
	function removeHandler() {

		var status = getStatusSelections();
		if ("01" != status) {
			alert("当前状态不能删除");
			return false;
		}
		if (confirm('确认删除？')) {
			var pid = getIdSelections();
			<%--window.location = '${base}/bus/gdTraDj/remove/' + pid;--%>
			$.ajax({
				url: '${base}/pub/publog/remove/' + pid,
				type: 'json',
				success: function (data) {
					window.location = '${base}/pub/publog/list';
				}
			});
		}
	}

	//查看文件
	viewFile = function () {
		//选中的文件
		var pid = getIdSelections();

		if (pid == '' || pid == undefined) {
			alert("请选中一个文件");
			return;
		}
		if (pid.indexOf(',') != -1) {
			alert("只能选中一个文件");
			return;
		}
		window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
				+ encodeURIComponent('${base}/pub/publog/preview?pid=' + pid ));
	}

	/*文件下载*/
	function downFileOnclick(){
		var pid = getIdSelections();
		if (pid=='' || pid==null||pid.length!=1){
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
		var status = getStatusSelections();
		var url = '${base}/pub/publog/subDownFile/' + pid;
		if(status == "03"){
			var elemIF = document.createElement("iframe");
			elemIF.src = url;
			elemIF.style.display = "none";
			document.body.appendChild(elemIF);
			<%--window.location = '${base}/pub/publog/list';--%>
		}else {
			debugger;
			$.messager.alert('警告', "当前状态不支持下载，请查看！", 'warning');
			//当状态未通过时按钮置灰 不可点击
			document.getElementById("downFile").setAttribute("disabled", true);
		}
	}

	function OpenDoc()
	{
		var pid = getIdSelections();
		var filename=getFilenameSelections();
		filepath=filename;
		//window.open("${base}/static/js/jsp/webdocedit.html?filename="+filename+"&filepath=${filePath}");
		window.open("${base}/static/js/jsp/webdocedit.html?filepath=");

	}

	function send() {
		// 提交审批  的必须是待提交状态
		var status=getStatusSelections();
		var pid=getIdSelections();
		if (status == '01') {
			//查询出基本信息 修改状态 放到data中
			$.ajax({
				url:"${base}/pub/publog/send/"+pid,
				method:"GET",
				data:{"":""},
				dataType:"JSON",
				traditional:true,
				success:function(data){
					if(data.success){
						//开始走工作流
						var objData=data.data;
						var myDate = new Date();
						objData.id = myDate.getTime();
						data = JSON.stringify(objData);
						var formData = $.helper.base64Encode(data);
						//挂取工作流
						var obj = {
							procDefCode: 'PUB_YEAR',
							oldData: '',
							newData: formData,
							uploadSessions: '',
							procInstId: ''
						}

						$.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
							if (data.success) {
								$.messager.alert('提示', '提交成功。', 'info', function () {
									window.location = '${base}/workflow/process/runingList';
								});
							} else {
								$.messager.alert('错误', data.msg, 'error');
							}
						});
					}else
					{
						$.messager.alert('错误', data.msg, 'error');
					}
				}
			})
		}else {
			//alert("当前状态是："+status+"不符合要求！");
			alert("您已提交审批了！");
		}
	}

	/*获取当前数据id*/
	function getIdSelections() {
		return $.map($('#publogTable').bootstrapTable('getSelections'), function (row) {
			return row.pid;
		});
	}
	/*获取表单状态*/
	function getStatusSelections() {
		return $.map($('#publogTable').bootstrapTable('getSelections'), function (row) {
			return row.status;
		});
	}
	function getFilenameSelections() {
		return $.map($('#publogTable').bootstrapTable('getSelections'), function (row) {
			return row.pubFileName;
		});
	}
/*
* 重置
* */
	function frmReset(){
		window.location = '${base}/pub/publog/list';
	}
</script>
</body>
</html>