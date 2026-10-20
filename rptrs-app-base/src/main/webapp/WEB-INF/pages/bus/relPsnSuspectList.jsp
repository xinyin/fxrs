<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
	<style type="text/css">
		#relPsnSuspectTable_tb_add,#relPsnSuspectTable_tb_edit,
		#relPsnSuspectTable_tb_remove,#relPsnSuspectTable_tb_send,
		#relPsnSuspectTable_tb_checkByPsn,#relPsnSuspectTable_tb_exportSimple{
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
<title>关联关系自然人表</title>
	<style>
		.container {width: 600px; margin: 20px auto;}
		button { padding: 0 10px; }
		.text-info { color: #ff7300;}

		.mt10{
			margin-top: 10px;
		}

		.mt20{
			margin-top: 20px;
		}

		.hide {
			display: none;
		}

		#formBox {
			width: 550px;
			border: 1px solid #ddd;
			padding: 20px;
			margin: 20px auto;
		}

		.frm-item {
			line-height: 32px;
		}

		.frm-item + .frm-item {
			margin-top: 10px;
		}

		.frm-label {
			display: inline-block;
			width:9em;
			font-size: 14px;
			text-align: right;
		}

		.frm-input {
			width: 120px;
			height: 30px;
			border: 1px solid #ddd;
			padding: 0 10px;
		}

		.frm-submit {
			display: inline-block;
			min-width: 50px;
			height: 32px;
			padding: 0 10px;
			font-size: 14px;
			line-height: 32px;
			text-align: center;
			color: #fff;
			background-color: #0D76B4;
			text-decoration: none;
		}

		.frm-submit.disabled,
		.frm-submit.disabled:hover {
			background-color: #BCB7B7;
			cursor: not-allowed;
		}

		.frm-submit + .frm-reset {
			margin-left: 10px;
		}

		.frm-reset {
			display: inline-block;
			width: 60px;
			height: 32px;
			font-size: 14px;
			line-height: 32px;
			text-align: center;
			color: #fff;
			background-color: #B9A5CE;
			text-decoration: none;
		}

		.frm-submit:hover {
			background-color: #0F5883;
		}

		i.required {
			padding-right: 5px;
			color: #f00;
		}

		#addIpBox .msg-box {
			padding-left: 98px;
			color: #c33;
		}
		.dialog-confirm .dialog-submit {
			border: none;
			background-color: #6f55c0;
			color: #fff;
			text-align: center;
			text-decoration: none; }
		.dialog-confirm .dialog-submit:hover {
			text-decoration: none;
			background-color: #8032f5; }
		.dialog-confirm .disabled.dialog-submit, .dialog-confirm .disabled.dialog-submit:hover {
			border: 1px solid #ddd;
			color: #999;
			background-color: #f8f8f8;
			cursor: not-allowed; }

		.dialog-confirm .dialog-cancel {
			border: 1px solid #ddd;
			color: #666;
			text-align: center;
			text-decoration: none; }
		.dialog-confirm .dialog-cancel:hover {
			color: #6f55c0;
			border-color: #6f55c0;
			text-decoration: none; }
		.dialog-confirm .disabled.dialog-cancel, .dialog-confirm .disabled.dialog-cancel:hover {
			border: 1px solid #ddd;
			color: #999;
			background-color: #f8f8f8;
			cursor: not-allowed; }

		.dialog-background {
			display: none;
			position: fixed;
			left: 0;
			top: 0;
			z-index: 990;
			width: 100%;
			height: 100%;
			padding: 0;
			margin: 0;
			background-color: rgba(0, 0, 0, 0.5);
			filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#88000000,endColorstr=#88000000); }

		.dialog-section {
			opacity: 0;
			filter: alpha(opacity=0);
			display: none;
			position: absolute;
			top: 50%;
			left: 50%;
			z-index: 999;
			padding: 0;
			margin: 0;
			background-color: #FFF;
			box-shadow: 0 0 8px rgba(0, 0, 0, 0.5); }

		.dialog-body,
		.dialog-imagelist {
			box-sizing: content-box;
			z-index: 1;
			min-width: 516px;
			height: 100%;
			padding: 32px;
			padding-top: 48px;
			margin: 0;
			overflow: hidden; }
		.dialog-body.withNoPadding,
		.dialog-imagelist.withNoPadding {
			padding: 0; }
		.dialog-body.withNoMinWidth,
		.dialog-imagelist.withNoMinWidth {
			min-width: auto;
			min-width: 0; }

		.dialog-body img {
			display: block;
			vertical-align: top; }

		.dialog-btnPrev,
		.dialog-btnNext {
			position: absolute; }

		.dialog-btnPrev,
		.dialog-btnNext {
			top: 50%;
			z-index: 9;
			margin-top: -18px;
			width: 36px;
			height: 36px;
			overflow: hidden;
			font: 16px/36px 'Simsun';
			text-align: center;
			color: #fff !important;
			cursor: pointer;
			background-color: #7F7F7F;
			background-color: rgba(0, 0, 0, 0.5); }

		.dialog-btnPrev {
			left: 0; }

		.dialog-btnNext {
			right: 0; }

		.dialog-disabled {
			cursor: not-allowed;
			background-color: rgba(0, 0, 0, 0.3);
			filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#44000000,endColorstr=#44000000); }

		.dialog-header {
			position: relative;
			width: auto;
			height: 39px;
			padding: 0 46px 0 16px;
			margin: 0;
			border-bottom: 1px solid #d9d9d9;
			font-size: 16px;
			line-height: 39px;
			white-space: nowrap;
			background-color: #e6e6e6; }
		.dialog-header + .dialog-body {
			padding-top: 32px; }
		.dialog-header + .dialog-body.withNoPadding {
			padding: 0; }

		.dialog-close {
			display: inline-block;
			vertical-align: middle;
			background-image: url("images/icons-dialog.png");
			background-repeat: no-repeat;
			position: absolute;
			right: 16px;
			top: 12px;
			width: 14px;
			height: 14px;
			padding: 0;
			margin: 0;
			background-position: 0 -135px;
			cursor: pointer; }

		#dialogTipBox .dialog-body {
			padding-top: 48px;
			padding-bottom: 48px; }
		#dialogTipBox .dialog-msg {
			padding: 0;
			margin: 0;
			text-align: center; }
		#dialogTipBox .dialog-msg-text {
			padding: 0;
			margin: 0;
			font-size: 16px; }
		#dialogTipBox .i-success,
		#dialogTipBox .i-warning,
		#dialogTipBox .i-error {
			position: relative;
			display: inline-block;
			top: -2px;
			margin-right: 20px;
			width: 36px;
			height: 36px;
			vertical-align: middle;
			background-image: url("images/icons-dialog.png");
			background-repeat: no-repeat; }
		#dialogTipBox .i-success {
			background-position: 0 0; }
		#dialogTipBox .i-warning {
			background-position: 0 -45px; }
		#dialogTipBox .i-error {
			background-position: 0 -90px; }

		.dialog-confirm {
			box-sizing: content-box;
			position: relative;
			width: 235px;
			min-width: 235px;
			font-size: 0;
			text-align: left;
			margin: 0 auto; }
		.dialog-confirm.withCenter {
			text-align: center; }
		.dialog-confirm.withIcon, .dialog-confirm.withIconSuccess, .dialog-confirm.withIconWarning, .dialog-confirm.withIconError {
			padding-left: 54px; }
		.dialog-confirm.withIcon:before, .dialog-confirm.withIconSuccess:before, .dialog-confirm.withIconWarning:before, .dialog-confirm.withIconError:before {
			content: '';
			display: inline-block;
			vertical-align: middle;
			background-image: url("images/icons-dialog.png");
			background-repeat: no-repeat;
			position: absolute;
			left: 0;
			top: 0;
			width: 36px;
			height: 36px;
			background-position: 0 -45px; }
		.dialog-confirm.withIconSuccess:before {
			background-position: 0 0; }
		.dialog-confirm.withIconWarning:before {
			background-position: 0 -45px; }
		.dialog-confirm.withIconError:before {
			background-position: 0 -90px; }
		.dialog-confirm .dialog-msg {
			padding: 0;
			margin: 0;
			font-size: 16px;
			line-height: 32px; }
		.dialog-confirm .dialog-msg + .dialog-desc {
			margin-top: 10px; }
		.dialog-confirm .dialog-desc {
			padding: 0;
			margin: 0;
			font-size: 12px;
			line-height: 24px;
			margin-bottom: 20px; }
		.dialog-confirm .dialog-content {
			padding: 0;
			margin: 0;
			font-size: 12px;
			line-height: 24px;
			text-align: left; }
		.dialog-confirm .dialog-content + .dialog-buttonBox {
			text-align: center; }
		.dialog-confirm .dialog-buttonBox {
			overflow: hidden;
			padding: 0;
			margin: 24px 0 0 0; }
		.dialog-confirm .dialog-buttonBox a {
			margin-left: 15px; }
		.dialog-confirm .dialog-buttonBox a:first-child {
			margin-left: 0; }
		.dialog-confirm .dialog-submit,
		.dialog-confirm .dialog-cancel {
			display: inline-block; }
		.dialog-confirm .dialog-submit {
			width: 98px;
			height: 29px;
			font-size: 12px;
			line-height: 29px; }
		.dialog-confirm .dialog-cancel {
			box-sizing: content-box;
			width: 96px;
			height: 27px;
			font-size: 12px;
			line-height: 27px; }

		.dialog-success-top {
			position: fixed;
			left: 50%;
			top: 0;
			z-index: 999;
			min-width: 400px;
			padding: 0;
			margin: 0;
			border-radius: 5px;
			border-top-left-radius: 0;
			border-top-right-radius: 0;
			font-size: 16px;
			line-height: 46px;
			text-align: center;
			color: #fff;
			background-color: #4aac42; }
		.dialog-success-top .i-icon {
			position: relative;
			display: inline-block;
			vertical-align: middle;
			width: 26px;
			height: 18px;
			margin-right: 15px;
			background: url("images/icons-dialog.png") no-repeat 0 -180px; }

		.dialog-warning-top {
			position: fixed;
			left: 50%;
			top: 0;
			z-index: 999;
			min-width: 400px;
			padding: 0;
			margin: 0;
			border-radius: 5px;
			border-top-left-radius: 0;
			border-top-right-radius: 0;
			font-size: 16px;
			line-height: 46px;
			text-align: center;
			color: #fff;
			background-color: #ff4400; }

		/*# sourceMappingURL=dialog.css.map */
	</style>
</head>
<body class="gray-bg" style="background: white">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="自然人名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_name" col="7"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="证件类型" col="5" formControl="true"/>
						<bf:select name="__LIKES_identifyType" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-IdentifyType" >
							<bf:option text="请选择" value="%"></bf:option>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="证件号码" col="5" formControl="true"/>
						<bf:text name="__LIKES_identifyNumber" col="7" placeholder="证件号码"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="状态" col="5" formControl="true"/>
						<bf:select name="__LIKES_status" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-RelApplystatus" >
							<bf:option text="请选择" value="%"></bf:option>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="数据来源" col="5" formControl="true"/>
						<bf:select name="__LIKES_dataSource" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-DataSourceSus" >
							<bf:option text="请选择" value="%"></bf:option>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="更新时间" col="5" formControl="true"></bc:label>
						<bf:datebox name="__LIKES_modifiedTime" id="modifiedTime" col="7" format="date"/>
					</bf:formGroup>
				</bc:row>
				<bc:row cls="m-b">
					<div class="col-sm-12 col-sm-offset-5">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					</div>
				</bc:row>
			</bf:form>
			<bc:table id="relPsnSuspectTable" idField="pid" url="${base}/bus/relPsnSuspect">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="edit" text="编辑" type="button" handler="editRelPsn"/>
				<bc:toolbar id="remove" text="删除" type="button" handler="delPsnForFrom"/>
				<bc:toolbar id="importSimple" text="导入" type="button" handler="importHandler"/>
				<bc:toolbar id="exportHandlerHis" text="模板下载" type="button" handler="exportHandler"/>
				<bc:toolbar id="send" text="提交审批" handler="send" type="button"/>
				<bc:toolbar id="export" text="导出" type="button" handler="dataExport"/>
				<bc:column field="pid" title="自增长主键" width="100" visible="false"/>
				<bc:column field="regCode" title="登记编码" width="100" visible="false"/>
				<bc:column field="orignalOrg" title="源主体银行机构" width="100" visible="false"/>
				<bc:column field="orignalOrgName" title="源主体银行机构名称" width="100" visible="false"/>
				<bc:column field="name" title="姓名" width="100"/>
				<bc:column field="identifyType" title="证件类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
				<bc:column field="identifyNumber" title="证件号码" width="100"/>
				<bc:column field="sex" title="性別" width="100" visible="false"/>
				<bc:column field="nationality" title="国别/地区" width="100" visible="false"/>
				<bc:column field="domesticOrForeign" title="境内 / 境外" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
				<bc:column field="race" title="民族" width="100" visible="false"/>
				<bc:column field="isAssoParty" title="是否关联方" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-isRel"  />
				<bc:column field="dataSource" title="数据来源" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DataSourceSus"/>
				<bc:column field="birthdate" title="出生日期" width="100" visible="false"/>
				<bc:column field="mobile" title="移动电话" width="100" visible="false"/>
				<bc:column field="email" title="邮箱" width="100" visible="false"/>
				<bc:column field="dutyCode" title="职位" width="100" visible="false"/>
				<bc:column field="status" title="状态" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelApplystatus"/>
				<bc:column field="statusComment" title="状态说明" width="100" visible="false"/>
				<bc:column field="modifiedTime" title="更新时间" width="100"/>
				<bc:column field="maritalStatus" title="婚姻状况" width="100" visible="false"/>
				<bc:column field="validStatus" title="是否有效   0|无效1|有效" width="100" visible="false"/>
				<bc:column field="activeTime" title="生效时间" width="100" visible="false"/>
				<bc:column field="invalidTime" title="失效时间" width="100" visible="false"/>
				<bc:column field="upApprovalDepartment" title="上一级审批部门" width="100" visible="false"/>
				<bc:column field="nextApprovalDepartment" title="下一级审批部门" width="100" visible="false"/>
				<bc:column field="upApprovalPeople" title="上一级审批人" width="100" visible="false"/>
				<bc:column field="nextApprovalPeople" title="下一级审批人" width="100" visible="false"/>
				<bc:column field="regulatorYbj" title="银保监口径  0|否 1|是" width="100" visible="false"/>
				<bc:column field="regulatorZjh" title="证监会口径（0|否1|是）" width="100" visible="false"/>
				<bc:column field="regulatorCzb" title="财政部口径（0|否1|是）" width="100" visible="false"/>
				<bc:column field="regulatorOth1" title="其他口径1（0|否1|是）" width="100" visible="false"/>
				<bc:column field="regulatorOth2" title="其他口径2（0|否1|是）" width="100" visible="false"/>
				<bc:column field="isBlack" title="是否黑名单（0|否1|是）" width="100" visible="false"/>
				<bc:operator id="view" icon="search" title="查看"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<script src="../../static/js/jquery.dialog.js"></script>
<be:processEvent/>
<script type="text/javascript">

	$(function () {
		var code = "${code}";
		//新增
		if(code.search("01,") != -1){
			$('#relPsnSuspectTable_tb_add').show();
		}
		//编辑
		if(code.search("02,") != -1){
			$('#relPsnSuspectTable_tb_edit').show();
		}
		//删除
		if(code.search("03,") != -1){
			$('#relPsnSuspectTable_tb_remove').show();
		}
		//提交审批
		if(code.search("07,") != -1){
			$('#relPsnSuspectTable_tb_send').show();
		}
		//导出
		if(code.search("08,") != -1){
			$('#relPsnSuspectTable_tb_exportSimple').show();
		}
	})


	function send(){
		var tables =  $('#relPsnSuspectTable').jTable('getCheckData');
		var pid = tables[0].pid;
		var status = tables[0].status;
		if (pid == '' || pid == null) {
			$.messager.alert('错误', "请选择至少一条数据", 'error');
			return;
		}

		$.ajax({
			url: "${base}/bus/relPsnSuspect/send/" + pid,
			type: "post",
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function (data) {
				if (data.success) {
					$.messager.alert('提示', '提交成功。', 'info', function () {
						window.location = '${base}/workflow/process/runingList';
					});
				} else {
					$.messager.alert('错误', data.msg, 'error');
				}
			}
		});
	}

	//
	function dataExport(){
		var psnName=$("[name='__LIKES_name']").val()==""?"%25":$("[name='__LIKES_name']").val();
		var identifyType=$("[name='__LIKES_identifyType']").val()=="%"?"%25":$("[name='__LIKES_identifyType']").val();
		var identifyNumber=$("[name='__LIKES_identifyNumber']").val()==""?"%25":$("[name='__LIKES_identifyNumber']").val();
		var approvalStatus=$("[name='__LIKES_status']").val()=="%"?"%25":$("[name='__LIKES_status']").val();
		var data = psnName +","+ identifyType +","+ identifyNumber +","+ approvalStatus;
        var url='${base}/sys/xlsie/SusPsnInfoExportCallbackImpl/'+ data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
	}

	//模块下载
	function exportHandler() {
		var data = "1"
		var url = '${base}/sys/xlsie/SusPsnoutExportCallbackImpl/' + data;
		var elemIF = document.createElement("iframe");
		elemIF.src = url;
		elemIF.style.display = "none";
		document.body.appendChild(elemIF);
	}

	//导入
	function importHandler() {
		$.messager.modal({
			winId: 'wImportFromExcel', //winid只能为 wImportFromExcel
			width: 500,
			height: 230,
			args: {
				callback : loadCallback
			},
			title: '疑似自然人信息导入',
			url: '${base}/sys/xlsie/import?bean=SusPsnImportCallbackImpl',
			onClose: function () {
				loadCallback();
			}
		});
	}

	//刷新表格
	function loadCallback() {
		$('#relPsnSuspectTable').jTable('refresh');
	}


    function  editRelPsn() {
		var tables =  $('#relPsnSuspectTable').jTable('getCheckData');
		var status = tables[0].status;
        var pid = getIdSelections();

        if('4' == status || '6' == status){
			$.messager.alert('提示', '已归档和审批中的数据无法编辑。', 'info');
        	return;
		}
        if(pid != ""){
            window.location = '${base}/bus/relPsnSuspect/edit/' + pid;
        }else {
            $.messager.alert('提示', '请选择自然人。', 'info');
            return "";
        }
    }
    function getIdSelections() {
        return $.map($('#relPsnSuspectTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    //删除
    function delPsnForFrom() {
		var tables =  $('#relPsnSuspectTable').jTable('getCheckData');
		var status = tables[0].status;
        var pidForForm=getIdSelections();
        if(pidForForm==""||pidForForm==undefined){
			$.messager.alert('错误', "请选择一条数据", 'error');
        }else{
			if('4' == status || '6' == status){
				$.messager.alert('提示', '已归档和审批中的数据无法删除。', 'info');
				return;
			}
            // var r=confirm("确定要删除数据？");
			$.messager.confirm('提示', '确定要删除吗？', function (r) {
            if(r){
                $.ajax({
                    url:'${base}/bus/relPsnSuspect/delPsnForFrom/'+pidForForm,
                    type: 'get',
                    dataType: "json",
                    success:function (res) {
                        // alert(res.msg);
                        if(res.success){
							$.messager.alert('提示', res.msg, 'info', function () {
								window.location.reload();
							})
                        }else {
							$.messager.alert('错误', data.msg, 'error');
						}
                    }
                })
            }
        })}
    }


	//重置
	function frmReset() {
		window.location = '${base}/bus/relPsnSuspect/list';
	}


</script>
</body>
</html>