<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
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
            <bc:table id="traInvestmentTable" idField="pid" url="${base}/sub/relatedRelation" multipleVisible="false"
                      queryUrl="/queryAdd">
                <bc:toolbar id="subFile" text="生成报送文件" type="button" handler="frmSubmitFile"/>
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="entityName" title="关联方名称/关联方中间节点名称" width="100"/>
                <bc:column field="entityUniscUode" title="关联方代码/关联方中间节点代码" width="100"/>
                <bc:column field="entityType" title="关联方/关联方中间节点类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/Rel_POP"/>
                <bc:column field="superEntityName" title="前一层关联方名称/关联方中间节点名称" width="100"/>
                <bc:column field="superEntityUniscCode" title="前一层关联方代码/关联方中间节点代码" width="100"/>
                <bc:column field="superEntityType" title="前一层关联方/关联方中间节点类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/Rel_POP"/>
                <bc:column field="relationType" title="与前一层的关联关系" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-RelZZD"/>
                <bc:column field="equityShare" title="与前一层之间是否有权益份额" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelequityShare"/>
                <bc:column field="holdingPercent" title="持有比例" width="100"/>
                <bc:column field="pledgeRatio" title="质押股权比例" width="100"/>
                <bc:column field="relationInfo" title="关联关系说明" width="100"/>
                <bc:column field="validStatus" title="该关系是否有效" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <bc:column field="activeTimeString" title="生效日期" width="100"/>
                <bc:column field="invalidTimeString" title="失效日期" width="100"/>
            </bc:table>
            <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                <bc:button type="primary" text="返回" icon="arrow-circle-left">
                    <be:event>
                        window.location = '${base}/sub/relatedRelation/list';
                    </be:event>
                </bc:button>
            </div>
        </bc:ibox>
    </div>
</div>
<div id="exampleModalCenter" role="dialog"
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
        var data = "relatedRelation";
        var subOrg = "复星保德信人寿保险有限公司";
        var url = '${base}/sub/sublog/template/' + data + '/' + subOrg;
        $.ajax({
            url: url,
            type: 'post',
            data: data,
            dataType: 'json',
            beforeSend: function (XMLHttpRequest) {
                $("#exampleModalCenter").attr("style", "display:block;");
            },
            success: function (data) {
                if (data.success) {
                    $.messager.alert('成功', data.msg, 'info', function () {
                        $("#exampleModalCenter").attr("style", "display:none;");
                        window.location = '${base}/sub/relatedRelation/list';
                    });
                } else {
                    if (data.msg == "暂无报送数据生成") {
                        $("#exampleModalCenter").attr("style", "display:none;");
                        $.messager.alert('提示', data.msg, 'info');
                    } else {
                        $("#exampleModalCenter").attr("style", "display:none;");
                        $.messager.alert('失败', data.msg, 'error');
                    }
                }
            },
            error: function () {
                $("#exampleModalCenter").attr("style", "display:none;");
                $.messager.alert('失败', '生成报送文件失败,请重试', 'error');
            }
        });
    }
</script>
</body>
</html>
