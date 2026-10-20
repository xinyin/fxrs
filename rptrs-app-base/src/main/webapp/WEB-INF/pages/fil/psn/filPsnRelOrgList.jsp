<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <br:css pack="list"/>
    <title>关联关系机构</title>
    <style>
        .returnList{
            text-align: center;
            margin-left: auto;
            margin-bottom: 5px;
            margin-top: 10px;
            margin-right: auto;
        }
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" col="12" collapsible="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="op" name="op" value="${op}"/>
            <bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
            <bf:formGroup>
                <div class="col-sm-6">
                    <bc:button type="light" text="基本信息">
                        <be:event>
                            basicInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="light" text="关系自然人信息">
                        <be:event>
                            affiliateInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="关系机构信息">
                        <be:event>
                            <%--rightsInformation();--%>
                        </be:event>
                    </bc:button>
                    <bc:button type="light" text="关系金融产品">
                        <be:event>
                            proRelInformation();
                        </be:event>
                    </bc:button>
            </bf:formGroup>
            <bc:ibox title="所属关联机构" subTitle="" col="12" collapsible="true">
                <bc:table id="psnRelOrgTable" idField="pid" url="${base}/fil/filRelPsn" queryUrl="psnRelOrg/${fromPid}" addUrl="addPsnRelOrg/${fromPid}">
                    <%--<bc:toolbar id="add" text="新增" type="button"/>
                    <bc:toolbar id="info" text="编辑" type="button" handler="viewAffiliate"/>--%>
                    <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                   <%-- <bc:toolbar id="info" text="详情" type="button" handler="viewAffiliate2"/>--%>
                    <bc:column field="pid" title="自增主键" width="100" visible="false"/>
                    <bc:column field="relId" title="relId" width="100" visible="false"/>
<%--                    <bc:column field="fromPid" title="源主体pid" width="100" visible="false"/>--%>
<%--                    <bc:column field="relCategory" title="关系类型" width="100" visible="false"/>--%>
<%--                    <bc:column field="relCategorySub" title="关系子类型" width="100" visible="false"/>--%>
<%--                    <bc:column field="fromType" title="源主体类型  PSN|个人 ORG|机构" width="100" visible="false"/>--%>
<%--                    <bc:column field="toType" title="关联关系主体类型   PSN|个人 ORG|机构" width="100" visible="false"/>--%>
<%--                    <bc:column field="fromMemo" title="源主体显示名称" width="100" visible="false"/>--%>
<%--                    <bc:column field="toMemo" title="关联关系主体显示名称" width="100" visible="false"/>--%>
<%--                    <bc:column field="relSort" title="关系类别" width="100" visible="false"/>--%>
<%--                    <bc:column field="position" title="职位" width="100" visible="false"/>--%>
<%--                    <bc:column field="relation" title="亲属关系" width="100" visible="false"/>--%>
<%--                    <bc:column field="shareholdingRatio" title="持股比例" width="100" visible="false"/>--%>
<%--                    <bc:column field="toPidShareholders" title="是否关联关系主体股东    0-否 1-是" width="100" visible="false"/>--%>
<%--                    <bc:column field="isShow" title="是否显示" width="100" visible="false"/>--%>
<%--                    <bc:column field="level" title="关系层级" width="100" visible="false"/>--%>
<%--                    <bc:column field="liftReason" title="解除原因" width="100" visible="false"/>--%>
                    <bc:column field="orgName" title="机构名称" width="100"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="uniscCode" title="证件号码" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="shareholdingRatio" title="持股比例或表决权(%)" width="100"/>
                    <bc:column field="toType" title="类型" width="100" visible="false"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:operator id="viewAffiliate1" icon="eye" title="查看" handler="viewAffiliate1"/>
                </bc:table>
            </bc:ibox>

            <bc:ibox title="下一级关联机构" subTitle="" col="12" collapsible="true">
                <bc:table id="psnRelOrgTable1" idField="pid" url="${base}/fil/filRelPsn" queryUrl="psnDownRelOrg/${fromPid}" addUrl="addPsnRelOrg/${fromPid}">
                    <%--<bc:toolbar id="add" text="新增" type="button"/>--%>
                    <%--<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                    <bc:toolbar id="info" text="详情" type="button" handler="viewAffiliate2"/>--%>
                    <bc:column field="pid" title="自增主键" width="100" visible="false"/>
                    <bc:column field="relId" title="relId" width="100" visible="false"/>
