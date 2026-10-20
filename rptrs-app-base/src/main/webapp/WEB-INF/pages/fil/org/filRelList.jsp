<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>Rel</title>
    <script>
        window.onload=x;
        function x() {
            // alert("进入");
            var value = '${op}';
            if (value != "0"){
                // alert("进入其他");
                $('#relTable1_tb_addORG').css('display', 'none');
                $('#relTable1_tb_remove').css('display', 'none');
            }
        }
        function addORGHandler(){
            var orgid = $('#orgid').val();
            window.location = "${base}/bus/rel/addOrg/" + orgid;
        }
        function editHandler(){
            var id = getIdSelections();
            var orgid = $('#orgid').val();
            var relId = getrelIdSelections();
            if(relId != null && relId !=""){
                window.location = "${base}/bus/rel/editAll/"+id + "/" + orgid + "/" + relId;
            }else{
                alert("请选择关联方");
                return;
            }

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
        function getrelIdSelections1() {
            return $.map($('#relTable2').bootstrapTable('getSelections'), function (row) {
                return row.relId;
            });
        }
       //归属
        function viewAffiliate1() {
            var relId = getrelIdSelections();
            var fromPid = $('#orgid').val();
            var type="UP";
            if (relId=='' || relId==null){
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            window.location="${base}/fil/relOrg/viewOrg/"+ relId+"/"+fromPid+"/"+type;
        }
        //下级
        function viewAffiliate2() {
            var relId = getrelIdSelections1();
            var fromPid = $('#orgid').val();
            var type="DOWN";
            if (relId=='' || relId==null){
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            window.location="${base}/fil/relOrg/viewOrg/"+ relId+"/"+fromPid+"/"+type;
        }


        function removeHandler() {
            var relId = getrelIdSelections();
            if (relId=='' || relId==null){
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            $.messager.confirm('提示', '确定要删除吗？', function(r){
                if(r){
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
                                window.location = '${base}/bus/rel/list/'+orgid+'?op=0';
                            }else
                            {
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        }
                    })
                }
            })
        }
        function basicInformation() {
            var value = '${wt}';
            var pid = $('#pid').val();

            window.location.href = "${base}/fil/relOrg/edit/" + pid + '?op=1&wt=' + value;

        }

        function affiliateInformation() {
            var value = '${op}';
            var pid = $('#pid').val();

            window.location = '${base}/fil/relOrg/listAffi/' + pid + '?op=1&wt=' + value;


        }


        function orgRelInformation() {
            var value = '${wt}';
            var pid = $('#pid').val();

            window.location = '${base}/fil/relOrg/orgRelList/' + pid + '?op=1&wt=' + value;

        }

        function proRelInformation() {
            var value = '${wt}';
            var pid = $('#pid').val();
            window.location = '${base}/fil/relOrg/proOfOrgList/' + pid + '?op=1&wt=' + value;

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
                    <bc:button id = "psnRelInformation" type="light" text="关系自然人信息">
                        <be:event>
                            affiliateInformation();
                        </be:event>
                    </bc:button>
                    <bc:button id ="orgRelInformation" type="primary" text="关系机构信息">
                        <be:event>
                            <%--orgRelInformation();--%>
                        </be:event>
                    </bc:button>
                    <bc:button id ="proRelInformation" type="light" text="关系金融产品">
                        <be:event>
                            proRelInformation();
                        </be:event>
                    </bc:button>
                </bc:col>
            </bf:formGroup>
            <bc:ibox title="所属关联机构" subTitle="" col="12" collapsible="true">
                <bc:table id="relTable1" idField="pid" url="${base}/bus/rel" queryUrl="queryVo?orgid=${orgid}">
                  <%--  <bc:toolbar id="addORG" text="新增" type="button" handler="addORGHandler"/>
                    <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>--%>
                    <%--<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>--%>
<%--                    <bc:toolbar id="info" text="详情" type="button" handler="viewAffiliate1"/>--%>
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
                    <bc:column field="orgName" title="名称" width="100"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="uniscCode" title="证件号码" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="shareholdingRatio" title="持股比例或表决权(%)" width="100"/>
                    <bc:column field="toType" title="类型" width="100" visible="false"/>
                    <bc:operator id="viewAffiliate1" icon="eye" title="查看" handler="viewAffiliate1"/>
                </bc:table>
            </bc:ibox>
            <bc:ibox title="下一级关联机构" subTitle="" col="12" collapsible="true">
                <bc:table id="relTable2" idField="pid" url="${base}/bus/rel" queryUrl="queryDownVo?orgid=${orgid}">
                    <%--<bc:toolbar id="addORG" text="新增" type="button" handler="addORGHandler"/>--%>
                    <%--<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>--%>
                    <%--<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                    <bc:toolbar id="info" text="详情" type="button" handler="viewAffiliate2"/>--%>
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
                    <bc:column field="orgName" title="名称" width="100"/>
                    <bc:column field="identifyType" title="证件类型" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="uniscCode" title="证件号码" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="shareholdingRatio" title="持股比例或表决权(%)" width="100"/>
                    <bc:column field="toType" title="类型" width="100" visible="false"/>
                    <bc:operator id="viewAffiliate2" icon="eye" title="查看" handler="viewAffiliate2"/>
                </bc:table>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                        <bc:button  type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                var value='${wt}';
                                if(value == '1'){
                                window.location = '${base}/fil/relOrg/list';
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
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
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