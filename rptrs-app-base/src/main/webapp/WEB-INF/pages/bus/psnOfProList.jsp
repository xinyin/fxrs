<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>金融产品关联关系自然人</title>

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
                </bc:col>
            </bf:formGroup>
            <bc:ibox title="所属关联自然人" subTitle="" col="12" collapsible="true">
                <bc:table id="psnTable" idField="pid" url="${base}/bus" queryUrl="relPsnOfPro/psnUpOfProList/${fromPid}"
                          addUrl="relPro/addPsnOfPro/${fromPid}">
                    <bc:toolbar id="add" text="新增" type="button"/>
                    <bc:toolbar id="edit" text="编辑" type="button" handler="editAffiliate"/>
                    <bc:toolbar id="remove" text="删除" type="button" handler="removeAffiliate"/>
                    <bc:column field="pid" title="关联关系表自增长主键" width="100" visible="false"/>
                    <bc:column field="relId" title="relId" width="100" visible="false"/>
                    <bc:column field="name" title="姓名" width="100"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="identifyNumber" title="证件号码" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:operator id="viewPsnOfPro1" icon="eye" title="查看" handler="viewPsnOfPro1"/>
                </bc:table>
            </bc:ibox>

            <bc:ibox title="下一级关联自然人" subTitle="" col="12" collapsible="true">
                <bc:table id="psnDownTable" idField="pid" url="${base}/bus"
                          queryUrl="relPsnOfPro/psnDownOfProList/${fromPid}">
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
                    <bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                    <bc:operator id="viewPsnOfPro2" icon="eye" title="查看" handler="viewPsnOfPro2"/>
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
<be:processEvent/>
<script type="text/javascript">
    /*$(document).ready(function(){
        showAndnone();
    });*/
    window.onload = showAndnone();

    function showAndnone() {
        var value = '${op}';

        if (value == 1) {
            // $('#psnTable_tb_add').hide(); //隐藏
            $('#psnTable_tb_add').css('display', 'none');
            $('#psnTable_tb_edit').css('display', 'none');
        }
    }

    function basicInformation() {
        var value = '${op}';
        var pid = $("#fromPid").val();
        if (value == "0") {
            // alert(value);
            window.location = '${base}/bus/relPro/edit/' + pid + '?op=0';
        } else {
            // alert(value);
            window.location = '${base}/bus/relPro/edit/' + pid + '?op=1';
        }
    }

    function rightsInformation() {
        var value = '${op}';
        var pid = $("#fromPid").val();
        if (value == "0") {
            // alert(value);
            window.location = '${base}/bus/relPro/orgOfProList/' + pid + '?op=0 ';
        } else {
            // alert(value);
            window.location = '${base}/bus/relPro/orgOfProList/' + pid + '?op=1 ';
        }
    }

    function editAffiliate() {
        var pid = getIdSelections();
        var relId = getrelId();
        var fromPid = $("#fromPid").val();
        if (pid != "") {
            window.location = '${base}/bus/relPsn/proeditPsn/' + pid + "/" + relId + "/" + fromPid;

        } else {
            $.messager.alert('提示', '请选择自然人。', 'info');
            return "";
        }


        /*var pid = getIdSelections();
        var relId = getPidSelections();
        var fromPid = $("#fromPid").val();
        if(pid != ""){
            window.location = '/bus/relPsn/editPsnRelOrg/' + pid+"/"+ fromPid +"/"+ relId;
        }else {
            $.messager.alert('提示', '请选择机构。', 'info');
            return "";
        }*/

    }

    function viewPsnOfPro1() {

        var pid = getIdSelections();
        var relId = getrelId();
        var fromPid = $("#fromPid").val();
        if (pid != "") {
            window.location = '${base}/bus/relPsn/proviewPsn/' + pid + "/" + relId + "/" + fromPid;

        } else {
            $.messager.alert('提示', '请选择自然人。', 'info');
            return "";
        }
        <%--var relId = getrelId();--%>
        <%--var fromPid = $('#fromPid').val();--%>
        <%--var type="UP";--%>
        <%--if (relId=='' || relId==null){--%>
        <%--	$.messager.alert('错误', "请选择一条数据", 'error');--%>
        <%--	return;--%>
        <%--}--%>
        <%--window.location="${base}/bus/relPro/viewAffiliate/"+ +relId+"/"+fromPid+"/"+type;--%>
    }

    function viewPsnOfPro2() {
        var pid = getIdSelections();
        var relId = getrelId();
        var fromPid = $("#fromPid").val();
        if (pid != "") {
            window.location = '${base}/bus/relPsn/proviewPsn/' + pid + "/" + relId + "/" + fromPid;

        } else {
            $.messager.alert('提示', '请选择自然人。', 'info');
            return "";
        }
    }

    function getIdSelections() {
        return $.map($('#psnTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    function removeAffiliate() {
        var relId = getrelId();
        // alert(relId);
        if (relId == '' || relId == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
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
                        window.location = '${base}/bus/relPro/psnOfProList/' + fromPid + '?op=0';
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })
        })
    }

    function getrelId() {
        return $.map($('#psnTable').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }

</script>
</body>
</html>