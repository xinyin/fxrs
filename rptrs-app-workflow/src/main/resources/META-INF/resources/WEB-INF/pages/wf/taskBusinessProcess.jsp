<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">

<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>流程实例列表</title>
    <x:jsLib/>
    <style>
        #nodeKeySelect {
            width: 300px;
        }
    </style>
</head>
<body class="gray-bg">
<input type="hidden" id="clientAddInput" value="${clientAdd}"/>
<input type="hidden" id="tokenInput" value="${token}"/>
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <div style="margin-bottom: auto">
            <bc:ibox title="业务信息" subTitle="" col="12" collapsible="true">
                <iframe style="width: 100%; border: 0;" id="myFrame" name="myFrame"
                        src="${clientAdd}/workflow/client/bizEdit?token=${token}" col="12"></iframe>
            </bc:ibox>
        </div>
        <bc:ibox title="处理" subTitle="" col="12" collapsible="true">

            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <div id="selectNextDept" style="display: none">
                        <bf:formGroup>
                            <bc:label text="总部部门" col="2" formControl="true"/>
                            <div id="demo" style="margin-left: 30%;width: 30%">

                            </div>
                        </bf:formGroup>
                    </div>
<%--                    <bc:table id="docTable" idField="pid" url="${base}/bus/docFile" queryUrl="/query?flag=6"--%>
<%--                              selectFirst="false">--%>
<%--                        <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>--%>
<%--                        <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>--%>
<%--                        <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>--%>
<%--                        <bc:column field="pid" title="主键" width="100" visible="false"/>--%>
<%--                        <bc:column field="fileName" title="文件名称" width="100"/>--%>
<%--                        <bc:column field="fileUrl" title="文件路径" width="100" visible="false"/>--%>
<%--                        <bc:column field="fileType" title="文件类型" width="100"/>--%>
<%--                        <bc:column field="fileSize" title="文件大小" width="100"/>--%>
<%--                        <bc:column field="modifiedTime" title="上传时间" width="100"/>--%>
<%--                    </bc:table>--%>
                    <bf:formGroup>
                        <bc:label text="意见" col="2" formControl="true"/>
                        <bf:text id="memo" name="memo" col="8" multiple="true"></bf:text>
                    </bf:formGroup>

                    <bf:formGroup>
                        <bc:col col="2" colOffset="2">
                            <bc:col col="5">
                                <bc:button type="primary" id="btnPass" text="通过">
                                    <be:event>adopt();</be:event>
                                </bc:button>
                            </bc:col>
                        </bc:col>
                        <bc:col col="4">
                            <bc:col col="5">
                                <bc:button id="btnReject" type="primary" text="退回">
                                    <%--                                    <be:event>reject();</be:event>--%>
                                    <be:event>showModel();</be:event>
                                </bc:button>
                            </bc:col>
                            <bc:col col="2" colOffset="2">
                                <bc:col col="5">
                                    <bc:button type="primary" id="btnPass" text="返回">
                                        <be:event>
                                            window.history.go(-1);
                                        </be:event>
                                    </bc:button>
                                </bc:col>
                            </bc:col>
                        </bc:col>
                    </bf:formGroup>
                </bc:row>
            </bf:form>
        </bc:ibox>
    </div>
    <%--模态框--%>
    <div>
        <button class="btn btn-primary btn-lg" data-toggle="modal" id="modalBtn" data-target="#myModal"
                style="display: none">开始演示模态框
        </button>
        <!-- 模态框（Modal） -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">选择回退节点</h4>
                    </div>
                    <div class="modal-body">
                        <li>
                            <label text="选择回退节点"></label>
                            <div class='col-xs-8 col-sm-8'>
                                <select id="nodeKeySelect" name="nodeKeySelect" class="selectpicker"
                                        data-live-search="true" style="height: 35px">

                                </select>
                            </div>
                        </li>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="closeModalBtn" data-dismiss="modal">关闭
                        </button>
                        <button type="button" class="btn btn-primary" onclick="reject()">选择</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>
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
                    <div style="text-align: center" id="loading"></div>
                    <form method="post" enctype="multipart/form-data" id="uploadForm"
                          style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
                        <div class="form-group" style="text-align: center;vertical-align: middle;">
                            <label for="file"></label>
                            <img src="${base}/static/img/sendfile.png"/>
                            <a href="javascript:;" class="file">
                                <input type="file" class="form-control" id="file" name="file" multiple
                                       style="padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
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

    function exportIn() {
        $("#hiddenBtn").click();
    }


    var procInstId = '${procInstId}';
    var taskId = '${taskId}';
    var formData = '${data}';
    var isLast = ${isLast};
    var nextApprovalPeople;


    $(function () {
        $("#nodeKeySelect").html("");
        var deviceStr = "";
        $.ajax({
            type: "GET",
            url: "${base}/workflow/approve/selectCompleteNode?procInstId="+procInstId+"&taskId="+taskId,
            data: {},
            async: false,
            dataType: "json",
            success: function (result) {
                console.log(JSON.stringify(result));
                for (var i = 0; i < result.length; i++) {
                    if(result[i].name === "审核人会签" || result[i].name === "负责人会签" ){
                        continue;
                    }
                    deviceStr += '<option  value="' + result[i].value + '">' + result[i].name + '</option>';
                }

            },
            error: function (XMLHttpRequest) {
                layer.alert(messages.systemE, {
                    title: messages.promptBox,
                    icon: 0,
                    btn: messages.btn,
                });
                // alert("error" + XMLHttpRequest.readyState);
            }
        });

        $('#nodeKeySelect').append(deviceStr);
        $('#nodeKeySelect').selectpicker('refresh');
    })


    $(function () {
        $.ajax({
            url: "${base}/ins/process/queryParentDeptCode?procInstId=" + procInstId + "&taskId=" + taskId,
            method: "GET",
            dataType: "JSON",
            traditional: true,
            success: function (data) {
                //是否需要展示部门选择
                if (data.length > 0) {
                    $("#demo").select2({
                        placeholder: "请选择总部部门",
                        data: data
                    });
                    $("#selectNextDept").css('display', 'block');
                }
            }
        })
    })

    //文件下载
    function downFileOnclick() {
        //选中的文件
        var pids = getIdSelections();
        if (pids == '' || pids == undefined) {
            $.messager.alert('错误', "请选择需要下载的文件!", 'error');
            return;
        }
        var url = '${base}/bus/docFile/download/' + pids;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    //文件上传
    $("#upload").click(function () {
        var obj = document.getElementById("file");
        for (var i = 0; i < obj.files.length; i++) {
            var temp = obj.files[i].name;
            var temArr = temp.split('.');
            var fileLast = temArr[temArr.length - 1];

        }
        var formData = new FormData($('#uploadForm')[0]);
        $.ajax({
            type: 'post',
            url: '${base}/bus/docFile/uploadDocFile?flag=6', //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            beforeSend: function (XMLHttpRequest) {
                //alert('远程调用开始...');
                $("#loading").html("<p style=\"color: dodgerblue\">正在上传，请稍后操作...</p>");
            },
            success: function (data) {
                $("#close").click();
                alert(data.msg);
                if (data.success) {
                    //处理数据
                    var files = data.fileData;
                    var count = files.length;
                    //获取原有表单的数据
                    var rows = getData();
                    rows = rows.concat(files);
                    //构造表格显示数据
                    var total = rows.length;
                    var showData = {'total': total, 'rows': rows}
                    $('#docTable').bootstrapTable('load', showData);
                    $("#loading").empty();
                }
            },
            error: function () {
                $("#close").click();
                alert("上传失败,请重试");
                $("#loading").empty();
            }
        })
    });
    function showModel() {
        $('#modalBtn').click();
    }
    //表单提交 , 先执行验证表单事件
    function reject() {
        var nodeKey = $('#nodeKeySelect').val();
        var memo = $('#memo').val();
        if (!memo) {
            $.messager.alert('提示', '回退意见不能为空。', 'info');
            return;
        }
        $("#memo").val($("#memo").val().replace(/[\r\n]/g, ""));
        $.messager.confirm('提示', '确定要回退该记录吗？', function (r) {
            if (r) {
                // alert("开始退回处理")
                // return;
                //做退回处理
                $.ajax({
                    url: "${base}/bus/return/dealTask",
                    method: "POST",
                    data: {
                        nodeKey: nodeKey,
                        taskId: taskId,
                        procInstId: procInstId,
                        memo: $('#memo').val()
                    },
                    dataType: "JSON",
                    traditional: true,
                    success: function (data) {
                        if (data.success) {
                            $.mask.post('${base}/workflow/approve/reject',
                                {
                                    nodeKey: nodeKey,
                                    taskId: taskId,
                                    procInstId: procInstId,
                                    memo: $('#memo').val()
                                }, function (data) {
                                    if (data.success) {
                                        $.messager.alert('提示', '处理成功。', 'info', function () {
                                            window.location = '${base}/workflow/approve/todoList';
                                        })
                                    } else {
                                        $.messager.alert('错误', data.msg, 'error');
                                    }
                                })
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    }
                });

            }
        })
    }

    //通过
    function adopt() {
        var myFrame = document.getElementById('myFrame');
        var data = {type: 'workflow', opt: 'save'};
        var clientAddInput = $('#clientAddInput').val();
        var tokenInput = $('#tokenInput').val();
        // alert(clientAddInput + '----' + tokenInput)
        $("#memo").val($("#memo").val().replace(/[\r\n]/g, ""));
        myFrame.contentWindow.postMessage(data, clientAddInput + '/workflow/client/bizEdit?token=' + tokenInput);
    }

    window.addEventListener('message', function (e) {
        /* 判断是不是监听的工作流相关数据 */
        if ('workflow' == e.data.type) {
            // debugger;
            if (e.data.formData) {
                formData = e.data.formData;
            }

            var json = JSON.parse(formData);
            // json.nextApprovalPeople = nextApprovalPeople;
            //添加选择的下一个部门信息

            var css = $('#selectNextDept').css('display')
            if (css == 'block') {
                var nextHeadDept = $("#demo").find("option:selected").val();
                // nextHeadDept= $("#demo").select2("val");
                // nextHeadDept = $("#demo").val();
                console.log(nextHeadDept)
                if (nextHeadDept == null || nextHeadDept == '' || nextHeadDept == undefined) {
                    $.messager.alert('提示', '请选择对应总部的部门', 'info');
                    return;
                }
                json.hdDeptCode = nextHeadDept;
            }
            var procInstId1 = json.procInstId;
            //添加备份需要的流程实例信息
            if (procInstId1 == null) {
                json.procInstId = procInstId;
            }
            formData = JSON.stringify(json);
            console.log(formData)
            formData = $.helper.base64Encode(formData);
            var memo = $('#memo').val();
            if (memo == null || memo == '') {
                memo = '通过'
            }
            $.mask.post('${base}/workflow/approve/complete',
                {
                    taskId: taskId,
                    formData: formData,
                    procInstId: procInstId,
                    memo: memo
                },
                function (data) {
                    if (data.success) {
                        $.messager.alert('提示', '处理成功。', 'info', function () {
                            window.location = '${base}/workflow/approve/todoList';
                        })
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            );
        } else if ('workflowFrameH' == e.data.type) {
            var myFrame = document.getElementById('myFrame');
            myFrame.style.height = e.data.height + 'px';
        }
    });

    //iframe自适应高度
    $(function () {
        var ifm = document.getElementById("myFrame");
        ifm.height = document.documentElement.clientHeight;
    })
</script>

</body>
</html>