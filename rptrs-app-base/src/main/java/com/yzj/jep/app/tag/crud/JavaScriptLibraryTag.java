package com.yzj.jep.app.tag.crud;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 导入标记库中所需要的 JavaScript 库
 * 一次导入4个 js 文件，分别为 windlg.js, common.js, crud.datagrid.js, crud.tree.js
 *
 * @author mma
 * @version 3.0
 * @date 2012-3-19 下午12:08:51
 */
public class JavaScriptLibraryTag extends TagSupport {

    private static final long serialVersionUID = -2206940223017812935L;

    @Override
    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            String contextPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
            JspWriter writer = pageContext.getOut();
            writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + contextPath + "/static/styles/colorpicker.css\">");
            writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + contextPath + "/static/styles/lookup.css\">");
            writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + contextPath + "/static/styles/report.css\">");
            writer.println("<script src=\"" + contextPath + "/static/scripts/colorpicker/colorpicker.js\" type=\"text/javascript\"></script>");
            writer.println("<script src=\"" + contextPath + "/static/scripts/windlg.js\" type=\"text/javascript\"></script>");
            writer.println("<script src=\"" + contextPath + "/static/scripts/xui/common.js\" type=\"text/javascript\"></script>");
            writer.println("<script src=\"" + contextPath + "/static/scripts/xui/crud.datagrid.js\" type=\"text/javascript\"></script>");
            writer.println("<script src=\"" + contextPath + "/static/scripts/xui/crud.tree.js\" type=\"text/javascript\"></script>");
            writer.println("<script src=\"" + contextPath + "/static/scripts/xui/lookup.js\" type=\"text/javascript\"></script>");
            writer.println("<script src=\"" + contextPath + "/static/scripts/xui/report.js\" type=\"text/javascript\"></script>");
            writer.println("<script type=\"text/javascript\">");
            writer.println("jQuery.ajaxSetup({cache:false});");
            writer.println("function formatDate(value){");
            writer.println("\tvar longValue = parseFloat(value);");
            writer.println("\tif(longValue != NaN){");
            writer.println("\t\tvar date = new Date(value)");
            writer.println("\t\treturn $.date.format(date, $.date.DATE_FORMAT_4)");
            writer.println("\t}");
            writer.println("\treturn value;");
            writer.println("}");
            writer.println();
            writer.println("function formatDateTime(value){");
            writer.println("\tvar longValue = parseFloat(value);");
            writer.println("\tif(longValue != NaN){");
            writer.println("\t\tvar date = new Date(value)");
            writer.println("\t\treturn $.date.format(date, $.date.DATE_FORMAT_1)");
            writer.println("\t}");
            writer.println("\treturn value;");
            writer.println("}");
            writer.println("function formatCenter(value){");
            writer.println("\tif(!value) return '';");
            writer.println("\t\treturn '<span style=\"width:100%;text-align:center\">' + value + '</span>'");
            writer.println("}");
            writer.println("function formatRight(value){");
            writer.println("\tif(!value) return '';");
            writer.println("\t\treturn '<span style=\"width:100%;text-align:right\">' + value + '</span>'");
            writer.println("}");
            writer.println("function formatCommafy(value){");
            writer.println("\tif(value === undefined || value == null) return '';");
            writer.println("\treturn '<span style=\"width:100%;text-align:right\">' + $.format.commafy(value) + '</span>';");
            writer.println("}");
            writer.println("function formatCommafy2(value){");
            writer.println("\tif(value === undefined || value == null) return '';");
            writer.println("\treturn '<span style=\"width:100%;text-align:right\">' + $.format.roundCommafy(value, 2) + '</span>';");
            writer.println("}");
            writer.println("function formatCommafy4(value){");
            writer.println("\tif(value === undefined || value == null) return '';");
            writer.println("\treturn '<span style=\"width:100%;text-align:right\">' + $.format.roundCommafy(value, 4) + '</span>';");
            writer.println("}");
            writer.println("</script>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return EVAL_PAGE;
    }

}
