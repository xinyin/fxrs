<%@ taglib prefix="bev" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ taglib prefix="be" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
    <br:css pack="form"/>
    <title>RelOrg</title>
    <script>
        $(document).ready(function () {
            show();
        });

        function isShareholder(val) {
            if (val == '1') {
                $('#aboutAandP').css('display', 'block')
                document.getElementById('chk').click();
                localStorage.vals = val;


            }
            if (val == '0') {
                $('#aboutAandP').css('display', 'none')
                document.getElementById('chk1').click();
                localStorage.vals = val;
            }
            return val;
        }

        $(document).ready(function () {
            show();
        });

        function show() {
            var val = '${relOrg.isShareholder}';
            if (val == '1') {
                $('#aboutAandP').css('display', 'block')

                localStorage.vals = val;
            }
            if (val == '0') {
                $('#aboutAandP').css('display', 'none')
            }
            var val1 = '${relOrg.isNoShareVote}';
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
            var val2 = '${relOrg.isMainGd}';
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

        // alert("进入")
    </script>
    <x:jsLib/>
    <style>
        .boxse .form-group .col-xs-5 {
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

        .ismainhoder {
            display: none;
        }

        .voteProportion {
            display: none;
        }
    </style>


</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="relOrgBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${relOrg.pid}"/>
            <bf:hidden id="op" name="op" value="${op}"/>
            <bf:formGroup>
                <div class="col-sm-6">
                    <bc:button type="info" text="基本信息">
                        <be:event>
                            <%--                            basicInformation();--%>
                        </be:event>
                    </bc:button>
                    <bc:button id="psnRelInformation" type="info" text="关系自然人信息">
                        <be:event>
                            affiliateInformation();
                        </be:event>
                    </bc:button>
                    <bc:button id="listAffi" type="info" text="关系机构信息">
                        <be:event>
                            orgRelInformation();
                        </be:event>
                    </bc:button>
                    <bc:button id="proRelInformation" type="info" text="关系金融产品">
                        <be:event>
                            proRelInformation();
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
            <div class="boxse">
                <bf:formGroup hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="关联方名称" col="4" formControl="true"/>
                        <bf:text id="orgName" name="orgName" col="8" value="${relOrg.orgName}"
                                 helpText="${relOrg2.orgName}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="证件类型" col="4" formControl="true"/>
                        <bf:select id="identifyType" name="identifyType" col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-ORGIdentifyType" value="${relOrg.identifyType}">
                        </bf:select>
                        <c:if test="${not empty relOrg2.identifyType}">
                            <div class="col-md-8 col-md-offset-4">
                                <bf:select id="identifyType1" name="identifyType1" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-ORGIdentifyType"
                                           value="${relOrg2.identifyType}">
                                </bf:select>
                            </div>
                        </c:if>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="证件号码" col="4" formControl="true"/>
                        <bf:text id="uniscCode" name="uniscCode" col="8" value="${relOrg.uniscCode}"
                                 helpText="${relOrg2.uniscCode}">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="境内/境外" col="4" formControl="true"/>
                        <bf:select id="domesticOrForeign" name="domesticOrForeign" col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-DomesticOrForeign"
                                   value="${relOrg.domesticOrForeign}">
                        </bf:select>
                        <c:if test="${not empty relOrg2.domesticOrForeign}">
                            <div class="col-md-8 col-md-offset-4">
                                <bf:select id="domesticOrForeign1" name="domesticOrForeign1" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" disabled="true"
                                           value="${relOrg2.domesticOrForeign}">
                                </bf:select>
                            </div>
                        </c:if>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="企业类型" col="4" formControl="true"/>
                        <bf:select id="orgType" name="orgType" col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-CompanyType" value="${relOrg.orgType}">
                        </bf:select>
                        <c:if test="${not empty relOrg2.orgType}">
                            <div class="col-md-8 col-md-offset-4">
                                <bf:select id="orgType1" name="orgType1" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-CompanyType" disabled="true"
                                           value="${relOrg2.orgType}">
                                </bf:select>
                            </div>
                        </c:if>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="机构状态" col="4" formControl="true"/>
                        <bf:select id="status" name="status" col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Status" value="${relOrg.status}">
                        </bf:select>
                        <c:if test="${not empty relOrg2.status}">
                            <div class="col-md-8 col-md-offset-4">
                                <bf:select id="status1" name="status1" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-Status" disabled="true"
                                           value="${relOrg2.status}">
                                </bf:select>
                            </div>
                        </c:if>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="注册资本(万元/人民币)" col="4" formControl="true"/>
                        <bf:text id="regCapital" name="regCapital" col="8" value="${relOrg.regCapital}"
                                 helpText="${relOrg2.regCapital}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="注册资本币种代码" col="4" formControl="true"/>
                        <bf:select id="regCapitalCurrency" name="regCapitalCurrency" col="8"
                                   dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Currency"
                                   value="${relOrg.regCapitalCurrency}">
                        </bf:select>
                        <c:if test="${not empty relOrg2.regCapitalCurrency}">
                            <div class="col-md-8 col-md-offset-4">
                                <bf:select id="regCapitalCurrency1" name="regCapitalCurrency1" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-Currency" disabled="true"
                                           value="${relOrg2.regCapitalCurrency}">
                                </bf:select>
                            </div>
                        </c:if>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="注册地址" col="4" formControl="true"/>
                        <bf:text id="regAddress" name="regAddress" col="8"
                                 helpText="${relOrg2.regAddress}" value="${relOrg.regAddress}">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="营业范围" col="4" formControl="true"/>
                        <bf:text id="businessScope" name="businessScope" col="8" value="${relOrg.businessScope}"
                                 helpText="${relOrg2.businessScope}">
                        </bf:text>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="法定代表人" col="4" formControl="true"/>
                        <bf:text id="legalPerson" name="legalPerson" col="8" value="${relOrg.legalPerson}"
                                 helpText="${relOrg2.legalPerson}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="法定代表人证件类型" col="4" formControl="true"/>
                        <bf:select id="legalIdentifyType" name="legalIdentifyType" col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-IdentifyType" disabled="true"
                                   value="${relOrg.legalIdentifyType}">
                        </bf:select>
                        <c:if test="${not empty relOrg2.legalIdentifyType}">
                            <div class="col-md-8 col-md-offset-4">
                                <bf:select id="legalIdentifyType" name="legalIdentifyType" col="7" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-IdentifyType"
                                           value="${relOrg2.legalIdentifyType}">
                                </bf:select>
                            </div>
                        </c:if>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="法定代表人证件号码" col="4" formControl="true"/>
                        <bf:text id="legalIdentifyNumber" name="legalIdentifyNumber" col="8" disabled="true"
                                 value="${relOrg.legalIdentifyNumber}" helpText="${relOrg2.legalIdentifyNumber}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="机构名称" col="4" formControl="true"/>
                        <bf:select id="mgerOrg" name="mgerOrg" col="8" dataSource="custom"
                                   url="sys/unit/selectUnit" value="${relOrg.mgerOrg}" disabled="true">
                        </bf:select>
                        <c:if test="${not empty relOrg2.mgerOrg}">
                            <div class="col-md-8 col-md-offset-4">
                                <bf:select id="mgerOrg1" name="mgerOrg1" dataSource="custom"
                                           url="sys/unit/selectUnit" disabled="true"
                                           value="${relOrg2.mgerOrg}">
                                </bf:select>
                            </div>
                        </c:if>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="集团证件号" col="5" formControl="true"/>
                        <bf:text id="memGroup" name="memGroup" col="7" value="${relOrg.memGroup}">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bc:col col="4">
                    <bc:label text="是否为股东" col="4" formControl="true"/>
                    <bf:checkGroup name="isShareholder" col="8" value="${relOrg.isShareholder}">
                        <bf:radio value="1" id="chk" label="是" click="checksh(e.target.value)"/>
                        <bf:radio value="0" id="chk1" label="否" click="checksh(e.target.value)"/>
                    </bf:checkGroup>
                    <c:if test="${not empty relOrg2.isShareholder}">
                        <div class="col-md-8 col-md-offset-4">
                            <bf:checkGroup name="isShareholder1" value="${relOrg2.isShareholder}">
                                <bf:radio value="1" id="chk" label="是" click="checksh(e.target.value)"/>
                                <bf:radio value="0" id="chk1" label="否" click="checksh(e.target.value)"/>
                            </bf:checkGroup>
                        </div>
                    </c:if>
                </bc:col>
                <bf:formGroup hrp="bottom" id="aboutAandP">
                    <bc:col col="4">
                        <bc:label text="持股占比(%)" col="4" formControl="true"/>
                        <bf:text id="shareholdingProportion" name="shareholdingProportion" col="8"
                                 value="${relOrg.shareholdingProportion}"
                                 helpText="${relOrg2.shareholdingProportion}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="质押股权比例(%)" col="5" formControl="true"/>
                        <bf:text id="pledgeRatio" name="pledgeRatio" col="7" disabled="true"
                                 value="${relOrg.pledgeRatio}" helpText="${relOrg2.pledgeRatio}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                </bf:formGroup>


                    <%-- <bf:formGroup hrp="bottom">
                         <bc:col col="4">
                             <bc:label text="关联关系类别" col="5" formControl="true"/>
                             <bf:select id="relCategory" name="relCategory" col="7" dataSource="custom"
                                        url="sys/dictItem/getDictItems/BUS-RelCategory"
                                        includes="B,C,E" value="${relOrg.relCategory}">
                                 <be:event type="select">
                                     setRelCategorySub(e.target.value);
                                 </be:event>
                             </bf:select>
                             <c:if test="${not empty relOrg2.relCategory}">
                                 <div class="col-md-8 col-md-offset-4">
                                     <bf:select id="relCategory" name="relCategory" dataSource="custom"
                                                url="sys/dictItem/getDictItems/BUS-RelCategory"
                                                includes="B,C,E" value="${relOrg2.relCategory}">
                                     </bf:select>
                                 </div>
                             </c:if>
                         </bc:col>
                         <bc:col col="4">
                             <bc:label cls="required" text="关系" col="5" formControl="true"/>
                             <bf:select id="relCategorySub" name="relCategorySub" col="7" dataSource="custom"
                                        url="sys/dictItem/getDictItems/BUS-Rel" value="${relOrg.relCategorySub}">
                                 <bv:notEmpty/>
                             </bf:select>
                             <c:if test="${not empty relOrg2.relCategorySub}">
                                 <div class="col-md-8 col-md-offset-4">
                                     <bf:select id="relCategorySub" name="relCategorySub" dataSource="custom"
                                                url="sys/dictItem/getDictItems/BUS-Rel" value="${relOrg2.relCategorySub}">
                                     </bf:select>
                                 </div>
                             </c:if>
                         </bc:col>
                         <bc:col col="4">
                             <bc:label cls="required" text="该关系是否有效" col="5" formControl="true"/>
                             <bf:select id="isValid" name="isValid" col="7" dataSource="custom"
                                        url="sys/dictItem/getDictItems/BUS-valid"
                                        value="${relOrgVo.isValid}" disabled="true">
                             </bf:select>
                             <c:if test="${not empty relOrg2.isValid}">
                                 <div class="col-md-8 col-md-offset-4">
                                     <bf:select id="isValid" name="isValid" dataSource="custom"
                                                url="sys/dictItem/getDictItems/BUS-valid"
                                                value="${relOrgVo.isValid}" disabled="true">
                                     </bf:select>
                                 </div>
                             </c:if>
                         </bc:col>
                     </bf:formGroup>
                     <bf:formGroup hrp="bottom">
                         <bc:col col="4">
                             <bc:label cls="required" text="生效日期" col="5" formControl="true"/>
                             <bf:datebox name="activeTime" id="activeTime" col="7" format="date"
                                         value="${relOrgVo.activeTime}" disabled="ture">
                             </bf:datebox>
                             <c:if test="${not empty relOrg2.activeTime}">
                                 <div class="col-md-8 col-md-offset-4">
                                     <bf:datebox name="activeTime" id="activeTime" col="7" format="date"
                                                 value="${relOrg2.activeTime}" disabled="ture">
                                     </bf:datebox>
                                 </div>
                             </c:if>
                         </bc:col>
                         <bc:col col="4">
                             <bc:label text="失效日期" col="5" formControl="true"/>
                             <bf:datebox name="invalidTime" id="invalidTime" col="7" format="date"
                                         value="${relOrgVo.invalidTime}" disabled="ture">
                             </bf:datebox>
                             <c:if test="${not empty relOrg2.invalidTime}">
                                 <div class="col-md-8 col-md-offset-4">
                                     <bf:datebox name="invalidTime" id="invalidTime" col="7" format="date"
                                                 value="${relOrg2.invalidTime}" disabled="ture">
                                     </bf:datebox>
                                 </div>
                             </c:if>
                         </bc:col>



                         <bc:col col="4">
                             <bc:label text="统计口径" col="4" formControl="true"/>
                             <bf:select id="regulatorOth1" name="regulatorOth1" col="8" dataSource="custom"
                                        url="sys/dictItem/getDictItems/BUS-Caliber" value="${relOrg.regulatorOth1}">
                             </bf:select>
                             <c:if test="${not empty relOrg2.regulatorOth1}">
                                 <div class="col-md-8 col-md-offset-4">
                                     <bf:select id="regulatorOth2" name="regulatorOth2" dataSource="custom"
                                                url="sys/dictItem/getDictItems/BUS-Caliber" disabled="true"
                                                value="${relOrg2.regulatorOth1}">
                                     </bf:select>
                                 </div>
                             </c:if>
                         </bc:col>



                     </bf:formGroup>
                     <bf:formGroup hrp="bottom">


                         <bc:col col="4">
                             <bc:label text="注册资本(万元/人民币)" col="4" formControl="true"/>
                             <bf:text id="regCapital" name="regCapital" col="8" value="${relOrg.regCapital}"
                                      helpText="${relOrg2.regCapital}">
                             </bf:text>
                         </bc:col>

                         <bc:col col="4">
                             <bc:label text="黑名单标识" col="4" formControl="true"/>
                             <bf:select id="isBlack" name="isBlack" col="8" dataSource="custom"
                                        url="sys/dictItem/getDictItems/BUS-isNo" value="${relOrg.isBlack}">
                             </bf:select>
                             <c:if test="${not empty relOrg2.isBlack}">
                                 <div class="col-md-8 col-md-offset-4">
                                     <bf:select id="isBlack1" name="isBlack1" dataSource="custom"
                                                url="sys/dictItem/getDictItems/BUS-isNo" disabled="true"
                                                value="${relOrg2.isBlack}">
                                     </bf:select>
                                 </div>
                             </c:if>
                         </bc:col>
                     </bf:formGroup>
                     <bf:formGroup hrp="bottom">
                         <bc:col col="4">
                             <bc:label text="加入黑名单原因" col="4" formControl="true"/>
                             <bf:text id="blackReason" name="blackReason" col="8" value="${relOrg.blackReason}"
                                      helpText="${relOrg2.blackReason}">
                             </bf:text>
                         </bc:col>




                     </bf:formGroup>
                     <bf:formGroup hrp="bottom">


                         <bc:col col="4">
                             <bc:label text="集团名称" col="4" formControl="true"/>
                             <bf:text id="groupName" name="groupName" col="8" value="${relPsn.groupName}"
                                      helpText="${relOrg2.groupName}">
                             </bf:text>
                         </bc:col>

                         <bc:col col="4">
                             <bc:label text="集团证件号" col="4" formControl="true"/>
                             <bf:text id="memGroup" name="memGroup" col="8" value="${relPsn.memGroup}"
                                      helpText="${relOrg2.memGroup}">
                             </bf:text>
                         </bc:col>
                     </bf:formGroup>
                     <bf:formGroup hrp="bottom">
                     <div id=ismainhoder class="ismainhoder">
                         <bc:col col="4">
                             <bc:label text="是否本行主要股东" col="4" formControl="true"/>
                             <bf:checkGroup name="ismainhoder" col="8" value="${relOrg.isMainGd}">
                                 <bf:radio value="1" label="是" id="ma"/>
                                 <bf:radio value="0" label="否" id="ma1" checked="true"/>
                                 &lt;%&ndash;<be:event>
                                     checkIsShareholder();  name="isshareholder"
                                 </be:event>&ndash;%&gt;
                             </bf:checkGroup>
                             <c:if test="${not empty relOrg2.isMainGd}">
                                 <div class="col-md-8 col-md-offset-4">
                                     <bf:checkGroup name="ismainhoder1" col="8" value="${relOrg2.isMainGd}">
                                         <bf:radio value="1" label="是" id="ma"/>
                                         <bf:radio value="0" label="否" id="ma1" checked="true"/>
                                         &lt;%&ndash;<be:event>
                                             checkIsShareholder();  name="isshareholder"
                                         </be:event>&ndash;%&gt;
                                     </bf:checkGroup>
                                 </div>
                             </c:if>
                         </bc:col>
                         <bc:col col="4">
                             <bc:label text="股权证编号" col="4" formControl="true"/>
                             <bf:text id="capitalNumber" name="capitalNumber" col="8" value="${relOrg.capitalNumber}"
                                      helpText="${relOrg2.capitalNumber}">
                             </bf:text>
                         </bc:col>
                         </bf:formGroup>
                         <bf:formGroup hrp="bottom">
                             <bc:col col="4">
                                 <bc:label text="股东账号" col="4" formControl="true"/>
                                 <bf:text id="shareholdersCode" name="shareholdersCode" col="8"
                                          value="${relOrg.shareholdersCode}"
                                          helpText="${relOrg2.shareholdersCode}">
                                 </bf:text>
                             </bc:col>
                             <bc:col col="4">
                                 <bc:label text="交易账户开户行" col="4" formControl="true"/>
                                 <bf:text id="openBank" name="openBank" col="8" value="${relOrg.openBank}"
                                          helpText="${relOrg2.openBank}">
                                 </bf:text>
                             </bc:col>
                             <bc:col col="4">
                                 <bc:label text="交易账户账号" col="4" formControl="true"/>
                                 <bf:text id="tradeAccount" name="tradeAccount" col="8" value="${relOrg.tradeAccount}"
                                          helpText="${relOrg2.tradeAccount}">
                                 </bf:text>
                             </bc:col>
                         </bf:formGroup>
                         <bf:formGroup hrp="bottom">
                             <bc:col col="4">
                                 <bc:label text="入股时间" col="4" formControl="true"/>
                                 <bf:datebox name="inTime" id="inTime" col="8" format="date" value="${relOrg.inTime}">
                                 </bf:datebox>
                                 <c:if test="${not empty relOrg2.inTime}">
                                     <div class="col-md-8 col-md-offset-4">
                                         <bf:datebox name="inTime1" id="inTime1" format="date" value="${relOrg2.inTime}">
                                         </bf:datebox>
                                     </div>
                                 </c:if>
                             </bc:col>
                             <bc:col col="4">
                                 <bc:label text="入股价格(元/股)" col="4" formControl="true"/>
                                 <bf:text id="inPrice" name="inPrice" col="8" value="${relOrg.inPrice}"
                                          helpText="${relOrg2.inPrice}">
                                 </bf:text>
                             </bc:col>
                             <bc:col col="4">
                                 <bc:label text="持股数量（股）" col="4" formControl="true"/>
                                 <bf:text id="holeShareAmount" name="holeShareAmount" col="8"
                                          value="${relOrg.holeShareAmount}"
                                          helpText="${relOrg2.holeShareAmount}">
                                 </bf:text>
                             </bc:col>
                         </bf:formGroup>
                         <bf:formGroup hrp="bottom">

                         <bc:col col="4">
                             <bc:label text="总冻结股数（股）" col="4" formControl="true"/>
                             <bf:text id="frozenShare" name="frozenShare" col="8"
                                      value="${relOrg.frozenShare}" disabled="true"
                                      helpText="${relOrg2.frozenShare}">
                             </bf:text>
                         </bc:col>
                         <bc:col col="4">
                             <bc:label text="总质押股数（股）" col="4" formControl="true"/>
                             <bf:text id="pledgedShare" name="pledgedShare" col="8"
                                      value="${relOrg.pledgedShare}" disabled="true"
                                      helpText="${relOrg2.pledgedShare}">
                             </bf:text>
                         </bc:col>
                     </div>
                     </bf:formGroup>--%>
            </div class="boxse" id="ismianhd">

            <%--<div id="tableDiv" class="ismainhoder" style="width: 95%;margin:0 auto">
                <bc:table id="relFileTable" idField="pid" url="${base}/fil/relFile" multiple="true"
                          selectFirst="false" queryUrl="queryList?relSerial=${relOrg.relSerial}">
                    &lt;%&ndash;                    <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>&ndash;%&gt;
                    &lt;%&ndash;                    <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>&ndash;%&gt;
                    <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
                    <bc:column field="pid" title="主键" width="100" visible="false"/>
                    <bc:column field="fileName" title="文件名称" width="100"/>
                    <bc:column field="fileUrl" title="文件路径" width="100" visible="false"/>
                    <bc:column field="fileType" title="文件类型" width="100"/>
                    <bc:column field="fileVersion" title="版本号" width="100" visible="false"/>
                    <bc:column field="approveStatus" title="操作状态" width="100" visible="false"/>
                    <bc:column field="fileSize" title="文件大小" width="100"/>
                    <bc:column field="relSerial" title="交易流水号" width="100" visible="false"/>
                    <bc:column field="uploadAgency" title="上传机构" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-GHOrg"/>
                    <bc:column field="uploadDepartment" title="上传部门" width="100"/>
                    <bc:column field="modifiedTime" title="上传时间" width="100"/>
                </bc:table>
            </div>
            <bc:col col="4">
                <bc:label text="是否具有非持股表决权" col="5" formControl="true"/>
                <bf:checkGroup name="isNoShareVote" col="7" value="${relOrg.isNoShareVote}">
                    <bf:radio id="isv" value="1" label="是" click="isNoShareVote(e.target.value)"/>
                    <bf:radio id="isv1" value="0" label="否" checked="true" click="isNoShareVote(e.target.value)"/>
                </bf:checkGroup>
            </bc:col>
            <div id="voteProportion" class="voteProportion">
                <bc:col col="4">
                    <bc:label text="表决权占比：" col="5" formControl="true"/>
                    <bf:text id="voteProportion" name="voteProportion" col="7"
                             value="${relOrg.voteProportion}">
                    </bf:text>
                </bc:col>
            </div>
            <bc:col col="4">
                <bc:label text="限制表决权情况：" col="5" formControl="true"/>
                <bf:text id="limitVoteSituation" name="limitVoteSituation" col="7"
                         value="${relPsn.limitVoteSituation}">
                </bf:text>
            </bc:col>
            <bf:formGroup>

            </bf:formGroup>--%>
        </bf:form>
    </bc:ibox>
</div>
</div>
<%--<div>--%>
<%--    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"--%>
<%--            id="hiddenBtn"--%>
<%--            style="display: none">--%>
<%--        文件上传按钮--%>
<%--    </button>--%>
<%--    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"--%>
<%--         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">--%>
<%--        <div class="modal-dialog modal-dialog-centered" role="document">--%>
<%--            <div class="modal-content">--%>
<%--                <div class="modal-header">--%>
<%--                    <h5 class="modal-title" id="exampleModalCenterTitle">文件上传</h5>--%>
<%--                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">--%>
<%--                        <span aria-hidden="true">&times;</span>--%>
<%--                    </button>--%>
<%--                </div>--%>
<%--                <div class="modal-body">--%>
<%--                    <form method="post" enctype="multipart/form-data" id="uploadForm"--%>
<%--                          style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">--%>
<%--                        <div class="form-group" style="text-align: center;vertical-align: middle;">--%>
<%--                            <label for="file"></label>--%>
<%--                            <img src="../../static/img/sendfile.png"/>--%>
<%--                            <a href="javascript:;" class="file">选择文件--%>
<%--                                <input type="file" class="form-control" id="file" name="file" multiple--%>
<%--                                       style="padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">--%>
<%--                            </a>--%>
<%--                        </div>--%>

<%--                    </form>--%>
<%--                    <div class="form-group"--%>
<%--                         style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">--%>
<%--                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>--%>
<%--                        <button type="submit" class="btn btn-primary" id="upload">上传</button>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">
    $(document).ready(function () {
        x();
    });
    <%--$(document).ready(function () {--%>
    <%--    show();--%>
    <%--});--%>

    <%--function show() {--%>
    <%--    var val = '${relOrg.isShareholder}';--%>
    <%--    if (val == '1') {--%>
    <%--        // alert("进入1")--%>
    <%--        $('#ismainhoder').css('display', 'block')--%>
    <%--        $('#tableDiv').css('display', 'block')--%>
    <%--        document.getElementById('chk1').checked = false--%>
    <%--        document.getElementById('chk').checked = true--%>
    <%--        localStorage.vals = val;--%>
    <%--    }--%>
    <%--    if (val == '0') {--%>
    <%--        // alert("进入0")--%>
    <%--        $('#ismainhoder').css('display', 'none')--%>
    <%--        $('#tableDiv').css('display', 'none')--%>
    <%--        localStorage.vals = val;--%>
    <%--        document.getElementById('chk1').checked = true--%>
    <%--        document.getElementById('chk').checked = false--%>
    <%--    }--%>

    <%--    var val1 = '${relOrg.isNoShareVote}';--%>
    <%--    if (val1 == '1') {--%>
    <%--        $('#voteProportion').css('display', 'block')--%>
    <%--        $('#voteProportion').css('display', 'block')--%>
    <%--        document.getElementById('isv').checked = true--%>
    <%--        document.getElementById('isv1').checked = false--%>
    <%--        localStorage.vals = val;--%>
    <%--    }--%>
    <%--    if (val1 == '0') {--%>
    <%--        $('#voteProportion').css('display', 'none')--%>
    <%--        $('#voteProportion').css('display', 'none')--%>
    <%--        document.getElementById('isv').checked = false--%>
    <%--        document.getElementById('isv1').checked = true--%>
    <%--        localStorage.vals = val;--%>
    <%--    }--%>
    <%--    //是否为主要股东--%>
    <%--    var val2 = '${relOrg.isMainGd}';--%>
    <%--    if (val2 == '1') {--%>
    <%--        // $('#voteProportion').css('display', 'block')--%>
    <%--        // $('#voteProportion').css('display', 'block')--%>
    <%--        document.getElementById('ma').checked = true--%>
    <%--        document.getElementById('ma1').checked = false--%>
    <%--        // localStorage.vals = val;--%>
    <%--    }--%>
    <%--    if (val2 == '0') {--%>
    <%--        document.getElementById('ma').checked = false--%>
    <%--        document.getElementById('ma1').checked = true--%>
    <%--    }--%>
    <%--}--%>

    function x() {
        localStorage.clear();
        // alert("进入");
        var value = '${op}';
        // alert(value);
        if (value != "0") {
            // $('#y').css('display', 'none');
            $('#x').css('display', 'none');
            $('#orgName').attr('disabled', 'true');//姓名
            $('#identifyType').attr('disabled', 'true');
            $('#uniscCode').attr('disabled', 'true');
            $('#regAddress').attr('disabled', 'true');
            $('#domesticOrForeign').attr('disabled', 'true');
            $('#mgerOrg').attr('disabled', 'true');
            $('#orgType').attr('disabled', 'true');
            $('#businessScope').attr('disabled', 'true');
            $('#status').attr('disabled', 'true');
            $('#regCapital').attr('disabled', 'true');
            $('#isBlack').attr('disabled', 'true');
            $('#blackReason').attr('disabled', 'true');
            $('#regCapitalCurrency').attr('disabled', 'true');
            $('#legalPerson').attr('disabled', 'true');
            $('#groupName').attr('disabled', 'true');
            $('#memGroup').attr('disabled', 'true');
            $('regulatorOth1').attr('disabled', 'true');

            $('#shareholdersCode').attr('disabled', 'true');
            $('#capitalNumber').attr('disabled', 'true');
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

            $('#regulatorOth1').attr('disabled', 'true');  //统计口径
            $('#chk').attr('disabled', 'true');  //本行主要股东
            $('#chk1').attr('disabled', 'true');  //本行主要股东
            $('#ma').attr('disabled', 'true');  //本行主要股东
            $('#ma1').attr('disabled', 'true');  //本行主要股东
            $('#isv').attr('disabled', 'true');  //具有非持股表决权
            $('#isv1').attr('disabled', 'true');  //具有非持股表决权
            $("[name=inTime]").attr("readonly", "readonly");
            $("[name=birthdate]").attr("readonly", "readonly");//出生日期

        }
        var opt = '${opt}';
        if (opt == "edit") {
            document.getElementById("mgerOrg").setAttribute("disabled", true);
        }

    }

    function exportIn() {
        $("#hiddenBtn").click();
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
        var formData = new FormData($('#uploadForm')[0]);
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

    function getAllIdSelections() {
        return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function getIdSelections() {
        return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    /*基本数据*/
    function basicInformation() {
        var value = '${op}';
        window.location.href = "${base}/bus/relOrg/edit/" + pid + '?op=2';
    }

    /*自然人*/
    function affiliateInformation() {
        var pid = $('#pid').val();
        window.location = '${base}/bus/relOrg/listAffi/' + pid + '?op=2';
    }

    je

    /*机构*/
    function orgRelInformation() {
        var pid = $('#pid').val();
        window.location = '${base}/bus/rel/list/' + pid + '?op=2';
    }

    /*金融机构*/
    function proRelInformation() {
        var pid = $('#pid').val();
        window.location = '${base}/bus/relOrg/proOfOrgList/' + pid + '?op=2';
    }


    /*function checkIsShareholder(){
        var oradio=document.getElementsByName("isshareholder");
        if(oradio[0].checked){
            alert("你好");l
        }
    }*/
    function checksh(val) {
        var opt = $('#opt').val();
        if (val == '1') {
            $('#ismainhoder').css('display', 'block')
            $('#tableDiv').css('display', 'block')
            localStorage.vals = val;
        }
        if (val == '0') {
            $('#ismainhoder').css('display', 'none')
            $('#tableDiv').css('display', 'none')
            localStorage.vals = val;
        }
        return val;
    }

    function isNoShareVote(val) {
        var opt = $('#opt').val();
        if (val == '1') {
            $('#voteProportion').css('display', 'block')
            // $('#voteProportion').css('display', 'block')
            localStorage.vals = val;
        }
        if (val == '0') {
            $('#voteProportion').css('display', 'none')
            // $('#voteProportion').css('display', 'none')
            localStorage.vals = val;
        }
        return val;
    }

    $("#holeShareAmount").blur(function () {
        var holeShareAmount = $('#holeShareAmount').val();
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
                        $("#frozenShare").val(0)
                        $("#pledgedShare").val(0)
                    } else {
                        $.messager.alert('提示', '计算失败', 'info');
                    }
                }
            });
        }
    })
</script>
</body>
</html>
