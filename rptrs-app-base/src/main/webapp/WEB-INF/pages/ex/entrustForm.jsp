<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <x:jsLib/>
    <br:css pack="form"/>
    <title>金融产品新增或编辑</title>
    <x:jsLib/>

    <style>
        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .has-success .select2-dropdown, .has-success .select2-selection {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }

        .MainGd {
            display: none;
        }

        .voteProportion {
            display: none;
        }
    </style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
<bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
    <bf:form id="editForm" method="post" validation="true">
        <bf:hidden id="pid" name="pid" value="${entrustApply.pid}"/>
        <div class="boxse">
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="委托类型" col="4" formControl="true"/>
                    <bf:text id="entrustType" name="entrustType" col="8" disabled="true" value="${entrustApply.entrustType}">
                    </bf:text>
                </bc:col>
                    <bc:col col="6">
                        <bc:label text="任务编号" col="4" formControl="true"/>
                        <bf:text id="taskNumber" name="taskNumber" col="8" disabled="true" value="${entrustApply.taskNumber}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="委托人" col="4" formControl="true"/>
                    <bf:staffLookup id="staffCode" name="staffCode" col="8"
                                    value="${entrustApply.staffCode}" text="${entrustApply.staffName}" propText="staffName" propValue="staffCode"
                                    callback="result"  onSelect="staffChange" disabled="true" mult="false" placeholder="委托人">
                        <bv:notEmpty/>
                    </bf:staffLookup>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="被委托人" col="4" formControl="true"/>
                    <bf:staffLookup id="transferStaffCode" name="transferStaffCode" col="8"
                                    value="${entrustApply.transferStaffCode}" text="${entrustApply.transferStaffName}" propText="staffName" propValue="staffCode"
                                    callback="result"  onSelect="transferStaffChange" disabled="true" mult="false" placeholder="被委托人">
                        <bv:notEmpty/>
                    </bf:staffLookup>
                </bc:col>
            </bf:formGroup>

            <bf:formGroup hr="dashed" hrp="bottom">

                <bc:col col="6">
                    <bc:label text="委托人部门" col="4" formControl="true"/>
                    <bf:text id="staffDep" name="staffDep" col="8"  value="${entrustApply.staffDep}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="被委托人部门" col="4" formControl="true"/>
                    <bf:text id="transferStaffDep" name="transferStaffDep" col="8"  value="${entrustApply.transferStaffDep}" disabled="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="开始时间" col="4" formControl="true"/>
                    <bf:datebox id="startTime" name="startTime" col="8" disabled="true" value="${entrustApply.startTime}">
                    </bf:datebox>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="结束时间" col="4" formControl="true"/>
                    <bf:datebox id="endTime" name="endTime" col="8" disabled="true" value="${entrustApply.startTime}">
                    </bf:datebox>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="新被委托人名称" col="4" formControl="true"/>
                    <bf:staffLookup id="transferStaffCodeNew" name="transferStaffCodeNew" col="8"
                                    value="${procAutoTransfer.transferStaffCode}" text="${entrustApply.transferStaffNameNew}" propText="staffName" propValue="staffCode"
                                    callback="result" disabled="true" mult="false" placeholder="被委托人">
                    </bf:staffLookup>
                </bc:col>

            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="事项说明" col="4" formControl="true"/>
                    <bf:text id="remark" name="remark" col="8" disabled="true"  multiple="true" rows="5" >${entrustApply.remark}</bf:text>
                </bc:col>
            </bf:formGroup>
    </bf:form>
            </bc:ibox>
        </div>

<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
</body>
</html>
