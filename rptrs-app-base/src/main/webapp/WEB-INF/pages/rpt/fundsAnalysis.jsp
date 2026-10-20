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
						<bf:datebox id="investmentBalanceTimes" name="investmentBalanceTime" col="7" format="date" >
							<%--<bv:date format="YYYY" message="只能输入合理的年份，类型为YYYY类型" />--%>
						</bf:datebox>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="季度" col="5" formControl="true"/>
						<bf:select id="quarter" name="quarter" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-Quarter">
							<bf:option text="本年度" value=""></bf:option>
							<be:event type="select">
								var input = $("#quarter");
								input.focus();
								input.blur();
							</be:event>
						</bf:select>
					</bf:formGroup>
					<div class="col-sm-12 col-sm-offset-5">
						<bc:button id="btnQuery" type="danger" text="查询" cls="col-sm-offset-1" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					</div>
				</bc:row>
			</bf:form>
			<bc:table id="fundsInvestmentStaTable" idField="pid" url="${base}/bus/fundsInvestmentSta"
					  queryUrl="query1">

				<bc:toolbar id="out" text="导出" type="button" handler="out"/>
				<bc:column field="investmentBalanceTimeString" title="投资余额统计时间" width="100"/>
				<bc:column field="realEstateBalance" title="不动产类资产投资余额" width="100"/>
				<bc:column field="foreignBalance" title="境外投资余额" width="100"/>
				<bc:column field="fixedIncomeBalance" title="固定收益类资产投资余额" width="100"/>
				<bc:column field="balanceInvestment" title="权益类资产投资余额" width="100"/>
				<bc:column field="insuranceManagementBalance" title="保险资产管理产品投资余额" width="100"/>
				<bc:column field="bankFinancialBalance" title="商业银行理财产品投资余额" width="100"/>
				<bc:column field="bankNegotiableSecuritiesBalance" title="银行业金融机构信资产支持证券投资余额" width="100"/>
				<bc:column field="trustSchemeBalance" title="信托公司集合资金信托计划投资余额" width="100"/>
				<bc:column field="negotiableSecuritiesSpecialPlanBalance" title="证券公司专项资产管理计划投资余额" width="100"/>
				<bc:column field="insuranceFundsSupportPlanBalance" title="保险资管公司发行资产支持计划投资余额" width="100"/>
				<bc:column field="otherInvestmentBalance" title="其他投资余额" width="100"/>
				<bc:column field="allInvestmentBalance" title="全部投资余额" width="100"/>
				<bc:column field="mobilityBalance" title="流动性资产投资余额" width="100"/>
				<bc:column field="status" title="状态" width="100"  formatter="custom:sys/dictItem/getDictItems/BUS_FundsStatus"/>

				<%--<bc:column field="totalCapital" title="总资产(上年末)" width="100"/>
				<bc:column field="netCaptial" title="净资产(上年末)" width="100"/>
				<bc:column field="fixedIncomeBalance" title="固定收益类投资余额" width="100"/>
				<bc:column field="listedBalance" title="上市权益类投资余额" width="100"/>
				<bc:column field="unlistedBalance" title="未上市权益类投资余额" width="100"/>
				<bc:column field="unlistedPro" title="未上市权益类资产占总资产比例" width="100"/>
				<bc:column field="realEstatePro" title="不动产类资产占总资产比例" width="100"/>
				<bc:column field="bankFinancialBalance" title="商业银行理财产品投资余额" width="100"/>
				<bc:column field="otherFinanceBalance" title="其他类金融资产投资余额" width="100"/>
				<bc:column field="otherTotal" title="其他金融资产-合计" width="100"/>
				<bc:column field="otherTotalPro" title="其他金融资产占总资产比例" width="100"/>
				<bc:column field="foreignPro" title="境外投资占总资产比例" width="100"/>
				<bc:column field="allBalance" title="全部投资余额（含活期存款）" width="100"/>
				<bc:column field="allInvestProForTotal" title="全部投资余额占总资产比例" width="100"/>
				<bc:column field="allInvestProForNetAsset" title="全部投资余额占净资产比例" width="100"/>
				<bc:column field="mobilityBalance" title="流动性资产投资余额" width="100"/>--%>

			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
	$(document).ready(function () {
		// $("input[name='__LIKES_years']").val(new Date().getUTCFullYear());
		$("#investmentBalanceTimes").datetimepicker("setDate", new Date());

	});
	$("#investmentBalanceTimes").datetimepicker({
		format: "yyyy",
		autoclose: true,
		startView: 4,
		minView: 4,
		todayHighlight: true,
	});
	$("#investmentBalanceTimes").change(function () {
		if (
				$("#investmentBalanceTimes").datetimepicker("getDate") == "" ||
				$("#investmentBalanceTimes").datetimepicker("getDate") == null ||
				$("#investmentBalanceTimes").datetimepicker("getDate") == undefined
		) {
			$("#investmentBalanceTimes").datetimepicker("setDate", new Date());
		}
	});
	/*获取pid*/
	function getIdSelections() {
		return $.map($('#fundsInvestmentStaTable').bootstrapTable('getSelections'), function (row) {
			return row.pid;
		});
	}
	//获取状态
	function getStausSelections() {
		return $.map($('#fundsInvestmentStaTable').bootstrapTable('getSelections'), function (row) {
			return row.status;
		});
	}

	function out() {
		var investmentBalanceTime = $("[name='investmentBalanceTime']").val();
		var quarter = " ";
		console.log(investmentBalanceTime)
		console.log(quarter)
		var data = investmentBalanceTime + "-" + quarter + "-";
		var url = '${base}/sys/xlsie/FundsInvestmentStaExportCallBackImpl/'+data;
		var elemIF = document.createElement("iframe");
		elemIF.src = url;
		elemIF.style.display = "none";
		document.body.appendChild(elemIF);
	}

	//重置
	function frmReset() {
		window.location = '${base}/bus/fundsInvestmentSta/list';
	}

</script>

</body>
</html>