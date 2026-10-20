<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>权益信息</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="relPsnBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${relPsn.pid}"/>
            <bf:hidden id="relId" name="relId" value="${relid}"/>
            <bf:hidden id="orgId" name="orgId" value="${orgid}"/>
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
                    <bc:label text="机构名称(默认是总行)" col="4" formControl="true"/>
                    <bf:select id="mgerOrg" name="mgerOrg" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relPsn.mgerOrg}">
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="民族" col="4" formControl="true"/>
                    <bf:text id="race" name="race" col="8" value="${relPsn.race}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="境内外标识" col="4" formControl="true"/>
                    <bf:select id="domesticOrForeign" name="domesticOrForeign" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" value="${relPsn.domesticOrForeign}">
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
                    <bf:text id="birthdate" name="birthdate" col="8" value="${relPsn.birthdate}">
                    </bf:text>
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
                <bc:col col="4">
                    <bc:label text="持股比例或表决权" col="4" formControl="true"/>
                    <bf:text id="shareholdingRatio" name="shareholdingRatio" col="8" value="${relPsn.shareholdingRatio}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/bus/rel/savePsn', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            var orgid = $('#orgId').val();
                            window.location = '${base}/bus/rel/list/' + orgid;
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
                            var orgid = $('#orgId').val();
                            window.location = '${base}/bus/rel/list/' + orgid;
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

    });
</script>
</body>
</html>
