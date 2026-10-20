<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
	<style type="text/css">
		#relPsnTable_tb_add,#relPsnTable_tb_edit,
        #relPsnTable_tb_remove,#relPsnTable_tb_importSimple,
        #relPsnTable_tb_sendCommand,#relPsnTable_tb_exportHandler,
        #relPsnTable_tb_send,#relPsnTable_tb_out,#relPsnTable_tb_addOrRemoveBlacklist{
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

<title>关联关系自然人</title>
	<style>
		.form-group  select{
			width: 100% !important;
		}
	</style>
</head>
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bc:row cls="m-b">
					<bf:formGroup col="4">
						<bc:label text="姓名" col="5" formControl="true"/>
						<bf:text name="__LIKES_name" col="7" placeholder="姓名"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="证件类型" col="5" formControl="true"/>
						<bf:select name="__LIKES_identifyType" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-IdentifyType" >
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
						<bf:text name="__LIKES_identifyNumber" col="7" placeholder="证件号码"/>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="审批状态" col="5" formControl="true"/>
						<bf:select name="__LIKES_status" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Applystatus" >
							<bf:option text="ALL" value="%"></bf:option>
							<be:event type="select">
								var input = $("#__LIKES_status");
								input.focus();
								input.blur();
							</be:event>
						</bf:select>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label text="是否黑名单" col="5" formControl="true"/>
						<bf:select name="__LIKES_isBlack" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-isNo" >
							<bf:option text="ALL" value="%"></bf:option>
							<be:event type="select">
								var input = $("#__LIKES_isBlack");
								input.focus();
								input.blur();
							</be:event>
						</bf:select>
					</bf:formGroup>
<%--					数据来源--%>
					<bf:formGroup col="4">
						<bc:label text="数据来源" col="5" formControl="true"/>
						<bf:select id="dataSource" name="__LIKES_dataSource" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-DataSource">
							<bf:option text="ALL" value="%"></bf:option>
							<be:event type="select">
								var input = $("#__LIKES_dataSource");
								input.focus();
								input.blur();
							</be:event>
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
					<%--<bc:col col="3" >
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					</bc:col>--%>
				</bc:row>
				<bc:row cls="m-b">
					<div class="col-sm-12 col-sm-offset-5">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					</div>

				</bc:row>
			</bf:form>
			<bc:table id="relPsnTable"  idField="pid" multiple="false" url="${base}/fil/filRelPsn" selectFirst="false">
				<bc:toolbar id="" text="查看" type="button" handler="viewFilBusiness"/>
				<bc:toolbar id="compared" text="查看变更" handler="compared" type="button"/>
				<%--<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="edit" text="编辑" type="button" handler="editRelPsn"/>
				<bc:toolbar id="remove" text="删除" type="button" handler="removeRelPsn"/>
				<bc:toolbar id ="importSimple" text="导入" type="button" handler="importHandler"/>
				<bc:toolbar id ="sendCommand" text="任务下发" type="button" handler="sendCommand"/>
				<bc:toolbar id ="exportHandler" text="模板下载" type="button" handler="exportHandler"/>
				&lt;%&ndash;<bc:toolbar id="importSimple" text="导入" type="button" handler="importHandler"/>&ndash;%&gt;
				&lt;%&ndash;<bc:toolbar id="exportHandler" text="下载导入模板" type="button" handler="exportHandler"/>&ndash;%&gt;
				<bc:toolbar id="send" text="提交审批" handler="send" type="button"/>
				<bc:toolbar id ="out" text="导出" type="button" handler="out"/>
				&lt;%&ndash;<bc:toolbar id="out" text="数据导出" handler="addblack" type="button"/>&ndash;%&gt;
				&lt;%&ndash;<bc:toolbar id="disconnection" text="解除关联关系" handler="disconnection" type="button"/>&ndash;%&gt;
				<bc:toolbar id="addOrRemoveBlacklist" text="加入/移出黑名单" handler="addOrRemoveBlacklist" type="button"/>--%>
				<bc:column field="pid" title="自增长主键" width="100"  visible="false"/>
				<bc:column field="filNum" title="备案编号" width="100"/>
				<bc:column field="regCode" title="登记编码" width="100"  visible="false"/>
				<bc:column field="orignalOrg" title="源主体银行机构" width="100"  visible="false"/>
				<bc:column field="orignalOrgName" title="源主体银行机构名称" width="100"  visible="false"/>
				<bc:column field="name" title="姓名" width="100"/>
				<bc:column field="sex" title="性別" width="100"  visible="false"/>
				<bc:column field="nationality" title="国别/地区" width="100"  visible="false"/>
                <bc:column field="identifyType" title="证件类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="identifyNumber" title="证件号码" width="100"/>
				<bc:column field="domesticOrForeign" title="境内外标识" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
				<bc:column field="race" title="民族" width="100"  visible="false"/>
				<bc:column field="mgerOrg" title="机构名称" width="100" formatter="custom:sys/unit/selectUnit"/>
				<bc:column field="birthdate" title="出生日期" width="100" visible="false"/>
				<bc:column field="mobile" title="移动电话" width="100" visible="false"/>
				<bc:column field="email" title="邮箱" width="100" visible="false"/>
				<bc:column field="dutyCode" title="职位" width="100" visible="false"/>
