<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="list"/>
	<title>自然人关联关系金融产品</title>
	<script>
		window.onload=x;
		function x() {
			var value = '${op}';
			if (value != "0"){
				$('#affiliateTable_tb_add').css('display', 'none');
				$('#affiliateTable_tb_remove').css('display', 'none');
				$('#proTable_tb_edit').css('display', 'none');
				$('#proTable_tb_add').css('display', 'none');
			}
		}
	</script>
	<style>
		.returnList{
			text-align: center;
			margin-left: auto;
			margin-bottom: 5px;
			margin-top: 10px;
			margin-right: auto;
		}
	</style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
			<bf:hidden id="opt" name="opt" value="${opt}"/>
			<bf:hidden id="op" name="op" value="${op}"/>
			<bf:hidden id="fromPid" name="fromPid" value="${fromPid}"/>
			<bf:formGroup>
				<div class="col-sm-6">
					<bc:button type="light" text="基本信息">
						<be:event>
							basicInformation();
						</be:event>
					</bc:button>
					<bc:button type="light" text="关系自然人信息">
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
							<%--prosInformation();--%>
						</be:event>
					</bc:button>
				</div>
			</bf:formGroup>
			<bc:ibox title="所属关联金融产品" subTitle="" col="12" collapsible="true">
					<bc:table id="proTable" idField="pid" url="${base}/fil" queryUrl="filRelPsn/proUpOfPsnList/${fromPid}" addUrl="filRelPsn/addProUpOfPsn/${fromPid}">
					<%--<bc:toolbar id="add" text="新增" type="button" />
					<bc:toolbar id="edit" text="编辑" type="button" handler="editPro"/>--%>
<%--					<bc:toolbar id="remove1" text="删除" type="button" handler="removePro"/>--%>
						<%-- <bc:toolbar id="info" text="详情" type="button" handler="viewProUp"/>--%>
					<bc:column field="pid" title="关联关系表自增长主键" width="100"  visible="false"/>
					<bc:column field="relId" title="关联关系relId" width="100"  visible="false"/>
					<bc:column field="proName" title="金融产品名称" width="100"/>
					<bc:column field="proProvider" title="产品批准/注册/备案号" width="100" />
					<bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
					<bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
					<bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
					<bc:column field="isValid" title="该关系是否有效" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
					<bc:operator id="viewProUp" icon="eye" title="查看" handler="viewProUp"/>
				</bc:table>
			</bc:ibox>
			<bc:ibox title="下一级金融产品" subTitle="" col="12" collapsible="true">
				<bc:table id="proDownTable" idField="pid" url="${base}/fil" queryUrl="filRelPsn/proDownOfPsnList/${fromPid}">
					<%--<bc:toolbar id="add" text="新增" type="button"/>--%>
					<%--<bc:toolbar id="edit" text="编辑" type="button" handler="editPro"/>--%>
					<%--<bc:toolbar id="remove" text="删除" type="button" handler="removeAffiliate"/>
                    <bc:toolbar id="info" text="详情" type="button" handler="viewProDown"/>--%>
					<bc:column field="pid" title="关联关系表自增长主键" width="100"  visible="false"/>
					<bc:column field="relId" title="关联关系relId" width="100"  visible="false"/>
					<bc:column field="proName" title="金融产品名称" width="100"/>
					<bc:column field="proProvider" title="产品批准/注册/备案号" width="100" />
					<bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
					<bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
					<bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
					<bc:column field="isValid" title="该关系是否有效" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
					<bc:operator id="viewProDown" icon="eye" title="查看" handler="viewProDown"/>
				</bc:table>
				<bf:formGroup>
					<div class="returnList">
						<bc:button id="returnButton" type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								returnList();
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

	function returnList(){
		var value='${wt}';
		if(value == '1'){
			window.location = '${base}/fil/filRelPsn/list';
		}else if(value == '2'){
			window.location = '${base}/workflow/approve/doneList'
		}else{
			window.location = '${base}/workflow/process/completeList'
		}
	}

	function basicInformation() {
		var value = '${wt}';
		var pid = $("#fromPid").val();

		window.location = '${base}/fil/filRelPsn/compared/' + pid + '?op=1&wt=' + value;

	}

	function affiliateInformation() {
		var value = '${wt}';
		var pid = $("#pid").val();
		window.location = '${base}/fil/filRelPsn/affiliateList/' + pid + '?op=1&wt='+value;
	}

	function rightsInformation() {
		var value = '${wt}';
		var pid = $("#fromPid").val();
		window.location = '${base}/fil/filRelPsn/psnRelOrgList/' + pid + '?op=1&wt=' + value;

	}

	function proRelInformation() {
		var value = '${wt}';
		var pid = $("#fromPid").val();
		window.location = '${base}/fil/filRelPsn/proOfPsnList/' + pid + '?op=1&wt=' + value;
	}


    function viewProUp() {
        var relId = getrelId();
        var fromPid = $('#fromPid').val();
        // var type="UP";
        if (relId == '' || relId == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location="${base}/fil/filRelPsn/viewPro/"+ relId+"/" + "?op=1";
    }

    function viewProDown() {
        var relId = getProDownrelId();
        var fromPid = $('#fromPid').val();
        // var type="UP";
        if (relId == '' || relId == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location="${base}/fil/filRelPsn/viewPro/"+ relId+"/"+ "?op=1";
    }

	function  editPro() {
		var id = getIdSelections();
		var fromPid = $("#fromPid").val();
		if(id != ""){
			window.location = '${base}/fil/filRelPsn/editPro/' + id+"/"+ fromPid;
		}else {
			$.messager.alert('提示', '请选择金融产品。', 'info');
			return "";
		}
	}
	function getIdSelections() {
		return $.map($('#proTable').bootstrapTable('getSelections'), function (row) {
			return row.pid ;
		});
	}
	function removePro() {
		var relId = getrelId();
		// alert(relId);
		if (relId=='' || relId==null){
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
		}
		$.ajax({
			url:"${base}/fil/filRelPsnOfPro/removeRelProOfPsn/"+relId,
			method:"POST",
			data:{"":""},
			dataType:"JSON",
			traditional:true,
			success:function(data){
				if(data.success){
					var fromPid = $('#fromPid').val();
					window.location = '${base}/fil/filRelPsn/proOfPsnList/'+fromPid+'?op=0';
				}else
				{
					$.messager.alert('错误', data.msg, 'error');
				}
			}
		})
	}
	function getrelId(){
		// alert($('#proTable').bootstrapTable('getSelections'));
		// var b = $('#proTable').bootstrapTable('getSelections');
		// alert(JSON.stringify(b[0]));
		// if(b.length == 1){
		// 	return b[0].relId;
		// }

		return $.map($('#proTable').bootstrapTable('getSelections'), function (row) {
			return row.relId;
		});
	}

    function getProDownrelId(){
        return $.map($('#proDownTable').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }
</script>
</body>
</html>