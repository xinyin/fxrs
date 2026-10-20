<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>GdTraDj</title>
    <style>
        .form-group {
            margin-bottom: 8px !important;
        }

        .form-group .col-xs-4 {
            margin-top: 0.4%;
        }

        .hr-line-dashed {
            border-top: 1px dashed #e7eaec;
            color: #ffffff;
            background-color: #ffffff;
            height: 1px;
            margin: 7px 0 !important;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="gdTraDjBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${gdTraDj.pid}"/>
            <bf:hidden id="operationType" name="operationType" value="${gdTraDj.operationType}"/>
            <bf:hidden id="domesticOrForeign" name="domesticOrForeign" value="${gdTraDj.domesticOrForeign}"/>
            <div>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="股东代码" col="4" formControl="true"/>
                        <bf:text id="shareholdersCode" name="shareholdersCode" col="8"
                                 value="${gdTraDj.shareholdersCode}" change="getShareholder()">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="股东名称" col="4" formControl="true"/>
                        <bf:text id="shareholdersName" name="shareholdersName" col="8"
                                 value="${gdTraDj.shareholdersName}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="股东类型" col="4" formControl="true"/>
                        <bf:select id="shareholdersType" name="shareholdersType" col="8"
                                   value="${gdTraDj.shareholdersType}"
                                   dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelatedPartiesType" disabled="true">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_shareholdersType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="总持有股数" col="4" formControl="true"/>
                        <bf:text id="holeShareAmount" name="holeShareAmount" col="8" value="${gdTraDj.holeShareAmount}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="总冻结股数" col="4" formControl="true"/>
                        <bf:text id="frozenShareAmount" name="frozenShareAmount" col="8"
                                 value="${gdTraDj.frozenShareAmount}" disabled="true">
                        </bf:text>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="操作股数" col="4" formControl="true"/>
                        <bf:text id="operationAmount" name="operationAmount" col="8" value="${gdTraDj.operationAmount}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="冻结生效时间" col="4" formControl="true"/>
                        <bf:datebox id="effectiveTime" name="effectiveTime" col="8" format="date"
                                    value="${gdTraDj.effectiveTime}"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="冻结失效时间" col="4" formControl="true"/>
                        <bf:datebox id="failureTime" name="failureTime" col="8" format="date"
                                    value="${gdTraDj.failureTime}"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="冻结机构" col="4" formControl="true"/>
                        <bf:text id="frozenOrg" name="frozenOrg" col="8" value="${gdTraDj.frozenOrg}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="冻结流水号" col="4" formControl="true"/>
                        <bf:text id="frozenSerial" name="frozenSerial" col="8" value="${gdTraDj.frozenSerial}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="续冻失效时间" col="4" formControl="true"/>
                        <bf:datebox id="continueFrozenTime" name="continueFrozenTime" col="8" format="date"
                                    value="${gdTraDj.continueFrozenTime}"/>
                    </bc:col>
                    <div>

                    </div>
                    <bc:col col="12">
                        <bc:label text="冻结原因" col="1" formControl="true"/>
                        <bf:text id="frozenReason" name="frozenReason" col="10" value="${gdTraDj.frozenReason}"
                                 multiple="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="续冻原因" col="1" formControl="true"/>
                        <bf:text id="continueFrozenReason" name="continueFrozenReason" col="10"
                                 value="${gdTraDj.continueFrozenReason}" multiple="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
            </div>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-4" style="padding:3% 0 0 0">
                    <bc:button type="danger" text="确认提交" icon="save" click="sub()">
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/gdTraDj/list';
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

    function sub() {
        var pid = $('#pid').val();
        $.ajax({
            url: "${base}/bus/gdTraDj/changeApplyStatus/" + pid,
            type: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    var formData = $('#editForm').serializeJson();
                    var frozenShareAmount = $('#frozenShareAmount').val();
                    var shareholdersType = $('#shareholdersType').val();
                    var holeShareAmount = $('#holeShareAmount').val();
                    var myDate = new Date();
                    formData.id = myDate.getTime();
                    formData.type = "djTra";
                    formData.frozenShareAmount = frozenShareAmount;
                    formData.shareholdersType = shareholdersType;
                    formData.holeShareAmount = holeShareAmount;
                    var data = JSON.stringify(formData);
                    formData = $.helper.base64Encode(data);
                    // alert(data);
                    var obj = {
                        procDefCode: 'gd',
                        oldData: '',
                        newData: formData,
                        uploadSessions: '',
                        procInstId: ''
                    }
                    $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
                        if (data.success) {
                            $.messager.alert('提示', '提交成功。', 'info', function () {
                                window.location = '${base}/workflow/process/runingList';
                            });
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });

                } else {
                    alert(data.msg);
                }
            }
        });
    }

    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
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
</script>
</body>
</html>
