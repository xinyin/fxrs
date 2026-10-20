<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>Limit</title>
   <%-- <style>
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

        .bootstrap-table {
            margin: 0 5%;
        }

        .filesup .btn {
            margin: 0 0 -18px 16% !important;
        }

        .fixed-table-toolbar .bs-bars, .fixed-table-toolbar .columns, .fixed-table-toolbar .search {
            position: relative;
            margin: 0 0 5px 0 !important;
            line-height: 34px;
            display: flex !important;
        }
    </style>--%>
</head>
<body class="gray-bg" style="background: white">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="gdTraZyBox" title="" subTitle="" col="12" collapsible="true">
            <bf:form id="editForm" method="post" validation="true">
                <bf:hidden id="opt" name="opt" value="${opt}"/>
                <bf:hidden id="pid" name="pid" value="${product.pid}"/>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="交易机构" col="4" formControl="true"/>
                        <bf:select id="tranOrg" name="tranOrg"  col="8" dataSource="custom"
                                   url="sys/unit/selectUnit" value="${limit.tranOrg}">
                            <be:event type="select">
                                var unitCodes=$('#tranOrg').val();
                                $.ajax({
                                type:"post",
                                url:"/sys/unit/selectDept",
                                data:{"unitCodes":unitCodes},
                                success:function(arrUser){
                                console.log(JSON.stringify(arrUser));
                                $('#busDepart').empty();
                                $('#busDepart').select2({data: arrUser, width: '100%', minimumResultsForSearch: Infinity});
                                }
                                })
                            </be:event>
                        </bf:select>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="业务部门" col="4" formControl="true"/>
                        <bf:select id="busDepart" name="__LIKES_busDepart"  col="8" >
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="交易类型" col="4" formControl="true"/>
                        <bf:select id="tradeType" name="tradeType"  col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-BusinessType" value="${limit.tradeType}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="业务类型" col="4" formControl="true"/>
                        <bf:select id="businessType" name="businessType"  col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-TtradeType" value="${limit.businessType}">
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="业务级别" col="4" formControl="true"/>
                        <bf:select id="proPath" name="proPath"  col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-BusLevel" value="${limit.proPath}" disabled="true">
                        </bf:select>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="业务级别名称" col="4" formControl="true"/>
                        <bf:select id="busDepart" name="busDepart"  col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-BusLevel" value="${limit.busDepart}">
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="预计额度币种" col="4" formControl="true"/>
                        <bf:select id="preLimitCurrency" name="preLimitCurrency"  col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Currency" value="${limit.preLimitCurrency}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="预计额度（元）" col="4" formControl="true"/>
                        <bf:text id="preLimit" name="preLimit" col="8" value="${product.preLimit}">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="剩余额度（元）" col="4" formControl="true"/>
                        <bf:text id="remLimit" name="remLimit" col="8" value="${product.remLimit}" disabled="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button type="danger" text="确认提交" icon="save" click="sub()">
                        </bc:button>
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
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

<br:js pack="list"/>
<be:processEvent/>
<script src="../../static/js/jquery.dialog.js"></script>

<script type="text/javascript">
    $(document).ready(function () {

    });

    function sub() {
        var pid = $('#pid').val();
        alert(pid);
        $.ajax({
            url: "${base}/bus/limit/changeApplyStatus/" + pid,
            type: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    var formData = $('#editForm').serializeJson();
                    var myDate = new Date();
                    formData.id = myDate.getTime();
                    formData.type = "LimitTra";
                    var data = JSON.stringify(formData);
                    formData = $.helper.base64Encode(data);
                    var obj = {
                        procDefCode: 'Estimated_Quota',
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