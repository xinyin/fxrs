<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="../include/common.jsp"%>
<br:css pack="list"/>
<title>规则集和规则映射</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="规则映射" subTitle="规则模型和规则的关联" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="规则" col="5" formControl="true"/>
						<bf:text name="__LIKES_ruleName" col="7" placeholder="规则"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="primary" text="查询" icon="search"/>
						<bc:button id="btnReset" type="default" text="重置" icon="refresh"/>
						<bc:button id="btnBack" type="default" text="返回" icon="arrow-circle-left">
							<be:event>
									window.location = "${base}/rul/packages/list"
							</be:event>
						</bc:button>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="pkgRuleMapTable" idField="id" url="${base}/rul/pkgRuleMap" queryUrl="query?__EQS_pkgId=${packagesId}">
				<bc:toolbar id="select" text="增加" type="button" handler="openSelectWin"/>
				<bc:column field="ruleName" title="规则" width="200"/>
				<%--<bc:operator id="view" icon="search" title="查看"/>--%>
				<bc:operator id="remove" icon="trash" title="移除"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script>
    function openSelectWin() {
        $.messager.modal({
            winId: 'winObjsAdd',
            width: 800,
            height: 450,
            title: '选择规则库',
            url: "${base}/rul/rules/select?packagesId=" + '${packagesId}',
            onClose: function(){
                $("#pkgRuleMapTable").bootstrapTable('refresh');
            }
        });
    }
</script>
</body>
</html>