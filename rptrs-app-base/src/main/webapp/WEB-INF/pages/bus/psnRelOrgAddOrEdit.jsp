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
            <bf:hidden id="paFromPid" name="paFromPid" value="${paFromPid}"/>
            <div class="boxse">


                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label text="关联方名称" col="5" formControl="true"/>
                        <bf:text id="orgName" name="orgName" col="7" click="forNameSelect()" value="${relOrg.orgName}">
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
                            <bf:option text="请选择" value=""></bf:option>
                            <be:event type="select">
                                setRelCategorySub(e.target.value);
                            </be:event>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="关系" col="5" formControl="true"/>
                        <bf:select id="relCategorySub" name="relCategorySub" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Rel" value="${relOrg.relCategorySub}">
                            <bf:option text="请先选择关联关系类别" value=""></bf:option>
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
                                    value="${relOrg.activeTimeString}" disabled="ture">
                            <bv:notEmpty/>
                        </bf:datebox>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="失效日期" col="5" formControl="true"/>
                        <bf:datebox name="invalidTimeString" id="invalidTimeString" col="7" format="date"
                                    value="${relOrg.invalidTimeString}" disabled="ture">
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
                                     value="${relPsnVo.shareholdingRatio}" disabled="false">
                                <bv:between min="0" max="100" inclusive="true" message="必须为0-100的数字"/>
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                    </div>
                    <bc:col col="4">
                        <bc:label text="质押股权比例(%)" col="5" formControl="true"/>
                        <bf:text id="pledgeRatio" name="pledgeRatio" col="7"
                                 value="${relOrg.pledgeRatio}" disabled="fasle">
                            <bv:between min="0" max="100" inclusive="true" message="必须为0-100的数字"/>
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label cls="required" text="关系说明" col="3" formControl="true"/>
                    <bf:text id="relStatement" name="relStatement" col="9" rows="5" multiple="true"
                             value="${relOrg.relStatement}"><bv:stringLength max="200"
                                                                             message="请将字数控制在200以内！"/><bv:notEmpty/></bf:text>
                </bf:formGroup>

                <bf:formGroup hidden="true">
                    <bc:col col="4">
                        <bc:label text="注册地" col="5" formControl="true"/><%--regAddress--%>
                        <bf:text id="" name="" col="7" disabled="true" value="${relOrg.regAddress}">
                        </bf:text>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="机构名称" col="5" formControl="true"/>
                        <bf:select id="" name="" col="7" dataSource="custom"
                                   url="sys/unit/selectUnit" disabled="true" value="${relOrg.mgerOrg}"><%--mgerOrg--%>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="企业类型" col="5" formControl="true"/>
                        <bf:select id="" name="" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-CompanyType" disabled="true"
                                   value="${relOrg.orgType}"><%--orgType--%>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="营业范围" col="5" formControl="true"/>
                        <bf:text id="" name="" col="7" disabled="true"
                                 value="${relOrg.businessScope}"><%--businessScope--%>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="注册资本(万元/人民币)" col="5" formControl="true"/>
                        <bf:text id="" name="" col="7" disabled="true" value="${relOrg.regCapital}"><%--regCapital--%>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="注册资本币种代码" col="5" formControl="true"/>
                        <bf:select id="" name="" disabled="true" col="7"
                                   dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Currency"
                                   value="${relOrg.regCapitalCurrency}"><%--regCapitalCurrency--%>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="关联关系判定" col="5" formControl="true"/>
                        <bf:select id="predicate" name="predicate" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS_Predicate"
                                   value="${RelOrgVo.predicate}"><%--predicate--%>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="实体生效日期" col="5" formControl="true"/>
                        <bf:datebox name="effectiveDate" id="effectiveDate" col="7" format="date"
                                    value="${relOrgVo.effectiveDate}">
                        </bf:datebox>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="实体失效日期" col="5" formControl="true"/>
                        <bf:datebox name="ineffectiveDate" id="ineffectiveDate" col="7" format="date"
                                    value="${relOrgVo.ineffectiveDate}">
                        </bf:datebox>
                    </bc:col>
                </bf:formGroup>

            </div>
            <div id="voteProportion" class="voteProportion">
                <bc:col col="4">
                    <bc:label text="表决权占比：" col="5" formControl="true"/>
                    <bf:text id="voteProportion" name="voteProportion" col="7"
                             value="${relPsnVo.voteProportion}" disabled="ture">
                    </bf:text>
                </bc:col>
            </div>
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


    var relCategory = '${relOrg.relCategory}';
    $(document).ready(function () {
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
    });

    var relCategory = '${relOrg.relCategory}';
    var relCategorySubVal = '${relOrg.relCategorySub}';
    $(document).ready(function () {
        if (relCategorySub != '' || relCategorySub != undefined) {
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
        if (value == '' || value == undefined) {
            $("#relCategorySub").empty();
            return;
        }
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
