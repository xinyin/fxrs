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
			<%--隐藏标签  用于必须字段--%>
			<bf:hidden id="pid" name="pid" value="${relPsnSuspect.pid}"/>
			<bc:ibox id="relPsnSuspectBox" title="关联关系自然人表" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="姓名:" col="4" formControl="true"/>
						<bc:label text="${relPsnSuspect.name}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="性別:" col="4" formControl="true"/>
						<bf:select id="sex" name="sex" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Sex" value="${relPsnSuspect.sex}">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="机构名称:" col="4" formControl="true"/>
						<bf:select id="mgerOrg" name="mgerOrg" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relPsnSuspect.mgerOrg}">
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="民族:" col="4" formControl="true"/>
						<bf:select id="race" name="race" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Race" value="${relPsnSuspect.race}">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证件类型:" col="4" formControl="true"/>
						<bf:select id="identifyType" name="identifyType" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relPsnSuspect.identifyType}">
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证件号码:" col="4" formControl="true"/>
						<bc:label text="${relPsnSuspect.identifyNumber}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="境内外标识:" col="4" formControl="true"/>
						<bf:select id="domesticOrForeign" name="domesticOrForeign" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" value="${relPsnSuspect.domesticOrForeign}">
						</bf:select>
					</bc:col>
					<bc:col col="6">
						<bc:label text="婚姻状况:" col="4" formControl="true"/>
						<bf:select id="maritalStatus" name="maritalStatus" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-MaritalStatus" value="${relPsnSuspect.maritalStatus}">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="移动电话:" col="4" formControl="true"/>
						<bc:label text="${relPsnSuspect.mobile}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="邮箱:" col="4" formControl="true"/>
						<bc:label text="${relPsnSuspect.email}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="出生日期:" col="4" formControl="true"/>
						<bc:label text="${relPsnSuspect.birthdate}" col="8" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/relPsnSuspect/list';
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