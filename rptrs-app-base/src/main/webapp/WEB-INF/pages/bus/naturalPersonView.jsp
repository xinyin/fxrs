<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
<br:css pack="form"/>
<title>自然人</title>
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
			margin: 10px 5px;
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
	</style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
		<div class="row">
			<bc:ibox id="naturalPersonBox" title="自然人" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="自增长主键" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.pid}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="登记编码" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.regCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="源主体银行或保险机构" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.orignalOrg}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="源主体银行或保险机构名称" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.orignalOrgName}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="姓名" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.name}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="性別" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.sex}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="国别/地区" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.nationality}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="境内/境外(1-境内，2-境外)" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.domesticOrForeign}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="民族" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.race}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证件类型" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.identifyType}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="证件号码" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.identifyNumber}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="出生日期" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.birthdate}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="移动电话" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.mobile}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="邮箱" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.email}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="报送版本号" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.reportVersion}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="职务编码" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.dutyCode}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否有效 0-无效，1-有效" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.validStatus}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="是否被报送过（0-历史上从未报送过，1-已被报送过）" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.isReported}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="生效时间" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.activeTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>
				<bf:formGroup hr="dashed" hrp="bottom">
					<bc:label text="失效时间" col="2" formControl="true"/>
					<bc:label text="${naturalPerson.invalidTime}" col="10" formControl="true" style="text-align:left"/>
				</bf:formGroup>

				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-2">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/naturalPerson/list';
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