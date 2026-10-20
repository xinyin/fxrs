//用于保存右键选中的表格Id
var ids_1 = '';

//用于保存tab页的编号，默认为0
var tabNo = 0;

var widgetCode;

//用于存放正在编辑的表格ID
var editCellId = '';

//表格表名的开始部分
var DS_TABLENAME = 'DS_';

//右键添加一列时，表头的默认数据
var header = '{"title":"新增列","font-bold":true,"width":210,"background-color":"#FFFFFF","font-size":13,"text-align":"center"}';

//右键添加一列时， 表达式的默认数据	
var detail = '{"expr":"", "width" : 210,"font-size" : 13,"text-align":"center"}';

//用于存放数据提供程序 
var widgetProvider = [];

//用于判断tab能否关闭 
var canClose = false;

//数据组件的编码
var widgetCode = '${dashWidget.widgetCode}';
//数据组件的名称
var widgetName = '${dashWidget.widgetName}';
//版本号
var version;

//默认tab页的数据
var initTab = '{"title":"页面","provider":"","hyperlink":{"show":"false","title":"","text":"更多...","url":"#"},"table":{"header":{"cells":[{"title":"列一","font-bold":true,"background-color":"#FFFFFF","width":"210px","font-size":"13px","text-align":"center"},{"title":"列二","font-bold":true,"background-color":"#FFFFFF","width":"210px","font-size":"13px","text-align":"center"},{"title":"列三","font-bold":true,"background-color":"#FFFFFF","width":"210px","font-size":"13px","text-align":"center"},{"title":"列四","font-bold":true,"background-color":"#FFFFFF","width":"210px","font-size":"13px","text-align":"center"}]},"detail":{"cells":[{"expr":"","width":"210px","font-size":"13px","text-align":"center"},{"expr":"","width":"210px","font-size":"13px","text-align":"center"},{"expr":"","width":"210px","font-size":"13px","text-align":"center"},{"expr":"","width":"210px","font-size":"13px","text-align":"center"}]}}}';

var tabData;

//改变列宽度相关变量
var _mc = false,	//是否开始调整列宽度的标识 
    _mcsx = 0, 		//列的开始X坐标
    _mcex = 0, 		//列的结束X坐标
    _mcmx = 0, 		//列的偏移坐标
    _mcsrc = null, 	//调整的列对象
    _mcmw = 30, 	//列的最小宽度(px)
    _mcew = 4;		//列之间的间隔

function init() {
    //初始化tab页
    _initTab(tabData);

    //绑定关闭tab按钮事件
    closeTab();

    //初始化事件
    _initMenu();

    //初始化修改列宽事件
    _initEvent();

    //选中面板时触发事件
    onSelect();

}

//点击当前面板的第一个单元格
function clickCell(cellNo) {
    var designTabs = $('#dataWidgetTabs').jTab('getTabTitle');
    var tabId = $(designTabs).children().attr("name");
    $('#ds' + tabId + '_a_' + cellNo).click();
    $("#pg" + tabId).find('tr').slice(1, 10).css('display', 'table-row');
    var pgRows = $(".pgRow");
    $.each(pgRows, function (i, el) {
        var td = $(el).children()[1];
        $(td).children().css('width', '200px');
    })
}

function clickTitle(tabId) {
    var tr = $("#pg" + tabId).find('.pgGroupRow')[0];
    var insideHtml = $(tr).html();
    var insideText = $(insideHtml).text();
    var isPlus = insideText[0] === '-';
    var subText = insideText.substring(1);
    var currentText = isPlus ? '+' + subText : '-' + subText;
    var currentHtml = insideHtml.replace(insideText, currentText);
    $(tr).html(currentHtml);
}


//初始化tab页
function _initTab(data) {
    //获得tab数据
    var dataTab = data.tabs;
    if (dataTab) {
        //根据tab数据，循环初始化每个tab页面
        $.each(dataTab, function (i, tab) {
            tabNo = i;
            //初始化tab
            _generateTab(tab);
        })
    }
}

