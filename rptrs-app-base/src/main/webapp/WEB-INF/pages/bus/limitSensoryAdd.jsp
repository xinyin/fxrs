<%@ taglib prefix="bev" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>LimitSensory</title>
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
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="limitSensoryBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${pid}"/>

            <bf:formGroup >
                <bc:col col="6">
                    <bc:label text="交易主体" col="4" formControl="true"/>
                    <bf:select id="sensoryIndex" name="sensoryIndex" col="8" dataSource="custom"
                               url="bus/limit/getTranOrgItems" value="${tendId}" disabled="true" excludes="">
                        <bf:option text="请选择" value="0"></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls = "required" text="业务级别名称" col="4" formControl="true"/>
                    <bf:lookup id="busLeveName" name="busLeveName" col="8" title="业务级别名称"
                               url="${base}/bus/limit/proNameListTree" winId="productViewId"
                               propText="proName" propValue="level"
                               callback="result" onSelect="onSelect" text="${limitSensory.busLeveName}"
                               value="${limitSensory.busLeveName}" disabled="${opt == 'edit' || opt == 'view'}">
                    </bf:lookup>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup >
                <bc:col col="6">
                    <bc:label cls = "required" text="交易类型" col="4" formControl="true"/>
                    <bf:select id="tradeType" name="tradeType" col="8" dataSource="custom"
                               url="bus/limit/getTradeTypeItems" value="${limitSensory.tradeType}" disabled="true">
                    </bf:select>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="业务类型" col="4" formControl="true"/>
                    <bf:select id="businessType" name="businessType" col="8" dataSource="custom"
                               url="bus/limit/getBuinessTypeItems" value="${limitSensory.businessType}" disabled="true">
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup >
                <bc:col col="6">
                    <bc:label text="预计额度（元）" col="4" formControl="true"/>
                    <bf:text id="preLimit" name="preLimit" col="8" value="${limitSensory.busLeve}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="额度使用率" col="4" formControl="true"/>
                    <bf:text id="limitRate" name="limitRate" col="8" value="${limitSensory.limitRate}"
                             disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup >
                <bc:col col="6">
                    <bc:label cls = "required" text="一级预警阈值(%)" col="4" formControl="true"/>
                    <bf:text id="warnThreshold1l" name="warnThreshold1l" col="8"
                             value="${limitSensory.warnThreshold1l}" disabled="${opt == 'view'}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls = "required" text="二级预警阈值(%)" col="4" formControl="true"/>
                    <bf:text id="warnThreshold2l" name="warnThreshold2l" col="8"
                             value="${limitSensory.warnThreshold2l}" disabled="${opt == 'view'}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup >
                <bc:col col="6">
                    <bc:label cls = "required" text="三级预警阈值(%)" col="4" formControl="true"/>
                    <bf:text id="warnThreshold3l" name="warnThreshold3l" col="8"
                             value="${limitSensory.warnThreshold3l}" disabled="${opt == 'view'}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-12 col-sm-offset-5">
                    <bc:button type="danger" text="保存" icon="save" >
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/bus/limitSensory/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/bus/limitSensory/list';
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
                            window.location = '${base}/bus/limitSensory/list';
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
    var tradeType = '${product.tradeType}';
    var businessType = '${product.businessType}';
    $(document).ready(function () {
        //初始化内容脚本
        if (tradeType == '') {
            tradeType = 'A';
        }
        if (businessType == '' || businessType == undefined) {
            changeNextY(tradeType);
        }
    });

    function preLimitQuery(e) {
        var formData = $('#editForm').serializeAll();
        formData.businessType = e;
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "${base}/bus/limitSensory/preLimitQuery",
            data: formData,
            success: function (data) {
                //preLimit   limitRate
                $("#busLeve").val(data.preLimit);
                $("#busLeveName").val(data.limitRate);
                //    busLeve   busLeveName
            }
        });
    }
    function onSelect() {
        var busLeveName = $("input[name=busLeveName]").val();
        $.ajax({
            type: "post",
            url: "${base}/bus/limitSensory/selectbuinessL",
            data: {"unitCodes": busLeveName},
            success: function (data) {

                var tradeType = data.tradesType
                var businessType = data.businessType
                var preLimit = data.preLimit;
                var limitRate = data.limitRate;
                $('#tradeType').val(tradeType);
                $('#tradeType').select2({value: tradeType});
                $('#businessType').val(businessType);
                $('#businessType').select2({value: businessType});
                $('#preLimit').val(preLimit);
                $('#limitRate').val(limitRate);

            }
        });
    }

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
