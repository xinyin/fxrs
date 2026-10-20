<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>金融产品关联关系机构</title>
    <style>
        .returnList {
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
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
            <bf:hidden id="op" name="op" value="${op}"/>
            <%--<bf:hidden id="selectPsnId" name="selectPsnId" value="${selectPsnId}"/>--%>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:button type="light" text="基本信息">
                        <be:event>
                            basicInformation();
                        </be:event>
                    </bc:button>
                    <%-- <bc:button type="light" text="关系自然人信息">
                         <be:event>
                             affiliateInformation();
                         </be:event>
                     </bc:button>--%>
                    <bc:button type="primary" text="关联法人或其他组织">
                        <be:event>
                            <%--     rightsInformation();--%>
                        </be:event>
                    </bc:button>
                </bc:col>
            </bf:formGroup>
            <bc:ibox title="所属关联机构" subTitle="" col="12" collapsible="true">
                <bc:table id="psnRelOrgTable" idField="pid" url="${base}/bus"
                          queryUrl="relOrgOfPro/orgUpOfProList/${fromPid}?op=0" addUrl="relPro/addOrgOfPro/${fromPid}">
                    <bc:toolbar id="add" text="新增" type="button"/>
                    <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                    <bc:toolbar id="remove" text="删除" type="button" handler="removeHanlder"/>
                    <bc:column field="pid" title="关联关系表自增长主键" width="100" visible="false"/>
                    <bc:column field="relId" title="关联关系relId" width="100" visible="false"/>
                    <bc:column field="fromPid" title="源主体pid" width="100" visible="false"/>
                    <bc:column field="orgName" title="机构名称" width="100"/>
                    <%-- <bc:column field="identifyType" title="证件类型" width="100"
                                formatter="custom:sys/dictItem/getDictItems/BUS-ORGIdentifyType"/>
                     <bc:column field="uniscCode" title="证件号码" width="100"/>--%>
                    <bc:column field="relCategory" title="关联关系类别" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:column field="shareholdingRatio" title="持有比例（%）" width="100"/>
                    <bc:column field="relStatus" title="关联操作状态" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelStatus" visible="false"/>
                    <bc:operator id="viewAffiliate1" icon="eye" title="查看" handler="viewAffiliate1"/>
                </bc:table>
            </bc:ibox>

            <bc:ibox title="下一级关联机构" subTitle="" col="12" collapsible="true">
                <bc:table id="psnRelOrgTable1" idField="pid" url="${base}/bus/relOrgOfPro"
                          queryUrl="orgDownOfProList/${fromPid}?op=0">
                    <bc:column field="pid" title="关联关系表自增长主键" width="100" visible="false"/>
                    <bc:column field="relId" title="关联关系relId" width="100" visible="false"/>
                    <bc:column field="fromPid" title="源主体pid" width="100" visible="false"/>
                    <bc:column field="orgName" title="机构名称" width="100"/>
                    <%-- <bc:column field="identifyType" title="证件类型" width="100"
                                formatter="custom:sys/dictItem/getDictItems/BUS-ORGIdentifyType"/>
                     <bc:column field="uniscCode" title="证件号码" width="100"/>--%>
                    <bc:column field="relCategory" title="关联关系类别" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:column field="shareholdingRatio" title="持有比例（%）" width="100"/>
                    <bc:column field="relStatus" title="关联操作状态" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelStatus" visible="false"/>
                    <bc:operator id="viewAffiliate2" icon="eye" title="查看" handler="viewAffiliate2"/>
                </bc:table>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-5" style="padding-top:3%">
                        <bc:button id="y" type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                <%--var uniscCode = $('#uniscCode').value();--%>
                                window.location = '${base}/bus/relPro/list';
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

    window.onload = showAndnone();

    function showAndnone() {
        var value = '${op}';
        if (value == '2') {
            $('#y').hide()
        }
        if (value == 1) {
            // $('#psnTable_tb_add').hide(); //隐藏
            $('#psnRelOrgTable_tb_add').css('display', 'none');
            $('#psnRelOrgTable_tb_edit').css('display', 'none');
            $('#psnRelOrgTable_tb_remove').css('display', 'none');
        }
    }

    function basicInformation() {
        var value = '${op}';
        var pid = $("#fromPid").val();
        if (value == '0') {
            // alert(value);
            window.location = '${base}/bus/relPro/edit/' + pid + '?op=0';
        }
        if (value == '1') {
            // alert(value);
            window.location = '${base}/bus/relPro/edit/' + pid + '?op=1';
        }
        if (value == '2') {
            // alert(value);
            window.location = '${base}/bus/relPro/edit/' + pid + '?op=2';
        }
    }

    function affiliateInformation() {
        var value = '${op}';
        var pid = $("#fromPid").val();
        if (value == "0") {
            // alert(value);
            window.location = '${base}/bus/relPro/psnOfProList/' + pid + '?op=0';
        } else {
            // alert(value);
            window.location = '${base}/bus/relPro/psnOfProList/' + pid + '?op=1';
        }
    }

    //编辑
    function editHandler() {
        var pid = getIdSelections();
        var relId = getrelId();
        var fromPid = $("#fromPid").val();
        if (pid != "") {
            window.location = '${base}/bus/relOrg/editProRelOrg/' + pid + '/' + fromPid + '/' + relId;
        } else {
            $.messager.alert('提示', '请选择机构。', 'info');
            return "";
        }
    }

    function viewAffiliate1() {
        var pid = getIdSelections();
        var relId = getrelId();
        var fromPid = $("#fromPid").val();
        if (pid != "") {
            <%--window.location = '${base}/bus/relOrg/editProRelOrg/' + pid + '/' + fromPid + '/' + relId;--%>
            window.location = "${base}/bus/relOrg/viewPsnRelOrgs/" + pid + '/' + fromPid + '/' + relId;
        } else {
            $.messager.alert('提示', '请选择机构。', 'info');
            return "";
        }
    }


    function viewAffiliate2() {
        var pid = getIdSelections1();
        var relId = getrelId1();
        var fromPid = $("#fromPid").val();
        if (pid != "") {
            <%--window.location = '${base}/bus/relOrg/editProRelOrg/' + pid + '/' + fromPid + '/' + relId;--%>
            window.location = "${base}/bus/relOrg/viewPsnRelOrgs/" + pid + '/' + fromPid + '/' + relId;
        } else {
            $.messager.alert('提示', '请选择机构。', 'info');
            return "";
        }
    }

    function getRelStatus() {
        return $.map($('#psnRelOrgTable').bootstrapTable('getSelections'), function (row) {
            return row.relStatus;
        });
    }


    function removeHanlder() {
        var relId = getrelId();
        if (relId == '' || relId == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        var relStatus = getRelStatus();
        if (relStatus == "00"){
            $.messager.alert('错误', "此条数据已归档，不可删除", 'error');
            return;
        }
        $.messager.confirm('提示', '确定要删除吗？', function (r) {

            $.ajax({
                url: "${base}/bus/relPsnOfPro/removeRelOrgInformation/" + relId,
                method: "POST",
                data: {"": ""},
                dataType: "JSON",
                traditional: true,
                success: function (data) {
                    if (data.success) {
                        var fromPid = $('#fromPid').val();
                        window.location = '${base}/bus/relPro/orgOfProList/' + fromPid + '?op=0';
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })
        })
    }


    function getrelId() {
        return $.map($('#psnRelOrgTable').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }

    function getIdSelections() {
        return $.map($('#psnRelOrgTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    function getrelId1() {
        return $.map($('#psnRelOrgTable1').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }

    function getIdSelections1() {
        return $.map($('#psnRelOrgTable1').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }


</script>
</body>
</html>