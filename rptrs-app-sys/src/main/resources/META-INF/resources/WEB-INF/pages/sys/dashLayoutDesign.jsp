<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <link rel="stylesheet" type="text/css" href="${res}/css/plugins/jQueryUI/jquery-ui.css"/>
    <link rel="stylesheet" type="text/css" href="${res}/css/dashLayout.css"/>
    <title>布局设计</title>
    <style type="text/css">
        #rightAddColumns {
            position: relative;
            left: -10px;
        }

        #removeRightColumn {
            position: relative;
            left: -10px;
        }

        #wigetSelect {
            position: relative;
            left: -50px;
        }

        #addWidget {
            position: relative;
            left: 20px;
        }

        #saveConfig {
            position: relative;
            left: -20px;
        }

        #back {
            position: relative;
            left: -70px;
        }
    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="${title}" col="12" collapsible="true">
            <bf:form id="" validation="false">
                <bf:formGroup>
                    <bc:col col="2">
                        <bc:button id="rightAddColumns" icon="plus" text="在右侧增加列"/>
                    </bc:col>
                    <bc:col col="2">
                        <bc:button id="removeRightColumn" type="primary" icon="times" text="删除最右侧的列"/>
                    </bc:col>
                    <bc:col col="2">
                        <bf:select id="wigetSelect" name="name" value="code" dataSource="custom"
                                   url="sys/dashLayoutDesign/getAllSelectWidget"/>
                    </bc:col>
                    <bc:col col="2">
                        <bc:button id="addWidget" type="primary" text="添加组件" icon="plus"/>
                    </bc:col>
                    <bc:col col="2">
                        <bc:button id="saveConfig" type="primary" text="保存配置" icon="save"/>
                    </bc:col>
                    <bc:col col="2">
                        <bc:button id="back" icon="arrow-circle-left" text="返回"/>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup>
                    <div id="main" region="center" style="border-top:none; width: 1148px;overflow: auto;"></div>
                </bf:formGroup>
            </bf:form>
        </bc:ibox>
    </div>

    <div id="main" region="center" style="border-top:none;background-color: white;width: 1148px;overflow: auto;">

    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript" src="${res}/js/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="${res}/js/echarts/echarts.js"></script>
<script type="text/javascript" src="${res}/jep/js/jDashWidget/dashLayoutDesign.js"></script>
<script type="text/javascript" src="${res}/js/jquery.json-2.4.min.js"></script>
<script type="text/javascript">
    $(function () {
        /* 获得编辑的layoutCode */
        layoutCode = '${layoutCode}';
        /*设置模式*/
        _mode = 'design';
        /*设置布局的容器*/
        _container = $('#main');
        /*获取空布局配置*/
        _config = _getDefaultConfig();
        /* 配置的设计类型 */
        _designType = "NORMAL";
        /*获得到当前的布局编码*/
        $.mask.post('${base}/sys/dashLayoutConfig/getLayoutConfigData/' + layoutCode, function (data) {
            if (data.config) {
                _config = data.config;
                console.log(_config);
                version = data.version;
                _designType = data.designType;
            } else {
                _designType = 'NORMAL';
                _config = $.parseJSON('{"columns":[{"id":"column1","width":380,"widgets":[]},{"id":"column2","width":380,"widgets":[]},{"id":"column3","width":380,"widgets":[]}]}');
            }

            /*初始化页面*/
            _init();
            /*初始化工具栏*/
            _initToolbar();

        });
    })
</script>
</body>
</html>