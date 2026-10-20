package com.yzj.jep.app.api.web.controller;

import com.yzj.jep.app.bus.sch.UpdateSourceTask;
import com.yzj.jep.app.bus.service.RelPsnAndOrgOfProService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.ins.service.UnSortOutService;
import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.code.YesNo;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.sys.code.NotifyConfigType;
import com.yzj.jep.sys.code.NotifyStatus;
import com.yzj.jep.sys.code.StaffNotifyStatus;
import com.yzj.jep.sys.mail.IEmailSendService;
import com.yzj.jep.sys.model.Notify;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.model.StaffNotify;
import com.yzj.jep.sys.service.StaffNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 测试 数据库关联关系导入到图数据库中
 */
@Controller
@RequestMapping("/JobTest/")
public class JobTestController {
    Logger logger = LoggerFactory.getLogger(JobTestController.class);
    @Autowired
    private RelService relService;
    @Autowired
    private RelPsnAndOrgOfProService relPsnAndOrgOfProService;

    @Autowired
    private IEmailSendService iEmailSendService;

    private PersonService personService;
    private OrganService organService;
    private RelatedRelationService relatedRelationService;
    private FinProductService finProductService;
    private CreditService creditService;
    private LoanService loanService;
    private NonloanService nonloanService;
    private InsInvestmentService insInvestmentService;
    private InsFundsService insFundsService;
    private InsBusinessService insBusinessService;
    private InsInterestService insInterestService;
    private InsServiceService insServiceService;
    private InsOtherService insOtherService;

    private InsAllbustraninfoService insAllbustraninfoService;
    private InsAllfundsService insAllfundsService;
    private InsRelfundsService insRelfundsService;

    private EastGLFXXService eastGLFXXService;
    private EastGLJYHZService eastGLJYHZService;
    private EastZDGLJYMXService eastZDGLJYMXService;
    private EastGLGXService eastGLGXService;

    private UnSortOutService unSortOutService;

    @RequestMapping(value = "/Job",method = RequestMethod.GET)
    @ResponseBody
    public void executeJob(String job ){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //抽取开始
        String nowDate = getDate();
        Map map = new HashMap();
        if(StringUtils.isEmpty(job)){
            job = "all";
        }
        map.put("tag",job);
        relService.updateNeo4j(map, nowDate);
    }



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

    @RequestMapping(value = "/email",method = RequestMethod.GET)
    @ResponseBody
    public String sendEmail(){
        return iEmailSendService.sendMail("973546200@qq.com", "", "", "测试邮件", "测试环境邮件发送");
    }


    @RequestMapping("/initNeo4j")
    @ResponseBody
    public String initNeo4j() {
        Map<String, Object> map = new HashMap<>();
        String nowDate = "2022-03-04";
        relService.updateNeo4j(map, nowDate);
        return "success";
    }

    @RequestMapping("/initNeo4j1")
    @ResponseBody
    public String initNeo4j1() {
        Map<String, Object> map = new HashMap<>();
        String nowDate = "2022-03-04";
        relService.updateNeo4j1(map, nowDate);
        return "success";
    }

