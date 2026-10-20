<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>表达式编辑</title>
    <x:jsLib/>
    <style type="text/css">

        .left_Expr {
            height: 127.535px;
            border: 1px solid #1ab394;
            float: left;
            overflow: hidden;
        }

        .left_Expr ul {
            width: 100%;
            height: 100%;
            text-align: left;
            text-decoration: none;
        }

        .left_Expr li {
            height: 20px;
            line-height: 40px;
            list-style-type: none;
        }

        .left_Expr a {
            text-decoration: none;
            color: inherit;
            word-spacing: normal;
        }

        .mouse_selected {
            background-color: #FBEC88;
        }

        legend {
            color: #06c;
            font-weight: 800;
            background: #fff;
            border: #b6b6b6 solid 1px;
            padding: 3px 6px;
        }

        #left_Expr {
            position: relative;
            left: 120px;
        }

        #childExpr {
            position: relative;
            left: 200px;
        }

        #genre {
            position: relative;
            left: 50px;
        }

    </style>
</head>
<body class="gray-bg" style="background-color: #FFFFFF">
<div class="row">
    <%-- 		<bc:ibox id="ruleExprBox" title="编辑expr" subTitle="" col="12" collapsible="true"> --%>
    <bf:form id="editForm" method="post" validation="true">

        <bf:formGroup type="success">
            <bc:col col="12">
                <bf:text id="expr" name="expr" col="12" value="" multiple="true" cls="expr_text"></bf:text>
            </bc:col>
        </bf:formGroup>

        <bf:formGroup type="success">
            <bc:label text="运算符" col="2" style="color: black" formControl="true"/>
            <ul id="expression_button" style="width: 100%; height: 100%;">

                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr("+");'>+</li>
                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr("-");'>-</li>
                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr("*");'>*</li>
                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr("/");'>/</li>
                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr("!");'>!</li>
                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr("|");'>|</li>
                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr("&");'>&</li>
                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr("(");'>(</li>
                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr(")");'>)</li>
                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr("=");'>=</li>
                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr("<");'>&lt;</li>
                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr(">");'>&gt;</li>

                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr("!=");'>!=</li>
                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr("<=");'>&lt;=</li>
                <li class="btn btn-default btn-xs" unselectable="on" onclick='updateOptExpr(">=");'>&gt;=</li>
            </ul>
        </bf:formGroup>

        <bf:formGroup type="success" hr="dashed" hrp="top">
            <bc:label text="类别" col="4" formControl="true" style="text-align:center;color: black"/>
            <bc:label text="子类别" col="4" formControl="true" style="text-align:center;color: black"/>
            <bc:label text="双击插入" col="4" formControl="true" style="text-align:center;color: black"/>
        </bf:formGroup>
        <bf:formGroup type="success">
            <div class="left_Expr" id="genre" style="width: 200px; margin-left: 15px;">
                <ul id="type">
                    <li name="dataElement" onclick="selectType('dataElement')"><a href="#">数据绑定</a></li>
                    <li name="functions" onclick="selectType('functions')"><a href="#">函数</a></li>
                    <li name="operators" onclick="selectType('operators')"><a href="#">运算符</a></li>
                </ul>
            </div>

            <div class="left_Expr" id="left_Expr" style="width: 200px; margin-left: 15px;">
                <ul id="childrenType"></ul>
            </div>
            <div class="left_Expr" id="childExpr" style="width: 200px; margin-left: 15px;overflow: auto;">
                <ul id="children"></ul>
            </div>

        </bf:formGroup>

        <bf:formGroup>
            <div class="col-sm-12" align="center">
                <bc:button type="primary" text="保存" click="btnSave()"/>
                <bc:button text="关闭">
                    <be:event>
                        $.messager.hide('winSeriesExprEdit');
                    </be:event>
                </bc:button>
            </div>
        </bf:formGroup>
    </bf:form>
    <%-- 		</bc:ibox> --%>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    var params;
    var pos = -1;
    var widgetProvierId; //数据绑定程序ID
    var provider; //定义数据绑定子类函数

    /* 定义函数子类函数 */
    var func = {
        data: [{name: 'f01', value: '字符串函数'}, {name: 'f02', value: '数学函数'}, {name: 'f03', value: '数据库函数'}]
    };
    /* 定义运算符的子类函数 */
    var operator = {
        data: [{name: 'o01', value: '算数运算符'}, {name: 'o02', value: '逻辑运算符'}]
    };
    /* 定义函数的具体数据 */
    var childrenData = {
        o01:
            [
                {name: '+', value: '+'},
                {name: '-', value: '-'},
                {name: '*', value: '*'},
                {name: '/', value: '/'}
            ]
    };

    childrenData.o02 = [
        {name: '&', value: '&'},
        {name: '!', value: '!'},
        {name: '|', value: '|'}
    ];
    childrenData.f01 = [
        {name: '.concat()', value: 'concat'},
        {name: '.split()', value: 'split'},
        {name: '.toLowerCase()', value: 'toLowerCase'},
        {name: '.toUpperCase()', value: 'toUpperCase'},
        {name: '.length()', value: 'length'},
        {name: '.indexOf()', value: 'indexOf'},
        {name: '.substring()', value: 'substring'},
        {name: '.substr()', value: 'substr'}
    ];

    childrenData.f02 = [
        {name: 'Math.round()', value: 'Math.round'},
        {name: 'Math.sqrt()', value: 'Math.sqrt'},
        {name: 'Math.cos()', value: 'Math.cos'},
        {name: 'Math.sin()', value: 'Math.sin'},
        {name: 'Math.tan()', value: 'Math.tan'},
        {name: 'Math.acos()', value: 'Math.acos'},
        {name: 'Math.floor()', value: 'Math.floor'},
        {name: 'Math.ceil()', value: 'Math.ceil'}
    ];

    childrenData.f03 = [
        {name: 'COUNT()', value: 'COUNT'},
        {name: 'SUM()', value: 'SUM'},
        {name: 'Max()', value: 'Max'},
        {name: 'Min()', value: 'Min'},
        {name: 'AVG()', value: 'AVG'}
    ];

    $(document).ready(function () {
        var args = $.messager.args('winSeriesExprEdit');
        params = args.params;
        widgetProvierId = params.provierId;
        $("#expr").val($.trim(params.expr));

        $.mask.post("${base}/sys/dashWidgetProvider/getDataProviderByWidgetProvierId", {widgetProvierId: widgetProvierId}, function (data) {
            provider = {data: [{name: 'p01', value: data.name}]};
            childrenData['p01'] = [];
            var columns = data.columns;
            $.each(columns, function (i, v) {
                childrenData['p01'].push({name: 'dataRow[&quot;' + v.columnCode + '&quot;]', value: v.columnCode});
            });
        });

        //判断是否是IE浏览器
        if (window.ActiveXObject || "ActiveXObject" in window) {
            $('#expr').mouseup(function () {
                pos = TT.pos(this);
            });

            $('#expr').keyup(function () {
                pos = TT.pos(this);
            });
        }
    })

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
                alert("987");
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

    //更改表达式
    function updateOptExpr(data) {
        if (data) {
            /* 因为直接把=作为参数，会出现BUG，所以进行转义 */
            if (data == "EQ") {
                data = "=";
            } else if (data == "LE") {
                data = "<=";
            } else if (data == "GE") {
                data = ">=";
            }
            //获取更改之前的表达式
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

    //设置选中类别的样式
    function selectType(type) {
        /* 设置选中样式 */
        $('#type li a').removeClass();
        $('#type li[name="' + type + '"] a').addClass('mouse_selected');
        /* 清空原来子项目 */
        $("#childrenType").empty();
        $("#children").empty();
        /* 组装新子项目 */
        $('#childrenType').append(generateChildrenType(type));
    }

    /* 根据类别，组装对应的子类别 */
    function generateChildrenType(type) {

        var liHtml = '';
        var data = [];

        if ('dataElement' == type) {
            data = provider.data;

        } else if ('functions' == type) {
            data = func.data;
        } else {
            data = operator.data;
        }

        $.each(data, function (i, v) {
            liHtml += '<li onclick="selectChildType(\'' + v.name + '\')" name="' + v.name + '" style="cursor: pointer;height: 20px;line-height: 20px;" ><a href="#" style="text-decoration: none;">' + v.value + '</a></li>';
        });

        return liHtml;
    }

    //点击子类别
    function selectChildType(type) {
        /* 设置选中样式 */
        $('#childrenType li a').removeClass();
        $('#childrenType li[name="' + type + '"] a').addClass('mouse_selected');

        var childs = childrenData[type];

        var liHtml = '';
        $.each(childs, function (i, v) {
            liHtml += '<li onclick="updateOptExpr(\'' + v.name + '\')" style="margin-left: 5px; cursor: pointer;height: 20px;line-height: 20px;" ><a href="#" style="text-decoration: none;">' + v.value + '</a></li>';
        });
        $('#children').empty();
        $('#children').append(liHtml);
    }

    /* 保存 */
    function btnSave() {
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            var formData = $('#editForm').serializeAll();
            var expr = $("#expr").val();
            var obj = {
                expr: expr,
                widgetProvierId: widgetProvierId
            };

            $.mask.post('${base}/sys/dashWidgetProvider/verifyExpr', obj, function (data) {
                if (data.success) {
                    params.expr = expr;
                    $.messager.args('winSeriesExprEdit').callback(params);
                    $.messager.hide('winSeriesExprEdit');
                } else {
                    $.messager.toastr('错误', '表达式编辑错误，请检查', 'error');
                }
            });
        }
    }
</script>
</body>
</html>
