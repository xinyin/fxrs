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
		<bc:ibox title="待报送数据列表" subTitle="" col="12" collapsible="true">
			<bc:table id="glfxAddTable" idField="id" multipleVisible="false" url="${base}/east/glfxx" queryUrl="/queryFile">
				<bc:toolbar id="addEastGlfx" text="生成报送文件" type="button" handler="frmSubmitFile"/>
				<bc:column field="id" title="id" width="100" visible="false"/>
<%--				<bc:column field="pch" title="流水号" width="100"/>--%>
<%--				<bc:column field="subNum" title="保险机构代码" width="100"/>--%>
<%--				<bc:column field="subOrg" title="保险机构名称" width="100"/>--%>
<%--				<bc:column field="khbh" title="客户编号" width="100"/>--%>
<%--				<bc:column field="khmc" title="客户名称" width="100"/>--%>
<%--				<bc:column field="khsbm" title="客户识别码" width="100"/>--%>
<%--				<bc:column field="khlb" title="客户类别" width="100"/>--%>
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
	//生成报送文件
	function frmSubmitFile() {
		var data = "E1";
		var url = '${base}/east/glfxx/createSubRelParFile/' + data;
		$.ajax({
			url: url,
			type: 'post',
			data: data,
			dataType: 'json',
			success: function (data) {
				if (data.success==false) {
					if (data.msg == "暂无报送数据生成") {
						$.messager.alert('提示', data.msg, 'info');
					} else {
						$.messager.alert('失败', data.msg, 'error');
					}
				} else{
					$.messager.alert('成功', data.msg, 'info', function () {
						window.location = '${base}/east/glfxx/list';
					});
				}
			}
		});
	}
</script>
</body>
</html>