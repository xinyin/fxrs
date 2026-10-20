<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>首页仪表盘布局映射表，可映射到角色、角色组等，现在只使用映射到角色。</title>
    <style type="text/css">
    </style>
</head>
<body class="gray-bg">
<div class="row animated fadeInRight">
    <bc:ibox title="角色组授权" subTitle="" col="12" collapsible="true">
        <bf:form id="queryForm" validation="true">
            <bc:row>
                <bc:col col="4">
                    <bc:label text="角色组编码" col="4" formControl="true"/>
                    <bf:text name="roleGroupCode" col="8" placeholder="角色组编码"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="角色组名称" col="4" formControl="true"/>
                    <bf:text name="roleGroupName" col="8" placeholder="角色组名称"/>
                </bc:col>
                <bc:col col="4">
                    <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                    <bc:button id="btnReset" text="重置" icon="refresh"/>
                </bc:col>
            </bc:row>
        </bf:form>
        <bc:table id="dashLayoutMapTable" idField="roleGroupCode" queryUrl="searchDashLayoutRoleGroupMap"
                  url="${base}/sys/dashLayoutMap">
            <bc:toolbar id="roleGroupEdit" text="设置" type="button" handler="roleGroupEdit"/>
            <bc:column field="roleGroupCode" title="角色组编码" width="50"/>
            <bc:column field="roleGroupName" title="角色组名称" width="50"/>
            <bc:column field="roleGroupDesc" title="角色组描述" width="50"/>
            <bc:column field="layoutCode" title="布局编码" width="50"/>
            <bc:column field="layoutName" title="布局名称" width="50"/>
            <bc:operator id="see" icon="eye" title="查看" handler="see"/>
        </bc:table>
    </bc:ibox>

    <bc:ibox title="角色授权" subTitle="" col="12" collapsible="true">
        <bf:form id="queryRoleMapForm" validation="true">
            <bc:col col="4">
                <bc:label text="岗位编码" col="4" formControl="true"/>
                <bf:text name="roleCode" id="roleCode" col="8" placeholder="角色组编码"/>
            </bc:col>
            <bc:col col="4">
                <bc:label text="岗位名称" col="4" formControl="true"/>
                <bf:text name="roleName" id="roleName" col="8" placeholder="角色组名称"/>
            </bc:col>
            <bc:col col="4">
                <bc:button id="btnQueryRoleMap" type="primary" text="查询" icon="search" click="btnQueryRoleMap()"/>
                <bc:button id="btnReset" click="resetRoleMap()" text="重置" icon="refresh"/>
            </bc:col>
        </bf:form>
        <bc:table id="dashLayoutMapRoleTable" idField="roleCode" queryUrl="searchDashLayoutRoleMap"
                  url="${base}/sys/dashLayoutMap">
            <bc:toolbar id="roleEdit" text="设置" type="button" handler="roleEdit"/>
            <bc:column field="roleCode" title="岗位编码" width="50"/>
            <bc:column field="roleName" title="岗位名称" width="50"/>
            <bc:column field="roleDesc" title="岗位描述" width="50"/>
            <bc:column field="roleStdName" title="岗位类型" width="50"/>
            <bc:column field="layoutCode" title="布局编码" width="50"/>
            <bc:column field="layoutName" title="布局名称" width="50"/>
            <bc:operator id="seeRole" icon="eye" title="查看" handler="seeRole"/>
        </bc:table>
    </bc:ibox>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    $(function () {
        $('.panel-body').css('border', '0px');
    })

    //查看角色组布局映射
    function see(e, value, row, index) {
        $.messager.modal({
            winId: 'roleGroupViewWin',
            width: 770,
            height: 350,
            title: '查看角色组授权',
            url: '${base}/sys/dashLayoutMap/viewRoleGroppMap',
            args: {row: row}
        });
    }

    //设置
    function roleGroupEdit() {
        var rows = $("#dashLayoutMapTable").jTable("getCheckData");
        var roleGroupCode = rows[0].roleGroupCode;
        var roleGroupName = rows[0].roleGroupName;
        var layoutCode = rows[0].layoutCode;
        $.messager.modal({
            winId: 'roleGroupEditWin',
            width: 770,
            height: 280,
            title: '编辑角色组仪表盘布局映射',
            url: '${base}/sys/dashLayoutMap/roleGroupEdit' + '?roleGroupCode=' + roleGroupCode + '&roleGroupName=' + roleGroupName + '&layoutCode=' + layoutCode,
            args: {row: rows[0]},
            onClose: function () {
                $('#dashLayoutMapTable').bootstrapTable('refresh');
            }
        });
    }

    //查询角色的布局映射
    function btnQueryRoleMap() {
        var roleCode = $("#roleCode").val().trim();
        var roleName = $("#roleName").val().trim();

        var page = $(".page-size").text();
        var pageSize = page.substring(2, page.length);
        var pageNumber = 1;

        var param = {
            pageNumber: pageNumber,
            pageSize: pageSize,
            roleCode: roleCode,
            roleName: roleName
        }
        $.mask.post('${base}/sys/dashLayoutMap/searchDashLayoutRoleMap', param, function (data) {
            $("#dashLayoutMapRoleTable").bootstrapTable('load', data);
        })
    }

    //重置角色布局映射数据
    function resetRoleMap() {
        $("#roleCode").val('');
        $("#roleName").val('');
        $("#dashLayoutMapRoleTable").bootstrapTable('refresh');
    }


    //查看角色布局映射
    function seeRole(e, value, row, index) {
        $.messager.modal({
            winId: 'roleViewWin',
            width: 770,
            height: 400,
            title: '查看角色授权',
            url: '${base}/sys/dashLayoutMap/viewRoleMap',
            args: {row: row}
        });
    }

    //角色设置
    function roleEdit() {
        var rows = $("#dashLayoutMapRoleTable").jTable("getCheckData");
        var roleCode = rows[0].roleCode;
        var roleName = rows[0].roleName;
        var roleStdName = rows[0].roleStdName;
        var layoutCode = rows[0].layoutCode;

        $.messager.modal({
            winId: 'roleEditWin',
            width: 770,
            height: 280,
            title: '编辑岗位仪表盘布局映射',
            url: '${base}/sys/dashLayoutMap/dashLayoutRoleMapEdit?' + 'roleCode=' + roleCode + '&roleName=' + roleName + '&roleStdName=' + roleStdName + '&layoutCode=' + layoutCode,
            onClose: function () {
                $('#dashLayoutMapRoleTable').bootstrapTable('refresh');
            }
        });
    }


</script>
</body>
</html>