var strPath = window.top.location.pathname;
var postPath = strPath.substring(0, strPath.substring(1).indexOf('/') + 1);
var readOnly, formHref, win;
var space = 200;
var WIN_WIDTH = window.screen.width - space, WIN_HEIGHT = window.screen.height - space / 2;
var WIN_TOP = space / 6, WIN_LEFT = space / 2;
//指标来源类型
var fromTypeData;
//指标状态
var indicatorStatusData;
//当前机构下所有部门
var currentUnitsData;
//当前机构下所有部门
var allFirstUnitsData;
//指标单元状态
var statusData;
//阈值来源类型
var ThresholdFromData;
var indicatorDimensionData;
//
var dimensionDatas;
$.get(postPath + "/kri/dimensionData/findAll", function (data) {
    dimensionDatas = data;
});
//阈值来源类型
$.post(postPath + "/fixedDict/ThresholdFrom", function (data) {
    ThresholdFromData = data;
});
/*$.post(postPath+"/fixedDict/IndicatorFrom",function(data){
	//$("#fromType").combobox("loadData",data);
	fromTypeData = data;
});*/
/*$.post(postPath+"/dict/get/INDICATOR_STATUS",function(data){
	$("#indicatorStatus").combobox("loadData",data);
	indicatorStatusData = data;
});*/
$.post(postPath + "/kri/indicator/getUnitsToCombo", function (data) {
    currentUnitsData = data;
});
$.post(postPath + "/kri/indicator/getAllFirstUnits", function (data) {
    allFirstUnitsData = data;
});
/*$.post(postPath+"/dict/get/INDICATORCELL_STATUS",function(data){
	$("#status").combobox("loadData",data);
	statusData = data;
});*/
var datagridColumns;
var indicatorData;

function initIndicatorList(indicatorGridId, childGridId, type) {
    var indicatorColumns = new Array();
    if (type == "1") {
        indicatorColumns.push({field: 'indicatorCode', title: '指标预警人次', width: 85, formatter: waringCountFormatter});
    } else if (type == "2") {
    } else if (type == "current") {
        indicatorColumns.push({field: 'indicatorCode', title: '指标预警人次', width: 85, formatter: waringCountFormatter});
    } else {
        indicatorColumns.push({field: 'indicatorCode', title: '指标编码', width: 85});
    }
    indicatorColumns.push({field: 'indicatorName', title: '指标名称', width: 120});
    $('#' + indicatorGridId).datagrid({
        title: '指标列表', //标题
        singleSelect: true,
        rownumbers: true,
        fit: true, //高度
        idField: "indicatorId",
        columns: [indicatorColumns],
        toolbar: [{
            text: '查看指标',
            iconCls: 'icon-view',
            handler: function () {
                viewIndicator(indicatorGridId);
            }
        }],
        onLoadSuccess: function (data) {
            easyui.datagrid.headcenter();
            $(this).datagrid('clearSelections');
            if (data.total > 0) {
                $(this).datagrid('selectRow', 0);
            }
            loadDatagridData(indicatorGridId, childGridId);
        },
        onClickRow: function () {
            loadDatagridData(indicatorGridId, childGridId);
        },
        onDblClickRow: function () {
            viewIndicator(indicatorGridId);
        }
    });
    if (type == "current") {
        $.post(postPath + "/kri/waringResult/getAllWarningIndicator", function (data) {
            indicatorData = data;
            $('#' + indicatorGridId).datagrid("loadData", data);
        });
    } else {
        $.post(postPath + "/kri/indicator/getAllReleased", function (data) {
            indicatorData = data;
            $('#' + indicatorGridId).datagrid("loadData", data);
        });
    }
}

//查看指标
function viewIndicator(indicatorGridId) {
    var r = $('#' + indicatorGridId).datagrid('getSelected');
    if (r) {
        readOnly = true;
        formHref = postPath + '/kri/indicator/edit/' + r.indicatorId;
        if (win) {
            win.focus();
        } else {
            win = window.open(postPath + '/kri/indicator/edit', "查看指标", 'height=' + WIN_HEIGHT + ',width=' + WIN_WIDTH + ',top=' + WIN_TOP + ',left=' + WIN_LEFT + ',toolbar=no,menubar=no, resizable=no,location=no, status=no');
        }
    }
}

var oldIndicatorId;

