var optData = {
    'sum': '+',
    'sub': '-',
    'mul': '*',
    'del': '/',
    //'f2' : '%',
    //'f1' : '=',
    'f3': '(',
    'f4': ')'
};
var numberData = {
    'one': '1',
    'two': '2',
    'three': '3',
    'four': '4',
    'five': '5',
    'six': '6',
    'seven': '7',
    'eight': '8',
    'nine': '9',
    'zero': '0',
    'point': '.'
};

var aggData = {
    'SUM': 'SUM',
    'AVG': 'AVG',
    'COUNT': 'COUNT',
    'MAX': 'MAX',
    'MIN': 'MIN'
};

/**
 * 填充按钮
 * @param type 按钮类型
 * @param width 宽度
 * @param height 高度
 * @param parentID 父节点ID
 */
function createAggButton(type, width, height, parentID) {
    var ul = '<ul id="agg_button" style="width:' + width + 'px;height:' + height + 'px;">';
    var data = aggData;
    /*遍历数据，加入UL中*/
    for (var key in data) {
        /*默认选中NONE*/
        if (data[key] == 'SUM') {
            ul += '<li class = "checked" unselectable="on" value = "' + data[key] + '" >' + data[key] + '</li>';
        } else {
            ul += '<li class = "agg" unselectable="on"  value = "' + data[key] + '" >' + data[key] + '</li>';
        }
    }
    ul += '</ul>';
    $(ul).appendTo($('#' + parentID));
}


var start = 0, end = 0;

/**
 * 填充按钮
 * @param type 按钮类型
 * @param width 宽度
 * @param height 高度
 * @param parentID 父节点ID
 */
function createButton(type, width, height, parentID) {
    var ul = '<ul id="expression_button" style="width:' + width + 'px;height:' + height + 'px;">';
    var data = null;
    if (type == 'opt') {
        data = optData;
    } else if (type == 'num') {
        data = numberData;
    }
    for (var key in data) {
        ul += '<li class = "opt_number" unselectable="on" '
            + 'onclick=formatTextCurrent("' + data[key] + '","' + data[key] + '","' + data[key] + '");>' + data[key] + '</li>';
    }
    ul += '</ul>';
    $(ul).appendTo($('#' + parentID));
}

/**
 * 填充表达式
 * @param data
 */
function formatTextCurrent(value, name, disp, formatName) {
    if (!value) return;
    /*指标项的计算公式的设置*/
    var oldExp = $('#calcExpr').val();
    var oldExpDisp = $('#calcExprDisp').val();
    var oldExpDesc = $('#calcExprDesc').val();
    var oldFormatExpDesc = $('#calcExprFormatDisp').val();
    $('#calcExpr').val(oldExp + value);
    $('#calcExprDisp').val(oldExpDisp + disp);
    $('#calcExprDesc').val(oldExpDesc + name);
    $('#calcExprFormatDisp').val(oldFormatExpDesc + formatName);

    /*指标的计算公式的设置*/
    var oldExpr = $('#calcExpression').val();
    var oldExprDesc = $('#calcExpressionDesc').val();
    $('#calcExpression').val(oldExpr + value);
    $('#calcExpressionDesc').val(oldExprDesc + name);

    /* 表达式改变的时候保存按钮设置为不可编辑  */
    $('#expressionSave').linkbutton('disable');

};

//-------------------------标准法用

/**
 * 填充按钮
 * @param type 按钮类型
 * @param width 宽度
 * @param height 高度
 * @param parentID 父节点ID
 */
function createMDLButton(type, width, height, parentID) {
    var ul = '<ul id="expression_button" style="width:' + width + 'px;height:' + height + 'px;">';
    var data = null;
    if (type == 'opt') {
        data = optData;
    } else if (type == 'num') {
        data = numberData;
    }
    for (var key in data) {
        ul += '<li unselectable="on" onclick=formatTextMDL("' + data[key] + '","' + data[key] + '");>' + data[key] + '</li>';
    }
    ul += '</ul>';
    $(ul).appendTo($('#' + parentID));
}

/**
 * 标准法模块 填充表达式
 * @param data
 */
function formatTextMDL(value, name) {
    if (!value) return;
    var oldExp = $('#calcFormula').val();
    var oldExpDesc = $('#calcFormulaDesc').val();
    $('#calcFormula').val(oldExp + value);
    $('#calcFormulaDesc').val(oldExpDesc + name);
};