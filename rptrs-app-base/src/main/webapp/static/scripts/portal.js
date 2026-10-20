//document.write("<script   language='text/javascript'  src='index.js'><\/script>");
/**
 * 刷新待办任务
 */
function fetchTodoTask(e) {
    $(e).hide();
    $.todo.render('#mytasks', contextPath + '/todo/my?r=' + new Date(), e);
}

function refreshAndFetchTodoTask() {
    $.todo.render('#mytasks', contextPath + '/todo/refresh?r=' + new Date());
}


/**
 * 查看流程
 */
function viewProcess(processId, processCode, wfId, processName) {
    openMainTabs('wProcessInfoView', '流程查看-' + processName,
        contextPath + "/bpa/bpaFrames/processInfoFrames?saved=yes&optype=view&processId=" + processId + "&processCode=" + processCode
        + "&wfId=" + (wfId ? wfId : ''), true);
}

/**
 * 查看合同审查申请
 */
function viewContractApply(contractCode) {
//	alert(contractCode);
    openWindow('wContractDto', '查看合同审查申请',
        contextPath + "/ctm/contract/view/" + contractCode
        , {inFrame: true, model: true, size: 'custom', width: 900, height: 560, windowArgs: {}});
}

/**
 * 查看整改计划
 */
function viewImprovePlan(improvePlanCode) {
    openWindow('wImprovePlanSpeedEdit', '查看整改计划'
        , contextPath + "/acp/improvePlanFrames/getImprovePlan?planCode=" + improvePlanCode + "&optype=view&rand=" + Math.random(10000000)
        , {inFrame: true, size: '', width: 900, height: 550, windowArgs: {}});
}

/**
 * 查看内部制度
 */
function viewInnerLaw(lawId, lawCode) {
    openWindow('wInnerLaw', '查看制度'
        , contextPath + "/reg/regFrames/getInnerLawFrames?saved=yes&lawCode=" + lawCode + "&lawId=" + lawId + "&optype=view&rand=" + Math.random(10000000)
        , {inFrame: true, size: '', width: 900, height: 560, windowArgs: {}});
}


/**
 * 查看违规积分详情
 */
function viewFoulScore(formType, applyformId, appealformId) {
    if (formType == 'APPLY') {
        openWindow('wScoreApplyformEdit', '违规积分认定'
            , contextPath + "/fps/scoreApplyform/getApplyformFrames?saved=yes&applyformId=" + applyformId + "&optype=confirmView"
            , {
                inFrame: true, size: '', width: 900, height: 560, windowArgs: {
                    op: 'view', readOnly: true,
                    formHref: contextPath + "/fps/scoreApplyform/edit/" + applyformId + ".json"
                }
            });
    }

    if (formType == 'APPEAL') {
        openWindow('wScoreAppealformEdit', '违规积分复议'
            , contextPath + "/fps/scoreAppealformApprove/getAppealformFrames?appealformId=" + appealformId + "&applyformId=" + applyformId + "&optype=approve"
            , {inFrame: true, size: '', width: 850, height: 510, windowArgs: {readOnly: true}});
    }

}


/**
 * 查看文档
 */
function viewDoc(docId) {
    openWindow("wDocEdit", "文档查看", contextPath + "/bas/doc/edit?opType=view&id=" + docId + "&wfId=", {
        inFrame: true,
        size: null,
        width: 600,
        height: 350,
        windowArgs: {formHref: contextPath + '/bas/doc/edit/' + docId}
    });
}


/**
 * 查看RCSA评估问卷
 */
function viewAssessRiskInfo(assessRiskId, processId) {

    openMainTabs('wAssessRiskInfoView', '查看评估问卷',
        contextPath + "/rcsa/assessProcessInfoFrames/getFrames?optype=view&id=" + assessRiskId + "&processId=" + processId, true
    );
}


/**
 * 查看KRI指标详情
 */
function viewIndicatorInfo(indicatorId) {
    openWindow('wIndicatorInfoFrames', '查看指标'
        , contextPath + "/kri/indicatorInfoFrames/getFrames?indicatorId=" + indicatorId + "&optype=view&save=yes"
        , {inFrame: true, size: '', width: 900, height: 500, windowArgs: {}});

}

/**
 * 查看KRI指标当前值详细信息
 */
function viewIndicatorMonitorNow(indicatorId, nowIndicatorValueId) {
    openWindow('wIndicatorMonitorFrames', '指标监控'
        , contextPath + "/kri/indicatorMonitor/iframe?indicatorId=" + indicatorId + "&indicatorValueId=" + nowIndicatorValueId
        , {inFrame: true, size: '', width: 1000, height: 500, windowArgs: {}});

}

/**
 * 查看KRI指标上期值详细信息
 */
function viewIndicatorMonitorBefore(indicatorId, beforeIndicatorValueId) {
    openWindow('wIndicatorMonitorFrames', '指标监控'
        , contextPath + "/kri/indicatorMonitor/iframe?indicatorId=" + indicatorId + "&indicatorValueId=" + beforeIndicatorValueId
        , {inFrame: true, size: '', width: 1000, height: 500, windowArgs: {}});

}

/**
 * 查看监督检查记录详细信息
 */
function viewOuterInspectRecord(recordId) {
    openWindow("wOuterInspectRecordEdit", "外部监督检查记录查看"
        , contextPath + "/cim/outerInspectRecordForQuery/view/" + recordId,
        {inFrame: true, size: null, width: 900, height: 600, windowArgs: {}});
}

/**
 * 查看行内检查计划详细信息
 */
function viewInnerInspectPlanEdit(id) {
    openWindow("wInnerInspectPlanEdit", "行内检查计划查询"
        , contextPath + "/cim/innerInspectPlanQuery/view/" + id,
        {inFrame: true, size: null, width: 900, height: 600, windowArgs: {}});
}


