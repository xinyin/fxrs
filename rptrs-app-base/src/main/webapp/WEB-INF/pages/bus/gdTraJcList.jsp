<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>GdTraZr</title>
    <script>
        function editHandler() {
            var status = getStatusSelections();
            if ("4" == status) {
                alert('审批中不能编辑');
                return false;
            }
            var pid = getIdSelections();
            window.location = '${base}/bus/gdTraZr/editJc/' + pid;
        }

        function addHandler() {
            window.location = '${base}/bus/gdTraZr/addJc';
        }

        function removeHandler() {
            var status = getStatusSelections();
            if ("3" != status) {
                alert('已审批不能删除');
                return false;
            }
            if (confirm('确认删除？')) {
                var pid = getIdSelections();
                $.ajax({
                    url: '${base}/bus/gdTraZr/remove/' + pid,
                    type: 'json',
                    success: function (data) {
                        window.location = '${base}/bus/gdTraZr/Jclist';
                    }
                });
            }
        }

        function getIdSelections() {
            return $.map($('#gdTraZrTable').bootstrapTable('getSelections'), function (row) {
                return row.pid;
            });
        }

        function getTypeSelections() {
            return $.map($('#gdTraZrTable').bootstrapTable('getSelections'), function (row) {
                return row.operationType;
            });
        }

        function getStatusSelections() {
            return $.map($('#gdTraZrTable').bootstrapTable('getSelections'), function (row) {
                return row.status;
            });
        }

        function subHandler() {
            var status = getStatusSelections();
            if ("3" != status) {
                alert('不能提交审批');
                return false;
            }
            var type = getTypeSelections();
            var pid = getIdSelections();
            window.location = '${base}/bus/gdTraZr/submit/' + pid + '/' + type;
        }

    </script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="被继承人股东代码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_fromShareholdersCode" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="继承人股东代码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_toShareholdersCode" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="股权变更时间" col="5" formControl="true"/>
                        <bf:datebox name="__LIKES_changeTime" col="7">
                        </bf:datebox>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="状态" col="5" formControl="true"/>
                        <bf:select name="__LIKES_status" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Applystatus">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_status");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <%--<bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
                    </bc:col>--%>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="gdTraZrTable" idField="pid" url="${base}/bus/gdTraZr" queryUrl="queryJc" addUrl="addJc">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="修改" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="submit" text="提交审批" type="button" handler="subHandler"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="fromShareholdersCode" title="被继承人股东代码" width="100"/>
                <bc:column field="fromShareholdersName" title="被继承人股东名称" width="100"/>
                <bc:column field="toShareholdersCode" title="继承人股东代码" width="100"/>
                <bc:column field="toShareholdersName" title="继承人股东名称" width="100"/>
                <bc:column field="afterFromAmount" title="变更后被继承人持股数量（股）" width="100"/>
                <bc:column field="afterFromProportion" title="变更后被继承人持股数比例（%)" width="100"/>
                <bc:column field="afterToAmount" title="变更后继承人持股数量（股）" width="100"/>
                <bc:column field="afterToProportion" title="变更后继承人持股数比例（%）" width="100"/>
                <bc:column field="operationAmount" title="继承股数（股）" width="100"/>
                <bc:column field="operationPrice" title="转让单价（元/股）" width="100" visible="false"/>
                <bc:column field="status" title="审批状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="operationType" title="操作类型" width="100" visible="false"/>
                <bc:column field="changeTime" title="股权变更时间" width="100" visible="false"/>
                <bc:operator id="view" icon="search" title="查看"/>
                <%--                <bc:operator id="edit" icon="edit" title="编辑"/>--%>
                <%--                <bc:operator id="remove" icon="trash" title="移除"/>--%>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>