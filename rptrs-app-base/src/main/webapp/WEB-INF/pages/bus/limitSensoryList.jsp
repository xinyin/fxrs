<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>LimitSensory</title>
    <style>
        .form-group .input-group.date,
        .form-group .select2{
            width: 100% !important;
        }
        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }
        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup hrp="bottom">
                        <bc:col col="4">
                            <bc:label text="交易主体" col="5" formControl="true"/>
                            <bf:select id="sensoryIndex" name="__LIKES_sensoryIndex" col="7" dataSource="custom"
                                       url="bus/limit/getTranOrgItems" value="${tranOrg}">
                                <bf:option text="ALL" value="%"></bf:option>
                                <be:event type="select">
                                    var input = $("#__LIKES_sensoryIndex");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="交易类型" col="5" formControl="true"/>
                            <bf:select id="tradeType" name="__LIKES_tradeType" col="7" dataSource="custom"
                                       url="bus/limit/getTradeTypeItems">
                                <bf:option text="ALL" value="%"></bf:option>
                                <be:event type="select">
                                    setBusinessType(e.target.value);
                                </be:event>
                            </bf:select>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="业务类型" col="5" formControl="true"/>
                            <bf:select id="businessType" name="__LIKES_businessType" col="7" dataSource="custom"
                                       url="bus/limit/getBuinessTypeItems" >
                                <bf:option text="ALL" value="%"></bf:option>
                            </bf:select>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup hrp="bottom">
                        <bc:col col="4">
                            <bc:label text="产品名称" col="5" formControl="true"/>
                            <bf:text id="proPath" name="__LIKES_proPath" col="7" placeholder="产品名称"/>
                        </bc:col>
                       <%-- <bc:col col="4">
                            <bc:label text="预警级别" col="5" formControl="true"/>
                            <bf:select id="warnLeve" name="__LIKES_warnLeve" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-Warninglevel">
                                <bf:option text="ALL" value="%"></bf:option>
                                <be:event type="select">
                                    var input = $("#__LIKES_warnLeve");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bc:col>--%>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="limitSensoryTable" idField="pid" url="${base}/bus/limitSensory">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editLimitSensory"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeLimitSensory"/>
                <bc:column field="sensoryIndex" title="交易主体" width="150"  formatter="custom: bus/limit/getTranOrgItems"/>
                <%--                <bc:column field="sensoryIndex" title="交易机构" width="100"/>--%>
                <bc:column field="tradeType" title="交易类型" width="100"
                           formatter="custom:bus/limit/getTradeTypeItems"/>
                <bc:column field="businessType" title="业务类型" width="100"
                           formatter="custom:bus/limit/getBuinessTypeItems"/>
                <bc:column field="busLeveName" title="产品名称" width="100"/>
                <bc:column field="warnThreshold1l" title="一级预警阈值" width="100"/>
                <bc:column field="warnThreshold2l" title="二级预警阈值" width="100"/>
                <bc:column field="warnThreshold3l" title="三级预警阈值" width="100"/>
                <bc:operator id="view" icon="search" title="查看"/>

            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    var tradeType = $('#tradeType').val();
    var businessType = $('#businessType').val();
    debugger;
    //初始化内容脚本
    $(document).ready(function(){
        if(tradeType == null || tradeType == '' || tradeType == undefined ){
            setBusinessType(tradeType);
        }
    });
    //通过交易类型查询相对应的业务类型
    function setBusinessType(value){
        $.ajax({
            type: 'GET',
            url: '${base}/bus/limit/queryBusinessType/' + value,
            dataType: 'json',
            delay: 200,
            success: function(data){
                debugger;
                var arr = new Array();
                $.each(data, function(i, val){
                    var obj = {
                        id: val.value,
                        text: val.name
                    };
                    arr[i] = obj;
                });
                $("#businessType").empty();
                $("#businessType").select2({data: arr, width: "100%"});
            }
        });
    }


    //编辑
    function editLimitSensory() {
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (pid != "") {
            window.location = '${base}/bus/limitSensory/edit/' + pid + '?';
        } else {
            $.messager.alert('提示', '请选择。', 'info');
            return "";
        }
    }

    //删除
    function removeLimitSensory() {
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (pid != "") {
            if (confirm("确认删除吗")) {
                window.location = '${base}/bus/limitSensory/remove/' + pid + '?';
            }
        } else {
            $.messager.alert('提示', '请选择。', 'info');
            return "";
        }

    }
    //获取复选框id
    function getIdSelections() {
        return $.map($('#limitSensoryTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    //重置
    function frmReset() {

        window.location = '${base}/bus/limitSensory/list';
    }


</script>
</body>
</html>