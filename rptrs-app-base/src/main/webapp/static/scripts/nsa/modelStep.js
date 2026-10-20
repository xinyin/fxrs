/* 定义表类型 */
var TABLE_TYPE_STEP = 2,
    TABLE_TYPE_META = 1;
/*定义表达式类型*/
var EXPR_TYPE_EXTEND = 2,
    EXPR_TYPE_BASE = 1;

/*定义返回结果表类型*/
var RESULT_TYPE_TABLE = 'T',
    RESULT_TYPE_VIEW = 'V';

/*定义列类型*/
var COLUMN_TYPE_CUSTOM = 'C',
    COLUMN_TYPE_ORIGINAL = 'O';

/*定义公式类型*/
var EXPR_TYPE_BASE = 'B',
    EXPR_TYPE_EXTEND = 'E';

/*表达式数据*/
var baseOpt = {
    '+': '+',
    '-': '-',
    '*': '*',
    '/': '/',
    '||': '||',
    '\'\'': '\'\''
};
/*扩展表达式类型*/
var extOpt = {
    '+': '+',
    '-': '-',
    '*': '*',
    '/': '/',
    '||': '||',
    '\'\'': '\'\'',
    '\(\)': '\(\)',
    ',': ',',
    '%': '%',
    '=': '=',
    '&lt;&gt;': '&lt;&gt;',
    '&lt;': '&lt;',
    '&gt;': '&gt;',
    '<=': '<=',
    '>=': '>=',
    'AND': 'AND',
    'OR': 'OR',
    'NOT': 'NOT',
    'IN': 'IN',
    'LIKE': 'LIKE',
    'NULL': 'NULL'
};

/*连接类型*/
var baseJoinType =
    [{
        'id': 'INNER',
        'text': '完全匹配',
        'selected': true
    }, {
        'id': 'LEFT',
        'text': '主表中的纪录'
    }, {
        'id': 'LEFTONLY',
        'text': '主表有而关联表中没有的纪录'
    }, {
        'id': 'RIGHTONLY',
        'text': '主表没有而关联表中有的纪录'
    }, {
        'id': 'FULL',
        'text': '主表和关联表的全纪录'
    }];


/*格式化列数据，去掉不必要的列*/
function formatCol(cols) {
    var newCols = [];
    $.each(cols, function (index, col) {
        var data = {
            columnCode: col.columnCode,
            columnName: (col.columnName) ? col.columnName : '',
            dataType: col.dataType,
            columnType: (col.columnType) ? col.columnType : 'O',
            expr: col.expr,
            length: col.length,
            scale: col.scale,
            columnOrder: index,
            columnoProp: (col.prop) ? col.prop : ''
        };
        newCols.push(data);
    });

    return newCols;
}

/*格式化Script中列数据，去掉不必要的列*/
function formatScrCol(cols) {
    var newCols = [];
    $.each(cols, function (index, col) {
        var data = {
            type: col.columnType,
            code: col.columnCode,
            name: (col.columnName) ? col.columnName : '',
            expr: col.expr
        };
        newCols.push(data);
    });

    return newCols;
}

/*格式化Script中列数据，去掉不必要的列*/
function preScrCol(cols) {
    var newCols = [];
    $.each(cols, function (index, col) {
        var data = {
            columnType: col.type,
            columnCode: col.code,
            columnName: col.name,
            expr: col.expr
        };
        newCols.push(data);
    });

    return newCols;
}

/* 数组深度拷贝 */
function copyArray(arr) {
    var newArr = [];
    $.each(arr, function (index, val) {
        newArr.push(val);
    });
    return newArr;
}

/*选择行 id为datagrid Id， length 数组长度，index当前索引*/
function selectRow(id, length, index) {
    if (length == index)
        $('#' + id).datagrid('selectRow', (index - 1));
    else if (length > 1)
        $('#' + id).datagrid('selectRow', index);
    else if (length == 1)
        $('#' + id).datagrid('selectRow', 0);
}


/*datagrid查询 */
function doSearchReLoadData(datagridId, initialData, searchBox, value) {

    /* 获取搜索输入的值 */
    value = value.trim();
    /* 输入的条件不为空，进行过滤 */
    if (value) {

        value = value.toUpperCase();
        /* 获取原数组长度 */
        var lengths = initialData.length;

        /* 新建数组用于筛选后显示 */
        var metaDNewata = new Array();

        /* 筛选符合条件的数据  */
        for (var i = 0; i < lengths; i++) {
            var columnCode = initialData[i].columnCode;
            var columnName = initialData[i].columnName;

            /* 定义变量，记录输入的值是否包含子在columnCode */
            var columnCodeLength = -1;
            /* 定义变量，记录输入的值是否包含子在columnName */
            var columnNameLength = -1;

            /* 原始数据列名不为空进行筛选 */
            if (columnCode) {
                /* 用indexOf查找输入的值是否包含在columnCode，包含返回大于-1否则返回-1 */
                columnCodeLength = columnCode.indexOf(value);
            }

            /* 原始数据描述不为空进行筛选 */
            if (columnName) {
                /* 用indexOf查找输入的值是否包含在columnName，包含返回大于-1否则返回-1 */
                columnNameLength = columnName.indexOf(value);
            }

            /* 输入的值包含在columnCode 或者 columnName 把此对象push到数组metaDNewata */
            if (columnCodeLength >= 0 || columnNameLength >= 0) {
                metaDNewata.push(initialData[i]);
            }
        }

        /* 把metaDNewata load 到 datagrid 中*/
        $('#' + datagridId).datagrid('loadData', metaDNewata);
    } else {
        /*没有输入任何值，直接返回原始数组*/
        $('#' + datagridId).datagrid('loadData', initialData);
        $('#' + searchBox).searchbox('setValue', null);
    }
}

