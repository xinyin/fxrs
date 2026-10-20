package com.yzj.jep.app.sub.util;

import com.google.common.collect.Lists;
import com.yzj.jep.app.sub.model.Template;
import com.yzj.jep.app.sub.model.TemplateDetails;
import com.yzj.jep.app.sub.service.TemplateDetailsService;
import com.yzj.jep.app.sub.service.TemplateService;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.PO;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.service.CRUDService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class BatchUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchUtils.class);
    private static TemplateService templateService = SpringContextHolder.getBean(TemplateService.class);
    private static TemplateDetailsService templateDetailsService = SpringContextHolder.getBean(TemplateDetailsService.class);

    public static <T extends PO> void batchCheckData() throws Exception {
        LOGGER.info("批量字段校验开始...");
        int pageSize = 3;
        //匹配校验过滤器指定in的属性
        String fiterInStringProperty = "status";
        //校验姓名与身份证匹配等查询in条件 status in (2,3,4)
        List<String> filterInStringPropertyList = asList("2","3","4");
        List<Template> templates = templateService.selectAll();
        for (Template template : templates) {

            Integer pid = template.getPid();
            String service = template.getService();
            CRUDService crudService = SpringContextHolder.getBean(service);
            LOGGER.info("模板id:[{}]", pid);
            Map<String, Object> checkItem = templateDetailsService.getcheckItem(pid);
            List<String> lengthColNameList = (List<String>) (Object) checkItem.get("lengthColNameList");
            Map<String, Integer> lengthMap = (Map<String, Integer>) checkItem.get("lengthMap");
            List<String> mustColNameList = (List<String>) (Object) checkItem.get("mustColNameList");
            List<Map<String, String>> conMustList = (List<Map<String, String>>) (Object) checkItem.get("conMustList");
            //获取匹配校验的模板数据
            List<Map<String,String>> checkDataColNameList = (List<Map<String, String>>) (Object)checkItem.get("checkDataColNameList");
            List<Filter> filters = Lists.newArrayList();
            filters.add(Filter.eq("status", "0"));
            long count = crudService.count(filters);
            LOGGER.info("待校验共[{}]条", count);
            Page<T> page = new Page<>();
            long time = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            for (int i = 0; i < time; i++) {
                page.setPageNumber(1);
                page.setPageSize(pageSize);
                Page query = crudService.query(page, filters);
                List<T> queryResult = query.getResult();
                for (T t : queryResult) {
                    boolean check = false;
                    StringBuilder sb = new StringBuilder();
                    for (String colName : lengthColNameList) {
                        Integer length = lengthMap.get(colName);
                        Method method = t.getClass().getMethod("get" + colName.substring(0, 1).toUpperCase() + colName.substring(1));
                        String result = method.invoke(t).toString();
                        if (StringUtils.isNotBlank(result) && result.length() > length) {
                            LOGGER.info(colName + ":[{}]，最大长度[{}]", result, length);
                            sb.append(colName + "超出长度,");
                            if (!check) {
                                check = true;
                            }
                        }
                    }
                    for (String colName : mustColNameList) {
                        Method method = t.getClass().getMethod("get" + colName.substring(0, 1).toUpperCase() + colName.substring(1));
                        Object result = method.invoke(t);
                        if (result instanceof String) {
                            if (StringUtils.isBlank((String) result)) {
                                LOGGER.info(colName + "为空");
                                sb.append(colName + "为空,");
                                if (!check) {
                                    check = true;
                                }
                            }
                        } else {
                            if (result == null) {
                                LOGGER.info(colName + "为空");
                                sb.append(colName + "为空,");
                                if (!check) {
                                    check = true;
                                }
                            }
                        }
                    }
                    for (Map<String, String> map : conMustList) {
                        String colName = map.get("colName");
                        String conditionCol = map.get("conditionCol");
                        String conditionValue = map.get("conditionValue");
                        String[] conditionCols = conditionCol.split(",");
                        String[] conditionValues = conditionValue.split(",");
                        boolean isEmpty = true;
                        for (int j = 0; j < conditionCols.length; j++) {
                            if (!isEmpty) {
                                break;
                            }
                            Method method = t.getClass().getMethod("get" + conditionCols[j].substring(0, 1).toUpperCase() + conditionCols[j].substring(1));
                            String result = method.invoke(t).toString();
                            String[] condition = conditionValues[j].split("\\|");
                            if (condition.length <= 1) {
                                if (!result.equals(condition[0])) {
                                    isEmpty = false;
                                }
                            } else {
                                int diff = 0;
                                for (int k = 0; k < condition.length; k++) {
                                    if (!result.equals(condition[k])) {
                                        diff++;
                                    }
                                    if (diff == condition.length) {
                                        isEmpty = false;
                                    }
                                }
                            }

                        }
                        if (isEmpty) {
                            Method method = t.getClass().getMethod("get" + colName.substring(0, 1).toUpperCase() + colName.substring(1));
                            Object invoke = method.invoke(t);
                            if (invoke instanceof String) {
                                if (StringUtils.isBlank(invoke.toString())) {
                                    LOGGER.info(colName + "为空");
                                    sb.append(colName + "为空,");
                                    if (!check) {
                                        check = true;
                                    }
                                }
                            } else {
                                if (invoke == null) {
                                    LOGGER.info(colName + "为空");
                                    sb.append(colName + "为空,");
                                    if (!check) {
                                        check = true;
                                    }
                                }
                            }
                        }
                    }
                    //进行匹配校验，姓名与身份证或名称与社会统一信用代码匹配
                    for (Map<String,String> colNameMap :checkDataColNameList){
                        check= BatchUtils.checkData(t,crudService,colNameMap.get("col_name"),colNameMap.get("match_name"),check,fiterInStringProperty,filterInStringPropertyList,sb);
                    }

                    //数据校验
                    Method setStatus = t.getClass().getMethod("setStatus", String.class);
                    Method setStatusComment = t.getClass().getMethod("setStatusComment", String.class);
                    //数据校验结果
                    Method setDataCheckResult = t.getClass().getMethod("setDataCheckResult", String.class);
                    //数据检验详情
                    Method setDataCheckDetails = t.getClass().getMethod("setDataCheckDetails", String.class);
                    //下载报送情况
                    Method setIsReported = t.getClass().getMethod("setIsReported", String.class);
                    //生成报送文件状态
                    Method setIsSubDocument = t.getClass().getMethod("setIsSubDocument", String.class);
                    if (check) {
                        setStatus.invoke(t, "1");
                        setStatusComment.invoke(t, sb.toString());
                        setDataCheckResult.invoke(t,"2");
                        setDataCheckDetails.invoke(t,sb.toString());
                    } else {
                        setStatus.invoke(t, "3");
                        setStatusComment.invoke(t, "校验已通过");
                        setDataCheckResult.invoke(t,"1");
                        setDataCheckDetails.invoke(t,"校验已通过");
                    }
                    setIsSubDocument.invoke(t,"0");
                    setIsReported.invoke(t,"0");
                    crudService.updateSelective(t);
                }
            }
        }
    }

    /**
     * 数据匹配校验
     * 匹配姓名和身份证号
     * 法人名称和社会统一信用编码
     * 通用方法
     *
     * @param t 校验的泛型对象 eg：Person
     * @param crudService 校验需要的Sevice eg:personService
     * @param checkProperty 校验属性 eg:identityNo
     * @param judgmentProperty 判断条件指定属性 eg:name
     * @param check 校验结果 eg:false
     * @param fiterInStringProperty 过滤器指定In的属性 eg:status
     * @param filterInStringPropertyList 过滤器In的属性List eg:{"2","3","4"}
     * @param sb 校验详情
     */
    public static  <T extends PO> Boolean checkData(T t, CRUDService crudService
            ,String checkProperty,String judgmentProperty
            ,Boolean check, String fiterInStringProperty
            ,List<String> filterInStringPropertyList,StringBuilder sb) throws Exception{

        //获取待校验的数据
        //获取待校验数据的判断条件
        //拼接get方法名字
        String judgmentPropertyName = "get" + judgmentProperty.substring(0, 1).toUpperCase() + judgmentProperty.substring(1);
        //获得get方法
        Method judgmentPropertyMethod = t.getClass().getMethod(judgmentPropertyName);
        //获得get方法的数据
        String judgmentPropertyResult = judgmentPropertyMethod.invoke(t).toString();
        //获取待校验数据的校验属性
        String checkPropertyName = "get" + checkProperty.substring(0, 1).toUpperCase() + checkProperty.substring(1);
        Method checkPropertyMethod = t.getClass().getMethod(checkPropertyName);
        String checkPropertyResult = checkPropertyMethod.invoke(t).toString();

        //获取数据库的数据
        List<Filter> filters = Lists.newArrayList();
        filters.add(Filter.eq(checkProperty, checkPropertyResult));
        filters.add(Filter.in(fiterInStringProperty, filterInStringPropertyList));
        String [] returnProperties = {judgmentProperty,checkProperty};
        List<T> checkDate = crudService.select(returnProperties,filters);

        //校验属性与判断条件是否匹配
        //系统中存在相同数据，需校验
        if (!checkDate.isEmpty()){
            int num = 0;
            for (T t1 :checkDate ){
                if (!judgmentPropertyMethod.invoke(t1).toString().isEmpty() && !judgmentPropertyMethod.invoke(t1).toString().equals(judgmentPropertyResult)){
                    num++;
                    LOGGER.info("校验不通过.系统存在数据:"+ judgmentProperty+"["+  num + "]:"+judgmentPropertyMethod.invoke(t1).toString()+checkProperty+"["+  num + "]:"+checkPropertyMethod.invoke(t1).toString()+"\n");
                    sb.append("校验不通过.系统存在数据:"+ judgmentProperty+"["+  num + "]:"+judgmentPropertyMethod.invoke(t1).toString()+checkProperty+"["+  num + "]:"+checkPropertyMethod.invoke(t1).toString()+"\n");
                    if (!check) {
                        check = true;
                    }
                }
            }
        }
        return check;
    }

    public static void batchCreateFile() throws Exception {
        LOGGER.info("批量生成文件开始...");
        List<Template> templates = templateService.selectAll();
		List<String> list = new ArrayList<>();
        for (Template template : templates) {
            String fileName = template.getTemplateName() + DateUtils.format(new Date(), "yyyyMMdd");
            List<Filter> filters = Lists.newArrayList();
            templateDetailsService.createSubmitFile(template.getPid(), filters,fileName, template.getSubType());
        }
    }
}
