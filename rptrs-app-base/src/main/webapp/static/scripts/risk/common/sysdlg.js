/**
 * 组织机构树 接口
 *
 * @param mult
 *               多选单选参数 true为多选，false为单选
 *               kind
 *              选择机构类型 "ORG,DEPT","ORG","DEPT"
 *              auth
 *              查看权限 "0"全部  "1" 本级以及下级机构 “2" 本级机构及部门  "3"本部门或机构单一节点  “4”本业务条线下的所欲部门
 * @param rtnFunc
 *               弹出框操作后返回函数，可自己定义方法名
 */
function UnitDlg(mult, kind, auth, rtnFunc) {
    openWindow("wUnitTree", "组织机构树", contextPath + "/unit/toUnitDlg",
        {
            inFrame: true,
            modal: true,
            size: null,
            width: 460,
            height: 480,
            windowArgs: {
                'mult': mult,
                'kind': kind,
                'auth': auth,
                rtnFunction: rtnFunc
            }
        }
    );
}

function UnitWindow(mult, kind, auth, rtnFunc) {
    openWindow("wUnitTreeWindow", "组织机构树", contextPath + "/unit/toUnitWindow",
        {
            inFrame: true,
            modal: true,
            size: "custom", height: 500, width: 850,
            windowArgs: {
                'mult': mult,
                'kind': kind,
                'auth': auth,
                rtnFunction: rtnFunc
            }
        }
    );
}

function UnitDlgModal(mult, selKind, auth, rtnFunc) {
    openWindow("wUnitTree", "组织机构树", contextPath + "/unit/toUnitDlg",
        {
            inFrame: true,
            modal: true,
            size: "small",
            windowArgs: {mult: mult, selKind: selKind, auth: auth, rtnFunction: rtnFunc}
        }
    );

}

//岗位选择接口
function RoleSelect(rtnFunc) {
    openWindow("wRoleList", "岗位选择", contextPath + "/sys/toRoleListDlg",
        {
            inFrame: true,
            modal: true,
            size: "", width: 700, height: 550,
            windowArgs: {result: rtnFunc}
        }
    );
}

function RoleSelect2(rtnFunc) {
    openWindow("wRoleList", "岗位选择", contextPath + "/sys/toRoleListDlg2",
        {
            inFrame: true,
            modal: true,
            size: "", width: 700, height: 550,
            windowArgs: {result: rtnFunc}
        }
    );
}

//用户选择接口
function StaffSelect(rtnFunc) {
    openWindow("wStaffList", "用户选择", contextPath + "/sys/toStaffListDlg",
        {
            inFrame: true,
            modal: true,
            size: "", width: 700, height: 550,
            windowArgs: {result: rtnFunc}
        }
    );
}

function StaffSelectModal(rtnFunc) {
    openWindow("wStaffList", "用户选择", contextPath + "/sys/toStaffListDlg",
        {
            inFrame: true,
            modal: true,
            size: "", width: 700, height: 550,
            windowArgs: {result: rtnFunc}
        }
    );
}

function StaffSelectByParameter(mult, unitCode, rtnFunc) {
    openWindow("wStaffList", "用户选择", contextPath + "/sys/toStaffListDlg",
        {
            inFrame: true,
            modal: true,
            size: "", width: 700, height: 550,
            windowArgs: {mult: mult, unitCode: unitCode, result: rtnFunc}
        }
    );
}

function PositionSelect(mult, unitCode, rtnFunc) {
    openWindow("wPositionList", "用户选择", contextPath + "/sys/toPositionListDlg",
        {
            inFrame: true,
            modal: true,
            size: "", width: 700, height: 550,
            windowArgs: {mult: mult, unitCode: unitCode, result: rtnFunc}
        }
    );
}

//调用风险事件的列表，选择风险事件
function RebaseinfoDlg(mult, rtnFunc) {
    openWindow("wReBaseinfoList", "风险事件", contextPath + "/ldc/tradwater/selRebaseinfo",
        {
            inFrame: true,
            modal: true,
            size: "medium",
            windowArgs: {
                'mult': mult,
                rtnFunction: rtnFunc
            }
        }
    );
}
