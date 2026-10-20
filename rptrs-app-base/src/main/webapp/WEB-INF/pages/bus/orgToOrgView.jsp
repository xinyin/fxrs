<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <br:css pack="form"/>
    <title>疑似机构-机构关系新增或编辑</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="relOrgSuspectBox" title="" subTitle="" col="12" collapsible="false">
            <bf:form id="editForm" method="post" validation="true">
                <bf:hidden id="opt" name="opt" value="${opt}"/>
                <%--源pid--%>
                <bf:hidden id="fromPid" name="fromPid" value="${relOrgSuspect.fromPid}"/>
                <%--关联关系所属pid--%>
                <bf:hidden id="relPid" name="relPid" value="${relOrgSuspect.relPid}"/>
                <%--机构所属pid--%>
                <bf:hidden id="toPid" name="toPid" value="${relOrgSuspect.toPid}"/>
                <bf:hidden id="paFromPid" name="paFromPid" value="${paFromPid}"/>

                <bf:formGroup type="success" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="机构名称" col="5" formControl="true"/>
                        <bf:text id="orgName" name="orgName" col="7" value="${relOrgSuspect.orgName}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="证件类型" col="5" formControl="true"/>
                        <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-IdentifyType"
                                   includes="UNIFIEDSOCIALCREDITCODE" value="${relOrgSuspect.identifyType}" disabled="true">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="证件号码" col="5" formControl="true"/>
                        <bf:text id="uniscCode" name="uniscCode" col="7" value="${relOrgSuspect.uniscCode}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="注册地" col="5" formControl="true"/>
                        <bf:text id="regAddress" name="regAddress" col="7" disabled="true" value="${relOrgSuspect.regAddress}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="境内/境外" col="5" formControl="true"/>
                        <bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" disabled="true" value="${relOrgSuspect.domesticOrForeign}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="机构名称" col="5" formControl="true"/>
                        <bf:select id="mgerOrg" name="mgerOrg" col="7" dataSource="custom"
                                   url="sys/unit/selectUnit" disabled="true" value="${relOrgSuspect.mgerOrg}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="企业类型" col="5" formControl="true"/>
                        <bf:select id="orgType" name="orgType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/SUB-BusinessType" disabled="true" value="${relOrgSuspect.orgType}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="营业范围" col="5" formControl="true"/>
                        <bf:text id="businessScope" name="businessScope" col="7" disabled="true" value="${relOrgSuspect.businessScope}">
                        </bf:text>
                    </bc:col>


                    <bc:col col="4">
                        <bc:label text="注册资本(万元/人民币)" col="5" formControl="true"/>
                        <bf:text id="regCapital" name="regCapital" col="7" disabled="true" value="${relOrgSuspect.regCapital}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="注册资本币种代码" col="5" formControl="true"/>
                        <bf:text id="regCapitalCurrency" name="regCapitalCurrency" disabled="true" col="7"
                                 value="${relOrgSuspect.regCapitalCurrency}">
                        </bf:text>
                    </bc:col>


                    <bc:col col="4">
                        <bc:label text="法定代表人" col="5" formControl="true"/>
                        <bf:text id="legalPerson" name="legalPerson" col="7" disabled="true" value="${relOrgSuspect.legalPerson}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="机构状态" col="5" formControl="true"/>
                        <bf:select id="status" name="status" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Status" disabled="true" value="${relOrgSuspect.status}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="关联关系类别" col="5" formControl="true"/>
                        <bf:select id="relCategory" name="relCategory" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-RelCategory"
                                   includes="C,D,E" value="${relOrgSuspect.relCategory}" disabled="true">
                            <be:event type="select">
                                setRelCategorySub(e.target.value);
                            </be:event>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="关系" col="5" formControl="true"/>
                        <bf:select id="relCategorySub" name="relCategorySub" col="7"  disabled="true" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Rel" value="${relOrgSuspect.relCategorySub}">
                        </bf:select>
                    </bc:col>
                    <div id="shareholdingRatio1">
                        <bc:col col="4">
                            <bc:label text="持股比例或表决权" col="5" formControl="true"/>
                            <%--<bf:select id="relCategorySub" name="relCategorySub" col="8" dataSource="custom"--%>
                            <%--url="sys/dictItem/getDictItems/BUS-DutyCode" value="${relPsn.relCategorySub}">--%>
                            <%--</bf:select>--%>
                            <bf:text id="shareholdingRatio" name="shareholdingRatio" col="7"
                                     disabled="true" value="${relOrgSuspect.shareholdingRatio}">
                            </bf:text>
                        </bc:col>
                    </div>
                </bf:formGroup>

                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
