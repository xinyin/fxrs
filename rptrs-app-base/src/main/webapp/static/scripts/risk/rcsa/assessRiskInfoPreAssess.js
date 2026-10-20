jQuery.ajaxSetup({
    cache: false
});


/*获取页面参数与字典*/
$.cache.cache('custom', contextPath + "/std/getControlList", 'STD_CONTROL_TYPE');
$.cache.cache('dict', 'RCSA_RISK_FREQ');
$.cache.cache('dict', 'RCSA_RISK_FINANCE');
$.cache.cache('dict', 'RCSA_RISK_STD');
$.cache.cache('dict', 'RCSA_RISK_SEVERITY');
$.cache.cache('dict', 'RCSA_CTRL_OVERALL');
$.cache.cache('dict', 'RCSA_RISK_BE_RESAVE');
$.cache.cache('dict', 'RCSA_CTRL_DESIGN');
$.cache.cache('dict', 'RCSA_CTRL_EXECUTE');
$.cache.cache('fixedDict', 'ControlMode');

/*声明风险评估对象*/
var re = $.risk.createRiskEval();

$(function () {

    using('messager');
    using('combobox', function () {

        loadCombo('fixedRiskFreq', 'RCSA_RISK_FREQ');
        loadCombo('fixedRiskFinance', 'RCSA_RISK_FINANCE');
        loadCombo('fixedRiskCustomer', 'RCSA_RISK_STD');
        loadCombo('fixedRiskRenown', 'RCSA_RISK_STD');
        loadCombo('fixedRiskSupervise', 'RCSA_RISK_STD');
        loadCombo('fixedRiskEmployee', 'RCSA_RISK_STD');
        loadCombo('fixedRiskOrderOfSeverity', 'RCSA_RISK_SEVERITY');
        loadCombo('ctrlOverAllGrade', 'RCSA_CTRL_OVERALL');
        loadCombo('restRiskFreq', 'RCSA_RISK_FREQ');
        loadCombo('restRiskFinance', 'RCSA_RISK_FINANCE');
        loadCombo('restRiskCustomer', 'RCSA_RISK_STD');
        loadCombo('restRiskRenown', 'RCSA_RISK_STD');
        loadCombo('restRiskSupervise', 'RCSA_RISK_STD');
        loadCombo('restRiskEmployee', 'RCSA_RISK_STD');
        loadCombo('restRiskOrderOfSeverity', 'RCSA_RISK_SEVERITY');
        loadCombo('restRiskBeResave', 'RCSA_RISK_BE_RESAVE');

        /*绑定固定风险评估项*/
        re.bindFixed('fixedRiskFreq', 'fixedRiskFinance', 'fixedRiskCustomer', 'fixedRiskRenown'
            , 'fixedRiskSupervise', 'fixedRiskEmployee', 'fixedRiskOrderOfSeverity');

        /*绑定剩余风险评估项*/
        re.bindRest('restRiskFreq', 'restRiskFinance', 'restRiskCustomer', 'restRiskRenown'
            , 'restRiskSupervise', 'restRiskEmployee', 'restRiskOrderOfSeverity');

        using('combobox', function () {
            $('#restRiskBeResave').combobox({
                onSelect: function (item) {
                    var $this = $(this);
                    var restSeverity = $('#restRiskOrderOfSeverity').combobox('getValue');
                    var selectValue = $this.combobox('getValue');
                    if (!restSeverity) {
                        $.messager.alert('提示', '尚未确定剩余风险严重度', 'info', function () {
                            $this.combobox('setValue', '');
                        });
                        return;
                    }

                    if (selectValue == '3' && (restSeverity == '4' || restSeverity == '5')) {
                        $.messager.alert('提示', '请选择合适的风险可接受程度', 'info', function () {
                            $this.combobox('setValue', '');
                        });
                        return;
                    }

                    if (selectValue == '1') {
                        $.messager.alert('提示', '风险可接受程度为【不可接受】，请填写改进建议', 'info');
                    }
                }
            });
        });

        using('form', function () {
            loadForm();
            using('datagrid', function () {
                loadDatagrid();
            });

        });
    });

    using('linkbutton', function () {
        $('#btnSave').linkbutton({
            iconCls: 'icon-save'
        });
        $('#btnCancel').linkbutton({
            iconCls: 'icon-cancel'
        });
    });

    $("#btnRiskReasonType").bind('click', function () {
        riskFactarDlg(false, function (data) {
            var idStr = "";
            var nameStr = "";
            for (var i = 0; i < data.length; i++) {
                if (idStr != "") {
                    nameStr += ",";
                }
                idStr += data[i].id;
                nameStr += data[i].name + "\n";
            }
            $("#riskReasonTypeText").val(nameStr);
            $("#riskReasonType").val(idStr);
            $("#riskReasonTypeText").validatebox('validate');
        });
    });

    using('tabs', function () {
        $('#riskAssessTabs').tabs({
            fit: true,
            border: false,
            onSelect: function (title) {
                if (title == '历史评估结果') {
                    $('#ifrPreAssessRiskHis').attr('src', contextPath + "/rcsa/assessRiskInfo/preAssessRiskHis?id=" + $('#id').val());
                }
            }
        });
    });
});

