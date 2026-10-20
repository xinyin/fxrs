<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>自定义字段</title>
    <style type="text/css">
        #customColumnIbox {
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
<div class="row">
    <bc:ibox id="customColumnIbox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:formGroup>
                <bc:label text="列名称" col="3" formControl="true"/>
                <bf:text id="columnCode" name="columnCode" col="6">
                    <bv:jepCode/>
                    <bv:notEmpty/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="中文名称" col="3" formControl="true"/>
                <bf:text id="columnName" name="columnName" col="6">
                    <bv:notEmpty/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="表达式" col="3" formControl="true"/>
                <bf:text id="expr" name="expr" col="6" multiple="true" rows="3"><bv:notEmpty/></bf:text>
                <bc:button id="exprBtn" icon="pencil" tooltip="编辑表达式" size="xs" click="exprBtn()"/>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-xs-6 col-xs-offset-3" align="center">
                    <bc:button type="primary" text="保存" click="btnSave()"/>
                    <bc:button text="关闭">
                        <be:event>
                            $.messager.hide('wEditCustomRow');
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
    var tables = ''; //已选择的表
    var type = ''; //表达式类型
    var customRow = ''; //编辑的自定义列
    var opt = ''; //操作类型
    var rowIndex = ''; //编辑自定义列在指针
    var mainIndex = '';  //编辑自定义列在总的指针
    var rCols = []; //已经选择的列
    var options = '';

    $(document).ready(function () {
        var args = $.messager.args('wEditCustomRow').data;
        tables = args.tables;
        type = args.type;
        customRow = args.customRow;
        opt = args.opt;
        rowIndex = args.rowIndex;
        mainIndex = args.mainIndex;
        options = args.options;
        rCols = args.rCols;

        /* 如果编辑，把原来的步骤给赋值上 */
        if (opt == 'edit') {
            $('#columnCode').val(customRow.columnCode);
            $('#columnCode').attr('disable', true);
            $('#columnName').val(customRow.columnName);
            $('#expr').val(customRow.expr);
            rCols = arr_dive(rCols, [customRow]);
        }
    });

    /* 编辑表达式 */
    function exprBtn() {
        var expr = {expr: $('#expr').val()};
        var data = {
            tables: tables,
            expr: expr,
            type: type,
            options: ''
        };
        $.messager.modal({
            winId: 'wSelectCondition',
            width: 800,
            height: 450,
            title: '选择条件',
            url: '${base}/sys/metaQuery/condition',
            args: {callback: conCallBack, data: data}
        });
    }

    /* 编辑表达式的回调函数 ，更新表达式*/
    function conCallBack(data) {
        expr = data;
        $('#expr').val(data.expr);
    }

    /* 保存 */
    function btnSave() {
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            var columnCode = $.trim($('#columnCode').val());
            columnCode = columnCode.toUpperCase();

            /* 判断是否存在与已选择的列重名的情况，如果存在，就返回重新修改 */
            if (isExistCol(columnCode)) {
                $.messager.toastr('系统提示', '列名称与已选择的列重复，请核实后输入', 'warning');
                return false;
            }

            var checkObj = {
                columnCode: columnCode,
                columnName: $('#columnName').val(),
                expr: $('#expr').val(),
                rowIndex: rowIndex,
                mainIndex: mainIndex,
                opt: opt
            };

            $.messager.args('wEditCustomRow').callback(checkObj);
            $.messager.hide('wEditCustomRow');
        }
    }

    /* ==================方法工具================== */

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
     * 比较两个数组，去掉第一个数组中的第二个数组中存在的对象。
     * 第一个数组减去第二个数组
     */
    function arr_dive(aArr, bArr) {

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
</script>
</body>
</html>
