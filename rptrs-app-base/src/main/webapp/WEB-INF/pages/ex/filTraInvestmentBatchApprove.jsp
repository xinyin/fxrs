<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp" %>
	<br:css pack="form"/>
	<title>关联交易表（保险）-投资入股类</title>
	<style>
		.bs-checkbox{
			display: none;
		}
	</style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<div id="tableDiv" style="width:95%;margin:0 auto;height: 60%">
			<bc:ibox id="traInvestmentBox" title="" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true" >
				<bc:table id="traInvestmentTable" idField="pid" url="${base}/ins/filInvestment" queryUrl="queryPidsByConditions/${pids}">
					<bc:column field="pid" title="主键" width="100" visible="false"/>
					<bc:column field="relId" title="关联方ID" width="100" visible="false"/>
					<bc:column field="transNum" title="交易流水号" width="100"/>
					<bc:column field="traSubName" title="交易名称" width="100"
							   formatter="custom:sys/dictItem/getDictItems/INS-tranName"/>
					<bc:column field="relName" title="关联方名称" width="100"
							   formatter="custom:sys/dictItem/getDictItems/INS-relName"/>
					<bc:column field="investmentType" title="证件类型" width="100"
							   formatter="custom:sys/dictItem/getDictItems/INS-certType"/>
					<bc:column field="relNumber" title="证照号码" width="100"
							   formatter="custom:sys/dictItem/getDictItems/INS-relNumber"/>
					<bc:column field="investmentType" title="业务类型" width="100"
							   formatter="custom:sys/dictItem/getDictItems/INS-InvestmentType"/>
					<bc:column field="majorTrade" title="是否重大关联交易" width="100" formatter="fixedDict:IsStatusDict"/>
					<bc:column field="contractAmount" title="合同金额（万元/人民币）" width="100"/>
					<bc:column field="tranDate" title="合同签署日期" width="100"/>
					<bc:column field="considerType" title="审议类型" width="100"/>
					<bc:column field="status" title="操作状态" width="100"
							   formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
<%--					<bc:column field="statusComment" title="操作状态说明" width="100"/>--%>
<%--					<bc:operator id="view" icon="search" title="操作/查看"/>--%>
				</bc:table>
			</bf:form>
		</bc:ibox>
		</div>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">
    //跟据审议类型规则给定审议类型得值
    function isConsiderType(){
        //是否为重大关联交易
        var isMajorTrade = $('#majorTrade').val();
        //是否为国家定价
        var isDealPrice = $('#isDealPrice').val();
        //交易金额
        var tradeAmount = $('#tradeAmount').val();
        //关联方类型
        var relType = $('#relType').val();
        //是否为现金认购
        var isCashSubscript = $('#isCashSubscript').val();
        //如果是重大关联交易审议类型为逐笔审议
        if (isMajorTrade != null && isMajorTrade != undefined && isMajorTrade != ''){
            if(isMajorTrade == "0"){
                $('#considerType').val("3");
                $('#considerType').select2({value: "3"});
                return;
            }
        }
        //如果是现金交易审议类型为免予审议
        if (isCashSubscript != null && isCashSubscript != undefined && isCashSubscript != ''){
            if(isCashSubscript == "0"){
                $('#considerType').val('1');
                $('#considerType').select2({value: '1'});
                return;
            }
        }
        //如果是国家定价重大关联交易审议类型为免予审议
        if(isDealPrice != null && isDealPrice != undefined && isDealPrice != ''){
            if(isDealPrice == "0"){
                $('#considerType').val("1");
                $('#considerType').select2({value: "1"});
                return;
            }
        }
        //根据关联方规则判断审议类型
        if(tradeAmount != null && tradeAmount != undefined && tradeAmount != '' && relType != null && relType != undefined && relType != ''){
            if(relType == "PSN" && tradeAmount <= "50"){
                $('#considerType').val("1");
                $('#considerType').select2({value: "1"});
                return;
            }
            if(relType == "ORG" && tradeAmount <= "500"){
                $('#considerType').val("1");
                $('#considerType').select2({value: "1"});
                return;
            }
        }
        $('#considerType').val("2");
        $('#considerType').select2({value: "2"});
    }
    //判断是否为控股子公司来决定子公司名称证件号能不能添加
    function HoldingSubsidiarySub(value){
        var value = value
        if(value == '1'){
            $('#holdingSubsidiaryNumber').attr("disabled","false");
            $('#holdingSubsidiaryName').attr("disabled","false");

        }
        if(value == '0'){
            $('#holdingSubsidiaryNumber').removeAttr("disabled");
            $('#holdingSubsidiaryName').removeAttr("disabled");

        }

    };


	var investmentType = $("#investmentType").val();
	var opt = '${opt}';
	$(document).ready(function () {
		debugger;
		if (opt == 'edit'){
			$("#save1").css("display", "block");
		}
			$("#docTable_tb_exportIn").css("display", "none");
			$("#docTable_tb_removeDoc").css("display", "none");

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
	//上传文件
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
			url: '${base}/ins/traInsenclosure/uploadFile?businessType=0', //上传文件的请求路径必须是绝对路径
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


</script>
</body>
</html>
