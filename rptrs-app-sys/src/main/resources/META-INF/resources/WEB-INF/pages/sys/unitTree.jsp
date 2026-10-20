<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>Unit管理</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="unitBox" title="机构树配置" col="12" collapsible="false">
            <bc:col col="3">
                <bc:tree id="unitTree" url="${base}/sys/unit" edit="true"
                         formId="unitForm" autoParam="id=parentId"
                         formUneditableField="unitCode" formNameField="unitName">
                    <be:event type="onClick">
                        nodeClick(event, treeId, treeNode, clickFlag);
                    </be:event>
                </bc:tree>
            </bc:col>
            <bc:col col="9">
                <bf:form id="unitForm" method="post" validation="true">
                    <bf:formGroup>
                        <bc:col col="6">
                            <bc:label text="上级机构编码" col="4" formControl="true"/>
                            <bf:text name="parentId" col="8" disabled="true"/>
                        </bc:col>
                        <bc:col col="6">
                            <bc:label text="上级机构名称" col="4" formControl="true"/>
                            <bf:text name="parentName" col="8" disabled="true"/>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:col col="6">
                            <bc:label text="机构编码" col="4" formControl="true"/>
                            <bf:text id="unitCode" name="unitCode" col="8">
                                <bv:notEmpty/>
                                <bv:regexp regexp="^[\w\s]+$" message="请输入正确的编码"/>
                                <bv:remote url="${base}/sys/unit/check" name="unitCode" message="该编码已被占用"/>
                            </bf:text>
                        </bc:col>
                        <bc:col col="6">
                            <bc:label text="机构名称" col="4" formControl="true"/>
                            <bf:text id="unitName" name="unitName" col="8">
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:col col="6">
                            <bc:label text="机构层级" col="4" formControl="true"/>
                            <bf:select name="hierarchy" dataSource="fixedDict" url="InsUnit" col="8"/>
                        </bc:col>
                        <bc:col col="6">
                            <bc:label text="机构类型" col="4" formControl="true"/>
                            <bf:select name="kind" dataSource="fixedDict" url="UnitKind" col="8">
                                <bv:notEmpty/>
                            </bf:select>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:col col="6">
                            <bc:label text="状态" col="4" formControl="true"/>
                            <bf:select name="status" dataSource="fixedDict" url="UnitStatus" col="8">
                                <bv:notEmpty/>
                            </bf:select>
                        </bc:col>
                        <bc:col col="6">
                            <bc:label text="法人编码" col="4" formControl="true"/>
                            <bf:text id="tenantId" name="tenantId" col="8">
                            </bf:text>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:col col="6">
                            <bc:label text="是否为业务部门" col="4" formControl="true"/>
                            <bf:checkGroup id="isBizDept" name="isBizDept" col="8">
                                <bf:radio dataSource="fixedDict" url="YesNo"/>
                            </bf:checkGroup>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="选择业务种类" col="2" formControl="true"/>
                        <bf:lookup id="bizTypeCode" name="bizTypeCode"
                                   url="${base}/sys/bizType/dialog" col="10" winId="bizTypeSelect"
                                   propText="bizTypeName" propValue="bizTypeCode"
                                   value="${unit.bizTypeCode}" text="${unit.bizTypeName}"
                                   callback="result" title="业务种类">
                        </bf:lookup>
                        <input type="hidden" name="bizTypeName">
                    </bf:formGroup>
                    <bf:formGroup hidden="true">
                        <bc:label text="路径名称" col="2" formControl="true"/>
                        <bf:text id="pathName" name="pathName" col="10" disabled="true"/>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="机构描述" col="2" formControl="true"/>
                        <bf:text id="unitDesc" name="unitDesc" col="10" multiple="true"/>
                    </bf:formGroup>
                    <bf:formGroup>
                        <div align="center">
                            <bc:button id="btnSave" type="primary" text="保存" icon="save">
                                <be:event>
                                    var bizTypeName = $('input[name="bizTypeCodeText"]').val();
                                    $('input[name="bizTypeName"]').val(bizTypeName);
                                </be:event>
                            </bc:button>
                        </div>
                    </bf:formGroup>
                </bf:form>
            </bc:col>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {
        var treeObj = $.fn.zTree.getZTreeObj("_jTree_unitTree");
// 	treeObj.setting.callback.onClick = nodeClick;
    });

    function nodeClick(event, treeId, treeNode, clickFlag) {
        console.log(treeNode.kind);
// 	if(treeNode.kind == 'DEPT')
// 	{
// 		$('input[name="isBizDept"]').removeAttr('disabled');
// 	}
// 	else if(treeNode.kind == 'UNIT')
// 	{
// 		$('input[name="isBizDept"]').attr('disabled',true);
// 		$('input[name="isBizDept"]').attr("checked", false);
// 	}
    }
</script>
</body>
</html>