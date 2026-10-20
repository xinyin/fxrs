<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>机构关联关系金融产品</title>
    <script>
        window.onload = x;

        function x() {
            var value = '${op}';
            if (value != "0") {
                $('#orgTable_tb_add').css('display', 'none');
                $('#orgTable_tb_remove1').css('display', 'none');
                $('#orgTable_tb_edit').css('display', 'none');
            }
            if (value == '2') {
                $('#returnOrgList').hide();
            }
        }
    </script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bf:hidden id="opt" name="opt" value="${opt}"/>
                <bf:hidden id="op" name="op" value="${op}"/>
                <bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
            </bf:form>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:button type="light" text="基本信息">
                        <be:event>
                            basicInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="light" text="自然人信息">
                        <be:event>
                            affiliateInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="light" text="关联法人或其他组织">
                        <be:event>
                            rightsInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="关系金融产品">
                        <be:event>
                            <%--proRelInfoewrmation();--%>
                        </be:event>
                    </bc:button>
                </bc:col>
            </bf:formGroup>
            <bc:ibox title="所属关联金融产品" subTitle="" col="12" collapsible="true">
                <bc:table id="orgTable" idField="pid" url="${base}/bus" queryUrl="relOrg/proUpOfOrgList/${fromPid}"
                          addUrl="relOrg/addProUpOfOrg/${fromPid}">
                    <bc:toolbar id="add" text="新增" type="button"/>
                    <bc:toolbar id="edit" text="编辑" type="button" handler="editOrgPro"/>
                    <bc:toolbar id="remove1" text="删除" type="button" handler="removeAffiliate"/>
                    <bc:column field="pid" title="关联关系表自增长主键" width="100" visible="false"/>
                    <bc:column field="relId" title="关联关系relId" width="100" visible="false"/>
                    <bc:column field="proName" title="金融产品名称" width="100"/>
                    <bc:column field="proProvider" title="产品批准/注册/备案号" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:column field="shareholdingRatio" title="持有占比(%)" width="100"/>
                    <bc:column field="relStatus" title="操作状态" width="100" visible="false"/>
                    <bc:operator id="viewOrgPro" icon="eye" title="查看" handler="viewOrgPro"/>
                </bc:table>
            </bc:ibox>

            <bc:ibox title="下一级金融产品" subTitle="" col="12" collapsible="true">
                <bc:table id="orgDownTable" idField="pid" url="${base}/bus"
                          queryUrl="relOrg/proDownOfOrgListss/${fromPid}">
                    <bc:column field="pid" title="关联关系表自增长主键" width="100" visible="false"/>
                    <bc:column field="relId" title="关联关系relId" width="100" visible="false"/>
                    <bc:column field="proName" title="金融产品名称" width="100"/>
                    <bc:column field="proProvider" title="产品批准/注册/备案号" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:column field="shareholdingRatio" title="持有占比(%)" width="100"/>
                    <bc:column field="relStatus" title="审批状态" width="100" visible="false"/>
                    <bc:operator id="viewOrgPro1" icon="eye" title="查看" handler="viewOrgPro1"/>
                </bc:table>
                <bf:formGroup>
                    <div id="returnOrgList" class="col-sm-4 col-sm-offset-5" style="padding-top:3%">
                        <bc:button id="y" type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
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
<script type="text/javascript">

    function basicInformation() {
        var value = '${op}';
        var pid = $("#fromPid").val();
        if (value == "0") {
            // alert(value);
            window.location = '${base}/bus/relOrg/edit/' + pid + '?op=0';
        } else if (value == '2') {
            window.location = "${base}/bus/relOrg/orgForm/" + pid;
        } else {
            // alert(value);
            window.location = '${base}/bus/relOrg/edit/' + pid + '?op=' + value;
        }
    }

    function affiliateInformation() {
        var value = '${op}';
        var pid = $("#fromPid").val();

        if (value == "0") {
            window.location = '${base}/bus/relOrg/listAffi/' + pid + '?op=0';
        } else {
            // alert(value);
            window.location = '${base}/bus/relOrg/listAffi/' + pid + '?op=' + value;

        }

    }

    function rightsInformation() {
        var value = '${op}';
        var pid = $("#fromPid").val();
        if (value == "0") {
            // alert(value);
            window.location = '${base}/bus/rel/list/' + pid + '?op=0 ';
        } else {
            // alert(value);
            window.location = '${base}/bus/rel/list/' + pid + '?op=' + value;
        }
    }

    function proRelInformation() {
        var value = '${op}';
        var pid = $("#fromPid").val();
        if (value == "0") {
            // alert(value);
            window.location = '${base}/bus/relOrg/proOfOrgList/' + pid + '?op=0 ';
        } else {
            // alert(value);
            window.location = '${base}/bus/relOrg/proOfOrgList/' + pid + '?op=' + value;
        }
    }

    //编辑
    function editOrgPro() {
        var pid = getIdSelections();
        var relId = getrelId();
        var fromPid = $('#fromPid').val();
        if (relId == '' || relId == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location = "${base}/bus/relOrg/editProUpOfOrg/" + pid + "/" + relId + "/" + fromPid;
    }

    //所属详情
    function viewOrgPro() {
        var pid = getIdSelections();
        var relId = getrelId();
        var fromPid = $('#fromPid').val();
        if (relId == '' || relId == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location = "${base}/bus/relOrg/viewProUpOfOrg/" + pid + "/" + relId + "/" + fromPid;
    }

    //下一级详情
    function viewOrgPro1() {
        var pid = getIdSelections1();
        var relId = getrelId1();
        var fromPid = $('#fromPid').val();
        if (relId == '' || relId == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location = "${base}/bus/relOrg/viewProUpOfOrg/" + pid + "/" + relId + "/" + fromPid;
    }

    <%--function  editPro() {--%>
    <%--	var id = getIdSelections();--%>
    <%--	var fromPid = $("#fromPid").val();--%>
    <%--	if(id != ""){--%>
    <%--		window.location = '${base}/bus/relOrg/editPro/' + id+"/"+ fromPid;--%>
    <%--	}else {--%>
    <%--		$.messager.alert('提示', '请选择机构。', 'info');--%>
    <%--		return "";--%>
    <%--	}--%>
    <%--}--%>

    function getIdSelections() {
        return $.map($('#orgTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    function getIdSelections1() {
        return $.map($('#orgDownTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    function getRelStatus() {
        return $.map($('#orgTable').bootstrapTable('getSelections'), function (row) {
            return row.relStatus;
        });
    }

    function getStatusSelections() {
        return $.map($('#orgTable').bootstrapTable('getSelections'), function (row) {
            return row.relStatus;
        });
    }

    function removeAffiliate() {
        var fromPid = "${fromPid}"
        var relId = getrelId();
        // alert(relId);
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
            $.ajax({
                url: "${base}/bus/relPsnOfPro/removeRelInformation/" + relId,
                method: "POST",
                data: {"": ""},
                dataType: "JSON",
                traditional: true,
                success: function (data) {
                    if (data.success) {
                        var fromPid = $('#fromPid').val();
                        window.location = '${base}/bus/relOrg/proOfOrgList/' + fromPid + '?op=0';
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })
        })
    }

    function getrelId() {
        return $.map($('#orgTable').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }

    function getrelId1() {
        return $.map($('#orgDownTable').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }
</script>
</body>
</html>