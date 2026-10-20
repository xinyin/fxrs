<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>关联关系表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="incidenceRelationBox" title="关联关系表" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="自增主键" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联主体pid" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.entityPid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联主体登记编码" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.entityRegCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联主体是类别（1-法人，2-自然人）" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.entityType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联主体名称" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.entityName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上层关联主体pid" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.superEntityPid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上层关联主体登记编码" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.superEntityRegCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上层关联主体是类别（1-法人，2-自然人）" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.superEntityType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上层关联主体名称" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.superEntityName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="主体与上层主体关联关系id" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.relationId}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="主体与上层主体关联关描述" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.relationDesc}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联关系说明" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.relationInfo}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否持有或控制上一级关联方股权(0-否，1-是)" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.isShareHolding}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="持股占比（默认为0）" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.holdingPercent}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="银行保险机构编码" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.orignalRegcode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="源主体（银行/保险机构）名称" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.orignalOrgName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联主体与银行保险机构关联关系" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.relationOrignal}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="报送版本号" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.reportVersion}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="上一层pid" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.parentLevelPid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="关联关系层级（1为根节点(银行/保险)，该表中只有以2开始的子节点）" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.level}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否有效 0-无效，1-有效" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.validStatus}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否被报送过（0-历史上从未报送过，1-已被报送过）" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.isReported}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="生效时间" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.activeTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="失效时间" col="2" formControl="true"/>
					<bc:label text="${incidenceRelation.invalidTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/incidenceRelation/list';
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