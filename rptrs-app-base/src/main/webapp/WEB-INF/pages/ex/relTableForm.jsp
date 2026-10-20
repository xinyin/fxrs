<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <x:jsLib/>
    <br:css pack="form"/>
    <title>关联关系列表展示</title>
    <x:jsLib/>
</head>
<style>
    .bs-checkbox {
        display: none;
    }
</style>
<body class="gray-bg" style="background: white">
<div id="wrapper" class="animated fadeInRight">
    <div class="row" style="height: 100%">
        <bc:ibox title="" subTitle="数据表格" col="12" collapsible="true">
            <bc:table id="relPsnTable" idField="pid" url="${base}/bus/relation" queryUrl="getRelationsByPids/${pids}"
                      selectFirst="false">
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="fromMemo" title="关联方名称" width="100"/>
                <bc:column field="fromIdentifyNumber" title="关联方代码" width="100"/>
                <bc:column field="fromType" title="关联方类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-OrgOrPsn"/>
                <bc:column field="relPsnOrg" title="与前一层的关联关系" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-RelZZD"/>
                <bc:column field="toMemo" title="前一层关联方名称" width="100"/>
                <bc:column field="toIdentifyNumber" title="前一层关联方代码" width="100"/>
                <bc:column field="toType" title="前一层关联方类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-OrgOrPsn"/>
                <bc:column field="isValid" title="该关系是否仍然有效" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                <bc:column field="equityShare" title="是否有权益份额" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-RelequityShare"/>
                <bc:column field="shareholdingRatio" title="持有比例(%)" width="100"/>
                <bc:column field="pledgeRatio" title="股权质押比例(%)" width="100"/>
                <bc:column field="activeTimeString" title="生效日期" width="150"/>
                <bc:column field="invalidTimeString" title="失效日期" width="150"/>
                <bc:column field="relStatement" title="关联关系说明" width="100"/>
                <bc:column field="transactionDep" title="经办部门" width="100"/>
                <bc:column field="operator" title="经办人" width="100"/>
                <bc:column field="approvalStatus" title="数据状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-RelApplystatus"/>
                <bc:operator id="view_1" icon="eye" title="操作/查看" handler="view1"/>
            </bc:table>
        </bc:ibox>
        <%--<bf:formGroup>
            <div class="col-sm-4 col-sm-offset-5">
                <bc:button type="primary" text="返回" icon="arrow-circle-left">
                    <be:event>
                        window.history.go(-1);
                    </be:event>
                </bc:button>
            </div>
        </bf:formGroup>--%>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">
    $(document).ready(function () {

    });

    function view1() {
        $('#relPsnTable').on("click-row.bs.table", function (e, row, $element) {
            var pid = row.pid
            /**跳转页面*/
            window.location = '${base}/bus/relation/view1/' + pid;
        });
    }

    function getIdSelections() {
        return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }
</script>
</body>
</html>