//初始化tab
function _generateTab(tab) {
    var tabTitle = tab.title;
    var tabId = 'ds' + tabNo;
    var addTab = {
        title: tabTitle,
        id: tabNo,
        icon: 'add',
        content: '<div id="' + tabId + '"></div>',
        colse: true
    }
    $("#dataWidgetTabs").jTab("addTab", addTab);
    //初始化tab标题等数据

    var _form = '<form id="formTab' + tabId + '" class="form-horizontal"></form>';
    $('#' + tabId).append(_form);

    var titleData = '<div class="form-group">';
    titleData += '<div class="col-xs-6">';
    titleData += '<label class="control-label col-xs-3">标题</label>';
    titleData += '<div class="col-xs-6">';
    titleData += '<input type="text" class="form-control tab-title" onblur="changeTabTitle(\'' + tabId + '\')" id="tabTitle' + tabNo + '" name="tabTitle" value="' + tabTitle + '">';
    titleData += '</div></div>';
    titleData += '<div class="col-xs-6">';
    titleData += '<label class="control-label col-xs-3">数据提供程序</label>';
    titleData += '<div class="col-xs-6">';
    titleData += '<select  onchange="dataProviderChange(this)" class="dataProviderCls form-control"  id="provider' + tabNo + '" value="' + tab.provider + '"></select>';
    titleData += '</div></div>';
    titleData += '</div>';
    $('#formTab' + tabId).append(titleData);


    var dgData = '<div class="form-group">';
    dgData += '<div class="charData" id ="charData' + tabNo + '" style="height: 65px;width:845px; margin: 3 2 0 3; overflow-x: auto; overflow-y: hidden; position: relative;right : -100px;" >' + ' </div>';
    dgData += '</div>';
    $('#formTab' + tabId).append(dgData);

    //根据JSON格式的参数渲染页面，当参数为空的时候，就使用默认的页面
    _initUL('charData' + tabNo, tab.table, tabNo);

    var setData = '<div style="height: 27px;margin: 0 2 0 3; position:  relative;top: -15px;right: 155px;"> <div style="height: 27px; margin: 4 10 0 3; width: 60px; float:right; text-align:right;"><a id="btn_hyperlink_' + tabNo + '" href="#" class="hyperlink-set" onclick="bindHyperlink(' + tabId + ')" >链接...</a> </div></div>';
    $('#formTab' + tabId).append(setData);

    //属性编辑器
    var pgData = '<div class="form-group">';
    pgData += '<div id="pg' + tabNo + '" style="float:left;height:250px;overflow-y:auto;position: relative;left:100px;top:-23px;width:850px"></div>';
    pgData += '</div>';
    $('#formTab' + tabId).append(pgData);

    //初始化数据提供程序等数据与事件 
    _initTotalData(tabId, tab);
}

//根据JSON格式的参数渲染页面，当参数为空的时候，就使用默认的页面 
function _initUL(divId, data, tabIndex) {
    /* 获得当前tab选项卡的ID */
    var tabId = 'ds' + tabIndex;

    // 获得表格的宽度
    var tableWidth = _getTableWidth(data);

    // 生成表格区域的ID
    var tableId = 'table_' + tabIndex;

    //当数据不存在的时候，就设置为默认的三列数据
    var tableT = '<div style="list-style:none; width: ' + tableWidth + 'px;" id = ' + tableId + ' ></div>';
    $('#' + divId).append(tableT);

    //判空操作
    if (data) {
        //因为存在数据，就一定存在表格数据，所以直接取出cell数据
        var header = data.header.cells;
        var detail = data.detail.cells;

        //因为header和detail中的数据个数一定是一致的，所以可以直接用下面的循环取出数据
        for (var i = 0; i < header.length; i++) {
            var headerStyle = allPrpos(header[i]);
            var detailStyle = allPrpos(detail[i]);

            var cellId = 'cell_' + tabIndex + '_' + i;

            //判断是否存在设定的表格格式
            var expr = detail[i]['expr'];
            if (expr) {
                expr = expr.replace(/\</g, '&lt;');
                expr = expr.replace(/\>/g, '&gt;');
            } else {
                expr = '&nbsp;';
            }

            //获得每个表格的宽度，并设计
            var width = header[i]['width'];
            if (!width) {
                width = 200;
            } else {
                width = parseInt(width);
            }

            var cellWidth = width + 5;

            var column = '<div id = ' + cellId + ' style="width: ' + cellWidth + '" class="column"><ul style=" float:left; text-align:center; padding : 0px;">'
                + '<li id="' + tabId + '_a_' + i + '" class="header cellBorder" style=" width:' + width + 'px; ' + headerStyle + '">' + header[i]['title'] + '</li> '
                + '<li id="' + tabId + '_b_' + i + '" class="detail cellBorder" style=" width:' + width + 'px; ' + detailStyle + '">' + expr + '</li></ul></div>';
            $('#' + tableId).append(column);

            //增加边界和列边框
            _addResizeBorder4Column($('#' + cellId));
        }

    }
}

//根据表格数据，获得表格长度 
function _getTableWidth(data) {
    var tableWidth = 0;
    //判空操作
    if (data) {
        var header = data.header;
        //获得表头， 因为头和数据的表格宽带都是一样的，所以只要获得头的宽度，就可以得到表格的宽度
        if (header) {
            var headerCells = header.cells;
            $.each(headerCells, function (i, cell) {
                //获得每个表格的宽带，并设计
                var width = cell['width'];
                //当没有设定宽度的时候，设定宽度的默认值为200
                if (!width) {
                    width = 200;
                } else {
                    width = parseInt(width);
                }

                //为防止在火狐中出现滚动条，所以把表格整体宽度加5
                tableWidth += width + 7;

            });
        }
    }

    return tableWidth;
}


