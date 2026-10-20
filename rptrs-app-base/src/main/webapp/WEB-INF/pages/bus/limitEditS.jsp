<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ taglib prefix="be" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>Limit</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="productBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${pid}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="交易机构" col="4" formControl="true"/>
                    <bf:select id="tranOrg" name="tranOrg" col="8" dataSource="custom"
                               url="sys/unit/selectUnit" value="${product.tranOrg}" disabled="${opt == 'edit'}">
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="交易类型" col="4" formControl="true"/>
                    <bf:select id="tradeType" name="tradeType" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/INS-tranType" value="${product.tradeType}"
                               disabled="${opt == 'edit'}">
                        <bv:notEmpty/>
                        <be:event type="select">
                            changeNext(e.target.value);
                        </be:event>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom" >
                <bc:col col="6">
                    <bc:label text="业务类型" col="4" formControl="true"/>
                    <bf:select id="businessType" name="businessType" col="8" dataSource="custom"
                               url="sys/dictItem/getDictItems/EFC-TYPE" value="${product.businessType}"
                               disabled="true">
                        <be:event type="select">

                        </be:event>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="预计额度（元）" col="4" formControl="true"/>
                    <bf:text id="preLimit" name="preLimit" col="8" value="${product.preLimit}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="额度使用率" col="4" formControl="true"/>
                    <bf:text id="limitRate" name="limitRate" col="8" value="${product.limitRate}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="已用额度" col="4" formControl="true"/>
                    <bf:text id="pasLimit" name="pasLimit" col="8" value="${product.pasLimit}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="剩余额度" col="4" formControl="true"/>
                    <bf:text id="remLimit" name="remLimit" col="8" value="${product.remLimit}" disabled="true">
                        <bv:notEmpty/>
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
    $("#preLimit").blur(function preLimitQuery() {
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
    });
</script>
</body>
</html>
