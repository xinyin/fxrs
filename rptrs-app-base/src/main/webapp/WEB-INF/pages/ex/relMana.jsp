<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <br:css pack="form"/>
    <title>自然人新增或编辑</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox title="关联方上级信息" subTitle="" col="12" collapsible="true">
        <bc:table id="upPsnTable" idField="id" url="${base}/bus/relPsnSuspect" queryUrl="/queryUpInfo/7780/ORG">
            <bc:column field="name" title="关联方名称" width="100"/>
            <bc:column field="shareholdersCode" title="股东代码" width="100"/>
            <bc:column field="identifyType" title="证件类型" width="100"/>
            <bc:column field="uniscCode" title="证件号码" width="100"/>
            <bc:column field="relType" title="关联关系类型" width="100"/>
            <bc:column field="shareholdingRatio" title="持股比例" width="100"/>
            <bc:column field="holeShareAmount" title="持股数量" width="100"/>
        </bc:table>
    </bc:ibox>
    <bc:ibox id="projectBox" title="关联方基本信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <%--<bf:hidden id="opt" name="opt" value="${opt}"/>--%>
            <bf:hidden id="pid" name="pid" value="${pid}"/>
            <bf:formGroup type="success" hr="dashed" hrp="bottom">
                <%--<bc:col col="4">--%>
                <%--<bc:label text="自增pid" col="4" formControl="true"/>--%>
                <%--<bf:text id="pid" name="pid" col="8" value="${pid}">--%>
                <%--</bf:text>--%>
                <%--</bc:col>--%>
                <bc:col col="6">
                    <bc:label text="关联方名称" col="4" formControl="true"/>
                    <bf:text id="name" name="name" col="8" value="${name}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="股东代码" col="4" formControl="true"/>
                    <bf:text id="shareholdersCode" name="shareholdersCode" col="8"  value="${shareholdersCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup type="success" hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="证件类型" col="4" formControl="true"/>
                    <bf:text id="identifyType" name="identifyType" col="8" value="${identifyType}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="证件号码" col="4" formControl="true"/>
                    <bf:text id="identifyNumber" name="identifyNumber" col="8" value="${identifyNumber}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup type="success" hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="关联关系类型" col="4" formControl="true"/>
                    <bf:text id="toRelCategory" name="toRelCategory" col="8" value="${toRelCategory}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="持股比例" col="4" formControl="true"/>
                    <bf:text id="shareholdingRatio" name="shareholdingRatio" col="8" value="${shareholdingRatio}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup type="success" hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="持股数量" col="4" formControl="true"/>
                    <bf:text id="shareholdingStrand" name="shareholdingStrand" col="8" value="${shareholdingStrand}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="情况说明" col="4" formControl="true"/>
                    <bf:text id="cognizaReason" name="cognizaReason" col="8" value="${cognizaReason}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">
    <%--function basicInformation() {--%>
    <%--var opt = $("#opt").val();--%>
    <%--if(opt == "edit"){--%>
    <%--var pid = $("#pid").val();--%>
    <%--window.location = '${base}/bus/relPsnSuspect/edit/'+ pid+'?';--%>
    <%--}else{--%>
    <%--window.location = '${base}/bus/relPsnSuspect/addForRelPsnAdd';--%>
    <%--}--%>
    <%--}--%>
    <%--function affiliateInformation() {--%>
        <%--var opt = $("#opt").val();--%>
        <%--var pid = $("#pid").val();--%>
        <%--if(pid == ""){--%>
            <%--pid = "%"--%>
        <%--}--%>
        <%--// if(opt == "edit"){--%>
        <%--window.location = '${base}/bus/relPsnSuspect/suspectAffiliateList/edit/'+pid + '? ';--%>
        <%--// }else{--%>
        <%--&lt;%&ndash;window.location = '${base}/bus/relPsnSuspect/suspectAffiliateList/'+ opt + "/" +pid +'?';&ndash;%&gt;--%>
        <%--&lt;%&ndash;}&ndash;%&gt;--%>
    <%--}--%>
    <%--function rightsInformation() {--%>
        <%--var opt = $("#opt").val();--%>
        <%--var pid = $("#pid").val();--%>
        <%--window.location = '${base}/bus/relPsnSuspect/psnRelOrgList/edit/' +pid +'? ';--%>
    <%--}--%>
    $(document).ready(function () {
        var opt = $('#opt').val();
        if (opt != 'edit') {
            document.getElementById("psnRelInformation").setAttribute("disabled", true);
            document.getElementById("orgRelInformation").setAttribute("disabled", true);
        }
    });
</script>
</body>
</html>
