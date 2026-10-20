<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>角色管理</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="roleBox" title="编辑角色信息" col="12" collapsible="true">
            <bf:form id="editForm" method="post" validation="true">
                <bf:hidden name="opt" id="opt"/>
                <bf:formGroup>
                    <bc:label text="机构层级" col="4" formControl="true"/>
                    <bf:select id="hierarchy" name="hierarchy" col="5" value="${role.hierarchy}"
                               disabled="${opt == 'edit'}"
                               dataSource="fixedDict" url="InsUnit">
                        <bv:notEmpty/>
                        <be:event type="select">
                            select(e.target.value);
                        </be:event>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="角色类型" col="4" formControl="true"/>
                    <bf:select name="roleStdCode" id="roleStdCode" col="5" dataSource="custom"
                               url="sys/roleStd/queryAll"
                               value="${role.roleStdCode}" disabled="${opt == 'edit'}">
                        <bv:notEmpty/>
                        <be:event type="select">
                            setRoleCodeAndName();
                        </be:event>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="角色组" col="4" formControl="true"/>
                    <bf:select id="roleGroup" name="roleGroupCode" col="5" dataSource="custom" allowClear="true"
                               url="sys/roleGroup/queryAll?hierarchy=${role.hierarchy}" value="${roleGroupCode}"
                               multiple="true">
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="角色编码" col="4" formControl="true"/>
                    <bf:text name="roleCode" col="5" placeholder="" value="${role.roleCode}" id="roleCode"
                             disabled="true">
                        <bv:notEmpty/>
                        <bv:jepCode/>
                        <bv:remote url="${base}/sys/role/check" name="roleCode" message="该编码已被占用"/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="角色名称" col="4" formControl="true"/>
                    <bf:text name="roleName" id="roleName" col="5" placeholder="" value="${role.roleName}"
                             disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="状态" col="4" formControl="true"/>
                    <bf:checkGroup name="status" col="5" value="${role.status}">
                        <bf:radio dataSource="fixedDict" url="RoleStatus" checked="true">
                            <bv:choice max="1" min="1" message="请选择状态"/>
                        </bf:radio>
                    </bf:checkGroup>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="角色描述" col="4" formControl="true"/>
                    <bf:text name="roleDesc" multiple="true" col="5" placeholder="" value="${role.roleDesc}"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div align="center">
                        <bc:button type="primary" text="保存" icon="save">
                            <be:event>
                                $('#editForm').data('formValidation').validate();
                                var isValid = $('#editForm').data('formValidation').isValid();
                                if(isValid)
                                {
                                var roleCode = $('#roleCode').val();
                                $.mask.post('${base}/sys/role/check', {roleCode:roleCode}, function(data){
                                if(data.valid)
                                {
                                var formData = $('#editForm').serializeAll();
                                $.mask.post('${base}/sys/role/save', formData, function(data){
                                if(data.success)
                                {
                                $.messager.alert('提示', data.msg, 'info', function(){
                                window.location = '${base}/sys/role/list';
                                });
                                }
                                else
                                {
                                $.messager.alert('错误', data.msg, 'error');
                                }
                                });
                                }else
                                {
                                $.messager.alert('提示', '角色已存在！', 'error');
                                }
                                });

                                }
                            </be:event>
                        </bc:button>
                        <bc:button text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/role/list';
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
        if ('${opt}' == 'add') {
            loadData($('#hierarchy').val());
        }
        setRoleCodeAndName();
    });

    function select(value) {
        loadData(value);
        setRoleCodeAndName();
    }

    function setRoleCodeAndName() {
        var hierarchy = $('#hierarchy').val();
        var roleStdCode = $('#roleStdCode').val();
        var hierarchyText = $('#hierarchy').find("option:selected").text();
        var roleStdCodeText = $('#roleStdCode').find("option:selected").text();
        $('#roleCode').val(hierarchy + roleStdCode);
        $('#roleName').val(hierarchyText + roleStdCodeText);
    }

    function loadData(value) {
        $.ajax({
            type: 'GET',
            url: '${base}/sys/roleGroup/queryAll?hierarchy=' + value,
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
                $("#roleGroup").empty();
                $("#roleGroup").select2({data: arr, width: "100%"});
            }
        });
    }
</script>
</body>
</html>