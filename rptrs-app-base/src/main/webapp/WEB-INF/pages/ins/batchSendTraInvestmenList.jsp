<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>关联交易表（保险）-投资入股类集中审议</title>
</head>
<style>
    .bs-checkbox{
        display: none;
    }
    .fixed-table-toolbar{
        display: none;
    }
</style>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup hrp="bottom">
                        <bf:formGroup col="4">
                            <bc:label text="年度" col="5" formControl="true"/>
                            <bf:select id="year" name="year" col="7">
                                <bf:option text="请选择" value=""></bf:option>
                                <bf:option text="2020" value="2020"></bf:option>
                                <bf:option text="2021" value="2021"></bf:option>
                                <bf:option text="2022" value="2022"></bf:option>
                                <bf:option text="2023" value="2023"></bf:option>
                                <bf:option text="2024" value="2023"></bf:option>
                                <bf:option text="2025" value="2023"></bf:option>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="季度" col="5" formControl="true"/>
                            <bf:select name="quarter" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-Quarter">
                                <bf:option text="ALL" value=""></bf:option>
                                <be:event type="select">
                                    var input = $("#__LIKES_fromType");
                                    input.focus();
                                    input.blur();
                                </be:event>
                            </bf:select>
                        </bf:formGroup>
                    </bf:formGroup>
                    <bc:row cls="m-b">
                        <div class="col-sm-12 col-sm-offset-5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                        </div>
                    </bc:row>
                </bc:row>
            </bf:form>
            <div><h3>待集中审议交易列表</h3></div>
            <bc:table id="traInvestmentTable" idField="pid" url="${base}/ins/traInvestment" queryUrl="batchSubmit">
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="relId" title="关联方ID" width="100" visible="false"/>
                <bc:column field="transNum" title="交易流水号" width="100"/>
                <bc:column field="tranName" title="交易名称" width="100"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <%--                <bc:column field="investmentType" title="证件类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-certType"/>--%>
                <bc:column field="relNumber" title="证照号码" width="100"/>
                <bc:column field="investmentType" title="业务类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-InvestmentType"/>
                <bc:column field="majorTrade" title="是否重大关联交易" width="100" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="relTradeAmount" title="关联交易金额（万元/人民币）" width="100"/>
                <bc:column field="tranDate" title="合同签署日期" width="100"/>
                <bc:column field="pracTradeAmount" title="实际交易金额" width="100" visible="false"/>
                <bc:column field="considerType" title="审议类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-considerType"/>
                <bc:column field="status" title="操作状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:operator id="view" icon="eye" title="操作/查看"/>
            </bc:table>

            <bf:formGroup>
                <div class="col-sm-12 col-sm-offset-5">
                    <bc:button type="danger" text="提交审批" icon="save" click="batchSubmit()">
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/ins/traInvestment/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    $(document).ready(function () {
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
            var year = parseInt($("#editYear").val());
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
    //刷新表格
    function loadCallback() {
        $('#traInvestmentTable').jTable('refresh');
    }

    /*
   * 重置
   * */
    function frmReset() {
        window.location = '${base}/ins/traInvestment/batchSendList';
    }

    /**集中审议页面跳转*/
    function batchSend(){
        //跳转到集中审议页面
        window.location = '${base}/ins/traInvestment/batchSendList/';
    }

    /*
  根据当前时间和随机数生成备案号
  * */
    function randomNumber() {
        var now = new Date();
        var year = now.getFullYear().toString();
        var month = now.getMonth() + 1;
        var day = now.getDate();
        var hour = now.getHours();
        var minutes = now.getMinutes();
        var seconds = now.getSeconds();
        var no = year+month+day+hour+minutes+seconds;
        return no+(Math.round(Math.random()*89+100)).toString();
    }

    /**
     * 批量提交
     */
    function batchSubmit(){
        //获取查询条件
        var year = $("[name='year']").val();
        var quarter = $("[name='quarter']").val();
        //查询待提交的数据
        $.ajax({
            url:"${base}/ins/traInvestment/batchConditionSubmit?year="+year+"&quarter="+quarter,
            method:"GET",
            dataType:"JSON",
            traditional:true,
            success:function(data){
                if(data.success){
                    //开始走工作流
                    var objData=data.data;
                    var myDate = new Date();
                    objData.filNum = '1o'+randomNumber()
                    objData.id = myDate.getTime();
                    //机构信息
                    var unitCode = data.unitCode;
                    //部门信息
                    var deptCode = data.deptCode;
                    var deptName = data.deptName;
                    var hdDeptCode = data.hdDeptCode;
                    objData.unitCode=unitCode;
                    objData.deptCode=deptCode;
                    objData.deptName=deptName;
                    objData.hdDeptCode=hdDeptCode;
                    data = JSON.stringify(objData);
                    var formData = $.helper.base64Encode(data);
                    //区分总部一级部门和二级部门
                    var isSecond = data.isSecond;
                    var procDefCode;
                    if(unitCode=='8600'){
                        //总公司
                        if(isSecond){
                            //二级部门
                            procDefCode = 'zyzbejybrel'
                        }else {
                            //一级部门
                            procDefCode='zyzbyjybrel'
                        }
                    }else {
                        //分公司
                        procDefCode = 'zybr_relGeneral';
                    }
                    //挂取工作流
                    var obj = {
                        procDefCode: procDefCode,
                        oldData: formData,
                        newData: formData,
                        uploadSessions: '',
                        procInstId: ''
                    }
                    $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
                        if (data.success) {
                            $.messager.alert('提示', '提交成功。', 'info', function () {
                                window.location = '${base}/ins/traInvestment/list';
                            });
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });
                }else
                {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        })

    }

    //确认按钮
    function toConfirm() {
        var pid = getIdSelections()
        var status = getStatusSelections()
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
        }
        if (status == 9) {
            window.location = '${base}/ins/traInvestment/toConfirm/' + pid;
        } else {
            $.messager.alert('错误', "当前状态不符合要求", 'error');
            return;
        }
    }

    //实际交易数据管理
    function ActualTra() {
        var transNum = getTransNumSelections();
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location = '${base}/ins/traCramcInfo/ActualTra/' + transNum;
    }

    function getTransNumSelections() {
        return $.map($('#traInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row.transNum;
        });
    }

    function getRowSelections() {
        return $.map($('#traInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row;
        });
    }

    function getStatusSelections() {
        return $.map($('#traInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    function getIdSelections() {
        return $.map($('#traInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
</script>
</body>
</html>