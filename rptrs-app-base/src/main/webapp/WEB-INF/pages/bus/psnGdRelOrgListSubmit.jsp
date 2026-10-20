<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>关联关系机构</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="关联关系机构" subTitle="机构关系信息" col="12" collapsible="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="info" text="基本信息">
                        <be:event>
                            basicInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="info" text="自然人信息">
                        <be:event>
                            affiliateInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="info" text="机构关系信息">
                        <be:event>
                            <%--rightsInformation();--%>
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
            <bc:ibox title="所属关联机构" subTitle="" col="12" collapsible="true">
                <bc:table id="psnRelOrgTable" idField="pid" url="${base}/bus/relPsn" queryUrl="psnRelOrg/${fromPid}">
<%--                    <bc:toolbar id="addRel" text="新增" type="button" handler="addRelHandler"/>--%>
                    <%--<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>--%>
<%--                    <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>--%>
                    <bc:column field="pid" title="自增主键" width="100" visible="false"/>
                    <bc:column field="relId" title="relId" width="100" visible="false"/>
                    <bc:column field="fromPid" title="源主体pid" width="100" visible="false"/>
                    <bc:column field="relCategory" title="关系类型" width="100" visible="false"/>
                    <bc:column field="relCategorySub" title="关系子类型" width="100" visible="false"/>
                    <bc:column field="fromType" title="源主体类型  PSN|个人 ORG|机构" width="100" visible="false"/>
                    <bc:column field="toType" title="关联关系主体类型   PSN|个人 ORG|机构" width="100" visible="false"/>
                    <bc:column field="fromMemo" title="源主体显示名称" width="100" visible="false"/>
                    <bc:column field="toMemo" title="关联关系主体显示名称" width="100" visible="false"/>
                    <bc:column field="relSort" title="关系类别" width="100" visible="false"/>
                    <bc:column field="position" title="职位" width="100" visible="false"/>
                    <bc:column field="relation" title="亲属关系" width="100" visible="false"/>
                    <bc:column field="shareholdingRatio" title="持股比例" width="100" visible="false"/>
                    <bc:column field="toPidShareholders" title="是否关联关系主体股东    0-否 1-是" width="100" visible="false"/>
                    <bc:column field="isShow" title="是否显示" width="100" visible="false"/>
                    <bc:column field="level" title="关系层级" width="100" visible="false"/>
                    <bc:column field="liftReason" title="解除原因" width="100" visible="false"/>
                    <bc:column field="orgName" title="股东名称" width="100"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="uniscCode" title="证件号码" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
                    <bc:column field="toType" title="类型" width="100" visible="false"/>
                    <bc:column field="relStatus" title="处理状态" formatter="custom:sys/dictItem/getDictItems/BUS-RelStatus"
                               width="100"/>
                    <%--<bc:operator id="view" icon="search" title="查看"/>--%>
                    <%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
                    <%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
                </bc:table>
            </bc:ibox>

            <bc:ibox title="下一级关联机构" subTitle="" col="12" collapsible="true">
                <bc:table id="psnRelOrgTable1" idField="pid" url="${base}/bus/relPsn"
                          queryUrl="psnDownRelOrg/${fromPid}" addUrl="addPsnRelOrg/${fromPid}">
                    <%--<bc:toolbar id="add" text="新增" type="button"/>--%>
                    <%--<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>--%>
                    <%--<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>--%>
                    <bc:column field="pid" title="自增主键" width="100" visible="false"/>
                    <bc:column field="relId" title="relId" width="100" visible="false"/>
                    <bc:column field="fromPid" title="源主体pid" width="100" visible="false"/>
                    <bc:column field="relCategory" title="关系类型" width="100" visible="false"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系子类型" width="100" visible="false"
                               formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="fromType" title="源主体类型  PSN|个人 ORG|机构" width="100" visible="false"/>
                    <bc:column field="toType" title="关联关系主体类型   PSN|个人 ORG|机构" width="100" visible="false"/>
                    <bc:column field="fromMemo" title="源主体显示名称" width="100" visible="false"/>
                    <bc:column field="toMemo" title="关联关系主体显示名称" width="100" visible="false"/>
                    <bc:column field="relSort" title="关系类别" width="100" visible="false"/>
                    <bc:column field="position" title="职位" width="100" visible="false"/>
                    <bc:column field="relation" title="亲属关系" width="100" visible="false"/>
                    <bc:column field="shareholdingRatio" title="持股比例" width="100" visible="false"/>
                    <bc:column field="toPidShareholders" title="是否关联关系主体股东    0-否 1-是" width="100" visible="false"/>
                    <bc:column field="isShow" title="是否显示" width="100" visible="false"/>
                    <bc:column field="level" title="关系层级" width="100" visible="false"/>
                    <bc:column field="liftReason" title="解除原因" width="100" visible="false"/>
                    <bc:column field="orgName" title="股东名称" width="100"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="uniscCode" title="证件号码" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别"
                               formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory" width="100"/>
                    <bc:column field="relCategorySub" title="关系" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"
                               width="100"/>
                    <bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
                    <bc:column field="toType" title="类型" width="100" visible="false"/>
                    <bc:column field="relStatus" title="处理状态" width="100"/>
                    <%--<bc:operator id="view" icon="search" title="查看"/>--%>
                    <%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
                    <%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
                </bc:table>
            </bc:ibox>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    function basicInformation() {
        var pid = $("#fromPid").val();
        window.location = '${base}/bus/gdInfo/openSubmit/' + pid + '/PSN';
    }

    function affiliateInformation() {
        var pid = $("#fromPid").val();
        window.location = '${base}/bus/gdInfo/psnRelPsnListSubmit/' + pid;
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

    function removeHandler() {
        var relId = getPidSelections();
        $.ajax({
            url: "${base}/bus/rel/removePsnRel/" + relId,
            method: "POST",
            data: {"": ""},
            dataType: "JSON",
            traditional: true,
            success: function (data) {
                if (data.success) {
                    var fromPid = $('#fromPid').val();
                    window.location = '${base}/bus/gdInfo/psnRelOrgList/' + fromPid;
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        })
    }

    function getIdSelections() {
        return $.map($('#psnRelOrgTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    function getPidSelections() {
        return $.map($('#psnRelOrgTable').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }
    function addRelHandler() {
        window.location = '${base}/bus/gdInfo/psnRelOrgEdit/${fromPid}';
    }
</script>
</body>
</html>