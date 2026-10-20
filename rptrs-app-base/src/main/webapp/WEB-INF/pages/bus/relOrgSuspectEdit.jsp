<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>RelOrgSuspect</title>
	<style>
		.form-group .col-xs-4{
			margin: 0.3% 0!important;
			padding-top:5px;
		}
		.form-group .col-xs-4>.col-xs-7{
			height: 34px !important;
		}
		.form-group .input-group.date,
		.form-group .select2{
			width: 100% !important;
		}
		.boxse .form-group .col-xs-4{
			margin: 0.3% 0!important;
		}
		.boxse .has-success .form-control,.boxse .has-success .form-control:focus {
			border-color: #CCCCCC !important;
		}
		.has-success .select2-dropdown, .has-success .select2-selection {
			border-color: #CCCCCC !important;
		}
		.gray-bg {
			background: white !important;
		}
		.MainGd {
			display: none;
		}
		.voteProportion {
			display: none;
		}
	</style>
</head>
<body class="gray-bg" style="background: white">
	<div class="row">
		<bc:ibox id="relOrgSuspectBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:hidden id="pid" name="pid" value="${relOrgSuspect.pid}"/>
				<div class="boxse">
					<bf:formGroup>
						<bf:formGroup>
							<bc:col col="4">
								<bc:label cls="required" text="法人名称" col="5" formControl="true"/>
								<bf:text id="orgName" name="orgName" col="7" value="${relOrgSuspect.orgName}">
									<bv:notEmpty/>
								</bf:text>
							</bc:col>
							<bc:col col="4">
								<bc:label cls="required" text="证件类型" col="5" formControl="true"/>
								<bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
										   url="sys/dictItem/getDictItems/BUS-ORGIdentifyType" value="${relOrgSuspect.identifyType}">
									<bf:option text="请选择" value=""/>
									<bv:notEmpty/>
								</bf:select>
							</bc:col>
							<bc:col col="4">
								<bc:label cls="required" text="证件号码" col="5" formControl="true"/>
								<bf:text id="uniscCode" name="uniscCode" col="7" value="${relOrgSuspect.uniscCode}">
									<bv:notEmpty/>
								</bf:text>
							</bc:col>
						</bf:formGroup>
						<bf:formGroup>
							<bc:col col="4">
								<bc:label cls="required" text="境内/境外" col="5" formControl="true"/>
								<bf:checkGroup name="domesticOrForeign" col="7" value="${relOrgSuspect.domesticOrForeign}">
									<bf:radio id="chk" value="1" label="境内" checked="true">
										<bv:notEmpty/>
									</bf:radio>
									<bf:radio id="chk1" value="2" label="境外" />
								</bf:checkGroup>
							</bc:col>
							<bc:col col="4">
								<bc:label cls="required" text="是否为本主体控股公司" col="5" formControl="true"/>
								<bf:select id="isSubsidiary" name="isSubsidiary" col="7" dataSource="custom"
										   url="sys/dictItem/getDictItems/BUS-isSubsidiary" value="${relOrgSuspect.isSubsidiary}">
									<bf:option text="请选择" value=""></bf:option>
									<bv:notEmpty/>
								</bf:select>
							</bc:col>
							<bc:col col="4">
								<bc:label text="机构状态" col="5" formControl="true"/>
								<bf:select id="status" name="status" col="7" dataSource="custom"
										   url="sys/dictItem/getDictItems/BUS-Status" value="${relOrgSuspect.status}">
									<bf:option text="请选择" value=""></bf:option>
								</bf:select>
							</bc:col>
						</bf:formGroup>
						<bf:formGroup>
							<bc:col col="4">
								<bc:label text="企业类型" col="5" formControl="true"/>
								<bf:select id="orgType" name="orgType" col="7" dataSource="custom"
										   url="sys/dictItem/getDictItems/BUS-CompanyType" value="${relOrgSuspect.orgType}">
									<bf:option text="请选择" value=""></bf:option>
								</bf:select>
							</bc:col>
							<bc:col col="4">
								<bc:label text="注册地址" col="5" formControl="true"/>
								<bf:text id="regAddress" name="regAddress" col="7" value="${relOrgSuspect.regAddress}">
								</bf:text>
							</bc:col>
							<bc:col col="4">
								<bc:label text="注册资本(万元/人民币)" col="5" formControl="true"/>
								<bf:text id="regCapital" name="regCapital" col="7" value="${relOrgSuspect.regCapital}">
								</bf:text>
							</bc:col>
						</bf:formGroup>
						<bf:formGroup>
							<bc:col col="4">
								<bc:label text="法定代表人" col="5" formControl="true"/>
								<bf:text id="legalPerson" name="legalPerson" col="7" value="${relOrgSuspect.legalPerson}">
								</bf:text>
							</bc:col>
							<bc:col col="4">
								<bc:label text="法定代表人证件类型" col="5" formControl="true"/>
								<bf:select id="legalIdentifyType" name="legalIdentifyType" col="7" dataSource="custom"
										   url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relOrgSuspect.legalIdentifyType}">
									<bf:option text="请选择" value=""/>
								</bf:select>
							</bc:col>
							<bc:col col="4">
								<bc:label text="法定代表人证件号码" col="5" formControl="true"/>
								<bf:text id="legalIdentifyNumber" name="legalIdentifyNumber" col="7"
										 value="${relOrgSuspect.legalIdentifyNumber}">
									<bv:stringLength min="5" max="18" trim="true" message="请输入正确的证件号码"/>
								</bf:text>
							</bc:col>
						</bf:formGroup>
						<bf:formGroup>
							<bc:col col="4">
								<bc:label text="统计口径" col="5" formControl="true"/>
								<bf:text id="regulatorYbj" name="regulatorYbj" col="7"
										 value="银保监会" disabled="true">
								</bf:text>
							</bc:col>
							<bc:col col="4">
								<bc:label text="集团证件号" col="5" formControl="true"/>
								<bf:text id="memGroup" name="memGroup" col="7" value="${relOrgSuspect.memGroup}">
								</bf:text>
							</bc:col>
							<bc:col col="4">
								<div id="note" style="float: right;margin: 0.3% 0 !important;">
									<p style="color: red;margin: auto;text-align: center;font-weight: 700;line-height: 2rem;font-size: smaller">根据监管要求，若两个关联方或多个关联方属于同一集团时，需要合并计算。企业所属集团时，集团证件号必填，按照统一社会信用代码填写。</p>
								</div>
							</bc:col>
						</bf:formGroup>
						<bf:formGroup>
							<bc:col col="12">
								<bc:label text="经营范围" col="1" formControl="true"/>
								<bf:text id="businessScope" name="businessScope" col="10" multiple="true" value="${relOrgSuspect.businessScope}"></bf:text>
							</bc:col>
						</bf:formGroup>
					</bf:formGroup>
				</div>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-5" style="padding: 3% 0 0 0">
						<bc:button type="success" outline="true" text="保存" icon="save">
							<be:event>
								save();
							</be:event>
						</bc:button>
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/relOrgSuspect/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
			</bf:form>
		</bc:ibox>
	</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
