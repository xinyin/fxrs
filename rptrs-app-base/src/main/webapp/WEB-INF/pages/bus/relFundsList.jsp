<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">

        #relFundsTable_tb_add, #relFundsTable_tb_edit,
        #relFundsTable_tb_remove{
            display: none;
        }
    </style>
    <title>RelFunds</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="与单一关联方投资余额管理" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="年度" col="5" formControl="true"/>
                        <bf:datebox id="investmentBalanceTimes" name="investmentBalanceTime" col="7" format="date">
                            <bv:date format="YYYY" message="只能输入合理的年份，类型为YYYY类型"/>
                        </bf:datebox>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="月度" col="5" formControl="true"/>
                        <bf:select id="quarter" name="quarter" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Monthes">
                            <bf:option text="本年度" value="0"></bf:option>
                            <be:event type="select">
                                var input = $("#quarter");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="3">
                        <bc:label text="关联方类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_relType" id="type" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-relType">
                            <bf:option text="请选择" value="%"></bf:option>
                            <be:event type="select">
                                setIdentifyType(e.target.value);
                                var input = $("#__LIKES_relType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="关联方名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_relName" col="7"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="关联方证件号码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_relIdentifyNumber" col="7"/>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frReset()" />
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="relFundsTable" idField="pid" url="${base}/bus/relFunds"
                      queryUrl="query1">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="investmentBalanceTimeString" title="投资余额统计时间" width="100"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="relType" title="关联方类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="relIdentifyNumber" title="关联方证件号" width="100"/>
                <bc:column field="fundsInvestmentBalance" title="资金运用投资余额" width="100"/>
<%--                <bc:column field="relTotalAssets" title="关联方总资产" width="100"/>--%>
                <bc:column field="status" title="状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS_FundsStatus"/>
                <bc:operator id="view" icon="eye" title="操作"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    //根据权限显示按钮
    $(function () {
        var code = "${code}";
        if (code.search("01,") != -1) {
            $('#relFundsTable_tb_add').show();
        }
        if (code.search("02,") != -1) {
            $('#relFundsTable_tb_edit').show();
        }
        if (code.search("03") != -1) {
            $('#relFundsTable_tb_remove').show();
        }
    });

    $(document).ready(function () {
        // $("input[name='__LIKES_years']").val(new Date().getUTCFullYear());
        $("#investmentBalanceTimes").datetimepicker("setDate", new Date());

    });
    $("#investmentBalanceTimes").datetimepicker({
        format: "yyyy",
        autoclose: true,
        startView: 4,
        minView: 4,
        todayHighlight: true,
    });
    $("#investmentBalanceTimes").change(function () {
        if (
            $("#investmentBalanceTimes").datetimepicker("getDate") == "" ||
            $("#investmentBalanceTimes").datetimepicker("getDate") == null ||
            $("#investmentBalanceTimes").datetimepicker("getDate") == undefined
        ) {
            $("#investmentBalanceTimes").datetimepicker("setDate", new Date());
        }
    });

    /*获取pid*/
    function getIdSelections() {
        return $.map($('#relFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    //获取状态
    function getStausSelections() {
        return $.map($('#relFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    //编辑
    function editHandler() {

        var pid = getIdSelections();

        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        } else {
            window.location = '${base}/bus/relFunds/edit/' + pid;
        }
    }

    //删除
    function removeHandler() {
        var pid = getIdSelections();
        var status = getStausSelections();
       /* if (status == '1') {
            $.messager.alert('错误', "只能删除待补录数据", 'error');
            return;
        }*/

    if (pid == '' || pid == null || pid.length != 1) {
    	$.messager.alert('错误', "请选择一条数据", 'error');
    	return;
    }else{
        $.messager.confirm('提示', '确定要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    url: '${base}/bus/relFunds/remove/'+pid,
                    dataType: 'json',
                    type: 'post',
                    success: function (data) {
                        if (data.success) {
                            $.messager.alert(data.msg);
                            window.location = '${base}/bus/relFunds/list';
                        } else {
                            $.messager.alert(data.msg);
                        }
                    }
                });
            }
        })
    }
    window.location = "${base}/bus/rel/removeOrg/" + relId;
    }

    function frReset() {
        window.location = '${base}/bus/relFunds/list';
    }

</script>
</body>
</html>
