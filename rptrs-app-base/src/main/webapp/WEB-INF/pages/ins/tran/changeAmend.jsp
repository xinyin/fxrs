<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>变更经办人</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="messageForm" name="form2" method="post" validation="true">
            <bf:hidden name="pid" id="pid" value="${pid }"></bf:hidden>
            <bf:hidden name="tranType" id="tranType" value="${tranType }"></bf:hidden>

            <bf:formGroup type="success" hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="经办人ID" col="4" formControl="true"/>
                    <bf:staffLookup id="staffCode" name="staffCode" col="7"
                                    value="${staffCode}"
                                    callback="result"
                                    text="${staffCode}" propText="staffCode"
                                    propValue="str"
                                    onSelect="receiverChange" mult="false">
                        <bv:notEmpty/>
                    </bf:staffLookup>

<%--                    <bf:lookup url="${base}/ins/tran/dialog"  id="relName"--%>
<%--                               name="relName" winId="${relRandom}"  propText="staffCode"--%>
<%--                               value="${staffCode}" text="${staffCode}"--%>
<%--                               propValue="staffCode" col="7" width="800px" height="260px"  onSelect="receiverChange">--%>
<%--                    </bf:lookup>--%>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="经办人名称" col="4" formControl="true"/>
                    <bf:text id="amendName" name="amendName" col="7"  value="${amendName}"
                             disabled="true"/>
                    <bv:notEmpty/>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="success" outline="true" text="保存" icon="save">
                        <be:event>
                            save();
                        </be:event>
                    </bc:button>
                    <bc:button type="info" outline="true" text="关闭">
                        <be:event>
                            $.messager.hide("winad");
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

    /**
     * 数据回显
     */
    function receiverChange() {
        let valueList = $("[name='staffCode']").val();
        if (valueList !== null && valueList !== '' && valueList.length > 0) {
            let list = valueList.split(',');
            // let staffCode = list[0];
            let amendName = list[1];
            // $('#staffCode').val(null == staffCode ? '' : staffCode);
            $('#amendName').val(null == amendName ? '' : amendName);
        }
    }

    /**
     * 更新经办人
     */
    function save() {
        var staffCode = $("[name = 'staffCode']").val();
        if(null != staffCode && '' != staffCode && "" != staffCode){
            staffCode = staffCode.split(',')[0];
        }
        var amendName = $('#amendName').val();
        var pid = $('#pid').val();
        var tranType = $('#tranType').val();

        // if (tranType == '' || tranType == null) {
        //     $.messager.alert('错误', "交易类型不能为空！", 'error');
        //     return;
        // }
        //
        // if (staffCode == '' || staffCode == null) {
        //     $.messager.alert('错误', "经办人ID不能为空！", 'error');
        //     return;
        // }
        $('#messageForm').data('formValidation').validate();
        var isValid = $('#messageForm').data('formValidation').isValid();
        if(isValid){
            $.ajax({
                type: 'GET',
                url: '${base}/ins/tran/updataAmend?staffCode=' + staffCode + "&amendName=" + amendName + "&pid=" + pid + "&tranType=" + tranType,
                dataType: 'json',
                async: false,
                delay: 200,
                success: function (data) {
                    if (data.success) {
                        <%--$.messager.alert('提示', '更新经办人成功。', 'info', function () {--%>
                        <%--    if ("1" == tranType) {--%>
                        <%--        window.location = '${base}/ins/tran/traFunds';--%>
                        <%--    }--%>
                        <%--    if ("2" == tranType) {--%>
                        <%--        window.location = '${base}/ins/tran/traService';--%>
                        <%--    }--%>
                        <%--    if ("3" == tranType) {--%>
                        <%--        window.location = '${base}/ins/tran/traInterest';--%>
                        <%--    }--%>
                        <%--    if ("4" == tranType) {--%>
                        <%--        window.location = '${base}/ins/tran/traBusiness';--%>
                        <%--    }--%>
                        <%--});--%>

                        $.messager.alert('提示', '更新经办人成功。', 'info', function(){
                            $.messager.hide("winad");
                        });

                    } else {
                        $.messager.alert('提示', "更新经办人失败。", 'error');
                    }
                }
            });
        }

    }

</script>
</body>
</html>
