//服务器发布地址，取risk路径
var strPath = window.top.location.pathname;
var postPath = strPath.substring(0, strPath.substring(1).indexOf('/') + 1);

/**
 * 新资本协议损失形态分类选择
 *
 * @param mult
 *               多选单选参数 true为多选，false为单选
 * @param rtnFunc
 *               弹出框操作后返回函数，可自己定义方法名
 */
function LossTypeDlg(mult, rtnFunc) {
    openWindow("wLossTypeDlg", "风险事件类型", postPath + "/std/selectLossType",
        {inFrame: true, size: "small", windowArgs: {rtnFunction: rtnFunc, mult: mult}});
}

/**
 * 控制措施字典选择
 *
 * @param mult
 *               多选单选参数 true为多选，false为单选
 * @param level
 *               树层级,需要展示到第几层
 * @param rtnFunc
 *               弹出框操作后返回函数，可自己定义方法名
 */

function controlDlg(mult, level, rtnFunc) {
    openWindow("wControlDlg", "控制措施字典选择", postPath + "/std/selectControl",
        {inFrame: true, size: "medium", windowArgs: {rtnFunction: rtnFunc, mult: mult}});
}

function controlDlgModal(mult, level, rtnFunc) {
    openWindow("wControlDlg", "控制措施字典选择", "/" + postPath + "/std/selectControl",
        {inFrame: true, size: "medium", windowArgs: {rtnFunction: rtnFunc, mult: mult}});
}

/**
 * 风险事件库
 * @param mult
 *               多选单选参数 true为多选，false为单选
 * @param level
 *               树层级,需要展示到第几层
 * @param riskEventCallback
 *               弹出框操作后返回函数，可自己定义方法名
 */

function riskEventDlg(mult, level, riskEventCallback) {
    openWindow("wRiskEventDlg", "风险事件类型", postPath + "/std/riskEventDlg",
        {inFrame: true, size: "medium", windowArgs: {result: riskEventCallback, mul: mult, level: level}});
}

function riskEventDlgModal(mult, level, riskEventCallback) {
    openWindow("wRiskEventDlg", "风险事件类型", "/" + postPath + "/std/riskEventDlg",
        {inFrame: true, size: "medium", windowArgs: {result: riskEventCallback, mul: mult, level: level}});
}


/**
 * 风险事件类型库
 * @param mult
 *               多选单选参数 true为多选，false为单选
 * @param level
 *               树层级,需要展示到第几层
 * @param riskEventCallback
 *               弹出框操作后返回函数，可自己定义方法名
 */

function riskTypeDlg(mult, level, riskTypeCallback) {
    openWindow("wRiskTypeDlg", "风险事件类型", postPath + "/std/lossType",
        {inFrame: true, size: "medium", windowArgs: {result: riskTypeCallback, mul: mult, level: level}});
}

function riskTypeDlgModal(mult, level, riskTypeCallback) {
    openWindow("wRiskTypeDlg", "风险事件类型", "/" + postPath + "/std/lossType",
        {inFrame: true, size: "medium", windowArgs: {result: riskTypeCallback, mul: mult, level: level}});
}


/**
 * 风险因子选择
 *
 * @param size
 *               设置弹出框的大小
 * @param mult
 *               多选单选参数 true为多选，false为单选
 * @param getSelectValuesc
 *               弹出框操作后返回函数，可自己定义方法名
 */

function riskFactarDlg(title, riskFactorCallback, level) {
    openWindow("wriskFactarDlg", title, postPath + "/std/riskFactorTree",
        {inFrame: true, size: "medium", windowArgs: {result: riskFactorCallback, title: title, level: level}});
}

function riskFactarDlgModal(title, riskFactorCallback, level) {
    openWindow("wriskFactarDlg", title, "/" + postPath + "/std/riskFactorTree",
        {inFrame: true, size: "medium", windowArgs: {result: riskFactorCallback, title: title, level: level}});
}