/* 比较两个数组，去掉第一个数组中的第二个数组中存在的对象 */
function arr_dive(aArr, bArr) {   //第一个数组减去第二个数组

    if (bArr.length == 0) {
        return aArr;
    }
    var result = [];
    $.each(aArr, function (aIndex, a) {
        var isHave = false;
        $.each(bArr, function (bIndex, b) {
            if (a.expr == b.expr) {
                isHave = true;
                bArr[bIndex] = a;
            }
        });
        if (!isHave) {
            result.push(a);
        }
    });
    return result;
}

/* 判断列名是否已经存在 ，存在返回true，不存在返回false*/
function isExistCol(value) {
    var isExist = false;
    /* 遍历整个已选择的数组，查看是否已经存在该列名 */
    $.each(rCols, function (index, col) {
        if (col.columnCode == value) {
            return isExist = true;
        }
    });
    return isExist;
}


/*
 * 操作 textarea 的工具方法
 */
var TT = {
    /*
    * 获取光标位置
    * @param t element
    * @param p number
    * @return number
    */
    pos: function (t, p) {
        if (p != undefined) {
            this.sel(t, p, p);
        } else {
            if (document.selection) {
                t.focus();
                var ds = document.selection;
                var range = ds.createRange();
                var stored_range = range.duplicate();
                stored_range.moveToElementText(t);
                stored_range.setEndPoint("EndToEnd", range);
                t.selectionStart = stored_range.text.length - range.text.length;
                t.selectionEnd = t.selectionStart + range.text.length;
                return t.selectionStart;
            } else {
                return t.selectionStart;
            }
        }
    },

    /*
     * 光标移动n个位置
     * @param t element
     * @param p number
     * @param n number
     */
    mov: function (t, p, n) {
        var pos = this.pos(t);
        if (p == 0) {
            pos -= n;
        } else {
            pos += n;
        }
        this.pos(t, pos);
    },


    /*
    * 插入到光标后面
    * @Method add
    * @param t element
    * @param txt String
    * @return
    */
    ins: function (t, txt) {
        var val = t.value;
        if (document.selection) {
            t.focus();
            document.selection.createRange().text = txt;
        } else {
            var cp = t.selectionStart;
            var ubbLength = t.value.length;
            var s = t.scrollTop;
            // document.getElementById('aaa').innerHTML += s + '<br/>';
            t.value = t.value.slice(0, t.selectionStart) + txt + t.value.slice(t.selectionStart, ubbLength);
            this.pos(t, cp + txt.length);
            // document.getElementById('aaa').innerHTML += t.scrollTop + '<br/>';
            firefox = navigator.userAgent.toLowerCase().match(/firefox\/([\d\.]+)/) && setTimeout(function () {
                if (t.scrollTop != s) t.scrollTop = s;
            }, 0);
        }
        t.focus();
    },

    /*
    * 删除光标 前面或者后面的 n 个字符
    * @Method del
    * @param t element
    * @param n number n>0 后面 n<0 前面
    * @return
    * 重新设置 value 的时候 scrollTop 的值会被清0
    */
    rem: function (t, n) {
        var p = this.pos(t);
        var s = t.scrollTop;
        var val = t.value;
        t.value = n > 0 ? val.slice(0, p - n) + val.slice(p) : val.slice(0, p) + val.slice(p - n);
        console.info(t.value);
        this.pos(t, p - (n < 0 ? 0 : n));
        firefox = navigator.userAgent.toLowerCase().match(/firefox\/([\d\.]+)/) && setTimeout(function () {
            if (t.scrollTop != s) t.scrollTop = s;
        }, 10);
        t.focus();
    },

    /*
    * 选中 s 到 z 位置的文字
    * @Method sel
    * @param t element
    * @param s number
    * @param z number
    * @return
    */
    sel: function (t, s, z) {
        if (typeof (s) == "string") {
            var index = t.value.indexOf(s);
            index != -1 ? this.sel(t, index, index + s.length) : false;
        } else {
            if (document.selection) {
                var range = t.createTextRange();
                range.moveEnd('character', -t.value.length);
                range.moveEnd('character', z);
                range.moveStart('character', s);
                range.select();
            } else {
                t.setSelectionRange(s, z);
                t.focus();
            }
        }

    }

} 