//事件类型回调函数
function rtnRiskType(data) {
    //清空风险因子进行重置
    $("#riskEventTypeText").val("");
    $("#riskEventType").val("");
    var riskFactorsIdStr = "";
    var riskFactorsNameStr = "";

    for (var i = 0; i < data.length; i++) {
        if (riskFactorsIdStr != "") {
            riskFactorsIdStr += ",";
        }
        riskFactorsIdStr += data[i].id;
        riskFactorsNameStr += data[i].name + "\n";
    }
    $("#riskEventTypeText").val(riskFactorsNameStr);
    $("#riskEventType").val(riskFactorsIdStr);
}

function loadCombo(id, code, opts, filters) {
    var _args = {
        data: $.cache.getCache(code, filters),
        'panelHeight': 'auto',
        'textField': 'name',
        'valueField': 'value',
        'multiple': false,
        'editable': false,
        'disabled': false
    };
    _args = $.extend({}, _args, opts);
    $('#' + id).combobox(_args);
}


function loadTabs() {
    $('#riskAssessTabs').tabs({
        fit: true,
        border: true,
        onSelect: function (title) {
            if (title == '历史评估结果') {
                $('#ifrPreAssessRiskHis').attr('src', contextPath + "/rcsa/assessRiskInfo/preAssessRiskHis?id=" + $('#id').val());
            }
        }
    });
    $('#riskAssessTabs').tabs('add', {
        title: '历史评估结果',
        content: '<div title="历史评估结果" ><iframe id="ifrPreAssessRiskHis" name="ifrPreAssessRiskHis" scrolling="no" frameborder="0" style="width: 100%; height: 100%;"></iframe></div>',
        closable: false
    });
    $('#riskAssessTabs').tabs('select', '风险评估');
}

function loadDatagrid() {
    $('#tblAssessRiskCtrl').datagrid(
        {
            title: '控制评估列表', // 标题
            method: 'post',
            url: contextPath + "/rcsa/assessCtrlInfo/query.json", // 数据来源
            pagination: true, // 显示分页
            rownumbers: true, // 显示行号
            singleSelect: true,
            queryParams: {
                "_EQS_assessProcessId": $('#assessProcessId').val(),
                "_EQS_assessRiskId": $('#id').val()
            },
            height: 277, // 高度
            toolbar: [
                {
                    text: '控制评估',
                    iconCls: 'icon-edit',
                    handler: function () {
                        var r = $('#tblAssessRiskCtrl')
                            .datagrid('getSelected');
                        if (r) {
                            var processName = $("#processName")
                                .val();
                            openWindow(
                                "wAssessCtrlInfoEdit",
                                "控制评估",
                                contextPath + "/rcsa/assessCtrlInfo/edit?id=" + r.id,
                                {
                                    inFrame: true,
                                    size: null,
                                    width: 800,
                                    height: 560,
                                    windowArgs: {
                                        selectCtrlHis: 'part',
                                        processName: processName,
                                        controlTypeData: $.cache.getCache('STD_CONTROL_TYPE'),
                                        formHref: contextPath + '/rcsa/assessCtrlInfo/edit/' + r.id,
                                        refreshList: ctrlReload
                                    }
                                });
                        } else {
                            $.messager.alert('系统提示',
                                "请选择你要修改的记录。", 'info');
                            return;
                        }
                    }
                },
                {
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        var processName = $("#processName").val();
                        // var assessRiskDesc =
                        // $('#riskDesc').val();assessRiskDesc:assessRiskDesc,

                        openWindow(
                            "wAssessCtrlInfoEdit",
                            "新增控制",
                            contextPath + "/rcsa/assessCtrlInfo/addFromRiskAssess?assessProcessId=" + $('#assessProcessId').val() + "&assessRiskId=" + $('#id').val(),
                            {
                                inFrame: true,
                                size: null,
                                width: 800,
                                height: 600,
                                windowArgs: {
                                    processName: processName,
                                    flag: 'riskAdd',
                                    assessProcessId: $('#assessProcessId').val(),
                                    assessRiskId: $('#id').val(),
                                    refreshList: ctrlReload
                                }
                            });
                    }
                },
                {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        var row = $('#tblAssessRiskCtrl')
                            .datagrid('getSelected');

                        if (row) {
                            var delMsg = row.ctrlCode;
                            var sourceType = row.sourceType;
                            if (sourceType && sourceType.trim() != '2') {
                                $.messager.alert('系统提示',
                                    "不是本次评估期新增的控制措施不可以删除！",
                                    'info');
                                return;
                            }

                            $.messager.confirm('系统提示', '确定要删除【' + delMsg + '】吗？', function (result) {
                                if (result) {
                                    $.post(contextPath + '/rcsa/assessCtrlInfo/delete?id=' + row.id, function (data) {
                                        $.messager.alert('系统提示', data.msg, 'info', function () {
                                            if (data.success)
                                                $('#tblAssessRiskCtrl').datagrid('reload');
                                        });
                                    });
                                }
                            });
                        }
                    }
                }],
            onLoadSuccess: function () {
                $('#tblAssessRiskCtrl').datagrid('clearSelections');
                var rows = $('#tblAssessRiskCtrl').datagrid('getRows');
                if (rows.length > 0) {
                    $('#tblAssessRiskCtrl').datagrid('selectRow', 0);
                }
            }
        });
}

