<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>待生成报送文件数据</title>
    <style>
        table{
            table-layout: fixed;
        }
        td{
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }
    </style>
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

        </bc:row>
    </bf:form>
    <bc:table id="traInterestTable" idField="pid" url="${base}/sub/insInterest"  multipleVisible="false"  queryUrl="querySubFile" >
        <bc:toolbar id="subFile" text="生成报送文件" type="button" handler="frmSubmitFile"/>
        <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
        <bc:column field="tranType" title="交易类型" width="100"
                   formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
        <bc:column field="busType" title="业务类型" width="100"
                   formatter="custom:sys/dictItem/getDictItems/INT-BusinessTypes"/>
        <bc:column field="contractNo" title="合同编号" width="100" />
        <bc:column field="tranDate" title="合同签订日期" width="150" />
        <bc:column field="busStartTime" title="业务起始日期" width="150" />
        <bc:column field="busEndTime" title="业务结束日期" width="150" />
        <bc:column field="tradeAmount" title="交易金额（万元）" width="150" />
        <bc:column field="tradingDirection" title="交易方向" width="100"
                   formatter="custom:sys/dictItem/getDictItems/INS-Tra-Directions"/>
        <bc:column field="tradePrice" title="交易价格" width="200" />
        <bc:column field="pricingPolicy" title="定价政策" width="200" />
        <bc:column field="tranName" title="交易概述" width="200" />
        <bc:column field="tradeSubject" title="交易标的" width="200" />
        <bc:column field="relType" title="关联方类别" width="100"
                   formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
        <bc:column field="relName" title="关联方名称" width="100" />
        <bc:column field="relNumber" title="关联方代码" width="100" />
        <bc:column field="singleRelatedTranType" title="单笔关联交易类型" width="150"
                   formatter="custom:sys/dictItem/getDictItems/INS-SingleRelatedTranType"/>
        <bc:column field="majorType" title="达到重大关联交易的情形" width="200"
                   formatter="custom:sys/dictItem/getDictItems/INS-majorType"/>
        <bc:column field="guaranteeConditions" title="担保条件" width="200"/>
        <bc:column field="remark" title="其他事项说明" width="200" />
    </bc:table>
    <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
    <bc:button type="primary" text="返回" icon="arrow-circle-left">
        <be:event>
            window.location = '${base}/sub/sublog/traInterestList';
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
        var data = "interest";
        var subOrg="复星保德信人寿保险有限公司";
        var url = '${base}/sub/sublog/singleFile/' + data;
        $.ajax({
            url: url,
            type: 'post',
            data: data,
            dataType: 'json',
            beforeSend: function (XMLHttpRequest) {
                $("#exampleModalCenter").attr("style","display:block;");
            },
            success: function (data) {
                if (data.success) {
                    $.messager.alert('成功',data.msg,'info',function(){
                        $("#exampleModalCenter").attr("style","display:none;");
                        window.location = '${base}/sub/sublog/traInterestList';
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
            },
            error: function () {
                $("#exampleModalCenter").attr("style","display:none;");
                $.messager.alert('失败','生成报送文件失败,请重试','error');
            }
        });
    }
</script>
</body>
</html>