//根据数据属性生成字符串
function allPrpos(obj) {
    // 用来保存所有的属性名称和值
    var props = '';
    // 开始遍历
    for (var p in obj) { // 方法
        if (typeof (obj [p]) == ' function ') {
            obj [p]();
        } else {
            //首先判断是不是title 和 expr
            if (p != 'title' && p != 'expr') {

                if (p == 'underline' && (obj[p] == true || obj[p] == 'true')) {
                    props += 'text-decoration: underline; ';
                } else if (p == 'italic' && (obj[p] == true || obj[p] == 'true')) {
                    props += 'font-style:italic; ';
                } else if (p == 'strikeline' && (obj[p] == true || obj[p] == 'true')) {
                    props += 'text-decoration: line-through; ';
                } else if (p == 'font-bold' && (obj[p] == true || obj[p] == 'true')) {
                    props += 'font-weight:bold; ';
                } else if (p == 'color') {
                    // p 为属性名称，obj[p]为对应属性的值
                    props += p + ':  ' + obj [p] + '; ';
                } else if (p == 'background-color') {
                    // p 为属性名称，obj[p]为对应属性的值
                    props += p + ':  ' + obj [p] + '; ';
                } else if (p == 'text-align') {
                    // p 为属性名称，obj[p]为对应属性的值
                    props += p + ':  ' + obj [p] + '; ';
                } else if (p == 'vertical-align') {
                    // p 为属性名称，obj[p]为对应属性的值
                    props += p + ':  ' + obj [p] + '; ';
                } else if (p == 'display') {
                    // p 为属性名称，obj[p]为对应属性的值
                    props += p + ':  ' + obj [p] + '; ';
                } else {
                    // p 为属性名称，obj[p]为对应属性的值
                    props += p + ':  ' + obj [p] + '; ';
                }

            }

        }
    } // 最后显示所有的属性
    return props;
}

//为 Column 增加可改变大小的边框
function _addResizeBorder4Column(column) {
    var _edge = $('<div onmousedown="set(this)"  class="column-edge">EDGE</div>').appendTo(column);
}

//鼠标按下的时候改变列宽
function set(el) {
    event.stopPropagation();
    _mc = true;
    _mcsx = event.pageX;
    _mcsrc = $(el).parent();
}


function _initTotalData(tabId, tab) {
    //绑定数据提供程序
    initDataProviderSelect(tabId, tab);
}

//绑定数据提供程序 
function initDataProviderSelect(tabId, tab) {

    var sel = $('#' + tabId + ' .dataProviderCls');
    $.each(widgetProvider, function (i, obj) {
        if (obj.value == tab.provider) {
            sel.append('<option selected="selected" value="' + obj.value + '">' + obj.name + '</option>');
        } else {
            sel.append('<option  value="' + obj.value + '">' + obj.name + '</option>');
        }

    });

    var designTabs = $('#dataWidgetTabs').jTab('getTab');
    var tabIndex = designTabs.index;
    var tabs = tabData.tabs;
    tabs[tabIndex].provider = sel.val();
}


//绑定改变tab标题事件
function changeTabTitle(tabId) {
    //获得新值
    var value = $('#' + tabId + ' .tab-title').val();
    $('#dataWidgetTabs').jTab('updateTab', {'title': value});

    var designTabs = $('#dataWidgetTabs').jTab('getTab');
    var tabIndex = designTabs.index;

    //改变要进行保存的tab的标题
    var tabs = tabData.tabs;
    tabs[tabIndex].title = value;
    closeTab();
}

/* 绑定设置按钮的点击事件 */
function bindHyperlink(tabId) {

    var tab = $("#dataWidgetTabs").jTab('getTab');
    var index = tab.index;
    var tabs = tabData.tabs;
    var hyperlink = tabs[index].hyperlink;

    $.messager.modal({
        winId: 'wDashWidgetHyperlink',
        width: 850,
        height: 250,
        title: '设置链接',
        url: base + '/sys/dashWidget/dashWidgetHyperlink',
        args: {hyperlink: hyperlink}
    });

};


function _initMenu() {
    //表头绑定双击事件
    dblclicHeader();

    //数据列绑定双击事件，双击编辑公式
    dblclickDetail();

    //绑定表格右击事件
    tableContextmenu();

    //绑定点击表头进行设置格式的事件
    cellClick();
}

