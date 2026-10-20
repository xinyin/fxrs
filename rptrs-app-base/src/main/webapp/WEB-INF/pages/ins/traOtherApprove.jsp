<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp" %>
	<br:css pack="form"/>
	<title>关联交易表（保险）-保险业务类</title>
	<style>
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
<body class="gray-bg">
<div class="row">
	<bc:ibox id="traOtherBox" title="" subTitle="" col="12" collapsible="true">
		<bf:form id="editForm" method="post" validation="true">
			<bf:hidden id="opt" name="opt" value="${opt}"/>
			<bf:hidden id="relId" name="relId" value="${traOther.relId}"/>
			<bf:hidden id="relNumber" name="relNumber" value="${traOther.relNumber}"/>
			<bf:hidden id="relType" name="relType" value="${traOther.relType}"/>
			<bf:hidden id="pid" name="pid" value="${traOther.pid}"/>
			<bf:hidden id="ruleResult" name="ruleResult" value=""/>
			<bf:hidden id="total" name="total" value="${traOther.totolTradeAmount}"/>
			<bf:hidden id="amount" name="amount" value="${traOther.tradeAmount}"/>
			<bc:row cls="m-b">

				<bf:formGroup hrp="bottom">
					<bf:formGroup col="4">
						<div title="交易主体名称">
							<bc:label cls="required" text="交易主体名称" col="5" formControl="true"/>
							<bf:select id="traSubName" name="traSubName" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/INS-subjectName" disabled="${opt == 'view'}"
									   value="${traOther.traSubName}">
								<bf:option text="请选择"></bf:option>
								<be:event type="select">
								</be:event>
							</bf:select>
						</div>
					</bf:formGroup>
					<%--                    <bf:formGroup col="4">--%>
					<%--                        <div title="交易对手名称">--%>
					<%--                            <bc:label text="交易对手名称" col="5" formControl="true"/>--%>
					<%--                            <bf:lookup id="relName" name="relName" col="7" title="交易对手信息"--%>
					<%--                                       url="${base}/ins/traInvestment/relNameSelect" winId="relNameSelect"--%>
					<%--                                       propText="relName" propValue="relName"--%>
					<%--                                       callback="result"  text="${traOther.relName}"--%>
					<%--                                       value="${traOther.relName}" disabled="${opt == 'view'}" onSelect="oneSelect">--%>
					<%--                            </bf:lookup>--%>
					<%--                        </div>--%>
					<%--                    </bf:formGroup>--%>
					<bf:formGroup col="4">
						<bc:label text="交易对手名称" col="5" formControl="true"/>
						<bf:text id="relName" name="relName" col="7" click="relNameSelect()"
								 value="${traOther.relName}" disabled="${opt == 'view'}"
						>
							<bv:notEmpty/>
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="4">
						<div title="是否为控股子公司">
							<bc:label cls="required" text="是否为控股子公司" col="5" formControl="true"/>
							<bf:select id="isHoldingSubsidiary" name="isHoldingSubsidiary" col="7"
									   value="${traOther.isHoldingSubsidiary}" disabled="${opt == 'view'}"
									   dataSource="fixedDict" url="IsStatusDict">
								<be:event type="select">
									HoldingSubsidiarySub(e.target.value);
