<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>首页仪表盘布局映射表，可映射到角色、角色组等，现在只使用映射到角色。</title>
</head>
<body class="gray-bg" style="background-color: white;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bf:form id="form" method="post" validation="false">
            <bf:formGroup>
                <bc:label text="岗位编码" col="4" formControl="true"/>
                <bc:label cls="roleCode" text="" col="8" formControl="true" style="text-align:center"/>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="岗位名称" col="4" formControl="true"/>
                <bc:label cls="roleName" text="" col="8" formControl="true" style="text-align:center"/>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="岗位描述" col="4" formControl="true"/>
                <bc:label cls="roleDesc" text="" col="8" formControl="true" style="text-align:center"/>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="岗位类型" col="4" formControl="true"/>
                <bc:label cls="roleStdName" text="" col="8" formControl="true" style="text-align:center"/>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="布局编码" col="4" formControl="true"/>
                <bc:label cls="layoutCode" text="" col="8" formControl="true" style="text-align:center"/>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="布局名称" col="4" formControl="true"/>
                <bc:label cls="layoutName" text="" col="8" formControl="true" style="text-align:center"/>
            </bf:formGroup>
            <bf:formGroup col="6">
                <div class="col-sm-4 col-sm-offset-10">
                    <bc:button text="关闭" icon="times">
                        <be:event>
                            $.messager.hide('roleViewWin');
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(function () {
        var args = $.messager.args('roleViewWin');
        var row = args.row;
        $(".roleCode").text(row.roleCode);
        $(".roleName").text(row.roleName);
        $(".roleDesc").text(row.roleDesc);
        $(".roleStdName").text(row.roleStdName);
        $(".layoutCode").text(row.layoutCode);
        $(".layoutName").text(row.layoutName);
    })
</script>
</body>
</html>