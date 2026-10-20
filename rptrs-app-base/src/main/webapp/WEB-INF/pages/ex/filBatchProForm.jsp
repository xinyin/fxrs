<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <link rel="stylesheet" href="/static/css/dialog.css">
    <script src="/static/js/jquery.dialog.js"></script>
    <br:css pack="list"/>
    <style>
        .bs-checkbox{
            display: none;
        }
    </style>
    <title>关联关系金融产品表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bc:table id="relProTable" idField="pid" selectFirst="false" url="${base}/fil/filRelPro"  queryUrl="queryPidsByConditions/${pids}" multiple="true">
                <bc:column field="proName" title="产品名称" width="100"/>
                <bc:column field="proProvider" title="产品批准/注册/备案号" width="100"/>
                <bc:column field="proType" title="产品分类" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-pro"/>
                <bc:column field="adName" title="产品管理人全称" width="100"/>
                <bc:column field="adCreditCode" title="管理人统一社会信用代码" width="100"/>
                <bc:column field="approvalStatus" title="操作状态"
                           formatter="custom:sys/dictItem/getDictItems/BUS-ApplySingleStatus" width="100"/>
                <bc:column field="effectRelation" title="是否存在有效关系"
                           formatter="custom:sys/dictItem/getDictItems/BUS-EffectRelation" width="100"/>
                <bc:column field="modifiedTime" title="更新日期" width="100"/>
                <bc:operator id="view" icon="eye" title="查看"/>
            </bc:table>
        </bc:ibox>
        <bf:formGroup>
            <div class="col-sm-4 col-sm-offset-5">
                <bc:button type="primary" text="返回" icon="arrow-circle-left">
                    <be:event>
                        window.history.go(-1);
                    </be:event>
                </bc:button>
            </div>
        </bf:formGroup>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">
</script>
</body>
</html>
