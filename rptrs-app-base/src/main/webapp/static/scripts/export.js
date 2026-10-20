//服务器发布地址，取risk路径
var strPath = window.top.location.pathname;
var postPath = strPath.substring(0, strPath.substring(1).indexOf('/') + 1);
//检索条件
var fromPath = "";

//点击导出，弹出的参数设置窗口
function exportResult(datagridName, fromPath) {
    this.fromPath = fromPath;
    openWindow("wExportParameters",
        "导出参数设定",
        postPath + "/export/toParamters",
        {
            inFrame: true, size: null, width: 390, height: 140,
            windowArgs: {datagridName: datagridName},
            onClose: ccExportPara
        });
}

//关闭参数设置窗口的回调函数
function ccExportPara() {

    var data = getWindowRes("wExportParameters");
    var flag = data.flag;
    var datagridName = data.datagridName;
    datagridName = "#" + datagridName;
    if (flag != "close") {
        var page_options = $(datagridName).datagrid('getPager').data("pagination").options;
        var curr = page_options.pageNumber;
        var total = page_options.total;
        var pagesize = page_options.pageSize;

        var exportType = data.exportType;
        var exportRange = data.exportRange;

        var url = fromPath + "/export?";
        if (exportType == "EXCEL") {
            url += "exportType=EXCEL&";
            if (exportRange == "ALL") {
                url += "page=1&rows=" + (total) + "&";
            } else if (exportRange == "CURRENT") {
                url += "page=" + curr + "&rows=" + pagesize + "&";
            }
        } else {
            alert("导出pdf");
            return;
        }
        var params = $(datagridName).datagrid('options').queryParams;
        for (var k in params) {
            url += k + "=" + params[k] + "&";
        }
        //列的titile字符串，用“，”进行分割
        var columnTitils = "";
        //列对应的变量值，用”，“进行分割
        var columnFields = "";
        //获得datagrid的所有列
        var columns = $(datagridName).datagrid("getColumnFields");
        for (var k in columns) {
            //根据列的filed参数，获得一个完整的列
            var column = $(datagridName).datagrid("getColumnOption", columns[k]);
            if (k > 0) {
                columnTitils += ",";
                columnFields += ",";
            }
            columnTitils += encodeURIComponent(column.title);
            columnFields += columns[k];
        }
        url += "&columnTitles=" + columnTitils + "&columnFields=" + columnFields;
        window.location = url;
    }
}



