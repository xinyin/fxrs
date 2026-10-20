package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.bus.model.Doc;
import com.yzj.jep.app.bus.model.DocFile;
import com.yzj.jep.app.bus.model.DocRel;
import com.yzj.jep.app.bus.service.DocFileService;
import com.yzj.jep.app.bus.service.DocRelService;
import com.yzj.jep.app.bus.service.DocService;
import com.yzj.jep.app.bus.vo.DocInnerVo;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bus/report")
public class reportController extends CrudController<Doc> {

    private static Logger logger = LoggerFactory.getLogger(DocForInnerController.class);

    @Autowired
    private DocService docService;

    @Autowired
    private DocFileService docFileService;

    @Autowired
    private DocRelService docRelService;

    /**
     * 获取行内列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String listForInner(Model model) {

        return "bus/report";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Doc> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        String beginDate = "";
        String endDate = "";
        int index;
        for (int i = 0; i < filters.size(); i++) {
            if ("beginDate".equals(filters.get(i).getProperty())) {
                index = i;
                beginDate = filters.get(i).getValue().toString();
                filters.remove(index);
                if (!StringUtils.isEmpty(beginDate)) {
                    filters.add(new Filter("approvalTime", Operator.GE, beginDate));
                }
            }
            if ("endDate".equals(filters.get(i).getProperty())) {
                endDate = filters.get(i).getValue().toString();
                index = i;
                filters.remove(index);
                if (!StringUtils.isEmpty(beginDate)) {
                    filters.add(new Filter("approvalTime", Operator.LE, endDate));
                }
            }

        }
        filters.add(new Filter("delflag", Operator.EQ, 1));
        orders.add(new Order("modifiedByName", OrderType.DESC));
        filters.add(new Filter("systemType", Operator.EQ, "I"));
        docService.query(page, filters, orders);
    }


    /**
     * 编辑行内制度页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, Model model) {

        Doc doc = docService.get(pid);
        model.addAttribute("doc", doc);
        model.addAttribute("opt", "edit");
        return "bus/docListForInnerNew";
    }


    /**
     * 行内制度删除
     *
     * @param pids
     * @return
     */
    @RequestMapping(value = "/remove/{pids}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String pids) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            if (!StringUtils.isEmpty(pids)) {
                List<Filter> filtersForDoc = null;
                List<Filter> filtersForDocFile = null;
                List<Filter> filtersForDocRel = null;
                String[] arrayForPids = pids.split(",");
                for (String pid : arrayForPids) {
                    //制度表中删除标记置为是
                    filtersForDoc = new Filter.Builder().eq("pid", pid).build();
                    List<Doc> docs = docService.select(filtersForDoc);
                    if (docs != null && docs.size() > 0) {
                        docs.get(0).setDelflag("0");
                        int updateFordoc = docService.update(docs.get(0));
                        if (updateFordoc == 1) {
                            //制度文件表中删除标记记为是
                            filtersForDocFile = new Filter.Builder().eq("docPid", docs.get(0).getPid()).build();
                            List<DocFile> docFiles = docFileService.select(filtersForDocFile);
                            if (docFiles != null && docFiles.size() > 0) {
                                for (DocFile docFile : docFiles) {
                                    docFile.setDelflag("0");
                                    docFileService.update(docFile);
                                }
                            } else {
                                tips.put(WebCode.SUCCESS, false);
                                tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
                            }
                            //行内外制度关联表中删除标记记为是
                            filtersForDocRel = new Filter.Builder().eq("docInnerPid", pid).build();
                            List<DocRel> docRels = docRelService.select(filtersForDocRel);
                            if (docRels != null && docRels.size() > 0) {
                                for (DocRel docRel : docRels) {
                                    docRel.setDelflag("0");
                                    docRelService.update(docRel);
                                }
                            }
                        } else {
                            tips.put(WebCode.SUCCESS, false);
                            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
                        }
                    }
                }
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            } else {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return tips;
    }

    /**
     * 验证提交的pid是否已经存在数据库中
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = docService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }








}
