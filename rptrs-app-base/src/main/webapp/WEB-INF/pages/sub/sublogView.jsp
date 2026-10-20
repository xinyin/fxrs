<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>金融产品信息报表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="金融产品信息报表" subTitle="" col="12" collapsible="true">
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="生成报送文件日期：${date}" col="8" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="报送机构：${sublog.subOrg}" col="5" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="操作人：${sublog.operatorName}" col="5" formControl="true"/>
                </bf:formGroup>
            </bf:formGroup>
            <bc:table id="subViewTable" idField="pid" url="${base}/sub/sublog" queryUrl="ProductView/${pid}">
                <bc:column field="pid" title="pid" width="100" visible="false"/>
                <bc:column field="productName" title="关联方名称" width="100"/>
                <bc:column field="productIssueNo" title="关联方代码" width="100"/>
                <bc:column field="productRegisterNo" title="关联方类别" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="productType" title="交易类型" width="100"/>
                <bc:column field="registerOrg" title="业务类型" width="100"/>
                <bc:column field="productOwner" title="合同编码" width="100"/>
                <bc:column field="productOwnerUniscCode" title="合同签署日期" width="100"/>
                <bc:column field="productCustodian" title="交易金额(万元/人民币)" width="100"/>
                <bc:column field="productCustodianUniscCode" title="是否为控股子公司" width="100"/>
            </bc:table>
            <bc:button  type="primary" text="返回" icon="arrow-circle-left">
                <be:event>
                    window.location = '${base}/sub/sublog/list';
                </be:event>
            </bc:button>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

</script>
</body>
</html>
