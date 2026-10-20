<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>其他类交易管理</title>
    <script>


        /* 任务下发 */
        function sendCommand(){
            var tables =  $('#otherTypeTable').jTable('getCheckData');
            if(tables.length == 1)
            {
                $.messager.modal({
                    winId: 'otherTypeSend',
                    width: 700,
                    height: 420,
                    title: '选择机构',
                    url: '${base}/bus/otherType/otherTypeSend',
                    args: {orgId: tables[0].pid},
                    onClose: function(){
                        $('#otherTypeTable').jTable('clearCheckData');
                        $('#otherTypeTable').jTable('refresh');
                    }
                });
            }
            else
            {
                $.messager.toastr('提示', '请选择一条数据');
            }
        }

        //重置
        function frmReset() {
            window.location = '${base}/bus/otherType/list';
        }


    </script>
    <link rel="stylesheet" href="../../static/css/dialog.css">
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="其他关联交易管理类" subTitle="请输入查询信息" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="关联方名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_relatedName" col="7" placeholder="关联方名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_identlfyType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-IdentifyType">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_identlfyType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件号码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_identNumber" col="7" placeholder="证件号码"/>
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
                        <bc:label text="交易类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_businessType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-OtherType">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_businessType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="统计口径" col="5" formControl="true"/>

                        <bf:select name="__LIKES_regulatorOth1" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Caliber">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_regulatorOth1");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="是否重大关联交易" col="5" formControl="true"/>
						<bf:select name="__LIKES_importantTrade" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-isNo">
							<bf:option text="ALL" value="%"></bf:option>
							<be:event type="select">
								var input = $("#__LIKES_importantTrade");
								input.focus();
								input.blur();
							</be:event>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="交易机构" col="5" formControl="true"/>
						<bf:select name="__LIKES_houseManageOrg" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-GHOrg">
							<bf:option text="ALL" value="%"></bf:option>
							<be:event type="select">
								var input = $("#__LIKES_houseManageOrg");
								input.focus();
								input.blur();
							</be:event>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="业务起始时间" col="5" formControl="true"/>
						<bf:datebox id="businessStartTime" name="__GES_businessStartTime" col="7"/>
					</bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="业务结束时间" col="5" formControl="true"/>
                        <bf:datebox id="businessEndTime" name="__LES_businessEndTime" col="7"/>
                    </bf:formGroup>
                <%--<bc:row cls="m-b">
                    <bc:col col="3" colOffset="1">
                        <bc:col col="3" colOffset="1">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        </bc:col>
                        <bc:col col="3" colOffset="1">
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                        </bc:col>
                    </bc:col>--%>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>

                </bc:row>
            </bf:form>
            <bc:table id="otherTypeTable" idField="pid" url="${base}/bus/otherType">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="import" text="导入" type="button"/>
                <bc:toolbar id="importExp" text="任务下发" type="button" handler="sendCommand"/>
                <bc:toolbar id="" text="模板下载" type="button"/>
                <bc:toolbar id="sub" text="提交审批" handler="sub" type="button"/>
                <bc:toolbar id="" text="导出" type="button"/>
                <bc:column field="pid" title="序号" width="100" visible='false'/>
                <bc:column field="relatedName" title="关联方名称" width="100"/>
                <bc:column field="houseManageOrg" title="机构名称" width="100"/>
                <bc:column field="identlfyType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="identNumber" title="证件号码" width="100"/>
                <bc:column field="tranType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-BusinessType"/>
                <bc:column field="businessType" title="业务类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-OtherType"/>
                <bc:column field="transationAmount" title="交易金额/万元/人民币" width="100"/>
                <bc:column field="importantTrade" title="是否重大关联交易" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <bc:column field="regulatorOth1" title="统计口径" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Caliber"/>
                <bc:column field="businessEndTime" title="业务完成时间" width="100"/>
                <bc:column field="relatedTranType" title="关联交易类别" width="100" visible="false"/>
                <bc:column field="status" title="状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="statusComment" title="状态说明" width="100"/>
                <bc:column field="dataResource" title="数据来源" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DataSources"/>
                <bc:operator id="view" icon="search" title="查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script src="../../static/js/jquery.dialog.js"></script>
<script type="text/javascript">

    function getIdSelections() {
        return $.map($('#otherTypeTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;//返回数据行中的id值
        });
    }

    function getStatusSelections() {
        return $.map($('#otherTypeTable').bootstrapTable('getSelections'), function (row) {
            return row.status;//返回数据行中的id值
        });
    }

    function getdataResourceSelections() {
        return $.map($('#otherTypeTable').bootstrapTable('getSelections'), function (row) {
            return row.dataResource;//返回数据行中的id值
        });
    }

    function editHandler() {
        var dataResource = getdataResourceSelections();
        if (dataResource != '1'){
            $.messager.alert('抽取的数据不能修改');
            return false;
        }
        var status = getStatusSelections();
        if (status == '4'){
            $.messager.alert('审批中不能修改');
            return false;
        }

        var pid = getIdSelections();
        window.location = '${base}/bus/otherType/edit/' + pid;
    }

    function removeHandler() {
        var dataResource = getdataResourceSelections();
        if (dataResource != '1'){
            $.messager.alert('抽取的数据不能删除');
            return false;
        }
        var status = getStatusSelections();
        if (status != '3'){
            $.messager.alert('已提交审批的不能删除');
            return false;
        }
        if (confirm("确认删除?")) {
            var pid = getIdSelections();
            $.ajax({
                url: '${base}/bus/otherType/remove/' + pid,
                type: 'post',
                data: 'json',
                success: function (data) {
                    if (data.success) {
                        $.messager.alert(data.msg);
                        window.location = '${base}/bus/otherType/list';
                    }
                }
            });
        }

        var pid = getIdSelections();
        $.ajax({
            url: '${base}/bus/otherType/remove/' + pid,
            type: 'post',
            data: 'json',
            success: function (data) {
                if (data.success) {
                    $.messager.alert(data.msg);
                    window.location = '${base}/bus/otherType/list';
                }
            }
        });
    }
    function sub() {
        var status = getStatusSelections();
        if (status != '3'){
            $.messager.alert('不能提交审批');
            return false;
        }
        var pid = getIdSelections();
        window.location = '${base}/bus/otherType/submit/' + pid;
    }

</script>
</body>
</html>