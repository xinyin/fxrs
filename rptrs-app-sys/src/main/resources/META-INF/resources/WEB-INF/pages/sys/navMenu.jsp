<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>新增角色类型</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    var menus = '${menuList}';
    var data = JSON.parse(menus);
    $(document).ready(function () {

        //初始化页面
        initMenu();
    });

    //初始化页面菜单模块
    function initMenu() {
        //循环获取导航菜单
        var html = '';
        $.each(data, function (i, v) {
            if (i == 0 && i % 3 == 1) {
                html += '<div class="row">';
            }

            html += '<div class="col-sm-4 ">';
            html += '<div id="iboxRoleStd" class="ibox float-e-margins">';
            html += '<div class="ibox-title"><h5>' + v.menuName + '</h5></div>';
            html += '<div class="ibox-content"><div class="panel-body">';
            html += '<label  class="control-label col-sm-6"><a href="javaScript: void(0);" onClick="clickMenu(\'' + v.menuCode + '\', \'' + v.menuName + '\', \'' + v.url + '\');">点击进入</a></label>';
            html += '</div></div></div></div>';

            if (i != 0 && i % 3 == 0) {
                html += '</div>';
            }
        });
        html += '</div>';
        $('#wrapper').html(html);
    }

    //点击事件处理
    function clickMenu(code, name, url) {
        /*重新打开当前页面*/
        $(window.parent.setNavMenu(code, name, url));
    }
</script>
</body>
</html>