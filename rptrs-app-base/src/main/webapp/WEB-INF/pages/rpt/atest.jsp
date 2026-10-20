<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 加载systemInfo配置文件 -->
<fmt:setBundle basename="jep" var="sysInfo" />
<!-- 读取配置值AppName，并赋值给变量appName -->
<fmt:message key="reportUrl" var="reportUrl" bundle="${sysInfo}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>报表</title>
</head>
<body>
	<div region="center" style="padding: 0px; overflow: auto;">
		年度:<select id="year" style="width: 140px;margin-left: 15px">
		<option value ="2020">2020年</option>
		<option value ="2021">2021年</option>
		<option value="2022">2022年</option>
		<option value="2023">2023年</option>
		<option value="2024">2024年</option>
		<option value="2025">2025年</option>
		<option value="2026">2026年</option>
	</select>
		季度:<select id="quarter" style="width: 140px;margin-left: 15px">
		<option value ="">空</option>
		<option value ="one">第一季度</option>
		<option value ="two">第二季度</option>
		<option value="three">第三季度</option>
		<option value="four">第四季度</option>
	</select>
		月份:<select id="month" style="width: 140px;margin-left: 15px">
		<option value ="">空</option>
		<option value ="1">一月</option>
		<option value ="2">二月</option>
		<option value="3">三月</option>
		<option value="4">四月</option>
		<option value="5">五月</option>
		<option value="6">六月</option>
		<option value="7">七月</option>
		<option value="8">八月</option>
		<option value="9">九月</option>
		<option value="10">十月</option>
		<option value="11">十一月</option>
		<option value="12">十二月</option>
	</select>
		<input type="button" onclick="search()"  id ="search" value="查询">
		<input type="button" onclick="btnReset()"  id ="reset" value="重置">
        <iframe id="content" name="content" frameborder="0" style="width:100%;height: 500px" scrolling="yes" src="${reportUrl}report_tran.rptdesign"></iframe>
	</div>
</body>
<script type="text/javascript">
	function search() {
        //var a = $("#tranType").val()
		var startTime = "";
		var endTime = "" ;
		var year = document.getElementById("year").value;
		var quarter = document.getElementById("quarter").value;
		var month = document.getElementById("month").value;
		if(quarter == null || quarter ==""){
			if(month == null || month ==""){
			    startTime = year+"-01-01";
			    endTime = year+"-12-31";
			}else {
                if(month == "1"){
                    startTime = year+"-01-01";
                    endTime = year + "-01-31";
                }
                if(month == "2"){
                    if(year % 4 ==0){
                        startTime = year+"-02-01";
                        endTime = year + "-02-29";
					}else{
                        startTime = year+"-02-01";
                        endTime = year + "-02-28";
					}
                }
                if(month == "3"){
                    startTime = year+"-03-01";
                    endTime = year + "-03-31";
                }
                if(month == "4"){
                    startTime = year+"-04-01";
                    endTime = year + "-04-30";
                }if(month == "5"){
                    startTime = year+"-05-01";
                    endTime = year + "-05-31";
                }if(month == "6"){
                    startTime = year+"-06-01";
                    endTime = year + "-06-30";
                }if(month == "7"){
                    startTime = year+"-07-01";
                    endTime = year + "-07-31";
                }
                if(month == "8"){
                    startTime = year+"-08-01";
                    endTime = year + "-08-30";
                }
                if(month == "9"){
                    startTime = year+"-09-01";
                    endTime = year + "-09-30";
                }
                if(month == "10"){
                    startTime = year+"-10-01";
                    endTime = year + "-10-31";
                }
                if(month == "11"){
                    startTime = year+"-11-01";
                    endTime = year + "-11-30";
                }
                if(month == "12"){
                    startTime = year+"-12-01";
                    endTime = year + "-12-31";
                }
			}
		}else {
		    if(quarter == "one"){
                startTime = year+"-01-01";
                endTime = year + "-03-31";
			}
            if(quarter == "two"){
                startTime = year+"-04-01";
                endTime = year + "-06-30";
            }
            if(quarter == "three"){
                startTime = year+"-07-01";
                endTime = year + "-09-30";
            }
            if(quarter == "four"){
                startTime = year+"-10-01";
                endTime = year + "-12-31";
            }

		}
		//alert(startTime+":----:"+endTime)


        var b = document.getElementById("content").src;
            document.getElementById("content").src ="${reportUrl}report_tran.rptdesign " + "&startTime="+startTime +"&endTime="+endTime;
            //alert(  b  + "&startTime="+startTime +"&endTime="+endTime)

        }
    function btnReset(){
        var q =document.getElementById("quarter");
        q.value="";
        var m = document.getElementById("month");
        m.value = "";
    }
</script>
</html>