<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <br:css pack="form"/>
    <title>报送批次表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="docSubmitBox" title="报送批次表" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="3">
                        <bc:label text="报送机构" col="4" formControl="true"/>
                        <bf:select id="submitOrg" name="submitOrg" col="8"  disabled="true" dataSource="custom" url="/sys/dictItem/getDictItems/SUBMIT-org">
                        </bf:select>
                    </bc:col>
                    <bc:col col="3">
                        <bc:label text="年度" col="6" formControl="true"/>
                        <bc:label text="${docSubmit.year}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="3">
                        <bc:label text="季度" col="6" formControl="true"/>
                        <bf:select id="quarter" name="quarter" col="6"   disabled="true" value="${docSubmit.quarter}"  dataSource="custom" url="/sys/dictItem/getDictItems/SUBMIT-quarter">
                        </bf:select>
                    </bc:col>
                    <bc:col col="3">
                        <bc:label text="月份" col="6" formControl="true"/>
                        <bf:select id="month" name="month" col="6" disabled="true" dataSource="custom" url="/sys/dictItem/getDictItems/SUBMIT-month" value="${docSubmit.month}">
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <%--<div  id="result" value="${docSubmit.failCause}" style="width: 500px;height: 150px;border: 1px solid;margin: 0px auto;">--%>
                    <%--</div>--%>
                    <input type="text" id="result" value="${docSubmit.failCause}" style="width: 500px;height: 150px;border: 1px solid;margin-left: 300px">
                    </input>
                    <%--<input type="text" id="result" value="校验结果..." style="width: 500px;height: 150px;border: 1px solid;margin-left: 300px">--%>
                    <%--</input>--%>
                </bf:formGroup>

                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-4">
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/bus/psnDocSubmit/list';
                            </be:event>
                        </bc:button>
                    </div>
                </bf:formGroup>
            </bf:form>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>