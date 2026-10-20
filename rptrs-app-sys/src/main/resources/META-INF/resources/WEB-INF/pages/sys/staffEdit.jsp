<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>用户管理</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="iboxStaff" title="编辑用户" subTitle="请输入用户信息" col="12" collapsible="true">
        <bf:form id="formStaff" method="post" validation="true">
            <bf:hidden name="opt"/>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="用户编码" col="4" formControl="true"/>
                    <bf:text id="staffCode" name="staffCode" col="8" value="${staff.staffCode}"
                             disabled="${opt == 'edit'}">
                        <bv:notEmpty/>
                        <bv:remote url="${base}/sys/staff/check" name="staffCode" message="用户编码已存在"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="用户名称" col="4" formControl="true"/>
                    <bf:text id="staffName" name="staffName" col="8" value="${staff.staffName}"
                             disabled="${editable == 'N'}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="性别" col="4" formControl="true"/>
                    <bf:checkGroup name="gender" col="8" value="${staff.gender}" disabled="${editable == 'N'}">
                        <bf:radio dataSource="fixedDict" url="Gender" checked="true"/>
                    </bf:checkGroup>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="所属部门" col="4" formControl="true"/>
                    <bf:lookup id="deptCode" name="deptCode" col="8" title="用户所属部门"
                               url="${base}/sys/staff/unitDlg" winId="unitSelect"
                               value="${staff.deptCode}" text="${staff.deptName}"
                               propText="unitName" propValue="unitCode"
                               callback="result" onSelect="onSelect">
                        <bv:notEmpty/>
                    </bf:lookup>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="出生日期" col="4" formControl="true"/>
                    <bf:datebox id="birthday" name="birthday" col="8" value="${staff.birthdayString}"
                                disabled="${editable == 'N'}"/>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="所在用户组" col="4" formControl="true"/>
                    <bf:select id="staffGroupCode" name="staffGroupCode" col="8" dataSource="custom" allowClear="true"
                               url="sys/staffGroup/queryAll" value="${staff.staffGroupCode}" multiple="true">
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="邮件" col="4" formControl="true"/>
                    <bf:text id="email" name="email" col="8" value="${staff.email}" disabled="${editable == 'N'}">
                        <bv:regexp regexp="^[^@\\s]+@([^@\\s]+\\.)+[^@\\s]+$" message="请输入有效的邮箱"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="设定角色" col="4" formControl="true"/>
                    <bf:select id="role" name="roleCode" col="8" dataSource="custom" allowClear="true"
                               url="sys/role/queryAll?hierarchy=${staf}" value="${staff.roleCode}" multiple="true">
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="移动电话" col="4" formControl="true"/>
                    <bf:text id="mobile" name="mobile" col="8" value="${staff.mobile}" disabled="${editable == 'N'}">
                        <bv:phone country="CN"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="设定角色组" col="4" formControl="true"/>
                    <bf:select id="roleGroup" name="roleGroupCode" col="8" dataSource="custom" allowClear="true"
                               url="sys/roleGroup/queryAll?hierarchy=${hierarchy}" value="${staff.roleGroupCode}"
                               multiple="true">
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="状态" col="4" formControl="true"/>
                    <bf:checkGroup name="status" value="${staff.status}" col="8">
                        <bf:radio dataSource="fixedDict" url="StaffStatus" checked="true"/>
                    </bf:checkGroup>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="用户类型1" col="4" formControl="true"/>
                    <bf:checkGroup name="staffType" value="${staff.staffType}" col="8">
                        <bf:radio dataSource="fixedDict" url="StaffType1" checked="true"/>
                    </bf:checkGroup>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="用户类型2" col="4" formControl="true"/>
                    <bf:checkGroup name="staffType2" value="${staff.staffType2}" col="8">
                        <bf:radio dataSource="fixedDict" url="StaffType" checked="true"/>
                    </bf:checkGroup>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="地址" col="2" formControl="true"/>
                <bf:text name="address" multiple="true" col="8" placeholder="" value="${staff.address}"></bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <div align="center">
                    <bc:button type="primary" text="保存" icon="save">
                        <be:event>
                            $('#formStaff').data('formValidation').validate();
                            var isValid = $('#formStaff').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#formStaff').serializeAll();
                            $.mask.post('${base}/sys/staff/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', '保存成功。', 'info', function(){
                            window.location = '${base}/sys/staff/list';
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
                            window.location = '${base}/sys/staff/list';
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

    function onSelect(data) {
        /* 重新加载角色、角色组的值 */
        var role = $("#role");
        var roleUrl = '${base}/sys/role/queryAll?hierarchy=';
        var roleGroup = $("#roleGroup");
        var roleGroupUrl = '${base}/sys/roleGroup/queryAll?hierarchy=';
        loadData(roleUrl, data[0].hierarchy, role);
        loadData(roleGroupUrl, data[0].hierarchy, roleGroup);
    }

    function loadData(url, hierarchy, id) {

        $.ajax({
            type: 'GET',
            url: url + hierarchy,
            dataType: 'json',
            delay: 200,
            success: function (data) {

                var arr = new Array();
                $.each(data, function (i, val) {
                    var obj = {
                        id: val.value,
                        text: val.name
                    };
                    arr[i] = obj;
                });
                id.empty(); //清空下拉框
                id.select2({data: arr, width: '100%'});
            }
        });
    }

</script>
</body>
</html>
