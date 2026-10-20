<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="list"/>
	<title>关联关系自然人</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="关联关系自然人" subTitle="自然人关系信息" col="12" collapsible="true">
			<%--<bf:hidden id="opt" name="opt" value="${opt}"/>--%>
			<bf:hidden id="fromPid" name="pid" value="${pid}"/>
			<bf:formGroup>
				<div class="col-sm-4 col-sm-offset-2">
					<bc:button type="info" text="基本信息">
						<be:event>
							basicInformation();
						</be:event>
					</bc:button>
					<bc:button type="info" text="自然人信息">
						<be:event>
							<%--affiliateInformation();--%>
						</be:event>
					</bc:button>
					<bc:button type="info" text="机构关系信息">
						<be:event>
							rightsInformation();
						</be:event>
					</bc:button>
				</div>
			</bf:formGroup>
			<bc:table id="affiliateTable" idField="pid" url="${base}/bus/insiderOrg" queryUrl="queryPsnList/${pid}" addUrl="addAffiliate/${fromPid}">
				<bc:toolbar id="add" text="新增" type="button"/>
				<bc:toolbar id="edit" text="编辑" type="button" handler="editAffiliate"/>
				<bc:toolbar id="remove" text="删除" type="button" handler="removeAffiliate"/>
				<bc:column field="pid" title="关联关系表自增长主键" width="100"  visible="false"/>
				<bc:column field="relId" title="关联关系relId" width="100"  visible="false"/>
				<bc:column field="name" title="名称" width="100"/>
				<bc:column field="identifyType" title="证件类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
				<bc:column field="identifyNumber" title="证件号码" width="100"/>
				<bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
				<bc:column field="relCategorySub" title="关系" width="100"/>
				<bc:column field="voteProportion" title="持股比例和表决权" width="100"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

	function basicInformation() {
		var pid = $("#fromPid").val();
		window.location = '${base}/bus/relPsn/edit/'+ pid+'?';
	}
	function rightsInformation() {
		var pid = $("#fromPid").val();
		window.location = '${base}/bus/relPsn/psnRelOrgList/'+pid +'? ';
	}
	function  editAffiliate() {
		var id = getIdSelections();
		var fromPid = $("#fromPid").val();
		if(id != ""){
			window.location = '${base}/bus/relPsn/editAffiliate/' + id+"/"+ fromPid;
		}else {
			$.messager.alert('提示', '请选择自然人。', 'info');
			return "";
		}
	}
	function getIdSelections() {
		return $.map($('#affiliateTable').bootstrapTable('getSelections'), function (row) {
			return row.pid +"/"+ row.relId;
		});
	}
	function removeAffiliate() {
		var relId = getrelId();
		$.ajax({
			url:"${base}/bus/relPsn/removeRelInformation/"+relId,
			method:"POST",
			data:{"":""},
			dataType:"JSON",
			traditional:true,
			success:function(data){
				if(data.success){
					var fromPid = $('#fromPid').val();
					window.location = '${base}/bus/relPsn/affiliateList/'+fromPid;
				}else
				{
					$.messager.alert('错误', data.msg, 'error');
				}
			}
		})
	}
	function getrelId(){
		return $.map($('#affiliateTable').bootstrapTable('getSelections'), function (row) {
			return row.relId;
		});
	}
</script>
</body>
</html>