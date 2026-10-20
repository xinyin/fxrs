<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>关联关系自然人</title>
    <script>
        window.onload = x;

        function x() {
            var value = '${op}';
            if (value != "0") {
                $('#affiliateTable_tb_add').css('display', 'none');
                $('#affiliateTable_tb_remove1').css('display', 'none');
                $('#affiliateTable_tb_edit').css('display', 'none');
                $('#returnButton').css('display', 'none');
            }
        }
    </script>
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
            <bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
            <bf:formGroup>
                <div class="col-sm-6">
                    <bc:button type="light" text="基本信息">
                        <be:event>
                            basicInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="关系自然人信息">
                        <be:event>
                            <%--affiliateInformation();--%>
                        </be:event>
                    </bc:button>
                    <bc:button type="light" text="关系机构信息">
                        <be:event>
                            rightsInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="light" text="关系金融产品">
                        <be:event>
                            proRelInformation();
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
            <bc:ibox title="所属关联自然人" subTitle="" col="12" collapsible="true">
                <bc:table id="affiliateTable" idField="pid" url="${base}/fil/filRelPsn" queryUrl="affiliate/${fromPid}"
                          addUrl="addAffiliate/${fromPid}">
                    <bc:toolbar id="add" text="新增" type="button"/>
                    <bc:toolbar id="edit" text="编辑" type="button" handler="editAffiliate"/>
                    <bc:toolbar id="remove1" text="删除" type="button" handler="removeAffiliate"/>
                    <%-- <bc:toolbar id="info" text="详情" type="button" handler="viewAffiliate2"/>--%>
                    <bc:column field="pid" title="关联关系表自增长主键" width="100" visible="false"/>
                    <bc:column field="relId" title="关联关系relId" width="100" visible="false"/>
                    <bc:column field="name" title="姓名" width="100"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="identifyNumber" title="证件号码" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:operator id="viewAffiliate1" icon="eye" title="查看" handler="viewAffiliate1"/>
                </bc:table>
            </bc:ibox>

            <bc:ibox title="下一级关联自然人" subTitle="" col="12" collapsible="true">
                <bc:table id="affiliateTable1" idField="pid" url="${base}/fil/filRelPsn"
                          queryUrl="affiliateDown/${fromPid}" addUrl="addAffiliate/${fromPid}">
                    <%--<bc:toolbar id="add" text="新增" type="button"/>--%>
                    <%--<bc:toolbar id="edit" text="编辑" type="button" handler="editAffiliate"/>
                    <bc:toolbar id="info" text="详情" type="button" handler="viewAffiliate2"/>--%>
                    <bc:column field="pid" title="关联关系表自增长主键" width="100" visible="false"/>
                    <bc:column field="relId" title="关联关系relId" width="100" visible="false"/>
                    <bc:column field="name" title="姓名" width="100"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="identifyNumber" title="证件号码" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:operator id="viewAffiliate2" icon="eye" title="查看" handler="viewAffiliate2"/>
                </bc:table>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-5" style="padding-top:3%">
                        <bc:button id="y" type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                var value='${wt}';
                                if(value == '1'){
                                window.location = '${base}/fil/filRelPsn/list';
                                }else if(value == '2'){
                                window.location = '${base}/workflow/approve/doneList'
                                }else{
                                window.location = '${base}/workflow/process/completeList'
                                }
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
        var value='${wt}';
        if(value == '1'){
            window.location = '${base}/fil/filRelPsn/list';
        }else{
            window.location = '${base}/workflow/process/completeList'
        }
    }

    function basicInformation() {
        var value = '${wt}';
        var pid = $("#fromPid").val();

        window.location = '${base}/fil/filRelPsn/compared/' + pid + '?op=1&wt=' + value;

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

    function editAffiliate() {
        var id = getIdSelections();
        var fromPid = $("#fromPid").val();
        if (id != "") {
            window.location = '${base}/fil/filRelPsn/editAffiliate/' + id + "/" + fromPid;
        } else {
            $.messager.alert('提示', '请选择自然人。', 'info');
            return "";
        }
    }

    function getIdSelections() {
        return $.map($('#affiliateTable').bootstrapTable('getSelections'), function (row) {
            return row.pid + "/" + row.relId;
        });
    }

    function removeAffiliate() {
        var relId = getrelId();
        if (relId == '' || relId == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        $.messager.confirm('提示', '确定要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    url: "${base}/fil/filRelPsn/removeRelInformation/" + relId,
                    method: "POST",
                    data: {"": ""},
                    dataType: "JSON",
                    traditional: true,
                    success: function (data) {
                        if (data.success) {
                            var fromPid = $('#fromPid').val();
                            window.location = '${base}/fil/filRelPsn/affiliateList/' + fromPid + '?op=0';
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    }
                })
            }
        })
    }

    function viewAffiliate1() {
        var relId = getrelId();
        var fromPid = $('#fromPid').val();
        var type = "UP";
        if (relId == '' || relId == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location = "${base}/fil/filRelPsn/viewAffiliate/" + relId + "/" + fromPid + "/" + type + "?op=1";
    }

    function viewAffiliate2() {
        var relId = getrelId1();
        var fromPid = $('#fromPid').val();
        var type = "DOWN";
        if (relId == '' || relId == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location = "${base}/fil/filRelPsn/viewAffiliate/" + relId + "/" + fromPid + "/" + type + "?op=1";
    }

    function viewAffiliate3() {
        var relId = getrelId();
        var fromPid = $('#fromPid').val();
        var type = "UP";
        if (relId == '' || relId == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location = "${base}/fil/filRelPsn/viewAffiliate/" + relId + "/" + fromPid + "/" + type + "?op=1";
    }


    function getrelId() {
        return $.map($('#affiliateTable').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }

    function getrelId1() {
        return $.map($('#affiliateTable1').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }
</script>
</body>
</html>