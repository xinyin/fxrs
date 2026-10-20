<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="dashWidgetProviderBox" title="绑定数据源_${dashWidget.name}" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" name="editForm"
                 method="post" validation="true">
            <bf:hidden name="opt" id="opt"/>
            <bf:hidden name="provierId" id="provierId"></bf:hidden>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:row>
                    <bc:col col="6">
                        <bc:label text="组件编码" col="4" formControl="true"/>
                        <bf:text id="widgetCode" name="widgetCode" col="8"
                                 value="${dashWidget.code}" disabled="true"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="组件名称" col="4" formControl="true"/>
                        <bf:text id="name" name="name" col="8" value="${dashWidget.name}"
                                 disabled="true"/>
                    </bc:col>
                </bc:row>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="提供程序名称" col="4" formControl="true"/>
                    <bf:text id="providerName" name="providerName" col="8">
                        <bv:notEmpty message="提供程序名称不能为空"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="数据提供程序" col="4" formControl="true"/>
                    <bf:select col="8" name="providerCode" id="providerCode"
                               value="code" dataSource="custom"
                               url="sys/dataProvider/getDataProvider">
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="数据提供程序参数" col="4" formControl="true"/>
                    <bf:text name="providerParams" id="providerParams" col="8" rows="3" multiple="true"
                             placeholder="参数为JSON格式，如：{id:'123'}"><bv:notEmpty message="数据提供程序参数不能为空"/></bf:text>
                </bc:col>
                <bc:col col="4" colOffset="2">
                    <bc:button text="确认" icon="save" click="btnSave()" type="primary"/>
                    <bc:button text="返回" icon="arrow-circle-left" click="btnBack()"/>
                </bc:col>
            </bf:formGroup>
        </bf:form>
        <bc:table id="widgetProviderTable" idField="provierId" url="${base}/sys/dashWidgetProvider"
                  queryUrl="query?__EQS_widgetCode=${dashWidget.code}">
            <bc:toolbar id="edit" text="编辑" type="button" handler="edit"/>
            <bc:column field="widgetCode" title="组件编码" width="80"/>
            <bc:column field="providerCode" title="数据提供程序编码" width="80"
                       formatter="custom:sys/dataProvider/getDataProvider"/>
            <bc:column field="providerName" title="数据提供程序名称" width="80"/>
            <bc:column field="providerParams" title="数据提供程序参数" width="80"/>
            <bc:column field="beenUsed" title="是否被引用" width="80" formatter="fixedDict:YesNo"/>
            <bc:operator id="remove" icon="trash" title="移除"/>
        </bc:table>
    </bc:ibox>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    /* 编辑 */
    function edit() {
        var rows = $("#widgetProviderTable").jTable("getSelections");
        if (!rows[0]) {
            $.messager.toastr('提示', '请选择需要编辑的数据', 'info');
            return;
        } else if ("Y" == rows[0].beenUsed) {
            $.messager.toastr('提示', '此数据提供程序已被引用，不能修改。', 'info');
            return;
        } else {
            var selectRow = rows[0];
            //提供程序名称
            $("#providerName").val(selectRow.providerName);
            //数据提供程序参数
            $("#providerParams").val(selectRow.providerParams);
            //数据提供程序(下拉框)
            var providerCode = selectRow.providerCode;
            $("#provierId").val(selectRow.provierId);
            $("#providerCode").find("option[value = '" + providerCode + "']").attr("selected", "selected");
        }
    }

    /* 保存 */
    function btnSave() {
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            var widgetType = "${dashWidget.type}";
            var provierId = $("#provierId").val();
            if ((provierId == null || provierId == '') && widgetType == 'CHART') {
                var length = $("#widgetProviderTable").bootstrapTable("getSelections").length;
                if (length > 0) {
                    $.messager.toastr('提示', '图表组件只能绑定一个数据提供程序', 'warning');
                    return;
                }
            }
            var params = $('#providerParams').val();
            try {
                console.info($.parseJSON(params));
            } catch (error) {
                $.messager.toastr('提示', '数据提供程序参数格式为json格式。', 'info');
                return;
            }

            var data = $('#editForm').serializeAll();
            $.mask.post('${base}/sys/dashWidgetProvider/save', data, function (data) {
                if (data.success) {
                    $.messager.toastr('提示', data.msg, 'info');
                    $('#widgetProviderTable').bootstrapTable('refresh');
                    window.location = '${base}/sys/dashWidget/list';
                } else {
                    $.messager.toastr('错误', data.msg, 'error');
                }
            });
        }
    }

    //返回
    function btnBack() {
        window.location = '${base}/sys/dashWidget/list';
    }
</script>
</body>
</html>
