package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.bus.vo.PronImportVo;
import com.yzj.jep.app.ins.constants.DicItemType;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.util.DicItemUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 内部交易管理导入实现
 *
 * @author qiran
 * @time 2020年9月24日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = ProImportCallbackImpl.BEANID)
public class ProImportCallbackImpl implements XlsImportCallback {

    @Resource
    DictItemService dictItemService;

    @Resource
    private RelProService relProService;


    public static final String BEANID = "ProImportCallbackImpl";

    /**
     * 配置导入的模板（XML）文件
     */
    private static final String XML_TEMP_FILE = "ProImportMapper.xml";
    private static final String IMPORT_DATAS = "relPros";

    @Override
    public String getImportXmlTemplate() {
        return XLSTEMPLATE_PATH + XML_TEMP_FILE;
    }

    @Override
    public void setXlsReaderBeans(Map beans) {
        //一般而言，有几个sheet页，就有几个元素
        beans.put(IMPORT_DATAS, new ArrayList<>());
    }

    /**
     * 文件内容导入数据库逻辑判断
     */
    @Override
    public XlsMessage executeSaveCallback(Map parameters, Map beans) throws ServiceException {
        XlsMessage res;
        int num = 0;
        int add = 0;
        int update = 0;
        List<PronImportVo> list = (List<PronImportVo>) beans.get(IMPORT_DATAS);
        if (CollUtil.isNotEmpty(list)) {
            //根据部门存储数据权限
            UserProfile profile = UserProfileUtils.getUserProfile();
            assert profile != null;
            String unitCode = profile.getUnitCode();
            for (PronImportVo entity : list) {
                if (entity.getProductIssueNo() == null) {
                    continue;
                }
                boolean b = XssCheck.reflect(entity);
                if (!b) {
                    return null;
                }
                List<Filter> filters;
                num++;
                //检查产品是否存在
                RelPro relPro;
                filters = new Filter.Builder().eq("proProvider", entity.getProductIssueNo())
                        .eq("mgerOrg", unitCode).build();
                List<RelPro> relPros = relProService.select(filters);
                Map<String, List<DictItem>> dicMap = getDicValue();
                String relType = DicItemUtils.getItemValue(dicMap, DicItemType.PRODUCT_TYPE, entity.getProductType());
                entity.setProductType(relType);
                //构建产品的基本信息，不存在则新增，存在则更新
                if (StringUtils.isNotEmpty(entity.getProductName())
                        && StringUtils.isNotEmpty(entity.getIdentifyNumber())
                        && StringUtils.isNotEmpty(entity.getProductType())
                        && StringUtils.isNotEmpty(entity.getProductOwner())
                        && StringUtils.isNotEmpty(entity.getProductIssueNo())
                        && StringUtils.isNotEmpty(entity.getProductCustodian())
                        && StringUtils.isNotEmpty(entity.getRegisterOrg())
                        && StringUtils.isNotEmpty(entity.getProductOwnerUniscCode())
                        && StringUtils.isNotEmpty(entity.getProductCustodianUniscCode())) {
                    entity.setProductName(entity.getProductName().trim());
                    entity.setProductOwner(entity.getProductOwner().trim());
                    entity.setProductIssueNo(entity.getProductIssueNo().trim());
                    entity.setProductCustodian(entity.getProductCustodian().trim());
                    entity.setProductOwnerUniscCode(entity.getProductOwnerUniscCode().trim());
                    entity.setProductCustodianUniscCode(entity.getProductCustodianUniscCode().trim());
                    entity.setRegisterOrg(entity.getRegisterOrg().trim());
                    entity.setIdentifyNumber(entity.getIdentifyNumber().trim());
                }
                relPro = builderProBasicInfo(entity, unitCode, profile);
                if (CollUtil.isEmpty(relPros)) {
                    relProService.insertSelective(relPro);
                    add ++;
                } else {
                    relPro.setPid(relPros.get(0).getPid());
                    relProService.updateSelective(relPro);
                    update ++;
                }
            }
            res = new XlsMessage();
            if(update > 0){
                res.setMessage("成功导入" + num + "条信息;新增"+add+"条；更新"+update+"条。");
                return res;
            }
            res.setMessage("成功导入" + num + "条。");

        } else {
            res = new XlsMessage();
            res.setMessage("导入失败：");
            res.setException("导入失败，请检查导入数据。");
        }
        return res;
    }

