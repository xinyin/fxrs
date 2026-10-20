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
            <bf:hidden id="productCode" name="productCode" value="${traCredit.productCode}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label cls="required" text="关联方证件号码" col="5" formControl="true"/>
                    <bf:text id="identNumber" name="identNumber" col="7" value="${traCredit.identNumber}"
                             change="getInfo()">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label cls="required" text="关联方类型" col="5" formControl="true"/>
                    <bf:select id="relType" name="relType" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-RelatedPartiesType" col="7"
                               value="${traCredit.relType}">
                        <bf:option text="ALL" value=""></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label cls="required" text="关联方名称" col="5" formControl="true"/>
                    <bf:text id="relatedName" name="relatedName" col="7" value="${traCredit.relatedName}"
                             change="getInfo()">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易类型" col="5" formControl="true"/>
                    <bf:select id="tranType" name="tranType" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-BusinessType" col="7"
                               value="${traCredit.tranType}" disabled="true">
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易批准日期" col="5" formControl="true"/>
                    <bf:datebox id="traApproveTime" name="traApproveTime" col="7"
                                value="${traCredit.traApproveTime}"/>
                    <bv:notEmpty/>
                </bc:col>
                <%--<bc:col col="4">
                    <bc:label text="产品名称" col="5" formControl="true"/>
                    <bf:text id="productName" name="productName" col="7" value="${traCredit.productName}"
                             change="getProInfo()">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="一级业务种类" col="5" formControl="true"/>
                    <bf:text id="typeOneBus" name="typeOneBus" col="7" value="${traCredit.typeOneBus}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="二级业务种类" col="5" formControl="true"/>
                    <bf:text id="typeTwoBus" name="typeTwoBus" col="7" value="${traCredit.typeTwoBus}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="三级业务种类" col="5" formControl="true"/>
                    <bf:text id="typeThreeBus" name="typeThreeBus" col="7" value="${traCredit.typeThreeBus}"
                             disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="四级业务种类" col="5" formControl="true"/>
                    <bf:text id="typeFourBus" name="typeFourBus" col="7" value="${traCredit.typeFourBus}"
                             disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务表内外属性" col="5" formControl="true"/>
                    <bf:text id="tableAttrBus" name="tableAttrBus" col="7" value="${traCredit.tableAttrBus}"
                             disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务条线" col="5" formControl="true"/>
                    <bf:text id="busLine" name="busLine" col="7" value="${traCredit.busLine}" disabled="true">
                    </bf:text>
                </bc:col>--%>
                <bc:col col="4">
                    <bc:label cls="required" text="银行内部授信审批编号" col="5" formControl="true"/>
                    <bf:text id="internalApplyNo" name="internalApplyNo" col="7" value="${traCredit.internalApplyNo}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label cls="required" text="授信额度(万元/人民币)" col="5" formControl="true"/>
                    <bf:text id="creditAmount" name="creditAmount" col="7" value="${traCredit.creditAmount}"
                             change="getIsImportTrade()">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                    <bf:select id="importantTrade" name="importantTrade" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-isNo" col="7"
                               value="${traCredit.importantTrade}" disabled="true">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="担保方式" col="5" formControl="true"/>
                    <bf:select id="guaraMode" name="guaraMode" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-GuaranteeMode" col="7"
                               value="${traCredit.guaraMode}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证人证件类型" col="5" formControl="true"/>
                    <bf:select id="bzrIdentifyType" name="bzrIdentifyType" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-IdentifyType" col="7"
                               value="${traCredit.bzrIdentifyType}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证人证件号" col="5" formControl="true"/>
                    <bf:text id="bzrIdentNumber" name="bzrIdentNumber" col="7"
                             value="${traCredit.bzrIdentNumber}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证人名称" col="5" formControl="true"/>
                    <bf:text id="guarantor" name="guarantor" col="7" value="${traCredit.guarantor}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证金金额(万元/人民币)" col="5" formControl="true"/>
                    <bf:text id="securitDeposit" name="securitDeposit" col="7" value="${traCredit.securitDeposit}"
                             change="getIsImportTrade()">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证金比例" col="5" formControl="true"/>
                    <bf:text id="marginRatio" name="marginRatio" col="7" value="${traCredit.marginRatio}"><span>%</span>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="质押的银行存单金额(万元/人民币)" col="5" formControl="true"/>
                    <bf:text id="bankDeposit" name="bankDeposit" col="7" value="${traCredit.bankDeposit}"
                             change="getIsImportTrade()">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="质押的国债金额(万元/人民币)" col="5" formControl="true"/>
                    <bf:text id="nationalDebt" name="nationalDebt" col="7" value="${traCredit.nationalDebt}"
                             change="getIsImportTrade()">
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
                <bc:col col="4">
                    <bc:label text="其中外币授信额度折算为本币金额" col="5" formControl="true"/>
                    <bf:text id="foreignCurrency" name="foreignCurrency" col="7" value="${traCredit.foreignCurrency}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="12">
                    <bc:label cls="required" text="授信方案" col="0" formControl="true"/>
                    <bf:text id="creditPlan" name="creditPlan" col="7"
                             multiple="true" value="${traCredit.creditPlan}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="12">
                    <bc:label text="其他交易信息" col="0" formControl="true"/>
                    <bf:text id="remark" name="remark" col="7"
                             multiple="true" value="${traCredit.remark}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <div id="tableDiv" style="width:95%;margin:0 auto">
                <bc:table id="docTable" idField="pid" url="${base}/bus/traEnclosure" multiple="true"
                          selectFirst="false" queryUrl="queryList?transNum=${traCredit.transNum}">
                    <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
                    <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>
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

            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding: 3% 0 0 0">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            var docInnerPids = getAllIdSelections();