/**
 * 部门选择
 * @param level    显示层级
 * @param callback    回调函数
 * @param type    部门类型  nanhai_head:只有总行机关   nanhai:南海部门和支行一级  nanhai_branch:南海所有支行   null:南海所有组织机构
 * @param mult    多选单选参数 'true'为多选，'false'为单选 mult为字符串类型   默认为单选
 * @param defaultVal    多选时代的默认值
 */
function deptDlg(level, callback, type, mult, defaultVal) {
    openWindow("wUnitTree", "部门选择", contextPath + "/unit/toUnitDlg",
        {
            inFrame: true,
            size: "medium",
            windowArgs: {rtnFunction: callback, type: type, mult: mult, defaultVal: defaultVal}
        });
}

function deptDlgModal(level, callback, type, mult, defaultVal) {
    openWindow("wUnitTree", "部门选择", "/" + contextPath + "/unit/toUnitDlg",
        {
            inFrame: true,
            size: "medium",
            windowArgs: {rtnFunction: callback, type: type, mult: mult, defaultVal: defaultVal}
        });
}


/**
 * 流程选择
 * @param level    显示层级
 * @param callback    回调函数
 * @param type    流程类型  nanhai_head:null:所有流程 nanhai_head:enabled 所有启用流程
 * @param mult    多选单选参数 'true'为多选，'false'为单选 mult为字符串类型   默认为单选
 * @param defaultVal    多选时的默认值
 */
function processDlg(level, callback, type, mult, planCode) {
    openWindow("wProcessTree", "流程选择", contextPath + "/rcsa/planProcess/toProcessDlg",
        {
            inFrame: true,
            size: "medium",
            windowArgs: {rtnFunction: callback, type: type, mult: mult, planCode: planCode}
        });
}

function processDlgModal(level, callback, type, mult, defaultVal) {
    openWindow("wProcessTree", "流程选择", "/" + contextPath + "/rcsa/planProcess/toProcessDlg",
        {
            inFrame: true,
            size: "medium",
            windowArgs: {rtnFunction: callback, type: type, mult: mult, defaultVal: defaultVal}
        });
}

/**
 * 部门选择
 * @param level    显示层级
 * @param callback    回调函数
 * @param type    部门类型  nanhai_head:只有总行机关   nanhai:南海部门和支行一级    null:南海所有组织机构
 * @param mult    多选单选参数 'true'为多选，'false'为单选 mult为字符串类型   默认为单选
 * @param defaultVal    多选时代的默认值
 */

/*function collectDeptDlg(level,callback,type,mult,defaultVal){
	openWindow("wUnitTree","部门选择",contextPath+"/unit/toUnitDlg",
  					{inFrame:true,size:"medium",windowArgs:{rtnFunction:callback,type:type,mult:mult,defaultVal:defaultVal}});
}

function collectDeptDlgModal(level,callback,type,mult,defaultVal){
		openWindow("wUnitTree","部门选择","/"+contextPath+"/unit/toUnitDlg",
	  					{inFrame:true,size:"medium",windowArgs:{rtnFunction:callback,type:type,mult:mult,defaultVal:defaultVal}});
	}*/


/**
 *
 * 重大事件类型
 *
 * @param level
 *                树层级,需要展示到第几层
 * @param callback
 *                弹出框操作后返回函数，可自己定义方法名
 */

function losseventDlg(level, callback) {
    openWindow("wLossEventTree", "重大事件选择", contextPath + "/std/ldcEventtree",
        {inFrame: true, size: "medium", windowArgs: {rtnFunction: callback, level: level}});
}

function losseventModal(level, callback) {
    openWindow("wLossEventTree", "重大事件选择", "/" + contextPath + "/std/ldcEventtree",
        {inFrame: true, size: "medium", windowArgs: {rtnFunction: callback, level: level}});
}


/**
 *
 * 业务条线选择
 *
 * @param level
 *                树层级,需要展示到第几层
 * @param callback
 *                弹出框操作后返回函数，可自己定义方法名
 */

function bizlineDlg(level, callback) {
    openWindow("wBizlineTree", "业务条线选择", contextPath + "/std/BizLinetree",
        {inFrame: true, size: "medium", windowArgs: {rtnFunction: callback}});
}

