/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月20日 11:07:48
 *
 */

package com.yzj.jep.app.pub.web.controller;

import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.model.TraEnclosure;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.contract.util.ContractUtil;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.TraOtherService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.pub.model.ArchManageVo;
import com.yzj.jep.app.pub.model.PubFileTemplate;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.service.ArchManageService;
import com.yzj.jep.app.pub.service.PubFileTemplateService;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.app.sub.util.DownFileUtils;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.UUIDUtils;
import com.yzj.jep.app.util.WorderToNewWordUtils;
import com.yzj.jep.app.util.ZipUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.yzj.jep.commons.DateUtils.*;

@Controller
@RequestMapping("/pub/pubfile")
public class PubFileTemplateController extends CrudController<PubFileTemplate> {
    private static Logger logger = LoggerFactory.getLogger(PubFileTemplateController.class);
    @Autowired
    private PubFileTemplateService pubFileTemplateService;

    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private RelService relService;
    @Autowired
    private TraUsecreditService traUsecreditService;

    @Autowired
    private SingleRelatedPartyService singleRelatedPartyService;

    @Autowired
    private QuarterlyDetailService quarterlyDetailService;

    @Autowired
    private LegalInviolableRightsService legalInviolableRightsService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private TraInvestmentService traInvestmentService;

    @Autowired
    private TraFundsService traFundsService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private TraBusinessService traBusinessService;


    @Autowired
    private TraServiceService traServiceService;

    @Autowired
    private TraOtherService traOtherService;


    @Autowired
    private InsInvestmentService insInvestmentService;
    @Autowired
    private InsFundsService insFundsService;
    @Autowired
    private InsBusinessService insBusinessService;
    @Autowired
    private InsInterestService insInterestService;
    @Autowired
    private InsServiceService insServiceService;
    @Autowired
    private InsOtherService insOtherService;
    @Autowired
    private PublogService publogService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private DictItemService dictItemService;

    private static final Map<String, String> subjectName = new HashMap<>();

    static {

        /*
         * 根据不同的主体来添加不同的后缀
         * */
        subjectName.put("JT", "阳光保险集团股份有限公司");
        subjectName.put("SX", "阳光人寿保险股份有限公司");
        subjectName.put("CX", "阳光财产保险股份有限公司");
        subjectName.put("XB", "阳光信用保证保险股份有限公司");
        subjectName.put("ZG", "阳光资产管理股份有限公司");
    }

    private static final Map<String, String> subjectDate = new HashMap<>();

    static {

        /*
         * 根据不同的主体来添加不同的后缀
         * */
        subjectDate.put("FX", "复星保德信人寿保险有限公司");
    }

    @Value("/home/rptrs/template")
    private String disclosePath;

    @Autowired
    private Configs configs;

    @Override
    protected void prepareQuery(Page<PubFileTemplate> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        String type = request.getParameter("type");
        /*
        type   1:逐笔披露-重大模板  2：逐笔披露-统一协议模板 3：定期披露模板
         */
        if (StringUtils.isNotEmpty(type) && type.equals("1")) {
            filters.add(new Filter("tempFileTepe", type));
        } else if (StringUtils.isNotEmpty(type) && type.equals("2")) {
            filters.add(new Filter("tempFileTepe", type));
        } else if (StringUtils.isNotEmpty(type) && type.equals("3")) {
            filters.add(new Filter("tempFileTepe", type));
        }
        pubFileTemplateService.query(page, filters, orders);
    }

