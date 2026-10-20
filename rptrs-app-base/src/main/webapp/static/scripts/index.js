/**
 * 创建新选项卡
 * @param tabId    选项卡id
 * @param path      选项卡远程调用路径
 */
function openTab(tabId, path) {
    var tabCount = $('#tt').tabs('tabs').length;
    if (tabCount > 8) {
        $.messager.alert('系统提示', '您打开的页签过多,请关闭一些页签。', 'info');
    }
    var title = document.getElementById(tabId).innerText;
    if ($("#tt").tabs('exists', title)) {
        $("#tt").tabs('select', title);
    } else {
        var name = '_ifr_' + tabId;
        $('#tt').tabs('add', {
            width: $("#tt").parent().width(),
            height: $("#tt").parent().height(),
            title: title,
            closable: true,
            cache: false,
            content: '<iframe id="' + tabId + '" frameborder="0" scrolling="auto" style="z-index: 1;width:100%;height:100%; overflow: visible;" src="' + path + '" onload="javascript:setIframeHeight(this);"/>'
        });
    }
}

function setMain(title, href) {
    $(".combo-panel").parent(".panel").remove();
    var centerURL = href;
    var centerTitle = title;
    $('body').layout('panel', 'center').panel({
        title: "所在位置：" + centerTitle,
        href: centerURL
    });
    $('body').layout('panel', 'center').panel('refresh');
    return false;
}

/**
 * 设置IFrame宽度和高度
 * @param w
 */
function setIframeHeight(w) {
    if (document.getElementById) {
        if (w && window.opera) {
            if (w.contentDocument && w.contentDocument.body.offsetHeight) {
                w.height = w.contentDocument.body.offsetHeight;
                w.width = w.contentDocument.body.offsetWidth;
            } else if (w.Document && w.Document.body.scrollHeight) {
                w.height = w.Document.body.scrollHeight;
                w.width = w.Document.body.scrollWidth;
            }

            //w.parent.height=w.height;
            //w.parent.width=w.height;
        }
    }
}

function SetClientWindowHeight(iframeId) {
    var w = document.getElementById(iframeId);
    if (document.getElementById) {
        if (w && !window.opera) {
            if (w.contentDocument
                && w.contentDocument.body.offsetHeight) {
                w.height = w.contentDocument.body.offsetHeight;
            } else if (w.Document && w.Document.body.scrollHeight) {
                w.height = w.Document.body.scrollHeight;
            }
        }
    }
}

/**
 * 初始化多级竖向菜单
 */
function initMenu(path) {
    if (path.indexOf("?") > 0) {
        path = path + "&rand=" + Math.random() * 1000;
    } else {
        path = path + "?rand=" + Math.random() * 1000;
    }

    $.getJSON(path, function (cates) {
        var items = [];
        $.each(cates, function (idx, cate) {
            $('#funMenus').accordion('add', {
                title: cate.menuName,
                iconCls: "icon-add",
                content: loadCategoryMenu(cate)
            });
        });

        $.each(cates, function (idx, cate) {
            $('#cate' + cate.menuCode).menuTree();
        });
    });

    $('#funMenus').accordion({
        fit: true,
        animate: false
    });
}

function loadCategoryMenu(cate) {
    return '<div id="cate' + cate.menuCode + '" class="menuTree"><ul iconCls="icon-ok">' + parse(cate.children) + '</ul></div>';
}

function parse(items) {
    var els = [];

    $.each(items, function (idx, itr) {
        els.push('<li id="m' + itr.menuCode + '"');

        if (itr.children != null && itr.children.length > 0) {
            els.push(' class="parent"><a id="' + itr.menuCode + '"href="#" style="color:#000000;width:180px;height:auto;">' + itr.menuName + '</a>');

            els.push('<ul id="m' + itr.menuCode + '">');
            els.push(parse(itr.children));
            els.push('</ul>');
        } else {
            els.push(' class="child">');
            els.push('<a id="' + itr.menuCode + '"href="" style="color:#000000;width:180px;height:auto;" onclick="javascript:openTab(\'' + itr.menuCode + '\',\'' + itr.url + '\');return false;" target="_ifr_' + itr.menuCode + '">' + itr.menuName + '</a>');
        }

        els.push('</li>');
    });

    return els.join('');
}