/* 表头绑定双击事件 */
function dblclicHeader() {
    /* 表头绑定双击事件 */
    $('.header').dblclick(function () {
        /* 获得编码的表格ID, 再根据ID修改对应的数据  */
        var tdId = this.id;
        var td = $(this);

        var width = this.width;

        // 根据表格文本创建文本框 并加入表头中
        var text = td.text();
        var txt = $('<input type="text" style="width: 100%; height: 100%" >').val($.trim(text));

        txt.blur(function () {
            // 失去焦点，保存值。
            var newText = $(this).val();

            // 移除文本框,显示新值
            $(this).remove();
            td.text(newText);

            var strs = new Array(); //定义一数组
            strs = tdId.split('_'); //字符分割
            var designTabs = $('#dataWidgetTabs').jTab('getTab');
            var tabIndex = designTabs.index;
            var tabs = tabData.tabs;

            /* 获得表格数据 */
            var tableData = tabs[tabIndex].table;
            /* 获得列号 */
            var colNo = parseInt(strs[2]);
            /* 获得JSON中的数据 */
            var headerArray = tableData.header.cells;
            headerArray[colNo].title = newText;

            $('#' + tdId).click();
        });

        td.text('');
        td.append(txt);

        //将输入框中的文本高亮选中
        //将jquery对象转化为DOM对象
        var inputDom = txt.get(0);
        inputDom.select();

    });
}


//数据列绑定双击事件，双击编辑公式
function dblclickDetail() {
    $('.detail').dblclick(function () {
        //获得当前点击的对象
        var td = $(this);
        // 获得文档内容
        var text = td.text();
        //获得id
        var tdId = td.attr('id');
        //根据表格ID获得当前的tab页的编号
        var strs = new Array(); //定义一数组
        strs = tdId.split('_'); //字符分割
        var tabNo = parseInt(strs[0].substring(2));
        //获得当前tab页选择的数据提供程序
        var provierId = $("#provider" + tabNo).val();
        //判断是否已经选择了数据提供程序，没有选择数据提供程序，就不能编辑数据
        if (!provierId) {
            $.messager.toastr('系统提示', '请选择数据绑定程序。', 'info');
            return;
        }

        //组成对象，提交到公式编辑页面
        var params = {
            provierId: provierId,
            expr: text,
            tdId: tdId,
            options: ''
        };

        $.messager.modal({
            winId: 'winSeriesExprEdit',
            width: 850,
            height: 480,
            title: '表达式编辑',
            url: base + '/sys/dashWidget/expr',
            args: {callback: result, params: params},
            onClose: function () {
            }
        });
    })
}

/* 公式编辑回调函数 */
function result(exprObject) {
    /* 获得编码的表格ID, 再根据ID修改对应的数据  */
    var tdId = exprObject.tdId;

    var strs = new Array(); //定义一数组

    strs = tdId.split('_'); //字符分割

    var designTabs = $('#dataWidgetTabs').jTab('getTab');

    var tabIndex = designTabs.index;

    var tabs = tabData.tabs;

    /* 获得表格数据 */
    var tableData = tabs[tabIndex].table;

    /* 获得列号 */
    var colNo = parseInt(strs[2]);

    /* 获得JSON中的数据 */
    var detailArray = tableData.detail.cells;

    var expr = exprObject.expr;

    detailArray[colNo].expr = expr;

    if (expr) {
        expr = expr.replace(/\</g, '&lt;');
        expr = expr.replace(/\>/g, '&gt;');
    }
    document.getElementById(tdId).innerHTML = expr;
}

//禁止浏览器右键点击
document.oncontextmenu = function () {
    return false;
}


//绑定表格右击事件
function tableContextmenu() {
    $('li[class="header cellBorder"]').bind('contextmenu', function (e) {
        //右键点击表格的列的id
        ids_1 = this.id;
        var pageX = e.pageX;
        var pageY = e.pageY;
        var documentClass = $('.document-class');
        documentClass.css({
            'left': pageX,
            'top': pageY
        }).show();
    });
    //点击任意位置菜单消失
    var doc = $(document);
    doc.on('click', function () {
        $('.document-class').hide();
    })
}


//在该行之前插入列
function addPriorColumns() {
    //判断是否点击了某一列
    if (ids_1) {
        var strs = new Array();
        strs = ids_1.split("_");

        var designTabs = $("#dataWidgetTabs").jTab("getTab");
        var tabIndex = designTabs.index;

        var tabs = tabData.tabs;
        //获得表格数据
        var tableData = tabs[tabIndex].table;

        //获得列号
        var colNo = parseInt(strs[2]);

        //获得表头数据
        var headerArray = tableData.header.cells;
        //插入表头数据
        headerArray.splice(colNo, 0, $.parseJSON(header));

        //单元格数据
        var detailArray = tableData.detail.cells;
        //插入单元格数据
        detailArray.splice(colNo, 0, $.parseJSON(detail));

        var parentDiv = $('#' + ids_1).parent().parent().parent().parent();

        parentDiv.empty();

        //根据JSON格式的参数渲染页面，当参数为空的时候，就使用默认的页面
        _initUL(parentDiv.attr('id'), tableData, tabIndex)

        //初始化菜单
        _initMenu();
    }
}

