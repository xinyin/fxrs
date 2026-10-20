<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
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
    <title>金融产品新增或编辑</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="relOrgBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${relPsn.pid}"/>
            <bf:hidden id="relId" name="relId" value="${relId}"/>
            <bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
            <bf:hidden id="paFromPid" name="paFromPid" value="${paFromPid}"/>
            <div class="boxse">
                <bf:formGroup type="success" hrp="bottom">

                    <bf:formGroup type="success">
                        <bc:col col="4">
                            <bc:label cls="required" text="产品名称" col="5" formControl="true"/>
                            <bf:text id="proName" name="proName" click="forNameSelect()" col="7" value="${relProVo.proName}">
                            </bf:text>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label cls="required" text="产品批准/注册/备案号" col="5" formControl="true"/>
                            <bf:text id="proProvider" name="proProvider" col="7" disabled="true" value="${relProVo.proProvider}">
                            </bf:text>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label cls="required" text="产品分类" col="5" formControl="true"/>
                            <bf:select id="proType" name="proType" col="7" dataSource="custom" disabled="true"
                                       url="sys/dictItem/getDictItems/BUS-pro" value="${relProVo.proType}">
                            </bf:select>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup type="success">
                        <bc:col col="4">
                            <bc:label  cls="required" text="关联关系类别" col="5" formControl="true"/>
                            <bf:select id="relCategory" name="relCategory" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-RelCategory"
                                       includes="3,4" value="${relProVo.relCategory}">
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
                                <bv:notEmpty/>
                                <bf:option text="请选择"></bf:option>
                            </bf:select>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label cls="required" text="该关系是否有效" col="5" formControl="true"/>
                            <bf:select id="isValid" name="isValid" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-valid"
                                       value="${relProVo.isValid}" disabled="false">
                            </bf:select>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup type="success">
                        <bc:col col="4">
                            <bc:label cls="required" text="生效日期" col="5" formControl="true"/>
                            <bf:datebox name="activeTimeString" id="activeTimeString" col="7" format="date"
                                        value="${relProVo.activeTimeString}" disabled="ture">
                                <bv:notEmpty/>
                            </bf:datebox>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="失效日期" col="5" formControl="true"/>
                            <bf:datebox name="invalidTimeString" id="invalidTimeString" col="7" format="date"
                                        value="${relProVo.invalidTimeString}" disabled="ture">
                                <bv:callback message="请填写必填字段" callback="setInvalidTime"/>
                            </bf:datebox>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label cls="required" text="与前一层是否有权益份额" col="5" formControl="true"/>
                            <bf:select id="equityShare" name="equityShare" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/WhetherToShare"
                                       value="${relProVo.equityShare}" disabled="false">
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
                                         value="${relProVo.shareholdingRatio}" disabled="false">
                                    <bv:notEmpty/>
                                </bf:text>
                            </bc:col>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="关系说明" col="3" formControl="true"/><bf:text id="relStatement" name="relStatement" col="9" rows="5" multiple="true" value="${relProVo.relStatement}"><bv:stringLength max="200" message="请将字数控制在200以内！"/><bv:notEmpty/></bf:text>
                    </bf:formGroup>
                </bf:formGroup>
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
                            $.mask.post('${base}/bus/relOrg/saveProOfOrg/'+paFromPid+'/${fromPid}', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            <%--var opt = $("#opt").val();--%>
                            window.location = '${base}/bus/relOrg/proOfOrgList/'+${fromPid}+'?op=0';
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
                            <%--var opt = $("#opt").val();--%>
                            var fromPid = $('#fromPid').val();
                            window.location = '${base}/bus/relOrg/proOfOrgList/'+fromPid+'?op=0';
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


    var relCategory = '${relProVo.relCategory}';
    var relCategorySubVal = '${relProVo.relCategorySub}';
    $(document).ready(function () {
        if (relCategorySub != '' && relCategorySub != undefined) {
            setRelCategorySub(relCategory);
        }
    });
    var equityShareval = '${relProVo.equityShare}';
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
        var type = 'pro';
        $.messager.modal({
            winId: 'forNameSelect',
            width: 700,
            height: 420,
            title: '关联方信息查询',
            url: '${base}/bus/rel/forNameSelect/' + type,
            args: {
                result: function (obj) {
                    $("#paFromPid").val(obj["pid"]);
                    $("#proName").val(obj["name"]);
                    $("#proType").val(obj["proType"]);
                    $('#proType').select2({value: (obj["proType"])});
                    $("#proProvider").val(obj["number"]);
                }
            },
            onClose: function () {
            }
        });
    }
</script>


<script type="text/javascript">

    //加上红点
    $(document).ready(function () {
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
    });
</script>
</body>
</html>

