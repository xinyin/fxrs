<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>UnSortOut</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="unSortOutBox" title="交易类型确认" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:hidden id="pid" name="pid" value="${pids}"/>
				<div>
					<p style="font-size:15px;color:red">温馨提示：所选交易的交易类型保持后，请在对应交易类型下维护交易数据。</p>
				</div>
				<br>
				<br>
				<bf:formGroup col="6">
					<bc:label text="已选择${total}条交易数据，请确认所属交易类型：" col="8" formControl="true" style="color:blue"/>
					<bf:select id="tradeType" name="tradeType" col="4" dataSource="custom"
							   url="bus/limit/getTradeTypeItems" value="" >
						<bv:notEmpty/>
					</bf:select>
				</bf:formGroup>

				<bf:formGroup col="6">
					<bc:label text="合同编号" col="5" formControl="true"/>
					<bf:text name="contractNo" col="7" placeholder="合同编号"/>
				</bf:formGroup>


				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2" style="margin-left: 45%">
						<bc:button type="primary"  text="保存" icon="save" click="btnSave();">
						</bc:button>
						<bc:button  text="返回">
							<be:event>
								window.location = '${base}/ins/traUnSortOut/list';
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
	
});
//保存
function btnSave(){
		$('#editForm').data('formValidation').validate();
		var dataAll = $('#editForm').serializeAll();



    var contractNo = $("[name='contractNo']").val();

    if (contractNo == undefined || '' == contractNo) {
        $.messager.alert('错误', "合同编号不能为空！", 'error');
        return;
    }
		$.mask.post('${base}/ins/traUnSortOut/save/'+contractNo , dataAll, function(data){
			if(data.success)
			{
				$.messager.alert('提示', '保存成功。', 'info', function(){
					window.location = '${base}/ins/traUnSortOut/list';
				});
			}
			else
			{
				$.messager.alert('错误', data.msg, 'error');
			}
		});

}
</script>
</body>
</html>
