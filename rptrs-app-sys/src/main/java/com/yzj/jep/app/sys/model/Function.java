/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月09日 19:59:12
 *
 */

package com.yzj.jep.app.sys.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_function")
public class Function extends BasePO {

    public static final String TABLE_ALIAS = "Function";
    public static final String ALIAS_FUNCTION_ID = "自增主键";
    public static final String ALIAS_BUTTON_CODE = "按钮编码";
    public static final String ALIAS_BUTTON_NAME = "按钮名称";
    public static final String ALIAS_MENU_CODE = "菜单id";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "FUNCTION_ID", nullable = false, length = 10)
    private Integer functionId;

    @Column(name = "BUTTON_CODE", nullable = false, length = 255)
    private String buttonCode;

    @Column(name = "BUTTON_NAME", nullable = false, length = 255)
    private String buttonName;

    @Column(name = "MENU_CODE", nullable = false, length = 255)
    private String menuCode;
    //columns END


    public Integer getFunctionId() {
        return this.functionId;
    }

    public void setFunctionId(Integer value) {
        this.functionId = value;
    }


    public String getButtonCode() {
        return this.buttonCode;
    }

    public void setButtonCode(String value) {
        this.buttonCode = value;
    }


    public String getButtonName() {
        return this.buttonName;
    }

    public void setButtonName(String value) {
        this.buttonName = value;
    }


    public String getMenuCode() {
        return this.menuCode;
    }

    public void setMenuCode(String value) {
        this.menuCode = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("function[\n")
                .append("functionId = ").append(functionId).append(", \n")
                .append("buttonCode = ").append(buttonCode).append(", \n")
                .append("buttonName = ").append(buttonName).append(", \n")
                .append("menuCode = ").append(menuCode).append(", \n")
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getFunctionId())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Function == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Function other = (Function) obj;
        return new EqualsBuilder()
                .append(getFunctionId(), other.getFunctionId())
                .isEquals();
    }
}

