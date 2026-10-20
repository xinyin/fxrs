package com.yzj.jep.app.sub.web.controller;

import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.bus.model.DocFile;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.model.TraEnclosure;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.pub.model.PubFileTemplate;
import com.yzj.jep.app.pub.service.PubFileTemplateService;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sub.util.IdWorker;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.WorderToNewWordUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.yzj.jep.commons.DateUtils.*;

@Controller
@RequestMapping("/sub/subfile")
public class SubFileTemplateController extends CrudController<PubFileTemplate> {
    private static Logger logger = LoggerFactory.getLogger(SubFileTemplateController.class);
    @Autowired
    private PubFileTemplateService pubFileTemplateService;

    @Autowired
    private TraInterestService traInterestService;

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
    private SublogService sublogService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private DictItemService dictItemService;

    @Value("/home/rptrs/upload")
    private String disclosePath;

    @Autowired
    private Configs configs;

    @RequestMapping("/toGenerateWord/{tranType}/{pid}/{filePid}/{fileName}")
    @ResponseBody
    public Map<String, String> toword(@PathVariable("tranType") String tranType, @PathVariable("pid") Integer pid, @PathVariable("filePid") Integer filePid, @PathVariable(value = "fileName") String fileName, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        Map<String, String> res = new HashMap<>();
        String uuid = UUID.randomUUID().toString();
        PubFileTemplate pubFileTemplate = pubFileTemplateService.selectById(filePid);

        //模板文件地址
        String inputUrl = pubFileTemplate.getTempFileUrl();
        //拿文件后缀 String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String suffix = inputUrl.substring(inputUrl.lastIndexOf("."));
        //新生产的模板文件
        String outputUrl = disclosePath + '\\' + uuid + '\\' + File.separator + fileName + suffix;
        //文件存放路径
        String FilerUrl = disclosePath + '\\' + uuid;
        //文件名
        String fileNameAll = File.separator + fileName + suffix;

        Map<String, String> map = new HashMap<>();
        File destDir = new File(disclosePath + "/" + uuid);
        File dest = new File(disclosePath + "/" + uuid + "/" + fileNameAll);
        if (!destDir.getParentFile().exists()) {
            destDir.getParentFile().mkdir();
        }
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }


