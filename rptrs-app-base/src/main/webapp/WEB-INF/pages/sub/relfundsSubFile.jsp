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
        <bc:ibox title="待生成报送文件数据" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="报送机构" col="5" formControl="true"/>
                        <bf:text id="subOrg" name="subOrg" col="7"
                                 value="复星保德信人寿保险有限公司"
                                 disabled="true">
                        </bf:text>
                    </bf:formGroup>

                    <bf:formGroup col="4">
                        <bc:label text="年度" col="5" formControl="true"/>
                        <bf:datebox id="subYear" name="__LIKES_subYear" col="7" format="date"/>
                        <bv:date format="YYYY" message="只能输入合理的年份，类型为YYYY类型"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="季度" col="5" formControl="true"/>
                        <bf:select id="subQuarter" name="__LIKES_subQuarter" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Quarter" >
                            <bf:option text="请选择" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#subQuarter");
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
            <bc:table id="insRelfundsTable" idField="pid" url="${base}/sub/insRelfunds"
                      queryUrl="querySubFile">
                <bc:toolbar id="subFile" text="生成报送文件" type="button" handler="frmSubmitFile"/>
<%--                <bc:toolbar id="exportHandler" text="模板下载" type="button" handler="exportHandler"/>--%>
<%--                <bc:toolbar id="importSimple" text="导入" type="button" handler="importHandler"/>--%>
<%--                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>--%>
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="relType" title="关联方类别" width="100" formatter="custom:sys/dictItem/getDictItems/Rel_POP"/>/
                <bc:column field="relName" title="关联方名称" width="100"/>/
                <bc:column field="relNumber" title="关联方代码" width="100"/>
                <bc:column field="corpInvestFund" title="资金运用投资余额（万元)" width="100"/>
<%--                <bc:column field="corpTotalFund" title="关联方总资产投资（万元)" width="100"/>--%>
            </bc:table>
            <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                <bc:button type="primary" text="返回" icon="arrow-circle-left">
                    <be:event>
                        window.location = '${base}/sub/insRelfunds/list';
                    </be:event>
                </bc:button>
            </div>
        </bc:ibox>
    </div>
</div>
<div id="exampleModalCenter" role="dialog"
     style="display: none">
    <div class="modal-body">
        <div style="text-align: center" id="loading"><p style="color: dodgerblue">正在生成报送文件，请稍后操作...</p></div>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    //生成报送文件
    function frmSubmitFile() {
        var subType = "relfunds";
        var subOrg = "复星保德信人寿保险有限公司";
        var subYear = $("[name='__LIKES_subYear']").val();
        //var subYear = $('#subYear').val();
        var subQuarter = $('#subQuarter').val();
        var url = '${base}/sub/sublog/quaterFile';
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
            beforeSend: function (XMLHttpRequest) {
                $("#exampleModalCenter").attr("style", "display:block;");
            },
            success: function (data) {
                if (data.success) {
                    $.messager.alert('成功', data.msg, 'info', function () {
                        $("#exampleModalCenter").attr("style", "display:block;");
                        window.location = '${base}/sub/insRelfunds/list';
                    });
                } else {
                    if (data.msg == "暂无报送数据生成") {
                        $("#exampleModalCenter").attr("style", "display:block;");
                        $.messager.alert('提示', data.msg, 'info');
                    } else {
                        $("#exampleModalCenter").attr("style", "display:block;");
                        $.messager.alert('失败', data.msg, 'error');
                    }
                }
            }
        });
    }

    //模板下载
    function exportHandler() {
        var data = "1"
        var url = '${base}/sys/xlsie/RelFundsExcelCallBackImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    //删除
    function removeHandler() {
        if (confirm('确认删除？')) {
            var pid = getIdSelections();
            if (pid == '') {
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            ;
            $.ajax({
                url: '${base}/sub/insRelfunds/removeHandler/' + pid,
                type: 'json',
                success: function (data) {
                    if (data.success) {
                        $.messager.alert('提示', data.msg, 'info', function () {
                            window.location = '${base}/sub/insRelfunds/addSubFile';
                        })
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            });
        }
    }

    //导入
    function importHandler() {
        $.messager.modal({
            winId: 'wImportFromExcel', //winid只能为 wImportFromExcel
            width: 500,
            height: 230,
            args: {
                callback: loadCallback
            },
            title: '单一法人资金运用类汇总表导入',
            url: '${base}/sys/xlsie/import?bean=RelFundsImportCallbackImpl',
            onClose: function () {
                loadCallback();
            }
        });
    }

    /**
     * 重置
     */
    function frmReset() {
        location.reload();
    }

    //获取表单选择的pid
    function getIdSelections() {
        return $.map($('#insAllfundsTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    //刷新表格
    function loadCallback() {
        $('#insRelfundsTable').jTable('refresh');
    }

    $("#subYear").datetimepicker({
        format: "yyyy",
        autoclose: true,
        startView: 4,
        minView: 4,
        todayHighlight: true
    });
    $(document).ready(function () {
        $("#subYear").datetimepicker("setDate", new Date());
    });
</script>
</body>
</html>
