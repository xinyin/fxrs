<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>Rel</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="relBox" title="Rel" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="自增主键" col="2" formControl="true"/>
					<bc:label text="${rel.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联关系pid" col="2" formControl="true"/>
					<bc:label text="${rel.toPid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="源主体pid" col="2" formControl="true"/>
					<bc:label text="${rel.fromPid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关系类型" col="2" formControl="true"/>
					<bc:label text="${rel.relCategory}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关系子类型" col="2" formControl="true"/>
					<bc:label text="${rel.relCategorySub}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="源主体类型  PSN|个人 ORG|机构" col="2" formControl="true"/>
					<bc:label text="${rel.fromType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联关系主体类型   PSN|个人 ORG|机构" col="2" formControl="true"/>
					<bc:label text="${rel.toType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="源主体显示名称" col="2" formControl="true"/>
					<bc:label text="${rel.fromMemo}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联关系主体显示名称" col="2" formControl="true"/>
					<bc:label text="${rel.toMemo}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关系类别" col="2" formControl="true"/>
					<bc:label text="${rel.relSort}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="职位" col="2" formControl="true"/>
					<bc:label text="${rel.position}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="亲属关系" col="2" formControl="true"/>
					<bc:label text="${rel.relation}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="持股比例" col="2" formControl="true"/>
					<bc:label text="${rel.shareholdingRatio}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否关联关系主体股东    0-否 1-是" col="2" formControl="true"/>
					<bc:label text="${rel.toPidShareholders}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否显示" col="2" formControl="true"/>
					<bc:label text="${rel.isShow}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关系层级" col="2" formControl="true"/>
					<bc:label text="${rel.level}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="解除原因" col="2" formControl="true"/>
					<bc:label text="${rel.liftReason}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/rel/list';
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