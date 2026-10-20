<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>布局的基本信息</title>
</head>
<body class="gray-bg">
<div class="row animated fadeInRight">
    <bc:ibox title="布局的基本信息" subTitle="请输入布局的基本信息查询信息" col="12" collapsible="true">
        <bf:form id="queryForm" validation="true">
            <bc:row>
                <bc:col col="4">
                    <bc:label text="布局编码" col="4" formControl="true"/>
                    <bf:text name="__LIKES_code" col="8" placeholder="编码">
                        <bv:jepCode/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="布局名称" col="4" formControl="true"/>
                    <bf:text name="__LIKES_name" col="8" placeholder="名称"/>
                </bc:col>
                <bc:col col="4">
                    <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                    <bc:button id="btnReset" text="重置" icon="refresh"/>
                </bc:col>
            </bc:row>
        </bf:form>
        <bc:table id="dashLayoutTable" idField="code" url="${base}/sys/dashLayout">
            <bc:toolbar id="add" text="新增" type="button"/>
            <bc:toolbar id="design" text="设计" type="button" handler="design"/>
            <bc:toolbar id="designProfessional" text="专家模式" type="button" handler="designProfessional"/>
            <bc:column field="code" title="布局编码" width="100"/>
            <bc:column field="name" title="布局名称" width="100"/>
            <bc:column field="priority" title="优先级" width="100"/>
            <bc:column field="designType" title="设计模式" width="100" formatter="fixedDict:DashLayoutDesignType"/>
            <bc:column field="desc" title="布局描述" width="100"/>
            <bc:operator id="edit" icon="edit" title="编辑"/>
            <bc:operator id="remove" icon="trash" title="移除"/>
            <bc:operator id="view" icon="file-o" title="查看"/>
            <be:event type="onClickRow">onClickRow</be:event>
            <be:event type="onCheck">onClickRow</be:event>
        </bc:table>
    </bc:ibox>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    function design() {
        var rows = $("#dashLayoutTable").jTable("getSelections");
        if (!rows[0]) {
            $.messager.toastr('请选择要设计的布局');
        } else {
            var title = rows[0].name + "_布局设计";
            var layoutCode = rows[0].code;
            window.location = "${base}/sys/dashLayoutDesign/design?title=" + title + "&layoutCode=" + layoutCode;
        }

    }

    function designProfessional() {
        var rows = $("#dashLayoutTable").bootstrapTable("getSelections");
        if (!rows[0]) {
            $.messager.toastr('系统提示', '请选择要设计的布局', 'warning');
        } else {
            $.messager.modal({
                winId: 'winProfessional',
                width: 800,
                height: 270,
                title: '专家模式设计布局',
                url: '${base}/sys/dashLayout/professional',
                args: {code: rows[0].code},
                onClose: function () {
                    $('#dashLayoutTable').bootstrapTable('refresh');
                }
            });
        }
    }

    function onClickRow(row) {
        if (row['designType'] == 'PROFESSIONAL') {
            $('#dashLayoutTable_tb_design').attr('disabled', true);
            $('#dashLayoutTable_tb_designProfessional').attr('disabled', false);

        } else {
            $('#dashLayoutTable_tb_design').attr('disabled', false);
            $('#dashLayoutTable_tb_designProfessional').attr('disabled', true);
        }
    }
</script>
</body>
</html>