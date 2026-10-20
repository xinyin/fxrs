<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <x:jsLib/>
    <br:css pack="list"/>
    <title>机构信息数据</title>
    <x:jsLib/>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
    <bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="pid" name="pid" value="${rootOrgInfo.pid}"/>
            <div class="boxse">
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label cls="required" text="年度" col="5" formControl="true"/>
                        <bf:text id="years" name="years" col="7" value="${rootOrgInfo.years}" disabled="true">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="季度" col="5" formControl="true"/>
                        <bf:text id="quarter" name="quarter" col="7" value="${rootOrgInfo.quarter}" disabled="true">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="总资产(万元，请保留4位小数)" col="5" formControl="true"/>
                        <bf:text id="totalCapital" name="totalCapital" col="7" value="${rootOrgInfo.totalCapital}"
                                 disabled="true">
                            <bv:regexp regexp="^([1-9]([0-9]*)|([0]))(\\.[0-9]{0,4})?$" message="请输入正确的数值,限4位小数"/>
                            <bv:stringLength max="20" />
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label text="净资产(万元，请保留4位小数)" cls="required" col="5" formControl="true"/>
                        <bf:text id="netCaptial" name="netCaptial" col="7" value="${rootOrgInfo.netCaptial}"
                                 disabled="true">
                            <bv:regexp regexp="^([1-9]([0-9]*)|([0]))(\\.[0-9]{0,4})?$" message="请输入正确的数值,限4位小数"/>
                            <bv:stringLength max="20" />
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="季度综合偿付能力充足率（%）" cls="required" col="5" formControl="true"/>
                        <bf:text id="quarterCompreSolvencyRate" name="quarterCompreSolvencyRate" col="7"
                                 value="${rootOrgInfo.quarterCompreSolvencyRate}" disabled="true">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                    <%--<bf:formGroup>
                        <div class="col-sm-4 col-sm-offset-5">
                            <bc:button type="primary" text="返回" icon="arrow-circle-left">
                                <be:event>
                                    window.history.go(-1);
                                </be:event>
                            </bc:button>
                        </div>
                    </bf:formGroup>--%>
            </div>
        </bf:form>
    </bc:ibox>
</div>
<br:js pack="list"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {
    });
</script>
</body>
</html>