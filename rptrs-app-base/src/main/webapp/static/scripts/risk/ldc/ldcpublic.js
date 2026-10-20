/**
 * ldc 填报 与 合并所相同的 js代码
 */

//服务器发布地址，取risk路径
var strPath = window.top.location.pathname;
var postPath = strPath.substring(0, strPath.substring(1).indexOf('/') + 1);


$(function () {

    //设置事件初始发生日期
    $('#firstOccurDate').datebox({
        onSelect: function (date) {
            var formatDate = $.date.format(date, 'yyyy-MM-dd');
            var fillDate = $('#fillDate').datebox('getValue');
            var discoverDate = $('#discoverDate').datebox('getValue');

            if (fillDate && formatDate > fillDate) {
                $.messager.alert('提示', '【事件初始发生日期】不能大于【本次填报日期】!', 'info', function () {
                    $('#firstOccurDate').datebox('setValue', '');
                    $('#firstOccurDate').focus();
                });
            } else if (discoverDate && formatDate > discoverDate) {
                $.messager.alert('提示', '【事件初始发生日期】不能大【事件发现日期】!', 'info', function () {
                    $('#firstOccurDate').datebox('setValue', '');
                    $('#firstOccurDate').focus();
                });
            }

        }
    });

    //事件发现日期
    $('#discoverDate').datebox({
        onSelect: function (date) {
            var formatDate = $.date.format(date, 'yyyy-MM-dd');
            var beginDate = $('#firstOccurDate').datebox('getValue');
            if (beginDate && formatDate < beginDate) {
                $.messager.alert('提示', '【事件发现日期】不能小于【事件初始发生日期】!', 'info', function () {
                    $('#discoverDate').datebox('setValue', '');
                    $('#discoverDate').focus();
                });
            }

            var genEndtime = $('#fillDate').datebox('getValue');

            if (genEndtime && formatDate > genEndtime) {
                $.messager.alert('提示', '【事件发现日期】不能大于【本次填报日期】！', 'info', function () {
                    $('#discoverDate').datebox('setValue', '');
                    $('#discoverDate').focus();
                });
            }
        }
    });

});


function changeRisk() {
    var num1 = $('#customerImpact').combobox("getValue");
    var num2 = $('#reputationEffect').combobox("getValue");
    var num3 = $('#legalImpact').combobox("getValue");
    var num4 = $('#employeeImpact').combobox("getValue");

    if (num1 != "" && num2 != "" && num3 != "" && num4 != "") {
        var getmax = [num1, num2, num3, num4];
        riskImpactValue = eval("Math.max(" + getmax.toString() + ")");
        if (riskImpactValue == 0) riskImpactValue = 1;
        $('#riskImpact').combobox("setValue", riskImpactValue);
    }
}


//附件添加链接
function addlnk(value, rowData, rowIndex) {
    return "<a id='formatLink' href='javascript:;' onclick='viewAttach(\"" + rowData.id + "\", \"" + rowData.srcFileName + "\", \"" + rowData.fileFormat + "\");'>" + value + "</a>";
}

//附件查看链接
function viewAttach(id, name, ext) {
    pageNo++;
    open(postPath + '/attach/view?id=' + id, 'viewAttach' + pageNo, 'width=' + (screen.availWidth - 10) + ',height=' + (screen.availHeight - 50) + ',resizable=yes');
}

//转换附件单位显示
function formatFileSize(value, rowData) {
    var size = parseFloat(value);
    if (size == NaN) return '未知';

    var unit = 'B';
    if (size > 1024) {
        size = Math.round(size / 1024 * 100) / 100;
        unit = 'KB';
    }
    if (size > 1024) {
        size = Math.round(size / 1024 * 100) / 100;
        unit = 'MB';
    }
    return size + unit;
}

//附件上传
function uploadAttach(entityId, uploadSession, attachTypeCode, callBackMethod) {
    openWindow('wAttachUpload', '上传文件', postPath + '/attach/toUpload?entityId=' + entityId + '&uploadSession='
        + uploadSession + '&attachTypeCode=' + attachTypeCode
        , {inFrame: true, size: '', width: 400, height: 150, windowArgs: {callback: callBackMethod}});
}

//附件下载
function downLoadAttach(iframeName, id) {
    $('#' + iframeName).attr('src', postPath + '/attach/download?id=' + id);
}

//附件删除
function deleteAttach(tableName, id, name, ext) {
    $.messager.confirm('提示', '确定要删除附件[ ' + name + '.' + ext + ' ]吗？', function (r) {
        if (!r) return;
        $.post(postPath + '/attach/delete?ids=' + id, function (data) {
            if (data) {
                $('#' + tableName).datagrid('reload');
            }
        });
    });
}