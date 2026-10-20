<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>股东信息</title>
    <script>

        //重置
        function frmReset() {
            window.location = '${base}/bus/gdInfo/list';
        }

    </script>
</head>
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="股东账号" col="5" formControl="true"/>
                        <bf:text name="__LIKES_shareholdersCode" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="股东名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_name" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="股东类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_type" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-OrgOrPsn">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_type");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="是否国企" col="5" formControl="true"/>
                        <bf:select name="__LIKES_stateOwned" col="7" dataSource="custom"
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
                        <bc:label text="持股占比（%）" col="5" formControl="true"/>
                        <bf:text name="__LIKES_shareholdingProportion" col="7" placeholder=""/>
                    </bf:formGroup>
                   <%-- <bc:col col="3">
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
            <bc:table id="relPsnTable" idField="pid" url="${base}/bus/gdInfo">
                <bc:toolbar id="PSNkh" text="新增自然人股东" type="button" handler="PSNkhHandler"/>
                <bc:toolbar id="ORGkh" text="新增机构股东" type="button" handler="ORGkhHandler"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="xh" text="销户" type="button" handler="xhHandler"/>
                <bc:toolbar id="dj" text="账户冻结" type="button" handler="djHandler"/>
                <bc:toolbar id="jd" text="账户解冻" type="button" handler="jdHandler"/>
                <bc:toolbar id="submit" text="提交审批" type="button" handler="openSubHandler"/>
                <bc:toolbar id="black" text="加入/移除黑名单" type="button" handler="blackHandler"/>
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="shareholdersCode" title="股东账号" width="100"/>
                <bc:column field="name" title="股东名称" width="100"/>
                <bc:column field="type" title="股东类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-RelatedPartiesType"/>
                <bc:column field="stateOwned" title="是否国企" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <bc:column field="domesticOrForeign" title="境内/境外" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                <bc:column field="identifyType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="identifyNumber" title="证件号码" width="100"/>
                <bc:column field="holeShareAmount" title="持股数量（股）" width="100"/>
                <bc:column field="shareholdingProportion" title="持股占比（%）" width="100"/>
                <bc:column field="isDJ" title="是否冻结" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <bc:column field="status" title="审批状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="statusComment" title="状态说明" width="100"/>
                <bc:column field="isBlack" title="黑名单标识" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <bc:operator id="viewGdInfo" icon="eye" title="查看" handler="viewHandler"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    function getIdSelections() {
        return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    function getStatusSelections() {
        return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    function getTypeSelections() {
        return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
            return row.type;
        });
    }

    function getdjStatuspeSelections() {
        return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
            return row.isDJ;
        });
    }

    function PSNkhHandler() {
        window.location = '${base}/bus/gdInfo/psnOpen';
    }

    function ORGkhHandler() {
        window.location = '${base}/bus/gdInfo/orgOpen';
    }

    function xhHandler() {
        var status = getStatusSelections();
        if ('4' == status) {
            alert('审批中的数据不能销户');
            return false;
        }
        if (confirm('确定销户?')) {
            var type = getTypeSelections();
            var pid = getIdSelections();
            window.location = '${base}/bus/gdInfo/submit/xh/' + type + '/' + pid;
        }
    }

    function djHandler() {
        var status = getStatusSelections();
        if ('4' == status) {
            alert('数据正在审批中');
            return false;
        }
        var jd = getdjStatuspeSelections();
        if (jd == '1') {
            alert('已冻结');
            return false;
        }
        var type = getTypeSelections();
        var pid = getIdSelections();
        window.location = '${base}/bus/gdInfo/submit/dj/' + type + '/' + pid;
    }

    function jdHandler() {
        var status = getStatusSelections();
        if ('4' == status) {
            alert('数据正在审批中');
            return false;
        }
        var jd = getdjStatuspeSelections();
        if (jd != '1') {
            alert('非冻结状态');
            return false;
        }
        var type = getTypeSelections();
        var pid = getIdSelections();
        window.location = '${base}/bus/gdInfo/submit/jd/' + type + '/' + pid;
    }

    function blackHandler() {
        var status = getStatusSelections();
        if ('4' == status) {
            alert('数据正在审批中');
            return false;
        }
        var type = getTypeSelections();
        var pid = getIdSelections();
        window.location = '${base}/bus/gdInfo/submit/black/' + type + '/' + pid;
    }

    function openSubHandler() {
        var status = getStatusSelections();
        if ('4' == status) {
            alert('数据正在审批中');
            return false;
        }
        if('2' == status){
            alert('待补录不能提交审批');
            return false;
        }
        var type = getTypeSelections();
        var pid = getIdSelections();
        window.location = '${base}/bus/gdInfo/openSubmit/' + pid + '/' + type;
    }

    function editHandler() {
        var type = getTypeSelections();
        var pid = getIdSelections();
        var status = getStatusSelections();
        if ('4' == status) {
            alert('数据正在审批中');
            return false;
        }
        window.location = '${base}/bus/gdInfo/edit/' + pid + '/' + type;
    }

    function viewHandler() {
        var type = getTypeSelections();
        var pid = getIdSelections();
        window.location = '${base}/bus/gdInfo/view/' + pid + '/' + type;
    }
</script>
</body>
</html>