<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>自然人新增或编辑</title>
    <style>
        /*.boxse .form-group .col-xs-5 {*/
        /*margin: 0.3% 0 !important;*/
        /*}*/

        /*.boxse .has-success .form-control, .boxse .has-success .form-control:focus {*/
        /*border-color: #CCCCCC !important;*/
        /*}*/

        /*.has-success .select2-dropdown, .has-success .select2-selection {*/
        /*border-color: #CCCCCC !important;*/
        /*}*/
        .form-group .col-xs-4 {
            margin-top: 0.4%;
        }

        .labels .control-label {
            display: inline-block;
            width: 12%;
            float: left;
        }

        .gray-bg {
            background: white !important;
        }
    </style>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${relOrgSuspect.pid}"/>
            <%--<div class="boxse">--%>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="企业名称" col="5" formControl="true"/>
                    <bf:text id="orgName" name="orgName" col="7" value="${relOrgSuspect.orgName}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="企业类型" col="5" formControl="true"/>
                    <bf:select id="orgType" name="orgType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/SUB-BusinessType" value="${relOrgSuspect.orgType}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="境内外标识(1-境内，2-境外)" col="5" formControl="true"/>
                    <bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
                               value="${relOrgSuspect.domesticOrForeign}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="证件类型" col="5" formControl="true"/>
                    <bf:text id="identifyType" name="identifyType" col="7" value="${relOrgSuspect.identifyType}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="社会统一信用代码" col="5" formControl="true"/>
                    <bf:text id="uniscCode" name="uniscCode" col="7" value="${relOrgSuspect.uniscCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="注册资本(万元/人民币)" col="5" formControl="true"/>
                    <bf:text id="regCapital" name="regCapital" col="7" value="${relOrgSuspect.regCapital}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="注册资本币种代码" col="5" formControl="true"/>
                    <bf:text id="regCapitalCurrency" name="regCapitalCurrency" col="7"
                             value="${relOrgSuspect.regCapitalCurrency}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="营业范围" col="5" formControl="true"/>
                    <bf:text id="businessScope" name="businessScope" col="7" value="${relOrgSuspect.businessScope}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="机构状态（1-正常，2-停业）" col="5" formControl="true"/>
                    <bf:select id="status" name="status" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Status" value="${relOrgSuspect.status}">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="法定代表人" col="5" formControl="true"/>
                    <bf:text id="legalPerson" name="legalPerson" col="7" value="${relOrgSuspect.legalPerson}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="是否为股东" col="5" formControl="true"/>
                    <bf:text id="isShareholder" name="isShareholder" col="7" value="${relPsnSuspect.isShareholder}">
                    </bf:text>
                    <%--<bf:select id="mgerOrg" name="mgerOrg" col="8" dataSource="custom" url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relPsnSuspect.mgerOrg}">--%>
                    <%--</bf:select>--%>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="是否国有企业" col="5" formControl="true"/>
                    <bf:text id="stateOwned" name="stateOwned" col="7" value="${relPsnSuspect.stateOwned}">
                    </bf:text>
                    <%--<bf:select id="mgerOrg" name="mgerOrg" col="8" dataSource="custom" url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relPsnSuspect.mgerOrg}">--%>
                    <%--</bf:select>--%>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="股东代码" col="5" formControl="true"/>
                    <bf:text id="shareholdersCode" name="shareholdersCode" col="7"
                             value="${relPsnSuspect.shareholdersCode}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="关联银行账户开户行" col="6" formControl="true"/>
                    <bf:text id="openBank" name="openBank" col="6" value="${relPsnSuspect.openBank}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="关联银行户账号" col="5" formControl="true"/>
                    <bf:text id="tradeAccount" name="tradeAccount" col="7" value="${relPsnSuspect.tradeAccount}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="持股数量" col="5" formControl="true"/>
                    <bf:text id="holeShareAmount" name="holeShareAmount" col="7"
                             value="${relPsnSuspect.holeShareAmount}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="持股占比" col="5" formControl="true"/>
                    <bf:text id="shareholdingProportion" name="politicsStatus" col="7"
                             value="${relPsnSuspect.shareholdingProportion}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="注册地" col="5" formControl="true"/>
                    <bf:text id="regAddress" name="regAddress" col="7" value="${relOrgSuspect.regAddress}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="认定结果" col="5" formControl="true"/>
                    <bf:select id="isSuspect" name="isSuspect" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-isRel" value="${relPsnSuspect.isSuspect}">
                    </bf:select>
                </bc:col>

            </bf:formGroup>
            <%--</div>--%>
            <bf:formGroup type="success">
                <div class="labels">
                    <bc:col col="12">
                        <bc:label text="认定原因" col="2" formControl="true"/>
                        <bf:text id="cognizaReason" name="cognizaReason" col="10" value="${relOrgSuspect.cognizaReason}"
                                 multiple="true" placeholder="此项为必填项">
                            <bv:notEmpty message="请输入认定原因"/>
                        </bf:text>
                    </bc:col>
                </div>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding: 3% 0 0 0">
                    <bc:button type="success" outline="true" text="人工认定" icon="save">
                        <be:event>
                            sub();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/relOrgSuspect/list';
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

    function sub() {
        var pid = $("#pid").val();
        var isSuspect = $("#isSuspect").val();
        $.ajax({
            url: "${base}/bus/relOrgSuspect/orgAudit/" + pid,
            type: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                console.log(data.relPsnSuspect)
                if (data != null) {
                    //必须传入id 值
                    var objData = data.relOrgSuspect;
                    var downSuspectList = data.downSuspectList;
                    var upSuspectList = data.upSuspectList;
                    var upSuspectPantList = data.upSuspectPantList;

                    if (isSuspect) {
                        //认定为关联方，需要校验关系
                        if (upSuspectList != null || upSuspectList != "") {
                            window.location="${base}/bus/relOrgSuspect/savedata/" + pid;
                        }
                    }else {
                        window.location="${base}/bus/relOrgSuspect/list";

                    }
                }
            }
        })
    }

    /*function sub() {
        var pid = $("#pid").val();
        var isSuspect = $("#isSuspect").val();
        var dutyOrg = "0201"
        var checkPsn = "brm"
        $.ajax({
            url: "${base}/bus/relOrgSuspect/orgAudit/" + pid,
            type: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                console.log(data.relPsnSuspect)
                if (data != null) {
                    //必须传入id 值
                    var objData = data.relOrgSuspect;
                    var downSuspectList = data.downSuspectList;
                    var upSuspectList = data.upSuspectList;
                    var upSuspectPantList = data.upSuspectPantList;
                    //判断是否设置了关联管理
                    if (isSuspect == "1") {
                        //认定为关联方，需要校验关系
                        if (upSuspectList == null || upSuspectList == "" && upSuspectPantList == null || upSuspectPantList == "") {
                            alert('请先设置关联关系')
                            return;
                        }
                    }
                    //alert(objData)
                    var myDate = new Date();
                    objData.id = myDate.getTime();
                    objData.processType = "CheckByOrg";
                    // 欠缺是否是关联方
                    objData.cognizaReason = $("#cognizaReason").val().trim();
                    objData.isSuspect = $("#isSuspect").val().trim();
                    // alert(objData.cognizaReason)
                    // alert(objData.isSuspect)
                    data = JSON.stringify(objData);
                    var formData = $.helper.base64Encode(data);
                    // alert(objData)
                    var obj = {
                        procDefCode: 'YZJ_SUSPECT',
                        oldData: '',
                        newData: formData,
                        uploadSessions: '',
                        procInstId: '',
                        // downSuspects:downSuspectList,
                        // upSuspects:upSuspectList
                    }
                    $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
                        if (data.success) {
                            $.messager.alert('提示', '提交成功。', 'info', function () {
                                window.location = '${base}/workflow/process/runingList';
                            });
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });
                }
            }
        })
    }*/

    function rightsInformation() {
        var opt = $("#opt").val();
        var pid = $("#pid").val();
        window.location = '${base}/bus/relPsnSuspect/psnRelOrgList/edit/' + pid + '? ';
    }

    $(document).ready(function () {
        var opt = $('#opt').val();
        if (opt != 'edit') {
            document.getElementById("psnRelInformation").setAttribute("disabled", true);
            document.getElementById("orgRelInformation").setAttribute("disabled", true);
        }
    });
</script>
</body>
</html>