String.prototype.trim = function () {
    var t = new String(this);
    t = t.replace(/(^\s*)|(\s*$)/g, "");
    return t.replace(/(^\u3000*)|(\u3000*$)/g, "");
};

//绑定tab的双击事件、右键事件   
function bindTabEvent() {
    $(".tabs-inner").live('dblclick', function () {
        var curTitle = $(this).children("span").text();
        if ($(this).next().is('.tabs-close')) {
            $('#tt').tabs('close', curTitle);
        }
    });
    $(".tabs-inner").live('contextmenu', function (e) {
        var subtitle = $(this).text();
        if (subtitle == "首页") return true;
        $('#tt').tabs('select', subtitle);
        $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });
        var curTitle = $(this).children("span").text();
        $('#mm').data("currtab", curTitle);
        return false;
    });
}

//绑定tab右键菜单事件   
function bindTabMenuEvent() {
    //双击关闭
    $(".tabs-inner").live('dblclick', function () {
        var curTitle = $(this).children("span").text();
        if ($(this).next().is('.tabs-close')) {
            $('#tt').tabs('close', curTitle);
        }
    });
    //刷新
    $('#mm-refresh').click(function () {
        var currTab = $('#tt').tabs('getSelected');
        var url = $(currTab.panel('options').content).find('iframe').attr('src');
        var ifrId = $(currTab.panel('options').content).attr('id');
        $('#tt').tabs('update', {
            tab: currTab,
            options: {
                content: '<div style="width:99%;height:99%;padding:3px;overflow:hidden;"><iframe id="' + ifrId + '" frameborder="0" scrolling="auto" style="width:100%;height:100%; overflow: visible;" src="' + url + '" onload="setIframeHeight(this);"/></div>'
            }
        });
    });

    //关闭当前
    $('#mm-close').click(function () {
        var curTitle = $('#mm').data("currtab");
        $('#tt').tabs('close', curTitle);
    });

    //全部关闭   
    $('#mm-closeall').click(function () {
        $('.tabs-inner span').each(function (i, n) {
            if ($(this).parent().next().is('.tabs-close')) {
                var t = $(n).text();
                $('#tt').tabs('close', t);
            }
        });
    });

    //关闭其它   
    $('#mm-closeother').click(function () {
        var curTitle = $('#mm').data("currtab");
        $('.tabs-inner span').each(function (i, n) {
            if ($(this).parent().next().is('.tabs-close')) {
                var t = $(n).text();
                if (t != curTitle)
                    $('#tt').tabs('close', t);
            }
        });
    });
}

//2012年2月8日（菜单修改后）   start
function showMenu(obj) {  //系统框架菜单的展开和隐藏。
    var lm = document.getElementById("leftMenu");
    if (lm.style.display == "block") {
        lm.style.display = "none";
        obj.src = "resource/images/flexRight.gif";
        obj.alt = "展开";
        obj.onmouseover = function () {
            obj.src = "resource/images/flexRightOver.gif";
        };
        obj.onmouseout = function () {
            obj.src = "resource/images/flexRight.gif";
        };
        document.all._tabpane_TabSet_Apps.style.width = "100%";  //给tabset重新定义宽度,防止它跟随当前宽度不改变。
    } else {
        lm.style.display = "block";
        obj.src = "resource/images/flexLeft.gif";
        obj.alt = "收缩";
        obj.onmouseover = function () {
            obj.src = "resource/images/flexLeftOver.gif";
        };
        obj.onmouseout = function () {
            obj.src = "resource/images/flexLeft.gif";
        };
    }
}

