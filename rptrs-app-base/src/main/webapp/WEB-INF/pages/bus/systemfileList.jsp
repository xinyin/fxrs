<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="list"/>
<title>制度文件</title>
<link rel="stylesheet" href="/static/css/dialog.css">
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
	<div class="row">
		<bc:ibox title="制度文件" subTitle="请输入制度文件查询信息" col="12" collapsible="true">
			<bf:form id="queryForm" validation="true">
                <bf:hidden name="type" id="type" value="${type}"></bf:hidden>
				<bf:hidden name="entity" id="entity" value="${entity}"></bf:hidden>
				<bf:hidden name="id" id="id" value="${id}"></bf:hidden>
				<bc:row cls="m-b">
					<bf:formGroup col="3">
						<bc:label text="制度名称" col="5" formControl="true"/>
						<bf:text name="__LIKES_fileName" col="7" placeholder=""/>
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="文号" col="5" formControl="true"/>
						<bf:text name="__LIKES_fileName" col="7" placeholder=""/>
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="发文部门" col="5" formControl="true"/>
						<bf:text name="__EQS_enableStatus" col="7" placeholder=""/>
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="时效性" col="5" formControl="true"/>
						<bf:text name="__EQS_enableStatus" col="7" placeholder=""/>
					</bf:formGroup>
					<bf:formGroup col="3">
						<bc:label text="生效日期" col="5" formControl="true"/>
						<bf:datebox name="__EQS_modifiedTime" col="7" />
                        <bf:datebox name="__EQS_modifiedTime" col="7" />
					</bf:formGroup>
					<%--<bc:col col="3">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
					</bc:col>--%>
				</bc:row>
				<bc:row cls="m-b">
					<div class="col-sm-12 col-sm-offset-5">
						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
						<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
					</div>
				</bc:row>
			</bf:form>
			<bc:table id="systemfileTable" idField="pid" multiple="true" url="${base}/bus/systemfile" queryUrl="systemType/${type}">
				<bc:toolbar id="upload" text="新建" type="button" handler="uploadFild"/>
				<bc:toolbar id="download" text="编辑" type="button" handler="downloadFild"/>
				<bc:toolbar id="submit" text="导出" type="button"/>
				<bc:toolbar id="remove" text="删除" type="button" handler="deleteHandler"/>
				<bc:column field="fileName" title="制度名称" width="100"/>
				<bc:column field="fileType" title="文号" width="100"/>
				<bc:column field="modifiedTime" title="发文部门" width="100"/>
				<bc:column field="createByName" title="时效性" width="100"/>
				<bc:column field="effectiveTime" title="生效日期" width="100"/>
				<bc:column field="fileSize" title="失效日期" width="100"/>
				<bc:column field="enableStatus" title="操作" width="100"/>
			</bc:table>
			<input type="file" id="fileName" name="fileName">
			<input type="button" id="ajax-submit" value="ajax-submit">
		</bc:ibox>
	</div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script src="/static/js/jquery.dialog.js"></script>
