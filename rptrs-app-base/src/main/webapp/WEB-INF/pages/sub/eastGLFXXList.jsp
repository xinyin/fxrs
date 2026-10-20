<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">
        #glfxTable_tb_addNewFile, #glfxTable_tb_remove,
        #glfxTable_tb_downFile
        {
            display: none;

        }
    </style>
    <title>RelPar</title>
    <style>
        .form-group .input-group.date,
        .form-group .select2 {
            width: 100% !important;
        }

        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="关联方信息表" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="5">
                        <bc:label text="文件名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_fileName" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div title="生成时间">
                            <bc:label cls="required" text="生成时间" col="5" formControl="true"/>
                            <bf:datebox  id="__LIKES_createTime" name="__LIKES_createTime" col="7" value="${entrust.createTime}"
                                         disabled="${opt == 'view'}">
                                <bv:notEmpty/>
                            </bf:datebox>
                        </div>
                    </bf:formGroup>
                    <bc:row cls="m-b">
                        <div class="col-sm-12 col-sm-offset-5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                        </div>
                    </bc:row>
                </bc:row>
            </bf:form>
            <bc:table id="glfxTable" idField="pid" url="${base}/sub/sublog" queryUrl="queryEast/E1">
                <bc:toolbar id="addNewFile" text="新增" type="button" handler="addHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="downFile" text="下载文件" type="button" handler="downFileOnclick"/>
                <bc:column field="id" title="id" width="100" visible="false"/>
                <bc:column field="batchDecimal" title="batchDecimal" width="100" visible="false"/>
                <bc:column field="fileName" title="文件名称" width="100"/>
                <bc:column field="createTimeString" title="生成时间" width="100"/>
<%--                <bc:column field="subOrg" title="报送机构" width="100"/>--%>
                <bc:column field="operatorName" title="操作员" width="100"/>
                <bc:operator id="viewData" icon="eye" title="查看" handler="viewHandler"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    //根据权限显示按钮
    $(function () {
        var code = "${code }";
        if (code.search("01,") != -1) {
            $('#glfxTable_tb_addNewFile').show();
        }
        if (code.search("03,") != -1) {
            $('#glfxTable_tb_remove').show();
        }
        if (code.search("013") != -1) {
            $('#glfxTable_tb_downFile').show();
        }

    })


    //下载文件
    function downFileOnclick() {
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        var url = '${base}/sub/credit/subEastDownFile/' + pid;
        if (true) {
            var elemIF = document.createElement("iframe");
            elemIF.src = url;
            elemIF.style.display = "none";
            document.body.appendChild(elemIF);
        } else {
            $.messager.alert('警告', "当前状态不支持下载，请查看！", 'warning');
            //当状态未通过时按钮置灰 不可点击
            document.getElementById("downFile").setAttribute("disabled", true);
        }
    }
   /* $(function () {
        $('#glfxTable_tb_addNewFile').show();
    });*/

    function addHandler() {
        window.location = '${base}/east/glfxx/add';
    }
    //删除文件
    function removeHandler() {
        $.messager.confirm('提示', '确定要删除吗？', function (r) {
            if (r) {
                var pid = getIdSelections();
                if (pid == '' || pid == null || pid.length != 1) {
                    $.messager.alert('错误', "请选择一条数据", 'error');
                    return;
                }
                $.ajax({
                    url: '${base}/east/glfxx/remove/' + pid,
                    type: 'json',
                    success: function (data) {
                        if (data.success) {
                            $.messager.alert('提示', data.msg, 'info', function () {
                                window.location = '${base}/east/glfxx/list';
                            })
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    }
                });
            }
        });
    }
    //重置
    function frmReset() {
        window.location = '${base}/sub/sublog/queryEast/E1';
    }
    function getBatchNumberSelections() {
        return $.map($('#glfxTable').bootstrapTable('getSelections'), function (row) {
            return row.batchDecimal;
        });
    }
    function getIdSelections() {
        return $.map($('#glfxTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    function viewHandler() {
        var batchNumber = getBatchNumberSelections();
        window.location = '${base}/east/glfxx/view/' + batchNumber;
    }
</script>
</body>
</html>