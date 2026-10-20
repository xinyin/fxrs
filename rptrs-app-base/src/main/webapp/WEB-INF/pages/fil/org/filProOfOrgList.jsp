<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="list"/>
	<title>机构关联关系金融产品</title>
	<script>
		window.onload=x;
		function x() {
			var value = '${op}';
			if (value != "0"){
				$('#affiliateTable_tb_add').css('display', 'none');
				$('#affiliateTable_tb_remove').css('display', 'none');
			}
			// var selectPsnId = getIdSelections();
			// if(selectPsnId != '' || selectPsnId != 'undefined'){
			// 	$('#selectPsnId').val(selectPsnId);
			// }
		}
	</script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<bc class="row">
		<bc:ibox title="" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:hidden id="op" name="op" value="${op}"/>
				<bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
			</bf:form>
			<bf:formGroup>
				<bc:col col="6">
					<bc:button type="light" text="基本信息">
						<be:event>
							basicInformation();
						</be:event>
					</bc:button>
					<bc:button type="light" text="自然人信息">
						<be:event>
							affiliateInformation();
						</be:event>
					</bc:button>
					<bc:button type="light" text="关系机构信息">
						<be:event>
							rightsInformation();
						</be:event>
					</bc:button>
					<bc:button type="primary" text="关系金融产品">
						<be:event>
							<%--proRelInfoewrmation();--%>
						</be:event>
					</bc:button>
				</bc:col>
			</bf:formGroup>
			<bc:ibox title="所属关联金融产品" subTitle="" col="12" collapsible="true">
				<bc:table id="orgTable" idField="pid" url="${base}/fil" queryUrl="relOrg/proUpOfOrgList/${fromPid}" addUrl="relOrg/addProUpOfOrg/${fromPid}">
					<bc:toolbar id="add" text="新增" type="button" />
					<bc:toolbar id="edit" text="编辑" type="button" handler="editOrgPro"/>
					<%--<bc:toolbar id="remove1" text="删除" type="button" handler="removeAffiliate"/>
					<bc:toolbar id="info" text="详情" type="button" handler="viewOrgPro"/>--%>
					<bc:column field="pid" title="关联关系表自增长主键" width="100"  visible="false"/>
					<bc:column field="relId" title="关联关系relId" width="100"  visible="false"/>
					<bc:column field="proName" title="金融产品名称" width="100"/>
					<bc:column field="proProvider" title="产品批准/注册/备案号" width="100" />
					<bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
					<bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
					<bc:column field="shareholdingProportion" title="持股比例或表决权" width="100"/>
					<bc:column field="isValid" title="该关系是否有效" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
					<bc:operator id="viewOrgPro" icon="eye" title="查看" handler="viewOrgPro"/>
				</bc:table>
			</bc:ibox>

			<bc:ibox title="下一级金融产品" subTitle="" col="12" collapsible="true">
				<bc:table id="orgDownTable" idField="pid" url="${base}/fil" queryUrl="relOrg/proDownOfOrgList/${fromPid}">
					<%--<bc:toolbar id="info" text="详情" type="button" handler="viewOrgPro1"/>--%>
					<bc:column field="pid" title="关联关系表自增长主键" width="100"  visible="false"/>
					<bc:column field="relId" title="关联关系relId" width="100"  visible="false"/>
					<bc:column field="proName" title="金融产品名称" width="100"/>
					<bc:column field="proProvider" title="产品批准/注册/备案号" width="100" />
					<bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
					<bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
					<bc:column field="shareholdingProportion" title="持股比例或表决权" width="100"/>
					<bc:column field="isValid" title="该关系是否有效" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
					<bc:operator id="viewOrgPro1" icon="eye" title="查看" handler="viewOrgPro1"/>
				</bc:table>
			<bf:formGroup>
			<div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
				<bc:button  type="primary" text="返回" icon="arrow-circle-left">
					<be:event>
						var value='${wt}';
						if(value == '1'){
						window.location = '${base}/fil/relOrg/list';
						}else if(value == '2'){
						window.location = '${base}/workflow/approve/doneList'
						}else{
						window.location = '${base}/workflow/process/completeList'
						}
					</be:event>
				</bc:button>
			</div>
			</bf:formGroup>
			</bc:ibox>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

	function basicInformation() {
		var value = '${wt}';
		var pid = $('#pid').val();

		window.location.href = "${base}/fil/relOrg/edit/" + pid + '?op=1&wt=' + value;

	}

	function affiliateInformation() {
		var value = '${op}';
		var pid = $('#pid').val();

		window.location = '${base}/fil/relOrg/listAffi/' + pid + '?op=1&wt=' + value;


	}


	function orgRelInformation() {
		var value = '${wt}';
		var pid = $('#pid').val();

		window.location = '${base}/fil/relOrg/orgRelList/' + pid + '?op=1&wt=' + value;

	}

	function proRelInformation() {
		var value = '${wt}';
		var pid = $('#pid').val();
		window.location = '${base}/fil/relOrg/proOfOrgList/' + pid + '?op=1&wt=' + value;

	}


	//编辑
	function editOrgPro() {
		// var pid = getIdSelections();
		var relId = getrelId();
		var fromPid = $('#fromPid').val();
		var type="UP";
		if (relId == '' || relId == null) {
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
		window.location="${base}/fil/relOrg/editProUpOfOrg/"+relId+"/"+fromPid+"/"+type + "?op=0";
	}

	//所属详情
	function viewOrgPro() {
		// var pid = getIdSelections();
		var relId = getrelId();
		var fromPid = $('#fromPid').val();
		var type="UP";
		if (relId == '' || relId == null) {
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
		window.location="${base}/fil/relOrg/editProUpOfOrg/"+relId+"/"+fromPid+"/"+type + "?op=1";
	}
	//下一级详情
	function viewOrgPro1() {
		// var pid = getIdSelections();
		var relId = getrelId();
		var fromPid = $('#fromPid').val();
		var type="DOWN";
		if (relId == '' || relId == null) {
			$.messager.alert('错误', "请选择一条数据", 'error');
			return;
		}
		window.location="${base}/fil/relOrg/editProUpOfOrg/"+relId+"/"+fromPid+"/"+type + "?op=1";
	}
	<%--function  editPro() {--%>
	<%--	var id = getIdSelections();--%>
	<%--	var fromPid = $("#fromPid").val();--%>
	<%--	if(id != ""){--%>
	<%--		window.location = '${base}/fil/relOrg/editPro/' + id+"/"+ fromPid;--%>
	<%--	}else {--%>
	<%--		$.messager.alert('提示', '请选择机构。', 'info');--%>
	<%--		return "";--%>
	<%--	}--%>
	<%--}--%>
	function getIdSelections() {
		return $.map($('#orgTable').bootstrapTable('getSelections'), function (row) {
			return row.pid ;
		});
	}
	function removeAffiliate() {
		var relId = getrelId();
		// alert(relId);
		if (relId=='' || relId==null){
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
		}
		$.ajax({
			url:"${base}/fil/relPsnOfPro/removeRelInformation/"+relId,
			method:"POST",
			data:{"":""},
			dataType:"JSON",
			traditional:true,
			success:function(data){
				if(data.success){
					var fromPid = $('#fromPid').val();
					window.location = '${base}/fil/relPro/psnOfProList/'+fromPid+'?op=0';
				}else
				{
					$.messager.alert('错误', data.msg, 'error');
				}
			}
		})
	}
	function getrelId(){
		return $.map($('#orgTable').bootstrapTable('getSelections'), function (row) {
			return row.relId;
		});
	}
</script>
</body>
</html>