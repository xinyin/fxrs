package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.batch.BatchInsertHistoryData;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.model.RelPsnHistory;
import com.yzj.jep.app.bus.service.RelPsnHistoryService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.vo.PsnInfoVo;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.util.IntegerUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.DictItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 内部交易管理导入实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = PsnImportCallbackImpl.BEANID)
public class PsnImportCallbackImpl implements XlsImportCallback {

    @Autowired
    DictItemService dictItemService;

    @Autowired
    private RelPsnService relPsnService;

    @Resource
    AppConfigService appConfigService;


    @Autowired
    private RelPsnHistoryService relPsnHistoryService;


    public static final String BEANID = "PsnImportCallbackImpl";

    /**
     * 配置导入的模板（XML）文件
     */
    private static final String XML_TEMP_FILE = "PsnImportMapper.xml";

    private static final String IMPORT_DATAS = "relPsns";


    @Override
    public String getImportXmlTemplate() {
        return XLSTEMPLATE_PATH + XML_TEMP_FILE;
    }

    @Override
    public void setXlsReaderBeans(Map beans) {
        //一般而言，有几个sheet页，就有几个元素
        beans.put(IMPORT_DATAS, new ArrayList());
    }

    /**
     * 文件内容导入数据库逻辑判断
     */
    @Override
    public XlsMessage executeSaveCallback(Map parameters, Map beans) throws ServiceException {
        XlsMessage res;
        //证件号码
        String number = "identifyNumber";
        //字典码
        String dictCode = "dictCode";
        String delflag = "delflag";
        List<PsnInfoVo> list = (List<PsnInfoVo>) beans.get(IMPORT_DATAS);
        //总数
        int sum = list.size();
        //for计数
        int num = 0;
        // 数据合格条数
        int count1 = 0;
        //新增的数量
        int count2 = 0;
        //更新的数量
        int count3 = 0;
        StringBuilder importErrorMsg = new StringBuilder();
        StringBuilder result = new StringBuilder();
        importErrorMsg.append("失败原因：");
        String verifyNumber = appConfigService.getConfigValue("WHETHER_VERIFY_IDNUMBER");
        try {
            if (CollUtil.isNotEmpty(list)) {
                //根据部门存储数据权限
                UserProfile profile = UserProfileUtils.getUserProfile();
                //获取字典信息
                Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
                List<RelPsnHistory> relPsnHistorieAddList = new ArrayList<>();
                List<RelPsnHistory> relPsnHistorieUpdateList = new ArrayList<>();
                for (PsnInfoVo entity : list) {
                    try {
                        num++;
                        boolean b = XssCheck.reflect(entity);
                        if (!b) {
                            throw new Exception("数据格式不规范, 请检查！");
                        }
                        if (entity.getName() == null) {
                            result.append("第").append(num + 1).append("条数据【姓名】为空！");
                            continue;
                        }
                        StringBuilder errorMsgDetail = new StringBuilder();
                        StringBuilder errorMsg = new StringBuilder();
                        errorMsg.append("第【").append(num + 1).append("】条数据，关联方名称为：【").append(entity.getName()).append("】的数据中");
                        if (entity.getIdentifyType() == null) {
                            errorMsgDetail.append("【证件类型】为空；");
                        }
                        if (entity.getIdentifyNumber() == null) {
                            errorMsgDetail.append("【证件号码】为空；");
                        } else {
                            if(!"N".equals(verifyNumber)){
                                String identifyType = dictCodeMap.get("idTypeMap").get(entity.getIdentifyType());
                                if("1".equals(identifyType)){
                                    boolean flag = idNumCHK(entity.getIdentifyNumber().trim());
                                    if (!flag) {
                                        errorMsgDetail.append("【证件号码格式】不正确；");
                                    }
                                }
                            }
                        }

                        if (entity.getName() == null) {
                            errorMsgDetail.append("【姓名】为空；");
                        }

                        if (entity.getDomesticOrForeign() == null) {
                            errorMsgDetail.append("【境内外标识】为空；");
                        }


                        if (entity.getEmail() != null) {
                            boolean flag = emailNumChk(entity.getEmail().trim());
                            if (flag) {
                                errorMsgDetail.append("【邮箱格式】不正确；");
                            }
                        }

                        if (entity.getBirthdate() != null) {
                            String msg = checkDate(entity.getBirthdate().trim());
                            if (StringUtils.isNotEmpty(msg)) {
                                errorMsgDetail.append("【出生日期格式】不正确；");
                            }

                        }


                        if(ObjectUtil.isNotEmpty(errorMsgDetail)){
                            result.append(errorMsg).append(errorMsgDetail);
                            continue;
                        }
                        if (entity.getIdentifyNumber() != null && entity.getIdentifyType() != null && entity.getDomesticOrForeign() != null) {
                            String identifyNumber = entity.getIdentifyNumber();
                            String identifyType = entity.getIdentifyType();
                            String name = entity.getName();
                            String domesticOrForeign = entity.getDomesticOrForeign();
                            entity.setIdentifyNumber(identifyNumber.trim());
                            entity.setIdentifyType(identifyType.trim());
                            entity.setName(name.trim());
                            entity.setDomesticOrForeign(domesticOrForeign.trim());
                        }
                        //checkPsnExist  检查自然人基本信息是否存在
                        boolean checkPsnExist = false;
                        List<RelPsn> psnList = relPsnService.select(builderFilter(number,
                                entity.getIdentifyNumber(), delflag, "1"));
                        if (CollUtil.isNotEmpty(psnList)) {
                            checkPsnExist = true;
                        }
                        //构建自然人的基本信息
                        RelPsn relPsn = builderPsnBasicInfo(entity, dictCodeMap, profile);
                        RelPsnHistory relPsnHistory = new RelPsnHistory();
                        BeanUtils.copyProperties(relPsn, relPsnHistory);
                        relPsnHistory.setOperator(profile.getStaffName());
                        relPsnHistory.setTransactionDep(profile.getDeptName());
                        relPsnHistory.setPid(null);
                        String maxBh = relPsnService.getMaxBh();
                        relPsn.setGlfbh(maxBh);
                        relPsn.setRelateNumber(maxBh);
                        int index = 0;
                        if (checkPsnExist) {
                            relPsn.setPid(psnList.get(0).getPid());
                            //如果状态不是已归档，则用原来数据库中的状态
                            if (!"6".equals(relPsn.getStatus())) {
                                relPsn.setStatus(psnList.get(0).getStatus());
                            }
                            index = relPsnService.updateSelective(relPsn);
                            if (index > 0) {
                                relPsn.setRecordNumber(psnList.get(0).getRecordNumber());
                                BatchInsertHistoryData.createPsnData(relPsnHistorieUpdateList, psnList.get(0), "2", relPsn.getPid());
                            }
                            count3++;
                        } else {
                            //插入表格中自然人数据

                            String recordNumberNew = "";
                            // 新增时需要查询当前是否存在相应版本号的数据
                            List<Filter> isExistNum = new Filter.Builder().nnl("recordNumber").eq("delflag", "1").build();
                            List<RelPsn> relPsns = relPsnService.select(isExistNum);

                            if(CollectionUtils.isNotEmpty(relPsns)){
                                recordNumberNew = relPsns.get(0).getRecordNumber();
                            }else {
                                // 查询当前最新版本号
                                List<Filter> filters = new Filter.Builder().nnl("recordNumber").build();
                                List<Order> orders = new Order.Builder().desc("createTime").build();
                                List<RelPsn> psnDayList = relPsnService.select(filters, orders);
                                boolean flag = CollectionUtils.isNotEmpty(psnDayList);
                                if(flag){
                                    recordNumberNew =  psnDayList.get(0).getRecordNumber();
                                }else {
                                    recordNumberNew = IntegerUtils.recordNumber(flag, null);
                                }
                            }
                            relPsn.setRecordNumber(recordNumberNew);
                            relPsn.setEffectRelation("0");
                            index = relPsnService.insertSelective(relPsn);
                            if (index > 0) {
                                RelPsn p = new RelPsn();
                                p.setIdentifyNumber(relPsn.getIdentifyNumber());
                                p.setIdentifyType(relPsn.getIdentifyType());
                                RelPsn psn = relPsnService.get(p);
                                BatchInsertHistoryData.createPsnData(relPsnHistorieAddList, relPsn, "1", psn.getPid());
                            }
                            count2++;
                        }

                    } catch (Exception e) {
                        if(StringUtils.isEmpty(e.getMessage())){
                            importErrorMsg.append("第").append(num+1).append("关联方导入失败关联方证件号码为：").append(entity.getIdentifyNumber()).append("导入失败").append(";");
                        }else {
                            importErrorMsg.append("第").append(num+1).append("关联方导入失败关联方证件号码为：").append(entity.getIdentifyNumber()).append("失败原因为：").append(e.getMessage()).append(";");
                        }
                        count1++;
                    }
                }
                // 导入成功的数据进行备份表备份
                BatchInsertHistoryData.insertPsnHistoryTable(relPsnHistorieAddList);
                BatchInsertHistoryData.insertPsnHistoryTable(relPsnHistorieUpdateList);

                result.append("数据总数量为：【").append(sum).append("】条，成功【")
                        .append(num).append("】条。");
                // 有错误数据
                if(count1 > 0){
                    result.append("失败【").append(count1).append("】条，").append(importErrorMsg);
                }
                // 有新增数据
                if(count2 > 0){
                    result.append("新增【").append(count2).append("】条");
                }
                // 有更新数据
                if(count3 > 0){
                    result.append("更新【").append(count3).append("】条");
                }
                res = new XlsMessage();
                res.setMessage(result.toString());
                return res;
            } else {
                res = new XlsMessage();
                res.setMessage("导入失败：");
                res.setException("导入数据为空或数据格式不正确，请检查数据！");
                return res;
            }
        } catch (Exception e) {
            res = new XlsMessage();
            res.setMessage("导入失败：");
            res.setException("失败原因：导入过程出现异常，请重试！");
            return res;
        }
    }

