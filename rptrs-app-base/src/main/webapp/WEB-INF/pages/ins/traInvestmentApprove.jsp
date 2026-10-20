<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp" %>
	<br:css pack="form"/>
	<title>关联交易表（保险）-投资入股类</title>
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
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox id="traInvestmentBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true" >
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:hidden id="relId" name="relId" value="${traInvestment.relId}"/>
				<bf:hidden id="relNumber" name="relNumber" value="${traInvestment.relNumber}"/>
				<bf:hidden id="relType" name="relType" value="${traInvestment.relType}"/>
				<bf:hidden id="pid" name="pid" value="${traInvestment.pid}"/>
				<bf:hidden id="ruleResult" name="ruleResult" value=""/>
				<bf:hidden id="total" name="total" value="${traInvestment.totolTradeAmount}"/>
				<bf:hidden id="amount" name="amount" value="${traInvestment.tradeAmount}"/>
				<bc:row cls="m-b">
					<bf:formGroup hrp="bottom">
						<bf:formGroup col="4">
							<bc:label cls="required" text="交易主体名称" col="5" formControl="true"/>
							<bf:select id="traSubName" name="traSubName" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/INS-subjectName"
									   disabled="${opt == 'view'}" value="${traInvestment.traSubName}">
								<bf:option text="请选择"></bf:option>
								<be:event type="select">
								</be:event>
							</bf:select>
						</bf:formGroup>
						<%--                    <bf:formGroup col="4">--%>
						<%--                        <bc:label cls="required" text="交易对手名称" col="5" formControl="true"/>--%>
						<%--                        <bf:lookup id="relName" name="relName" col="7" title="交易对手信息"--%>
						<%--                                   url="${base}/ins/traInvestment/relNameSelect" winId="relNameSelect"--%>
						<%--                                   propText="relName" propValue="relName"--%>
						<%--                                   callback="result"  text="${traInvestment.relName}"--%>
						<%--                                   value="${traInvestment.relName}" disabled="${opt == 'view'}"--%>
						<%--                                   beforeOpen="empTraSub"   onSelect="oneSelect">--%>
						<%--                        </bf:lookup>--%>
						<%--                    </bf:formGroup>--%>
						<bf:formGroup col="4">
							<bc:label text="交易对手名称" col="5" formControl="true"/>
							<bf:text id="relName" name="relName" col="7" click="relNameSelect()"
									 value="${traInvestment.relName}" disabled="${opt == 'view'}"
							>
								<bv:notEmpty/>
							</bf:text>
						</bf:formGroup>


						<bf:formGroup col="4">
							<bc:label cls="required" text="是否为控股子公司" col="5" formControl="true"/>
							<bf:select id="isHoldingSubsidiary" name="isHoldingSubsidiary" col="7"
									   value="${traInvestment.isHoldingSubsidiary}" disabled="${opt == 'view'}"
									   dataSource="fixedDict" url="IsStatusDict">
								<be:event type="select">
									HoldingSubsidiarySub(e.target.value);
