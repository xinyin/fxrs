<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>关联交易表（保险）-保险业务类</title>
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



		.form-group {
			margin: 0.3% 0 !important;
		}

		.form-group .col-xs-12 {
			margin: 0.3% 0 !important;
		}

		.form-group .col-xs-12 label {
			display: inline-block;
			float: left;
			margin: 0 0 0 3%;
			padding: 0 2% 0 0;
		}

		.form-group .col-xs-12 label {
			display: inline-block;
			float: left;
			margin: 0 0 0 3%;
			padding: 0 2% 0 0;
			width: 10%;
		}

		.has-success .form-control, .boxse .has-success .form-control:focus {
			border-color: #CCCCCC !important;
		}

		.gray-bg {
			background: white !important;
		}
	</style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="traBusinessBox" title="" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="关联方证件号码" col="5" formControl="true"/>
						<bf:text id="relNumber" name="relNumber" col="7" value="${traBusiness.relNumber}"
								 change="getRelInfo()" disabled="true">
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="交易对手类型" col="5" formControl="true"/>
						<bf:select id="relType" name="relType" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/INS-relType"
								   includes="" value="${traBusiness.relType}" disabled="true">
							<be:event type="select">
							</be:event>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="交易对手名称" col="5" formControl="true"/>
						<bf:text id="relName" name="relName" col="7" value="${traBusiness.relName}" change="getRelInfo()"
								 disabled="true">
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="4">
						<div title="本年度与该关联方发生的关联交易累计金额（万元/人民币）">
							<bc:label text="关联交易累计金额" col="5" formControl="true"/>
							<bf:text id="totolTradeAmount" name="totolTradeAmount" col="7"
									 value="${traBusiness.totolTradeAmount}" disabled="true">
							</bf:text>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="是否重大关联交易" col="5" formControl="true"/>
						<bf:select id="majorTrade" name="majorTrade" col="7" dataSource="fixedDict" url="IsStatusDict"
								   disabled="true"
								   value="${traBusiness.majorTrade}">
							<bf:option text="-" value=""></bf:option>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="口径" col="5" formControl="true"/>
						<bf:select id="caliber" name="caliber" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-Caliber" includes="" value="${traBusiness.caliber}"
								   disabled="true">
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="交易日期" col="5" formControl="true"/>
						<bf:datebox id="tranDate" name="tranDate" col="7" value="${traBusiness.tranDateString}" disabled="true"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="合同/交易编号" col="5" formControl="true"/>
						<bf:text id="contractNo" name="contractNo" col="7" value="${traBusiness.contractNo}" disabled="true">
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="交易金额（万元/人民币）" col="5" formControl="true"/>
						<bf:text id="tradeAmount" name="tradeAmount" col="7" value="${traBusiness.tradeAmount}"
								 change="isGreatTrade()" disabled="true">
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="业务起始日期" col="5" formControl="true"/>
						<bf:datebox id="busStartTime" name="busStartTime" col="7"
									value="${traBusiness.busStartTimeString}" disabled="true"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="业务终止日期" col="5" formControl="true"/>
						<bf:datebox id="busEndTime" name="busEndTime" col="7"
									value="${traBusiness.busEndTimeString}" disabled="true"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="业务类型" col="5" formControl="true"/>
						<bf:select id="busType" name="busType" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/INT-InsBusinessType"
								   includes="" value="${traBusiness.busType}" disabled="true">
							<bf:option text="请选择" value=""></bf:option>
							<be:event type="select">
							</be:event>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="统一交易协议业务情况" col="5" formControl="true"/>
						<bf:select id="unitfTradeAgrmentAbusiness" name="unitfTradeAgrmentAbusiness" col="7"
								   dataSource="custom" url="sys/dictItem/getDictItems/INT-InsTradeBusiness"
								   includes="" value="${traBusiness.unitfTradeAgrmentAbusiness}" disabled="true">
							<bf:option text="请选择" value=""></bf:option>
							<be:event type="select">
							</be:event>
						</bf:select>
					</bf:formGroup>
				<%--	<bf:formGroup col="4">
						<bc:label text="交易定价是否为国家规定" col="5" formControl="true"/>
						<bf:select id="isDealPrice" name="isDealPrice" col="7" dataSource="fixedDict" url="IsStatusDict"
								   value="${traBusiness.isDealPrice}" disabled="true">
							<bf:option text="请选择" value=""></bf:option>
							<bv:notEmpty/>
						</bf:select>
					</bf:formGroup>--%>
				</bc:row>
				<bf:formGroup hrp="bottom">
					<bf:formGroup col="6">
						<bc:label text="交易发生原因及合同、证明文件等" col="3" formControl="true"/>
						<bf:text id="tradeOccur" name="tradeOccur" col="9" rows="2" value="${traBusiness.tradeOccur}"
								 multiple="true" disabled="true">
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="6">
						<bc:label text="交易价格" col="3" formControl="true"/>
						<bf:text id="tradePrice" name="tradePrice" col="9" rows="2" value="${traBusiness.tradePrice}"
								 multiple="true" disabled="true">
						</bf:text>
					</bf:formGroup>
				</bf:formGroup>
				<bf:formGroup hrp="bottom">
					<bf:formGroup col="6">
						<bc:label text="定价政策" col="3" formControl="true"/>
						<bf:text id="pricingPolicy" name="pricingPolicy" col="9" rows="2" value="${traBusiness.pricingPolicy}"
								 multiple="true" disabled="true">
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="6">
						<bc:label text="交易标的" col="3" formControl="true"/>
						<bf:text id="tradeSubject" name="tradeSubject" col="9" rows="2" value="${traBusiness.tradeSubject}"
								 multiple="true" disabled="true">
						</bf:text>
					</bf:formGroup>
				</bf:formGroup>
				<bf:formGroup hrp="bottom">
					<bf:formGroup col="6">
						<bc:label text="其他事项说明" col="3" formControl="true"/>
						<bf:text id="remark" name="remark" col="9" rows="2" value="${traBusiness.remark}"
								 disabled="true" multiple="true">
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="6">

					</bf:formGroup>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-5">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/ins/traBusiness/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
				</bf:form>
			</bc:ibox>
		</div>
	</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>