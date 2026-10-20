<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>系统消息提示</title>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <style>
        html, body {
            height: 100%;
        }

        html, body, head {
            padding: 0px;
            margin: 0px;
        }

        .full {
            height: 100%;
        }

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

        .router-view {
            width: 100%;
            position: relative;
            height: 100%;
            overflow: hidden;
        }

        div {
            -webkit-box-sizing: border-box;
            box-sizing: border-box;
        }


        .http404-container {
            position: relative;
            top: 45%;
            -webkit-transform: translateY(-50%);
            transform: translateY(-50%);
            box-sizing: border-box;
        }

        .pic {
            width: 500px;
            display: block;
            margin: 0 auto;
            border-style: none;
        }

        .title {
            width: 100%;
            text-align: center;
            font-size: 24px;
            color: #111;
            line-height: 38px;
            margin-top: 40px;
        }

        .desc {
            width: 100%;
            font-size: 18px;
            color: #777;
            text-align: center;
            line-height: 38px;
            margin-top: 8px;
            box-sizing: border-box;
        }

        .btn {
            margin-top: 40px;
            width: 125px;
            height: 44px;
            text-align: center;
            font-size: 14px;
            color: #fefefe;
            line-height: 44px;
            background: #2362fb;
            border-radius: 6px;
            margin: 50px auto 0;
            cursor: pointer;
        }


    </style>
</head>


<body>

<div class="box full">

    <div data-v-053a6219="" class="wrapper router-view">
        <div data-v-053a6219="" class="http404-container">

            <img data-v-053a6219="" src="${base}/static/img/404.png"
                 alt="" class="pic">
            <div class="title">${msg}</div>
            <div class="desc">请检查您输入的网址是否正确</div>
            <%--            <div  class="btn">--%>
            <%--                返回首页--%>
            <%--            </div>--%>
        </div>
    </div>


</body>
</html>
