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
        <bc:ibox title="金融产品信息报表" subTitle="" col="12" collapsible="true">
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="生成报送文件日期：${date}" col="12" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="报送机构：复星保德信人寿保险有限公司" col="12" formControl="true"/>
                </bf:formGroup>
            </bf:formGroup>
            <bc:table id="subViewTable" idField="pid" url="${base}/sub/finProduct/"
                      queryUrl="ProView/${pid}" multipleVisible="false">
                <bc:column field="pid" title="pid" width="100" visible="false"/>
                <bc:column field="productName" title="金融产品全称" width="100"/>
                <bc:column field="productIssueNo" title="产品批准/注册/备案号" width="100"/>
                <bc:column field="productRegisterNo" title="产品代码" width="100"/>
                <bc:column field="productType" title="产品分类" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-pro"/>
                <bc:column field="registerOrg" title="产品登记机构" width="100"/>
                <bc:column field="productOwner" title="产品管理人全称" width="100"/>
                <bc:column field="productOwnerUniscCode" title="管理人统一社会信用代码" width="100"/>
                <bc:column field="productCustodian" title="产品托管人全称" width="100"/>
                <bc:column field="productCustodianUniscCode" title="托管人统一社会信用代码" width="100"/>
                <bc:column field="assetDesc" title="底层资产" width="100"/>
                <bc:column field="investors" title="投资者" width="100"/>
            </bc:table>
        </bc:ibox>
        <bf:formGroup col="12">
            <div style="text-align:center">
                <bc:button type="primary" text="返回" icon="arrow-circle-left">
                    <be:event>
                        window.location = '${base}/sub/sublog/list';
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
