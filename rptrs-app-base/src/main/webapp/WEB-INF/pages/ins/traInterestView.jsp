<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>关联交易表（保险）-利益转移类</title>
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
        .form-group {
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

        .gray-bg {
            background: white !important;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="traInterestBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${traInterest.pid}"/>
            <bf:hidden id="transNum" name="transNum" value="${traInterest.transNum}"/>
            <bf:hidden id="relId" name="relId" value="${traInterest.relId}"/>
            <bc:row cls="m-b">
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label cls = "required" text="关联方证件号码" col="5" formControl="true"/>
                    <bf:text id="relNumber" name="relNumber" col="7" value="${traInterest.relNumber}"
                             disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="交易对手类型" col="5" formControl="true"/>
                    <bf:select id="relType" name="relType" dataSource="custom"
                               url="sys/dictItem/getDictItems/INS-relType" col="7"
                               value="${traInterest.relType}" disabled="true">
                        <bv:notEmpty/>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="交易对手名称" col="5" formControl="true"/>
                    <bf:text id="relName" name="relName" col="7" value="${traInterest.relName}"
                             change="getInfo()" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <div title="本年度与该关联方发生的关联交易累计金额（万元/人民币）">
                        <bc:label text="关联交易累计金额" col="5" formControl="true"/>
                        <bf:text id="totolTradeAmount" name="totolTradeAmount" col="7"
                                 value="${traInterest.totolTradeAmount}" disabled="true">
                        </bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                    <bf:select id="majorTrade" name="majorTrade" dataSource="fixedDict"
                               url="IsStatusDict" col="7"
                               value="${traInterest.majorTrade}" disabled="true">
                        <bf:option text="-" value=""></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="统计口径" col="5" formControl="true"/>
                    <bf:select id="caliber" name="caliber" col="7" value="YBJH" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Caliber" disabled="true">
                    </bf:select>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="交易类型" col="5" formControl="true"/>
                    <bf:select id="tranType" name="tranType" dataSource="custom"
                               url="sys/dictItem/getDictItems/INS-tranType" col="7"
                               value="04" disabled="true">
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <div title="交易日期/合同签订日期">
                        <bc:label cls = "required" text="交易/签订日期" col="5" formControl="true"/>
                        <bf:datebox id="tranDate" name="tranDate" col="7" value="${traInterest.tranDateString}" disabled="true">
                            <bv:notEmpty/>
                        </bf:datebox>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label cls = "required" text="合同/交易编号" col="5" formControl="true"/>
                    <bf:text id="contractNo" name="contractNo" col="7" value="${traInterest.contractNo}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label cls = "required" text="交易金额（万元/人民币）" col="5" formControl="true"/>
                    <bf:text id="tradeAmount" name="tradeAmount" col="7" change="isGreatTrade()"
                             value="${traInterest.tradeAmount}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label cls = "required" text="业务起始日期" col="5" formControl="true"/>
                    <bf:datebox id="busStartTime" name="busStartTime" col="7"
                                value="${traInterest.busStartTimeString}" disabled="true">
                        <bv:notEmpty/>
                    </bf:datebox>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="业务终止日期" col="5" formControl="true"/>
                    <bf:datebox id="busEndTime" name="busEndTime" col="7"
                                value="${traInterest.busEndTimeString}" disabled="true"/>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="产品名称" col="5" formControl="true"/>
                    <bf:lookup id="proName" name="proName" col="7" title="业务级别名称"
                               url="${base}/ins/traInterest/proNameListTree" winId="productViewId"
                               propText="proName" propValue="level"
                               callback="result" onSelect="onSelect" text="${product.proPath}"
                               value="${product.proPath}"  disabled="true" placeholder="选择产品或者业务类型">
                    </bf:lookup>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="产品名称" col="5" formControl="true"/>
                    <bf:lookup id="proName" name="proName" col="7" title="业务级别名称"
                               url="${base}/ins/traInterest/proNameListTree" winId="productViewId"
                               propText="proName" propValue="level"
                               callback="result" onSelect="onSelect" text="${product.proPath}"
                               value="${product.proPath}"  disabled="true" placeholder="选择产品或者业务类型">
                    </bf:lookup>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="业务类型" col="5" formControl="true"/>
                    <bf:select id="busType" name="busType" dataSource="custom"
                               url="ins/traInvestment/getBuinessTypeItems" col="7"
                               value="${traInterest.busType}" disabled="true">
                        <bf:option text="请选择" value=""></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
               <%-- <bf:formGroup col="4">
                    <bc:label cls = "required" text="交易定价是否为国家规定" col="5" formControl="true"/>
                    <bf:select id="isDealPrice" name="isDealPrice" dataSource="fixedDict" disabled="true"
                               url="IsStatusDict" col="7" value="${traInterest.isDealPrice}">
                        <bf:option text="请选择" value=""></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bf:formGroup>--%>
                </bf:formGroup>
            </bc:row>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="6">
                    <bc:label cls = "required" text="交易发生原因及合同、证明文件等" col="3" formControl="true"/>
                    <bf:text id="tradeOccur" name="tradeOccur" col="9" rows="2"
                             multiple="true" value="${traInterest.tradeOccur}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label cls = "required" text="交易价格" col="3" formControl="true"/>
                    <bf:text id="tradePrice" name="tradePrice" col="9" rows="2"
                             multiple="true" value="${traInterest.tradePrice}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="6">
                    <bc:label cls = "required" text="定价政策" col="3" formControl="true"/>
                    <bf:text id="pricingPolicy" name="pricingPolicy" col="9" rows="2"
                             multiple="true" value="${traInterest.pricingPolicy}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label cls = "required" text="交易标的" col="3" formControl="true"/>
                    <bf:text id="tradeSubject" name="tradeSubject" col="9" rows="2"
                             multiple="true" value="${traInterest.tradeSubject}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="6">
                    <bc:label text="其他事项说明" col="3" formControl="true"/>
                    <bf:text id="remark" name="remark" col="9" rows="2"
                             multiple="true" value="${traInterest.remark}" disabled="true">
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                </bf:formGroup>
            </bf:formGroup>

            <div id="tableDiv" style="width:95%;margin:0 auto">
                <bc:table id="docTable" idField="pid" url="${base}/ins/traInsenclosure" multiple="true"
                          selectFirst="false" queryUrl="queryList?transNum=${traInterest.transNum}">
                    <%--<bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>--%>
                    <%--<bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>--%>
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
                <div class="col-sm-4 col-sm-offset-5">
                    <%--<bc:button type="danger" text="保存" icon="save">
                        &lt;%&ndash;traInterest&ndash;%&gt;
                        <be:event>
                            var docInnerPids = getAllIdSelections();
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
                            $.mask.post('${base}/ins/traInterest/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/ins/traInterest/list';
                            });
                            }
                            else
                            {
                            $.messager.alert('错误', data.msg, 'error');
                            }
                            });
                            }
                        </be:event>
                    </bc:button>--%>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/ins/traInterest/list';
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
                            <img src="${base}/static/img/sendfile.png"
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
    $(document).ready(function(){
        changeNext('0');
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

    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        if (pid == '' || pid == undefined) {
            alert("请选中一个文件");
            return;
        }
        if (pid.length > 1) {
            alert("只能选中一个文件");
            return;
        }
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/ins/traInsenclosure/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
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
                url: '${base}/ins/traInsenclosure/removeFile/' + pids,
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
            url: '${base}/ins/traInsenclosure/uploadFile?businessType=04', //上传文件的请求路径必须是绝对路劲
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

    function changeNext(val) {
        if(val=='0'){
            $("#approvalNumDiv").css("display","none")
            $("#securitiesTypeDiv").css("display","block")
        }else if(val=='1'){
            $("#approvalNumDiv").css("display","block")
            $("#securitiesTypeDiv").css("display","none")
        }
    }

    $("#relNumber").blur(function () {
        var relNumber=$("#relNumber").val();
        var relName=$("#relName").val();
        if ((relNumber !== '' && relNumber !== undefined)||(relName !== '' && relName !== undefined)) {
            infoBlur(relNumber,relName);
        }
    });

    //判断是否重大
    $("#tradeAmount").blur(function () {
        var tradeAmount=$("#tradeAmount").val();
        var relId=$("#relId").val();
        if ((tradeAmount !== '' && tradeAmount !== undefined)||(relId !== '' && relId !== undefined)) {
            // infoBlur(relNumber,relName);
        }
    });

    function infoBlur(relNumber,relName) {
        $.ajax({
            type: 'GET',
            url: '${base}/ins/traInvestment/selectRel',
            data: {
                "relName": relName,
                "identifyNumber": relNumber
            },
            dataType: 'json',
            success: function (data) {
                $("#relNumber").empty();
                if (data.success) {
                    $("#relId").val(data.relId);
                    $("#relName").val(data.relName);
                    $("#totolTradeAmount").val(data.totalTradeAmount);
                    $("#caliber").val(data.caliber);
                    $("#caliber").select2({value:(data.caliber)});
                    // $("#majorTrade").val(majorTrade);
                    // $("#majorTrade").select2({value:(majorTrade)});
                }else {
                    $.messager.alert('提示', '关联方不存在！', 'warning');
                }
            }
        });
    }
</script>
</body>
</html>