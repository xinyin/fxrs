<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title></title>
    <style type="text/css">
        #buttonCls {
            position: relative;
            top: 50px;
        }
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="hyperlinkForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="12">
                        <bc:col col="6">
                            <bc:label text="是否显示" col="4" formControl="true"/>
                            <bf:checkGroup name="isShow" id="isShow" col="8">
                                <bf:radio dataSource="fixedDict" url="YesNo">
                                </bf:radio>
                            </bf:checkGroup>
                        </bc:col>
                        <bc:col col="6">
                            <bc:label text="标题" col="4" formControl="true"/>
                            <bf:text name="title" id="title" col="8" placeholder="请输入标题"/>
                        </bc:col>
                    </bf:formGroup>

                    <bf:formGroup col="12">
                        <bc:col col="6">
                            <bc:label text="显示文本" col="4" formControl="true"/>
                            <bf:text name="showText" id="showText" col="8" placeholder="请输入显示文本"/>
                        </bc:col>
                        <bc:col col="6">
                            <bc:label text="访问路径" col="4" formControl="true"/>
                            <bf:text name="url" id="url" col="8" placeholder="请输入访问路径"/>
                        </bc:col>
                    </bf:formGroup>

                    <bf:formGroup>
                        <div class="col-sm-12" align="center" id="buttonCls">
                            <bc:button type="primary" text="保存" icon="save" click="btnSave()"/>
                            <bc:button text="关闭" icon="times">
                                <be:event>
                                    $.messager.hide('wDashWidgetHyperlink');
                                </be:event>
                            </bc:button>
                        </div>
                    </bf:formGroup>
                </bc:row>
            </bf:form>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    var hyperlink;
    $(function () {
        var args = $.messager.args('wDashWidgetHyperlink');
        //获得上个页面传过来的数据
        hyperlink = args.hyperlink;
        //当数据不为空的时候，页面进行回显
        if (hyperlink) {
            //设置是否显示的回显
            var show = hyperlink.show;
            if (show == true || show == 'true') {
                $('input:radio[value="Y"]').prop("checked", "checked");
            } else {
                $('input:radio[value="N"]').prop("checked", "checked");
            }

            //回显标题
            var title = hyperlink.title;
            if (title) {
                $("#title").val(title);
            }
            //回显文本
            var text = hyperlink.text;
            if (text) {
                $("#showText").val(text);
            }
            //回显访问路径
            var url = hyperlink.url;
            if (url) {
                $("#url").val(url);
            }
        }

    })

    //保存
    function btnSave() {
        $('#hyperlinkForm').data('formValidation').validate();
        var isValid = $('#hyperlinkForm').data('formValidation').isValid();
        if (!isValid) {
            return;
        }
        $.messager.confirm('提示', '确定要保存这些数据吗?', function (result) {
            if (result) {
                //获取是否显示的值
                var show = $('input:radio[name="isShow"]:checked').val();
                if (show == 'Y') {
                    hyperlink.show = 'true';
                } else {
                    hyperlink.show = 'false';
                }

                //获取标题
                var title = $("#title").val();
                hyperlink.title = title;

                //获取文本
                var text = $("#showText").val();
                hyperlink.text = text;

                //获取访问路径
                var url = $("#url").val();
                if (url) {
                    url = url.replace(/\\/g, '/');
                }
                hyperlink.url = url;

                $.messager.hide('wDashWidgetHyperlink');
            }
        });
    }

</script>
</body>
</html>