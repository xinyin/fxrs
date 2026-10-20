<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ taglib prefix="be" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>单一查询</title>
    <style>
        .form-group {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
            width: 10%;
        }

        .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }

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
    </style>
</head>
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup hrp="bottom">
                        <bf:formGroup col="4">
                            <bc:label text="机构名称" col="5" formControl="true"/>
                            <bf:select name="__LIKES_traSubName" id="__LIKES_traSubName" col="7" dataSource="custom" includes="0001,ZZ_Delegation_01,ZZ_Delegation_02,ZZ_Delegation_03,ZZ_Delegation_04"
                                       url="sys/dictItem/getDictItems/INS-subjectName" value="${unitCode}" disabled="${opt == 'MH'}">
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="关联方类别" col="5" formControl="true"/>
                            <bf:select name="__LIKES_type" id="type" col="7">
                                <bf:option text="关联自然人" value="psn"></bf:option>
                                <bf:option text="关联法人或其他组织" value="org"></bf:option>
                                <be:event type="select">
                                    fun(e.target.value)
                                </be:event>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="关联方名称" col="5" formControl="true"/>
                            <bf:text name="__LIKES_name" col="7" placeholder="关联方名称"/>
                        </bf:formGroup>
                        <bf:formGroup col="4" id="p">
                            <bc:label text="证件类型" col="5" formControl="true"/>
                            <bf:select name="__LIKES_identifyTypePSN" id="__LIKES_identifyTypePSN" col="7"
                                       dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-IdentifyType">
                                <bf:option text="ALL" value=""></bf:option>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4" id="o">
                            <bc:label text="证件类型" col="5" formControl="true"/>
                            <bf:select name="__LIKES_identifyTypeORG" id="__LIKES_identifyTypeORG" col="7"
                                       dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-ORGIdentifyType">
                                <bf:option text="ALL" value=""></bf:option>
                                <be:event type="select">
                                    var input = $("#__LIKES_identifyTypeORG");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="证件号码" col="5" formControl="true"/>
                            <bf:text name="__LIKES_identifyNumber" id="__LIKES_identifyNumber" col="7"
                                     placeholder="证件号码"><bv:notEmpty/></bf:text>

                        </bf:formGroup>
                    </bf:formGroup>
                    <bf:formGroup hrp="bottom">
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="relPsnTable" idField="pid" url="${base}/bus/relPsn" selectFirst="true"
                      queryUrl="relSinQuery1">
                <bc:toolbar id="out" text="导出" type="button" handler="out"/>
                <bc:toolbar id="view" text="查看" type="button" handler="query"/>
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="regCode" title="登记编码" width="100" visible="false"/>
                <bc:column field="name" title="关联方名称" width="100"/>
                <bc:column field="domesticOrForeign" title="境内外标识" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>

                <bc:column field="identifyTypePSN" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="identifyTypeORG" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-ORGIdentifyType"/>
                <bc:column field="identifyNumber" title="证件号码" width="100"/>
<%--                <bc:operator id="viewAffiliate1" icon="eye" title="查看" handler="query"/>--%>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    $(document).ready(function () {
        var type = $('#type').val();
        selectIdentifyType(type);
    });

    //页面默认关联关系是自然人
    function selectIdentifyType(val) {
        if (val == 'psn') {
            fun(val)
            $('#relPsnTable').bootstrapTable('hideColumn', 'identifyTypeORG');
            $('#relPsnTable').bootstrapTable('showColumn', 'identifyTypePSN');

        }
        if (val == 'org') {
            fun(val)
            $('#relPsnTable').bootstrapTable('showColumn', 'identifyTypeORG');
            $('#relPsnTable').bootstrapTable('hideColumn', 'identifyTypePSN');
        }
    }

    $("#btnQuery").on("click", function () {
        var type = $("#type").val();
        selectIdentifyType(type)
    });


    function fun(val) {
        if (val == 'psn') {
            $("#o").hide();
            $("#p").show();
        }
        if (val == 'org') {
            $("#o").show();
            $("#p").hide();
        }
    }


    //根据不同的标记发送不同的请求进行导出数据处理
    function out() {
        var flag = $("#type").val();
        if (flag == "psn") {
            //自然人导出数据
            out1();
        } else {
            //关联机构导出数据
            out2();
        }
    }

    //__LIKES_traSubName   __LIKES_name   __LIKES_identifyTypePSN   __LIKES_identifyTypeORG  __LIKES_identifyNumber
    function out1() {
        debugger
        var mgerOrg = $("#__LIKES_traSubName").val();
        var name = $("#__LIKES_name").val();
        var identifyType = $("#__LIKES_identifyTypePSN").val();
        var identifyNumber = $("#__LIKES_identifyNumber").val();
        if (name == '' || name == undefined) {
            name = 'undefined';
        }
        if (identifyNumber == '' || identifyNumber == undefined) {
            identifyNumber = 'undefined';
        }
        if (identifyType == ' ' || identifyType == undefined) {
            identifyType = 'undefined';
        }
        var data = mgerOrg + ',' + name + ',' + identifyType + ',' + identifyNumber;
        var data1 = getIdSelections();
        var url = '${base}/sys/xlsie/sinOrBatPsnExportImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    function out2() {
        var mgerOrg = $("#__LIKES_traSubName").val();
        var name = $("#__LIKES_name").val();
        var identifyType = $("#__LIKES_identifyTypeORG").val();
        var identifyNumber = $("#__LIKES_identifyNumber").val();

        if (name == '' || name == undefined) {
            name = 'undefined';
        }
        if (identifyNumber == '' || identifyNumber == undefined) {
            identifyNumber = 'undefined';
        }
        if (identifyType == ' ' || identifyType == undefined) {
            identifyType = 'undefined';
        }
        var data = mgerOrg + ',' + name + ',' + identifyType + ',' + identifyNumber;
        var data1 = getIdSelections();
        // alert(data);
        var url = '${base}/sys/xlsie/sinOrBatOrgExportImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    //查看按钮
    function query() {
        var pid = getIdSelections();
        var val = $("#type").val();
        // alert(val);
        if (pid == '') {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (val == 'psn') {
            window.location = '${base}/bus/delRel/viewPSN/' + pid;
        } else {
            window.location = '${base}/bus/delRel/viewORG/' + pid;

        }
    }


    //自增长主键
    function getIdSelections() {
        return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    //证件类别
    function getIdentifyTypeSelections() {
        return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
            return row.identifyType;
        });
    }


    function removeRelPsn() {
        var pid = getIdSelections();
        $.ajax({
            url: "${base}/bus/rel/queryRel/" + pid + "/PSN",
            <%--url:"${base}/bus/relPsn/remove/"+pid,--%>
            method: "GET",
            data: {"": ""},
            dataType: "JSON",
            traditional: true,
            success: function (data) {
                if (data.success) {
                    alert("可以删除")
                    <%--window.location = '${base}/bus/relPsn/list';--%>
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        })
    }


    //刷新表格
    function loadCallback() {
        $('#naturalPersonTable').jTable('refresh');
    }

    function frmReset() {
        window.location = '${base}/bus/relPsn/relSinQuery';
    }

</script>
</body>
</html>