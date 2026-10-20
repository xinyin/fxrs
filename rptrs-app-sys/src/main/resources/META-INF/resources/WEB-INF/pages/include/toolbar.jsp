<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="row border-bottom" style=" margin-left: -1px;">
    <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0; ">
        <div class="navbar-header minimalize">
            <a class="navbar-minimalize btn " href="#"><i class="fa fa-bars"></i> </a>
        </div>
        <div class="navbar-header pull-right" style="margin-right: 60px;margin-top: 20px;cursor: pointer;">
            <ul class="nav navbar-top-links navbar-right">
                <%--<li>
                    <span style="font-size: 16px;color: red">
                    <marquee onmouseout="this.start();" onmouseover="this.stop();">
                        为获得更好的用户体验，建议您用谷歌浏览器登录集中认证平台后访问本系统！&lt;%&ndash;集中认证平台地址: http://172.20.0.15/index.jsp&ndash;%&gt;
                    </marquee>
                    </span>
                </li>--%>
                <%--<li rel="bell">
                    <div class="dropdown-toggle count-info" data-toggle="dropdown">
                        <span id="envelopeNum" class="label label-danger" style="border-radius: 15px;"></span>
                        <i class="fa fa-bell-o" style="color: #000; padding-top: 5px;"></i>
                    </div>
                    <!-- 	<ul id="notifYUL" class="dropdown-menu dropdown-messages"></ul> -->
                </li>--%>

                <li>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </li>
                <li>
                    <img class="img-circle" style="border-radius:50%" width="30px" height="30px"
                         src="${res}/img/head.png" alt="头像"/>
                </li>
                <li>
					<span class="m-t-xs" rel="drevil" style="cursor: pointer;">
						&nbsp;&nbsp;<strong class="font-bold">${currentUser.staffName}</strong>
					</span>
                </li>
            </ul>
        </div>
    </nav>
    <div id="navigation" class="wrapper border-bottom white-bg" style="padding-left: 16px;margin-top: 2px;">
        <div>
            <ol id="bread" class="breadcrumb">
                <li><a href="javaScript: void(0);" onClick="navIndexMenuClick(''+base + '/welcome')">首页</a></li>
            </ol>
        </div>
    </div>
</div>