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
            <bc:table id="personTable" idField="pid" url="${base}/sub/person" multipleVisible="false"
                      queryUrl="/queryAddPerson">
                <bc:toolbar id="addFile" text="生成报送文件" type="button" handler="frmSubmitFile"/>
                <bc:column field="pid" title="pid" width="100" visible="false"/>
                <bc:column field="name" title="姓名" width="100"/>
                <bc:column field="domesticOrForeign" title="境内外标识" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                <bc:column field="identityType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="identityNo" title="证件号码" width="100"/>
            </bc:table>
            <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                <bc:button type="primary" text="返回" icon="arrow-circle-left">
                    <be:event>
                        window.location = '${base}/sub/person/sublist';
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
        var data = "person";
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
                        window.location = '${base}/sub/person/sublist';
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
