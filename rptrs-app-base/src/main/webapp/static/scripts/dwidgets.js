/**
 * �򿪴�����������
 * @param href
 * @param menuCode
 * @param menuName
 * @param menuUrl
 * @param taskKey
 */
function todo_onclick(menuCode, menuName, menuUrl, taskKey) {
    if (menuUrl && menuUrl.indexOf('__Notify=N') > 0) {
        menuUrl = menuUrl + "&taskKey=" + taskKey;
    }
    window.parent.triggerOpen(menuCode, menuUrl, menuName);
}

/**
 * ͨ��jQuery��ȡjson���ݹ�����������б�
 * @param data
 */
function initTodoTask(data, refresh) {
    if (data.taskList.length > 0) {
        var s = [];
        var taskModules = [];
        var currTM = "";
        $.each(data.taskList, function (idx, item) {
            if (currTM != item.taskModule) {
                taskModules.push(item.taskModule);
                currTM = item.taskModule;
            }
        });
        $('#divTodo').html('');
        for (var i = 0; i < taskModules.length; i++) {
            s.push('<div style="padding:2px 0px;margin:0;">��' + taskModules[i] + '��');
            $.each(data.taskList, function (idx, item) {
                if (taskModules[i] == item.taskModule) {
                    var clickStr = 'onclick=todo_onclick("' + item.menuCode + '","' + item.menuName + '","' + item.menuURL + '","' + item.taskKey + '")';
                    s.push('<li style="padding:2px 2px 2px 20px; list-style-type:square;"><span class="todoItemSpan"><a href="#" ' + clickStr + '>' + item.taskTopic + '</a></span></li>');
                }
            });
            s.push('</div>');
        }
        if (refresh == "refresh") {//ˢ�µ�ʱ���ӳٰ������
            setTimeout(function () {
                $('#divTodo').html(s.join(''));
            }, 500);
        } else {//��ʼ��¼��ֱ�Ӽ���
            $('#divTodo').html(s.join(''));
        }
    } else {
        $('#divTodo').html('<div style="text-align:center;padding:50px 0px">û�д����������</div>');
    }
}

/**
 * ˢ�´�������
 * @param contextPath
 */
function reloadTodoTask(contextPath) {
    $.ajax({
        cache: false,
        async: true,
        type: "GET",
        url: contextPath + "/TodoTaskServlet",
        data: "manRefresh=true",
        beforeSend: function () {
            $("#divRefreshTodo").show();
        },
        success: function (result) {
            $("#divRefreshTodo").hide();
            initTodoTask(result, "refresh");
        }
    });
}

/**
 * ����֪ͨ������⣬�鿴֪ͨ��������
 * @param infoId
 */
function infoClick(infoId) {
    if (infoId) {
        path = "/jsp/col/colInformationContentRead.jsp?id=" + infoId + "&rnd=" + Math.random();
        window.parent.triggerOpen("DW_INFO_VIEW", path, "�鿴֪ͨ����");
    }
}

/**
 * ����Ԥ����Ϣ�������ӣ��鿴Ԥ����Ϣ����
 * @param warnInfoId
 */
function warnInfoClick(warnInfoId) {
    if (warnInfoId) {
        path = "/jsp/portal/ptlDwWarnInfoDetail.jsp?id=" + warnInfoId + "&rnd=" + Math.random();
        window.parent.triggerOpen("DW_WARN_VIEW", path, "�鿴Ԥ����Ϣ");
    }
}

/**
 * �����ж��ƻ��������ӣ��鿴�ж��ƻ���Ϣ���ݣ������ǲ鿴��������ִ���еģ�����procIdһ����ֵ
 * @param acpId �ж��ƻ�id
 * @param procId ����id
 */