<%--                    <bc:column field="fromPid" title="源主体pid" width="100" visible="false"/>--%>
<%--                    <bc:column field="relCategory" title="关系类型" width="100" visible="false" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>--%>
<%--                    <bc:column field="relCategorySub" title="关系子类型" width="100" visible="false" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>--%>
<%--                    <bc:column field="fromType" title="源主体类型  PSN|个人 ORG|机构" width="100" visible="false"/>--%>
<%--                    <bc:column field="toType" title="关联关系主体类型   PSN|个人 ORG|机构" width="100" visible="false"/>--%>
<%--                    <bc:column field="fromMemo" title="源主体显示名称" width="100" visible="false"/>--%>
<%--                    <bc:column field="toMemo" title="关联关系主体显示名称" width="100" visible="false"/>--%>
<%--                    <bc:column field="relSort" title="关系类别" width="100" visible="false"/>--%>
<%--                    <bc:column field="position" title="职位" width="100" visible="false"/>--%>
<%--                    <bc:column field="relation" title="亲属关系" width="100" visible="false"/>--%>
<%--                    <bc:column field="shareholdingRatio" title="持股比例" width="100" visible="false"/>--%>
<%--                    <bc:column field="toPidShareholders" title="是否关联关系主体股东    0-否 1-是" width="100" visible="false"/>--%>
<%--                    <bc:column field="isShow" title="是否显示" width="100" visible="false"/>--%>
<%--                    <bc:column field="level" title="关系层级" width="100" visible="false"/>--%>
<%--                    <bc:column field="liftReason" title="解除原因" width="100" visible="false"/>--%>
                    <bc:column field="orgName" title="机构名称" width="100"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="uniscCode" title="证件号码" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory" width="100"/>
                    <bc:column field="relCategorySub" title="关系" formatter="custom:sys/dictItem/getDictItems/BUS-Rel" width="100"/>
                    <bc:column field="shareholdingRatio" title="持股比例或表决权(%)" width="100"/>
                    <bc:column field="toType" title="类型" width="100" visible="false"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:operator id="viewAffiliate2" icon="eye" title="查看" handler="viewAffiliate2"/>
                </bc:table>
                <bf:formGroup>
                    <div class="returnList">
                        <bc:button id="returnButton" type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                returnList();
                            </be:event>
                        </bc:button>
                    </div>
                </bf:formGroup>
            </bc:ibox>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script type="text/javascript">

    function returnList(){
        var value='${wt}';
        if(value == '1'){
            window.location = '${base}/fil/filRelPsn/list';
        }else if(value == '2'){
            window.location = '${base}/workflow/approve/doneList'
        }else{
            window.location = '${base}/workflow/process/completeList'
        }
    }

    window.onload=x;
    function x() {
        // alert("进入");
        var value = '${op}';
        if (value != "0"){
            // alert("进入其他");
            $('#psnRelOrgTable_tb_add').css('display', 'none');
            $('#psnRelOrgTable_tb_remove').css('display', 'none');
        }
    }

    function viewAffiliate() {
        var relId = getPidSelections();
        var fromPid = $('#fromPid').val();
        var type="UP";
        if (relId=='' || relId==null){
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location="${base}/fil/filRelPsn/viewPsnRelOrg/"+ relId+"/"+fromPid+"/"+type+"?op=0";
    }

    function viewAffiliate1() {
        var relId = getPidSelections();
        var fromPid = $('#fromPid').val();
        var type="UP";
        if (relId=='' || relId==null){
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location="${base}/fil/filRelPsn/viewPsnRelOrg/"+ relId+"/"+fromPid+"/"+type+"?op=1";
    }
    function viewAffiliate2() {
        var relId = getPidSelections1();
        var fromPid = $('#fromPid').val();
        var type="DOWN";
        if (relId=='' || relId==null){
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location="${base}/fil/filRelPsn/viewPsnRelOrg/"+ relId+"/"+fromPid+"/"+type+"?op=1";
    }


    function basicInformation() {
        var value = '${wt}';
        var pid = $("#fromPid").val();

        window.location = '${base}/fil/filRelPsn/compared/' + pid + '?op=1&wt=' + value;

    }

    function affiliateInformation() {
        var value = '${wt}';
        var pid = $("#pid").val();
        window.location = '${base}/fil/filRelPsn/affiliateList/' + pid + '?op=1&wt='+value;
    }

    function rightsInformation() {
        var value = '${wt}';
        var pid = $("#fromPid").val();
        window.location = '${base}/fil/filRelPsn/psnRelOrgList/' + pid + '?op=1&wt=' + value;

    }

    function proRelInformation() {
        var value = '${wt}';
        var pid = $("#fromPid").val();
        window.location = '${base}/fil/filRelPsn/proOfPsnList/' + pid + '?op=1&wt=' + value;
    }


    function  editHandler() {
        var pid = getIdSelections();
        var relId = getPidSelections();
        var fromPid = $("#fromPid").val();
        if(pid != ""){
            window.location = '${base}/fil/filRelPsn/editPsnRelOrg/' + pid+"/"+ fromPid +"/"+ relId;
        }else {
            $.messager.alert('提示', '请选择机构。', 'info');
            return "";
        }
    }
    function removeHandler() {
        var relId = getPidSelections();
        if (relId=='' || relId==null){
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        $.messager.confirm('提示', '确定要删除吗？', function(r){
            if(r){
                $.ajax({
                    url:"${base}/fil/rel/removePsnRel/"+relId,
                    method:"POST",
                    data:{"":""},
                    dataType:"JSON",
                    traditional:true,
                    success:function(data){
                        if(data.success){
                            var fromPid = $('#fromPid').val();
                            window.location = '${base}/fil/filRelPsn/psnRelOrgList/'+fromPid+'?op=0';
                        }else
                        {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    }
                })
            }
        })
    }
    function getIdSelections() {
        return $.map($('#psnRelOrgTable').bootstrapTable('getSelections'), function (row) {
            return row.pid ;
        });
    }
    function getPidSelections() {
        return $.map($('#psnRelOrgTable').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }
    function getPidSelections1() {
        return $.map($('#psnRelOrgTable1').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }
</script>
</body>
</html>