//在改行之后添加列
function addAfterColumns() {
    if (ids_1) {
        var strs = new Array(); //定义一数组
        strs = ids_1.split("_"); //字符分割

        var designTabs = $('#dataWidgetTabs').jTab('getTab');
        var tabIndex = designTabs.index;

        var tabs = tabData.tabs;

        /* 获得表格数据 */
        var tableData = tabs[tabIndex].table;

        /* 获得列号 */
        var colNo = parseInt(strs[2]);

        var cellNo = colNo + 1;

        var headerArray = tableData.header.cells;
        headerArray.splice(cellNo, 0, $.parseJSON(header));

        var detailArray = tableData.detail.cells;
        detailArray.splice(cellNo, 0, $.parseJSON(detail));

        var parentDiv = $('#' + ids_1).parent().parent().parent().parent();
        parentDiv.empty();

        _initUL(parentDiv.attr('id'), tableData, tabIndex)

        //初始化菜单
        _initMenu();
    }
}

//删除列
function delColumns() {
    if (ids_1) {
        var strs = new Array(); //定义一数组
        strs = ids_1.split("_"); //字符分割

        var designTabs = $('#dataWidgetTabs').jTab('getTab');
        var tabIndex = designTabs.index;

        var tabs = tabData.tabs;

        //获得表格数据
        var tableData = tabs[tabIndex].table;

        //获得列号
        var colNo = parseInt(strs[2]);

        var headerArray = tableData.header.cells;

        if (headerArray.length > 1) {
            var delTdWidth = headerArray[colNo]['width'];
            if (!delTdWidth) {
                delTdWidth = 200;
            }
            //从数组中删除colNo的单元格
            headerArray.splice(colNo, 1);

            var detailArray = tableData.detail.cells;

            //从数组中删除colNo的单元格的表达式
            detailArray.splice(colNo, 1);

            var parentDiv = $('#' + ids_1).parent().parent().parent().parent();
            parentDiv.empty();

            _initUL(parentDiv.attr('id'), tableData, tabIndex)

            //初始化菜单
            _initMenu();

        } else {
            $.messager.toastr('系统提示', '至少需要一列数据。', 'info');
        }

    }

}

//改变列宽度事件
function _initEvent() {
    /*增加改变组件大小和列大小的相关事件*/
    $(document).mouseup(function (e) {
        /*在body上放开鼠标时，如果是调整列宽度状态，则停止调整*/
        if (_mc) {
            _mc = false;
            _mcsrc = null;
        }

    }).mousemove(function (e) {

        /*
         * 调整列的宽度，按以下步骤：
         * 1. 记录调整的结束坐标
         * 2. 开始坐标 - 结束坐标 = 偏移量
         * 3. 将开始坐标设置为结束坐标
         * 4. 如果正在调整过程中，并且当前列调整后的宽度大于最小宽度，则调整列的宽度
         * 5. 调整后计算所有列的宽度
         */
        _mcex = e.pageX;
        _mcmx = _mcsx - _mcex;
        _mcsx = _mcex;
        if (_mc && (_mcsrc.width() - _mcmx >= _mcmw)) {
            _mcsrc.width(_mcsrc.width() - _mcmx);

            var _parent = _mcsrc.parent();
            _parent.width(_parent.width() - _mcmx);

            var _ul = _mcsrc.children("ul");
            _ul.width(_ul.width() - _mcmx);

            var _li = _ul.children("li");
            var tdWidth = _li.width() - _mcmx;
            _li.width(tdWidth);

            /* 根据表格ID修改数据 */
            var id = _mcsrc.attr('id');
            //调整数据列的宽度
            _resizeDataWidth(id, tdWidth);
        }

    });
}

//调整数据列的宽度
function _resizeDataWidth(divId, width) {
    var strs = new Array(); //定义一数组
    strs = divId.split('_'); //字符分割

    /* 获得当前tab页的页码 */
    var designTabs = $('#dataWidgetTabs').jTab('getTab');
    var tabIndex = designTabs.index;

    var tabs = tabData.tabs;
    /* 获得表格数据 */
    var tableData = tabs[tabIndex].table;

    /* 获得列号 */
    var colNo = parseInt(strs[2]);

    var hArray = tableData.header.cells;

    var dArray = tableData.detail.cells;

    /* 当新设定的值不等原值的时候，进行重新设定 */
    if (hArray[colNo]['width'] != width) {
        hArray[colNo]['width'] = width;
        dArray[colNo]['width'] = width;
    }
}

