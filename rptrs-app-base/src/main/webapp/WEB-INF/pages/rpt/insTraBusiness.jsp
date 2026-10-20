
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <!-- 加载systemInfo配置文件 -->
    <fmt:setBundle basename="jep" var="sysInfo" />
    <!-- 读取配置值AppName，并赋值给变量appName -->
    <fmt:message key="reportUrl" var="reportUrl" bundle="${sysInfo}" />
    <br:css pack="list"/>
    <style>
        .m-b {
            margin-bottom: -10px;
        }
    </style>
    <title>关联交易表(保险)-保险业务类</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox  col="12" title=""  collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <div id="A">
                        <bc:row cls="m-b" >
                            <bf:formGroup col="3">
                                <bc:label text="年度" col="5" formControl="true"/>
                                <bf:select id="year" name="year" col="7">
                                    <bf:option text ="2020年" value ="2020"></bf:option>
                                    <bf:option text ="2021年" value ="2021"></bf:option>
                                    <bf:option text ="2022年" value="2022"></bf:option>
                                    <bf:option text ="2023年" value="2023"></bf:option>
                                    <bf:option text ="2024年" value="2024"></bf:option>
                                    <bf:option text ="2025年" value="2025"></bf:option>
                                    <bf:option text ="2026年" value="2026"></bf:option>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="3">
                                <bc:label text="季度" col="5" formControl="true"/>
                                <bf:select id="quarter" name="quarter" col="7">
                                    <bf:option text ="空" value =""></bf:option>
                                    <bf:option text ="第一季度" value ="one"></bf:option>
                                    <bf:option text ="第二季度" value ="two"></bf:option>
                                    <bf:option text ="第三季度" value="three"></bf:option>
                                    <bf:option text ="第四季度" value="four"></bf:option>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="3">
                                <bc:label text="月份" col="5" formControl="true"/>
                                <bf:select id="month" name="month" col="7">
                                    <bf:option text ="空" value =""></bf:option>
                                    <bf:option text ="一月" value ="1"></bf:option>
                                    <bf:option text ="二月" value ="2"></bf:option>
                                    <bf:option text ="三月" value ="3"></bf:option>
                                    <bf:option text ="四月" value ="4"></bf:option>
                                    <bf:option text ="五月" value ="5"></bf:option>
                                    <bf:option text ="六月" value ="6"></bf:option>
                                    <bf:option text ="七月" value ="7"></bf:option>
                                    <bf:option text ="八月" value ="8"></bf:option>
                                    <bf:option text ="九月" value ="9"></bf:option>
                                    <bf:option text ="十月" value ="10"></bf:option>
                                    <bf:option text ="十一" value ="11"></bf:option>
                                    <bf:option text ="十二" value ="12"></bf:option>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="3">
                                <bc:label text="统计主体" col="5" formControl="true"/>
                                <bf:select name="tradeORG" id="tradeORG" col="7" dataSource="custom"
                                           url="sys/unit/selectUnit">
                                    <bf:option text="ALL" value="%"></bf:option>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="3">
                                <bc:col col="3">
                                    <table id="ta" style="margin-left:504px;border-collapse: separate;border-spacing:4px;">
                                        <tr>
                                            <td><bc:button type="danger" text="统计" icon="search" click="btnsearch()">
                                            </bc:button></td>
                                            <td><bc:button id="btnReset" type="primary" text="重置" icon="refresh"
                                                           click="btnReast()">
                                                <be:event>
                                                    <%--alert($("#quarter").val())
                                                    var q =document.getElementById("quarter");
                                                    q.value="";
                                                    var m = document.getElementById("month");
                                                    m.value ="";
                                                    alert($("#quarter").val())--%>
                                                    $("#quarter").val("");
                                                    $("#month").val("");
                                                    <%--$("#quarter").text(""); 下拉选项也重置--%>

                                                </be:event>
                                            </bc:button></td>
                                        </tr>
                                    </table>


                                </bc:col>
                            </bf:formGroup>
                            <%--<bf:formGroup col="4">--%>
                            <%--<bc:label text="机构名称" col="5" formControl="true"/>--%>
                            <%--<bf:select name="manage_org" id ="manage_org" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-GHOrg" >--%>
                            <%--<bf:option text="ALL" value="%"></bf:option>--%>
                            <%--</bf:select>--%>
                            <%--</bf:formGroup>--%>


                        </bc:row>
                    </div>


                    <%--<bc:col col="12" colOffset="5">--%>
                    <%--<bc:button  type="danger" text="查询" icon="search" click = "btnsearch()">--%>
                    <%--</bc:button>--%>
                    <%--<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="btnReast()" >--%>
                    <%--</bc:button>--%>
                    <%--</bc:col>--%>
                </bc:row>
            </bf:form>
        </bc:ibox>
        <%--<iframe id="content" name="content" frameborder="0" style="width:100%;height: 500px" scrolling="yes" src="${reportUrl}allBusTranInfo.rptdesign"></iframe>&ndash;%&gt;--%>
        <iframe id="content" name="content" frameborder="0" style="width:100%;height: 500px;background-color: #ffffff;" scrolling="yes" src="${reportUrl}report_business.rptdesign"></iframe>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>

