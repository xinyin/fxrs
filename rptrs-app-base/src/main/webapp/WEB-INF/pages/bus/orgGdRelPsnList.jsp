<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>自然人关联信息</title>
	<script>
		function editHandler(){
			var pid = getIdSelections();
			var relId = getrelId();
			var orgid = $('#orgid').val();
			if( relId != null){
				window.location = "${base}/bus/relOrg/editAffiliate/"+pid+"/"+relId+"/"+orgid;
			}else {
				$.messager.alert('提示', '请选择自然人。', 'info');
				return "";
			}

		}
		function getIdSelections() {
			return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
				return row.pid;
			});
		}
		function getrelId(){
			return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
				return row.relId;
			});
		}
		function addHandler(){
			var orgid = $('#orgid').val();
			alert(orgid);
			window.location = "${base}/bus/relOrg/addAffiliate/"+orgid;
		}
		function removeHandler() {
			var relId = getrelId();
			<%--window.location = "${base}/bus/rel/removeOrg/" + relId;--%>
			$.ajax({
				url:"${base}/bus/rel/removePsnRel/"+relId,
				method:"POST",
				data:{"":""},
				dataType:"JSON",
				traditional:true,
				success:function(data){
					if(data.success){
						var orgid = $('#orgid').val();
						window.location = '${base}/bus/relOrg/listAffi/'+orgid;
					}else
					{
						$.messager.alert('错误', data.msg, 'error');
					}
				}
			})
		}
		function affiliateInformation(){
			var orgid = $('#orgid').val();
			window.location =  '${base}/bus/relOrg/listAffi/' + orgid;
		}
		function orgRelInformation(){
			var orgid = $('#orgid').val();
			window.location = '${base}/bus/gdInfo/orgRelOrgList/'+orgid;
		}
		function basicInformation(){
			var orgid = $('#orgid').val();
			window.location = "${base}/bus/gdInfo/edit/"+orgid + '/ORG';
		}
		function addRelHandler(){
			var orgid = $('#orgid').val();
			window.location = '${base}/bus/gdInfo/orgRelPsnEdit/'+orgid;
		}
	</script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="自然人关联信息" subTitle="" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
				<bf:hidden id="orgid" name="orgid" value="${orgid}"/>
				<%--<bc:row cls="m-b">--%>
					<%--<bf:formGroup col="4">--%>
						<%--<bc:label text="自增长主键" col="5" formControl="true"/>--%>
						<%--<bf:text name="__LIKES_pid" col="7" placeholder="自增长主键"/>--%>
					<%--</bf:formGroup>--%>
					<%--<bc:col col="3" colOffset="1">--%>
						<%--<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>--%>
						<%--<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>--%>
					<%--</bc:col>--%>
				<%--</bc:row>--%>
			</bf:form>
			<bf:formGroup>
				<div class="col-sm-6 col-sm-offset-2">
					<bc:button type="info" text="基本信息">
						<be:event>
							basicInformation();
						</be:event>
					</bc:button>
					<bc:button id = "psnRelInformation" type="info" text="关系自然人信息">
						<be:event>
