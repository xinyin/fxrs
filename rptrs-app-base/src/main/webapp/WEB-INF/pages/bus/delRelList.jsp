<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>解除关联关系库</title>
    <style>
        .form-group .input-group.date,
        .form-group .select2 {
            width: 100% !important;
        }

        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }
        #queryId{
            display: none;
        }
        #advancedQuery_id{
            height:30px; line-height:30px; width:100px; overflow:hidden;cursor:pointer;
        }
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="关联方名称" col="5" formControl="true"/>
                        <bf:text id="name" name="__LIKES_name" col="7" placeholder="关联方名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件号码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_identifyNumber" col="7" placeholder="证件号码" id="identifyNumber"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="关联方类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_type" id="type" col="7">
                            <bf:option text="请选择" value=""></bf:option>
                            <bf:option text="自然人" value="PSN"></bf:option>
                            <bf:option text="法人或非法人组织" value="ORG"></bf:option>
                            <bf:option text="金融产品" value="PRO"></bf:option>
                            <be:event type="select">
                                setIdentifyType(e.target.value);
                                var input = $("#__LIKES_relType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                 <%--   <bf:formGroup col="3">
                        <bc:label text="解除日期" col="5" formControl="true"/>
                        <bf:datebox id="__GES_createTime" name="__LIKES_createTime" col="7"/>
                    </bf:formGroup>


                    <center>
                        <div id="advancedQueryId">
                            <span id="advancedQuery_id" style="color: #02afff">高级查询▼</span>
                        </div>
                    </center>

                    <div id="queryId" class="queryClass">
                        <bf:formGroup col="3">
                            <bc:label text="关联方类型" col="5" formControl="true"/>
                            <bf:select name="__LIKES_type" id="type" col="7">
                                <bf:option text="请选择" value=""></bf:option>
                                <bf:option text="自然人" value="PSN"></bf:option>
                                <bf:option text="法人或其他组织" value="ORG"></bf:option>
                                <be:event type="select">
                                    setIdentifyType(e.target.value);
                                    var input = $("#__LIKES_relType");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="3">
                            <bc:label text="证件类型" col="5" formControl="true"/>
                            <bf:select name="__LIKES_identifyType" id="identifyType" col="7" dataSource="custom">
                                <bf:option text="请先选择关联方类型" value=""></bf:option>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="3">
                            <bc:label text="经办部门" col="5" formControl="true"/>
                            <bf:select id="transactionDep_id" name="__LIKES_transactionDep" col="7" dataSource="custom">
                                <bf:option text="ALL" value=""></bf:option>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="3">
                            <bc:label text="经办人" col="5" formControl="true"/>
                            <bf:text id="operator_id" name="__LIKES_operator" col="7" placeholder="经办人"/>
                        </bf:formGroup>
                    </div>--%>

                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="delRelTable" idField="pid" url="${base}/bus/delRel">
                <bc:toolbar id="out" text="导出" type="button" handler="out"/>
                <%--                <bc:toolbar id="view" text="查看" type="button" handler="viewHanlder"/>--%>
                <bc:column field="pid" title="pid" width="100" visible="false"/>
                <bc:column field="relPid" title="relPid" width="100" visible="false"/>
                <bc:column field="type" title="关联方类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-OrgOrPsn"/>
                <bc:column field="name" title="关联方名称" width="100"/>
                <bc:column field="identifyType" title="证件类型" width="100"/>
                <bc:column field="identifyNumber" title="证件号码" width="100"/>
                <bc:column field="domesticOrForeign" title="境内/境外" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                <bc:column field="createTimeString" title="更新日期" width="100"/>
                <bc:operator id="view" icon="eye" title="操作/查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    //高级查询
    window.onload = function(){
        var flag = true;
        document.getElementById("advancedQuery_id").onclick = function() {
            if (flag){
                document.getElementById("queryId").style.display = "block";
                flag = false;
            }else{
                document.getElementById("queryId").style.display = "none";
                flag = true;
            }
        }
    }


    //查看按钮
    function viewHanlder() {
        var relId = getRelIdSelections();
        var type = getTypeSelections();
        if (relId == '') {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location = '${base}/bus/delRel/view/' + relId + "/" + type;
    }


    //自增长主键
    function getRelIdSelections() {
        return $.map($('#delRelTable').bootstrapTable('getSelections'), function (row) {
            return row.relPid;
        });
    }
    function getTypeSelections() {
        return $.map($('#delRelTable').bootstrapTable('getSelections'), function (row) {
            return row.type;
        });
    }


    //根据关联方类型查询证件类型下拉选项
    function setIdentifyType(value) {
        $.ajax({
            type: 'GET',
            url: '${base}/bus/delRel/queryIdentifyType?type='+ value,
            dataType: 'json',
            delay: 200,
            success: function (data) {
                var arr = new Array();
                $.each(data, function (i, val) {
                    var obj = {
                        id: val.value,
                        text: val.name
                    };
                    arr[i] = obj;
                });
                $("#identifyType").empty();
                $("#identifyType").select2({data: arr, width: "100%"});
            }
        });
    }


    //导出按钮
    function out() {
        var name = $("#name").val().trim();
        var identifyNumber = $("#identifyNumber").val();
        var type = $("#type").val();

        if (name == '' || name == undefined) {
            name = 'undefined';
        }
        if (identifyNumber == '' || identifyNumber == undefined) {
            identifyNumber = 'undefined';
        }
        if (type == ' ' || type == undefined) {
            type = 'undefined';
        }
        var data = name + "," + identifyNumber + "," + type;
        //下载文档
        var url = '${base}/sys/xlsie/DelRelExportCallbackImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    //重置
    function frmReset() {
        window.location = '${base}/bus/delRel/list';
    }
</script>
</body>
</html>