<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ taglib prefix="be" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>Limit</title>
    <style>
        .form-group .input-group.date,
        .form-group .select2{
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
<body class="gray-bg">
<div class="row">
    <bc:ibox id="productBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
<%--            <bf:hidden id="pid" name="pid" value="${product.pid}"/>--%>
            <input id="pid" name="pid" value="${product.pid}" hidden/>
            <bc:row cls="m-b">
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="交易主体" col="5" formControl="true"/>
                    <bf:select id="tranOrg" name="tranOrg" col="7" dataSource="custom"
                               url="bus/limit/getTranOrgItems" value="${product.tranOrg}" excludes="" disabled="true">
                        <bf:option text="请选择" value="%"></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label cls = "required" text="产品名称" col="5" formControl="true"/>
                    <bf:lookup id="proPath" name="proPath" col="7" title="业务级别名称"
                               url="${base}/bus/limit/proNameListTree" winId="productViewId"
                               propText="proName" propValue="level"
                               callback="result" onSelect="onSelect" text="${product.proPath}"
                               value="${product.proPath}" disabled="${opt == 'view' || opt=='edit'}">
                    </bf:lookup>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="交易类型" col="5" formControl="true"/>
                    <bf:select id="tradeType" name="tradeType" col="7" dataSource="custom"
                               url="bus/limit/getTradeTypeItems" value="${product.tradeType}" disabled="true">
                        <bv:notEmpty/>
                    </bf:select>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="业务类型" col="5" formControl="true"/>
                    <bf:select id="businessType" name="businessType" col="7" dataSource="custom"
                               url="bus/limit/getBuinessTypeItems" value="${product.businessType}" disabled="true">
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label cls = "required" text="预计额度（元）" col="5" formControl="true"/>
                    <bf:text id="preLimit" name="preLimit" col="7" value="${product.preLimit}"
                             disabled="${opt == 'view'}">
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="额度使用率" col="5" formControl="true"/>
                    <bf:text id="limitRate" name="limitRate" col="7" value="${product.limitRate}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="已用额度" col="5" formControl="true"/>
                    <bf:text id="pasLimit" name="pasLimit" col="7" value="${product.pasLimit}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="剩余额度" col="5" formControl="true"/>
                    <bf:text id="remLimit" name="remLimit" col="7" value="${product.remLimit}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                </bf:formGroup>
            </bc:row>
            <bf:formGroup hr="dashed" hrp="bottom">
                <div class="col-sm-12 col-sm-offset-5">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/bus/limit/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/bus/limit/list';
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
                            window.location = '${base}/bus/limit/list';
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
    var tradeType = $('#tradeType').val();
    var businessType = $('#businessType').val();
    $(document).ready(function () {


    });

    function onSelect() {
        var proName = $("input[name=proName]").val();
        // alert(proName);
        $.ajax({
            type: "post",
            url: "${base}/bus/limit/selectbuinessL",
            data: {"unitCodes": proName},
            success: function (data) {
                var nums = data.preLimitReturn;
                var preLimitReturn = '参考值:' + nums;
                var tradeType = data.tradesType;
                var businessType = data.businessType;
                $('#tradeType').val(tradeType);
                $('#tradeType').select2({value: tradeType});
                $('#businessType').val(businessType);
                $('#businessType').select2({value: businessType});
                $("#preLimitLS").html(preLimitReturn)
            }
        });
    }


    $("#preLimit").blur(function preLimitQuery() {
        //proName   preLimit
        var proName = $("#pid").val();
        var preLimit = $("input[name=preLimit]").val();
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "${base}/bus/limit/warnAboutPrelimits",
            data: {
                "pid": proName,
                "preLimit": preLimit
            },
            success: function (data) {
                if (data.success) {
                    var formData = $('#editForm').serializeAll();
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        url: "${base}/bus/limit/preLimitQuery",
                        data: formData,
                        success: function (data) {
                            $("#limitRate").val(data.limitRate);
                            $("#pasLimit").val(data.pasLimit);
                            $("#remLimit").val(data.remLimit);
                        }
                    });
                } else {
                    alert("预计额度超过参考值！请重设预计额度。参考值：" + data.msg);
                    $("#limitRate").val('');
                    $("#pasLimit").val('');
                    $("#remLimit").val('');
                }
            }

        });
    });


</script>
<script type="text/javascript">

    //加上红点
    $(document).ready(function(){
        $(".required").each(function() {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>"+$this.html());
        });
    });
</script>
</body>
</html>