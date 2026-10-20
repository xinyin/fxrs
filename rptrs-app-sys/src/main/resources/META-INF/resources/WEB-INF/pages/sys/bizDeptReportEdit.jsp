<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>机构上报关系表</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="bizDeptReportBox" title="机构上报关系编辑" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden name="opt" id="opt"/>
            <bf:formGroup>
                <bf:hidden name="id" value="${bizDeptReport.id}"></bf:hidden>
                <bf:hidden id="deptName" name="deptName" value="${bizDeptReport.deptName}"></bf:hidden>
                <bc:label text="上报部门" col="4" formControl="true"/>
                <bf:unitLookup id="deptCode" name="deptCode"
                               col="4"
                               value="${bizDeptReport.deptCode}" text="${bizDeptReport.deptName}"
                               propText="unitName" propValue="unitCode"
                               callback="result" onSelect="onSelectDept">
                    <bv:notEmpty/>
                </bf:unitLookup>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="部门业务种类名称" col="4" formControl="true"/>
                <bf:select name="bizCode" col="4" dataSource="custom"
                           url="sys/bizDeptReport/queryBizType?deptCode=${bizDeptReport.deptCode}"
                           disabled="${opt != 'edit'}" value="${bizDeptReport.bizCode}">
                    <bv:notEmpty/>
                </bf:select>
                <bf:hidden name="bizName" id="bizName" value="${bizDeptReport.bizName}"/>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="管理部门" col="4" formControl="true"/>
                <bf:unitLookup id="managerDeptCode" name="managerDeptCode"
                               col="4"
                               value="${bizDeptReport.managerDeptCode}" text="${bizDeptReport.managerDeptName}"
                               propText="unitName" propValue="unitCode"
                               callback="result" onSelect="onSelectManagerDept">
                    <bv:notEmpty/>
                    <bv:different field="deptCodeText" message="上报部门和管理部门不能是同一个部门"/>
                </bf:unitLookup>
                <bf:hidden name="managerDeptName" id="managerDeptName" value="${bizDeptReport.managerDeptName}"/>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="备注" col="4" formControl="true"/>
                <bf:text id="remark" name="remark" col="4" rows="4" multiple="true">${bizDeptReport.remark}</bf:text>
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
                            $('#bizName').val($('select[name="bizCode"]').select2("data")[0]['text']);
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/sys/bizDeptReport/checkRepeat', formData, function(data){
                            if(data.valid){
                            $.mask.post('${base}/sys/bizDeptReport/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/bizDeptReport/list';
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
                            $.messager.alert('提示', '上报部门【' + $('#deptName').val() + '】的业务【' + $('#bizName').val() + '】已设定了管理部门，不能再次设定。', 'info');
                            }
                            });

                            }
                        </be:event>
                    </bc:button>
                    <bc:button text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/sys/bizDeptReport/list';
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
    /* 处理选择的部门名称 */
    function onSelectDept(data) {
        $('#deptName').val(data[0]['unitName']);
        /*  if($('#managerDeptCode').jUnitLookup('getValue') == data[0]['unitCode']){
                $.messager.alert('提示', '上报部门和管理部门，不能是同一个，请重新选择。', 'info', function(){
                    $('#deptCode').jUnitLookup('cleanData');
                });
            } */
        // 获取部门下拉框选中的值
        var v = data[0]['unitCode'];
        if (v == null) {
            // 下拉框禁用
            $('select[name="bizCode"]').prop("disabled", true);
        } else {
            // 部门选择后下拉框启用
            $('select[name="bizCode"]').prop("disabled", false);

            // 角色联动部门下拉框
            var url = "queryBizType?deptCode=" + v;
            // 向后台请求获取数据
            $.getJSON(url, function (data) {

                if (data == "") {
                    swal('该部门下未维护业务类型，请先添加业务类型!');
                } else {

                    var optionString = "";
                    // 遍历部门下的角色给下拉框赋值
                    $.each(data, function (i, value) {
                        optionString += "<option value=\'" + value.value + "\'>" + value.name + "</option>";
                    });

                    $('select[name="bizCode"]').html(optionString);
                }
            });

        }
    }

    /* 处理选择的管理部门名称 */
    function onSelectManagerDept(data) {
        $('#managerDeptName').val(data[0]['unitName']);
        /* if($('#deptCode').jUnitLookup('getValue') == data[0]['unitCode']){
            $.messager.alert('提示', '上报部门和管理部门，不能是同一个，请重新选择。', 'info', function(){
                $('#managerDeptCode').jUnitLookup('cleanData');
            });
        } */
    }

    function onSelectBizCode(data) {
        $.messager.alert(data);
    }
</script>
</body>
</html>
