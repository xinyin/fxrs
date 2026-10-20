<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="basic"/>
    <title>关联交易合规管理平台</title>
    <style type="text/css">
        .navbar-right > a:hover {
            background-color: red;
        }

    </style>
</head>
<body class="skin-1 skin-light" style="overflow: hidden;">
<!-- 整体布局 -->
<div id="wrapper" style="overflow: hidden;">
    <!-- 左侧菜单 -->
    <%@ include file="/WEB-INF/pages/include/nav.jsp" %>
    <!-- 右侧整体 -->
    <div id="page-wrapper" class="gray-bg" style="padding-left: 0px;border-left-width: 0px;padding-right: 0px;">
        <!-- 顶部工具栏 -->
        <%@ include file="/WEB-INF/pages/include/toolbar.jsp" %>
        <!-- 页面内容 -->
        <div id="pageContent" class="wrapper wrapper-content"
             style="padding-left: 1px; padding-top: 0px;margin-top: -1px;">
            <div id="tabContent" style="margin-top: 2px;">
                <div class="content-tabs">
                    <button class="roll-nav roll-left-0 J_tabLeft">
                        <i class="fa fa-backward"></i>
                    </button>
                    <nav class="page-tabs J_menuTabs">
                        <div class="page-tabs-content">
                            <a href="javascript:;" class="active J_menuTab" data-id="J_menuTab_defaultId">首页</a>
                        </div>
                    </nav>
                    <button class="roll-nav roll-right J_tabRight">
                        <i class="fa fa-forward"></i>
                    </button>
                </div>
                <div class="J_mainContent" id="content-main">
                    <iframe class="J_iframe" id="J_menuTab_defaultId" data-id="J_menuTab_defaultId" name="iframe0"
                            width="100%" height="100%" frameborder="0" seamless></iframe>
                </div>
            </div>
            <iframe id="content" name="content" frameborder="0" style="margin:0;padding:0;width:100%;"
                    scrolling="yes"></iframe>
        </div>
    </div>
</div>
<br:js pack="basic"/>
<link href="${res}/js/jquey-tab/jquery.contextMenu.min.css" rel="stylesheet"/>
<link href="${res}/js/jquey-tab/menuTab-blue.css" rel="stylesheet"/>
<link href="${res}/css/index.css" rel="stylesheet"/>

<script src="${res}/js/jquey-tab/jquery.contextMenu.min.js"></script>
<script src="${res}/js/jquey-tab/jep.menuTabs.js"></script>
<script src="${res}/js/jquey-tab/layer.js"></script>
<script src="${res}/js/jquey-tab/laytpl.js"></script>
<script src="${res}/js/index.js"></script>
<script type="text/javascript">
    //面包屑导航全路径
    var pathTitles = '';
    var timer = '';
    var isIndexTab = false;
    $(document).ready(function () {
        console.log('首页---------------')
        /*iframe 自适应内容大小*/
        //$.helper.autoResizeIframe('content');

        /* 获得目录数据，生成菜单目录 */
        $.mask.post('${base}/sys/menu/loadCurrentMenu?tenantRequired=false', function (data) {
            //初始化菜单
            initMenu(data);
        });

        /* 初始化获取通知 */
        initNotify();
        /* 每2分钟请求一次 */
//	timer = setInterval(initNotify, 20000); 

        // if (isIndexTab) {
        //     $('#content').hide();
        //     $('#navigation').hide();
        // } else {
        //     $('#tabContent').hide();
        // }

        //计算页面高度 rightMenu
        var height = $(window).height();
        $('#wrapper').height(height);
        $('#rightMenu').height(height);
        $('#side-menu').height((height - 120));
        //模式不同， 计算方式不一样
        if (isIndexTab) {//是否显示多tab页导航
            $('#content').hide();
            $('#navigation').hide();
            $('#tabContent').height(height);
            $('#content-main').attr("src", "${base}/welcome");
            $('#content-main').height((height - 70));
        } else {
            $('#tabContent').hide();
            $('#content').attr("src", "${base}/welcome");
            $('#content').height((height - 100));
        }

        //鼠标滑过出现下拉菜单
        var html = '<div style="width:100px;">';
        var merOrgHOme = '${merOrgHome}';
        var patt1 = new RegExp("Delegation");
        if (patt1.test(merOrgHOme)) {
            html += '<a href="javaScript: void(0);" class="dropdown-toggle count-info" style="color: #000;min-hight:30px;display:block;padding:5px;" onClick="onClick(\'${base}/sys/user/changePassword\',\'tabChangePassword\',\'sys.100.100_修改密码\',\'修改密码\');"><i class="fa fa-cog" style="color: #000;"></i> 修改密码</a>';
        }
        html += '<a href="${base}/login/logout" style="color: #000;hight:40px;display:block;min-hight:30px;display:block;padding:5px;" ><i class="fa fa-sign-out" style="color: #000;"></i> 退出系统</a>';
        html += '</div>';
        $("[rel=drevil]").popover({
            trigger: 'manual',
            placement: 'bottom',
            html: 'true',
            content: html,
            animation: false
        }).on("mouseenter", function () {
            var _this = this;
            $(this).popover("show");
            $(this).siblings(".popover").on("mouseleave", function () {
                $(_this).popover('hide');
            });
        }).on("mouseleave", function () {
            var _this = this;
            setTimeout(function () {
                if (!$(".popover:hover").length) {
                    $(_this).popover("hide")
                }
            }, 100);
        });
    });
</script>
</body>
</html>