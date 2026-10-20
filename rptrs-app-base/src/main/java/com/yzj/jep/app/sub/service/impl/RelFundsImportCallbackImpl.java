package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.RelFunds;
import com.yzj.jep.app.bus.service.RelFundsService;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.sub.model.InsAllfunds;
import com.yzj.jep.app.sub.model.InsRelfunds;
import com.yzj.jep.app.sub.service.InsAllfundsService;
import com.yzj.jep.app.sub.service.InsRelfundsService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.service.DictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 单一法人资金运用类关联交易统计表导入实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = RelFundsImportCallbackImpl.BEANID)
public class RelFundsImportCallbackImpl implements XlsImportCallback {

    @Autowired
    DictItemService dictItemService;

   @Autowired
   private InsRelfundsService insRelfundsService;

    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private RelProService relProService;

    public static final String BEANID = "RelFundsImportCallbackImpl";

    /**
     * 配置导入的模板（XML）文件
     */
    private static final String XML_TEMP_FILE = "relFundsImportMapper.xml";

    private static final String IMPORT_DATAS = "relFunds";

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
        int num = 0;
        @SuppressWarnings("unchecked")
        List<InsRelfunds> list = (List<InsRelfunds>) beans.get(IMPORT_DATAS);
        if (list != null && list.size() > 0) {
            String falNum=null;
            //根据部门存储数据权限
            UserProfile profile = UserProfileUtils.getUserProfile();
            //获取字典信息
            int i=0;
            for (InsRelfunds entity : list) {
                StringBuilder stringBuilder=new StringBuilder();
                String s = checkEmpty(entity, stringBuilder);
                if (StringUtils.isNotEmpty(s))
                {
                    falNum += i + 1 + ",";
                }
                entity.setDelflag("1");
                entity.setCreateTime(new Date());
                entity.setTenantId(profile.getTenantId());
                entity.setStatus("3");
                insRelfundsService.insert(entity);
                num++;
            }
            res = new XlsMessage();
            String result = "";
            if (org.apache.commons.lang3.StringUtils.isNotBlank(falNum)) {
                result = ",第[" + falNum + "]条数据保存失败,";
            }
            res.setMessage("成功导入" + num + "条信息"+result);
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
     * 获取文件内容，验证数据的有效性
     */
    @Override
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        //如果通过检查，就保持result为null，否则给出返回错误描述
        StringBuilder result = new StringBuilder();
        List<InsRelfunds> list = (List<InsRelfunds>) mapReaded.get(IMPORT_DATAS);
        //因为有一条空数据
        if (list != null && list.size() != 0) {
            //TODO 执行导入前的检查，如xxx编码是否存在，xxx引用的编码不存在等
            for (InsRelfunds relFunds : list) {
                if (relFunds.getRelName()==null) {
                    break;
                }
                //字符串校验
                checkEmpty(relFunds, result);
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
     * @return
     * @desc 字段的非空校验
     */
    public String checkEmpty(InsRelfunds relfunds, StringBuilder result) {
       if (relfunds.getRelName()==null){
           result.append("关联方名称为空").append(";");
       }
        if (relfunds.getRelNumber()==null){
            result.append("关联方证件号码为空").append(";");
        }
        if (relfunds.getCorpInvestFund()==null){
            result.append("资金运用投资余额（万元)为空").append(";");
        }
        if (relfunds.getRelType()==null){
            result.append("关联方类别为空").append(";");
        }

        return result.toString();
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

}
