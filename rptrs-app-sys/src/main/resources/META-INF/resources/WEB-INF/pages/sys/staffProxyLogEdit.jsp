<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>人员借调</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="staffProxyLogBox" title="被借调人信息" col="12" collapsible="true">
        <bf:form id="">
            <bf:formGroup>
                <bc:col col="4">
                    <bc:label text="用户编码" col="4" formControl="true"/>
                    <bc:label text="${staff.staffCode}" col="8" formControl="true" style="text-align:left"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="用户名称" col="4" formControl="true"/>
                    <bc:label text="${staff.staffName}" col="8" formControl="true" style="text-align:left"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="用户性别" col="4" formControl="true"/>
                    <bc:label text="fixedDict:Gender/${staff.gender}" col="8" formControl="true"
                              style="text-align:left"/>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="4">
                    <bc:label text="出生日期" col="4" formControl="true"/>
                    <bc:label text="${staff.birthdayString}" col="8" formControl="true" style="text-align:left"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="移动电话" col="4" formControl="true"/>
                    <bc:label text="${staff.mobile}" col="8" formControl="true" style="text-align:left"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="用户邮件" col="4" formControl="true"/>
                    <bc:label text="${staff.email}" col="8" formControl="true" style="text-align:left"/>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="4">
                    <bc:label text="用户地址" col="4" formControl="true"/>
                    <bc:label text="${staff.address}" col="8" formControl="true" style="text-align:left"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="所属部门" col="4" formControl="true"/>
                    <bc:label text="${staff.deptName}" col="8" formControl="true" style="text-align:left"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="所属用户组" col="4" formControl="true"/>
                    <bc:label text="${staff.staffGroupName}" col="8" formControl="true" style="text-align:left"/>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="4">
                    <bc:label text="用户状态" col="4" formControl="true"/>
                    <bc:label text="fixedDict:StaffStatus/${staff.status}" col="8" formControl="true"
                              style="text-align:left"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="关联角色组" col="4" formControl="true"/>
                    <bc:label text="${staff.roleGroupName}" col="8" formControl="true" style="text-align:left"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="关联角色" col="4" formControl="true"/>
                    <bc:label text="${staff.roleName}" col="8" formControl="true" style="text-align:left"/>
                </bc:col>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>

    <bc:ibox title="编辑借调信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" validation="true">
            <bf:formGroup>
                <bf:hidden name="staffCode" value="${staff.staffCode}"/>
                <bc:col col="6">
                    <bc:label text="借往部门" col="4" formControl="true"></bc:label>
                    <bf:lookup id="deptCode" name="deptCode"
                               url="${base}/sys/staffProxyLog/proxyLogDlg?deptCode=${staff.deptCode}" col="8"
                               winId="winProxyDlg"
                               value="${staffProxyLog.deptCode}" text="${staffProxyLog.deptName}"
                               propText="displayName" propValue="unitCode"
                               callback="result" title="选择借调部门" onSelect="onSelect">
                    </bf:lookup>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="预计借调结束时间" col="4" formControl="true"/>
                    <bf:datebox id="preEndDate" name="preEndDate" value="${staffProxyLog.preEndDate}" col="8"
                                format="date"/>
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
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/sys/staffProxyLog/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/staffProxyLog/list';
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
                    <bc:button text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/sys/staffProxyLog/list';
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
</body>
</html>
