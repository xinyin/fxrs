package com.yzj.jep.app.pub.util;

/**
 * 错误代码
 */
public enum StatusCode {

    SUCCESS("成功", "0000"),
    REQ_PARM_MISS("请求参数缺失，缺少参数{请求参数名称}", "1001"),
    DATA_SIGN_FAIL("数据验签错误", "1003"),
    MESS_PARSE_EXC("报文解析异常", "1004"),
    BUS_DATA_ERR("业务数据非空错误，{数据项名称}", "1005"),
    CONT_INFO_NOFOUND("找不到合同信息", "2001"),
    PERMISSIONS_ERR("权限错误", "2002"),
    FAIL("服务端未知错误", "9999");

    private String value;
    private String id;

    StatusCode(String value, String id) {
        this.value = value;
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public String getId() {
        return id;
    }

    public static String getValue(String id) {
        StatusCode[] statusCodes = values();
        for (StatusCode statusCode : statusCodes) {
            if (statusCode.getId().equals(id)) {
                return statusCode.getValue();
            }
        }
        return null;
    }

}
