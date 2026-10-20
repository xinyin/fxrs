<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>附属信息</title>
    <script>
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
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="relPsnBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="orgId" name="orgId" value="${orgid}"/>
            <bf:hidden id="relId" name="relId" value="${relId}"/>
            <bf:hidden id="pid" name="pid" value="${pid}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="证件类型" col="4" formControl="true"/>
                    <bf:select id="identifyType" name="identifyType" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relPsn.identifyType}">
                    </bf:select>
                </bc:col>
                <bc:col col="8">
                    <bc:label text="证件号码" col="4" formControl="true"/>
                    <bf:text id="identifyNumber" name="identifyNumber" col="8" value="${relPsn.identifyNumber}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="姓名" col="4" formControl="true"/>
                    <bf:text id="name" name="name" col="8" value="${relPsn.name}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="性別" col="4" formControl="true"/>
                    <bf:select id="sex" name="sex" col="8" dataSource="custom" url="sys/dictItem/getDictItems/BUS-Sex"
                               value="${relPsn.sex}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="机构名称" col="4" formControl="true"/>
                    <bf:select id="mgerOrg" name="mgerOrg" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relPsn.mgerOrg}">
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="民族" col="4" formControl="true"/>
                    <%--<bf:text id="race" name="race" col="8" value="${relPsn.race}">--%>
                    <%--</bf:text>--%>
                    <bf:select id="race" name="race" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Race"
                               value="${relPsn.race}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="境内/境外" col="4" formControl="true"/>
                    <bf:select id="domesticOrForeign" name="domesticOrForeign" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
                               value="${relPsn.domesticOrForeign}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="统计口径" col="4" formControl="true"/>
                    <bf:select id="regulatorOth1" name="regulatorOth1" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-regulator" value="${relPsn.regulatorOth1}">
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="婚姻状况" col="4" formControl="true"/>
                    <bf:select id="maritalStatus" name="maritalStatus" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-MaritalStatus" value="${relPsn.maritalStatus}">
                    </bf:select>
                </bc:col>
                <bc:col col="8">
                    <bc:label text="出生日期" col="4" formControl="true"/>
                    <%--<bf:text id="birthdate" name="birthdate" col="8" value="${relPsn.birthdate}">--%>
                    <%--</bf:text>--%>
                    <bf:datebox name="birthdate" id="birthdate" col="8" format="date" value="${relPsn.birthdate}">
                    </bf:datebox>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="移动电话" col="4" formControl="true"/>
                    <bf:text id="mobile" name="mobile" col="8" value="${relPsn.mobile}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="邮箱" col="4" formControl="true"/>
                    <bf:text id="email" name="email" col="8" value="${relPsn.email}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>

            <bf:formGroup>
                <bc:col col="4">
                    <bc:label text="关联关系类别" col="4" formControl="true"/>

                    <bf:select id="relCategory" name="relCategory" col="8" dataSource="custom"
                               url="bus/relOrg/queryCategorySub/BC" value="${relPsn.relCategory}">
                        <be:event type="select">funName(e.target.value);</be:event>
                    </bf:select>

                    <%--<bf:select id="relCategory" name="relCategory" col="8" dataSource="custom"--%>
                               <%--url="sys/dictItem/getDictItems/BUS-RelCategory" value="${relPsn.relCategory}">--%>
                        <%--<be:event type="select">funName(e.target.value);</be:event>--%>
                    <%--</bf:select>--%>

                    <%--<bf:text id="relCategory" name="relCategory" col="8" value="${relPsn.relCategory}">--%>
                    <%--</bf:text>--%>
                </bc:col>
                <div id="relCategorySubdiv1">
                    <bc:col col="4">
                        <bc:label text="关系" col="4" formControl="true"/>
                        <bf:select id="relCategorySub1" name="relCategorySub" col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelB" value="${relPsn.relCategorySub}" multiple="true">
                        </bf:select>
                        <%--<bf:text id="relCategorySub" name="relCategorySub" col="8" value="${relPsn.relCategorySub}">--%>
                        <%--</bf:text>--%>
                    </bc:col>
                </div>
                <div id="relCategorySubdiv2">
                    <bc:col col="4">
                        <bc:label text="关系" col="4" formControl="true"/>
                        <bf:select id="relCategorySub2" name="relCategorySub" col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelC" value="${relPsn.relCategorySub}">
                        </bf:select>
                        <%--<bf:text id="relCategorySub" name="relCategorySub" col="8" value="${relPsn.relCategorySub}">--%>
                        <%--</bf:text>--%>
                    </bc:col>
                </div>
                <div id="shareholdingRatio">
                    <bc:col col="4">
                        <bc:label text="持股比例或表决权" col="4" formControl="true"/>
                        <%--<bf:select id="relCategorySub" name="relCategorySub" col="8" dataSource="custom"--%>
                        <%--url="sys/dictItem/getDictItems/BUS-DutyCode" value="${relPsn.relCategorySub}">--%>
                        <%--</bf:select>--%>
                        <bf:text id="shareholdingRatio" name="shareholdingRatio" col="8"
                                 value="${relPsn.shareholdingRatio}">
                        </bf:text>
                    </bc:col>
                </div>
            </bf:formGroup>


            <%--<bf:formGroup>--%>
                <%--<bc:col col="4">--%>
                    <%--<bc:label text="关联关系类别" col="4" formControl="true"/>--%>
                    <%--<bf:text id="relCategory" name="relCategory" col="8" value="${relPsn.relCategory}">--%>
                    <%--</bf:text>--%>
                <%--</bc:col>--%>
                <%--<bc:col col="4">--%>
                    <%--<bc:label text="关系" col="4" formControl="true"/>--%>
                    <%--<bf:text id="relCategorySub" name="relCategorySub" col="8" value="${relPsn.relCategorySub}">--%>
                    <%--</bf:text>--%>
                <%--</bc:col>--%>
                <%--<bc:col col="4">--%>
                    <%--<bc:label text="持股比例或表决权" col="4" formControl="true"/>--%>
                    <%--<bf:text id="shareholdingRatio" name="shareholdingRatio" col="8"--%>
                             <%--value="${relPsn.shareholdingRatio}">--%>
                    <%--</bf:text>--%>
                <%--</bc:col>--%>
            <%--</bf:formGroup>--%>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            var relCategory = $('#relCategory').val();
                            if(relCategory == 'A'){
                            $.messager.alert('关联关系类别不能亲属关系');
                            return false;
                            }
                            if($('#relCategorySubdiv1').is(':hidden')){
                            $('#relCategorySub1').empty();
                            }
                            if($('#relCategorySubdiv2').is(':hidden')){
                            $('#relCategorySub2').empty();
                            $('#shareholdingRatio').val('');
                            }
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/bus/rel/saveAffi', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            var orgId = $('#orgId').val();
                            window.location = '${base}/bus/relOrg/listAffi/' + orgId;
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
                    <bc:button type="primary" text="取消" icon="arrow-circle-left">
                        <be:event>
                            var orgId = $('#orgId').val();
                            window.location = '${base}/bus/relOrg/listAffi/' + orgId;
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
    $(document).ready(function () {
        var opt = $('#opt').val();
        if (opt == 'edit') {
            var relCategory = '${relPsn.relCategory}';
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
            $('#relCategorySubdiv1').hide();
            $('#relCategorySubdiv2').hide();
            $('#shareholdingRatio').hide();
        }
    });
</script>
</body>
</html>
