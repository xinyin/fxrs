<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>待生成报送文件数据</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">


                    <bf:formGroup col="4">
                        <bc:label cls="required" text="年度" col="5" formControl="true"/>
                        <bf:select id="subYear" name="__LIKES_subYear" col="7">
                            <bf:option text="请选择" value="%"></bf:option>
                        </bf:select>
                    </bf:formGroup>

                    <bf:formGroup col="4">
                        <bc:label text="季度" col="5" formControl="true"/>
                        <bf:select id="subQuarter" name="__LIKES_subQuarter" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Quarter" value="">
                            <bf:option text="请选择季度" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_pubQuarter");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="publogTimeTable" idField="pid" url="${base}/pub/publog" queryUrl="queryTraVo"  multipleVisible="false">
                <bc:toolbar id="subFile" text="生成披露文件" type="button" handler="subFile"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="no" title="序号" width="100"/>
                <bc:column field="tranYear" title="年度" width="100"/>
                <bc:column field="tranQuarter" title="季度" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Quarter"/>
                <bc:column field="relName" title="交易对手名称" width="100"/>
                <bc:column field="tranType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
                <bc:column field="remark" title="其他事项说明" width="100"/>
                <bc:column field="tradeAmount" title="关联交易金额（万元）" width="100"/>
                <%--                <bc:column field="status" title="交易状态" width="100"--%>
                <%--                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>--%>
            </bc:table>
            <bc:button type="primary" text="返回" icon="arrow-circle-left">
                <be:event>
                    window.location = '${base}/pub/publog/lists';
                </be:event>
            </bc:button>

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
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
                    <button type="button" class="btn btn-primary" id="upload">确认</button>
                </div>
            </div>
        </div>
    </div>
</div>


<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    // var num;
    // var fileNamed;
    // var pid;


    $(function () {
        $.get("${base}/pub/pubfile/query?type=2", {}, function (data) {
            var po = data.rows;
            for (var i = 0; i < po.length; i++) {
                $("#stype").append('<option value=' + po[i].pid + '>' + po[i].tempFileName + '</option>');
            }
        });
    });

    $(document).ready(function () {
        /*向下拉框中填入数据*/
        $(function () {
            var dd = new Date();
            var currentYear = dd.getFullYear();
            var years = [];
            for (var i = 1; i <= 10; i++) {
                years.push(currentYear--);
            }
            // var size = currentYear - 10 + 1;
            for (var i = years.length; i > 0; i--) {
                var yearOld = currentYear + i;
                $("#subYear").append($("<option value=" + yearOld + ">" + yearOld + "年" + "</option>"));
            }
        });
    });

    function subFile() {
        var subYear = $('#subYear').val();
        var subQuarter = $('#subQuarter').val();

        if (subYear == '%' || subQuarter == '%') {

            $.messager.alert('提示',"请先查询披露数据的年度与季度", 'error');
            return;
        }
        $("#hiddenBtn").click();
    }


    //生成报送文件
    function frmSubmitFile() {
        var subType = "09";
        var subOrg = $('#subOrg').val();
        var subYear = $('#subYear').val();
        //var subYear = $('#subYear').val();
        var subQuarter = $('#subQuarter').val();
        var url = '${base}/pub/publog/quaterFile';
        $.ajax({
            url: url,
            type: 'post',
            data: {
                subType: subType,
                subOrg: subOrg,
                subYear: subYear,
                subQuarter: subQuarter
            },
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    $.messager.alert('成功', data.msg, 'info', function () {
                        window.location = '${base}/sub/insAllbustraninfo/list';
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
    }


    $("#upload").click(function () {

        var fileName = $('#fileName').val();
        var filePid = getStype();

        if (filePid === '0') {
            alert("请选择披露模板！")
            return false;
        }

        var subType = "09";
        var pubYear = $("[name='__LIKES_subYear']").val();
        var pubQuarter = $('#subQuarter').val();
        var url = '${base}/pub/publog/quaterFile';

        // var r = confirm("你确定要生成"+pubYear+"年第"+pubQuarter+"季度披露文件?")


        $.ajax({
            url: url,
            type: 'post',
            data: {
                subType: subType,
                subYear: pubYear,
                subQuarter: pubQuarter,
                fileName: fileName,
                filePid: filePid
            },
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    $.messager.alert('成功', data.msg, 'info', function () {
                        window.location = '${base}/pub/publog/lists';
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

    })


    function getStype() {
        return $('#stype option:selected').val()
    }


    function exportIn() {
        $("#hiddenBtn").click();
    }


    $("#pubYear").datetimepicker({
        format: "yyyy",
        autoclose: true,
        startView: 4,
        minView: 4,
        todayHighlight: true,

    });
</script>
</body>
</html>