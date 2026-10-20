<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>单笔关联交易对手方</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="singleRelatedPartyBox" title="单笔关联交易对手方" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="物理主键，自增" col="2" formControl="true"/>
					<bc:label text="${singleRelatedParty.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易id" col="2" formControl="true"/>
					<bc:label text="${singleRelatedParty.transId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="报送记录版本" col="2" formControl="true"/>
					<bc:label text="${singleRelatedParty.reportVersion}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联方类别（1-机构，2-人）" col="2" formControl="true"/>
					<bc:label text="${singleRelatedParty.counterpartyType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联方pid（当交易对手为机构时，对应法人信息表的pid；为个人时，对应人员信息表的pid）" col="2" formControl="true"/>
					<bc:label text="${singleRelatedParty.counterparty}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易对手名称" col="2" formControl="true"/>
					<bc:label text="${singleRelatedParty.counterpartyName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证件类型" col="2" formControl="true"/>
					<bc:label text="${singleRelatedParty.identifyType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="境内外" col="2" formControl="true"/>
					<bc:label text="${singleRelatedParty.domesticOrForeign}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证件号码" col="2" formControl="true"/>
					<bc:label text="${singleRelatedParty.identifyNumber}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="统一社会信用代码" col="2" formControl="true"/>
					<bc:label text="${singleRelatedParty.uniscCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易本年度累计金额" col="2" formControl="true"/>
					<bc:label text="${singleRelatedParty.transCountYear}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/singleRelatedParty/list';
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