    /**
     * 构建产品的基本信息
     */
    private RelPro builderProBasicInfo(PronImportVo entity, String mgerOrg, UserProfile profile) {
        RelPro relPro = new RelPro();
        relPro.setProName(entity.getProductName());
        relPro.setProProvider(entity.getProductIssueNo());
        relPro.setProCode(entity.getProductRegisterNo());
        relPro.setProRegOrg(entity.getRegisterOrg());
        relPro.setAdName(entity.getProductOwner());
        relPro.setAdCreditCode(entity.getProductOwnerUniscCode());
        relPro.setCareName(entity.getProductCustodian());
        relPro.setCareCreditCode(entity.getProductCustodianUniscCode());
        relPro.setAssetDesc(entity.getAssetDesc());
        relPro.setInvestors(entity.getInvestors());
        relPro.setProType(entity.getProductType());
        // 逻辑删除标记
        relPro.setDelflag("1");
        //1:待补录 6：已归档\
        relPro.setApprovalStatus("6");
        //数据来源为手工录入
        relPro.setDataSource("1");
        relPro.setMgerOrg(mgerOrg);
        relPro.setAmendId(profile.getStaffCode());
        relPro.setAmendName(profile.getStaffName());
        relPro.setCreateById(profile.getStaffCode());
        relPro.setCreateByName(profile.getStaffName());
        return relPro;

    }


    /**
     * 构建查询的filter
     */
    public List<Filter> builderFilter(String property, String value) {
        return new Filter.Builder().eq(property, value).build();
    }

    /**
     * 获取文件内容，验证数据的有效性
     */
    @Override
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        //如果通过检查，就保持result为null，否则给出返回错误描述
        String result = null;
        List<PronImportVo> list = (List<PronImportVo>) mapReaded.get(IMPORT_DATAS);
        if (list != null && list.size() - 1 > 0) {
            for (int i = 0; i < list.size() - 1; i++) {
                PronImportVo pronImportVo = list.get(i);
                Map<String, List<DictItem>> dicMap = getDicValue();
                //检查基本信息
                String resBasic = checkBasicInfo(pronImportVo, dicMap);
                if (!"".equals(resBasic)) {
                    result = "检测到第" + (i + 1) + "条数据问题含有：" + resBasic;
                    return result;
                }
            }
        } else {
            result = "导入数据为空。";
        }
        return result;
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


    @Override
    public Map setReturnBeanFromXls(Map beans, InputStream inputXls) throws ServiceException {
        //对导入的数据做特殊处理，如不处理则直接返回beans
        return beans;
    }


    /**
     * 数据校验
     */
    public String checkBasicInfo(PronImportVo pronImportVo, Map<String, List<DictItem>> dicMap) {
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isEmpty(pronImportVo.getProductName())) {
            sb.append("产品名称不能为空；");
        }

        if (StringUtils.isEmpty(pronImportVo.getProductType())) {
            sb.append("产品分类不能为空；");
        } else {
            String relType = DicItemUtils.getItemValue(dicMap, DicItemType.PRODUCT_TYPE, pronImportVo.getProductType());
            if (StringUtils.isEmpty(relType)) {
                sb.append("产品分类格式不正确；");
            }
        }

        if (StringUtils.isEmpty(pronImportVo.getProductIssueNo())) {
            sb.append("产品批准/注册/备案号不能为空；");
        }
        if (StringUtils.isEmpty(pronImportVo.getProductOwner())) {
            sb.append("产品管理人法定名称不能为空；");
        }
        if (StringUtils.isEmpty(pronImportVo.getProductOwnerUniscCode())) {
            sb.append("产品管理人证件号码不能为空；");
        }
        if (StringUtils.isEmpty(pronImportVo.getAssetDesc())) {
            sb.append("底层资产不能为空；");
        }

        return sb.toString();
    }


    /**
     * 查询字段集合
     */
    public Map<String, List<DictItem>> getDicValue() {

        Map<String, List<DictItem>> dicItemMap = new HashMap();
        List<DictItem> dictItemList = dictItemService.selectAll();
        if (CollectionUtil.isNotEmpty(dictItemList)) {
            dicItemMap = dictItemList.stream().collect(Collectors.groupingBy(DictItem::getDictCode));
        }

        return dicItemMap;
    }
}
