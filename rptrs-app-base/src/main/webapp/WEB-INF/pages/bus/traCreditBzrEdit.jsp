<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>关联交易表-授信-保证人信息表</title>
</head>
<body class="gray-bg">
	<div class="row">
		<bc:ibox id="traCreditBzrBox" title="编辑信息" subTitle="" col="12" collapsible="true">
			<bf:form id="editForm" method="post" validation="true">
				<bf:hidden id="opt" name="opt" value="${opt}"/>
				<bf:hidden id="pid" name="pid" value="${traCreditBzr.pid}"/>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="自增主键" col="4" formControl="true"/>
						<bf:text id="pid" name="pid" col="8" value="${traCreditBzr.pid}"  disabled="${opt == 'edit'}">
							<bv:jepCode/>
							<bv:notEmpty/>
							<bv:remote url="${base}/bus/traCreditBzr/check" name="pid" message="该编码已被占用"/>
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="授信类ID" col="4" formControl="true"/>
						<bf:text id="creditId" name="creditId" col="8" value="${traCreditBzr.creditId}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="保证人名称" col="4" formControl="true"/>
						<bf:text id="guarantorName" name="guarantorName" col="8" value="${traCreditBzr.guarantorName}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="证件类型" col="4" formControl="true"/>
						<%--<bf:text id="identlfyType" name="identlfyType" col="8" url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${traCreditBzr.identlfyType}">
						</bf:text>--%>
						<bf:select id="identlfyType" name="identlfyType"  dataSource="custom" url="sys/dictItem/getDictItems/BUS-IdentifyType" col="8" value="${traCredit.guarantor}">
						</bf:select>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="证件号码" col="4" formControl="true"/>
						<bf:text id="identNumber" name="identNumber" col="8" value="${traCreditBzr.identNumber}">
						</bf:text>
					</bc:col>
					<bc:col col="6">
						<bc:label text="性别" col="4" formControl="true"/>
						<bf:text id="sex" name="sex" col="8" value="${traCreditBzr.sex}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:col col="6">
						<bc:label text="关系" col="4" formControl="true"/>
						<bf:text id="relation" name="relation" col="8" value="${traCreditBzr.relation}">
						</bf:text>
					</bc:col>
				</bf:formGroup>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="danger" text="保存" icon="save">
							<be:event>
								$('#editForm').data('formValidation').validate();
								var isValid = $('#editForm').data('formValidation').isValid();
								if(isValid)
								{
									var formData = $('#editForm').serializeAll();
									$.mask.post('${base}/bus/traCreditBzr/save', formData, function(data){
										if(data.success)
										{
											$.messager.alert('提示', data.msg, 'info', function(){
												window.location = '${base}/bus/traCreditBzr/list';
											});
										}
										else
										{
											$.messager.alert('错误', data.msg, 'error');
										}
									});
								}
							</be:event>
						</bc:button>
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/traCreditBzr/list';
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

</script>
	<script type="text/javascript">
        $("#approvalTime").datetimepicker({
            format:"yyyy-mm-dd hh:ii:00",
            language:"zh-CN",
            minuteStep:30,
            autoclose:2,
            todayBtn:"linked",
            minView: "hour"
        });

        $("#applicaSubTime").datetimepicker({
            format:"yyyy-mm-dd hh:ii:00",
            language:"zh-CN",
            minuteStep:30,
            autoclose:2,
            todayBtn:"linked",
            minView: "hour"
        });


        $("#tranDate").datetimepicker({
            format:"yyyy-mm-dd hh:ii:00",
            language:"zh-CN",
            minuteStep:30,
            autoclose:2,
            todayBtn:"linked",
            minView: "hour"
        });

        $("#validityContract").datetimepicker({
            format:"yyyy-mm-dd hh:ii:00",
            language:"zh-CN",
            minuteStep:30,
            autoclose:2,
            todayBtn:"linked",
            minView: "hour"
        });

	</script>
</body>
</html>
