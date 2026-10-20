package com.yzj.jep.app.bus.sch;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelOrgBranch;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgBranchService;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.app.util.JdbcUtil;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import com.yzj.jep.sys.mail.IEmailSendService;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 每日从数分系统抽取保险业务类数据
 */
@ScheduledJob("InDataExtractTask")
public class InsuranceDataExtractTask implements Job {

    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private RelOrgBranchService relOrgBranchService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private IEmailSendService iEmailSendService;

    @Override
    public void execute(JobContext context) throws JobExecutionException, IOException {
        List<RelPsn> relPsnList = null;
        List<RelOrg> relOrgList = null;
        List<RelOrgBranch> relOrgBranchList = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //查询数分系统数据
            conn = JdbcUtil.getConnectUseProp();
            conn.setAutoCommit(false);
            //定义sql
            String sql = "SELECT * FROM test";
            //获取执行sql的对象
            ps = conn.prepareStatement(sql);
//            ps.setString(1,"seq");
//            ps.setString(2, Submitted.ORGNUMBER.getId());
//            ps.setString(3,Submitted.ORGNUMBER.getName());
            //将sql语句放入batch
            ps.addBatch();
            rs = ps.executeQuery();
            conn.commit();
                /**
                 * 循环遍历每一条交易的对手信息去我们关联方表，
                 * 和分公司表去匹配，如果能匹配到，
                 * 就抽取该条记录到保险业务类表中，状态为待补录状态，匹配不到则不抽取
                 */
                while (rs.next()) {
                    if ("1".equals(rs.getString("customerType"))) {
                        RelPsn psn = new RelPsn();
                        psn.setIdentifyType(rs.getString("uscType"));
                        psn.setIdentifyNumber(rs.getString("uscCode"));
                        relPsnList = relPsnService.select(psn);
                        if(null != relPsnList && relPsnList.size()>0){
                            /**
                             * 关联方为自然人并且实际发生金额大于50万的交易，
                             * 需要对相关部门经办人和负责人角色人员进行短信、邮件提醒
                             */
                            if(rs.getInt("pracTradeAmount")>500000){
                                /**
                                 * 关联自然人单笔50以上发起审批提醒：
                                 * 【中邮保险】关联交易管理系统提醒您有待办事宜：
                                 * 关联自然人xxx单笔50万元以上保险业务需立即发起关联交易审批。
                                 *
                                 */
                                List<Staff> list =  staffService.selectStaffByStdRolesAndDept("","3300,3320");
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("关联自然人单笔50以上发起审批提醒：【中邮保险】关联交易管理系统提醒您有待办事宜：关联自然人");
                                stringBuilder.append(rs.getString("relName"));
                                stringBuilder.append("单笔50万元以上保险业务需立即发起关联交易审批。");
                                for (Staff staff:list){
                                    if (null !=staff.getEmail()){
                                        iEmailSendService.sendMail(staff.getEmail(), "", "", "关联自然人单笔50以上发起审批提醒", stringBuilder.toString());
                                    }
                                }
                            }
                        }
                    }
                    if ("2".equals(rs.getString("customerType"))) {
                        RelOrg org = new RelOrg();
                        org.setIdentifyType(rs.getString("uscType"));
                        org.setUniscCode(rs.getString("uscCode"));
                        relOrgList = relOrgService.select(org);
                    }
                    if(null == relOrgList && relOrgList.size()<=0){
                        RelOrgBranch relOrgBranch = new RelOrgBranch();
                        relOrgBranch.setIdentifyType(rs.getString("uscType"));
                        relOrgBranch.setUniscCode(rs.getString("uscCode"));
                        relOrgBranchList = relOrgBranchService.select(relOrgBranch);
                    }
                    if (null != relPsnList && relPsnList.size() > 0 &&
                            null != relOrgList && relOrgList.size() > 0 &&
                            null != relOrgBranchList && relOrgBranchList.size() > 0){
                        TraBusiness traBusiness = new TraBusiness();
                        traBusiness.setTraSubName(rs.getString("transName"));
                        traBusiness.setRelName(rs.getString("relName"));
                        traBusiness.setTransNum(rs.getString("transNum"));
                        traBusiness.setRelNumber(rs.getString("relNumber"));
                        traBusiness.setTradeAmount(rs.getBigDecimal("tradeAmount"));
                        traBusiness.setTranDate(rs.getDate("tranDate"));
                        traBusiness.setCreateTime(new Date());
                        traBusiness.setStatus("1");
                        traBusiness.setMajorTrade("1");
                        traBusiness.setRelType(rs.getString("relType"));
                        traBusiness.setTranName(rs.getString("tranOpponent"));
                        traBusiness.setContractAmount(rs.getBigDecimal("contractAmount"));
                        traBusiness.setContractNo(rs.getString("contractNo"));
                        traBusiness.setPracTradeAmount(rs.getString("pracTradeAmount"));
                        traBusiness.setBusStartTime(rs.getDate("busStartTime"));
                        traBusiness.setBusEndTime(rs.getDate("busEndTime"));
                        //插入关联交易保险业务类表
                        traBusinessService.insertSelective(traBusiness);
                    }

                }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.close(rs,ps,conn);
        }
    }
}
