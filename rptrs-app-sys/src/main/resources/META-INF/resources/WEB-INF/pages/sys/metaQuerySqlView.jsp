<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>元数据表的自定义查询</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <div class="col-xs-12 ibox-content">
            <div class="panel-body" style="min-height: 325px">'${sql}'</div>
            <bc:row cls="m-b">
                <bf:formGroup></bf:formGroup>
                <div align="center">
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            $.messager.hide('viewSqlList');
                        </be:event>
                    </bc:button>
                </div>
            </bc:row>
        </div>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>
