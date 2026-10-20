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
    <title>单笔其他关联交易报表查询</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox  col="12" title=""  collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="季报:" col="5" formControl="true"/>
                        <label><input name="isq" style="margin-top: 10px" type="radio" value="1"  class="merchantzc_radio"/>是 </label>
                        <label><input name="isq" type="radio" value="0" checked="" class="merchantzc_radio" />否 </label>
                    </bf:formGroup>

                    <div id="A">
                        <bc:row cls="m-b" >
                            <bf:formGroup col="4">
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
                            <bf:formGroup col="4">
                                <bc:label text="季度" col="5" formControl="true"/>
                                <bf:select id="quarter" name="quarter" col="7">
                                    <bf:option text ="空" value =""></bf:option>
                                    <bf:option text ="第一季度" value ="one"></bf:option>
                                    <bf:option text ="第二季度" value ="two"></bf:option>
                                    <bf:option text ="第三季度" value="three"></bf:option>
                                    <bf:option text ="第四季度" value="four"></bf:option>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="机构名称" col="5" formControl="true"/>
                                <bf:select name="manage_org" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-GHOrg" >
                                    <bf:option text="ALL" value="%"></bf:option>
                                </bf:select>
                            </bf:formGroup>

                            <bf:formGroup col="4">
                                <bc:label text="关联交易类别" col="5" formControl="true"/>
                                <bf:select name="related_tran_type" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-IdentifyType" >
                                    <bf:option text="ALL" value="%"></bf:option>
                                </bf:select>
                            </bf:formGroup>
                        </bc:row>
                    </div>


                    <div id="B">
                        <bc:row cls="m-b"  >
                            <bf:formGroup col="4" type="success">
                                <bc:label text="开始日期" col="5"  formControl="true"></bc:label>
                                <bf:datebox name="startTime" id="startTime" col="7" format="date">
                                </bf:datebox>
                            </bf:formGroup>
                            <bf:formGroup col="4" type="success">
                                <bc:label text="结束日期" col="5"  formControl="true"></bc:label>
                                <bf:datebox id="endTime" name="endTime" col="7" format="date">
                                </bf:datebox>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="机构名称" col="5" formControl="true"/>
                                <bf:select name="manage_org" id ="manage_org" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-GHOrg" >
                                    <bf:option text="ALL" value="%"></bf:option>
                                </bf:select>
                            </bf:formGroup>

                            <bf:formGroup col="4">
                                <bc:label text="关联交易类别" col="5" formControl="true"/>
                                <bf:select name="related_tran_type" id ="related_tran_type" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-IdentifyType" >
                                    <bf:option text="ALL" value="%"></bf:option>
                                </bf:select>
                            </bf:formGroup>
                        </bc:row>
                    </div>
                    <bc:col col="12" colOffset="5">
                        <bc:button  type="danger" text="查询" icon="search" click = "btnsearch()">
                        </bc:button>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh"  click="btnReast()">

                        </bc:button>
                    </bc:col>
                </bc:row>
            </bf:form>
        </bc:ibox>
        <%--<iframe id="content" name="content" frameborder="0" style="width:100%;height: 500px" scrolling="yes" src="${reportUrl}report_singleService.rptdesign"></iframe>--%>
        <iframe id="content" name="content" frameborder="0" style="width:100%;height: 500px" scrolling="yes" src="${reportUrl}report_singleService.rptdesign"></iframe>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>

<script src="../../static/js/jquery.dialog.js"></script>
<script type="text/javascript">
    /* function btnReset(){
         var q =document.getElementById("quarter").value;
         q.value="";
         var m = document.getElementById("month").value;
         m.value = "";
     }*/
    //初始化
    function init() {
        $("#A").hide();

    };
    init();

    $(function(){
        var isPermanentValue = $('input[name="isq"]:checked ').val();
        if(isPermanentValue == "0" || isPermanentValue==""){
            $("#B").show();
            $("#A").hide();
        }
        if(isPermanentValue == "1"){
            $("#A").show();
            $("#B").hide();
        }
        $(".merchantzc_radio").click(function(){
            var isPermanentValue = $('input[name="isq"]:checked ').val();
            if(isPermanentValue == "0" || isPermanentValue==""){
                $("#B").show();
                $("#A").hide();
            }
            if(isPermanentValue == "1"){
                $("#A").show();
                $("#B").hide();
            }
        })
    })

    //查询
    function btnsearch() {
        var startTime = "";
        var endTime = "" ;
        var year = document.getElementById("year").value;
        var quarter = document.getElementById("quarter").value;
        var time1 = $('input[name="startTime"]').val();
        var time2 = $('input[name="endTime"]').val();
        var isPermanentValue = $('input[name="isq"]:checked ').val();
        var related_tran_type = $("#related_tran_type").val();
        var manage_org = $("#manage_org").val();
        var param = document.getElementById("param1");
        if(isPermanentValue == "1"){
            if(quarter != null || quarter !=""){
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
            }else{
                startTime = year+"-01-01";
                endTime = year+"-12-31";
            }


            /*alert(startTime+"----"+endTime)*/
        }else{
            if(time1 =="" && time2 ==""){
                $.messager.alert('错误', "请选择查询日期", 'error',function()
                {
                    window.location = "${base}/report/design/singleOther";
                });
                return;            }
            else if(time1 =="" ){
                $.messager.alert('错误', "请选择开始日期", 'error',function()
                {
                    window.location = "${base}/report/design/singleOther";
                });
                return;            }
            else  if(time2 ==""){
                $.messager.alert('错误', "请选择结束日期", 'error',function()
                {
                    window.location = "${base}/report/design/singleOther";
                });
                return;            }

            startTime = time1;
            endTime = time2;
            // alert(startTime+"----"+endTime)
        }
        document.getElementById("content").src ="${reportUrl}report_singleService.rptdesign"+"&startTime="+startTime+"&endTime="+endTime+"&manage_org="+manage_org;

        //window.location ="${base}/report/design/singleOtherView?startTime="+startTime+"&endTime="+endTime+"&manage_org="+manage_org+"&related_tran_type="+related_tran_type;

    }


    //重置
    function btnReast() {

        window.location = '${base}/report/design/singleOther';
    }



    //初始化01
    /* window.onload=function(){
         $("#A").hide();
     }*/
</script>
</body>
</html>