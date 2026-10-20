/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月20日 14:23:05
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.bus.model.DocFile;
import com.yzj.jep.app.bus.model.DocRel;
import com.yzj.jep.app.bus.service.DocFileService;
import com.yzj.jep.app.bus.service.DocRelService;
import com.yzj.jep.app.bus.vo.DocInnerVo;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.yzj.jep.app.bus.model.Doc;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.DocService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/bus/docForInner")
public class DocForInnerController extends CrudController<Doc> {
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
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "bus/docForInner/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);

        return "bus/docListForInner";
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
     * 行内制度新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String addForInner(Model model) {
        model.addAttribute("sysType", "I");
        return "bus/docListForInnerNew";
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
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model) {

        Doc doc = docService.get(pid);
        model.addAttribute("doc", doc);

        return "bus/docView";
    }

    /**
     * 行内新增/编辑 保存
     *
     * @param doc
     * @return
     */
//	@RequestMapping(value = "/save")
//	@ResponseBody
//	public Map<String, Object> save(@RequestBody Map<String,Object> map) {
//		Map<String, Object> tips = new HashMap<String, Object>();
//		Integer pidForInnerDoc = null;
//		if (!map.isEmpty()){
//		   //判断制度信息中是否有pid,是为更新,否为新增
//		   if (!map.containsKey(("pid"))){
//			  Random r = new Random();
//			  int a=(int)(r.nextDouble()*6666666);
//              Doc doc = new Doc();
//              doc.setPid(a);
//              doc.setDelflag("1");
//              if (map.containsKey("sysName")){
//              	doc.setSysName(map.get("sysName").toString());
//			  }
//			  if (map.containsKey("articleNumber")){
//			  	doc.setArticleNumber(map.get("articleNumber").toString());
//			  }
//			   if (map.containsKey("dispatchDep")){
//				   doc.setDispatchDep(map.get("dispatchDep").toString());
//			   }
//			   if (map.containsKey("isVal")){
//				   doc.setIsVal(map.get("isVal").toString());
//			   }
//			   if (map.containsKey("effectiveTime")){
//			   	   Date date = DateUtils.parseDate(map.get("effectiveTime").toString());
//				   doc.setEffectiveTime(date);
//			   }
//			   if (map.containsKey("expiryTime")){
//				   Date date = DateUtils.parseDate(map.get("expiryTime").toString());
//				   doc.setExpiryTime(date);
//			   }
//			   doc.setSystemType("I");
//			   doc.setCreateTime(new Date()); //创建时间
//			   doc.setCreateTimeString(new Date().toString()); //创建时间
//			   doc.setModifiedTime(new Date()); //修改时间
//			   doc.setModifiedTimeString(new Date().toString()); //修改时间
//			   int insert = docService.insert(doc);
//			   if (insert==1){
//			   	logger.debug("新增制度成功");
//			   }else {
//			   	logger.debug("新增制度失败");
//			   }
//			   pidForInnerDoc = a;
//		   }else {
//			   //根据主键查询
//			   List<Filter> filters = new Filter.Builder().eq("pid", (map.get("pid").toString())).eq("delflag", 1).build();
//			   List<Doc> docs = docService.select(filters);
//			   if (docs!=null && docs.size()>0){
//			   	Doc doc = docs.get(0);
//			   if (map.containsKey("sysName")) {
//				   doc.setSysName(map.get("sysName").toString());
//			   }
//			   if (map.containsKey("articleNumber")) {
//				   doc.setArticleNumber(map.get("articleNumber").toString());
//			   }
//			   if (map.containsKey("dispatchDep")) {
//				   doc.setDispatchDep(map.get("dispatchDep").toString());
//			   }
//			   if (map.containsKey("isVal")) {
//				   doc.setIsVal(map.get("isVal").toString());
//			   }
//			   if (map.containsKey("effectiveTime")) {
//				   Date date = DateUtils.parseDate(map.get("effectiveTime").toString());
//				   doc.setEffectiveTime(date);
//			   }
//			   if (map.containsKey("expiryTime")) {
//				   Date date = DateUtils.parseDate(map.get("expiryTime").toString());
//				   doc.setExpiryTime(date);
//			   }
//			   doc.setModifiedTime(new Date()); //修改时间
//			   doc.setModifiedTimeString(new Date().toString()); //修改时间
//			   int update = docService.update(doc);
//			   if (update == 1) {
//				   logger.debug("更新制度成功");
//			   } else {
//				   logger.debug("更新制度失败");
//			   }
//			   pidForInnerDoc = new Integer(map.get("pid").toString());
//		   }
//		   }
//		   if (map.containsKey("pidForDocFile")) {
//			   String pidForDocFile = (String) map.get("pidForDocFile");
//			   if (!StringUtils.isEmpty(pidForDocFile)){
//				   String[] arrayForDoc = pidForDocFile.split(",");
//			   if (arrayForDoc != null && arrayForDoc.length > 0) {
//				   for (String s : arrayForDoc) {
//					   DocFile docFile = docFileService.get(new Integer(s));
//					   docFile.setDocPid(pidForInnerDoc);
//					   int update = docFileService.update(docFile);
//					   if (update == 1) {
//						   logger.debug("更新制度文件成功");
//					   } else {
//						   logger.debug("更新制度文件失败");
//					   }
//				   }
//			   }
//		   }
//		   }
//		  if (map.containsKey("pidForOutsideDoc")) {
//			  String pidForOutsideDoc = (String) map.get("pidForOutsideDoc");
//			  if (!StringUtils.isEmpty(pidForOutsideDoc)){
//				  String[] arrayForOutsideDoc = pidForOutsideDoc.split(",");
//			  if (arrayForOutsideDoc != null && arrayForOutsideDoc.length > 0) {
//				  for (String s : arrayForOutsideDoc) {
//					  Random r = new Random();
//					  int b = (int) (r.nextDouble() * 6666666);
//					  DocRel docRel = new DocRel();
//					  docRel.setPid(b);
//					  docRel.setDocInnerPid(pidForInnerDoc);
//					  docRel.setDelflag("1");
//					  docRel.setDocOutsidePid(new Integer(s));
//					  docRel.setCreateTime(new Date()); //创建时间
//					  docRel.setCreateTimeString(new Date().toString()); //创建时间
//					  docRel.setModifiedTime(new Date()); //修改时间
//					  docRel.setModifiedTimeString(new Date().toString()); //修改时间
//					  int insert = docRelService.insert(docRel);
//					  if (insert == 1) {
//						  logger.debug("新增行内外制度关联成功");
//					  } else {
//						  logger.debug("新增行内外制度关联失败");
//					  }
//				  }
//			  }
//		  }
//		  }
//		    tips.put(WebCode.SUCCESS,true);
//		    tips.put(WebCode.MSG,WebCode.SUCCESS_SAVE);
//		}else {
//			tips.put(WebCode.SUCCESS, false);
//			tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
//		}
//		return tips;
//	}

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


