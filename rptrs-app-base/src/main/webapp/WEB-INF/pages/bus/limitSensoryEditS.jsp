<%@ taglib prefix="bev" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>LimitSensory</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="limitSensoryBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${limitSensory.pid}"/>

            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="交易机构" col="4" formControl="true"/>
                    <bf:select id="sensoryIndex" name="sensoryIndex" col="8" dataSource="custom"
                               url="sys/unit/selectUnit" value="${limitSensory.sensoryIndex}"
                               disabled="true">
                        <bf:option text="All" value="%"></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls = "required"  text="交易类型" col="4" formControl="true"/>
                    <bf:select id="tradeType" name="tradeType" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/INS-tranType" value="${limitSensory.tradeType}"
                               disabled="true">
                        <bf:option text="All" value="%"></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom" >
                <bc:col col="6">
                    <bc:label text="业务类型" col="4" formControl="true"/>
                    <bf:select id="businessType" name="businessType" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/EFC-TYPE" value="${limitSensory.businessType}"
                               disabled="true" >
                        <bf:option text="All" value="%"></bf:option>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="预计额度（元）" col="4" formControl="true"/>
                    <bf:text id="busLeve" name="busLeve" col="8" value="${limitSensory.busLeve}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="额度使用率" col="4" formControl="true"/>
                    <bf:text id="busLeveName" name="busLeveName" col="8" value="${limitSensory.busLeveName}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>

            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls = "required" text="一级预警阈值" col="4" formControl="true"/>
                    <bf:text id="warnThreshold1l" name="warnThreshold1l" col="8"
                             value="${limitSensory.warnThreshold1l}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls = "required" text="二级预警阈值" col="4" formControl="true"/>
                    <bf:text id="warnThreshold2l" name="warnThreshold2l" col="8"
                             value="${limitSensory.warnThreshold2l}">
                    </bf:text>
                </bc:col>
                <bc:col col="1">
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls = "required" text="三级预警阈值" col="4" formControl="true"/>
                    <bf:text id="warnThreshold3l" name="warnThreshold3l" col="8"
                             value="${limitSensory.warnThreshold3l}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-12 col-sm-offset-5">
                    <bc:button type="danger" text="保存" icon="save">
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
    $(document).ready(function () {
        changeNext('01');
    });

    function changeNext(val) {
        var buinesss = '';
        if (val == '01') {
            $("#InvestmentType").css("display", "none")
            $("#InsBusinessType").css("display", "none")
            $("#BusinessType").css("display", "none")
            $("#ServiceType").css("display", "none")
            $("#InvMethod01").css("display", "block")
        } else if (val == '02') {
            $("#InsBusinessType").css("display", "none")
            $("#InvMethod01").css("display", "none")
            $("#BusinessType").css("display", "none")
            $("#ServiceType").css("display", "none")
            $("#InvestmentType").css("display", "block")
        } else if (val == '03') {
            $("#InvestmentType").css("display", "none")
            $("#InvMethod01").css("display", "none")
            $("#BusinessType").css("display", "none")
            $("#ServiceType").css("display", "none")
            $("#InsBusinessType").css("display", "block")
        } else if (val == '04') {
            $("#InvestmentType").css("display", "none")
            $("#InvMethod01").css("display", "none")
            $("#InsBusinessType").css("display", "none")
            $("#ServiceType").css("display", "none")
            $("#BusinessType").css("display", "block")
        } else if (val == '05') {
            $("#InvestmentType").css("display", "none")
            $("#InvMethod01").css("display", "none")
            $("#BusinessType").css("display", "none")
            $("#InsBusinessType").css("display", "none")
            $("#ServiceType").css("display", "block")
        }
    }

    function preLimitQuery(e) {
        var formData = $('#editForm').serializeAll();
        formData.businessType=e;
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "${base}/bus/limitSensory/preLimitQuery",
            data: formData,
            success: function (data) {
                //preLimit   limitRate
                $("#preLimit").val(data.preLimit);
                $("#limitRate").val(data.limitRate);
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
