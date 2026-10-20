<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp" %>
	<br:css pack="form"/>
	<title>机构基础数据编辑</title>
	<style>
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
<div class="row">
	<bc:ibox id="orgBaseDataBox" title="" subTitle="" col="12" collapsible="true">
		<bf:form id="editForm" method="post" validation="true">
			<bf:hidden id="opt" name="opt" value="${view}"/>
			<bf:hidden id="pid" name="pid" value="${rootOrgInfo.pid}"/>
			<bf:hidden id="viewYear" name="pid" value="${rootOrgInfo.years}"/>
			<bf:hidden id="limit01" name="limit01" value="${limit01}"/>
			<bf:hidden id="limit02" name="limit02" value="${limit02}"/>
			<bf:hidden id="limit03" name="limit03" value="${limit03}"/>
			<bf:hidden id="limit04" name="limit04" value="${limit04}"/>
			<bf:hidden id="limit05" name="limit05" value="${limit05}"/>
			<bf:hidden id="limit06" name="limit06" value="${limit06}"/>
			<bf:hidden id="limit07" name="limit07" value="${limit07}"/>
			<bc:row cls="m-b">
				<bf:formGroup hrp="bottom" hr="dashed">
					<bf:formGroup col="6">
						<bc:label cls="required" text="年度" col="3" formControl="true"/>
						<%--<bf:datebox id="years" name="years" col="9" format="date" value="${dateTime}"/>--%>
						<bf:select id="years" name="years" col="9" value="${dateTime}" disabled="true">
							<be:event>
								yearSelected();
							</be:event>
							<bf:option text="请选择" value=""></bf:option>
						</bf:select>
						<bv:notEmpty/>
					</bf:formGroup>
					<bf:formGroup col="6">
						<bc:label cls="required" text="季度" col="3" formControl="true"/>
						<bf:select id="quarter" name="quarter" col="9" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-Quarter"
								   value="${rootOrgInfo.quarter}" disabled="true">
							<bf:option text="请选择" value=""></bf:option>
							<bv:notEmpty/>
						</bf:select>
					</bf:formGroup>
				</bf:formGroup>
			</bc:row>
			<bc:row cls="m-b">
				<bf:formGroup hrp="bottom" hr="dashed" >
					<bf:formGroup col="6">
						<bc:label text="总资产(万元，请保留4位小数)" cls="required" col="3" formControl="true"/>
						<bf:text id="totalCapital" name="totalCapital" col="9"
								 value="${rootOrgInfo.totalCapital}" disabled="true">
							<bv:notEmpty/>
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="6">
						<bc:label text="净资产(万元，请保留4位小数)" cls="required" col="3" formControl="true"/>
						<bf:text id="netCapital" name="netCapital" col="9"
								 value="${rootOrgInfo.netCaptial}" disabled="true">
							<bv:notEmpty/>
						</bf:text>
					</bf:formGroup>
				</bf:formGroup>
			</bc:row>
			<bc:row cls="m-b">
				<bf:formGroup hrp="bottom" hr="dashed" >
					<%--<bf:formGroup col="6">
						<bc:label text="季度综合偿付能力充足率（%）" cls="required" col="3" formControl="true"/>
						<bf:text id="quarterCompreSolvencyRate" name="quarterCompreSolvencyRate" col="9" value="${rootOrgInfo.quarterCompreSolvencyRate}" disabled="true">
							<bv:notEmpty/>
						</bf:text>
					</bf:formGroup>--%>

					<bf:formGroup col="6">
						<bc:label text="权益类资产投资上限比例（%）" col="3" formControl="true"/>
						<bf:text id="equityUpperLimitScale" name="equityUpperLimitScale" col="9" value="${rootOrgInfo.equityUpperLimitScale}" disabled="true">
							<bv:notEmpty/>
						</bf:text>
					</bf:formGroup>
				</bf:formGroup>
			</bc:row>


			<bf:formGroup>
				<div class="col-sm-12 col-sm-offset-5">
					<bc:button type="primary" text="返回" icon="arrow-circle-left">
						<be:event>
							window.location = '${base}/bus/rootOrgInfo/list';
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
    /*start*/

    // function x() {
    // 	$('#years').attr('disabled','true');
    // 	$('#quarter').attr('disabled','true');
    // 	$('#totalCapital').attr('disabled','true');
    // 	$('#netCapital').attr('disabled','true');
    // 	$('#quarterCompreSolvencyRate').attr('disabled','true');
    // }

    /* 获取选中的下拉框的值 */
    function yearSelected(){
        var valueSel = $("#years").find("option:selected").val();
        console.info("选中的option的value值为："+valueSel);/* value值，如2017 */
    }


    /*end*/

</script>
</body>
</html>
