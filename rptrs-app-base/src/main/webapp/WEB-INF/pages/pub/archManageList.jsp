<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>归档文件管理</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="年度" col="5" formControl="true"/>
                        <bf:datebox id="archDates" name="archDate" col="7" format="year">
                            <bv:date format="YYYY" message="只能输入合理的年份，类型为YYYY类型"/>
                        </bf:datebox>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="季度" col="5" formControl="true"/>
                        <bf:select id="quarter" name="quarter" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Quarter">
                            <bf:option text="ALL" value=""></bf:option>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="月份" col="3" formControl="true"/>
                        <bf:select id="month" name="month" col="8" dataSource="custom"
                                   url="/sys/dictItem/getDictItems/SUBMIT-month">
                        </bf:select>
                    </bf:formGroup>
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="publogTable" idField="pid" url="${base}/pub/archManage"
                      queryUrl="query1" selectFirst="true">
                <bc:toolbar id="downFile" text="文件下载" type="button" handler="downloadAllFild"/>
                <bc:column field="pubFileName" title="对应文件名称" width="100"/>
                <bc:column field="pubSubject" title="更新部门" width="100"/>
                <bc:column field="createByName" title="操作员" width="100"/>
                <bc:column field="archDateString" title="归档日期" width="100"/>
                <%--                <bc:column field="pubYear" title="年度" width="100"/>--%>
                <%--                <bc:column field="pubQuarter" title="季度" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Quarter" />--%>
                <bc:operator id="downloadOne" icon="download" title="下载" handler="downloadFile"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {
        $("#archDates").datetimepicker("setDate", new Date());

    });
    $("#archDates").datetimepicker({
        format: "yyyy",
        autoclose: true,
        startView: 4,
        minView: 4,
        todayHighlight: true,
    });
    $("#archDates").change(function () {
        if (
            $("#archDates").datetimepicker("getDate") == "" ||
            $("#archDates").datetimepicker("getDate") == null ||
            $("#archDates").datetimepicker("getDate") == undefined
        ) {
            $("#archDates").datetimepicker("setDate", new Date());
        }
    });

    //文件下载-单条
    function downloadFile() {
        var tables = $('#publogTable').jTable('getCheckData')
        var data = []
        tables.forEach(v => {
            data.push(v.pid)
        })
        console.log(data)
        var params = {
            pid: data
        };
        console.log(params);
        var form = document.createElement('form')
        form.id = 'form'
        document.body.appendChild(form)
        for (var obj in params) {
            if (params.hasOwnProperty(obj)) {
                var input = document.createElement('input')
                input.tpye = 'hidden'
                input.name = obj;
                input.value = params[obj]
                form.appendChild(input)
            }
        }
        form.method = "GET" //请求方式
        form.action = '${base}/pub/archManage/download';
        form.submit()
        document.body.removeChild(form)
    }

    /*获取当前数据id*/
    function getIdSelections() {
        return $.map($('#publogTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    /*获取表单状态*/
    function getStatusSelections() {
        return $.map($('#publogTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    //文件下载（全部）
    function downloadAllFild() {
        var tables =  $('#publogTable').jTable('getCheckData')
        var data = []
        tables.forEach(v=>{
            data.push(v.pid)
        })
        console.log(data)
        var params = {
            pid:data
        };
        var form = document.createElement('form')
        form.id = 'form'
        form.name = 'form'
        document.body.appendChild (form)
        for (var obj in params) {
            if (params.hasOwnProperty(obj)) {
                var input = document.createElement('input')
                input.tpye='hidden'
                input.name = obj;
                input.value = params[obj]
                form.appendChild(input)
            }
        }
        form.method = "GET" //请求方式
        form.action = '${base}/pub/archManage/downloadAll'
        form.submit()
        document.body.removeChild(form)

    }

    //重置
    function frmReset() {
        window.location = '${base}/pub/archManage/List';
    }
</script>
</body>
</html>