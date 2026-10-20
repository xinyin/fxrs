<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="list"/>
	<title>内部交易额度预警规则</title>
</head>
<style>
	.center div {
		float: none;
		margin: 1% 0;
	}

	.lefts, .rights {
		border: 1px solid #cccccc;
		width: 40%;
	}

	.lefts p, .rights p {
		width: 16%;
		margin: 3% auto;
		padding: 2%;
		background: #0d8ddb;
		color: white;
		border-radius: 6px;
	}

	.lefts div, .rights div {
		float: none;
		margin: 2% 0;
		display: flex;

	}

	.center {
		margin: 10% 0 0 0;
		width: 20%;
	}

	.center label {
		text-align: center !important;
	}

	.box {
		display: flex;
		justify-content: space-around;
		width: 80%;
		margin: 0 auto;
	}

	.file {
		position: absolute;
		top: 100px;
		left: 44%;
		display: inline-block;
		background: #D0EEFF;
		border: 1px solid #99D3F5;
		border-radius: 4px;
		padding: 4px 12px;
		overflow: hidden;
		color: #1E88C7;
		text-decoration: none;
		text-indent: 0;
		line-height: 20px;
	}

	.file input {
		position: absolute;
		font-size: 100px;
		right: 0;
		top: 0;
		opacity: 0;
	}

	.file:hover {
		background: #AADFFD;
		border-color: #78C3F3;
		color: #004974;
		text-decoration: none;
	}
</style>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="内部交易额度预警规则" subTitle="请输入内部交易额度预警规则查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true" method="post">


				<bc:col col="5" >
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<input id="allPrecent" value="${allPercent}" style="display: none"/>
							<bc:label text="预警指标" col="4" formControl="true"/>
							<bf:text id="warningIndex" name="warningIndex" col="4" disabled="true" value="全部内部人">
							</bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="一级预警阈值(%)" col="4" formControl="true"/>
							<bf:text id="warningIndex1" name="warnThreshold1l" col="4" value="${warnConfig.warnThreshold1l}">
							</bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="二级预警阈值(%)" col="4" formControl="true"/>
							<bf:text id="warningIndex2" name="warnThreshold2l" col="4" value="${warnConfig.warnThreshold2l}">
							</bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="三级预警阈值(%)" col="4" formControl="true"/>
							<bf:text id="warningIndex3" name="warnThreshold3l" col="4" value="${warnConfig.warnThreshold3l}">
							</bf:text>
						</bc:col>
					</bf:formGroup>
				</bc:col>
				<bc:col col="2">
					<div style="width: 50%;border-right: 1px solid ;height: 280px"></div>
				</bc:col>
				<bc:col col="5" >
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<input id="allPrecent" value="${allPercent}" style="display: none"/>
							<bc:label text="预警指标" col="4" formControl="true"/>
							<bf:text id="swarningIndex" name="warningIndex" col="4" disabled="true" value="单一内部人">
							</bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="一级预警阈值(%)" col="4" formControl="true"/>
							<bf:text id="swarningIndex1" name="swarnThreshold1l" col="4" value="${warnConfig1.warnThreshold1l}">
							</bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="二级预警阈值(%)" col="4" formControl="true"/>
							<bf:text id="swarningIndex2" name="swarnThreshold2l" col="4" value="${warnConfig1.warnThreshold2l}">
							</bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="三级预警阈值(%)" col="4" formControl="true"/>
							<bf:text id="swarningIndex3" name="swarnThreshold3l" col="4" value="${warnConfig1.warnThreshold3l}">
							</bf:text>
						</bc:col>
					</bf:formGroup>
				</bc:col>


				<bf:formGroup>
					<div class="col-sm-12 col-sm-offset-5">
						<bc:button type="danger" text="保存" icon="save">
							<be:event>
								$('#queryForm').data('formValidation').validate();
								var isValid = $('#queryForm').data('formValidation').isValid();
								if(isValid)
								{
								var formData = $('#queryForm').serializeAll();

								//全部内部人
								var allPrecent = $('#allPrecent').val();
								var warningIndex11 = $('#warningIndex1').val();
								var warnThreshold2l = $('#warningIndex2').val;
								var warnThreshold3l = $('#warningIndex3').val;

								if(allPrecent < warningIndex11){

								}else if(allPrecent >= warningIndex11 && allPrecent <= warnThreshold2l){
								$.messager.alert('提示', '一级预警', 'info');
								return
								}else if (allPrecent >= warnThreshold2l && allPrecent <= warnThreshold3l){
								$.messager.alert('提示', '二级预警', 'info');
								return
								}else {
								$.messager.alert('提示', '三级预警', 'info');
								return
								}

								//单一内部人
                                <%--var single1 = $("#warningIndex01").val();--%>
								<%--var single2 = $("#warningIndex02").val();--%>
								<%--var single3 = $("#warningIndex03").val();--%>
								<%--var v1 = formData[0].value+","+single1;--%>
								<%--var v2 = formData[1].value+","+single2;--%>
								<%--var v3 = formData[2].value+","+single3;--%>

								<%--var dataForm = [];--%>
								<%--var jsonData0={"name":"warnThreshold1l","value":v1};--%>
								<%--var jsonData1={"name":"warnThreshold2l","value":v2};--%>
								<%--var jsonData2={"name":"warnThreshold3l","value":v3};--%>
								<%--var warningIndex ={"name":"warningIndex","value":"全部内部人,单一内部人"}--%>
								<%--dataForm.push(jsonData0);--%>
								<%--dataForm.push(jsonData1);--%>
								<%--dataForm.push(jsonData2);--%>
								<%--dataForm.push(warningIndex);--%>

								<%--console.log()--%>

								$.mask.post('${base}/bus/warnConfig/save', formData, function(data){
								if(data.success)
								{
								$.messager.alert('提示', data.msg, 'info', function(){
								window.location = '${base}/bus/warnConfig/list';
								});
								}
								else
								{
								$.messager.alert('错误', data.msg, 'error');
								}
								});
								}
							</be:event>
						</bc:button>
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/warnConfig/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
			</bf:form>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<%--<script type="text/javascript">
    $(document).ready(function(){

    });
</script>--%>
</body>
</html>