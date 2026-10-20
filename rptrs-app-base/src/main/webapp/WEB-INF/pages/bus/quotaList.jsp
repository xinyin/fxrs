<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>RelOrg</title>
    <script>
        function editHandler(){
            var pid = getIdSelections();
            window.location = "${base}/bus/quota/edit/"+pid;
            // console.log($ids_str);
            // alert($ids_str)
        }
        //模板下载
        function exportHandler(){
            var data = 0;
            var url='${base}/sys/xlsie/QuotaTemplateCallbackImpl/'+ data;
            var elemIF = document.createElement("iframe");
            elemIF.src = url;
            elemIF.style.display = "none";
            document.body.appendChild(elemIF);
        }

        //导入
        function importHandler(){
            $.messager.modal({
                winId: 'wImportFromExcel', //winid只能为 wImportFromExcel
                width: 500,
                height: 230,
                args: {
                    callback:loadCallback
                },
                title: '内部交易管理信息导入',
                url: '${base}/sys/xlsie/import?bean=QuotaImportCallbackImpl',
                onClose: function(){
                    location.reload();
                    loadCallback();
                }
            });
        }
        //刷新表格
        function loadCallback(){
            $('#naturalPersonTable').jTable('refresh');
        }
        function getIdSelections() {
            return $.map($('#relOrgTable').bootstrapTable('getSelections'), function (row) {
                return row.pid;
            });
        }

        //删除
        function removeHandler() {

            $.messager.confirm('提示', '确定要删除这些数据吗?', function(result){
                if(!result){
                    return "";
                }else {
                    var pid = getIdSelections();
                    <%--window.location = "${base}/bus/rel/removeOrg/" + relId;--%>

                    $.ajax({
                        url:"${base}/bus/quota/remove/"+pid,
                        method:"POST",
                        data:{"":""},
                        dataType:"JSON",
                        traditional:true,
                        success:function(data){
                            if(data.success){
                                $.messager.toastr('成功', data.msg, 'success');
                                window.location = '${base}/bus/quota/list';
                            }else
                            {
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        }
                    })
                }
            });

        }


        //数据导出
        function exportHand() {
            var rows =  $('#relOrgTable').jTable('getCheckData');

            var orgName = $("#orgName").val();
            var identifyType = $("#identifyType").val();
            var uniscCode = $("#uniscCode").val();


            if(orgName==""){
                orgName="%25";
            }
            if(identifyType=="%"){
                identifyType="%25";
            }
            if(uniscCode==""){
                uniscCode="%25";
            }

            var data = orgName+","+identifyType+","+uniscCode;
            var url='${base}/sys/xlsie/QuotaExportCallbackImpl/' + data;
            var elemIF = document.createElement("iframe");
            elemIF.src = url;
            elemIF.style.display = "none";
            document.body.appendChild(elemIF);

        }

        function  addOrRemoveBlacklist() {
            var pid = getIdSelections();
            if(pid != ""){
                window.location = '${base}/bus/relOrg/addOrRemoveBlack/' + pid+'?';
            }else {
                $.messager.alert('提示', '请选择自然人。', 'info');
                return "";
            }
        }
        function  disconnection() {
            var pid = getIdSelections();
            if(pid != ""){
                window.location = '${base}/bus/relOrg/disconnection/' + pid+'?';
            }else {
                $.messager.alert('提示', '请选择机构。', 'info');
                return "";
            }
        }
        function upload() {
            alert("aaaa");
            var obj = document.getElementById("file");
            for (var i = 0; i < obj.files.length; i++) {
                var temp = obj.files[i].name;
                var temArr = temp.split('.');
                var fileLast = temArr[temArr.length - 1];
                alert(fileLast);
                if (fileLast != 'xls' && fileLast != 'xlsx') {
                    alert("请上传Excel文件");
                    return;
                }else{
                    var formData = new FormData($('#uploadForm')[0]);
                    //alert(formData);
                    $.ajax({
                        url:'${base}/bus/relOrg/impor',
                        type:'post',
                        data:formData,
                        cache: false,
                        processData: false,  // 不处理数据
                        contentType: false,
                        success:function (data) {
                            if(data.success){
                                alert("导入成功！")
                                $("#close").click();
                            }
                        }
                    })
                }
            }

        }

        /*
* 审批
* */
        function send() {
            // 提交审批  的必须是待提交状态
            var status=getStatusSelections();
            var pid=getIdSelections();
            if (status == 3) {
                //查询出基本信息 修改状态 放到data中
                $.ajax({
                    url:"${base}/bus/quotaOrg/send/"+pid,
                    method:"GET",
                    data:{"":""},
                    dataType:"JSON",
                    traditional:true,
                    success:function(data){
                        if(data.success){
                            //开始走工作流
                            var objData=data.data;
                            var myDate = new Date();
                            objData.id = myDate.getTime();
                            data = JSON.stringify(objData);
                            var formData = $.helper.base64Encode(data);
                            //挂取工作流
                            var obj = {
                                procDefCode: 'zjcc_ORG001',
                                oldData: '',
                                newData: formData,
                                uploadSessions: '',
                                procInstId: ''
                            }

                            $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
                                if (data.success) {
                                    $.messager.alert('提示', '提交成功。', 'info', function () {
                                        window.location = '${base}/workflow/process/runingList';
                                    });
                                } else {
                                    $.messager.alert('错误', data.msg, 'error');
                                }
                            });
                        }else
                        {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    }
                })
            }else {
                alert("当前状态是："+status+"不符合要求！");
            }
        }

        function getStatusSelections() {
            return $.map($('#relOrgTable').bootstrapTable('getSelections'), function (row) {
                return row.status;
            });
        }

    </script>