/* 绑定点击表格进行设置格式的事件 */
function cellClick() {
    $('.charData li').click(function () {
        var td = $(this);
        //清空原来的选中样式
        $('.cellSelected').addClass('cellBorder');
        $('.cellSelected').removeClass('cellSelected');

        //给当前点击框添加选中样式
        td.addClass('cellSelected');
        td.removeClass('cellBorder');
        //获得选择的td的内容
        var text = td.text();
        //定义一数组
        var strs = new Array();
        // 获得单元格ID
        editCellId = this.id;
        //字符分割
        strs = editCellId.split('_');
        var designTabs = $('#dataWidgetTabs').jTab('getTab');
        var tabIndex = designTabs.index;
        var tabs = tabData.tabs;
        //获得表格数据
        var tableData = tabs[tabIndex].table;
        //获得列号
        var colNo = parseInt(strs[2]);

        //获得JSON中的数据
        var array;
        if ('a' == strs[1]) {
            array = tableData.header.cells;
        } else {
            array = tableData.detail.cells;
        }
        //回显使用
        var fontBold = array[colNo]['font-bold'];
        fontBold = eval(fontBold);
        if (!fontBold) {
            fontBold = "N";
        } else {
            fontBold = "Y";
        }

        var title = array[colNo]['title'];
        if (!title) {
            title = '';
        }
        var fontSize = array[colNo]['font-size'];
        if (!fontSize) {
            fontSize = 0;
        } else {
            fontSize = fontSize.replace('px', '');
        }

        var color = array[colNo]['color'];
        if (!color) {
            color = '';
        }

        var backgroundColor = array[colNo]['background-color'];
        if (!backgroundColor) {
            backgroundColor = '';
        }

        var bold = array[colNo]['bold'];
        if (!bold) {
            bold = '';
        }

        var italic = array[colNo]['italic'];
        italic = eval(italic);
        if (!italic) {
            italic = "N";
        } else {
            italic = "Y";
        }

        var underline = array[colNo]['underline'];
        underline = eval(underline);
        if (!underline) {
            underline = "N";
        } else {
            underline = "Y";
        }

        var strikeline = array[colNo]['strikeline'];
        strikeline = eval(strikeline);
        if (!strikeline) {
            strikeline = "N";
        } else {
            strikeline = "Y";
        }

        var display = array[colNo]['display'];
        if (!display) {
            display = "block";
        }

        var verticalAlign = array[colNo]['vertical-align'];
        if (!verticalAlign) {
            verticalAlign = 'top';
        }

        var textAlign = array[colNo]['text-align'];
        console.log(array[colNo]);
        if (!textAlign) {
            textAlign = 'center';
        }


        var paddingTop = array[colNo]['padding-top'];
        if (!paddingTop) {
            paddingTop = 0;
        }

        var paddingBottom = array[colNo]['padding-bottom'];
        if (!paddingBottom) {
            paddingBottom = 0;
        }

        var paddingLeft = array[colNo]['padding-left'];
        if (!paddingLeft) {
            paddingLeft = 0;
        }

        var paddingRight = array[colNo]['padding-right'];
        if (!paddingRight) {
            paddingRight = 0;
        }

        var marginTop = array[colNo]['margin-top'];
        if (!marginTop) {
            marginTop = 0;
        }

        var marginBottom = array[colNo]['margin-bottom'];
        if (!marginBottom) {
            marginBottom = 0;
        }

        var marginLeft = array[colNo]['margin-left'];
        if (!marginLeft) {
            marginLeft = 0;
        }

        var marginRight = array[colNo]['margin-right'];
        if (!marginRight) {
            marginRight = 0;
        }

        //定义需要编辑的属性
        var setObj = {
            title: title,
            fontBold: fontBold,
            fontSize: fontSize,
            color: color,
            backgroundColor: backgroundColor,
            italic: italic,
            underline: underline,
            strikeline: strikeline,
            display: display,
            verticalAlign: verticalAlign,
            textAlign: textAlign,
            paddingTop: paddingTop,
            paddingBottom: paddingBottom,
            paddingLeft: paddingLeft,
            paddingRight: paddingRight,
            marginTop: marginTop,
            marginBottom: marginBottom,
            marginLeft: marginLeft,
            marginRight: marginRight
        };

        var cpOptions = {preferredFormat: 'hex', showInput: true, showInitial: true};

        //定义属性的具体信息
        var metaObj = {
            title: {group: '通用', name: '标题'},
            fontBold: {group: '通用', name: '粗体', type: 'boolean'},
            fontSize: {group: '通用', name: '字体大小', type: 'number', options: {min: 0, max: 500, step: 1}},
            color: {group: '通用', name: '字体颜色', type: 'color', options: cpOptions},
            backgroundColor: {group: '通用', name: '背景颜色', type: 'color', options: cpOptions},
            italic: {group: '通用', name: '斜体', type: 'boolean'},
            underline: {group: '通用', name: '下划线', type: 'boolean'},
            strikeline: {group: '通用', name: '走向线', type: 'boolean'},
            display: {group: '通用', name: '框类型', type: 'options', options: ['block', 'none']},
            verticalAlign: {group: '对齐方式', name: '垂直对齐', type: 'options', options: ['top', 'middle', 'bottom']},
            textAlign: {group: '对齐方式', name: '文本对齐方式', type: 'options', options: ['left', 'center', 'right']},
            paddingTop: {group: '内边距', name: '上内间距', type: 'number', options: {min: 0, max: 500, step: 5}},
            paddingBottom: {group: '内边距', name: '下内间距', type: 'number', options: {min: 0, max: 500, step: 5}},
            paddingLeft: {group: '内边距', name: '左内间距', type: 'number', options: {min: 0, max: 500, step: 5}},
            paddingRight: {group: '内边距', name: '右内间距', type: 'number', options: {min: 0, max: 500, step: 5}},
            marginTop: {group: '外边距', name: '上外边距', type: 'number', options: {min: 0, max: 500, step: 5}},
            marginBottom: {group: '外边距', name: '下外边距', type: 'number', options: {min: 0, max: 500, step: 5}},
            marginLeft: {group: '外边距', name: '左外边距', type: 'number', options: {min: 0, max: 500, step: 5}},
            marginRight: {group: '外边距', name: '右外边距', type: 'number', options: {min: 0, max: 500, step: 5}}
        };

        //当不是表头的时候不需要title属性
        if ('a' != strs[1]) {
            delete setObj.title;
            delete metaObj.title;
        }

        tabIndex = parseInt(strs[0].substring(2));
        pgId = '#pg' + tabIndex;
        $(pgId).jqPropertyGrid(setObj, {
            meta: metaObj,
            isCollapsible: true,
            callback: function (grid, name, value) {
                if (name == "fontBold") {
                    changeStyle("font-bold", value);
                } else if (name == "italic") {
                    changeStyle("italic", value);
                } else if (name == "underline") {
                    changeStyle("underline", value);
                } else if (name == "strikeline") {
                    changeStyle("strikeline", value);
                } else if (name == "display") {
                    changeStyle("display", value);
                } else if (name == "verticalAlign") {
                    changeStyle("vertical-align", value);
                } else if (name == "textAlign") {
                    changeStyle("text-align", value);
                } else if (name == "color") {
                    changeStyle("color", value);
                } else if (name == "backgroundColor") {
                    changeStyle("background-color", value);
                } else if (name == "fontSize") {
                    changeStyle("font-size", value);
                } else if (name == "paddingTop") {
                    changeStyle("padding-top", value);
                } else if (name == "paddingBottom") {
                    changeStyle("padding-bottom", value);
                } else if (name == "paddingLeft") {
                    changeStyle("padding-left", value);
                } else if (name == "paddingRight") {
                    changeStyle("padding-right", value);
                } else if (name == "marginTop") {
                    changeStyle("margin-top", value);
                } else if (name == "marginBottom") {
                    changeStyle("margin-bottom", value);
                } else if (name == "marginLeft") {
                    changeStyle("margin-left", value);
                } else if (name == "marginRight") {
                    changeStyle("margin-right", value);
                } else {
                    var strs = new Array(); //定义一数组
                    strs = editCellId.split('_'); //字符分割
                    var designTabs = $('#dataWidgetTabs').jTab('getTab');
                    var tabIndex = designTabs.index;
                    var tabs = tabData.tabs;

                    /* 获得表格数据 */
                    var tableData = tabs[tabIndex].table;
                    /* 获得列号 */
                    var colNo = parseInt(strs[2]);

                    /* 获得JSON中的数据 */
                    var array;

                    /* 判断当前编辑的是否为表头 */
                    if ('a' == strs[1]) {
                        array = tableData.header.cells;
                    } else {
                        array = tableData.detail.cells;
                    }

                    /* 当新设定的值不等原值的时候，进行重新设定 */
                    if (array[colNo]['title'] != value) {
                        array[colNo]['title'] = value;
                    }

                    $('#' + editCellId).text('');
                    $('#' + editCellId).append(value);

                }

            }
        });

        //展开属性编辑器的第一个面板
        $("#pg" + tabIndex).find('tr').slice(1, 10).css('display', 'table-row');
        var pgRows = $(".pgRow");
        $.each(pgRows, function (i, el) {
            var td = $(el).children()[1];
            $(td).children().css('width', '200px');
        })

        //改变属性编辑器面板标题
        clickTitle(tabIndex);

    });
}

