<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp" %>
	<x:jsLib/>
	<br:css pack="form"/>
	<title>自然人新增或编辑</title>
	<script>
		$(document).ready(function () {
			x();
		});
		function x() {
			$('#name').attr('disabled', 'true');//姓名
			$('#sex').attr('disabled', 'true');//性别
			$('#identifyType').attr('disabled', 'true');//证件类型
			$('#identifyNumber').attr('disabled', 'true');//证件号码
			$('#domesticOrForeign').attr('disabled', 'true');//境内外标识
			$('#mobile').attr('disabled', 'true');//移动电话
			$('#email').attr('disabled', 'true');//邮箱
			$('#workUnits').attr('disabled', 'true');//单位地址
			$('#unitAddress').attr('disabled', 'true');//单位地址
			$("[name=birthdate]").attr("readonly", "readonly");//出生日期
			$(".input-group-addon").off();
			$('#chk').attr('disabled', 'true');
			$('#chk1').attr('disabled', 'true');
		}
	</script>
	<x:jsLib/>

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

		.form-group {
			margin: 0.3% 0 !important;
		}

		.form-group .col-xs-12 {
			margin: 0.3% 0 !important;
		}

		.form-group .col-xs-12 label {
			display: inline-block;
			float: left;
			margin: 0 0 0 3%;
			padding: 0 2% 0 0;
		}

		.form-group .col-xs-12 label {
			display: inline-block;
			float: left;
			margin: 0 0 0 3%;
			padding: 0 2% 0 0;
			width: 10%;
		}

		.has-success .form-control, .boxse .has-success .form-control:focus {
			border-color: #CCCCCC !important;
		}

		.gray-bg {
			background: white !important;
		}
	</style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
	<bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
	<bf:form id="editForm" method="post" validation="true">
	<bf:hidden id="opt" name="opt" value="${opt}"/>
	<bf:formGroup>
		<div class="col-sm-6">
			<bc:button type="primary" text="基本信息">
				<be:event>
					basicInformation();
				</be:event>
			</bc:button>

		</div>
	</bf:formGroup>
	<bc:row cls="m-b">
	<bf:formGroup hrp="bottom">
		<div class="boxse">
			<bf:formGroup type="success">
				<bc:col col="4">
					<bc:label cls="required" text="姓名" col="5" formControl="true"/>
					<bf:text id="name" name="name" col="7" value="${relPsnHistroy.name}" disabled='true'>
						<bv:notEmpty/>
					</bf:text>
				</bc:col>
				<bc:col col="4">
					<bc:label cls="required" text="证件类型" col="5" formControl="true"/>
					<bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
							   url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relPsnHistroy.identifyType}" disabled='true'>
						<bf:option text="请选择" value=""></bf:option>
						<bv:notEmpty/>
					</bf:select>
				</bc:col>
				<bc:col col="4">
					<bc:label cls="required" text="证件号码" col="5" formControl="true"/>
					<bf:text id="identifyNumber" name="identifyNumber" col="7"
							 value="${relPsnHistroy.identifyNumber}" disabled='true'>
						<bv:notEmpty/>
					</bf:text>
				</bc:col>
			</bf:formGroup>
			<bf:formGroup type="success">
				<bc:col col="4">
					<bc:label cls="required" text="境内/境外" col="5" formControl="true"/>
					<bf:checkGroup id="domesticOrForeign" name="domesticOrForeign" col="7"
								   value="${relPsnHistroy.domesticOrForeign}" disabled='true'>
						<bf:radio id="chk" value="1" label="境内" checked="true"/>
						<bf:radio id="chk1" value="2" label="境外"/>
					</bf:checkGroup>
				</bc:col>
				<bc:col col="4">
					<bc:label text="性别" col="5" formControl="true"/>
					<bf:select id="sex" name="sex" col="7" dataSource="custom"
							   url="sys/dictItem/getDictItems/BUS-Sex" value="${relPsnHistroy.sex}" disabled='true'>
						<bf:option text="请选择" value=""></bf:option>
					</bf:select>
				</bc:col>
				<bc:col col="4">
					<bc:label text="出生日期" col="5" formControl="true"/>
					<bf:datebox name="birthdate" id="birthdate" col="7" format="date" disabled='true'
								value="${relPsnHistroy.birthdate}">
					</bf:datebox>
				</bc:col>
			</bf:formGroup>
			<bf:formGroup type="success">
				<bc:col col="4">
					<bc:label text="工作单位" col="5" formControl="true"/>
					<bf:text id="workUnits" name="workUnits" col="7" value="${relPsnHistroy.workUnits}" disabled='true'>
					</bf:text>
				</bc:col>
				<bc:col col="4">
					<bc:label text="单位地址" col="5" formControl="true"/>
					<bf:text id="unitAddress" name="unitAddress" col="7" value="${relPsnHistroy.unitAddress}" disabled='true'>
						<bv:stringLength max="100" message="单位地址长度不能超过100"/>
					</bf:text>
				</bc:col>
				<bc:col col="4">
					<bc:label text="移动电话" col="5" formControl="true"/>
					<bf:text id="mobile" name="mobile" col="7" value="${relPsnHistroy.mobile}" disabled='true'>
						<bv:stringLength min="11" max="11" message="请输入11位数字" trim="true"/>
					</bf:text>
				</bc:col>
			</bf:formGroup>
			<bf:formGroup type="success">
				<bc:col col="4">
					<bc:label text="邮箱" col="5" formControl="true"/>
					<bf:text id="email" name="email" col="7" value="${relPsnHistroy.email}" disabled='true'>
						<bv:stringLength max="32" message="邮箱长度不能超过32位"/>
					</bf:text>
				</bc:col>
				<%--<bc:col col="4">
                    <bc:label text="统计口径" col="5" formControl="true"/>
                    <bf:text id="regulatorYbj" name="regulatorYbj" col="7"
                             disabled='true' value="银保监会"/>
                </bc:col>--%>
			</bf:formGroup>
		</div>
	</bf:formGroup>
</div>
<bf:formGroup>
	<div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
		<bc:button id="y" type="primary" text="返回" icon="arrow-circle-left">
			<be:event>
				window.location = '${base}/bus/relPsn/history/list';
			</be:event>
		</bc:button>
	</div>
</bf:formGroup>
</bc:row>
</bf:form>
</bc:ibox>
</div>
<br:js pack="form"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script type="text/javascript">
	clear();
	$(document).ready(function () {
		$(".required").each(function () {
			var $this = $(this);
			$(this).html("<font color='red'>✶</font>" + $this.html());
		});
	});
	function clear() {
		localStorage.clear()
	}
</script>
</body>
</html>