    /*
     * 根据行内制度主键查询制度信息
     * */
    @RequestMapping(value = "/selectDocForPid/{pid}")
    @ResponseBody
    public Map<String, Object> selectDocForPid(@PathVariable String pid) {
        Map<String, Object> tips = new HashMap<>();
        List<Doc> docsList = new ArrayList<>();
        if (!StringUtils.isEmpty(pid)) {
            List<Filter> filterForDocPid = new Filter.Builder().eq("pid", pid).eq("delflag", 1).build();
            List<Doc> docs = docService.select(filterForDocPid);
            if (docs != null && docs.size() > 0) {
                docsList.add(docs.get(0));
            }
            tips.put(WebCode.SUCCESS_OPERATE, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
            tips.put("fileData", docsList);
        } else {
            tips.put(WebCode.FAULURE_OPERATE, true);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return tips;
    }


    /**
     * 行外制度查询(关联处)
     */
    @RequestMapping(value = "/selectDocForOutside")
    @ResponseBody
    public Map<String, Object> selectDocForOutside(Page<Doc> page, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = null;
        List<Order> orders = null;
        String sysName = request.getParameter("sysName");
        String articleNumber = request.getParameter("articleNumber");
        String issueOrg = request.getParameter("issueOrg");
        filters = new Filter.Builder().eq("delflag", 1).eq("systemType", "O").like("sysName", sysName).like("articleNumber", articleNumber).like("issueOrg", issueOrg).build();
        orders = new Order.Builder().desc("modifiedTime").build();
        List<Doc> systemfiles = docService.select(filters, orders);
        page.setTotal(systemfiles.size());
        int pages = (systemfiles.size() + page.getPageSize() - 1) / page.getPageSize();
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<Doc> docList = new ArrayList<>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < systemfiles.size()) {
                docList.add(systemfiles.get(i));
            }
        }
        page.setResult(docList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /*
     * 关联行外制度解除
     * */
    @RequestMapping("/delRelForSysOutside/{sysInnerPid}/{sysOutsidePid}")
    @ResponseBody
    public Map<String, Object> delRelForSysOutside(@PathVariable String sysInnerPid, @PathVariable String sysOutsidePid) {
        Map<String, Object> tips = new HashMap<>();
        List<Filter> filters = new ArrayList<>();
        if (!StringUtils.isEmpty(sysInnerPid) && !StringUtils.isEmpty(sysOutsidePid)) {
            String[] arrayForOutsidePid = sysOutsidePid.split(",");
            for (String pid1 : arrayForOutsidePid) {
                filters = new Filter.Builder().eq("docOutsidePid", pid1).eq("docInnerPid", sysInnerPid).eq("delflag", 1).build();
                List<DocRel> docRels = docRelService.select(filters);
                if (docRels != null && docRels.size() > 0) {
                    DocRel docRel = docRels.get(0);
                    docRel.setDelflag("0");
                    int update = docRelService.update(docRel);
                }
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } else {
            tips.put(WebCode.FAILURE_DELETE, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return tips;
    }

    /*
     * 根据行内制度主键查询已关联行外制度信息
     * */
    @RequestMapping(value = "/selectDocForInnerPid/{pid}")
    @ResponseBody
    public Map<String, Object> selectDocForInnerPid(@PathVariable String pid) {
        Map<String, Object> tips = new HashMap<>();
        List<DocRel> docsList = new ArrayList<>();
        if (!StringUtils.isEmpty(pid)) {
            List<Filter> filterForInnerDocPid = new Filter.Builder().eq("docInnerPid", pid).eq("delflag", 1).build();
            List<DocRel> docRels = docRelService.select(filterForInnerDocPid);
            List<Integer> pidOutSides = new ArrayList<>();
            if (docRels != null && docRels.size() > 0) {
                for (DocRel docRel : docRels) {
                    pidOutSides.add(docRel.getDocOutsidePid());
                }
            }
            //查询关联外规信息
            List<Filter> filterForOutside = new Filter.Builder().in("pid", pidOutSides).eq("delflag", 1).build();
            List<Doc> docList = docService.select(filterForOutside);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
            tips.put("fileData", docList);
        } else {
            tips.put(WebCode.FAULURE_OPERATE, true);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return tips;
    }


    @RequestMapping(value = "/queryDocAll")
    public String queryDocAll(Model model, HttpServletRequest request) {
        String docPid = request.getParameter("docPid");
        String docFilePids = request.getParameter("docFilePids");
        if (!StringUtils.isEmpty(docPid) && !StringUtils.isEmpty(docFilePids)) {
            List<Filter> filters = new Filter.Builder().eq("pid", docPid).eq("delflag", 1).build();
            List<Doc> docs = docService.select(filters);
            if (docs != null && docs.size() > 0) {
                model.addAttribute("doc", docs.get(0));
            }
            String[] arrFilePids = docFilePids.split(",");
            List<String> arrList = Arrays.asList(arrFilePids);
            List<String> docFilePidList = new ArrayList<>(arrList);
            List<Filter> filters1 = new Filter.Builder().eq("docPid", docPid).eq("delflag", 1).build();
            List<DocFile> docFileList = docFileService.select(filters1);
            if (docFileList != null && docFileList.size() > 0) {
                for (DocFile docFile : docFileList) {
                    docFilePidList.add(docFile.getPid().toString());
                }
            }
            String a = "";
            List<String> newList = (List) docFilePidList.stream().distinct().collect(Collectors.toList());
            for (String o : newList) {
                a = a + o + ",";
            }
            model.addAttribute("docFilePids", a);
        }
        List<String> docRelPid = new ArrayList<>();
        List<Filter> filterForRel = new Filter.Builder().eq("docInnerPid", docPid).eq("delflag", 1).build();
        List<DocRel> docRels = docRelService.select(filterForRel);
        if (docRels != null && docRels.size() > 0) {
            for (DocRel docRel : docRels) {
                docRelPid.add(docRel.getDocOutsidePid().toString());
            }
        }
        String b = "";
        List<String> newListb = (List) docRelPid.stream().distinct().collect(Collectors.toList());
        for (String o : newListb) {
            b = b + o + ",";
        }
        model.addAttribute("docOutsidePid", b);
        return "";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(@RequestBody DocInnerVo docInnerVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Integer pidForInnerDoc = null;
        if (docInnerVo != null) {
            //判断制度信息中是否有pid,是为更新,否为新增
            if (docInnerVo.getOpt() == null) {
                Random r = new Random();
                int a = (int) (r.nextDouble() * 6666666);
                Doc doc = new Doc();
                doc.setPid(a);
                doc.setDelflag("1");
                if (!StringUtils.isEmpty(docInnerVo.getSysName())) {
                    doc.setSysName(docInnerVo.getSysName());
                }
                if (!StringUtils.isEmpty(docInnerVo.getArticleNumber())) {
                    doc.setArticleNumber(docInnerVo.getArticleNumber());
                }
                if (!StringUtils.isEmpty(docInnerVo.getDispatchDep())) {
                    doc.setDispatchDep(docInnerVo.getDispatchDep());
                }
                if (!StringUtils.isEmpty(docInnerVo.getIsVal())) {
                    doc.setIsVal(docInnerVo.getIsVal());
                }
                if (docInnerVo.getBeginDate() != null) {
                    doc.setEffectiveTime(docInnerVo.getBeginDate());
                }
                if (docInnerVo.getEndDate() != null) {
                    doc.setExpiryTime(docInnerVo.getEndDate());
                }
                doc.setSystemType("I");
                doc.setCreateTime(new Date()); //创建时间
                doc.setCreateTimeString(new Date().toString()); //创建时间
                doc.setModifiedTime(new Date()); //修改时间
                doc.setModifiedTimeString(new Date().toString()); //修改时间
                int insert = docService.insert(doc);
                if (insert == 1) {
                    logger.debug("新增制度成功");
                } else {
                    logger.debug("新增制度失败");
                }
                pidForInnerDoc = a;
            } else {
                //根据主键查询
                List<Filter> filters = new Filter.Builder().eq("pid", (docInnerVo.getOpt().toString())).eq("delflag", 1).build();
                List<Doc> docs = docService.select(filters);
                if (docs != null && docs.size() > 0) {
                    Doc doc = docs.get(0);
                    if (!StringUtils.isEmpty(docInnerVo.getSysName())) {
                        doc.setSysName(docInnerVo.getSysName());
                    }
                    if (!StringUtils.isEmpty(docInnerVo.getArticleNumber())) {
                        doc.setArticleNumber(docInnerVo.getArticleNumber());
                    }
                    if (!StringUtils.isEmpty(docInnerVo.getDispatchDep())) {
                        doc.setDispatchDep(docInnerVo.getDispatchDep());
                    }
                    if (!StringUtils.isEmpty(docInnerVo.getIsVal())) {
                        doc.setIsVal(docInnerVo.getIsVal());
                    }
                    if (docInnerVo.getBeginDate() != null) {
                        doc.setEffectiveTime(docInnerVo.getBeginDate());
                    }
                    if (docInnerVo.getEndDate() != null) {
                        doc.setExpiryTime(docInnerVo.getEndDate());
                    }
                    doc.setModifiedTime(new Date()); //修改时间
                    doc.setModifiedTimeString(new Date().toString()); //修改时间
                    int update = docService.update(doc);
                    if (update == 1) {
                        logger.debug("更新制度成功");
                    } else {
                        logger.debug("更新制度失败");
                    }
                    pidForInnerDoc = new Integer(docInnerVo.getOpt().toString());
                }
            }
            if (docInnerVo.getDocTableSelectItem() != null && docInnerVo.getDocTableSelectItem().size() > 0) {
                List<Integer> newList = (List) docInnerVo.getDocTableSelectItem().stream().distinct().collect(Collectors.toList());
                for (Integer s : newList) {
                    DocFile docFile = docFileService.get(s);
                    docFile.setDocPid(pidForInnerDoc);
                    int update = docFileService.update(docFile);
                    if (update == 1) {
                        logger.debug("更新制度文件成功");
                    } else {
                        logger.debug("更新制度文件失败");
                    }
                }
            }
            if (docInnerVo.getDocTable1SelectItem() != null && docInnerVo.getDocTable1SelectItem().size() > 0) {
                List<Integer> newList = (List) docInnerVo.getDocTable1SelectItem().stream().distinct().collect(Collectors.toList());
                for (Integer s : newList) {
                    Random r = new Random();
                    int b = (int) (r.nextDouble() * 6666666);
                    DocRel docRel = new DocRel();
                    docRel.setPid(b);
                    docRel.setDocInnerPid(pidForInnerDoc);
                    docRel.setDelflag("1");
                    docRel.setDocOutsidePid(s);
                    docRel.setCreateTime(new Date()); //创建时间
                    docRel.setCreateTimeString(new Date().toString()); //创建时间
                    docRel.setModifiedTime(new Date()); //修改时间
                    docRel.setModifiedTimeString(new Date().toString()); //修改时间
                    int insert = docRelService.insert(docRel);
                    if (insert == 1) {
                        logger.debug("新增行内外制度关联成功");
                    } else {
                        logger.debug("新增行内外制度关联失败");
                    }
                }
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

}
