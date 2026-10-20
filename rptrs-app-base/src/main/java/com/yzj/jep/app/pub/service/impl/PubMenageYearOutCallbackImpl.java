package com.yzj.jep.app.pub.service.impl;

import com.yzj.jep.app.pub.model.PubYearVo;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
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

import static java.math.BigDecimal.ROUND_DOWN;
import static java.math.BigDecimal.ZERO;

/**
 * @author LONG
 * @date 2020/7/20
 * @description
 */
@Service(value = PubMenageYearOutCallbackImpl.BEANID)
class PubMenageYearOutCallbackImpl implements XlsExportCallback {

    private static Logger logger = LoggerFactory.getLogger(PubMenageYearOutCallbackImpl.class);

    public static final String BEANID = "PubMenageYearOutCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "PubMenageYear.xls";

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
        String fileName = "PubMenageYear_" + sf.format(new Date()) + ".xls";
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
        List<PubYearVo> listPub = new ArrayList<PubYearVo>();
        // String lastAmt;
        // String lastRatio;
        BigDecimal periodAmt;
        //获取参数
        String pubYear = (String) parameters.get("id");
        if (StringUtils.isNotBlank(pubYear)) {
            logger.info("查询本年度所匹配的数据");
            //查询数据 通过本年度去查询所匹配的数据
            List<PubYearVo> lists = publogService.queryDate(pubYear);
            logger.info("查询上年度所匹配的数据");
            //查询数据 通过上期年度去查询所匹配的数据
            List<PubYearVo> list = publogService.queryDate(String.valueOf(Integer.valueOf(pubYear) - 1));

            if (lists.size() > 0 && lists != null) {
                //通过lists集合获取交易类型 查询交易类型下的本期所有金额
                for (int i = 0; i < lists.size(); i++) {
                    List<Filter> filters = new ArrayList<>();
                    PubYearVo pubYearVo = new PubYearVo();
                    pubYearVo.setNo(String.valueOf(i + 1));
                    pubYearVo.setRelName(lists.get(i).getRelName());
                    //通过查询字典 将编码值跟文字进行对应
                    filters.add(new Filter("dictCode", "INS-tranType"));
                    filters.add(new Filter("itemValue", lists.get(i).getTranType()));
                    DictItem dictItem = dictItemService.get(filters);
                    if (dictItem != null) {
                        pubYearVo.setTranType(dictItem.getItemLabel());
                    }
                    //将相同的交易类型合并单元格

                    pubYearVo.setRelText(lists.get(i).getTradeSubject());
                    pubYearVo.setPricingPolicy("");
                    if (lists.get(i).getTradeAmount() == null) {
                        periodAmt = BigDecimal.ZERO;
                        pubYearVo.setPeriodAmt(String.valueOf(BigDecimal.ZERO));
                    } else {
                        periodAmt = BigDecimal.valueOf(Double.valueOf(lists.get(i).getTradeAmount()));
                        pubYearVo.setPeriodAmt(lists.get(i).getTradeAmount());  //本期发生的金额
                    }
                    logger.info("查询上年度所匹配的数据中的总金额");
                    //上期发生的所有金额
                    BigDecimal lastTotal = publogService.selectTotal(lists.get(i).getTranType(), String.valueOf(Integer.valueOf(pubYear) - 1));
                    logger.info("查询上年度所匹配的数据中的所有关联方金额");
                    //上期发生
                    BigDecimal lastAmt = publogService.selectLastAmt(lists.get(i).getRelName(), lists.get(i).getTranType(), String.valueOf(Integer.valueOf(pubYear) - 1));
                    if (lastAmt == null) {
                        lastAmt = BigDecimal.ZERO;
                        pubYearVo.setLastAmt(String.valueOf(BigDecimal.ZERO));
                    } else {
                        pubYearVo.setLastAmt(String.valueOf(lastAmt));
                    }
                    if (lastTotal != null && !lastTotal.equals(ZERO)) {
                        //上期同类占比
                        pubYearVo.setLastRatio(String.valueOf(((lastAmt.multiply(BigDecimal.valueOf(100))).divide(lastTotal, 2, ROUND_DOWN))));
                    } else {
                        pubYearVo.setLastRatio(String.valueOf(BigDecimal.ZERO));
                    }

                    //本期发生所有金额
                    logger.info("查询本年度所匹配的数据中的总金额");
                    BigDecimal total = publogService.selectTotal(lists.get(i).getTranType(), pubYear);
                    //通过lists集合获取关联方 交易类型  查询关联方的本期所有金额
                    if (total != null && (BigDecimal.ZERO).equals(total)) {
                        //本期同类占比

                        pubYearVo.setPeriodRatio(String.valueOf(((periodAmt.multiply(BigDecimal.valueOf(100))).divide(total, 2, ROUND_DOWN))));
                    } else {
                        pubYearVo.setPeriodRatio(String.valueOf(BigDecimal.ZERO));
                    }
                    pubYearVo.setTotal(String.valueOf(total));
                    //判断是否是重大关联交易
                    if ("1".equals(lists.get(i).getMajorTrade())) {
                        //统计重大关联交易笔数
                        Integer aa = publogService.majorTradeCount(lists.get(i).getRelName(), lists.get(i).getTranType(), pubYear);
                        pubYearVo.setMajorTradecount(String.valueOf(aa));
                    } else {
                        pubYearVo.setMajorTradecount(String.valueOf(BigDecimal.ZERO));
                    }
                    listPub.add(pubYearVo);
                }
                logger.info("将生成的list集合的数据放到map中生成xls表格数据");
                resultMap.put("pubYear", listPub);
            }

            logger.info("生成披露表格后 将信息插入到披露记录表中");
            if (lists.size() > 0 && lists != null) {
                //保存数据到披露记录表
                Publog publog = new Publog();
                List<Order> orders = new ArrayList<>();
                orders.add(new Order("createTime", OrderType.DESC));
                String version = "0.1";
                UserProfile userProfile = UserProfileUtils.getUserProfile();
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
                List<Filter> filter = new ArrayList<Filter>();
                filter.add(new Filter("pubYear", pubYear));
                filter.add(new Filter("pubFileName", getExportFilename(parameters)));
                List<Publog> log = publogService.select(filter);
                publog.setPubFileName(getExportFilename(parameters));
                publog.setPubSubject("大家保险集团本级");
                publog.setPubYear(pubYear);
                publog.setDownAmt(0); //下载次数  默认是0
                publog.setStatus("01"); //01-待提交  02-审批中  03-审批通过  04-审批退回
                if (log != null && log.size() > 0) {
                    if (log.get(log.size() - 1).getPubVersion() != null) {
                        String lastVersion = log.get(log.size() - 1).getPubVersion().substring(1);
                        BigDecimal newVersion = (new BigDecimal(lastVersion)).add(new BigDecimal(version));
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