function loadForm() {
    $('body').css('visibility', 'visible');

    var args = getWindowArgs('wAssessRiskInfoEdit');
    if (args.readOnly) {
        $("form input").attr("readOnly", true);
        $('#btnSave').css('display', 'none');
    }

    if (args.formHref) {
        $('#assessRiskInfoForm').form({
            onLoadSuccess: function (data) {
                $('#id').attr('readonly', 'readonly');
                $("#riskReasonTypeText").val(fmtRiskFactor($("#riskReasonType").val(), args.riskFactorData));
                $("#riskEventTypeText").val(fmtRiskEvent($("#riskEventType").val(), args.riskEventData));

                /*根据加载的值设置风险严重程度*/
                re.setFixedSeverity();
                re.setRestSeverity();

//				using('tabs', function(){
//					loadTabs();
//				});

            }
        }).form('load', args.formHref);
    }
}


function fmtControlDesign(value, rowData) {
    return $.cache.getLabel('RCSA_CTRL_DESIGN', value);
    /*
     * var designName = value; $.each(controlDesignData,function(idx,ctrl){
     * if(value == ctrl.itemValue){ designName = ctrl.itemLabel; return false; }
     * }); return designName;
     */
};

function fmtControlExecute(value, rowData) {
    return $.cache.getLabel('RCSA_CTRL_EXECUTE', value);
    /*
     * var execName = value; $.each(controlExecuteData,function(idx,ctrl){
     * if(value == ctrl.itemValue){ execName = ctrl.itemLabel; return false; }
     * }); return execName;
     */
};

function fmtCtrlType(value, rowData) {
    return $.cache.getLabel('STD_CONTROL_TYPE', value, 'controlName', 'controlCode');
    /*var typeName = value;
    $.each(controlTypeData, function(idx, type) {
        if (value == type.controlCode) {
            typeName = type.controlName;
            return false;
        }
    });
    return typeName;*/
};

function fmtCtrlMode(value, rowData) {
    return $.cache.getLabel('ControlMode', value);
    /*
     * var modeName = value; $.each(controlModeData,function(idx,mode){ if(value ==
     * mode.value){ modeName = mode.name; return false; } }); return modeName;
     */
};

function fmtCtrlOverAll(value, rowData) {
    return $.cache.getLabel('RCSA_CTRL_OVERALL', value);
    /*
     * var name = value; $.each(controlOverAllData,function(idx,ctrl){ if(value ==
     * ctrl.itemValue){ name = ctrl.itemLabel; return false; } }); return name;
     */
};

function ctrlReload() {
    $('#tblAssessRiskCtrl').datagrid('reload');
}

function btnSave() {
    var r = $('#assessRiskInfoForm').form('validate');
    if (!r) {
        return false;
    }

    var flag = re.validate('fixedRiskFreq', 'fixedRiskFinance', 'fixedRiskCustomer', 'fixedRiskRenown'
        , 'fixedRiskSupervise', 'fixedRiskEmployee', 'fixedRiskOrderOfSeverity',
        'restRiskFreq', 'restRiskFinance', 'restRiskCustomer', 'restRiskRenown'
        , 'restRiskSupervise', 'restRiskEmployee', 'restRiskOrderOfSeverity', 'restRiskBeResave');
    if (!flag) {
        return;
    }

    $.post(contextPath + "/rcsa/assessRiskInfo/save", $("#assessRiskInfoForm")
        .serializeArray(), function (data) {
        $.messager.alert('系统提示', data.msg, 'info', function () {
            if (data.success)
                getWindowArgs('wAssessRiskInfoEdit').refreshList();
            closeWindow("wAssessRiskInfoEdit");
        });
    });
}

function fmtRiskFactor(value, data) {
    var name = value;
    $.each(data, function (idx, type) {
        if (value == type.riskFactorCode) {
            name = type.riskFactorName;
            return false;
        }
    });
    return name;
};

function fmtRiskEvent(value, data) {
    var name = value;
    $.each(data, function (idx, type) {
        if (value == type.riskEventCode) {
            name = type.riskEventName;
            return false;
        }
    });
    return name;
};

function fmtBigText(value) {
    if (!value) return "";
    return "<span title='" + value + "' style='width:100%;height:100%;'>" + value + "</span>";
}