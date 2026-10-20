<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title></title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="relOrgBox" title="" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="自增主键" col="2" formControl="true"/>
					<bc:label text="${relOrg.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="登记编码" col="2" formControl="true"/>
					<bc:label text="${relOrg.regCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="源主体银行机构" col="2" formControl="true"/>
					<bc:label text="${relOrg.orignalOrg}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="源主体银行机构名称" col="2" formControl="true"/>
					<bc:label text="${relOrg.orignalOrgName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="企业名称" col="2" formControl="true"/>
					<bc:label text="${relOrg.orgName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="企业类型" col="2" formControl="true"/>
					<bc:label text="${relOrg.orgType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="境内外标识(1-境内，2-境外)" col="2" formControl="true"/>
					<bc:label text="${relOrg.domesticOrForeign}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证件类型" col="2" formControl="true"/>
					<bc:label text="${relOrg.identifyType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="社会统一信用代码" col="2" formControl="true"/>
					<bc:label text="${relOrg.uniscCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="注册资本(万元/人民币)" col="2" formControl="true"/>
					<bc:label text="${relOrg.regCapital}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="注册资本币种代码" col="2" formControl="true"/>
					<bc:label text="${relOrg.regCapitalCurrency}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="营业范围" col="2" formControl="true"/>
					<bc:label text="${relOrg.businessScope}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="机构状态（1-正常，2-停业）" col="2" formControl="true"/>
					<bc:label text="${relOrg.status}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="法定代表人" col="2" formControl="true"/>
					<bc:label text="${relOrg.legalPerson}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="注册地" col="2" formControl="true"/>
					<bc:label text="${relOrg.regAddress}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否有效 0-无效，1-有效" col="2" formControl="true"/>
					<bc:label text="${relOrg.validStatus}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="生效时间" col="2" formControl="true"/>
					<bc:label text="${relOrg.activeTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="失效时间" col="2" formControl="true"/>
					<bc:label text="${relOrg.invalidTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="机构名称(默认是总行)" col="2" formControl="true"/>
					<bc:label text="${relOrg.mgerOrg}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="操作状态    未提交/已提交/未审批" col="2" formControl="true"/>
					<bc:label text="${relOrg.approvalStatus}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="操作状态说明" col="2" formControl="true"/>
					<bc:label text="${relOrg.statusComment}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上一级审批部门" col="2" formControl="true"/>
					<bc:label text="${relOrg.upApprovalDepartment}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="下一级审批部门" col="2" formControl="true"/>
					<bc:label text="${relOrg.nextApprovalDepartment}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上一级审批人" col="2" formControl="true"/>
					<bc:label text="${relOrg.upApprovalPeople}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="下一级审批人" col="2" formControl="true"/>
					<bc:label text="${relOrg.nextApprovalPeople}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证监会口径（0|否1|是）" col="2" formControl="true"/>
					<bc:label text="${relOrg.regulatorZjh}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="财政部口径（0|否1|是）" col="2" formControl="true"/>
					<bc:label text="${relOrg.regulatorCzb}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其他口径1（0|否1|是）" col="2" formControl="true"/>
					<bc:label text="${relOrg.regulatorOth1}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其他口径2（0|否1|是）" col="2" formControl="true"/>
					<bc:label text="${relOrg.regulatorOth2}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否黑名单（0|否1|是）" col="2" formControl="true"/>
					<bc:label text="${relOrg.isBlack}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/relOrg/list';
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