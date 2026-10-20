<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>业务种类</title>
</head>
<body class="gray-bg">
<!-- 整体布局 -->
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="iboxbizTypeDlg" title="" col="12" collapsible="false">
            <div class="row">
                <bc:col col="12">
                    <bc:tree id="tree" url="${base}/sys/bizType" edit="false"
                             autoParam="id=parentId" alertType="toastr" check="true">
                    </bc:tree>
                </bc:col>
            </div>
            <div class="row">
                <bc:col col="4" colOffset="8">
                    <bc:button text="取消" outline="true">
                        <be:event>
                            $.messager.hide('bizTypeSelect');
                        </be:event>
                    </bc:button>
                    <bc:button text="确定" type="primary">
                        <be:event>
                            var nodes = $('#tree').jTree('getCheckedNodes');
                            var codeList = new Array();
                            var nameList = new Array();
                            $.each(nodes, function(i, val){
                            codeList.push(val.bizTypeCode);
                            nameList.push(val.bizTypeName);
                            });

                            $.messager.args('bizTypeSelect').result([{
                            'bizTypeCode': codeList,
                            'bizTypeName': nameList
                            }]);
                            $.messager.hide('bizTypeSelect');
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
    $(document).ready(function () {

// 	var treeObj = $.fn.zTree.getZTreeObj("_jTree_tree");

        /*禁用树节点的URL*/
// 	treeObj.setting.data.key.url = "fasle";

        /*异步加载数据成功后，勾选中已设置的菜单*/
        /* treeObj.setting.callback.onAsyncSuccess = function(event, treeId, treeNode, msg){

            var jsonMsg = JSON.parse(msg);

            var nodes = '
        ${nodes}'
		var json = JSON.parse(nodes);
		
		for(var i = 0; i < json.length; i++)
		{
			for(var j = 0; j < jsonMsg.length; j++)
			{
				if(json[i].id == jsonMsg[j].id)
				{
					var node = treeObj.getNodeByParam('id', json[i].id);
					if(node != null){
						treeObj.checkNode(node);
					}
				}
			}
		}
	} */
    });
</script>
</body>
</html>