<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="form"/>
	<title>自然人新增或编辑</title>
	<x:jsLib/>

	<style>
		.boxse .form-group .col-xs-4{
			margin: 0.3% 0!important;
		}
		.boxse .form-group .col-xs-12{
			margin: 0.3% 0!important;
		}
		.boxse .form-group .col-xs-12 label{
			display: inline-block;
			float: left;
			margin: 0 0 0 4%;
			padding: 0 2% 0 0;
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
	</style>
</head>
<body class="gray-bg" >
<div class="row">
	<bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
		<bf:form id="editForm" method="post" validation="true">
			<bf:hidden id="opt" name="opt" value="${opt}"/>
			<bf:hidden id="pid" name="pid" value="${relPsn.pid}"/>
			<bf:hidden id="status" name="status" value="${relPsn.status}"/>
			<bf:hidden id="relSerial" name="relSerial" value="${relPsn.relSerial}"/>
			<div class="boxse">
				<bf:formGroup type="success"   hrp="bottom">
					<bc:col col="4">
						<bc:label text="姓名" col="5" formControl="true"/>
						<bf:text id="name" name="name" col="7" value="${relPsn.name}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="性別" col="5" formControl="true"/>
						<bf:select id="sex" name="sex" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Sex" value="${relPsn.sex}">
						</bf:select>
					</bc:col>

					<bc:col col="4">
						<bc:label text="证件类型" col="5" formControl="true"/>
						<bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relPsn.identifyType}">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="证件号码" col="5" formControl="true"/>
						<bf:text id="identifyNumber" name="identifyNumber" col="7" value="${relPsn.identifyNumber}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="政治面貌" col="5" formControl="true"/>
						<bf:select id="politicsStatus" name="politicsStatus" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS_politically" value="${relPsn.politicsStatus}">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="学历" col="5" formControl="true"/>
						<bf:select id="educationBackground" name="educationBackground" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Education" value="${relPsn.educationBackground}">
						</bf:select>
					</bc:col>

					<bc:col col="4">
						<bc:label text="民族" col="5" formControl="true"/>
						<bf:select id="race" name="race" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Race" value="${relPsn.race}">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="境内外标识" col="5" formControl="true"/>
						<bf:select id="domesticOrForeign" name="domesticOrForeign"  col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" value="${relPsn.domesticOrForeign}">
						</bf:select>
					</bc:col>

					<bc:col col="4">
						<bc:label text="出生日期" col="5" formControl="true"/>
						<bf:datebox name="birthdate" id="birthdate" col="7" format="date" value="${relPsn.birthdate}">
						</bf:datebox>
					</bc:col>
					<bc:col col="4">
						<bc:label text="婚姻状况" col="5" formControl="true"/>
						<bf:select id="maritalStatus" name="maritalStatus" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-MaritalStatus" value="${relPsn.maritalStatus}">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="移动电话" col="5" formControl="true"/>
						<bf:text id="mobile" name="mobile" col="7" value="${relPsn.mobile}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="邮箱" col="5" formControl="true"/>
						<bf:text id="email" name="email" col="7" value="${relPsn.email}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="机构名称" col="5" formControl="true"/>
						<bf:select id="mgerOrg" name="mgerOrg" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relPsn.mgerOrg}">
						</bf:select>
					</bc:col>
					<bc:col col="4">
						<bc:label text="居住地址" col="5" formControl="true"/>
						<bf:text id="residentialAddress" name="residentialAddress" col="7" value="${relPsn.residentialAddress}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="工作单位" col="5" formControl="true"/>
						<bf:text id="workUnits" name="workUnits" col="7" value="${relPsn.workUnits}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="单位地址" col="5" formControl="true"/>
						<bf:text id="unitAddress" name="unitAddress" col="7" value="${relPsn.unitAddress}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="是否为股东" col="5" formControl="true"/>
						<bf:checkGroup name="isShareholder" col="7" value="${relPsn.isShareholder}">
							<bf:radio value="1" label="是" checked="true"/>
							<bf:radio value="0" label="否"/>
						</bf:checkGroup>
					</bc:col>
					<bc:col col="4">
						<bc:label text="股东代码（账户号）" col="5" formControl="true"/>
						<bf:text id="shareholdersCode" name="shareholdersCode" col="7" value="${relPsn.shareholdersCode}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="交易账户开户行" col="5" formControl="true"/>
						<bf:text id="openBank" name="openBank" col="7" value="${relPsn.openBank}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="交易账户账号" col="5" formControl="true"/>
						<bf:text id="tradeAccount" name="tradeAccount" col="7" value="${relPsn.tradeAccount}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="入股时间" col="5" formControl="true"/>
						<bf:text id="inTime" name="inTime" col="7" value="${relPsn.inTime}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="入股价格" col="5" formControl="true"/>
						<bf:text id="inPrice" name="inPrice" col="7" value="${relPsn.inPrice}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="持股数量（股）" col="5" formControl="true"/>
						<bf:text id="holeShareAmount" name="holeShareAmount" col="7" value="${relPsn.holeShareAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="持股占比" col="5" formControl="true"/>
						<bf:text id="shareholdingProportion" name="shareholdingProportion" col="7" value="${relPsn.shareholdingProportion}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="总冻结股数（股）" col="5" formControl="true"/>
						<bf:text id="frozenShareAmount" name="frozenShareAmount" col="7" value="${relPsn.frozenShareAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="4">
						<bc:label text="总质押股数（股）" col="5" formControl="true"/>
						<bf:text id="pledgedShareAmount" name="pledgedShareAmount" col="7" value="${relPsn.pledgedShareAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="12">
						<bc:label text="冻结/解冻原因" col="0" formControl="true"/>
						<bf:text id="reason" name="reason" col="7" multiple="true">
						</bf:text>
					</bc:col>
				</bf:formGroup>
			</div>
			<bf:formGroup>
				<div class="col-sm-4 col-sm-offset-5" style="padding: 3% 0 0 0">
					<bc:button type="success" outline="true" text="确认提交" icon="save" click="sub()">
					</bc:button>
					<bc:button type="primary" text="返回" icon="arrow-circle-left">
						<be:event>
							window.location = '${base}/bus/gdInfo/list';
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
	$.fn.serializeJson = function () {
		var serializeObj = {};
		var array = this.serializeArray();
		$(array).each(function () {
			if (serializeObj[this.name]) {
				if ($.isArray(serializeObj[this.name])) {
					serializeObj[this.name].push(this.value);
				} else {
					serializeObj[this.name] = [serializeObj[this.name], this.value];
				}
			} else {
				serializeObj[this.name] = this.value;
			}
		});
		return serializeObj;
	};
	function sub(){
		var sex = $('#sex').find("option:selected").text();
		var identifyType = $('#identifyType').find("option:selected").text();
		var politicsStatus = $('#politicsStatus').find("option:selected").text();
		var educationBackground = $('#educationBackground').find("option:selected").text();
		var race = $('#race').find("option:selected").text();
		var domesticOrForeign = $('#domesticOrForeign').find("option:selected").text();
		var maritalStatus = $('#maritalStatus').find("option:selected").text();
		var mgerOrg = $('#mgerOrg').find("option:selected").text();
		var formData = $('#editForm').serializeJson();
		var myDate = new Date();
		formData.id =  myDate.getTime();
		formData.type = "psnDJ";
		formData.sex = sex;
		formData.identifyType =identifyType;
		formData.politicsStatus = politicsStatus;
		formData.educationBackground = educationBackground;
		formData.race = race;
		formData.domesticOrForeign = domesticOrForeign;
		formData.maritalStatus = maritalStatus;
		formData.mgerOrg = mgerOrg;
		var data = JSON.stringify(formData);
		formData = $.helper.base64Encode(data);
		debugger;
		var obj = {
			procDefCode: 'trancredit',
			oldData: '',
			newData: formData,
			uploadSessions: '',
			procInstId: ''
		}
		$.mask.post('${base}/workflow/process/saveAndStart', obj, function(data){
			if(data.success)
			{
				$.messager.alert('提示', '提交成功。', 'info', function(){
					window.location = '${base}/workflow/process/runingList';
				});
			}
			else
			{
				$.messager.alert('错误', data.msg, 'error');
			}
		});
	}
	<%--	var pid = getIdSelections1();//返回一个包含所有选中行中id列值的数组--%>
	<%--	// alert(pid);--%>
	<%--	$.ajax({--%>
	<%--		url:"${base}/bus/traCredit/subAudit/"+pid,--%>
	<%--		type:"post",--%>
	<%--		contentType: "application/json; charset=utf-8",--%>
	<%--		dataType: "json",--%>
	<%--		success:function(data){--%>
	<%--			console.log(data.data)--%>
	<%--			if(data !=null){--%>
	<%--				//必须传入id 值--%>
	<%--				var objData = data.data;--%>
	<%--				//alert(objData)--%>
	<%--				var myDate = new Date();--%>
	<%--				objData.id =  myDate.getTime();--%>
	<%--				objData.type = "CREDIT";--%>
	<%--				data = JSON.stringify(objData);--%>
	<%--				var formData = $.helper.base64Encode(data);--%>
	<%--				// alert(objData)--%>
	<%--				var obj = {--%>
	<%--					procDefCode: 'trancredit',--%>
	<%--					oldData: '',--%>
	<%--					newData: formData,--%>
	<%--					uploadSessions: '',--%>
	<%--					procInstId: ''--%>
	<%--				}--%>
	<%--				$.mask.post('${base}/workflow/process/saveAndStart', obj, function(data){--%>
	<%--					if(data.success)--%>
	<%--					{--%>
	<%--						$.messager.alert('提示', '提交成功。', 'info', function(){--%>
	<%--							window.location = '${base}/workflow/process/runingList';--%>
	<%--						});--%>
	<%--					}--%>
	<%--					else--%>
	<%--					{--%>
	<%--						$.messager.alert('错误', data.msg, 'error');--%>
	<%--					}--%>
	<%--				});--%>
	<%--			}--%>
	<%--		}--%>
	<%--	})--%>
	<%--}--%>
</script>
</body>
</html>