//将属性编辑器编辑过后的新值赋值给表格
function changeStyle(style, newValue) {
    //定义一数组
    var strs = new Array();
    //字符分割
    strs = editCellId.split('_');

    var designTabs = $('#dataWidgetTabs').jTab('getTab');
    var tabIndex = designTabs.index;

    var tabs = tabData.tabs;

    //获得表格数据
    var tableData = tabs[tabIndex].table;
    //获得列号
    var colNo = parseInt(strs[2]);

    /* 获得JSON中的数据 */
    var array;

    /* 判断当前编辑的是否为表头 */
    if ('a' == strs[1]) {
        array = tableData.header.cells;
    } else {
        array = tableData.detail.cells;
    }

    /* 当新设定的值不等原值的时候，进行重新设定 */
    if (array[colNo][style] != newValue) {
        if (newValue == 'Y') {
            newValue = true;
        }
        if (newValue == 'N') {
            newValue = false;
        }
        if (style == "font-size") {
            array[colNo][style] = newValue + "px";
        } else {
            array[colNo][style] = newValue;
        }

    }

    var style = allPrpos(array[colNo]);

    /* 判断是否存在设定的表格格式 */
    if (style && style != '') {
        $('#' + editCellId).attr('style', style);
    } else {
        $('#' + editCellId).removeAttr('style');
    }

}

