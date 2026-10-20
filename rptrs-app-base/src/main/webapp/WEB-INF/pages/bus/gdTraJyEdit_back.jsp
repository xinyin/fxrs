<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>GdTraZy</title>

	<script>
        function getShareholder() {
            var shareholdersCode = $('#shareholdersCode').val();
            $.ajax({
                type: 'GET',
                url: '${base}/bus/gdTraJy/getShareholder',
                data: {code: shareholdersCode},
                dataType: 'json',
                success: function (data) {
                    var shareHolder = data.shareHolder;
                    var shareholdersType = data.shareholdersType;
                    $('#shareholdersName').val(shareHolder.name);
                    $('#shareholdersType').val(shareholdersType);
                    $('#holeShareAmount').val(shareHolder.holeShareAmount);
                    $('#frozenShareAmount').val(shareHolder.frozenShareAmount);
                    $('#domesticOrForeign').val(shareHolder.domesticOrForeign);
                }
            });
        }

        function getAllIdSelections() {
            return $.map($('#docTable').bootstrapTable('getData'), function (row) {
                return row.pid//返回数据行中的id值
            });
        }
	</script>

</head>
<body class="gray-bg" style="background: white">
	<div class="row">
		<bc:ibox id="gdTraZyBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${gdTraZy.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/gdTraZy/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="股东代码" col="4" formControl="true"/>
						<bf:text id="shareholdersCode" name="shareholdersCode" col="8" value="${gdTraZy.shareholdersCode}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="股东名称" col="4" formControl="true"/>
						<bf:text id="shareholdersName" name="shareholdersName" col="8" value="${gdTraZy.shareholdersName}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="股东类型" col="4" formControl="true"/>
						<bf:text id="shareholdersType" name="shareholdersType" col="8" value="${gdTraZy.shareholdersType}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="是否国有企业" col="4" formControl="true"/>
						<bf:text id="stateOwned" name="stateOwned" col="8" value="${gdTraZy.stateOwned}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="境内境外" col="4" formControl="true"/>
						<bf:text id="domesticOrForeign" name="domesticOrForeign" col="8" value="${gdTraZy.domesticOrForeign}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="总持有股数" col="4" formControl="true"/>
						<bf:text id="holeShareAmount" name="holeShareAmount" col="8" value="${gdTraZy.holeShareAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="总质押股数" col="4" formControl="true"/>
						<bf:text id="pledgedShareAmount" name="pledgedShareAmount" col="8" value="${gdTraZy.pledgedShareAmount}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="操作类型" col="4" formControl="true"/>
						<bf:text id="operationType" name="operationType" col="8" value="${gdTraZy.operationType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="操作股数" col="4" formControl="true"/>
						<bf:text id="operationAmount" name="operationAmount" col="8" value="${gdTraZy.operationAmount}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="质押合同号" col="4" formControl="true"/>
						<bf:text id="pledgedContract" name="pledgedContract" col="8" value="${gdTraZy.pledgedContract}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="质押生效时间" col="4" formControl="true"/>
						<bf:text id="effectiveTime" name="effectiveTime" col="8" value="${gdTraZy.effectiveTime}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="解押时间" col="4" formControl="true"/>
						<bf:text id="unpledgedTime" name="unpledgedTime" col="8" value="${gdTraZy.unpledgedTime}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="提交审批时间" col="4" formControl="true"/>
						<bf:text id="submitTime" name="submitTime" col="8" value="${gdTraZy.submitTime}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="审批状态" col="4" formControl="true"/>
						<bf:text id="status" name="status" col="8" value="${gdTraZy.status}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="状态说明" col="4" formControl="true"/>
						<bf:text id="statusComment" name="statusComment" col="8" value="${gdTraZy.statusComment}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="经办人" col="4" formControl="true"/>
						<bf:text id="operator" name="operator" col="8" value="${gdTraZy.operator}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="审批人" col="4" formControl="true"/>
						<bf:text id="approver" name="approver" col="8" value="${gdTraZy.approver}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="质押融资额" col="4" formControl="true"/>
						<bf:text id="pledgedAmount" name="pledgedAmount" col="8" value="${gdTraZy.pledgedAmount}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="质权人名称" col="4" formControl="true"/>
						<bf:text id="name" name="name" col="8" value="${gdTraZy.name}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="质权人证件类型" col="4" formControl="true"/>
						<bf:text id="identifyType" name="identifyType" col="8" value="${gdTraZy.identifyType}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="质权人证件号码" col="4" formControl="true"/>
						<bf:text id="identifyNumber" name="identifyNumber" col="8" value="${gdTraZy.identifyNumber}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="解押说明" col="4" formControl="true"/>
						<bf:text id="unpledgedComment" name="unpledgedComment" col="8" value="${gdTraZy.unpledgedComment}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>


					<%--<div id="tableDiv" style="width: 80%;margin-left: 12%">
						<bc:table id="docTable" idField="pid" url="${base}/bus/gdFile" multiple="true"
								  selectFirst="false" queryUrl="queryList?traSerial=${gdTraJy.frozenSerial}">
							<bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
							<bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>
							<bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
							<bc:column field="pid" title="主键" width="100" visible="false"/>
							<bc:column field="fileName" title="文件名" width="100"/>
							<bc:column field="fileType" title="文件类型" width="100"/>
							<bc:column field="fileSize" title="大小" width="100"/>
							<bc:column field="uploadOrg" title="上传机构" width="100"/>
							<bc:column field="uploadDep" title="上传部门" width="100"/>
							<bc:column field="createTime" title="上传时间" width="100"/>
						</bc:table>
					</div>--%>

					<div class="col-sm-4 col-sm-offset-5">
						<bc:button type="danger" text="保存" icon="save">
							<%--<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/bus/gdTraJy/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/gdTraJy/list';
											});
										}
										else
										{
											$.messager.alert('错误', data.msg, 'error');
										}
									});
								}
							</be:event>--%>
						</bc:button>
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/gdTraZy/list';
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
	<script src="../../static/js/jquery.dialog.js"></script>


</body>
</html>