isConsiderType();
								</be:event>
								<bf:option text="请选择" value=""></bf:option>
								<bv:notEmpty/>
							</bf:select>
						</bf:formGroup>
					</bf:formGroup>

					<bf:formGroup hrp="bottom">
						<bf:formGroup col="4">
							<bc:label text="控股子公司全称" col="5" formControl="true"/>
							<bf:text id="holdingSubsidiaryName" name="holdingSubsidiaryName" col="7"
									 value="${traInvestment.holdingSubsidiaryName}"
									 disabled = "true">
								<bv:notEmpty/>
							</bf:text>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="控股子公司证件类型" col="5" formControl="true"/>
							<bf:select id="holdingSubsidiaryType" name="holdingSubsidiaryType" col="7" value="${traInvestment.holdingSubsidiaryType}"
									   dataSource="custom" url="sys/dictItem/getDictItems/BUS-ORGIdentifyType" disabled="true">
							</bf:select>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="控股子公司证件号码" col="5" formControl="true"/>
							<bf:text id="holdingSubsidiaryNumber" name="holdingSubsidiaryNumber" col="7"
									 value="${traInvestment.holdingSubsidiaryNumber}"
									 disabled = "true">
								<bv:notEmpty/>
							</bf:text>
						</bf:formGroup>
					</bf:formGroup>

					<bf:formGroup hrp="bottom">
						<bf:formGroup col="4">
							<bc:label cls="required" text="交易对手上年末总资产（万元/人民币）" col="5" formControl="true"/>
							<bf:text id="totalAssets" name="totalAssets" col="7" value="${traInvestment.totalAssets}"
									 disabled="${opt == 'view'}">
								<bv:notEmpty/>
							</bf:text>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="交易类型" col="5" formControl="true"/>
							<bf:select id="tranType" name="tranType" col="7" value="${traInvestment.tranType}"
									   dataSource="custom"
									   url="sys/dictItem/getDictItems/INS-tranType" disabled="true">
							</bf:select>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label cls="required" text="业务类型" col="5" formControl="true"/>
							<bf:select id="investmentType" name="investmentType" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/INS-InvestmentType" disabled="${opt == 'view'}"
									   includes="" value="${traInvestment.investmentType}">
								<bf:option text="请选择"></bf:option>
								<be:event type="select">
									changeNext(e.target.value)
								</be:event>
								<bv:notEmpty/>
							</bf:select>
						</bf:formGroup>
					</bf:formGroup>
					<bf:formGroup hrp="bottom">
						<div id="approvalNumDiv" style="display: none" >
							<bf:formGroup col="4">
								<bc:label cls="required" text="行政审批文号" col="5" formControl="true"/>
								<bf:text id="approvalNum" name="approvalNum" col="7" value="${traInvestment.approvalNum}"
										 disabled="${opt == 'view'}">
									<bv:notEmpty/>
								</bf:text>
							</bf:formGroup>
						</div>
						<div id="securitiesTypeDiv" style="display: none">
							<bf:formGroup col="4">
								<bc:label cls="required" text="证券类型" col="5" formControl="true"/>
								<bf:select id="securitiesType" name="securitiesType" col="7"
										   value="${traInvestment.securitiesType}" dataSource="custom"
										   url="sys/dictItem/getDictItems/INS-SecuritiesType" disabled="${opt == 'view'}">
									<bf:option text="请选择" value=""></bf:option>
								</bf:select>
							</bf:formGroup>
						</div>
						<bf:formGroup col="4">
							<bc:label text="是否重大关联交易" col="5" formControl="true"/>
							<bf:select id="majorTrade" name="majorTrade" col="7" value="${traInvestment.majorTrade}"
									   disabled="true" dataSource="fixedDict"
									   url="IsStatusDict">
								<bf:option text="-" value=""></bf:option>
							</bf:select>
						</bf:formGroup>
					</bf:formGroup>

					<bf:formGroup hrp="bottom">
						<bf:formGroup col="4">
							<bc:label cls="required" text="合同/交易编号" col="5" formControl="true"/>
							<bf:text id="contractNo" name="contractNo" col="7" value="${traInvestment.contractNo}"
									 disabled="${opt == 'view'}">
								<bv:notEmpty/>
							</bf:text>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label cls="required" text="合同金额（万元/人民币）" col="5" formControl="true"/>
							<bf:text id="contractAmount" name="contractAmount" col="7" disabled="${opt == 'view'}"
									 value="${traInvestment.contractAmount}">
								<bv:notEmpty/>
							</bf:text>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label cls="required" text="合同/交易发生日期" col="5" formControl="true"/>
							<bf:datebox id="tranDate" name="tranDate" col="7" value="${traInvestment.tranDateString}"
										disabled="${opt == 'view'}">
								<bv:notEmpty/>
							</bf:datebox>
						</bf:formGroup>

					</bf:formGroup>

					<bf:formGroup hrp="bottom">
						<bf:formGroup col="4">
							<bc:label cls="required" text="业务起始日期" col="5" formControl="true"/>
							<bf:datebox id="busStartTime" name="busStartTime" col="7" value="${traInvestment.busStartTimeString}"
										disabled="${opt == 'view'}">
								<bv:notEmpty/>
							</bf:datebox>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="业务结束日期" col="5" formControl="true"/>
							<bf:datebox id="busEndTime" name="busEndTime" col="7" value="${traInvestment.busEndTimeString}"
										disabled="${opt == 'view'}"/>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="本年度与该关联方累计交易金额（万元/人民币）" col="5" formControl="true"/>
							<bf:text id="totolTradeAmount" name="totolTradeAmount" col="7"
									 value="${traInvestment.totolTradeAmount}" disabled="true"
									 placeholder="本年度与该关联方发生的关联交易累计金额（万元/人民币）">
							</bf:text>
						</bf:formGroup>
					</bf:formGroup>

					<bf:formGroup hrp="bottom">
