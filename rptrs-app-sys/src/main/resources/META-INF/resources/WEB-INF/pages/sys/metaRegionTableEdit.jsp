<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>元数据区域关系表</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="metaRegionTableBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="关系ID" col="4" formControl="true"/>
                    <bf:text id="regionTableId" name="regionTableId" col="8" value="${metaRegionTable.regionTableId}"
                             disabled="${opt == 'edit'}">
                        <bv:jepCode/>
                        <bv:notEmpty/>
                        <bv:remote url="${base}/sys/metaRegionTable/check" name="regionTableId" message="该编码已被占用"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="区域ID" col="4" formControl="true"/>
                    <bf:text id="regionId" name="regionId" col="8" value="${metaRegionTable.regionId}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="元数据ID" col="4" formControl="true"/>
                    <bf:text id="tableId" name="tableId" col="8" value="${metaRegionTable.tableId}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="模式" col="4" formControl="true"/>
                    <bf:text id="schema" name="schema" col="8" value="${metaRegionTable.schema}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="元数据编码" col="4" formControl="true"/>
                    <bf:text id="tableCode" name="tableCode" col="8" value="${metaRegionTable.tableCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="M - 模型结果表             S - 步骤结果表             B - 业务数据表" col="4" formControl="true"/>
                    <bf:text id="tableType" name="tableType" col="8" value="${metaRegionTable.tableType}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="对象类型：             T - 表             V  -  视图" col="4" formControl="true"/>
                    <bf:text id="objectType" name="objectType" col="8" value="${metaRegionTable.objectType}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="对象来源：             S - 系统同步             C - 用户创建             M - 模型创建" col="4"
                              formControl="true"/>
                    <bf:text id="objectSource" name="objectSource" col="8" value="${metaRegionTable.objectSource}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="机构编码" col="4" formControl="true"/>
                    <bf:text id="unitCode" name="unitCode" col="8" value="${metaRegionTable.unitCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="机构名称" col="4" formControl="true"/>
                    <bf:text id="unitName" name="unitName" col="8" value="${metaRegionTable.unitName}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="部门编码" col="4" formControl="true"/>
                    <bf:text id="deptCode" name="deptCode" col="8" value="${metaRegionTable.deptCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="部门名称" col="4" formControl="true"/>
                    <bf:text id="deptName" name="deptName" col="8" value="${metaRegionTable.deptName}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="用户编码" col="4" formControl="true"/>
                    <bf:text id="staffCode" name="staffCode" col="8" value="${metaRegionTable.staffCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="用户名称" col="4" formControl="true"/>
                    <bf:text id="staffName" name="staffName" col="8" value="${metaRegionTable.staffName}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/sys/metaRegionTable/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/metaRegionTable/list';
                            });
                            }
                            else
                            {
                            $.messager.alert('错误', data.msg, 'error');
                            }
                            });
                            }
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/sys/metaRegionTable/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {

    });
</script>
</body>
</html>
