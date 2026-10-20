<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>自然人关联解除名单</title>
	<style>
		* {
			margin: 0;
			padding: 0
		}

		ul {
			list-style: none;
		}
		#contain {
			border: solid rgb(224, 222, 222);
			border-radius: 10px;
			/*overflow: scroll;*/
			height: 500px;
		}
		#coupon-unused{
			margin: 60px 0 0 0;
		}

		#contain h4 {
			margin-top: 5px;
			text-align: center;
			height: 30px;
			line-height: 30px;
			border-bottom: solid rgb(240, 240, 240);
		}

		/* tab */
		#coupon>li {
			float: left;
			margin-top: 10px;
			display: inline-block;
			width: 100px;
			border: solid rgb(240, 240, 240);
			height: 30px;
			line-height: 30px;
			font-size: 12px;
			color: rgb(177, 175, 175);
			box-sizing:border-box;
			text-align: center;
		}

		#coupon>li:first-child {
			border-left: 0;
			border-right: 0;
		}

		#coupon>li:last-child {
			border-left: 0;
			border-right: 0;
		}

		#coupon .active {
			border: solid red;
			background: red;
			color: #fff;
		}

		/* 内容 */
		#coupon-contain {
			margin: 50px 5px;
		}

		#coupon-contain>div>div {
			margin: 60px 5px;
			text-align: center;
			height: 100%;
			line-height: 28px;
			border-radius: 8px;
			/*background: rebeccapurple;*/
		}

		/* 隐藏所有 */
		#coupon-contain>div {
			display: none;
		}

		/* 显示class为show的元素 */
		#coupon-contain>.show {
			display: block;
		}

		#coupon-contain>div:first-child>div {
			/*background: green;*/
		}

		#coupon-contain>div:last-child>div {
			/*background: #333;*/
		}

		table {
			border-collapse: collapse;
			border-spacing: 0;
			border: 1px solid #c0c0c0;
		}

		th,td {
			border: 1px solid #d0d0d0;
			color: #404060;
			padding: 10px;
		}

		th {
			background-color: #09c;
			font: bold 16px "微软雅黑";
			color: #fff;
		}

		td {
			font: 14px "微软雅黑";
		}
		tbody tr {
			background-color: #f0f0f0;
		}
		tbody tr:hover {
			cursor: pointer;
			background-color: #fafafa;
		}
		.box1{
			margin: 6% 1%;
			display: flex;
			justify-content: space-between;
			width: 57%;
			border: 1px solid #cccccc;
			height: 100%;
		}
		.div1 >.form-group {
			margin-bottom: 15px !important;
			display: flex !important;
		}
		.div1 > .hr-line-dashed {
			border-top: 1px dashed #e7eaec;
			color: #ffffff;
			background-color: #ffffff;
			height: 1px;
			margin: 10px 0 !important;
		}
		.div1 p{
			/*border-bottom: 1px solid #cccccc;*/
			display: flex;
			line-height: 30px;
			text-align: center;
		}
		.div1 p span:nth-child(1){
			width: 50%;
			text-align: right
		}
		.div1 p span:nth-child(2){
			width: 50%;
			text-align: left;
		}
		#coupon-used {
			margin: 6% 1%
		}

		.form-group .input-group.date,
		.form-group .select2{
			width: 100% !important;
		}
		.boxse .form-group .col-xs-4 {
			margin: 0.3% 0 !important;
		}
		.boxse .has-success .form-control, .boxse .has-success .form-control:focus {
			border-color: #CCCCCC !important;
		}
	</style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="naturalPersonRemoveBox" title="自然人关联解除名单" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<div id="contain">
					<h4>详情</h4>
					<ul id="coupon">
						<li class="active">基本信息</li>
						<li>附属信息</li>
					</ul>
					<div id="coupon-contain">
						<div id=“coupon-unused”  class="show">
							<div class="box1">
								<div class="div1" style="width: 30%">

										<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
										<%--<bc:label text="登记编码" col="12" formControl="true"/>--%>
										<%--<bc:label text="${legalPerson.regCode}" col="10" formControl="true" style="text-align:left"/>--%>
										<%--</bf:formGroup>--%>
									<bf:formGroup >
										<bc:label text="姓名" col="12" formControl="true"/>
										<bc:label text="${naturalPersonRemove.name}" col="10" formControl="true" style="text-align:left"/>
									</bf:formGroup>
									<bf:formGroup >
										<bc:label text="性别" col="12" formControl="true"/>
										<bc:label text="${naturalPersonRemove.sex}" col="10" formControl="true" style="text-align:left"/>
									</bf:formGroup>
									<bf:formGroup >
										<bc:label text="婚姻状况" col="12" formControl="true" />
										<bc:label text="${naturalPersonRemove.maritalStatus}" col="10" formControl="true" style="text-align:left"/>
									</bf:formGroup>
									<bf:formGroup >
										<bc:label text="民族" col="12" formControl="true"/>
										<bc:label text="${naturalPersonRemove.race}" col="10" formControl="true" style="text-align:left"/>
									</bf:formGroup>
									<bf:formGroup >
										<bc:label text="出生日期" col="12" formControl="true"/>
										<bc:label text="${naturalPersonRemove.birthdate}" col="10" formControl="true" style="text-align:left"/>
									</bf:formGroup>
								</div>
								<div class="div1" style="width:70%">
									<bf:formGroup >
										<bc:label text="国别/地区" col="12" formControl="true"/>
										<bc:label text="${naturalPersonRemove.nationality}" col="10" formControl="true" style="text-align:left"/>
									</bf:formGroup>
									<bf:formGroup >
										<bc:label text="境内/境外" col="12" formControl="true"/>
										<bc:label text="${naturalPersonRemove.domesticOrForeign}" col="10" formControl="true" style="text-align:left"  />
									</bf:formGroup>
									<bf:formGroup >
										<bc:label text="证件类型" col="12" formControl="true"/>
										<bc:label text="${naturalPersonRemove.identifyType}" col="10" formControl="true" style="text-align:left"/>
									</bf:formGroup>
									<bf:formGroup >
										<bc:label text="证件号码" col="12" formControl="true"/>
										<bc:label text="${naturalPersonRemove.identifyNumber}" col="10" formControl="true" style="text-align:left"/>
									</bf:formGroup>
									<bf:formGroup >
										<bc:label text="移动电话" col="12" formControl="true"/>
										<bc:label text="${naturalPersonRemove.mobile}" col="10" formControl="true" style="text-align:left"/>
									</bf:formGroup>
										<bf:formGroup >
										<bc:label text="邮箱" col="12" formControl="true"/>
										<bc:label text="${naturalPersonRemove.email}" col="10" formControl="true" style="text-align:left"/>
										</bf:formGroup>
								</div>
							</div>

						</div>
						<div id=“coupon-used” class="hide">
							<table>
								<thead>
								<tr>
									<th>姓名</th>
									<th>性别</th>
									<th>证件号</th>
									<th>关系</th>
									<th>操作</th>
								</tr>
								</thead>
								<tbody id="J_TbData">
								</tbody>
							</table>

						</div>
					</div>
				</div>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="自增长主键" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.pid}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="登记编码" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.regCode}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="源主体机构" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.orignalOrg}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="源主体银行名称" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.orignalOrgName}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="姓名" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.name}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="性別" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.sex}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="国别/地区" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.nationality}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="境内/境外(1-境内，2-境外)" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.domesticOrForeign}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="民族" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.race}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="证件类型" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.identifyType}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="证件号码" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.identifyNumber}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="出生日期" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.birthdate}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="移动电话" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.mobile}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="邮箱" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.email}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="报送版本号" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.reportVersion}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="是否有效 0-无效，1-有效" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.validStatus}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="职务编码" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.dutyCode}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="婚姻状况" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.maritalStatus}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="状态    未提交/已提交/未审批" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.status}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="是否被报送过（0-历史上从未报送过，1-已被报送过）" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.isReported}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="计入关联解除时间" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.removeDate}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="计入关联解除原因" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.removeCause}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="提交人" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.commitName}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="提交人职位" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.commitDutyCode}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="复核人" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.checkName}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="复核人职位" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.checkDutyCode}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="生效时间" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.activeTime}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<%--<bf:formGroup hr="dashed" hrp="bottom">--%>
					<%--<bc:label text="失效时间" col="2" formControl="true"/>--%>
					<%--<bc:label text="${naturalPersonRemove.invalidTime}" col="10" formControl="true" style="text-align:left"/>--%>
				<%--</bf:formGroup>--%>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/naturalPersonRemove/list';
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
<script>
	var data = [{
		name: "王1三",
		sex: "男",
		idno:"111111111223333",
		type: "职工",
		toDo: "编辑",
	},{
		name: "王2三",
		sex: "男",
		idno:"111111111223333",
		type: "职工",
		toDo: "编辑",
	},{
		name: "王3三",
		sex: "男",
		idno:"111111111223333",
		type: "职工",
		toDo: "编辑",
	}];
	var data1 = [{
		name: "王1三",
		sex: "男",
		idno:"111111111223333",
		toDo: "编辑",
	},{
		name: "王2三",
		sex: "男",
		idno:"111111111223333",
		toDo: "编辑",
	},{
		name: "王3三",
		sex: "男",
		idno:"111111111223333",
		toDo: "编辑",
	}];
	var data2 = [{
		name: "王1三",
		sex: "男",
		idno:"111111111223333",
		toDo: "编辑",
	},{
		name: "王2三",
		sex: "男",
		idno:"111111111223333",
		toDo: "编辑",
	},{
		name: "王3三",
		sex: "男",
		idno:"111111111223333",
		toDo: "编辑",
	}];
	$(function(){
		$.ajax({
			//请求方式
			type:'get',
			url:'https://api.myjson.com/bins/73fno',
			//服务器返回的数据类型
			dataType:'json',
			//请求成功的处理
			success:function(data){
				console.log(data);
				//获取需要的数据
				var group = data.content;
				//拼接字符串
				var str = '';
				//对数据做遍历，拼接到页面显示
				for(var i=0;i<group.length;i++){
					str += '<div class="item">'+
							'<div class="item-title">'+ group[i].name +'</div>'+
							'<div>'+
							'<span class="item-content">'+ group[i].time +'</span>'+
							'<span class="item-content">'+ group[i].teacher +'</span>'+
							'</div>'+
							'</div>';
				}
				//放入页面的容器显示
				$('#container').html(str);

			},
			//请求失败的处理
			error:function(jqXHR){
				console.log(jqXHR);
			}
		});

		//动态创建表格 拼接html
		var html = "";
		for( var i = 0; i < data.length; i++ ) {
			html += "<tr>";
			html +=     "<td contentEditable='true'>" + data[i].name + "</td>"
			html +=     "<td contentEditable='true'>" + data[i].sex + "</td>"
			html +=     "<td contentEditable='true'>" + data[i].idno + "</td>"
			html +=     "<td contentEditable='true'>" + data[i].type + "</td>"
			html +=     "<td  >" + data[i].toDo + "</td>"
			html += "</tr>";
		}
		$("#J_TbData").html(html);
		//动态创建表格 拼接html
		var html1 = "";
		for( var i = 0; i < data1.length; i++ ) {
			html1 += "<tr>";
			html1 +=     "<td contentEditable='true'>" + data1[i].name + "</td>"
			html1 +=     "<td contentEditable='true'>" + data1[i].sex + "</td>"
			html1 +=     "<td contentEditable='true'>" + data1[i].idno + "</td>"
			html1 +=     "<td >" + data1[i].toDo + "</td>"
			html1 += "</tr>";
		}
		$("#J_TbData1").html(html1);
		//动态创建表格 拼接html
		var html2 = "";
		for( var i = 0; i < data2.length; i++ ) {
			html2 += "<tr>";
			html2 +=     "<td>" + data2[i].name + "</td>"
			html2 +=     "<td>" + data2[i].sex + "</td>"
			html2 +=     "<td>" + data2[i].idno + "</td>"
			html2 +=     "<td>" + data2[i].toDo + "</td>"
			html2 += "</tr>";
		}
		$("#J_TbData2").html(html2);
		var $coupon_tab =  $("#coupon>li");
		var $coupon_contain = $('#coupon-contain>div');
		var index = 0;
		$coupon_tab.click(function(){
			//移除tab中class的active属性
			$($coupon_tab[index]).removeClass('active');
			//移除class的active属性
			$($coupon_contain[index]).removeClass('show');
			index = $(this).index();
			//添加tab中class的active属性
			$(this).addClass('active');
			//添加class的active属性
			$($coupon_contain[index]).addClass('show');
		});
	})
</script>
</body>
</html>