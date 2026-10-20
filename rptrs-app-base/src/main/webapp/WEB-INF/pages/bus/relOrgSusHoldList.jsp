<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>Rel</title>
    <script>
        function addPSNHandler(){
            window.location = "${base}/bus/rel/addPsn";
        }
        function addORGHandler(){
            window.location = "${base}/bus/rel/addOrg";
        }
        function editHandler(){
            var $ids = [];    //定义一个空数组
            debugger;
            var $chkBoxes = $('#relTable1').find('input:checked');
            if ($chkBoxes.length == 0) {
                alert('请至少选择一个数据集');
                return false;
            }
            $($chkBoxes).each(function () {
                $ids.push( $(this).attr('value') );
            });
            var $ids_str = $ids.join(',');
            window.location = "${base}/bus/rel/editAll/"+$ids_str;
        }
        function addHandler(){
            window.location = "${base}/bus/rel/addOrg";
        }
        function editHandler1(){
            var $ids = [];    //定义一个空数组
            var $chkBoxes = $('#relTable2').find('input:checked');
            if ($chkBoxes.length == 0) {
                alert('请至少选择一个数据集');
                return false;
            }

            $($chkBoxes).each(function () {
                $ids.push( $(this).attr('value') );
            });
            var $ids_str = $ids.join(',');
            window.location = "${base}/bus/rel/editOrg/"+$ids_str;
        }
    </script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <bc class="row">
        <bf:hidden id="pid" name="pid" value="${pid}"/>
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:formGroup>
                <bc:col col="6" colOffset="2">
                    <bc:button type="light" text="基本信息">
                        <be:event>
                            basicInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="light" text="自然人关系信息">
                        <be:event>
                            affiliateInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="机构关系信息">
                        <be:event>

                        </be:event>
                    </bc:button>
                </bc:col>
            </bf:formGroup>
            <bc:ibox title="所属关联机构" subTitle="" col="12" collapsible="true">
                <bc:table id="relOrgShareTable" idField="pid" url="${base}/bus/relOrgSuspect/" queryUrl="viewShareInfo/${pid}">
                    <bc:toolbar id="add1" text="新增" type="button" handler="addHandler"/>
                    <bc:toolbar id="edit1" text="编辑" type="button" handler="editHandler"/>
                    <bc:toolbar id="remove1" text="删除" type="button" handler="removeHandler" />
                    <bc:toolbar id="info" text="详情" type="button" handler="viewAffiliate1"/>
                    <bc:column field="orgName" title="名称" width="200" />
                    <bc:column field="pid" title="关系id" width="200" visible="false"/>
                    <bc:column field="identifyType" title="证件类型" width="150"
                               formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                    <bc:column field="uniscCode" title="证件号码" width="100"/>
                    <bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                    <bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
                    <bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
                </bc:table>
            </bc:ibox>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script>
    <%--function removeHandler() {--%>
    <%--    var pid = getrelId();--%>
    <%--    // alert(pid);--%>
    <%--    $.ajax({--%>
    <%--        url:"${base}/bus/relOrgSuspect/remove/"+pid,--%>
    <%--        method:"GET",--%>
    <%--        data:{"":""},--%>
    <%--        dataType:"JSON",--%>
    <%--        traditional:true,--%>
    <%--        success:function(data){--%>
    <%--            alert(res.msg);--%>
    <%--            if (res.success) {--%>
    <%--                window.location.reload();--%>
    <%--            }--%>
    <%--        }--%>
    <%--    })--%>
    <%--}--%>
    function removeHandler(){
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
        return $.map($('#relOrgShareTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    function basicInformation() {
        var pid = $("#pid").val();
        window.location = '${base}/bus/relOrgSuspect/edit/'+ pid+'?';
    }

    function affiliateInformation() {
        var pid = $("#pid").val();
        if(pid == ""){
            alert("pid不可为空")
        }else {
            window.location = '${base}/bus/relOrgSuspect/viewSub/'+pid;
        }
    }
    <%--function editHandler(){--%>
    <%--    var $ids = [];    //定义一个空数组--%>
    <%--    debugger;--%>
    <%--    var $chkBoxes = $('#relOrgShareTable').find('input:checked');--%>
    <%--    if ($chkBoxes.length == 0) {--%>
    <%--        alert('请至少选择一个数据集');--%>
    <%--        return false;--%>
    <%--    }--%>
    <%--    $($chkBoxes).each(function () {--%>
    <%--        $ids.push( $(this).attr('value') );--%>
    <%--    });--%>
    <%--    var $ids_str = $ids.join(',');--%>
    <%--    window.location = "${base}/bus/relOrgSuspect/relOrgEdit/"+$ids_str;--%>
    <%--}--%>

    function editHandler(){
        var pid = getrelId();
        // alert(pid);
        if(pid == ""){
            alert("pid不可为空")
        }else {
            window.location = "${base}/bus/relOrgSuspect/relOrgEdit/"+pid;
        }
    }

    function addHandler(){
        var pid = $("#pid").val();
        if(pid == ""){
            alert("pid不可为空")
        }else {
            window.location = "${base}/bus/relOrgSuspect/relOrgAdd/"+pid;
        }
    }
    function viewAffiliate1() {
        var relId = getrelId();
        if( relId != null){
            window.location = "${base}/bus/relOrgSuspect/relOrgView/"+relId;
        }else {
            $.messager.alert('提示', '请选择自然人。', 'info');
            return "";
        }
    }
</script>
</body>
</html>