<%--                        <bc:button type="success" outline="true"  text="保存" icon="save">--%>
<%--                            <be:event>--%>
<%--                                &lt;%&ndash;if($('#relCategorySubdiv1').is(':hidden')){&ndash;%&gt;--%>
<%--                                &lt;%&ndash;$('#relCategorySub1').empty();&ndash;%&gt;--%>
<%--                                &lt;%&ndash;}&ndash;%&gt;--%>
<%--                                &lt;%&ndash;if($('#relCategorySubdiv2').is(':hidden')){&ndash;%&gt;--%>
<%--                                &lt;%&ndash;$('#relCategorySub2').empty();&ndash;%&gt;--%>
<%--                                &lt;%&ndash;$('#shareholdingRatio').val('');&ndash;%&gt;--%>
<%--                                &lt;%&ndash;}&ndash;%&gt;--%>
<%--                                $('#editForm').data('formValidation').validate();--%>
<%--                                var isValid = $('#editForm').data('formValidation').isValid();--%>
<%--                                if(isValid)--%>
<%--                                {--%>
<%--                                var formData = $('#editForm').serializeAll();--%>
<%--                                var parentPid=$('#savefromPid').val();--%>
<%--                                &lt;%&ndash;                                var fromPid = $('#toPid').val();&ndash;%&gt;--%>
<%--                                &lt;%&ndash;                                alert("......"+fromPid);&ndash;%&gt;--%>
<%--                                $.mask.post('${base}/bus/relOrgSuspect/relPsnSave/'+parentPid+'/${relPsnSuspect.fromPid}', formData, function(data){--%>

<%--                                if(data.success)--%>
<%--                                {--%>
<%--                                $.messager.alert('提示', data.msg, 'info', function(){--%>
<%--                                var opt = $("#opt").val();--%>
<%--                                window.location = '${base}/bus/relOrgSuspect/viewSub/${relPsnSuspect.fromPid}';--%>
<%--                                });--%>
<%--                                }--%>
<%--                                else--%>
<%--                                {--%>
<%--                                $.messager.alert('错误', data.msg, 'error');--%>
<%--                                }--%>
<%--                                });--%>
<%--                                }--%>
<%--                            </be:event>--%>
<%--                        </bc:button>--%>
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                <%--window.location = '${base}/bus/relOrgSuspect/list';--%>
                                <%--var fromPid = $("#fromPid").val();
                                window.location = '${base}/bus/relOrgSuspect/viewSub/'+fromPid;--%>
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
    var relCategory = '${relOrgSuspect.relCategory}';
    $(document).ready(function(){
        //初始化内容脚本
        if(relCategory==''){
            relCategory='A';
        }
        setRelCategorySub(relCategory);
    });

    function setRelCategorySub(value){
        if (value == 'C' || value == 'D') {
            $('#shareholdingRatio1').show();
        }else {
            $('#shareholdingRatio1').hide();
        }
        $.ajax({
            type: 'GET',
            url: '${base}/bus/relPsn/queryRelCategorySub?relCategory=' + value+'&type=p',
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
                }else {
                    $.messager.alert('提示', '归属关联方不存在，请重新输入', 'warning', function () {
                        $("#uniscCode").empty();
                    });
                }
            }
        });
        var orgName = $("#orgName").val();
        var identifyType = $("#identifyType").val();
        var uniscCode = $(this).val();
        if (uniscCode != '' && uniscCode != undefined) {
        }

    });
</script>
</body>
</html>