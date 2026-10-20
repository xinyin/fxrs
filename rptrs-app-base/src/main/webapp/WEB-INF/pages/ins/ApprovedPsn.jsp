<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>选择人员</title>
</head>
<body class="gray-bg">
<!-- 整体布局 -->
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:col col="3">
			<div id="unitTree" data-toggle="jTree" data-auto-param="id=parentId" 
				data-alert-type="toastr" data-on-click="onClick" ></div>
		</bc:col>
		<div class="col-sm-9">
			<bf:form id="queryForm" validation="true">
				<bf:hidden name="__LIKES_status" value="ENABLE"/>
				<bc:row cls="m-b">
					<bf:formGroup col="8">
						<bc:col col="6">
							<bc:label text="用户编码" col="4" formControl="true"></bc:label>
							<bf:text name="__LIKES_staffCode" col="8">
								<bv:jepCode/>
							</bf:text>
						</bc:col>
						<bc:col col="6">
							<bc:label text="用户姓名" col="4" formControl="true"></bc:label>
							<bf:text name="__LIKES_staffName" col="8"/>
						</bc:col>
					</bf:formGroup>
					<bc:col col="3" colOffset="1">
						<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
						<bc:button id="btnReset" text="重置" icon="refresh"/>
					</bc:col>
				</bc:row>
			</bf:form>
			<bc:table id="staffTable" idField="staffCode" url="${base}/sys/staff"
				childField="deptCode" childFieldType="S" parentType="tree" parentId="unitTree" parentField="unitCode" 
				multiple="${mult}" selectFirst="false">
				<bc:toolbar id="setRoleGroup" text="提交" type="button" handler="selectStaff"/>
				<bc:column field="staffCode" title="用户编码" width="100"/>
				<bc:column field="staffName" title="用户名称" width="100"/>
			</bc:table>
		</div>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
	var unitCode = undefined;
/* 右边列表页面 JS*/
function onClick(event, treeId, treeNode){
	unitCode = treeNode.unitCode
	}

function selectStaff(){
	/* 判断是否是多选 */
	if(${mult}){
		var selectedStaffs =  $('#staffTable').jTable('getCheckData');
		if(selectedStaffs.length == 0){
			$.messager.alert('提示', '请选择人员。', 'info');
			return;
		}
		var codeList = new Array();
		var nameList = new Array();
		$.each(selectedStaffs, function(i, val){
			codeList.push(val.staffCode);
			nameList.push(val.staffName);
		});
		
		$.messager.args('Approved').result({
			'staffCode': codeList,
			'staffName': nameList
		});
	}
	else
	{
		var selectedStaffs =  $('#staffTable').bootstrapTable('getSelections');
		if(selectedStaffs.length == 0){
			$.messager.alert('提示', '请选择人员。', 'info');
			return;
		}
		else
		{
			$.messager.args('Approved').result({
				'staffCode': selectedStaffs[0].staffCode,
				'staffName': selectedStaffs[0].staffName
			});
		}
		
	}
		
	$.messager.hide('Approved');
}

/* 左边树相关js */
var _URL = '${base}/sys/unit/selectUnit/';
var args;
$(document).ready(function(){
	args = $.messager.args('Approved');
	var options = args2options(args);
	//重新加载unitTree
	$('#unitTree').jTree('reload', options);
	
	$('th[data-field="state"]').removeAttr('data-radio');
	$('th[data-field="state"]').attr('data-checkbox', 'true');
	
});




/* 根据传入的参数设置对应的属性 */
function args2options(args){
	
	/* 初始化属性集*/
	var options = {toggle: "jTree" };
	/* 是否传入参数，如果没有传人，则设置为默认参数 */
	if(args){
		/* 根据参数判断是否是懒加载 */
		if(args['lazy']=="false" || args['lazy'] == false){
			options['lazy'] = false;
			options['url'] = _URL + 'eager';
			
			/* 获取包含参数 */
			if(args['includes']){
				if(options['otherParam']){
					options['otherParam']['includes']  = JSON.stringify(args['includes']);
				}
				else
				{
					options['otherParam'] = {'includes' : JSON.stringify(args['includes'])};
				}
			}
			
			/* 获取排除参数 */
			if(args['excludes']){
				if(options['otherParam']){
					options['otherParam']['excludes']  = JSON.stringify(args['excludes']);
				}
				else
				{
					options['otherParam'] = {'excludes' : JSON.stringify(args['excludes'])};
				}
			}
		}
		else
		{
			options['url'] = _URL + 'lazy';
			/* 获取包含参数 */
			if(args['includes']){
				/* 判断otherParam是否是一个数组 */
				if(jQuery.isArray(options['otherParam'])){
					options['otherParam'].push('includes');
					options['otherParam'].push(JSON.stringify(args['includes']));
				}
				else
				{
					options['otherParam'] = ['includes', JSON.stringify(args['includes'])];
				}
				
			}
			
			/* 获取排除参数 */
			if(args['excludes']){
				/* 判断otherParam是否是一个数组 */
				if(jQuery.isArray(options['otherParam'])){
					options['otherParam'].push('excludes');
					options['otherParam'].push(JSON.stringify(args['excludes']));
				}
				else
				{
					options['otherParam'] = ['excludes', JSON.stringify(args['excludes'])];
				}
			}
		}
	}
	return options;
}
</script>
</body>
</html>