$(document).ready(function(){
	$(".required").each(function() {
		var $this = $(this);
		$(this).html("<font color='red'>✶</font>"+$this.html());
	});
});

function save() {
	var uniscCode = $('#uniscCode').val();
	var identifyType = $('#identifyType').val();
	if (uniscCode!=null&&identifyType!=null&&
			uniscCode!=""&&identifyType!=""){
		//验证格式
		if (identifyType == "1"||identifyType=="2") {//统一社会信用代码或工商注册号
			if (!(8 <= uniscCode.length && uniscCode.length <= 18)) {
				$.messager.alert('提示', '请检查证件号码', 'warning');
				return false;
			}
		} else {
			if (!(5 <= uniscCode.length && uniscCode.length <= 18)) {
				$.messager.alert('提示', '请检查证件号码', 'warning');
				return false;
			}
		}
		$.ajax({
			type: 'GET',
			url: '${base}/bus/relOrg/selectMgerOrg',
			data: {
				"uniscCode": uniscCode,
				"identifyType": identifyType,
			},
			dataType: 'json',
			success: function (data) {
				if (data) {
					$.messager.alert('提示', '关联方已存在', 'warning');
					return false;
				}
			}
		});
	}
	$('#editForm').data('formValidation').validate();
	var isValid = $('#editForm').data('formValidation').isValid();
	if(isValid){
		var formData = $('#editForm').serializeAll();
		$.mask.post('${base}/bus/relOrgSuspect/save', formData, function(data){
			if(data.success) {
				$.messager.alert('提示', data.msg, 'info', function(){
					window.location = '${base}/bus/relOrgSuspect/list';
				});
			}
			else
			{
				$.messager.alert('错误', data.msg, 'error');
			}
		});
	}
}
</script>
</body>
</html>