<%--                            if (docInnerPids == '' || docInnerPids == undefined) {--%>
<%--                            alert("文件不能为空,请选中或上传文件");--%>
<%--                            return;--%>
<%--                            }--%>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            var docTableSelectItem = {
                            'name':'docTableSelectItem',
                            'value':docInnerPids
                            };
                            formData.push(docTableSelectItem);
                            $.mask.post('${base}/bus/traCredit/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/bus/traCredit/list';
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
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/traInternaiTran/list';
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
                            <img src="../../static/img/sendfile.png"
                                 style="top:28px;position:relative;z-index :0;font-size: 20px;"/>
                            <a href="javascript:;" class="file" style="top:28px;position:relative;z-index :0;">选择文件
                                <input type="file" class="form-control" id="file" name="file" multiple
                                       style="top:-58px;position:relative;z-index :1;opacity:0;padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
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
        if (opt == 'edit') {
            if (businessType == 'comprehensive') {
                $('#tranScopeDiv').show();
            } else {
                $('#tranScopeDiv').hide();
            }
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

    //表单提交数据
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

    function getInfo() {
        var relatedName = $("#relatedName").val();
        var identifyType = $("#identifyType").val();
        var identNumber = $("#identNumber").val();
        var obj = {
            'relatedName': relatedName,
            'identifyType': identifyType,
            'identNumber': identNumber
        }
        $.ajax({
            type: 'post',
            url: '${base}/bus/traCredit/getRelInfo',
            dataType: 'json',
            data: obj,
            success: function (data) {
                if (data.success != undefined) {
                    if (data.success) {
                        var relType = data.relType;
                        var relId = data.relId;
                        var name = data.name;
                        var identifyType = data.identifyType;
                        var identifyNumber = data.identifyNumber;
                        $('#relType').val(relType);
                        $('#relType').select2({value: relType});
                        $('#relId').val(relId);
                        $("#relatedName").val(name);
                        $("#identNumber").val(identifyNumber);
                    }
                    if (!data.success) {
                        alert(data.msg);
                        $("#identNumber").val('');
                        $("#relatedName").val('');
                    }
                }
            }
        })
    }

    function getIsImportTrade() {
        var creditAmount = $('#creditAmount').val();
        var securitDeposit = $('#securitDeposit').val();
        var bankDeposit = $('#bankDeposit').val();
        var nationalDebt = $('#nationalDebt').val();
        var relType = $('#relType').val();
        var relId = $('#relId').val();
        var pid = $('#pid').val();
        var obj = {
            creditAmount: creditAmount,
            relType: relType,
            relId: relId,
            pid: pid,
            securitDeposit: securitDeposit,
            bankDeposit: bankDeposit,
            nationalDebt: nationalDebt
        };
        $.ajax({
            url: '${base}/bus/traCredit/judgeIsImportTrade',
            data: obj,
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    var isImportTrade = data.isImportTrade;
                    if (isImportTrade) {
                        $('#importantTrade').val('1');
                        $('#importantTrade').select2({value: '1'});
                    } else {
                        $('#importantTrade').val('0');
                        $('#importantTrade').select2({value: '0'});
                    }

                } else {
                    alert(data.msg);
                }
            }
        });
    }

    function getProInfo() {
        var productName = $('#productName').val();
        var obj = {
            productName: productName
        };
        $.ajax({
            type: 'post',
            url: '${base}/bus/product/proInfo',
            dataType: 'json',
            data: obj,
            success: function (data) {
                var proId = data.productId;
                var busLine = data['01'];
                var typeOneBus = data['02'];
                var typeTwoBus = data['03'];
                var typeThreeBus = data['04'];
                var typeFourBus = data['05'];
                $('#busLine').val(busLine);
                $('#typeOneBus').val(typeOneBus);
                $('#typeTwoBus').val(typeTwoBus);
                $('#typeThreeBus').val(typeThreeBus);
                $('#typeFourBus').val(typeFourBus);
                $('#productCode').val(proId);
            }
        });
    }

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

    // $("#validityContract").datetimepicker({
    //     format: "yyyy-mm-dd hh:ii:00",
    //     language: "zh-CN",
    //     minuteStep: 30,
    //     autoclose: 2,
    //     todayBtn: "linked",
    //     minView: "hour"
    // });
    // var a =$("#pid1").val();
    // alert(a);

    //在必填字段前面加上红星
    $(document).ready(function(){
        $(".required").each(function() {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>"+$this.html());
        });
    });
</script>
</body>
</html>
