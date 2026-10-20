<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>机构代管关系表</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="bizDeptProxyBox" title="代管管理编辑" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden name="opt" id="opt" value="${opt}"/>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="部门名称" col="4" formControl="true"/>
                    <bf:unitLookup id="deptCode" name="deptCode"
                                   col="8"
                                   value="${bizDeptProxy.deptCode}" text="${bizDeptProxy.deptName}"
                                   propText="unitName" propValue="unitCode"
                                   callback="result" onSelect="onSelectDept">
                        <bv:notEmpty/>
                    </bf:unitLookup>
                    <bf:hidden name="id" id="id" value="${bizDeptProxy.id}"></bf:hidden>
                    <bf:hidden name="deptName" id="deptName" value="${bizDeptProxy.deptName}"></bf:hidden>
                    <bf:hidden name="stdRoleName" id="stdRoleName" value="${bizDeptProxy.stdRoleName}"></bf:hidden>
                    <bf:hidden name="proxyDeptName" id="proxyDeptName"
                               value="${bizDeptProxy.proxyDeptName}"></bf:hidden>
                    <bf:hidden name="proxyStdRoleName" id="proxyStdRoleName"
                               value="${bizDeptProxy.proxyStdRoleName}"></bf:hidden>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="标准角色" col="4" formControl="true"/>
                    <bf:select name="stdRoleCode" col="8" dataSource="custom" url="sys/bizDeptProxy/queryStdRole"
                               value="${bizDeptReport.bizCode}">
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="代管部门名称" col="4" formControl="true"/>
                    <bf:unitLookup id="proxyDeptCode" name="proxyDeptCode"
                                   col="8"
                                   value="${bizDeptProxy.proxyDeptCode}" text="${bizDeptProxy.proxyDeptName}"
                                   propText="unitName" propValue="unitCode"
                                   callback="result" onSelect="onSelectProxyDept">
                        <bv:notEmpty/>
                    </bf:unitLookup>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="代管标准角色编码" col="4" formControl="true"/>
                    <bf:select name="proxyStdRoleCode" col="8" dataSource="custom" url="sys/bizDeptProxy/queryStdRole"
                               value="${bizDeptReport.proxyStdRoleCode}">
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="12">
                    <bc:label text="备注" col="2" formControl="true"/>
                    <bf:text id="remark" name="remark" col="10" rows="5"
                             multiple="true">${bizDeptProxy.remark}</bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div align="center">
                    <bc:button type="primary" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            <!-- 设置选择项的值 -->
                            $('#stdRoleName').val($('select[name="stdRoleCode"]').select2("data")[0]['text']);
                            $('#proxyStdRoleName').val($('select[name="proxyStdRoleCode"]').select2("data")[0]['text']);
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/sys/bizDeptProxy/checkRepeat', formData, function(data){
                            if(data.valid||data.bizDeptProxyID==$('#id').val()){
                            $.mask.post('${base}/sys/bizDeptProxy/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/bizDeptProxy/list';
                            });
                            }
                            else
                            {
                            $.messager.alert('错误', data.msg, 'error');
                            }
                            });
                            }
                            else
                            {
                            $.messager.alert('提示', '部门【' + $('#deptName').val() + '】的标准角色【' + $('#stdRoleName').val() + '】已设定了代管部门，不能再次设定。', 'info');
                            }
                            });
                            }
                        </be:event>
                    </bc:button>
                    <bc:button text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/sys/bizDeptProxy/list';
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
    /* 处理选择的管理部门名称 */
    function onSelectDept(data) {
        $('#deptName').val(data[0]['unitName']);
        if ($('#proxyDeptCode').jUnitLookup('getValue') == data[0]['unitCode']) {
            $.messager.alert('提示', '代管部门和部门，不能是同一个，请重新选择。', 'info', function () {
                $('#deptCode').jUnitLookup('cleanData');
            });
        }
    }

    /* 处理选择的管理部门名称 */
    function onSelectProxyDept(data) {
        $('#proxyDeptName').val(data[0]['unitName']);
        if ($('#deptCode').jUnitLookup('getValue') == data[0]['unitCode']) {
            $.messager.alert('提示', '代管部门和部门，不能是同一个，请重新选择。', 'info', function () {
                $('#proxyDeptCode').jUnitLookup('cleanData');
            });
        }
    }


</script>
</body>
</html>
