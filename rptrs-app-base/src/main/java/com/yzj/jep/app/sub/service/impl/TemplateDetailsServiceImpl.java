/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月01日 17:05:11
 *
 */

package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.enums.RelTypeCode;
import com.yzj.jep.app.enums.Submitted;
import com.yzj.jep.app.ins.enums.RelatedPartyCategoryEnum;
import com.yzj.jep.app.ins.service.UnSortOutService;
import com.yzj.jep.app.sub.mapper.TemplateDetailsMapper;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.app.sub.util.MD5Utils;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.JdbcUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.core.utils.xlsio.transformer.XlsTransformer4Merge;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.web.util.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Administrator
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TemplateDetailsServiceImpl extends MyBatisCRUDService<TemplateDetails, Integer> implements TemplateDetailsService {

    Logger logger = LoggerFactory.getLogger(TemplateDetailsServiceImpl.class);


    private static final String EXPORT_TEMPLATE_E1_NAME = "EastGLF.xls";
    private static final String EXPORT_TEMPLATE_E2_NAME = "EastJRCP.xls";
    private static final String EXPORT_TEMPLATE_E3_NAME = "EastGLGX.xls";
    private static final String EXPORT_TEMPLATE_E4_NAME = "EastZDGLJYMX.xls";
    private static final String EXPORT_TEMPLATE_E5_NAME = "EastGLJYHZ.xls";
    private static final String EXPORT_TEMPLATE_0_NAME = "subPerson.xls";
    private static final String EXPORT_TEMPLATE_1_NAME = "EastGLJYHZ.xls";
    private static final String EXPORT_TEMPLATE_2_NAME = "subRelation.xls";
    private static final String EXPORT_TEMPLATE_3_NAME = "finProduct.xls";
    private static final String EXPORT_TEMPLATE_08_NAME = "relFunds.xls";
    private static final String EXPORT_TEMPLATE_07_NAME = "allFunds.xls";
    private static final String EXPORT_TEMPLATE_06_NAME = "allBusTran.xlsx";

    @Autowired
    private TemplateDetailsMapper templateDetailsMapper;

    @Autowired
    private PersonViewService personViewService;

    @Autowired
    private ProductViewService productViewService;

    @Autowired
    private OrganViewService organViewService;
    @Autowired
    private InsRelfundsService insRelfundsService;

    @Autowired
    private RelatedRelationViewService relatedRelationViewService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private InsInvestmentViewService insInvestmentViewService;

    @Autowired
    private InsFundsViewService insFundsViewService;

    @Autowired
    private InsBusinessViewService insBusinessViewService;

    @Autowired
    private InsInterestViewService insInterestViewService;

    @Autowired
    private InsServiceViewService insServiceViewService;
    @Autowired
    private InsAllfundsService insAllfundsService;

    @Autowired
    private SublogService sublogService;

    @Autowired
    private static Configs configs;

    @Autowired
    private InsOtherService insOtherService;
    @Autowired
    private OrganService organService;
    @Autowired
    private PersonService personService;
    @Autowired
    private RelatedRelationService relatedRelationService;
    @Autowired
    private FinProductService finProductService;
    @Autowired
    private InsOtherViewService insOtherViewService;


    @Autowired
    private InsAllbustraninfoViewService insAllbustraninfoViewService;

    @Autowired
    private InsRelfundsViewService insRelfundsViewService;
    @Autowired
    private InsAllfundsViewService insAllfundsViewService;
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
    private EastGLFXXService eastGLFXXService;
    @Autowired
    private EastGLFXXViewService eastGLFXXViewService;

    @Autowired
    private EastZDGLJYMXService eastZDGLJYMXService;
    @Autowired
    private EastZDGLJYMXViewService eastZDGLJYMXViewService;

    @Autowired
    private EastGLJYHZService eastGljyhzbService;

    @Autowired
    private EastGLJYHZViewService eastGljyhzbViewService;

    @Autowired
    private EastGLGXService eastGLGXService;
    @Autowired
    private EastGLGXViewService eastGLGXViewService;

    @Autowired
    private UnSortOutService unSortOutService;
    @Autowired
    private InsAllbustraninfoService insAllbustraninfoService;


    @Override
    public Mapper<TemplateDetails, Integer> getMapper() {
        return templateDetailsMapper;
    }

    @Override
    public boolean createSubFile(Integer templateId, List<Filter> filters, String subYear, String subQuarter, String fileName, String subType) throws Exception {
        boolean flag;
        logger.info("生成模板文件开始...");
        logger.info("当前模板ID：[{}]", templateId);
        UserProfile profile = UserProfileUtils.getUserProfile();
        configs = SpringContextHolder.getBean(Configs.class);
        String filePath = configs.getReport();
        Template template = templateService.get(templateId);
        String service = template.getService();
        CRUDService crudService = SpringContextHolder.getBean(service);
        List<Filter> templateFilters = new ArrayList<>();
        templateFilters.add(Filter.nl("parentId"));
        templateFilters.add(Filter.eq("templateId", templateId));
        TemplateDetails root = this.get(templateFilters);
        logger.info("root节点id:[{}],name:[{}]", root.getPid(), root.getColName());
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement(root.getElementName());
        filters.add(Filter.eq("status", 3));
        long count = crudService.count(filters);
        if (count > 0) {
            int pageSize = 3;
            long time = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            for (int i = 0; i < time; i++) {
                Page page = new Page();
                page.setPageNumber(i + 1);
                page.setPageSize(pageSize);
                Page query = crudService.query(page, filters);
                List<Object> objects = query.getResult();
                for (Object obj : objects) {
                    createElement(rootElement, root.getPid(), templateId, root, obj);
                }
                OutputFormat format = OutputFormat.createPrettyPrint();
                File file = new File(filePath + "/" + fileName + ".xml");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdir();
                }
                XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
//                XMLWriter writer = new XMLWriter(new FileOutputStream(new File("F:/credit/" + fileName + ".xml")), format);
                writer.write(document);
                logger.info("写文件");
                writer.close();
            }
        }
        Sublog sublog = new Sublog();
        if (count > 0) {
            logger.info("生成报送数据");
            sublog.setFileName(fileName);
            //年度
            if (StringUtils.isNotBlank(subYear)) {
                sublog.setSubYear(subYear);
            }
            //季度
            if (StringUtils.isNotBlank(subQuarter)) {
                sublog.setSubQuarter(subQuarter);
            }
            sublog.setCreateTime(new Date());
            sublog.setSubType(subType);//报送类型
            sublog.setDownStatus("1"); //是否下载   默认未下载  0-是 1-否
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            } else {
                sublog.setSubOrg(profile.getUnitCode());  //报送机构
                sublog.setOperatorName(profile.getStaffName());
            }
            sublog.setStatus("01");  //状态默认是待提交
            sublog.setDownAmt(0);  //下载次数  默认是0
            sublog.setBatchDecimal(BatchNumberUtil.makeBatchNumber());
            sublog.setMaxPercentInNetAssets(new Date());
            sublogService.insert(sublog);
            flag = true;
        } else {
            logger.info("暂无报送数据生成");
            flag = false;
        }
        return flag;
    }

    /**
     * @param templateId
     * @param filters
     * @param fileName
     * @param subType
     * @return
     * @throws Exception
     */
    @Override
    public boolean createSubmitFile(Integer templateId, List<Filter> filters, String fileName, String subType) throws Exception {
        boolean flag;
        logger.info("生成模板文件开始...");
        logger.info("当前模板ID：[{}]", templateId);
        //定义流水号
        String seq = null;
        UserProfile profile = UserProfileUtils.getUserProfile();
        configs = SpringContextHolder.getBean(Configs.class);
        String filePath = configs.getReport();
        Template template = templateService.get(templateId);
        String service = template.getService();
        CRUDService crudService = SpringContextHolder.getBean(service);
        List<Filter> templateFilters = new ArrayList<>();
        templateFilters.add(Filter.nl("parentId"));
        templateFilters.add(Filter.eq("templateId", templateId));
        TemplateDetails root = this.get(templateFilters);
        logger.info("root节点id:[{}],name:[{}]", root.getPid(), root.getColName());
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement(root.getElementName());
        switch (subType) {
            case "1":
                //自然人需要
                filters.add(Filter.eq("effectRelation", "1"));
                break;
            case "0":
                //法人需要
                filters.add(Filter.eq("effectRelation", "1"));
                break;
        }
        filters.add(Filter.eq("status", 3));
        long count = crudService.count(filters);
        if (count > 0) {
            int pageSize = 3;
            long time = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            for (int i = 0; i < time; i++) {
                Page page = new Page();
                page.setPageNumber(i + 1);
                page.setPageSize(pageSize);
                Page query = crudService.query(page, filters);
                List<Object> objects = query.getResult();
                for (Object obj : objects) {
                    createElement(rootElement, root.getPid(), templateId, root, obj);
                    //    Method setStatus = obj.getClass().getMethod("setStatus", String.class);
                    //    setStatus.invoke(obj, "4");
                    //    crudService.updateSelective((PO) obj);
                    if ("0".equals(subType)) {
                        //法人机构或其它报送
                        Organ organ = (Organ) obj;
                        if (organ != null) {
                            if ("".equals(seq) || seq == null) {
                                //生成流水号
                                seq = BatchNumberUtil.makeBatchNumber();
                            }
                            //查询到的数据插入到查看表中
                            OrganView organView = new OrganView();
                            organView.setSubSeq(seq);
                            organView.setName(organ.getName());
                            organView.setLegalIdentifyNumber(organ.getIdentityNo());
                            organView.setDomesticOrForeign(organ.getDomesticOrForeign());
                            organView.setIdentifyType(organ.getIdentifyType());
                            organView.setProtPid(organ.getPid());
                            organView.setBusinessScope(organ.getBusinessScope());
                            organView.setOrgNature(organ.getOrgNature());
                            organView.setLegalPerson(organ.getLegalPerson());
                            organView.setRegAddress(organ.getRegAddress());
                            organView.setRegCapital(organ.getRegCapital());
                            organViewService.insertSelective(organView);
                            organ.setStatus("4");
                            organService.updateSelective(organ);
                        }
                    }
                    if ("1".equals(subType)) {
                        //自然人档案报送
                        Person person = (Person) obj;
                        if (person != null) {
                            if ("".equals(seq) || seq == null) {
                                //生成流水号
                                seq = BatchNumberUtil.makeBatchNumber();
                            }
                            //查询到的数据插入到查看表中
                            PersonView personView = new PersonView();
                            personView.setSubSeq(seq);
                            personView.setName(person.getName());
                            personView.setDomesticOrForeign(person.getDomesticOrForeign());
                            personView.setIdentityType(person.getIdentityType());
                            personView.setIdentityNo(MD5Utils.md5ToLowerCase(person.getIdentityNo()));
                            personView.setProtPid(person.getPid());
                            personViewService.insertSelective(personView);
                            person.setStatus("4");
                            personService.updateSelective(person);
                        }
                    }
                    if ("2".equals(subType)) {
                        //关联关系报送
                        RelatedRelation relatedRelation = (RelatedRelation) obj;
                        if (relatedRelation != null) {
                            if ("".equals(seq) || seq == null) {
                                //生成流水号
                                seq = BatchNumberUtil.makeBatchNumber();
                            }
                            RelatedRelationView relatedRelationView = new RelatedRelationView();
                            relatedRelationView.setEntityName(relatedRelation.getEntityName());
                            relatedRelationView.setEntityType(relatedRelation.getEntityType());
                            if ("2".equals(relatedRelation.getEntityType())) {
                                relatedRelationView.setEntityUniscUode(MD5Utils.md5ToLowerCase(relatedRelation.getEntityUniscUode()));
                            } else {
                                relatedRelationView.setEntityUniscUode(relatedRelation.getEntityUniscUode());
                            }
                            relatedRelationView.setHoldingPercent(relatedRelation.getHoldingPercent());
                            relatedRelationView.setActiveTimeString(relatedRelation.getActiveTimeString());
                            relatedRelationView.setInvalidTimeString(relatedRelation.getInvalidTimeString());
                            relatedRelationView.setIsShareHolding(relatedRelation.getIsShareHolding());
                            relatedRelationView.setRegulator(relatedRelation.getRegulator());
                            relatedRelationView.setRelationInfo(relatedRelation.getRelationInfo());
                            relatedRelationView.setRelationSeq(seq);
                            relatedRelationView.setProdPid(relatedRelation.getPid());
                            relatedRelationView.setRelationType(relatedRelation.getRelationType());
                            relatedRelationView.setStatus(relatedRelation.getStatus());
                            relatedRelationView.setStatusComment(relatedRelation.getStatusComment());
                            relatedRelationView.setSuperEntityName(relatedRelation.getSuperEntityName());
                            relatedRelationView.setSuperEntityType(relatedRelation.getSuperEntityType());
                            if ("2".equals(relatedRelation.getSuperEntityType())) {
                                relatedRelationView.setSuperEntityUniscCode(MD5Utils.md5ToLowerCase(relatedRelation.getSuperEntityUniscCode()));
                            } else {
                                relatedRelationView.setSuperEntityUniscCode(relatedRelation.getSuperEntityUniscCode());
                            }

                            relatedRelationView.setValidStatus(relatedRelation.getValidStatus());
                            relatedRelationView.setEquityShare(relatedRelation.getEquityShare());
                            relatedRelationView.setPledgeRatio(relatedRelation.getPledgeRatio());
                            relatedRelationViewService.insertSelective(relatedRelationView);
                            relatedRelation.setStatus("4");
                            relatedRelationService.updateSelective(relatedRelation);
                        }
                    }
                    if ("3".equals(subType)) {
                        //金融产品报送
                        FinProduct product = (FinProduct) obj;
                        if (product != null) {
                            if ("".equals(seq) || seq == null) {
                                //生成流水号
                                seq = BatchNumberUtil.makeBatchNumber();
                            }
                            ProductView productView = new ProductView();
                            productView.setSubSeq(seq);
                            productView.setProductName(product.getProductName());
                            productView.setProductRegisterNo(product.getProductRegisterNo());
                            productView.setProductType(product.getProductType());
                            productView.setProductIssueNo(product.getProductIssueNo());
                            productView.setRegisterOrg(product.getRegisterOrg());
                            productView.setProductOwner(product.getProductOwner());
                            productView.setProductCustodianUniscCode(product.getProductCustodianUniscCode());
                            productView.setProductOwnerUniscCode(product.getProductOwnerUniscCode());
                            productView.setProductCustodian(product.getProductCustodian());
                            productView.setProtPid(product.getPid());
                            productView.setAssetDesc(product.getAssetDesc());
                            productView.setInvestors(product.getInvestors());
                            productViewService.insertSelective(productView);
                            product.setStatus("4");
                            finProductService.updateSelective(product);
                        }
                    }
                }
                OutputFormat format = OutputFormat.createPrettyPrint();
                File file = new File(filePath + "/" + fileName + ".xml");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdir();
                }
                XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
//                XMLWriter writer = new XMLWriter(new FileOutputStream(new File("D:/home/rptrs/upload/" + fileName + ".xml")), format);
////                XMLWriter writer = new XMLWriter(new FileOutputStream(new File("D:/work/" + fileName + ".xml")), format);
                writer.write(document);
                logger.info("写文件");
                writer.close();
            }
            Map<String, Object> params = new HashMap<>();
            params.put("subType", subType);

        }
        Sublog sublog = new Sublog();
        if (count > 0) {
            logger.info("生成报送数据");
            sublog.setFileName(fileName + ".xml");
            sublog.setCreateTime(new Date());
            sublog.setTenantId(profile.getTenantId());
            sublog.setSubOrg("复星保德信人寿保险有限公司");
            sublog.setSubType(subType);//报送类型
            sublog.setDownStatus("1"); //是否下载   默认未下载  0-是 1-否
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            } else {
                sublog.setOperatorName(profile.getStaffName());
            }
            sublog.setStatus("01");  //状态默认是待提交
            sublog.setDownAmt(0);  //下载次数  默认是0
            sublog.setBatchDecimal(seq); //批次号
            sublog.setFileUrl(filePath + "/" + fileName + ".xml");//文件存放路径
            //sublog.setBatchDecimal(BatchNumberUtil.makeBatchNumber());
            sublog.setMaxPercentInNetAssets(new Date());
            sublogService.insertSelective(sublog);
            flag = true;
        } else {
            logger.info("暂无报送数据生成");
            flag = false;
        }
        return flag;
    }

    /**
     * 单笔报送生成xml
     *
     * @param templateId
     * @param filters
     * @param fileName
     * @param subType
     * @return
     * @throws Exception
     */
    @Override
    public boolean createSingleSubFile(Integer templateId, List<Filter> filters, String fileName, String subType) throws Exception {
        boolean flag;
        logger.info("生成模板文件开始...");
        logger.info("当前模板ID：[{}]", templateId);
        String seq = null;
        UserProfile profile = UserProfileUtils.getUserProfile();
        configs = SpringContextHolder.getBean(Configs.class);
        String filePath = configs.getReport();
        Template template = templateService.get(templateId);
        String service = template.getService();
        CRUDService crudService = SpringContextHolder.getBean(service);
        List<Filter> templateFilters = new ArrayList<>();
        templateFilters.add(Filter.nl("parentId"));
        templateFilters.add(Filter.eq("templateId", templateId));
        TemplateDetails root = this.get(templateFilters);
        logger.info("root节点id:[{}],name:[{}]", root.getPid(), root.getColName());
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement(root.getElementName());
//        if("null".equals(subOrg)){
//            filters.add(Filter.nl("tenantId"));
//        }else if (subOrg != null){
//            filters.add(Filter.eq("tenantId",subOrg));
//        }
        filters.add(Filter.eq("status", "3"));
        filters.add(Filter.eq("delflag", "1"));
        long count = crudService.count(filters);
        if (count > 0) {
            int pageSize = 3;
            long time = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            for (int i = 0; i < time; i++) {
                Page page = new Page();
                page.setPageNumber(i + 1);
                page.setPageSize(pageSize);
                Page query = crudService.query(page, filters);
                List<Object> objects = query.getResult();
                for (Object obj : objects) {
                    createElement(rootElement, root.getPid(), templateId, root, obj);
//                    if ("01".equals(subType)) {
//                        //投资入股类
//                        InsInvestment investment = (InsInvestment) obj;
//                        if (investment != null) {
//                            if ("".equals(seq) || seq == null) {
//                                //生成流水号
//                                seq = BatchNumberUtil.makeBatchNumber();
//                            }
//                            //查询到的数据插入到查看表中
//                            InsInvestmentView investmentView = new InsInvestmentView();
//                            BeanUtils.copyProperties(investment, investmentView);
//                            investmentView.setInvestmentSeq(seq);
//                            investmentView.setPid(null);
//                            investmentView.setPortPid(investment.getPid());
//                            insInvestmentViewService.insertSelective(investmentView);
//                            investment.setStatus("4");
//                            insInvestmentService.updateSelective(investment);
//
//                        }
//                    }
                    if ("02".equals(subType)) {
                        //资金运用类
                        InsFunds insFunds = (InsFunds) obj;
                        if (insFunds != null) {
                            if ("".equals(seq) || seq == null) {
                                //生成流水号
                                seq = BatchNumberUtil.makeBatchNumber();
                            }
                            InsFundsView insFundsView = new InsFundsView();
                            BeanUtils.copyProperties(insFunds, insFundsView);
                            if ("PSN".equals(insFundsView.getRelType())) {
                                insFundsView.setRelNumber(MD5Utils.md5ToLowerCase(insFundsView.getRelNumber()));
                            }
                            insFundsView.setFundsSeq(seq);
                            insFundsView.setPid(null);
                            insFundsView.setPortPid(insFunds.getPid());
                            insFundsViewService.insertSelective(insFundsView);
                            insFunds.setStatus("4");
                            insFundsService.updateSelective(insFunds);
                        }
                    }
                    if ("03".equals(subType)) {
                        //保险业务类
                        InsBusiness insBusiness = (InsBusiness) obj;
                        if (insBusiness != null) {
                            if ("".equals(seq) || seq == null) {
                                //生成流水号
                                seq = BatchNumberUtil.makeBatchNumber();
                            }
                            InsBusinessView insBusinessView = new InsBusinessView();
                            BeanUtils.copyProperties(insBusiness, insBusinessView);
                            if ("PSN".equals(insBusinessView.getRelType())) {
                                insBusinessView.setRelNumber(MD5Utils.md5ToLowerCase(insBusinessView.getRelNumber()));
                            }
                            insBusinessView.setBusinessSeq(seq);
                            insBusinessView.setPid(null);
                            insBusinessView.setPortPid(insBusiness.getPid());
                            insBusinessViewService.insertSelective(insBusinessView);
                            insBusiness.setStatus("4");
                            insBusinessService.updateSelective(insBusiness);

                        }
                    }
                    if ("04".equals(subType)) {
                        //利益转移类
                        InsInterest insInterest = (InsInterest) obj;
                        if (insInterest != null) {
                            if ("".equals(seq) || seq == null) {
                                //生成流水号
                                seq = BatchNumberUtil.makeBatchNumber();
                            }
                            InsInterestView insInterestView = new InsInterestView();
                            BeanUtils.copyProperties(insInterest, insInterestView);
                            if ("PSN".equals(insInterest.getRelType())) {
                                insInterest.setRelNumber(MD5Utils.md5ToLowerCase(insInterest.getRelNumber()));
                            }
                            insInterestView.setInterestSeq(seq);
                            insInterestView.setPid(null);
                            insInterestView.setPortPid(insInterest.getPid());
                            insInterestViewService.insertSelective(insInterestView);
                            insInterest.setStatus("4");
                            insInterestService.updateSelective(insInterest);
                        }
                    }
                    if ("05".equals(subType)) {
                        //提供服务类
                        InsService insService = (InsService) obj;
                        if (insService != null) {
                            if ("".equals(seq) || seq == null) {
                                //生成流水号
                                seq = BatchNumberUtil.makeBatchNumber();
                            }
                            InsServiceView insServiceView = new InsServiceView();
                            BeanUtils.copyProperties(insService, insServiceView);
                            if ("PSN".equals(insService.getRelType())) {
                                insService.setRelNumber(MD5Utils.md5ToLowerCase(insService.getRelNumber()));
                            }
                            insServiceView.setServiceSeq(seq);
                            insServiceView.setPid(null);
                            insServiceView.setPortPid(insService.getPid());
                            insServiceViewService.insert(insServiceView);
                            insService.setStatus("4");
                            insServiceService.updateSelective(insService);
                        }
                    }
//                    if ("09".equals(subType)) {
//                        //其他类
//                        InsOther insOther = (InsOther) obj;
//                        if (insOther != null) {
//                            if ("".equals(seq) || seq == null) {
//                                //生成流水号
//                                seq = BatchNumberUtil.makeBatchNumber();
//                            }
//                            InsOtherView insOtherView = new InsOtherView();
//                            BeanUtils.copyProperties(insOther, insOtherView);
//                            insOtherView.setOtherSeq(seq);
//                            insOtherView.setPortPid(insOther.getPid());
//                            insOtherView.setPid(null);
//                            insOtherViewService.insert(insOtherView);
//                            insOther.setStatus("4");
//                            insOtherService.update(insOther);
//                        }
//                    }
                }
                OutputFormat format = OutputFormat.createPrettyPrint();
                File file = new File(filePath + "/" + fileName + ".xml");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdir();
                }
                XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
                writer.write(document);
                logger.info("写文件");
                writer.close();
            }

        }

        Sublog sublog = new Sublog();
        if (count > 0) {
            logger.info("生成报送数据");
            sublog.setFileName(fileName + ".xml");
            sublog.setTenantId(profile.getTenantId());
            sublog.setCreateTime(new Date());
            sublog.setSubType(subType);//报送类型
            sublog.setSubOrg("复星保德信人寿保险有限公司");//报送机构
            sublog.setDownStatus("1"); //是否下载   默认未下载  0-是 1-否
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            } else {
                sublog.setOperatorName(profile.getStaffName());
            }
            sublog.setStatus("01");  //状态默认是待提交
            sublog.setDownAmt(0);  //下载次数  默认是0
            sublog.setBatchDecimal(seq); //批次号
            sublog.setFileUrl(filePath + "/" + fileName + ".xml");//文件存放路径
            //sublog.setBatchDecimal(BatchNumberUtil.makeBatchNumber());
            sublog.setMaxPercentInNetAssets(new Date());
            sublogService.insertSelective(sublog);
            flag = true;
        } else {
            logger.info("暂无报送数据生成");
            flag = false;
        }
        return flag;
    }


    /**
     * 季度报送生成xml
     *
     * @param templateId
     * @param filters
     * @param fileName
     * @param subType
     * @return
     * @throws Exception
     */

    @Override
    public boolean createQuarterSubFile(Integer templateId, List<Filter> filters, String fileName, String subYear, String subQuarter, String subType, HttpServletRequest request) throws Exception {
        boolean flag = false;
        Map<String, Object> map = new HashMap<>();
        logger.info("生成模板文件开始...");
        logger.info("当前模板ID：[{}]", templateId);
        String seq = null;
        UserProfile profile = UserProfileUtils.getUserProfile();
        configs = SpringContextHolder.getBean(Configs.class);
        String filePath = configs.getReport();
        Template template = templateService.get(templateId);
        String service = template.getService();
        CRUDService crudService = SpringContextHolder.getBean(service);
        List<Filter> templateFilters = new ArrayList<>();
        templateFilters.add(Filter.nl("parentId"));
        templateFilters.add(Filter.eq("templateId", templateId));
        TemplateDetails root = this.get(templateFilters);
        logger.info("root节点id:[{}],name:[{}]", root.getPid(), root.getColName());
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement(root.getElementName());
        filters = RequestUtils.buildFilters(request);
        filters.add(Filter.eq("status", 3));
        if(StringUtils.isNotBlank(subYear)){
            filters.add(Filter.like("subYear", subYear));
        }
        if(StringUtils.isNotBlank(subQuarter)){
            filters.add(Filter.eq("subQuarter", subQuarter));
        }
        long count = crudService.count(filters);
        if (count > 0) {
            String personIdentityNo =null;
            int pageSize = 3;
            long time = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            for (int i = 0; i < time; i++) {
                Page page = new Page();
                page.setPageNumber(i + 1);
                page.setPageSize(pageSize);
                Page query = crudService.query(page, filters);
                List<Object> objects = query.getResult();
                for (Object obj : objects) {
                    createElement(rootElement, root.getPid(), templateId, root, obj);
                    if("06".equals(subType)){
                        //关联交易明细表,如果存在未归档数据或者需要生成的报送文件信息存在待补录状态，则失败，提示缺失数据


                        List<Filter> allBusFilter = new ArrayList<>();
                        allBusFilter.add(Filter.eq("traStatus", 3));
                        allBusFilter.add(Filter.eq("subYear", subYear));
                        allBusFilter.add(Filter.eq("subQuarter", subQuarter));

                        InsAllbustraninfo insAllbustraninfo = (InsAllbustraninfo) obj;
                        if (insAllbustraninfo != null) {
                            if ("".equals(seq) || seq == null) {
                                //生成流水号
                                seq = BatchNumberUtil.makeBatchNumber();
                            }
                            //查询到的数据插入到查看表中
                            InsAllbustraninfoView insAllbustraninfoView = new InsAllbustraninfoView();
                            BeanUtils.copyProperties(insAllbustraninfo, insAllbustraninfoView);
                            if ("PSN".equals(insAllbustraninfo.getRelType())) {
                                insAllbustraninfo.setRelNumber(personIdentityNo);
                                personIdentityNo = null;
                            }
                            insAllbustraninfoView.setAllbustraninfoSeq(seq);
                            insAllbustraninfoView.setPid(null);
                            insAllbustraninfoViewService.insert(insAllbustraninfoView);
                            insAllbustraninfo.setStatus("4");
                            insAllbustraninfoService.updateSelective(insAllbustraninfo);
                        }
                    }
                    if ("07".equals(subType)) {
                        //资金运用类关联交易统计
                        InsAllfunds insAllfunds = (InsAllfunds) obj;
                        if (insAllfunds != null) {
                            if ("".equals(seq) || seq == null) {
                                //生成流水号
                                seq = BatchNumberUtil.makeBatchNumber();
                            }
                            //查询到的数据插入到查看表中
                            InsAllfundsView insAllfundsView = new InsAllfundsView();
                            BeanUtils.copyProperties(insAllfunds, insAllfundsView);
                            insAllfundsView.setAllfundsSeq(seq);
                            insAllfundsView.setPid(null);
                            insAllfundsView.setPortPid(insAllfunds.getPid());
                            insAllfundsViewService.insertSelective(insAllfundsView);
                            insAllfunds.setStatus("4");
                            insAllfundsService.updateSelective(insAllfunds);
                        }
                    }
                    if ("08".equals(subType)) {
                        //与单一法人资金运用类关联交易统计
                        InsRelfunds insRelfunds = (InsRelfunds) obj;
                        if (insRelfunds != null) {
                            if ("".equals(seq) || seq == null) {
                                //生成流水号
                                seq = BatchNumberUtil.makeBatchNumber();
                            }
                            //查询到的数据插入到查看表中
                            InsRelfundsView insRelfundsView = new InsRelfundsView();
                            BeanUtils.copyProperties(insRelfunds, insRelfundsView);
                            insRelfundsView.setRelfundsSeq(seq);
                            insRelfundsView.setPid(null);
                            insRelfundsView.setPortPid(insRelfunds.getPid());
                            if (StringUtils.isNotBlank(insRelfunds.getRelType()) && RelTypeCode.PSN.getId().equals(insRelfunds.getRelType())) {
                                insRelfunds.setRelNumber(personIdentityNo);
                                personIdentityNo = null;
                            }
                            insRelfundsViewService.insertSelective(insRelfundsView);
                            insRelfunds.setStatus("4");
                            insRelfundsService.updateSelective(insRelfunds);
                        }
                    }
                }
                OutputFormat format = OutputFormat.createPrettyPrint();
                File file = new File(filePath + "/" + fileName + ".xml");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdir();
                }
                XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
                writer.write(document);
                logger.info("写文件");
                writer.close();
            }
        }
        Sublog sublog = new Sublog();
        if (count > 0) {
            logger.info("生成报送数据");
            sublog.setFileName(fileName + ".xml");
            sublog.setCreateTime(new Date());
            sublog.setTenantId(profile.getTenantId());
            sublog.setSubYear(subYear);
            sublog.setSubQuarter(subQuarter);
            sublog.setSubType(subType);//报送类型
            sublog.setSubOrg("复星保德信人寿保险有限公司");//报送机构
            sublog.setDownStatus("1"); //是否下载   默认未下载  0-是 1-否
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            } else {
                sublog.setOperatorName(profile.getStaffName());
            }
            sublog.setStatus("01");  //状态默认是待提交
            sublog.setDownAmt(0);  //下载次数  默认是0
            sublog.setBatchDecimal(seq); //批次号
            sublog.setFileUrl(filePath + "/" + fileName + ".xml");//文件存放路径
            //sublog.setBatchDecimal(BatchNumberUtil.makeBatchNumber());
            sublog.setMaxPercentInNetAssets(new Date());
            sublogService.insertSelective(sublog);
            flag = true;
        } else {
            logger.info("暂无报送数据生成");
            flag = false;
        }
        return flag;
    }

    /**
     * 根据报送类型获取模板名字
     *
     * @param subType
     * @return
     */
    public String getTemplateName(String subType) {
        switch (subType) {
            case "E1":
                return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_E1_NAME;
            case "E2":
                return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_E2_NAME;
            case "E3":
                return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_E3_NAME;
            case "E4":
                return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_E4_NAME;
            case "E5":
                return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_E5_NAME;
            case "0":
                return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_0_NAME;
            case "1":
                return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_1_NAME;
            case "2":
                return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_2_NAME;
            case "3":
                return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_3_NAME;
            case "08":
                return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_08_NAME;
            case "07":
                return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_07_NAME;
            case "06":
                return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_06_NAME;
            default:
                return null;
        }
    }

    private void createElement(Element element, Integer parentId, Integer templateId, TemplateDetails templateDetails, Object obj) throws Exception {
        logger.info("父节点id:[{}],模板id[{}]", parentId, templateId);
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq("parentId", parentId));
        filters.add(Filter.eq("templateId", templateId));
        List<TemplateDetails> templateDetailsList = this.select(filters);
        logger.info("子节点是否为空:[{}]", templateDetailsList.isEmpty());
        if (templateDetailsList.isEmpty()) {
            if (StringUtils.isNotBlank(templateDetails.getColName())) {
                String colName = templateDetails.getColName();
                logger.info("获取字段：[{}]", colName);
                Method method = obj.getClass().getMethod("get" + colName.substring(0, 1).toUpperCase() + colName.substring(1));
                Method method1 = null;
                Method method2 = null;
                Method method3 = null;
                Method method4 = null;
                Object o1 = null;
                Object o2 = null;
                Object o3 = null;
                Object o4 = null;
                // 关系取值判定
                if ("3".equals(String.valueOf(templateId))) {
                    method1 = obj.getClass().getMethod("get" + "EntityType");
                    method2 = obj.getClass().getMethod("get" + "SuperEntityType");
                    o1 = method1.invoke(obj);
                    o2 = method2.invoke(obj);
                }
                // 单一报送
                if ("17".equals(String.valueOf(templateId))) {
                    method3 = obj.getClass().getMethod("get" + "RelType");
                    o3 = method3.invoke(obj);
                }
                // 资金运用类、保险业务及其他类
                boolean b = "10".equals(String.valueOf(templateId)) || "11".equals(String.valueOf(templateId))
                        || "12".equals(String.valueOf(templateId)) || "13".equals(String.valueOf(templateId)) || "14".equals(String.valueOf(templateId));
                if (b) {
                    method4 = obj.getClass().getMethod("get" + "RelType");
                    o4 = method4.invoke(obj);
                }
                Object o = method.invoke(obj);
                logger.info(colName + ":[{}]", o);
                if (o == null) {
                    element.setText("");
                } else {
                    // 自然人报送文件里加密证件号码
                    if ("2".equals(String.valueOf(templateId)) && "identityNo".equals(colName)) {
                        element.setText(MD5Utils.md5ToLowerCase(o.toString()));
                    }
                    // 关系报送里加密本层证件号码
                    else if ("3".equals(String.valueOf(templateId)) && "entityUniscUode".equals(colName) && Objects.nonNull(o1)) {
                        if (o1.toString().equals(RelTypeCode.PSN.getId())) {
                            element.setText(MD5Utils.md5ToLowerCase(o.toString()));
                        } else {
                            element.setText(o.toString());
                        }
                    }
                    // 关系报送里加密前一层证件号码
                    else if ("3".equals(String.valueOf(templateId)) && "superEntityUniscCode".equals(colName) && Objects.nonNull(o2)) {
                        if (o2.toString().equals(RelTypeCode.PSN.getId())) {
                            element.setText(MD5Utils.md5ToLowerCase(o.toString()));
                        } else {
                            element.setText(o.toString());
                        }
                    }
                    // 与单一关联方资金运用关联交易统计表自然人加密
                    else if ("17".equals(String.valueOf(templateId)) && "relNumber".equals(colName) && Objects.nonNull(o3)) {
                        if (o3.toString().equals(RelTypeCode.PSN.getId())) {
                            element.setText(MD5Utils.md5ToLowerCase(o.toString()));
                        } else {
                            element.setText(o.toString());
                        }
                    }
                    // 关联交易报送自然人加密
                    else if (b && "relNumber".equals(colName) && Objects.nonNull(o4)) {
                        if (RelatedPartyCategoryEnum.LEGALPERSON.getSysCode().equals(o4.toString())) {
                            element.setText(MD5Utils.md5ToLowerCase(o.toString()));
                        } else {
                            element.setText(o.toString());
                        }
                    } else {
                        element.setText(o.toString());
                    }
                }

            }
        } else {
            for (TemplateDetails templateDetails1 : templateDetailsList) {

                Element element1 = element.addElement(templateDetails1.getElementName());
                createElement(element1, templateDetails1.getPid(), templateId, templateDetails1, obj);
            }
        }
    }

    @Override
    public Map<String, Object> getcheckItem(Integer templateId) {
        logger.info("模板id:[{}]", templateId);
        Map<String, Object> checkItemMap = Maps.newHashMap();
        List<String> lengthColNameList = Lists.newArrayList();
        Map<String, Integer> lengthMap = Maps.newHashMap();
        List<String> mustList = Lists.newArrayList();
        List<Map<String, String>> conMustList = Lists.newArrayList();
        //校验名字与身份证匹配或名称与社会统一信用代码一致
        List<Map<String, String>> checkDataColNameList = Lists.newArrayList();
        List<Filter> filters = Lists.newArrayList();
        filters.add(Filter.eq("templateId", templateId));
        List<TemplateDetails> templateDetailsList = this.select(filters);
        templateDetailsList.stream().forEach(templateDetails -> {
            if (templateDetails.getColLength() != null) {
                String colName = templateDetails.getColName();
                lengthColNameList.add(colName);
                lengthMap.put(colName, templateDetails.getColLength());
                logger.info("校验长度字段：[{}],最大长度：[{}]", colName, templateDetails.getColLength());
            }
            if ("1".equals(templateDetails.getColMust())) {
                mustList.add(templateDetails.getColName());
                logger.info("校验必填字段：[{}]", templateDetails.getColName());
            }
            if ("2".equals(templateDetails.getColMust())) {
                Map<String, String> map = Maps.newHashMap();
                map.put("colName", templateDetails.getColName());
                map.put("conditionCol", templateDetails.getConditionCol());
                map.put("conditionValue", templateDetails.getConditionValue());
                conMustList.add(map);
                logger.info("校验条件下必填字段：[{}]", templateDetails.getColName());
            }
            if (templateDetails.getMatchName() != null && templateDetails.getColName() != null) {
                Map<String, String> checkDataMap = Maps.newHashMap();
                ;
                checkDataMap.put("col_name", templateDetails.getColName());
                checkDataMap.put("match_name", templateDetails.getMatchName());
                checkDataColNameList.add(checkDataMap);
                logger.info("匹配校验字段名：[{}],判断条件名：[{}]", templateDetails.getColName(), templateDetails.getMatchName());
            }
        });
        checkItemMap.put("lengthColNameList", lengthColNameList);
        checkItemMap.put("lengthMap", lengthMap);
        checkItemMap.put("mustColNameList", mustList);
        checkItemMap.put("conMustList", conMustList);
        checkItemMap.put("checkDataColNameList", checkDataColNameList);
        return checkItemMap;
    }

    /**
     * @param filters
     * @param fileName
     * @param subType
     * @return
     */
    @Override
    public boolean addEastView(List<Filter> filters, String fileName, String subType) {
        //获取当前用户
        UserProfile profile = UserProfileUtils.getUserProfile();
        configs = SpringContextHolder.getBean(Configs.class);
        String seq = null;
        Map<String, Object> tis = new HashMap<>();
        tis.put("subType", subType);
        long count = 0;
        switch (subType) {
            case "E1":
                count = eastGLFXXService.count(filters);
                break;
            case "E4":
                count = eastZDGLJYMXService.count(filters);
                break;
            case "E5":
                count = eastGljyhzbService.count(filters);
                break;
            default:
        }
        if (count > 0) {
            if ("E1".equals(subType)) {
                EastGLFXXView eastGLFXXView = null;
                List<EastGLFXX> list = eastGLFXXService.select(filters);
                for (EastGLFXX glfxx : list) {
                    if (glfxx != null) {
                        if ("".equals(seq) || seq == null) {
                            //生成流水号
                            seq = BatchNumberUtil.makeBatchNumber();
                        }
                    }
                    //将数据进行备份
                    eastGLFXXView = new EastGLFXXView();
                    eastGLFXXView.setGlfbh(glfxx.getGlfbh());
                    eastGLFXXView.setGlfmc(glfxx.getGlfmc());
                    eastGLFXXView.setGlflb(glfxx.getGlflb());
                    eastGLFXXView.setGlfsbm(glfxx.getGlfsbm());
                    eastGLFXXView.setTenantId(glfxx.getTenantId());
                    eastGLFXXView.setYxbz(glfxx.getYxbz());
                    eastGLFXXView.setQtglgxsm(glfxx.getQtglgxsm());
                    eastGLFXXView.setGxlx(glfxx.getGxlx());
                    eastGLFXXView.setPch(seq);
                    eastGLFXXView.setRelid(glfxx.getId());
                    eastGLFXXViewService.insert(eastGLFXXView);
                    //修改生成报送状态
                    glfxx.setEastStatus(0);
                    eastGLFXXService.update(glfxx);
                }
            }

            if ("E4".equals(subType)) {
                EastZDGLJYMXView eastZDGLJYMXView = null;
                List<EastZDGLJYMX> list = eastZDGLJYMXService.select(filters);
                for (EastZDGLJYMX zdgljymx : list) {
                    if (zdgljymx != null) {
                        if ("".equals(seq) || seq == null) {
                            //生成流水号
                            seq = BatchNumberUtil.makeBatchNumber();
                        }
                    }
                    //将数据进行备份
                    eastZDGLJYMXView = new EastZDGLJYMXView();
//                    BeanUtils.copyProperties(zdgljymx, eastZDGLJYMXView);
                    eastZDGLJYMXView.setGlfbh(zdgljymx.getGlfbh());
                    eastZDGLJYMXView.setJybh(zdgljymx.getJybh());
                    eastZDGLJYMXView.setGlfmc(zdgljymx.getGlfmc());
                    eastZDGLJYMXView.setGlflb(zdgljymx.getGlflb());
                    eastZDGLJYMXView.setHtqdrq(zdgljymx.getHtqdrq());
                    eastZDGLJYMXView.setGljyywlx(zdgljymx.getGljyywlx());
                    eastZDGLJYMXView.setGljyje(zdgljymx.getGljyje());
                    eastZDGLJYMXView.setTenantId(zdgljymx.getTenantId());
                    eastZDGLJYMXView.setRelid(zdgljymx.getId());
                    eastZDGLJYMXView.setPch(seq);
                    eastZDGLJYMXViewService.insert(eastZDGLJYMXView);
                    //修改生成报送状态
                    zdgljymx.setEastStatus(0);
                    eastZDGLJYMXService.update(zdgljymx);
                }
            }
            if ("E5".equals(subType)) {
                EastGljyhzbView eastGljyhzbView = null;
                List<EastGljyhzb> list = eastGljyhzbService.select(filters);
                for (EastGljyhzb gljyhzb : list) {
                    if (gljyhzb != null) {
                        if ("".equals(seq) || seq == null) {
                            //生成流水号
                            seq = BatchNumberUtil.makeBatchNumber();
                        }
                    }
                    //将数据进行备份
                    eastGljyhzbView = new EastGljyhzbView();
                    eastGljyhzbView.setTjzq(gljyhzb.getTjzq());//统计周期
                    eastGljyhzbView.setPch(seq);
                    eastGljyhzbView.setRelid(gljyhzb.getId());
                    eastGljyhzbView.setGlfbh(gljyhzb.getGlfbh());
                    eastGljyhzbView.setGlfmc(gljyhzb.getGlfmc());
                    eastGljyhzbView.setGlflb(gljyhzb.getGlflb());
                    eastGljyhzbView.setGljyje(gljyhzb.getGljyje());
                    eastGljyhzbView.setGljyywlx(gljyhzb.getGljyywlx());
                    eastGljyhzbViewService.insert(eastGljyhzbView);
                    //修改生成报送状态
                    gljyhzb.setEastStatus(0);
                    eastGljyhzbService.update(gljyhzb);
                }
            }
        } else {
            logger.info("暂无报送数据生成");
            return false;
        }


        Sublog sublog = new Sublog();

        logger.info("生成报送数据");
        sublog.setBatchDecimal(seq);
        sublog.setFileName(fileName);
        sublog.setCreateTime(new Date());
        sublog.setSubType(subType);//报送类型
        sublog.setDownStatus("1"); //是否下载   默认未下载  0-是 1-否
        if (profile == null) {
            throw new ServiceException("user session may be expired.");
        } else {
            sublog.setSubOrg(profile.getUnitCode());  //报送机构
            sublog.setOperatorName(profile.getStaffName());
        }
        sublog.setStatus("01");  //状态默认是待提交
        sublog.setDownAmt(0);  //下载次数  默认是0
        //sublog.setBatchDecimal(BatchNumberUtil.makeBatchNumber());
        sublog.setMaxPercentInNetAssets(new Date());
        sublogService.insert(sublog);
        return true;
    }

    public void subRelatedParties(List<Filter> filters, Connection conn, PreparedStatement ps, ResultSet rs) {
        String seq = null;
        EastGLFXXView eastGLFXXView = null;
        try {
            //定义sql
            String sql = "insert into eastic_glfxxb (lsh,bxjgdm,bxjgmc,khbh,khmc,khsbm,khlb,glfbh,glfmc,glfsbm,glflb,gxlx,qtgllxsm,glyxbz,dis_bank_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            //获取执行sql的对象
            ps = conn.prepareStatement(sql);
            List<EastGLFXX> list = eastGLFXXService.select(filters);
            Long startTime = System.currentTimeMillis();
            for (EastGLFXX glfxx : list) {
                if (glfxx != null) {
                    if ("".equals(seq) || seq == null) {
                        //生成流水号
                        seq = BatchNumberUtil.makeBatchNumber();
                    }
                }
                ps.setString(1, seq);
                ps.setString(2, Submitted.ORGNUMBER.getId());
                ps.setString(3, Submitted.ORGNUMBER.getName());
                ps.setString(4, Submitted.NOTAPPLICABLE.getName());
                ps.setString(5, Submitted.NOTAPPLICABLE.getName());
                ps.setString(6, Submitted.NOTAPPLICABLE.getName());
                ps.setString(7, Submitted.NOTAPPLICABLE.getName());
                ps.setString(8, glfxx.getGlfbh());
                ps.setString(9, glfxx.getGlfmc());
                ps.setString(10, glfxx.getGlfsbm());
                ps.setString(11, glfxx.getGlflb());
                ps.setString(12, glfxx.getJgzjlx());
                ps.setString(13, "无");
                ps.setString(14, glfxx.getYxbz());
                ps.setInt(15, 1);
                //将sql语句放入batch
                ps.addBatch();
            }
            int[] a = ps.executeBatch();
            conn.commit();
            Long endTime = System.currentTimeMillis();
            logger.info("用时：" + (endTime - startTime));
            String pch = BatchNumberUtil.makeBatchNumber();
            for (int i = 0; i < a.length; i++) {
                if (a[i] > 0) {
                    EastGLFXX glfxx = list.get(i);
                    //将数据进行备份
                    eastGLFXXView = new EastGLFXXView();
                    eastGLFXXView.setBussedate(glfxx.getBussedate());
                    eastGLFXXView.setPch(pch);
                    eastGLFXXView.setRelid(glfxx.getId());
                    eastGLFXXView.setGlfbh(glfxx.getGlfbh());
                    eastGLFXXView.setGlfmc(glfxx.getGlfmc());
                    eastGLFXXView.setGlfsbm(glfxx.getGlfsbm());
                    eastGLFXXView.setGlflb(glfxx.getGlflb());
                    eastGLFXXView.setGrzjlx(glfxx.getGrzjlx());
                    eastGLFXXView.setJgzjlx(glfxx.getJgzjlx());
                    eastGLFXXView.setJnwxz(glfxx.getJnwxz());
                    eastGLFXXView.setYxbz(glfxx.getYxbz());
                    eastGLFXXViewService.insertSelective(eastGLFXXView);
                    //修改生成报送状态
                    glfxx.setEastStatus(0);
                    eastGLFXXService.update(glfxx);
                }
            }
            this.syslog(pch, Submitted.E1.getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("east报送异常！", e.getMessage());
        } finally {
            JdbcUtil.close(rs, ps, conn);
        }
    }

    public boolean subRelationship(List<Filter> filters, Connection conn, PreparedStatement ps, ResultSet rs) {
        String seq = null;
        EastGLGXView eastGLGXView = null;
        try {
            //定义sql
            String sql = "insert into eastic_glfxxb (lsh,bxjgdm,bxjgmc,khbh,khmc,khsbm,khlb,glfbh,glfmc,glfsbm,glflb,gxlx,qtgllxsm,glyxbz,dis_bank_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            //获取执行sql的对象
            ps = conn.prepareStatement(sql);
            List<EastGLGX> list = eastGLGXService.select(filters);
            Long startTime = System.currentTimeMillis();
            for (EastGLGX glfgx : list) {
                if (glfgx != null) {
                    if ("".equals(seq) || seq == null) {
                        //生成流水号
                        seq = BatchNumberUtil.makeBatchNumber();
                    }
                }
                ps.setString(1, seq);
                ps.setString(2, glfgx.getSubNum());
                ps.setString(3, glfgx.getSubOrg());
                ps.setString(4, glfgx.getKhbh());
                ps.setString(5, glfgx.getKhmc());
                ps.setString(6, glfgx.getKhsbm());
                ps.setString(7, glfgx.getKhlb());
                ps.setString(8, glfgx.getGlfbh());
                ps.setString(9, glfgx.getGlfmcGlfzjjdmc());
                ps.setString(10, glfgx.getGlfdmGlfzjjddm());
                ps.setString(11, glfgx.getGlfGlfzjjdlx());
                ps.setString(12, glfgx.getYqycdglgx());
                ps.setString(13, glfgx.getGlgxsm());
                ps.setString(14, glfgx.getGxyxbz());
                ps.setInt(15, 1);
                //将sql语句放入batch
                ps.addBatch();
            }
            int[] a = ps.executeBatch();
            conn.commit();
            Long endTime = System.currentTimeMillis();
            logger.info("用时：" + (endTime - startTime));
            String pch = BatchNumberUtil.makeBatchNumber();
            for (int i = 0; i < a.length; i++) {
                if (a[i] > 0) {
                    EastGLGX glfgx = list.get(i);
                    eastGLGXView = new EastGLGXView();
                    //将数据进行备份
                    eastGLGXView.setBussedate(glfgx.getBussedate());
                    eastGLGXView.setGlfmcGlfzjjdmc(glfgx.getGlfmcGlfzjjdmc());
                    eastGLGXView.setGlfdmGlfzjjddm(glfgx.getGlfdmGlfzjjddm());
                    eastGLGXView.setGlfGlfzjjdlx(glfgx.getGlfGlfzjjdlx());
                    eastGLGXView.setQycglfmcGlfzjjdmc(glfgx.getQycglfmcGlfzjjdmc());
                    eastGLGXView.setQycglfdmGlfzjjddm(glfgx.getQycglfdmGlfzjjddm());
                    eastGLGXView.setQycglfGlfzjjdlx(glfgx.getQycglfGlfzjjdlx());
                    eastGLGXView.setYqycdglgx(glfgx.getYqycdglgx());
                    eastGLGXView.setYqyczjsfyqyfe(glfgx.getYqyczjsfyqyfe());
                    eastGLGXView.setPch(pch);
                    eastGLGXView.setCybl(glfgx.getCybl());
                    eastGLGXView.setRelid(glfgx.getId());
                    eastGLGXView.setZygqbl(glfgx.getZygqbl());
                    eastGLGXView.setGlgxsm(glfgx.getGlgxsm());
                    eastGLGXView.setGxyxbz(glfgx.getGxyxbz());
                    eastGLGXView.setSxrq(glfgx.getSxrq());
                    eastGLGXView.setSxrq1(glfgx.getSxrq1());
                    eastGLGXView.setGlfbh(glfgx.getGlfbh());
                    eastGLGXView.setSubNum(glfgx.getSubNum());
                    eastGLGXView.setSubOrg(glfgx.getSubOrg());
                    eastGLGXView.setKhbh(glfgx.getKhbh());
                    eastGLGXView.setKhlb(glfgx.getKhlb());
                    eastGLGXView.setKhmc(glfgx.getKhmc());
                    eastGLGXView.setKhsbm(glfgx.getKhsbm());
                    eastGLGXViewService.insertSelective(eastGLGXView);
                    //修改生成报送状态
                    glfgx.setEastStatus(0);
                    eastGLGXService.update(glfgx);
                }
            }
            this.syslog(pch, Submitted.E3.getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("east报送异常！", e.getMessage());
            return false;
        } finally {
            JdbcUtil.close(rs, ps, conn);
        }
        return true;
    }

    public boolean subMajor(List<Filter> filters, Connection conn, PreparedStatement ps, ResultSet rs) {
        String seq = null;
        EastZDGLJYMXView eastZDGLJYMXView = null;
        try {
            List<EastZDGLJYMX> list = eastZDGLJYMXService.select(filters);
            //定义sql
            String sql = "insert into eastic_zdgljyb (lsh,bxjgdm,bxjgmc,khbh,glfbh,jybh,glfmc,gljyrq,gljylx,gljyje,dis_bank_id) values(?,?,?,?,?,?,?,?,?,?,?)";
            //获取执行sql的对象
            ps = conn.prepareStatement(sql);
            Long startTime = System.currentTimeMillis();
            for (EastZDGLJYMX zdgljymx : list) {
                if (zdgljymx != null) {
                    if ("".equals(seq) || seq == null) {
                        //生成流水号
                        seq = BatchNumberUtil.makeBatchNumber();
                    }
                }
                ps.setString(1, seq);
//                ps.setString(2, zdgljymx.getSubNum());
//                ps.setString(3, zdgljymx.get
//                ());
//                ps.setString(4, zdgljymx.getKhbh());
                ps.setString(5, zdgljymx.getGlfbh());
                ps.setString(6, zdgljymx.getJybh());
                ps.setString(7, zdgljymx.getGlfmc());
                ps.setString(8, zdgljymx.getBussedate());
                ps.setString(9, zdgljymx.getGljyywlx());
//                ps.setString(10, zdgljymx.getGljyje());
                ps.setInt(11, 1);

                //将sql语句放入batch
                ps.addBatch();
            }
            int[] a = ps.executeBatch();
            conn.commit();
            Long endTime = System.currentTimeMillis();
            logger.info("用时：" + (endTime - startTime));
            String pch = BatchNumberUtil.makeBatchNumber();
            for (int i = 0; i < a.length; i++) {
                if (a[i] > 0) {
                    //将数据进行备份
                    EastZDGLJYMX zdgljymx = list.get(i);
                    eastZDGLJYMXView = new EastZDGLJYMXView();
                    BeanUtils.copyProperties(zdgljymx, eastZDGLJYMXView);
                    eastZDGLJYMXView.setRelid(zdgljymx.getId());
                    eastZDGLJYMXView.setPch(pch);
                    eastZDGLJYMXView.setGlfbh(zdgljymx.getGlfbh());
//                    eastZDGLJYMXView.setSubNum(zdgljymx.getSubNum());
//                    eastZDGLJYMXView.setSubOrg(zdgljymx.getSubOrg());
//                    eastZDGLJYMXView.setKhbh(zdgljymx.getKhbh());
//                    eastZDGLJYMXView.setKhlb(zdgljymx.getKhlb());
//                    eastZDGLJYMXView.setKhmc(zdgljymx.getKhmc());
//                    eastZDGLJYMXView.setKhsbm(zdgljymx.getKhsbm());
                    eastZDGLJYMXViewService.insertSelective(eastZDGLJYMXView);
                    //修改生成报送状态
                    zdgljymx.setEastStatus(0);
                    eastZDGLJYMXService.update(zdgljymx);
                }
            }
            this.syslog(pch, Submitted.E4.getId());
        } catch (Exception e) {
            logger.error("east报送异常！", e.getMessage());
            return false;
        } finally {
            JdbcUtil.close(rs, ps, conn);
        }
        return true;
    }

    public boolean subTransactionSum(List<Filter> filters, Connection conn, PreparedStatement ps, ResultSet rs, String year, String quarter) {
        String seq = null;
        EastGljyhzbView eastGljyhzbView = null;
        try {
            List<EastGljyhzb> list = eastGljyhzbService.select(filters);
//            //定义sql
//            String sql = "insert into eastic_gljyhzb (lsh,bxjgdm,bxjgmc,tjzq,khbh,glfbh,glfmc,gljylx,gljyje,dis_bank_id) values(?,?,?,?,?,?,?,?,?,?)";
//            //获取执行sql的对象
//            ps = conn.prepareStatement(sql);
            Long sTime = System.currentTimeMillis();
            String zq = year + Submitted.getLabel(quarter);
            for (EastGljyhzb gljyhzb : list) {
                if (gljyhzb != null) {
                    if ("".equals(seq) || seq == null) {
                        //生成流水号
                        seq = BatchNumberUtil.makeBatchNumber();
                    }
                }
//                ps.setString(1,seq);
//                ps.setString(2,gljyhzb.getSubNum());
//                ps.setString(3,gljyhzb.getSubOrg());
//                ps.setString(4,zq);
//                ps.setString(5,gljyhzb.getKhbh());
//                ps.setString(6,gljyhzb.getGlfbh());
//                ps.setString(7,gljyhzb.getGlfmc());
//                ps.setString(8,gljyhzb.getGljyywlx());
//                ps.setString(9,gljyhzb.getGljyje());
//                ps.setInt(10,1);

                //将sql语句放入batch
                ps.addBatch();
            }
            int[] a = ps.executeBatch();
            conn.commit();
            Long eTime = System.currentTimeMillis();
            logger.info("用时：" + (eTime - sTime));
            String pch = BatchNumberUtil.makeBatchNumber();
            for (int i = 0; i < a.length; i++) {
                if (a[i] > 0) {
                    //将数据进行备份
                    EastGljyhzb gljyhzb = list.get(i);
                    eastGljyhzbView = new EastGljyhzbView();
                    eastGljyhzbView.setBussedate(gljyhzb.getBussedate());
                    eastGljyhzbView.setPch(pch);
                    eastGljyhzbView.setRelid(gljyhzb.getId());
                    eastGljyhzbView.setGlfbh(gljyhzb.getGlfbh());
                    eastGljyhzbView.setGlfmc(gljyhzb.getGlfmc());
                    eastGljyhzbView.setGljyje(gljyhzb.getGljyje());
                    eastGljyhzbView.setGljyywlx(gljyhzb.getGljyywlx());
//                    eastGljyhzbView.setSubNum(gljyhzb.getBxjgdm());
//                    eastGljyhzbView.setSubOrg(gljyhzb.getBxjgmc());
                    eastGljyhzbView.setKhbh(gljyhzb.getKhbh());
//                    eastGljyhzbView.setKhlb(gljyhzb.getKhlb());
//                    eastGljyhzbView.setKhmc(gljyhzb.getKhmc());
//                    eastGljyhzbView.setKhsbm(gljyhzb.getKhsbm());
                    eastGljyhzbView.setTjzq(zq);
                    eastGljyhzbViewService.insertSelective(eastGljyhzbView);
                    //修改生成报送状态
                    gljyhzb.setEastStatus(0);
                    eastGljyhzbService.update(gljyhzb);
                }
            }
            this.syslog(pch, Submitted.E5.getId());
        } catch (Exception e) {
            logger.error("east报送异常！", e.getMessage());
            return false;
        } finally {
            JdbcUtil.close(rs, ps, conn);
        }
        return true;
    }

    public void syslog(String seq, String subType) {
        //获取当前用户
        UserProfile profile = UserProfileUtils.getUserProfile();
        Sublog sublog = new Sublog();
        logger.info("生成报送数据");
        sublog.setBatchDecimal(seq);
        sublog.setCreateTime(new Date());
        //报送类型
        sublog.setSubType(subType);
        //是否下载   默认未下载  0-是 1-否
        sublog.setDownStatus("1");
        if (profile == null) {
            throw new ServiceException("user session may be expired.");
        } else {
            //报送机构
            sublog.setSubOrg(profile.getUnitCode());
            sublog.setOperatorName(profile.getStaffName());
        }
        //状态默认是待提交
        sublog.setStatus("01");
        //下载次数  默认是0
        sublog.setDownAmt(0);
        StringBuffer buffer = new StringBuffer();
        LocalDate localDate = LocalDate.now();
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        String currentDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        buffer.append(Submitted.getLabel(subType));
        buffer.append("_");
        buffer.append(currentDate);
        sublog.setFileName(buffer.toString());
        sublog.setMaxPercentInNetAssets(Date.from(zonedDateTime.toInstant()));
        sublog.setSubOrg(Submitted.ORGNUMBER.getName());
        sublog.setSubmissionDate(Date.from(zonedDateTime.toInstant()));
        sublogService.insertSelective(sublog);
    }

    @Override
    public void creatExcel(String bean, String fileName, Map<String, Object> params) throws Exception {
        configs = SpringContextHolder.getBean(Configs.class);
        String subType = (String) params.get("subType");
        //选择excel模板
        XlsExportCallback eCallback = (XlsExportCallback) SpringContextHolder.getBean(bean);
        String templateName = this.getTemplateName(subType);
        //生成文件所放位置
        String filePath = configs.getEastFile();
        //调用业务方法,组装导出XLS报表所需数据
        Map context = eCallback.executeExportCallback(params);
        //将文件放到指定路径
        File dest = new File(filePath + "/" + fileName);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        int i = 1;
        //判断文件是否存在,存在重命名
        while (dest.exists()) {
            String name = fileName.substring(0, fileName.indexOf("."));
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String newFilename = name + "(" + i + ")" + suffix;
            dest = new File(filePath + "/" + newFilename);
            i++;
        }
        FileOutputStream out = new FileOutputStream(dest);
        XlsTransformer4Merge transformer = new XlsTransformer4Merge();
        transformer.setBeanId(bean);
        transformer.transformXLS(templateName, context, out);
        if (null != context) {
            context.clear();
        }
        if (null != params) {
            params.clear();
        }
        out.flush();
        out.close();
    }

}
