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
		<bc:ibox title="新增" subTitle="" col="12" collapsible="true">
			<bc:table id="glgxTable" idField="id" multipleVisible="false" url="${base}/east/glgx" queryUrl="/querySubFile">
				<bc:toolbar id="addEastGlfxView" text="生成报送文件" type="button" handler="frmSubmitFile"/>
				<bc:column field="id" title="id" width="100" visible="false"/>
				<bc:column field="bussedate" title="采集日期" width="100"/>
				<bc:column field="glfmcGlfzjjdmc" title="关联方名称/关联方中间节点名称" width="100"/>
				<bc:column field="glfdmGlfzjjddm" title="关联方代码/关联方中间节点代码" width="100"/>
				<bc:column field="glfGlfzjjdlx" title="关联方/关联方中间节点类型" width="100"/>
				<bc:column field="qycglfmcGlfzjjdmc" title="前一层关联方名称/关联方中间节点名称" width="100"/>
				<bc:column field="qycglfdmGlfzjjddm" title="前一层关联方代码/关联方中间节点代码" width="100"/>
				<bc:column field="qycglfGlfzjjdlx" title="前一层关联方/关联方中间节点类型" width="100"/>
				<bc:column field="yqycdglgx" title="与前一层的关联关系" width="100"/>
				<bc:column field="yqyczjsfyqyfe" title="与前一层之间是否有权益份额" width="100"/>
				<bc:column field="cybl" title="持有比例" width="100"/>
				<bc:column field="zygqbl" title="质押股权比例" width="100"/>
				<bc:column field="glgxsm" title="关联关系说明" width="100"/>
				<bc:column field="gxyxbz" title="关系有效标志" width="100"/>
				<bc:column field="sxrq" title="生效日期" width="100"/>
				<bc:column field="sxrq1" title="失效日期" width="100"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
	//生成报送文件
	function frmSubmitFile() {
		var data = "E3";
		var url = '${base}/east/glgx/createSubRelParFile/' + data;
		var EastUrl = '${base}/sys/xlsie/east/EastExportCallBackImpl/' + data;
		$.ajax({
			url: EastUrl,
			type: 'post',
			data: data,
			dataType: 'json',
			success: function (data) {
				$.ajax({
					url: url,
					type: 'post',
					data: data,
					dataType: 'json',
					success: function (data1) {
						if (data.sucess==false)
						{
							if (data.msg == "暂无报送数据生成") {
								$.messager.alert('提示', data.msg, 'info');
							} else {
								$.messager.alert('失败', data.msg, 'error');
							}
						}
						else{
							if (data1.success) {
								$.messager.alert('成功', data1.msg, 'info', function () {
									window.location = '${base}/east/glgx/list';
								});
							} else {
								if (data1.msg == "暂无报送数据生成") {
									$.messager.alert('提示', data1.msg, 'info');
								} else {
									$.messager.alert('失败', data1.msg, 'error');
								}
							}
						}
					}
				});
			}
		});
	}
</script>
</body>
</html>