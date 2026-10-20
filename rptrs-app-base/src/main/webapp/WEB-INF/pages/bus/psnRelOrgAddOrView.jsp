<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>RelOrg</title>
    <style>
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

        .has-success .select2-dropdown, .has-success .select2-selection {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }

        .voteProportion {
            display: none;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="relOrgBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${relOrg.pid}"/>
            <bf:hidden id="relId" name="relId" value="${relId}"/>
            <bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
            <bf:hidden id="paFromPid" name="paFromPid" value="${relOrg.pid}"/>
            <div class="boxse">

                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label text="关联方名称" col="5" formControl="true"/>
                        <bf:text id="orgName" name="orgName" col="7" value="${relOrg.orgName}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="证件类型" col="5" formControl="true"/>
                        <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-ORGIdentifyType" disabled="true"
                                    value="${relOrg.identifyType}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="证件号码" col="5" formControl="true"/>
                        <bf:text id="uniscCode" name="uniscCode" col="7" disabled="true" value="${relOrg.uniscCode}">
                            <bv:stringLength min="8" max="18" trim="true"/>
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label text="境内/境外" col="5" formControl="true"/>
                        <bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" disabled="true"
                                   value="${relOrg.domesticOrForeign}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="法定代表人" col="5" formControl="true"/>
                        <bf:text id="legalPerson" name="legalPerson" col="7" disabled="true"
                                 value="${relOrg.legalPerson}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="机构状态" col="5" formControl="true"/>
                        <bf:select id="status" name="status" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Status" disabled="true"
                                   value="${relOrg.status}">
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label cls="required" text="关联关系类别" col="5" formControl="true"/>
                        <bf:select id="relCategory" name="relCategory" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelCategory"
                                   includes="2,3,5,6" value="${relOrg.relCategory}">
                            <bf:option text="请选择"></bf:option>
                            <bv:notEmpty/>
                            <be:event type="select">
                                setRelCategorySub(e.target.value);
                            </be:event>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="关系" col="5" formControl="true"/>
                        <bf:select id="relCategorySub" name="relCategorySub" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Rel" value="${relOrg.relCategorySub}">
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="该关系是否有效" col="5" formControl="true"/>
                        <bf:select id="isValid" name="isValid" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-valid"
                                   value="${relOrg.isValid}" disabled="false">
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label cls="required" text="生效日期" col="5" formControl="true"/>
                        <bf:datebox name="activeTimeString" id="activeTimeString" col="7" format="date"
                                    value="${relOrg.activeTimeString}">
                            <bv:notEmpty/>
                        </bf:datebox>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="失效日期" col="5" formControl="true"/>
                        <bf:datebox name="invalidTimeString" id="invalidTimeString" col="7" format="date"
                                    value="${relOrg.invalidTimeString}">
                            <bv:callback message="请填写必填字段" callback="setInvalidTime"/>
                        </bf:datebox>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="与前一层是否有权益份额" col="5" formControl="true"/>
                        <bf:select id="equityShare" name="equityShare" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/WhetherToShare"
                                   value="${relOrg.equityShare}" disabled="false">
                            <be:event type="select">
                                setShareholdingRatio(e.target.value);
                            </be:event>
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>
                    <div id="holdingRatio">
                        <bc:col col="4">
                            <bc:label cls="required" text="持有比例(%)" col="5" formControl="true"/>
                            <bf:text id="shareholdingRatio" name="shareholdingRatio" col="7"
                                     value="${relOrg.shareholdingRatio}" disabled="false">
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                    </div>
                    <bc:col col="4">
                        <bc:label text="质押股权比例(%)" col="5" formControl="true"/>
                        <bf:text id="pledgeRatio" name="pledgeRatio" col="7"
                                 value="${relOrg.pledgeRatio}" disabled="fasle">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label cls="required" text="关系说明" col="3" formControl="true"/>
                    <bf:text id="relStatement" name="relStatement" col="9" rows="5" multiple="true" value="${relOrg.relStatement}"><bv:stringLength max="200" message="请将字数控制在200以内！"/><bv:notEmpty/></bf:text>
                </bf:formGroup>
            </div>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                    <bc:button id="x" type="success" outline="true" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            var paFromPid=$('#paFromPid').val();
                            $.mask.post('${base}/bus/relPsn/saveOrg/'+paFromPid+'/${fromPid}', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/bus/relPsn/psnRelOrgList/'+${fromPid}+'?op=0';
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
                            window.history.go(-1);
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
</div>
<br:js pack="form"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script type="text/javascript">


    // setInvalidTime
    function setInvalidTime(value, validator, $field) {
        debugger
        //  isValid invalidTimeString
        var isValid = $("#isValid").val();
        var fieId1 = $field[0].id;
        var value1 = value;
        var investmentType = $('#invalidTimeString').val();
        if (fieId1 == "") {
            if ((isValid == "0") && (value1 == null || value1 == "")) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }


    $(document).ready(function () {
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
    });
    var relCategory = '${relOrg.relCategory}';
    var relCategorySubVal = '${relOrg.relCategorySub}';
    $(document).ready(function () {
        if (relCategorySub != '' && relCategorySub != undefined) {
            setRelCategorySub(relCategory);
        }
    });
    var equityShareval = '${relOrg.equityShare}';
    $(document).ready(function () {
        if (equityShareval != '' && equityShareval != undefined) {
            setShareholdingRatio(equityShareval)
        }
    });
    /*
* 与前一层是否有权益份额联动持有比例
* */
    function setShareholdingRatio(val) {
        if (val == '1' || val == '2') {
            //    shareholdingRatio
            $("#holdingRatio").show()
        }
        if (val == '0') {
            $("#holdingRatio").hide()
        }
    }

    function setRelCategorySub(value) {

        $.ajax({
            type: 'GET',
            url: '${base}/bus/relPsn/queryRelCategorySub?relCategory=' + value + '&type=o',
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
                $("#relCategorySub").empty();
                $("#relCategorySub").val(relCategorySubVal);
                $("#relCategorySub").select2({data: arr, width: "100%"});
                var select2 = $("#relCategorySub").select2();    //获取selectid

                select2.val(relCategorySubVal).trigger("change");
                select2.change();
            }
        });
    }

    $(document).ready(function () {
        var opt = $('#opt').val();
        var value = '${op}';
        // alert(value);
        if (value != "0") {
            $('#isValid').attr('disabled', 'true');//该关系是否有效
            $('#Predicate').css('disabled', 'true');//关联关系判定
            $('#shareholdingRatio').attr('disabled', 'true');//持股所占比例
            $("[name=effectiveDate]").attr("readonly", "readonly");//实体生效日期
            $("[name=ineffectiveDate]").attr("readonly", "readonly");//实体失效日期

            $("[name=activeTime]").attr("readonly", "readonly");//生效日期
            $("[name=invalidTime]").attr("readonly", "readonly");//失效日期
            $("[name=pledgeRatio]").attr('disabled', 'true');//质押股权比例
            $("[name=equityShare]").attr('disabled', 'true');//与前一层是否有权益份额
            $("[name=relStatement]").attr('disabled', 'true');
            $('#x').css('display', 'none');//保存按钮
            Array.prototype.slice.call(document.getElementsByTagName('input')).map(i => i.disabled = true)
            Array.prototype.slice.call(document.getElementsByTagName('select')).map(i => i.disabled = true)
        }

    });


    //输入身份证反显关联方
    $("#uniscCode").blur(function () {
        var orgName = $("#orgName").val();
        var identifyType = $("#identifyType").val();
        var uniscCode = $(this).val();
        if (uniscCode != '' && uniscCode != undefined) {
            $.ajax({
                type: 'GET',
                url: '${base}/bus/relOrg/selectRelOrg',
                data: {
                    "orgName": orgName,
                    "identifyType": identifyType,
                    "uniscCode": uniscCode
                },
                dataType: 'json',
                success: function (data) {
                    if (data.length > 0) {
                        $("#paFromPid").val(data[0].pid);
                        $("#orgName").val(data[0].orgName);
                        $("#regAddress").val(data[0].regAddress);
                        $("#domesticOrForeign").val(data[0].domesticOrForeign);
                        $("#mgerOrg").val(data[0].mgerOrg);
                        $("#orgType").val(data[0].orgType);
                        $("#businessScope").val(data[0].businessScope);
                        $("#regCapital").val(data[0].regCapital);
                        $("#regCapitalCurrency").val(data[0].regCapitalCurrency);
                        $("#legalPerson").val(data[0].legalPerson);
                        $("#status").val(data[0].status);
                        // $("#shareholdingRatio").val(data[0].shareholdingRatio);
                    } else {
                        $.messager.alert('提示', '归属关联方不存在，请重新输入', 'warning', function () {
                            $("#uniscCode").empty();
                        });
                    }
                }
            });
        }

    });
</script>
</body>
</html>
