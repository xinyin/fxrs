<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>待生成报送文件</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="待生成数据列表" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <%--年度--%>
                   <%-- <bf:formGroup col="4">
                        <bc:label text="年度" col="5" formControl="true"/>
                        <bf:text name="year" col="7" placeholder="年度" value=""/>
                    </bf:formGroup>--%>
                    <%--季度，调用字典接口--%>
                    <%--<bf:formGroup col="4">
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
                    </bf:formGroup>--%>
                </bc:row>
            </bf:form>
            <bc:table id="relParTable" idField="id" multipleVisible="false" url="${base}/east/gljyhz"
                      queryUrl="/querySubFile">
                <bc:toolbar id="addEastGljyView" text="生成报送文件" type="button" handler="frmSubmitFile"/>
                <bc:column field="id" title="id" width="100" visible="false"/>
                <bc:column field="tjzq" title="统计周期" width="100"/>
                <bc:column field="glfbh" title="关联方编号" width="100"/>
                <bc:column field="glfmc" title="关联方名称" width="100"/>
                <bc:column field="glflb" title="关联方类别" width="100"/>
                <bc:column field="gljyywlx" title="关联交易类型" width="100"/>
                <bc:column field="gljyje" title="关联交易金额" width="100"/>
            </bc:table>
            <div class="col-sm-4 col-sm-offset-5" style="padding-top: 3%">
                <bc:button type="primary" text="返回" icon="arrow-circle-left">
                    <be:event>
                        window.location = '${base}/east/gljyhz/list';
                    </be:event>
                </bc:button>
            </div>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    //生成报送文件
    function frmSubmitFile() {
        var type = "E5";
        // var year = $("[name='year']").val();
        // var quarter =$("[name='quarter']").val().trim();
        var url = '${base}/east/gljyhz/createSubRelParFile/';
        $.ajax({
            url: url,
            type: 'post',
            data: { subType:type
                  },
            dataType: 'json',
            success: function (data) {
                if (data.success==false)
                {
                    if (data.msg == "暂无报送数据生成") {
                        $.messager.alert('提示', data.msg, 'info');
                    } else {
                        $.messager.alert('失败', data.msg, 'error');
                    }
                } else{
                    $.messager.alert('成功', data.msg, 'info', function () {
                        window.location = '${base}/east/gljyhz/list';
                    });
                }
            }
        });
    }

</script>
</body>
</html>