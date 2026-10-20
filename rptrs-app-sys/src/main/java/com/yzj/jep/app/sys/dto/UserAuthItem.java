package com.yzj.jep.app.sys.dto;

import java.io.Serializable;

import com.yzj.jep.sys.code.ResourceKind;

/**
 * 用户登录后，在session中保存用户的授权信息，
 * 保存的格式为 Map<String, List<UserAuthItem>>。
 * 其中 String 为 MenuCode.
 *
 * @author Michael
 * @version 3.0
 * @date 2011-12-15 上午11:52:29
 */
public class UserAuthItem implements Serializable {

    private static final long serialVersionUID = -9001765676207458507L;

    /**
     * 资源种类
     */
    private ResourceKind kind;

    /**
     * 数据权限种类代码 或 功能点代码
     */
    private String resourceCode;

    /**
     * 如果 resourceKind 是功能点，则该字段保存界面的控件ID
     * 如果 resourceKind 是数据权限，则该字段保存权重
     */
    private String resourceData;

    public ResourceKind getKind() {
        return kind;
    }

    public void setKind(ResourceKind kind) {
        this.kind = kind;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceData() {
        return resourceData;
    }

    public void setResourceData(String resourceData) {
        this.resourceData = resourceData;
    }

}
