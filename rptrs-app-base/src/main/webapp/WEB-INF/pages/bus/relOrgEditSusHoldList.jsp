<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>Rel</title>
    <script>
        function editHandler(){
            var $ids = [];    //定义一个空数组
            debugger;
            var $chkBoxes = $('#relOrgShareTable').find('input:checked');
            if ($chkBoxes.length == 0) {
                alert('请至少选择一个数据集');
                return false;
            }
            $($chkBoxes).each(function () {
                $ids.push( $(this).attr('value') );
            });
            var $ids_str = $ids.join(',');
            window.location = "${base}/bus/relOrgSuspect/relOrgEdit/"+$ids_str;
        }
        function addHandler(){
            var pid = $("#pid").val();
            if(pid == ""){
                alert("pid不可为空")
            }else {
                window.location = "${base}/bus/relOrgSuspect/relOrgAdd/"+pid;
            }
        }
    </script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="relOrgSuspectBox1" title="机构关系编辑" subTitle="" col="12" collapsible="false">
            <bf:hidden id="pid" name="pid" value="${pid}"/>
            <bf:formGroup>
                <div class="col-sm-6 col-sm-offset-2">
                    <bc:button type="info" text="基本信息">
                        <be:event>
                            basicInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="info" text="自然人关系信息">
                        <be:event>
                            affiliateInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="info" text="机构关系信息">
                        <be:event>

                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
            <bc:table id="relOrgShareTable" idField="pid" url="${base}/bus/relOrgSuspect/" queryUrl="viewShareInfo/${pid}">
                <bc:toolbar id="add1" text="新增" type="button" handler="addHandler"/>
                <%--<bc:toolbar id="addORG" text="新增机构股东" type="button" handler="addORGHandler"/>--%>
                <bc:toolbar id="edit1" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove1" text="删除" type="button" handler="removeHandler"/>
                <%--<bc:column field="pid" title="自增主键" width="100" visible="false"/>--%>
                <bc:column field="orgName" title="名称" width="200"/>
                <bc:column field="identifyType" title="证件类型" width="150"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="uniscCode" title="证件号码" width="100"/>
                <bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                <bc:column field="toMemo" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelC"/>
                <bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
                <%--<bc:column field="toType" title="类型" width="100" visible="false"/>--%>
                <%--<bc:operator id="view" icon="search" title="查看"/>--%>
                <%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
                <%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script>
    function basicInformation() {
        var pid = $("#pid").val();
        window.location = '${base}/bus/relOrgSuspect/edit/'+ pid+'?';
    }

    function affiliateInformation() {
        var pid = $("#pid").val();
        if(pid == ""){
            alert("pid不可为空")
        }else {
            window.location = '${base}/bus/relOrgSuspect/editSub/'+pid;
        }
    }
</script>
</body>
</html>