isConsiderType();
								</be:event>
								<bf:option text="请选择" value=""></bf:option>
								<bv:notEmpty/>
							</bf:select>
						</div>
					</bf:formGroup>
				</bf:formGroup>

				<bf:formGroup hrp="bottom">
					<bf:formGroup col="4">
						<div title="控股子公司全称">
							<bc:label text="控股子公司全称" col="5" formControl="true"/>
							<bf:text id="holdingSubsidiaryName" name="holdingSubsidiaryName" col="7"
									 value="${traOther.holdingSubsidiaryName}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4">
						<div title="控股子公司证件类型">
							<bc:label text="控股子公司证件类型" col="5" formControl="true"/>
							<bf:select id="holdingSubsidiaryType" name="holdingSubsidiaryType" col="7" value="${traOther.holdingSubsidiaryType}"
									   dataSource="custom" url="sys/dictItem/getDictItems/BUS-ORGIdentifyType" disabled="true">
							</bf:select>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4">
						<div title="控股子公司证件号码">
							<bc:label text="控股子公司证件号码" col="5" formControl="true"/>
							<bf:text id="holdingSubsidiaryNumber" name="holdingSubsidiaryNumber" col="7"
									 value="${traOther.holdingSubsidiaryNumber}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</div>
					</bf:formGroup>
				</bf:formGroup>

				<bf:formGroup hrp="bottom">
					<bf:formGroup col="4">
						<div title="交易对手上年末总资产（万元/人民币）">
							<bc:label cls="required" text="交易对手上年末总资产（万元/人民币）" col="5" formControl="true"/>
							<bf:text id="totalAssets" name="totalAssets" col="7" value="${traOther.totalAssets}"
									 disabled="${opt == 'view'}">
								<bv:notEmpty/>
							</bf:text>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4">
						<div title="交易类型">
							<bc:label text="交易类型" col="5" formControl="true"/>
							<bf:select id="tranType" name="tranType" col="7" value="${traOther.tranType}"
									   dataSource="custom"
									   url="sys/dictItem/getDictItems/INS-tranType" disabled="true">
							</bf:select>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4">
						<div title="业务类型">
							<bc:label cls="required" text="业务类型" col="5" formControl="true"/>
							<bf:select id="busType" name="busType" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/INS-otherType"
									   includes="" value="${traOther.busType}" disabled="${opt == 'view'}">
								<bf:option text="请选择" value=""></bf:option>
								<be:event type="select">
								</be:event>
								<bv:notEmpty/>
							</bf:select>
						</div>
					</bf:formGroup>
				</bf:formGroup>

				<bf:formGroup hrp="bottom">
					<bf:formGroup col="4">
						<div title="合同/交易编号">
							<bc:label cls="required" text="合同/交易编号" col="5" formControl="true"/>
							<bf:text id="contractNo" name="contractNo" col="7" value="${traOther.contractNo}"
									 disabled="${opt == 'view'}">
								<bv:notEmpty/>
							</bf:text>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4">
						<div title="合同金额（万元/人民币）">
							<bc:label cls="required" text="合同金额（万元/人民币）" col="5" formControl="true"/>
							<bf:text id="contractAmount" name="contractAmount" col="7"
									 value="${traOther.contractAmount}" disabled="${opt == 'view'}">
								<bv:notEmpty/>
							</bf:text>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4">
						<div title="合同/交易发生日期">
							<bc:label cls="required" text="合同/交易发生日期" col="5" formControl="true"/>
							<bf:datebox id="tranDate" name="tranDate" col="7" value="${traOther.tranDate}"
										disabled="${opt == 'view'}">
								<bv:notEmpty/>
							</bf:datebox>
						</div>
					</bf:formGroup>
				</bf:formGroup>

				<bf:formGroup hrp="bottom">
					<bf:formGroup col="4">
						<div title="业务起始日期">
							<bc:label cls="required" text="业务起始日期" col="5" formControl="true"/>
							<bf:datebox id="busStartTime" name="busStartTime" col="7"
										value="${traOther.busStartTimeString}" disabled="${opt == 'view'}">
								<bv:notEmpty/>
							</bf:datebox>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4">
						<div title="业务终止日期">
							<bc:label text="业务终止日期" col="5" formControl="true"/>
							<bf:datebox id="busEndTime" name="busEndTime" col="7"
										value="${traOther.busEndTimeString}" disabled="${opt == 'view'}"/>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4">
						<div title="本年度与该关联方发生的关联交易累计金额（万元/人民币）">
							<bc:label text="本年度与该关联方累计交易金额（万元/人民币）" col="5" formControl="true"/>
							<bf:text id="totolTradeAmount" name="totolTradeAmount" col="7"
									 value="${traOther.totolTradeAmount}" disabled="true"

									 mouseover="">
							</bf:text>
						</div>
					</bf:formGroup>
				</bf:formGroup>

				<bf:formGroup hrp="bottom">
