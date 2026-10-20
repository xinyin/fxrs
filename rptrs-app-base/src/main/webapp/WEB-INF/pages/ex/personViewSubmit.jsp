<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>自然人关联方档案</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="自然人关联方档案" subTitle="" col="12" collapsible="true">
            <bf:formGroup col="4">
                <bc:label text="生成报送文件日期：${date}" col="8" formControl="true"/>
                <bc:label text="报送机构：${sublog.subOrg}" col="5" formControl="true"/>
                <bc:label text="操作人：${sublog.operatorName}" col="5" formControl="true"/>
            </bf:formGroup>
            <bc:table id="subViewTable" idField="pid" url="${base}/sub/person" queryUrl="ProductView/${pid}">
                <bc:column field="pid" title="pid" width="100" visible="false"/>
                <bc:column field="name" title="姓名" width="100"/>
                <bc:column field="domesticOrForeign" title="境内（外）" width="100"/>
                <bc:column field="identityType" title="证件类型" width="100"/>
                <bc:column field="identityNo" title="证件号码" width="100"/>
            </bc:table>
        </bc:ibox>
            <bc:button  type="primary" text="返回" icon="arrow-circle-left">
                <be:event>
                    window.location = '${base}/sub/person/sublist';
                </be:event>
            </bc:button>

    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

</script>
</body>
</html>
