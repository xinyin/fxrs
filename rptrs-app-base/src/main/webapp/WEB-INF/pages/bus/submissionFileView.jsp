<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>报送文件</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="submissionFileBox" title="报送文件" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="序号" col="2" formControl="true"/>
					<bc:label text="${submissionFile.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>

				<%--<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="文件路径" col="2" formControl="true"/>
					<bc:label text="${submissionFile.fileUrl}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>--%>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="文件名称" col="2" formControl="true"/>
					<bc:label text="${submissionFile.fileName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="报送文件类型" col="2" formControl="true"/>
					<bc:label text="${submissionFile.fileType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<%--<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="显示状态" col="2" formControl="true"/>
					<bc:label text="${submissionFile.showStatus}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="审批状态" col="2" formControl="true"/>
					<bc:label text="${submissionFile.approveStatus}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>--%>

				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="时间属性             " col="2" formControl="true"/>
					<bc:label text="${submissionFile.timeAttribute}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="报送批次号             " col="2" formControl="true"/>
					<bc:label text="${submissionFile.repBatchNo}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="发起报送时间             " col="2" formControl="true"/>
					<bc:label text="${submissionFile.repTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="报送状态" col="2" formControl="true"/>
					<bc:label text="${submissionFile.submissionStatus}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>


				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="报送人             " col="2" formControl="true"/>
					<bc:label text="${submissionFile.repPerson}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="复核人             " col="2" formControl="true"/>
					<bc:label text="${submissionFile.reviewer}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="报送信息反馈             " col="2" formControl="true"/>
					<bc:label text="${submissionFile.repInforFeedback}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
			<%--	<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="法人机构" col="2" formControl="true"/>
					<bc:label text="${submissionFile.corporateBody}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="报送机构" col="2" formControl="true"/>
					<bc:label text="${submissionFile.repOrg}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="生成文件类型(1、关联方信息2、重大关联交易3、季报4、年报5、月报6、G157、行内制度)" col="2" formControl="true"/>
					<bc:label text="${submissionFile.buildFileType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>--%>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/submissionFile/list';
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
</body>
</html>