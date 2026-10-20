<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp"%>
	<br:css pack="form"/>
	<title>披露年度</title>
</head>
<body class="gray-bg">
<div class="J_mainContent" id="content-main">
	<iframe class="J_iframe" id="J_menuTab_defaultId" data-id="J_menuTab_defaultId" name="iframe0" width="100%" height="500px" src="" frameborder="0" seamless></iframe>
</div>
<div class="row">
	<bc:ibox id="traBusinessBox" title="" subTitle="" col="12" collapsible="true">
		<bf:form id="editForm" method="post" validation="true">
			<bf:hidden id="opt" name="opt" value="${opt}"/>
			<bf:hidden id="op" name="op" value="${op}"/>
			<bf:hidden id="pid" name="pid" value="${publog.pid}"/>
			<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
				<%--<bc:col col="6">--%>
				<%--<bc:label text="主键" col="4" formControl="true"/>--%>
				<%--<bf:text id="pid" name="pid" col="8" value="${traBusiness.pid}"  disabled="${opt == 'edit'}">--%>
				<%--<bv:jepCode/>--%>
				<%--<bv:notEmpty/>--%>
				<%--<bv:remote url="${base}/ins/traBusiness/check" name="pid" message="该编码已被占用"/>--%>
				<%--</bf:text>--%>
				<%--</bc:col>--%>
			<%--	<bc:col col="6">
					<bc:label text="披露主体" col="4" formControl="true"/>
					<bf:text id="pubSubject" name="relNumberpubSubject" col="8" value="${publog.pubSubject}" >
					</bf:text>
				</bc:col>
				<bc:col col="6">
					<bc:label text="年度" col="4" formControl="true"/>
					<bf:text id="pubYear" name="pubYear" col="8" value="${publog.pubYear}" >
					</bf:text>

				</bc:col>
			</bf:formGroup>

			<bf:formGroup hr="dashed" hrp="bottom">
				<div style="width: 95%;margin:0 auto" >
					<bc:table id="publogTable" idField="pid" url="${base}/pub/publog">
						&lt;%&ndash;<bc:toolbar id="add" text="新增" type="button" handler="sendAdd"/>
                        <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>&ndash;%&gt;
						&lt;%&ndash;<bc:toolbar id="downFile" text="下载文件" type="button" handler="downFileOnclick"/>&ndash;%&gt;
						<bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
						&lt;%&ndash;<bc:toolbar id="send" text="提交审批" handler="send" type="button"/>&ndash;%&gt;&ndash;%&gt;
						<bc:column field="pid" title="pid" width="100" visible="false"/>
						<bc:column field="pubFileName" title="文件名称" width="100"/>
						<bc:column field="pubSubject" title="披露主体" width="100"/>
						<bc:column field="pubYear" title="年度" width="100"/>
						<bc:column field="pubQuarter" title="季度" width="100" visible="false"/>
						<bc:column field="maxPercentInNetAssets" title="生成披露文件时间" width="100"/>
						<bc:column field="pubVersion" title="生成版本号" width="100"/>
						<bc:column field="downAmt" title="下载次数" width="100" visible="false"/>
						<bc:column field="status" title="状态" width="100" formatter="custom:sys/dictItem/getDictItems/SUBMIT-status"/>
						<bc:column field="statusComment" title="状态说明" width="100" visible="false"/>
						<bc:column field="downStatus" title="是否已下载" width="100" formatter="fixedDict:IsStatusDict"/>
						<bc:operator id="view" icon="search" title="查看" handler="viewFile"/>
					</bc:table>
				</div>
			</bf:formGroup>--%>

		</bf:form>
	</bc:ibox>
</div>
</div>
<<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">
	$(document).ready(function(){

		document.getElementById("J_menuTab_defaultId").src =
				"${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
				+ encodeURIComponent('${base}/pub/publog/preview?pid=' + ${publog.pid} );
	});


</script>
</body>
</html>
