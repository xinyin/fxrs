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
                </bc:row>
            </bf:form>
            <bc:table id="subTraTable" idField="pid" url="${base}/sub/finProduct" queryUrl="/queryAddProduct"  multipleVisible="false">
                <bc:toolbar id="addFile" text="生成报送文件" type="button" handler="frmSubmitFile"/>
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="productName" title="金融产品全称" width="100"/>
                <bc:column field="productIssueNo" title="产品批准/注册/备案号" width="100"/>
                <bc:column field="productRegisterNo" title="产品代码" width="100"/>
                <bc:column field="productType" title="产品分类" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-pro"/>
                <bc:column field="registerOrg" title="产品登记机构" width="100"/>
                <bc:column field="productOwner" title="产品管理人全称" width="100"/>
                <bc:column field="productOwnerUniscCode" title="管理人统一社会信用代码" width="100"/>
                <bc:column field="productCustodian" title="产品托管人全称" width="100"/>
                <bc:column field="productCustodianUniscCode" title="托管人统一社会信用代码" width="100"/>
            </bc:table>
            <bc:button  type="primary" text="返回" icon="arrow-circle-left">
                <be:event>
                    window.location = '${base}/sub/sublog/list';
                </be:event>
            </bc:button>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    //生成报送文件
    function frmSubmitFile() {
        var data = "pro";
        var subOrg="复星保德信人寿保险有限公司";
        var url = '${base}/sub/sublog/template/' + data +'/'+ subOrg;
        $.ajax({
            url: url,
            type: 'post',
            data: data,
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    $.messager.alert('成功',data.msg,'info',function(){
                        window.location = '${base}/sub/sublog/list';
                    });
                } else {
                    if(data.msg == "暂无报送数据生成"){
                        $.messager.alert('提示',data.msg,'info');
                    }else{
                        $.messager.alert('失败',data.msg,'error');
                    }
                }
            }
        });

    }
</script>
</body>
</html>