function menu_afterItemChange(code, name, path, contextRelative, target) {
    var obj = document.getElementById(code);
    var title;
    if ($.browser.msie) {
        title = obj.innerText;
    } else {
        title = obj.textContent;
    }
    openMainTabs(code, title, path);
}

/*关闭某个tab页面*/
function closeMainTabs(title) {
    var tabset = window.top.$('#tt');
    if (tabset == undefined) return;

    if (tabset.tabs && tabset.tabs('exists', title)) {
        tabset.tabs('close', title);
    }
}

/*
 * 根据标签页的标题，返回指定标签页中，内嵌的frame的window对象
 */
function getMainTabFrame(title) {
    var tabset = window.top.$('#tt');
    if (tabset == undefined) return;

    if (tabset.tabs && tabset.tabs('exists', title)) {
        var tab = tabset.tabs('getTab', title);
        if (tab == undefined) return;
        var iframe = tab.panel('body').find('iframe');
        if (iframe && iframe[0]) {
            return iframe[0].contentWindow;
        }
    }
}

function addMainCloseHandler(title, handler) {
    var tabset = (typeof newTab == undefined && newTab) ? $('#tt') : (window.top.$('#tt'));
    var opts = tabset.tabs('options');
    opts.chs.push({
        title: title,
        handler: handler
    });
}

function openMainTabs(code, title, path, newTab, params) {
    var tabset = (typeof newTab == undefined && newTab) ? $('#tt') : (window.top.$('#tt'));
    var tabCount = tabset.tabs('tabs').length;
    if (tabCount > 8) {
        $.messager.alert('系统提示', '您打开的页签过多,请关闭一些页签。', 'info');
    }
    if (tabset.tabs('exists', title)) {
        tabset.tabs('select', title);
    } else {
        if (typeof params != undefined) {
            var prefix = path.indexOf('?') > 0 ? '&' : '?';
            for (var p in params) {
                path += prefix + p + '=' + params[p];
            }
        }
        console.info(params);
        var name = '_ifr_' + code;
        tabset.tabs('add', {
            title: title,
            closable: true,
            cache: false,
            fit: true,
            params: params,
            loadingMessage: code,
            content: '<div style="width:100%;height:100%;"><iframe id="' + name + '" frameborder="0" scrolling="no" style="width:99.5% !important;height:99.4% !important;height:100%;padding:2px;overflow: hidden;" src="' + path + '" onload="javascript:setIframeHeight(this);"></iframe></div>'
        });
        $.get('menuClick/save?menuCode=' + code);
    }
}

function rolinTab(obj, menuInfo) {
    var list = document.getElementById(obj).getElementsByTagName("LI");
    var list2 = document.getElementById(obj).getElementsByTagName("h3");
    var list3 = document.getElementById(obj).getElementsByTagName("a");
    var state = {show: false, hidden: false, showObj: false};
    var state2 = {show2: false, hidden2: false, showObj2: false};
    for (var i = 0; i < list.length; i++) {
        var tmp = new rolinItem(list[i], state);
        if (i == 0) tmp.pShow();
    }
    for (var j = 0; j < list2.length; j++) {
        var tmp2 = new $rolinItem(list2[j], state2, j, menuInfo);
    }
    for (var j = 0; j < list3.length; j++) {
        var tmp3 = new $3rolinItem(list3[j], state2, j, menuInfo);
    }
}

