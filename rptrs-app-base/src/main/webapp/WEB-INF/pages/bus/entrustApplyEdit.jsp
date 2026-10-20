<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <br:css pack="form"/>
    <title>EntrustApply</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="entrustApplyBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${entrustApply.pid}"/>
            <bf:hidden id="status" name="status" value="${entrustApply.status}"/>
            <bf:hidden id="staffName" name="staffName" value="${entrustApply.staffName}"/>
            <bf:hidden id="transferStaffName" name="transferStaffName" value="${entrustApply.transferStaffName}"/>
        <div class="boxse">
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label cls="required" text="委托类型" col="4" formControl="true"/>
                    <bf:select id="entrustType" name="entrustType" col="7"   dataSource="custom"  url="sys/dictItem/getDictItems/BUS-TaskNumberType"
                                value="${entrustApply.entrustType}" disabled="${opt == 'view'}">
                        <be:event type="select">
                            setType(e.target.value);
                        </be:event>
                        <bv:notEmpty/>
                    </bf:select>
                </bf:formGroup>
            <div id="taskCode">
                <bf:formGroup col="4">
                    <bc:label text="任务编号" col="4" formControl="true"/>
                    <bf:text id="taskNumber" name="taskNumber" col="8" click="forNameSelect()" value="${entrustApply.taskNumber}" disabled="${opt == 'view'}">
                    </bf:text>
                </bf:formGroup>
            </div>
            </bf:formGroup>
            <bf:formGroup type="success">
                <bf:formGroup col="4">
                    <bc:label cls="required" text="委托人" col="4" formControl="true"/>
                    <bf:staffLookup id="staffCode" name="staffCode" col="8"
                                    value="${entrustApply.staffCode}" text="${entrustApply.staffName}" propText="staffName" propValue="staffCode"
                                    callback="result"  onSelect="staffChange" mult="false" placeholder="委托人" disabled="true">
                    </bf:staffLookup>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="委托人部门" col="4" formControl="true"/>
                    <bf:text id="staffDep" name="staffDep" col="8" value="${entrustApply.staffDep}" disabled="true">
                    </bf:text>
                </bf:formGroup>
            </bf:formGroup>

            <bf:formGroup hr="dashed" hrp="bottom">

                <bf:formGroup col="4">
                    <bc:label cls="required" text="被委托人" col="4" formControl="true"/>
                    <bf:staffLookup id="transferStaffCode" name="transferStaffCode" col="8"
                                    value="${entrustApply.transferStaffCode}" text="${entrustApply.transferStaffName}" propText="staffName" propValue="staffCode"
                                    callback="result"  onSelect="transferStaffChange" mult="false" placeholder="被委托人" disabled="${opt == 'view'}">
                    </bf:staffLookup>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="被委托人部门" col="4" formControl="true"/>
                    <bf:text id="transferStaffDep" name="transferStaffDep" col="8" value="${entrustApply.transferStaffDep}" disabled="true">
                    </bf:text>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label cls="required" text="开始时间" col="4" formControl="true"/>
                    <bf:datebox id="startTime" name="startTime" col="8" value="${entrustApply.startTime}" disabled="${opt == 'view'}">
                        <bv:notEmpty/>
                        <be:event type="change">
                            var startDate = $("input[name='startTime']").val();
                            var endDate = $("input[name='endTime']").val();
                            if(endDate)
                            {
                            var start= new Date(startDate.replace("-", "/").replace("-", "/"));
                            var end = new Date(endDate.replace("-", "/").replace("-", "/"));
                            if(start > end)
                            {
                            $.messager.toastr('提示', '任务开始日期必须小于任务结束日期。', 'error');
                            }
                            }
                        </be:event>
                    </bf:datebox>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label cls="required" text="结束时间" col="4" formControl="true"/>
                    <bf:datebox id="endTime" name="endTime" col="8" value="${entrustApply.startTime}" disabled="${opt == 'view'}" >
                        <bv:notEmpty/>
                        <be:event type="change">
                            var startDate = $("input[name='startTime']").val();
                            var endDate = $("input[name='endTime']").val();
                            if(startDate)
                            {
                            var start= new Date(startDate.replace("-", "/").replace("-", "/"));
                            var end = new Date(endDate.replace("-", "/").replace("-", "/"));
                            if(start > end)
                            {
                            $.messager.toastr('提示', '任务结束日期必须大于任务开始日期。', 'error');
                            }
                            }
                        </be:event>
                    </bf:datebox>
                </bf:formGroup>
            </bf:formGroup>
        <div id="newEntrustPeople">
            <bf:formGroup hr="dashed" hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="新被委托人名称" col="4" formControl="true"/>
                    <bf:staffLookup id="transferStaffCodeNew" name="transferStaffCodeNew" col="8"
                                    value="${procAutoTransfer.transferStaffCodeNew}" text="${entrustApply.transferStaffNameNew}" propText="staffName" propValue="staffCode"
                                    callback="result" onSelect="transferStaffNewChange"  mult="false" placeholder="被委托人" disabled="${opt == 'view'}">
                    </bf:staffLookup>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="新被委托人部门" col="4" formControl="true"/>
                    <bf:text id="transferStaffDepNew" name="transferStaffDepNew" col="8" value="${entrustApply.transferStaffDepNew}" disabled="true">
                    </bf:text>
                </bf:formGroup>
            </bf:formGroup>
        </div>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label cls="required" text="事项说明" col="4" formControl="true"/>
                    <bf:text id="remark" name="remark" col="8"  multiple="true" rows="5" disabled="${opt == 'view'}">
                        ${entrustApply.remark}
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
            </bf:formGroup>
        </div>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="danger" text="保存" icon="save" >
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            var taskNumber = $('#taskNumber').val();
                            var entrustType = $('#entrustType').val();
                            if(entrustType=='1'&& taskNumber== ''){
                            $.messager.alert('错误', "任务编号不能为空", 'error');
                            return;
                            }
                            var transferStaffCodeNew =  $("input[name='transferStaffCodeNewText']").val();
                            if(entrustType=='1'&& taskNumber== ''){
                            $.messager.alert('错误', "新被委托人不能为空", 'error');
                            return;
                            }
                            var transferStaffName =  $("input[name='transferStaffCodeText']").val();
                            if(transferStaffName== ''){
                            $.messager.alert('错误', "被委托人不能为空", 'error');
                            return;
                            }
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/sys/entrustApply/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/entrustApply/list';
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
                            window.location = '${base}/sys/entrustApply/list';
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
    $(document).ready(function(){
        $("#startTime").datetimepicker("setStartDate",new Date());
        $("#endTime").datetimepicker("setStartDate",new Date());
        var type = $('#entrustType').val();
        if (type == '1') {
            $("#taskCode").show();
            $("#newEntrustPeople").show();
        }
        if (type == '0') {
            $("#taskCode").hide();
            $("#newEntrustPeople").hide();
        }
    });
    function transferStaffChange(data){
        $('#transferStaffDep').val(data[0]['deptName']);
        $('#transferStaffName').val(data[0]['staffName']);
    }
    function transferStaffNewChange(data){
        $('#transferStaffName').val(data[0]['staffName']);
        $('#transferStaffDepNew').val(data[0]['deptName']);
    }
    function staffChange(data){
        $('#staffDep').val(data[0]['deptName']);
        $('#staffName').val(data[0]['staffName']);

    }
    /**
     * 名称弹框
     */
    function forNameSelect() {
        $.messager.modal({
            winId: 'taskNumberSelect',
            width: 700,
            height: 420,
            title: '任务编号查询',
            url: '${base}/pat/taskNumber/list',
            args: {
                result: function (obj) {
                    console.log(obj);
                    $("#taskNumber").val(obj["pid"]);
                    $("input[name='staffCodeText']").val(obj["name"]);
                    $("input[name='transferStaffCodeText']").val(obj["transferStaffName"]);
                    $("#staffDep").val(obj["staffDep"]);
                    $("#transferStaffDep").val(obj["transferStaffDep"]);
                    $("input[name='startTime']").val(obj["startTime"]);
                    $("input[name='endTime']").val(obj["endTime"]);
                }
            },
            onClose: function () {
            }
        });
    }

    function setType(val) {
        if (val == '1') {
            $("#taskCode").show();
            $("#newEntrustPeople").show();
        }
        if (val == '0') {
            $("#taskCode").hide();
            $("#newEntrustPeople").hide();
        }
    }

</script>
</body>
</html>