    /**
     * 构建查询的filter
     */
    public List<Filter> builderFilter(String property1, String value1, String property2, String value2) {
        if ("".equals(property2.trim())) {
            return new Filter.Builder().eq(property1, value1).build();
        }
        return new Filter.Builder().eq(property1, value1).eq(property2, value2).build();
    }

    /**
     * 构建自然人基本信息
     */
    public RelPsn builderPsnBasicInfo(PsnInfoVo entity, Map<String, Map<String, String>> dictCodeMap, UserProfile profile) {
        RelPsn relPsn = new RelPsn();
        //设置名称
        relPsn.setName(entity.getName());
        //证件类型
        relPsn.setIdentifyType(dictCodeMap.get("idTypeMap").get(entity.getIdentifyType()));
        //证件号码
        relPsn.setIdentifyNumber(entity.getIdentifyNumber());
        //境内外标识
        relPsn.setDomesticOrForeign(dictCodeMap.get("foreignMap").get(entity.getDomesticOrForeign()));
        //性别
        relPsn.setSex(dictCodeMap.get("sexMap").get(entity.getSex()));
        //生日
        relPsn.setBirthdate(entity.getBirthdate());
        //电话
        if (entity.getMobile() != null) {
            relPsn.setMobile(new BigDecimal(entity.getMobile()).toString());
        } else {
            relPsn.setMobile(entity.getMobile());
        }
        //邮箱
        relPsn.setEmail(entity.getEmail());
        relPsn.setResidentialAddress(entity.getResidentialAddress());
        //数据源默认设置下手工录入
        relPsn.setDataSource("2");
        relPsn.setStatusComment(entity.getStatusComment());
        //导入时默认都没加入黑名单-
        relPsn.setIsBlack("0");
        relPsn.setRegulatorOth1("xxx");
        relPsn.setDelflag("1");
        relPsn.setStatus("6");

        relPsn.setModifiedTime(new Date());
        //工作单位
        relPsn.setUnitAddress(entity.getUnitsAddress());
        //工作地址
        relPsn.setWorkUnits(entity.getWorkUnits());
        //质押比率
        relPsn.setPledgeRatio(entity.getPledgeRatio());
        relPsn.setMgerOrg(profile.getUnitCode());
        relPsn.setCreateById(profile.getStaffCode());
        relPsn.setCreateByName(profile.getStaffName());
        relPsn.setAmendId(profile.getStaffCode());
        relPsn.setAmendName(profile.getStaffName());
        relPsn.setOperator(profile.getStaffName());
        relPsn.setTransactionDep(profile.getDeptName());
        return relPsn;
    }

