var chartData = {
    "bg_colour": "#FcFcFc",
    "elements": [{
        "type": "bar",
        "alpha": 0.7
    }],
    "x_axis": {
        "max": 5
    },
    "y_axis": {
        "max": 3
    }
};

function initChartsData(contextRoot, id, data) {
    //var strPath=window.top.location.pathname;
    //var postPath=strPath.substring(0,strPath.substring(1).indexOf('/') + 1);
    var flashvars = {};
    var params = {wmode: "opaque"};
    var attributes = {};
    swfobject.embedSWF(contextRoot + "/images/open-flash-chart.swf", id, "100%", "100%", "9.0.0", "expressInstall.swf", flashvars, params, attributes, function (swf) {
        if (swf.success) {
            try {
                var chart = findSWF(swf.id);
                if (data) {
                    chart.load(JSON.stringify(data));
                    //window.setTimeout('var chart=findSWF("' + swf.id + '");chart.load(\'' + JSON.stringify(data) + '\');', 200);
                    //window.setTimeout('reLoadChartsData("' + id + '", "' + JSON.stringify(data) + '");', 200);
                } else {
                    chart.load(JSON.stringify(chartData));
                }
            } catch (e) {
            }
        }
    });
}

function reLoadChartsData(id, data) {
    try {
        var c = findSWF(id);
        if (data) {
            //alert(JSON.stringify(ss));
            c.load(data);
        } else {
            c.load(JSON.stringify(chartData));
        }
    } catch (e) {
    }
}

function open_flash_chart_data() {
    return JSON.stringify(chartData);
}

function findSWF(movieName) {
    if (navigator.appName.indexOf("Microsoft") != -1) {
        return window[movieName];
    } else {
        return document[movieName];
    }
}  	