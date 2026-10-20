<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>查询名称</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="metaTableBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:formGroup>
                <bc:col col="12">
                    <bc:label text="查询名称" col="4" formControl="true"/>
                    <bf:text id="queryName" name="queryName" col="8">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="12">
                    <bc:label text="查询类型" col="4" formControl="true"/>
                    <bf:select id="queryType" name="queryType" col="8" dataSource="fixedDict" url="MetaQueryType">
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-4">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            btnSave();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            $.messager.hide('wmetaQueryName');
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    /*metaTable Id*/
    var tableId = '';
    /*数据查询脚本*/
    var script = '';
    $(document).ready(function () {
        jQuery.ajaxSetup({
            cache: false
        });

        var args = $.messager.args('wmetaQueryName');
        tableId = args.tableId;
        script = args.script;
    });

    /*保存事件*/
    function btnSave() {
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            var queryName = $('#queryName').val();
            var queryType = $('#queryType').val();

            data = {
                queryId: '',
                tableId: tableId,
                queryName: queryName,
                queryType: queryType,
                script: script
            };

            /*保存*/
            $.mask.post('${base}/sys/metaQuery/save', data, function (data) {
                $.messager.alert('系统提示', data.msg, 'info', function () {
                    if (data.success) {
                        var type = 'Q';
                        if (queryType == 'PUBLIC')
                            type = 'P';
                        $.messager.args('wmetaQueryName').callback(queryName, data.queryId, tableId, type);
                        $.messager.hide('wmetaQueryName');
                    }
                });

            });
        }
    }


</script>
</body>
</html>
