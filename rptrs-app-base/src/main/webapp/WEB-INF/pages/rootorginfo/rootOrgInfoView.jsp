<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<%@ include file="/WEB-INF/pages/include/common.jsp" %>
	<br:css pack="form"/>
	<title>机构基础数据编辑</title>
	<style>
		.form-group {
			margin: 0.3% 0 !important;
		}

		.form-group .col-xs-12 {
			margin: 0.3% 0 !important;
		}

		.form-group .col-xs-12 label {
			display: inline-block;
			float: left;
			margin: 0 0 0 3%;
			padding: 0 2% 0 0;
		}

		.form-group .col-xs-12 label {
			display: inline-block;
			float: left;
			margin: 0 0 0 3%;
			padding: 0 2% 0 0;
			width: 10%;
		}

		.has-success .form-control, .boxse .has-success .form-control:focus {
			border-color: #CCCCCC !important;
		}

		.gray-bg {
			background: white !important;
		}

		.form-group .input-group.date,
		.form-group .select2 {
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
<div class="row">
	<bc:ibox id="orgBaseDataBox" title="" subTitle="" col="12" collapsible="true">
		<bf:form id="editForm" method="post" validation="true">
			<bf:hidden id="opt" name="opt" value="${view}"/>
			<bf:hidden id="pid" name="pid" value="${rootOrgInfo.pid}"/>
			<bf:hidden id="viewYear" name="pid" value="${rootOrgInfo.years}"/>
			<bf:hidden id="limit01" name="limit01" value="${limit01}"/>
			<bf:hidden id="limit02" name="limit02" value="${limit02}"/>
			<bf:hidden id="limit03" name="limit03" value="${limit03}"/>
			<bf:hidden id="limit04" name="limit04" value="${limit04}"/>
			<bf:hidden id="limit05" name="limit05" value="${limit05}"/>
			<bf:hidden id="limit06" name="limit06" value="${limit06}"/>
			<bf:hidden id="limit07" name="limit07" value="${limit07}"/>
			<bc:row cls="m-b">
				<bf:formGroup hrp="bottom" hr="dashed">
					<bf:formGroup col="6">
						<bc:label cls="required" text="年度" col="3" formControl="true"/>
						<%--<bf:datebox id="years" name="years" col="9" format="date" value="${dateTime}"/>--%>
						<bf:select id="years" name="years" col="9" value="${dateTime}" disabled="true">
							<be:event>
								yearSelected();
							</be:event>
							<bf:option text="请选择" value=""></bf:option>
						</bf:select>
						<bv:notEmpty/>
					</bf:formGroup>
					<bf:formGroup col="6">
						<bc:label cls="required" text="季度" col="3" formControl="true"/>
						<bf:select id="quarter" name="quarter" col="9" dataSource="custom"
								   url="sys/dictItem/getDictItems/BUS-Quarter"
								   value="${rootOrgInfo.quarter}" disabled="true">
							<bf:option text="请选择" value=""></bf:option>
							<bv:notEmpty/>
						</bf:select>
					</bf:formGroup>
				</bf:formGroup>
			</bc:row>
			<bc:row cls="m-b">
				<bf:formGroup hrp="bottom" hr="dashed" >
					<bf:formGroup col="6">
						<bc:label text="总资产(万元，请保留4位小数)" cls="required" col="3" formControl="true"/>
						<bf:text id="totalCapital" name="totalCapital" col="9"
								 value="${rootOrgInfo.totalCapital}" disabled="true">
							<bv:notEmpty/>
						</bf:text>
					</bf:formGroup>
					<bf:formGroup col="6">
						<bc:label text="净资产(万元，请保留4位小数)" cls="required" col="3" formControl="true"/>
						<bf:text id="netCapital" name="netCapital" col="9"
								 value="${rootOrgInfo.netCaptial}" disabled="true">
							<bv:notEmpty/>
						</bf:text>
					</bf:formGroup>
				</bf:formGroup>
			</bc:row>
			<bc:row cls="m-b">
				<bf:formGroup hrp="bottom" hr="dashed" >
					<%--<bf:formGroup col="6">
						<bc:label text="季度综合偿付能力充足率（%）" cls="required" col="3" formControl="true"/>
						<bf:text id="quarterCompreSolvencyRate" name="quarterCompreSolvencyRate" col="9" value="${rootOrgInfo.quarterCompreSolvencyRate}" disabled="true">
							<bv:notEmpty/>
						</bf:text>
					</bf:formGroup>--%>

					<bf:formGroup col="6">
						<bc:label text="权益类资产投资上限比例（%）" col="3" formControl="true"/>
						<bf:text id="equityUpperLimitScale" name="equityUpperLimitScale" col="9" value="${rootOrgInfo.equityUpperLimitScale}" disabled="true">
							<bv:notEmpty/>
						</bf:text>
					</bf:formGroup>
				</bf:formGroup>
			</bc:row>

			<h3>限额说明</h3>
			<bc:ibox id="a" title="" subTitle="" col="12" collapsible="true">
				<!-- bootstrap的table类
				table-primary  表格颜色也可以加在table/tr/thead/tbody/td
				table-strap条形纹  可以隔行变色，不受背景色影响
				table-hover 鼠标悬停着重色 加在hover
				table-bordered 加边框
				border-danger 加边框颜色
				-->
				<table class="table table-primary table-striped table-hover table-bordered border-success">
					<!-- 表头会自己让文字和背景形成对比，深色背景就是白色字，白色背景就是深色文字 -->
					<thead class="table-dark">
					<tr bgcolor = "#6495ed">
						<td>序号</td>
						<td>业务标准</td>
						<td>限额说明</td>
						<td>具体限额（万元）</td>
					</tr>
					</thead>
					<tbody >
					<tr>
						<td>1</td>
						<td>重大关联交易标准</td>
						<td>保险机构重大关联交易是指保险机构与单个关联方之间单笔或年度累计交易金额达到3000万元以上，且占保险机构上一年度末经审计的净资产的1%以上的交易。</td>
						<td id="limit1"></td>
					</tr>
					<tr>
						<td>2</td>
						<td>对全部关联方的投资限额</td>
						<td>保险机构投资全部关联方的账面余额，合计不得超过保险机构上一年度末总资产的25%与上一年度末净资产二者中的金额较低者；</td>
						<td id="limit2"></td>
					</tr>
					<tr>
						<td>3</td>
						<td>投资关联方权益类资产限额</td>
						<td>保险机构投资权益类资产的账面余额中，对关联方的投资金额不得超过大类资产投资限额的30%；</td>
						<td id="limit3"></td>
					</tr>
					<tr>
						<td>4</td>
						<td>投资关联方不动产类资产限额（非标）</td>
						<td>未在银行间市场、证券交易所市场等国务院同意设立的交易市场交易的资产，合计不高于本公司上季末总资产的25% </td>
						<td id="limit4"></td>
					</tr>
					<tr>
						<td>5</td>
						<td>投资关联方其他金融资产限额（非标）</td>
						<td>未在银行间市场、证券交易所市场等国务院同意设立的交易市场交易的资产，合计不高于本公司上季末总资产的20%。</td>
						<td id="limit5"></td>
					</tr>
					<tr>
						<td>6</td>
						<td>投资关联方境外投资限额</td>
						<td>保险机构境外投资的账面余额中，对关联方的投资金额不得超过大类资产投资限额的30%；</td>
						<td id="limit6"></td>
					</tr>
					<tr>
						<td>7</td>
						<td>投资单一关联方的限额</td>
						<td>保险机构投资单一关联方的账面余额，合计不得超过保险机构上一年度末净资产的30%</td>
						<td id="limit7"></td>
					</tr>
					</tbody>
				</table>
			</bc:ibox>

			<bf:formGroup>
				<div class="col-sm-12 col-sm-offset-5">
					<bc:button type="primary" text="返回" icon="arrow-circle-left">
						<be:event>
							window.location = '${base}/bus/rootOrgInfo/list';
						</be:event>
					</bc:button>
				</div>
			</bf:formGroup>
		</bf:form>
	</bc:ibox>
</div>

<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    /*start*/
    $(document).ready(function () {

		/**
		 *填充限额数据
		 */

		var limit01 = parseFloat($("#limit01").val());
		var limit02 = parseFloat($("#limit02").val());
		var limit03 = parseFloat($("#limit03").val());
		var limit04 = parseFloat($("#limit04").val());
		var limit05 = parseFloat($("#limit05").val());
		var limit06 = parseFloat($("#limit06").val());
		var limit07 = parseFloat($("#limit07").val());
		$('#limit1' ).text(limit01);
		$('#limit2' ).text(limit02);
		$('#limit3' ).text(limit03);
		$('#limit4' ).text(limit04);
		$('#limit5' ).text(limit05);
		$('#limit6' ).text(limit06);
		$('#limit7' ).text(limit07);
		console.log("-----limit01=" + limit01 + "-----limit02=" + limit02 + "-----limit03=" + limit03 + "-----limit04=" + limit04 + "-----limit05=" + limit05 + "-----limit06=" + limit06 + "-----limit07=" + limit07)

        // x();
        /*向下拉框中填入数据*/
        $(function(){
            var dd = new Date();
            var currentYear = dd.getFullYear();
            var years = [];
            for(var i = 1; i <= 10; i++){
                years.push(currentYear--);
            }
            console.log(years);

            // var size = currentYear - 10 + 1;
            //年份回显
            var year = parseInt($("#viewYear").val());
            if(year != undefined && year != ''){
                for( var j=0; j<years.length; j++ ){
                    var yearOld = currentYear + j + 1;
                    if(yearOld == year){
                        $("#years").append($("<option value="+year+" selected>"+year+"年"+"</option>"));
                        continue;
                    }
                    $("#years").append($("<option value="+yearOld+">"+yearOld+"年"+"</option>"));
                }
            }else {
                for( var i=0; i<years.length; i++ ){
                    var yearOld = currentYear + i + 1;
                    $("#years").append($("<option value="+yearOld+">"+yearOld+"年"+"</option>"));
                }
            }
        });
    });

    // function x() {
    // 	$('#years').attr('disabled','true');
    // 	$('#quarter').attr('disabled','true');
    // 	$('#totalCapital').attr('disabled','true');
    // 	$('#netCapital').attr('disabled','true');
    // 	$('#quarterCompreSolvencyRate').attr('disabled','true');
    // }

    /* 获取选中的下拉框的值 */
    function yearSelected(){
        var valueSel = $("#years").find("option:selected").val();
        console.info("选中的option的value值为："+valueSel);/* value值，如2017 */
    }


    /*end*/

</script>
</body>
</html>
