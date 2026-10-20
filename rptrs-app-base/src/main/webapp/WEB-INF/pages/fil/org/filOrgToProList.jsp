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
            window.location = "${base}/fil/rel/addOrg/" + orgid;
        }
        function editHandler(){
            var id = getIdSelections();
            var orgid = $('#orgid').val();
            var relId = getrelIdSelections();
            window.location = "${base}/fil/rel/editAll/"+id + "/" + orgid + "/" + relId;
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
            window.location="${base}/fil/rel/viewOrg/"+ relId+"/"+fromPid+"/"+type;
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
            window.location="${base}/fil/rel/viewOrg/"+ relId+"/"+fromPid+"/"+type;
        }


        function removeHandler() {
            var relId = getrelIdSelections();
            if (relId=='' || relId==null){
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            $.messager.confirm('提示', '确定要删除吗？', function(r){
                if(r){
                    <%--window.location = "${base}/fil/rel/removeOrg/" + relId;--%>
                    $.ajax({
                        url:"${base}/fil/rel/removeRel/"+relId,
                        method:"POST",
                        data:{"":""},
                        dataType:"JSON",
                        traditional:true,
                        success:function(data){
                            if(data.success){
                                var orgid = $('#orgid').val();
                                window.location = '${base}/fil/rel/list/'+orgid+'?op=0';
                            }else
                            {
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        }
                    })
                }
            })
        }
        function affiliateInformation(){
            var value='${op}';
            var orgid = $('#orgid').val();
            if (value == "0"){
                // alert(value);
                window.location =  '${base}/fil/relOrg/listAffi/' + orgid + '?op=0';
            }else {
                // alert(value);
                window.location =  '${base}/fil/relOrg/listAffi/' + orgid + '?op=1';
            }
        }
        function orgRelInformation(){
            var value='${op}';
            var orgid = $('#orgid').val();
            if (value == "0"){
                // alert(value);
                window.location = '${base}/fil/rel/list/'+orgid + '?op=0';
            }else {
                // alert(value);
                window.location = '${base}/fil/rel/list/'+orgid + '?op=1';
            }
        }
        function basicInformation(){
            var value='${op}';
            var orgid = $('#orgid').val();
            if (value == "0"){
                // alert(value);
                window.location = "${base}/fil/relOrg/edit/"+orgid + '?op=0';
            }else {
                // alert(value);
                window.location = "${base}/fil/relOrg/edit/"+orgid + '?op=1';
            }
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
                            orgRelInformation();
                        </be:event>
                    </bc:button>
                    <bc:button id="proRelInformation" type="info" text="关系金融产品">
                    </bc:button>
                </div>
            </bf:formGroup>
            <bc:ibox title="所属关联金融产品" subTitle="" col="12" collapsible="true">
                <bc:table id="relTable1" idField="pid" url="${base}/fil/relOrg" queryUrl="queryProVo?orgid=${orgid}">
                    <bc:toolbar id="addORG" text="新增" type="button" handler="addORGHandler"/>
                    <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                    <bc:toolbar id="info" text="详情" type="button" handler="viewAffiliate1"/>
                    <bc:column field="proName" title="金融产品名称" width="100"/>
                    <bc:column field="proProvider" title="产品批准/注册/备案号" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="shareholdingRatio" title="持股比例或表决权(%)" width="100"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100" visible="false"/>
                </bc:table>
            </bc:ibox>
            <bc:ibox title="下级关联金融产品" subTitle="" col="12" collapsible="true">
                <bc:table id="relTable2" idField="pid" url="${base}/fil/rel" queryUrl="queryDownProVo?orgid=${orgid}">
                    <bc:toolbar id="info" text="详情" type="button" handler="viewAffiliate2"/>
                    <bc:column field="proName" title="金融产品名称" width="100"/>
                    <bc:column field="proProvider" title="产品批准/注册/备案号" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="shareholdingRatio" title="持股比例或表决权(%)" width="100"/>
                    <bc:column field="isValid" title="该关系是否有效" width="100" visible="false"/>
                </bc:table>
            </bc:ibox>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script>
    function viewHandler1(){
        var pid=getIdSelections1();
        window.location = '${base}/fil/relOrg/edit/' + pid;
    }

    function viewHandler2(){
        var pid=getIdSelections2();
        window.location = '${base}/fil/relOrg/edit/' + pid;
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