<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>定义系统中的附件上传类型。</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="attachmentTypeBox" title="" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="上传类型编码" col="6" formControl="true"/>
                    <bc:label text="${attachmentType.attachTypeCode}" col="6" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="上传类型名称" col="6" formControl="true"/>
                    <bc:label text="${attachmentType.attachTypeName}" col="6" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="附件上传路径（系统配置项）" col="6" formControl="true"/>
                    <bc:label text="${attachmentType.attachPath}" col="6" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div align="center">
                        <bc:button text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/attachmentType/list';
                            </be:event>
                        </bc:button>
                    </div>
                </bf:formGroup>
            </bf:form>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>