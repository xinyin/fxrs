<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>LimitTrans</title>
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
					<bf:formGroup hrp="bottom">
						<bc:col col="4">
							<bc:label text="交易主体" col="5" formControl="true"/>
							<bf:select id="tranOrg" name="__LIKES_tranOrg" col="7" dataSource="custom"
									   url="bus/limit/getTranOrgItems" value="${tranOrg}">
								<bf:option text="ALL" value="%"></bf:option>
								<be:event type="select">
									var input = $("#__LIKES_tranOrg");
									input.focus();
									input.blur();
								</be:event>
							</bf:select>
						</bc:col>
						<bc:col col="4">
							<bc:label text="交易类型" col="5" formControl="true"/>
							<bf:select id="tradeType" name="__LIKES_tradeType" col="7" dataSource="custom"
									   url="bus/limit/getTradeTypeItems">
								<bf:option text="ALL" value="%"></bf:option>
								<be:event type="select">
									setBusinessType(e.target.value);
								</be:event>
							</bf:select>
						</bc:col>
						<bc:col col="4">
							<bc:label text="业务类型" col="5" formControl="true"/>
							<bf:select id="businessType" name="__LIKES_businessType" col="7" dataSource="custom"
									   url="bus/limit/getBuinessTypeItems" >
								<bf:option text="ALL" value="%"></bf:option>
							</bf:select>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hrp="bottom">
						<bc:col col="4">
							<bc:label text="产品名称" col="5" formControl="true"/>
							<bf:text id="proPath" name="__LIKES_proPath" col="7" placeholder="产品名称"/>
						</bc:col>
						<bc:col col="4">
							<bc:label text="审批状态" col="5" formControl="true"/>
							<bf:select id="status" name="__LIKES_status" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/BUS-Applystatus">
								<bf:option text="ALL" value="%"></bf:option>
								<be:event type="select">
									var input = $("#__LIKES_status");
									input.focus();
									input.blur();
								</be:event>
							</bf:select>
						</bc:col>
					</bf:formGroup>
				</bc:row>
				<bc:row cls="m-b">
					<div class="col-sm-12 col-sm-offset-5">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					</div>
				</bc:row>
			</bf:form>
			<bc:table id="limitTransTable" idField="pid" url="${base}/bus/limitTrans">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="edit" text="编辑" type="button" handler="editLimitTrans"/>
				<bc:toolbar id="remove" text="删除" type="button" handler="removeLimitTrans"/>
				<bc:toolbar id="sends" text="提交审批" type="button" handler="sendLimit"/>
