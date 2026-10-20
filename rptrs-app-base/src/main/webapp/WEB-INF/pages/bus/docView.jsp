<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>制度详情</title>
</head>
<style>
    #docTable {
        text-align: center;
        vertical-align: middle;
    }
</style>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="docBox" title="" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <%--<bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="主键" col="2" formControl="true"/>
                    <bc:label text="${doc.pid}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>--%>
                <%--<bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="制度名称" col="2" formControl="true"/>
                    <bc:label text="${doc.sysName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="文号" col="2" formControl="true"/>
                    <bc:label text="${doc.articleNumber}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="发文部门" col="2" formControl="true"/>
                    <bc:label text="${doc.dispatchDep}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="上传机构" col="2" formControl="true"/>
                    <bc:label text="${doc.uploadOrg}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="上传部门" col="2" formControl="true"/>
                    <bc:label text="${doc.uploadDep}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="生效时间" col="2" formControl="true"/>
                    <bc:label text="${doc.effectiveTime}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="失效时间" col="2" formControl="true"/>
                    <bc:label text="${doc.expiryTime}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="颁布机构" col="2" formControl="true"/>
                    <bc:label text="${doc.issueOrg}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="是否有效" col="2" formControl="true"/>
                    <bc:label text="${doc.isVal}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="制度类型" col="2" formControl="true"/>
                    <bc:label text="${doc.systemType}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>--%>
                <bf:formGroup >
                    <bc:col col="4">
                        <bc:label text="制度名称" col="4" formControl="true"/>
                        <bf:text value="${doc.sysName}" col="8" id="sysName" name="sysName" disabled="true"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="文号" col="4" formControl="true"/>
                        <bf:text value="${doc.articleNumber}" col="8" id="articleNumber" name="articleNumber" disabled="true"/>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="发文部门" col="4" formControl="true"/>
                        <bf:text value="${doc.dispatchDep}" col="8" id="dispatchDep" name="dispatchDep" disabled="true"/>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup >
                    <bc:col col="4">
                        <bc:label text="上传机构" col="4" formControl="true"/>
                        <bf:text value="${doc.uploadOrg}" col="8" id="uploadOrg" name="uploadOrg" disabled="true"/>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="上传部门" col="4" formControl="true"/>
                        <bf:text value="${doc.uploadDep}" col="8" id="uploadDep" name="uploadDep" disabled="true"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="生效时间" col="4" formControl="true"/>
                        <bf:text value="${doc.effectiveTime}" col="8" id="effectiveTime" name="effectiveTime" disabled="true"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup >
                    <bc:col col="4">
                        <bc:label text="失效时间" col="4" formControl="true"/>
                        <bf:text value="${doc.expiryTime}" col="8" id="expiryTime" name="expiryTime" disabled="true"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="颁布机构" col="4" formControl="true"/>
                        <bf:text value="${doc.issueOrg}" col="8" id="issueOrg" name="issueOrg" disabled="true"/>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="是否有效" col="4" formControl="true"/>
                        <bf:text value="${doc.isVal=='0'?'现行有效':'无效'}" col="8" id="isVal" name="isVal" disabled="true"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup >
                    <bc:col col="4">
                        <bc:label text="制度类型" col="4" formControl="true"/>
                        <bf:text value="${doc.systemType=='I'?'行内制度':'行外制度'}" col="8" id="systemType" name="systemType" disabled="true"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="制度文件" col="4" formControl="true"/>
                        <bf:text value="" id="" name="" col="8" disabled="true"/>
                    </bc:col>
                </bf:formGroup>



                <div id="tableDiv" style="">
                    <bc:table id="docTable" idField="pid" url="${base}/bus/docFile" multiple="true"
                              selectFirst="false">
                        <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
                        <bc:column field="pid" title="主键" width="100" visible="false"/>
                        <bc:column field="fileName" title="文件名" width="100"/>
                        <bc:column field="fileType" title="文件类型" width="100"/>
                        <bc:column field="fileSize" title="大小" width="100"/>
                        <bc:column field="uploadOrg" title="上传机构" width="100"/>
                        <bc:column field="uploadDep" title="上传部门" width="100"/>
                        <bc:column field="createTime" title="上传时间" width="100"/>
                    </bc:table>
                </div>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-5" style="margin-top: 10px;">
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                <%--var url=window.location.href;
                                if(url.indexOf('docForInner')!=-1){
                                    window.location='${base}/bus/docForInner/list'
                                }else{
                                window.location='${base}/bus/docForOutside/list'
                                }--%>
                                window.location.href =document.referrer;
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
<script>
    //页面初始化时展示文件列表数据和关联外规的数据
    $(function () {
        var url = window.location.href;
        if (url.indexOf('/') == -1) {
            //新增数据，无需查询
            return;
        }
        //编辑操作
        var arr = url.split('/');
        var docPid = arr[arr.length - 1];
        //查询文件表数据
        $.ajax({
            type: 'get',
            url: '${base}/bus/docFile/query?docPid=' + docPid,
            dataType: 'json',
            success: function (res) {
                $('#docTable').bootstrapTable('load', res);
            }
        })
    })

    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        if (pid == '' || pid == undefined) {
            $.messager.alert("请选中一个文件");
            return;
        }
        if (pid.indexOf(',') != -1) {
            $.messager.alert("只能选中一个文件");
            return;
        }
        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/bus/docFile/preview?pid='+pid+'&fileName='+fileName+'.pdf'));
    }

    function getIdSelections() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    //文件表所有选中的数据
    function getselectData() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }
</script>
</body>
</html>