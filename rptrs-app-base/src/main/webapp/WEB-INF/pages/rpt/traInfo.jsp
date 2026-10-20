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
						<bc:label text="交易主体" col="5" formControl="true"/>
						<bf:select id="traSubName" name="traSubName" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/INS-subjectName" value="${unitCode}" disabled="${opt1 == 'MH'}">
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="年度" col="5" formControl="true"/>
						<bf:datebox id="year" name="year" col="7" format="date"/>
					</bf:formGroup>
					<bf:hidden id="years" name="years" value="${years}"/>
					<bf:formGroup col="4">
						<bc:label text="季度" col="5" formControl="true"/>
						<bf:select id="quarter" name="quarter" value="${quarter}" col="7">
							<bf:option text ="ALL" value ="%"></bf:option>
							<bf:option text ="第一季度" value ="1"></bf:option>
							<bf:option text ="第二季度" value ="2"></bf:option>
							<bf:option text ="第三季度" value="3"></bf:option>
							<bf:option text ="第四季度" value="4"></bf:option>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="关联交易类型" col="5" formControl="true"/>
						<bf:select id="tranType" name="tranType" value="${traType}" col="7" dataSource="custom" url="sys/dictItem/getDictItems/INS-tranType">
							<bf:option text="ALL" value="%"></bf:option>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup hrp="bottom">
						<bf:formGroup col="4">
						<bc:label text="审议类型" col="5" formControl="true"/>
						<bf:select id="considerType" name="considerType" col="7"
								   dataSource="custom" url="sys/dictItem/getDictItems/INS-considerType">
							<bf:option text="ALL" value="%"></bf:option>
						</bf:select>
						</bf:formGroup>
					</bf:formGroup>
					<div class="col-sm-12 col-sm-offset-5">
						<bc:button id="btnQuery" type="danger" text="查询" cls="col-sm-offset-1" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					</div>
				</bc:row>
			</bf:form>
			<bc:table id="traInfo" idField="transNum" url="${base}/report/design" selectFirst="true"
					  queryUrl="/traInfo" multiple="true" >
				<bc:toolbar id="out" text="导出" type="button" handler="out"/>
				<bc:toolbar id="outOne" text="导出选中数据" type="button" handler="outOne"/>
				<bc:column field="transNum" title="交易编号" width="100"/>
				<bc:column field="tranType" title="交易类型" width="100"
						   formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
				<bc:column field="traSubName" title="交易主体名称" width="100"
						   formatter="custom:sys/dictItem/getDictItems/INS-subjectName"/>
				<bc:column field="relName" title="交易对手名称" width="100"/>
				<bc:column field="relType" title="交易对手类型" width="100"
						   formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
				<bc:column field="majorTrade" title="是否重大关联交易" width="100" formatter="fixedDict:IsStatusDict"/>
				<bc:column field="considerType" title="审议类型" width="100"
						   formatter="custom:sys/dictItem/getDictItems/INS-considerType"/>
				<bc:column field="tranDate" title="合同/交易发生日期" width="100"/>
				<bc:column field="contractAmount" title="关联交易金额（万元/人民币）" width="100"/>
				<bc:column field="status" title="操作状态" width="100"
						   formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
				<bc:operator id="view" icon="search" title="操作/查看"/>
			</bc:table>
			<bc:button id="reback" type="primary" text="返回" icon="arrow-circle-left">
				<be:event>
					window.location = '${base}//report/design/tra';
				</be:event>
			</bc:button>
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

	function out() {
		// var data = "00";
		// var data=getIdSelections();
		// alert(data);
        //var year = $([name='year']).val();
		var year = $("[name='year']").val();
        var quarter = $("[name='quarter']").val();
        if (quarter == "%") {
			quarter = "ALL";
		}
        var tranType = $("[name='tranType']").val();
        if (tranType == "%") {
			tranType = "ALL";
		}
		var traSubName = getTraSubName();
        var considerType = $("[name='considerType']").val();
        if (considerType == "%") {
			considerType= "ALL";
		}
		var data = year + "," + quarter + "," + tranType + "," + considerType+","+traSubName;
		var url = '${base}/sys/xlsie/StatisticalDetailExportCallBackImpl/' + data;
		var elemIF = document.createElement("iframe");
		elemIF.src = url;
		elemIF.style.display = "none";
		document.body.appendChild(elemIF);
	}
	function outOne() {
		var tranNum = getIdSelections();
		if (tranNum == '' || tranNum == undefined) {
			$.messager.alert('提示', '请至少选一条数据', 'info');
			return;
		}
		// if (tranNum.indexOf(",") != -1) {
		// 	$.messager.alert('提示', '最多选择一条数据', 'info');
		// 	return;
		// } else {
		var year = $("[name='year']").val();
		var quarter = $("[name='quarter']").val();
		if (quarter == "%") {
			quarter = "ALL";
		}
		var tranType = $("[name='tranType']").val();
		if (tranType == "%") {
			tranType = "ALL";
		}
		var considerType = $("[name='considerType']").val();
		if (considerType == "%") {
			considerType= "ALL";
		}
		var traSubName = getTraSubName();
		var data = year + "," + quarter + "," + tranType + "," + considerType+","+traSubName+ "," + tranNum;
		var url = '${base}/sys/xlsie/StatisticalDetailExportCallBackImpl/' + data;
		var elemIF = document.createElement("iframe");
		elemIF.src = url;
		elemIF.style.display = "none";
		document.body.appendChild(elemIF);
		// }
	}

	function getTraSubName() {
		return 	$("[name='traSubName']").val();
	}


	//获取表单选择的交易编号
	function getIdSelections() {
		return $.map($('#traInfo').bootstrapTable('getSelections'), function (row) {
			return row.transNum;
		});
	}

	//重置
	function frmReset() {
		window.location = '${base}/report/design/traInfoList';
	}

	$("#year").datetimepicker({
		format:"yyyy",
		autoclose: true,
		startView:4,
		minView:4,
		todayHighlight:true,
	});
</script>
</body>
</html>