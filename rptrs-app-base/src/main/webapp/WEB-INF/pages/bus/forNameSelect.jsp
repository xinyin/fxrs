<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">
        #relPsnTable_tb_add, #relPsnTable_tb_edit,
        #relPsnTable_tb_remove, #relPsnTable_tb_importSimple,
        #relPsnTable_tb_sendCommand, #relPsnTable_tb_exportHandler,
        #relPsnTable_tb_send, #relPsnTable_tb_out, #relPsnTable_tb_addOrRemoveBlacklist {
            display: none;

        }
    </style>

    <title>关联关系维护</title>
    <style>
        .form-group select {
            width: 100% !important;
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
                <div class="col-sm-12 col-sm-offset-5">
                    <bc:row cls="m-b">
                        <div id="person_">
                            <bf:formGroup col="8">
                                <bc:label text="关联方名称" col="4" formControl="true"/>
                                <bf:text name="__LIKES_name" id="__LIKES_name" value="" placeholder="请输入名称"
                                         col="7"/>
                            </bf:formGroup>
                        </div>
                        <div id="org_">
                            <bf:formGroup col="8">
                                <bc:label text="机构名称" col="4" formControl="true"/>
                                <bf:text name="__LIKES_orgName" id="__LIKES_name" value="" placeholder="请输入名称"
                                         col="7"/>
                            </bf:formGroup>
                        </div>
                        <div id="product_">
                            <bf:formGroup col="8">
                                <bc:label text="产品名称" col="4" formControl="true"/>
                                <bf:text name="__LIKES_proName" id="__LIKES_name" value="" placeholder="请输入名称"
                                         col="7"/>
                            </bf:formGroup>
                        </div>

                        <bf:formGroup col="4">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        </bf:formGroup>
                    </bc:row>
                </div>
            </bf:form>
            <div class="col-sm-4 col-sm-offset-5">

            </div>
            <bc:button type="danger" text="选择" click="submit1()">
            </bc:button>
            <bc:table id="relPsnOrOrgOrProTable" idField="pid" url="${base}/bus/rel"
                      queryUrl="/selectName?type=${type}">
                <bc:column field="pid" title="ID" width="100"/>
                <bc:column field="name" title="名称" width="100"/>
                <bc:column field="numPSNType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="numORGType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-ORGIdentifyType"/>
                <bc:column field="proType" title="产品类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-pro"/>
                <bc:column field="number" title="证件号码" width="100"/>
                <bc:column field="domesticOrForeign" title="境内外" width="100"/>
                <bc:column field="legalRepresentative" title="法定代表人" width="100"/>
                <bc:column field="orgStatus" title="机构状态" width="100"/>
            </bc:table>

        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">


    $(document).ready(function () {
        var type = "${type}";
        selectIdentifyType(type);
    });

    //页面默认关联关系是自然人
    function selectIdentifyType(val) {
        //numPSNType   numORGType     proType
        // name   numType   number  domesticOrForeign  LegalRepresentative  orgStatus  proType
        // person_   org_     product_
        $('#relPsnOrOrgOrProTable').bootstrapTable('hideColumn', 'pid');
        $('#relPsnOrOrgOrProTable').bootstrapTable('hideColumn', 'domesticOrForeign');
        $('#relPsnOrOrgOrProTable').bootstrapTable('hideColumn', 'legalRepresentative');
        $('#relPsnOrOrgOrProTable').bootstrapTable('hideColumn', 'orgStatus');
        $('#relPsnOrOrgOrProTable').bootstrapTable('hideColumn', 'proType');

        if (val == 'psn') {
            $('#relPsnOrOrgOrProTable').bootstrapTable('hideColumn', 'numORGType');
            $('#relPsnOrOrgOrProTable').bootstrapTable('hideColumn', 'proType');
            $('#relPsnOrOrgOrProTable').bootstrapTable('showColumn', 'numPSNType');
            $('#org_').remove();
            $('#product_').remove();

        }
        if (val == 'org') {
            $('#relPsnOrOrgOrProTable').bootstrapTable('showColumn', 'numORGType');
            $('#relPsnOrOrgOrProTable').bootstrapTable('hideColumn', 'proType');
            $('#relPsnOrOrgOrProTable').bootstrapTable('hideColumn', 'numPSNType');
            $('#person_').remove();
            $('#product_').remove();
        }
        if (val == 'pro') {
            $('#relPsnOrOrgOrProTable').bootstrapTable('showColumn', 'proType');
            $('#relPsnOrOrgOrProTable').bootstrapTable('hideColumn', 'numORGType');
            $('#relPsnOrOrgOrProTable').bootstrapTable('hideColumn', 'numPSNType');
            $('#person_').remove();
            $('#org_').remove();
        }
    }

    $("#__LIKES_name").blur(function () {
        var val = $("#__LIKES_name").val();
        if (val == '' || val == undefined) {
            $("#__LIKES_name").val('')
        }
    })


    /*获取pid*/
    function getIdSelections() {
        return $.map($('#relPsnOrOrgOrProTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    /*获取姓名*/
    function getName() {
        return $.map($('#relPsnOrOrgOrProTable').bootstrapTable('getSelections'), function (row) {
            return row.name;
        });
    }

    /*获取自然人类型*/
    function getPSNType() {
        return $.map($('#relPsnOrOrgOrProTable').bootstrapTable('getSelections'), function (row) {
            return row.numPSNType;
        });
    }

    /*获取机构类型*/
    function getORGType() {
        return $.map($('#relPsnOrOrgOrProTable').bootstrapTable('getSelections'), function (row) {
            return row.numORGType;
        });
    }

    /*获取证件号码*/
    function getIdentifyNumber() {
        return $.map($('#relPsnOrOrgOrProTable').bootstrapTable('getSelections'), function (row) {
            return row.number;
        });
    }

    /*获取境内外*/
    function getDomesticOrForeign() {
        return $.map($('#relPsnOrOrgOrProTable').bootstrapTable('getSelections'), function (row) {
            return row.domesticOrForeign;
        });
    }

    /*获取法定代表人*/
    function getLegalRepresentative() {
        return $.map($('#relPsnOrOrgOrProTable').bootstrapTable('getSelections'), function (row) {
            return row.legalRepresentative;
        });
    }

    /*获取机构状态*/
    function getOrgStatus() {
        return $.map($('#relPsnOrOrgOrProTable').bootstrapTable('getSelections'), function (row) {
            return row.orgStatus;
        });
    }

    /*获取产品类型*/
    function getProType() {
        return $.map($('#relPsnOrOrgOrProTable').bootstrapTable('getSelections'), function (row) {
            return row.proType;
        });
    }

    // name   numType   number  domesticOrForeign  LegalRepresentative  orgStatus  proType
    //关闭弹窗返回选择对象
    function submit1() {
        var pid = getIdSelections();
        var name = getName();
        var psnType = getPSNType();
        var orgType = getORGType();
        var number = getIdentifyNumber();
        var domesticOrForeign = getDomesticOrForeign();
        var legalRepresentative = getLegalRepresentative();
        var orgStatus = getOrgStatus();
        var proType = getProType();
        // alert(pid + '->' + name + '->' + psnType + '->' + number + '->' + domesticOrForeign)
        if (pid != null && pid != " " || pid != undefined) {
            $.messager.args('forNameSelect').result({
                'pid': pid,
                'name': name,
                'psnType': psnType,
                'orgType': orgType,
                'proType': proType,
                'number': number,
                'domesticOrForeign': domesticOrForeign,
                'legalRepresentative': legalRepresentative,
                'orgStatus': orgStatus
            });
            $.messager.hide('forNameSelect');
            //关闭窗口
        } else {
            $.messager.alert('提示', '请选择一条数据', 'warning');
            return false;
        }
    }

</script>
</body>
</html>