function $rolinItem(obj, state2, j, menuInfo) { //二级菜单
    obj.onmouseover = function () {
        this.className = "h3Over";
    };
    obj.onmouseout = function () {
        this.className = "";
    };
    obj.onclick = function () {
        //点击对象样式发生变化
        var h3Num = document.getElementById("mainMenu1").getElementsByTagName("H3");
        for (var n = 0; n < h3Num.length; n++) {
            if (n == j) {
                h3Num[n].className = "h3Over";
                h3Num[n].onmouseout = function () {
                    this.className = "h3Over";
                };
            } else {
                h3Num[n].className = "";
                h3Num[n].onmouseout = function () {
                    this.className = "";
                };
            }
        }
        if (obj.nextSibling && obj.nextSibling.nodeName == "DIV") {
            if (obj.nextSibling.style.display == "" || obj.nextSibling.style.display == "none") {
                obj.nextSibling.style.display = "block";
                getFirstChild(obj).nextSibling.firstChild.src = "resource/images/arrow3_2.gif";
            } else {
                obj.nextSibling.style.display = "none";
                getFirstChild(obj).nextSibling.firstChild.src = "resource/images/arrow3.gif";
            }
        } else {
            getFirstChild(obj).nextSibling.firstChild.display = "none";
//	 	var record = ds_usermenu.getFirstRecord();
            var record = menuInfo;
            if (record != null) {
                for (var r = 0; r < record.length; r++) {
                    if (record[r].menuCode == obj.id) {
                        menu_afterItemChange(record[r].menuCode, record[r].menuName, record[r].url, record[r].contextRelative, record[r].menuTarget);
                        return;
                    }
                }
            }
//	 	while (record!=null){
//	 		if (record.getValue("menuCode")==obj.id){
//	 			menu_afterItemChange(record.getValue("menuCode"),record.getValue("menuName"),record.getValue("menuUrl"),record.getValue("contextRelative"),record.getValue("menuTarget"));
//	 			return ;
//	 		} 	
//	 		record = record.getNextRecord();
//	 	}
        }
    };
}

function $3rolinItem(obj, state2, j, menuInfo) { //三级菜单
    obj.onmouseover = function () {
        this.className = "aOver";
    };
    obj.onmouseout = function () {
        this.className = "";
    };
    obj.onclick = function () {
        //alert(obj.nextSibling.nodeName);
        var h3Num = document.getElementById("mainMenu1").getElementsByTagName("A");
        for (var n = 0; n < h3Num.length; n++) {
            if (n == j) {
                h3Num[n].className = "aOver";
                h3Num[n].onmouseout = function () {
                    this.className = "aOver";
                };
            } else {
                h3Num[n].className = "";
                h3Num[n].onmouseout = function () {
                    this.className = "";
                };
            }
        }
        if (obj.nextSibling.nodeName == "DIV") {
            if (obj.nextSibling.style.display == "" || obj.nextSibling.style.display == "none") {
                obj.nextSibling.style.display = "block";
                getFirstChild(obj).nextSibling.firstChild.src = "resource/images/arrow3_2.gif";
            } else {
                obj.nextSibling.style.display = "none";
                getFirstChild(obj).nextSibling.firstChild.src = "resource/images/arrow3.gif";
            }
        } else {
//	 	var record = ds_usermenu.getFirstRecord();
            var record = menuInfo;
            if (record != null) {
                for (var r = 0; r < record.length; r++) {
                    if (record[r].menuCode == obj.id) {
                        menu_afterItemChange(record[r].menuCode, record[r].menuName, record[r].url, record[r].contextRelative, record[r].menuTarget);
                        return;
                    }
                }
            }
//	 	while (record!=null){
//	 		if (record.getValue("menuCode")==obj.id){
//	 			menu_afterItemChange(record.getValue("menuCode"),record.getValue("menuName"),record.getValue("menuUrl"),record.getValue("contextRelative"),record.getValue("menuTarget"));
//	 			return ;
//	 		}
//	 		record = record.getNextRecord();
//	 	} 	
        }
    };
}

//刷新与首页同iframe的其他标签页，title是标签页的标题
function refreshTab(title) {
    var whichTab = window.top.$('#tt').tabs('getTab', title);
    if (whichTab) {
        var url = $(whichTab).find('iframe').attr('src');
        whichTab.panel('refresh');
    }
}