<%--						<bf:formGroup col="4">--%>
<%--							<bc:label text="交易金额（万元/人民币）" col="5" formControl="true"/>--%>
<%--							<bf:text id="tradeAmount" name="tradeAmount" col="7" value="${traInvestment.tradeAmount}"--%>
<%--									 disabled="${opt == 'view'}" change='isGreatTrade()'>--%>
<%--								<bv:notEmpty/>--%>
<%--							</bf:text>--%>
<%--						</bf:formGroup>--%>
						<bf:formGroup col="4">
							<bc:label cls="required" text="生效时间" col="5" formControl="true"/>
							<bf:datebox id="effectiveTime" name="effectiveTime" col="7" value="${traInvestment.effectiveTimeString}"
										disabled="${opt == 'view'}">
								<bv:notEmpty/>
							</bf:datebox>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label cls="required" text="收益率" col="5" formControl="true"/>
							<bf:text id="yield_" name="yield_" col="7" value="${traInvestment.yield_}"
									 disabled = "${opt == 'view'}">
								<bv:notEmpty/>
							</bf:text>
						</bf:formGroup>
					</bf:formGroup>

					<bf:formGroup hrp="bottom">
						<bf:formGroup col="4">
							<bc:label cls="required" text="股\份\张数" col="5" formControl="true"/>
							<bf:text id="shareNum" name="shareNum" col="7" value="${traInvestment.shareNum}"
									 disabled="${opt == 'view'}">
								<bv:notEmpty/>
							</bf:text>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label cls="required" text="变动股比或份额占比（%）" col="5" formControl="true"/>
							<bf:text id="variableshareratio" name="variableshareratio" col="7" value="${traInvestment.variableshareratio}"
									 disabled="${opt == 'view'}"  placeholder="(%)">
								<bv:notEmpty/>
							</bf:text>
						</bf:formGroup>

						<bf:formGroup col="4">
							<bc:label cls="required" text="变动后股比或份额占比（%）" col="5" formControl="true"/>
							<bf:text id="variableshareratioAfter" name="variableshareratioAfter" col="7" value="${traInvestment.variableshareratioAfter}"
									 disabled="${opt == 'view'}" placeholder="(%)">
								<bv:notEmpty/>
							</bf:text>
						</bf:formGroup>
					</bf:formGroup>

					<bf:formGroup hrp="bottom">
						<bf:formGroup col="4">
							<bc:label cls="required" text="交易定价是否为国家规定" col="5" formControl="true"/>
							<bf:select id="isDealPrice" name="isDealPrice" col="7" value="${traInvestment.isDealPrice}"
									   disabled="${opt == 'view'}" dataSource="fixedDict" url="IsStatusDict">
								<be:event type="select">
									isConsiderType();
								</be:event>
								<bf:option text="请选择" value=""></bf:option>
								<bv:notEmpty/>
							</bf:select>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="是否现金认购" col="5" formControl="true"/>
							<bf:select id="isCashSubscript" name="isCashSubscript" col="7"
									   value="${traInvestment.isCashSubscript}" disabled="${opt == 'view'}"
									   dataSource="fixedDict" url="IsStatusDict">
								<be:event type="select">
									isConsiderType();
								</be:event>
								<bf:option text="请选择" value=""></bf:option>
								<bv:notEmpty/>
							</bf:select>
						</bf:formGroup>
						<bf:formGroup col="4">
							<bc:label text="审议类型" col="5" formControl="true"/>
							<bf:select id="considerType" name="considerType" col="7" value="${traInvestment.considerType}"
									   dataSource="custom" url="sys/dictItem/getDictItems/INS-considerType" disabled="true">
								<bv:notEmpty/>
							</bf:select>
						</bf:formGroup>
					</bf:formGroup>

					<bf:formGroup hrp="bottom">
						<bf:formGroup col="4">
							<bc:label cls="required" text="达到重大关联交易的情形" col="5" formControl="true"/>
							<bf:select id="majorType" name="majorType" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/INS-majorType" disabled="${opt == 'view'}"
									   includes="" value="${traInvestment.majorType}">
								<bf:option text="-" value=""></bf:option>
							</bf:select>
						</bf:formGroup>
					</bf:formGroup>
				</bc:row>

				<bf:formGroup hrp="bottom">
					<bf:formGroup col="6">
						<bc:label cls="required" text="交易价格" col="3" type="warning" formControl="true"/>
						<bf:text id="tradePrice" name="tradePrice" col="9" rows="2" multiple="true"
								 value="${traInvestment.tradePrice}" disabled="${opt == 'view'}">
							<bv:notEmpty/>
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="6">
						<bc:label cls="required" text="定价政策" col="3" formControl="true"/>
						<bf:text id="pricingPolicy" name="pricingPolicy" col="9" rows="2" multiple="true"
								 value="${traInvestment.pricingPolicy}" disabled="${opt == 'view'}">
							<bv:notEmpty/>
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="6">
						<bc:label cls="required" text="对财务状况及经营成果的影响" col="3" formControl="true"/>
						<bf:text id="financialImpact" name="financialImpact" col="9" rows="2" multiple="true"
								 value="${traInvestment.financialImpact}" disabled="${opt == 'view'}">
							<bv:notEmpty/>
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="6">
						<bc:label cls="required" text="交易标的" col="3" formControl="true"/>
						<bf:text id="tradeSubject" name="tradeSubject" col="9" rows="2" multiple="true" value="${traInvestment.tradeSubject}"
								 disabled="${opt == 'view'}">
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="6">
						<bc:label cls="required" text="其他事项说明" col="3" formControl="true"/>
						<bf:text id="remark" name="remark" col="9" rows="2" multiple="true" value="${traInvestment.remark}"
								 disabled="${opt == 'view'}">
						</bf:text>
					</bf:formGroup>
				</bf:formGroup>
				<c:if test="${op ne 1}">
					<div id="tableDiv" style="width:95%;margin:0 auto">
						<bc:table id="docTable" idField="pid" url="${base}/ins/traInsenclosure" multiple="true"
								  selectFirst="false" queryUrl="queryList?transNum=${traInvestment.transNum}">
							<%--<bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>--%>
							<%--<bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>--%>
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
				</c:if>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="success" id="confirmSubmit" text="确认提交" icon="arrow-circle-right" click="Approved()">
						</bc:button>
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/ins/traInvestment/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
			</bf:form>
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
							<img src="${base}/static/img/sendfile.png"
								 style="top:28px;position:relative;z-index :0;font-size: 20px;"/>
							<a href="javascript:;" class="file" style="top:28px;position:relative;z-index :0;">选择文件
								<input type="file" class="form-control" id="file" name="file" multiple
									   style="top:-58px;position:relative;z-index :1;opacity:0;padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
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
    var nextApprovalPeople ;
	//选择下一节点审批人
	function Approved() {
		var traSubName =$('#traSubName').val();
        //临时修改2021-04-30
        if (false) {
			var relName = $('#relName').val();
			var tranType = $('#tranType').val();
			var contractNo = $('#contractNo').val();
			var contractAmount = $('#contractAmount').val();
			$("#confirmSubmit").attr("disabled","disabled");
			var obj = {
				traSubName:traSubName,
				relName:relName,
				tranType:tranType,
				contractNo:contractNo,
				contractAmount:contractAmount
			};
			$.ajax({
					url: '${base}/ins/traInvestment/sendNotice',
					data: obj,
					type: 'post',
					jsonType: 'json',
					success: function (data) {
						if (data.success) {
							var msg = data.msg;
							$.messager.alert('提示', msg, 'info');
						}
						window.location = '${base}/ins/traInvestment/list';
					}
				}
			);
		}else{
			/*$.messager.modal({
				winId: 'Approved',
				width: 1200,
				height: 420,
				title: '选择下一节点审批人',
				url: '${base}/ins/traInvestment/Approved?mult=true',
				args: {
					result: function (obj) {
						nextApprovalPeople = obj["staffCode"];
						sub()
					}
				},
				onClose: function(){

				}
			});*/
		}
		/*$.messager.modal({
			winId: 'Approved',
			width: 1200,
			height: 420,
			title: '选择下一节点审批人',
			url: '${base}/ins/traInvestment/Approved?mult=true',
			args: {
				result: function (obj) {
					nextApprovalPeople = obj["staffCode"];
					sub()
				}
			},
			onClose: function(){

			}
		});*/
		sub()
	}

	var investmentType = $("#investmentType").val();
	$(document).ready(function () {
		if (investmentType != null && investmentType != undefined && investmentType != '') {
			changeNext(investmentType);
		}
		if (investmentType == '' || investmentType == undefined || investmentType == null) {
			changeNext('0');
		}
	});

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
		if (pid.indexOf(',') != -1) {
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
			url: '${base}/ins/traInsenclosure/uploadFile?businessType=0', //上传文件的请求路径必须是绝对路劲
			data: formData,
			cache: false,
			processData: false,
			contentType: false,
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
				}
			},
			error: function () {
				$("#close").click();
				alert("上传失败,请重试");
			}
		})
	});

	//文件表数据
	function getData() {
		return $.map($('#docTable').bootstrapTable('getData'), function (row) {
			return row
		});
	}

	function changeNext(val) {
		if (val == '04') {
			$("#approvalNumDiv").css("display", "none");
			$("#securitiesTypeDiv").css("display", "block");
			$("#variableshareratio").removeAttr("placeholder");
		}
		if (val == '01') {
			$("#approvalNumDiv").css("display", "block");
			$("#securitiesTypeDiv").css("display", "none");
			$("#variableshareratio").removeAttr("placeholder");
		}
		if (val == '02') {
			$("#approvalNumDiv").css("display", "block");
			$("#securitiesTypeDiv").css("display", "none");
			$("#variableshareratio").attr("placeholder","减少股比请填写负数")
		}
		if (val == '03') {
			$("#approvalNumDiv").css("display", "block");
			$("#securitiesTypeDiv").css("display", "none");
			$("#variableshareratio").removeAttr("placeholder");
		}
	}

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
			url: '${base}/ins/filInvestment/selectRel',
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
				} else {
					$.messager.alert('提示', '关联方不存在！', 'warning');
				}
			}
		});
	}
	/*
  根据当前时间和随机数生成备案号
  * */
	function randomNumber() {
		var now = new Date();
		var year = now.getFullYear().toString();
		var month = now.getMonth() + 1;
		var day = now.getDate();
		var hour = now.getHours();
		var minutes = now.getMinutes();
		var seconds = now.getSeconds();
		var no = year+month+day+hour+minutes+seconds;
		return no+(Math.round(Math.random()*89+100)).toString();
	}

	//objData.filNum = randomNumber()+'.'+data.pid

	 function sub() {
		//判断一般流程或是重大流程
		var procDefCode ;
		var majorTrade = $('#majorTrade').val();
		if (majorTrade == "0"){
			//重大关联交易
			procDefCode = 'zybr_relGreat';
			// procDefCode = 'zjcc_tra001';
		}else {
			//一般关联交易
			// procDefCode = 'zjcc_YB_tra001';
			procDefCode = 'zybr_relGeneral';
		}
		 // 提交审批  的必须是待提交状态
		// var status=getStatusSelections();
		 var pid= $('#pid').val();
	/*	 if (pid=='' || pid==null||pid.length!=1){
			 $.messager.alert('错误', "请选择一条数据", 'error');
			 return;
		 }*/

			 //查询出基本信息 修改状态 放到data中
			 $.ajax({
				 url:"${base}/ins/traInvestment/send/"+pid,
				 method:"GET",
				 dataType:"JSON",
				 traditional:true,
				 success:function(data){
					 if(data.success){
						 //开始走工作流
						 var objData=data.data;
						 var myDate = new Date();
						 objData.filNum = '1o'+randomNumber()
						 objData.id = myDate.getTime();
						 // objData.nextApprovalPeople = nextApprovalPeople;
						 //机构信息
						 var unitCode = data.unitCode;
						 //部门信息
						 var deptCode = data.deptCode;
						 objData.id = myDate.getTime();
						 objData.unitCode=unitCode;
						 objData.deptCode=deptCode;
						 var deptName = data.deptName;
						 var hdDeptCode = data.hdDeptCode;
						 objData.deptName=deptName;
						 objData.hdDeptCode=hdDeptCode;
						 data = JSON.stringify(objData);
						 var formData = $.helper.base64Encode(data);
						 //区分总部一级部门和二级部门
						 var isSecond = data.isSecond;
						 if (majorTrade == "0"){
							 //重大关联交易
							 if(unitCode=='8600'){
								 //总公司
								 if(isSecond){
									 //二级部门
									 procDefCode = 'zyzbejzdrel'
								 }else {
									 //一级部门
									 procDefCode='zyzbyizdrel'
								 }
							 }else {
								 //分公司
								 procDefCode = 'zybr_relGreat';
							 }
						 }else {
							 if(unitCode=='8600'){
								 //总公司
								 if(isSecond){
									 //二级部门
									 procDefCode = 'zyzbejybrel'
								 }else {
									 //一级部门
									 procDefCode='zyzbyjybrel'
								 }
							 }else {
								 //分公司
								 procDefCode = 'zybr_relGeneral';
							 }
						 }
						 //挂取工作流
						 var obj = {
							 procDefCode: procDefCode,
							 oldData: formData,
							 newData: formData,
							 uploadSessions: '',
							 procInstId: ''
						 }
						 $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
							 if (data.success) {
								 $.messager.alert('提示', '提交成功。', 'info', function () {
									 window.location = '${base}/ins/traInvestment/list';
								 });
							 } else {
								 $.messager.alert('错误', data.msg, 'error');
							 }
						 });
					 }else
					 {
						 $.messager.alert('错误', data.msg, 'error');
					 }
				 }
			 })

    }

	$.fn.serializeJson = function () {
		var serializeObj = {};
		var array = this.serializeAll();
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


</script>
</body>
</html>
