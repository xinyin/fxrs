<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>元数据表的自定义查询</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="metaQueryBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="元数据ID" col="4" formControl="true"/>
                    <bf:text id="tableId" name="tableId" col="8" value="${metaQuery.tableId}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="查询ID" col="4" formControl="true"/>
                    <bf:text id="queryId" name="queryId" col="8" value="${metaQuery.queryId}"
                             disabled="${opt == 'edit'}">
                        <bv:jepCode/>
                        <bv:notEmpty/>
                        <bv:remote url="${base}/sys/metaQuery/check" name="queryId" message="该编码已被占用"/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="查询名称" col="4" formControl="true"/>
                    <bf:text id="queryName" name="queryName" col="8" value="${metaQuery.queryName}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="公共查询：PUBLIC             私有查询：PRIVATE" col="4" formControl="true"/>
                    <bf:text id="queryType" name="queryType" col="8" value="${metaQuery.queryType}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="查询内容" col="4" formControl="true"/>
                    <bf:text id="script" name="script" col="8" value="${metaQuery.script}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="状态：             10 - 草稿             30 - 已生效             50 - 已失效" col="4"
                              formControl="true"/>
                    <bf:text id="status" name="status" col="8" value="${metaQuery.status}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="机构编码" col="4" formControl="true"/>
                    <bf:text id="unitCode" name="unitCode" col="8" value="${metaQuery.unitCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="机构名称" col="4" formControl="true"/>
                    <bf:text id="unitName" name="unitName" col="8" value="${metaQuery.unitName}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="部门编码" col="4" formControl="true"/>
                    <bf:text id="deptCode" name="deptCode" col="8" value="${metaQuery.deptCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="部门名称" col="4" formControl="true"/>
                    <bf:text id="deptName" name="deptName" col="8" value="${metaQuery.deptName}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="用户编码" col="4" formControl="true"/>
                    <bf:text id="staffCode" name="staffCode" col="8" value="${metaQuery.staffCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="用户名称" col="4" formControl="true"/>
                    <bf:text id="staffName" name="staffName" col="8" value="${metaQuery.staffName}">
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
                            $.mask.post('${base}/sys/metaQuery/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/metaQuery/list';
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
                            window.location = '${base}/sys/metaQuery/list';
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