<%--				<bc:column field="status" title="审批状态" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>--%>
<%--				<bc:column field="statusComment" title="审批状态说明" width="100"/>--%>
				<bc:column field="maritalStatus" title="婚姻状况" width="100" visible="false"/>
				<bc:column field="validStatus" title="是否有效   0|无效1|有效" width="100" visible="false"/>
				<bc:column field="activeTime" title="生效时间" width="100" visible="false"/>
				<bc:column field="invalidTime" title="失效时间" width="100" visible="false"/>
				<bc:column field="upApprovalDepartment" title="上一级审批部门" width="100" visible="false"/>
				<bc:column field="nextApprovalDepartment" title="下一级审批部门" width="100" visible="false"/>
				<bc:column field="upApprovalPeople" title="上一级审批人" width="100" visible="false"/>
				<bc:column field="nextApprovalPeople" title="下一级审批人" width="100" visible="false"/>
				<bc:column field="regulatorOth1" title="口径" width="100"
						   formatter="custom:sys/dictItem/getDictItems/BUS-Caliber" />
<%--				<bc:column field="regulatorYbj" title="银保监口径" width="100"  formatter="custom:sys/dictItem/getDictItems/BUS-isNo" visible="false"/>--%>
<%--				<bc:column field="regulatorZjh" title="证监会口径" width="100"  formatter="custom:sys/dictItem/getDictItems/BUS-isNo" visible="false"/>--%>
<%--				<bc:column field="regulatorCzb" title="财政部口径" width="100"  formatter="custom:sys/dictItem/getDictItems/BUS-isNo" visible="false"/>--%>
<%--				<bc:column field="regulatorOth1" title="其他口径1（0|否1|是）" width="100" visible="false"/>--%>
<%--				<bc:column field="regulatorOth2" title="其他口径2（0|否1|是）" width="100" visible="false"/>--%>
				<bc:column field="isBlack" title="是否黑名单" width="100"  formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
				<bc:column field="dataSource" title="数据来源" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DataSource"/>
				<bc:column field="blackReason" title="加入黑名单原因" width="100" visible="false"/>
                <bc:column field="modifiedTime" title="更新时间" width="100"/>
				<%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
				<%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
	function viewFilBusiness() {
		var pid = getIdSelections();
		if(pid.length==1){
			window.location = '${base}/fil/filRelPsn/view/'+pid;
		}else{
			$.messager.alert('提示', '请选择一个关联方。', 'info');
			return "";
		}
	}

    function getStatusSelections() {
        return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }
	function compared() {
		var pid = getIdSelections();
		if(pid.length==1){
			window.location = '${base}/fil/filRelPsn/compared/' + pid+'?';
		}else {
			$.messager.alert('提示', '请选择一个关联方。', 'info');
			return;
		}
	}
    function getIdSelections() {
        return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

	//刷新表格
	function loadCallback(){
		$('#relPsnTable').jTable('refresh');
	}
	function  addOrRemoveBlacklist() {
        var status=getStatusSelections();
        if (status !="6"){
            $.messager.alert('提示', '请选择已归档的关联方', 'info');
            return "";
        }
		var pid = getIdSelections();
		if(pid != ""){
			window.location = '${base}/fil/relPsn/addOrRemoveBlack/' + pid+'?';
		}else {
			$.messager.alert('提示', '请选择自然人。', 'info');
			return "";
		}
	}
	function  disconnection() {
		var pid = getIdSelections();
		if(pid != ""){
			window.location = '${base}/fil/relPsn/disconnection/' + pid+'?';
		}else {
			$.messager.alert('提示', '请选择自然人。', 'info');
			return "";
		}
	}
	
	//重置
	function frmReset() {

		window.location = '${base}/fil/filRelPsn/list';
	}
</script>
</body>
</html>