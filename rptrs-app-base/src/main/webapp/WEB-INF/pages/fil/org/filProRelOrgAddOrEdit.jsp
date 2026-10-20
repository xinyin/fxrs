<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>RelOrg</title>
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
            <bf:formGroup type="success" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="机构名称" col="5" formControl="true"/>
                    <bf:text id="orgName" name="orgName" col="7" value="${relOrg.orgName}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="证件类型" col="5" formControl="true"/>
                    <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-IdentifyType"
                               includes="UNIFIEDSOCIALCREDITCODE" value="${relOrg.identifyType}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="证件号码" col="5" formControl="true"/>
                    <bf:text id="uniscCode" name="uniscCode" col="7" value="${relOrg.uniscCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="注册地" col="5" formControl="true"/>
                    <bf:text id="regAddress" name="regAddress" col="7" disabled="true" value="${relOrg.regAddress}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="境内/境外" col="5" formControl="true"/>
                    <bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" disabled="true" value="${relOrg.domesticOrForeign}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="机构名称" col="5" formControl="true"/>
                    <bf:select id="mgerOrg" name="mgerOrg" col="7" dataSource="custom"
                               url="sys/unit/selectUnit" disabled="true" value="${relOrg.mgerOrg}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="企业类型" col="5" formControl="true"/>
                    <bf:select id="orgType" name="orgType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-CompanyType" disabled="true" value="${relOrg.orgType}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="营业范围" col="5" formControl="true"/>
                    <bf:text id="businessScope" name="businessScope" col="7" disabled="true" value="${relOrg.businessScope}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="注册资本(万元)" col="5" formControl="true"/>
                    <bf:text id="regCapital" name="regCapital" col="7" disabled="true" value="${relOrg.regCapital}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="注册资本币种代码" col="5" formControl="true"/>
                    <bf:select id="regCapitalCurrency" name="regCapitalCurrency" disabled="true" col="7"
                               dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Currency"
                               value="${relOrg.regCapitalCurrency}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="法定代表人" col="5" formControl="true"/>
                    <bf:text id="legalPerson" name="legalPerson" col="7" disabled="true" value="${relOrg.legalPerson}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="机构状态" col="5" formControl="true"/>
                    <bf:select id="status" name="status" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Status" disabled="true" value="${relOrg.status}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="关联关系类别" col="5" formControl="true"/>
                    <bf:select id="relCategory" name="relCategory" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-RelCategory"
                               includes="C,D" value="${relOrg.relCategory}">
                        <be:event type="select">
                            setRelCategorySub(e.target.value);
                        </be:event>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="关系" col="5" formControl="true"/>
                    <bf:select id="relCategorySub" name="relCategorySub" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Rel" includes="24,25,26,27,28,29,30,31" value="${relOrg.relCategorySub}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="该关系是否有效" col="5" formControl="true"/>
                    <bf:select id="isValid" name="isValid" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-valid"
                               value="${relOrg.isValid}" disabled="false">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="生效日期" col="5" formControl="true"/>
                    <bf:datebox name="effectiveDate" id="effectiveDate" col="7" format="date" value="${relOrg.effectiveDate}">
                    </bf:datebox>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="失效日期" col="5" formControl="true"/>
                    <bf:datebox name="ineffectiveDate" id="ineffectiveDate" col="7" format="date" value="${relOrg.ineffectiveDate}">
                    </bf:datebox>
                </bc:col>
                <div id="shareholdingRatio1">
                    <bc:col col="4">
                        <bc:label text="持股比例或表决权(%)" col="5" formControl="true"/>
                        <%--<bf:select id="relCategorySub" name="relCategorySub" col="8" dataSource="custom"--%>
                        <%--url="sys/dictItem/getDictItems/BUS-DutyCode" value="${relPsn.relCategorySub}">--%>
                        <%--</bf:select>--%>
                        <bf:text id="shareholdingRatio" name="shareholdingRatio" col="7"
                                 value="${relOrg.shareholdingRatio}">
                        </bf:text>
                    </bc:col>
                </div>


                <bc:col col="12">
                    <bc:label text="关联关系说明" col="2" formControl="true"/>
                    <bf:text id="relStatement" name="relStatement" col="10"  value="${relOrg.relStatement}" disabled="false" multiple="true">
                    </bf:text>
                </bc:col>
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
                            var relId=$('#relId').val();

                            $.mask.post('${base}/fil/relOrgOfPro/saveOrgOfPros/'+paFromPid+'/'+relId+'/${fromPid}', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            <%--var opt = $("#opt").val();--%>
                            window.location = '${base}/fil/relPro/orgOfProList/'+${fromPid}+'?op=0';
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
                            window.location = '${base}/fil/relPro/orgOfProList/'+fromPid+'?op=0';
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
    var relCategory = '${relPsnVo.relCategory}';
    $(document).ready(function(){
        //初始化内容脚本
        if(relCategory==''){
            relCategory='B';
        }
        setRelCategorySub(relCategory);
    });
    function setRelCategorySub(value){
        if (value == 'C') {
            $('#shareholdingRatio1').show();
        }else {
            $('#shareholdingRatio1').hide();
        }
        $.ajax({
            type: 'GET',
            url: '${base}/fil/relPsn/queryRelCategorySub?relCategory=' + value+'&type=o',
            dataType: 'json',
            delay: 200,
            success: function(data){

                var arr = new Array();
                $.each(data, function(i, val){
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
    $("#uniscCode").blur(function () {
        var orgName = $("#orgName").val();
        var identifyType = $("#identifyType").val();
        var uniscCode = $(this).val();
        if (uniscCode != '' && uniscCode != undefined) {
            $.ajax({
                type: 'GET',
                url: '${base}/fil/relOrg/selectRelOrg',
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
                        $('#mgerOrg').select2({value:(data[0].mgerOrg)});

                        $("#orgType").val(data[0].orgType);
                        $('#orgType').select2({value:(data[0].orgType)});

                        $("#businessScope").val(data[0].businessScope);
                        $("#regCapital").val(data[0].regCapital);

                        $("#regCapitalCurrency").val(data[0].regCapitalCurrency);
                        $('#regCapitalCurrency').select2({value:(data[0].regCapitalCurrency)});

                        $("#legalPerson").val(data[0].legalPerson);
                        $("#status").val(data[0].status);
                        // $("#shareholdingRatio").val(data[0].shareholdingRatio);
                    }else {
                        $.messager.alert('提示', '归属关联方不存在，请重新输入', 'warning', function () {
                            $("#uniscCode").empty();
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
