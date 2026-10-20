<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>关联关系自然人表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="relPsnBox" title="关联关系自然人表" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="自增长主键" col="2" formControl="true"/>
					<bc:label text="${relPsn.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="登记编码" col="2" formControl="true"/>
					<bc:label text="${relPsn.regCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="源主体银行机构" col="2" formControl="true"/>
					<bc:label text="${relPsn.orignalOrg}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="源主体银行机构名称" col="2" formControl="true"/>
					<bc:label text="${relPsn.orignalOrgName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="姓名" col="2" formControl="true"/>
					<bc:label text="${relPsn.name}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="性別" col="2" formControl="true"/>
					<bc:label text="${relPsn.sex}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="国别/地区" col="2" formControl="true"/>
					<bc:label text="${relPsn.nationality}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="境内外标识" col="2" formControl="true"/>
					<bc:label text="${relPsn.domesticOrForeign}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="民族" col="2" formControl="true"/>
					<bc:label text="${relPsn.race}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证件类型" col="2" formControl="true"/>
					<bc:label text="${relPsn.identifyType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证件号码" col="2" formControl="true"/>
					<bc:label text="${relPsn.identifyNumber}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="出生日期" col="2" formControl="true"/>
					<bc:label text="${relPsn.birthdate}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="移动电话" col="2" formControl="true"/>
					<bc:label text="${relPsn.mobile}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="邮箱" col="2" formControl="true"/>
					<bc:label text="${relPsn.email}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="职位" col="2" formControl="true"/>
					<bc:label text="${relPsn.dutyCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="操作状态    未提交/已提交/未审批" col="2" formControl="true"/>
					<bc:label text="${relPsn.status}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="操作状态说明" col="2" formControl="true"/>
					<bc:label text="${relPsn.statusComment}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="婚姻状况" col="2" formControl="true"/>
					<bc:label text="${relPsn.maritalStatus}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否有效   0|无效1|有效" col="2" formControl="true"/>
					<bc:label text="${relPsn.validStatus}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="生效时间" col="2" formControl="true"/>
					<bc:label text="${relPsn.activeTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="失效时间" col="2" formControl="true"/>
					<bc:label text="${relPsn.invalidTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="机构名称(默认是总行)" col="2" formControl="true"/>
					<bc:label text="${relPsn.mgerOrg}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上一级审批部门" col="2" formControl="true"/>
					<bc:label text="${relPsn.upApprovalDepartment}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="下一级审批部门" col="2" formControl="true"/>
					<bc:label text="${relPsn.nextApprovalDepartment}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上一级审批人" col="2" formControl="true"/>
					<bc:label text="${relPsn.upApprovalPeople}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="下一级审批人" col="2" formControl="true"/>
					<bc:label text="${relPsn.nextApprovalPeople}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="银保监口径  0|否 1|是" col="2" formControl="true"/>
					<bc:label text="${relPsn.regulatorYbj}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证监会口径（0|否1|是）" col="2" formControl="true"/>
					<bc:label text="${relPsn.regulatorZjh}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="财政部口径（0|否1|是）" col="2" formControl="true"/>
					<bc:label text="${relPsn.regulatorCzb}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其他口径1（0|否1|是）" col="2" formControl="true"/>
					<bc:label text="${relPsn.regulatorOth1}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="其他口径2（0|否1|是）" col="2" formControl="true"/>
					<bc:label text="${relPsn.regulatorOth2}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否黑名单（0|否1|是）" col="2" formControl="true"/>
					<bc:label text="${relPsn.isBlack}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/relPsn/list';
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