<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <x:jsLib/>
    <br:css pack="form"/>
    <title>自然人新增或编辑</title>
    <script>
        $(document).ready(function () {
            show();
        });
        $(document).ready(function () {
            x();
        });
        // window.onload=show;
        // window.onload=x;
        function isShareholder(val) {
            if (val == '1') {
                $('#aboutAandP').css('display', 'block')
                // $('#pledgeRatio').css('display', 'block')
                /*$('#MainGd').css('display', 'block')
                $('#tableDiv').css('display', 'block')*/

                localStorage.vals = val;
            }
            if (val == '0') {
                $('#aboutAandP').css('display', 'none')
                // $('#pledgeRatio').css('display', 'none')
                /*$('#MainGd').css('display', 'none')
                $('#tableDiv').css('display', 'none')*/
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

        <%--var opt = $('#opt').val();--%>
        <%--if(opt !='add'){--%>
        <%--    window.onload = function() {--%>

        <%--            show();--%>
        <%--            showisv();--%>

        <%--    }--%>
        <%--}--%>

        function x() {
            // alert("进入");
            var value = '${op}';
            // alert(value);
            if (value != "0") {
                $('#relFileTable_tb_removeDoc').css('display', 'none');//删除按钮
                $('#relFileTable_tb_exportIn').css('display', 'none');//上传文件按钮
                $('#y').css('display', 'none');//返回按钮
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
                $('#regulatorOth1').attr('disabled', 'true');


                $('#chk').attr('disabled', 'true');  //本行股东
                $('#chk1').attr('disabled', 'true');  //本行股东
                $('#ma').attr('disabled', 'true');  //本行主要股东
                $('#ma1').attr('disabled', 'true');  //本行主要股东
                $('#isv').attr('disabled', 'true');  //具有非持股表决权
                $('#isv1').attr('disabled', 'true');  //具有非持股表决权
                $("[name=inTime]").attr("readonly", "readonly");
                $("[name=birthdate]").attr("readonly", "readonly");//出生日期
                $(".input-group-addon").off();
                // $('#birthdate').css('disabled','true');//出生日期
                // $('#inTime').css('disabled','true');  //$('#date').datebox('disable');


            }
        }

        function show() {
            // alert("进入")
            var val = '${relPsn.isShareholder}';
            if (val == '1') {
                // alert("进入1")
                // $('#MainGd').css('display', 'block')
                // $('#tableDiv').css('display', 'block')
                document.getElementById('chk1').checked = false
                document.getElementById('chk').checked = true
                $('#aboutAandP').css('display', 'block')
                localStorage.vals = val;
            }
            if (val == '0') {
                // alert("进入0")
                // $('#MainGd').css('display', 'none')
                // $('#tableDiv').css('display', 'none')
                // localStorage.vals = val;
                document.getElementById('chk1').checked = true
                document.getElementById('chk').checked = false
                $('#aboutAandP').css('display', 'none')
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

            <%--var opt = $('#opt').val();--%>
            <%--if(opt !='add'){--%>
            <%--    window.onload = function() {--%>

            <%--            show();--%>
            <%--            showisv();--%>

            <%--    }--%>
            <%--}--%>

            function x() {
                // alert("进入");
                var value = '${op}';
                // alert(value);
                if (value != "0") {
                    $('#relFileTable_tb_removeDoc').css('display', 'none');//删除按钮
                    $('#relFileTable_tb_exportIn').css('display', 'none');//上传文件按钮
                    // $('#y').css('display', 'none');//返回按钮
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
                    $('#regulatorOth1').attr('disabled', 'true');


                    $('#chk').attr('disabled', 'true');  //本行股东
                    $('#chk1').attr('disabled', 'true');  //本行股东
                    $('#ma').attr('disabled', 'true');  //本行主要股东
                    $('#ma1').attr('disabled', 'true');  //本行主要股东
                    $('#isv').attr('disabled', 'true');  //具有非持股表决权
                    $('#isv1').attr('disabled', 'true');  //具有非持股表决权
                    $("[name=inTime]").attr("readonly", "readonly");
                    $("[name=birthdate]").attr("readonly", "readonly");//出生日期
                    $(".input-group-addon").off();
                    // $('#birthdate').css('disabled','true');//出生日期
                    // $('#inTime').css('disabled','true');  //$('#date').datebox('disable');


                }
            }


            var val1 = '${relPsn.isNoShareVote}';
            if (val1 == '1') {
                $('#voteProportion').css('display', 'block')
                $('#voteProportion').css('display', 'block')
                document.getElementById('isv').checked = true
                document.getElementById('isv1').checked = false
                localStorage.vals = val;
            }
            if (val1 == '0') {
                $('#voteProportion').css('display', 'none')
                $('#voteProportion').css('display', 'none')
                document.getElementById('isv').checked = false
                document.getElementById('isv1').checked = true
                localStorage.vals = val;
            }
            //是否为主要股东
            var val2 = '${relPsn.isMainGd}';
            if (val2 == '1') {
                // $('#voteProportion').css('display', 'block')
                // $('#voteProportion').css('display', 'block')
                document.getElementById('ma').checked = true
                document.getElementById('ma1').checked = false
                // localStorage.vals = val;
            }
            if (val2 == '0') {
                document.getElementById('ma').checked = false
                document.getElementById('ma1').checked = true
            }

        }

        //反显持股占比
        $("#holeShareAmount").blur(function () {
            var holeShareAmount = $('#holeShareAmount').val();
            // alert(holeShareAmount);
            if (holeShareAmount != '' && holeShareAmount != undefined) {
                $.ajax({
                    type: 'GET',
                    url: '${base}/fil/filRootOrgInfo/count/' + holeShareAmount,
                    data: {
                        "": ''
                    },
                    dataType: 'json',
                    success: function (data) {
                        console.log(data)
                        // $("#identifyNumber").empty();
                        if (data.success) {
                            $("#shareholdingProportion").val(data.shareholdingProportion)
                            $("#frozenShareAmount").val(0)
                            $("#pledgedShareAmount").val(0)
                        } else {
                            $.messager.alert('提示', '计算失败', 'info');
                        }
                    }
                });
            }
        })

    </script>
    <x:jsLib/>

    <style>
        .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
            padding-top: 5px;
        }

        .form-group .col-xs-4 > .col-xs-7 {
            height: 34px !important;
        }

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
    <bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
    <bf:form id="editForm" method="post" validation="true">
        <bf:hidden id="opt" name="opt" value="${opt}"/>
        <bf:hidden id="op" name="op" value="${op}"/>
        <bf:hidden id="pid" name="pid" value="${relPsn.pid}"/>
        <bf:hidden id="status" name="status" value="${relPsn.status}"/>
        <bf:hidden id="relSerial" name="relSerial" value="${relPsn.relSerial}"/>
    <bf:formGroup>
    <div class="col-sm-6 ">
        <bc:button type="light" text="基本信息">
            <be:event>
                basicInformation();
            </be:event>
        </bc:button>
        <bc:button type="light" text="关系自然人信息">
            <be:event>
                affiliateInformation();
            </be:event>
        </bc:button>
        <bc:button type="light" text="关系机构信息">
            <be:event>
                orgRelInformation();
            </be:event>
        </bc:button>
        <%--<bc:button type="light   " text="关系金融产品">
            <be:event>
                proRelInformation();
            </be:event>
        </bc:button>--%>
    </div>
    </bf:formGroup>
    <div class="boxse">
        <bc:row cls="m-b">
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="姓名" col="5" formControl="true"/>
                    <bf:text id="name" name="name" col="7" value="${relPsn.name}"
                             helpText="${relPsn2.name}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="性別" col="5" formControl="true"/>
                    <bf:select id="sex" name="sex" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Sex"
                               value="${relPsn.sex}" disabled="true">
                    </bf:select>
                    <c:if test="${not empty relPsn2.sex}">
                        <div class="col-md-7 col-md-offset-5">
                            <bf:select id="sex1" name="sex1" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-Sex"
                                       value="${relPsn2.sex}" disabled="true">
                            </bf:select>
                        </div>
                    </c:if>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="证件类型" col="5" formControl="true"/>
                    <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relPsn.identifyType}"
                               disabled="true">
                    </bf:select>
                    <c:if test="${not empty relPsn2.identifyType}">
                        <div class="col-md-7 col-md-offset-5">
                            <bf:select id="identifyType1" name="identifyType1" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-IdentifyType"
                                       value="${relPsn2.identifyType}" disabled="true">
                            </bf:select>
                        </div>
                    </c:if>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="证件号码" col="5" formControl="true"/>
                    <bf:text id="identifyNumber1" name="identifyNumber1" col="7" value="${relPsn.identifyNumber}"
                             helpText="${relPsn2.identifyNumber}" disabled="true">
                        <%--                        click="addRelPsn()"--%>
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="境内外标识" col="5" formControl="true"/>
                    <bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
                               value="${relPsn.domesticOrForeign}" disabled="true">
                    </bf:select>
                    <c:if test="${not empty relPsn2.domesticOrForeign}">
                        <div class="col-md-7 col-md-offset-5">
                            <bf:select id="domesticOrForeign1" name="domesticOrForeign1" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
                                       value="${relPsn2.domesticOrForeign}" disabled="true">
                            </bf:select>
                        </div>
                    </c:if>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="政治面貌" col="5" formControl="true"/>
                    <bf:select id="politicsStatus" name="politicsStatus" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS_politically" value="${relPsn.politicsStatus}"
                               disabled="true">
                        <bf:option text="请选择" value="A"></bf:option>
                    </bf:select>
                    <c:if test="${not empty relPsn2.politicsStatus}">
                        <div class="col-md-7 col-md-offset-5 ">
                            <bf:select id="politicsStatus1" name="politicsStatus1" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS_politically"
                                       value="${relPsn2.politicsStatus}" disabled="true">
                            </bf:select>
                        </div>
                    </c:if>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="出生日期" col="5" formControl="true"/>
                    <bf:datebox name="birthdate" id="birthdate" col="7" format="date" value="${relPsn.birthdate}"
                                disabled="true">
                    </bf:datebox>
                    <c:if test="${not empty relPsn2.birthdate}">
                        <div class="col-md-7 col-md-offset-5">
                            <bf:datebox name="birthdate1" id="birthdate1" format="date" value="${relPsn2.birthdate}"
                                        disabled="true">
                            </bf:datebox>
                        </div>
                    </c:if>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="移动电话" col="5" formControl="true"/>
                    <bf:text id="mobile" name="mobile" col="7" value="${relPsn.mobile}"
                             helpText="${relPsn2.mobile}" disabled="true">
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="婚姻状况" col="5" formControl="true"/>
                    <bf:select id="maritalStatus" name="maritalStatus" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-MaritalStatus" value="${relPsn.maritalStatus}"
                               disabled="true">
                        <bf:option text="请选择" value="A"></bf:option>
                    </bf:select>
                    <c:if test="${not empty relPsn2.maritalStatus}">
                        <div class="col-md-7 col-md-offset-5 content1">
                            <bf:select id="maritalStatus1" name="maritalStatus1" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-MaritalStatus"
                                       value="${relPsn2.maritalStatus}" disabled="true">
                            </bf:select>
                        </div>
                    </c:if>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="邮箱" col="5" formControl="true"/>
                    <bf:text id="email" name="email" col="7" value="${relPsn.email}"
                             helpText="${relPsn2.email}" disabled="true">
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="工作单位" col="5" formControl="true"/>
                    <bf:text id="workUnits" name="workUnits" col="7" value="${relPsn.workUnits}"
                             helpText="${relPsn2.workUnits}" disabled="true">
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="单位地址" col="5" formControl="true"/>
                    <bf:text id="unitAddress" name="unitAddress" col="7" value="${relPsn.unitAddress}"
                             helpText="${relPsn2.unitAddress}" disabled="true">
                    </bf:text>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="居住地址" col="5" formControl="true"/>
                    <bf:text id="residentialAddress" name="residentialAddress" col="7"
                             value="${relPsn.residentialAddress}"
                             helpText="${relPsn2.residentialAddress}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="机构名称" col="5" formControl="true"/>
                    <bf:select id="mgerOrg" name="mgerOrg" col="7" dataSource="custom"
                               disabled="true" url="sys/unit/selectUnit" value="${relPsn.mgerOrg}">
                    </bf:select>
                    <c:if test="${not empty relPsn2.mgerOrg}">
                        <div class="col-md-7 col-md-offset-5">
                            <bf:select id="mgerOrg1" name="mgerOrg1" dataSource="custom" url="sys/unit/selectUnit"
                                       value="${relPsn2.mgerOrg}" disabled="true">
                            </bf:select>
                        </div>
                    </c:if>
                </bf:formGroup>
                <bf:formGroup col="4" id = "isShareholderDisabled">
                    <bc:label text="是否为股东" col="5" formControl="true"/>
                    <bf:checkGroup name="isShareholder" col="7" value="${relPsn.isShareholder}" disabled="true">
                        <bf:radio id="chk" value="1" label="是"  /><%--click="isShareholder(e.target.value)"--%>
                        <bf:radio id="chk1" value="0" label="否"
                                 /><%-- click="isShareholder(e.target.value)"--%>
                    </bf:checkGroup>
                    <div class="col-md-7 col-md-offset-5">
                        <c:if test="${not empty relPsn2.isShareholder}">
                            <bf:checkGroup name="isShareholder1" value="${relPsn2.isShareholder}">
                                <bf:radio id="chk" value="1" label="是" click="isShareholder(e.target.value)"/>
                                <bf:radio id="chk1" value="0" label="否" checked="true"
                                          click="isShareholder(e.target.value)"/>
                            </bf:checkGroup>
                        </c:if>
                    </div>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup hrp="bottom" id="aboutAandP">
                <bc:col col="4">
                    <bc:label text="持股占比(%)" col="5" formControl="true"/>
                    <bf:text id="shareholdingProportion" name="shareholdingProportion" col="7"
                             value="${relPsn.shareholdingProportion}" disabled="true"
                             helpText="${relPsn2.shareholdingProportion}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="质押股权比例(%)" col="5" formControl="true"/>
                    <bf:text id="pledgeRatio" name="pledgeRatio" col="7" disabled="true"
                             value="${relPsn.pledgeRatio}" helpText="${relPsn2.pledgeRatio}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>

            <%-- <bf:formGroup hrp="bottom">
                 <bf:formGroup col="4">
                     <bc:label text="民族" col="5" formControl="true"/>
                     <bf:select id="race" name="race" col="7" dataSource="custom"
                                url="sys/dictItem/getDictItems/BUS-Race" value="${relPsn.race}" disabled="true">
                     </bf:select>
                     <c:if test="${not empty relPsn2.race}">
                         <div class="col-md-7 col-md-offset-5">
                             <bf:select id="race1" name="race1" dataSource="custom"
                                        url="sys/dictItem/getDictItems/BUS-Race"
                                        value="${relPsn2.race}" disabled="true">
                             </bf:select>
                         </div>
                     </c:if>
                 </bf:formGroup>
             </bf:formGroup>
             <bf:formGroup hrp="bottom">
             <bf:formGroup hrp="bottom">

                 <bf:formGroup col="4">
                     <bc:label text="统计口径" col="5" formControl="true"/>
                     <bf:select id="regulatorOth1" name="regulatorOth1" col="7" dataSource="custom"
                                disabled="true" url="sys/dictItem/getDictItems/BUS-Caliber"
                                value="${relPsn.regulatorOth1}">
                     </bf:select>
                     <c:if test="${not empty relPsn2.regulatorOth1}">
                         <div class="col-md-7 col-md-offset-5 content1">
                             <bf:select id="regulatorOth2" name="regulatorOth2" dataSource="custom"
                                        url="sys/dictItem/getDictItems/BUS-Caliber"
                                        value="${relPsn2.regulatorOth1}" disabled="true">
                             </bf:select>
                         </div>
                     </c:if>
                 </bf:formGroup>

             </bf:formGroup>
             <bf:formGroup hrp="bottom">



             </bf:formGroup>

             <div id="MainGd" class="MainGd">
                 <bf:formGroup hrp="bottom">
                     &lt;%&ndash;                    新添的&ndash;%&gt;
                     <bc:col col="4">
                         <bc:label text="是否为本行主要股东" col="5" formControl="true"/>
                         <bf:checkGroup name="isMainGd" col="7" value="${relPsn.isMainGd}">
                             <bf:radio id="ma" value="1" label="是"/>
                             <bf:radio id="ma1" value="0" label="否" checked="true"/>
                         </bf:checkGroup>
                         <div class="col-md-7 col-md-offset-5">
                             <c:if test="${not empty relPsn2.isMainGd}">
                                 <bf:checkGroup name="isMainGd1" col="7" value="${relPsn2.isMainGd}">
                                     <bf:radio id="ma" value="1" label="是"/>
                                     <bf:radio id="ma1" value="0" label="否" checked="true"/>
                                 </bf:checkGroup>
                             </c:if>
                         </div>
                     </bc:col>
                     <bc:col col="4">
                         <bc:label text="股权证编号" col="5" formControl="true"/>
                         <bf:text id="shareholdersCode" name="shareholdersCode" col="7"
                                  value="${relPsn.shareholdersCode}"
                                  helpText="${relPsn2.shareholdersCode}">
                         </bf:text>
                     </bc:col>

                     <bc:col col="4">
                         <bc:label text="股东账号" col="5" formControl="true"/>
                         <bf:text id="capitalNumber" name="capitalNumber" col="7"
                                  value="${relPsn.capitalNumber}"
                                  helpText="${relPsn2.capitalNumber}">
                         </bf:text>
                     </bc:col>
                 </bf:formGroup>
                 <bf:formGroup hrp="bottom">
                     <bc:col col="4">
                         <bc:label text="交易账户开户行" col="5" formControl="true"/>
                         <bf:text id="openBank" name="openBank" col="7" value="${relPsn.openBank}"
                                  helpText="${relPsn2.openBank}">
                         </bf:text>
                     </bc:col>
                     <bc:col col="4">
                         <bc:label text="交易账户账号" col="5" formControl="true"/>
                         <bf:text id="tradeAccount" name="tradeAccount" col="7" value="${relPsn.tradeAccount}"
                                  helpText="${relPsn2.tradeAccount}">
                         </bf:text>
                     </bc:col>
                     <bc:col col="4">
                         <bc:label text="入股时间" col="5" formControl="true"/>
                         <bf:datebox id="inTime" name="inTime" col="7" value="${relPsn.inTime}">
                         </bf:datebox>
                         <div class="col-md-7 col-md-offset-5">
                             <c:if test="${not empty relPsn2.inTime}">
                                 <bf:datebox id="inTime1" name="inTime1" col="7" value="${relPsn2.inTime}">
                                 </bf:datebox>
                             </c:if>
                         </div>
                     </bc:col>
                 </bf:formGroup>
                 <bf:formGroup hrp="bottom">
                     <bc:col col="4">
                         <bc:label text="入股价格(元/股)" col="5" formControl="true"/>
                         <bf:text id="inPrice" name="inPrice" col="7" value="${relPsn.inPrice}"
                                  helpText="${relPsn2.inPrice}">
                         </bf:text>
                     </bc:col>
                     <bc:col col="4">
                         <bc:label text="持股数量（股）" col="5" formControl="true"/>
                         <bf:text id="holeShareAmount" name="holeShareAmount" col="7"
                                  value="${relPsn.holeShareAmount}"
                                  helpText="${relPsn2.holeShareAmount}">
                         </bf:text>
                     </bc:col>

                 </bf:formGroup>
                 <bf:formGroup hrp="bottom">
                     <bc:col col="4">
                         <bc:label text="总冻结股数（股）" col="5" formControl="true"/>
                         <bf:text id="frozenShareAmount" name="frozenShareAmount" col="7"
                                  value="${relPsn.frozenShareAmount}"
                                  helpText="${relPsn2.frozenShareAmount}">
                         </bf:text>
                     </bc:col>
                     <bc:col col="4">
                         <bc:label text="总质押股数（股）" col="5" formControl="true"/>
                         <bf:text id="pledgedShareAmount" name="pledgedShareAmount" col="7"
                                  value="${relPsn.pledgedShareAmount}"
                                  helpText="${relPsn2.pledgedShareAmount}">
                         </bf:text>
                     </bc:col>
                 </bf:formGroup>
             </div>
             <div id="tableDiv" class="MainGd">
                 <bf:formGroup hr="dashed" hrp="bottom">
                     <div style="width: 95%;margin:0 auto">
                         <bc:table id="relFileTable" idField="pid" url="${base}/fil/relFile" multiple="true"
                                   selectFirst="false" queryUrl="queryList?relSerial=${relPsn.relSerial}">
                             <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
                             <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>
                             <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
                             <bc:column field="pid" title="主键" width="100" visible="false"/>
                             <bc:column field="fileName" title="文件名称" width="100"/>
                             <bc:column field="fileUrl" title="文件路径" width="100" visible="false"/>
                             <bc:column field="fileType" title="文件类型" width="100"/>
                             <bc:column field="fileVersion" title="版本号" width="100" visible="false"/>
                             <bc:column field="approveStatus" title="操作状态" width="100" visible="false"/>
                             <bc:column field="fileSize" title="文件大小" width="100"/>
                             <bc:column field="relSerial" title="交易流水号" width="100" visible="false"/>
                             <bc:column field="uploadAgency" title="上传机构" width="100"/>
                             <bc:column field="uploadDepartment" title="上传部门" width="100"/>
                             <bc:column field="modifiedTime" title="上传时间" width="100"/>
                         </bc:table>
                     </div>
                 </bf:formGroup>
             </div>
             </div>
             <bf:formGroup hrp="bottom">
                 &lt;%&ndash;            新添字段&ndash;%&gt;
                 <bc:col col="4">
                     <bc:label text="是否具有非持股表决权" col="5" formControl="true"/>
                     <bf:checkGroup name="isNoShareVote" col="7" value="${relPsn.isNoShareVote}">
                         <bf:radio id="isv" value="1" label="是" click="isNoShareVote(e.target.value)"/>
                         <bf:radio id="isv1" value="0" label="否" checked="true"
                                   click="isNoShareVote(e.target.value)"/>
                     </bf:checkGroup>
                 </bc:col>
                 <div id="voteProportion" class="voteProportion">
                     <bc:col col="4">
                         <bc:label text="表决权占比：" col="5" formControl="true"/>
                         <bf:text id="voteProportion" name="voteProportion" col="7"
                                  value="${relPsn.voteProportion}">
                         </bf:text>
                     </bc:col>
                 </div>
                 <bc:col col="4">
                     <bc:label text="限制表决权情况：" col="5" formControl="true"/>
                     <bf:text id="limitVoteSituation" name="limitVoteSituation" col="7"
                              value="${relPsn.limitVoteSituation}">
                     </bf:text>
                 </bc:col>
             </bf:formGroup>--%>
            <%-- <bf:formGroup>
                 <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                     <bc:button id="y" type="primary" text="返回" icon="arrow-circle-left">
                         <be:event>
                             var value='${op}';
                             &lt;%&ndash;alert(value);&ndash;%&gt;
                             if(value == "0"){
                             window.location = '${base}/fil/filRelPsn/list';
                             }else {
                             window.location = '${base}/fil/filRelPsn/relSinQuery';
                             }

                         </be:event>
                     </bc:button>
                 </div>
             </bf:formGroup>--%>
        </bc:row>
        </bf:form>
        </bc:ibox>
    </div>
    <div>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"
                id="hiddenBtn"
                style="display: none">
            文件上传按钮
        </button>
        <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalCenterTitle">文件上传</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form method="post" enctype="multipart/form-data" id="uploadForm"
                              style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
                            <div class="form-group" style="text-align: center;vertical-align: middle;">
                                <label for="file"></label>
                                <img src="../../static/img/sendfile.png"/>
                                <a href="javascript:;" class="file">选择文件
                                    <input type="file" class="form-control" id="file" name="file" multiple
                                           style="z-index:1;padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
                                </a>
                            </div>

                        </form>
                        <div class="form-group"
                             style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
                            <button type="submit" class="btn btn-primary" id="upload">上传</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br:js pack="form"/>
    <script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
    <div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
    <be:processEvent/>
    <script type="text/javascript">
        clear()

        function exportIn() {
            // alert(localStorage.vals);
            if (localStorage.vals == "1") {
                $("#hiddenBtn").click();
            }
        }

        function clear() {
            localStorage.clear()
            // console.log('111')
        }

        function getIdSelections() {
            return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
                return row.pid//返回数据行中的id值
            });
        }

        function getAllIdSelections() {
            return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
                return row.pid//返回数据行中的id值
            });
        }

        //文件表所有选中的数据
        function getselectData() {
            return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
                return row//返回数据行中的id值
            });
        }

        $("#upload").click(function () {
            var obj = document.getElementById("file");
            for (var i = 0; i < obj.files.length; i++) {
                var temp = obj.files[i].name;
                var temArr = temp.split('.');
                var fileLast = temArr[temArr.length - 1];
                if (fileLast != 'pdf') {
                    alert("请上传pdf文件");
                    return;
                }
            }
            var mgerOrg = $('#mgerOrg').val();
            var formData = new FormData($('#uploadForm')[0]);
            formData.append("uploadAgency", mgerOrg);
            formData.append("uploadDepartment", mgerOrg);
            $.ajax({
                type: 'post',
                url: '${base}/fil/relFile/uploadFile', //上传文件的请求路径必须是绝对路劲
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                success: function (data) {
                    $("#close").click();
                    alert(data.msg);
                    if (data.success) {
                        //处理数据
                        var files = data.relFile;
                        var count = files.length;
                        //获取原有表单的数据
                        var rows = getData();
                        rows = rows.concat(files);
                        //构造表格显示数据
                        var total = rows.length;
                        var showData = {'total': total, 'rows': rows}
                        $('#relFileTable').bootstrapTable('load', showData);
                    }
                },
                error: function () {
                    $("#close").click();
                    alert("上传失败,请重试");
                }
            })
        });

        //表单提交数据
        $.fn.serializeJson = function () {
            var serializeObj = {};
            var array = this.serializeAll();
            $(array).each(function () {
                if (serializeObj[this.name]) {
                    if ($.isArray(serializeObj[this.name])) {
                        serializeObj[this.name].push(this.value);
                    } else {
                        serializeObj[this.name] = [serializeObj[this.name], this.value];
                    }
                } else {
                    serializeObj[this.name] = this.value;
                }
            });
            return serializeObj;
        };

        //文件表数据
        function getData() {
            return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
                return row
            });
        }

        //查看文件
        viewFile = function () {
            //选中的文件
            var pid = getIdSelections();
            //获得该条数据
            var row = getselectData();
            var fileName = row[0].fileName;
            if (pid == '' || pid == undefined) {
                alert("请选中一个文件");
                return;
            }
            if (pid.indexOf(',') != -1) {
                alert("只能选中一个文件");
                return;
            }
            window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
                + encodeURIComponent('${base}/fil/relFile/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
        }
        //移除文件
        removeDoc = function () {
            var rows = getData();//删除前数据
            var pids = getIdSelections();//删除数据主键
            var selectRows = getselectData();//选中的数据
            if (selectRows.length == 0) {
                alert("请先选中数据");
                return;
            }
            if (confirm("确认删除？")) {
                $.ajax({
                    type: 'get',
                    url: '${base}/fil/relFile/removeFile/' + pids,
                    dataType: 'json',
                    success: function (res) {
                        alert(res.msg);
                        if (res.success) {
                            //从数据中移除
                            for (var i = 0; i < selectRows.length; i++) {
                                var row = selectRows[i];
                                rows.splice(rows.indexOf(row), 1);
                            }
                            var showData = {'total': rows.length, 'rows': rows};
                            $('#relFileTable').bootstrapTable('load', showData);
                        }
                    }

                })
            }
        }


        function affiliateInformation() {
            var pid = $("#pid").val();
            window.location = '${base}/bus/relPsn/affiliateList/' + pid + '?op=2';
        }

        function orgRelInformation() {
            var pid = $("#pid").val();
            window.location = '${base}/bus/relPsn/psnRelOrgList/' + pid + '?op=2';
        }

        function proRelInformation() {
            var pid = $("#pid").val();
            window.location = '${base}/bus/relPsn/proOfPsnList/' + pid + '?op=2';
        }


        $(document).ready(function () {
            var opt = $('#opt').val();
            if (opt != 'edit') {
                document.getElementById("psnRelInformation").setAttribute("disabled", true);
                document.getElementById("orgRelInformation").setAttribute("disabled", true);
                document.getElementById("proRelInformation").setAttribute("disabled", true);
            }
        });

        //反显
        function addRelPsn() {
            <%--var identifyNumber = ${relPsn.identifyNumber}.val();--%>
            <%--var  parms = {--%>
            <%--    'identifyNumber' :identifyNumber--%>
            <%--}--%>
            $.ajax({
                url: "${base}/fil/filRelPsn/viewRelPsn",
                type: "get",
                data: parms,
                contentType: "application/json; charset=utf-8",
                dataType: "json",

                success: function (data) {
                    console.log(data)
                    if (data.size() > 0) {
                        $("#name").val(data[0].name)
                        <%--$("#residentialAddress").value="${relPsn.residentialAddress};--%>
                    }
                }
            })
        }

        //查询反显
        $("#identifyNumber").blur(function () {
            var identifyNumber = $(this).val();
            if (identifyNumber != '' && identifyNumber != undefined) {
                $.ajax({
                    type: 'GET',
                    url: '${base}/fil/filRelPsn/selectRelPsn',
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
                            /*if (data[0].name != '' && data[0].name != undefined) {
                                localStorage.name = data[0].name;
                            } else {
                                localStorage.name = ''
                            }
                            if (data[0].identifyType != '' && data[0].identifyType != undefined) {
                                localStorage.identifyType = data[0].identifyType;
                            } else {
                                localStorage.identifyType = ''
                            }
                            if (data[0].identifyNumber != '' && data[0].identifyNumber != undefined) {
                                localStorage.identifyNumber = data[0].identifyNumber;
                            } else {
                                localStorage.identifyNumber = ''
                            }
                            // localStorage.toPid = data[0].pid
                            // localStorage.toType = data[0].type
                            $("#name").val(data[0].name)
                            if (data[0].identifyNumber != '' && data[0].identifyNumber != undefined) {
                                $("#identifyNumber").val(data[0].identifyNumber)
                            } else {
                                $("#identifyNumber").val('')
                            }
                            if (data[0].identifyType != '' && data[0].identifyType != undefined) {
                                if (data[0].identifyType == 'UNIFIEDSOCIALCREDITCODE') {
                                    var str = "<option value=" + data[0].identifyType + ">统一社会信用代码</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'MILITARYID') {
                                    var str = "<option value=" + data[0].identifyType + ">军官证</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'PASSPORT') {
                                    var str = "<option value=" + data[0].identifyType + ">护照</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'IDCARD') {
                                    var str = "<option value=" + data[0].identifyType + ">居民身份证</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'PERMANENTRESIDENT') {
                                    var str = "<option value=" + data[0].identifyType + ">居民户口簿</option>";
                                    $("#selectit").append(str);
                                } else if (data[0].identifyType == 'OTHER') {
                                    var str = "<option value=" + data[0].identifyType + ">其他证件</option>";
                                    $("#selectit").append(str);
                                }
                            } else {
                                var str = "<option value='0'>无</option>";
                                $("#identifyType").append(str);
                            }
                            $("#identifyNumber").val(data[0].identifyNumber)*/
                        } /*else {
                        $.messager.alert('提示', '请添加关联方。', 'warning', function () {
















































































                        <%--window.location = '${base}/workflow/process/runingList';--%>
                        });

                        $("#identifyNumber").val('')
                        $("#identifyType").val('')
                        // $("#selectit option[value='0']").attr("selected", true);
                        var str = "<option value='0'>无</option>";
                        $("#identifyNumber").append(str);
                    }*/

                    }
                });
            }

        })
    </script>
</body>
</html>
