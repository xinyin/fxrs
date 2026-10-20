<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp" %>
	<br:css pack="list"/>
	<title>关联交易汇总表</title>
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
		.class_name{
			display: flex;
			justify-content: center;
		}
	</style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="统计分析" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="3">
						<bc:label text="年度" col="5" formControl="true"/>
						<bf:datebox id="year" name="year" col="7" format="date"/>
					</bf:formGroup>
					<bf:hidden id="years" name="years" value="${traSubName}"/>
					<bf:formGroup col="3">
						<bc:label text="季度" col="5" formControl="true"/>
						<bf:select id="quarter" name="quarter" value="${quarter}" col="7">
							<bf:option text ="ALL" value ="%"></bf:option>
							<bf:option text ="第一季度" value ="1"></bf:option>
							<bf:option text ="第二季度" value ="2"></bf:option>
							<bf:option text ="第三季度" value ="3"></bf:option>
							<bf:option text ="第四季度" value ="4"></bf:option>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="交易时间" col="5" formControl="true"/>
						<bf:datebox id="startDate" name="startDate" col="7" />
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="~" col="1" formControl="true"/>
						<bf:datebox id="endDate" name="endDate" col="7" />
					</bf:formGroup>

				</bc:row>
				<bc:row cls="m-b">
					<div class="col-sm-12 col-sm-offset-5">
						<bc:button id="btnQuery" type="danger" text="查询" cls="col-sm-offset-1" icon="search" click="btnQuery()"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					</div>
				</bc:row>
			</bf:form>
			<bc:table id="QuarterList" idField="transNum" url="${base}/report/design" selectFirst="flase"
					  queryUrl="/getQuarterListByManagerOrg" multiple="true" >
				<bc:toolbar id="export" text="导出" type="button" handler="out"/>
				<bc:column field="tranDate" title="交易时间" width="100"/>
				<bc:column field="relName" title="交易对象" width="100"/>
				<bc:column field="managerOrg" title="管理机构" width="100"/>
				<bc:column field="relStatement" title="关联关系说明" width="100"/>
				<bc:column field="tranType" title="交易类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
				<bc:column field="tranOverView" title="交易概述" width="100"/>
				<bc:column field="contractAmount" title="交易金额(亿元)" width="100"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

	var opt = '${opt}';
	$(document).ready(function () {
		if (opt == 'list') {
			$("#reback").css("display", "none");
		}

	})

	function getselectData() {
		return $.map($('#QuarterList').bootstrapTable('getSelections'), function (row) {
			return row//返回数据行中的id值
		});
	}
	function btnQuery(){
		var startDate = $("input[name='startDate']").val();
		var endDate = $("input[name='endDate']").val();
		if(startDate!="" || endDate!=""){
			$("[name='year']").val("");
			$("[name='quarter']").val("");
		}
		if(startDate!="" && endDate ==""){
			$.messager.toastr('提示', '请选择任务结束时间', 'error');
		}
		if(startDate=="" && endDate !=""){
			$.messager.toastr('提示', '请选择任务开始时间。', 'error');
		}
	}
	function out() {
		var year =$("[name='year']").val();
		var quarter =  $("[name='quarter']").val();
		var url ='';
		var selectData = getselectData();
		var startDate = $("input[name='startDate']").val();
		var endDate = $("input[name='endDate']").val();
		if(selectData.length >0){
			var data = JSON.stringify(selectData);
			var form = $("<form>");
			form.attr('style', 'display:none');
			form.attr('target', '');
			form.attr('method', 'post');
			form.attr('action', '${base}/sys/xlsie/QuarterDetailExportCallBackCWImpl/');
			var input1 = $('<input>');
			input1.attr('type', 'hidden');
			input1.attr('name', 'selectData');
			input1.attr('value', data);
			var input2 = $('<input>');
			input2.attr('type', 'hidden');
			input2.attr('name', 'year');
			input2.attr('value', year);
			var input3 = $('<input>');
			input3.attr('type', 'hidden');
			input3.attr('name', 'quarter');
			input3.attr('value', quarter);
			$('body').append(form);
			form.append(input1);
			form.append(input2);
			form.append(input3);
			form.submit();
			form.remove();
		}else {
			var myDate=new Date()
			year = year == ""? myDate.getFullYear() : year;
			quarter = quarter == "%"? 5 : quarter;
			startDate = startDate == ""? "%25" : startDate;
			endDate = endDate == ""? "%25" : endDate;
			var data =  year + "," + quarter +"," + startDate +"," + endDate;
			url = '${base}/sys/xlsie/QuarterDetailExportCallBackCWImpl/' + data;
			var elemIF = document.createElement("iframe");
			elemIF.src = url;
			elemIF.style.display = "none";
			document.body.appendChild(elemIF);
		}

	}

	function getTraSubName() {
		return 	$("[name='traSubName']").val();
	}


	//获取表单选择的交易编号
	function getIdSelections() {
		return $.map($('#QuarterList').bootstrapTable('getSelections'), function (row) {
			return row.transNum;
		});
	}


	//重置
	function frmReset() {
		window.location = '${base}/report/design/quarterListByManagerOrg';
	}

	$("#year").datetimepicker({
		format:"yyyy",
		autoclose: true,
		startView:4,
		minView:4,
		todayHighlight:true,
	});
</script>
</body >
</html>