<%--				<bc:toolbar id="out" text="数据导出" type="button" handler="out"/>--%>
				<bc:column field="pid" title="pid" width="100" visible="false"/>
				<bc:column field="tranOrg" title="被调剂交易主体" width="100" formatter="custom:bus/limit/getTranOrgItems"/>
				<bc:column field="tradeType" title="被调剂交易类型" width="100" formatter="custom:bus/limit/getTradeTypeItems"/>
				<bc:column field="businessType" title="被调剂业务类型" width="100" formatter="custom:bus/limit/getBuinessTypeItems"/>
				<bc:column field="proName" title="被调剂产品名称" width="100"/>
				<bc:column field="preLimit" title="被调剂前预计额度" width="100" visible="false"/>
				<bc:column field="preLimitLater" title="被调剂后预计额度" width="100" visible="false"/>
				<bc:column field="pasLimit" title="被调剂前剩余额度" width="100" visible="false"/>
				<bc:column field="pasLimitLater" title="被调剂后剩余额度" width="100" visible="false"/>
				<bc:column field="warnLeve" title="被调剂前预警级别" width="100" visible="false"/>
				<bc:column field="warnLeveLater" title="被调剂后预警级别" width="100" visible="false"/>
				<bc:column field="tranAmount" title="调剂金额" width="100"/>
				<bc:column field="tranOrgL" title="调剂交易主体" width="100" formatter="custom:bus/limit/getTranOrgItems"/>
				<bc:column field="tradeTypeL" title="调剂交易类型" width="100" formatter="custom:bus/limit/getTradeTypeItems"/>
				<bc:column field="businessTypeL" title="调剂业务类型" width="100" formatter="custom:bus/limit/getBuinessTypeItems"/>
				<bc:column field="proNameL" title="调剂产品名称" width="100"/>
				<bc:column field="preLimitL" title="被调剂前预计额度" width="100" visible="false"/>
				<bc:column field="preLimitLaterL" title="被调剂后预计额度" width="100" visible="false"/>
				<bc:column field="pasLimitL" title="被调剂前剩余额度" width="100" visible="false"/>
				<bc:column field="pasLimitLaterL" title="被调剂后剩余额度" width="100" visible="false"/>
				<bc:column field="warnLeveL" title="被调剂前预警级别" width="100" visible="false"/>
				<bc:column field="warnLeveLaterL" title="被调剂后预警级别" width="100" visible="false"/>
				<bc:column field="status" title="审批状态" width="100"
						   formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
				<bc:operator id="view" icon="search" title="查看"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

	var tradeType = $('#tradeType').val();
	var businessType = $('#businessType').val();
	debugger;
	//初始化内容脚本
	$(document).ready(function(){
		if(tradeType == null || tradeType == '' || tradeType == undefined ){
			setBusinessType(tradeType);
		}
	});
	//通过交易类型查询相对应的业务类型
	function setBusinessType(value){
		$.ajax({
			type: 'GET',
			url: '${base}/bus/limit/queryBusinessType/' + value,
			dataType: 'json',
			delay: 200,
			success: function(data){
				debugger;
				var arr = new Array();
				$.each(data, function(i, val){
					var obj = {
						id: val.value,
						text: val.name
					};
					arr[i] = obj;
				});
				$("#businessType").empty();
				$("#businessType").select2({data: arr, width: "100%"});
			}
		});
	}

	/*
* 审批
* */
	function sendLimit() {
		// 提交审批  的必须是待提交状态
		var status=getStatusSelections();
		var pid=getIdSelections();
		// alert(pid+"---"+status)
		if (pid=='' || pid==null || pid.length!=1){
			$.messager.alert('错误', "请选择一条数据", 'error');
			return ;
		}
		if (status == 3) {
			//查询出基本信息 修改状态 放到data中
			$.ajax({
				url:"${base}/bus/limitTrans/send/"+pid,
				method:"GET",
				dataType:"JSON",
				traditional:true,
				success:function(data){
					if(data.success){
						//开始走工作流
						debugger
						var objData = data.data;
						var myDate = new Date();
						objData.id = myDate.getTime();
						data = JSON.stringify(objData);
						var formData = $.helper.base64Encode(data);
						//挂取工作流
						var obj = {
							procDefCode: 'ADJUST_LIMIT',
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
    //编辑
    function  editLimitTrans() {
        var pid = getIdSelections();
		var status = getStatusSelections();
		if (pid == '' || pid == null || pid.length != 1) {
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
        if(pid != ""){
			if (status == '4') {
				$.messager.alert('错误', "审批中数据不可编辑", 'error');
				return false;
			}
            window.location = '${base}/bus/limitTrans/edit/' + pid+'?';
        }else {
            $.messager.alert('提示', '请选择。', 'info');
            return "";
        }
    }

    //删除
	function removeLimitTrans() {
		var pid = getIdSelections();
		var status = getStatusSelections();
		if (pid == '' || pid == null || pid.length != 1) {
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
		if (status == "4") {
			$.messager.alert('错误', "审批中，无法删除", 'error');
			return;
		}
		$.messager.confirm('提示', '确定要删除吗？', function (r) {
			if (r) {
				$.ajax({
					url: "${base}/bus/limitTrans/remove/" + pid,
					method: "post",
					data: {"": ""},
					dataType: "JSON",
					traditional: true,
					success: function (data) {
						if (data.success) {
							$.messager.alert('提示', data.msg, 'info', function () {
								window.location = "${base}/bus/limitTrans/list";
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
        return $.map($('#limitTransTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
	function getStatusSelections() {
		return $.map($('#limitTransTable').bootstrapTable('getSelections'), function (row) {
			return row.status;//返回数据行中的id值
		});
	}
	function getdataResourceSelections() {
		return $.map($('#limitTransTable').bootstrapTable('getSelections'), function (row) {
			return row.dataResource;//返回数据行中的id值
		});
	}

	//重置
	function frmReset() {

		window.location = '${base}/bus/limitTrans/list';
	}



</script>
</body>
</html>