<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<style>
	.boxxx {
		border-bottom: 1px dashed #e7eaec;
	}

	.center div {
		float: none;
		margin: 1% 0;
	}

	.lefts, .rights {
		border: 1px solid #cccccc;
		width: 40%;
	}

	.lefts p, .rights p {
		width: 26%;
		text-align: center;
		margin: 3% auto;
		padding: 2%;
		background: #0d8ddb;
		color: white;
		border-radius: 6px;
	}

	.lefts div, .rights div {
		float: none;
		margin: 1% 0;
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
		top: 43px;

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
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp" %>
	<br:css pack="form"/>
	<title>LimitTrans</title>
</head>
<body class="gray-bg">
<div class="row">
	<bc:ibox id="limitTransBox" title="" subTitle="" col="12" collapsible="true">
		<bf:form id="editForm" method="post" validation="true">
			<bf:hidden id="opt" name="opt" value="${opt}"/>
			<input id="pid" name="pid" value="${limitTrans.pid}" type="hidden">
			<input id="befPid" name="befPid" type="hidden">
			<input id="latPid" name="latPid" type="hidden">
			<div class="box">
				<div class="lefts">
					<p>被调剂方</p>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="交易机构" col="4" style="line-height:34px" formControl="true"/>
							<bf:select id="tranOrg" name="tranOrg" col="8" dataSource="custom"
									   url="bus/limit/getTranOrgItems" value="${limitTrans.tranOrg}" excludes="超级管理员" disabled="true">
								<bf:option text="All" value=""></bf:option>
								<bv:notEmpty/>
							</bf:select>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="交易类型" col="4" style="line-height:34px" formControl="true"/>
							<bf:select id="tradeType" name="tradeType" col="8" dataSource="custom"
									   url="bus/limit/getTradeTypeItems" value="${limitTrans.tradeType}"
									   disabled="true">
								<bf:option text="请选择" value=""></bf:option>
								<bv:notEmpty/>
							</bf:select>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="业务类型" col="4" style="line-height:34px" formControl="true"/>
							<bf:select id="businessType" name="businessType" col="8" dataSource="custom"
									   url="bus/limit/getBuinessTypeItems"
									   value="${limitTrans.businessType}" disabled="true">
								<bf:option text="请选择" value=""></bf:option>
								<bv:notEmpty/>

							</bf:select>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="产品名称" col="4" style="line-height:34px" formControl="true"/>
							<bf:lookup id="proName" name="proName" col="7" title="业务级别名称"
									   url="${base}/bus/limit/proNameListTree" winId="productViewId"
									   propText="proName" propValue="level"
									   callback="result" onSelect="onSelect1"  disabled="true">
							</bf:lookup>
						</bc:col>
					</bf:formGroup>

					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="预计额度" col="5" style="line-height:34px" formControl="true"/>
							<bf:text id="preLimit" name="preLimit" col="8" value="${limitTrans.preLimit}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
						<bc:col col="12">
							<bc:label text="调剂后" col="4" style="line-height:34px" formControl="true"/>
							<bf:text id="preLimitLater" name="preLimitLater" col="8" value="${limitTrans.preLimitLater}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="剩余额度" col="5" style="line-height:34px" formControl="true"/>
							<bf:text id="pasLimit" name="pasLimit" col="8" value="${limitTrans.pasLimit}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
						<bc:col col="12">
							<bc:label text="调剂后" col="4" style="line-height:34px" formControl="true"/>
							<bf:text id="pasLimitLater" name="pasLimitLater" col="8" value="${limitTrans.pasLimitLater}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="预警级别" col="5" style="line-height:34px" formControl="true"/>
							<bf:text id="warnLeve" name="warnLeve" col="8" value="${limitTrans.warnLeve}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
						<bc:col col="12">
							<bc:label text="调剂后" col="4" style="line-height:34px" formControl="true"/>
							<bf:text id="warnLeveLater" name="warnLeveLater" col="8" value="${limitTrans.warnLeveLater}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
					</bf:formGroup>
				</div>
				<div class="center">
					<bc:col col="12">
						<bc:label text="调剂金额" col="12" formControl="true"/>
						<bf:text id="tranAmount" name="tranAmount" col="12" value="${limitTrans.tranAmount}" disabled="true">
						</bf:text>
					</bc:col>
						<%--======================================================================================================================================--%>
					<div class="col-sm-12 col-sm-offset-5" style="  text-align: center;">
						<bc:button id="btnQuery" type="danger" text="确定" icon="" click="limitMony()"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					</div>
				</div>
				<div class="rights">
					<p>调剂方</p>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="交易机构" col="4" style="line-height:34px" formControl="true"/>
							<bf:select id="tranOrgL" name="tranOrgL" col="8" dataSource="custom"
									   url="bus/limit/getTranOrgItems" value="${limitTrans.tranOrgL}" excludes="" disabled="true">
								<bf:option text="All" value=""></bf:option>
								<bv:notEmpty/>
							</bf:select>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="交易类型" col="4" style="line-height:34px" formControl="true"/>
							<bf:select id="tradeTypeL" name="tradeTypeL" col="8" dataSource="custom"
									   url="bus/limit/getTradeTypeItems" value="${limitTrans.tradeTypeL}"
									   disabled="true">
								<bf:option text="请选择" value=""></bf:option>
								<bv:notEmpty/>
							</bf:select>

						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">

						<bc:col col="12">
							<bc:label text="业务类型" col="4" style="line-height:34px" formControl="true"/>
							<bf:select id="businessTypeL" name="businessTypeL" col="8" dataSource="custom"
									   url="bus/limit/getBuinessTypeItems" value="${limitTrans.businessTypeL}"
									   disabled="true">
								<bf:option text="请选择" value=""></bf:option>
								<bv:notEmpty/>
							</bf:select>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="产品名称" col="4" style="line-height:34px" formControl="true"/>
							<bf:lookup id="proNameL" name="proNameL" col="7" title="业务级别名称"
									   url="${base}/bus/limit/proNameListTree" winId="productViewId"
									   propText="proName" propValue="level"
									   callback="result" onSelect="onSelect2"  disabled="true">
							</bf:lookup>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="预计额度" col="5" style="line-height:34px" formControl="true"/>
							<bf:text id="preLimitL" name="preLimitL" col="8" value="${limitTrans.preLimitL}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
						<bc:col col="12">
							<bc:label text="调剂后" col="4" style="line-height:34px" formControl="true"/>
							<bf:text id="preLimitLaterL" name="preLimitLaterL" col="8"
									 value="${limitTrans.preLimitLaterL}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">
						<bc:col col="12">
							<bc:label text="剩余额度" col="5" style="line-height:34px" formControl="true"/>
							<bf:text id="pasLimitL" name="pasLimitL" col="8" value="${limitTrans.pasLimitL}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
						<bc:col col="12">
							<bc:label text="调剂后" col="4" style="line-height:34px" formControl="true"/>
							<bf:text id="pasLimitLaterL" name="pasLimitLaterL" col="8"
									 value="${limitTrans.pasLimitLaterL}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
					</bf:formGroup>
					<bf:formGroup hr="dashed" hrp="bottom">

						<bc:col col="12">
							<bc:label text="预警级别" col="5" style="line-height:34px" formControl="true"/>
							<bf:text id="warnLeveL" name="warnLeveL" col="8" value="${limitTrans.warnLeveL}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
						<bc:col col="12">
							<bc:label text="调剂后" col="4" style="line-height:34px" formControl="true"/>
							<bf:text id="warnLeveLaterL" name="warnLeveLaterL" col="8"
									 value="${limitTrans.warnLeveLaterL}"
									 disabled="true">
								<bv:notEmpty/>
							</bf:text>
						</bc:col>
					</bf:formGroup>
				</div>
			</div>
			<div id="tableDiv" style="width: 95%;margin:0 auto">
				<bc:table id="relFileTable" idField="pid" url="${base}/bus/relFile" multiple="true"
						  selectFirst="false" queryUrl="queryList?relSerial=${limitTrans.seqTrade}">
					<%--<bc:toolbar id="exportIn" text="上传额度调剂认同书" type="button" handler="exportIn"/>
					<bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>--%>
					<bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
					<bc:column field="pid" title="主键" width="100" visible="false"/>
					<bc:column field="fileName" title="文件名称" width="100"/>
					<bc:column field="fileUrl" title="文件路径" width="100" visible="false"/>
					<bc:column field="fileType" title="文件类型" width="100"/>
					<bc:column field="fileVersion" title="版本号" width="100" visible="false"/>
					<bc:column field="approveStatus" title="审批状态" width="100" visible="false"/>
					<bc:column field="fileSize" title="文件大小" width="100"/>
					<bc:column field="relSerial" title="交易流水号" width="100" visible="false"/>
					<bc:column field="uploadAgency" title="上传机构" width="100"/>
					<bc:column field="uploadDepartment" title="上传部门" width="100"/>
					<bc:column field="modifiedTime" title="上传时间" width="100"/>
				</bc:table>
			</div>
			<bf:formGroup>
				<div class="col-sm-12 col-sm-offset-5">
					<bc:button type="primary" text="取消" icon="arrow-circle-left">
						<be:event>
							window.location = '${base}/bus/limitTrans/list';
						</be:event>
					</bc:button>
				</div>
			</bf:formGroup>
		</bf:form>
	</bc:ibox>
</div>
</div>
<div>
	<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter" id="hiddenBtn" style="display: none">
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
							<img src="../../static/img/sendfile.png"/>
							<a href="javascript:;" class="file">选择文件
								<input type="file" class="form-control" id="file" name="file" multiple
									   style="z-index:1;padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
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
<script type="text/javascript">
	function exportIn() {
		$("#hiddenBtn").click();
	}

	$(document).ready(function () {
	});

	/*调剂金额确认提交*/
	function limitMony() {
		var preLimit = $("input[name=preLimit]").val();
		var pasLimit = $("input[name=pasLimit]").val();
		var preLimitL = $("input[name=preLimitL]").val();
		var pasLimitL = $("input[name=pasLimitL]").val();
		var tranAmount = $("input[name=tranAmount]").val();
		var befPid = $("input[name=befPid]").val();
		var latPid = $("input[name=latPid]").val();
		// alert(preLimit + '-' + pasLimit + '-' + preLimitL + '-' + pasLimitL + '-' + tranAmount)

		if(preLimitL == null || preLimitL == '' || pasLimitL == null || pasLimitL == ''){
			$.messager.alert('警告',"请选择调剂方有预计额度的产品名称！",'warning');
			return ;
		}
		$.ajax({
			type: "post",
			url: "${base}/bus/limitTrans/subtractTranAmount",
			data: {
				"befPid": befPid,
				"latPid": latPid,
				"preLimit": preLimit,
				"pasLimit": pasLimit,
				"preLimitL": preLimitL,
				"pasLimitL": pasLimitL,
				"tranAmount": tranAmount
			},
			success: function (data) {
				if (data.success) {
					$('#preLimitLater').val(data.preLimitLater);
					$('#pasLimitLater').val(data.pasLimitLater);
					$('#preLimitLaterL').val(data.preLimitLaterL);
					$('#pasLimitLaterL').val(data.pasLimitLaterL);
					$('#warnLeveLater').val(data.warnLeveLater);
					$('#warnLeveLaterL').val(data.warnLeveLaterL);
				} else {
					alert(data.msg)
					$.messager.alert('警告',data.msg,'warning');
				}
			}
		});
	}


	/*被调剂产品选择*/
	function onSelect1() {
		var proName = $("input[name=proName]").val();
		$('#befPid').val(proName.split('-')[1]);
		$.ajax({
			type: "post",
			url: "${base}/bus/limitTrans/selectbuinessL",
			data: {"unitCodes": proName},
			success: function (data) {
				if (data.success) {
					var tradeType = data.tradesType;
					var businessType = data.businessType;
					var preLimit = data.preLimit;
					var remLimit = data.remLimit;
					var warnLeve = data.warnLeve;
					$('#tradeType').val(tradeType);
					$('#tradeType').select2({value: tradeType});
					$('#businessType').val(businessType);
					$('#businessType').select2({value: businessType});
					// preLimit   pasLimit   remLimit
					$('#preLimit').val(preLimit);
					$('#pasLimit').val(remLimit);
					$('#warnLeve').val(warnLeve);
				} else {
					$.messager.alert('警告',"该节点下没有设置预计额度，请选择其他节点",'warning');
				}

			}
		});
	}

	function onSelect2() {
		if ($("input[name=proName]").val() == '' || $("input[name=proName]").val() == undefined) {
			$.messager.alert('警告',"请先设置被调剂方！",'warning');
			// alert('请先设置被调剂方！');
			return false;
		}
		if ($("input[name=proName]").val().split('-')[3] != $("input[name=proNameL]").val().split('-')[3]) {
			$.messager.alert('警告',"请选择相同父级节点下的节点进行调剂！",'warning');
			// alert('请选择相同父级节点下的节点进行调剂！');
			return false;
		}
		if ($("input[name=proName]").val() == $("input[name=proNameL]").val()) {
			$.messager.alert('警告',"不可选择相同节点！",'warning');
			// alert('不可选择相同节点！');
			return false;
		}

		var proNameL = $("input[name=proNameL]").val();
		$('#latPid').val(proNameL.split('-')[1]);
		$.ajax({
			type: "post",
			url: "${base}/bus/limitTrans/selectbuinessL",
			data: {"unitCodes": proNameL},
			success: function (data) {
				if (data.success) {
					var tradeType = data.tradesType;
					var businessType = data.businessType;
					var preLimit = data.preLimit;
					var remLimit = data.remLimit;
					var warnLeve = data.warnLeve;
					$('#tradeTypeL').val(tradeType);
					$('#tradeTypeL').select2({value: tradeType});
					$('#businessTypeL').val(businessType);
					$('#businessTypeL').select2({value: businessType});
					$('#preLimitL').val(preLimit);
					$('#pasLimitL').val(remLimit);
					$('#warnLeveL').val(warnLeve);
				} else {
					$.messager.alert('警告',"该节点下没有设置预计额度，请选择其他节点",'warning');
					// alert('该节点下没有设置预计额度，请选择其他节点')
				}

			}
		});
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
				url: '${base}/bus/relFile/removeFile/' + pids,
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
						$('#relFileTable').bootstrapTable('load', showData);
					}
				}
			})
		}
	}
	//查看文件
	viewFile = function () {
		//选中的文件
		var pid = getIdSelections();
		//获得该条数据
		var row = getselectData();
		var fileName = row[0].fileName;
		if (pid == '' || pid == undefined) {
			alert("请选中一个文件");
			return;
		}
		if (pid.indexOf(',') != -1) {
			alert("只能选中一个文件");
			return;
		}
		window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
				+ encodeURIComponent('${base}/bus/relFile/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
	}
	//文件表所有选中的数据
	function getselectData() {
		return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
			return row//返回数据行中的id值
		});
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
		var unitCodes = $('#tranOrg').val();
		var formData = new FormData($('#uploadForm')[0]);
		formData.append("uploadAgency", unitCodes);
		formData.append("uploadDepartment", unitCodes);
		$.ajax({
			type: 'post',
			url: '${base}/bus/relFile/uploadFile', //上传文件的请求路径必须是绝对路径
			data: formData,
			cache: false,
			processData: false,
			contentType: false,
			success: function (data) {
				$("#close").click();
				alert(data.msg);
				if (data.success) {
					//处理数据
					var files = data.relFile;
					var count = files.length;
					//获取原有表单的数据
					var rows = getData();
					rows = rows.concat(files);
					//构造表格显示数据
					var total = rows.length;
					var showData = {'total': total, 'rows': rows}
					$('#relFileTable').bootstrapTable('load', showData);
				}
			},
			error: function () {
				$("#close").click();
				alert("上传失败,请重试");
			}
		})
	});
	$(document).ready(function () {
	});
	//表单提交数据
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
	//文件表数据
	function getData() {
		return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
			return row
		});
	}
	function getAllIdSelections() {
		return $.map($('#relFileTable').bootstrapTable('getData'), function (row) {
			return row.pid//返回数据行中的id值
		});
	}
	function getIdSelections() {
		return $.map($('#relFileTable').bootstrapTable('getSelections'), function (row) {
			return row.pid//返回数据行中的id值
		});
	}
	function basicInformation() {
		var pid = $('#pid').val();
		window.location.href = "${base}/bus/relOrg/edit/" + pid;
	}
	function affiliateInformation() {
		var pid = $('#pid').val();
		if (pid == null || pid == "") {
			alert("请先保存基本信息!")
		} else {
			window.location = '${base}/bus/relOrg/listAffi/' + pid;
		}
	}
</script>
</body>
</html>
