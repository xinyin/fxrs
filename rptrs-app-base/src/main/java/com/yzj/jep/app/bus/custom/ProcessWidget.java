package com.yzj.jep.app.bus.custom;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.dash.utils.WidgetUtils;
import com.yzj.jep.sys.dash.widget.AbstractWidget;
import com.yzj.jep.sys.dash.widget.annotation.CustomWidget;
import com.yzj.jep.sys.dash.widget.exception.WidgetException;
import com.yzj.jep.sys.dto.StaffNotifyDto;
import com.yzj.jep.sys.service.StaffNotifyService;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 流程跟踪组件
 *
 * @author Michael
 * durisk.yzj.com
 * 2015年12月21日
 */
@CustomWidget(code = "Process", name = "流程跟踪", desc = "显示流程跟踪的组件", remark = "这是备注\r\n你设置了吗")
public class ProcessWidget extends AbstractWidget {

    public ProcessWidget(String code, String type, String title) {
        super(code, type, title);
    }

    @Override
    public String content() throws WidgetException {
        Page<StaffNotifyDto> page = new Page<>();

        List<Order> orders = new Order.Builder().desc("createTime").build();

        UserProfile staff = UserProfileUtils.getUserProfile();
        List<Filter> filters = new Filter.Builder().eq("staffCode", staff.getStaffCode()).eq("type", "REMIND").eq("type2", "REMIND3").build();
        StaffNotifyService staffNotifyService = SpringContextHolder.getBean(StaffNotifyService.class);
        staffNotifyService.queryNotifyByStaffCodeAndType(page, filters, orders);
        StringBuffer sb = new StringBuffer();
        sb.append("<div id='mytasks' class='content' style=\"height:968px;\">");
        if (page.getResult().size() == 0) {
            sb.append("<tr class=\"no-records-found\">");
            sb.append("<td class=\"text-center\">没有找到匹配的记录</td>");
            sb.append("<td class=\"check-mail\">");
            sb.append("</tr>");
        } else {
            int num = 0;
            for (StaffNotifyDto v : page.getResult()) {
                num++;
                if (num <= 5) {
                    sb.append("<tr class=\"unread\"><td class=\"check-mail\">" +
                            "<input type=\"checkbox\" class=\"i-checks\" value=\"" + v.getStaffNotifyId() + "\">&nbsp;&nbsp;" +
                            "</td>" +
                            "<td class=\"mail-contact\"><a href=\"\">" + v.getSenderName() + "</a>&nbsp;&nbsp;" +
                            "<i class=\"fa fa-list text-danger pull-right\"></i>" + new SimpleDateFormat("yyyy-MM-d").format(v.getCreateTime()) + "&nbsp;&nbsp;" + "</td>" +
                            "<td class=\"mail-subject\"><a href=\"'+ viewUrl+'\">" + v.getSubject() + "</a></td><br/>" +
                            "</tr>");
                }
            }
        }
        sb.append("</div>");
        sb.append("<div class='more'>" + WidgetUtils.genEmptyMore() + "</div>");
        sb.append("<script type=\"text/javascript\">fetchTodoTask();</script>");
        return sb.toString();
    }

}
