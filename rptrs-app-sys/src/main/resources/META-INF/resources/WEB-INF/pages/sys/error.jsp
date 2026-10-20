<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>系统消息提示</title>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
</head>
<body>
<h1 style="text-align: center;color: red;font-size: 40px;margin-top: 100px;">发生系统错误，请联系管理员。</h1>
<h2 style="text-align: center;color: red;font-size: 20px;margin-top: 10px;">${ex.message}</h2>
</body>
</html>