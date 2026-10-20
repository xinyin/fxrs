package com.yzj.jep.app.pub.service.impl;

import com.yzj.jep.app.pub.model.PubQuarterVo;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LONG
 * @date 2020/7/20
 * @description
 */
@Service(value = PubMenageQuarterOutCallbackImpl.BEANID)
class PubMenageQuarterOutCallbackImpl implements XlsExportCallback {

    private static Logger logger = LoggerFactory.getLogger(PubMenageQuarterOutCallbackImpl.class);

    public static final String BEANID = "PubMenageQuarterOutCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "PubMenageQuarter.xls";

    @Autowired
    private PublogService publogService;


    @Autowired
    private DictItemService dictItemService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        logger.info("设置生成披露表格文件名");
        //设置文件名
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "PubMenageQuarte_" + sf.format(new Date()) + ".xls";
        return fileName;
    }

    @Override
    public Map<Integer, List<int[]>> getMergeColumns() {
        return null;
    }

    @Override
    public Map<Integer, Integer> getMergeStartRowNum() {
        return null;
    }

    /*
     * 编写业务代码
     * */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        logger.info("业务逻辑开始。。。。。");
        Map resultMap = new HashMap();
        List<PubQuarterVo> listPub = new ArrayList<PubQuarterVo>();
        //获取参数
        String data = (String) parameters.get("data");
        String[] param = data.split(",");
        String pubYear = param[0];
        String pubQuarter = param[1];
        String pubQuarterge = null;
        String pubQuarterle = null;
        if (StringUtils.isNotBlank(pubYear) && StringUtils.isNotBlank(pubQuarter)) {
           /* switch (pubQuarter){
                case "1":
                    pubQuarterge = DateUtils.parseDate(pubYear + "-01-01");
                    pubQuarterle = DateUtils.parseDate(pubYear + "-03-31");
                    break;
                case "2":
                    pubQuarterge = DateUtils.parseDate(pubYear + "-04-01");
                    pubQuarterle = DateUtils.parseDate(pubYear + "-06-30");
                    break;
                case "3":
                    pubQuarterge = DateUtils.parseDate(pubYear + "-07-01");
                    pubQuarterle = DateUtils.parseDate(pubYear + "-09-30");
                    break;
                case "4":
                    pubQuarterge = DateUtils.parseDate(pubYear + "-10-01");
                    pubQuarterle = DateUtils.parseDate(pubYear + "-12-31");
                    break;
                default:
            }*/

            switch (pubQuarter) {
                case "1":
                    pubQuarterge = pubYear + "-01-01";
                    pubQuarterle = pubYear + "-03-31";
                    break;
                case "2":
                    pubQuarterge = pubYear + "-04-01";
                    pubQuarterle = pubYear + "-06-30";
                    break;
                case "3":
                    pubQuarterge = pubYear + "-07-01";
                    pubQuarterle = pubYear + "-07-16";
                    break;
                case "4":
                    pubQuarterge = pubYear + "-10-01";
                    pubQuarterle = pubYear + "-12-31";
                    break;
                default:
            }
            logger.info("根据年度和季度查询所匹配的数据开始。。。。");

            //根据年度和季度查询所匹配的数据
            List<PubQuarterVo> listQuarter = publogService.queryQuarterDate(pubQuarterge, pubQuarterle);

            if (listQuarter.size() > 0 && listQuarter != null) {
                //通过lists集合获取交易类型 查询交易类型下的本季度所有金额
                for (int i = 0; i < listQuarter.size(); i++) {
                    List<Filter> filters = new ArrayList<>();
                    PubQuarterVo PubQuarterVo = new PubQuarterVo();
                    PubQuarterVo.setNo(String.valueOf(i + 1));
                    PubQuarterVo.setRelName(listQuarter.get(i).getRelName());
                    //通过查询字典 将编码值跟文字进行对应
                    filters.add(new Filter("dictCode", "INS-tranType"));
                    filters.add(new Filter("itemValue", listQuarter.get(i).getTranType()));
                    DictItem dictItem = dictItemService.get(filters);
                    if (dictItem != null) {
                        PubQuarterVo.setTranType(dictItem.getItemLabel());
                    }
                    //将相同的交易类型合并单元格

                    PubQuarterVo.setTradeSubject(listQuarter.get(i).getTradeSubject());
                    //  PubQuarterVo.setPricingPolicy("");
                    if (listQuarter.get(i).getTradeAmount() == null) {
                        PubQuarterVo.setTradeAmount(String.valueOf(BigDecimal.ZERO));
                    } else {
                        PubQuarterVo.setTradeAmount(listQuarter.get(i).getTradeAmount());  //本季度发生的金额
                    }
                    logger.info("查询本季度所匹配的数据中的总金额");
                    //本季度的所有金额
                    BigDecimal quarterTotal = publogService.selectQuarterTotal(listQuarter.get(i).getTranType(), pubQuarterge, pubQuarterle);
                    logger.info("查询本季度所匹配的数据中的所有关联方金额");

                    if (quarterTotal != null && !quarterTotal.equals(BigDecimal.ZERO)) {
                        PubQuarterVo.setTradePrice(String.valueOf(BigDecimal.ZERO));
                    } else {
                        PubQuarterVo.setTradePrice(String.valueOf(quarterTotal));  //本季度发生的金额
                    }
                    if (listQuarter.get(i).getTradePrice() == null) {
                        PubQuarterVo.setTradePrice(String.valueOf(BigDecimal.ZERO));
                    } else {
                        PubQuarterVo.setTradePrice(listQuarter.get(i).getTradePrice());  //本季度发生的金额
                    }
                    //本期发生所有金额
                    logger.info("查询本年度所匹配的数据中的总金额");
                    BigDecimal total = publogService.selectTotal(listQuarter.get(i).getTranType(), pubYear);
                    //通过lists集合获取关联方 交易类型  查询关联方的本期所有金额
                    //    BigDecimal mount = publogService.selectAmt(lists.get(i).getRelName(),lists.get(i).getTranType(),pubYear);

                    listPub.add(PubQuarterVo);
                }

            }
            logger.info("将生成的list集合的数据放到map中生成xls表格数据");
            resultMap.put("pubYear", listPub);

            logger.info("生成披露表格后 将信息插入到披露记录表中");
            //保存数据到披露记录表
            Publog publog = new Publog();
            String version = "0.1";
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            List<Filter> filter = new ArrayList<Filter>();
            filter.add(new Filter("pubYear", pubYear));
            filter.add(new Filter("pubFileName", getExportFilename(parameters)));
            //  Publog log = publogService.get(filter);
            List<Publog> log = publogService.select(filter);
            publog.setPubFileName(getExportFilename(parameters));
            publog.setPubSubject("大家保险集团本级");
            publog.setPubYear(pubYear);
            publog.setDownAmt(0); //下载次数  默认是0
            publog.setStatus("01"); //01-待提交  02-审批中  03-审批通过  04-审批退回

            if (log != null && log.size() > 0) {
                if (log.get(0).getPubVersion() != null) {
                    String lastVersion = log.get(0).getPubVersion().substring(1);
                    Float newVersion = Float.parseFloat(lastVersion) + Float.parseFloat(version);
                    publog.setPubVersion("V" + newVersion);
                } else {
                    publog.setPubVersion("V" + version);
                }
            } else {
                publog.setPubVersion("V" + version);
            }

            publog.setDownStatus("1");  //是否下载  0-是  1-否
            publog.setMaxPercentInNetAssets(new Date());
            publog.setModifiedTime(new Date());
            //        publog.setDelflag("1");  //逻辑删除标识 1-不删除  0-删除
            if (userProfile != null) {
                publog.setCreateById(userProfile.getUserid());
                publog.setCreateByName(userProfile.getStaffName());
                publog.setCreateTime(new Date());
            } else {
                throw new ServiceException("user session may be expired.");
            }
            publogService.insert(publog);
        }
        return resultMap;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
}