    @RequestMapping("/updateSource")
    @ResponseBody
    public String updateSource() {

        personService= SpringContextHolder.getBean(PersonService.class);
        organService=SpringContextHolder.getBean(OrganService.class);
        relatedRelationService=SpringContextHolder.getBean(RelatedRelationService.class);
        finProductService=SpringContextHolder.getBean(FinProductService.class);
        creditService=SpringContextHolder.getBean(CreditService.class);
        loanService=SpringContextHolder.getBean(LoanService.class);
        nonloanService=SpringContextHolder.getBean(NonloanService.class);
        insAllbustraninfoService=SpringContextHolder.getBean(InsAllbustraninfoService.class);
        insInvestmentService=SpringContextHolder.getBean(InsInvestmentService.class);
        insFundsService=SpringContextHolder.getBean(InsFundsService.class);
        insBusinessService=SpringContextHolder.getBean(InsBusinessService.class);
        insInterestService=SpringContextHolder.getBean(InsInterestService.class);
        insServiceService = SpringContextHolder.getBean(InsServiceService.class);
        insOtherService = SpringContextHolder.getBean(InsOtherService.class);

        Map<String, Object> map = new HashMap<>();
        String nowDate = "2022-03-14";

        //抽取自然人信息
        try {
            //personService.extractPerson(map,nowDate);
        }catch (Exception e){
            logger.error("抽取自然人信息异常：",e);
        }
        //抽取法人机构
        try {
            //organService.extracOrgan(map,nowDate);
        }catch (Exception e){
            logger.error("抽取法人机构异常：",e);
        }
        //抽取关联关系
        try {
            //relatedRelationService.extractRelatedRelation(map,nowDate);
        }catch (Exception e){
            logger.error("抽取关联关系异常：",e);
        }
        //抽取金融产品
        try {
            //finProductService.extractFinProduct(map,nowDate);
        }catch (Exception e){
            logger.error("抽取金融产品异常：",e);
        }
        //抽取投资入股类
        try{
            insInvestmentService.extractInsInestment(map,nowDate);
        }catch(Exception e){
            logger.error("抽取投资入股类异常：",e);
        }
        //抽取资金运用类
        try{
            insFundsService.extractInsFunds(map,nowDate);
        }catch(Exception e){
            logger.error("抽取资金运用类异常：",e);
        }
        //抽取保险业务类
        try{
            insBusinessService.extractInsBusiness(map,nowDate);
        }catch(Exception e){
            logger.error("抽取保险业务类异常：",e);
        }
        //抽取利益转移类
        try{
            insInterestService.extractInsInterest(map,nowDate);
        }catch(Exception e){
            logger.error("抽取利益转移类异常：",e);
        }
        //抽取提供服务类
        try{
            insServiceService.extractInsService(map,nowDate);
        }catch(Exception e){
            logger.error("抽取提供服务类异常：",e);
        }
        //抽取其他类
        try{
            insOtherService.extractInsOther(map,nowDate);
        }catch(Exception e){
            logger.error("抽取其他类异常：",e);
        }

        return "success";
    }

    @RequestMapping("/updateSourceTask")
    @ResponseBody
    public String updateSourceTask() {
        insAllbustraninfoService=SpringContextHolder.getBean(InsAllbustraninfoService.class);
        insAllfundsService = SpringContextHolder.getBean(InsAllfundsService.class);
        insRelfundsService = SpringContextHolder.getBean(InsRelfundsService.class);
        personService= SpringContextHolder.getBean(PersonService.class);
        organService=SpringContextHolder.getBean(OrganService.class);
        eastGLFXXService = SpringContextHolder.getBean(EastGLFXXService.class);
        eastGLJYHZService = SpringContextHolder.getBean(EastGLJYHZService.class);
        eastZDGLJYMXService = SpringContextHolder.getBean(EastZDGLJYMXService.class);
        insServiceService = SpringContextHolder.getBean(InsServiceService.class);
        Map<String, Object> map = new HashMap<>();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //抽取关联交易明细表
//        try {
//            insAllbustraninfoService.extractCredit(map);
//        }catch (Exception e){
//            logger.error("抽取关联交易明细表异常：",e);
//        }
//        //抽取资金运用类统计表
//        try {
//            insAllfundsService.extractCredit(map);
//        }catch (Exception e){
//            logger.error("抽取资金运用统计表异常：",e);
//        }
//        try {
//            insRelfundsService.extractCredit(map);
//        }catch (Exception e){
//            logger.error("抽取与单一法人资金运用统计表异常：",e);
//        }

        eastGLFXXService = SpringContextHolder.getBean(EastGLFXXService.class);
        eastGLJYHZService = SpringContextHolder.getBean(EastGLJYHZService.class);
        eastZDGLJYMXService = SpringContextHolder.getBean(EastZDGLJYMXService.class);
        eastGLGXService = SpringContextHolder.getBean(EastGLGXService.class);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //抽取开始
        String nowDate=getDate();
        try {
            unSortOutService = SpringContextHolder.getBean(UnSortOutService.class);
            unSortOutService.scheduledTasks();
//            personService.extractPerson(map,nowDate);
        }catch (Exception e){
            logger.error("抽取自然人信息异常：",e);
        }
        //抽取法人机构
//        try {
//            organService.extracOrgan(map,nowDate);
//        }catch (Exception e){
//            logger.error("抽取法人机构异常：",e);
//        }


        try {
            logger.info("开始east抽取关联方信息---");
//            eastGLFXXService.eastExtract();
//            insServiceService.extractInsService(map,nowDate);
            logger.info("结束east抽取关联方信息---当前时间："+ DateUtils.formatCurrentDateTime());
        }catch (Exception e){
            logger.error("east抽取关联方信息异常：",e);
        }
        logger.info("EASTReportTask源数据抽取完成----当前时间："+df.format(new Date()));
        logger.info("==========EASTReportTask 结束调度执行============");




        return "success";
    }
}
