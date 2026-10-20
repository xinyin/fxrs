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

        function show() {
            var value = '${op}';
            if (value == "view") {
                $('#save').css('display', 'none');//保存按钮
                $('#name').attr('disabled', 'true');//姓名
                $('#sex').attr('disabled', 'true');//性别
                $('#identifyType').attr('disabled', 'true');//证件类型
                $('#identifyNumber').attr('disabled', 'true');//证件号码
                $('#domesticOrForeign').attr('disabled', 'true');//境内外标识
                $('#mobile').attr('disabled', 'true');//移动电话
                $('#email').attr('disabled', 'true');//邮箱
                $('#unitAddress').attr('disabled', 'true');//单位地址
                $('#workUnits').attr('disabled', 'true');
                $('#regulatorYbj').attr('disabled', 'true');
                $("[name=birthdate]").attr("readonly", "readonly");//出生日期
                $('#chk').attr('disabled', 'true');
                $('#chk1').attr('disabled', 'true');
                $(".input-group-addon").off();
            }
        }
    </script>
    <x:jsLib/>
    <style>
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
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="op" name="op" value="${op}"/>
            <bf:hidden id="pid" name="pid" value="${relPsn.pid}"/>
            <bf:hidden id="status" name="status" value="${relPsn.status}"/>
            <bf:hidden id="relSerial" name="relSerial" value="${relPsn.relSerial}"/>
            <bf:hidden id="recordNumber" name="recordNumber" value="${relPsn.recordNumber}"/>
            <div class="boxse">
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label cls="required" text="姓名" col="5" formControl="true"/>
                        <bf:text id="name" name="name" col="7" value="${relPsn.name}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="证件类型" col="5" formControl="true"/>
                        <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relPsn.identifyType}">
                            <bf:option text="请选择" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label cls="required" text="证件号码" col="5" formControl="true"/>
                        <bf:text id="identifyNumber" name="identifyNumber" col="7"
                                 value="${relPsn.identifyNumber}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label cls="required" text="境内/境外" col="5" formControl="true"/>
                        <bf:checkGroup id="domesticOrForeign" name="domesticOrForeign" col="7"
                                       value="${relPsn.domesticOrForeign}">
                            <bf:radio id="chk" value="1" label="境内" checked="true"/>
                            <bf:radio id="chk1" value="2" label="境外"/>
                        </bf:checkGroup>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="性别" col="5" formControl="true"/>
                        <bf:select id="sex" name="sex" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Sex" value="${relPsn.sex}">
                            <bf:option text="请选择" value=""></bf:option>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="出生日期" col="5" formControl="true"/>
                        <bf:datebox name="birthdate" id="birthdate" col="7" format="date" disabled="${op == 'view'}"
                                    value="${relPsn.birthdate}">
                        </bf:datebox>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label text="工作单位" col="5" formControl="true"/>
                        <bf:text id="workUnits" name="workUnits" col="7" value="${relPsn.workUnits}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="单位地址" col="5" formControl="true"/>
                        <bf:text id="unitAddress" name="unitAddress" col="7" value="${relPsn.unitAddress}">
                            <bv:stringLength max="100" message="单位地址长度不能超过100"/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="移动电话" col="5" formControl="true"/>
                        <bf:text id="mobile" name="mobile" col="7" value="${relPsn.mobile}">
                            <bv:stringLength min="11" max="11" message="请输入11位数字" trim="true"/>
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup type="success">
                    <bc:col col="4">
                        <bc:label text="邮箱" col="5" formControl="true"/>
                        <bf:text id="email" name="email" col="7" value="${relPsn.email}">
                            <bv:stringLength max="32" message="邮箱长度不能超过32位"/>
                        </bf:text>
                    </bc:col>
                    <%--<bc:col col="4">
                        <bc:label text="统计口径" col="5" formControl="true"/>
                        <bf:text id="regulatorYbj" name="regulatorYbj" col="7"
                                 disabled='true' value="银保监会"/>
                    </bc:col>--%>
                    <bc:col col="4">
                        <bc:label text="是否有效" col="5" formControl="true"/>
                        <bf:text id="effectRelation" name="effectRelation" col="7" value="${relPsn.effectRelation}">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
            </div>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                    <bc:button id="save" type="danger" text="保存" icon="save">
                        <be:event>
                            save();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/relPsn/list';
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
    $(document).ready(function () {
        $("#effectRelation").hide();
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
    });

    //保存校验
    function save() {
        //身份信息校验
        var identifyNumber = $('#identifyNumber').val();
        var identifyType = $('#identifyType').val();
        if (identifyNumber != null && identifyType != null &&
            identifyNumber !== "" && identifyType !== "") {
            if (checkIDCard() === false) {//校验
                return false;
            }
            /*var identifyNumberVal = "

            ${relPsn.identifyNumber}";
            var identifyTypeVal = "

            ${relPsn.identifyType}";
            if (!(identifyNumber == identifyNumberVal && identifyType == identifyTypeVal)) {
                $.ajax({
                    type: 'GET',
                    url: '

            ${base}/bus/relPsn/selectIdentifyNumber',
                    data: {
                        "identifyNumber": identifyNumber,
                        "identifyType": identifyType,
                    },
                    dataType: 'json',
                    success: function (data) {
                        if (data) {
                            $.messager.alert('提示', '关联方已存在', 'warning');
                            return ;
                        }
                    }
                });
            }*/
        }
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            var formData = $('#editForm').serializeAll();
            $.mask.post('${base}/bus/relPsn/save', formData, function (data) {
                if (data.success) {
                    $.messager.alert('提示', data.msg, 'info', function () {
                        window.location = '${base}/bus/relPsn/list';
                    });
                } else if (data.error) {
                    $.messager.alert('错误', data.msg, 'error');
                }
                if (data.failed) {
                    $.messager.alert('错误', data.msg, 'warning');
                }
            });
        }
    }


    function checkIDCard() {
        var idType = $("#identifyType").val();
        var identifyNumber = $("#identifyNumber").val();
        //验证格式
        if (idType != "5") {//其他
            if (!(8 <= identifyNumber.length && identifyNumber.length <= 18)) {
                $.messager.alert('提示', '请检查证件号码', 'warning');
                return false;
            }
        } else {
            if (!(5 <= identifyNumber.length && identifyNumber.length <= 18)) {
                $.messager.alert('提示', '请检查证件号码', 'warning');
                return false;
            }
        }
        //邮箱校验
        var email11 = $('#email').val();
        if (email11 != '' && email11 != null) {
            var myreg = /^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/;
            if (!myreg.test(email11)) {
                $.messager.alert('提示', '请检查邮箱格式', 'warning');
                return false;
            }
        }
        //判断是否是居民身份证
        if (idType == "1") {
            // 格式的正则
            var idcard_patter = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
            // 正则判断正确后进行更加详细的判断
            if (idcard_patter.test(identifyNumber)) {
                var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
                var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
                for (var i = 0; i < 17; i++) {
                    idCardWiSum += identifyNumber.substring(i, i + 1) * idCardWi[i];
                }
                var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
                var idCardLast = identifyNumber.substring(17);//得到最后一位身份证号码
                if (idCardMod == 2) {
                    //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                    if (!idCardLast == "X" || !idCardLast == "x") {
                        $.messager.alert('提示', '身份证号码错误', 'warning');
                        return false;
                    }
                }
                //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                if (!idCardLast == idCardY[idCardMod]) {
                    $.messager.alert('提示', '身份证号码错误', 'warning');
                    return false;
                }
            } else {
                $.messager.alert('提示', '身份证号码错误', 'warning');
                return false;
            }

        }
    }
</script>
</body>
</html>
