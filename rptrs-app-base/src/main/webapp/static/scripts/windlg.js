/**
 * 调试变量，解决在浏览器中由于每次打开的窗口url后都有个随机数，而不能设置断点的问题。<br/>
 * 开发过程中设置为true。在打开窗口后面不添加随机数；<br/>
 * 生产环境下设置为false。在打开的窗口后面添加随机数，例如：&xPlatformRandomNum=1234598798<br/>
 */
var debug = true;

/**
 * 在生产环境下（debug=false）需要为每个打开的Dlg的URL后添加一个随机数参数，以避免浏览器缓存。<br/>
 * 需要指定一个在项目中绝对用不到的变量名，默认为'xPlatformRandomNum'<br/>
 */
var randomParamName = 'xPlatformRandomNum';

function initWindow(winId, width, height) {
}

/**
 * 打开窗口,支持加载时自定义回调函数
 * @param winId 窗口ID（可选）
 * @param title 窗口标题（可选）
 * @param path 路径（可选）
 * @param callback 回调函数（可选）
 * @param props 兼容jquery-easyui的窗口属性（必须），扩展属性inframe：是否使用iframe，size：窗口大小（small,medium,large）
 * @return 有指定id的div，可当成jquery对象使用
 */
function openWindow() {
    var args = ['winId', 'title', 'href', 'onLoad'];
    var props = {};

    for (var i = 0; i < arguments.length; i++) {
        if (i < arguments.length - 1 || typeof arguments[i] != "object")
            props[args[i]] = arguments[i];
        else
            props = $.extend(arguments[i], props);
    }

    var defProps = {size: 'medium'/*small,medium,large*/, cache: false};
    var forceProps = {resizable: false, closed: false, modal: true, maximizable: false, minimizable: false};
    props = $.extend($.extend($.extend($.extend($.extend({}, $.fn.panel.defaults), $.fn.window.defaults), defProps), props), forceProps);

    if (props.size == 'small') {
        $.extend(props, {width: 400, height: 300});
    } else if (props.size == 'medium') {
        $.extend(props, {width: 600, height: 500});
    } else if (props.size == 'large') {
        $.extend(props, {width: 800, height: 700});
    } else if (props.size == 'auto') {
        $.extend(props, {width: 'auto', height: 'auto'});
    }

    if (props.href) {
        if (!debug) {//非debug模式，添加随机数，防止浏览器缓存
            props.href += (props.href.indexOf('?') >= 0 ? '&' : '?') + randomParamName + '=' + Math.random();
        }
    }

    var res = null, isnew = false;
    if (props.inFrame === true) {
        props.content = '<iframe id="' + props.winId + '_iframe" border="0" frameborder="no" framespacing="0" style="width:100%;height:100%;" src="' + props.href + '"></iframe>';
        delete props.href;
        if (top.$('#' + props.winId)[0]) {
            res = top.$('#' + props.winId);
        } else {
            res = top.$('<div id="' + props.winId + '" style="padding:4px;">');
            isnew = true;
        }
    } else {
        if ($('#' + props.winId)[0]) {
            res = $('#' + props.winId);
        } else {
            res = $('<div id="' + props.winId + '" style="padding:4px;">');
            isnew = true;
        }
    }
    props.id = props.winId;
    res[0]._windowArgs = props.windowArgs;
    delete props.windowArgs;

    res.window(props);

    /*
     * 该行代码会导致在IE6下首页的“修改密码”功能出现权限不足的错误
     * 延迟处理会解决IE6权限不足的问题
     * 如果不刷新，IE6下修改密码的页面也显示不出
     * 在其他版本的浏览器中，没有发现不刷新会导致页面显示不出
     * 但刷新会导致页面的url请求两次
     * modified by mamin 2013/12/6
     */

    if ($.browser.msie && $.browser.version == '6.0') {
        setTimeout(function () {
            if (!isnew) res.window('refresh');
        }, 1000);
    }

    return res;
}

/**获取指定对话框的返回值，在closeWindow的第二个参数中设置对话框返回值*/
function getWindowRes(winId) {
    var win = $("#" + winId)[0];
    if (win && win._windowRes)
        return win._windowRes;
    win = top.$("#" + winId)[0];
    if (win && win._windowRes)
        return win._windowRes;
    return {};
}

/**获取指定对话框的参数，在openWindow的参数中设定对话框参数*/
function getWindowArgs(winId) {
    var win = $("#" + winId)[0];
    if (win && win._windowArgs)
        return win._windowArgs;
    win = top.$("#" + winId)[0];
    if (win && win._windowArgs)
        return win._windowArgs;
    return {};
}

/**关闭对话框*/
function closeWindow(winId, res) {
    if ($("#" + winId)[0] && $("#" + winId).window) {
        $("#" + winId)[0]._windowRes = res;
        $("#" + winId).window("close");
    }

    if (top.$("#" + winId)[0] && top.$("#" + winId).window) {
        top.$("#" + winId)[0]._windowRes = res;
        top.$("#" + winId).window("close");
        //以下3行代码会导致ie浏览器再次打开窗口（如新建/编辑窗口）时，窗口中的文本框无法回去焦点（光标无法进入文本框，导致无法编辑），gyzhang@20131223@nanhai
        //top.$('.window-shadow').remove();
        //top.$('.window-mask').remove();
        //top.$('.panel.window').remove();
    }
}

/*function textAlignLeft(value){
	if(value == null || value=="null")  value = "";
	return '<div style="text-align:left;">' + value + '</div>';
}
function textAlignRight(value){
	if(value == null || value=="null")  value = "";
	return '<div style="text-align:right;">' + value + '</div>';
}*/
var easyui = easyui || {};
easyui.datagrid = easyui.datagrid || {};
easyui.datagrid.headcenter = function () {
    $(".datagrid-header .datagrid-cell").css('text-align', 'center');
};

jQuery(function ($) {
    if ($.browser.mozilla) {
        try {
            var formdiv = document.getElementById("formdiv").clientHeight;
            if (formdiv != null) {
                var clientHeight = document.body.clientHeight;
                $("#tabdiv").css("height", clientHeight - formdiv - 2);
            }
        } catch (e) {
        }
    }
});