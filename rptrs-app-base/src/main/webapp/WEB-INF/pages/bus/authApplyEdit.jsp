<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <x:jsLib/>
    <br:css pack="form"/>
    <title>权限申请新增或编辑</title>
    <x:jsLib/>

    <style>
        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .has-success .select2-dropdown, .has-success .select2-selection {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }

        .MainGd {
            display: none;
        }

        .voteProportion {
            display: none;
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

        .form-group {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
            width: 10%;
        }

        .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }
    </style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
    <bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="pid" name="pid" value="${authApply.pid}"/>
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <div class="boxse">
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label cls="required" text="申请人" col="5" formControl="true"/>
                        <bf:text id="applicant" name="applicant" col="7" click="forNameSelect()" value="${authApply.applicant}"
                                 disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="所属部门" col="5" formControl="true"/>
                        <bf:text id="department" name="department" col="7" disabled="true" value="${authApply.department}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">

                    <bf:formGroup col="4">
                        <bc:label  text="工作岗位" col="5" formControl="true"/>
                        <bf:text name="post" id="post" col="7" value="${authApply.post}" disabled="${opt == 'view'}">
                        </bf:text>
                    </bf:formGroup>

                    <bf:formGroup col="4">
                        <bc:label cls="required" text="申请角色" col="5" formControl="true"/>
                        <bf:text name="role" id="role" col="7" click="roleSelect()"  value="${authApply.role}" disabled="${opt == 'view'}">
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:col col="8">
                        <bc:label text="申请权限主要描述" col="3" formControl="true"/>
                        <bf:text id="remark" name="remark" col="9"  multiple="true" rows="5" disabled="${opt == 'view'}">${procAutoTransfer.remark}</bf:text>
                    </bc:col>
                </bf:formGroup>
            </div>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            var docInnerPids = getAllIdSelections();
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            var role =  $("#role").val();
                            if("" ==role){
                            $.messager.alert('错误', "角色信息不能为空", 'error');
                            return;
                            }
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            var docTableSelectItem = {
                            'name':'docTableSelectItem',
                            'value':docInnerPids
                            };
                            formData.push(docTableSelectItem);
                            $.mask.post('${base}/bus/auth/apply/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.confirm('提示', data.msg, function(r){
                            if(r)
                            {
                            window.location = '${base}/bus/auth/apply/list';
                            }
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
                            window.location = '${base}/bus/auth/apply/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
</div>
<div>
    <br:js pack="form"/>
    <be:processEvent/>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".required").each(function () {
                var $this = $(this);
                $(this).html("<font color='red'>✶</font>" + $this.html());
            });
        });

        /**
         * 名称弹框
         */
        function forNameSelect() {
            $.messager.modal({
                winId: 'forNameSelect',
                width: 700,
                height: 420,
                title: '用户信息查询',
                url: '${base}/bus/auth/apply/applicantSelect',
                args: {
                    result: function (obj) {
                        $("#applicant").val(obj["name"]);
                        $("#department").val(obj["deptName"]);
                    }
                },
                onClose: function () {
                }
            });
        }

        /**
         * 角色信息弹框
         */
        function roleSelect() {
            $.messager.modal({
                winId: 'forNameSelect',
                width: 700,
                height: 420,
                title: '角色信息查询',
                url: '${base}/bus/auth/apply/roleSelect',
                args: {
                    result: function (obj) {
                        $("#role").val(obj["name"]);
                    }
                },
                onClose: function () {
                }
            });
        }
        function getAllIdSelections() {
            return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
                return row.pid//返回数据行中的id值
            });
        }

    </script>
</body>
</html>