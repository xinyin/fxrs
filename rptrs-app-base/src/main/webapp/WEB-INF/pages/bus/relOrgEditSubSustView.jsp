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
    <div class="row">
        <%--隐藏标签  用于必须字段--%>
        <bf:hidden id="pid" name="pid" value="${pid}"/>
        <bc:ibox id="relOrgSuspectBox" title="自然人关系编辑" subTitle="" col="12" collapsible="false">
            <%--<bf:form id="form" method="post" validation="false">--%>
                <%--<bf:hidden id="opt" name="opt" value="${opt}"/>--%>
            <%--</bf:form>--%>
            <bf:formGroup>
                <div class="col-sm-6 col-sm-offset-2">
                    <bc:button type="info" text="基本信息">
                        <be:event>
                            basicInformation();
                        </be:event>
                    </bc:button>
                    <bc:button type="info" text="自然人关系信息">
                        <be:event>
                        </be:event>
                    </bc:button>
                    <bc:button type="info" text="机构关系信息">
                        <be:event>
                            holderInformation();
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
            <bc:table id="relOrgSubTable" idField="pid" url="${base}/bus/relOrgSuspect/" queryUrl="viewSubInfo/${pid}">
                <bc:toolbar id="add1" text="新增" type="button" handler="addHandler"/>
                <bc:toolbar id="edit1" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove1" text="删除" type="button"/>
                <%--<bc:column field="pid" title="关联关系表自增长主键" width="100"  visible="false"/>--%>
                <%--<bc:column field="toPid" title="关联关系pid" width="100"  visible="false"/>--%>
                <bc:column field="name" title="姓名" width="100"/>
                <%--<bc:column field="sex" title="性別" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Sex"/>--%>
                <bc:column field="identifyType" title="证件类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="identifyNumber" title="证件号码" width="100"/>
                <bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
                <bc:column field="dutyCode" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DutyCode"/>
                <bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    function basicInformation() {
        var pid = $("#pid").val();
        window.location = '${base}/bus/relOrgSuspect/edit/'+ pid+'?';
    }

    function holderInformation(){
        var pid = $("#pid").val();
        if(pid == ""){
            alert("pid不可为空")
        }else {
            window.location = '${base}/bus/relOrgSuspect/editHolder/'+pid;
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
        var $ids = [];
        var $chkBoxes = $('#relOrgSubTable').find('input:checked');
        if ($chkBoxes.length == 0) {
            alert('请至少选择一个数据集');
            return false;
        }

        $($chkBoxes).each(function () {
            $ids.push( $(this).attr('value') );
        });
        var $ids_str = $ids.join(',');
        window.location = "${base}/bus/relOrgSuspect/relPsnEdit/"+$ids_str;
        // console.log($ids_str);
        // alert($ids_str)
    }
</script>
</body>
</html>