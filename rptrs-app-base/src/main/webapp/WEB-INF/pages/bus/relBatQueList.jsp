<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>批量查询</title>
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
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">

        <bc:ibox title="" subTitle="" col="12" collapsible="true">
        <bf:form id="queryForm" validation="true">
            <bc:row cls="m-b">
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="口径" col="5" formControl="true"/>
                    <bf:select id="__LIKES_regulatorOth1" name="__LIKES_regulatorOth1" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Caliber" value="${PsnAndOrgVo.regulatorOth1}">
                        <bf:option text="ALL" value=""></bf:option>
                        <be:event type="select">
                            var input = $("#__LIKES_regulatorOth1");
                            input.focus();
                            input.blur();
                        </be:event>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="所属关联方证件号码" col="5" formControl="true"/>
                    <bf:text name="__LIKES_identifyNumber" col="7" placeholder="证件号码"/>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="关联关系类别" col="5" formControl="true"/>
                    <bf:select id="__LIKES_relCategory" name="__LIKES_relCategory" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-RelCategory"
                               includes="A,B,C,D,E" value="${PsnAndOrgVo.relCategory}">
                        <bf:option text="ALL" value=""></bf:option>
                        <be:event type="select">
                            setRelCategorySub(e.target.value);
                        </be:event>
                    </bf:select>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="关系" col="5" formControl="true"/>
                    <bf:select id="__LIKES_relCategorySub" name="__LIKES_relCategorySub" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Rel" value="${PsnAndOrgVo.relCategorySub}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="是否黑名单" col="5" formControl="true"/>
                    <bf:select id="__LIKES_isBlack" name="__LIKES_isBlack" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-isNo">
                        <bf:option text="ALL" value=""></bf:option>
                        <be:event type="select">
                            var input = $("#__LIKES_isBlack");
                            input.focus();
                            input.blur();
                        </be:event>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="集团号" col="5" formControl="true"/>
                    <bf:text name="__LIKES_memGroup" col="7" placeholder=""/>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="集团" col="5" formControl="true"/>
                    <bf:text name="__LIKES_groupName" col="7" placeholder=""/>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="是否国有企业" col="5" formControl="true"/>
                    <bf:select id="stateOwned" name="stateOwned" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-isNo">
                        <bf:option text="ALL" value=""></bf:option>
                        <be:event type="select">
                            var input = $("#stateOwned");
                            input.focus();
                            input.blur();
                        </be:event>
                    </bf:select>
                </bf:formGroup>

                </bf:formGroup>

                <%--					<bf:formGroup col="4">--%>
                <%--						<bc:label text="姓名" col="5" formControl="true"/>--%>
                <%--						<bf:text name="__LIKES_name" col="7" placeholder="姓名"/>--%>
                <%--					</bf:formGroup>--%>
                <%--					<bf:formGroup col="4">--%>
                <%--						<bc:label text="证件类型" col="3" formControl="true"/>--%>
                <%--						<bf:select name="__LIKES_identifyType" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-IdentifyType" >--%>
                <%--							<bf:option text="ALL" value="%"></bf:option>--%>
                <%--							<be:event type="select">--%>
                <%--								var input = $("#__LIKES_identifyType");--%>
                <%--								input.focus();--%>
                <%--								input.blur();--%>
                <%--							</be:event>--%>
                <%--						</bf:select>--%>
                <%--					</bf:formGroup>--%>
                <%--					<bf:formGroup col="4">--%>
                <%--						<bc:label text="证件号码" col="3" formControl="true"/>--%>
                <%--						<bf:text name="__LIKES_identifyNumber" col="8" placeholder="证件号码"/>--%>
                <%--					</bf:formGroup>--%>
                <%--<bf:formGroup col="4">--%>
                <%--<bc:label text="审批状态" col="5" formControl="true"/>--%>
                <%--<bf:select name="__LIKES_status" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Applystatus" >--%>
                <%--<bf:option text="ALL" value="%"></bf:option>--%>
                <%--<be:event type="select">--%>
                <%--var input = $("#__LIKES_status");--%>
                <%--input.focus();--%>
                <%--input.blur();--%>
                <%--</be:event>--%>
                <%--</bf:select>--%>
                <%--</bf:formGroup>--%>

                <%--					数据来源--%>
                <%--					<bc:col col="4">--%>
                <%--						<bc:label text="数据来源" col="5" formControl="true"/>--%>
                <%--						<bf:select id="dataSource" name="dataSource" col="7" dataSource="custom"--%>
                <%--								   url="sys/dictItem/getDictItems/BUS-DataSource"--%>
                <%--								   value="${relPsn.dataSource}">--%>
                <%--						</bf:select>--%>
                <%--					</bc:col>--%>
<%--                <bc:col col="5"></bc:col>--%>
                <bc:col col="12" colOffset="5">
                    <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                    <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                </bc:col>
            </bc:row>
        </bf:form>
        <bc:table id="relPsnTable" idField="pid" url="${base}/bus/relPsn" queryUrl="relBatQuery1">
            <bc:toolbar id="out" text="导出" type="button" handler="out"/>
            <bc:toolbar id="print" text="打印" type="button" handler="print"/>
            <bc:toolbar id="view" text="查看" type="button" handler="query"/>
            <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
            <bc:column field="regCode" title="登记编码" width="100" visible="false"/>
            <bc:column field="name" title="关联方名称" width="100"/>
            <bc:column field="domesticOrForeign" title="境内外标识" width="100"
                       formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
            <bc:column field="mgerOrg" title="机构名称" width="100" formatter="custom:sys/unit/selectUnit"/>
            <bc:column field="identifyType" title="证件类型" width="100"
                       formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
            <bc:column field="identifyNumber" title="证件号码" width="100"/>
            <bc:column field="regulatorOth1" title="口径" width="100"
                       formatter="custom:sys/dictItem/getDictItems/BUS-Caliber" />
            <bc:column field="isBlack" title="是否黑名单" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
        </bc:table>
    </div>
