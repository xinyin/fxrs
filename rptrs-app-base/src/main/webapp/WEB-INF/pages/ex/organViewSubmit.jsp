<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>投资入股类信息报表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="法人和组织报送" subTitle="" col="12" collapsible="true">
            <bf:formGroup col="4">
                <bc:label text="生成报送文件日期：${date}" col="8" formControl="true"/>
                <bc:label text="报送机构：${subOrg}" col="5" formControl="true"/>
                <bc:label text="操作人：${operator}" col="5" formControl="true"/>
            </bf:formGroup>
            <bc:table id="organViewTable" idField="pid" url="${base}/sub/organ" queryUrl="viewHandler/${pid}">
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="organSeq" title="法人和其他关联方流水号" width="100" visible="false"/>
                <bc:column field="name" title="法人名称" width="100" />
                <bc:column field="domesticOrForeign" title="境内(外)" width="100" />
                <bc:column field="uniscCode" title="社会统一信用代码" width="100" />
            </bc:table>
            <bc:button  type="primary" text="返回" icon="arrow-circle-left">
                <be:event>
                    window.location = '${base}/sub/organ/list';
                </be:event>
            </bc:button>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>