function rolinItem(obj, state) {  //一级菜单
    var speed = 0.6;
    var range = 1;
    var interval = null;
    var tarH = 0;
    var tar = this;
    var head = getFirstChild(obj);
    var content = getNextChild(head);
    var isOpen = false;
    document.getElementById("mainMenu1").style.height = document.body.offsetHeight - 83; //固定左侧高度
    var neg = document.getElementById("mainMenu1").getElementsByTagName("H2");
    numTip = neg.length * 25 + 83;//计算栏目的个数乘以高度等于栏目的高度,113是上下其他高度总合
    content.style.height = document.body.offsetHeight > numTip ? document.body.offsetHeight - numTip : 100;    //调整下拉框的高度大小
    this.pHidden = function () {
        if (isOpen) hidden();
    };
    this.pShow = show;
    var baseH = content.offsetHeight;
    content.style.display = "none";
    var isOpen = false;

    head.onmouseover = function () {
        this.className = "h2Over";
    };

    head.onmouseout = mouseout;

    head.onclick = function () {
        if (!state.show && !state.hidden) {
            if (!isOpen) {
                head.onmouseout = null;
                show();
            } else {
                hidden();
            }
        }
    };

    function mouseout() {
        this.className = "";
    }

    function show() {
        head.className = "h2Over";
        head.onmouseout = mouseout;

        function mouseout() {
            this.className = "h2Over";
        }

        getFirstChild(head).nextSibling.firstChild.src = "resource/images/more_2.gif";
        state.show = true;
        if (state.openObj && state.openObj != tar) {
            state.openObj.pHidden();
        }
        content.style.height = "0px";
        content.style.display = "block";
        content.style.overflow = "auto";
        state.openObj = tar;
        tarH = baseH;
        interval = setInterval(move, 10);
    }

    function showS() {
        isOpen = true;
        state.show = false;
    }

    function hidden() {
        getFirstChild(head).nextSibling.firstChild.src = "resource/images/more.gif";
        state.hidden = true;
        tarH = 0;
        interval = setInterval(move, 10);
    }

    function hiddenS() {
        head.style.borderBottom = "none";
        head.onmouseout = mouseout;
        head.onmouseout();
        //alert(content.style.display);
        //alert(content.offsetHeight);
        content.style.display = "none";
        isOpen = false;
        state.hidden = false;
    }

    function move() {
        var dist = (tarH - content.style.height.pxToNum()) * speed;
        if (Math.abs(dist) < 1) dist = dist > 0 ? 1 : -1;
        content.style.height = (content.style.height.pxToNum() + dist) + "px";
        if (Math.abs(content.style.height.pxToNum() - tarH) <= range) {
            clearInterval(interval);
            content.style.height = tarH + "px";
            if (tarH != 0) {
                showS();
            } else {
                hiddenS();
            }
        }
    }
}

//	var $ = function($) {return document.getElementById($)};
String.prototype.pxToNum = function () {
    var t = new String(this);
    return Number(t.replace("px", ""));
};

function getFirstChild(obj) {
    var result = obj.firstChild;
    while (!result.tagName) {
        result = result.nextSibling;
    }
    return result;
}

function getNextChild(obj) {
    var result = obj.nextSibling;
    while (!result.tagName) {
        result = result.nextSibling;
    }
    return result;
}

function showTag(obj, obj2) {     //左边tag切换功能
    var r = document.getElementById("tagMenu").getElementsByTagName("li");
    var m = document.getElementById("tagMenu").getElementsByTagName("img");
    if (obj == 1) {
        for (var i = 0; i < 2; i++) {
            if (i == obj - 1) {
                document.getElementById("mainMenu" + (i + 1)).style.display = "block";
                m[i].src = "resource/images/icon1.gif";
            } else {
                document.getElementById("mainMenu" + (i + 1)).style.display = "none";
                m[i].src = "resource/images/icon2.gif";
            }
        }
    } else if (obj == 2) {
        for (var i = 0; i < 2; i++) {
            if (i == obj - 1) {
                document.getElementById("mainMenu" + (i + 1)).style.display = "block";
                m[i].src = "resource/images/icon4.gif";
            } else {
                document.getElementById("mainMenu" + (i + 1)).style.display = "none";
                m[i].src = "resource/images/icon3.gif";
            }
        }
    }
}

//2012年2月8日（菜单修改后）   end
