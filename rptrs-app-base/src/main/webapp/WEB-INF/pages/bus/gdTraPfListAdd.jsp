<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="list"/>
	<title>GdTraPf</title>
<style>
	.leftof .col-xs-3{
      margin-left: 3.5%;
	}
</style>
</head>
<body class="gray-bg" style="background: white">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup hrp="bottom">
						<bf:formGroup col="4">
							<bc:label text="派发方案名称" col="5" formControl="true"/>
							<bf:text name="name" col="7" placeholder="派发方案名称"/>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="派发年份" col="5" formControl="true"/>
							<bf:datebox id="year" name="year" col="7" format="date" value="${gdTraPf.operationAmount}"/>
							<%--<bf:text name="year" col="7" placeholder="派发年份"/>--%>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="股利分配比例" col="5" formControl="true"/>
							<bf:text name="allocationProportion" col="7" placeholder="股利分配比例"/>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="现金分红" col="5" formControl="true"/>
							<bf:text name="cashBonus" col="7" placeholder="现金分红"/>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="派送红股" col="5" formControl="true"/>
							<bf:text name="bonusIssue" col="7" placeholder="股东代码"/>
						</bf:formGroup>
						<div class="leftof">
							<bc:col col="3" colOffset="0">
								<bc:button id="btnQuery" type="danger" text="保存" icon="search">
									<be:event>
										$('#editForm').data('formValidation').validate();
										var isValid = $('#editForm').data('formValidation').isValid();
										if(isValid)
										{
										var formData = $('#editForm').serializeAll();
										$.mask.post('${base}/bus/gdTraPf/save', formData, function(data){
										if(data.success)
										{
										$.messager.alert('提示', data.msg, 'info', function(){
										window.location = '${base}/bus/gdTraPf/list';
										});
										}
										else
										{
										$.messager.alert('错误', data.msg, 'error');
										}
										});
										}
									</be:event>
								</bc:button>

								<bc:button id="btnReset" type="primary" text="取消" icon="refresh"/>
							</bc:col>
						</div>

					</bf:formGroup>
					<bf:formGroup  hrp="bottom" >
						<div class="lhight" >
							<bf:formGroup col="4">
								<bc:label text="股东代码" col="5" formControl="true"/>
								<bf:text name="__LIKES_shareholdersCode" col="7" placeholder="股东代码"/>
							</bf:formGroup>
							<bf:formGroup col="4">
								<bc:label text="股东名称" col="5" formControl="true"/>
								<bf:text name="__LIKES_shareholdersName" col="7" placeholder="股东名称"/>
							</bf:formGroup>
							<bf:formGroup col="4">
								<bc:label text="境内-境外" col="5" formControl="true"/>
								<%--<bf:text name="shareholdersName" col="7" placeholder="股东名称"/>--%>
								<bf:select name="__LIKES_domesticOrForeign" col="7" dataSource="custom"  >
									<bf:option text="ALL" value=""></bf:option>
									<bf:option text="境内" value="%"></bf:option>
									<bf:option text="境外" value="%"></bf:option>
									<be:event type="select">
										var input = $("#domesticOrForeign");
										input.focus();
										input.blur();
									</be:event>
								</bf:select>

							</bf:formGroup>
						</div>
					</bf:formGroup>
				</bc:row>
			</bf:form>
             <div style="margin: -3% 0 0 3.3%;">
				 <bc:col col="4" colOffset="0">
					 <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
					 <bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
				 </bc:col>
			 </div>

			<bc:table id="gdTraPfTable" idField="pid" url="${base}/bus/gdTraPf">
				<%--<bc:toolbar id="add" text="新增" type="button"/>--%>
				<%--<bc:column field="pid" title="主键" width="100"/>--%>
				<bc:column field="name" title="派发方案名称" width="100"/>
				<bc:column field="year" title="派发年份" width="100"/>
				<bc:column field="createTime" title="文件生成时间" width="100"/>
				<bc:column field="totalShare" title="当前总股本" width="100"/>
				<bc:column field="allocationAmount" title="股利分配额" width="100"/>
				<bc:column field="allocationProportion" title="股利分配比例" width="100"/>
				<bc:column field="cashBonus" title="现金分红" width="100"/>
				<bc:column field="bonusIssue" title="派送红股" width="100"/>
				<bc:column field="status" title="审批状态" width="100"/>
				<bc:column field="route" title="路径" width="100"/>
				<bc:operator id="view" icon="search" title="查看"/>
				<bc:operator id="edit" icon="edit" title="编辑"/>
				<bc:operator id="remove" icon="trash" title="移除"/>
			</bc:table>
			<div style="margin: -3% 0 0 0;">
				<bf:formGroup   hrp="bottom">
					<bc:col col="3" colOffset="4">
						<bc:button id="btnQuery" type="danger" text="生成分配方案" icon="search"/>
						<bc:button id="btnReset" type="primary" text="取消" icon="refresh"/>
					</bc:col>
				</bf:formGroup>
			</div>

		</bc:ibox>

	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>

</body>
</html>