function acpClick(acpId, procId) {
    if (!acpId) return;
    if (!procId) return;
    var path;
    if (procId) {
        path = "/jsp/act/actPlanInfoApproSuggest.jsp?action=approve&actionFlag=modify&operate=view&planId=" + acpId + "&processId=" + procId;
    } else {
        path = "/jsp/act/actPlanInfoEdit.jsp?actionFlag=modify&flag=view&state=view&planId=" + acpId + "&rnd=" + Math.random();
    }
    window.parent.triggerOpen("DW_ACP_VIEW", path, "�鿴�ж��ƻ�");
}

/**
 * �������ձ��棬�鿴���ձ�������
 * @param rptId
 */
function rptClick(rptId) {
    if (rptId) {
        path = "/jsp/kms/kmsViewTabset.jsp?riskreportId=" + rptId + "&rnd=" + Math.random();
        window.parent.triggerOpen("DW_RPT_VIEW", path, "�鿴���ձ���");
    }
}

/**
 * ����KRIָ��ֵ���鿴KRIԤ��ֵ����kriDataCenter.jspҳ�濽������
 * @param id
 * @param unitid
 * @param bizGroupid
 * @param unitFlag
 */
function kriClick(id, unitid, bizGroupid, unitFlag) {
    var path = "/jsp/kri/kriDataCenterInfo.jsp?kriId=" + id + "&unitid=" + unitid + "&unitFlag=" + unitFlag + "&bizGroupid=" + bizGroupid + "&showsub=0&rnd=" + Math.random();
    window.parent.triggerOpen("DW_KRI_VIEW", path, "�鿴KRI����");
}

/**
 * ����LDC���ƣ��鿴LDC��ϸ��Ϣ
 * @param id ��ʧ�¼�id
 * @param code ��ʧ�¼�code
 * @param name ��ʧ�¼�name
 */
function ldcClick(id, code, name) {
    var path = "/jsp/ldc/ldcLosseventEdit.jsp?flag=view&readOnly=true&losseventId=" + id + "&losseventCode=" + code + "&losseventName=" + name + "&cancel=ldcQuery&rnd=" + Math.random();
    window.parent.triggerOpen("DW_LDC_VIEW", path, "�鿴LDC����");
}

/**
 * �������Ʒ�����ƣ��鿴�����Ŀ��ϸ��Ϣ
 * @param id ��ʧ�¼�id
 * @param code ��ʧ�¼�code
 * @param name ��ʧ�¼�name
 */
function osrClick(id, name) {
    var path = "/jsp/osr/osrVarietyRunProject.jsp?flag=view&readOnly=true&varietyId=" + id + "&rnd=" + Math.random();
    window.parent.triggerOpen("DW_OSR_VIEW", path, "�鿴�����Ŀ����");
}

/**
 * ����LDC��ʧ�ֲ�ҳǩ��ͼ���鿴��ʧ�ֲ�����
 */
function ldcChartMore() {
    window.parent.triggerOpen("DW_LDC_CHART_MORE", "/jsp/ldc/ldcMoreQuery.jsp", "������ʧ�ֲ�");
}

/**
 * ���������Ӧ�̣��鿴��ϸ��Ϣ
 * @param id
 */
function supplierClick(id) {
    var path = "/jsp/osr/osrSupplierViewTabset.jsp?id=" + id;
    window.parent.triggerOpen("DW_OSM_SUPPLIER_VIEW", path, "�鿴�����Ӧ��");
}

function kriRadarMore() {
    window.parent.triggerOpen("DW_KRI_RADAR_MORE", "/jsp/kri/kriTrendMoreQuery.jsp", "ָ������");
}

function kriOsrRadarMore() {
    window.parent.triggerOpen("DW_KRI_OSR_RADAR_MORE", "/jsp/kri/kriOsrTrendMoreQuery.jsp", "ָ������(���)");
}


function osrContactClick(id, projectId) {
    var path = "/jsp/osr/osrContractInfo.jsp?opType=3&contractId=" + id + "&projectId=" + projectId + "&opFlag=view";
    window.parent.triggerOpen("DW_CONTACT_VIEW", path, "�鿴��ͬ����");
}
