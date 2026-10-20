<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <x:jsLib/>
    <br:css pack="form"/>
    <title>金融产品新增或编辑</title>
    <script>
        $(document).ready(function () {
            show();
        });
        $(document).ready(function () {
            x();
        });

        function isShareholder(val) {
            var opt = $('#opt').val();
            if (val == '1') {
                $('#MainGd').css('display', 'block')
                $('#tableDiv').css('display', 'block')
                localStorage.vals = val;
            }
            if (val == '0') {
                $('#MainGd').css('display', 'none')
                $('#tableDiv').css('display', 'none')
                localStorage.vals = val;
            }
            return val;
        }

        function isNoShareVote(val) {
            var opt = $('#opt').val();
            if (val == '1') {
                $('#voteProportion').css('display', 'block')
                $('#voteProportion').css('display', 'block')
                localStorage.vals = val;
            }
            if (val == '0') {
                $('#voteProportion').css('display', 'none')
                $('#voteProportion').css('display', 'none')
                localStorage.vals = val;
            }
            return val;
        }

        function x() {
            $('#y').css('display', 'none');//返回按钮
            $('#regulatorOth1').attr('disabled', 'true');//统计口径
            $('#mgerOrg').attr('disabled', 'true');//统计口径
            $('#x').css('display', 'none');//保存按钮
            $('#name').attr('disabled', 'true');//姓名
            $('#sex').attr('disabled', 'true');//性别
            $('#identifyType').attr('disabled', 'true');//证件类型
            $('#identifyNumber').attr('disabled', 'true');//证件号码
            $('#politicsStatus').attr('disabled', 'true');//政治面貌
            $('#educationBackground').attr('disabled', 'true');//学历
            $('#race').attr('disabled', 'true');//民族
            $('#domesticOrForeign').attr('disabled', 'true');//境内外标识
            $('#maritalStatus').attr('disabled', 'true');//婚姻状况
            $('#mobile').attr('disabled', 'true');//移动电话
            $('#email').attr('disabled', 'true');//邮箱
            $('#residentialAddress').attr('disabled', 'true');//居住地址
            $('#unitAddress').attr('disabled', 'true');//单位地址
            $('#shareholdersCode').attr('disabled', 'true');//股权证编号
            $('#capitalNumber').attr('disabled', 'true');//股东账号
            $('#openBank').attr('disabled', 'true');
            $('#tradeAccount').attr('disabled', 'true');
            $('#inPrice').attr('disabled', 'true');
            $('#holeShareAmount').attr('disabled', 'true');
            $('#shareholdingProportion').attr('disabled', 'true');
            $('#frozenShareAmount').attr('disabled', 'true');
            $('#pledgedShareAmount').attr('disabled', 'true');
            $('#isNoShareVote').attr('disabled', 'true');
            $('#voteProportion').attr('disabled', 'true');
            $('#limitVoteSituation').attr('disabled', 'true');
            $('#workUnits').attr('disabled', 'true');
            // $('#birthdate').css('disabled','true');//出生日期
            // $('#inTime').css('disabled','true');  //$('#date').datebox('disable');
        }

    </script>
    <x:jsLib/>

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
    </style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
    <bc:ibox id="productBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="op" name="op" value="${op}"/>
            <bf:hidden id="pid" name="pid" value="${relPro.pid}"/>
            <bf:hidden id="status" name="status" value="${relPro.approvalStatus}"/>
            <%--<bf:hidden id="relSerial" name="relSerial" value="${relPro.relSerial}"/>--%>
            <bf:formGroup>