</div>
</bc:ibox>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    //数据导出
    function out() {
        out1();
        out2();

    }
    function out1() {
        var data = getIdSelections();
        var url='${base}/sys/xlsie/sinOrBatPsnExportImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    function out2() {
        var data = getIdSelections();
        var url='${base}/sys/xlsie/sinOrBatOrgExportImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    //打印按钮
    function print() {
        alert("未连接打印机");
        return;
    }

    //关联关系类别和关系对话框联动
    var relCategory = '${PsnAndOrgVo.relCategory}';
    $(document).ready(function () {
        //初始化内容脚本
        // if (relCategory == '') {
        //     relCategory = 'A';
        // }
        setRelCategorySub(relCategory);
    });

    function setRelCategorySub(value) {
        $.ajax({
            type: 'GET',
            url: '${base}/bus/relPsn/queryRelCategorySub?relCategory=' + value + '&type=p',
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
                $("#__LIKES_relCategorySub").empty();
                $("#__LIKES_relCategorySub").select2({data: arr, width: "100%"});
            }
        });
    }

    //查询按钮
    function query(){
        var pid = getIdSelections();
        var val = getIdentifyTypeSelections();
        // alert(val);
            if(pid == ''){
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            if(pid != ""){
            if( val !== 'UNIFIEDSOCIALCREDITCODE'){
                window.location='${base}/bus/relPsn/edit/' + pid + '?op=1';
            }else {
                window.location='${base}/bus/relOrg/edit/' + pid + '?op=1';
            }
        }else {
            $.messager.alert('提示', '请选择机构。', 'info');
            return "";
        }

    }

    <%--function editRelPsn() {--%>
    <%--    var pid = getIdSelections();--%>
    <%--    if (pid != "") {--%>
    <%--        window.location = '${base}/bus/relPsn/edit/' + pid + '?';--%>
    <%--    } else {--%>
    <%--        $.messager.alert('提示', '请选择自然人。', 'info');--%>
    <%--        return "";--%>
    <%--    }--%>
    <%--}--%>

    //自增主键
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

    <%--function removeRelPsn() {--%>
    <%--    var pid = getIdSelections();--%>
    <%--    $.ajax({--%>
    <%--        url: "${base}/bus/rel/queryRel/" + pid + "/PSN",--%>
    <%--        &lt;%&ndash;url:"${base}/bus/relPsn/remove/"+pid,&ndash;%&gt;--%>
    <%--        method: "GET",--%>
    <%--        data: {"": ""},--%>
    <%--        dataType: "JSON",--%>
    <%--        traditional: true,--%>
    <%--        success: function (data) {--%>
    <%--            if (data.success) {--%>
    <%--                alert("可以删除")--%>
    <%--                &lt;%&ndash;window.location = '${base}/bus/relPsn/list';&ndash;%&gt;--%>
    <%--            } else {--%>
    <%--                $.messager.alert('错误', data.msg, 'error');--%>
    <%--            }--%>
    <%--        }--%>
    <%--    })--%>
    <%--}--%>

    <%--function  exportHandler() {--%>
    <%--window.location = '${base}/bus/relPsn/exportHandler/'+ 1+'?';--%>
    <%--}--%>
    //模块下载
    <%--function exportHandler() {--%>
    <%--    var data = "00"--%>
    <%--    var url = '${base}/sys/xlsie/RelPsnExportCallbackImpl/' + data;--%>
    <%--    var elemIF = document.createElement("iframe");--%>
    <%--    elemIF.src = url;--%>
    <%--    elemIF.style.display = "none";--%>
    <%--    document.body.appendChild(elemIF);--%>
    <%--}--%>

    //导入
    <%--function importHandler() {--%>
    <%--    $.messager.modal({--%>
    <%--        winId: 'wImportFromExcel', //winid只能为 wImportFromExcel--%>
    <%--        width: 500,--%>
    <%--        height: 230,--%>
    <%--        args: {--%>
    <%--            callback: loadCallback--%>
    <%--        },--%>
    <%--        title: '自然人信息导入',--%>
    <%--        url: '${base}/sys/xlsie/import?bean=RelPsnImportCallbackImpl',--%>
    <%--        onClose: function () {--%>
    <%--            loadCallback();--%>
    <%--        }--%>
    <%--    });--%>
    <%--}--%>

    //刷新表格
    function loadCallback() {
        $('#naturalPersonTable').jTable('refresh');
    }
    function frmReset(){
        window.location = '${base}/bus/relPsn/relBatQuery';
    }
    <%--function addOrRemoveBlacklist() {--%>
    <%--    var pid = getIdSelections();--%>
    <%--    if (pid != "") {--%>
    <%--        window.location = '${base}/bus/relPsn/addOrRemoveBlack/' + pid + '?';--%>
    <%--    } else {--%>
    <%--        $.messager.alert('提示', '请选择自然人。', 'info');--%>
    <%--        return "";--%>
    <%--    }--%>
    <%--}--%>

    <%--function disconnection() {--%>
    <%--    var pid = getIdSelections();--%>
    <%--    if (pid != "") {--%>
    <%--        window.location = '${base}/bus/relPsn/disconnection/' + pid + '?';--%>
    <%--    } else {--%>
    <%--        $.messager.alert('提示', '请选择自然人。', 'info');--%>
    <%--        return "";--%>
    <%--    }--%>
    <%--}--%>
</script>
</body>
</html>