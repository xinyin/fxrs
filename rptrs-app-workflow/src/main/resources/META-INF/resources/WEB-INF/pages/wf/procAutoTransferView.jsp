<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>流程自动转办定义</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="procAutoTransferBox" title="流程自动转办定义" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<%-- <bf:formGroup>
					<bc:label text="用户编码" col="2" formControl="true"/>
					<bc:label text="${procAutoTransfer.staffCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<bc:label text="用户名称" col="2" formControl="true"/>
					<bc:label text="${procAutoTransfer.staffName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup> --%>
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="转办用户编码" col="6" formControl="true"/>
						<bc:label text="${procAutoTransfer.transferStaffCode}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="转办用户名称" col="6" formControl="true"/>
						<bc:label text="${procAutoTransfer.transferStaffName}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="开始时间" col="6" formControl="true"/>
						<bc:label text="${procAutoTransfer.startTimeString}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
					<bc:col col="6">
						<bc:label text="结束时间" col="6" formControl="true"/>
						<bc:label text="${procAutoTransfer.endTimeString}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				
				<bf:formGroup>
					<bc:col col="6">
						<bc:label text="备注" col="6" formControl="true"/>
						<bc:label text="${procAutoTransfer.remark}" col="6" formControl="true" style="text-align:left"/>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div align="center">
						<bc:button text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/wf/procAutoTransfer/list';
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