function bizlineModal(level, callback) {
    openWindow("wBizlineTree", "业务条线选择", "/" + contextPath + "/std/bizlinetree",
        {inFrame: true, size: "medium", windowArgs: {rtnFunction: callback}});
}


/**
 * 初始风险事件发生频率列表
 */
function initEventFreq(tagId) {
    $('#' + tagId).combogrid({
        idField: 'id',
        textField: 'calcValue',
        panelWidth: 385,
        panelHeight: 210,
        url: postPath + '/std/getAllEventFreq',
        columns: [[
            {field: 'text', title: '发生频率', width: 100},
            {field: 'calcValue', title: '计算值', width: 100},
            {field: 'des', title: '描述', width: 180}
        ]]
    });
}

/**
 * 初始风险事件严重程度列表
 */
function initEventSeverity(tagId) {
    $('#' + tagId).combogrid({
        panelWidth: 400,
        panelHeight: 200,
        idField: 'id',
        textField: 'calcValue',
        url: postPath + '/std/getAllEventSeverity',
        columns: [[
            {field: 'text', title: '预计直接损失', width: 188},
            {field: 'calcValue', title: '损失计算值', width: 188}
        ]]
    });
}

/**
 * 初始风险容忍度列表
 */
function initTolerance(tagId) {
    $('#' + tagId).combogrid({
        panelWidth: 435,
        panelHeight: 110,
        idField: 'id',
        textField: 'text',
        url: postPath + '/std/getAllTolerance',
        onChange: changeColor,
        columns: [[
            {field: 'text', title: '颜色块', width: 80},
            {field: 'color', title: '颜色值', width: 150, formatter: forsetValue},
            {field: 'des', title: '描述', width: 200}
        ]],
        onLoadSuccess: function () {
            changeColor();
        }
    });
}

//加载颜色值
function forsetValue(value) {
    if (value) {
        var s = '<div style="width:100%;background:#fff;border:1px solid #ccc">' +
            '<div style="background:' + value + '">' + value + '</div></div>';
        return s;
    } else {
        return '';
    }
}

//当combogrid值发生改变时加载颜色值
function changeColor() {
    var grid = $('#tolerance').combogrid('grid');
    var row = grid.datagrid("getSelected").color;
    //获得tolerance1 combogrid的文本
    var textb = $('#tolerance').combogrid("textbox");
    textb.css("background", row);
}

/**
 * 初始页面时添加多选框项
 * @param divId
 *             页面放置多选框位置
 * @param url
 *             多选框项值获取url（例：/std/getAllEventEffect，最前面必须加'/'）
 * @param name
 *             input属性name,提交值需要
 * @param ids
 *             默认选中的标签id
 */
function creatCheckBoxList(divId, url, name, ids) {
    $.ajax({
        type: "POST",
        url: postPath + url,
        dataType: "json",
        success: function (data) {
            var pTag = $('#' + divId);
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    var divTag = $("<div style='float: left'></div>");
                    var inputTag = $("<input></input>");
                    inputTag.attr("type", "checkbox");
                    inputTag.attr("style", "width:30px");
                    inputTag.attr("id", data[i].id);
                    inputTag.attr("name", name);
                    inputTag.attr("value", data[i].id);
                    // 设置默认选中项
                    if (null != ids && ids.length > 0) {
                        for (var j = 0; j < ids.length; j++) {
                            if (data[i].id == ids[j].id) {
                                inputTag.attr("checked", true);
                            }
                        }
                    }
                    divTag.append(inputTag);
                    divTag.append(data[i].text);
                    pTag.append(divTag);
                }
            }
        }
    });
}

/**
 * 字典项选择
 * @param divId
 *             页面放置多选框位置
 * @param url
 *             多选框项值获取url（例：/std/getAllEventEffect，最前面必须加'/'）
 * @param mult
 *             多选标识
 * @param dictCode
 *             字典项名称，对应DICTITEM表中的 DICT_CODE字段
 */
function getDictItems(mult, dictItemSelected, dictCode) {
    openWindow("wDictItemsDlg", "字典项选择", postPath + "/std/getDictItems?dictCode=" + dictCode + "",
        {inFrame: true, width: 600, height: 500, size: "", windowArgs: {result: dictItemSelected, mult: mult}});
}

