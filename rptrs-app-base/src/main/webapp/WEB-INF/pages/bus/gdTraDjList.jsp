<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>GdTraDj</title>
    <script>
        function addJdhandler() {
            var pid = getIdSelections();
            var typeSelections = getTypeSelections();
            if (typeSelections != '0') {
                alert('未选择冻结类型');
                return false;
            }
            window.location = '${base}/bus/gdTraDj/addJd/' + pid;
        }

        function editHandler() {
            var status = getStatusSelections();
            if ("4" == status) {
                alert('审批中不能编辑');
                return false;
            }
            var typeSelections = getTypeSelections();
            var pid = getIdSelections();
            if (typeSelections == '0') {
                window.location = '${base}/bus/gdTraDj/edit/' + pid;
            } else if (typeSelections = '1') {
                window.location = '${base}/bus/gdTraDj/editJd/' + pid;
            }
        }

        function removeHandler() {
            var status = getStatusSelections();
            if ("3" != status) {
                alert("已审批不能删除");
                return false;
            }
            if (confirm('确认删除？')) {
                var pid = getIdSelections();
                <%--window.location = '${base}/bus/gdTraDj/remove/' + pid;--%>
                $.ajax({
                    url: '${base}/bus/gdTraDj/remove/' + pid,
                    type: 'json',
                    success: function (data) {
                        window.location = '${base}/bus/gdTraDj/list';
                    }
                });
            }
        }

        function getIdSelections() {
            return $.map($('#gdTraDjTable').bootstrapTable('getSelections'), function (row) {
                return row.pid;
            });
        }

        function getTypeSelections() {
            return $.map($('#gdTraDjTable').bootstrapTable('getSelections'), function (row) {
                return row.operationType;
            });
        }

        function getStatusSelections() {
            return $.map($('#gdTraDjTable').bootstrapTable('getSelections'), function (row) {
                return row.status;
            });
        }

        function subHandler() {
            var status = getStatusSelections();
            if ("3" != status) {
                alert("不能提交审批");
                return false;
            }
            var type = getTypeSelections();
            var pid = getIdSelections();
            window.location = '${base}/bus/gdTraDj/submit/' + type + '/' + pid;
        }

        //重置
        function frmReset() {
            window.location = '${base}/bus/gdTraDj/list';
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
                        <bc:label text="股东代码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_shareholdersCode" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="股东名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_shareholdersName" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="股东类型" col="5" formControl="true"/>
                        <bf:text name="__LIKES_shareholdersType" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="是否国有企业" col="5" formControl="true"/>
                        <bf:select name="__EQS_stateOwned" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-isNo">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_stateOwned");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="境内/境外" col="5" formControl="true"/>
                        <bf:select name="__LIKES_domesticOrForeign" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-DomesticOrForeign">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_domesticOrForeign");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
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
                    <bf:formGroup col="4">
                        <bc:label text="操作类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_operationType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-StockFreeze">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#LIKES_operationType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="冻结流水号" col="5" formControl="true"/>
                        <bf:text name="__LIKES_frozenSerial" col="7" placeholder=""/>
                    </bf:formGroup>
                    <%--<bc:col col="3" colOffset="0">
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
            <bc:table id="gdTraDjTable" idField="pid" url="${base}/bus/gdTraDj">
                <bc:toolbar id="add" text="新增冻结" type="button"/>
                <bc:toolbar id="addJd" text="解冻" type="button" handler="addJdhandler"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="submit" text="提交审批" type="button" handler="subHandler"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="shareholdersCode" title="股东代码" width="100"/>
                <bc:column field="shareholdersName" title="股东名称" width="100"/>
                <bc:column field="shareholdersType" title="股东类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-RelatedPartiesType"/>
                <bc:column field="stateOwned" title="是否国有企业" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <bc:column field="domesticOrForeign" title="境内境外" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                <bc:column field="holeShareAmount" title="总持有股数" width="100"/>
                <bc:column field="frozenShareAmount" title="总冻结股数" width="100"/>
                <bc:column field="operationType" title="操作类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-StockFreeze"/>
                <bc:column field="operationAmount" title="操作股数" width="100"/>
                <bc:column field="frozenSerial" title="冻结流水号" width="100"/>
                <bc:column field="effectiveTime" title="冻结生效时间" width="100"/>
                <bc:column field="failureTime" title="冻结失效时间" width="100"/>
                <bc:column field="unfrozenTime" title="解冻时间" width="100"/>
                <bc:column field="submitTime" title="提交审批时间" width="100"/>
                <bc:column field="status" title="审批状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="statusComment" title="状态说明" width="100"/>
                <bc:column field="operator" title="经办人" width="100"/>
                <bc:column field="approver" title="审批人" width="100"/>
                <bc:column field="frozenOrg" title="冻结机构" width="100" visible="false"/>
                <bc:column field="continueFrozenTime" title="续冻失效时间" width="100" visible="false"/>
                <bc:column field="frozenReason" title="冻结原因" width="100" visible="false"/>
                <bc:column field="continueFrozenReason" title="续冻原因" width="100" visible="false"/>
                <bc:column field="unfrozenReason" title="解冻说明" width="100" visible="false"/>
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