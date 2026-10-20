<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>自然人关联信息备案</title>
	<script>
		window.onload=x;
		function x() {
			// alert("进入");
			var value = '${op}';
			if (value != "0"){
				// alert("进入其他");
				$('#relPsnTable_tb_add').css('display', 'none');
				$('#relPsnTable_tb_remove').css('display', 'none');
			}
		}
		function editHandler(){
			var pid = getIdSelections();
			var relId = getrelId();
			var orgid = $('#orgid').val();
			if( relId !== null && relId !=""){
				window.location = "${base}/fil/relOrg/editAffiliate/"+pid+"/"+relId+"/"+orgid;
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
        function getrelId1(){
            return $.map($('#relPsnTable1').bootstrapTable('getSelections'), function (row) {
                return row.relId;
            });
        }
		function addHandler(){
			var orgid = $('#orgid').val();
			// alert(orgid);
			window.location = "${base}/fil/relOrg/addAffiliate/"+orgid;
		}


        function viewAffiliate1() {
            var relId = getrelId();
            var fromPid = $('#orgid').val();
            var type="UP";
            if (relId=='' || relId==null){
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            window.location="${base}/fil/relOrg/viewAffiliate/"+ relId+"/"+fromPid+"/"+type;
        }

        function viewAffiliate2() {
            var relId = getrelId1();
            var fromPid = $('#orgid').val();
            var type="DOWN";
            if (relId=='' || relId==null){
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            window.location="${base}/fil/relOrg/viewAffiliate/"+ relId+"/"+fromPid+"/"+type;
        }



		function removeHandler() {
			var relId = getrelId();
            if (relId=='' || relId==null){
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            $.messager.confirm('提示', '确定要删除吗？', function(r){
                if(r){
                    <%--window.location = "${base}/fil/rel/removeOrg/" + relId;--%>
                    $.ajax({
                        url:"${base}/fil/rel/removeRel/"+relId,
                        method:"POST",
                        data:{"":""},
                        dataType:"JSON",
                        traditional:true,
                        success:function(data){
                            if(data.success){
                                var orgid = $('#orgid').val();
                                window.location = '${base}/fil/relOrg/listAffi/'+orgid+'?op=0';
                            }else
                            {
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        }
                    })
                }
            })
		}
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

	</script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="" subTitle="" col="12" collapsible="true">
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
				<bc:col col="12">
					<bc:button type="light" text="基本信息">
						<be:event>
							basicInformation();
						</be:event>
					</bc:button>
					<bc:button id = "psnRelInformation" type="primary" text="关系自然人信息">
						<be:event>
							<%--affiliateInformation();--%>
						</be:event>
					</bc:button>
					<bc:button id ="orgRelInformation" type="light" text="关系机构信息">
						<be:event>
							orgRelInformation();
						</be:event>
					</bc:button>
					<bc:button id ="proRelInformation" type="light" text="关系金融产品">
						<be:event>
							proRelInformation();
						</be:event>
					</bc:button>
				</bc:col>
			</bf:formGroup>
			<bc:ibox title="所属关联自然人" subTitle="" col="12" collapsible="true">
				<bc:table id="relPsnTable" idField="pid" url="${base}/fil/relOrg" queryUrl="queryPsnList?orgid=${orgid}" addUrl="addAffiliate/${orgid}">
					<bc:toolbar id="add" text="新增" type="button" handler="addHandler"/>
					<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
					<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
					<%--<bc:toolbar id="info" text="详情" type="button" handler="viewAffiliate1"/>--%>
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
					<bc:column field="shareholdingRatio" title="持股比例或表决权(%)" width="100"/>
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
					<bc:operator id="viewAffiliate1" icon="eye" title="查看" handler="viewAffiliate1"/>
				</bc:table>
			</bc:ibox>
			<bc:ibox title="下级关联自然人" subTitle="" col="12" collapsible="true">
				<bc:table id="relPsnTable1" idField="pid" url="${base}/fil/relOrg" queryUrl="queryDownPsnList?orgid=${orgid}" addUrl="addAffiliate/${orgid}">
					<%--<bc:toolbar id="add" text="新增" type="button"/>--%>
					<%--<bc:toolbar id="add" text="新增" type="button" handler="addHandler"/>--%>
					<%--<bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
					<bc:toolbar id="info" text="详情" type="button" handler="viewAffiliate2"/>--%>
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
					<bc:column field="shareholdingRatio" title="持股比例或表决权(%)" width="100"/>
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
					<bc:operator id="viewAffiliate2" icon="eye" title="查看" handler="viewAffiliate2"/>
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
		<%--<bf:formGroup>--%>
			<%--<div class="col-sm-4 col-sm-offset-2">--%>
				<%--<bc:button type="primary" text="下一步" icon="arrow-circle-right">--%>
					<%--<be:event>--%>
						<%--var orgid = $('#orgid').val();--%>
						<%--window.location = '${base}/fil/rel/list/' + orgid;--%>
					<%--</be:event>--%>
				<%--</bc:button>--%>
			<%--</div>--%>
		<%--</bf:formGroup>--%>
	</div>
</div>
<br:js pack="list"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
</body>
</html>