<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>业务模块</title>
</head>
<body class="gray-bg">
<!-- 整体布局 -->
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox id="iboxbizModuleDlg" title="" col="12" collapsible="false">
			<div class="row">
				<bc:col col="12">
					<bc:tree id="bizModuleTree" url="${base}/workflow/bizModule"
						autoParam="id=parentId"  alertType="toastr">
					</bc:tree>
				</bc:col>
			</div>
			<div class="row">
				<bc:col col="4" colOffset="8">
					<bc:button text="取消" >
						<be:event>
							$.messager.hide('bizModuleSelect');
						</be:event>
					</bc:button>
					<bc:button text="确定"  type="primary">
						<be:event>
							var nodes = $('#bizModuleTree').jTree('getSelectedNodes');
							$.messager.args('bizModuleSelect').result([{
								'moduleCode': nodes[0].code,
								'moduleName': nodes[0].name
							}]);
							$.messager.hide('bizModuleSelect');
						</be:event>
					</bc:button>
				</bc:col>
			</div>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
$(document).ready(function(){
	var treeObj = $.fn.zTree.getZTreeObj("_jTree_bizModuleTree");
	treeObj.setting.callback.onAsyncSuccess = function(event, treeId, treeNode, msg){
		/* 设置树为展开 */
		treeObj.expandAll(true);
		
		/* 选中默认值  */
		var treeNode = treeObj.getNodeByParam("id", '${code}', null);
		treeObj.selectNode(treeNode);
		
		/* 设置选择子节点 */
		treeObj.setting.callback.beforeClick = function(treeId, treeNode){
			console.log(treeNode);
			if(treeNode.children == undefined){
				return true;
			}
			else if(treeNode.children.length == 0){
				return true;
			}
			else
			{
				 $.messager.toastr('提示', '请选择子节点！', 'warning');
				return false;
			}
		}
	}
	
});
</script>
</body>
</html>