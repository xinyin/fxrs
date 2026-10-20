<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <%--  <title>待生成报送文件数据</title>--%>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="待生成控制委员会报告数据" subTitle="" col="12" collapsible="false" >
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <%-- <bf:formGroup col="4">
                         <bc:label text="报送机构" col="5" formControl="true"/>
                         <bf:text id="subOrg" name="subOrg" col="7"
                                  value="${subOrg}"
                                  disabled="true">
                         </bf:text>
                     </bf:formGroup>--%>
                    <bf:formGroup col="8">
                        <bc:label text="合同签署日期" col="3" formControl="true"/>
                        <bf:datebox id="startTranDate" name="startTranDate" col="3">
                        </bf:datebox>
                        <div style="float: left ;margin-top:8px">
                            ----
                        </div>
                        <bf:datebox id="endTranDate" name="endTranDate" col="3">
                        </bf:datebox>
                    </bf:formGroup>
                    <%-- <bf:formGroup col="4">
                         <bc:label text="季度" col="5" formControl="true"/>
                         <bf:select id="subQuarter" name="__LIKES_subQuarter" col="7" dataSource="custom"
                                    url="sys/dictItem/getDictItems/BUS-Quarter" >
                             <bf:option text="请选择" value=""></bf:option>
                             <be:event type="select">
                                 var input = $("#subQuarter");
                                 input.focus();
                                 input.blur();
                             </be:event>
                         </bf:select>
                     </bf:formGroup>--%>
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="publogTable" idField="pid" url="${base}/pub/publog" queryUrl="/queryQuarter"  multipleVisible="false">
                <bc:toolbar id="export" text="导出" type="button" handler="out"/>
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="tranType" title="交易类型" width="100"/>
                <bc:column field="number" title="件数" width="100"/>
                <bc:column field="amount" title="金额" width="100"/>
                <bc:column field="proprotion" title="占比" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<div  id="exampleModalCenter" role="dialog"
      style="display: none">
    <div class="modal-body">
        <div style="text-align: center" id="loading"><p style="color: dodgerblue">正在生成报送文件，请稍后操作...</p></div>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    //导出
    function out() {

        var startTranDate = $("[name='startTranDate']").val() == "" ? "null" : $("[name='startTranDate']").val();
        var endTranDate = $("[name='endTranDate']").val() == "" ? "null" : $("[name='endTranDate']").val();

        var type = "check";
        var data = type + "," + startTranDate + "," + endTranDate  ;
        var url = '${base}/sys/xlsie/reportDataControlExcelImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }


    $("#subYear").datetimepicker({
        format:"yyyy",
        autoclose: true,
        startView:4,
        minView:4,
        todayHighlight:true
    });
    $(document).ready(function () {
        $("#subYear").datetimepicker("setDate", new Date());
    });

    //日期插件初始化
    $('#startTranDate').datetimepicker({
        language:  'zh-CN',
        format:'yyyy-mm-dd',
        autoclose: true,
        minView: "month"
    }).on("changeDate",function(ev){  //值改变事件
        //选择的日期不能大于第二个日期控件的日期
        if(ev.date){
            $("#endTranDate").datetimepicker('setStartDate', new Date(ev.date.valueOf()));
        }else{
            $("#endTranDate").datetimepicker('setStartDate',null);
        }
    });
    $('#endTranDate').datetimepicker({
        language:  'zh-CN',
        format:'yyyy-mm-dd',
        minView: "month",
        autoclose: true,

    }).on("changeDate",function(ev){
        //选择的日期不能小于第一个日期控件的日期
        if(ev.date){
            $("#startTranDate").datetimepicker('setEndDate', new Date(ev.date.valueOf()));
        }else{
            $("#startTranDate").datetimepicker('setEndDate',new Date());
        }
    });


</script>
</body>
</html>