var optData = {
    "sum": "+", "sub": "-", "mul": "*", "del": "/", "f2": "%", "f1": "=", "f3": "(", "f4": ")"
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

function createButton(type, width, height, parentID) {
    var ul = '<ul id="expression_button" style="width:' + width + 'px;height:' + height + 'px;">';
    var data = null;
    if (type == "opt") {
        data = optData;
    } else if (type == "num") {
        data = numberData;
    }
    for (var key in data) {
        ul += '<li unselectable="on" onclick=formatTextCurrent("' + data[key] + '","' + type + '");>' + data[key] + '</li>';
    }
    ul += '</ul>';
    $(ul).appendTo($("#" + parentID));
}

var ifmRich, ifmRichDoc;
var textareaObj;

function initRich(richWidth, richHeight, textareaID, flag) {
    textareaObj = document.getElementById(textareaID);
    ifmRich = document.getElementById("rich" + textareaID);
    if (!ifmRich) {
        var iframe = '<table cellpadding="0" cellspacing="0" border="0" style="width:' + richWidth + '; height:' + richHeight + ';border:0px #c5daed solid;border-right:0px;border-bottom:0px;"><tr><td valign="top">'
            + '<iframe frameborder="0" id="rich' + textareaID + '" framespacing="0" marginwidth="2" marginheight="2" style="overflow-x: hidden; overflow-y: auto;"></iframe>'
            + '</td></tr></table>';
        // 插入
        textareaObj.insertAdjacentHTML("afterEnd", iframe);
        ifmRich = document.getElementById("rich" + textareaID);
    }
    ifmRich.style.height = richHeight;
    ifmRich.style.width = richWidth;
    ifmRichDoc = ifmRich.contentWindow.document;
    ifmRichDoc.open();
    var textareaValue = updateinitRichFromTextarea(textareaObj.value, flag);
    ifmRichDoc.write(textareaValue);
    ifmRichDoc.close();
    ifmRichDoc.body.contentEditable = false;
    ifmRichDoc.designMode = "on";

    var browserName = navigator.appName;
    if (browserName == "Microsoft Internet Explorer") {
        ifmRichDoc.onkeyup = function () {
            updateTextArea();
        };
    } else {
        // document.all['rich' + n].contentWindow.document;
        ifmRichDoc.addEventListener('keyup', function OnKeyUp() {
            updateTextArea();
        }, true);
    }
    textareaObj.style.display = 'none';
}

function updateinitRichFromTextarea(value, flag) {
    var array = new Array();
    for (; value.indexOf("[") != -1;) {
        var startIndex = value.lastIndexOf("[");
        var start = value.substring(0, startIndex);
        var end = value.substring(startIndex + 1);
        var endIndex = end.indexOf("]");
        var id = end.substring(0, endIndex);
        end = end.substring(endIndex + 1);
        var text = id;
        if ('getTextById' in window) {
            text = getTextById(text);
        }
        if (flag && flag == "1") {
            array.push({id: "%5B" + id + "%5D", text: text});
        } else {
            if (text.indexOf("<input") == -1) {
                text = getInputTag("%5B" + id + "%5D", text);
            }
        }
        value = start + "%5B" + id + "%5D" + end;
    }
    for (; value.indexOf("{") != -1;) {
        var startIndex = value.indexOf("{");
        var start = value.substring(0, startIndex);
        var endIndex = value.indexOf("}");
        var end = value.substring(endIndex + 1);
        var id = value.substring(startIndex + 1, endIndex);
        var text = id;
        text = text.replace("DIMDATE_", "");
        text = text.replace(/DIMENSION_\d{2}_/g, "");
        text = getDimTextById(text, array);
        var aIdx = text.indexOf(" AS ");
        if (aIdx != -1) {
            text = text.substring(0, aIdx);
        }
        if (text.indexOf("<input") == -1) {
            text = getInputTag("%7B" + id + "%7D", text);
        }
        value = start + text + end;
    }
    value = value.replace(/%5B/g, "[");
    value = value.replace(/%5D/g, "]");
    value = value.replace(/%7B/g, "{");
    value = value.replace(/%7D/g, "}");
    return value;
}

function getDimTextById(id, array) {
    for (var i in array) {
        var a = array[i];
        id = id.replace(a.id, a.text);
    }
    return id;
}

function getInputTag(id, text) {
    return '<input type="button" readonly="readonly" disabled="disabled"  name="' + id + '" value="' + text
        + '" style="border:1px #ccc solid;background:#fff;height:20px;padding:2px;margin:2px;"/>';
}

function formatTextEnd(id, type, selected) {
    ifmRich.contentWindow.focus();
    var richHtml = ifmRichDoc.body.innerHTML;
    var rng = ifmRichDoc.selection.createRange();
    rng.moveStart("character", richHtml.length);
    rng.select();
    formatTextCurrent(id, type, selected);
}

function formatTextCurrent(id, type, text, selected) {
    ifmRich.contentWindow.focus();
    if (id == "FontName") {
        ifmRichDoc.execCommand("FontName", false, selected);
    } else if (type == "opt" || type == "num") {
        insertHTML(id);
    } else if (type == "exp") {
        insertHTML(getInputTag(id, text));
        //ifmRichDoc.execCommand(id, false, null);
    }
    updateTextArea();
}

function isExistWhere() {
    var con = ifmRichDoc.body.innerHTML;
    if (con.indexOf("WHERE") != -1)
        return true;
    return false;
}

function formatTextGroup(id, type, selected) {
    ifmRich.contentWindow.focus();
    var richHtml = ifmRichDoc.body.innerHTML;
    var len = richHtml.length;
    if (richHtml.indexOf("WHERE") == -1) {
        len = richHtml.indexOf("GROUP");
        id = "WHERE" + id;
        selected = "WHERE" + selected;
    }
    var rng = ifmRichDoc.selection.createRange();
    rng.moveStart("character", len);
    rng.select();
    formatTextCurrent(id, type, selected);
}

function insertHTML(html) {
    html = " " + html + " ";
    clearIfmRichDoc();
    var browserName = navigator.appName;
    if (browserName == "Microsoft Internet Explorer") {
        ifmRichDoc.selection.createRange().pasteHTML(html);
    } else {
        var div = ifmRichDoc.createElement("div");
        div.innerHTML = html;
        insertNodeAtSelection(div);
    }
}

function insertNodeAtSelection(insertNode) {
    var sel = ifmRich.contentWindow.getSelection();
    var range = sel.getRangeAt(0);
    sel.removeAllRanges();
    range.deleteContents();
    var container = range.startContainer;
    var pos = range.startOffset;
    range = document.createRange();

    if (container.nodeType == 3 && insertNode.nodeType == 3) {
        container.insertData(pos, insertNode.nodeValue);
        range.setEnd(container, pos + insertNode.length);
        range.setStart(container, pos + insertNode.length);
    } else {
        var afterNode;
        if (container.nodeType == 3) {
            var textNode = container;
            container = textNode.parentNode;
            var text = textNode.nodeValue;
            var textBefore = text.substr(0, pos);
            var textAfter = text.substr(pos);
            var beforeNode = document.createTextNode(textBefore);
            afterNode = document.createTextNode(textAfter);
            container.insertBefore(afterNode, textNode);
            container.insertBefore(insertNode, afterNode);
            container.insertBefore(beforeNode, insertNode);
            container.removeChild(textNode);
        } else {
            afterNode = container.childNodes[pos];
            container.insertBefore(insertNode, afterNode);
        }
        range.setEnd(afterNode, 0);
        range.setStart(afterNode, 0);
    }
    sel.addRange(range);
}

function updateTextArea() {
    var obj = document.createElement("DIV");
    obj.style.cssText = "display:none;";
    document.body.appendChild(obj);
    clearIfmRichDoc();
    obj.innerHTML = ifmRichDoc.body.innerHTML;
    var inputObj = obj.getElementsByTagName("input");
    for (var i = inputObj.length - 1; i > -1; i--) {
        var val = document.createTextNode(inputObj[i].name);
        obj.insertBefore(val, inputObj[i]);
        obj.removeChild(inputObj[i]);
    }
    var objHtml = obj.innerHTML;
    var regExp = new RegExp("&nbsp;", "g");
    objHtml = objHtml.replace(regExp, "");
    textareaObj.value = objHtml;
}

function clearIfmRichDoc() {
    if (ifmRichDoc.body) {
        var con = ifmRichDoc.body.innerHTML;
        if (con && con.indexOf("<P>") != -1)
            ifmRichDoc.body.innerHTML = "";
    }
}

function clearOutExp() {
    //var con = ifmRichDoc.body.innerHTML;
    //if(con.indexOf("WHERE") != -1)
    //ifmRichDoc.body.innerHTML = con.substring(con.indexOf(">") + 1);
}

var mathMethod =
    [{
        "id": 0,
        "text": "常用函数",
        "state": "closed",
        "children": [{
            "text": "abs()",
            "attributes": {
                "title": "返回数字的绝对值。",
                "desc": ""
            }
        }, {
            "text": "ceil()",
            "attributes": {
                "title": "返回大于等于数字参数的最小整数(取整函数)，对数字进行上舍入。",
                "desc": ""
            }
        }, {
            "text": "floor()",
            "attributes": {
                "title": "返回小于等于数字参数的最大整数，对数字进行下舍入。",
                "desc": ""
            }
        }, {
            "text": "round()",
            "attributes": {
                "title": "返回数字最接近的整数，四舍五入。",
                "desc": ""
            }
        }, {
            "text": "max()",
            "attributes": {
                "title": "返回数个数字中较大的值。",
                "desc": ""
            }
        }, {
            "text": "min()",
            "attributes": {
                "title": "返回数个数字中较小的值。",
                "desc": ""
            }
        }, {
            "text": "sqrt()",
            "attributes": {
                "title": "返回数字的平方根。",
                "desc": ""
            }
        }]
    }, {
        "id": 1,
        "text": "三角函数",
        "state": "closed",
        "children": [{
            "text": "PI",
            "attributes": {
                "title": "圆周率π。",
                "desc": "圆的周长与其直径的比值(圆周率π)，约等于3.1415926。"
            }
        }, {
            "text": "sin()",
            "attributes": {
                "title": "返回数字的正弦值。",
                "desc": ""
            }
        }, {
            "text": "cos()",
            "attributes": {
                "title": "返回数字的余弦值。",
                "desc": ""
            }
        }, {
            "text": "tan()",
            "attributes": {
                "title": "返回数字的正切值。",
                "desc": ""
            }
        }, {
            "text": "asin()",
            "attributes": {
                "title": "返回数字的反正弦值。",
                "desc": ""
            }
        }, {
            "text": "acos()",
            "attributes": {
                "title": "返回数字的反余弦值。",
                "desc": ""
            }
        }, {
            "text": "atan()",
            "attributes": {
                "title": "返回数字的反正切值。",
                "desc": ""
            }
        }]
    }, {
        "text": "自然对数相关函数",
        "state": "closed",
        "children": [{
            "text": "E",
            "attributes": {
                "title": "自然对数的底数。",
                "desc": "E约等于2.718。"
            }
        }, {
            "text": "exp()",
            "attributes": {
                "title": "返回E的x次幂(指数)。",
                "desc": ""
            }
        }, {
            "text": "log()",
            "attributes": {
                "title": "返回数字的自然对数。",
                "desc": ""
            }
        }]
    }];
var db2Method =
    [{
        "id": "0",
        "text": "常用函数",
        "state": "closed",
        "children": [{
            "id": "CAST",
            "text": "CAST()",
            "attributes": {
                "title": "强制转换函数。",
                "desc": "CAST (<expression> AS <data_ type>[ length ])" +
                    "---字符串强制转int" +
                    "select max(cast(salesdp_id as int) ) from 'TBLSALESDP';" +
                    "----Double类型强制转换为Decimal" +
                    "select cast(p.DGDD_THRLINE_NUM as decimal(12,2)) from  tbl_payment p;" +
                    " ---TimeStamp类型按照yyyy-MM的形式输出" +
                    "select cast(UPDATETIME as varchar(7)) as queryDate,count(cardnumber) from 'TBLSTORAGE_LIST';" +
                    "---TimeStamp类型按照yyyy年MM的形式输出" +
                    "select replace(cast(UPDATETIME as varchar(7)),'-','年') as queryDate,count(cardnumber) from 'TBLSTORAGE_LIST';"
            }
        }, {
            "id": "AVG",
            "text": "AVG()",
            "attributes": {
                "title": "返回一组数值的平均值。",
                "desc": "SELECT AVG(COLUMN1) FROM TABLENAME。"
            }
        }, {
            "id": "CORRELATION",
            "text": "CORR()，CORRELATION()",
            "attributes": {
                "title": "返回一对数值的关系系数。",
                "desc": "SELECT CORRELATION(COLUMN1,COLUMN2)FROM TABLENAME。"
            }
        }, {
            "id": "COUNT",
            "text": "COUNT()",
            "attributes": {
                "title": "返回一组行或值的个数。",
                "desc": "SELECT COUNT(*)FROMB TABLENAME。"
            }
        }, {
            "id": "MAX",
            "text": "MAX()",
            "attributes": {
                "title": "返回一组数值中的最大值。",
                "desc": "SELECT MAX(COLUMN1)FROM TABLENAME。"
            }
        }, {
            "id": "MIN",
            "text": "MIN()",
            "attributes": {
                "title": "返回一组数值中的最小值。",
                "desc": "SELECT MIN(COLUMN1)FROM TABLENAME。"
            }
        }, {
            "id": "SUM",
            "text": "SUM()",
            "attributes": {
                "title": "返回一组数据的和。",
                "desc": "SELECT SUM(COLUMN1)FROM TABLENAME。"
            }
        }, {
            "id": "VARIANCE",
            "text": "VAR(),VARIANCE()",
            "attributes": {
                "title": "返回一组数值的方差。",
                "desc": "SELECT VARIANCE(COLUMN1)FROM TABLENAME。"
            }
        }, {
            "id": "ABS",
            "text": "ABS(),ABSVAL()",
            "attributes": {
                "title": "返回参数的绝对值。",
                "desc": "SELECT ABS(-3.4)FROM TABLENAME"
            }
        }, {
            "id": "FLOAT",
            "text": "FLOAT()",
            "attributes": {
                "title": "返回一个数的浮点表示。",
                "desc": "SELECT FLOAT(789)FROM TABLENAME。"
            }
        }]
    }];
