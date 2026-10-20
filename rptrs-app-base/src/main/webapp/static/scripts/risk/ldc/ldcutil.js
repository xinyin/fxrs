//找到主键
function findBizCode() {
    return $.ajax({url: '${ctx}/ldc/lossevent/getBizCode', type: 'GET', async: false, cache: false}).responseText;
}

function reutrnStaffName(value, url, inputId) {
    if (value) {
        var str = $.ajax({url: url, type: 'GET', async: false, cache: false}).responseText;
        var staffJson;
        if ($.parseJSON(str)) {
            staffJson = $.parseJSON(str);
            $('#' + inputId + '').val(staffJson.staffName);
        }
    }


}

function reutrnUnitName(value, url, inputId) {
    if (value) {
        var str = $.ajax({url: url, type: 'GET', async: false, cache: false}).responseText;
        var unitJson;
        if ($.parseJSON(str)) {
            unitJson = $.parseJSON(str);
            $('#' + inputId + '').val(unitJson.unitName);
        }
    }

}

function formatNumber(srcStr, nAfterDot)        //nAfterDot小数位数
{
    var srcStr, nAfterDot;
    var resultStr, nTen;
    srcStr = "" + srcStr + "";
    strLen = srcStr.length;
    dotPos = srcStr.indexOf(".", 0);
    if (dotPos == -1) {
        resultStr = srcStr + ".";
        for (i = 0; i < nAfterDot; i++) {
            resultStr = resultStr + "0";
        }
        return resultStr;
    } else {
        if ((strLen - dotPos - 1) >= nAfterDot) {
            nAfter = dotPos + nAfterDot + 1;
            nTen = 1;
            for (j = 0; j < nAfterDot; j++) {
                nTen = nTen * 10;
            }
            resultStr = Math.round(parseFloat(srcStr) * nTen) / nTen;
            return resultStr;
        } else {
            resultStr = srcStr;
            for (i = 0; i < (nAfterDot - strLen + dotPos + 1); i++) {
                resultStr = resultStr + "0";
            }
            return resultStr;
        }
    }
}


function amtVaidator(amt, salce, inputId) {
    var pattern = /^[0-9]+([.]\d{1,2})?$/;
    if (!pattern.test(amt, salce, inputId)) {
        $.messager.alert("提示", "请输入数字(例:0.00),最高保留两位小数");
        $('#' + inputId + '').val("");
    } else {
        $('#' + inputId + '').val(formatNumber(amt, salce));
    }
}

function num3dou(num) {
    re = /(\d{1,3})(?=(\d{3})+(?:$|\D))/g
    var retNum;
    var num = num + "";
    var index = num.indexOf(".");
    if (index == -1) {// 无小数点
        retNum = num.replace(re, "$1,");
    } else {
        var intPart = num.substring(0, index);
        var pointPart = num.substring(index + 1, num.length);
        intPart = intPart.replace(re, "$1,");
        retNum = intPart + "." + pointPart;
    }

    return retNum;

}

