/**
 * ldc所用格式化编码变名称的js
 */

//服务器发布地址，取risk路径
var strPath = window.top.location.pathname;
var postPath = strPath.substring(0, strPath.substring(1).indexOf('/') + 1);


//转化状态
function tansWfStatus(value) {
    return $.dict.getLabel("LDC_WF_STATUS", value);
}

//转化科目状态
function tansLossSubStatus(value) {
    return $.dict.getLabel("LDC_LOSS_SUB_STATUS", value);
}

//转化科目类型
function tansLossSubType(value) {
    return $.dict.getLabel("LDC_LOSS_SUB_TYPE", value);
}

//转化科目是否为损失
function tansLossSubIsloss(value) {
    return $.dict.getLabel("YES_OR_NO", value);
}

//事件类型
function eventTypeName(value) {
    var s = $.ajax({
        url: postPath + "/std/lossType/getLossTypeName?lossTypeCode=" + value,
        type: "GET",
        async: false,
        cache: false
    }).responseText;
    if ($.parseJSON(s).lossTypeName != null)
        return $.parseJSON(s).lossTypeName;
}

//单位名称
function tansUnitName(value) {
    var s = $.ajax({
        url: postPath + "/unit/getUnitAllName?unitCode=" + value,
        type: "GET",
        async: false,
        cache: false
    }).responseText;
    if ($.parseJSON(s).unitName != null)
        return $.parseJSON(s).unitName;
}

function getTypes(value) {
    return {'0': '上报', '1': '合并'}[value];
}
