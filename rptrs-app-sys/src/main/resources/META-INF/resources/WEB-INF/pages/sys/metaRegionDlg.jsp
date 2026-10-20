<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>数据区域选择</title>
</head>
<body class="gray-bg">
<!-- 整体布局 -->
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="iboxmetaRegionDlg" title="" col="12" collapsible="false">
            <div class="row" style="min-height: 300px;">
                <bc:col col="12">
                    <bc:tree id="tree" url="${base}/sys/dataAnalysis" queryUrl="queryMetaregionTree" edit="false"
                             autoParam="id=id" alertType="toastr">
                    </bc:tree>
                </bc:col>
            </div>
            <div class="row">
                <bc:col col="4" colOffset="8">
                    <bc:button text="取消" outline="true">
                        <be:event>
                            $.messager.hide('winMetaRegionDlg');
                        </be:event>
                    </bc:button>
                    <bc:button text="确定" type="primary">
                        <be:event>
                            var nodes = $('#tree').jTree('getSelectedNodes');
                            if(nodes.length != 1){
                            $.messager.toastr('提示', '请选择一个数据区域。', 'info');
                            }
                            else
                            {
                            console.info(nodes[0].id);
                            $.messager.args('winMetaRegionDlg').result([{
                            'regionId': nodes[0].id,
                            'regionName': nodes[0].name

                            }]);
                            $.messager.hide('winMetaRegionDlg');
                            }
                        </be:event>
                    </bc:button>
                </bc:col>
            </div>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>