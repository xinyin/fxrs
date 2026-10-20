<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>选择条件</title>
    <style type="text/css">
        #conditionIbox {
            margin-bottom: 0px;
        }

        .ibox-content {
            padding-bottom: 0px;
        }

        .panel-body {
            padding-bottom: 0px;
        }
    </style>
</head>
<body class="gray-bg" style="overflow: hidden">
<div class="row" style="overflow: hidden">
    <bc:ibox id="conditionIbox" title="" subTitle="" col="12">
        <bf:form id="editForm" method="post" validation="true">
            <bf:formGroup>
                <bf:text id="expr" name="expr" col="12" multiple="true" rows="3"></bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:tabs id="asd">
                    <bc:tab id="columnInfo" title="原始列" visible="true">
                        <bf:formGroup>
                            <bf:text id="searchText" name="searchText" placeholder="搜索列名、中文名" col="4" colOffset="8"/>
                            <div style="overflow-y: auto; overflow-x: hidden; max-height: 220px;" class="col-xs-12">
                                <bc:table id="columns" idField="" url="" showRefresh="false" showColumns="false"
                                          pagination="false">
                                    <bc:column field="alias" title="表名" width="100" formatter="formatTableName"/>
                                    <bc:column field="columnCode" title="列名" width="100" formatter="isKeyFormat"/>
                                    <bc:column field="columnName" title="中文名" width="100"/>
                                    <bc:column field="dataType" title="类型" width="100"/>
                                    <be:event type="onDblClickRow">onDblClickRow</be:event>
                                </bc:table>
                            </div>
                        </bf:formGroup>
                    </bc:tab>
                    <bc:tab id="funInfo" title="函数">
                        <div style="overflow-y: auto; overflow-x: hidden; max-height: 270px;">
                            <bc:table id="funColumns" idField="" url="" showRefresh="false" showColumns="false"
                                      pagination="false">
                                <bc:column field="expr" title="函数" width="100"/>
                                <bc:column field="funcDesc" title="函数名" width="100"/>
                                <bc:column field="category" title="函数类型" width="100"/>
                                <be:event type="onDblClickRow">onDblClickRow</be:event>
                            </bc:table>
                        </div>
                    </bc:tab>
                </bc:tabs>
            </bf:formGroup>
            <bf:formGroup>
                <div align="center">
                    <bc:button type="primary" text="确定" click="btnSave()"/>
                    <bc:button text="关闭">
                        <be:event>
                            $.messager.hide('wSelectCondition');
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    var cols = []; //所有列
    var pos = -1;
    var tables;

    $(document).ready(function () {
        var args = $.messager.args('wSelectCondition').data;
        tables = args.tables;
        var expr = args.expr;
        var type = args.type;

        if (expr) {
            $('#expr').val(expr.expr);
        }

        /* 初始化页面数据 */
        init();
    });

    function init() {
        $('.fixed-table-toolbar').hide();
        $('#searchText').parent().attr('style', 'padding-right: 32px;');
        $('#expr').parent().attr('style', 'padding-left: 0px;');

        /* 自动搜索 */
        $('#searchText').bind('input propertychange', function () {
            setTimeout(doSearch, 500);
        });

        /* 后台取出对应的所有列 */
        var tableJson = JSON.stringify(tables);
        $.ajax({
            type: 'GET',
            url: '${base}/sys/metaColumn/getAllColumns',
            data: {tables: tableJson},
            dataType: 'json',
            success: function (data) {
                cols = data;

                /* 加载表数据 */
                $('#columns').bootstrapTable('append', cols.slice(0));
            }
        });

        /* 加载函数表数据 */
        loadFunColumns()
    }

    function loadFunColumns() {
        /* 定义函数*/
        var data = [
            {expr: 'CONCAT(text1, text2)', funcDesc: '连接字符串', category: '字符串函数'},
            {expr: 'LENGTH(text)', funcDesc: '字符串长度', category: '字符串函数'},
            {expr: 'SUBSTR(text, start, count)', funcDesc: '截取字符串', category: '字符串函数'},
            {expr: 'LEFT(str, len)', funcDesc: '从左边开始截取字符串', category: '字符串函数'},
            {expr: 'RIGHT(str, len)', funcDesc: '从右边开始截取字符串', category: '字符串函数'},
            {expr: 'TRIM(text)', funcDesc: '去除字符串两端的空格', category: '字符串函数'},
            {expr: 'LTRIM(text)', funcDesc: '去除字符串左边的空格', category: '字符串函数'},
            {expr: 'RTRIM(text)', funcDesc: '去除字符串右边的空格', category: '字符串函数'},
            {expr: 'UPPER(text)', funcDesc: '把字符串转化为大写', category: '字符串函数'},
            {expr: 'LOWER(text)', funcDesc: '把字符串转化为小写', category: '字符串函数'},
            {
                expr: 'INSTR(source, target, str, end)',
                funcDesc: '函数返回“目标字符串”从“起始位置”开始在“源字符串”中第“匹配序号”次出现时的位置',
                category: '字符串函数'
            },
            {expr: 'INSTR(source, target)', funcDesc: '函数返回“目标字符串”从第一个字符开始在“源字符串”中第1次出现时的位置', category: '字符串函数'},
            {expr: 'REPLACE(text1, text2, text3)', funcDesc: '替换字符串', category: '字符串函数'},
            {expr: 'RPAD(text, length, format)', funcDesc: '当字符串长度不足length时，用format替换，增加在右边', category: '字符串函数'},
            {expr: 'LPAD(text, length, format)', funcDesc: '当字符串长度不足length时，用format替换，增加在左边', category: '字符串函数'},
            {expr: 'TRANSLATE(source, original, replacement)', funcDesc: '转换字符串', category: '字符串函数'},

            {expr: 'ABS(number)', funcDesc: '返回数字的绝对值', category: '数值函数'},
            {expr: 'SIGN(number)', funcDesc: '当数字为正数返回1，数字为负数时返回-1', category: '数值函数'},
            {expr: 'CEIL(number)', funcDesc: '向上取整', category: '数值函数'},
            {expr: 'FLOOR(number)', funcDesc: '向下取整', category: '数值函数'},
            {expr: 'ROUND(number, num_digits)', funcDesc: '四舍五入，取小数点后num_digits位。num_digits可以为负数', category: '数值函数'},
            {expr: 'TRUNC(number, num_digits)', funcDesc: '格式化数字，取小数点后num_digits位。num_digits可以为负数', category: '数值函数'},
            {expr: 'MOD(number1, number2)', funcDesc: '取余', category: '数值函数'},

            {expr: 'LAST_DAY(date)', funcDesc: '取该日期当月最后一天', category: '日期时间函数'},
            {expr: 'ADD_MONTHS(date, months)', funcDesc: '日期增加months个月', category: '日期时间函数'},
            {expr: 'SYSDATE', funcDesc: '获得当前时间', category: '日期时间函数'},
            {expr: 'DAYS(date)', funcDesc: 'days函数，返回日期与“date(0000-01-00)”之间的天数', category: '日期时间函数'},
            {expr: 'HOUR(datetime)', funcDesc: '返回时间是小时数字', category: '日期时间函数'},
            {expr: 'MINUTE(datetime)', funcDesc: '返回时间的分钟数字', category: '日期时间函数'},
            {expr: 'SECOND(datetime)', funcDesc: '返回时间的秒数', category: '日期时间函数'},
            {expr: 'DAY(date)', funcDesc: '返回时间的天数', category: '日期时间函数'},
            {expr: 'MONTH(date)', funcDesc: '返回时间的月数', category: '日期时间函数'},
            {expr: 'YEAR(date)', funcDesc: '返回时间的年数', category: '日期时间函数'},
            {expr: 'DATE(datetime)', funcDesc: '返回日期', category: '日期时间函数'},
            {expr: 'TIME(datetime)', funcDesc: '返回时间', category: '日期时间函数'},
            {expr: 'WEEK(date)', funcDesc: '获得时间的周数', category: '日期时间函数'},
            {expr: 'DAYNAME(date)', funcDesc: '获得日期的英文的星期一到星期日', category: '日期时间函数'},

            {expr: 'TO_CHAR(number)', funcDesc: '数字转字符串', category: '转换函数'},
            {expr: 'TO_CHAR(date, format)', funcDesc: '返回日期格式化后的字符串', category: '转换函数'},
            {expr: 'TO_DATE(text, format)', funcDesc: '字符串转为日期格式', category: '转换函数'},
            {expr: 'TO_NUMBER(text)', funcDesc: '字符串转数字', category: '转换函数'},
            {expr: 'TO_TIMESTAMP(text, format)', funcDesc: '字符串转为时间戳格式', category: '转换函数'},
            {
                expr: 'NVL(check_expression, replacement_value)',
                funcDesc: '当check_expression为空时就显示replacement_value',
                category: '转换函数'
            },
        ];

        $('#funColumns').bootstrapTable('append', data.slice(0));
    }

    /* 双击原始列 */
    function onDblClickRow(row, $element, field) {
        updateExpr(row.expr);
    }

    /* 更新公式的值 */
    function updateExpr(data) {
        /* 转义= */
        if (data) {
            if (data == 'EQ')
                data = '=';
            else if (data == 'LE')
                data = '<=';
            else if (data == 'GE')
                data = '>=';

            var infg = $('#expr').val();
            var t = $('#expr')[0];

            if ((window.ActiveXObject || "ActiveXObject" in window) && pos != -1) {
                TT.pos(t, pos);
            }

            TT.ins(t, data);
            if (data == "''" || data == '()') {
                TT.mov(t, 0, 1);
            }
            pos = TT.pos(t);
        }
    }

    /* 格式化表名*/
    function formatTableName(value, row, index) {
        if (value == 'T1')
            return '(主表)' + row.tableCode;
        else
            return '(关联表-' + value + ')' + row.tableCode;
    }

    /* 列主键格式化，是否显示主键图标 */
    function isKeyFormat(value, row, index) {
        if (row.isKey == 'Y') {
            return '<i class="fa fa-key" style="padding-right: 3px;"></i>' + row.columnCode;
        }
        return row.columnCode;
    }

    /* 左页面查询 */
    function doSearch() {
        $('#columns').jTable('clearCheckData');

        /* 搜索字段 */
        var properties = ['columnName', 'columnCode'];

        /* 获取搜索输入的值 */
        var value = $('#searchText').val();
        value = value.trim();

        /* 输入的条件不为空，进行过滤 */
        if (value) {
            value = value.toUpperCase();
            /* 新建数组用于筛选后显示 */
            var metaDNewata = new Array();
            /* 遍历数据 */
            $.each(cols, function (index, data) {
                var flag = false;
                /* 遍历数据，对每个数据进行比对 */
                $.each(properties, function (i, key) {
                    var val = data[key];
                    if (val) {
                        if (val.indexOf(value) > -1) {
                            flag = true;
                            return false;
                        }
                    }
                });
                if (flag) {
                    metaDNewata.push(data);
                }
                flag = false;
            });
            /* 把metaDNewata load 到表格 中*/
            $('#columns').bootstrapTable('removeAll');
            $('#columns').bootstrapTable('append', metaDNewata);
        } else {
            /*没有输入任何值，直接返回原始数组*/
            $('#columns').bootstrapTable('removeAll');
            $('#columns').bootstrapTable('append', cols.slice(0));
        }
    }

    /* 保存 */
    function btnSave() {
        var checkObj = {
            expr: $('#expr').val()
        };
        $.messager.args('wSelectCondition').callback(checkObj);
        $.messager.hide('wSelectCondition');
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
                $.messager.alert("987");
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
    };

    /* 获取文本框中光标位置 */
    $.fn.extend({
        focAdd: function (value) {
            var elem = this[0];
            if (elem && (elem.tagName == "TEXTAREA" || elem.type.toLowerCase() == "text")) {
                if ($.browser.msie) {
                    var rng;
                    if (elem.tagName == "TEXTAREA") {
                        rng = event.srcElement.createTextRange();
                        rng.moveToPoint(event.x, event.y);
                    } else {
                        rng = document.selection.createRange();
                    }
                    if (value === undefined) {
                        rng.moveStart("character", -event.srcElement.value.length);
                        return rng.text.length;
                    } else if (typeof value === "number") {
                        var index = this.position();
                        index > value ? (rng.moveEnd("character", value - index)) : (rng.moveStart("character", value - index))
                        rng.select();
                    }
                } else {
                    if (value === undefined) {
                        return elem.selectionStart;
                    } else if (typeof value === "number") {
                        elem.selectionEnd = value;
                        elem.selectionStart = value;
                    }
                }
            } else {
                if (value === undefined)
                    return undefined;
            }
        }
    });
</script>
</body>
</html>
