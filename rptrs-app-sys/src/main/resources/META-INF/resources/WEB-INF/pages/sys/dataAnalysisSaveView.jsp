<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>元数据表的自定义查询</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="metaTableBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:formGroup>
                <bc:col col="12">
                    <bc:label text="视图名称" col="4" formControl="true"/>
                    <bf:text id="tableCode" name="tableCode" col="8">
                        <bv:notEmpty/>
                        <bv:jepCode/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="12">
                    <bc:label text="中文名" col="4" formControl="true"/>
                    <bf:text id="tableName" name="tableName" col="8">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="12">
                    <bc:label text="模式" col="4" formControl="true"/>
                    <bf:select name="schema" col="8" id="schema" dataSource="custom" url="sys/metaTable/querySchema"
                               value="${schema}">
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="12">
                    <bc:label text="数据区域" col="4" formControl="true"/>
                    <bf:lookup url="${base}/sys/metaRegion/dialog" id="regionId" name="regionId"
                               propText="regionName" propValue="regionId"
                               width="650" height="400" col="8"
                               winId="winMetaRegionDlg" callback="result"
                               title="选择数据区域">
                        <bv:notEmpty/>
                    </bf:lookup>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-4">
                    <bc:button type="primary" text="重复检测" icon="save">
                        <be:event>
                            checkTableCode();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="保存" icon="save">
                        <be:event>
                            btnSave();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            $.messager.hide('wmetaQuerySaveView');
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
<script src="${resource}/scripts/easyui/jquery.json-2.4.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${resource}/scripts/nsa/MetaQuery.js"></script>
<script type="text/javascript">
    var currentSchema = '${schema}';
    var script = '';
    $(function () {
        var args = $.messager.args('wmetaQuerySaveView');
        script = args.script;
    });

    /*检测视图名称是否重复*/
    function checkTableCode() {
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            var tableCode = $('#tableCode').val();
            tableCode = tableCode.toUpperCase();
            var tableSchemas = $('#schema').val();

            $.mask.post('${base}/sys/dataAnalysis/checkViewName', {
                'tableCode': tableCode,
                'schema': tableSchemas
            }, function (data) {
                if (data.success) {
                    if (data.tableExists) {
                        $.messager.alert('系统提示', '视图已存在。', 'info');
                        return false;
                    } else {
                        $.messager.alert('系统提示', '视图名称可以使用。', 'info');
                        return true;
                    }
                }
            });
        }
    }

    /*保存*/
    function btnSave(type) {
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            /*获取表单数据*/
            var tableCode = $('#tableCode').val();
            tableCode = tableCode.toUpperCase();
            var tableName = $('#tableName').val();
            var tableSchemas = $('#schema').val();
            var regionId = $('#regionId').jLookup('getValue');

            var dataView = {
                script: script,
                tableCode: tableCode,
                tableName: tableName,
                schema: tableSchemas,
                regionId: regionId
            };

            $.mask.post('${base}/sys/dataAnalysis/checkViewName', {
                'tableCode': tableCode,
                'schema': tableSchemas
            }, function (data) {
                if (data.success) {
                    if (data.tableExists) {
                        $.messager.alert('系统提示', '视图已存在。', 'info');
                        return false;
                    } else {
                        /*保存*/
                        $.mask.post('${base}/sys/metaQuery/saveView', dataView, function (data) {
                            $.messager.alert('系统提示', data.msg, 'info', function () {
                                if (data.success) {
                                    $.messager.args('wmetaQuerySaveView').callback(regionId, data.tableId, tableCode);
                                    $.messager.hide('wmetaQuerySaveView');
                                }
                            });
                        });
                    }
                }
            });
        }
    }
</script>
</body>
</html>
