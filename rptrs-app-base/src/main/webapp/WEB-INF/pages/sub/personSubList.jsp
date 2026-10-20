<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <style type="text/css">
        #personTable_tb_submitFile, #personTable_tb_remove,
        #personTable_tb_downFile
        {
            display: none;

        }
    </style>
    <br:css pack="list"/>
    <title>Sublog</title>
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
        <bc:ibox title="" subTitle="自然人关联方档案报送" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="3">
                        <bc:label text="文件名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_fileName" col="7" placeholder="文件名称"/>
                    </bf:formGroup>
                   <%-- <bf:formGroup col="3">
                        <bc:label text="报送状态" col="5" formControl="true"/>
                        <bf:select name="__EQS_status" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/SUBMIT-status">
                            <bf:option text="请选择" value=""></bf:option>
                        </bf:select>
                    </bf:formGroup>--%>
                    <bc:row cls="m-b">
                        <div class="col-sm-12 col-sm-offset-5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                        </div>
                    </bc:row>
                </bc:row>
            </bf:form>
            <bc:table id="personTable" idField="pid" url="${base}/sub/sublog" selectFirst="true"
                      queryUrl="query/person">
                <bc:toolbar id="submitFile" text="新增" type="button" handler="addSubFile"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="downFile" text="xml文件下载" type="button" handler="downFileOnclick"/>
                <bc:toolbar id="downFile1" text="Excel文件下载" type="button" handler="downFileOnclick1"/>
              <%--  <bc:toolbar id="submission" text="确认报送" handler="submissionHandler" type="button"/>--%>
                <bc:column field="pid" title="主键名" width="100" visible="false"/>
                <bc:column field="fileName" title="文件名称" width="100"/>
                <bc:column field="subOrg" title="报送机构"  width="100" />
                <bc:column field="maxPercentInNetAssets" title="生成报送文件时间" width="100"/>
                <bc:column field="operatorName" title="操作员" width="100"/>
               <%-- <bc:column field="status" title="报送状态" width="100" formatter="custom:sys/dictItem/getDictItems/SUBMIT-status"/>
                <bc:column field="submissionDateString" title="报送日期" width="100"/>--%>
                <bc:operator id="personView" icon="eye" title="操作" handler="personView"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<div>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"
            id="hiddenBtn"
            style="display: none">
        确认报送文件按钮
    </button>
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="boxse">
                        <bf:form id="editForm" method="post" validation="true">
                            <bf:formGroup type="success">
                                <bc:col col="12">
                                    <bc:label cls="required" text="报送日期" col="3" formControl="true"/>
                                    <bf:datebox name="submissionDate" id="submissionDate" col="6" format="date"
                                                value="${sublog.submissionDate}">
                                        <bv:notEmpty/>
                                    </bf:datebox>
                                </bc:col>
                            </bf:formGroup>
                        </bf:form>
                    </div>
                </div>
                <div class="modal-body">
                    <div style="text-align: center" id="loading"></div>
                    <div class="form-group"
                         style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
                        <button type="button" class=" btn btn-danger" id="upload"><i class="fa fa-save"></i> 确定</button>
                        <button type="button" class=" btn btn-primary" data-dismiss="modal" aria-label="Close"><i
                                class="fa fa-arrow-circle-left"></i> 返回
                        </button>
                        <%--                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>--%>
                        <%--                        <button type="submit" class="btn btn-primary" id="upload">上传</button>--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div  id="exampleModalCenter1" role="dialog"
      style="display: none">
    <div class="modal-body">
        <div style="text-align: center" id="removing"><p style="color: dodgerblue">正在删除报送文件，请稍后操作...</p></div>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    //根据权限显示按钮
    $(function () {
        var code = "${code }";
        if (code.search("01,") != -1) {
            $('#personTable_tb_submitFile').show();
        }
        if (code.search("03,") != -1) {
            $('#personTable_tb_remove').show();
        }
        if (code.search("013") != -1) {
            $('#personTable_tb_downFile').show();
        }

    })


    //获取审批记录
    function send() {
        // 获取审批记录
        $.ajax({
            url:"${base}/sub/sublog/getApprovalLog",
            method:"GET",
            dataType:"JSON",
            traditional:true,
            success:function(data){
                if(data.success){
                    $.messager.alert('提示', data.msg, 'info');
                }else
                {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        })
    }


    function addSubFile() {
        window.location = '${base}/sub/person/addSubFile';
    }




    function viewHandler() {
        debugger;
        var pid = getIdSelections();
        if (pid == '') {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location = '${base}/sub/person/view/'+ pid;
    }

    //删除
    function removeHandler() {
        var status = getStatusSelections();
        if (status === undefined || status.length !== 1) {
            $.messager.alert('警告', "请选择一条数据", 'warning');
            return;
        }
        if (status == "02") {
            $.messager.alert('错误', " 已报送数据无法删除", 'error');
            return;
        }
        if (confirm('确认删除？')) {
            var pid = getIdSelections();
            if (pid == '') {
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            $.ajax({
                url: '${base}/sub/person/remove/' + pid,
                type: 'json',
               /* beforeSend: function (XMLHttpRequest) {
                    $("#exampleModalCenter1").attr("style","display:block;");
                },*/
                beforeSend: function (jqXHR, settings) {
                    $.mask.show('正在删除，请稍后。。。');
                },
                success: function (data) {
                    $.mask.remove();
                    if (data.success) {
                        $.messager.alert('提示', data.msg, 'info', function () {
                            window.location = '${base}/sub/person/sublist';
                        })
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            });
        }
    }

    //确认报送
    function submissionHandler() {
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        var status = getStatusSelections();
        //已下载&未报送
        if (status == '02') {
            $.messager.alert('错误', "无法重复报送", 'error');
            return;
        }
        $("#hiddenBtn").click();
    }

    //提交报送
    $("#upload").click(function () {
        var pid = getIdSelections();
        var formDate = $("#submissionDate").find("input").val();
        var subType = "pro";
        var myDate = new Date().format("yyyy-MM-dd");
        if (formDate > myDate) {
            $.messager.alert('错误', "报送时间不得大于当前时间", 'error');
            return;
        }
        $.ajax({
            type: 'post',
            url: '${base}/sub/sublog/saveSubmission/' + pid + '/' + formDate + '/' + subType,
            cache: false,
            processData: false,
            contentType: false,
            beforeSend: function (XMLHttpRequest) {
                //alert('远程调用开始...');
                $("#loading").html("<p style=\"color: dodgerblue\">正在报送，请稍后操作...</p>");
            },
            success: function (data) {
                if (data.success) {
                    $.messager.alert('提示', '报送成功', 'info', function () {
                        window.location = '${base}/sub/person/sublist';
                    });
                }
            },
            error: function () {
                $("#close").click();
                alert("报送失败,请重试");
                $("#loading").empty();
            }
        })
    });
    //当前时间格式化

    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    }


    //文件下载
    function downFileOnclick() {
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        //var url = '${base}/sub/credit/subDownFile/' + pid;
        var url1 = '${base}/sub/credit/subDownFile/' + pid;
        var elemIF = document.createElement("iframe");
        elemIF.src = url1;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }
    //获取表单的审批状态
    function getStatusSelections() {
        return $.map($('#personTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    //获取下载状态
    function getDownStatusSelections() {
        return $.map($('#personTable').bootstrapTable('getSelections'), function (row) {
            return row.downStatus;
        });
    }

    //获取表单选择的pid
    function getIdSelections() {
        return $.map($('#personTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    //重置
    function frmReset() {
        window.location = '${base}/sub/person/sublist';
    }
    function personView(){
        //获取pid
        var pid=getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location = '${base}/sub/person/view/' + pid;
    }
    //数据导出
    function downFileOnclick1() {
        var pid = getIdSelections();
        var data = pid;
        // if(getselectDataId.length>0){
        //     data = dataId
        // }else {
        //     data = type + "," + proName + "," + proType + "," + proProvider;
        // }
        var url = '${base}/sys/xlsie/PersonExportCallbackImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }


</script>
</body>
</html>