//新增一个tab页
function btnAddTab() {
    /* tab页计数器加1 */
    tabNo += 1;
    /* 把页面数据重新转化一遍，防止程序认为是同一个对象 */
    tabJson = $.parseJSON(initTab);
    /* 设置默认标题 */
    tabJson.title = 'tab' + tabNo;
    tabData.tabs.push(tabJson);
    _generateTab(tabJson);
    /*初始化表格菜单*/
    _initMenu();
    //选择面板的时候触发事件
    onSelect();
    //默认选中新增tab页表格的第一个单元格
    clickCell(0);
    $("#pg" + tabNo).find('tr').slice(1, 10).css('display', 'table-row');
    //绑定关闭tab的事件
    closeTab();
}

//保存数据
function saveDesign() {
    /* 验证是否每个页面都设置了数据绑定程序 */
    var isBind = false;
    var tabs = tabData.tabs;
    $.each(tabs, function (i, tab) {
        var provider = tab.provider;
        if (!provider || provider == '') {
            isBind = true;
            return false;
        }
    });

    if (isBind) {
        $.messager.toastr('系统提示', '存在页面未选择数据绑定程序，请选择。', 'info');
        return;
    }
    $.messager.confirm('系统提示', '确定保存这些数据吗？', function (result) {
        if (result) {
            var formData = {widgetCode: widgetCode, config: JSON.stringify(tabData), version: version};
            $.mask.post(base + "/sys/dashWidgetConfig/save", formData, function (data) {
                if (data.success) {
                    $.messager.alert('系统提示', data.msg, function (result) {
                        if (result) {
                            location.href = base + '/sys/dashWidget/list';
                        }
                    });
                } else {
                    $.messager.toastr('系统提示', data.msg, 'error');
                }
            });
        }
    });
}

//关闭tab触发的事件
function closeTab() {
    $('.tab-close').unbind("click");
    $('.tab-close').on('click', function (ev) {
        /* 获取当前存在多少个tab页的数据 */
        var tabs = tabData.tabs;
        var a = $(this).parent();
        var tabIndex = $(a).attr("name");
        /* 当只存在一个tab页的时候，不允许关闭该tab页 */
        if (tabs.length > 1) {
            $.messager.confirm('系统提示', '你确认想要关闭当前tab,并且删除该tab页数据吗？', function (result) {
                if (result) {
                    $('#dataWidgetTabs').jTab('colseTab', tabIndex);
                    tabs.splice(tabIndex, 1);
                    //关闭之后，默认点击当前tab页下table的第一个单元格
                    clickCell(0);
                }

            })
        } else {
            $.messager.toastr('系统提示', '至少需要一个tab页显示数据。', 'info');
        }
    })
}

//用户选择一个面板时默认点击第一个单元格
function onSelect() {
    $('.tab-list').on('click', function (e) {
        clickCell(0);
    })
}

//数据提供程序值发生改变时触发事件
function dataProviderChange(el) {
    var newValue = $(el).val();
    if (newValue || newValue != '') {
        var designTabs = $('#dataWidgetTabs').jTab('getTab');
        var tabIndex = designTabs.index;
        var tabs = tabData.tabs;
        tabs[tabIndex].provider = newValue;
    }
}

//返回
function back() {
    window.location = base + '/sys/dashWidget/list';
}

