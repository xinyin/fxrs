<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <br:css pack="form"/>
    <title>疑似机构-关系自然人详情</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="relPsnSuspectBox" title="" subTitle="" col="12" collapsible="false">
            <bf:form id="editForm" method="post" validation="true">
                <bf:hidden id="opt" name="opt" value="${opt}"/>
                <%--源pid--%>
                <bf:hidden id="fromPid" name="fromPid" value="${relPsnSuspect.fromPid}"/>
                <%--关联关系所属pid--%>
                <bf:hidden id="relPid" name="relPid" value="${relPsnSuspect.relPid}"/>
                <%--机构所属pid--%>
                <bf:hidden id="toPid" name="toPid" value="${relPsnSuspect.toPid}"/>

                <bf:hidden id="savefromPid" name="savefromPid" value="${savefromPid}"/>

                <div class="boxse">
                    <bf:formGroup type="success" hrp="bottom">
                        <bc:col col="4">
                            <bc:label text="关联方名称" col="5" formControl="true"/>
                            <bf:text id="name" name="name" col="7" value="${relPsnSuspect.name}" disabled="true">
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="证件类型" col="5" formControl="true"/>
                            <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                                       value="IDCARD" disabled="true" url="sys/dictItem/getDictItems/BUS-IdentifyType">
                            </bf:select>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="证件号码" col="5" formControl="true"/>
                            <bf:text id="identifyNumber" name="identifyNumber" col="7" value="${relPsnSuspect.identifyNumber}" disabled="true">
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="境内外标识" col="5" formControl="true"/>
                            <bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
                                       value="${relPsnSuspect.domesticOrForeign}" disabled="true">
                            </bf:select>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="出生日期" col="5" formControl="true"/>
                            <bf:datebox name="birthdate" id="birthdate" col="7" format="date" value="${relPsnSuspect.birthdate}" disabled="true">
                            </bf:datebox>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="居住地址" col="5" formControl="true"/>
                            <bf:text id="residentialAddress" name="residentialAddress" col="7"
                                     value="${relPsnSuspect.residentialAddress}" disabled="true">
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="关联关系类别" col="5" formControl="true"/>
                            <bf:select id="relCategory" name="relCategory" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-RelCategory"
                                       includes="B,C,E" value="${relPsnSuspect.relCategory}" disabled="true">
                                <be:event type="select">
                                    setRelCategorySub(e.target.value);
                                </be:event>
                            </bf:select>
                        </bc:col>
                        <bc:col col="4">
                            <bc:label text="关系" col="5" formControl="true"/>
                            <bf:select id="relCategorySub" name="relCategorySub" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Rel" value="${relPsnSuspect.relCategorySub}" disabled="true">
                            </bf:select>
                        </bc:col>
                        <div id="shareholdingRatio">
                            <bc:col col="4">
                                <bc:label text="持股比例或表决权" col="5" formControl="true"/>
                                <bf:text id="shareholdingProportion" name="shareholdingProportion" col="7"
                                         value="${relPsnSuspect.shareholdingProportion}">
                                </bf:text>
                            </bc:col>
                        </div>
                    </bf:formGroup>
                </div>
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
    var relCategory = '${relPsnSuspect.relCategory}';
    $(document).ready(function(){
        //初始化内容脚本
        if(relCategory==''){
            relCategory='A';
        }
        setRelCategorySub(relCategory);
    });

    function setRelCategorySub(value){
        if (value == 'C') {
            $('#shareholdingRatio').show();
        }else {
            $('#shareholdingRatio').hide();
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
    $("#identifyNumber").blur(function () {
        var name = $("#name").val();
        var identifyType = $("#identifyType").val();
        var identifyNumber = $(this).val();
        if (identifyNumber != '' && identifyNumber != undefined) {
            $.ajax({
                type: 'GET',
                url: '${base}/bus/relPsn/selectRelPsn',
                data: {
                    "name": name,
                    "identifyType": identifyType,
                    "identifyNumber": identifyNumber
                },
                dataType: 'json',
                success: function (data) {
                    $("#identifyNumber").empty();
                    if (data.length > 0) {
                        $("#savefromPid").val(data[0].pid);
                        $("#name").val(data[0].name);
                        $("#domesticOrForeign").val(data[0].domesticOrForeign);
                        $("[name='birthdate']").val(data[0].birthdate);
                        $("#residentialAddress").val(data[0].residentialAddress);
                        $("#shareholdingProportion").val(data[0].shareholdingProportion);
                        $("#voteProportion").val(data[0].voteProportion);
                    }else {
                        $.messager.alert('提示', '归属关联方不存在，请重新输入', 'warning', function () {
                            $("#identifyNumber").empty();
                        });
                    }
                }
            });
        }

    });
    <%--$(document).ready(function () {--%>
    <%--var opt = $('#opt').val();--%>
    <%--var relCategory = '${relPsn.relCategory}';--%>
    <%--if (opt == 'edit') {--%>

    <%--if (relCategory == 'B') {--%>
    <%--$('#relCategorySubdiv1').show();--%>
    <%--$('#relCategorySubdiv2').hide();--%>
    <%--$('#shareholdingRatio').hide();--%>
    <%--}--%>
    <%--if (relCategory == 'C') {--%>
    <%--$('#relCategorySubdiv1').hide();--%>
    <%--$('#relCategorySubdiv2').show();--%>
    <%--$('#shareholdingRatio').show();--%>
    <%--}--%>
    <%--}else{--%>
    <%--// $('#relCategorySubdiv1').hide();--%>
    <%--$('#relCategorySubdiv2').hide();--%>
    <%--$('#shareholdingRatio').hide();--%>
    <%--}--%>
    <%--});--%>
    function funName(val) {
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
        if (val == 'A') {
            $('#relCategorySubdiv1').hide();
            $('#relCategorySubdiv2').hide();
            $('#shareholdingRatio').hide();
        }
        // }

    }
</script>
</body>
</html>