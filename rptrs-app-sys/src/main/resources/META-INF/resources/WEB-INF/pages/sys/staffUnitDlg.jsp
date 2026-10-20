<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>更改用户所在部门</title>
</head>
<body class="white-bg">
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
            <div class="row" align="center">
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
                        $.messager.toastr('提示', '请选择具体的部门添加用户','info');
                        }
                        else
                        {
                        $.messager.args('unitSelect').result([{
                        'unitCode': nodes[0].unitCode,
                        'unitName': nodes[0].unitName,
                        'hierarchy': nodes[0].hierarchy
                        }]);
                        $.messager.hide('unitSelect');
                        }
                        }
                        });
                    </be:event>
                </bc:button>
                <bc:button text="取消">
                    <be:event>
                        $.messager.hide('unitSelect');
                    </be:event>
                </bc:button>
            </div>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>