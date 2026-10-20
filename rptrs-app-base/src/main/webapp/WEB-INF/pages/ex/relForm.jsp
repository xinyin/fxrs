<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <x:jsLib/>
    <br:css pack="form"/>
    <title>关联关系查看</title>
    <script>
        $(document).ready(function () {
            $('#aboutAandP').css('display', 'none')

        });

        // window.onload=show;
        function isShareholder(val) {
            if (val == '1') {
                $('#aboutAandP').css('display', 'block')
                localStorage.vals = val;
            }
            if (val == '0') {
                $('#aboutAandP').css('display', 'none')
                localStorage.vals = val;
                $('#shareholdingProportion').val('')
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

        .form-group .col-xs-12 {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
            width: 10%;
        }

        .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }
    </style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
    <bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="pid" name="pid" value="${rel.pid}"/>
            <bf:hidden id="fromPid" name="fromPid" value="${rel.fromPid}"/>
            <bf:hidden id="toPid" name="toPid" value="${rel.toPid}"/>
            <div class="boxse">
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label cls="required" text="本层关联方名称" col="5" formControl="true"/>
                        <bf:text id="fromMemo" name="fromMemo" col="7"  value="${rel.fromMemo}" disabled="true">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="本层关联方代码" col="5" formControl="true"/>
                        <bf:text id="fromIdentifyNumber" name="fromIdentifyNumber" col="7" disabled="true" value="${rel.fromIdentifyNumber}">
                            <bv:stringLength min="8" max="18" trim="true"/>
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label cls="required" text="本层关联方类型" col="5" formControl="true"/>
                        <bf:select id="fromType" name="fromType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-OrgOrPsn" disabled="true" value="${rel.fromType}">
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label cls="required" text="前一层关联方名称" col="5" formControl="true"/>
                        <bf:text id="toMemo" name="toMemo" col="7"  disabled="true" value="${rel.toMemo}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="前一层关联方代码" col="5" formControl="true"/>
                        <bf:text id="toIdentifyNumber" name="toIdentifyNumber" col="7" disabled="true" value="${rel.toIdentifyNumber}">
                            <bv:stringLength min="8" max="18" trim="true"/>
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label cls="required" text="前一层关联方类型" col="5" formControl="true"/>
                        <bf:select id="toType" name="toType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-OrgOrPsn" disabled="true" value="${rel.toType}"><bv:notEmpty/>
                        </bf:select>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup type="success">

                    <bc:col col="4">
                        <bc:label cls="required" text="与前一层的关联关系" col="5" formControl="true"/>
                        <bf:select id="relPsnOrg" name="relPsnOrg" col="7" dataSource="custom" disabled="true"
                                   url="sys/dictItem/getDictItems/BUS-RelZZD" value="${rel.relPsnOrg}"><bv:notEmpty/>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="与前一层之间是否有权益份额" col="5" formControl="true"/>
                        <bf:select id="equityShare" name="equityShare" col="7" dataSource="custom"  disabled="true"
                                   url="sys/dictItem/getDictItems/BUS-isNo" value="${rel.equityShare}"><bv:notEmpty/>
                        </bf:select>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label cls="required" text="持有比例（%）" col="5" formControl="true"/>
                        <bf:text id="shareholdingRatio" name="shareholdingRatio" col="7"  disabled="true" value="${rel.shareholdingRatio}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label text="股权质押比例（%）" col="5" formControl="true"/>
                        <bf:text id="pledgeRatio" name="pledgeRatio" col="7" disabled="true" value="${rel.pledgeRatio}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="该关系是否仍然有效" col="5" formControl="true"/>
                        <bf:select id="isValid" name="isValid" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-isNo" disabled="true" value="${rel.isValid}">
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label cls="required" text="生效日期" col="5" formControl="true"/>
                        <bf:datebox name="activeTime" id="activeTime" col="7" format="date" disabled="true" value="${rel.activeTime}">
                            <bv:notEmpty/>
                        </bf:datebox>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label text="失效日期" col="5" formControl="true"/>
                        <bf:datebox name="invalidTime" id="invalidTime" col="7" format="date" disabled="true" value="${rel.invalidTime}">
                        </bf:datebox>
                    </bc:col>
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="关系说明" col="3" formControl="true"/>
                        <bf:text id="relStatement" name="relStatement" col="9" rows="3" multiple="true" disabled="true" value="${rel.relStatement}"><bv:stringLength max="200" message="请将字数控制在200以内！"/><bv:notEmpty/></bf:text>
                    </bf:formGroup>
                </bf:formGroup>
            </div>
            <div id="returnBtn">
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-5">
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.history.go(-1);
                            </be:event>
                        </bc:button>
                    </div>
                </bf:formGroup>
            </div>
        </bf:form>
    </bc:ibox>
</div>
</div>
<div>
    <br:js pack="form"/>
        <script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
        <div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
    <be:processEvent/>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".required").each(function () {
                var $this = $(this);
                $(this).html("<font color='red'>✶</font>" + $this.html());
            });
        });
        /**是否显示返回按钮*/
        $(function (){
            var hidden = ${hidden};
            if (hidden != undefined || hidden != null) {
                //隐藏按钮
                $("#returnBtn").css("display","none");//隐藏div
                //$("#returnBtn").css("display","block");//显示div
            }

            // $('#returnBtn')
        })
    </script>
</body>
</html>