function getDictItemsModal(mult, dictItemSelected, dictCode) {
    openWindow("wDictItemsDlg", "字典项选择", "/" + postPath + "/std/getDictItems?dictCode=" + dictCode + "",
        {inFrame: true, width: 600, height: 500, size: "", windowArgs: {result: dictItemSelected, mult: mult}});
}

/**
 * 外规选择
 * @param divId
 *             页面放置多选框位置
 * @param url
 *             多选框项值获取url（例：/std/getAllEventEffect，最前面必须加'/'）
 * @param mult
 *             多选标识
 * @param dictCode
 *             字典项名称，对应DICTITEM表中的 DICT_CODE字段
 */
function getPubLawList(mult, dictItemSelected) {
    openWindow("wPubLawListDlg", "外规信息选择", postPath + "/reg/inter/publicLaw/list",
        {inFrame: true, width: 600, height: 500, size: "", windowArgs: {result: dictItemSelected, mult: mult}});
}

function getPubLawListModal(mult, dictItemSelected) {
    openWindow("wPubLawListDlg", "外规信息选择", "/" + postPath + "/reg/inter/publicLaw/list",
        {inFrame: true, width: 600, height: 500, size: "", windowArgs: {result: dictItemSelected, mult: mult}});
}

/**
 * 内规选择
 * @param divId
 *             页面放置多选框位置
 * @param url
 *             多选框项值获取url（例：/std/getAllEventEffect，最前面必须加'/'）
 * @param mult
 *             多选标识
 * @param dictCode
 *             字典项名称，对应DICTITEM表中的 DICT_CODE字段
 */
function getInnerLawList(mult, rtnFun) {
    openWindow("wInnerLawListDlg", "内规信息选择", postPath + "/reg/inter/innerLaw/list",
        {inFrame: true, width: 600, height: 500, size: "", windowArgs: {result: rtnFun, mult: mult}});
}

function getInnerLawListModal(mult, rtnFun) {
    openWindow("wInnerLawListDlg", "内规信息选择", "/" + postPath + "/reg/inter/innerLaw/list",
        {inFrame: true, width: 600, height: 500, size: "", windowArgs: {result: rtnFun, mult: mult}});
}


/**
 * 职位类别树 接口
 *
 * @param rtnFunc
 *               弹出框操作后返回函数，可自己定义方法名
 */
function getPost(rtnFun) {
    openWindow("wPostTree", "职位类别树", postPath + "/std/busposition/toPostTreeDlg",
        {inFrame: true, modal: true, size: "small", windowArgs: {rtnFunction: rtnFun}});
}

/**
 * 职位及职位类别 接口
 * @param mult
 *              多选单选参数 true为多选，false为单选
 * @param auth
 *              查看权限 "0"全部  "1" 初期化登录者所属机构的职位信息
 * @param rtnFunc
 *               弹出框操作后返回函数，可自己定义方法名
 */
function getPostPosition(mult, auth, rtnFun) {
    openWindow("wPostBusPositionTree", "选择职位类别", postPath + "/std/busposition/toPostPositionDlg",
        {
            inFrame: true,
            modal: true,
            size: "", width: 800, height: 600,
            windowArgs: {mult: mult, auth: auth, rtnFunction: rtnFun}
        });
}

/**
 * 流程清单选择
 * @param mult
 *               多选单选参数 true为多选，false为单选
 * @param level
 *               树层级,需要展示到第几层
 * @param processListCallback
 *               弹出框操作后返回函数，可自己定义方法名
 */
function processListDlg(mult, level, processListCallback) {
    openWindow("wprocessListTree", "流程清单", postPath + "/bpa/processList/processListTree", {
        inFrame: true, size: "medium", windowArgs: {result: processListCallback, mul: mult, level: level}
    });

}

function processListDlgModal(mult, level, processListCallback) {
    openWindow("wprocessListTree", "流程清单", "/" + postPath + "/bpa/processList/processListTree", {
        inFrame: true, size: "medium", windowArgs: {result: processListCallback, mul: mult, level: level}
    });
}

