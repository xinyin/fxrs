var strPath = window.top.location.pathname;
var postPath = strPath.substring(0, strPath.substring(1).indexOf('/') + 1);
var readOnly, formHref, win;
var space = 200;
var WIN_WIDTH = window.screen.width - space, WIN_HEIGHT = window.screen.height - space / 2;
var WIN_TOP = space / 6, WIN_LEFT = space / 2;

//维度数据
var dimensionDatas;
//收集方式
var collectPattern;
//基础数据状态
var statusData;
//数据频率
var periodCodeData;
//基础数据值状态
var valueStateData;
//维度信息
var dimensionInfo;
$.post(postPath + "/dict/get/DATAITEMVALUE_STATUS", function (data) {
    valueStateData = data;
});

/*$.post(postPath+"/fixedDict/PeriodKind",function(data){
	$("#periodCode").combobox("loadData",data);
	periodCodeData = data;
});
*/
$.post(postPath + "/fixedDict/DataitemStatus", function (data) {
    //$("#status").combobox("loadData",data);
    statusData = data;
});

$.post(postPath + "/fixedDict/CollectPattern", function (data) {
    //$("#collectPattern").combobox("loadData",data);
    collectPattern = data;
});

$.get(postPath + "/kri/dimensionData/findAll", function (data) {
    dimensionDatas = data;
});

function initDataitemList(dataitemGridId, childGridId, type) {
    var datagridColumns = new Array();
    datagridColumns.push({field: 'dataitemName', title: '数据项名称', width: 100});
    datagridColumns.push({field: 'timeDesc', title: '数据日期', width: 80});
    if (type == "collect") {
        datagridColumns.push({field: 'closeDate', title: '截止日期', width: 70, formatter: closeDateFormatter});
    }
    $('#' + dataitemGridId).datagrid({
        title: '基础数据项列表', //标题
        method: 'post',
        url: postPath + "/kri/dataitemValue/getDataitemValues?type=" + type, //数据来源
        singleSelect: true,
        rownumbers: true,
        fit: true, //高度
        idField: "dataitemValueId",
        columns: [datagridColumns],
        toolbar: [{
            text: '查看基础数据项',
            iconCls: 'icon-view',
            handler: function () {
                viewDataitem(dataitemGridId);
            }
        }],
        onLoadSuccess: function (data) {
            easyui.datagrid.headcenter();
            $(this).datagrid('clearSelections');
            //var rows = $(this).datagrid('getRows');
            if (data.total > 0) {
                $(this).datagrid('selectRow', 0);
                var r = $('#' + dataitemGridId).datagrid('getSelected');
                $("#dataitemCode").val(r.dataitemCode);
                $("#kriVer").val(r.kriVer);
                loadDatagridData(dataitemGridId, childGridId);
            }
        },
        onClickRow: function (rowIndex, rowData) {
            $("#dataitemCode").val(rowData.dataitemCode);
            $("#kriVer").val(rowData.kriVer);
            loadDatagridData(dataitemGridId, childGridId);
        },
        onDblClickRow: function () {
            viewDataitem(dataitemGridId);
        }
    });
}

function closeDateFormatter(v, rowData) {
    var format = "yyyy-MM-dd";
    var date = jQuery.date.parse(rowData.collectDate, format);
    var d = jQuery.date.dateAdd(date, 'd', parseInt(rowData.collectDatelimit));
    return jQuery.date.format(d, format);
}

//查看基础数据项
function viewDataitem(dataitemGridId) {
    var r = $('#' + dataitemGridId).datagrid('getSelected');
    if (r) {
        readOnly = true;
        formHref = postPath + '/kri/dataitem/edit/' + r.dataitemCode;
        if (win) {
            win.focus();
        } else {
            win = window.open(postPath + '/kri/dataitem/edit', "查看基础数据项", 'height=' + WIN_HEIGHT + ',width=' + WIN_WIDTH + ',top=' + WIN_TOP + ',left=' + WIN_LEFT + ',toolbar=no,menubar=no, resizable=no,location=no, status=no');
        }
    }
}

function loadDatagridData(dataitemGridId, datagridId) {
    var row = $('#' + dataitemGridId).datagrid("getSelected");
    if (row) {
        $.post(postPath + "/kri/dataitem/getDataitemDimension", {
            dataitemCode: row.dataitemCode,
            kriVer: row.kriVer
        }, function (data) {
            var dimensionCol = new Array();
            if (data) {
                dimensionInfo = data;
                initQueryCondition(data, "_EQS_");

                $.each(data, function (i, n) {
                    var code = n.dimension.dimensionCode;
                    var name = n.dimension.dimensionName;
                    code = code.toLowerCase();
                    code = code.replace("_", "");
                    dimensionCol.push({field: code, title: name, width: 150, formatter: formatterDimensionData});
                });
            }
            var params = $('#' + datagridId).datagrid('options').queryParams;
            params["_EQS_dataitemCode"] = row.dataitemCode;
            params["_EQS_timeId"] = row.timeId;
            var columns = [dimensionCol.concat(datagridColumns)];
            $('#' + datagridId).datagrid({columns: columns});
            $('#' + datagridId).datagrid('reload');
        });

    }
}

