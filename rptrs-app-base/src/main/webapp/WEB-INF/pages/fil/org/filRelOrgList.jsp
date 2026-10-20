<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <link rel="stylesheet" href="/static/css/dialog.css">
    <script src="/static/js/jquery.dialog.js"></script>
    <br:css pack="list"/>
    <title>RelOrg</title>
    <style>
        .black_overlay {
            display: none;
            position: absolute;
            top: 0%;
            left: 0%;
            width: 100%;
            height: 100%;
            background-color: #131313;
            z-index: 1001;
            -moz-opacity: 0.8;
            opacity: .80;
            filter: alpha(opacity=88);
        }

        .white_content {
            display: none;
            position: absolute;
            top: 25%;
            left: 25%;
            width: 45%;
            height: 25%;
            padding: 20px;
            border: 10px solid #ffae19;
            background-color: white;
            z-index: 1002;
            overflow: auto;
        }

        .BOX {
            display: flex;
            justify-content: space-around;
            width: 100%;
            margin: 1% auto;
        }

        .dialog-confirm {
            width: 100% !important;
        }

        .boxchild {
            width: 33%;
            display: flex;
        }

        .BOX .frm-label {
            width: 7em !important;
        }

        .dialog-section {
            display: block;
            left: 50%;
            position: fixed;
            margin-left: -438px !important;
            margin-top: -144px !important;
            opacity: 1;
            width: 80% !important;
        }

        #relOrgTable_tb_add,#relOrgTable_tb_edit,
        #relOrgTable_tb_remove,#relOrgTable_tb_importSimple,
        #relOrgTable_tb_sendCommand,#relOrgTable_tb_export,
        #relOrgTable_tb_send,#relOrgTable_tb_out,#relOrgTable_tb_addOrRemoveBlacklist{
            display: none;

        }

        .form-group .input-group.date,
        .form-group .select2{
            width: 100% !important;
        }
        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }
        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }
    </style>
    <script >



        function getStatusSelections() {
            return $.map($('#relOrgTable').bootstrapTable('getSelections'), function (row) {
                return row.approvalStatus;
            });
        }

        function editHandler() {
            var pid = getIdSelections();
            if (pid == '' || pid == null) {
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            var status = getStatusSelections();
            if (status != 4) {
                window.location = "${base}/fil/relOrg/edit/" + pid + "?op=0";
            } else {
                window.location = "${base}/fil/relOrg/edit/" + pid + "?op=1";
            }
        }




        function getIdSelections() {
            return $.map($('#relOrgTable').bootstrapTable('getSelections'), function (row) {
                return row.pid;
            });
        }



        function disconnection() {
            var pid = getIdSelections();
            if (pid != "") {
                window.location = '${base}/fil/relOrg/disconnection/' + pid + '?';
            } else {
                $.messager.alert('提示', '请选择机构。', 'info');
                return "";
            }
        }

        //刷新表格
        function loadCallback(){
            $('#relOrgTable').jTable('refresh');
        }




        //重置
        function frmReset() {
            window.location = '${base}/fil/relOrg/list';
        }
    </script>
</head>
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="机构名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_OrgName" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_identifyType" col="7" dataSource="custom"
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
                        <bc:label text="证件号码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_uniscCode" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="审批状态" col="5" formControl="true"/>
                        <bf:select name="__LIKES_approvalStatus" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Applystatus">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_approvalStatus");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="是否黑名单" col="5" formControl="true"/>
                        <bf:select name="__LIKES_isBlack" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-isNo">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_isBlack");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="数据来源" col="5" formControl="true"/>
                        <bf:select id="dataSource" name="dataSource" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-DataSource"
                                   value="${relPsn.dataSource}">
                            <bf:option text="ALL" value="%"></bf:option>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="口径" col="5" formControl="true"/>
                        <bf:select id="__LIKES_regulatorOth1" name="__LIKES_regulatorOth1" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Caliber" value="A">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_regulatorOth1");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <bc:col col="12" colOffset="5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </bc:col>
                </bc:row>


            </bf:form>
            <bc:table id="relOrgTable" idField="pid" multiple="false" url="${base}/fil/relOrg" selectFirst="false">
                <bc:toolbar id="" text="查看" type="button" handler="viewFilBusiness"/>
                <bc:toolbar id="compared" text="查看变更" handler="compared" type="button"/>
               <%-- <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
