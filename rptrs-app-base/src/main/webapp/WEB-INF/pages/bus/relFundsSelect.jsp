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

    <title>关联关系自然人</title>
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
                        <bf:formGroup col="8">
                            <bc:label text="关联方名称" col="3" formControl="true"/>
                            <bf:text name="__LIKES_name" col="7" placeholder="姓名"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        </bf:formGroup>
                    </bc:row>
                </div>
            </bf:form>
            <div class="col-sm-4 col-sm-offset-5">
                <bc:button type="danger" text="选择"  click="submit1()">
                </bc:button>
            </div>
            <bc:table id="relPsnAndOrg" idField="pid" url="${base}/bus/relFunds" queryUrl="/relSelectByName">
                <bc:column field="pid" title="ID" width="100" visible="false"/>
                <bc:column field="name" title="关联方名称" width="100"/>
                <bc:column field="type" title="关联方类别" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="identifyNumber" title="关联方证件号码" width="100"/>
            </bc:table>

        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    /*获取姓名*/
    function getName() {
        return $.map($('#relPsnAndOrg').bootstrapTable('getSelections'), function (row) {
            return row.name;
        });
    }
    /*获取pid*/
    function getIdSelections() {
        return $.map($('#relPsnAndOrg').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    /*获取类型*/
    function getType() {
        return $.map($('#relPsnAndOrg').bootstrapTable('getSelections'), function (row) {
            return row.type;
        });
    }
    /*获取证件号码*/
    function getIdentifyNumber() {
        return $.map($('#relPsnAndOrg').bootstrapTable('getSelections'), function (row) {
            return row.identifyNumber;
        });
    }



    //关闭弹窗返回选择对象
    function  submit1() {
        var pid = getIdSelections();
        var relName = getName();
        var relType = getType();
        var identifyNumber = getIdentifyNumber();
        if (pid != null && pid != " " || pid != undefined) {
            $.messager.args('relNameSelect').result({
                'relId': pid,
                'relName': relName,
                'relType': relType,
                'relNumber': identifyNumber,
            });
            $.messager.hide('relNameSelect');
            //关闭窗口
        }else {
            $.messager.alert('提示', '请选择一条数据', 'warning');
            return false;
        }
    }

</script>
</body>
</html>