<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>自然人新增或编辑</title>
    <x:jsLib/>
    <style>
        .form-group .col-xs-4 {
            margin-top: 0.5%;
        }

        .labels .control-label {
            display: inline-block;
            width: 12%;
            float: left;
        }
    </style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
    <bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${relPsnSuspect.pid}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="姓名" col="5" formControl="true"/>
                    <bf:text id="name" name="name" col="7" value="${relPsnSuspect.name}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="性別" col="5" formControl="true"/>
                    <bf:select id="sex" name="sex" disabled="true" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Sex" value="${relPsnSuspect.sex}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="民族" col="5" formControl="true"/>
                    <bf:select id="race" name="race" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Race" value="${relPsnSuspect.race}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="境内外标识" col="5" formControl="true"/>
                    <bf:select id="domesticOrForeign" name="domesticOrForeign" disabled="true" col="7"
                               dataSource="custom" url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
                               value="${relPsnSuspect.domesticOrForeign}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="证件类型" col="5" formControl="true"/>
                    <bf:select id="identifyType" name="identifyType" col="7" disabled="true" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relPsnSuspect.identifyType}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="证件号码" col="5" formControl="true"/>
                    <bf:text id="identifyNumber" name="identifyNumber" col="7" value="${relPsnSuspect.identifyNumber}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="婚姻状况" col="5" formControl="true"/>
                    <bf:select id="maritalStatus" name="maritalStatus" disabled="true" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-MaritalStatus" value="${relPsnSuspect.maritalStatus}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="出生日期" col="5" formControl="true"/>
                    <bf:datebox id="birthdate" name="birthdate" col="7" format="date"
                                value="${relPsnSuspect.birthdate}">
                    </bf:datebox>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="移动电话" col="5" formControl="true"/>
                    <bf:text id="mobile" name="mobile" col="7" value="${relPsnSuspect.mobile}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="邮箱" col="5" formControl="true"/>
                    <bf:text id="email" name="email" col="7" value="${relPsnSuspect.email}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="机构名称" col="5" formControl="true"/>
                    <bf:select id="mgerOrg" name="mgerOrg" col="7" disabled="true" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relPsnSuspect.mgerOrg}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="认定结果" col="5" formControl="true"/>
                    <bf:select id="isSuspect" name="isSuspect" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-isRel" value="${relPsnSuspect.isSuspect}">
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup type="success">
                <div class="labels">
                    <bc:col col="12">
                        <bc:label text="认定原因" col="2" formControl="true"/>
                        <bf:text id="cognizaReason" name="cognizaReason" col="10" value="${relPsnSuspect.cognizaReason}"
                                 multiple="true" placeholder="此项为必填项">
                            <bv:notEmpty message="请输入认定原因"/>
                        </bf:text>
                    </bc:col>
                </div>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-4">
                    <bc:button type="success" outline="true" text="人工认定" icon="save">
                        <be:event>
                            sub();
                            <%--$('#editForm').data('formValidation').validate();--%>
                            <%--var isValid = $('#editForm').data('formValidation').isValid();--%>
                            <%--if(isValid)--%>
                            <%--{--%>
                            <%--var formData = $('#editForm').serializeAll();--%>
                            <%--$.mask.post('${base}/bus/relPsnSuspect/save', formData, function(data){--%>
                            <%--if(data.success)--%>
                            <%--{--%>
                            <%--sub();--%>
                            <%--}--%>
                            <%--else--%>
                            <%--{--%>
                            <%--$.messager.alert('无法认定', data.msg, 'error');--%>
                            <%--}--%>
                            <%--});--%>
                            <%--}--%>
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/relPsnSuspect/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    function sub() {
        var pid = $("#pid").val();
        var isSuspect = $("#isSuspect").val();

        var dutyOrg = "0201"
        var checkPsn = "brm"
        var name = $("#name").val();
        // var identifyType=$("#identifyType").val();
        var identifyNumber = $("#identifyNumber").val();
        // var domesticOrForeign=$("#domesticOrForeign").val();
        if (name == "" || name == null || name == "undefined") {
            alert("姓名不可以为空！")
            return
        }
        if (identifyNumber == "" || identifyNumber == null || identifyNumber == "undefined") {
            alert("证件号码不可以为空！")
            return
        }

        $.ajax({
            url: "${base}/bus/relPsnSuspect/psnAudit/" + pid,
            type: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                console.log(data.relPsnSuspect);
                if (data != null) {

                    //必须传入id 值
                    var objData = data.relPsnSuspect;
                    var downSuspectList = data.downSuspectList;
                    var upSuspectList = data.upSuspectList;
                    //判断是否设置了关联管理
                    // if (isSuspect=="1") {
                    if (isSuspect) {
                        //认定为关联方，需要校验关系
                        if (upSuspectList != null || upSuspectList != "") {

                         window.location="${base}/bus/relPsnSuspect/savedata/" + pid;
                        }

                    }else {

                        window.location="${base}/bus/relPsnSuspect/list";

                    }
                       /* var myDate = new Date();
                        objData.id = myDate.getTime();
                        objData.processType = "CheckByPsn";
                        // 欠缺是否是关联方
                        objData.cognizaReason = $("#cognizaReason").val().trim();
                        objData.isSuspect = $("#isSuspect").val().trim();
                        data = JSON.stringify(objData);
                        var formData = $.helper.base64Encode(data);
                        // alert(objData)
                        var obj = {
                        procDefCode: 'YZJ_SUSPECT',
                        oldData: '',
                        newData: formData,
                        uploadSessions: '',
                        procInstId: '',
                        // downSuspects:downSuspectList,
                        // upSuspects:upSuspectList
                        }*/
                  /*      $.mask.post('/workflow/process/saveAndStart', obj, function (data) {
                        if (data.success) {
                        $.messager.alert('提示', '提交成功。', 'info', function () {
                        window.location = '/workflow/process/runingList';
                        });
                        } else {
                        $.messager.alert('错误', data.msg, 'error');
                        }
                        });*/
                }
            }
        })
    }


    function rightsInformation() {
        var opt = $("#opt").val();
        var pid = $("#pid").val();
        window.location = '${base}/bus/relPsnSuspect/psnRelOrgList/edit/' + pid + '? ';
    }

    $(document).ready(function () {
        var opt = $('#opt').val();
        if (opt != 'edit') {
            document.getElementById("psnRelInformation").setAttribute("disabled", true);
            document.getElementById("orgRelInformation").setAttribute("disabled", true);
        }
    });
</script>
</body>
</html>