&lt;%&ndash;                <bc:toolbar id="importSimple" text="导入" type="button" handler="importHandler"/>&ndash;%&gt;
                <bc:toolbar id ="importSimple" text="导入" type="button" handler="importHandler"/>
                <bc:toolbar id="sendCommand" text="任务下发" type="button" handler="sendCommand"/>
                <bc:toolbar id="out" text="模板下载" type="button" handler="outtHandler"/>
                <bc:toolbar id="send" text="提交审批" handler="send" type="button"/>
                <bc:toolbar id="export" text="导出" type="button" handler="exportHandler"/>
                &lt;%&ndash;<bc:toolbar id="out" text="数据导出" handler="addblack" type="button"/>&ndash;%&gt;
                &lt;%&ndash;<bc:toolbar id="disconnection" text="解除关联关系" handler="disconnection" type="button"/>&ndash;%&gt;
                <bc:toolbar id="addOrRemoveBlacklist" text="加入/移出黑名单" handler="addOrRemoveBlacklist" type="button"/>--%>
                <bc:column field="pid" title="自增主键" width="100" visible="false"/>
                <bc:column field="filNum" title="备案编号" width="100"/>
                <bc:column field="regCode" title="登记编码" width="100" visible="false"/>
                <bc:column field="orignalOrg" title="源主体银行机构" width="100" visible="false"/>
                <bc:column field="orignalOrgName" title="源主体银行机构名称" width="100" visible="false"/>
                <bc:column field="orgName" title="机构名称" width="100"/>
                <bc:column field="orgType" title="企业类型" width="100" visible="false"/>
                <bc:column field="identifyType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="uniscCode" title="证件号码" width="100"/>
                <bc:column field="domesticOrForeign" title="境内外标识" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                <bc:column field="mgerOrg" title="机构名称" width="100"
                           formatter="custom:sys/unit/selectUnit"/>
                <bc:column field="regCapital" title="注册资本(万元)" width="100" visible="false"/>
                <bc:column field="regCapitalCurrency" title="注册资本币种代码" width="100" visible="false"/>
                <bc:column field="businessScope" title="营业范围" width="100" visible="false"/>
                <bc:column field="status" title="机构状态（1-正常，2-停业）" width="100" visible="false"/>
                <bc:column field="legalPerson" title="法定代表人" width="100" visible="false"/>
                <bc:column field="regAddress" title="注册地" width="100" visible="false"/>
                <bc:column field="validStatus" title="是否有效 0-无效，1-有效" width="100" visible="false"/>
                <bc:column field="activeTime" title="生效时间" width="100" visible="false"/>
                <bc:column field="invalidTime" title="失效时间" width="100" visible="false"/>
                <bc:column field="approvalStatus" title="审批状态"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus" width="100"/>
                <bc:column field="statusComment" title="审批状态说明" width="100"/>
                <bc:column field="upApprovalDepartment" title="上一级审批部门" width="100" visible="false"/>
                <bc:column field="nextApprovalDepartment" title="下一级审批部门" width="100" visible="false"/>
                <bc:column field="upApprovalPeople" title="上一级审批人" width="100" visible="false"/>
                <bc:column field="nextApprovalPeople" title="下一级审批人" width="100" visible="false"/>
                <bc:column field="regulatorOth1" title="口径" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Caliber"/>
                <%--            <bc:column field="regulatorZjh" title="证监会口径（0|否1|是）" width="100" visible="false"/>--%>
                <%--            <bc:column field="regulatorCzb" title="财政部口径（0|否1|是）" width="100" visible="false"/>--%>
                <%--            <bc:column field="regulatorOth1" title="其他口径1（0|否1|是）" width="100" visible="false"/>--%>
                <%--            <bc:column field="regulatorOth2" title="其他口径2（0|否1|是）" width="100" visible="false"/>--%>
                <bc:column field="isBlack" title="黑名单标识" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <bc:column field="dataSource" title="数据来源" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DataSource"/>
                <bc:column field="modifiedTime" title="更新时间" width="100"/>
                <%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
                <%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
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
                            </a>
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
<script type="text/javascript">
    function viewFilBusiness() {
        var pid = getIdSelections();
        if(pid.length==1){
            window.location = '${base}/fil/relOrg/view/'+pid;
        }else{
            $.messager.alert('提示', '请选择一个关联方。', 'info');
            return "";
        }
    }
    function compared() {
        var pid = getIdSelections();
        if(pid.length==1){
            window.location = '${base}/fil/relOrg/compared/' + pid+'?';
        }else {
            $.messager.alert('提示', '请选择一个关联方。', 'info');
            return "";
        }
    }
</script>
</body>
</html>