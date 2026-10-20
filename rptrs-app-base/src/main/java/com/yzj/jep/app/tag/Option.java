package com.yzj.jep.app.tag;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * option 对象 ，包括name和expr
 *
 * @author mma
 * @version 3.0
 * @date 2012-3-17 下午1:36:35
 */
public class Option implements Serializable {

    private static final long serialVersionUID = 7783939811915774812L;

    Option(String name, String expr) {
        this.name = name;
        this.expr = expr;
    }

    /**
     * 名称
     */
    private String name;
    /**
     * 表达式
     */
    private String expr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Option)) {
            return false;
        }

        Option opt = (Option) o;
        return new EqualsBuilder().append(getName(), opt.getName())
                .append(getExpr(), opt.getExpr()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getName()).append(getExpr()).toHashCode();
    }


}