</head>
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
            <%--<bc:ibox title="" subTitle="" col="12" collapsible="true">--%>
                <%--<bf:form id="queryForm" validation="true">--%>
                    <bc:row cls="m-b">
                        <bf:formGroup col="4">
                            <bc:label text="内部人名称" col="4" formControl="true"/>
                            <bf:text id="orgName" name="__LIKES_OrgName" col="8" placeholder=""/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="证件类型" col="4" formControl="true"/>
                            <bf:select id="identifyType" name="__LIKES_identifyType" col="8" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-IdentifyType">
                                <bf:option text="ALL" value="%"></bf:option>
                                <be:event type="select">
                                    var input = $("#__LIKES_identifyType");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="证件号码" col="4" formControl="true"/>
                            <bf:text id="uniscCode" name="__LIKES_uniscCode" col="8" placeholder=""/>
                        </bf:formGroup>
                        <%-- <bf:formGroup col="4">
                         <bc:col col="3" >
                             <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                             <bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
                         </bc:col>
                         </bf:formGroup>--%>
                    </bc:row>
                    <bc:row cls="m-b">
                        <div class="col-sm-10 col-sm-offset-5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                        </div>

                    </bc:row>

                </bf:form>
                <bc:table id="relOrgTable" idField="pid" url="${base}/bus/quota">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="importSimple" text="导入" type="button" handler="importHandler"/>
                <bc:toolbar id="exportHandler" text="模板下载" type="button" handler="exportHandler"/>
                <%--<bc:toolbar id="send" text="提交审批" handler="send" type="button"/>--%>
                <bc:toolbar id="exportHand" text="导出" type="button" handler="exportHand"/>
                <%--<bc:toolbar id="out" text="数据导出" handler="addblack" type="button"/>--%>
                <%--<bc:toolbar id="disconnection" text="解除关联关系" handler="disconnection" type="button"/>--%>
                <%--<bc:toolbar id="addOrRemoveBlacklist" text="加入/移出黑名单" handler="addOrRemoveBlacklist" type="button"/>--%>
                <bc:column field="pid" title="自增主键" width="100" visible="false"/>
                <bc:column field="regCode" title="登记编码" width="100" visible="false"/>
                <bc:column field="orignalOrg" title="源主体银行机构" width="100" visible="false"/>
                <bc:column field="orignalOrgName" title="源主体银行机构名称" width="100" visible="false"/>
                <bc:column field="orgName" title="内部人名称" width="100"/>
                <bc:column field="orgType" title="企业类型" width="100" visible="false"/>
                <bc:column field="identifyType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="uniscCode" title="证件号码" width="100" />
                <bc:column field="presetLimitCurrency" title="预设额度币种" width="100" />
                <bc:column field="presetLimit" title="预设额度" width="100" />
                <bc:column field="availableLimit" title="可用额度" width="100" />
                <bc:column field="validStatus" title="是否生效" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-ValidStatus/"/>
                <%--<bc:column field="CREATEDTIME_" title="创建时间" width="100" />--%>

                <bc:column field="domesticOrForeign" title="境内/境外(1-境内，2-境外)" width="100" visible="false"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                <bc:column field="mgerOrg" title="机构名称" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-GHOrg"/>
                <bc:column field="regCapital" title="注册资本(万元/人民币)" width="100" visible="false"/>
                <bc:column field="regCapitalCurrency" title="注册资本币种代码" width="100" visible="false"/>
                <bc:column field="businessScope" title="营业范围" width="100" visible="false"/>
                <bc:column field="regCapitalCurrency" title="注册资本币种代码" width="100" visible="false"/>
                <bc:column field="businessScope" title="营业范围" width="100" visible="false"/>
                <bc:column field="statusComment" title="状态说明" width="100" visible="false"/>
                <bc:column field="upApprovalDepartment" title="上一级审批部门" width="100" visible="false"/>
                <bc:column field="nextApprovalDepartment" title="下一级审批部门" width="100" visible="false"/>
                <bc:column field="upApprovalPeople" title="上一级审批人" width="100" visible="false"/>
                <bc:column field="nextApprovalPeople" title="下一级审批人" width="100" visible="false"/>
                <bc:column field="regulatorZjh" title="证监会口径（0|否1|是）" width="100" visible="false"/>
                <bc:column field="regulatorCzb" title="财政部口径（0|否1|是）" width="100" visible="false"/>
                <bc:column field="regulatorOth1" title="其他口径1（0|否1|是）" width="100" visible="false"/>
                <bc:column field="regulatorOth2" title="其他口径2（0|否1|是）" width="100" visible="false"/>
                <bc:column field="isBlack" title="黑名单标识" width="100" visible="false" formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <bc:column field="modifiedTime" title="更新时间" width="100"/>
                <bc:operator id="view" icon="search" title="查看"/>
                <%--<bc:operator id="edit" icon="edit" title="编辑"/>
                <bc:operator id="remove" icon="trash" title="移除"/>--%>
                </bc:table>
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
                            <img src="../../static/img/sendfile.png"/>
                            <a href="javascript:;" class="file">选择文件
                                <input type="file" class="form-control" id="file" name="file" multiple
                                       style="padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
                            </a >
                        </div>

                    </form>
                    <div class="form-group"
                         style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
                        <button type="submit" class="btn btn-primary" onclick="upload()" id="upload">上传</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>