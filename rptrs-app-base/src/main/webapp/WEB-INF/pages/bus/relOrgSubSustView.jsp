<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <br:css pack="form"/>
    <title>疑似机构</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <bc class="row">
        <%--隐藏标签  用于必须字段   指的是查询时的pid--%>
        <bf:hidden id="pid" name="pid" value="${pid}"/>
        <bc:ibox id="relOrgSuspectBox" title="自然人关系查看" subTitle="" col="12" collapsible="false">
            <%--<bc:ibox id="form" method="post" validation="false">--%>
                <bf:hidden id="pid" name="pid" value="${pid}"/>
                <bf:formGroup>
                     <bc:col col="6" colOffset="2">
                        <bc:button type="light" text="基本信息">
                            <be:event>
                                basicInformation();
                            </be:event>
                        </bc:button>
                        <bc:button type="primary" text="关系自然人信息">
                            <be:event>
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
                    <bc:table id="relOrgSubTable" idField="pid" url="${base}/bus/relOrgSuspect/" queryUrl="viewSubInfo/${pid}">
                        <bc:toolbar id="add1" text="新增" type="button" handler="addHandler"/>
                        <bc:toolbar id="edit1" text="编辑" type="button" handler="editHandler"/>
                        <bc:toolbar id="remove1" text="删除" type="button" handler="remove"/>
                        <bc:toolbar id="info" text="详情" type="button" handler="viewAffiliate1"/>
                        <bc:column field="pid" title="关系id" width="100" visible="false"/>
                        <bc:column field="relId" title="关联关系主键" width="100" visible="false"/>
                        <bc:column field="topid" title="topid" width="100" visible="false"/>
                        <bc:column field="name" title="名称" width="100"/>
                        <bc:column field="sex" title="性別" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Sex" visible="false"/>
                        <bc:column field="race" title="民族" width="100" visible="false"/>
                        <bc:column field="identifyType" title="证件类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                        <bc:column field="identifyNumber" title="证件号码" width="100"/>
                        <bc:column field="birthdate" title="出生日期" width="100" visible="false"/>
                        <bc:column field="mobile" title="移动电话" width="100" visible="false"/>
                        <bc:column field="email" title="邮箱" width="100" visible="false"/>
                        <bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                        <%--<bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DutyCode"/>--%>
                        <bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                        <bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>

                    </bc:table>
                </bc:ibox>
        <%--</bc:ibox>--%>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    function remove(){
            var pid = getrelId();
            <%--window.location = "${base}/bus/rel/removeOrg/" + relId;--%>
            if (pid == "" || pid == undefined) {
                alert("请选中数据")
            }else {
                var r = confirm("确定要删除数据？");
                if (r){
                    $.ajax({
                        url:"${base}/bus/relOrgSuspect/remove/"+pid,
                        method:"GET",
                        data:{"":""},
                        dataType:"JSON",
                        traditional:true,
                        success:function(data){
                            alert(data.msg);
                            if (data.success) {
                                window.location.reload();
                            }
                        }
                    })
                }

            }

    }

    function getrelId(){
        return $.map($('#relOrgSubTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    function basicInformation() {
        var pid = $("#pid").val();
        window.location = '${base}/bus/relOrgSuspect/edit/'+ pid+'?';
    }

    function rightsInformation(){
        var pid = $("#pid").val();
        if(pid == ""){
            alert("pid不可为空")
        }else {
            window.location = '${base}/bus/relOrgSuspect/viewHolder/'+pid;
        }
    }
function addHandler(){
    var pid = $("#pid").val();
    if(pid == ""){
        alert("pid不可为空")
    }else {
        window.location = '${base}/bus/relOrgSuspect/relPsnAdd/'+pid;
    }
}
    function editHandler(){
        var relId = getrelId();
        if( relId != null && relId !=""){
            window.location = "${base}/bus/relOrgSuspect/relPsnEdit/"+relId;
        }else {
            $.messager.alert('提示', '请选择自然人。', 'info');
            // return "";
            return;
        }
        <%--var $ids = [];--%>
        <%--var $chkBoxes = $('#relOrgSubTable').find('input:checked');--%>
        <%--if ($chkBoxes.length == 0) {--%>
        <%--    alert('请至少选择一个数据集');--%>
        <%--    return false;--%>
        <%--}--%>

        <%--$($chkBoxes).each(function () {--%>
        <%--    $ids.push( $(this).attr('value') );--%>
        <%--});--%>
        <%--var $ids_str = $ids.join(',');--%>
        <%--window.location = "${base}/bus/relOrgSuspect/relPsnEdit/"+$ids_str;--%>
        // console.log($ids_str);
        // alert($ids_str)
    }
    function viewAffiliate1() {
        var relId = getrelId();
        if( relId != null){
            window.location = "${base}/bus/relOrgSuspect/relPsnEdits/"+relId;
        }else {
            $.messager.alert('提示', '请选择自然人。', 'info');
            return "";
    }
    }

</script>
</body>
</html>