<%--					<bf:formGroup col="4">--%>
<%--						<div title="交易金额（万元/人民币）">--%>
<%--							<bc:label text="交易金额（万元/人民币）" col="5" formControl="true"/>--%>
<%--							<bf:text id="tradeAmount" name="tradeAmount" col="7" value="${traOther.tradeAmount}"--%>
<%--									 change="isGreatTrade()" disabled="${opt == 'view'}">--%>
<%--								<bv:notEmpty/>--%>
<%--							</bf:text>--%>
<%--						</div>--%>
<%--					</bf:formGroup>--%>
					<bf:formGroup col="4">
						<div title="交易定价是否为国家规定">
							<bc:label cls="required" text="交易定价是否为国家规定" col="5" formControl="true"/>
							<bf:select id="isDealPrice" name="isDealPrice" col="7" dataSource="fixedDict"
									   url="IsStatusDict"
									   value="${traOther.isDealPrice}" disabled="${opt == 'view'}">
								<be:event type="select">
									isConsiderType();
								</be:event>
								<bf:option text="请选择" value=""></bf:option>
								<bv:notEmpty/>
							</bf:select>
						</div>
					</bf:formGroup>

					<bf:formGroup col="4">
						<div title="是否现金认购">
							<bc:label text="是否现金认购" col="5" formControl="true"/>
							<bf:select id="isCashSubscribe" name="isCashSubscribe" col="7"
									   value="${traOther.isCashSubscribe}" disabled="${opt == 'view'}"
									   dataSource="fixedDict" url="IsStatusDict">
								<be:event type="select">
									isConsiderType();
								</be:event>
								<bf:option text="请选择" value=""></bf:option>
								<bv:notEmpty/>
							</bf:select>
						</div>
					</bf:formGroup>
				</bf:formGroup>

				<bf:formGroup hrp="bottom">
					<bf:formGroup col="4">
						<div title="是否重大关联交易">
							<bc:label text="是否重大关联交易" col="5" formControl="true"/>
							<bf:select id="majorTrade" name="majorTrade" col="7" dataSource="fixedDict"
									   url="IsStatusDict"  disabled="true"
									   value="${traOther.majorTrade}">
								<bf:option text="-" value=""></bf:option>
							</bf:select>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4">
						<!-- <font color="red" size="5">*</font> -->
						<div title="审议类型">
							<bc:label text="审议类型" col="5" formControl="true"/>
							<bf:select id="considerType" name="considerType" col="7" value="${traOther.considerType}"
									   dataSource="custom" url="sys/dictItem/getDictItems/INS-considerType"
									   disabled="true">
								<bv:notEmpty/>
							</bf:select>
						</div>
					</bf:formGroup>
					<bf:formGroup col="4">
						<div title="达到重大关联交易的情形">
							<bc:label cls="required" text="达到重大关联交易的情形" col="5" formControl="true"/>
							<bf:select id="majorType" name="majorType" col="7" dataSource="custom"
									   url="sys/dictItem/getDictItems/INS-majorType"
									   includes="" value="${traOther.majorType}" disabled="${opt == 'view'}">
								<bf:option text="-" value=""></bf:option>
							</bf:select>
						</div>
					</bf:formGroup>
				</bf:formGroup>
			</bc:row>
			<bf:formGroup hrp="bottom">
				<bf:formGroup col="6">
					<div title="交易价格">
						<bc:label cls="required" text="交易价格" col="3" formControl="true"/>
						<bf:text id="tradePrice" name="tradePrice" col="9" rows="2" value="${traOther.tradePrice}"
								 multiple="true" disabled="${opt == 'view'}">
							<bv:notEmpty/>
						</bf:text>
					</div>
				</bf:formGroup>
				<bc:col col="6">
					<div title="定价政策">
						<bc:label cls="required" text="定价政策" col="3" formControl="true"/>
						<bf:text id="pricingPolicy" name="pricingPolicy" col="9" rows="2"
								 value="${traOther.pricingPolicy}"
								 multiple="true" disabled="${opt == 'view'}">
							<bv:notEmpty/>
						</bf:text>
					</div>
				</bc:col>
			</bf:formGroup>

			<bf:formGroup hrp="bottom">
				<bf:formGroup col="6">
					<div title="对财务状况及经营成果的影响">
						<bc:label cls="required" text="对财务状况及经营成果的影响" col="3" formControl="true"/>
						<bf:text id="tradeOccur" name="tradeOccur" col="9" rows="2" value="${traOther.tradeOccur}"
								 multiple="true" disabled="${opt == 'view'}">
						</bf:text>
					</div>
				</bf:formGroup>
				<bc:col col="6">
					<div title="交易标的">
						<bc:label cls="required" text="交易标的" col="3" formControl="true"/>
						<bf:text id="tradeSubject" name="tradeSubject" col="9" rows="2"
								 value="${traOther.tradeSubject}"
								 multiple="true" disabled="${opt == 'view'}">
							<bv:notEmpty/>
						</bf:text>
					</div>
				</bc:col>
			</bf:formGroup>

			<bf:formGroup hrp="bottom">
				<bc:col col="6">
					<div title="其他事项说明">
						<bc:label cls="required" text="其他事项说明" col="3" formControl="true"/>
						<bf:text id="remark" name="remark" col="9" rows="2" value="${traOther.remark}"
								 multiple="true"
								 disabled="${opt == 'view'}">
						</bf:text>
					</div>
				</bc:col>
			</bf:formGroup>
			<div id="tableDiv" class="ismainhoder">
				<c:if test="${op ne 1}">
					<bf:formGroup hr="dashed" hrp="bottom">
						<div style="width: 95%;margin:0 auto">
							<bc:table id="docTable" idField="pid" url="${base}/ins/traInsenclosure" multiple="true"
									  selectFirst="false" queryUrl="queryList?transNum=${traOther.transNum}">
								<bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
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
					</bf:formGroup>
				</c:if>
			</div>
			<bf:formGroup>
				<div class="col-sm-4 col-sm-offset-2">
					<bc:button type="success" id="confirmSubmit" text="确认提交" icon="arrow-circle-right" click="Approved()">
					</bc:button>
					<bc:button type="primary" text="返回" icon="arrow-circle-left">
						<be:event>
							window.location = '${base}/ins/traOther/list';
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
					url: '${base}/ins/traOther/sendNotice',
					data: obj,
					type: 'post',
					jsonType: 'json',
					success: function (data) {
						if (data.success) {
							var msg = data.msg;
							$.messager.alert('提示', msg, 'info');
						}
						window.location = '${base}/ins/traOther/list';
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
			sub()
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
	}



	$(document).ready(function () {

	});

	$(document).ready(function () {
		changeNext('0');
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
			$.messager.alert("请选中一个文件");
			return;
		}
		if (pid.indexOf(',') != -1) {
			$.messager.alert("只能选中一个文件");
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
			$.messager.alert("请先选中数据");
			return;
		}
		if (confirm("确认删除？")) {
			$.ajax({
				type: 'get',
				url: '${base}/ins/traInsenclosure/removeFile/' + pids,
				dataType: 'json',
				success: function (res) {
					$.messager.alert(res.msg);
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
				$.messager.alert("请上传pdf文件");
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
				$.messager.alert(data.msg);
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
				$.messager.alert("上传失败,请重试");
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
		if (val == '0') {
			$("#approvalNumDiv").css("display", "none")
			$("#securitiesTypeDiv").css("display", "block")
		} else if (val == '1') {
			$("#approvalNumDiv").css("display", "block")
			$("#securitiesTypeDiv").css("display", "none")
		}
	}


	//初始化数据
	function getRelInfo() {
		var relNumber = $('#relNumber').val();
		var relName = $('#relName').val();
		var obj = {
			relNumber: relNumber,
			relName: relName
		};
		$.ajax({
			url: '${base}/ins/traFunds/getRelInfo',
			data: obj,
			type: 'post',
			jsonType: 'json',
			success: function (data) {
				if (data.success) {
					var relName = data.relName;
					var relType = data.relType;
					var relId = data.relId;
					var relNumber = data.relNumber;
					var caliber = data.caliber;
					var totalTradeAmount = data.totalTradeAmount;
					$('#relName').val(relName);
					$('#relNumber').val(relNumber);
					$('#relId').val(relId);
					$('#relType').val(relType);
					$('#relType').select2({value: relType});
					$('#caliber').val(caliber);
					$('#caliber').select2({value: caliber});
					$('#totolTradeAmount').val(totalTradeAmount);
					$('#total').val(totalTradeAmount);
				} else {
					$.messager.alert(data.msg);
				}
			}
		});
	}

	//判断是否重大关联交易
	function isGreatTrade() {
		var relId = $('#relId').val();
		var relType = $('#relType').val();
		var tradeAmount = $('#tradeAmount').val();
		var totolTradeAmount = $('#totolTradeAmount').val();
		$('#totolTradeAmount').val(parseFloat(totolTradeAmount) + parseFloat(tradeAmount));
		if (${opt == 'edit'}) {
			var trade = $('#amount').val();
			totolTradeAmount = $('#total').val();
			$('#totolTradeAmount').val(parseFloat(totolTradeAmount) + (parseFloat(tradeAmount) - parseFloat(trade)));
		} else {
			var total = $('#total').val();
			$('#totolTradeAmount').val(parseFloat(total) + parseFloat(tradeAmount));
		}

		if (tradeAmount == '' || relNumber == null) {
			return;
		}
		if (relType == '' || relType == null) {
			return;
		}
		if (relId == '' || relId == null) {
			return;
		}
		var obj = {
			relId: relId,
			relType: relType,
			tradeAmount: tradeAmount
		};
		$.ajax({
			url: '${base}/ins/filBusiness/GreatCheck',
			data: obj,
			type: 'post',
			jsonType: 'json',
			success: function (data) {
				if (data.success) {
					$('#majorTrade').val(data.isGreatTrade);
					$('#majorTrade').select2({value: (data.isGreatTrade)})
				} else {
					$.messager.alert(data.msg);
				}
			}
		});
	}

	function submitHandler() {
		var pid = getIdSelections();
		window.location = "${base}/ins/traOther/submit/" + pid;
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
		// 提交审批  的必须是待提交状态
		//	var status = getStatusSelections();
		var pid =$('#pid').val();
		/*	if (pid == '' || pid == null || pid.length != 1) {
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }*/
		//判断一般流程或是重大流程
		var procDefCode ;
		var majorTrade = $('#majorTrade').val();
		if (majorTrade == "0"){
			procDefCode = 'zybr_relGreat';
		}else {
			procDefCode = 'zybr_relGeneral';
		}
		//if (status == 3) {
		//查询出基本信息 修改状态 放到data中
		$.ajax({
			url: "${base}/ins/traOther/send/" + pid,
			method: "GET",
			data: {"": ""},
			dataType: "JSON",
			traditional: true,
			success: function (data) {
				if (data.success) {
					//开始走工作流
					var objData = data.data;
					var myDate = new Date();
					objData.id = myDate.getTime();
					objData.filNum ='6o'+randomNumber()
					objData.nextApprovalPeople = nextApprovalPeople;
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
								window.location = '${base}/workflow/process/runingList';
							});
						} else {
							$.messager.alert('错误', data.msg, 'error');
						}
					});
				} else {
					$.messager.alert('错误', data.msg, 'error');
				}
			}
		})
		/*	} else {
                alert("当前状态不符合要求！");
            }*/
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
