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
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="生成报送文件日期：${date}" col="5" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="报送机构：复星保德信人寿保险有限公司" col="12" formControl="true"/>
                </bf:formGroup>

            </bf:formGroup>
            <bc:table id="subViewTable" idField="pid" url="${base}/sub/person" selectFirst="false" queryUrl="ProductView/${pid}" multipleVisible="false">
                <bc:column field="pid" title="pid" width="100" visible="false"/>
                <bc:column field="name" title="姓名" width="100"/>
                <bc:column field="domesticOrForeign" title="境内外标识" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                <bc:column field="identityType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="identityNo" title="证件号码" width="100"/>
            </bc:table>
        </bc:ibox>
        <bf:formGroup col="12">
            <div style="text-align:center">
                <bc:button type="primary" text="返回" icon="arrow-circle-left">
                    <be:event>
                        window.location = '${base}/sub/person/sublist';
                    </be:event>
                </bc:button>
            </div>
        </bf:formGroup>

    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

</script>
</body>
</html>
