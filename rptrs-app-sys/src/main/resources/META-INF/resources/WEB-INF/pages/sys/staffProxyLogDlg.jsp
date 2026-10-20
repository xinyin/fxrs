<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>更改用户借调部门</title>
</head>
<body class="gray-bg">
<!-- 整体布局 -->
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="iboxUnitDlg" title="" col="12" collapsible="false">
            <div class="row">
                <bc:col col="12">
                    <bc:tree id="tree" url="${base}/sys/unit" edit="false"
                             autoParam="id=parentId" alertType="toastr">
                    </bc:tree>
                </bc:col>
            </div>
            <div class="row">
                <bc:col col="4" colOffset="8">
                    <bc:button text="取消" outline="true">
                        <be:event>
                            $.messager.hide('winProxyDlg');
                        </be:event>
                    </bc:button>
                    <bc:button text="确定" type="primary">
                        <be:event>
                            var nodes = $('#tree').jTree('getSelectedNodes');
                            $.ajax({
                            type: 'GET',
                            url: '${base}/sys/unit/isOrg?unitCode=' + nodes[0].unitCode,
                            dataType: 'json',
                            success: function(data){
                            if(data.valid)
                            {
                            $.messager.toastr('提示', '请选择具体的部门进行借调');
                            }
                            else
                            {
                            var deptCode = '${deptCode}';
                            if(deptCode == nodes[0].unitCode)
                            {
                            $.messager.toastr('提示', '同一部门不需要借调');
                            }
                            else
                            {
                            $.messager.args('winProxyDlg').result([{
                            'unitCode': nodes[0].unitCode,
                            'unitName': nodes[0].unitName,
                            'displayName': nodes[0].displayName,
                            }]);
                            $.messager.hide('winProxyDlg');
                            }
                            }
                            }
                            });
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