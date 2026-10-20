<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>法人和其他关联方档案</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="法人和其他关联方档案" subTitle="" col="12" collapsible="true">
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="生成报送文件日期：${date}" col="12" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="报送机构：复星保德信人寿保险有限公司" col="12" formControl="true"/>
                </bf:formGroup>
            </bf:formGroup>
            <bc:table id="organViewTable" idField="pid" url="${base}/sub/organ" selectFirst="false" queryUrl="viewHandler/${pid}" multipleVisible="false">
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="name" title="法人名称" width="100"/>
                <bc:column field="domesticOrForeign" title="境内（外）" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                <bc:column field="identifyType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-ORGIdentifyType"/>
                <bc:column field="legalIdentifyNumber" title="证件号码" width="100"/>
                <bc:column field="orgNature" title="经济性质或类型" width="100" visible="false"/>
                <bc:column field="businessScope" title="主营业务或经营范围" width="100" visible="false"/>
                <bc:column field="legalPerson" title="法定代表人" width="100" visible="false"/>
                <bc:column field="regAddress" title="注册地" width="100" visible="false"/>
                <bc:column field="regCapital" title="注册资本" width="100" visible="false"/>
            </bc:table>
            <bc:button type="primary" text="返回" icon="arrow-circle-left">
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
