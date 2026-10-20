<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>MsgDeploy</title>
	<style>
		.ibox-title{
			text-align:center;
		}
	#busTitle{
		font: 70px;
	}
		#busWayDiv{
			width: 100%;
			height: 300px;
			text-align:center;
			font-size: xx-large;
		}
		#ImgDiv{
			text-align:center;
		}
		.msgimg{
			width: 100%;
			height: 100%;
		}
		.btright{
			text-align: right;
		}

	</style>

</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<div class="ibox-title">
				<h1 id="busTitle">${msgDeploy.busName}</h1>
			</div>
			<div class="ibox-content">
				<div class="panel-body">
					<input type="hidden" id="fList" value='${fList}'>
					<input type="hidden" id="names" value='${names}'>
					<input type="hidden" id="img1" name="img1"  value="${msgDeploy.img1}" />
					<div id="busWayDiv">${msgDeploy.busMessage}</div>
					<div id="ImgDiv">
					</div>
					<div style="height: 100px"></div>
					<div class="panel-body  btright" >
					<bf:formGroup>
						<div class="col-sm-4 col-sm-offset-2">
							<bc:button type="primary" text="返回" icon="arrow-circle-left">
								<be:event>
									window.history.back(-1);
								</be:event>
							</bc:button>
						</div>
					</bf:formGroup>
					</div>
				</div>
			</div>
		</div>
	</div>
<br:js pack="form"/>
<be:processEvent/>

<script type="text/javascript">
	$(document).ready(function () {
		initFileDiv ();
		//读取图片，并显示
		if($('#ImgDiv div').length>0){
			var namesArrlist=$("#names").val();
			var namesBe = namesArrlist.substr(1); //删除首字符
			var namesDe = namesBe.substring(0,namesBe.length-1);//删除尾字符
			var names=namesDe.split(", ");
			var fileUrl=$("#img1").val();
			for(var i=0;i<$('#ImgDiv div').length;i++){
				var filePath=fileUrl+'/'+names[i];
				/*图片路径*/
				var imgUrl = '${base}/bus/msgDeploy/image?filePath='+filePath;
				/*加载流程图片  */
				$("#"+"showimg"+i).attr('src',imgUrl);
			}
		}
	});
	//初始化FileDiv
	function initFileDiv () {
		var fList= $("#fList").val();
		for(var i=0;i<fList;i++){
			var content ='<div class="msgimg"  id="msgimg'+i+'" >';
			content +='<img class="showimg" src="" id="showimg'+i+'" >';
			content +="</div>";
			$("#ImgDiv").append(content)
		}
	}
</script>
</body>
</html>