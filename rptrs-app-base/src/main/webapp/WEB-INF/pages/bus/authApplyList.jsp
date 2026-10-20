<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">
        {
            display: none;

        }
    </style>

    <title>权限申请</title>
    <style>
        .form-group select {
            width: 100% !important;
        }

        .form-group .input-group.date,
        .form-group .select2 {
            width: 100% !important;
        }

        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }
    </style>
</head>
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <div title="创建日期">
                            <bc:label cls="required" text="创建日期" col="5" formControl="true"/>
                            <bf:datebox id="__GES_createTime" name="__LIKES_createTime" col="7" value="${entrust.createTime}"
                                        disabled="${opt == 'view'}">
                                <bv:notEmpty/>
                            </bf:datebox>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup hrp="bottom">
                        <bc:col col="4">
                            <bc:label text="申请状态" col="5" formControl="true"/>
                            <bf:select id="status" name="__LIKES_status" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-Applystatus">
                                <bf:option text="ALL" value="%"></bf:option>
                                <be:event type="select">
                                    var input = $("#__LIKES_status");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bc:col>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="applyTable" idField="pid" url="${base}/bus/auth/apply" multiple="false" selectFirst="true">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editEntrust"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeEntrust"/>
                <bc:toolbar id="send" text="提交审批" handler="send" type="button"/>
                <bc:column field="id" title="自增长主键" width="100" visible="false"/>
                <bc:column field="applicant" title="申请人" width="100"/>
                <bc:column field="department" title="所属部门" width="100"/>
                <bc:column field="post" title="工作岗位" width="100" />
                <bc:column field="role" title="申请角色" width="100" />
                <bc:column field="createTime" title="创建时间" width="100"/>
                <bc:column field="status" title="状态" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:operator id="view" icon="search" title="操作/查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    //根据权限显示按钮
    $(function () {
        var code = "${code }";

        //新增
        if (code.search("01,") != -1) {
            $('#applyTable_tb_add').show();
        }
        //编辑
        if (code.search("02,") != -1) {
            $('#applyTable_tb_edit').show();
        }
        //删除
        if (code.search("03,") != -1) {
            $('#applyTable_tb_remove').show();
        }
        //确定
        if (code.search("035,") != -1) {
            $('#applyTable_tb_affirm').show();
        }
        if (code.search("07,") != -1) {
            $('#applyTable_tb_send').show();
        }
    })

    var nextApprovalPeople;

    function editEntrust() {
        var id = getIdSelections();
        if (id == '' || id == null || id.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        var status = getStatusSelections();
        if (status != '3' && status != '5' ) {
            $.messager.alert('提示', '该状态数据不可编辑', 'info');
            return false;
        }
        window.location = '${base}/bus/auth/apply/edit/' + id;
    }


    /*获取状态*/
    function getStatusSelections() {
        return $.map($('#applyTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    /*获取pid*/
    function getIdSelections() {
        return $.map($('#applyTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }



    /*删除*/
    function removeEntrust() {
        var id = getIdSelections();
        var status = getStatusSelections();
        if (id == '' || id == null || id.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (status != '3' && status != '5' ) {
            $.messager.alert('提示', '该状态数据不可删除', 'info');
            return false;
        }
        $.messager.confirm('提示', '确定要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    url: "${base}/bus/auth/apply/remove/" + id,
                    method: "GET",
                    dataType: "JSON",
                    traditional: true,
                    success: function (data) {
                        if (data.success) {
                            window.location = '${base}/bus/auth/apply/list/';
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    }
                })
            }
        })

    }


    /*
  * 审批
  * */
    function send() {
        // 提交审批  的必须是待提交状态
        var status = getStatusSelections();
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        //查询出基本信息 修改状态 放到data中
        $.ajax({
            url: "${base}/bus/auth/apply/send/" + pid,
            method: "GET",
            data: {"": ""},
            dataType: "JSON",
            traditional: true,
            success: function (data) {
                //ZZ_PRO001
                if (data.success) {
                    //开始走工作流
                    var objData = data.data;
                    var myDate = new Date();
                    objData.id = myDate.getTime();
                    //获取备案号
                    var filNum = randomNumber();
                    objData.filNum = filNum;
                    data = JSON.stringify(objData);
                    var formData = $.helper.base64Encode(data);
                    //挂取工作流
                    var obj = {
                        procDefCode: 'ZZ_AuthApproval',
                        oldData: '',
                        newData: formData,
                        uploadSessions: '',
                        procInstId: ''
                    }

                    $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
                        if (data.success) {
                            $.messager.alert('提示', '提交成功。', 'info', function () {
                                window.location = '${base}/bus/auth/apply/list';
                            });
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        })
    }


    /*根据当前时间和随机数生成备案号*/
    function randomNumber() {
        var now = new Date();
        var year = now.getFullYear().toString();
        var month = now.getMonth() + 1;
        var day = now.getDate();
        var hour = now.getHours();
        var minutes = now.getMinutes();
        var seconds = now.getSeconds();
        var no = year + month + day + hour + minutes + seconds;
        return no + (Math.round(Math.random() * 89 + 100)).toString();
    }
    //刷新表格
    function loadCallback() {
        $('#applyTable').jTable('refresh');
    }

    //重置
    function frmReset() {
        window.location = '${base}/bus/auth/apply/list';
    }
</script>
</body>
</html>