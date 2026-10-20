<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>导出文件数据表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="股东代码" col="5" formControl="true"/>
                        <bf:text id="shareholdersCode" name="__LIKES_shareholdersCode" col="7" placeholder="股东代码"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="股东名称" col="5" formControl="true"/>
                        <bf:text id="name" name="__LIKES_name" col="7" placeholder="股东名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="股东类型" col="5" formControl="true"/>
                        <bf:select id="shareholdersType" name="__LIKES_shareholdersType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelatedPartiesType">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_shareholdersType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="是否国有企业" col="5" formControl="true"/>
                        <bf:select id="stateOwned" name="__EQS_stateOwned" col="7" dataSource="custom"
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
                        <bc:label text="境内境外" col="5" formControl="true"/>
                        <bf:select id="domesticOrForeign" name="__LIKES_domesticOrForeign" col="7" dataSource="custom"
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
                        <bf:select id="status" name="__LIKES_status" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Applystatus">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_status");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bc:col col="12" colOffset="5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="gdFileDataTable" idField="pid" url="${base}/bus/gdFileData"
                      queryUrl="getAllGdInfo?batchNumber=${batchNumber}">
                <%--                <bc:toolbar id="add" text="新增" type="button"/>--%>
                <%--				<bc:column field="pid" title="主键" width="100"/>--%>
                <bc:column field="shareholdersCode" title="股东代码" width="100"/>
                <bc:column field="name" title="股东名称" width="100"/>
                <bc:column field="shareholdersType" title="股东类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-RelatedPartiesType"/>
                <bc:column field="stateOwned" title="是否国有企业" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <bc:column field="demesticOrForeign" title="境内境外" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                <bc:column field="identifyType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="identifyNumber" title="证件号码" width="100"/>
                <bc:column field="djStatus" title="账户冻结" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <bc:column field="holeShareAmount" title="持股数量" width="100"/>
                <bc:column field="shareholdingProportion" title="持股占比" width="100"/>
                <bc:column field="status" title="审批状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="statusComment" title="状态说明" width="100"/>
                <bc:column field="batchNumber" title="数据批次" width="100" visible="false"/>
                <%--                <bc:operator id="view" icon="search" title="查看"/>--%>
                <%--                <bc:operator id="edit" icon="edit" title="编辑"/>--%>
                <%--                <bc:operator id="remove" icon="trash" title="移除"/>--%>
            </bc:table>
            <div id="file" style="display: none">
                <bf:formGroup col="4">
                    <bc:label text="文件名称" col="5" formControl="true"/>
                    <bf:text id="fileName" name="file" col="7" placeholder="文件名称"/>
                </bf:formGroup>
<%--                <bc:button type="success" outline="true" text="生成导出文件" icon="save" click="save()">--%>
<%--                    <be:event>--%>
<%--                    </be:event>--%>
<%--                </bc:button>--%>
            </div>
            <bc:col col="12" colOffset="5">
            <bc:buttonGroup cls="btn-group">
                <div style="display: inline-block;">
                <bc:button type="success" outline="true" text="确定" icon="save">
                    <be:event>
                        var a = $('#file').is(':hidden');
                        if(a){
                        $('#file').show();
                        } else {
                        save();
                        }
                    </be:event>
                </bc:button>
                </div>
                <div style="display: inline-block;margin-left: 10px">
                <bc:button type="info" outline="true" text="返回">
                    <be:event>
                        window.location = '${base}/bus/gdExportFile/list';
                    </be:event>
                </bc:button>
                </div>
            </bc:buttonGroup>
            </bc:col>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
<script>
    function save() {
        var shareholdersType = $('#shareholdersType').val();
        var fileName = $('#fileName').val();
        var shareholdersType = $('#shareholdersType').val();
        var stateOwned = $('#stateOwned').val();
        var domesticOrForeign = $('#domesticOrForeign').val();
        var status = $('#status').val();
        var obj = {
            shareholdersType: shareholdersType,
            fileName: fileName,
            shareholdersType: shareholdersType,
            stateOwned: stateOwned,
            domesticOrForeign: domesticOrForeign,
            status: status
        }
        $.ajax({
            url: '${base}/bus/gdFileData/newFileBatch',
            data: obj,
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    alert(data.msg);
                    window.location = '${base}/bus/gdExportFile/list';
                }
            }
        });
    }
</script>
</html>