<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>TraCredit</title>
    <style>
        .form-group .col-xs-4 {
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

        .select2-dropdown, .has-success .select2-selection {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="traCreditBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${traCredit.pid}"/>
            <bf:hidden id="transNum" name="transNum" value="${traCredit.transNum}"/>
            <bf:hidden id="relId" name="relId" value="${traCredit.relId}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="关联方证件号码" col="5" formControl="true"/>
                    <bf:text id="identNumber" name="identNumber" col="7" value="${traCredit.identNumber}"
                             disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="关联方类型" col="5" formControl="true"/>
                    <bf:select id="relType" name="relType" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-RelatedPartiesType" col="7" disabled="true"
                               value="${traCredit.relType}">
                        <bf:option text="ALL" value=""></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="关联方名称" col="5" formControl="true"/>
                    <bf:text id="relatedName" name="relatedName" col="7" value="${traCredit.relatedName}"
                             disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易类型" col="5" formControl="true"/>
                    <bf:select id="tranType" name="tranType" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-BusinessType" col="7"
                               value="${traCredit.tranType}" disabled="true">
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易批准日期" col="5" formControl="true"/>
                    <bf:datebox id="traApproveTime" name="traApproveTime" col="7" disabled="true"
                                value="${traCredit.traApproveTime}"/>
                    <bv:notEmpty/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="银行内部授信审批编号" col="5" formControl="true"/>
                    <bf:text id="internalApplyNo" name="internalApplyNo" col="7" value="${traCredit.internalApplyNo}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="授信额度(万元/人民币)" col="5" formControl="true"/>
                    <bf:text id="creditAmount" name="creditAmount" col="7" value="${traCredit.creditAmount}"
                             disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="其中外币授信额度折算为本币金额" col="5" formControl="true"/>
                    <bf:text id="foreignCurrency" name="foreignCurrency" col="7" value="${traCredit.foreignCurrency}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                    <bf:select id="importantTrade" name="importantTrade" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-isNo" col="7"
                               value="${traCredit.importantTrade}" disabled="true">
                        <bf:option text="ALL" value=""></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="担保方式" col="5" formControl="true"/>
                    <bf:select id="guaraMode" name="guaraMode" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-GuaranteeMode" col="7"
                               value="${traCredit.guaraMode}" disabled="true">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证人证件类型" col="5" formControl="true"/>
                    <bf:select id="bzrIdentifyType" name="bzrIdentifyType" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-IdentifyType" col="7" disabled="true"
                               value="${traCredit.bzrIdentifyType}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证人证件号" col="5" formControl="true"/>
                    <bf:text id="bzrIdentNumber" name="bzrIdentNumber" col="7" disabled="true"
                             value="${traCredit.bzrIdentNumber}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证人名称" col="5" formControl="true"/>
                    <bf:text id="guarantor" name="guarantor" col="7" value="${traCredit.guarantor}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证金金额(万元/人民币)" col="5" formControl="true"/>
                    <bf:text id="securitDeposit" name="securitDeposit" col="7" value="${traCredit.securitDeposit}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证金比例" col="5" formControl="true"/>
                    <bf:text id="marginRatio" name="marginRatio" col="7" value="${traCredit.marginRatio}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="质押的银行存单金额(万元/人民币)" col="5" formControl="true"/>
                    <bf:text id="bankDeposit" name="bankDeposit" col="7" value="${traCredit.bankDeposit}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="质押的国债金额(万元/人民币)" col="5" formControl="true"/>
                    <bf:text id="nationalDebt" name="nationalDebt" col="7" value="${traCredit.nationalDebt}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="统计口径" col="5" formControl="true"/>
                    <bf:select id="regulatorOth1" name="regulatorOth1" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Caliber" col="7"
                               value="${traCredit.regulatorOth1}" disabled="true">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="12">
                    <bc:label text="授信方案" col="0" formControl="true"/>
                    <bf:text id="creditPlan" name="creditPlan" col="7" disabled="true"
                             multiple="true" value="${traCredit.creditPlan}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="12">
                    <bc:label text="其他交易信息" col="0" formControl="true"/>
                    <bf:text id="remark" name="remark" col="7" disabled="true"
                             multiple="true" value="${traCredit.remark}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <div id="tableDiv" style="width:95%;margin:0 auto">
                <bc:table id="docTable" idField="pid" url="${base}/bus/traEnclosure" multiple="true"
                          selectFirst="false" queryUrl="queryList?transNum=${traCredit.transNum}">
                    <%--                    <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>--%>
                    <%--                    <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>--%>
                    <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
                    <bc:column field="pid" title="主键" width="100" visible="false"/>
                    <bc:column field="relatedName" title="文件名" width="100"/>
                    <bc:column field="fileType" title="文件类型" width="100"/>
                    <bc:column field="fileSize" title="大小" width="100"/>
                    <bc:column field="uploadOrg" title="上传机构" width="100"
                               formatter="custom:sys/dictItem/getDictItems/BUS-GHOrg"/>
                    <bc:column field="uploadDept" title="上传部门" width="100"/>
                    <bc:column field="uploadTime" title="上传时间" width="100"/>
                </bc:table>
            </div>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="12">
                    <bc:label text="提交备注" col="0" formControl="true"/>
                    <bf:text id="statusComment" name="statusComment" col="7"
                             multiple="true" value="${statusComment}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding: 3% 0 0 0">
                    <bc:button type="danger" text="确认提交" icon="save" click="sub()">
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/traCredit/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
</div>
<div>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"
            id="hiddenBtn"
            style="display: none">
        文件上传按钮
    </button>
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalCenterTitle">文件上传</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" enctype="multipart/form-data" id="uploadForm"
                          style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
                        <div class="form-group" style="text-align: center;vertical-align: middle;">
                            <label for="file"></label>
                            <img src="../../static/img/sendfile.png"/>
                            <a href="javascript:;" class="file">选择文件
                                <input type="file" class="form-control" id="file" name="file" multiple
                                       style="padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
                            </a>
                        </div>

                    </form>
                    <div class="form-group"
                         style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
                        <button type="submit" class="btn btn-primary" id="upload">上传</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {
        var opt = $('#opt').val();
        var businessType = $('#businessType').val();
        if (businessType == 'comprehensive') {
            $('#tranScopeDiv').show();
        } else {
            $('#tranScopeDiv').hide();
        }
        var isLetter = $('#isLetter').val();
        if (isLetter != '1') {
            $('#letter').hide();
        }
    });

    function exportIn() {
        $("#hiddenBtn").click();
    }

    function getIdSelections() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function getAllIdSelections() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    //文件表所有选中的数据
    function getselectData() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }

    $("#upload").click(function () {
        var obj = document.getElementById("file");
        for (var i = 0; i < obj.files.length; i++) {
            var temp = obj.files[i].name;
            var temArr = temp.split('.');
            var fileLast = temArr[temArr.length - 1];
            if (fileLast != 'pdf') {
                alert("请上传pdf文件");
                return;
            }
        }
        var formData = new FormData($('#uploadForm')[0]);
        $.ajax({
            type: 'post',
            url: '${base}/bus/traEnclosure/uploadFile', //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                $("#close").click();
                alert(data.msg);
                if (data.success) {
                    //处理数据
                    var files = data.traEnclosure;
                    var count = files.length;
                    //获取原有表单的数据
                    var rows = getData();
                    rows = rows.concat(files);
                    //构造表格显示数据
                    var total = rows.length;
                    var showData = {'total': total, 'rows': rows}
                    $('#docTable').bootstrapTable('load', showData);
                }
            },
            error: function () {
                $("#close").click();
                alert("上传失败,请重试");
            }
        })
    });

    //文件表数据
    function getData() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row
        });
    }

    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        if (pid == '' || pid == undefined) {
            alert("请选中一个文件");
            return;
        }
        if (pid.indexOf(',') != -1) {
            alert("只能选中一个文件");
            return;
        }
        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/bus/traEnclosure/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
    }
    //移除文件
    removeDoc = function () {
        var rows = getData();//删除前数据
        var pids = getIdSelections();//删除数据主键
        var selectRows = getselectData();//选中的数据
        if (selectRows.length == 0) {
            alert("请先选中数据");
            return;
        }
        if (confirm("确认删除？")) {
            $.ajax({
                type: 'get',
                url: '${base}/bus/traEnclosure/removeFile/' + pids,
                dataType: 'json',
                success: function (res) {
                    alert(res.msg);
                    if (res.success) {
                        //从数据中移除
                        for (var i = 0; i < selectRows.length; i++) {
                            var row = selectRows[i];
                            rows.splice(rows.indexOf(row), 1);
                        }
                        var showData = {'total': rows.length, 'rows': rows};
                        $('#docTable').bootstrapTable('load', showData);
                    }
                }

            })
        }
    }

    function funName(val) {
        if ('comprehensive' == val) {
            $('#tranScopeDiv').show();

        } else {
            $('#tranScopeDiv').hide();
        }
    }

    function sub() {
        var pid = $('#pid').val();
        var statusComment = $('#statusComment').val();
        var obj = {
            statusComment: statusComment
        };
        $.ajax({
            url: '${base}/bus/traCredit/changeApplyStatus/' + pid,
            type: 'post',
            dataType: 'json',
            data: obj,
            success: function (data) {
                if (data.success) {
                    var formData = $('#editForm').serializeJson();
                    var myDate = new Date();
                    formData.id = myDate.getTime();
                    formData.type = "CREDIT";
                    var data = JSON.stringify(formData);
                    var formData = $.helper.base64Encode(data);
                    var obj = {
                        procDefCode: 'trancredit',
                        oldData: '',
                        newData: formData,
                        uploadSessions: '',
                        procInstId: ''
                    };
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
<script type="text/javascript">
    $("#approvalTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });

    $("#applicaSubTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });


    $("#tranDate").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });

    $("#validityContract").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });
    // var a =$("#pid1").val();
    // alert(a);
</script>
</body>
</html>
