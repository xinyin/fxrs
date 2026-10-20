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
                    <div>

                        <table id="tab" width="80%" align="center" cellspacing="0" border="1" bordercolor="#000000">
                            <tbody id="tbody">
                            <tr align="center">
                                <td>制度名称</td>
                                <td>文号</td>
                                <td>发文部门</td>
                                <td>生效日期</td>
                                <td>操作</td>
                            </tr>
                                <c:forEach var="items" items="${doc}" varStatus="varStatus">
                                    <tr align="center">
                                        <td>${items.sysName}</td>
                                        <td>${items.articleNumber}</td>
                                        <td>${items.dispatchDep}</td>
                                        <td><fmt:formatDate value="${items.effectiveTime}" type="both"  pattern="yyyy-MM-dd" /></td>
                                        <td><a href="${base}/bus/docForInner/list">查看</a></td>
                                    </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <br>
                        <br>

                    </div>

                </bf:formGroup>

                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-4">
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/bus/sysDocSubmit/list';
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