    /**
     * 文件下载
     *
     * @param pid
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public File download(@RequestParam("pid") int pid, HttpServletResponse response, HttpServletRequest request) {
        PubFileTemplate pubFileTemplate = pubFileTemplateService.selectById(pid);
        File file = new File(pubFileTemplate.getTempFileUrl());
        if (file == null || !file.exists()) {
            throw new RuntimeException("文件不存在");
        }
        OutputStream out = null;
        try {
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(file.getName().getBytes(), "ISO8859-1"));
            out = response.getOutputStream();
            out.write(FileUtils.readFileToByteArray(file));
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 删除
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/remove/{pid}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable Integer pid) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            PubFileTemplate pubFileTemplate = pubFileTemplateService.selectById(pid);
            pubFileTemplateService.deleteByPrimaryKey(pid);
            //删除文件
            File file = new File(pubFileTemplate.getTempFileUrl());
            //如果当前文件存在就删除文件
            if (file.exists()) {
                file.delete();
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (Exception e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return tips;
    }


    /**
     * 获取模板列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/listtemp")
    @Log(module = "披露管理", description = "查询生成披露文件列表")
    public String listtemp(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            model.addAttribute("pubSubject", "");
        } else {
            model.addAttribute("pubSubject", tenantId);
        }
        return "pub/publogtempList";
    }


    /**
     * 文件上传
     *
     * @param flag     标识符区分更新或者添加
     * @param type     上传文件类型
     * @param pid      文件的pid
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadTemplateFile/{flag}/{type}/{pid}")
    @ResponseBody
    public Map<String, Object> load(@PathVariable("flag") int flag, @PathVariable("type") int type, @PathVariable(value = "pid") int pid, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        Map<String, Object> tips = new HashMap<String, Object>();

        List<PubFileTemplate> pubFileTemplateList = new ArrayList<>();
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
                List<MultipartFile> fileList = multipartRequest.getFiles("file");
                for (MultipartFile file : fileList) {
                    if (!file.isEmpty()) {
                        String fileNameAll = file.getOriginalFilename(); //文件全名
                        String fileName = fileNameAll.substring(0, fileNameAll.lastIndexOf("."));  //文件名
                        String[] strArray = fileNameAll.split("\\.");
                        int suffixIndex = strArray.length - 1;
                        String fileType = strArray[suffixIndex] + ""; //文件类型
                        double size = file.getSize();
                        System.out.println("文件类型为 " + fileType);
                        double fileSizeMB = new BigDecimal(size / 1024 / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB
                        System.out.println(fileNameAll + "-->" + fileSizeMB + "MB");
                        String path = disclosePath;
                        File dest = new File(path + File.separator + fileNameAll);
                        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                            dest.getParentFile().mkdir();
                        }
                        file.transferTo(dest); //保存文件


                        PubFileTemplate pubFileTemplate = new PubFileTemplate();
                        if (flag == 0) {
                            //新增
                            pubFileTemplate.setTempFileName(fileName); //文件名称
                            pubFileTemplate.setFileType(fileType); //文件类型
                            pubFileTemplate.setTempFileTepe(type + "");
                            pubFileTemplate.setTempFileUrl(path + File.separator + fileNameAll); //文件路径
                            pubFileTemplate.setDelflag("1");//删除状态 0代表是（查询接口不传删除的数据) 1代表否
                            pubFileTemplate.setFileSize(fileSizeMB);//文件大小
                            pubFileTemplate.setCreateTime(new Date()); //创建时间
                            pubFileTemplate.setCreateTimeString(new Date().toString()); //创建时间
                            pubFileTemplate.setModifiedTime(new Date()); //修改时间
                            pubFileTemplate.setModifiedTimeString(new Date().toString()); //修改时间
//                            pubFileTemplate.setCreateByName(UserProfileUtils.getUserProfile().getStaffName());
                            pubFileTemplate.setTempUploadDep(UserProfileUtils.getUserProfile().getDeptCode());
                            pubFileTemplate.setTempUploadDepname(UserProfileUtils.getUserProfile().getDeptName());
                            pubFileTemplate.setTempUploadOrg(UserProfileUtils.getUserProfile().getUnitCode());
                            pubFileTemplate.setTempUploadOrgname(UserProfileUtils.getUserProfile().getUnitName());
                            pubFileTemplateService.insertSelective(pubFileTemplate);
                        } else if (flag == 1) {
                            //更新
                            pubFileTemplate = pubFileTemplateService.selectById(pid);
                            pubFileTemplate.setPid(pid);
                            pubFileTemplate.setTempFileName(fileName); //文件名称
                            pubFileTemplate.setFileType(fileType); //文件类型
                            pubFileTemplate.setTempFileTepe(type + "");
                            pubFileTemplate.setTempFileUrl(path + File.separator + fileNameAll); //文件路径
                            pubFileTemplate.setDelflag("1");//删除状态 0代表是（查询接口不传删除的数据) 1代表否
                            pubFileTemplate.setFileSize(fileSizeMB);//文件大小
                            pubFileTemplate.setCreateTime(new Date()); //创建时间
                            pubFileTemplate.setCreateTimeString(new Date().toString()); //创建时间
                            pubFileTemplate.setModifiedTime(new Date()); //修改时间
                            pubFileTemplate.setModifiedTimeString(new Date().toString()); //修改时间
                            pubFileTemplate.setTempUploadDep(UserProfileUtils.getUserProfile().getDeptCode());
                            pubFileTemplate.setTempUploadDepname(UserProfileUtils.getUserProfile().getDeptName());
//                            pubFileTemplate.setCreateByName(UserProfileUtils.getUserProfile().getStaffName());
                            pubFileTemplate.setTempUploadOrg(UserProfileUtils.getUserProfile().getUnitCode());
                            pubFileTemplate.setTempUploadOrgname(UserProfileUtils.getUserProfile().getUnitName());
                            pubFileTemplateService.updateSelective(pubFileTemplate);

                            //删除原来的文件
                            File exsitFile = new File(pubFileTemplate.getTempFileUrl());
                            if (exsitFile.exists()) {
                                exsitFile.delete();
                            }
                        }
                        pubFileTemplateList.add(pubFileTemplate);
                        tips.put(WebCode.SUCCESS, true);
                        tips.put(WebCode.MSG, "上传成功.");
                        tips.put("fileData", pubFileTemplateList);
                    } else {
                        tips.put(WebCode.SUCCESS, false);
                        tips.put(WebCode.MSG, "上传失败,未收到文件.");
                    }
                }
            }
        } catch (Exception e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传失败，系统出现未知异常.");
            logger.error("上传失败：" + e.getMessage());
        }
        return tips;
    }

    @RequestMapping("/toGenerateWord/{tranType}/{pid}/{filePid}/{fileName}/{pubType}")
    @ResponseBody
    public Map<String, Object> toword(@PathVariable("tranType") String tranType, @PathVariable("pid") Integer pid, @PathVariable("filePid") Integer filePid, @PathVariable(value = "fileName") String fileName, @PathVariable(value = "pubType") String pubType, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        Map<String, Object> res = new HashMap<>();
        String eq = null;
        PubFileTemplate pubFileTemplate = pubFileTemplateService.selectById(filePid);
        //获取当前登录用户
        UserProfile profile = UserProfileUtils.getUserProfile();
        //主体全称
        String tranName = subjectName.get(profile.getTenantId());
        //简称
        String tran = subjectDate.get(profile.getTenantId());
        //模板文件地址
        String inputUrl = pubFileTemplate.getTempFileUrl();
        //拿文件后缀 String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String suffix = inputUrl.substring(inputUrl.lastIndexOf("."));
        //新生产的模板文件

        String outputUrl = disclosePath + "/" + fileName + suffix;
        File dest = new File(outputUrl);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        //文件存放路径
//        String FilerUrl = disclosePath + '\\' + uuid;
        List<Filter> filters3 = new Filter.Builder().eq("dictCode", "BUS-RelZZD").build();
        Map<String, Object> map = new HashMap<>();
        List<DictItem> dictItems3 = dictItemService.select(filters3);
        Map relPsnOrgMap = new HashMap();
        for (DictItem dic : dictItems3) {
            relPsnOrgMap.put(dic.getItemValue(), dic.getItemLabel());
        }
        if (tranType.equals("01")) {
            //投资入股类
            InsInvestment insInvestment = null;
            try {
                insInvestment = insInvestmentService.get(pid);
                if ("".equals(eq) || eq == null) {
                    //生成流水号
                    eq = BatchNumberUtil.makeBatchNumber();
                }
                if (insInvestment.getRelType().equalsIgnoreCase("org")) {

                    RelOrg relOrg = new RelOrg();
                    relOrg.setPid(insInvestment.getRelId());
                    StringBuilder stringBuilder = new StringBuilder();
                    relOrg = relOrgService.get(relOrg);
                    if (relOrg != null) {
                        map.put("relName", StringUtils.isNotEmpty(relOrg.getOrgName()) ? relOrg.getOrgName() : "");//关联方名称
                        map.put("regAddress", StringUtils.isNotEmpty(relOrg.getRegAddress()) ? relOrg.getRegAddress() : "");//注册地址
                        map.put("legalPerson", StringUtils.isNotEmpty(relOrg.getLegalPerson()) ? relOrg.getLegalPerson() : "");//法定代表人
                        map.put("orgType", StringUtils.isNotEmpty(selectoneByValue("BUS-CompanyType", relOrg.getOrgNature())) ? selectoneByValue("BUS-orgNature", relOrg.getOrgNature()) : "");//企业类型
                        map.put("uniscCode", StringUtils.isNotEmpty(relOrg.getUniscCode()) ? relOrg.getUniscCode() : "");//统一社会信用代码
                        map.put("regcagital", (relOrg.getRegCapital() != null) ? relOrg.getRegCapital().toString() + "万人民币" : "");//注册资本
                        map.put("businessScope", StringUtils.isNotEmpty(relOrg.getBusinessScope()) ? relOrg.getBusinessScope() : "");//经营范围

                        String queryRelation = queryRelation(stringBuilder, relOrg, tranName, relPsnOrgMap);
                        map.put("queryRelation", StringUtils.isNotEmpty(queryRelation) ? queryRelation : "");

                    }

                } else if (insInvestment.getRelType().equalsIgnoreCase("psn")) {

                    RelPsn relPsn = new RelPsn();
                    relPsn.setPid(insInvestment.getRelId());
                    List<RelPsn> relPsnList = relPsnService.select(relPsn);

                    if (relPsnList.size() > 0) {
                        relPsn = relPsnList.get(0);
                        map.put("orgType", "");
                        map.put("businessScope", "");
                        map.put("regCapital", "");
                    }
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            String tranTypeStr = selectoneByValue("INS-tranType", insInvestment.getTranType());
            //标题
            map.put("title", "关于" + tranName + "与" + insInvestment.getRelName() + selectoneByValue("INS-tranType", insInvestment.getTranType()) + "重大关联交易的信息披露公告");
            //
            if (insInvestment.getMajorTrade().equals("1")) {
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + insInvestment.getRelName() + "发生" + tranTypeStr + "交易构成单笔重大关联交易，现将相关情况信息披露如下：");
                map.put("tranTypeString", "本次交易为" + tranTypeStr + "关联交易。本次关联交易金额已达到 3000 万元以上，且占本公司上一年度末经审计的净资产的1%以上，属于重大关联交易。");
            } else if (insInvestment.getMajorTrade().equals("2")) {
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + insInvestment.getRelName() + "发生" + tranTypeStr + "交易构成累积重大关联交易，现将相关情况信息披露如下：");
                map.put("tranTypeString", "本次交易为" + tranTypeStr + "关联交易。与关联方本年度内累计发生交易金额已达到 3000 万元以上，且占本公司上一年度末经审计的净资产的1%以上，属于重大关联交易。");
            }
            //交易价格
            map.put("tradePrice", (insInvestment.getTradePrice() != null) ? insInvestment.getTradePrice() : "");
            //定价政策
            map.put("pricingPolicy", StringUtils.isNotEmpty(insInvestment.getPricingPolicy()) ? insInvestment.getPricingPolicy() : "");
            //交易标的
            map.put("tradeSubject", StringUtils.isNotEmpty(insInvestment.getTradeSubject()) ? insInvestment.getTradeSubject() : "");
//          //本年度与该关联方已发生的关联交易累计金额
            map.put("amount", (insInvestment.getTotolTradeAmount() != null) ? insInvestment.getTotolTradeAmount().toString() : "");
            //交易对公司本期和未来财务及经营状况的影响
            map.put("financialImpact", StringUtils.isNotEmpty(insInvestment.getFinancialImpact()) ? insInvestment.getFinancialImpact() : "");

        } else if (tranType.equals("02")) {
            InsFunds insFunds = new InsFunds();
            //资金运营类
            try {

                insFunds = insFundsService.get(pid);
                if ("".equals(eq) || eq == null) {
                    //生成流水号
                    eq = BatchNumberUtil.makeBatchNumber();
                }
                if (insFunds.getRelType().equalsIgnoreCase("org")) {

                    RelOrg relOrg = new RelOrg();
                    relOrg.setPid(insFunds.getRelId());
                    StringBuilder stringBuilder = new StringBuilder();
                    relOrg = relOrgService.get(relOrg);

                    if (relOrg != null) {
                        map.put("relName", StringUtils.isNotEmpty(relOrg.getOrgName()) ? relOrg.getOrgName() : "");//关联方名称
                        map.put("regAddress", StringUtils.isNotEmpty(relOrg.getRegAddress()) ? relOrg.getRegAddress() : "");//注册地址
                        map.put("legalPerson", StringUtils.isNotEmpty(relOrg.getLegalPerson()) ? relOrg.getLegalPerson() : "");//法定代表人
                        map.put("orgType", StringUtils.isNotEmpty(selectoneByValue("BUS-orgNature", relOrg.getOrgNature())) ? selectoneByValue("BUS-orgNature", relOrg.getOrgNature()) : "");//企业类型
                        map.put("uniscCode", StringUtils.isNotEmpty(relOrg.getUniscCode()) ? relOrg.getUniscCode() : "");//统一社会信用代码
                        map.put("regCapital", (relOrg.getRegCapital() != null) ? relOrg.getRegCapital().toString() + "万人民币" : "");//注册资本
                        map.put("businessScope", StringUtils.isNotEmpty(relOrg.getBusinessScope()) ? relOrg.getBusinessScope() : "");//经营范围

                        String queryRelation = queryRelation(stringBuilder, relOrg, tranName, relPsnOrgMap);
                        map.put("queryRelation", StringUtils.isNotEmpty(queryRelation) ? queryRelation : "");

                    }
                } else if (insFunds.getRelType().equalsIgnoreCase("psn")) {

                    RelPsn relPsn = new RelPsn();
                    relPsn.setPid(insFunds.getRelId());
                    List<RelPsn> relPsnList = relPsnService.select(relPsn);

                    if (relPsnList.size() > 0) {
                        relPsn = relPsnList.get(0);
                        map.put("orgType", "");
                        map.put("businessScope", "");
                        map.put("regCapital", "");
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            String tranTypeStr = selectoneByValue("INS-tranType", insFunds.getTranType());
            //标题
            map.put("title", "关于" + tranName + "与" + insFunds.getRelName() + selectoneByValue("INS-tranType", insFunds.getTranType()) + "重大关联交易的信息披露公告");
            //
            if (insFunds.getMajorTrade().equals("1")) {
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + insFunds.getRelName() + "发生" + tranTypeStr + "交易构成单笔重大关联交易，现将相关情况信息披露如下：");
                map.put("tranTypeString", "本次交易为" + tranTypeStr + "关联交易。本次关联交易金额已达到 3000 万元以上，且占本公司上一年度末经审计的净资产的1%以上，属于重大关联交易。");
            } else if (insFunds.getMajorTrade().equals("2")) {
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + insFunds.getRelName() + "发生" + tranTypeStr + "交易构成累积重大关联交易，现将相关情况信息披露如下：");
                map.put("tranTypeString", "本次交易为" + tranTypeStr + "关联交易。与关联方本年度内累计发生交易金额已达到 3000 万元以上，且占本公司上一年度末经审计的净资产的1%以上，属于重大关联交易。");
            }
            //交易价格
            map.put("tradePrice", (insFunds.getTradePrice() != null) ? insFunds.getTradePrice() : "");
            //定价政策
            map.put("pricingPolicy", StringUtils.isNotEmpty(insFunds.getPricingPolicy()) ? insFunds.getPricingPolicy() : "");
            //交易标的
            map.put("tradeSubject", StringUtils.isNotEmpty(insFunds.getTradeSubject()) ? insFunds.getTradeSubject() : "");
//          //本年度与该关联方已发生的关联交易累计金额
            map.put("amount", (insFunds.getTotolTradeAmount() != null) ? insFunds.getTotolTradeAmount().toString() : "");
            //交易对公司本期和未来财务及经营状况的影响
            map.put("financialImpact", StringUtils.isNotEmpty(insFunds.getFinancialImpact()) ? insFunds.getFinancialImpact() : "");

        } else if (tranType.equals("03")) {
            InsBusiness business = null;
            try {
                //保险业务类

                business = insBusinessService.get(pid);
                if ("".equals(eq) || eq == null) {
                    //生成流水号
                    eq = BatchNumberUtil.makeBatchNumber();
                }


                if (business.getRelType().equalsIgnoreCase("org")) {

                    RelOrg relOrg = new RelOrg();
                    relOrg.setPid(business.getRelId());
                    StringBuilder stringBuilder = new StringBuilder();
                    relOrg = relOrgService.get(relOrg);
                    if (relOrg != null) {
                        map.put("relName", StringUtils.isNotEmpty(relOrg.getOrgName()) ? relOrg.getOrgName() : "");//关联方名称
                        map.put("regAddress", StringUtils.isNotEmpty(relOrg.getRegAddress()) ? relOrg.getRegAddress() : "");//注册地址
                        map.put("legalPerson", StringUtils.isNotEmpty(relOrg.getLegalPerson()) ? relOrg.getLegalPerson() : "");//法定代表人
                        map.put("orgType", StringUtils.isNotEmpty(selectoneByValue("BUS-orgNature", relOrg.getOrgNature())) ? selectoneByValue("BUS-orgNature", relOrg.getOrgNature()) : "");//企业类型
                        map.put("uniscCode", StringUtils.isNotEmpty(relOrg.getUniscCode()) ? relOrg.getUniscCode() : "");//统一社会信用代码
                        map.put("regCapital", (relOrg.getRegCapital() != null) ? relOrg.getRegCapital().toString() + "万人民币" : "");//注册资本
                        map.put("businessScope", StringUtils.isNotEmpty(relOrg.getBusinessScope()) ? relOrg.getBusinessScope() : "");//经营范围

                        String queryRelation = queryRelation(stringBuilder, relOrg, tranName, relPsnOrgMap);
                        map.put("queryRelation", StringUtils.isNotEmpty(queryRelation) ? queryRelation : "");

                    }


                } else if (business.getRelType().equalsIgnoreCase("psn")) {

                    RelPsn relPsn = new RelPsn();
                    relPsn.setPid(business.getRelId());
                    List<RelPsn> relPsnList = relPsnService.select(relPsn);

                    if (relPsnList.size() > 0) {
                        relPsn = relPsnList.get(0);
                        map.put("orgType", "");
                        map.put("businessScope", "");
                        map.put("regCapital", "");
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String tranTypeStr = selectoneByValue("INS-tranType", business.getTranType());
            if ("1".equals(business.getIsAgreement())) {
                //统一交易协议标题
                String year = String.valueOf(DateUtils.getYear(business.getTranDate()));
                String month = String.valueOf(DateUtils.getMonth(business.getTranDate()));
                String date = String.valueOf(DateUtils.getDate(business.getTranDate()));
                map.put("title", tranName + "关于与" + business.getRelName() + "签订《XX合同》暨统一交易协议的重大关联交易信息披露公告");
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + business.getRelName() + "签订《XX合同》暨统一交易协议的重大关联交易事项披露如下：");
                map.put("tranTypeString", year + " 年 " + month + " 月 " + date + " 日，" + tran + "与" + business.getRelName() + "签署《xx合作协议》，本次交易为" + tranTypeStr + "关联交易。");
            } else if (business.getMajorTrade().equals("1")) {
                map.put("title", "关于" + tranName + "与" + business.getRelName() + tranTypeStr + "重大关联交易的信息披露公告");
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + business.getRelName() + "发生" + tranTypeStr + "交易构成单笔重大关联交易，现将相关情况信息披露如下：");
                map.put("tranTypeString", "本次交易为" + tranTypeStr + "关联交易。本次关联交易金额已达到 3000 万元以上，且占本公司上一年度末经审计的净资产的1%以上，属于重大关联交易。");
            } else if (business.getMajorTrade().equals("2")) {
                map.put("title", "关于" + tranName + "与" + business.getRelName() + tranTypeStr + "重大关联交易的信息披露公告");
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + business.getRelName() + "发生" + tranTypeStr + "交易构成累积重大关联交易，现将相关情况信息披露如下：");
                map.put("tranTypeString", "本次交易为" + tranTypeStr + "关联交易。与关联方本年度内累计发生交易金额已达到 3000 万元以上，且占本公司上一年度末经审计的净资产的1%以上，属于重大关联交易。");
            }

            //交易价格
            map.put("tradePrice", (business.getTradePrice() != null) ? business.getTradePrice() : "");
            //定价政策
            map.put("pricingPolicy", StringUtils.isNotEmpty(business.getPricingPolicy()) ? business.getPricingPolicy() : "");
            //交易标的
            map.put("tradeSubject", StringUtils.isNotEmpty(business.getTradeSubject()) ? business.getTradeSubject() : "");
//          //本年度与该关联方已发生的关联交易累计金额
            map.put("amount", (business.getTotolTradeAmount() != null) ? business.getTotolTradeAmount().toString() : "");
            //交易对公司本期和未来财务及经营状况的影响
            map.put("financialImpact", StringUtils.isNotEmpty(business.getFinancialImpact()) ? business.getFinancialImpact() : "");
        } else if (tranType.equals("04")) {
            InsInterest insInterest = null;
            try {
                //保险业务类

                insInterest = insInterestService.get(pid);
                if ("".equals(eq) || eq == null) {
                    //生成流水号
                    eq = BatchNumberUtil.makeBatchNumber();
                }

                if (insInterest.getRelType().equalsIgnoreCase("org")) {

                    RelOrg relOrg = new RelOrg();
                    relOrg.setPid(insInterest.getRelId());
                    StringBuilder stringBuilder = new StringBuilder();
                    relOrg = relOrgService.get(relOrg);
                    if (relOrg != null) {
                        map.put("relName", StringUtils.isNotEmpty(relOrg.getOrgName()) ? relOrg.getOrgName() : "");//关联方名称
                        map.put("regAddress", StringUtils.isNotEmpty(relOrg.getRegAddress()) ? relOrg.getRegAddress() : "");//注册地址
                        map.put("legalPerson", StringUtils.isNotEmpty(relOrg.getLegalPerson()) ? relOrg.getLegalPerson() : "");//法定代表人
                        map.put("orgType", StringUtils.isNotEmpty(selectoneByValue("BUS-orgNature", relOrg.getOrgNature())) ? selectoneByValue("BUS-orgNature", relOrg.getOrgNature()) : "");//企业类型
                        map.put("uniscCode", StringUtils.isNotEmpty(relOrg.getUniscCode()) ? relOrg.getUniscCode() : "");//统一社会信用代码
                        map.put("regCapital", (relOrg.getRegCapital() != null) ? relOrg.getRegCapital().toString() + "万人民币" : "");//注册资本
                        map.put("businessScope", StringUtils.isNotEmpty(relOrg.getBusinessScope()) ? relOrg.getBusinessScope() : "");//经营范围

                        String queryRelation = queryRelation(stringBuilder, relOrg, tranName, relPsnOrgMap);
                        map.put("queryRelation", StringUtils.isNotEmpty(queryRelation) ? queryRelation : "");
                    }


                } else if (insInterest.getRelType().equalsIgnoreCase("psn")) {

                    RelPsn relPsn = new RelPsn();
                    relPsn.setPid(insInterest.getRelId());
                    List<RelPsn> relPsnList = relPsnService.select(relPsn);

                    if (relPsnList.size() > 0) {
                        relPsn = relPsnList.get(0);
                        map.put("orgType", "");
                        map.put("businessScope", "");
                        map.put("regCapital", "");
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String tranTypeStr = selectoneByValue("INS-tranType", insInterest.getTranType());
            if ("1".equals(insInterest.getIsAgreement())) {
                //统一交易协议标题
                String year = String.valueOf(DateUtils.getYear(insInterest.getTranDate()));
                String month = String.valueOf(DateUtils.getMonth(insInterest.getTranDate()));
                String date = String.valueOf(DateUtils.getDate(insInterest.getTranDate()));
                map.put("title", tranName + "关于与" + insInterest.getRelName() + "签订《XX合同》暨统一交易协议的重大关联交易信息披露公告");
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + insInterest.getRelName() + "签订《XX合同》暨统一交易协议的重大关联交易事项披露如下：");
                map.put("tranTypeString", year + " 年 " + month + " 月 " + date + " 日，" + tran + "与" + insInterest.getRelName() + "签署《xx合作协议》，本次交易为" + tranTypeStr + "关联交易。");
            } else if (insInterest.getMajorTrade().equals("1")) {
                map.put("title", "关于" + tranName + "与" + insInterest.getRelName() + tranTypeStr + "重大关联交易的信息披露公告");
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + insInterest.getRelName() + "发生" + tranTypeStr + "交易构成单笔重大关联交易，现将相关情况信息披露如下：");
                map.put("tranTypeString", "本次交易为" + tranTypeStr + "关联交易。本次关联交易金额已达到 3000 万元以上，且占本公司上一年度末经审计的净资产的1%以上，属于重大关联交易。");
            } else if (insInterest.getMajorTrade().equals("2")) {
                map.put("title", "关于" + tranName + "与" + insInterest.getRelName() + tranTypeStr + "重大关联交易的信息披露公告");
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + insInterest.getRelName() + "发生" + tranTypeStr + "交易构成累积重大关联交易，现将相关情况信息披露如下：");
                map.put("tranTypeString", "本次交易为" + tranTypeStr + "关联交易。与关联方本年度内累计发生交易金额已达到 3000 万元以上，且占本公司上一年度末经审计的净资产的1%以上，属于重大关联交易。");
            }

            //交易价格
            map.put("tradePrice", (insInterest.getTradePrice() != null) ? insInterest.getTradePrice() : "");
            //定价政策
            map.put("pricingPolicy", StringUtils.isNotEmpty(insInterest.getPricingPolicy()) ? insInterest.getPricingPolicy() : "");
            //交易标的
            map.put("tradeSubject", StringUtils.isNotEmpty(insInterest.getTradeSubject()) ? insInterest.getTradeSubject() : "");
//          //本年度与该关联方已发生的关联交易累计金额
            map.put("amount", (insInterest.getTotolTradeAmount() != null) ? insInterest.getTotolTradeAmount().toString() : "");
            //交易对公司本期和未来财务及经营状况的影响
            map.put("financialImpact", StringUtils.isNotEmpty(insInterest.getFinancialImpact()) ? insInterest.getFinancialImpact() : "");
        } else if (tranType.equals("05")) {
            InsService insService = null;
            try {
                //保险业务类

                insService = insServiceService.get(pid);
                if ("".equals(eq) || eq == null) {
                    //生成流水号
                    eq = BatchNumberUtil.makeBatchNumber();
                }

                if (insService.getRelType().equalsIgnoreCase("org")) {

                    RelOrg relOrg = new RelOrg();
                    relOrg.setPid(insService.getRelId());
                    StringBuilder stringBuilder = new StringBuilder();
                    relOrg = relOrgService.get(relOrg);
                    if (relOrg != null) {
                        map.put("relName", StringUtils.isNotEmpty(relOrg.getOrgName()) ? relOrg.getOrgName() : "");//关联方名称
                        map.put("regAddress", StringUtils.isNotEmpty(relOrg.getRegAddress()) ? relOrg.getRegAddress() : "");//注册地址
                        map.put("legalPerson", StringUtils.isNotEmpty(relOrg.getLegalPerson()) ? relOrg.getLegalPerson() : "");//法定代表人
                        map.put("orgType", StringUtils.isNotEmpty(selectoneByValue("BUS-orgNature", relOrg.getOrgNature())) ? selectoneByValue("BUS-orgNature", relOrg.getOrgNature()) : "");//企业类型
                        map.put("uniscCode", StringUtils.isNotEmpty(relOrg.getUniscCode()) ? relOrg.getUniscCode() : "");//统一社会信用代码
                        map.put("regCapital", (relOrg.getRegCapital() != null) ? relOrg.getRegCapital().toString() + "万人民币" : "");//注册资本
                        map.put("businessScope", StringUtils.isNotEmpty(relOrg.getBusinessScope()) ? relOrg.getBusinessScope() : "");//经营范围

                        String queryRelation = queryRelation(stringBuilder, relOrg, tranName, relPsnOrgMap);
                        map.put("queryRelation", StringUtils.isNotEmpty(queryRelation) ? queryRelation : "");
                    }


                } else if (insService.getRelType().equalsIgnoreCase("psn")) {

                    RelPsn relPsn = new RelPsn();
                    relPsn.setPid(insService.getRelId());
                    List<RelPsn> relPsnList = relPsnService.select(relPsn);

                    if (relPsnList.size() > 0) {
                        relPsn = relPsnList.get(0);
                        map.put("orgType", "");
                        map.put("businessScope", "");
                        map.put("regCapital", "");
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String tranTypeStr = selectoneByValue("INS-tranType", insService.getTranType());
            if ("1".equals(insService.getIsAgreement())) {
                //统一交易协议标题
                String year = String.valueOf(DateUtils.getYear(insService.getTranDate()));
                String month = String.valueOf(DateUtils.getMonth(insService.getTranDate()));
                String date = String.valueOf(DateUtils.getDate(insService.getTranDate()));
                map.put("title", tranName + "关于与" + insService.getRelName() + "签订《XX合同》暨统一交易协议的重大关联交易信息披露公告");
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + insService.getRelName() + "签订《XX合同》暨统一交易协议的重大关联交易事项披露如下：");
                map.put("tranTypeString", year + " 年 " + month + " 月 " + date + " 日，" + tran + "与" + insService.getRelName() + "签署《xx合作协议》，本次交易为" + tranTypeStr + "关联交易。");
            } else if (insService.getMajorTrade().equals("1")) {
                map.put("title", "关于" + tranName + "与" + insService.getRelName() + tranTypeStr + "重大关联交易的信息披露公告");
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + insService.getRelName() + "发生" + tranTypeStr + "交易构成单笔重大关联交易，现将相关情况信息披露如下：");
                map.put("tranTypeString", "本次交易为" + tranTypeStr + "关联交易。本次关联交易金额已达到 3000 万元以上，且占本公司上一年度末经审计的净资产的1%以上，属于重大关联交易。");
            } else if (insService.getMajorTrade().equals("2")) {
                map.put("title", "关于" + tranName + "与" + insService.getRelName() + tranTypeStr + "重大关联交易的信息披露公告");
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + insService.getRelName() + "发生" + tranTypeStr + "交易构成累积重大关联交易，现将相关情况信息披露如下：");
                map.put("tranTypeString", "本次交易为" + tranTypeStr + "关联交易。与关联方本年度内累计发生交易金额已达到 3000 万元以上，且占本公司上一年度末经审计的净资产的1%以上，属于重大关联交易。");
            }

            //交易价格
            map.put("tradePrice", (insService.getTradePrice() != null) ? insService.getTradePrice() : "");
            //定价政策
            map.put("pricingPolicy", StringUtils.isNotEmpty(insService.getPricingPolicy()) ? insService.getPricingPolicy() : "");
            //交易标的
            map.put("tradeSubject", StringUtils.isNotEmpty(insService.getTradeSubject()) ? insService.getTradeSubject() : "");
//          //本年度与该关联方已发生的关联交易累计金额
            map.put("amount", (insService.getTotolTradeAmount() != null) ? insService.getTotolTradeAmount().toString() : "");
            //交易对公司本期和未来财务及经营状况的影响
            map.put("financialImpact", StringUtils.isNotEmpty(insService.getFinancialImpact()) ? insService.getFinancialImpact() : "");
        } else if (tranType.equals("06")) {
            InsOther insOther = null;
            try {
                insOther = insOtherService.get(pid);
                if ("".equals(eq) || eq == null) {
                    //生成流水号
                    eq = BatchNumberUtil.makeBatchNumber();
                }

                if (insOther.getRelType().equalsIgnoreCase("org")) {

                    RelOrg relOrg = new RelOrg();
                    relOrg.setPid(insOther.getRelId());
                    StringBuilder stringBuilder = new StringBuilder();
                    relOrg = relOrgService.get(relOrg);
                    if (relOrg != null) {
                        map.put("relName", StringUtils.isNotEmpty(relOrg.getOrgName()) ? relOrg.getOrgName() : "");//关联方名称
                        map.put("regAddress", StringUtils.isNotEmpty(relOrg.getRegAddress()) ? relOrg.getRegAddress() : "");//注册地址
                        map.put("legalPerson", StringUtils.isNotEmpty(relOrg.getLegalPerson()) ? relOrg.getLegalPerson() : "");//法定代表人
                        map.put("orgType", StringUtils.isNotEmpty(selectoneByValue("BUS-orgNature", relOrg.getOrgNature())) ? selectoneByValue("BUS-orgNature", relOrg.getOrgNature()) : "");//企业类型
                        map.put("uniscCode", StringUtils.isNotEmpty(relOrg.getUniscCode()) ? relOrg.getUniscCode() : "");//统一社会信用代码
                        map.put("regCapital", (relOrg.getRegCapital() != null) ? relOrg.getRegCapital().toString() + "万人民币" : "");//注册资本
                        map.put("businessScope", StringUtils.isNotEmpty(relOrg.getBusinessScope()) ? relOrg.getBusinessScope() : "");//经营范围

                        String queryRelation = queryRelation(stringBuilder, relOrg, tranName, relPsnOrgMap);
                        map.put("queryRelation", StringUtils.isNotEmpty(queryRelation) ? queryRelation : "");

                    }

                } else if (insOther.getRelType().equalsIgnoreCase("psn")) {

                    RelPsn relPsn = new RelPsn();
                    relPsn.setPid(insOther.getRelId());
                    List<RelPsn> relPsnList = relPsnService.select(relPsn);

                    if (relPsnList.size() > 0) {
                        relPsn = relPsnList.get(0);
                        map.put("orgType", "");
                        map.put("businessScope", "");
                        map.put("regCapital", "");
                    }
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            String tranTypeStr = selectoneByValue("INS-tranType", insOther.getTranType());
            //标题
            map.put("title", "关于" + tranName + "与" + insOther.getRelName() + selectoneByValue("INS-tranType", insOther.getTranType()) + "重大关联交易的信息披露公告");
            //
            if (insOther.getMajorTrade().equals("1")) {
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + insOther.getRelName() + "发生" + tranTypeStr + "交易构成单笔重大关联交易，现将相关情况信息披露如下：");
                map.put("tranTypeString", "本次交易为" + tranTypeStr + "关联交易。本次关联交易金额已达到 3000 万元以上，且占本公司上一年度末经审计的净资产的1%以上，属于重大关联交易。");
            } else if (insOther.getMajorTrade().equals("2")) {
                map.put("content", "根据中国银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35 号）的规定，" + tranName + "（以下简称“" + tran + "”或“我公司”与" + insOther.getRelName() + "发生" + tranTypeStr + "交易构成累积重大关联交易，现将相关情况信息披露如下：");
                map.put("tranTypeString", "本次交易为" + tranTypeStr + "关联交易。与关联方本年度内累计发生交易金额已达到 3000 万元以上，且占本公司上一年度末经审计的净资产的1%以上，属于重大关联交易。");
            }
            //交易价格
            map.put("tradePrice", (insOther.getTradePrice() != null) ? insOther.getTradePrice() : "");
            //定价政策
            map.put("pricingPolicy", StringUtils.isNotEmpty(insOther.getPricingPolicy()) ? insOther.getPricingPolicy() : "");
            //交易标的
            map.put("tradeSubject", StringUtils.isNotEmpty(insOther.getTradeSubject()) ? insOther.getTradeSubject() : "");
//          //本年度与该关联方已发生的关联交易累计金额
            map.put("amount", (insOther.getTotolTradeAmount() != null) ? insOther.getTotolTradeAmount().toString() : "");
            //交易对公司本期和未来财务及经营状况的影响
            map.put("financialImpact", StringUtils.isNotEmpty(insOther.getFinancialImpact()) ? insOther.getFinancialImpact() : "");

        }


        //避免当前代码问题影响其他数据
        try {
            Calendar calendar = Calendar.getInstance();
            Integer year = calendar.get(Calendar.YEAR);
            Integer month = calendar.get(Calendar.MONTH) + 1; //第一个月从0开始，所以得到月份＋1
            Integer day = calendar.get(Calendar.DAY_OF_MONTH);
            Integer quarter = month % 3 == 0 ? month / 3 : month / 3 + 1;

            map.put("nowYear", String.valueOf(year));
            map.put("nowMonth", String.valueOf(month));
            map.put("nowDay", String.valueOf(day));
            map.put("quarter", String.valueOf(quarter));
        } catch (Exception e) {
        }


        boolean changWord = WorderToNewWordUtils.changWord(inputUrl, outputUrl, map, new ArrayList<>());
        if (changWord) {
            try {
                //更新报送表中状态
                setPubStu(tranType, pid, eq);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //查询
            Publog publog = new Publog();
            //根据relId和交易类型
            publog.setRelId(Integer.valueOf(pid));
            publog.setTranType(tranType);

          /*  List<Publog> publogs = publogService.select(publog);
            if (CollectionUtils.isNotEmpty(publogs) && publogs.size() == 1) {
                //更新
                publog = publogs.get(0);
                publog.setPubFileName(fileName);//文件名
                publog.setPubSubject(UserProfileUtils.getUserProfile().getDeptName());//披露部门
                publog.setCreateByName(UserProfileUtils.getUserProfile().getStaffName());//操作员
                publog.setMaxPercentInNetAssets(new Date());//生成文件时间
                publog.setDownAmt(0); //下载数
                publog.setDownStatus("1");//下载状态
                publog.setPubType(pubType); //披露类型
                publog.setTranType(tranType); //交易类型
                publog.setFileUrl(FilerUrl);//文件路径
                publogService.updateSelective(publog);
            } else if (CollectionUtils.isEmpty(publogs) && publogs.size() == 0) {*/
            //新增
            publog.setPubFileName(fileName + suffix);//文件名
            publog.setPubSubject(UserProfileUtils.getUserProfile().getDeptName());//披露部门
            publog.setCreateByName(UserProfileUtils.getUserProfile().getStaffName());//操作员
            publog.setMaxPercentInNetAssets(new Date());//生成文件时间
            publog.setPubId(eq);
            publog.setDownAmt(0);//下载数
            publog.setStatus("0");//状态
            publog.setDownStatus("1");//下载状态
            publog.setPubType(pubType);//披露类型
            publog.setTranType(tranType);
            publog.setFileUrl(outputUrl);
            publogService.insertSelective(publog);
            res.put(WebCode.SUCCESS, true);
            res.put(WebCode.MSG, WebCode.FAILURE_SUCCESS_PUBFILE);
            return res;
        } else {
            res.put(WebCode.SUCCESS, false);
            res.put(WebCode.MSG, WebCode.FAILURE_FAILED_PUBFILE);
            return res;
        }
    }

    /*
    更新报送表已披露状态
     */
    public void setPubStu(String tranType, Integer pid, String eq) {
        if (tranType.equals("01")) {
            InsInvestment insInvestment = insInvestmentService.get(pid);
            insInvestment.setPubId(eq);
            insInvestment.setIsDiscovery("1");
            insInvestmentService.updateSelective(insInvestment);
        } else if (tranType.equals("02")) {
            InsFunds insFunds = insFundsService.get(pid);
            insFunds.setIsDiscovery("1");
            insFunds.setPubId(eq);
            insFundsService.updateSelective(insFunds);
        } else if (tranType.equals("03")) {
            InsBusiness insBusiness = insBusinessService.get(pid);
            insBusiness.setIsDiscovery("1");
            insBusiness.setPubId(eq);
            insBusinessService.updateSelective(insBusiness);
        } else if (tranType.equals("04")) {
            InsInterest insInterest = insInterestService.get(pid);
            insInterest.setIsDiscovery("1");
            insInterest.setPubId(eq);
            insInterestService.updateSelective(insInterest);
        } else if (tranType.equals("05")) {
            InsService insService = insServiceService.get(pid);
            insService.setIsDiscovery("1");
            insService.setPubId(eq);
            insServiceService.updateSelective(insService);
        } else if (tranType.equals("06")) {
            InsOther insOther = insOtherService.get(pid);
            insOther.setIsDiscovery("1");
            insOther.setPubId(eq);
            insOtherService.updateSelective(insOther);
        }
    }

    //----------------------------------------
    public String selectoneByValue(String value, String simpleName) {
        List<NameValuePair> items = new ArrayList<>();
        try {
            DictItem dictItem = new DictItem();
            dictItem.setDictCode(value);
            dictItem.setItemValue(simpleName);
            List<DictItem> dictItemList = dictItemService.select(dictItem);
            dictItem = dictItemList.get(0);
            return dictItem.getItemLabel();
        } catch (Exception e) {
        }
        return null;
    }

    //----------------------------------------
    public String selectByValue(String value, String simpleName) {
        List<NameValuePair> items = new ArrayList<>();
        try {
            List<Filter> filters = new Filter.Builder().eq("dictCode", simpleName).build();
            List<DictItem> list = dictItemService.select(filters);
            //过滤掉不要的字典项
            if (!org.springframework.util.CollectionUtils.isEmpty(list)) {
                for (DictItem dictItem : list) {
                    if (dictItem.getItemValue().equals(value)) {
                        return dictItem.getItemLabel();
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * doc转pdf
     *
     * @param
     * @param
     * @return
     */
   /* @ResponseBody
    @RequestMapping(value = "word2Pdf", method = RequestMethod.POST)
    public Map<String, String> word2Pdf(@RequestParam("fileUrl") String fileUrl, @RequestParam("fileName") String fileName) {
        Doc2Pdf.doc2pdf(fileUrl, disclosePath + "\\" + fileName + ".pdf");
        HashMap<String, String> map = new HashMap<>();
        map.put("code", "200");
        return map;
    }*/
    public String queryRelation(StringBuilder stringBuilder, RelOrg relOrg, String orgName, Map relPsnOrgMap) {
        List<Filter> filters = null;
        List<String> list = relService.selectByFormPid(relOrg.getPid().toString());//获取上层机构id
        if (list.size() == 0 || list == null) {
            return stringBuilder.toString();
        }

        for (String s : list) {
            RelPsn psn = new RelPsn();
            RelOrg org = relOrgService.get(Integer.valueOf(s));//获取上层机构
            if (org == null) {
                psn = relPsnService.get(Integer.valueOf(s));
                filters = new Filter.Builder().eq("fromIdentifyNumber", relOrg.getUniscCode()).eq("toIdentifyNumber", psn.getIdentifyNumber()).build();
            } else {
                filters = new Filter.Builder().eq("fromIdentifyNumber", relOrg.getUniscCode()).eq("toIdentifyNumber", org.getUniscCode()).build();
            }
            List<Rel> rels = relService.select(filters);//获取上层机构与本层关系
            if (rels.size() == 0 || rels == null) {
                continue;
            }
            for (Rel rel : rels) {
                String orgMap = String.valueOf(relPsnOrgMap.get(rel.getRelPsnOrg()));
                if (org != null) {
                    if (org.getOrgName().equals(orgName)) {
                        stringBuilder.append("本层关联方：" + relOrg.getOrgName() + "，前一层关联方：" + orgName + "，关联关系说明：" + orgMap + "。");
                        return stringBuilder.toString();
                    } else {
                        stringBuilder.append("本层关联方：" + relOrg.getOrgName() + "，前一层关联方：" + org.getOrgName() + "，关联关系说明：" + orgMap + "；");
                        stringBuilder.append("\r\n");
                        queryRelation(stringBuilder, org, orgName, relPsnOrgMap);
                    }
                } else {
                    stringBuilder.append("本层关联方：" + relOrg.getOrgName() + "，前一层关联方：" + psn.getName() + "，关联关系说明：" + orgMap + "；");
                    stringBuilder.append("\r\n");
                    queryPsnRelation(stringBuilder, psn, orgName, relPsnOrgMap);
                }
            }
        }
        return stringBuilder.toString();

    }

    public String queryPsnRelation(StringBuilder stringBuilder, RelPsn relPsn, String orgName, Map relPsnOrgMap) {
        List<Filter> filters = null;
        List<String> list = relService.selectByFormPid(relPsn.getPid().toString());//获取上层机构id
        if (list.size() == 0 || list == null) {
            return stringBuilder.toString();
        }
        for (String s : list) {
            RelOrg relOrg = new RelOrg();
            RelPsn psn = relPsnService.get(Integer.valueOf(s));//获取上层机构
            if (psn == null) {
                relOrg = relOrgService.get(Integer.valueOf(s));
                filters = new Filter.Builder().eq("fromIdentifyNumber", relPsn.getIdentifyNumber()).eq("toIdentifyNumber", relOrg.getUniscCode()).build();
            } else {
                filters = new Filter.Builder().eq("fromIdentifyNumber", relPsn.getIdentifyNumber()).eq("toIdentifyNumber", psn.getIdentifyNumber()).build();
            }
            List<Rel> rels = relService.select(filters);//获取上层机构与本层关系
            if (rels.size() == 0 || rels == null) {
                continue;
            }
            for (Rel rel : rels) {
                String orgMap = String.valueOf(relPsnOrgMap.get(rel.getRelPsnOrg()));
                if (psn != null) {
                    stringBuilder.append("本层关联方：" + relPsn.getName() + "，前一层关联方：" + psn.getName() + "，关联关系说明：" + orgMap + "；");
                    stringBuilder.append("\r\n");
                    queryPsnRelation(stringBuilder, psn, orgName, relPsnOrgMap);
                } else {
                    stringBuilder.append("本层关联方：" + relPsn.getName() + "，前一层关联方：" + relOrg.getOrgName() + "，关联关系说明：" + orgMap + "；");
                    stringBuilder.append("\r\n");
                    queryRelation(stringBuilder, relOrg, orgName, relPsnOrgMap);
                }
            }
        }
        return stringBuilder.toString();

    }
//    /**
//     * PDF文件预览
//     * @param request
//     * @param response
//     */
    /*@RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void pdfStreamHandler(HttpServletRequest request, HttpServletResponse response) {
        String pidStr = request.getParameter("pid");
        String tranType = request.getParameter("tranType");
        if (StringUtils.isEmpty(pidStr)) {
            return;
        }
        Publog publog = new Publog();
        publog.setRelId(Integer.valueOf(pidStr));
        publog.setTranType(tranType);
        List<Publog> publogs = publogService.select(publog);
        String path = "";
        String fileName = "";
        if (publogs != null && publogs.size() == 1) {
            path = publogs.get(0).getFileUrl().substring(0, publogs.get(0).getFileUrl().indexOf(".") + 1) + "pdf";
            fileName = publogs.get(0).getPubFileName();
        }
        File file = new File(path);

        if (file.exists()) {
            byte[] data = null;
            try {
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                FileInputStream input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
                input.close();
            } catch (Exception e) {
                System.out.println("pdf文件处理异常：" + e);
            }

        } else {
            return;
        }
    }*/


    /*
     * 文件上传
     * */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam("pid") Integer pid, @RequestParam("fileDate") String fileDate, @RequestParam("pubType") String pubType, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> tips = new HashMap<String, Object>();
        TraEnclosure enclosure = null;
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
            List<MultipartFile> multipartFileList = multipartRequest.getFiles("file");
            Publog plog = publogService.get(pid);
            String path = plog.getFileUrl();
            for (MultipartFile file : multipartFileList) {
                if (!file.isEmpty()) {
                    String fileNameAll = file.getOriginalFilename(); //文件全名
                    String fileName = fileNameAll.substring(0, fileNameAll.lastIndexOf("."));  //文件名
                    String[] strArray = fileNameAll.split("\\.");
                    int suffixIndex = strArray.length - 1;
                    String fileType = strArray[suffixIndex] + ""; //文件类型
                    double size = file.getSize();
                    System.out.println("文件类型为 " + fileType);
                    double fileSizeMB = new BigDecimal(size / 1024 / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB
                    System.out.println(fileNameAll + "-->" + fileSizeMB + "MB");
                    File dest = new File(path + "/" + fileNameAll);
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdir();
                    }
                    file.transferTo(dest);
                    enclosure = new TraEnclosure();
                    tips.put("fileUrl", enclosure);

                }
            }
            Publog publog = new Publog();
            switch (pubType) {
                case "1"://逐笔披露
                    publog.setStatus("1");
                    break;
                default: //逐笔报告
                    publog.setStatus("02");
                    break;
            }
            publog.setPid(pid);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            publog.setArchDate(sdf.parse(fileDate));
            publogService.updateSelective(publog);

            switch (plog.getTranType()) {
                case "01"://投资入股类
                    TraInvestment traInterestment = new TraInvestment();
                    traInterestment.setPid(plog.getRelId());
                    switch (pubType) {
                        case "1"://逐笔披露
                            traInterestment.setArchivingStatus("1");
                            break;
                        default: //逐笔报告
                            traInterestment.setReportStatus("1");
                            break;
                    }
                    traInvestmentService.updateSelective(traInterestment);
                    break;
                case "02"://资金运用类
                    TraFunds traFunds = new TraFunds();
                    traFunds.setPid(plog.getRelId());
                    switch (pubType) {
                        case "1"://逐笔披露
                            traFunds.setArchivingStatus("1");
                            break;
                        default://逐笔报告
                            traFunds.setReportStatus("1");
                            break;
                    }
                    traFundsService.updateSelective(traFunds);
                    break;
                case "03"://保险业务类
                    TraBusiness traBusiness = new TraBusiness();
                    traBusiness.setPid(plog.getRelId());
                    switch (pubType) {
                        case "1"://逐笔披露
                            traBusiness.setArchivingStatus("1");
                            break;
                        default://逐笔报告
                            traBusiness.setReportStatus("1");
                            break;
                    }
                    traBusinessService.updateSelective(traBusiness);
                    break;
                case "04"://  利益转移类
                    TraInterest traInterest = new TraInterest();
                    traInterest.setPid(plog.getRelId());
                    switch (pubType) {
                        case "1"://逐笔披露
                            traInterest.setArchivingStatus("1");
                            break;
                        default://逐笔报告
                            traInterest.setReportStatus("1");
                            break;
                    }
                    traInterestService.updateSelective(traInterest);
                    break;
                case "05": //提供服务类
                    TraService traService = new TraService();
                    traService.setPid(plog.getRelId());
                    switch (pubType) {
                        case "1"://逐笔披露
                            traService.setArchivingStatus("1");
                            break;
                        default://逐笔报告
                            traService.setReportStatus("1");
                            break;
                    }
                    traServiceService.updateSelective(traService);
                    break;
                default://其他类
                    TraOther traOther = new TraOther();
                    traOther.setPid(plog.getRelId());
                    switch (pubType) {
                        case "1"://逐笔披露
                            traOther.setArchivingStatus("1");
                            break;
                        default://逐笔报告
                            traOther.setReportStatus("1");
                            break;
                    }
                    traOtherService.updateSelective(traOther);
                    break;
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传失败");
        }
        return tips;
    }

    @RequestMapping("/fundsGenerateWord/{tranType}/{pid}/{filePid}/{fileName}")
    @ResponseBody
    public Map<String, String> fundsWord(@PathVariable("tranType") String tranType, @PathVariable("pid") Integer pid, @PathVariable("filePid") Integer filePid, @PathVariable(value = "fileName") String fileName, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        Map<String, String> res = new HashMap<>();
        String uuid = UUID.randomUUID().toString();
        PubFileTemplate pubFileTemplate = pubFileTemplateService.selectById(filePid);

        //模板文件地址
//        String inputUrl = pubFileTemplate.getTempFileUrl();
        //模板文件地址测试本地
        String inputUrl = "D:\\Temp\\资金运用类关联交易报告.docx";
        //拿文件后缀 String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String suffix = inputUrl.substring(inputUrl.lastIndexOf("."));
        //新生产的模板文件
//        String outputUrl = disclosePath + '\\' + uuid + '\\' + File.separator + fileName + suffix;
        //新生产的模板文件测试本地
        String outputUrl = "D:\\westForDocFile" + '\\' + uuid + '\\' + File.separator + fileName + suffix;
        File dest = new File(outputUrl);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        //文件存放路径
        String FilerUrl = disclosePath + '\\' + uuid;
        Map<String, String> map = new HashMap<>();

        //boolean changWord = WorderToNewWordUtils.changWord(inputUrl, outputUrl, map, new ArrayList<>());
        boolean changWord = false;
        if (changWord) {
            //查询
            Publog publog = new Publog();
            //根据relId和交易类型
            publog.setRelId(Integer.valueOf(pid));
            publog.setTranType("funds");

            List<Publog> publogs = publogService.select(publog);
            if (CollectionUtils.isNotEmpty(publogs) && publogs.size() == 1) {
                //更新
                publog = publogs.get(0);
                publog.setPubFileName(fileName);
                publog.setPubSubject(UserProfileUtils.getUserProfile().getDeptName());
                publog.setCreateByName(UserProfileUtils.getUserProfile().getStaffName());
                publog.setMaxPercentInNetAssets(new Date());
                publog.setDownAmt(0);
                publog.setStatus("0");
                publog.setDownStatus("1");
                publog.setPubType("9");
                publog.setTranType(tranType);
                publog.setFileUrl(FilerUrl);
                publogService.updateSelective(publog);
            } else if (CollectionUtils.isEmpty(publogs) && publogs.size() == 0) {
                //新增
                publog.setPubFileName(fileName);
                publog.setPubSubject(UserProfileUtils.getUserProfile().getDeptName());
                publog.setCreateByName(UserProfileUtils.getUserProfile().getStaffName());
                publog.setMaxPercentInNetAssets(new Date());
                publog.setDownAmt(0);
                publog.setStatus("0");
                publog.setDownStatus("1");
                publog.setPubType("9");
                publog.setTranType(tranType);
                publog.setFileUrl(FilerUrl);
                publog.setRelId(pid);
                publogService.insertSelective(publog);
            }

            res.put("code", "200");
            res.put("fileUrl", FilerUrl);
            res.put("fileName", fileName);
            return res;
        }
        res.put("400", "错误！");
        return res;
    }
}