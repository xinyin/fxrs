<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>RelFunds</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="RelFundsBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:hidden id="pid" name="pid" value="${relFunds.pid}"/>
				<bf:formGroup  hrp="bottom">
					<bc:col col="4">
						<bc:label cls="required" text="投资余额统计日期" col="5" formControl="true"/>
						<bf:datebox id="investmentBalanceTime" name="investmentBalanceTime" col="7"
									value="${relFunds.investmentBalanceTimeString}">
							<bv:date format="YYYY-MM-DD" message="只能输入合理的日期，类型为YYYY-MM-DD类型"/>
							<bv:notEmpty/>
						</bf:datebox>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hrp="bottom">
				<bf:formGroup col="4">
					<bc:label cls="required" text="关联方名称" col="5" formControl="true"/>
					<bf:text id="relName" name="relName" col="7" click="relNameSelect()"
							 value="${relFunds.relName}" disabled="${opt == 'view'}">
						<bv:notEmpty/>
					</bf:text>
				</bf:formGroup>
				</bf:formGroup>
				<bf:formGroup hrp="bottom">
				<bf:formGroup col="4">
					<bc:label text="关联方证件号码" col="5" formControl="true"/>
					<bf:text id="relIdentifyNumber" name="relIdentifyNumber" col="7"
							 value="${relFunds.relIdentifyNumber}"
							 disabled="true">
						<%--<bv:notEmpty/>--%>
					</bf:text>
				</bf:formGroup>
				</bf:formGroup>
				<bf:formGroup hrp="bottom">
				<bf:formGroup col="4">
					<bc:label text="关联方类型" col="5" formControl="true"/>
					<bf:select id="relType" name="relType" col="7" dataSource="custom"
							   url="sys/dictItem/getDictItems/INS-relType" disabled="true" value="${relFunds.relType}">
						<bf:option text="-" value=""></bf:option>
					</bf:select>
				</bf:formGroup>


				</bf:formGroup>
				<bf:formGroup hrp="bottom">
				<bf:formGroup col="4">
					<bc:label cls="required" text="资金运用投资余额" col="5" formControl="true"/>
					<bf:text id="fundsInvestmentBalance" name="fundsInvestmentBalance" col="7" value="${relFunds.fundsInvestmentBalance}"
							 >
						<bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
						<bv:stringLength max="21" message="字段长度最大为20位"/>
						<bv:notEmpty/>
					</bf:text>
				</bf:formGroup>
				</bf:formGroup>

				<bf:formGroup hrp="bottom">
				<bf:formGroup col="4">
					<bc:label cls="required" text="关联方上年末总资产" col="5" formControl="true"/>
					<bf:text id="relTotalAssets" name="relTotalAssets" col="7" value="${relFunds.relTotalAssets}"
							>
						<bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
						<bv:stringLength max="21" message="字段长度最大为20位"/>
						<bv:notEmpty/>
					</bf:text>
				</bf:formGroup>
				</bf:formGroup>

				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="danger" text="保存" icon="save">
							<be:event>
								save();
							</be:event>
						</bc:button>
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/relFunds/list';
							</be:event>
						</bc:button>
					</div>
				</bf:formGroup>
			</bf:form>
		</bc:ibox>
	</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
$(document).ready(function(){
	$(".required").each(function () {
		var $this = $(this);
		$(this).html("<font color='red'>✶</font>" + $this.html());
	});
});
//查询交易对手相关方法
function relNameSelect() {
		$.messager.modal({
			winId: 'relNameSelect',
			width: 700,
			height: 420,
			title: '交易对手信息',
			url: '${base}/bus/relFunds/relNameSelect',
			args: {
				result: function (obj) {
					$("#relId").val(obj["relId"]);
					$("#relName").val(obj["relName"]);
					$('#relType').val(obj["relType"]);
					$('#relType').select2({value: (obj["relType"])});
					$("#relIdentifyNumber").val(obj["relNumber"]);
				}
			},
			onClose: function () {
				if(!checkExistRelated()){
					$.messager.alert('警告', '单一关联方数据已经存在，同一季度不能重复添加！', 'warning');
				}
			}
		});
}

$("#investmentBalanceTime").change(function () {
	if(!checkExistRelated()){
		$.messager.alert('警告', '单一关联方数据已经存在，同一季度不能重复添加！', 'warning');
	}
})


function checkExistRelated() {
	var flag = true;
	var investmentBalanceTime = $("[name='investmentBalanceTime']").val();
	var relNumber = $('#relIdentifyNumber').val();
	var url = '${base}/bus/relFunds/checkExistRelated'
	if (null != investmentBalanceTime && null != relNumber && "" != investmentBalanceTime && "" != relNumber) {
		$.ajax({
			url: url,
			type: 'post',
			async: false,
			data: {
				investmentBalanceTime: investmentBalanceTime,
				relNumber: relNumber
			},
			dataType: 'json',
			success: function (data) {
				if (data.success) {
				} else {
					flag = false;
				}
			}
		});
	}
	return flag;
}

function save() {
	$('#editForm').data('formValidation').validate();
	var isValid = $('#editForm').data('formValidation').isValid();
	var flag = checkExistRelated();
	if (isValid) {
		if (flag) {
			var formData = $('#editForm').serializeAll();
			$.mask.post('${base}/bus/relFunds/save', formData, function (data) {
				if (data.success) {
					$.messager.alert('提示', data.msg, 'info', function () {
						window.location = '${base}/bus/relFunds/list';
					});
				} else {
					$.messager.alert('错误', data.msg, 'error');
				}
			});
		} else {
			$.messager.alert('警告', '单一关联方数据已经存在，同一季度不能重复添加！', 'warning');
		}
	}
}
</script>
</body>
</html>
