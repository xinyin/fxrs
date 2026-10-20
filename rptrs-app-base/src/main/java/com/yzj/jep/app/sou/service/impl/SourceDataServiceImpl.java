package com.yzj.jep.app.sou.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.sou.model.Insider;
import com.yzj.jep.app.sou.model.IousInfo;
import com.yzj.jep.app.sou.model.Organization;
import com.yzj.jep.app.sou.model.Shareholder;
import com.yzj.jep.app.sou.service.*;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.rules.drools5.service.PackagesService;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhangfei.
 * date 2020/4/14.
 * time 13:53.
 */
@Service
@Transactional
public class SourceDataServiceImpl implements SourceDataService {
    Logger logger = LoggerFactory.getLogger(SourceDataServiceImpl.class);
    public static final String BUS_AssetTransfer = "BUS-AssetTransfer";
    public static final String BUS_CreditCategory = "BUS-CreditCategory";
    public static final String BUS_ProvideServices = "BUS-ProvideServices";
    public static final String BUS_OtherType = "BUS-OtherType";

    @Autowired
    private PackagesService packagesService;
    //源股东信息
    @Autowired
    private ShareholderService shareholderService;
    //源人力内部员工信息
    @Autowired
    private InsiderService insiderService;
    //源人力内部机构信息
    @Autowired
    private OrganizationService organizationService;
    //源业务系统信贷信息
    @Autowired
    private IousInfoService iousInfoService;
    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private RelService relService;
    @Autowired
    private TraCreditService traCreditService;
    @Autowired
    private TraAssetService traAssetService;
    @Autowired
    private DictItemService dictItemService;


