<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp" %>
	<br:css pack="form"/>
	<title>关联交易表（保险）-利益转移类</title>
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

		textarea {
			resize: none; /* Disables resizability */
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
		#addInfos{
			color: #B48732;
			background: none;
			border: none;
			padding: 6px 16px;
		}
		.text-style {
			font-weight: bold;
			padding: 0;
			text-align: left !important;
			color: #404040;
			font-size: 16px;
		}
	</style>
</head>
<body class="gray-bg">
<div class="row">
	<bc:ibox id="traInterestBox" title="" subTitle="" col="12" collapsible="true">
		<bf:form id="editForm" method="post" validation="true">
			<bf:hidden id="opt" name="opt" value="${opt}"/>
			<bf:hidden id="relId" name="relId" value="${traInterest.relId}"/>
			<bf:hidden id="relNumber" name="relNumber" value="${traInterest.relNumber}"/>
			<bf:hidden id="relType" name="relType" value="${traInterest.relType}"/>
			<bf:hidden id="pid" name="pid" value="${traInterest.pid}"/>
			<bf:hidden id="ruleResult" name="ruleResult" value=""/>
			<bf:hidden id="total" name="total" value="${traInterest.totolTradeAmount}"/>
			<bf:hidden id="amount" name="amount" value="${traInterest.contractAmount}"/>
			<bf:hidden id="transNum" name="transNum" value="${traInterest.transNum}"/>
			<bf:hidden id="tradeZeroAmount" name="tradeZeroAmount" value="${traInterest.tradeZeroAmount}"/>
			<bf:hidden id="status" name="status" value="${traInterest.status}"/>
			<%--未达到累积关联重大的关联方relNumbers,多个关联方使用，隔开--%>
			<bf:hidden id="notMajorRelNumber" name="notMajorRelNumber" value="${traInterest.notMajorRelNumber}"/>
			<bc:row cls="m-b">
				<bf:formGroup cls="group-butom-1" hrp="bottom" hr="dashed">
					<bf:formGroup col="10">
						<bc:label text="交易发起方信息：" col="2" formControl="true" cls="text-style"/>
					</bf:formGroup>
					<bf:formGroup col="2">
					</bf:formGroup>
				</bf:formGroup>
				<bf:formGroup hrp="bottom">
					<bf:formGroup col="4">
						<bc:label  text="交易主体名称" col="5" formControl="true"/>
						<bf:text id="traSubName" name="traSubName" col="7"
								 value="复星保德信人寿保险有限公司"
								 disabled="true">
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="4">
						<bc:label  cls="required" text="管理机构" col="5" formControl="true"/>
						<bf:text id="managerOrg" name="managerOrg" col="7"
								 value="${traInterest.managerOrg}"
								 disabled="true">
						</bf:text>
					</bf:formGroup>
				</bf:formGroup>
				<bf:formGroup hrp="buttom">
					<bf:formGroup col="12">
						<bc:label  cls="required" text="交易名称" col="2" formControl="true"/>
						<bf:text id="tranName" name="tranName" col="10" value="${traInterest.tranName}"
								 disabled="${opt == 'view'}">
						</bf:text>
					</bf:formGroup>
				</bf:formGroup>
				<bf:formGroup hrp="buttom">
					<bf:formGroup col="4">
						<bc:label  cls="required" text="发起方是否为控股子公司" col="5" formControl="true"/>
						<bf:select id="isHoldingSubsidiary" name="isHoldingSubsidiary" col="7"
								   value="${traInterest.isHoldingSubsidiary}" disabled="${opt == 'view'}"
								   dataSource="fixedDict" url="IsStatusDict">
							<be:event type="select">
								HoldingSubsidiarySub(e.target.value);
								isInRoad();
								<%--                                    isGreatTrade();--%>
								isConsiderType();
								isAgreement($('#isAgreement').val())
							</be:event>
						</bf:select>
					</bf:formGroup>
				</bf:formGroup>
				<bf:formGroup hrp="bottom">
					<bf:formGroup col="4" id="j">
						<div id="holdingSubsidiaryNameDiv" title="">
							<bc:label text="发起方公司全称" col="5" formControl="true"/>
							<bf:text id="holdingSubsidiaryName" name="holdingSubsidiaryName" col="7"
									 value="${traInterest.holdingSubsidiaryName}" disabled="${opt == 'view'}">
							</bf:text>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4" id="q">
						<div id="holdingSubsidiaryTypeDiv" title="">
							<bc:label text="发起方公司证件类型" col="5" formControl="true"/>
							<bf:select id="holdingSubsidiaryType" name="holdingSubsidiaryType" col="7"
									   value="${traInterest.holdingSubsidiaryType}"
									   dataSource="custom" url="sys/dictItem/getDictItems/BUS-ORGIdentifyType"
									   disabled="${opt == 'view'}"
							>
							</bf:select>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4" id="k">
						<div id="holdingSubsidiaryNumberDiv" title="">
							<bc:label text="发起方公司证件号码" col="5" formControl="true"/>
							<bf:text id="holdingSubsidiaryNumber" name="holdingSubsidiaryNumber" col="7"
									 value="${traInterest.holdingSubsidiaryNumber}" disabled="${opt == 'view'}">
								<%----%>
							</bf:text>
						</div>
					</bf:formGroup>
				</bf:formGroup>
			</bc:row>
			<bc:row cls="m-b">
				<bf:formGroup cls="group-butom-1" hrp="bottom" hr="dashed">
					<bf:formGroup col="11">
						<bc:label text="交易对手信息：" col="2" formControl="true" cls="text-style"/>
					</bf:formGroup>
					<bf:formGroup col="1">
						<bc:button id="addInfos"  text="添加">
							<be:event>
								addInfos()
							</be:event>
						</bc:button>
					</bf:formGroup>
				</bf:formGroup>
				<%--第二行--%>
				<bf:formGroup  id="formItems">

				</bf:formGroup>
			</bc:row>

			<bc:row cls="m-b">
				<bf:formGroup cls="group-butom-2" hrp="bottom" hr="dashed">
					<bf:formGroup col="10">
						<bc:label text="交易信息：" col="2" formControl="true" cls="text-style"/>
					</bf:formGroup>
					<bf:formGroup col="2">

					</bf:formGroup>
				</bf:formGroup>
				<bf:formGroup hrp="bottom">
					<bf:formGroup col="4">
						<div title="交易类型">
							<bc:label text="交易类型" col="5" formControl="true"/>
							<bf:select id="tranType" name="tranType" col="7" value="04"
									   dataSource="custom"
									   url="sys/dictItem/getDictItems/INS-tranType" disabled="true">
							</bf:select>
						</div>
					</bf:formGroup>

					<bf:formGroup col="4">
						<div title="业务类型">
							<bc:label cls="required" text="业务类型" col="5" formControl="true"/>
							<bf:select id="busType" name="busType" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/INT-BusinessType"
									   includes="" value="${traInterest.busType}" disabled="${opt == 'view'}">
								<be:event type="select">
									showOrHidden(e.target.value)
								</be:event>
								<%--<bf:option text="请选择" value="0"/>--%>
								<bf:option text="请选择" value=""></bf:option>

							</bf:select>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4">
						<div id="contractNo_Div" title="合同编号">
							<bc:label  text="合同/交易编号" col="5" formControl="true"/>
							<bf:text id="contractNo" name="contractNo" col="7" value="${traInterest.contractNo}"
									 disabled="${opt == 'view'}">

							</bf:text>
						</div>
					</bf:formGroup>
				</bf:formGroup>
			</bc:row>


			<bf:formGroup hrp="bottom">
				<bf:formGroup col="4">
					<bc:label col="5" cls="required" text="合同签署日期" formControl="true"/>
					<bf:datebox id="contractDate" name="contractDate" col="7"
								value="${traInterest.contractDateString}" disabled="${opt == 'view'}">
					</bf:datebox>
				</bf:formGroup>
				<bf:formGroup col="4">
					<div id="contraTotalDiv" title="合同金额">
						<bc:label col="5" text="合同金额" formControl="true"/>
						<bf:text col="7" id="contractAmount" name="contractAmount" value="${traInterest.contractAmount}"
								 disabled="${opt == 'view'}"/>
					</div>
				</bf:formGroup>
				<bf:formGroup col="4">
					<div title="关联交易金额（万元/人民币）">
						<bc:label  cls="required" text="关联交易金额（万元/人民币）" col="5" formControl="true"/>
						<bf:text id="relTradeAmount" name="relTradeAmount" col="7" change='isInRoad(),isGreatTrade(),isConsiderType();'
								 value="${traInterest.relTradeAmount}" disabled="${opt == 'view'}">

							<bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
						</bf:text>
					</div>
				</bf:formGroup>
			</bf:formGroup>

			<bf:formGroup hrp="bottom">
				<bf:formGroup col="4">
					<div title="关联交易发生的起始日期">
						<bc:label  text="业务起始日期" col="5" formControl="true"/>
						<bf:datebox id="busStartTime" name="busStartTime" col="7"
									value="${traInterest.busStartTimeString}" disabled="${opt == 'view'}">

						</bf:datebox>
					</div>
				</bf:formGroup>

				<bf:formGroup col="4">
					<div title="关联交易发生的结束日期">
						<bc:label text="业务终止日期" col="5" formControl="true"/>
						<bf:datebox id="busEndTime" name="busEndTime" col="7"
									value="${traInterest.busEndTimeString}" disabled="${opt == 'view'}">
							<%----%>
						</bf:datebox>
					</div>
				</bf:formGroup>

				<bf:formGroup col="4">
					<div title="本年度与该关联方累计交易金额（万元/人民币）包含：单笔重大+累计金额+免于审议">
						<bc:label   text="实际交易金额（万元）" col="5" formControl="true"/>
						<bf:text id="pracTradeAmount" name="pracTradeAmount" col="7"
								 value="${traInterest.pracTradeAmount}" disabled="${opt == 'view'}"
								 change='isInRoad(),isGreatTrade(),isConsiderType();' >
						</bf:text>
					</div>
				</bf:formGroup>
			</bf:formGroup>

			<bf:formGroup hrp="bottom">

				<bf:formGroup col="4">
					<div title="交易定价是否为国家规定">
						<bc:label  cls="required" text="交易定价是否为国家规定" col="5" formControl="true"/>
						<bf:select id="isDealPrice" name="isDealPrice" col="7" dataSource="fixedDict" url="IsStatusDict"
								   value="${traInterest.isDealPrice}" disabled="${opt == 'view'}">
							<be:event type="select">
								isConsiderType();
							</be:event>

						</bf:select>
					</div>
				</bf:formGroup>

				<bf:formGroup col="4">
					<div title="是否现金认购">
						<bc:label cls="required" text="是否现金认购" col="5" formControl="true"/>
						<bf:select id="isCashSubscription" name="isCashSubscription" col="7"
								   dataSource="fixedDict" url="IsStatusDict" value="${traInterest.isCashSubscription}"
								   disabled="${opt == 'view'}">
							<be:event type="select">
								isConsiderType();
							</be:event>
						</bf:select>

					</div>
				</bf:formGroup>
				<bf:formGroup col="4">
					<div title="是否为统一交易协议">
						<!-- <font color="red" size="5">*</font> -->
						<bc:label cls="required"  text="是否为统一交易协议" col="5" formControl="true"/>
						<bf:select id="isAgreement" name="isAgreement" col="7"
								   value="${traInterest.isAgreement}"
								   dataSource="fixedDict" url="IsStatusDict" disabled="${opt == 'view'}">
							<be:event type="select">
								isAgreement(e.target.value);
							</be:event>
						</bf:select>
					</div>
				</bf:formGroup>

			</bf:formGroup>
			<bf:formGroup hrp="bottom">
				<bf:formGroup col="4">
					<div id="isAgreementImplDiv" title="是否属于统一交易协议执行情况">
						<bc:label text="是否属于统一交易协议执行情况" col="5" formControl="true"/>
						<bf:select id="isAgreementImpl" name="isAgreementImpl" col="7"
								   value="${traInterest.isAgreementImpl}"
								   dataSource="fixedDict" url="IsStatusDict" disabled="${opt == 'view'}">
							<bf:option text="请选择" value=""></bf:option>
							<be:event type="select">
								isAgreementImpl(e.target.value);
							</be:event>
						</bf:select>
					</div>
				</bf:formGroup>
				<bf:formGroup col="4">
					<div id="agreementNumDiv" title="主协议编号">
						<bc:label text="主协议编号" col="5" formControl="true"/>
						<bf:text id="agreementNum" name="tradeAgreementNumber" col="7"
								 value="${traInterest.tradeAgreementNumber}" disabled="${opt == 'view'}"
						>
						</bf:text>
					</div>
				</bf:formGroup>
			</bf:formGroup>

			<bf:formGroup hrp="bottom">
				<bf:formGroup col="4">
					<div title="达到重大关联交易的情形">
						<bc:label  text="达到重大关联交易的情形" col="5" formControl="true"/>
						<bf:select id="majorType" name="majorType" col="7" dataSource="custom"
								   url="sys/dictItem/getDictItems/INS-majorType"
								   includes="" value="${traInterest.majorType}" disabled="true">
							<bf:option text="-" value=""></bf:option>
						</bf:select>
					</div>
				</bf:formGroup>
				<bf:formGroup col="4">
					<div title="是否重大关联交易">
						<bc:label text="是否重大关联交易" col="5" formControl="true"/>
						<bf:select id="majorTrade" name="majorTrade" col="7" dataSource="fixedDict"
								   url="IsStatusDict" value="${traInterest.majorTrade}" disabled="true">
							<bf:option text="-" value=""></bf:option>
						</bf:select>
					</div>
				</bf:formGroup>

				<bf:formGroup col="4">
					<div title="审议类型">
						<!-- <font color="red" size="5">*</font> -->
						<bc:label text="审议类型" col="5" formControl="true"/>
						<bf:select id="considerType" name="considerType" col="7" value="${traInterest.considerType}"
								   dataSource="custom" url="sys/dictItem/getDictItems/INS-considerType" disabled="true">

						</bf:select>
					</div>
				</bf:formGroup>


			</bf:formGroup>

			<bf:formGroup hrp="bottom">
				<bf:formGroup col="6">
					<div title="交易单价">
						<bc:label cls="required" text="交易价格" col="3" formControl="true"/>
						<bf:text id="tradePrice" name="tradePrice" col="9" rows="2" multiple="true"
								 value="${traInterest.tradePrice}" disabled="${opt == 'view'}"></bf:text>
					</div>
				</bf:formGroup>
				<bf:formGroup col="6">
					<div title="交易价格、收益率的定价依据">
						<bc:label  cls="required" text="定价政策" col="3" formControl="true"/>
						<bf:text id="pricingPolicy" name="pricingPolicy" col="9" rows="2" multiple="true"
								 value="${traInterest.pricingPolicy}"
								 disabled="${opt == 'view'}"> </bf:text>
					</div>
				</bf:formGroup>
				<bf:formGroup col="6">
					<div title="与关联方之间关联交易的对象">
						<bc:label  cls="required"  text="交易标的" col="3" formControl="true"/>
						<bf:text id="tradeSubject" name="tradeSubject" col="9" rows="2" multiple="true" value="${traInterest.tradeSubject}" disabled="${opt == 'view'}"></bf:text>
					</div>
				</bf:formGroup>
				<bf:formGroup col="6">
					<div title="对公司的财务状况及经营成果的描述">
						<bc:label  text="对财务状况及经营成果的影响" col="3" formControl="true"/>
						<bf:text id="financialImpact" name="financialImpact" col="9" rows="2" multiple="true" value="${traInterest.financialImpact}" disabled="${opt == 'view'}"></bf:text>
					</div>
				</bf:formGroup>
				<bf:formGroup col="6">
					<div id="tradeSubjectAssetsDiv" title="关联方之间发生关联交易的标的或其资产情况。" id="1_3" class="Type1" style="display: none">
						<bc:label  text="标的资产情况" col="3" formControl="true"/>
						<bf:text id="tradeSubjectAssets" name="tradeSubjectAssets" col="9" rows="2" multiple="true"
								 value="${traInterest.tradeSubjectAssets}"
								 disabled="${opt == 'view'}"></bf:text>
					</div>
				</bf:formGroup>
				<bf:formGroup col="6">
					<div id="lastTradePriceDiv" title="前次交易价格" id="1_2" class="Type1" style="display: none">
						<bc:label  text="前次交易价格" col="3" formControl="true"/>
						<bf:text id="lastTradePrice" name="lastTradePrice" col="9" rows="2" multiple="true"
								 value="${traInterest.lastTradePrice}"
								 disabled="${opt == 'view'}"></bf:text>
					</div>
				</bf:formGroup>
				<bf:formGroup col="6">
					<div title="标的情况" id="3_1" class="Type3" style="display: none">
						<bc:label  text="标的情况" col="3" formControl="true"/>
						<bf:text id="subjectSituation" name="subjectSituation" col="9" rows="2" multiple="true"
								 value="${traInterest.subjectSituation}"
								 disabled="${opt == 'view'}"></bf:text>
					</div>
				</bf:formGroup>
				<bf:formGroup col="6">
					<div id="guaranteeConditionsDiv" title="与关联方之间提供担保时的担保条件。" id="2_1" class="Type2" style="display: none">
						<bc:label  text="担保条件" col="3" formControl="true"/>
						<bf:text id="guaranteeConditions" name="guaranteeConditions" col="9" rows="2" multiple="true"
								 value="${traInterest.guaranteeConditions}"
								 disabled="${opt == 'view'}"></bf:text>
					</div>
				</bf:formGroup>
				<bf:formGroup col="6">
					<div title="除上述关键信息外，其他需要说明的重要或特殊的事项">
						<bc:label  cls="required" text="其他事项说明" col="3" formControl="true"/>
						<bf:text id="remark" name="remark" col="9" rows="2" multiple="true"
								 value="${traInterest.remark}" disabled="${opt == 'view'}"> </bf:text>
					</div>
				</bf:formGroup>
			</bf:formGroup>

			<%--<bf:formGroup hrp="bottom">
                <bf:formGroup col="6">
                    <div title="其他事项说明" id="1_3" class="Type6" style="display: block">
                        <bc:label  text="其他事项说明" col="3" formControl="true"/>
                        <bf:text id="remark" name="remark" col="9" rows="2"
                                 multiple="true" value="${traInterest.remark}" disabled="${opt == 'view'}">
                        </bf:text>
                    </div>
                </bf:formGroup>
            </bf:formGroup>--%>

			<div id="tableDiv" style="width:95%;margin:0 auto">
				<bc:table id="docTable" idField="pid" url="${base}/ins/traInsenclosure" multiple="true"
						  selectFirst="false" queryUrl="queryList?transNum=${traInterest.transNum}">
					<bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
					<bc:toolbar id="downFile" text="下载" type="button" handler="downFileOnclick"/>
					<bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>
					<bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
					<bc:column field="pid" title="主键" width="100" visible="false"/>
					<bc:column field="relatedName" title="文件名" width="100"/>
					<bc:column field="fileType" title="文件类型" width="100"/>
					<bc:column field="fileSize" title="大小" width="100"/>
					<bc:column field="uploadOrg" title="上传机构" width="100"
							   formatter="custom:sys/unit/selectUnit"/>
					<bc:column field="uploadDept" title="上传部门" width="100"/>
					<bc:column field="uploadTime" title="上传时间" width="100"/>
				</bc:table>
			</div>
			<bf:formGroup>
				<div class="col-sm-4 col-sm-offset-5">
					<bc:button text="返回" icon="arrow-circle-left">
						<be:event>
							<%--                            window.location = '${base}/ins/traBusiness/list';--%>
							window.history.back(-1);
						</be:event>
					</bc:button>
				</div>
			</bf:formGroup>
		</bf:form>
	</bc:ibox>
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
					<div style="text-align: center" id="loading"></div>
					<form method="post" enctype="multipart/form-data" id="uploadForm"
						  style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
						<div class="form-group" style="text-align: center;vertical-align: middle;">
							<label for="file"></label>
							<img src="${base}/static/img/sendfile.png"/>
							<a href="javascript:;" class="file">
								<input type="file" class="form-control" id="file" name="file" multiple
									   style="padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
							</a>
						</div>

					</form>
					<div class="form-group"
						 style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
						<button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
						<button type="submit" class="btn btn-primary" id="upload">上传</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>