        if (tranType.equals("01")) {
            //投资入股类
            TraInvestment traInterest = traInvestmentService.get(pid);
            if (traInterest.getRelType().equalsIgnoreCase("org")) {

                RelOrg relOrg = new RelOrg();
                relOrg.setPid(traInterest.getRelId());

                List<RelOrg> relOrgList = relOrgService.select(relOrg);
                if (relOrgList.size() > 0) {
                    relOrg = relOrgList.get(0);


                    map.put("orgType", StringUtils.isNotEmpty(selectoneByValue("BUS-CompanyType", relOrg.getOrgType())) ? selectoneByValue("BUS-CompanyType", relOrg.getOrgType()) : "");
                    map.put("uniscCode", StringUtils.isNotEmpty(relOrg.getUniscCode()) ? relOrg.getUniscCode() : "");
                    map.put("regCapital", (relOrg.getRegCapital() != null) ? relOrg.getRegCapital().toString() + "万人民币" : "");
                    map.put("businessScope", StringUtils.isNotEmpty(relOrg.getBusinessScope()) ? relOrg.getBusinessScope() : "");
                }


            } else if (traInterest.getRelType().equalsIgnoreCase("psn")) {

                RelPsn relPsn = new RelPsn();
                relPsn.setPid(traInterest.getRelId());
                List<RelPsn> relPsnList = relPsnService.select(relPsn);

                if (relPsnList.size() > 0) {
                    relPsn = relPsnList.get(0);
                    map.put("orgType", "");
                    map.put("businessScope", "");
                    map.put("regCapital", "");
                }


            }


            map.put("relName", StringUtils.isNotEmpty(traInterest.getRelName()) ? traInterest.getRelName() : "");
            map.put("tranType", StringUtils.isNotEmpty(traInterest.getTranType()) ? traInterest.getTranType() : "");
            map.put("tradeAmount", (traInterest.getTradeAmount() != null) ? traInterest.getTradeAmount().toString() : "");
            map.put("pricingPolicy", StringUtils.isNotEmpty(traInterest.getPricingPolicy()) ? traInterest.getPricingPolicy() : "");
            map.put("financialImpact", StringUtils.isNotEmpty(traInterest.getFinancialImpact()) ? traInterest.getFinancialImpact() : "");
            map.put("totolTradeAmount", (traInterest.getTotolTradeAmount() != null) ? traInterest.getTotolTradeAmount().toString() : "");


            map.put("year", getYear(traInterest.getTranDate()) + "");
            map.put("month", getMonth(traInterest.getTranDate()) + "");
            map.put("day", getDate(traInterest.getTranDate()) + "");

            map.put("holdingSubsidiaryName", StringUtils.isNotEmpty(traInterest.getHoldingSubsidiaryName()) ? traInterest.getHoldingSubsidiaryName() : "");


        } else if (tranType.equals("02")) {
            //资金运营类
            TraFunds traInterest = null;
            RelOrg relOrg = null;
            try {

                traInterest = traFundsService.get(pid);


                if (traInterest.getRelType().equalsIgnoreCase("org")) {

                    relOrg = new RelOrg();
                    relOrg.setPid(traInterest.getRelId());

                    List<RelOrg> relOrgList = relOrgService.select(relOrg);
                    if (relOrgList.size() > 0) {
                        relOrg = relOrgList.get(0);


                        map.put("orgType", StringUtils.isNotEmpty(selectoneByValue("BUS-CompanyType", relOrg.getOrgType())) ? selectoneByValue("BUS-CompanyType", relOrg.getOrgType()) : "");
                        map.put("uniscCode", StringUtils.isNotEmpty(relOrg.getUniscCode()) ? relOrg.getUniscCode() : "");
                        map.put("regCapital", (relOrg.getRegCapital() != null) ? relOrg.getRegCapital().toString() + "万人民币" : "");
                        map.put("businessScope", StringUtils.isNotEmpty(relOrg.getBusinessScope()) ? relOrg.getBusinessScope() : "");
                    }


                } else if (traInterest.getRelType().equalsIgnoreCase("psn")) {

                    RelPsn relPsn = new RelPsn();
                    relPsn.setPid(traInterest.getRelId());
                    List<RelPsn> relPsnList = relPsnService.select(relPsn);

                    if (relPsnList.size() > 0) {
                        relPsn = relPsnList.get(0);
                        map.put("orgType", "");
                        map.put("businessScope", "");
                        map.put("regCapital", "");
                    }


                }
            } catch (Exception e) {
                traInterest = new TraFunds();
                relOrg = new RelOrg();
            }

            map.put("relName", StringUtils.isNotEmpty(traInterest.getRelName()) ? traInterest.getRelName() : "");
            map.put("tranType", StringUtils.isNotEmpty(traInterest.getTranType()) ? traInterest.getTranType() : "");
            map.put("tradeAmount", (traInterest.getTradeAmount() != null) ? traInterest.getTradeAmount().toString() : "");
            map.put("pricingPolicy", StringUtils.isNotEmpty(traInterest.getPricingPolicy()) ? traInterest.getPricingPolicy() : "");
            map.put("financialImpact", StringUtils.isNotEmpty(traInterest.getFinancialImpact()) ? traInterest.getFinancialImpact() : "");
            map.put("totolTradeAmount", (traInterest.getTotolTradeAmount() != null) ? traInterest.getTotolTradeAmount().toString() : "");


            map.put("year", getYear(traInterest.getTranDate()) + "");
            map.put("month", getMonth(traInterest.getTranDate()) + "");
            map.put("day", getDate(traInterest.getTranDate()) + "");

            map.put("holdingSubsidiaryName", StringUtils.isNotEmpty(traInterest.getHoldingSubsidiaryName()) ? traInterest.getHoldingSubsidiaryName() : "");


        } else if (tranType.equals("03")) {
            TraBusiness traInterest = null;
            RelOrg relOrg = null;
            try {
                //保险业务类

                traInterest = traBusinessService.get(pid);


                if (traInterest.getRelType().equalsIgnoreCase("org")) {

                    relOrg = new RelOrg();
                    relOrg.setPid(traInterest.getRelId());

                    List<RelOrg> relOrgList = relOrgService.select(relOrg);
                    if (relOrgList.size() > 0) {
                        relOrg = relOrgList.get(0);


                        map.put("orgType", StringUtils.isNotEmpty(selectoneByValue("BUS-CompanyType", relOrg.getOrgType())) ? selectoneByValue("BUS-CompanyType", relOrg.getOrgType()) : "");
                        map.put("uniscCode", StringUtils.isNotEmpty(relOrg.getUniscCode()) ? relOrg.getUniscCode() : "");
                        map.put("regCapital", (relOrg.getRegCapital() != null) ? relOrg.getRegCapital().toString() + "万人民币" : "");
                        map.put("businessScope", StringUtils.isNotEmpty(relOrg.getBusinessScope()) ? relOrg.getBusinessScope() : "");
                    }


                } else if (traInterest.getRelType().equalsIgnoreCase("psn")) {

                    RelPsn relPsn = new RelPsn();
                    relPsn.setPid(traInterest.getRelId());
                    List<RelPsn> relPsnList = relPsnService.select(relPsn);

                    if (relPsnList.size() > 0) {
                        relPsn = relPsnList.get(0);
                        map.put("orgType", "");
                        map.put("businessScope", "");
                        map.put("regCapital", "");
                    }


                }
            } catch (Exception e) {
                traInterest = new TraBusiness();
                relOrg = new RelOrg();
            }

            map.put("relName", StringUtils.isNotEmpty(traInterest.getRelName()) ? traInterest.getRelName() : "");
            map.put("tranType", StringUtils.isNotEmpty(traInterest.getTranType()) ? traInterest.getTranType() : "");
            map.put("tradeAmount", (traInterest.getTradeAmount() != null) ? traInterest.getTradeAmount().toString() : "");
            map.put("pricingPolicy", StringUtils.isNotEmpty(traInterest.getPricingPolicy()) ? traInterest.getPricingPolicy() : "");
            map.put("financialImpact", StringUtils.isNotEmpty(traInterest.getFinancialImpact()) ? traInterest.getFinancialImpact() : "");
            map.put("totolTradeAmount", (traInterest.getTotolTradeAmount() != null) ? traInterest.getTotolTradeAmount().toString() : "");


            map.put("year", getYear(traInterest.getTranDate()) + "");
            map.put("month", getMonth(traInterest.getTranDate()) + "");
            map.put("day", getDate(traInterest.getTranDate()) + "");

            map.put("holdingSubsidiaryName", StringUtils.isNotEmpty(traInterest.getHoldingSubsidiaryName()) ? traInterest.getHoldingSubsidiaryName() : "");


        } else if (tranType.equals("04")) {
            TraInterest traInterest = null;
            RelOrg relOrg = null;
            try {
                //利益转移类

                traInterest = traInterestService.get(pid);

                if (traInterest.getRelType().equalsIgnoreCase("org")) {

                    relOrg = new RelOrg();
                    relOrg.setPid(traInterest.getRelId());

                    List<RelOrg> relOrgList = relOrgService.select(relOrg);
                    if (relOrgList.size() > 0) {
                        relOrg = relOrgList.get(0);


                        map.put("orgType", StringUtils.isNotEmpty(selectoneByValue("BUS-CompanyType", relOrg.getOrgType())) ? selectoneByValue("BUS-CompanyType", relOrg.getOrgType()) : "");
                        map.put("uniscCode", StringUtils.isNotEmpty(relOrg.getUniscCode()) ? relOrg.getUniscCode() : "");
                        map.put("regCapital", (relOrg.getRegCapital() != null) ? relOrg.getRegCapital().toString() + "万人民币" : "");
                        map.put("businessScope", StringUtils.isNotEmpty(relOrg.getBusinessScope()) ? relOrg.getBusinessScope() : "");
                    }


                } else if (traInterest.getRelType().equalsIgnoreCase("psn")) {

                    RelPsn relPsn = new RelPsn();
                    relPsn.setPid(traInterest.getRelId());
                    List<RelPsn> relPsnList = relPsnService.select(relPsn);

                    if (relPsnList.size() > 0) {
                        relPsn = relPsnList.get(0);
                        map.put("orgType", "");
                        map.put("businessScope", "");
                        map.put("regCapital", "");
                    }


                }
            } catch (Exception e) {
                traInterest = new TraInterest();
                relOrg = new RelOrg();
            }
            map.put("relName", StringUtils.isNotEmpty(traInterest.getRelName()) ? traInterest.getRelName() : "");
            map.put("tranType", StringUtils.isNotEmpty(traInterest.getTranType()) ? traInterest.getTranType() : "");
            map.put("tradeAmount", (traInterest.getTradeAmount() != null) ? traInterest.getTradeAmount().toString() : "");
            map.put("pricingPolicy", StringUtils.isNotEmpty(traInterest.getPricingPolicy()) ? traInterest.getPricingPolicy() : "");
            map.put("financialImpact", StringUtils.isNotEmpty(traInterest.getFinancialImpact()) ? traInterest.getFinancialImpact() : "");
            map.put("totolTradeAmount", (traInterest.getTotolTradeAmount() != null) ? traInterest.getTotolTradeAmount().toString() : "");


        } else if (tranType.equals("05")) {
            TraService traInterest = null;
            RelOrg relOrg = null;

            try {
                //提供服务类
                traInterest = traServiceService.get(pid);

                if (traInterest.getRelType().equalsIgnoreCase("org")) {

                    relOrg = new RelOrg();
                    relOrg.setPid(traInterest.getRelId());

                    List<RelOrg> relOrgList = relOrgService.select(relOrg);
                    if (relOrgList.size() > 0) {
                        relOrg = relOrgList.get(0);


                        map.put("orgType", StringUtils.isNotEmpty(selectoneByValue("BUS-CompanyType", relOrg.getOrgType())) ? selectoneByValue("BUS-CompanyType", relOrg.getOrgType()) : "");
                        map.put("uniscCode", StringUtils.isNotEmpty(relOrg.getUniscCode()) ? relOrg.getUniscCode() : "");
                        map.put("regCapital", (relOrg.getRegCapital() != null) ? relOrg.getRegCapital().toString() + "万人民币" : "");
                        map.put("businessScope", StringUtils.isNotEmpty(relOrg.getBusinessScope()) ? relOrg.getBusinessScope() : "");
                    }


                } else if (traInterest.getRelType().equalsIgnoreCase("psn")) {

                    RelPsn relPsn = new RelPsn();
                    relPsn.setPid(traInterest.getRelId());
                    List<RelPsn> relPsnList = relPsnService.select(relPsn);

                    if (relPsnList.size() > 0) {
                        relPsn = relPsnList.get(0);
                        map.put("orgType", "");
                        map.put("businessScope", "");
                        map.put("regCapital", "");
                    }


                }
            } catch (Exception e) {
                traInterest = new TraService();
                relOrg = new RelOrg();
            }

            map.put("relName", StringUtils.isNotEmpty(traInterest.getRelName()) ? traInterest.getRelName() : "");
            map.put("tranType", StringUtils.isNotEmpty(traInterest.getTranType()) ? traInterest.getTranType() : "");
            map.put("tradeAmount", (traInterest.getTradeAmount() != null) ? traInterest.getTradeAmount().toString() : "");
            map.put("pricingPolicy", StringUtils.isNotEmpty(traInterest.getPricingPolicy()) ? traInterest.getPricingPolicy() : "");
            map.put("financialImpact", StringUtils.isNotEmpty(traInterest.getFinancialImpact()) ? traInterest.getFinancialImpact() : "");
            map.put("totolTradeAmount", (traInterest.getTotolTradeAmount() != null) ? traInterest.getTotolTradeAmount().toString() : "");

            map.put("year", getYear(traInterest.getTranDate()) + "");
            map.put("month", getMonth(traInterest.getTranDate()) + "");
            map.put("day", getDate(traInterest.getTranDate()) + "");

            map.put("holdingSubsidiaryName", StringUtils.isNotEmpty(traInterest.getHoldingSubsidiaryName()) ? traInterest.getHoldingSubsidiaryName() : "");


        } else if (tranType.equals("06")) {
            TraOther traInterest = null;
            RelOrg relOrg = null;
            try {
                //其他类

                traInterest = traOtherService.get(pid);


                if (traInterest.getRelType().equalsIgnoreCase("org")) {

                    relOrg = new RelOrg();
                    relOrg.setPid(traInterest.getRelId());

                    List<RelOrg> relOrgList = relOrgService.select(relOrg);
                    if (relOrgList.size() > 0) {
                        relOrg = relOrgList.get(0);


                        map.put("orgType", StringUtils.isNotEmpty(selectoneByValue("BUS-CompanyType", relOrg.getOrgType())) ? selectoneByValue("BUS-CompanyType", relOrg.getOrgType()) : "");
                        map.put("uniscCode", StringUtils.isNotEmpty(relOrg.getUniscCode()) ? relOrg.getUniscCode() : "");
                        map.put("regCapital", (relOrg.getRegCapital() != null) ? relOrg.getRegCapital().toString() + "万人民币" : "");
                        map.put("businessScope", StringUtils.isNotEmpty(relOrg.getBusinessScope()) ? relOrg.getBusinessScope() : "");
                    }


                } else if (traInterest.getRelType().equalsIgnoreCase("psn")) {

                    RelPsn relPsn = new RelPsn();
                    relPsn.setPid(traInterest.getRelId());
                    List<RelPsn> relPsnList = relPsnService.select(relPsn);

                    if (relPsnList.size() > 0) {
                        relPsn = relPsnList.get(0);
                        map.put("orgType", "");
                        map.put("businessScope", "");
                        map.put("regCapital", "");
                    }


                }
            } catch (Exception e) {
                traInterest = new TraOther();
                relOrg = new RelOrg();
            }

            map.put("relName", StringUtils.isNotEmpty(traInterest.getRelName()) ? traInterest.getRelName() : "");
            map.put("tranType", StringUtils.isNotEmpty(traInterest.getTranType()) ? traInterest.getTranType() : "");
            map.put("tradeAmount", (traInterest.getTradeAmount() != null) ? traInterest.getTradeAmount().toString() : "");
            map.put("pricingPolicy", StringUtils.isNotEmpty(traInterest.getPricingPolicy()) ? traInterest.getPricingPolicy() : "");
            map.put("financialImpact", StringUtils.isNotEmpty(traInterest.getFinancialImpact()) ? traInterest.getFinancialImpact() : "");
            map.put("totolTradeAmount", (traInterest.getTotolTradeAmount() != null) ? traInterest.getTotolTradeAmount().toString() : "");


            map.put("year", getYear(traInterest.getTranDate()) + "");
            map.put("month", getMonth(traInterest.getTranDate()) + "");
            map.put("day", getDate(traInterest.getTranDate()) + "");

            map.put("holdingSubsidiaryName", StringUtils.isNotEmpty(traInterest.getHoldingSubsidiaryName()) ? traInterest.getHoldingSubsidiaryName() : "");
            map.put("pricing", StringUtils.isNotEmpty(traInterest.getTradePrice()) ? traInterest.getTradePrice() : "");//交易价格

        }