<script type="text/javascript">
	$("#ajax-submit").click(function(){
		var formdata=new FormData();
		formdata.append("fileName",$("#ajax-submit")[0].files[0]);
		$.ajax({
			url:"/bus/systemfile/upload",
			type:"post",
			data:formdata,
			processData: false ,    // 不处理数据
			contentType: false,    // 不设置内容类型
			success:function(data){
				console.log(data)
				if(data == true){
					$.messager.toastr("提示","上传成功！","success");
					$('#systemfileTable').jTable('refresh');
					$('#systemfileTable').jTable('clearCheckData');
				}
			}
		})
	})
	//删除属性
	function deleteHandler(){
		var tables =  $('#systemfileTable').jTable('getCheckData');
		console.log(tables)
		if(tables.length > 0)
		{
			$.messager.confirm('提示', '确定要删除吗？', function(r){
				if(r){
					var dataa = []
					tables.forEach(v=>{
						dataa.push(v.pid)
					})
					console.log(dataa)
					$.ajax({
						url:"/bus/systemfile/remove?pid=" + dataa,
						type:"GET",
						dataType: 'json',
						success : function (result) {
							console.log(result)
							$('#systemfileTable').jTable('refresh');
							$('#systemfileTable').jTable('clearCheckData');
						}
					})
				}
			});
		}
		else
		{('提示', '请先选择需要删除的记录。', 'error');
		}
	}
	// 上传框
	function uploadFild() {
		$.sendConfirm({
			hideHeader: true,
			hideClose: true,
			withIcon: false,
			withCenter: true,
			content: '<input type="file" id="coverPhoto-file">',
		<%--	content:'<form id="myform" enctype="multipart/form-data" action="/bus/systemfile/upload" method="post">'--%>
		<%--	+'<input type="file" name="fileName" id="fileName"/>'--%>
		<%--	+'<input type="text" name="type" value="${type}" style="display:none"/>'--%>
		<%--	+'<input type="text" name="name" value="超级管理员" style="display:none" />'+'<div style="margin-top:30px">'--%>
		<%--+'<input id="subBtn" value="提交" type="submit"/>'+'</div>'+'</form>',--%>
			button: {
				confirm: '确认',
				cancel: '取消'
			},
			width: 260,
			onConfirm: function() {
				var app = document.getElementById('coverPhoto-file')
				console.log(app)
			},
			onCancel: function() {
				console.log('点击取消！');
			},
			onClose: function() {
				console.log('点击关闭！');
			}
		});
	};
	//文件下载
	function downloadFild() {
		var tables =  $('#systemfileTable').jTable('getCheckData')
		var data = []
		tables.forEach(v=>{
			data.push(v.pid)
		})
		console.log(data)
		var params = {
			pid:data
		};

		var form = document.createElement('form')
		form.id = 'form'
		form.name = 'form'
		document.body.appendChild (form)
		for (var obj in params) {
			if (params.hasOwnProperty(obj)) {
				var input = document.createElement('input')
				input.tpye='hidden'
				input.name = obj;
				input.value = params[obj]
				form.appendChild(input)
			}
		}
		form.method = "GET" //请求方式
		form.action = '/bus/systemfile/download'
		form.submit()
		document.body.removeChild(form)

	}
</script>
<style>
	.container {width: 600px; margin: 20px auto;}
	button { padding: 0 10px; }
	.text-info { color: #ff7300;}

	.mt10{
		margin-top: 10px;
	}

	.mt20{
		margin-top: 20px;
	}

	.hide {
		display: none;
	}

	#formBox {
		width: 550px;
		border: 1px solid #ddd;
		padding: 20px;
		margin: 20px auto;
	}

	.frm-item {
		line-height: 32px;
	}

	.frm-item + .frm-item {
		margin-top: 10px;
	}

	.frm-label {
		display: inline-block;
		width: 7em;
		font-size: 14px;
	}

	.frm-input {
		width: 120px;
		height: 30px;
		border: 1px solid #ddd;
		padding: 0 10px;
	}

	.frm-submit {
		display: inline-block;
		min-width: 50px;
		height: 32px;
		padding: 0 10px;
		font-size: 14px;
		line-height: 32px;
		text-align: center;
		color: #fff;
		background-color: #0D76B4;
		text-decoration: none;
	}

	.frm-submit.disabled,
	.frm-submit.disabled:hover {
		background-color: #BCB7B7;
		cursor: not-allowed;
	}

	.frm-submit + .frm-reset {
		margin-left: 10px;
	}

	.frm-reset {
		display: inline-block;
		width: 60px;
		height: 32px;
		font-size: 14px;
		line-height: 32px;
		text-align: center;
		color: #fff;
		background-color: #B9A5CE;
		text-decoration: none;
	}

	.frm-submit:hover {
		background-color: #0F5883;
	}

	i.required {
		padding-right: 5px;
		color: #f00;
	}

	#addIpBox .msg-box {
		padding-left: 98px;
		color: #c33;
	}
</style>
</body>
</html>