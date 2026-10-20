<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>Loan</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="用信明细情况表" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="文件名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_fileName" col="7" placeholder="文件名称"/>
					</bf:formGroup>
					<bf:formGroup col="4">
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
						<bc:label text="年度" col="5" formControl="true"/>
						<bf:datebox id="subYear" name="subYear" col="7" format="date" />
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
						<bf:select id="status" name="__LIKES_status" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/SUBMIT-status">
							<bf:option text="ALL" value=""></bf:option>
							<be:event type="select">
								var input = $("#__LIKES_status");
								input.focus();
								input.blur();
							</be:event>
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
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="loanTable" idField="pid" url="${base}/sub/credit" queryUrl="/query/loan">
				<bc:toolbar id="subFile" text="生成报送文件" type="button" handler="subFileOnclick"/>
				<%--<bc:toolbar id="send" text="提交审批" handler="send" type="button"/>--%>
				<bc:toolbar id="downFile" text="文件下载" type="button" handler="downFileOnclick"/>
				<bc:column field="pid" title="pid" width="100" visible="false"/>
				<bc:column field="fileName" title="文件名称" width="100"/>
				<bc:column field="subOrg" title="报送机构" width="100" formatter="custom:sys/unit/selectUnit"/>
				<bc:column field="subYear" title="年度" width="100" />
				<bc:column field="subQuarter" title="季度" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Quarter"/>
				<bc:column field="maxPercentInNetAssets" title="生成报送时间" width="100"/>
				<bc:column field="downStatus" title="是否已下载" width="100" formatter="fixedDict:IsStatusDict"/>
				<bc:column field="status" title="状态" width="100" formatter="custom:sys/dictItem/getDictItems/SUBMIT-status"/>
				<bc:column field="statusComment" title="状态说明" width="100" />
				<%--<bc:operator id="view" icon="search" title="查看"/>--%>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
	$("#subYear").datetimepicker({
		format:"yyyy",
		autoclose: true,
		startView:4,
		minView:4,
		todayHighlight:true
	});
	//生成报送文件
	function subFileOnclick() {
		var subType = "loan";
		var	subYear = $("[name='subYear']").val();
		var subQuarter = $("#subQuarter").val();
		var url = '${base}/sub/credit/subFile/' + subType;
		debugger;
		$.ajax({
			url: url,
			type: 'POST',
			data: {'subYear':subYear,'subQuarter':subQuarter},
			dataType: 'json',
			success: function (data) {
				if (data.success) {
					$.messager.alert('成功',data.msg,'info',function () {
						window.location = '${base}/sub/loan/list';
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
            // alert("当前状态不支持下载，请查看！");
			//当状态未通过时按钮置灰 不可点击
			document.getElementById("downFile").setAttribute("disabled", true);
		}
	}
	//获取表单的审批状态
	function getStatusSelections() {
		return $.map($('#loanTable').bootstrapTable('getSelections'), function (row) {
			return row.status;
		});
	}
	//获取表单选择的pid
	function getIdSelections() {
		return $.map($('#loanTable').bootstrapTable('getSelections'), function (row) {
			return row.pid;
		});
	}
	//重置
	function frmReset() {
		window.location = '${base}/sub/loan/list';
	}
</script>
</body>
</html>