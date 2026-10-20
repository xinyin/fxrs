<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="../include/common.jsp"%>
<br:css pack="list"/>
<title>规则表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="规则库" subTitle="这里维护着所有规则" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="规则名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_name" col="7" placeholder="名称"/>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" type="primary" text="查询" icon="search"/>
						<bc:button id="btnReset" type="default" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="rulesTable" idField="id" url="${base}/rul/rules">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="content" text="维护内容" type="button" handler="contentEdit"/>
				<bc:column field="code" title="规则编码" width="100"/>
				<bc:column field="name" title="规则名称" width="100"/>
				<bc:column field="priority" title="规则优先级" width="100"/>
				<bc:column field="remark" title="备注" width="100"/>
				<bc:operator id="view" icon="search" title="查看"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script>
	// 维护规则内容
	function contentEdit(){
        var rows= $("#rulesTable").jTable("getSelections");
        if(!rows[0]){
            $.messager.toastr('请选择要编辑的规则');
        }else{
            var title=rows[0].name+"编辑";
            var ruleId=rows[0].id;
            console.debug(ruleId);
            window.location = "${base}/rul/rules/design?ruleId="+ruleId;
        }
	}
</script>
</body>
</html>