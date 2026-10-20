<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp" %>
	<br:css pack="list"/>
	<title>ReportAnnual</title>
	<style>
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
		<bc:ibox title="" subTitle="统计分析" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="年度" col="5" formControl="true"/>
						<bf:datebox id="year" name="year" col="7" format="date"  />
					</bf:formGroup>
					<%--<bf:formGroup col="4">
						<bc:label text="季度" col="5" formControl="true"/>
						<bf:select id="quarter" name="quarter" col="7">
							<bf:option text ="ALL" value ="ALL"></bf:option>
							<bf:option text ="第一季度" value ="第一季度"></bf:option>
							<bf:option text ="第二季度" value ="第二季度"></bf:option>
							<bf:option text ="第三季度" value="第三季度"></bf:option>
							<bf:option text ="第四季度" value="第四季度"></bf:option>
						</bf:select>
					</bf:formGroup>--%>
					<bc:button id="btnQuery" type="danger" text="查询" cls="col-sm-offset-1" icon="search"/>
					<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					<%--<div class="col-sm-12 col-sm-offset-5">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					</div>--%>
				</bc:row>
			</bf:form>
			<bc:table id="statisticTable" idField="pid" url="${base}/report/design" selectFirst="true" pagination="false"
					  queryUrl="/getAnnual/year/quarter">
				<%--<bc:column field="year" title="年度" width="100"/>--%>
				<bc:toolbar id="out" text="导出" type="button" handler="out"/>
				<bc:column field="tranType" title="关联交易类型" width="100"/>
				<bc:column field="generalRelTranNumber" title="一般关联交易笔数" width="100"/>
				<bc:column field="generalRelTranAmount" title="一般关联交易累积金额（万元/人民币）" width="100"/>
				<bc:column field="majorRelTranNumber" title="重大关联交易笔数" width="100"/>
				<bc:column field="majorRelTranAmount" title="重大关联交易累积金额（万元/人民币）" width="100"/>
				<bc:column field="considerAmount" title="免审议累积金额（万元/人民币）" width="100"/>
				<bc:column field="tranAmount" title="总累积金额（万元/人民币）" width="100"/>
			</bc:table>
            <hr>
            <h5 style="color: #ff0000">总累积金额=一般累积金额+重大累积金额+免审议累积金额</h5>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

	function out() {
		// var data = "00";
		// var data=getIdSelections();
		// alert(data);
        //var year = $([name='year']).val();
		var year = $("[name='year']").val();
        var quarter = $("[name='quarter']").val();
		var data = year + "," + quarter;
		var url = '${base}/sys/xlsie/StatisticalExportCallBackImpl/' + data;
		var elemIF = document.createElement("iframe");
		elemIF.src = url;
		elemIF.style.display = "none";
		document.body.appendChild(elemIF);
	}
	//获取审批记录
	/*function send() {
		// 获取审批记录
		$.ajax({
			url:"${base}/report/design/query11",
			method:"GET",
			dataType:"JSON",
			traditional:true,
			success:function(data){
				if(data.success){
					$.messager.alert('提示', data.msg, 'info');
				}else
				{
					$.messager.alert('错误', data.msg, 'error');
				}
			}
		})
	}*/

	function addSubFile() {
		window.location = '${base}/sub/person/addSubFile';
	}

	function viewHandler() {
		debugger;
		var pid = getIdSelections();
		if (pid == '') {
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
		window.location = '${base}/sub/person/view/'+ pid;
	}


	//删除
	function removeHandler() {

		var status = getStatusSelections();
		if (status == "02" || status == "03") {
			$.messager.alert('错误', "审批中，或审批通过 则无法删除", 'error');
			return;
		}
		if (confirm('确认删除？')) {
			var pid = getIdSelections();
			$.ajax({
				url: '${base}/sub/sublog/remove/' + pid,
				type: 'json',
				success: function (data) {
					if (data.success) {
						$.messager.alert('提示', data.msg, 'info', function () {
							window.location = '${base}/sub/person/sublist';
						})
					} else {
						$.messager.alert('错误', data.msg, 'error');
					}
				}
			});
		}
	}

	//文件下载
	function downFileOnclick() {
		var pid = getIdSelections();
		if (pid == '' || pid == null || pid.length != 1) {
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
		var status = getStatusSelections();
		var url = '${base}/sub/credit/subDownFile/' + pid;
		if (status == "03") {
			var elemIF = document.createElement("iframe");
			elemIF.src = url;
			elemIF.style.display = "none";
			document.body.appendChild(elemIF);
		} else {
			$.messager.alert('提示', "当前状态不支持下载，请查看！", 'warning');
			//当状态未通过时按钮置灰 不可点击
			document.getElementById("downFile").setAttribute("disabled", true);
		}
	}

	//获取表单的审批状态
	function getStatusSelections() {
		return $.map($('#personTable').bootstrapTable('getSelections'), function (row) {
			return row.status;
		});
	}

	//获取表单选择的pid
	function getIdSelections() {
		return $.map($('#personTable').bootstrapTable('getSelections'), function (row) {
			return row.pid;
		});
	}

	//重置
	function frmReset() {
		window.location = '${base}/report/design/tra';
	}

	$("#year").datetimepicker({
		format:"yyyy",
		autoclose: true,
		startView:4,
		minView:4,
		todayHighlight:true
	});
</script>
</body>
</html>