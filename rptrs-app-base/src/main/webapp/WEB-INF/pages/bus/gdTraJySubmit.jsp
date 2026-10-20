<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <%--<title>GdTraZy</title>--%>
    <style>
        .form-group {
            margin-bottom: 8px !important;
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
<body class="gray-bg" style="background: white">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="gdTraZyBox" title="" subTitle="" col="12" collapsible="true">
            <bf:form id="editForm" method="post" validation="true">
                <bf:hidden id="pid" name="pid" value="${gdTraZy.pid}"/>
                <bf:hidden id="operationType" name="operationType" value="${gdTraZy.operationType}"/>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="股东代码" col="5" formControl="true"/>
                        <bf:text name="shareholdersCode" col="7" disabled="true" value="${gdTraZy.shareholdersCode}"
                                 placeholder="股东代码"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="股东名称" col="5" formControl="true"/>
                        <bf:text name="shareholdersName" col="7" disabled="true" value="${gdTraZy.shareholdersName}"
                                 placeholder="股东名称"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="股东类型" col="5" formControl="true"/>
                        <bf:select id="shareholdersType" name="shareholdersType" col="7" disabled="true"
                                   value="${gdTraZy.shareholdersType}"
                                   dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelatedPartiesType">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_shareholdersType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="总持有股数" col="5" formControl="true"/>
                        <bf:text name="holeShareAmount" col="7" disabled="true" value="${gdTraZy.holeShareAmount}" placeholder="总持有股数"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="总质押股数" col="5" formControl="true"/>
                        <bf:text name="pledgedShareAmount" col="7" disabled="true" value="${gdTraZy.pledgedShareAmount}"
                                 placeholder="总质押股数"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="质押合同号" col="5" formControl="true"/>
                        <bf:text name="pledgedContract" col="7" disabled="true" value="${gdTraZy.pledgedContract}" placeholder="质押合同号"/>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="质押股数" col="5" formControl="true"/>
                        <bf:text id="currPledgedAmount" name="currPledgedAmount" col="7" disabled="true"
                                 value="${gdTraZy.currPledgedAmount}"
                                 placeholder="质押股数">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="解押股数" col="5" formControl="true"/>
                        <bf:text name="operationAmount" col="7" disabled="true" placeholder="解押股数" value="${gdTraZy.operationAmount}"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="解押时间" col="5" formControl="true"/>
                        <bf:datebox id="unpledgedTime" name="unpledgedTime" col="7" disabled="true" format="date"
                                    value="${gdTraZy.operationAmount}"/>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="解押说明" col="5" formControl="true"/>
                        <bf:text name="unpledgedComment" col="7" disabled="true" placeholder="解押说明"
                                 value="${gdTraZy.unpledgedComment}"/>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-5" style="padding:3% 0 0 0;">
                        <bc:button type="danger" text="确认提交" icon="save" click="sub()">
                        </bc:button>
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/bus/gdTraJy/list';
                            </be:event>
                        </bc:button>
                    </div>
                </bf:formGroup>
            </bf:form>
        </bc:ibox>
    </div>
</div>

<br:js pack="list"/>
<be:processEvent/>
<script src="../../static/js/jquery.dialog.js"></script>

<script type="text/javascript">
    function sub() {
        var pid = $('#pid').val();
        $.ajax({
            url: "${base}/bus/gdTraZy/changeApplyStatus/" + pid,
            type: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    var formData = $('#editForm').serializeJson();
                    var myDate = new Date();
                    formData.id = myDate.getTime();
                    formData.type = "jyTra";
                    var data = JSON.stringify(formData);
                    formData = $.helper.base64Encode(data);
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
</script>

</body>
</html>