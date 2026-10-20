<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>季报保险公司与单一法人主体资金运用关联关系统计表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="quarterlyLegalBox" title="季报保险公司与单一法人主体资金运用关联关系统计表" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="自增主键" col="2" formControl="true"/>
					<bc:label text="${quarterlyLegal.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联交易季报主键" col="2" formControl="true"/>
					<bc:label text="${quarterlyLegal.reportId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="法人主键" col="2" formControl="true"/>
					<bc:label text="${quarterlyLegal.corpId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="法人主体名称" col="2" formControl="true"/>
					<bc:label text="${quarterlyLegal.corpName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="法人统一社会信用代码" col="2" formControl="true"/>
					<bc:label text="${quarterlyLegal.corpUniscCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="法人境内外" col="2" formControl="true"/>
					<bc:label text="${quarterlyLegal.corpRegisteAddress}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="法人投资金额" col="2" formControl="true"/>
					<bc:label text="${quarterlyLegal.corpInvestFund}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="法人主体总资产" col="2" formControl="true"/>
					<bc:label text="${quarterlyLegal.corpTotalFund}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="法人投资金额占保险公司上年末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${quarterlyLegal.corpInvestYtotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="法人主体总资产占保险公司上年末总资产百分比" col="2" formControl="true"/>
					<bc:label text="${quarterlyLegal.corpAssetsYtotalPct}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/quarterlyLegal/list';
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