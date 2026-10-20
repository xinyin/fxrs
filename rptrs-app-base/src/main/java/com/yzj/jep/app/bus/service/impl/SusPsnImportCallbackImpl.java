package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.RelPsnSuspect;
import com.yzj.jep.app.bus.model.RelPsnSuspectVo;
import com.yzj.jep.app.bus.service.RelPsnSuspectService;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service(value = SusPsnImportCallbackImpl.BEANID)
public class SusPsnImportCallbackImpl implements XlsImportCallback {

    @Autowired
    DictItemService dictItemService;

    @Autowired
    private RelPsnSuspectService relPsnSuspectService;


    public static final String BEANID = "SusPsnImportCallbackImpl";

    /**
     * 配置导入的模板（XML）文件
     */
    private static final String XML_TEMP_FILE = "SusPsnImportMapper.xml";

    private static final String IMPORT_DATAS = "SusRelPsns";

    private static final String YES = "是";

    private static final String NO = "否";

    private static final String VALID = "有效";

    private static final String INVALID = "无效";

    private static final String IDCARD = "居民身份证";

    private static final String ELSECARD = "其他";

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
        XlsMessage res = null;
        List<Integer> listres = new ArrayList<>();
        int num = 0;
        int numUp = 0;
        int numIn = 0;
        int rens = 0;
        String msg = null;
        //证件号码
        String identifyNumber = "identifyNumber";
        //字典码
        String dictCode = "dictCode";
        //证件类型
        String identifyType = "identifyType";
        @SuppressWarnings("unchecked")
        List<RelPsnSuspectVo> list = (List<RelPsnSuspectVo>) beans.get(IMPORT_DATAS);
        int size = 0;
        size = list.size();
        if (list != null && list.size() > 0) {
            //根据部门存储数据权限
            UserProfile profile = UserProfileUtils.getUserProfile();
            //获取字典信息
            Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
            for (int i = 0; i < list.size(); i++) {
                //判断姓名是否为空
                if (StringUtils.isEmpty(list.get(i).getName())) {
                    rens = i + 1;
                    listres.add(rens);
                    continue;
                }
                //判断证件号码类型是否为空
                if (StringUtils.isEmpty(list.get(i).getIdentifyType())) {
                    rens = i + 1;
                    listres.add(rens);
                    continue;
                }
                //判断证件号是否为空
                if (StringUtils.isEmpty(list.get(i).getIdentifyNumber())) {
                    rens = i + 1;
                    listres.add(rens);
                    continue;
                } else if (list.get(i).getIdentifyNumber().length() < 5) {
                    rens = i + 1;
                    listres.add(rens);
                    continue;
                }
                //境内外标识为空
                if (StringUtils.isEmpty(list.get(i).getDomesticOrForeign())) {
                    rens = i + 1;
                    listres.add(rens);
                    continue;
                }

                if (list.get(i).getIdentifyNumber() == null) {
                    rens = i + 1;
                    listres.add(rens);
                    continue;
                }
                String email = list.get(i).getEmail();
                if (email != null) {
                    boolean emailChk = emailNumChk(email);
                    if (!emailChk) {
                        list.get(i).setEmail("");
                    }
                }
                String idTyp = list.get(i).getIdentifyType();
                String idnum = list.get(i).getIdentifyNumber();
                if (idTyp.equals(IDCARD)) {
                    boolean idChk = idNumCHK(idnum);
                    if (!idChk) {
                        rens = i + 1;
                        listres.add(rens);
                        continue;
                    }
                } else if (idTyp.equals(ELSECARD)) {
                    if (!(5 <= idnum.length() && idnum.length() <= 18)) {
                        rens = i + 1;
                        listres.add(rens);
                        continue;
                    }
                } else {
                    if (!(8 <= idnum.length() && idnum.length() <= 18)) {
                        continue;
                    }
                }
                //checkPsnExist  检查自然人基本信息是否存在
                boolean checkPsnExist = false;
                List<RelPsnSuspect> psnList = relPsnSuspectService.select(
                        builderFilter(identifyNumber, list.get(i).getIdentifyNumber(), identifyType, dictCodeMap.get("idTypeMap").get(list.get(i).getIdentifyType())));
                if (psnList.size() != 0) {
                    checkPsnExist = true;
                }
                num++;

                //构建自然人的基本信息
                RelPsnSuspect RelPsnSuspect = builderPsnBasicInfo(list.get(i), dictCodeMap, profile);
                if (checkPsnExist) {
                    RelPsnSuspect.setPid(psnList.get(0).getPid());
                    //如果状态不是已归档，则用原来数据库中的状态
                    if (!"6".equals(RelPsnSuspect.getStatus())) {
                        RelPsnSuspect.setStatus(psnList.get(0).getStatus());
                    }
                    //更新表格中的自然人数据
                    relPsnSuspectService.updateSelective(RelPsnSuspect);
                    numUp++;
                } else {
                    //插入表格中自然人数据
                    relPsnSuspectService.insertSelective(RelPsnSuspect);
                    numIn++;
                }
            }
            String replace = listres.toString().replace("[", "").replace("]", "");
            if (numUp == 0 && (size - num) == 0) {
                msg = "成功导入" + numIn + "条信息";
            } else if (numUp == 0) {
                msg = "成功导入" + numIn + "条信息，第" + replace + "条信息有误。";
            } else if ((size - num) == 0) {
                msg = "成功导入" + numIn + "条信息，" + "更新" + numUp + "条信息";
            } else {
                msg = "更新" + numUp + "条信息" + "，第" + replace + "条信息有误。";
            }
            res = new XlsMessage();
            res.setMessage(msg);
        } else {
            res = new XlsMessage();
            res.setMessage("导入失败：");
            res.setException("导入失败，请检查导入数据。");
            return res;
        }
        return res;
    }

    /**
     * @param property1
     * @param property2
     * @param value1
     * @param value2
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(String property1, String value1, String property2, String value2) {
        if ("".equals(property2.trim())) {
            List<Filter> filters = new Filter.Builder().eq(property1, value1).build();
            return filters;
        }
        List<Filter> filters = new Filter.Builder().eq(property1, value1).eq(property2, value2).build();
        return filters;
    }

    /**
     * @param entity
     * @param dictCodeMap
     * @return
     * @desc 构建自然人基本信息
     */
    public RelPsnSuspect builderPsnBasicInfo(RelPsnSuspectVo entity, Map<String, Map<String, String>> dictCodeMap, UserProfile profile) {
        RelPsnSuspect RelPsnSuspect = new RelPsnSuspect();
        //姓名
        RelPsnSuspect.setName(entity.getName());
        //证件类型
        RelPsnSuspect.setIdentifyType(dictCodeMap.get("idTypeMap").get(entity.getIdentifyType()));
        //证件号码
        //BigDecimal identifyNum = new BigDecimal(entity.getIdentifyNumber());
        RelPsnSuspect.setIdentifyNumber(entity.getIdentifyNumber());
        //境内外标识
        RelPsnSuspect.setDomesticOrForeign(dictCodeMap.get("foreignMap").get(entity.getDomesticOrForeign()));
        //性别标识
        RelPsnSuspect.setSex(dictCodeMap.get("sexMap").get(entity.getSex()));
        //政治面貌
        //RelPsnSuspect.setPoliticsStatus(dictCodeMap.get("politicallyMap").get(entity.getPoliticsStatus()));
        //生日
        RelPsnSuspect.setBirthdate(entity.getBirthdate());
        //电话号码
        RelPsnSuspect.setMobile(entity.getMobile());
        //结婚状况
        //RelPsnSuspect.setMaritalStatus(dictCodeMap.get("maritalMap").get(entity.getMaritalStatus()));
        //电子邮件
        RelPsnSuspect.setEmail(entity.getEmail());
       //居住地址
        //RelPsnSuspect.setResidentialAddress(entity.getResidentialAddress());
        //设置监管机构Ybj
        //RelPsnSuspect.setRegulatorYbj(entity.getRegulatorYbj());
        //审批状态说明
        //RelPsnSuspect.setStatusComment(entity.getStatusComment());
        //导入时默认都没加入黑名单-
        RelPsnSuspect.setIsBlack("0");
        RelPsnSuspect.setRegulatorOth1("xxx");
        RelPsnSuspect.setDataSource("2");
        //删除标识号
        RelPsnSuspect.setDelflag("1");
        //审批状态    未提交/已提交/未审批
        RelPsnSuspect.setStatus("3");
        RelPsnSuspect.setIsAssoParty("0");
        RelPsnSuspect.setModifiedTime(new Date());
        //单位地址
        RelPsnSuspect.setUnitAddress(entity.getUnitsAddress());
        //工作单位
        RelPsnSuspect.setWorkUnits(entity.getWorkUnits());
        //股权比例
        //RelPsnSuspect.setShareholdingProportion(entity.getShareholdingRatio());
       //质押比例
        //RelPsnSuspect.setPledgeRatio(entity.getPledgeRatio());
        //管户机构(默认是总行)
        RelPsnSuspect.setMgerOrg(profile.getUnitCode());
        //创建日期
        RelPsnSuspect.setCreateById(profile.getStaffCode());
        //创建人
        RelPsnSuspect.setCreateByName(profile.getStaffName());

        return RelPsnSuspect;
    }

    /**
     * @param dictCode
     * @return
     * @desc 获取字典信息
     */
    public Map<String, Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String, String>> dictCodeMap = new HashMap<>(16);
        //关联方证件类型
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_IDENTIFYTYPE.getCode(), "", ""));
        Map idTypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("idTypeMap", idTypeMap);
        //境内外标识
        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_DOMESTICORFOREIGN.getCode(), "", ""));
        Map foreignMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("foreignMap", foreignMap);
        //性别
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_SEX.getCode(), "", ""));
        Map sexMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("sexMap", sexMap);
        //政治面貌
        List<DictItem> dictItems5 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_POLITICALLY.getCode(), "", ""));
        Map politicallyMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("politicallyMap", politicallyMap);
        //婚姻状况
        List<DictItem> dictItems6 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_MARITALSTATUS.getCode(), "", ""));
        Map maritalMap = dictItems6.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
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
        List<RelPsnSuspectVo> list = (List<RelPsnSuspectVo>) mapReaded.get(IMPORT_DATAS);
        int size = 0;
        size = list.size();
        //因为有一条空数据
        if (list != null && list.size() != 0) {
            //TODO 执行导入前的检查，如xxx编码是否存在，xxx引用的编码不存在等
            for (RelPsnSuspectVo relPsnSuspectVo : list) {
                if (StringUtils.isEmpty(relPsnSuspectVo.getNo())) {
                    break;
                }
                //字符串校验
                checkEmpty(relPsnSuspectVo, result);
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
     * @param RelPsnSuspectVo
     * @return
     * @desc 字段的非空校验
     */
    public String checkEmpty(RelPsnSuspectVo RelPsnSuspectVo, StringBuilder result) {

//        if (RelPsnSuspectVo.getIdentifyNumber().length() < 8) {
//            result.append("证件号码不合法").append(";");
//        }
        if (StringUtils.isEmpty(RelPsnSuspectVo.getName())) {
            result.append("姓名为空").append(";");
        }
        if (StringUtils.isEmpty(RelPsnSuspectVo.getIdentifyType())) {
            result.append("证件类型为空").append(";");
        }
        if (StringUtils.isEmpty(RelPsnSuspectVo.getIdentifyNumber())) {
            result.append("证件号码为空").append(";");
        } else if (RelPsnSuspectVo.getIdentifyNumber().length() < 8) {
            result.append("证件号码不合法").append(";");
        }
        if (StringUtils.isEmpty(RelPsnSuspectVo.getDomesticOrForeign())) {
            result.append("境内外标识为空").append(";");
        }
        return result.toString();
    }

    public String checkBasicInfo(RelPsnSuspectVo RelPsnSuspectVo) {
        String res = "";
        if (StringUtils.isEmpty(RelPsnSuspectVo.getNo())) {
            return res;
        }
        if (StringUtils.isEmpty(RelPsnSuspectVo.getName())) {
            res += "姓名为空";
        }
        if (StringUtils.isEmpty(RelPsnSuspectVo.getIdentifyNumber())) {
            res += "证件号码为空";
        }
        if (StringUtils.isEmpty(RelPsnSuspectVo.getIdentifyType())) {
            res += "证件类型为空";
        }
        if (StringUtils.isEmpty(RelPsnSuspectVo.getDomesticOrForeign())) {
            res += "境内/外为空";
        }
        if (StringUtils.isEmpty(RelPsnSuspectVo.getResidentialAddress())) {
            res += "居住地址为空";
        }
        return res;
    }

    /**
     * 将判定文字转为数字字典
     *
     * @param str
     * @return
     */
    private String isNo(String str) {
        if (!StringUtils.isEmpty(str)) {
            if (str.contains(YES)) {
                return "1";
            }

            if (NO.equals(str)) {
                return "0";
            }

            if (VALID.equals(str)) {
                return "1";
            }

            if (INVALID.equals(str)) {
                return "0";
            }
        }
        return "";
    }

    /**
     * @param date
     * @return
     * @desc 日期校验
     */
    public String checkDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return "";
        }

        if (date.length() != 8) {
            return "日期不合法,日期格式应改为为这种形式：20200101;";
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


    //验证邮箱格式
    public boolean emailNumChk(String email) {
        String emailNum = email;
        if (!emailNum.equals("")) {
            String mailChk = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
            if (!emailNum.matches(mailChk)) {
                return false;
            }
        }
        return true;
    }

    //验证证件号码格式
    public static boolean idNumCHK(String idNum) {
        String identifyNumber = idNum;
        if (identifyNumber.length() == 18) {
            try {
                // 格式的正则
                String idCardPatter = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                        "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
                // 正则判断正确后进行更加详细的判断
                if (identifyNumber.matches(idCardPatter)) {
                    char[] idArray = identifyNumber.toCharArray();
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
                        if (!idCardLast.equals("X")) {
                            return false;
                        }
                    }
                    //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                    if (!idCardLast.equals(idCardY[idCardMod])) {
                        return false;
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("异常:" + identifyNumber);
                return false;
            }
            return false;
        }
        return false;
    }
}
