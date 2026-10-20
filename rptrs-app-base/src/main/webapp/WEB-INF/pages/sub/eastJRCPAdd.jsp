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
			<bc:table id="jrcpTable" idField="id" multipleVisible="false" url="${base}/east/jrcp" queryUrl="/querySubFile">
				<bc:toolbar id="addEastGlfxView" text="生成报送文件" type="button" handler="frmSubmitFile"/>
				<bc:column field="id" title="id" width="100" visible="false"/>
				<bc:column field="bussedate" title="采集日期" width="100"/>
				<bc:column field="jrcpmc" title="金融产品名称" width="100"/>
				<bc:column field="jrcppzzcbah" title="金融产品批准/注册/备案号" width="100"/>
				<bc:column field="jrcpdm" title="金融产品代码" width="100"/>
				<bc:column field="jrcpfl" title="金融产品分类" width="100"/>
				<bc:column field="jrcpdjjg" title="金融产品登记机构" width="100"/>
				<bc:column field="jrcpglrqc" title="金融产品管理人全称" width="100"/>
				<bc:column field="glrtyshxydm" title="管理人统一社会信用代码" width="100"/>
				<bc:column field="jrcptgrqc" title="金融产品托管人全称" width="100"/>
				<bc:column field="tgrtyshxydm" title="托管人统一社会信用代码" width="100"/>
			</bc:table>
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
	//生成报送文件
	function frmSubmitFile() {
		var data = "E2";
		var url = '${base}/east/jrcp/createSubRelParFile/' + data;
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
									window.location = '${base}/east/jrcp/list';
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