    /**
     * 获取字典信息
     */
    public Map<String, Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String, String>> dictCodeMap = new HashMap<>(16);
        //关联方证件类型
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_IDENTIFYTYPE.getCode(), "", ""));
        Map<String, String> idTypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("idTypeMap", idTypeMap);
        //境内外标识
        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_DOMESTICORFOREIGN.getCode(), "", ""));
        Map<String, String> foreignMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("foreignMap", foreignMap);
        //性别
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_SEX.getCode(), "", ""));
        Map<String, String> sexMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("sexMap", sexMap);
        //政治面貌
        List<DictItem> dictItems5 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_POLITICALLY.getCode(), "", ""));
        Map<String, String> politicallyMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("politicallyMap", politicallyMap);
        //婚姻状况
        List<DictItem> dictItems6 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_MARITALSTATUS.getCode(), "", ""));
        Map<String, String> maritalMap = dictItems6.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("maritalMap", maritalMap);
        return dictCodeMap;
    }

    /**
     * 获取文件内容，验证数据的有效性
     */
    @Override
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        //如果通过检查，就保持result为null，否则给出返回错误描述
        StringBuilder result = new StringBuilder();
        List<PsnInfoVo> list = (List<PsnInfoVo>) mapReaded.get(IMPORT_DATAS);
        //因为有一条空数据
        if (list != null && list.size() != 0) {
            //TODO 执行导入前的检查，如xxx编码是否存在，xxx引用的编码不存在等
            for (int i = 0; i < list.size(); i++) {

            }

        } else {
            result.append("导入文件模板不匹配或者数据为空;");
        }
        return result.toString();
    }

    @Override
    public Map setReturnBeanFromXls(Map beans, InputStream inputXls) throws ServiceException {
        return beans;
    }

    /**
     *  日期校验
     */
    public String checkDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return "";
        }


        String str = "[0-9]*";
        if (!date.matches(str)) {
            return "日期不合法，含有字符;";
        }

        Integer year = Integer.valueOf(date.substring(0, 4));
        Integer month = Integer.valueOf(date.substring(4, 6));
        Integer day = Integer.valueOf(date.substring(6, 8));
        if (year < 1970 || year > 2189 || month > 12 || month < 1 || day > 31 || day < 1) {
            return "日期年份、月份、天输入错误,不符合常规;";
        }
        return "";
    }


    /**
     * 验证邮箱格式
     */
    public boolean emailNumChk(String email) {
        String emailNum = email;
        if (!emailNum.equals("")) {
            String mailChk ="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
            if(!emailNum.matches(mailChk)){
                return false;
            }
        }
        return true;
    }

    /**
     * 验证证件号码格式
     */
    public boolean idNumCHK(String idNum) {
        if (idNum.length() == 18) {
            try {
                // 格式的正则
                String idCardPatter = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                        "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
                // 正则判断正确后进行更加详细的判断
                if (idNum.matches(idCardPatter)) {
                    char[] idArray = idNum.toCharArray();
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2}; //将前17位加权因子保存在数组里
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"}; //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                    int idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(idArray[i]));
                        int count = current * idCardWi[i];
                        idCardWiSum += count;
                    }
                    String idCardLast = String.valueOf(idArray[17]);
                    int idCardMod = idCardWiSum % 11;
                    if (idCardMod == 2) {
                        //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                        if (!idCardLast.equals("X") && !idCardLast.equals("x")) {
                            return false;
                        }
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("异常:" + idNum);
                return false;
            }
            return false;
        }
        return false;
    }

    }
