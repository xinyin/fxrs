<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<br:css pack="form"/>
<title>机构详情</title>
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
                 width :100%;
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
			<bc:ibox id="legalPersonBox" title="法人详情" subTitle="" col="12" collapsible="false">
			<bf:form id="form" method="post" validation="false">
				<div id="contain">
					        <ul id="coupon">
					            <li class="active">基本信息</li>
					             <li>附属信息</li>
					             <li>权益信息</li>
					         </ul>
					         <div id="coupon-contain">
					             <div id=“coupon-unused”  class="show">
                                     <div class="box1">
                                         <div class="div1" style="width: 30%">
                                                 <%--<bf:formGroup hr="dashed" hrp="bottom">--%>
                                                     <%--<bc:label text="自增长主键" col="12" formControl="true"/>--%>
                                                     <%--<bc:label text="${legalPerson.pid}" col="10" formControl="true" style="text-align:left"/>--%>
                                                 <%--</bf:formGroup>--%>
                                                 <bf:formGroup hr="dashed" hrp="bottom">
                                                     <bc:label text="登记编码" col="12" formControl="true"/>
                                                     <bc:label text="${legalPerson.regCode}" col="10" formControl="true" style="text-align:left"/>
                                                 </bf:formGroup>
                                                 <bf:formGroup hr="dashed" hrp="bottom">
                                                     <bc:label text="企业名称" col="12" formControl="true"/>
                                                     <bc:label text="${legalPerson.orgName}" col="10" formControl="true" style="text-align:left"/>
                                                 </bf:formGroup>
                                                 <bf:formGroup hr="dashed" hrp="bottom">
                                                     <bc:label text="企业类型" col="12" formControl="true"/>
                                                     <bc:label text="${legalPerson.orgType}" col="10" formControl="true" style="text-align:left"/>
                                                 </bf:formGroup>
                                                 <bf:formGroup hr="dashed" hrp="bottom">
                                                     <bc:label text="境内/境外" col="12" formControl="true"/>
                                                     <bc:label text="${legalPerson.domesticOrForeign}" col="10" formControl="true" style="text-align:left"/>
                                                 </bf:formGroup>
                                                 <bf:formGroup hr="dashed" hrp="bottom">
                                                     <bc:label text="注册地" col="12" formControl="true"/>
                                                     <bc:label text="${legalPerson.regAddress}" col="10" formControl="true" style="text-align:left"/>
                                                 </bf:formGroup>
                                                 <bf:formGroup hr="dashed" hrp="bottom">
                                                     <bc:label text="企业法人" col="12" formControl="true"/>
                                                     <bc:label text="${legalPerson.orgName }" col="10" formControl="true" style="text-align:left"/>
                                                 </bf:formGroup>
                                         </div>
                                         <div class="div1" style="width:70%">
                                                 <bf:formGroup hr="dashed" hrp="bottom">
                                                     <bc:label text="社会统一信用代码" col="12" formControl="true"/>
                                                     <bc:label text="${legalPerson.uniscCode}" col="10" formControl="true" style="text-align:left"/>
                                                 </bf:formGroup>
                                                 <bf:formGroup hr="dashed" hrp="bottom">
                                                     <bc:label text="注册资本币种代码" col="12" formControl="true"/>
                                                     <bc:label text="${legalPerson.regCode}" col="10" formControl="true" style="text-align:left"/>
                                                 </bf:formGroup>
                                                 <bf:formGroup hr="dashed" hrp="bottom">
                                                     <bc:label text="机构状态" col="12" formControl="true"/>
                                                     <bc:label text="${legalPerson.status}" col="10" formControl="true" style="text-align:left"/>
                                                 </bf:formGroup>
                                                 <bf:formGroup hr="dashed" hrp="bottom">
                                                     <bc:label text="注册资本" col="12" formControl="true"/>
                                                     <bc:label text="${legalPerson.regCapital}" col="10" formControl="true" style="text-align:left"/>
                                                 </bf:formGroup>
                                                 <bf:formGroup hr="dashed" hrp="bottom">
                                                     <bc:label text="营业范围" col="12" formControl="true"/>
                                                     <bc:label text="${legalPerson.businessScope}" col="10" formControl="true" style="text-align:left"/>
                                                 </bf:formGroup>
                                         </div>
                                     </div>

					             </div>
					             <div id=“coupon-used” class="hide"  style="margin: 6% 0;">
                                     <table>
                                         <thead>
                                         <th><input type="checkbox" ></th>
                                         <th>姓名</th>
                                         <th>性别</th>
                                         <th>证件号</th>
                                         <th>职位</th>
                                         <th>操作</th>
                                         <c:forEach var="items" items="${naturalPersonList}" varStatus="varStatus">
                                             <tr>
                                                 <td><input type="checkbox"></td>
                                                 <td>${items.name}</td>
                                                 <td>${items.sex}</td>
                                                 <td>${items.identifyNumber}</td>
                                                 <td>${items.dutyCode}</td>
                                                 <td>操作</td>
                                             </tr>
                                         </c:forEach>
                                         </thead>
                                         <tbody id="J_TbData">
                                         </tbody>
                                     </table>

					             </div>
					             <div id=“coupon-expired” class="hide" >
					                 <div style="display: flex;justify-content: space-around;">
                                         <div style="width: 45%;">
                                             <h4>股东信息</h4>
                                             <table>
                                                 <thead>
                                                 <tr>
                                                     <th><input type="checkbox" ></th>
                                                     <th>股东名称</th>
                                                     <th>持股比例</th>
                                                     <th>备注</th>
                                                     <th>操作</th>
                                                 </tr>

                                                 </thead>
                                                 <tbody >
                                                 <c:forEach var="items" items="${inviolableRights}" varStatus="varStatus">
                                                     <c:if test="${items.isShare==0}">
                                                         <tr>
                                                             <td><input type="checkbox" onclick="test(this)"></td>
                                                             <td>${items.name}</td>
                                                             <td>${items.holderScale}</td>
                                                             <td>${items.comment}</td>
                                                             <td>操作</td>
                                                         </tr>
                                                     </c:if>
                                                 </c:forEach>
                                                 </tbody>
                                             </table>
                                         </div>
                                         <div style="width: 45%;">
                                             <h4>投资情况</h4>
                                             <table>
                                                 <thead>
                                                 <tr>
                                                     <th><input type="checkbox" ></th>
                                                     <th>被投资方</th>
                                                     <th>持股比例</th>
                                                     <th>备注</th>
                                                     <th>操作</th>
                                                 </tr>

                                                 </thead>
                                                 <tbody >
                                                 <c:forEach var="items" items="${inviolableRights}" varStatus="varStatus">
                                                     <c:if test="${items.isShare==1}">
                                                         <tr>
                                                             <td><input type="checkbox" onclick="test(this)"></td>
                                                             <td>${items.name}</td>
                                                             <td>${items.holderScale}</td>
                                                             <td>${items.comment}</td>
                                                             <td>操作</td>
                                                         </tr>
                                                     </c:if>
                                                 </c:forEach>
                                                 </tbody>
                                             </table>
                                         </div>
                                     </div>
					             </div>
					         </div>
					     </div>
				<bf:formGroup>
					<div class="col-sm-4 col-sm-offset-5">
						<bc:button type="primary" text="返回" icon="arrow-circle-left">
							<be:event>
								window.location = '${base}/bus/legalPerson/list';
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
              ///  $('#container').html(str);

            },
            //请求失败的处理
            error:function(jqXHR){
                console.log(jqXHR);
            }
        });

        //动态创建表格 拼接html
        // var html = "";
        // for( var i = 0; i < data.length; i++ ) {
        //     html += "<tr>";
        //     html +=     "<td contentEditable='true'>" + data[i].name + "</td>"
        //     html +=     "<td contentEditable='true'>" + data[i].sex + "</td>"
        //     html +=     "<td contentEditable='true'>" + data[i].idno + "</td>"
        //     html +=     "<td contentEditable='true'>" + data[i].type + "</td>"
        //     html +=     "<td  >" + data[i].toDo + "</td>"
        //     html += "</tr>";
        // }
        // $("#J_TbData").html(html);
        //动态创建表格 拼接html
        // var html1 = "";
        // for( var i = 0; i < data1.length; i++ ) {
        //     html1 += "<tr>";
        //     html1 +=     "<td contentEditable='true'>" + data1[i].name + "</td>"
        //     html1 +=     "<td contentEditable='true'>" + data1[i].sex + "</td>"
        //     html1 +=     "<td contentEditable='true'>" + data1[i].idno + "</td>"
        //     html1 +=     "<td >" + data1[i].toDo + "</td>"
        //     html1 += "</tr>";
        // }
        // $("#J_TbData1").html(html1);
        //动态创建表格 拼接html
        // var html2 = "";
        // for( var i = 0; i < data2.length; i++ ) {
        //     html2 += "<tr>";
        //     html2 +=     "<td>" + data2[i].name + "</td>"
        //     html2 +=     "<td>" + data2[i].sex + "</td>"
        //     html2 +=     "<td>" + data2[i].idno + "</td>"
        //     html2 +=     "<td>" + data2[i].toDo + "</td>"
        //     html2 += "</tr>";
        // }
        // $("#J_TbData2").html(html2);
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
    function test(o) {
        if (!o.checked) {
            return;
        }
        var tr = o.parentNode.parentNode;
        var tds = tr.cells;
        var str = "you click ";
        for(var i = 0;i < tds.length;i++){
            if (i <tds.length) {
                strstr = str + tds[2].innerHTML + "--";
            }
        }
        alert(str);
        alert(strstr);
    }
</script>
</body>
</html>