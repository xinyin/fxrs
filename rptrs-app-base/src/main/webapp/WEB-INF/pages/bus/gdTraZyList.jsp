<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>GdTraZy</title>
    <style>
        .form-group {
            margin-bottom: 8px !important;
        }

        .m-b {
            margin-bottom: 0 !important;
        }

    </style>
</head>
<body class="gray-bg" style="background: white">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup hrp="bottom">
                        <bf:formGroup col="4">
                            <bc:label text="股东代码" col="5" formControl="true"/>
                            <bf:text name="__LIKES_shareholdersCode" col="7" placeholder="股东代码"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="股东名称" col="5" formControl="true"/>
                            <bf:text name="__LIKES_shareholdersName" col="7" placeholder="股东名称"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="股东类型" col="5" formControl="true"/>
                            <bf:select name="__LIKES_shareholdersType" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-RelatedPartiesType">
                                <bf:option text="ALL" value="%"></bf:option>
                                <be:event type="select">
                                    var input = $("#shareholdersType");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="是否国有企业" col="5" formControl="true"/>
                            <bf:select name="__EQS_stateOwned" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-isNo">
                                <bf:option text="ALL" value=""></bf:option>
                                <be:event type="select">
                                    var input = $("#stateOwned");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="境内-境外" col="5" formControl="true"/>
                            <bf:select name="__EQS_domesticOrForeign" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-DomesticOrForeign">
                                <bf:option text="ALL" value=""></bf:option>
                                <be:event type="select">
                                    var input = $("#domesticOrForeign");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="状态" col="5" formControl="true"/>
                            <bf:select name="__LIKES_status" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-Applystatus">
                                <bf:option text="ALL" value=""></bf:option>
                                <be:event type="select">
                                    var input = $("#status");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="操作类型" col="5" formControl="true"/>
                            <bf:select name="__LIKES_operationType" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-StockRightsType">
                                <bf:option text="ALL" value="%"></bf:option>
                                <be:event type="select">
                                    var input = $("#operationType");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bf:formGroup>
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
            <bc:table id="gdTraZyTable" idField="pid" url="${base}/bus/gdTraZy">
                <bc:toolbar id="addZY" text="新增质押" type="button" handler="toZY"/>
                <bc:toolbar id="addJy" text="新增解押" type="button" handler="toJY"/>
                <bc:toolbar id="sub" text="提交审批" type="button" handler="subHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>

                <bc:column field="pid" title="主键" width="100" visible='false'/>
                <bc:column field="shareholdersCode" title="股东代码" width="100"/>
                <bc:column field="shareholdersName" title="股东名称" width="100"/>
                <bc:column field="shareholdersType" title="股东类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-RelatedPartiesType"/>
                <bc:column field="stateOwned" title="是否国有企业" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <bc:column field="domesticOrForeign" title="境内境外" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                <bc:column field="holeShareAmount" title="总持有股数" width="100"/>
                <bc:column field="pledgedShareAmount" title="总质押股数" width="100"/>
                <bc:column field="operationType" title="操作类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-StockRightsType"/>
                <bc:column field="operationAmount" title="操作股数" width="100"/>
                <bc:column field="pledgedContract" title="质押合同号" width="100"/>
                <bc:column field="effectiveTime" title="质押生效时间" width="100"/>
                <bc:column field="unpledgedTime" title="解押时间" width="100"/>
                <bc:column field="submitTime" title="提交审批时间" width="100"/>
                <bc:column field="status" title="审批状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="statusComment" title="状态说明" width="100"/>
                <bc:column field="operator" title="经办人" width="100" visible="false"/>
                <bc:column field="approver" title="审批人" width="100" visible="false"/>
                <bc:column field="pledgedAmount" title="质押融资额" width="100" visible="false"/>
                <bc:column field="name" title="质权人名称" width="100" visible="false"/>
                <bc:column field="identifyType" title="质权人证件类型" width="100" visible="false"/>
                <bc:column field="identifyNumber" title="质权人证件号码" width="100" visible="false"/>
                <bc:column field="unpledgedComment" title="解押说明" width="100" visible="false"/>
                <bc:operator id="view" icon="search" title="查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script src="../../static/js/jquery.dialog.js"></script>

<script type="text/javascript">
    function toZY() {

        window.location = '${base}/bus/gdTraZy/edit';
    }

    function toJY() {
        var operationType = getTypeSelections();
        if (operationType != '1') {
            alert('未选择质押类型');
            return false;
        }

        var pid = getIdSelections();
        console.log(pid)

        window.location = '${base}/bus/gdTraZy/addJy?pid=' + pid;

    }

    function getIdSelections() {
        return $.map($('#gdTraZyTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function getStatusSelections() {
        return $.map($('#gdTraZyTable').bootstrapTable('getSelections'), function (row) {
            return row.status//返回数据行中的id值
        });
    }

    function getTypeSelections() {
        return $.map($('#gdTraZyTable').bootstrapTable('getSelections'), function (row) {
            return row.operationType//返回数据行中的id值
        });
    }

    function toSP() {
        window.location = '${base}/bus/gdTraZy/edit2';
    }

    function subHandler() {
        var status = getStatusSelections();
        if ("3" != status) {
            alert('不能提交审批');
            return false;
        }
        var type = getTypeSelections();
        var pid = getIdSelections();
        window.location = '${base}/bus/gdTraZy/submit/' + pid + '/' + type;
    }

    function editHandler() {
        var status = getStatusSelections();
        if ("4" == status) {
            alert('审批中不能编辑');
            return false;
        }
        var pid = getIdSelections();
        var type = getTypeSelections();
        if ('1' == type) {
            window.location = '${base}/bus/gdTraZy/edit1/' + pid;
        } else {
            window.location = '${base}/bus/gdTraZy/editJy/' + pid;
        }

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
                url: '${base}/bus/gdTraZy/remove/' + pid,
                type: 'json',
                success: function (data) {
                    window.location = '${base}/bus/gdTraZy/list';
                }
            });
        }
    }

</script>

</body>
</html>