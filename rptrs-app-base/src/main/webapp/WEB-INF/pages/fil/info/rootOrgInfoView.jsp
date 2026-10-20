<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>备案基础信息表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="rootOrgInfoBox" title="备案基础信息表" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="主键" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="备案编号" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.filNum}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联机构主键" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.relOrgId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="总股数" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.totalShareAmount}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="资本净额" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.netCaptial}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="总资产" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.totalCapital}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="冻结股数(股)" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.frozenShareAmount}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="质押股数(股)" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.pledgedShareAmount}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上季末总资产" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.endLastQuarter}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上年末总资产" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.endLastYear}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上年末净资产" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.netCaptialLastYear}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上季末净资产" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.netCaptialLastQuarter}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="季度综合偿付能力充足率" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.quarterCompreSolvencyRate}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="年度" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.years}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="季度" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.quarter}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="审批状态" col="2" formControl="true"/>
					<bc:label text="${rootOrgInfo.approvalStatus}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/fil/rootOrgInfo/list';
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