    /**
     * 获取sysDate
     *
     * @return 日期
     */
    private String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.add(Calendar.DATE, -1);
        return sdf.format(date.getTime());
    }

    @Override
    public void dealIousInfo() {
        //查出所有源人力内部员工数据
        List<Filter> filters = new Filter.Builder().nl("version").build();
        List<IousInfo> iousInfos = iousInfoService.select(filters);
        for (IousInfo iousInfo : iousInfos) {

            //判断交易用户是否是关联方
            List<Filter> psnFilters = new Filter.Builder().eq("name", iousInfo.getCusName())
                    .eq("identifyType", "IDCARD")
                    .eq("identifyNumber", iousInfo.getCusIdCard()).build();
            List<RelPsn> psns = relPsnService.select(psnFilters);
            if (psns.size() == 0) {
                logger.info("用户[{}]不存在关联表", iousInfo.getCusName());
                iousInfo.setVersion(0);
                iousInfoService.update(iousInfo);
                continue;
            }
            //判断交易类型是否为关联交易
            String busType = iousInfo.getBusType();
            Map isRelTranMap = checkBusType(busType);
            boolean isRelTran = (boolean) isRelTranMap.get("result");
            String code = (String) isRelTranMap.get("code");
            //入库关联交易数据库
            if (isRelTran) {
                switch (code) {
                    //授信入库
                    case BUS_CreditCategory:
                        logger.info("授信类交易准备入库");
                        inDataCredit(iousInfo);
                        logger.info("授信类交易入库完成");
                        break;
                    //资产转移
                    case BUS_AssetTransfer:
                        logger.info("资产转移类交易准备入库");
                        inDataAsset(iousInfo);
                        logger.info("资产转移类交易入库完成");
                        break;
                    //提供服务
                    case BUS_ProvideServices:
                        logger.info("提供服务类交易待开发");
                        break;
                    //其他
                    case BUS_OtherType:
                        logger.info("其他服务类交易待开发");
                        break;
                    default:
                        logger.info("非关联交易[{}]", iousInfo.getCusName());
                }
                iousInfo.setVersion(0);
                iousInfoService.update(iousInfo);
                logger.info("业务系统交易信息[{}]抽取完成", iousInfo.getCusName());
            } else {
                logger.info("非关联交易[{}]", iousInfo.getCusName());
            }
        }
    }

    private void inDataCredit(IousInfo iousInfo) {
        TraCredit traCredit = changeIousToTra(iousInfo);
        traCreditService.insert(traCredit);
    }

    private void inDataAsset(IousInfo iousInfo) {
        TraAsset traAsset = changeIousToAsset(iousInfo);
        traAssetService.insert(traAsset);
    }

    private TraAsset changeIousToAsset(IousInfo iousInfo) {
        TraAsset traAsset = new TraAsset();
        traAsset.setTransNum(iousInfo.getLoanNum());
        traAsset.setRelatedName(iousInfo.getCusName());
        //后续需改造为对应源数据动态变
        traAsset.setIdentifyType("IDCARD");
        traAsset.setIdentNumber(iousInfo.getCusIdCard());
        traAsset.setTranType(getTranCode(iousInfo.getBusType(), BUS_AssetTransfer));
        traAsset.setBusinessType("资产转移类");
        traAsset.setRelatedTranType("一般关联交易");
        //必填数据有空为B-待补录，数据完整为A已归档
        traAsset.setStatus("B");
        traAsset.setIsDel(1);
        traAsset.setDelflag("1");
        traAsset.setVersion(0);
        return traAsset;
    }

    private TraCredit changeIousToTra(IousInfo iousInfo) {
        TraCredit traCredit = new TraCredit();
        traCredit.setTransNum(iousInfo.getLoanNum());
        traCredit.setRelatedName(iousInfo.getCusName());
        //后续需改造为对应源数据动态变
        traCredit.setIdentifyType("IDCARD");
        traCredit.setIdentNumber(iousInfo.getCusIdCard());
        traCredit.setTranType(getTranCode(iousInfo.getBusType(), BUS_CreditCategory));
        traCredit.setCreditAmount(iousInfo.getLoansAmount());
        traCredit.setCreditStartDate(iousInfo.getGrantDate());
        traCredit.setCreditEndDate(iousInfo.getExpireDate());
        traCredit.setBusinessType("授信类");
        traCredit.setRelatedTranType("一般关联交易");
        //必填数据有空为B-待补录，数据完整为A已归档
        traCredit.setStatus("B");
        traCredit.setIsDel(1);
        traCredit.setDelflag("1");
        traCredit.setVersion(0);
        return traCredit;
    }

    private String getTranCode(String busType, String code) {
        List<Filter> filters = new Filter.Builder().eq("dictCode", code).eq("itemLabel", busType).build();
        DictItem dictItem = dictItemService.get(filters);
        return dictItem.getItemValue();
    }

    private Map checkBusType(String busType) {
        List<NameValuePair> items = new ArrayList<>();
        Map resultMap = new HashMap();
        boolean result;
        List<Filter> filters = new Filter.Builder().eq("dictCode", "BUS-AssetTransfer").build();
        //资产转移交易类组装到所有关联交易类型
        result = assembleListReturn(items, filters, busType);
        resultMap.put("result", true);
        if (result) {
            resultMap.put("code", BUS_AssetTransfer);
            return resultMap;
        }
        List<Filter> creditFilters = new Filter.Builder().eq("dictCode", "BUS-CreditCategory").build();
        //授信类交易类组装到所有关联交易类型
        result = assembleListReturn(items, creditFilters, busType);
        if (result) {
            resultMap.put("code", BUS_CreditCategory);
            return resultMap;
        }
        List<Filter> provideFilters = new Filter.Builder().eq("dictCode", "BUS-ProvideServices").build();
        //提供服务交易类组装到所有关联交易类型
        result = assembleListReturn(items, provideFilters, busType);
        if (result) {
            resultMap.put("code", BUS_ProvideServices);
            return resultMap;
        }
        List<Filter> otherFilters = new Filter.Builder().eq("dictCode", "BUS-OtherType").build();
        //其他关联交易类组装到所有关联交易类型
        result = assembleListReturn(items, otherFilters, busType);
        if (result) {
            resultMap.put("code", BUS_OtherType);
            return resultMap;
        }
        resultMap.put("result", false);
        return resultMap;
    }

    private boolean assembleListReturn(List<NameValuePair> items, List<Filter> filters, String busType) {
        List<DictItem> list = dictItemService.select(filters);
        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                DictItem item = list.get(i);
                BasicNameValuePair nv = new BasicNameValuePair(item.getItemLabel(), item.getItemValue());
                items.add(nv);
            }
        }
        //判断是否是关联交易类型
        for (NameValuePair valuePair : items) {
            if (valuePair.getName().equals(busType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void dealOrgInfo(Map map) {
        //查出所有源人力内部员工数据
        List<Filter> filters = new Filter.Builder().nl("version").build();
        List<Organization> organizations = organizationService.select(filters);
        String packageId = "fc473b58-7a4b-11ea-a153-8cec4b488362";
        for (Organization organization : organizations) {
            JSONObject reqMap = new JSONObject();
            reqMap.put("rlOrgProportion", organization.getShareholdingProportion());
            //调用规则引擎（人力机构采集规则）
            logger.info("人力机构采集规则----机构名：" + organization.getOrgName());
            Map resMap = packagesService.execute(packageId, reqMap.toJSONString());
            Map ruleRetMap = (Map) resMap.get("resultMap");
            //非关联方
            if (ruleRetMap.get("retCode") == null) {
                logger.info("非关联方机构---机构名：" + organization.getOrgName());
                organization.setVersion(0);
                organizationService.update(organization);
                continue;
            }
            int retCode = (int) ruleRetMap.get("retCode");
            //查验源机构是否已存在关联表
            List<Filter> orgSFilters = new Filter.Builder().eq("orgName", organization.getOrgName()).build();
            List<RelOrg> orgSs = relOrgService.select(orgSFilters);
            if (orgSs.size() > 0) {
                logger.info("机构[{}]存在关联表", organization.getOrgName());
                organization.setVersion(0);
                organizationService.update(organization);
                continue;
            }
            String orgName = String.valueOf(map.get("orgName"));
            //查出关联机构与之绑定关系
            List<Filter> orgFilters = new Filter.Builder().eq("orgName", orgName).build();
            List<RelOrg> orgs = relOrgService.select(orgFilters);
            if (orgs.size() == 0) {
                logger.info("主机构[{}]不存在关联表", orgName);
                continue;
            }
            //更新机构到关联表
            RelOrg org = changeToOrg(organization, map);
            Rel rel = changeOrgToRel(org);
            if (retCode == 0) {
                //关联方保存入库
                logger.info("关联方机构保存入库");
                //插入到机构关联表
                relOrgService.insert(org);
                //插入关联关系表
                org = relOrgService.get(org);
                rel.setToPid(String.valueOf(org.getPid()));
                relService.insert(rel);
            }
            organization.setVersion(0);
            organizationService.update(organization);
        }
    }

    private Rel changeOrgToRel(RelOrg org) {
        Rel rel = new Rel();
        //from
        rel.setFromPid(String.valueOf(org.getOrignalOrg()));
        rel.setFromType("ORG");
        rel.setFromName(org.getRelType());
        rel.setFromMemo(org.getOrignalOrgName());
        //to
//        rel.setToPid(String.valueOf(org.getPid()));
        rel.setToType("ORG");
        rel.setToName(org.getRelType());
        rel.setToMemo(org.getOrgName());
        rel.setRelCategory("C");
        rel.setRelSort("1");
        rel.setIsShow("1");
        rel.setDelflag("1");
        return rel;
    }

    private RelOrg changeToOrg(Organization organization, Map map) {
        RelOrg relOrg = new RelOrg();
        relOrg.setOrgName(organization.getOrgName());
        relOrg.setRegCode(organization.getRegCode());
        relOrg.setOrignalOrg(String.valueOf(map.get("orgId")));
        relOrg.setOrignalOrgName(String.valueOf(map.get("orgName")));
        relOrg.setIdentifyType("UNIFIEDSOCIALCREDITCODE");
        relOrg.setUniscCode(organization.getUniscCode());
        relOrg.setLegalPerson(organization.getLegalPerson());
        relOrg.setRegAddress(organization.getRegAddress());
        relOrg.setOrgType(organization.getOrgProperty());
        relOrg.setShareholdingProportion(organization.getShareholdingProportion());
        relOrg.setValidStatus("1");
        relOrg.setRelType("1");
        relOrg.setIsBlack("0");
        relOrg.setDelflag("1");
        return relOrg;
    }

    @Override
    public void dealInsider(Map map) {
        //查出所有源人力内部员工数据
        List<Filter> filters = new Filter.Builder().nl("version").build();
        List<Insider> insiders = insiderService.select(filters);
        String packageId = "641b7103-7a25-11ea-a153-8cec4b488362";
        for (Insider insider : insiders) {
            JSONObject reqMap = new JSONObject();
            reqMap.put("rlDepart", insider.getDepart());
            reqMap.put("rlDuty", insider.getDuty());
            //调用规则引擎（人力采集规则）
            logger.info("人力采集规则----姓名：" + insider.getName());
            Map resMap = packagesService.execute(packageId, reqMap.toJSONString());
            Map ruleRetMap = (Map) resMap.get("resultMap");
            //非关联方
            if (ruleRetMap.get("retCode") == null) {
                logger.info("非关联方---姓名：" + insider.getName());
                continue;
            }
            int retCode = (int) ruleRetMap.get("retCode");
            //查验用户是否已存在关联表
            List<Filter> psnFilters = new Filter.Builder().eq("name", insider.getName())
                    .eq("identifyType", "IDCARD")
                    .eq("identifyNumber", insider.getIdCard()).build();
            List<RelPsn> psns = relPsnService.select(psnFilters);
            if (psns.size() > 0) {
                logger.info("用户[{}]已存在关联表", insider.getName());
                //标记已完成同步股东信息
                insider.setVersion(0);
                insiderService.update(insider);
                continue;
            }

            String orgName = String.valueOf(map.get("orgName"));
            //查验机构是否已存在关联表
            List<Filter> orgFilters = new Filter.Builder().eq("orgName", orgName).build();
            List<RelOrg> orgs = relOrgService.select(orgFilters);
            if (orgs.size() == 0) {
                logger.info("机构[{}]不存在关联表", orgName);
                continue;
            }

            RelPsn psn = changeInsToPsn(insider, map);
            RelOrg org = orgs.get(0);
            Rel rel = changeInsToRel(psn, org);
            if (retCode == 0) {
                //关联方保存入库
                logger.info("关联方保存入库");
                //插入到自然人关联表
                relPsnService.insert(psn);
                //插入关联关系表
                psn = relPsnService.get(psn);
                rel.setToPid(String.valueOf(psn.getPid()));
                relService.insert(rel);
            }

            //配偶是否关联方
            if (insider.getSpName() != null) {
                //查验用户是否已存在关联表
                List<Filter> spFilters = new Filter.Builder().eq("name", insider.getName())
                        .eq("mobile", insider.getSpMobile()).build();
                List<RelPsn> sppsns = relPsnService.select(spFilters);
                Rel spRel = new Rel();
                if (sppsns.size() > 0) {
                    RelPsn relSpPsn = sppsns.get(0);
                    logger.info("用户配偶[{}]已存在关联表", sppsns.get(0).getName());
                    spRel = changeToSpRel(psn, relSpPsn);
                    //插入关联关系表
                    spRel.setToPid(String.valueOf(relSpPsn.getPid()));
                    relService.insert(spRel);
                } else {
                    RelPsn spPsn = changeToSpPsn(insider);
                    //插入到配偶到自然人关联表
                    relPsnService.insert(spPsn);
                    spRel = changeToSpRel(psn, spPsn);
                    //插入关联关系表
                    relPsnService.get(spPsn);
                    spRel.setToPid(String.valueOf(spPsn.getPid()));
                    relService.insert(spRel);
                }
            }
            //标记已完成同步股东信息
            insider.setVersion(0);
            insiderService.update(insider);
        }
    }

    private RelPsn changeToSpPsn(Insider insider) {
        RelPsn psn = new RelPsn();
        psn.setName(insider.getSpName());
        psn.setSex(insider.getGender() == 1 ? "man" : "woman");
        psn.setIdentifyType("IDCARD");
        psn.setOrignalOrg(insider.getSpCompany());
        psn.setOrignalOrgName(insider.getSpCompany());
        psn.setMobile(insider.getSpMobile());
        psn.setDutyCode(insider.getSpDuty());
        String maStatus = changeMaritalStatus(insider.getMaritalStatus());
        psn.setMaritalStatus(maStatus);
        psn.setRelType("1");
        psn.setIsBlack("0");
        psn.setDelflag("1");
        psn.setMgerOrg("1");
        return psn;
    }

    private String changeMaritalStatus(String maritalStatus) {
        String status;
        switch (maritalStatus) {
            case "未婚":
                status = "unmarried";
                break;
            case "已婚":
                status = "married";
                break;
            case "离异":
                status = "divorced";
                break;
            case "丧偶":
                status = "deathSpouse";
                break;
            default:
                status = "unmarried";
        }
        return status;
    }

    private Rel changeToSpRel(RelPsn insider, RelPsn spPsn) {
        Rel rel = new Rel();
        //from
        rel.setFromPid(String.valueOf(insider.getPid()));
        rel.setFromType("PSN");
        rel.setFromName("配偶");
        rel.setFromMemo(insider.getName());
        //to
        rel.setToPid(String.valueOf(spPsn.getPid()));
        rel.setToType("PSN");
        rel.setToName("配偶");
        rel.setToMemo(spPsn.getName());
        rel.setRelCategory("B");
        rel.setRelSort("1");
        rel.setIsShow("1");
        return rel;
    }

    private Rel changeInsToRel(RelPsn psn, RelOrg org) {
        Rel rel = new Rel();
        //from
        rel.setFromPid(String.valueOf(org.getPid()));
        rel.setFromType("ORG");
        rel.setFromName("公司");
        rel.setFromMemo(org.getOrgName());
        //to
//        rel.setToPid(String.valueOf(psn.getPid()));
        rel.setToType("PSN");
        rel.setToName(psn.getDutyCode());
        rel.setToMemo(psn.getName());
        rel.setRelCategory("B");
        rel.setRelSort("1");
        rel.setIsShow("1");

        return rel;
    }

    private RelPsn changeInsToPsn(Insider insider, Map map) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        RelPsn psn = new RelPsn();
        psn.setName(insider.getName());
        psn.setIdentifyType("IDCARD");
        psn.setIdentifyNumber(insider.getIdCard());
        psn.setSex(insider.getGender() == 0 ? "man" : "woman");
        psn.setOrignalOrg(String.valueOf(map.get("orgId")));
        psn.setOrignalOrgName(String.valueOf(map.get("orgName")));
        psn.setNationality(insider.getNationality());
        psn.setRace(insider.getEthnic());
        psn.setBirthdate(df.format(insider.getBirthdate()));
        psn.setMobile(insider.getMobile());
        psn.setDutyCode(insider.getDuty());
        String maStatus = changeMaritalStatus(insider.getMaritalStatus());
        psn.setMaritalStatus(maStatus);
        psn.setRelType("1");
        psn.setIsBlack("0");
        psn.setDelflag("1");
        psn.setMgerOrg("1");
        return psn;
    }

    @Override
    public void dealShareHolder(Map map) {
        String date = getDate();
        //查出所有新增源股东数据
        List<Filter> filters = new Filter.Builder().eq("entityName", map.get("orgName")).like("createTime", date).build();
        List<Shareholder> shareholders = shareholderService.select(filters);
        iterationDeal(shareholders, "add", date);
        //查出所有要更新的源股东数据
        List<Filter> upFilters = new Filter.Builder().eq("entityName", map.get("orgName")).like("modifiedTime", date).build();
        List<Shareholder> upShareholders = shareholderService.select(upFilters);
        iterationDeal(upShareholders, "update", date);
    }

    private void iterationDeal(List<Shareholder> shareholders, String flag, String date) {
        String packageId = "db229626-78b9-11ea-a153-8cec4b488362";
        for (Shareholder holder : shareholders) {
            JSONObject reqMap = new JSONObject();
            reqMap.put("gdProportion", holder.getProportion());
            //调用规则引擎（股东采集规则）
            logger.info("调用股东规则----姓名：" + holder.getName());
            Map resMap = packagesService.execute(packageId, reqMap.toJSONString());
            Map ruleRetMap = (Map) resMap.get("resultMap");
            //非关联方
            if (ruleRetMap.get("retCode") == null) {
                logger.info("非关联方---姓名：" + holder.getName());
                //标记已完成同步股东信息
                holder.setVersion(0);
                shareholderService.update(holder);
                continue;
            }
            int retCode = (int) ruleRetMap.get("retCode");
            String type = holder.getType();
            if ("PSN".equals(type)) {
                //查验用户是否已存在关联表
                List<Filter> psnFilters = new Filter.Builder().eq("name", holder.getName())
                        .eq("identifyType", "IDCARD")
                        .eq("identifyNumber", holder.getIdCard()).build();
                List<RelPsn> psns = relPsnService.select(psnFilters);
                if (psns.size() > 0 && "update".equals(flag)) {
                    logger.info("新增股东用户[{}]已存在关联表", holder.getName());
                    //标记已完成同步股东信息
                    holder.setVersion(0);
                    shareholderService.update(holder);
                    continue;
                }
            } else {
                //查验机构是否已存在关联表
                List<Filter> orgFilters = new Filter.Builder().eq("orgName", holder.getName()).build();
                List<RelOrg> orgs = relOrgService.select(orgFilters);
                if (orgs.size() > 0 && "update".equals(flag)) {
                    logger.info("新增股东机构[{}]存在关联表", holder.getEntityName());
                    continue;
                }
            }

            //查验关联机构是否已存在关联表
            List<Filter> orgFilters = new Filter.Builder().eq("orgName", holder.getEntityName()).build();
            List<RelOrg> orgs = relOrgService.select(orgFilters);
            if (orgs.size() == 0) {
                logger.info("机构[{}]不存在关联表", holder.getEntityName());
                continue;
            }

            //保存入库
            insertDataGDInfo(holder, orgs.get(0), flag, retCode, type);
            //标记已完成同步股东信息
            holder.setVersion(holder.getVersion() == null ? 0 : holder.getVersion() + 1);
            shareholderService.update(holder);
            //查询是否有下级股东关联关系
            List<Filter> childFilters = new Filter.Builder().eq("entityName", holder.getEntityName()).like("createTime", date).build();
            List<Shareholder> childHolders = shareholderService.select(childFilters);
            iterationDeal(childHolders, "add", date);
            //查出所有要更新的源股东数据
            List<Filter> childUpFilters = new Filter.Builder().eq("entityName", holder.getEntityName()).like("modifiedTime", date).build();
            List<Shareholder> childUpShareholders = shareholderService.select(childUpFilters);
            iterationDeal(childUpShareholders, "update", date);
        }
    }

    private void insertDataGDInfo(Shareholder holder, RelOrg org, String flag, int retCode, String type) {

        if ("PSN".equals(type)) {
            RelPsn psn = changeToPsn(holder, flag);
            Rel rel = changeToRel(psn, org);
            if (retCode == 0) {
                //仅为股东
                logger.info("更新股东信息到关联表（仅股东）");
                psn.setRelType("0");
                rel.setRelType("0");
                //插入到自然人关联表
                if ("add".equals(flag)) {
                    relPsnService.insert(psn);
                    psn = relPsnService.get(psn);
                } else {
                    relPsnService.update(psn);
                }
                //插入关联关系表
                rel.setToPid(String.valueOf(psn.getPid()));
                if ("add".equals(flag)) {
                    relService.insert(rel);
                } else {
                    relService.update(rel);
                }
            }
            //即为股东又是关联方
            else if (retCode == 2) {
                logger.info("更新股东信息到关联表（关联方股东）");
                psn.setRelType("2");
                rel.setRelType("1");
                //插入到自然人关联表
                if ("add".equals(flag)) {
                    relPsnService.insert(psn);
                    psn = relPsnService.get(psn);
                } else {
                    relPsnService.update(psn);
                }
                //插入关联关系表
                rel.setToPid(String.valueOf(psn.getPid()));
                if ("add".equals(flag)) {
                    relService.insert(rel);
                } else {
                    relService.update(rel);
                }
            }
        } else {
            //更新股东机构信息
            RelOrg gdOrg = changeToGDOrg(holder, org, flag);
            Rel rel = changeToGDOrgRel(gdOrg, org);
            if (retCode == 0) {
                //仅为股东
                logger.info("更新股东信息到关联表（仅股东）");
                gdOrg.setRelType("0");
                rel.setRelType("0");
                //插入到自然人关联表
                if ("add".equals(flag)) {
                    relOrgService.insert(gdOrg);
                    gdOrg = relOrgService.get(gdOrg);
                } else {
                    relOrgService.update(gdOrg);
                }
                //插入关联关系表
                rel.setToPid(String.valueOf(gdOrg.getPid()));
                if ("add".equals(flag)) {
                    relService.insert(rel);
                } else {
                    relService.update(rel);
                }
            }
            //即为股东又是关联方
            else if (retCode == 2) {
                logger.info("更新股东信息到关联表（关联方股东）");
                gdOrg.setRelType("2");
                rel.setRelType("1");
                //插入到自然人关联表
                if ("add".equals(flag)) {
                    relOrgService.insert(gdOrg);
                    gdOrg = relOrgService.get(gdOrg);
                } else {
                    relOrgService.update(gdOrg);
                }
                //插入关联关系表
                rel.setToPid(String.valueOf(gdOrg.getPid()));
                if ("add".equals(flag)) {
                    relService.insert(rel);
                } else {
                    relService.update(rel);
                }
            }
        }

    }

    private Rel changeToGDOrgRel(RelOrg gdOrg, RelOrg org) {
        Rel rel = new Rel();
        //from
        rel.setFromPid(String.valueOf(org.getPid()));
        rel.setFromType("ORG");
        rel.setFromName("控股");
        rel.setFromMemo(org.getOrgName());
        //to
//        rel.setToPid(String.valueOf(psn.getPid()));
        rel.setToType("ORG");
        rel.setToName("股东");
        rel.setToMemo(gdOrg.getOrgName());
        rel.setRelCategory("C");
        rel.setRelSort("2");
        rel.setShareholdingRatio(Float.valueOf(gdOrg.getShareholdingProportion()));
        rel.setToPidShareholders("1");
        rel.setIsShow("1");
        return rel;
    }

    private RelOrg changeToGDOrg(Shareholder holder, RelOrg org, String flag) {
        RelOrg relOrg = new RelOrg();
        relOrg.setOrgName(holder.getName());
        relOrg.setIdentifyType("UNIFIEDSOCIALCREDITCODE");
        relOrg.setUniscCode(holder.getIdCard());
        if ("update".equals(flag)) {
            relOrg = relOrgService.get(relOrg);
        }
        relOrg.setOrignalOrg(org.getRegCode());
        relOrg.setOrignalOrgName(org.getOrgName());
        relOrg.setShareholdingProportion(String.valueOf(holder.getProportion()));
        relOrg.setValidStatus("1");
        relOrg.setRelType("1");
        relOrg.setIsBlack("0");
        relOrg.setDelflag("1");
        return relOrg;
    }

    private Rel changeToRel(RelPsn psn, RelOrg org) {
        Rel rel = new Rel();
        //from
        rel.setFromPid(String.valueOf(org.getPid()));
        rel.setFromType("ORG");
        rel.setFromName("控股");
        rel.setFromMemo(org.getOrgName());
        //to
//        rel.setToPid(String.valueOf(psn.getPid()));
        rel.setToType("PSN");
        rel.setToName("股东");
        rel.setToMemo(psn.getName());
        rel.setRelCategory("C");
        rel.setRelSort("2");
        rel.setShareholdingRatio(Float.valueOf(psn.getShareholdingProportion()));
        rel.setToPidShareholders("1");
        rel.setIsShow("1");

        return rel;
    }

    private RelPsn changeToPsn(Shareholder holder, String flag) {
        RelPsn psn = new RelPsn();
        psn.setName(holder.getName());
        psn.setIdentifyType("IDCARD");
        psn.setIdentifyNumber(holder.getIdCard());
        psn.setSex(holder.getGender() == 0 ? "man" : "woman");
        if ("update".equals(flag)) {
            psn = relPsnService.get(psn);
        }
        psn.setOrignalOrg(holder.getEntityId());
        psn.setOrignalOrgName(holder.getEntityName());
        psn.setInPrice(String.valueOf(holder.getContributionAmount()));
        psn.setShareholdingProportion(String.valueOf(holder.getProportion()));
        psn.setIsBlack("0");
        psn.setDelflag("1");
        psn.setMgerOrg("1");
        return psn;
    }
}
