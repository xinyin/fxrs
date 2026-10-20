<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
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

        .labels .control-label {
            display: inline-block;
            width: 10.5%;
            float: left;
            margin-top: 0.4%;
            text-align: center;
        }

    </style>
    <title>RelOrg</title>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
    <bc:ibox id="relOrgBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${relOrg.pid}"/>
            <bf:hidden id="relId" name="relId" value="${relId}"/>
            <bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
            <bf:hidden id="paFromPid" name="paFromPid" value="${paFromPid}"/>
            <bf:formGroup type="success" hrp="bottom">
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label text="机构名称" col="5" formControl="true"/>
                        <bf:text id="orgName" name="orgName" click="forNameSelect()" col="7" value="${relOrg.orgName}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="证件类型" col="5" formControl="true"/>
                        <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-ORGIdentifyType" value="${relOrg.identifyType}"
                                   disabled="true">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="证件号码" col="5" formControl="true"/>
                        <bf:text id="uniscCode" name="uniscCode" disabled="true" col="7" value="${relOrg.uniscCode}">
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
                                   url="sys/dictItem/getDictItems/BUS-Status" disabled="true" value="${relOrg.status}">
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label cls="required" text="关联关系类别" col="5" formControl="true"/>
                        <bf:select id="relCategory" name="relCategory" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelCategory"
                                   includes="3,4" value="${relOrg.relCategory}">
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
                                   url="sys/dictItem/getDictItems/BUS-Rel"
                                   value="${relOrg.relCategorySub}">
                            <bf:option text="请选择"></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="该关系是否有效" col="5" formControl="true"/>
                        <bf:select id="isValid" name="isValid" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-valid"
                                   value="${relOrg.isValid}" disabled="false">
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">

                    <bc:col col="4">
                        <bc:label cls="required" text="生效日期" col="5" formControl="true"/>
                        <bf:datebox name="activeTimeString" id="activeTimeString" col="7" format="date"
                                    value="${relOrg.activeTimeString}">
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
                </bf:formGroup>
                <bf:formGroup type="success">
                    <div id="holdingRatio">
                        <bc:col col="4">
                            <bc:label cls="required" text="持有比例(%)" col="5" formControl="true"/>
                            <bf:text id="shareholdingRatio" name="shareholdingRatio" col="7"
                                     value="${relOrg.shareholdingRatio}" disabled="false">
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                    </div>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <div class="labels">
                        <bc:col col="12">
                            <bc:label cls="required" text="关联关系说明" col="5" formControl="true"/>
                            <bf:text id="relStatement" name="relStatement" col="5" value="${relOrg.relStatement}" disabled="false" multiple="true"><bv:stringLength max="200" message="请将字数控制在200以内！"/><bv:notEmpty/></bf:text>
                        </bc:col>
                    </div>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                    <bc:button type="primary" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            var paFromPid=$('#paFromPid').val();
                            $.mask.post('${base}/bus/relOrgOfPro/saveOrgOfPro/'+paFromPid+'/${fromPid}', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            <%--var opt = $("#opt").val();--%>
                            window.location = '${base}/bus/relPro/orgOfProList/'+${fromPid}+'?op=0';
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

    /*
        * 关联关系类别联动
        * */
    function setRelCategorySub(value) {
        if (value == '' || value == undefined) {
            $("#relCategorySub").empty();
            return;
        }
        $.ajax({
            type: 'GET',
            url: '${base}/bus/relOrg/queryRelCategorySub?relCategory=' + value + '&type=t',
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

    /**
     * 名称弹框
     */
    //click="forNameSelect()"
    function forNameSelect() {
        debugger
        var type = 'org';
        $.messager.modal({
            winId: 'forNameSelect',
            width: 700,
            height: 420,
            title: '关联方信息查询',
            url: '${base}/bus/rel/forNameSelect/' + type,
            args: {
                result: function (obj) {
                    $("#paFromPid").val(obj["pid"]);
                    $("#orgName").val(obj["name"]);
                    $("#identifyType").val(obj["orgType"]);
                    $('#identifyType').select2({value: (obj["orgType"])});
                    $("#uniscCode").val(obj["number"]);
                    $("#domesticOrForeign").val(obj["domesticOrForeign"]);
                    $('#domesticOrForeign').select2({value: (obj["domesticOrForeign"])});
                    $("#legalPerson").val(obj["legalRepresentative"]);
                    $("#status").val(obj["orgStatus"]);
                    $('#status').select2({value: (obj["orgStatus"])});
                }
            },
            onClose: function () {
            }
        });
    }
</script>
</body>
</html>
