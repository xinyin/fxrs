<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>RelPar</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="已报送数据" subTitle="" col="12" collapsible="true">

	<%--		<div class="fixed-table-body">
				<table class="table table-bordered">
					<thead>
					<tr>
						<th style="text-align: center; width: 100px; " data-field="tranDate" tabindex="0">
							<div class="th-inner" style="font-size:25px">关联方信息</div>
							<div class="fht-cell"></div>
						</th>
					</tr>
					</thead>
					<tbody>
					<tr data-index="0">
						<td colspan="2" width="100%">
							<span style="float:left; border:1px;" id="traSubName" >推送日期:${time}</span>
						</td>

					</tr>
					</tbody>

				</table></div>--%>

			<bc:table id="glfxAddTable" idField="id" multipleVisible="false" url="${base}/east/glfxx" queryUrl="/viewDtail/list?id=${id}">
				<bc:column field="id" title="id" width="100" visible="false"/>
				<bc:column field="glfbh" title="关联方编号" width="100"/>
				<bc:column field="glfmc" title="关联方名称" width="100"/>
				<bc:column field="glfsbm" title="关联方识别码" width="100"/>
				<bc:column field="glflb" title="关联方类别" width="100"/>
				<bc:column field="gxlx" title="关系类型" width="100"/>
				<bc:column field="qtglgxsm" title="其他关联类型说明" width="100"/>
				<bc:column field="yxbz" title="关联有效标识" width="100"/>
			</bc:table>
			<div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
				<bc:button type="primary" text="返回" icon="arrow-circle-left">
					<be:event>
						window.location = '${base}/east/glfxx/list';
					</be:event>
				</bc:button>
			</div>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
</script>
</body>
</html>