function loadDatagridDatas(dataitemCode, kriVer, datagridId) {
    $.post(postPath + "/kri/dataitem/getDataitemDimension", {
        dataitemCode: dataitemCode,
        kriVer: kriVer
    }, function (data) {
        var dimensionCol = new Array();
        if (data) {
            dimensionInfo = data;
            initQueryCondition(data, "_EQS_");
            $.each(data, function (i, n) {
                var code = n.dimension.dimensionCode;
                var name = n.dimension.dimensionName;
                code = code.toLowerCase();
                code = code.replace("_", "");
                dimensionCol.push({
                    field: code,
                    title: name,
                    align: "center",
                    width: 150,
                    formatter: formatterDimensionData
                });
            });
        }
        var params = $('#' + datagridId).datagrid('options').queryParams;
        params["_EQS_dataitemCode"] = dataitemCode;
        var columns = [dimensionCol.concat(datagridColumns)];
        $('#' + datagridId).datagrid({columns: columns});
        $('#' + datagridId).datagrid('reload');
    });
}

function formatterDimensionData(value) {
    for (var i = 0; i < dimensionDatas.length; i++) {
        if (dimensionDatas[i].id == value) {
            return dimensionDatas[i].text;
        }
    }
}

//查看处理过程
function handleViewBar() {
    return {
        text: '查看处理过程',
        iconCls: 'icon-view',
        handler: function () {
            var r = $('#tblDataitemValue').datagrid('getSelected');
            if (r) {
                openWindow("wDataitemValueEdit", "处理过程", postPath + "/kri/dataitemValue/toHandEdit?dataitemValueId=" + r.dataitemValueId,
                    {
                        inFrame: true, size: "", width: 850, height: 390,
                        windowArgs: {
                            formHref: postPath + '/kri/dataitemValue/edit/' + r.dataitemValueId,
                            dataitemCode: $("#dataitemCode").val(),
                            kriVer: $("#kriVer").val(),
                            update: true,
                            refreshList: query
                        }
                        /*windowArgs:{formHref:postPath+'/kri/dataitemValue/edit/'+r.dataitemValueId,readOnly:true}*/
                    });
            }
        }
    }
}

//查看基础数据值
function viewDataValueBar() {
    return {
        text: '查看基础数据值',
        iconCls: 'icon-edit',
        handler: function () {
            var r = $('#tblDataitemValue').datagrid('getSelected');
            if (r) {
                openWindow("wDataitemValueEdit", "查看基础数据值", postPath + "/kri/dataitemValue/edit",
                    {
                        inFrame: true, size: "", width: 750, height: 230,
                        windowArgs: {
                            formHref: postPath + '/kri/dataitemValue/edit/' + r.dataitemValueId,
                            dataitemCode: $("#dataitemCode").val(),
                            kriVer: $("#kriVer").val(),
                            readOnly: true,
                            refreshList: query
                        }
                    });
            }
        }
    }
}

//查看基础数据项
/*function viewDataitemBar(){
	return{
		text:'查看基础数据项',
		iconCls:'icon-edit',
		handler:function(){
			var r = $('#tblDataitemValue').datagrid('getSelected');    
			if (r){      
				readOnly=true;
				formHref=postPath+'/kri/dataitem/edit/'+r.dataitemCode;
				var win = window.open(postPath+'/kri/dataitem/edit',"查看数据项",'height='+WIN_HEIGHT+',width='+WIN_WIDTH+',top='+WIN_TOP+',left='+WIN_LEFT+',toolbar=no,menubar=no, resizable=no,location=no, status=no') ;
			}else{
				$.messager.alert('系统提示',"请选择你要查看的数据项记录。",'info');
				return;
			}
		}
	}
}   */
function initValueEditDimension(data, type) {
    var dimensionCon = '';
    $("#dimensionCondition").html("");
    var len = data.length;
    if (len <= 2) {
        $("#formdiv").css("height", 83);
        $("#tabdiv").css("padding-top", 85);
    } else if (len > 2 && len <= 4) {
        $("#formdiv").css("height", 111);
        $("#tabdiv").css("padding-top", 113);
    } else if (len > 4) {
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
        dimensionCon += '<td class="kTableLabel" style="width:15%;">' + name + '维度</td><td class="kTableValue" style="width:35%;" colspan="' + colspan + '"><input id="' + type + code + '" name="' + type + code + '" style="width: 198px;"/></td>';
    });
    $(dimensionCon).appendTo($("#dimensionCondition"));
}

function initQueryCondition(data, type) {
    initValueEditDimension(data, type);
    $.each(data, function (i, n) {
        var code = n.dimension.dimensionCode;
        var c = code.toLowerCase();
        c = c.replace("_", "");
        $('#' + type + c).combotree({
            method: "get",
            //multiple:true,
            //cascadeCheck:false,
            url: postPath + "/kri/dimensionData/findByDimensionCode?dimensionCode=" + code,
            valueField: "id",
            textField: "text",
            editable: false
        });
    });
}