        map.put("tranType", StringUtils.isNotEmpty(selectoneByValue("INS-tranType", map.get("tranType"))) ? selectoneByValue("INS-tranType", map.get("tranType")) : "");

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
//        if (map.get("considerType").equals("1")) {
//            map.put("considerType", "可免予审议");
//        } else if (map.get("considerType").equals("2")) {
//            map.put("considerType", "可集中审议");
//        } else if (map.get("considerType").equals("3")) {
//            map.put("considerType", "逐笔审议");
//        }
        //boolean changWord = WorderToNewWordUtils.changWord(inputUrl, outputUrl, map, new ArrayList<>());
        boolean changWord = false;
        if (changWord) {
            //查询
            Sublog sublog = new Sublog();
            //根据relId和交易类型
            sublog.setRelId(Integer.valueOf(pid));
            sublog.setTranType(tranType);

            List<Sublog> sublogs = sublogService.select(sublog);
            if (CollectionUtils.isNotEmpty(sublogs) && sublogs.size() == 1) {
                //更新
                sublog = sublogs.get(0);
                sublog.setFileName(fileName + suffix);
                sublog.setSubOrg(UserProfileUtils.getUserProfile().getDeptName());
                sublog.setMaxPercentInNetAssets(new Date());
                sublog.setDownAmt(0);
                sublog.setStatus("01");
                sublog.setSubType("1");
                sublog.setTranType(tranType);
                sublog.setFileUrl(FilerUrl);
                sublogService.updateSelective(sublog);
            } else if (CollectionUtils.isEmpty(sublogs) && sublogs.size() == 0) {
                //新增
                sublog.setFileName(fileName + suffix);
                sublog.setSubOrg(UserProfileUtils.getUserProfile().getDeptName());
                sublog.setMaxPercentInNetAssets(new Date());
                sublog.setDownAmt(0);
                sublog.setStatus("01");
                sublog.setSubType("1");
                sublog.setTranType(tranType);
                sublog.setFileUrl(FilerUrl);
                sublog.setRelId(pid);
                sublogService.insertSelective(sublog);
            }

            res.put("code", "200");
            res.put("fileUrl", FilerUrl);
            res.put("fileName", fileName);
            return res;
        }
        res.put("400", "错误！");
        return res;
    }


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

    /*
     * 文件上传
     * */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam("pid") Integer pid,@RequestParam("fileDate") String fileDate, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> tips = new HashMap<String, Object>();
        TraEnclosure enclosure = null;
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
            List<MultipartFile> multipartFileList = multipartRequest.getFiles("file");
            Sublog slog = sublogService.get(pid);
            String path = slog.getFileUrl();
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
            Sublog sublog = new Sublog();
            sublog.setStatus("02");
            sublog.setPid(pid);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sublog.setSubmissionDate(sdf.parse(fileDate));
            sublogService.updateSelective(sublog);
            switch (slog.getTranType()) {
                case "01"://投资入股类
                    TraInvestment traInterestment = new TraInvestment();
                    traInterestment.setPid(slog.getRelId());
                    traInterestment.setArchivingStatus("2");
                    traInvestmentService.updateSelective(traInterestment);
                    break;
                case "02"://资金运用类
                    TraFunds traFunds = new TraFunds();
                    traFunds.setPid(slog.getRelId());
                    traFunds.setArchivingStatus("2");
                    traFundsService.updateSelective(traFunds);
                    break;
                case "03"://保险业务类
                    TraBusiness traBusiness = new TraBusiness();
                    traBusiness.setPid(slog.getRelId());
                    traBusiness.setArchivingStatus("2");
                    traBusinessService.updateSelective(traBusiness);
                    break;
                case "04"://  利益转移类
                    TraInterest traInterest = new TraInterest();
                    traInterest.setPid(slog.getRelId());
                    traInterest.setArchivingStatus("2");
                    traInterestService.updateSelective(traInterest);
                    break;
                case "05": //提供服务类
                    TraService traService=new TraService();
                    traService.setPid(slog.getRelId());
                    traService.setArchivingStatus("2");
                    traServiceService.updateSelective(traService);
                    break;
                default://其他类
                    TraOther traOther=new TraOther();
                    traOther.setPid(slog.getRelId());
                    traOther.setArchivingStatus("2");
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




}