<%--                <div class="col-sm-4">--%>
<%--                    <bc:button type="info" text="基本信息">--%>
<%--                        <be:event>--%>
<%--                            basicInformation();--%>
<%--                        </be:event>--%>
<%--                    </bc:button>--%>
<%--                        &lt;%&ndash;<bc:button id="psnRelInformation" type="info" text="关系自然人信息">--%>
<%--                            <be:event>--%>
<%--                                affiliateInformation();--%>
<%--                            </be:event>--%>
<%--                        </bc:button>&ndash;%&gt;--%>
<%--                    <bc:button id="orgRelInformation" type="info" text="关系机构信息">--%>
<%--                        <be:event>--%>
<%--                            orgRelInformation();--%>
<%--                        </be:event>--%>
<%--                    </bc:button>--%>
<%--                </div>--%>
            </bf:formGroup>
            <bf:formGroup>
                <%--<bc:col col="3">
                    <font color="red">*</font>
                </bc:col>--%>
                <bc:col col="6">
                    <bc:label cls="required" text="产品名称" col="4" formControl="true"/>
                    <bf:text id="proName" name="proName" col="8" disabled="true" value="${relPro.proName}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls="required" text="产品分类" col="4" formControl="true"/>
                    <bf:select id="proType" name="proType" col="8" dataSource="custom" disabled="true"
                               url="sys/dictItem/getDictItems/BUS-pro" value="${relPro.proType}">
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label cls="required" text="产品批准/注册/备案号" col="4" formControl="true"/>
                    <bf:text id="proProvider" name="proProvider" disabled="true" col="8" value="${relPro.proProvider}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="产品代码" col="4" formControl="true"/>
                    <bf:text id="proCode" name="proCode" disabled="true" col="8" value="${relPro.proCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label cls="required" text="产品管理人全称" col="4" formControl="true"/>
                    <bf:text id="adName" name="adName" disabled="true" col="8" value="${relPro.adName}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls="required" text="管理人统一社会信用代码" col="4" formControl="true"/>
                    <bf:text id="adCreditCode" name="adCreditCode" disabled="true" col="8"
                             value="${relPro.adCreditCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="产品托管人全称" col="4" formControl="true"/>
                    <bf:text id="careName" name="careName" col="8" disabled="true" value="${relPro.careName}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="托管人统一社会信用代码" col="4" formControl="true"/>
                    <bf:text id="careCreditCode" disabled="true" name="careCreditCode" col="8"
                             value="${relPro.careCreditCode}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="产品登记机构" col="4" formControl="true"/>
                    <bf:text id="proRegOrg" name="proRegOrg" disabled="true" col="8" value="${relPro.proRegOrg}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="机构名称" col="4" formControl="true"/>
                    <bf:select id="mgerOrg" name="mgerOrg" col="8" dataSource="custom" disabled='true'
                               url="sys/unit/selectUnit" value="${relPro.mgerOrg}">
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <%-- <bc:col col="6">
                     <bc:label text="持有比例(%)" col="4" formControl="true"/>
                     <bf:text id="careName" name="careName" col="8" value="${relPro.careName}">
                     </bf:text>
                 </bc:col>--%>
                <%--<bc:col col="6">
                    <bc:label cls="required" text="与前一层是否有份额" col="4" formControl="true"/>
                    <bf:select id="equityShare" name="equityShare" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/WhetherToShare"
                               value="${relPro.equityShare}" disabled="false">
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>--%>
            </bf:formGroup>
            <%--<bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5">
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.history.go(-1);
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>--%>
        </bf:form>
    </bc:ibox>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">


    function affiliateInformation() {
        var value = '${op}';
        var pid = $("#pid").val();
        if (pid == '') {
            alert("请保存基本信息")
            return;
        }
        if (value == "0") {
            // alert(value);
            window.location = '${base}/bus/relPro/psnOfProList/' + pid + '?op=0';
        } else {
            // alert(value);
            window.location = '${base}/bus/relPro/psnOfProList/' + pid + '?op=1';
        }

    }

    function orgRelInformation() {

        var pid = $("#pid").val();
        window.location = '${base}/bus/relPro/orgOfProList/' + pid + '?op=2';
    }

    $(document).ready(function () {
        Array.prototype.slice.call(document.getElementsByTagName('input')).map(i => i.disabled = true)
        Array.prototype.slice.call(document.getElementsByTagName('select')).map(i => i.disabled = true)
    });

    //查询反显
    $("#identifyNumber").blur(function () {
        var identifyNumber = $(this).val();
        if (identifyNumber != '' && identifyNumber != undefined) {
            $.ajax({
                type: 'GET',
                url: '${base}/bus/relPsn/selectRelPsn',
                data: {
                    "name": '',
                    // "shareCode": '',
                    "identifyType": '',
                    "identifyNumber": identifyNumber
                },
                dataType: 'json',
                success: function (data) {
                    console.log(data)
                    $("#identifyNumber").empty();
                    if (data.length > 0) {
                        $.messager.alert('提示', '关联方已存在！', 'warning', function () {
                            <%--window.location = '${base}/workflow/process/runingList';--%>
                        });
                    }
                }
            });
        }
    })
</script>
</body>
</html>
