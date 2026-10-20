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
        <bf:form id="queryForm" validation="true">
            <bc:row cls="m-b">
                <bf:formGroup col="4">
                    <bc:label text="年度" col="5" formControl="true"/>
                    <bf:datebox id="years" name="years" col="7" format="date">
                    </bf:datebox>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="季度" col="5" formControl="true"/>
                    <bf:select id="quarter" name="quarter" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Quarter">
                        <be:event type="select">
                            var input = $("#quarter");
                            input.focus();
                            input.blur();
                        </be:event>
                    </bf:select>
                </bf:formGroup>

            </bc:row>
            <bc:row cls="m-b">
                <div class="col-sm-12 col-sm-offset-5">
                    <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                    <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                </div>
            </bc:row>
        </bf:form>
        <bc:table id="rellogTable" idField="tranType" url="${base}/pub/publogYear" multipleVisible="false"
                  queryUrl="allQuarter?years=${years}&quarter=${quarter}">
            <bc:toolbar id="exportIn" text="生成披露文件" type="button" handler="exportIn"/>
            <bc:column field="tranType" title="(单位:亿元)" width="100" formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
            <bc:column field="sumQuarter" title="本季度各类关联交易总量" width="100"/>
            <bc:column field="sumYear" title="本年度各类关联交易累计金额" width="100" />
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
        // $("input[name='__LIKES_years']").val(new Date().getUTCFullYear());
        $("#years").datetimepicker("setDate", new Date());
        $.get("${base}/pub/pubfile/query?type=2", {}, function (data) {
            var po = data.rows;
            for (var i = 0; i <= po.length; i++) {
                $("#stype").append('<option value=' + po[i].pid + '>' + po[i].tempFileName + '</option>');
            }
        });

    });
    $("#years").datetimepicker({
        format: "yyyy",
        autoclose: true,
        startView: 4,
        minView: 4,
        todayHighlight: true,
    });
    $("#years").change(function () {
        if (
            $("#years").datetimepicker("getDate") == "" ||
            $("#years").datetimepicker("getDate") == null ||
            $("#years").datetimepicker("getDate") == undefined
        ) {
            $("#years").datetimepicker("setDate", new Date());
        }
    });
    function exportIn() {
        var thisDate=new Date();
        var thisYear= $("#years input").val();
        var selectDate;
        switch ($("#quarter").val()) {
            case "1":
                selectDate = thisYear + "-03-31";
                break;
            case "2":
                selectDate = thisYear + "-06-30";
                break;
            case "3":
                selectDate = thisYear + "-09-30";
                break;
            default:
                selectDate = thisYear + "-12-31";

        }
        selectDate=new Date(Date.parse(selectDate.replace(/-/g,  "/")));
        if(selectDate>thisDate){
            $.messager.alert('错误', "本季度尚未结束，不能生成本季度披露文件", 'error');
            return;
        }
        $.ajax({
            type: "post",
            url: "${base}/pub/publogYear/checkData",
            dataType: "json",
            success: function (data) {
                if (data.code === '200') {
                    $("#hiddenBtn").click();
                } else if(data.code === '201') {
                    $.messager.alert('错误', "存在未归类交易数据", 'error');
                }
            }
        });
    }
    $("#upload").click(function () {
        var years = $("#years input").val();
        //var subYear = $('#subYear').val();
        var quarter = $('#quarter').val();
        var fileName = $('#fileName').val();
        if(fileName==null || fileName== "" || fileName==undefined){
            $.messager.alert('提示', "文件名不能为空", 'info');
            return false;
        }
        //获取选中的模板
        var filePid = getStype();
        //获取id和交易类型
        var data = fileName + "," + years + "," + quarter;
        $.ajax({
            type: "post",
            url: "${base}/pub/publogYear/yearExcel/PublogYearExcelCallbackImpl/"+data,
            dataType: "json",
            success: function (data) {
                debugger;
                if (data.code === '200') {
                    $.messager.alert('信息', "操作成功！", 'info');
                    window.location = '${base}/pub/publogYear/list';
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
