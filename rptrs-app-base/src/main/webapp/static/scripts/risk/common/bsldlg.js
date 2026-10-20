//服务器发布地址，取risk路径
var strPath = window.top.location.pathname;
var postPath = strPath.substring(0, strPath.substring(1).indexOf('/') + 1);

/**
 * 产品接口
 *
 * @returns product
 *        返回选择的产品信息
 */
function productInfo(args, url) {
    var returnData = window.showModalDialog(postPath + url, args, "dialogWidth=600px;dialogHeight=500px");
    var product = "";
    if (returnData != null) {
        //args保存两个对象：args[0]是flag:BIZLINECODE，表示是条线；PROCESSID，表示是流程;
        //              args[1]:表示传递的id:业务条线id或者流程id
        if (args[0] == "BIZLINECODE") {
            product = returnData;
        } else if (args[0] == "PROCESSID") {
            for (var i = 0; i < returnData.length; i++) {
                product = product + returnData[i] + "\r\n";
            }
        }
        return product;
    }
}