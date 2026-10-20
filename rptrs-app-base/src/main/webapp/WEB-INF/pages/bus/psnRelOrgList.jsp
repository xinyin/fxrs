<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>关联关系机构</title>
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
            <bc:button type="primary" text="关联法人或其他组织">
                <be:event>
                    <%--rightsInformation();--%>
                </be:event>
            </bc:button>
           <%-- <bc:button type="light" text="关系金融产品">
                <be:event>
                    proRelInformation();
                </be:event>
            </bc:button>--%>
            </bf:formGroup>
            <bc:ibox title="所属关联法人或其他组织" subTitle="" col="12" collapsible="true">
                <bc:table id="psnRelOrgTable" idField="pid" url="${base}/bus/relPsn" queryUrl="psnRelOrg/${fromPid}"
                          addUrl="addPsnRelOrg/${fromPid}">
                    <bc:toolbar id="add" text="新增" type="button"/>
                    <bc:toolbar id="info" text="编辑" type="button" handler="editHanlder"/>
                    <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                    <bc:column field="pid" title="自增主键" width="100" visible="false"/>
                    <bc:column field="relId" title="relId" width="100" visible="false"/>
                    <bc:column field="orgName" title="机构名称" width="100"/>
                   <%-- <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>--%>
                    <%--                    <bc:column field="uniscCode" title="证件号码" width="100"/>--%>
                    <bc:column field="relCategory" title="关联关系类别" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="toType" title="类型" width="100" visible="false"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:column field="shareholdingRatio" title="持有占比(%)" width="100"/>
                    <bc:column field="relStatus" title="关联审批状态" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelStatus" visible="false"/>
                    <bc:operator id="viewAffiliate1" icon="eye" title="查看" handler="viewAffiliate1"/>
                </bc:table>
            </bc:ibox>

            <bc:ibox title="下一级关联法人或其他组织" subTitle="" col="12" collapsible="true">
                <bc:table id="psnRelOrgTable1" idField="pid" url="${base}/bus/relPsn"
                          queryUrl="psnDownRelOrg/${fromPid}" addUrl="addPsnRelOrg/${fromPid}">
                    <bc:column field="pid" title="自增主键" width="100" visible="false"/>
                    <bc:column field="relId" title="relId" width="100" visible="false"/>
                    <bc:column field="orgName" title="机构名称" width="100"/>
                    <%-- <bc:column field="identifyType" title="证件类型" width="100"
                                formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>--%>
                    <%--                    <bc:column field="uniscCode" title="证件号码" width="100"/>--%>
                    <bc:column field="relCategory" title="关联关系类别" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="toType" title="类型" width="100" visible="false"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:column field="shareholdingRatio" title="持有占比(%)" width="100"/>
                    <bc:column field="relStatus" title="关联审批状态" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelStatus" visible="false"/>
                    <bc:operator id="viewAffiliate2" icon="eye" title="查看" handler="viewAffiliate2"/>
                </bc:table>
                <bf:formGroup>
                    <div id="returnPsnList" class="returnList">
                        <bc:button id="returnButton" type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                <%--                                returnList()--%>
                                window.location = '${base}/bus/relPsn/list';
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

        function returnList() {
            window.location = '${base}/bus/relPsn/list';
        }

        window.onload = x;

        function x() {
            // alert("进入");
            var value = '${op}';
            if (value != "0") {
                // alert("进入其他");
                $('#psnRelOrgTable_tb_add').css('display', 'none');
                $('#psnRelOrgTable_tb_remove').css('display', 'none');
                $('#psnRelOrgTable_tb_info').css('display', 'none');

            }
            if (value == '2') {
                $('#returnPsnList').hide();
            }
        }

        function editHanlder() {
            debugger
            var pid = getIdSelections();
            var relId = getPidSelections();
            var fromPid = $("#fromPid").val();
            if (pid != "") {
                window.location = '${base}/bus/relPsn/editPsnToOrg/' + pid + "/" + relId + "/" + fromPid;
            } else {
                $.messager.alert('提示', '请选择一条数据。', 'info');
                return "";
            }
        }

        function viewAffiliate1() {
            var pid = getIdSelections();
            var relId = getPidSelections();
            var fromPid = $("#fromPid").val();
            if (pid == "") {
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            window.location = '${base}/bus/relPsn/viewPsnRelOrg/' + pid + "/" + relId + "/" + fromPid;
        }

        function viewAffiliate2() {
            var pid = getIdSelections1();
            var relId = getPidSelections1();
            var fromPid = $("#fromPid").val();
            if (pid == "") {
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            window.location = '${base}/bus/relPsn/viewPsnRelOrg/' + pid + "/" + relId + "/" + fromPid;
        }


        function basicInformation() {
            var value = '${op}';
            var pid = $("#fromPid").val();
            if (value == "0") {
                // alert(value);
                window.location = '${base}/bus/relPsn/edit/' + pid + '?op=0';
            } else if (value == '2') {
                window.location = '${base}/bus/relPsn/viewForm/' + pid;
            } else {
                // alert(value);
                window.location = '${base}/bus/relPsn/edit/' + pid + '?op=1';
            }
        }

        function affiliateInformation() {
            var value = '${op}';
            var pid = $("#fromPid").val();
            if (value == "0") {
                // alert(value);
                window.location = '${base}/bus/relPsn/affiliateList/' + pid + '?op=0';
            } else {
                // alert(value);
                window.location = '${base}/bus/relPsn/affiliateList/' + pid + '?op=' + value;
            }
        }

        function proRelInformation() {
            var value = '${op}';
            var pid = $("#fromPid").val();
            if (pid == '') {
                alert("请保存基本信息")
                return;
            }
            if (value == "0") {
                window.location = '${base}/bus/relPsn/proOfPsnList/' + pid + '?op=0';
            } else {
                window.location = '${base}/bus/relPsn/proOfPsnList/' + pid + '?op=' + value;
            }
        }

        function editHandler() {
            var pid = getIdSelections();
            var relId = getPidSelections();
            var fromPid = $("#fromPid").val();
            if (pid != "") {
                window.location = '${base}/bus/relPsn/editPsnRelOrg/' + pid + "/" + fromPid + "/" + relId;
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


        function removeHandler() {
            var relId = getPidSelections();
            if (relId == '' || relId == null) {
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            var relStatus = getRelStatus()
            if (relStatus == "00") {
                $.messager.alert('错误', "此条数据已归档，不可删除", 'error');
                return;
            }
            $.messager.confirm('提示', '确定要删除吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "${base}/bus/rel/removePsnRel/" + relId,
                        method: "POST",
                        data: {"": ""},
                        dataType: "JSON",
                        traditional: true,
                        success: function (data) {
                            if (data.success) {
                                var fromPid = $('#fromPid').val();
                                window.location = '${base}/bus/relPsn/psnRelOrgList/' + fromPid + '?op=0';
                            } else {
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        }
                    })
                }
            })
        }

        function getIdSelections() {
            return $.map($('#psnRelOrgTable').bootstrapTable('getSelections'), function (row) {
                return row.pid;
            });
        }
        function getIdSelections1() {
            return $.map($('#psnRelOrgTable1').bootstrapTable('getSelections'), function (row) {
                return row.pid;
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

        function getStatusSelections() {
            return $.map($('#psnRelOrgTable').bootstrapTable('getSelections'), function (row) {
                return row.relStatus;
            });
        }
    </script>
</body>
</html>