function loadDatagridData(indicatorGridId, datagridId) {
    var row = $('#' + indicatorGridId).datagrid("getSelected");
    if (row) {
        var newIndicatorId = row.indicatorId;
        if (oldIndicatorId != newIndicatorId) {
            oldIndicatorId = newIndicatorId;
            $.post(postPath + "/kri/indicator/getIndicatorDimension", {
                indicatorId: newIndicatorId,
                kriVer: row.mainVer
            }, function (data) {
                var dimensionCol = new Array();
                if (data) {
                    indicatorDimensionData = data;
                    initQueryCondition(data);
                    $.each(data, function (i, n) {
                        var code = n.dimension.dimensionCode;
                        var name = n.dimension.dimensionName;
                        code = code.toLowerCase();
                        code = code.replace("_", "");
                        dimensionCol.push({field: code, title: name, width: 100, formatter: formatterDimensionData});
                    });
                }
                var params = $('#' + datagridId).datagrid('options').queryParams;
                params["_EQS_indicatorId"] = row.indicatorId;
                var columns = [dimensionCol.concat(datagridColumns)];
                $('#' + datagridId).datagrid({columns: columns});
                $('#' + datagridId).datagrid('reload');
            });
        }
    } else {
        oldIndicatorId = null;
        $('#' + datagridId).datagrid("loadData", {"total": 0, "rows": []});
    }
}

/*
function formatterJoinDimensionData(value,rowData){
	var field = this.field;
	for(var i=0;i<dimensionDatas.length;i++){
		if(dimensionDatas[i].id==rowData.indicatorValue[field]){
			return textAlignLeft(dimensionDatas[i].text);
		}
	}
}*/
function waringCountFormatter(value, rowData) {
    var startDate = "";
    var endDate = "";
    var d = "0";
    $.ajax({
        type: 'post', async: false, url: postPath + "/kri/waringResult/getWaringCount",
        data: {startDate: startDate, endDate: endDate, indicatorId: rowData.indicatorId},
        success: function (data) {
            d = data;
        }
    });
    return d;
}

function formatterDimensionData(value) {
    for (var i = 0; i < dimensionDatas.length; i++) {
        if (dimensionDatas[i].id == value) {
            return dimensionDatas[i].text;
        }
    }
}

function initQueryCondition(data) {
    var dimensionCon = '';
    $("#dimensionCondition").html("");
    var len = data.length;
    var num = len + 2;
    if (num <= 2) {
        $("#formdiv").css("height", 83);
        $("#tabdiv").css("padding-top", 85);
    } else if (num > 2 && num <= 4) {
        $("#formdiv").css("height", 111);
        $("#tabdiv").css("padding-top", 113);
    } else if (num > 4) {
        $("#formdiv").css("height", 139);
        $("#tabdiv").css("padding-top", 141);
    }
    $.each(data, function (i, n) {
        var code = n.dimension.dimensionCode;
        var name = n.dimension.dimensionName;
        code = code.toLowerCase();
        code = code.replace("_", "");
        if (i == 0)
            dimensionCon = '<tr>';
        else if (i % 2 == 0) {
            if (i == len - 1)
                dimensionCon += '</tr>';
            else
                dimensionCon += '</tr><tr>';
        }
        var colspan = 1;
        if (len % 2 == 1 && i == len - 1) {
            colspan = 3;
        }
        dimensionCon += '<td class="kTableLabel" style="width:15%;">' + name + '维度</td><td class="kTableValue" colspan="' + colspan + '"><input id="_EQS_' + code + '" name="_EQS_' + code + '" style="width: 198px;"/></td>';
    });
    $(dimensionCon).appendTo($("#dimensionCondition"));
    $.each(data, function (i, n) {
        var code = n.dimension.dimensionCode;
        var c = code.toLowerCase();
        c = c.replace("_", "");
        $('#_EQS_' + c).combotree({
            method: "get",
            multiple: true,
            url: postPath + "/kri/dimensionData/findByDimensionCode?dimensionCode=" + code,
            valueField: "id",
            textField: "text",
            editable: false
        });
    });
}

var indicatorResult = new Array();

function indicatorFilter(obj, keyCode, datagridID) {
    if (keyCode == "13" || keyCode == "17" || keyCode == "18" || keyCode == "20") return;
    indicatorResult.length = 0;
    var value = $.trim(obj.value);
    if (value == "") {
        $('#' + datagridID).datagrid("loadData", indicatorData);
    } else {
        $.each(indicatorData['rows'], function (i, n) {
            var code = n.indicatorCode + n.indicatorName;
            if (code.indexOf(value) != -1) {
                indicatorResult.push(n);
            }
        });
        var result = {"total": indicatorResult.length, "rows": indicatorResult};
        $('#' + datagridID).datagrid("loadData", result);
    }
}