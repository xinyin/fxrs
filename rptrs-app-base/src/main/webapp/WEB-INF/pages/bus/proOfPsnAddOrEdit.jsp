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
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label text="产品名称" col="5" formControl="true"/>
                        <bf:text id="proName" name="proName" col="7" value="${relProVo.proName}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="产品批准/注册/备案号" col="5" formControl="true"/>
                        <bf:text id="proProvider" name="proProvider" col="7" value="${relProVo.proProvider}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label  text="关联关系类别" col="5" formControl="true"/>
                        <bf:select id="relCategory" name="relCategory" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelCategory"
                                   includes="3" value="${relProVo.relCategory}">
                            <be:event type="select">
                                setRelCategorySub(e.target.value);
                            </be:event>
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label  text="关系" col="5" formControl="true"/>
                        <bf:select id="relCategorySub" name="relCategorySub" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Rel"
                                   value="${relOrg.relCategorySub}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="该关系是否有效" col="5" formControl="true"/>
                        <bf:select id="isValid" name="isValid" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-valid"
                                   value="${relProVo.isValid}" disabled="false">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="生效日期" col="5" formControl="true"/>
                        <bf:datebox name="activeTime" id="activeTime" col="7" format="date" value="${relOrg.activeTime}"
                                    disabled="ture">
                        </bf:datebox>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label text="失效日期" col="5" formControl="true"/>
                        <bf:datebox name="invalidTime" id="invalidTime" col="7" format="date"
                                    value="${relOrg.invalidTime}" disabled="ture">
                        </bf:datebox>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="与前一层是否有权益份额" col="5" formControl="true"/>
                        <bf:select id="equityShare" name="equityShare" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/WhetherToShare"
                                   value="C" disabled="false">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label  text="持有比例(%)" col="5" formControl="true"/>
                        <bf:text id="shareholdingRatio" name="shareholdingRatio" col="7"
                                 value="${relProVo.shareholdingRatio}">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label cls="required" text="关系说明" col="3" formControl="true"/>
                    <bf:text id="relStatement" name="relStatement" col="9" rows="5" multiple="true" value="${relProVo.relStatement}"><bv:stringLength max="200" message="请将字数控制在200以内！"/><bv:notEmpty/></bf:text>
                </bf:formGroup>

                <bf:formGroup type="success" hidden="true">
                    <bc:col col="4">
                        <bc:label  text="实体生效日期" col="5" formControl="true"/>
                        <bf:datebox name="effectiveDate" id="effectiveDate" col="7" format="date"
                                    value="${relProVo.effectiveDate}">
                        </bf:datebox>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="实体失效日期" col="5" formControl="true"/>
                        <bf:datebox name="ineffectiveDate" id="ineffectiveDate" col="7" format="date"
                                    value="${relProVo.ineffectiveDate}">
                        </bf:datebox>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="质押股权比例" col="5" formControl="true"/><%--pledgeRatio--%>
                        <bf:text id="" name="" col="7"
                                 value="${relPsnVo.pledgeRatio}" disabled="fasle">
                        </bf:text>
                    </bc:col>
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
                            $.mask.post('${base}/bus/relPsn/saveProOfPsn/'+paFromPid+'/${fromPid}', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            <%--var opt = $("#opt").val();--%>
                            window.location = '${base}/bus/relPsn/proOfPsnList/'+${fromPid}+'?op=0';
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
                            window.location = '${base}/bus/relPsn/proOfPsnList/'+fromPid+'?op=0';
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
    var relCategory = '${relProVo.relCategory}';
    $(document).ready(function () {
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
        //初始化内容脚本
        if (relCategory == '') {
            relCategory = '3';
        }
        setRelCategorySub(relCategory);
    });

    function setRelCategorySub(value) {
        $.ajax({
            type: 'GET',
            url: '${base}/bus/relOrg/queryRelCategorySub?relCategory=' + value + '&type=r',
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
                $("#relCategorySub").select2({data: arr, width: "100%"});
            }
        });
    }

    //输入身份证反显关联方
    $("#proProvider").blur(function () {
        var proName = $("#proName").val();
        // var identifyType = $("#identifyType").val();
        var proProvider = $(this).val();
        if (proProvider != '' && proProvider != undefined) {
            $.ajax({
                type: 'GET',
                url: '${base}/bus/relPsn/selectRelProOfPsn',
                data: {
                    "proName": proName,
                    "proProvider": proProvider
                },
                dataType: 'json',
                success: function (data) {
                    // alert(data.stringify());
                    if (data.length > 0) {
                        $("#paFromPid").val(data[0].pid);
                        $("#proName").val(data[0].proName);
                        $("#relCategory").val(data[0].relCategory);
                        $("#relCategorySub").val(data[0].relCategorySub);
                        $("#shareholdingRatio").val(data[0].shareholdingRatio);
                        $("#isValid").val(data[0].isValid);
                        $("#effectiveDate").val(data[0].effectiveDate);
                        $("#ineffectiveDate").val(data[0].ineffectiveDate);
                        $("#relStatement").val(data[0].relStatement);
                        // $("#regCapitalCurrency").val(data[0].regCapitalCurrency);
                        // $("#legalPerson").val(data[0].legalPerson);
                        // $("#status").val(data[0].status);
                        // $("#shareholdingRatio").val(data[0].shareholdingRatio);
                    } else {
                        $.messager.alert('提示', '归属关联方不存在，请重新输入', 'warning', function () {
                            $("#proProvider").empty();
                        });
                    }
                }
            });
        }

    });
    /*function funName(val) {
        var opt = $('#opt').val();
        //if (opt != 'edit') {
        if (val == 'B') {
            $('#relCategorySubdiv1').show();
            $('#relCategorySubdiv2').hide();
            $('#shareholdingRatio').hide();
        }
        if (val == 'C') {
            $('#relCategorySubdiv1').hide();
            $('#relCategorySubdiv2').show();
            $('#shareholdingRatio').show();
        }

    }*/
    /*$(document).ready(function () {
        var opt = $('#opt').val();
        var relCategory = '${relOrg.relCategory}';
        if (opt == 'edit') {

            if (relCategory == 'B') {
                $('#relCategorySubdiv1').show();
                $('#relCategorySubdiv2').hide();
                $('#shareholdingRatio').hide();
            }
            if (relCategory == 'C') {
                $('#relCategorySubdiv1').hide();
                $('#relCategorySubdiv2').show();
                $('#shareholdingRatio').show();
            }
        }else{
            // $('#relCategorySubdiv1').hide();
            $('#relCategorySubdiv2').hide();
            $('#shareholdingRatio').hide();
        }
    });*/
</script>
</body>
</html>

