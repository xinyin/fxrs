<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>元数据表的自定义查询</title>
    <style type="text/css">
        #syncDataP {
            margin-bottom: 0px;
        }

        .ibox-content {
            padding-bottom: 0px;
            height: 450px;
        }

        .panel-body {
            padding-bottom: 0px;
        }

        .fixed-table-body {
            max-height: 240px;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="syncDataP" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="queryForm" validation="true">
            <bc:row>
                <bc:col col="6">
                    <bc:label text="查询名称" col="3" formControl="true"/>
                    <bf:text name="__LIKES_queryName" col="9" placeholder="查询名称"/>
                </bc:col>
                <bc:col col="6">
                    <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                    <bc:button id="btnReset" text="重置" icon="refresh"/>
                </bc:col>
            </bc:row>
        </bf:form>
        <bc:table id="metaQueryTable" idField="queryId" queryUrl="query?__EQS_status=30"
                  url="${base}/sys/metaQuery" multiple="true">
            <bc:toolbar id="saveData" text="同步" type="button" handler="saveData"/>
            <bc:toolbar id="cancel" text="取消" type="button" handler="cancel"/>
            <bc:column field="queryName" title="查询名称" width="100"/>
            <bc:column field="queryType" title="查询类型" width="100" formatter="fixedDict:MetaQueryType"/>
            <bc:column field="status" title="状态" width="100" formatter="fixedDict:MetaQueryStatus"/>
            <bc:operator id="seeMetaQuery" icon="eye" title="查看" handler="seeMetaQuery"/>
        </bc:table>
    </bc:ibox>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    /* 查看 */
    function seeMetaQuery() {
        var rows = $("#metaQueryTable").jTable('getCheckData');
        var queryId = rows[0].queryId;
        window.location = base + '/sys/metaQuery/viewMetaDataProvider/' + queryId;
    }

    //保存数据到dataProvider这张表中
    function saveData() {
        var rows = $("#metaQueryTable").jTable('getCheckData');
        var data = {
            dataProviderJson: JSON.stringify(rows)
        }
        $.mask.post('${base}/sys/dataProvider/save', data, function (data) {
            if (data.success) {
                $.messager.alert('提示', '同步成功', 'info', function () {
                    $.messager.hide('addDataSource');
                });
            } else {
                $.messager.toastr('错误', data.msg, 'error');
            }
        });
    }

    /* 取消 */
    function cancel() {
        $.messager.hide('addDataSource');
    }
</script>
</body>
</html>