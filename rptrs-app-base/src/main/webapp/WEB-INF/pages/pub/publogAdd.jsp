<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ taglib prefix="be" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>披露年度</title>
    <%--    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>--%>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="addBox" title="" subTitle="" col="12" collapsible="true">
            <bc:table id="pubDataTable" idField="pid" url="${base}/pub/publog"
                      queryUrl="queryData">
                <bc:toolbar id="exportIn" text="生成披露文件" type="button" handler="exportIn"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="relNumber" title="关联方证件号码" width="100"/>
                <bc:column field="relType" title="关联方类别" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="tranType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
                <bc:column field="tradeAmount" title="关联交易金额" width="100"/>
                <bc:column field="isAgreement" title="是否为统一交易协议" width="10" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="tradeSubject" title="交易标的" width="100"/>
            </bc:table>
        <bc:button type="primary" text="返回" icon="arrow-circle">
            <be:event>
                window.location = '${base}/pub/publog/list';
            </be:event>
        </bc:button>
    </bc:ibox>
</div>
<div id="x">
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"
            id="hiddenBtn"
            style="display: none">
        选择披露模板按钮
    </button>
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalCenterTitle">选择披露模板</h5>

                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <label>披露模板</label>&nbsp;&nbsp;&nbsp;&nbsp;
                    <select style="height: 30px;width: 150px" name="select" id="stype">
                        <option value="0" selected="selected">请选择披露模板</option>
                    </select>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <label>文件名称</label>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="text" id="fileName" style="height: 30px;width: 150px"/>
                </div>
                <div class="form-group"
                     style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
                    <button type="button" class="btn btn-primary" id="upload">确认</button>
                </div>
            </div>
        </div>
    </div>
</div>

<br:js pack="form"/>
<be:processEvent/>
</body>
<script type="text/javascript">
    var num;
    var fileNamed;
    var pid;
    $(function () {
        $.get("${base}/pub/pubfile/query?type=1", {}, function (data) {
            var po = data.rows;
            for (var i = 0; i <= po.length; i++) {
                $("#stype").append('<option value=' + po[i].pid + '>' + po[i].tempFileName+ '</option>');
            }
        });
    });




    function exportIn() {
        $("#hiddenBtn").click();
    }

    //查看文件
    /*showFile = function () {
        //选中的文件
        var tableId;
        if (num === '01') {
            tableId = getIdTraInvestmentTable()
        } else if (num === '02') {
            tableId = getIdTraFundsTable()
        } else if (num === '03') {
            tableId = getIdTraBusinessTable()
        } else if (num === '04') {
            tableId = getIdTraInterestTable()
        } else if (num === '05') {
            tableId = getIdTraServiceTable()
        } else if (num === '06') {
            tableId = getIdTraOtherTable()
        }

        var fileName = "预览文件";
        if (tableId == '' || tableId == undefined) {
            alert("请选中一个文件");
            return;
        }
        if (tableId.indexOf(',') != -1) {

            alert("只能选中一个文件");
            return;
        }
        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/pub/pubfile/preview?pid=' + tableId + '&fileName= 文件预览.pdf&tranType='+num+''));


    }*/

    $("#upload").click(function () {
        //首先得获取交易类型 num
        //然后在获取行pid
        var fileName = $('#fileName').val();
        var tableId = getIdPubDataTable();
        num=getTranTypePubDataTable();
        var filePid = getStype();
        if (filePid === '0') {
            $.messager.alert('提示', "请选择披露模板", 'error');
            return false;
        }
        var filePath;
        $.ajax({
            type: "GET",
            url: "${base}/pub/pubfile/toGenerateWord/" + num + '/' + tableId + '/' + filePid + '/' + fileName+'/'+'1',
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    $.messager.alert('成功', data.msg, 'info', function () {
                        window.location = '${base}/pub/publog/list';
                    });

                } else {
                    if (data.msg == "暂无报送数据生成") {
                        $.messager.alert('提示', data.msg, 'info');
                    } else {
                        $.messager.alert('失败', data.msg, 'error');
                    }
                }
            }
        });

    });

    function getStype() {
        return $('#stype option:selected').val()
    }


    function getIdPubDataTable() {
        return $.map($('#pubDataTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }
//返回行中统一交易协议状态
    function getIsAgreementPubDataTable() {
        return $.map($('#pubDataTable').bootstrapTable('getSelections'), function (row) {
            return row.isAgreement//返回数据行中的值
        });
    }
    function getTranTypePubDataTable() {
        return $.map($('#pubDataTable').bootstrapTable('getSelections'), function (row) {
            return row.tranType//返回数据行中的值
        });
    }


    function frmReset() {
        window.location = '${base}/pub/publog/add';
    }



    $("#pubYear").datetimepicker({
        format: "yyyy",
        autoclose: true,
        startView: 4,
        minView: 4,
        todayHighlight: true
    });
</script>
</html>