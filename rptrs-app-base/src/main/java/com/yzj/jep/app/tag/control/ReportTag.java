package com.yzj.jep.app.tag.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yzj.jep.app.base.constant.PrivilegeType;
import com.yzj.jep.app.base.utils.PrivilegeUtils;
import com.yzj.jep.app.tag.Option;
import com.yzj.jep.app.tag.OptionSupport;
import com.yzj.jep.app.tag.OptionSupportHandler;
import com.yzj.jep.app.tag.OptionTypeConstant;
import com.yzj.jep.app.tag.OptionUtils;

/**
 * 封装report
 *
 * @author Michael
 * @version 3.0
 * @date 2014年1月20日 下午7:25:38
 */
public class ReportTag extends BodyTagSupport implements OptionSupport {

    private static final long serialVersionUID = 633957035007648681L;

    private static final Logger logger = LoggerFactory.getLogger(ReportTag.class);

    /**
     * report的id
     */
    private String id;

    private String url;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /*所有选项处理对象*/
    OptionSupportHandler handler = new OptionSupportHandler();

    /**
     * 增加选项，同名选项将被覆盖
     */
    @Override
    public void addOption(Option opt) {
        handler.addOption(opt);
    }

    /**
     * 增加一个选项
     *
     * @param name
     * @param value
     */
    public void addOption(String name, String value) {
        Option option = OptionUtils.createOption(name, value);
        addOption(option);
    }

    /**
     * 获得所有选项
     */
    @Override
    public List<Option> getOptions() {
        return handler.getOptions();
    }

    /**
     * 判断是否包含某个选项
     */
    @Override
    public boolean contains(String key) {
        return handler.contains(key);
    }

    /**
     * 获得选项的表达式
     */
    @Override
    public String getJSExpression() {
        return handler.getJSExpression();
    }

    @Override
    public int doEndTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        PrivilegeType type = PrivilegeUtils.getThreadPrivilegeType();
        if (type == null) {
            type = PrivilegeType.DAK_FORBIDDEN;
        }
        addOption("url", url);

        /*增加数据权限控制*/
        List<Option> options = getOptions();
        Option params = null;
        for (Option opt : options) {
            if ("params".equals(opt.getName())) {
                params = opt;
            }
        }

        if (params == null) {
            params = OptionUtils.createOption("params", "{DAK: '" + type.getCode() + "'}", OptionTypeConstant.TYPE_OBJECT);
        } else {
            JSONObject jo = JSONObject.fromObject(params.getExpr().replace("'params':", ""));
            jo.put("DAK", type.getCode());
            params = OptionUtils.createOption("params", jo.toString(), OptionTypeConstant.TYPE_OBJECT);
        }
        addOption(params);

        try {
            /*输出 report 脚本*/
            writer.println("<script type=\"text/javascript\">");
            /*
             * 加入一个该对象的锁。由于在 easyui-layout 中，layout会动态改变
             * DOM 结构，导致该段脚本被执行两遍，可能会引发问题，
             * 因此加入锁，防止重复执行。
             */
            writer.println("var " + id + "_lock = false;");
            /*输出 report*/
            writer.println("jQuery(function($){");
            writer.println("if(!" + id + "_lock){");

            writer.println("$('#" + id + "').report(");
            writer.println(getJSExpression());
            writer.println(");");

            writer.println(id + "_lock=true;}");
            writer.println("});");
            writer.println("</script>");

            writer.print("<div id=\"" + id + "\" ></div>");

        } catch (IOException e) {
            logger.error("写入report: " + id + " 发生IO异常，请稍后重试。");
        }

        return super.doEndTag();
    }

    @Override
    public int doStartTag() throws JspException {
        handler.clear();
        return super.doStartTag();
    }

    @Override
    public int doAfterBody() throws JspException {
        return super.doAfterBody();
    }

}
