package com.yzj.jep.app.tag;

/**
 * 标识一个对象支持 js 表达式
 *
 * @author mma
 * @version 3.0
 * @date 2012-3-17 下午1:46:14
 */
public interface JSExpressionSupport {

    /**
     * 返回 js 表达式
     * 如 : method: 'post' 或者
     * {
     * url: '${ctx}/test/dept/query.json'
     * }
     *
     * @return
     */
    String getJSExpression();

}
