<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>自然人关联信息</title>
    <script>
        window.onload = x;

        function x() {
            // alert("进入");
            var value = '${op}';
            if (value != "0") {
                // alert("进入其他");
                $('#relPsnTable_tb_add').css('display', 'none');
                $('#relPsnTable_tb_remove').css('display', 'none');
                $('#relPsnTable_tb_edit').css('display', 'none');
            }
            if (value == '2') {
                $('#returnOrgList').hide();
            }

        }


        function getIdSelections() {
            return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
                return row.pid;
            });
        }

        function getIdSelections1() {
            return $.map($('#relPsnTable1').bootstrapTable('getSelections'), function (row) {
                return row.pid;
            });
        }

        function getrelId() {
            return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
                return row.relId;
            });
        }

        function getrelId1() {
            return $.map($('#relPsnTable1').bootstrapTable('getSelections'), function (row) {
                return row.relId;
            });
        }
        function getRelStatus() {
            return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
                return row.relStatus;
            });
        }

        function addHandler() {
            var orgid = $('#orgid').val();
            // alert(orgid);
            window.location = "${base}/bus/relOrg/addAffiliate/" + orgid;
        }

        function editHandler() {
            var pid = getIdSelections();
            var relId = getrelId();
            var orgid = $('#orgid').val();
            if (relId !== null && relId != "") {
                window.location = "${base}/bus/relOrg/editAffiliate/" + pid + "/" + relId + "/" + orgid;
            } else {
                $.messager.alert('提示', '请选择自然人。', 'info');
                return "";
            }

        }


        function viewAffiliate1() {
            var pid = getIdSelections();
            var orgid = $('#orgid').val();
            var relId = getrelId();
            var fromPid = $('#orgid').val();
            if (relId == '' || relId == null) {
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            window.location = "${base}/bus/relOrg/viewAffiliate/" + pid + "/" + relId + "/" + orgid;
        }

        function viewAffiliate2() {
            var pid = getIdSelections1();
            var orgid = $('#orgid').val();
            var relId = getrelId1();
            var fromPid = $('#orgid').val();
            if (relId == '' || relId == null) {
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            window.location = "${base}/bus/relOrg/viewAffiliate/" + pid + "/" + relId + "/" + orgid;
        }




        function removeHandler() {
            var relId = getrelId();
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
                    <%--window.location = "${base}/bus/rel/removeOrg/" + relId;--%>
                    $.ajax({
                        url: "${base}/bus/rel/removeRel/" + relId,
                        method: "POST",
                        data: {"": ""},
                        dataType: "JSON",
                        traditional: true,
                        success: function (data) {
                            if (data.success) {
                                var orgid = $('#orgid').val();
                                window.location = '${base}/bus/relOrg/listAffi/' + orgid + '?op=0';
                            } else {
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        }
                    })
                }
            })
        }

        function affiliateInformation() {
            var value = '${op}';
            var orgid = $('#orgid').val();
            if (value == "0") {
                // alert(value);
                window.location = '${base}/bus/relOrg/listAffi/' + orgid + '?op=0';
            } else {
                // alert(value);
                window.location = '${base}/bus/relOrg/listAffi/' + orgid + '?op=' + value;
            }
        }

        function orgRelInformation() {
            var value = '${op}';
            var orgid = $('#orgid').val();
            if (value == "0") {
                // alert(value);
                window.location = '${base}/bus/rel/list/' + orgid + '?op=0';
            } else {
                // alert(value);
                window.location = '${base}/bus/rel/list/' + orgid + '?op=' + value;
            }
        }

        function basicInformation() {
            var value = '${op}';
            var orgid = $('#orgid').val();
            if (value == "0") {
                // alert(value);
                window.location = "${base}/bus/relOrg/edit/" + orgid + '?op=0';
            } else if (value == '2') {
                window.location = "${base}/bus/relOrg/orgForm/" + orgid;
            } else {
                // alert(value);
                window.location = "${base}/bus/relOrg/edit/" + orgid + '?op=' + value;
            }
        }

        function proRelInformation() {
            var value = '${op}';
            var pid = $('#orgid').val();
            if (pid == null || pid == "") {
                alert("请先保存基本信息！")
            }
            if (value == "0") {
                // alert(value);bus/relPsn/proOfPsnList
                window.location = '${base}/bus/relOrg/proOfOrgList/' + pid + '?op=0';
            } else {
                // alert(value);
                window.location = '${base}/bus/relOrg/proOfOrgList/' + pid + '?op=' + value;
            }
        }
    </script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bf:hidden id="orgid" name="orgid" value="${orgid}"/>
            </bf:form>
            <bf:formGroup>
                <bc:col col="12">
                    <bc:button type="light" text="基本信息">
                        <be:event>
                            basicInformation();
                        </be:event>
                    </bc:button>
                    <bc:button id="psnRelInformation" type="primary" text="关系自然人信息">
                        <be:event>
                            <%--affiliateInformation();--%>
                        </be:event>
                    </bc:button>
                    <bc:button id="orgRelInformation" type="light" text="关联法人或其他组织">
                        <be:event>
                            orgRelInformation();
                        </be:event>
                    </bc:button>
                    <bc:button id="proRelInformation" type="light" text="关系金融产品">
                        <be:event>
                            proRelInformation();
                        </be:event>
                    </bc:button>
                </bc:col>
            </bf:formGroup>
            <bc:ibox title="所属关联自然人" subTitle="" col="12" collapsible="true">
                <bc:table id="relPsnTable" idField="pid" url="${base}/bus/relOrg" queryUrl="queryPsnList?orgid=${orgid}"
                          addUrl="addAffiliate/${orgid}">
                    <bc:toolbar id="add" text="新增" type="button" handler="addHandler"/>
                    <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                    <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                    <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                    <bc:column field="relId" title="关联关系主键" width="100" visible="false"/>
                    <bc:column field="name" title="姓名" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:column field="shareholdingRatio" title="持有占比(%)" width="100"/>
                    <bc:column field="relStatus" title="审批状态" width="100" visible="false"/>
                    <bc:operator id="viewAffiliate1" icon="eye" title="查看" handler="viewAffiliate1"/>
                </bc:table>
            </bc:ibox>
            <bc:ibox title="下级关联自然人" subTitle="" col="12" collapsible="true">
                <bc:table id="relPsnTable1" idField="pid" url="${base}/bus/relOrg"
                          queryUrl="queryDownPsnList?orgid=${orgid}" addUrl="addAffiliate/${orgid}">
                    <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                    <bc:column field="relId" title="关联关系主键" width="100" visible="false"/>
                    <bc:column field="name" title="姓名" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:column field="shareholdingRatio" title="持有占比(%)" width="100"/>
                    <bc:column field="relStatus" title="审批状态" width="100" visible="false"/>
                    <bc:operator id="viewAffiliate2" icon="eye" title="查看" handler="viewAffiliate2"/>
                </bc:table>
                <bf:formGroup>
                    <div id="returnOrgList" class="col-sm-4 col-sm-offset-5" style="padding-top:3%">
                        <bc:button id="y" type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                <%--var uniscCode = $('#uniscCode').value();--%>
                                window.location = '${base}/bus/relOrg/list';
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
</body>
</html>