<script src="../../static/js/jquery.dialog.js"></script>
<script type="text/javascript">

    //查询
    function btnsearch() {
        //var a = $("#tranType").val()
        var startTime = "";
        var endTime = "" ;
        var year = document.getElementById("year").value;
        var quarter = document.getElementById("quarter").value;
        var month = document.getElementById("month").value;
        var tradeORG = $("#tradeORG").val();
        if(quarter == "one"){
            if(month == '1'){
                startTime = year+"-01-01";
                endTime = year + "-01-31";
            }else if (month == '2'){
                if (year%4==0&&year%100!=0||year%400==0){
                    startTime = year+"-02-01";
                    endTime = year + "-02-29";
                }else {
                    startTime = year+"-02-01";
                    endTime = year + "-02-28";
                }

            }else if (month == '3') {
                startTime = year+"-03-01";
                endTime = year + "-03-31";
            }else {
                alert("请检查月份输入是否正确");
                return;
            }
        }else if(quarter == "two"){
            if(month == '4'){
                startTime = year+"-04-01";
                endTime = year + "-04-30";
            }else if (month == '5'){
                startTime = year+"-05-01";
                endTime = year + "-05-31";
            }else if (month == '6') {
                startTime = year+"-06-01";
                endTime = year + "-06-30";
            }else {
                alert("请检查月份输入是否正确");
                return;
            }
        }else if(quarter == "three"){
            if(month == '7'){
                startTime = year+"-07-01";
                endTime = year + "-07-31";
            }else if (month == '8'){
                startTime = year+"-08-01";
                endTime = year + "-08-31";
            }else if (month == '9') {
                startTime = year+"-09-01";
                endTime = year + "-09-30";
            }else {
                alert("请检查月份输入是否正确");
                return;
            }
        }else if(quarter == "four"){
            if(month == '10'){
                startTime = year+"-10-01";
                endTime = year + "-10-31";
            }else if (month == '11'){
                startTime = year+"-11-01";
                endTime = year + "-11-30";
            }else if (month == '12') {
                startTime = year+"-12-01";
                endTime = year + "-12-30";
            }else {
                alert("请检查月份输入是否正确");
                return;
            }
        }else {
            alert("请输入季度");
            return;
        }
        // window.location ="${base}/report/design/allBusTranInfoView?startTime="+startTime+"&endTime="+endTime+"&manage_org="+manage_org;
        document.getElementById("content").src ="${reportUrl}report_business.rptdesign"+"&startTime="+startTime+"&endTime="+endTime+"&tradeORG="+tradeORG;
    }


    //重置
    function btnReast() {

        window.location = '${base}/report/design/insTraBusiness';
    }

</script>
</body>
</html>

