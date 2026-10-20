<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
	<style type="text/css">
		#relOrgSuspectTable_tb_add,#relOrgSuspectTable_tb_edit,
		#relOrgSuspectTable_tb_remove,#relOrgSuspectTable_tb_exportHandler,
		#relOrgSuspectTable_tb_send,#relOrgSuspectTable_tb_checkByOrg,
		#relOrgSuspectTable_tb_export{
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
<title>RelOrgSuspect</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="机构名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_orgName" col="7"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="证件类型" col="5" formControl="true"/>
						<bf:select name="__LIKES_identifyType" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-ORGIdentifyType" >
							<bf:option text="请选择" value="%"></bf:option>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="证件号码" col="5" formControl="true"/>
						<bf:text name="__LIKES_uniscCode" col="7" placeholder="证件号码"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="状态" col="5" formControl="true"/>
						<bf:select name="__LIKES_approvalStatus" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-RelApplystatus" >
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
			<bc:table id="relOrgSuspectTable" idField="pid" url="${base}/bus/relOrgSuspect" multiple="false">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="edit" text="编辑" type="button" handler="editRelOrgSuspect"/>
				<bc:toolbar id="remove" text="删除" type="button" handler="delOrgForFrom" />
				<bc:toolbar id="importSimple" text="导入" type="button" handler="importHandler"/>
				<bc:toolbar id="exportHandlerHis" text="导入模板下载" type="button" handler="exportHandler"/>
				<bc:toolbar id="send" text="提交审批" handler="send" type="button"/>
				<bc:toolbar id="export" text="导出" type="button" handler="dataExport"/>
				<bc:column field="pid" title="自增主键" width="100" visible="false"/>
				<bc:column field="regCode" title="登记编码" width="100" visible="false"/>
				<bc:column field="orignalOrg" title="源主体银行机构" width="100" visible="false"/>
				<bc:column field="orignalOrgName" title="源主体银行机构名称" width="100" visible="false"/>
				<bc:column field="orgName" title="企业名称" width="100"/>
				<bc:column field="identifyType" title="证件类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-ORGIdentifyType"/>
				<bc:column field="uniscCode" title="证件号码" width="100"/>
				<bc:column field="orgType" title="企业类型" width="100" visible="false"/>
				<bc:column field="domesticOrForeign" title="境内 / 境外" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
				<bc:column field="mgerOrg" title="机构名称" width="100" visible="false" formatter="custom:sys/unit/selectAllUnit"/>
				<bc:column field="regCapital" title="注册资本(万元/人民币)" width="100" visible="false"/>
				<bc:column field="regCapitalCurrency" title="注册资本币种代码" width="100" visible="false"/>
				<bc:column field="businessScope" title="营业范围" width="100" visible="false"/>
				<bc:column field="orgNature" title="机构性质" width="100"  visible="false"/>
				<bc:column field="isAssoParty" title="是否关联方" width="100"  formatter="custom:sys/dictItem/getDictItems/BUS-isRel" />
				<bc:column field="dataSource" title="数据来源" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DataSourceSus"/>
				<bc:column field="approvalStatus" title="操作状态"  width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelApplystatus"/>
				<bc:column field="legalPerson" title="法定代表人" width="100" visible="false"/>
				<bc:column field="regAddress" title="注册地" width="100" visible="false"/>
				<bc:column field="validStatus" title="是否有效 0-无效，1-有效" width="100" visible="false"/>
				<bc:column field="activeTime" title="生效时间" width="100" visible="false"/>
				<bc:column field="invalidTime" title="失效时间" width="100" visible="false"/>
				<bc:column field="statusComment" title="状态说明"  visible="false" width="100"/>
				<bc:column field="modifiedTime" title="更新时间" width="100"/>
				<bc:operator id="view" icon="search" title="查看"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
	$(function () {
		var code = "${code }";
		if(code.search("01,") != -1){
			$('#relOrgSuspectTable_tb_add').show();
		}
		if(code.search("02,") != -1){
			$('#relOrgSuspectTable_tb_edit').show();
		}
		if(code.search("03,") != -1){
			$('#relOrgSuspectTable_tb_remove').show();
		}
		if(code.search("07,") != -1){
			$('#relOrgSuspectTable_tb_send').show();
		}
		if(code.search("08,") != -1){
			$('#relOrgSuspectTable_tb_export').show();
		}
	})


	/* 导入模板下载*/
	function exportHandler() {
		var data = "1"
		var url = '${base}/sys/xlsie/SusOrgoutExportCallbackImpl/' + data;
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
			title: '疑似法人或其他组织信息导入',
			url: '${base}/sys/xlsie/import?bean=SusOrgImportCallbackImpl',
			onClose: function () {
				loadCallback();
			}
		});
	}

	//刷新表格
	function loadCallback() {
		$('#relOrgSuspectTable').jTable('refresh');
	}

	function dataExport(){
		var orgName=$("[name='__LIKES_orgName']").val()==""?"%25":$("[name='__LIKES_orgName']").val();
		var identifyType=$("[name='__LIKES_identifyType']").val()=="%"?"%25":$("[name='__LIKES_identifyType']").val();
		var uniscCode=$("[name='__LIKES_uniscCode']").val()==""?"%25":$("[name='__LIKES_uniscCode']").val();
		var approvalStatus=$("[name='__LIKES_approvalStatus']").val()=="%"?"%25":$("[name='__LIKES_approvalStatus']").val();
        var data = orgName+","+identifyType+","+uniscCode+","+approvalStatus;
        var url='${base}/sys/xlsie/SusOrgInfoExportCallbackImpl/'+ data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
	}


	/*
	* 提交审批
	* */
	function send(){
		var tables =  $('#relOrgSuspectTable').jTable('getCheckData');
		var pid = tables[0].pid;
		var status = tables[0].status;
		if (pid == '' || pid == null) {
			$.messager.alert('错误', "请选择至少一条数据", 'error');
			return;
		}
		$.ajax({
			url: "${base}/bus/relOrgSuspect/send/" + pid,
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

	function  editRelOrgSuspect() {
		var tables =  $('#relOrgSuspectTable').jTable('getCheckData');
		var status = tables[0].approvalStatus;
		if('4' == status || '6' == status){
			$.messager.alert('提示', '审批中和已归档的数据无法编辑。', 'info');
			return;
		}
		var pid = getIdSelections();
		if(pid != ""){
			window.location = '${base}/bus/relOrgSuspect/edit/' + pid+'?';
		}else {
			$.messager.alert('提示', '请选择法人机构。', 'info');
			return "";
		}
	}

    //删除
    function delOrgForFrom() {
        var pid=getIdSelections();
		var tables =  $('#relOrgSuspectTable').jTable('getCheckData');
		var status = tables[0].approvalStatus;
        if(pid==""||pid==undefined){
			$.messager.alert('错误', "请选择一条数据", 'error');
        }else{
			if('4' == status || '6' == status){
				$.messager.alert('提示', '审批中和已归档的数据无法编辑。', 'info');
				return;
			}
            // var r=confirm("确定要删除数据？");
			$.messager.confirm('提示', '确定要删除吗？', function (r) {
            if(r){
                $.ajax({
                    url:'${base}/bus/relOrgSuspect/remove/'+pid,
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
        })
    }}

    function getIdSelections() {
		return $.map($('#relOrgSuspectTable').bootstrapTable('getSelections'), function (row) {
			return row.pid;
		});
	}
	function getStatusSelections() {
		return $.map($('#relOrgSuspectTable').bootstrapTable('getSelections'), function (row) {
			return row.approvalStatus;
		});
	}


	//重置
	function frmReset() {
		window.location = '${base}/bus/relOrgSuspect/list';
	}
</script>
</body>
</html>