<%--							affiliateInformation();--%>
						</be:event>
					</bc:button>
					<bc:button id ="orgRelInformation" type="info" text="关系机构信息">
						<be:event>
							orgRelInformation();
						</be:event>
					</bc:button>
				</div>
			</bf:formGroup>
			<bc:ibox title="所属关联自然人" subTitle="" col="12" collapsible="true">
				<bc:table id="relPsnTable" idField="pid" url="${base}/bus/relOrg" queryUrl="queryPsnList?orgid=${orgid}">
					<%--<bc:toolbar id="add" text="新增" type="button"/>--%>
					<bc:toolbar id="addRel" text="新增" type="button" handler="addRelHandler"/>
					<%--<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>--%>
					<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
					<bc:column field="pid" title="自增长主键" width="100" visible="false"/>
					<bc:column field="relId" title="关联关系主键" width="100" visible="false"/>
					<bc:column field="regCode" title="登记编码" width="100" visible="false"/>
					<bc:column field="orignalOrg" title="源主体银行机构" width="100" visible="false"/>
					<bc:column field="orignalOrgName" title="源主体银行机构名称" width="100" visible="false"/>
					<bc:column field="name" title="名称" width="100"/>
					<bc:column field="sex" title="性別" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Sex" visible="false"/>
					<bc:column field="nationality" title="国别/地区" width="100" visible="false"/>
					<bc:column field="domesticOrForeign" title="境内外标识" width="100" visible="false"/>
					<bc:column field="race" title="民族" width="100" visible="false"/>
					<bc:column field="identifyType" title="证件类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
					<bc:column field="identifyNumber" title="证件号码" width="100"/>
					<bc:column field="birthdate" title="出生日期" width="100" visible="false"/>
					<bc:column field="mobile" title="移动电话" width="100" visible="false"/>
					<bc:column field="email" title="邮箱" width="100" visible="false"/>
					<bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
					<%--<bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DutyCode"/>--%>
					<bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
					<bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
					<bc:column field="status" title="审批状态    未提交/已提交/未审批" width="100" visible="false"/>
					<bc:column field="statusComment" title="审批状态说明" width="100" visible="false"/>
					<bc:column field="maritalStatus" title="婚姻状况" width="100" visible="false"/>
					<bc:column field="validStatus" title="是否有效   0|无效1|有效" width="100" visible="false"/>
					<bc:column field="activeTime" title="生效时间" width="100" visible="false"/>
					<bc:column field="invalidTime" title="失效时间" width="100" visible="false"/>
					<bc:column field="mgerOrg" title="机构名称(默认是总行)" width="100" visible="false"/>
					<bc:column field="upApprovalDepartment" title="上一级审批部门" width="100" visible="false"/>
					<bc:column field="nextApprovalDepartment" title="下一级审批部门" width="100" visible="false"/>
					<bc:column field="upApprovalPeople" title="上一级审批人" width="100" visible="false"/>
					<bc:column field="nextApprovalPeople" title="下一级审批人" width="100" visible="false"/>
					<bc:column field="regulatorYbj" title="银保监口径  0|否 1|是" width="100" visible="false"/>
					<bc:column field="regulatorZjh" title="证监会口径（0|否1|是）" width="100" visible="false"/>
					<bc:column field="regulatorCzb" title="财政部口径（0|否1|是）" width="100" visible="false"/>
					<bc:column field="regulatorOth1" title="其他口径1（0|否1|是）" width="100" visible="false"/>
					<bc:column field="regulatorOth2" title="其他口径2（0|否1|是）" width="100" visible="false"/>
					<bc:column field="isBlack" title="是否黑名单（0|否1|是）" width="100" visible="false"/>
					<bc:column field="topid" title="topid" width="100" visible="false"/>
					<%--<bc:operator id="view" icon="search" title="查看"/>--%>
					<%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
					<%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
				</bc:table>
			</bc:ibox>
			<bc:ibox title="下级关联自然人" subTitle="" col="12" collapsible="true">
				<bc:table id="relPsnTable1" idField="pid" url="${base}/bus/relOrg" queryUrl="queryDownPsnList?orgid=${orgid}" addUrl="addAffiliate/${orgid}">
					<%--<bc:toolbar id="add" text="新增" type="button"/>--%>
					<%--<bc:toolbar id="add" text="新增" type="button" handler="addHandler"/>--%>
					<%--<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>--%>
					<%--<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>--%>
					<bc:column field="pid" title="自增长主键" width="100" visible="false"/>
					<bc:column field="relId" title="关联关系主键" width="100" visible="false"/>
					<bc:column field="regCode" title="登记编码" width="100" visible="false"/>
					<bc:column field="orignalOrg" title="源主体银行机构" width="100" visible="false"/>
					<bc:column field="orignalOrgName" title="源主体银行机构名称" width="100" visible="false"/>
					<bc:column field="name" title="名称" width="100"/>
					<bc:column field="sex" title="性別" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Sex" visible="false"/>
					<bc:column field="nationality" title="国别/地区" width="100" visible="false"/>
					<bc:column field="domesticOrForeign" title="境内外标识" width="100" visible="false"/>
					<bc:column field="race" title="民族" width="100" visible="false"/>
					<bc:column field="identifyType" title="证件类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
					<bc:column field="identifyNumber" title="证件号码" width="100"/>
					<bc:column field="birthdate" title="出生日期" width="100" visible="false"/>
					<bc:column field="mobile" title="移动电话" width="100" visible="false"/>
					<bc:column field="email" title="邮箱" width="100" visible="false"/>
					<bc:column field="relCategory" title="关联关系类别" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelCategory"/>
					<%--<bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-DutyCode"/>--%>
					<bc:column field="relCategorySub" title="关系" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-Rel"/>
					<bc:column field="shareholdingRatio" title="持股比例或表决权" width="100"/>
					<bc:column field="status" title="审批状态    未提交/已提交/未审批" width="100" visible="false"/>
					<bc:column field="statusComment" title="审批状态说明" width="100" visible="false"/>
					<bc:column field="maritalStatus" title="婚姻状况" width="100" visible="false"/>
					<bc:column field="validStatus" title="是否有效   0|无效1|有效" width="100" visible="false"/>
					<bc:column field="activeTime" title="生效时间" width="100" visible="false"/>
					<bc:column field="invalidTime" title="失效时间" width="100" visible="false"/>
					<bc:column field="mgerOrg" title="机构名称(默认是总行)" width="100" visible="false"/>
					<bc:column field="upApprovalDepartment" title="上一级审批部门" width="100" visible="false"/>
					<bc:column field="nextApprovalDepartment" title="下一级审批部门" width="100" visible="false"/>
					<bc:column field="upApprovalPeople" title="上一级审批人" width="100" visible="false"/>
					<bc:column field="nextApprovalPeople" title="下一级审批人" width="100" visible="false"/>
					<bc:column field="regulatorYbj" title="银保监口径  0|否 1|是" width="100" visible="false"/>
					<bc:column field="regulatorZjh" title="证监会口径（0|否1|是）" width="100" visible="false"/>
					<bc:column field="regulatorCzb" title="财政部口径（0|否1|是）" width="100" visible="false"/>
					<bc:column field="regulatorOth1" title="其他口径1（0|否1|是）" width="100" visible="false"/>
					<bc:column field="regulatorOth2" title="其他口径2（0|否1|是）" width="100" visible="false"/>
					<bc:column field="isBlack" title="是否黑名单（0|否1|是）" width="100" visible="false"/>
					<bc:column field="topid" title="topid" width="100" visible="false"/>
					<%--<bc:operator id="view" icon="search" title="详情"/>--%>
					<%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
					<%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
				</bc:table>
			</bc:ibox>
		</bc:ibox>
		<%--<bf:formGroup>--%>
			<%--<div class="col-sm-4 col-sm-offset-2">--%>
				<%--<bc:button type="primary" text="下一步" icon="arrow-circle-right">--%>
					<%--<be:event>--%>
						<%--var orgid = $('#orgid').val();--%>
						<%--window.location = '${base}/bus/rel/list/' + orgid;--%>
					<%--</be:event>--%>
				<%--</bc:button>--%>
			<%--</div>--%>
		<%--</bf:formGroup>--%>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>