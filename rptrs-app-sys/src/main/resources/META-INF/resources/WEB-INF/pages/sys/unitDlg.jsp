<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>选择机构</title>
</head>
<body class="gray-bg">
<!-- 整体布局 -->
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="iboxUnitDlg" title="" col="12" collapsible="false">
            <div class="row">
                <bc:col col="12">
                    <div id="tree" data-toggle="jTree" data-auto-param="id=parentId" data-alert-type="toastr"></div>
                </bc:col>
            </div>
            <div class="row">
                <bc:col col="4" colOffset="8">
                    <bc:button text="确定" type="primary">
                        <be:event>
                            if(mult){
                            var nodes = $('#tree').jTree('getCheckedChildNodes');
                            var codeList = new Array();
                            var nameList = new Array();
                            $.each(nodes, function(i, val){
                            codeList.push(val.unitCode);
                            nameList.push(val.displayName);
                            });

                            $.messager.args('jUnitSelect').result([{
                            'unitCode': codeList,
                            'unitName': nameList
                            }]);
                            }
                            else
                            {
                            var nodes = $('#tree').jTree('getSelectedNodes');
                            var args = $.messager.args('jUnitSelect');

                            $.messager.args('jUnitSelect').result([{
                            'unitCode': nodes[0].unitCode,
                            'unitName': nodes[0].unitName,
                            'hierarchy': nodes[0].hierarchy
                            }]);
                            }

                            $.messager.hide('jUnitSelect');
                        </be:event>
                    </bc:button>
                    <bc:button text="取消" outline="true">
                        <be:event>
                            $.messager.hide('jUnitSelect');
                        </be:event>
                    </bc:button>
                </bc:col>
            </div>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    var mult;
    var _URL = $.helper.curl(base, '/sys/unit/selectUnit/');
    $(document).ready(function () {
        var args = $.messager.args('jUnitSelect');
        var options = args2options(args);
        //重新加载tree
        $('#tree').jTree('reload', options);

    });

    /* 根据传入的参数设置对应的属性 */
    function args2options(args) {

        /* 初始化属性集*/
        var options = {toggle: "jTree"};
        /* 是否传入参数，如果没有传人，则设置为默认参数 */
        if (args) {
            /* 判断是否多选 */
            if (args['mult'] == "true" || args['mult'] == true) {
                options['check'] = true;
                mult = true;
            }

            /* 根据参数判断是否是懒加载 */
            if (args['lazy'] == "false" || args['lazy'] == false) {
                options['lazy'] = false;
                options['url'] = _URL + 'eager';

                /* 获取包含参数 */
                if (args['includes']) {
                    if (options['otherParam']) {
                        options['otherParam']['includes'] = JSON.stringify(args['includes']);
                    } else {
                        options['otherParam'] = {'includes': JSON.stringify(args['includes'])};
                    }
                }

                /* 获取排除参数 */
                if (args['excludes']) {
                    if (options['otherParam']) {
                        options['otherParam']['excludes'] = JSON.stringify(args['excludes']);
                    } else {
                        options['otherParam'] = {'excludes': JSON.stringify(args['excludes'])};
                    }
                }
            } else {
                options['url'] = _URL + 'lazy';
                /* 获取包含参数 */
                if (args['includes']) {
                    /* 判断otherParam是否是一个数组 */
                    if (jQuery.isArray(options['otherParam'])) {
                        options['otherParam'].push('includes');
                        options['otherParam'].push(JSON.stringify(args['includes']));
                    } else {
                        options['otherParam'] = ['includes', JSON.stringify(args['includes'])];
                    }

                }

                /* 获取排除参数 */
                if (args['excludes']) {
                    /* 判断otherParam是否是一个数组 */
                    if (jQuery.isArray(options['otherParam'])) {
                        options['otherParam'].push('excludes');
                        options['otherParam'].push(JSON.stringify(args['excludes']));
                    } else {
                        options['otherParam'] = ['excludes', JSON.stringify(args['excludes'])];
                    }
                }
            }
        }
        return options;
    }
</script>
</body>
</html>