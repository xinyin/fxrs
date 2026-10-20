<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>简单导出</title>
</head>
<body class="gray-bg" style="overflow: hidden;">
<div class="row">
    <bc:ibox title="" col="12" collapsible="false">
        <bf:form id="form1" name="form1" method="post" validation="false">
            <bf:formGroup>
                <bc:label text="导出类型" col="4" formControl="true" style="text-align:right"/>
                <bf:select id="exportType" name="exportType" col="8" dataSource="fixedDict" url="ExportType">
                </bf:select>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="导出范围" col="4" formControl="true" style="text-align:right"/>
                <bf:select id="exportRange" name="exportRange" col="8" dataSource="fixedDict" url="ExportRange">
                </bf:select>
            </bf:formGroup>
            <bf:formGroup>
                <div style="text-align: center;">
                    <bc:button type="primary" text="导出" icon="save" click="btnExport()"></bc:button>
                    <bc:button text="返回" click="btnColse()"></bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
<iframe id="ifrExport" src="about:blank" frameborder="0" scrolling="auto"
        style="display:none;z-index: 1;width:0px;height:0px; overflow: visible;"></iframe>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    var winId, title, url, pageNumber, pageSize, queryParams, total, columnTitles, columnFields;
    $(document).ready(function () {
        //获取参数
        var args = $.messager.args('exportWinID');
        winId = args.id;
        title = args.title;
        pageNumber = args.pageNumber;
        pageSize = args.pageSize;
        total = args.total;
        url = args.url;
        queryParams = args.queryParams;
        columnTitles = args.columnTitles;
        columnFields = args.columnFields;
    });

    //简单导出
    function btnExport() {
        var exportType = $('#exportType').val();

        var params = [];
        params.push('exportType=' + exportType);
        var exportRange = $('#exportRange').val();
        if (exportRange == 'ALL') {
            params.push('page=1');
            params.push('rows=' + total);
        } else {
            params.push('page=' + pageNumber);
            params.push('rows=' + pageSize);
        }

        /* 将查询参数增加到路径中 */
        for (var k in queryParams) {
            params.push(k + '=' + queryParams[k]);
        }
        params.push('columnTitles=' + columnTitles);
        params.push('columnFields=' + columnFields);
        params.push('exportFileName=' + $.helper.base64Encode((title == null || title == '') ? 'export' : title));
        url += '?' + params.join('&');
        $.messager.args('' + winId + '').callback(url);
        //关闭页面
        $.messager.hide('' + winId + '');
    }

    //关闭界面
    function btnColse() {
        $.messager.hide('' + winId + '');
    }
</script>
</body>
</html>