<script type="text/javascript">
	var traSubName='86';

	//记录添加个数；按照添加点击事件累加
	var count = 0;

	//记录页面具体添加个数，按照添加 删除按钮 动态变化
	var countTmp = 0;

	function checkFile () {
		// 0 : 是 1：否
		var name = $("#holdingSubsidiaryName").val() == null ||  $("#holdingSubsidiaryName").val() == "" ? 'no' : $("#holdingSubsidiaryName").val();
		var number = $("#holdingSubsidiaryNumber").val() == null || $("#holdingSubsidiaryNumber").val() == "" ? 'no' : $("#holdingSubsidiaryNumber").val();
		var isHoldingSubsidiary = $("#isHoldingSubsidiary").val();
		if (isHoldingSubsidiary == '0' && (name == 'no' || number == 'no')) {
			$.messager.alert('提示', '控股子公司全称和控股子公司证件号码必填', 'warning');
			return false;
		} else if (isHoldingSubsidiary == '1' && (name != 'no' || number != 'no')) {
			$.messager.alert('提示', '控股子公司全称和控股子公司证件号码需要置空', 'warning');
			return false;
		}
	}

	function checkSaves() {
		var  holdName=$('#holdingSubsidiaryName').val();
		var holdType=$('#holdingSubsidiaryType').val();
		var holdNum=$('#holdingSubsidiaryNumber').val();
		var isAgreementImpl= $('#isAgreementImpl').val();
		var agreementNum= $('#agreementNum').val();

		// $("#save2").attr("disabled", true);
		if($('#isHoldingSubsidiary').val=='0'&&
				(holdName == null && holdName == undefined && holdName == '')||
				(holdType == null && holdType == undefined && holdType == '')||
				(holdNum == null && holdNum == undefined && holdNum == '')

		){
			$.messager.alert('错误', "是控股子公司，控股子公司全称、证件类型、证件号码为必填项", 'error');
			return;
		}
		if($('#isAgreement').val=='0'&&
				(isAgreementImpl == null && isAgreementImpl == undefined && isAgreementImpl== '')||
				(agreementNum == null && agreementNum == undefined && agreementNum == '')

		){
			$.messager.alert('错误', "是统一交易协议，属于统一交易协议执行情况、主协议编号为必填项", 'error');
			return;
		}
		$("#save2").attr("disabled", true);
		checkFile ();
		var docInnerPids = getAllIdSelections();
		var formData = $('#editForm').serializeAll();
		$('#editForm').data('formValidation').validate();
		var isValid = $('#editForm').data('formValidation').isValid();

		if (isValid) {
			$.mask.post('${base}/ins/traInterest/quotaCheck', formData,function (data) {
				if (data.success) {
					var docTableSelectItem = {
						'name': 'docTableSelectItem',
						'value': docInnerPids
					};
					formData.push(docTableSelectItem);
					$.mask.post('${base}/ins/traInterest/traInterestCheck', formData,function (data) {
						if (data.success) {
							var formData1 = $('#editForm').serializeAll();
							var relInfs = [];

							for(var i = 1; i <=count; i++){
								var relNumberTmp = $("#relNumber"+i).val();
								//过滤 i 等于已经删除的item
								if(relNumberTmp === undefined || relNumberTmp === ''){
									continue
								}
								var relItemInfo = {
									"relId":"",
									"relNumber":"",
									"relType":"",
									"relName":"",
									"totolTradeAmount":"",
									"tranOpponent":"",
									"certType":"",
									// "assetsLastQuarter":"",
									"tradeAmount":"",
									"pid":""
								}

								relItemInfo.relId = $("#relId"+i).val();
								relItemInfo.relNumber = $("#relNumber"+i).val();
								relItemInfo.relType = $("#relType"+i).val();
								relItemInfo.relName = $("#relName"+i).val();
								relItemInfo.totolTradeAmount = $("#totolTradeAmount"+i).val();
								// relItemInfo.assetsLastQuarter = $("#assetsLastQuarter"+i).val();
								relItemInfo.tradeAmount = $("#tradeAmount"+i).val();
								relItemInfo.tranOpponent = $("#tranOpponent"+i).val();
								relItemInfo.certType = $("#certType"+i).val();
								relItemInfo.pid = $("#pid"+i).val();
								relInfs.push(relItemInfo)
							}
							var relInfoDatas = {
								'name':"relInfoListStr",
								'value': JSON.stringify(relInfs)
							}
							var docTableSelectItem = {
								'name': 'docTableSelectItem',
								'value': docInnerPids
							};
							formData1.push(docTableSelectItem);
							formData1.push(relInfoDatas);
							$.mask.post('${base}/ins/traInterest/save', formData1, function (data) {
								if (data.success) {
									$.messager.alert('提示', data.msg, 'info', function () {
										window.location = '${base}/ins/traInterest/list';
									});
								}else {
									$.messager.alert('错误', data.msg, 'error');
									$("#save2").attr("disabled", false);
								}
							});
						} else {
							//合规检测有不通过的，需要提示用户 是否继续交易
							$.messager.confirm('提示', data.msg, function (r) {
								if (r) {
									$('#editForm').val(data.code);
									var formData2 = $('#editForm').serializeAll();

									var relInfs = [];

									for(var i = 1; i <=count; i++){
										var relNumberTmp = $("#relNumber"+i).val();
										//过滤 i 等于已经删除的item
										if(relNumberTmp === undefined || relNumberTmp === ''){
											continue
										}
										var relItemInfo = {
											"relId":"",
											"relNumber":"",
											"relType":"",
											"relName":"",
											"totolTradeAmount":"",
											"tranOpponent":"",
											"certType":"",
											// "assetsLastQuarter":"",
											"tradeAmount":"",
											"pid":""
										}

										relItemInfo.relId = $("#relId"+i).val();
										relItemInfo.relNumber = $("#relNumber"+i).val();
										relItemInfo.relType = $("#relType"+i).val();
										relItemInfo.relName = $("#relName"+i).val();
										relItemInfo.totolTradeAmount = $("#totolTradeAmount"+i).val();
										// relItemInfo.assetsLastQuarter = $("#assetsLastQuarter"+i).val();
										relItemInfo.tradeAmount = $("#tradeAmount"+i).val();
										relItemInfo.tranOpponent = $("#tranOpponent"+i).val();
										relItemInfo.certType = $("#certType"+i).val();
										relItemInfo.pid = $("#pid"+i).val();
										relInfs.push(relItemInfo)
									}
									var relInfoDatas = {
										'name':"relInfoListStr",
										'value': JSON.stringify(relInfs)
									}
									var docTableSelectItem = {
										'name': 'docTableSelectItem',
										'value': docInnerPids
									};
									formData2.push(docTableSelectItem);
									formData2.push(relInfoDatas);
									$.mask.post('${base}/ins/traInterest/save', formData2, function (data) {
										if (data.success) {
											$.messager.alert('提示', data.msg, 'info', function () {
												window.location = '${base}/ins/traInterest/list';
											});
										}else {
											$.messager.alert('错误', data.msg, 'error');
											$("#save2").attr("disabled", false);
										}
									});
								}
							});
						}
					});
				}else {
					$.messager.alert('错误', data.msg, 'error');
					$("#save2").attr("disabled", false);
				}
			});
		}
	}

	//合同抽取待补录合同/交易发生日期不必填
	function notEmpt(value, validator, $field) {
		var fieId1 = $field[0].id;
		var value1 = value;
		var dataSources = '${traInterest.dataSources}';
		//生效时间
		if (fieId1 == "") {
			if ((dataSources != "3") && (value1 == null || value1 == "")) {
				return false;
			} else {
				return true;
			}
		}
	}

	//根据业务类型显示对应字段
	function showOrHidden(value) {
		/* var val = $('#busType option:selected').val();//选中的值*/
		if (value == '1' ) {
			$(".Type1").css("display", "block");
		} else {
			$(".Type1").css("display", "none");
		}
		if(value == '2'){
			$(".Type1").css("display", "none");
		}else{
			$(".Type1").css("display", "block");
		}

		if (value == '4') {
			$(".Type2").css("display", "block");
			$(".Type6").css("display", "none");
		} else {
			$(".Type2").css("display", "none");
		}

		if (value == '3' || value == '4' || value == '8' || value == '9' || value == '10') {
			$(".Type3").css("display", "block");
			$(".Type6").css("display", "none");
		} else {
			$(".Type3").css("display", "none");
			$(".Type6").css("display", "block");
		}
	}


	//查询交易对手相关方法
	function relNameSelect(relType, relId, relName1, totolTradeAmount, relNumber, certTypes, certType, tradeAmount) {


		$.messager.modal({
			winId: 'relNameSelect',
			width: 700,
			height: 420,
			title: '交易对手信息',
			url: '${base}/ins/traInvestment/relNameSelect/'+ traSubName,
			args: {
				result: function (obj) {
					$("#"+relId.id).val(obj["relId"]);
					$("#"+relName1.id).val(obj["relName"]);
					$("#"+totolTradeAmount.id).val(obj["totolTradeAmount"]);
					$("#" + tradeAmount.id).val(obj["tradeZeroAmount"]);
					$('#total').val(obj["totolTradeAmount"]);
					$("#"+relType.id).val(obj["relType"]);
					$("#"+relNumber.id).val(obj["relNumber"]);
					$("#tradeZeroAmount").val(obj["tradeZeroAmount"]);
					if (obj["identifyTypePSN"] != '') {
						$.ajax({
							url: "${base}/sys/dictItem/getDictItems/BUS-IdentifyType",
							success: function (data) {
								for (var i = 0; i < data.length; i++) {
									if (obj["identifyTypePSN"] == data[i].value) {
										$("#"+certType.id).val(data[i].value);
										$("#"+certTypes.id).val(data[i].name);
									}
								}
							}
						});
					} else {
						$.ajax({
							url: "${base}/sys/dictItem/getDictItems/BUS-ORGIdentifyType",
							success: function (data) {
								for (var i = 0; i < data.length; i++) {
									if (obj["identifyTypeORG"] == data[i].value) {
										$("#"+certType.id).val(data[i].value);
										$("#"+certTypes.id).val(data[i].name);
									}
								}
							}
						});
					}


				}
			},
			onClose: function () {
			}
		});

	}



	//跟据审议类型规则给定审议类型得值
	function isConsiderType() {
		//是否为国家定价
		var isDealPrice = $('#isDealPrice').val();
		//是否为现金认购
		var isCashSubscription = $('#isCashSubscription').val();
		//是否为重大关联交易
		var isMajorTrade = $('#majorTrade').val();
		//合同金额
		var relTradeAmount = $('#relTradeAmount').val();
		//关联方类型
		var relType = $('#relType1').val();

		//如果是现金交易审议类型为免予审议
		if (isCashSubscription != null && isCashSubscription != undefined && isCashSubscription != '' && isCashSubscription == "0") {
			//如果已经是逐笔审议类型  则不变 逐笔＞免于审议
			//如果已经是逐笔审议类型  则不变 逐笔＞免于审议
			if(($('#isAgreement').val()=='0'&&$("#isAgreementImpl").val()=='1')|| isMajorTrade=='0' && $('#considerType').val()=='3'){
				return;
			}
			$('#considerType').val('1');
			$('#considerType').select2({value: '1'});
			reRedStar();
			return;
		} else {
			//如果已经是逐笔审议类型  则不变 逐笔＞免于审议
			if(($('#isAgreement').val()=='0'&&$("#isAgreementImpl").val()=='1')|| isMajorTrade=='0' && $('#considerType').val()=='3'){
				return;
			}
			//如果是重大关联交易审议类型为逐笔审议
			if (isMajorTrade != null && isMajorTrade != undefined && isMajorTrade != '') {
				if (isMajorTrade == "0") {
					$('#considerType').val("3");
					$('#considerType').select2({value: "3"});
					addRedStar();
					return;
				}
			}
			//当关联方为个人合同金额<=50w  机构合同金额<=500w的时候免于审议
			if (relTradeAmount != null && relTradeAmount != undefined && relTradeAmount != ''
					&& relType != null && relType != undefined && relType != '') {
				if (relType == "PSN" && relTradeAmount <= 50) {
					$('#considerType').val("1");
					$('#considerType').select2({value: "1"});
					reRedStar();
					return;
				}
				if (relType == "ORG" && relTradeAmount <= 500) {
					$('#considerType').val("1");
					$('#considerType').select2({value: "1"});
					reRedStar();
					return;
				}
			}

			//如果是国家定价重大关联交易审议类型为免予审议
			if (isDealPrice != null && isDealPrice != undefined && isDealPrice != '') {
				if (isDealPrice == "0") {
					$('#considerType').val("1");
					$('#considerType').select2({value: "1"});
					reRedStar();
					return;
				}
			}
		}
		$('#considerType').val("2");
		$('#considerType').select2({value: "2"});
		reRedStar();
	}


	//判断是否为控股子公司来决定子公司名称证件号能不能添加
	function HoldingSubsidiarySub(value) {
		debugger;
		var value = value
		if (value == '1') {
			$('#holdingSubsidiaryNumber').val("");
			$('#holdingSubsidiaryName').val("");
			$('#holdingSubsidiaryType').val("");
			$('#holdingSubsidiaryNumberDiv').css('display', 'none');
			$('#holdingSubsidiaryNameDiv').css('display', 'none');
			$('#holdingSubsidiaryTypeDiv').css('display', 'none');
			$('#holdingSubsidiaryNumberDiv label font').remove();
			$('#holdingSubsidiaryNameDiv label font ').remove();
			$('#holdingSubsidiaryTypeDiv label font ').remove();


		}
		if (value == '0') {
			$('#holdingSubsidiaryNumberDiv').css('display', 'block')
			$('#holdingSubsidiaryNameDiv').css('display', 'block')
			$('#holdingSubsidiaryTypeDiv').css('display', 'block')
			if ($('#holdingSubsidiaryNumberDiv label font').length == 0) {
				$('#holdingSubsidiaryNumberDiv label').prepend("<font color='red'>✶</font>");
				$('#holdingSubsidiaryNameDiv label').prepend("<font color='red'>✶</font>");
				$('#holdingSubsidiaryTypeDiv label').prepend("<font color='red'>✶</font>");
			}

		}

	};

	//根据所选业务类型判断一些字段是否为必填
	function notEm(value, validator, $field) {
		var fieId1 = $field[0].id;
		var value1 = value;
		var busType = $('#busType').val();
		var unitfTradeAgrmentInterest = $('#unitfTradeAgrmentInterest').val();
		//交易标的
		if (fieId1 == "tradeSubject") {
			if ((busType == "240" || busType == "244"
					|| busType == "245" || busType == "246") && (value1 == null || value1 == "")) {
				return false;
			} else {
				return true;
			}
		}
		//前次交易价格
		if (fieId1 == "lastTradePrice") {
			if ((busType == "237" || busType == "238") && (value1 == null || value1 == "")) {
				return false;
			} else {
				return true;
			}
		}
		//是否为统一交易协议
		if (fieId1 == "isAgreement") {
			debugger;
			if ((unitfTradeAgrmentInterest == "01") && (value1 == null || value1 == " ")) {
				return false;
			} else {
				return true;
			}
		}
		//标的资产情况
		if (fieId1 == "tradeSubjectAssets") {
			if ((busType == "237" || busType == "238" || busType == "239") && (value1 == null || value1 == "")) {
				return false;
			} else {
				return true;
			}
		}
		return true;
	}

	var opt = '${opt}';
	var unitName='${unitName}';
	$(document).ready(function () {
		if(unitName!=null&&unitName!=''&&unitName!=undefined){
			$("#managerOrg").val(unitName);
		}
		if ('${isConfirm}' == 'true') {
			$("#save2").css("display", "none");
			$("#docTable_tb_exportIn").css("display", "none");
			$("#docTable_tb_removeDoc").css("display", "none");
			//     $("#confirm").css("display","block")
			// }else{
			//     $("#confirm").css("display","none")
		}
		$(".required").each(function () {
			var $this = $(this);
			$(this).html("<font color='red'>✶</font>" + $this.html());
		});
		var relList = ${traInterest.relInfoListStr};
		if (relList.length == 0) {
			$("#addInfos").trigger("click");
		}else{
			for(var i = 0;i<relList.length;i ++){
				addInfos(relList[i]);
			}
		}
		if($('#considerType').val()==3){
			addRedStar()
		}
		if (opt == 'view') {
			$("#save2").css("display", "none");
			var num=$('#formItems div').length;
			for(var i=1;i<=num;i++){
				$("#"+"tranOpponent"+i).attr("disabled",true);
				$("#"+"certTypes"+i).attr("disabled",true);
				$("#"+"relName"+i).attr("disabled",true);
				$("#"+"relNumber"+i).attr("disabled",true);
				$("#addInfos").hide();
				$(".btn-danger1").hide();
			}
			if($('#isHoldingSubsidiary').val()=='1'){
				HoldingSubsidiarySub('1');
			}
			if($('#isAgreement').val()=='1'){
				isAgreement('1');
			}
		} else {
			var val = $('#isHoldingSubsidiary').val();
			if (val != null && val != undefined && val != '') {
				HoldingSubsidiarySub(val)
			}
			var val1 = $('#isAgreement').val();
			if (val1 != null && val1 != undefined && val1 != '') {
				isAgreement(val1);
			}
		}

		$('#tranType').val("04");
		$('#tranType').select2({value: "04"});
		var busType = $('#busType').val();
		showOrHidden(busType)

	});

	function onSelect() {
		var proName = $("input[name=proName]").val();
		$.ajax({
			type: "post",
			url: "${base}/ins/traInterest/selectbuinessL",
			data: {"unitCodes": proName},
			success: function (data) {
				if (data.success) {
					var busType = data.busType;
					$('#busType').val(busType);
					$('#busType').select2({value: busType});
				} else {
					$.messager.alert('提示', data.msg, 'error');
				}
			}
		});
	}

	function toConfirm() {
		var pid = ${pid}
				$.ajax({
					url: "${base}/ins/traInterest/confirm/" + pid,
					method: "GET",
					dataType: "JSON",
					traditional: true,
					success: function (data) {
						if (data.success) {
							window.location = '${base}/ins/traInterest/list/';
						} else {
							$.messager.alert('错误', data.msg, 'error');
						}
					}
				})
	}

	function exportIn() {
		$("#hiddenBtn").click();
	}

	function getIdSelections() {
		return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
			return row.pid//返回数据行中的id值
		});
	}

	function getAllIdSelections() {
		return $.map($('#docTable').bootstrapTable('getData'), function (row) {
			return row.pid//返回数据行中的id值
		});
	}

	//文件表所有选中的数据
	function getselectData() {
		return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
			return row//返回数据行中的id值
		});
	}

	//查看文件
	viewFile = function () {
		//选中的文件
		var pid = getIdSelections();
		if (pid == '' || pid == undefined) {
			alert("请选中一个文件");
			return;
		}
		if (pid.length > 1) {
			alert("只能选中一个文件");
			return;
		}
		//获得该条数据
		var row = getselectData();
		var fileName = row[0].fileName;
		window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
				+ encodeURIComponent('${base}/ins/traInsenclosure/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
	}

	//移除文件
	removeDoc = function () {
		var rows = getData();//删除前数据
		var pids = getIdSelections();//删除数据主键
		var selectRows = getselectData();//选中的数据
		if (selectRows.length == 0) {
			alert("请先选中数据");
			return;
		}
		if (confirm("确认删除？")) {
			$.ajax({
				type: 'get',
				url: '${base}/ins/traInsenclosure/removeFile/' + pids,
				dataType: 'json',
				success: function (res) {
					alert(res.msg);
					if (res.success) {
						//从数据中移除
						for (var i = 0; i < selectRows.length; i++) {
							var row = selectRows[i];
							rows.splice(rows.indexOf(row), 1);
						}
						var showData = {'total': rows.length, 'rows': rows};
						$('#docTable').bootstrapTable('load', showData);
					}
				}

			})
		}
	}

	$("#upload").click(function () {
		var obj = document.getElementById("file");
		for (var i = 0; i < obj.files.length; i++) {
			var temp = obj.files[i].name;
			var temArr = temp.split('.');
			var fileLast = temArr[temArr.length - 1];
			if (fileLast != 'pdf') {
				alert("请上传pdf文件");
				return;
			}
		}
		var formData = new FormData($('#uploadForm')[0]);
		$.ajax({
			type: 'post',
			url: '${base}/ins/traInsenclosure/uploadFiles?businessType=04', //上传文件的请求路径必须是绝对路劲
			data: formData,
			cache: false,
			processData: false,
			contentType: false,
			beforeSend: function (XMLHttpRequest) {
				//alert('远程调用开始...');
				$("#loading").html("<p style=\"color: dodgerblue\">正在上传，请稍后操作...</p>");
			},
			success: function (data) {
				$("#close").click();
				alert(data.msg);
				if (data.success) {
					//处理数据
					var files = data.traEnclosure;
					var count = files.length;
					//获取原有表单的数据
					var rows = getData();
					rows = rows.concat(files);
					//构造表格显示数据
					var total = rows.length;
					var showData = {'total': total, 'rows': rows}
					$('#docTable').bootstrapTable('load', showData);
					$("#loading").empty();
				}
			},
			error: function () {
				$("#close").click();
				alert("上传失败,请重试");
				$("#loading").empty();
			}
		})
	});

	//文件表数据
	function getData() {
		return $.map($('#docTable').bootstrapTable('getData'), function (row) {
			return row
		});
	}

	/*function changeNext(val) {
        if (val == '0') {
            $("#approvalNumDiv").css("display", "none")
            $("#securitiesTypeDiv").css("display", "block")
        } else if (val == '1') {
            $("#approvalNumDiv").css("display", "block")
            $("#securitiesTypeDiv").css("display", "none")
        }
    }*/

	$("#relNumber").blur(function () {
		var relNumber = $("#relNumber").val();
		var relName = $("#relName").val();
		if ((relNumber !== '' && relNumber !== undefined) || (relName !== '' && relName !== undefined)) {
			infoBlur(relNumber, relName);
		}
	});

	//判断是否重大
	$("#tradeAmount").blur(function () {
		var tradeAmount = $("#tradeAmount").val();
		var relId = $("#relId").val();
		if ((tradeAmount !== '' && tradeAmount !== undefined) || (relId !== '' && relId !== undefined)) {
			// infoBlur(relNumber,relName);
		}
	});

	function infoBlur(relNumber, relName) {
		$.ajax({
			type: 'GET',
			url: '${base}/ins/traInvestment/selectRel',
			data: {
				"relName": relName,
				"identifyNumber": relNumber
			},
			dataType: 'json',
			success: function (data) {
				$("#relNumber").empty();
				if (data.success) {
					$("#relId").val(data.relId);
					$("#relName").val(data.relName);
					$("#totolTradeAmount").val(data.totalTradeAmount);
					$('#total').val(data.totalTradeAmount);
					$("#caliber").val(data.caliber);
					$("#caliber").select2({value: (data.caliber)});
					$("#relType").val(data.relType);
					$("#relType").select2({value: (data.relType)});
					// $("#majorTrade").val(majorTrade);
					// $("#majorTrade").select2({value:(majorTrade)});
				} else {
					$.messager.alert('提示', '关联方不存在！', 'warning');
				}
			}
		});
	}

	//判断当前关联方是否拥有在途数据
	function isInRoad() {
		var traSubName = $("#traSubName").val();
		// if (traSubName != '0001' && traSubName != '0001-ZZHK') {
		//     return;
		// }
		var relId = $('#relId').val();
		var totolTradeAmount = $('#total').val();
		if (traSubName == '' || traSubName == null) {
			return;
		}
		if (relId == '' || relId == null) {
			return;
		}
		if (totolTradeAmount == '' || totolTradeAmount == null) {
			return;
		}
		var obj = {
			relId: relId,
			traSubName: traSubName,
			totolTradeAmount: totolTradeAmount
		};
		$.ajax({
			url: '${base}/bus/relPsn/queryRelNameAmount',
			data: obj,
			type: 'post',
			jsonType: 'json',
			success: function (data) {
				if (data.success) {
					var inRoldAmount = data.inRoldAmount;
					var amount = data.amount;
					if (amount != 0) {
						$.messager.alert('在途交易金额提示', inRoldAmount, 'warning');
					}
				}
			}
		});
	}

	//判断是否重大关联交易
	function isGreatTrade() {
		debugger;
		var list = new Array();
		var falg;
		var relInfs = [];
		for (var i = 1; i <= $("#formItems div").length; i++) {
			//委托机构不判断是否重大
			// var traSubName = $("#traSubName").val();
			var relNumberTmp = $("#relNumber" + i).val();
			//过滤 i 等于已经删除的item
			if (relNumberTmp === undefined || relNumberTmp === '') {
				continue
			}
			/**封装关联交易对手信息*/
			var relItemInfo = {
				"relId": "",
				"relNumber": "",
				"relType": "",
				"relName": "",
				"totolTradeAmount": "",
				"certType": "",
				"tranOpponent": "",
				// "assetsLastQuarter": "",
				"tradeAmount": "",
				"pid": ""
			}
			relItemInfo.relId = $("#relId" + i).val();
			relItemInfo.relNumber = $("#relNumber" + i).val();
			relItemInfo.relType = $("#relType" + i).val();
			relItemInfo.relName = $("#relName" + i).val();
			relItemInfo.totolTradeAmount = $("#totolTradeAmount" + i).val();
			relItemInfo.certType = $("#certType" + i).val();
			relItemInfo.tranOpponent = $("#tranOpponent" + i).val();
			// relItemInfo.assetsLastQuarter = $("#assetsLastQuarter" + i).val();
			relItemInfo.tradeAmount = $("#tradeAmount" + i).val();
			relItemInfo.pid = $("#pid" + i).val();
			relInfs.push(relItemInfo)
		}
		/**合同交易金额*/
		var relTradeAmount = $('#relTradeAmount').val();
		if (relTradeAmount == '' || relTradeAmount == null) {
			return;
		}
		/**实际交易金额*/
		var pracTradeAmount = $('#pracTradeAmount').val();
		/**调用重大校验接口*/
		var obj = {
			relTradeAmount: relTradeAmount,
			pracTradeAmount:pracTradeAmount,
			relInfoListStr:JSON.stringify(relInfs)
		};
		$.ajax({
			url: '${base}/ins/traInterest/GreatCheck',
			data: obj,
			type: 'post',
			async: false,
			jsonType: 'json',
			success: function (data) {
				if (data.success) {
					var notMajorRelNumber = data.notMajorRelNumber;
					/**替换值*/
					console.log(data)
					$('#notMajorRelNumber').val(notMajorRelNumber);
					list.push(data);
				} else {
					$.message.alert(data.msg);
				}
			}
		});
		console.log(list);
		$('#majorTrade').val(list[0].isGreatTrade);
		$('#majorTrade').select2({value: (list[0].isGreatTrade)})
		if (list[0].isGreatTrade == '0') {
			$('#majorType').val(list[0].majorType);
			$('#majorType').select2({value: (list[0].majorType)})
			isConsiderType();
			return false;
		} else {
			$('#majorType').val(" ");
			$('#majorType').select2({value: (" ")})
			isConsiderType();
		}
	}

	//文件下载
	function downFileOnclick() {
		//选中的文件
		var pids = getIdSelections();
		if (pids == '' || pids == undefined) {
			$.messager.alert('错误', "请选择需要下载的文件!", 'error');
			return;
		}
		var url = '${base}/ins/traInsenclosure/downLoadFile/' + pids;
		var elemIF = document.createElement("iframe");
		elemIF.src = url;
		elemIF.style.display = "none";
		document.body.appendChild(elemIF);
	}

	function addInfos(relInfo) {
		count++;
		countTmp++;
		var item = "itme" + count
		var tranOpponent = "tranOpponent" + count;
		var certType = "certType" + count;
		var certTypes = "certTypes" + count;
		var relName = "relName" + count;
		var relNumber = "relNumber" + count;
		var totolTradeAmount = "totolTradeAmount" + count;
		var assetsLastQuarter = "assetsLastQuarter" + count;
		var tradeAmount = "tradeAmount" + count;
		var relId = "relId" + count;
		var relType="relType"+count;
		var pid = "pid" + count;
		//formItems
		var itemStr = ' <div class="form-group " id="' + item + '" >\n' +
				'   <div class="form-group ">\n' +
				'       <div class="form-group  col-xs-6">\n' +
				'           <label  class="control-label col-xs-4 "><font color="red">✶</font>交易对手名称</label>\n' +
				'           <div class="  col-xs-8">\n' +
				'               <input type="text" data-fv-notempty="true"  class="jep-input form-control" id="' + tranOpponent + '" value = "';
		if(relInfo != undefined && relInfo !=null && relInfo != ""){
			itemStr = itemStr + relInfo.tranOpponent;
		}//onblur="initRelInfo(\'' + relNumber + '\',\'' + relName + '\',\'' + totolTradeAmount + '\',\'' + relType +'\',\''+assetsLastQuarter + '\')"
		itemStr = itemStr + '"  >\n' +
				'           </div>\n' +
				'       </div>\n' +
				'       <div class="form-group  col-xs-5">\n' +
				'           <label  class="control-label col-xs-5">关联方证件类型</label>\n' +
				'           <div class="col-xs-7">\n' +
				'               <input  type="text" data-fv-notempty="true"  class="jep-input form-control"  id="' + certTypes + '" value = "';
		if(relInfo != undefined && relInfo !=null && relInfo != ""){
			if (relInfo.relType == 'PSN') {
				$.ajax({
					url: "${base}/sys/dictItem/getDictItems/BUS-IdentifyType",
					async:false,
					success: function (data) {

						for (var i = 0; i < data.length; i++) {
							if (relInfo.certType == data[i].value) {
								itemStr = itemStr + data[i].name;

							}
						}
					}
				});
			} else if(relInfo.relType =='ORG') {
				$.ajax({
					url: "${base}/sys/dictItem/getDictItems/BUS-ORGIdentifyType",
					async:false,
					success: function (data) {

						for (var i = 0; i < data.length; i++) {
							if (relInfo.certType == data[i].value) {
								itemStr = itemStr + data[i].name;
							}
						}
					}
				});
			}
		}
		itemStr = itemStr + '">\n' +
				'<input   type="hidden" data-fv-notempty="true"  class="jep-input form-control"  id="' + certType + '" value = "';
		if (relInfo != undefined && relInfo != null && relInfo != "") {
			itemStr = itemStr + relInfo.certType;
		}
		itemStr = itemStr + '">\n' +
				'           </div>\n' +
				'       </div>\n' +
				'       <div class="form-group  col-xs-1"></div>\n' +
				'   </div>\n' +
				'   <div class="form-group ">\n' +
				'       <div class="form-group  col-xs-6">\n' +
				'           <label  class="control-label col-xs-4">关联方名称</label>\n' +
				'           <div class="  col-xs-8">\n' +
				'           <input type="text" onclick="relNameSelect(' + relType + ',' + relId + ',' + relName + ',' + totolTradeAmount + ',' + relNumber + ',' + certTypes + ',' + certType +','+ tradeAmount + ')" class="jep-input form-control " id="' + relName + '" value = "';
		if(relInfo != undefined && relInfo !=null && relInfo != ""){
			itemStr = itemStr + relInfo.relName;
		}
		itemStr = itemStr + '">\n' +
				'           </div>\n' +
				'       </div>\n' +
				'       <div class="form-group  col-xs-5">\n' +
				'              <label  class="control-label col-xs-5 " title="关联方证件号码"><font color="red">✶</font>关联方证件号码</label>\n' +
				'              <div class="  col-xs-7">\n' +
				'                   <input type="text" data-fv-notempty="true"  class="jep-input form-control" id="' + relNumber + '" value = "';
		if(relInfo != undefined && relInfo !=null && relInfo != ""){
			itemStr = itemStr + relInfo.relNumber;
		}
		itemStr = itemStr + '">\n' +
				'               </div>\n' +
				'        </div>\n' +
				'        <div class="form-group  col-xs-1">\n' +
				'           <div class=" btn-danger1" onclick="delItme(\'' + item + '\')">删除</div>\n' +
				'        </div>\n' +
				'   </div>\n' +
				'   <div class="form-group ">\n' +
				'        <div class="form-group  col-xs-6">\n' +
				'             <label  class="control-label col-xs-4" title="本年度与该关联方累计交易金额(万元)">年度累计金额(万元)</label>\n' +
				'             <div class="  col-xs-8">\n' +
				'                   <input type="text" disabled="disabled" class="jep-input form-control" id="' + totolTradeAmount + '" value="';
		if(relInfo != undefined && relInfo !=null && relInfo != ""){
			itemStr = itemStr + relInfo.totolTradeAmount;
		}
		itemStr = itemStr + '" placeholder="本年度与该关联方发生的关联交易累计金额(万元)">\n' +
				'             </div>\n' +
				'       </div>\n' +
				'       <div class="form-group  col-xs-5">\n' +
				'            <label  class="control-label col-xs-5" title="与该关联方的交易金额(万元,请保留6位小数)">累计实际交易金额</label>\n' +
				'            <div class="  col-xs-7">\n' +
				'                  <input type="text" disabled="disabled" class="jep-input form-control" id="' + tradeAmount + '" value ="';
		if(relInfo != undefined && relInfo !=null && relInfo != ""){
			itemStr = itemStr + relInfo.tradeAmount;
		}
		itemStr = itemStr + '" >\n' +
				'            </div>\n' +
				'       </div>\n' +
				'       <div class="form-group  col-xs-1"></div>\n' +
				'       <input type="hidden" class="form-control" id="' + relId + '" value="';
		if (relInfo != undefined && relInfo != null && relInfo != "") {
			itemStr = itemStr + relInfo.relId;
		}
		itemStr = itemStr + '">\n' +
				'       <input type="hidden" class="form-control" id="' + relType + '" value="'
		if (relInfo != undefined && relInfo != null && relInfo != "") {
			itemStr = itemStr + relInfo.relType;
		}
		itemStr = itemStr + '">\n' +
				'       <input type="hidden" class="form-control" id="' + pid + '">\n' +
				'       </div>' +
				'    </div>\n' +
				'</div>';
		$("#formItems").append(itemStr);
	}

	function delItme(item){
		if (countTmp == 1) {
			$.messager.alert('提示', "请勿全部删除", 'info');
			return
		}
		$("#" + item).remove()
		countTmp--;
		isConsiderType();
	}
	function isAgreement(value) {
		var value1=$("#isAgreement").val();
		var isAgreeImpl=$("#isAgreementImpl").val();
		var value = value;
		if (value == '1'  || value1=='1') {
			debugger;
			$('#isAgreementImpl').val("");
			$('#isAgreementImpl').val("").trigger('change');
			$('#agreementNum').val("");
			$('#isAgreementImplDiv ').css('display', 'none');
			$('#agreementNumDiv  ').css('display', 'none');
			$('#isAgreementImplDiv label font').remove();
			$('#agreementNumDiv label font ').remove();
			isInRoad();
			isGreatTrade();
			isConsiderType();
			if($('#considerType').val()!='3'){
				reRedStar();
			}
		}
		if ((value == '0'&& isAgreeImpl =='1' && (isAgreeImpl ==null|| isAgreeImpl==" "))
				|| (value1 == '0'&& isAgreeImpl !='0' && (isAgreeImpl ==null|| isAgreeImpl==" ")) ) {
			$('#isAgreementImplDiv').css('display', 'block');
			$('#agreementNumDiv').css('display', 'block');
			if ($('#isAgreementImplDiv label font').length == 0) {
				$('#isAgreementImplDiv label').prepend("<font color='red'>✶</font>");
				$('#agreementNumDiv label ').prepend("<font color='red'>✶</font>");
			}
			$('#considerType').val("3");
			$('#considerType').select2({value: "3"});
			if ($('#contractNo_Div label font').length == 0) {
				addRedStar();
			}
		}
	};

	function addRedStar() {
		if ($('#contractNo_Div label font').length == 0) {
			$('#contractNo_Div label').prepend("<font color='red'>✶</font>");
			$('#busStartTimeDiv label').prepend("<font color='red'>✶</font>");
			$('#majorTradeDiv label').prepend("<font color='red'>✶</font>");
			$('#majorTypeDiv label').prepend("<font color='red'>✶</font>");
			$('#financialImpactDiv label').prepend("<font color='red'>✶</font>");
		}
		//根据业务类型逐笔时不同必填项不同
		if ($("#tradeSubjectAssetsDiv label font").length == 0 && $("#busType").val() == '1' || $("#busType").val() == '2') {
			$('#tradeSubjectAssetsDiv label').prepend("<font color='red'>✶</font>");
			$('#lastTradeTimeDiv label').prepend("<font color='red'>✶</font>");
			$('#lastTradePriceDiv label').prepend("<font color='red'>✶</font>");

		}
		if ($("#guaranteeConditionsDiv label font").length == 0 && $("#busType").val() == '4') {
			$('#guaranteeConditionsDiv label').prepend("<font color='red'>✶</font>");
		}
	}
	function reRedStar() {
		$('#contractNo_Div label font').remove();
		$('#busStartTimeDiv label font').remove();
		$('#majorTradeDiv label font').remove();
		$('#majorTypeDiv label font').remove();
		$('#financialImpactDiv label font').remove();

		$('#tradeSubjectAssetsDiv label font').remove();
		$('#lastTradeTimeDiv label font').remove();
		$('#lastTradePriceDiv label font').remove();
		$('#guaranteeConditionsDiv label font').remove();

	}
	function isAgreementImpl(value) {
		var IsAgreement=$("#isAgreement").val();
		var value = value;
		if(IsAgreement=='0'&&value=='0'){
			isInRoad();
			isGreatTrade();
			isConsiderType();
		}else{
			if ($('#isAgreementImplDiv label font').length == 0) {
				$('#isAgreementImplDiv label').prepend("<font color='red'>✶</font>");
				$('#agreementNumDiv label ').prepend("<font color='red'>✶</font>");
			}
			$('#considerType').val("3");
			$('#considerType').select2({value: "3"});
			if ($('#contractNo_Div label font').length == 0) {
				$('#contractNo_Div label').prepend("<font color='red'>✶</font>");
				$('#busStartTimeDiv label').prepend("<font color='red'>✶</font>");
				$('#financialImpactDiv label').prepend("<font color='red'>✶</font>");
			}

		}
	}
</script>
</body>
</html>
