<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>元数据区域，元数据的分类。管理</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="metaRegionBox" title="元数据区域" col="12" collapsible="false">
            <bc:col col="3">
                <bc:tree id="metaRegionTree" url="${base}/sys/metaRegion" edit="true"
                         formId="metaRegionForm" autoParam="id=parentId" onDefaultData="setDefaultData"
                         formUneditableField="regionCode" formNameField="regionName">
                </bc:tree>
            </bc:col>
            <bc:col col="9">
                <bf:form id="metaRegionForm" method="post" validation="true">
                    <bf:hidden name="tableCount"></bf:hidden>
                    <bf:hidden name="hierarchy"></bf:hidden>
                    <bf:hidden name="pathName"></bf:hidden>
                    <bf:hidden name="pathCode"></bf:hidden>
                    <bf:hidden name="tablespaceId"></bf:hidden>
                    <bf:hidden name="tablespaceCode"></bf:hidden>
                    <bf:hidden name="regionId"></bf:hidden>
                    <bf:hidden name="parentId"></bf:hidden>
                    <bf:formGroup>
                        <bc:label text="父菜单名称" col="2" formControl="true"/>
                        <bf:text name="parentName" col="6" disabled="true"/>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="区域编码" col="2" formControl="true"/>
                        <bf:text id="regionCode" name="regionCode" col="6">
                            <bv:notEmpty/>
                            <bv:jepCode/>
                            <bv:remote url="${base}/sys/metaRegion/check" name="regionCode" message="该编码已被占用"/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="区域名称" col="2" formControl="true"/>
                        <bf:text id="regionName" name="regionName" col="6">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="区域描述" col="2" formControl="true"/>
                        <bf:text id="regionDesc" name="regionDesc" col="6" multiple="true"></bf:text>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="状态" col="2" formControl="true"/>
                        <bf:select name="status" col="6" id="status" excludes="DRAFT" value="ENABLED"
                                   dataSource="fixedDict" url="Status">
                            <bv:notEmpty/>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="显示顺序" col="2" formControl="true"/>
                        <bf:text id="disorder" name="disorder" col="6">
                            <bv:notEmpty/>
                            <bv:digits/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup>
                        <div align="center">
                            <bc:button id="btnSave" type="primary" text="保存" icon="save"/>
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
    /**
     * 设置新增时节点的默认值
     */
    function setDefaultData(node) {
        /* 设置状态的默认值 */
        node['status'] = node['status'] ? node['status'] : 'ENABLED';
    }
</script>
</body>
</html>