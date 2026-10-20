<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>待生成报送文件数据</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
<div class="row">
<bc:ibox title="待生成报送文件数据" subTitle="" col="12" collapsible="true">
    <bf:form id="queryForm" validation="true">
        <bc:row cls="m-b">
            <bf:formGroup col="4">
                <bc:label text="报送机构" col="5" formControl="true"/>
                <bf:text id="subOrg" name="subOrg" col="7"
                         value="复星保德信人寿保险有限公司"
                         disabled="true">
                </bf:text>
            </bf:formGroup>

            <bf:formGroup col="4">
                <bc:label text="年度" col="5" formControl="true"/>
                <bf:datebox id="subYear" name="__LIKES_subYear" col="7" format="date"/>
                <bv:date format="YYYY" message="只能输入合理的年份，类型为YYYY类型"/>
            </bf:formGroup>
            <bf:formGroup col="4">
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
            </bf:formGroup>
            <div class="col-sm-12 col-sm-offset-5">
                <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
            </div>
        </bc:row>
    </bf:form>
    <bc:table id="insAllbustraninfoTable" idField="pid" url="${base}/sub/insAllbustraninfo"  multipleVisible="false" queryUrl="querySubFile">
        <bc:toolbar id="subFile" text="生成报送文件" type="button" handler="frmSubmitFile"/>
        <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
        <bc:column field="contractNo" title="合同编号" width="100"/>
        <bc:column field="tranDate" title="合同签署日期" width="100"/>
        <bc:column field="isAgreement" title="是否统一交易协议" width="100" formatter="fixedDict:IsStatusDict"/>
        <bc:column field="isAgreementImpl" title="统一交易协议执行情况" width="150"/>
        <bc:column field="tradeSubject" title="交易标的" width="100"/>
        <bc:column field="tranType" title="交易类型" width="100"
                   formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
        <bc:column field="busType" title="业务类型" width="100"/>
        <bc:column field="tradeAmount" title="交易金额（万元）" width="100"/>
        <bc:column field="tradingDirection" title="交易方向" width="100"/>
        <bc:column field="tradePrice" title="交易价格" width="100"/>
        <bc:column field="pricingPolicy" title="定价政策" width="100"/>
        <bc:column field="relName" title="关联方名称" width="100"/>
        <bc:column field="relNumber" title="关联方证件号码" width="100"/>
        <bc:column field="relType" title="关联方类型" width="100"
                   formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
<%--        <bc:column field="underInvolveRel" title="底层基础资产是否涉及控股股东或控股股东的关联方" width="100"--%>
<%--                   formatter="fixedDict:IsStatusDict"/>--%>
        <bc:column field="financialAssstsName" title="金融产品名称" width="100"/>
        <bc:column field="financialProScale" title="产品批准/注册/备案号" width="100"/>
        <bc:column field="financialProRatio" title="投资金融产品份额占比（%）" width="100"/>
<%--        <bc:column field="isHoldingSubsidiary" title="是否控股子公司交易" width="100"--%>
<%--                   formatter="fixedDict:IsStatusDict"/>--%>
<%--        <bc:column field="holdingSubsidiaryName" title="控股子公司全称" width="100"/>--%>
<%--        <bc:column field="holdingSubsidiaryNumber" title="控股子公司有效证件号码" width="100"/>--%>

        <bc:column field="remark" title="其他事项说明" width="100"/>
    </bc:table>
    <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
    <bc:button type="primary" text="返回" icon="arrow-circle-left">
        <be:event>
            window.location = '${base}/sub/insAllbustraninfo/list';
        </be:event>
    </bc:button>
    </div>
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
    //生成报送文件
    function frmSubmitFile() {
        var subType = "allbustraninfo";
        var subOrg = "复星保德信人寿保险有限公司";
        var subYear = $("[name='__LIKES_subYear']").val();
        //var subYear = $('#subYear').val();
        var subQuarter = $('#subQuarter').val();
        var url = '${base}/sub/sublog/quaterFile';
        $.ajax({
            url: url,
            type: 'post',
            data: {
                subType : subType,
                subYear :subYear,
                subQuarter :subQuarter
            },
            dataType: 'json',
            beforeSend: function (XMLHttpRequest) {
                $("#exampleModalCenter").attr("style","display:block;");
            },
            success: function (data) {
                if (data.success) {
                    $.messager.alert('成功',data.msg,'info',function(){
                        $("#exampleModalCenter").attr("style","display:none;");
                        window.location = '${base}/sub/insAllbustraninfo/list';
                    });
                } else {
                    if(data.msg == "暂无报送数据生成"){
                        $("#exampleModalCenter").attr("style","display:none;");
                        $.messager.alert('提示',data.msg,'info');
                    }else{
                        $("#exampleModalCenter").attr("style","display:none;");
                        $.messager.alert('失败',data.msg,'error');
                    }
                }
            }
        });
    }

    /**
     * 重置
     */
    function frmReset() {
        location.reload();
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
</script>
</body>
</html>
