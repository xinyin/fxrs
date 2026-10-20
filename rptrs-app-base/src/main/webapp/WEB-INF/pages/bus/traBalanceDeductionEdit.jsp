<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>关联交易余额扣除规则表</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="traBalanceDeductionBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${pid}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
                <%--					<bc:col col="6">--%>
                <%--						<bc:label text="主键" col="4" formControl="true"/>--%>
                <%--						<bf:text id="pid" name="pid" col="8" value="${traBalanceDeduction.pid}"  disabled="${opt == 'edit'}">--%>
                <%--							<bv:jepCode/>--%>
                <%--							<bv:notEmpty/>--%>
                <%--							<bv:remote url="${base}/bus/traBalanceDeduction/check" name="pid" message="该编码已被占用"/>--%>
                <%--						</bf:text>--%>
                <%--					</bc:col>--%>
                <bc:col col="6">
                    <bc:label text="保证金存款" col="5" formControl="true"/>
                    <bf:select id="securitDeposit" name="securitDeposit" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-isNo" value="${traBalanceDeduction.securitDeposit}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="质押的银行存单" col="5" formControl="true"/>
                    <bf:select id="bankDeposit" name="bankDeposit" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-isNo" value="${traBalanceDeduction.bankDeposit}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="国债" col="5" formControl="true"/>
                    <bf:select id="nationalDebt" name="nationalDebt" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-isNo" value="${traBalanceDeduction.nationalDebt}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
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
                            $.mask.post('${base}/bus/traBalanceDeduction/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/bus/traBalanceDeduction/edit';
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

    });
</script>
</body>
</html>
