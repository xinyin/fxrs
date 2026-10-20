<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>Doc</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="traFundsTableBox" title="" subTitle="" col="12" collapsible="true">
        <bc:table id="rellogTable" idField="pid，tranType" url="${base}/pub/publogRegular" queryUrl="allFunds">
            <bc:toolbar id="exportIn" text="生成披露文件" type="button" handler="exportIn"/>
            <bc:column field="pid" title="pid" width="100"  visible="false" />
            <bc:column field="relName" title="关联方名称" width="100"/>
            <bc:column field="relNumber" title="关联方代码" width="100"/>
            <bc:column field="relType" title="关联方类别" width="100" formatter="custom:sys/dictItem/getDictItems/INS-relType" />
            <bc:column field="tranType" title="交易类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
            <bc:column field="contractAmount" title="关联交易金额（万元）" width="100"/>
            <bc:column field="majorTrade" title="是否重大" width="100" formatter="fixedDict:IsStatusDict" />
            <bc:column field="pricingPolicy" title="定价政策" width="100"/>
        </bc:table>
    </bc:ibox>
</div>
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
                    <button type="button" class="btn btn-primary" id="upload">确认</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
                </div>
            </div>
        </div>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {
        $.get("${base}/pub/pubfile/query?type=1", {}, function (data) {
            var po = data.rows;
            for (var i = 0; i <= po.length; i++) {
                $("#stype").append('<option value=' + po[i].pid + '>' + po[i].tempFileName + '</option>');
            }
        });
    });
    function getIdTraFundsTable() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }
    function exportIn() {

        $("#hiddenBtn").click();
    }
    $("#upload").click(function () {
        //首先得获取交易类型 num
        //然后在获取行pid
        var fileName = $('#fileName').val();
        var  tranType=getTranType();
        var pid=getPid();
        var pubType="1";

        //获取选中的模板
        var filePid = getStype();
        if (filePid === '0') {
            $.messager.alert('提示', "请选择披露模板", 'info');
            return false;
        }
        if(fileName==null || fileName== "" || fileName==undefined){
            $.messager.alert('提示', "文件名不能为空", 'info');
            return false;
        }
        var filePath;
        $.ajax({
            type: "GET",
            url: "${base}/pub/pubfile/toGenerateWord/" +tranType+'/'+pid+'/'+ filePid + '/' + fileName+'/'+pubType,
            dataType: "json",
            success: function (data) {
                if (data.code === '200') {
                    $.messager.alert('信息', "操作成功！", 'info');
                    window.location = '${base}/pub/publogRegular/regularList';
                    // $("#close").click();
                    /*$.ajax({
                        type: "POST",
                        url: "

                    ${base}/pub/pubfile/word2Pdf",
                        data: {"fileUrl": data.fileUrl, "fileName": fileName},
                        dataType: "json",
                        success: function (success) {
                            if (success.code === '200') {
                                $("#close").click();
                            }
                        }
                    });*/
                } else {
                    $.messager.alert('错误', "系统发生未知异常，请联系管理员！", 'error');
                }
            }
        });

    });
    function getStype() {
        return $('#stype option:selected').val()
    }
    function getTranType() {
        return $.map($('#rellogTable').bootstrapTable('getSelections'), function (row) {
            return row.tranType//返回数据行中的关联类型
        });
    }
    function getPid() {
        return $.map($('#rellogTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的pid
        });
    }
</script>
</body>
</html>
