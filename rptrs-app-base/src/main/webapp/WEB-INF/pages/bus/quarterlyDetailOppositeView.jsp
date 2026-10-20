<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>关联交易季报-关联交易明细表-交易对手情况</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="quarterlyDetailOppositeBox" title="关联交易季报-关联交易明细表-交易对手情况" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="自增主键" col="2" formControl="true"/>
					<bc:label text="${quarterlyDetailOpposite.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="交易编码（与季报关联交易明细表关联）" col="2" formControl="true"/>
					<bc:label text="${quarterlyDetailOpposite.transNum}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联方类型" col="2" formControl="true"/>
					<bc:label text="${quarterlyDetailOpposite.relatedType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联方主键（与关联方档案关联）" col="2" formControl="true"/>
					<bc:label text="${quarterlyDetailOpposite.relatedId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联方名称" col="2" formControl="true"/>
					<bc:label text="${quarterlyDetailOpposite.relatedName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联方证件类型编码" col="2" formControl="true"/>
					<bc:label text="${quarterlyDetailOpposite.relatedCredentialsType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联方证件类型名称" col="2" formControl="true"/>
					<bc:label text="${quarterlyDetailOpposite.relatedCredentialsTypeName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联方证件号码" col="2" formControl="true"/>
					<bc:label text="${quarterlyDetailOpposite.relatedCredentialsNum}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="统一社会信用代码" col="2" formControl="true"/>
					<bc:label text="${quarterlyDetailOpposite.relatedUniscCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/quarterlyDetailOpposite/list';
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