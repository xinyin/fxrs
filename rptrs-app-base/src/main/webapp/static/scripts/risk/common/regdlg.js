//合规管理的对外接口
/**
 * 选择内规，固定为单选
 * @param rtnFunc 回调函数
 */
function innerLawSelect(rtnFunc) {
    openWindow("wInnerLawGrid", "制度", contextPath + "/reg/innerLaw/innerLawSelect",
        {
            inFrame: true,
            modal: true,
            size: "custom", height: 550, width: 950,
            windowArgs: {rtnFunction: rtnFunc}
        }
    );

}

/**
 * 选择内规，固定为多选
 * @param rtnFunc 回调函数
 */
function innerLawSelect4Process(rtnFunc, type, processId) {
    openWindow("wInnerLawGrid", "制度", contextPath + "/reg/innerLaw/innerLawSelect4Process?type=" + type + "&processId=" + processId,
        {
            inFrame: true,
            modal: true,
            size: "custom", height: 550, width: 950,
            windowArgs: {rtnFunction: rtnFunc, onClose: winClose}
        }
    );
}

function innerLawSelect4Applyform(rtnFunc, type, applyformId) {
    openWindow("wInnerLawGrid", "制度", contextPath + "/reg/innerLaw/innerLawSelect4Applyform?type=" + type + "&applyformId=" + applyformId,
        {
            inFrame: true,
            modal: true,
            size: "custom", height: 550, width: 900,
            windowArgs: {rtnFunction: rtnFunc, onClose: winClose}
        }
    );
}