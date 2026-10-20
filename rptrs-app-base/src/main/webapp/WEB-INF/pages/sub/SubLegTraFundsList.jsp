<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>Credit</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="与单一法人主体资金运用关联交易统计表" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="文件名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_fileName" col="7" placeholder=""/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="年度" col="5" formControl="true"/>
						<bf:datebox id="subYear" name="subYear" col="7" format="date"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="季度" col="5" formControl="true"/>
						<bf:select id="subQuarter" name="__LIKES_subQuarter" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-Quarter">
							<bf:option text="ALL" value=""></bf:option>
							<be:event type="select">
								var input = $("#__LIKES_subQuarter");
								input.focus();
								input.blur();
							</be:event>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="状态" col="5" formControl="true"/>
						<bf:select name="__LIKES_downStatus" col="7" dataSource="fixedDict"
								   url="IsStatusDict">
							<bf:option text="ALL" value="%"></bf:option>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="开始时间" col="5" formControl="true"/>
						<bf:datebox id="startDate" name="__GES_maxPercentInNetAssets" col="7" format="date"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="截止时间" col="5" formControl="true"/>
						<bf:datebox id="endDate" name="__LTS_maxPercentInNetAssets" col="7" format="date"/>
					</bf:formGroup>

					<%--<bf:formGroup col="4">
                        <bc:label text="报送机构" col="5" formControl="true"/>
                        <bf:select name="__LIKES_subOrg" col="7" dataSource="custom"
                                   url="sys/unit/selectUnit">
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="是否下载" col="5" formControl="true"/>
                        <bf:select name="__LIKES_downStatus" col="7" dataSource="fixedDict"
                                   url="IsStatusDict">
                            <bf:option text="ALL" value="%"></bf:option>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="状态" col="5" formControl="true"/>
                        <bf:select id="status" name="__LIKES_status" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/SUBMIT-status">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_status");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>--%>
					<bc:row cls="m-b">
						<div class="col-sm-12 col-sm-offset-5">
							<bc:col col="3" colOffset="0">
								<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
								<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
							</bc:col>
						</div>
					</bc:row>
				</bc:row>
			</bf:form>
			<bc:table id="creditTable" idField="pid" url="${base}/sub/sublog" queryUrl="singleDeal/08">
				<bc:toolbar id="subFile" text="新增" type="button" handler="subFileOnclick"/>
				<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
				<bc:toolbar id="downFile" text="下载" type="button" handler="downFileOnclick"/>
				<bc:toolbar id="send" text="获取审批记录" handler="send" type="button"/>
				<bc:column field="pid" title="自增长主键" width="100" visible="false"/>
				<bc:column field="batchDecimal" title="batchDecimal" width="100" visible="false"/>
				<bc:column field="fileName" title="文件名称" width="100"/>
				<bc:column field="subOrg" title="报送机构" width="100" formatter="custom:bus/limit/getTranOrgItems"/>
				<bc:column field="subYear" title="年度" width="100"/>
				<bc:column field="subQuarter" title="季度" width="100"/>
				<bc:column field="maxPercentInNetAssets" title="生成报送文件时间" width="100"/>
				<bc:column field="operatorName" title="操作员" width="100"/>
				<bc:column field="downStatus" title="状态" width="100" formatter="fixedDict:IsStatusDict"/>
				<%--<bc:column field="downStatus" title="是否已下载" width="100" formatter="fixedDict:IsStatusDict"/>
				<bc:column field="caozuoren" title="操作人" width="100"/>
				<bc:column field="status" title="状态" width="100" formatter="custom:sys/dictItem/getDictItems/SUBMIT-status"/>
				<bc:column field="statusComment" title="状态说明" width="100"/>--%>
				<bc:operator id="view" icon="search" title="查看"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

	function addSubLegTraFun() {
		window.location = '${base}/sub/sublog/addSubLegTraFun';
	}

	$("#subYear").datetimepicker({
		format:"yyyy",
		autoclose: true,
		startView:4,
		minView:4,
		todayHighlight:true
	});
	//生成报送文件
	function subFileOnclick() {
		var subType = "credit";
		var	subYear = $("[name='subYear']").val();
		var subQuarter = $("#subQuarter").val();
		var url = '${base}/sub/credit/subFile/' + subType;

		$.ajax({
			url: url,
			type: 'POST',
			data: {'subYear':subYear,'subQuarter':subQuarter},
			dataType: 'json',
			success: function (data) {
				if (data.success) {
					$.messager.alert('成功',data.msg,'info',function(){
						window.location = '${base}/sub/credit/list';
					});
				} else {
					if(data.msg == "暂无报送数据生成"){
						$.messager.alert('提示',data.msg,'info');
					}else{
						$.messager.alert('失败',data.msg,'error');
					}
				}
			}
		});
	}
	//文件下载
	function downFileOnclick(){
		var pid = getIdSelections();
		if (pid=='' || pid==null||pid.length!=1){
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
		var status = getStatusSelections();
		var url = '${base}/sub/credit/subDownFile/' + pid;
		if(status == "03" ){
			var elemIF = document.createElement("iframe");
			elemIF.src = url;
			elemIF.style.display = "none";
			document.body.appendChild(elemIF);
		}else {
			$.messager.alert('警告', "当前状态不支持下载，请查看！", 'warning');
          //  alert("当前状态不支持下载，请查看！");
			//当状态未通过时按钮置灰 不可点击
			document.getElementById("downFile").setAttribute("disabled", true);
		}
	}
	/*
     * 审批
     * */
	/*function send() {
		var status=getStatusSelections();
		var pid=getIdSelections();
		if (pid=='' || pid==null||pid.length!=1){
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
		debugger;
		// 提交审批  的必须是待提交状态
		if (status == "01") {
			//查询出基本信息 修改状态 放到data中
			$.ajax({
				url:"${base}/sub/credit/send/"+pid,
				method:"POST",
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
						//	procDefCode: 'YZJ_REL_PSN',
							procDefCode: 'YZJ_SUB',
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
			$.messager.alert('警告',"当前状态不符合要求！",'warning');
		}
	}*/

	//提交审批
	function send() {
		// 提交审批  的必须是待提交状态
		var status=getStatusSelections();
		var pid=getIdSelections();
		if (pid=='' || pid==null||pid.length!=1){
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
		if (status == "01") {
			//查询出基本信息 修改状态 放到data中
			$.ajax({
				url:"${base}/sub/sublog/send/"+pid,
				method:"GET",
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
							procDefCode: 'YZJ_SUB001',
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
			alert("当前状态不符合要求！");
		}
	}

	//获取表单的文件名
	function getFileNameSelections() {
		return $.map($('#creditTable').bootstrapTable('getSelections'), function (row) {
			return row.fileName;
		});
	}
	//获取表单的审批状态
	function getStatusSelections() {
		return $.map($('#creditTable').bootstrapTable('getSelections'), function (row) {
			return row.status;
		});
	}
	//获取表单选择的pid
	function getIdSelections() {
		return $.map($('#creditTable').bootstrapTable('getSelections'), function (row) {
			return row.pid;
		});
	}
	//重置
	function frmReset() {
		window.location = '${base}/sub/credit/list';
	}
</script>
</body>
</html>