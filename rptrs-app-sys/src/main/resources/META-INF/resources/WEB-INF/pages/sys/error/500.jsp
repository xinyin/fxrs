<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>系统消息提示</title>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <style type="text/css">
        body {
            height: 100%;
            -moz-osx-font-smoothing: grayscale;
            -webkit-font-smoothing: antialiased;
            text-rendering: optimizeLegibility;
            font-family: MicrosoftYaHei, Avenir, Helvetica, Arial, sans-serif;
            color: #333;
            font-size: 14px;
            background: #f5f6f9;
        }

        .title {
            width: 100%;
            text-align: center;
            font-size: 24px;
            color: #111;
            line-height: 38px;
        }

        .pic {
            width: 700px;
            height: 400px;
        }

    </style>
</head>
<body>

<div class="box">

    <div class="newContent">
        <p><br></p>
        <p style="text-align:center;"><img class="pic" src="${base}/static/img/500.png" alt=""></p>

        <div class="title">发生系统错误，请联系管理员</div>

        <%--        <a  style="text-align:center; color:#333; font-size:13px; text-decoration:none;--%>
        <%--        width:100px; height:32px; line-height:32px; border:1px solid #E3E2E8; display:block; margin:30px auto;">返回首页</a>--%>

        <p><br></p>
    </div>

</div>


</body>
</html>
