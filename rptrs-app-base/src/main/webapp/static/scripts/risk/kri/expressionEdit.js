var optData = {
    "sum": "+",
    "sub": "-",
    "mul": "*",
    "del": "/",
    //"f2" : "%",
    //"f1" : "=",
    "f3": "(",
    "f4": ")"
};
var numberData = {
    "one": "1",
    "two": "2",
    "three": "3",
    "four": "4",
    "five": "5",
    "six": "6",
    "seven": "7",
    "eight": "8",
    "nine": "9",
    "zero": "0",
    "point": "."
};

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
    if (type == "opt") {
        data = optData;
    } else if (type == "num") {
        data = numberData;
    }
    for (var key in data) {
        ul += '<li unselectable="on" onclick=formatTextCurrent("' + data[key] + '","' + data[key] + '");>' + data[key] + '</li>';
    }
    ul += '</ul>';
    $(ul).appendTo($("#" + parentID));
}

/**
 * 填充表达式
 * @param data
 */
function formatTextCurrent(value, name) {
    if (!value) return;
    var oldExp = $("#calcExpression").val();
    var oldExpDesc = $("#calcExpressionDesc").val();
    $("#calcExpression").val(oldExp + value);
    $("#calcExpressionDesc").val(oldExpDesc + name);
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
    if (type == "opt") {
        data = optData;
    } else if (type == "num") {
        data = numberData;
    }
    for (var key in data) {
        ul += '<li unselectable="on" onclick=formatTextMDL("' + data[key] + '","' + data[key] + '");>' + data[key] + '</li>';
    }
    ul += '</ul>';
    $(ul).appendTo($("#" + parentID));
}

/**
 * 标准法模块 填充表达式
 * @param data
 */
function formatTextMDL(value, name) {
    if (!value) return;
    var oldExp = $("#calcFormula").val();
    var oldExpDesc = $("#calcFormulaDesc").val();
    $("#calcFormula").val(oldExp + value);
    $("#calcFormulaDesc").val(oldExpDesc + name);
};