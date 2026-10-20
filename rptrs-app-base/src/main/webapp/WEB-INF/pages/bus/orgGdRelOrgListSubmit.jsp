<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>Rel</title>
    <script>
        function addORGHandler(){
            var orgid = $('#orgid').val();
            window.location = "${base}/bus/gdInfo/orgRelOrgEdit/" + orgid;
        }
        function editHandler(){
            var id = getIdSelections();
            var orgid = $('#orgid').val();
            var relId = getrelIdSelections();
            window.location = "${base}/bus/rel/editAll/"+id + "/" + orgid + "/" + relId;
        }
        function getIdSelections() {
            return $.map($('#relTable1').bootstrapTable('getSelections'), function (row) {
                return row.pid;
            });
        }
        function getrelIdSelections() {
            return $.map($('#relTable1').bootstrapTable('getSelections'), function (row) {
                return row.relId;
            });
        }
        function removeHandler() {
            var relId = getrelIdSelections();
            <%--window.location = "${base}/bus/rel/removeOrg/" + relId;--%>
            $.ajax({
                url:"${base}/bus/rel/removeRel/"+relId,
                method:"POST",
                data:{"":""},
                dataType:"JSON",
                traditional:true,
                success:function(data){
                    if(data.success){
                        var orgid = $('#orgid').val();
                        window.location = '${base}/bus/rel/list/'+orgid;
                    }else
                    {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })
        }
        function affiliateInformation(){
            var orgid = $('#orgid').val();
            window.location =  '${base}/bus/gdInfo/orgRelPsnListSubmit/' + orgid;
        }
        function orgRelInformation(){
            var orgid = $('#orgid').val();
            window.location = '${base}/bus/rel/list/'+orgid;
        }
        function basicInformation(){
            var orgid = $('#orgid').val();
            window.location = "${base}/bus/gdInfo/openSubmit/"+orgid + '/ORG';
        }
    </script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="机构关系信息" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bf:hidden id="orgid" name="orgid" value="${orgid}"/>
            </bf:form>
            <bf:formGroup>
                <div class="col-sm-6 col-sm-offset-2">
                    <bc:button type="info" text="基本信息">
                        <be:event>
                            basicInformation();
                        </be:event>
                    </bc:button>
                    <bc:button id = "psnRelInformation" type="info" text="自然人关系信息">
                        <be:event>
                            affiliateInformation();
                        </be:event>
                    </bc:button>
                    <bc:button id ="orgRelInformation" type="info" text="机构关系信息">
                        <be:event>
<%--                            orgRelInformation();--%>
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
            <bc:ibox title="所属关联机构" subTitle="" col="12" collapsible="true">
                <bc:table id="relTable1" idField="pid" url="${base}/bus/rel" queryUrl="queryVo?orgid=${orgid}">
<%--                    <bc:toolbar id="addORG" text="新增" type="button" handler="addORGHandler"/>--%>
                    <%--<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>--%>
<%--                    <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>--%>
                    <%--<bc:toolbar id="view1" text="查看" type="button" handler="viewHandler1"/>--%>
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
                    <bc:column field="orgName" title="名称" width="100"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="uniscCode" title="证件号码" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
                    <bc:column field="toType" title="类型" width="100" visible="false"/>
                    <%--<bc:operator id="view" icon="search" title="详情"/>--%>
                    <%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
                    <%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
                </bc:table>
            </bc:ibox>
            <bc:ibox title="下级关联机构" subTitle="" col="12" collapsible="true">
                <bc:table id="relTable11" idField="pid" url="${base}/bus/rel" queryUrl="queryDownVo?orgid=${orgid}">
                    <%--<bc:toolbar id="addORG" text="新增" type="button" handler="addORGHandler"/>--%>
                    <%--<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>--%>
                    <%--<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>--%>
<%--                    <bc:toolbar id="view2" text="查看" type="button" handler="viewHandler2"/>--%>
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
                    <bc:column field="orgName" title="名称" width="100"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="uniscCode" title="证件号码" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
                    <bc:column field="toType" title="类型" width="100" visible="false"/>
                    <%--<bc:operator id="view" icon="search" title="查看"/>--%>
                    <%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
                    <%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
                </bc:table>
            </bc:ibox>
        </bc:ibox>
    </div>
    <%--<div class="row">--%>
        <%--<bc:ibox title="投资情况" subTitle="" col="6" collapsible="true">--%>
            <%--<bc:table id="relTable2" idField="pid" url="${base}/bus/rel" queryUrl="queryTZVo?orgid=${orgid}">--%>
                <%--<bc:toolbar id="addTZORG" text="新增" type="button" handler="add1Handler"/>--%>
                <%--<bc:toolbar id="edit" text="编辑" type="button" handler="edit1Handler"/>--%>
                <%--<bc:toolbar id="remove" text="删除" type="button" handler="remove1Handler"/>--%>
                <%--<bc:column field="pid" title="自增主键" width="100" visible="false"/>--%>
                <%--<bc:column field="topid" title="topid" width="100" visible="false"/>--%>
                <%--<bc:column field="toPid" title="关联关系pid" width="100" visible="false"/>--%>
                <%--<bc:column field="fromPid" title="源主体pid" width="100" visible="false"/>--%>
                <%--<bc:column field="relCategory" title="关系类型" width="100" visible="false"/>--%>
                <%--<bc:column field="relCategorySub" title="关系子类型" width="100" visible="false"/>--%>
                <%--<bc:column field="fromType" title="源主体类型  PSN|个人 ORG|机构" width="100" visible="false"/>--%>
                <%--<bc:column field="toType" title="关联关系主体类型   PSN|个人 ORG|机构" width="100" visible="false"/>--%>
                <%--<bc:column field="fromMemo" title="源主体显示名称" width="100" visible="false"/>--%>
                <%--<bc:column field="toMemo" title="关联关系主体显示名称" width="100" visible="false"/>--%>
                <%--<bc:column field="relSort" title="关系类别" width="100" visible="false"/>--%>
                <%--<bc:column field="position" title="职位" width="100" visible="false"/>--%>
                <%--<bc:column field="relation" title="亲属关系" width="100" visible="false"/>--%>
                <%--<bc:column field="shareholdingRatio" title="持股比例" width="100" visible="false"/>--%>
                <%--<bc:column field="toPidShareholders" title="是否关联关系主体股东    0-否 1-是" width="100" visible="false"/>--%>
                <%--<bc:column field="isShow" title="是否显示" width="100" visible="false"/>--%>
                <%--<bc:column field="level" title="关系层级" width="100" visible="false"/>--%>
                <%--<bc:column field="liftReason" title="解除原因" width="100" visible="false"/>--%>
                <%--<bc:column field="toMemo" title="被投资方" width="100"/>--%>
                <%--<bc:column field="identifyType" title="证件类型" width="100"--%>
                           <%--formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>--%>
                <%--<bc:column field="identifyNumber" title="证件号码" width="100"/>--%>
                <%--<bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>--%>
                <%--<bc:column field="toType" title="类型" width="100" visible="false"/>--%>
                <%--<bc:operator id="view" icon="search" title="查看"/>--%>
                <%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
                <%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
            <%--</bc:table>--%>
        <%--</bc:ibox>--%>
    <%--</div>--%>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script>
    function viewHandler1(){
        var pid=getIdSelections1();
        window.location = '${base}/bus/relOrg/edit/' + pid;
    }

    function viewHandler2(){
        var pid=getIdSelections2();
        window.location = '${base}/bus/relOrg/edit/' + pid;
    }

    function getIdSelections1() {
        return $.map($('#relTable1').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    function getIdSelections2() {
        return $.map($('#relTable11').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }


</script>
</body>
</html>