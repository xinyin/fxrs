package com.yzj.jep.app.bus.sch;

import com.fPaaS1.core.IFPaaS1Handler;
import com.yzj.jep.app.bus.model.UnitEntitys;
import com.yzj.jep.app.bus.service.UpdateUnittService;
import com.yzj.jep.app.util.UnitUtil;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.sys.service.UnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author yls
 * @since 2020/10/22
 */
@ScheduledJob("UpdateUnitTask")
public class UpdateUnitTask implements Job {

    Logger logger = LoggerFactory.getLogger(UpdateUnitTask.class);

    private UnitService unitService;
    private UpdateUnittService updateUnittService;


    @Override
    public void execute(JobContext context) throws JobExecutionException {
        updateUnittService = SpringContextHolder.getBean(UpdateUnittService.class);
        List<UnitEntitys> unitEntitysList = new ArrayList<>();
        try {
            UnitUtil fozuUtil = new UnitUtil();
//           通过gateway获取接口实体
//            unitEntitysList = fozuUtil.deptsMethod();
            unitEntitysList = updateUnittService.deptsMethod();
//           unitEntitysList = fozuUtil.listAboutEntity();

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("机构树端口请求异常!请联系管理员");
        }
        if (unitEntitysList != null) {
//           执行工具类方法插入
            analysis(unitEntitysList);
        }
    }

    /**
     * Java实体类内嵌list进行遍历插入数据或修改未更新状态
     *
     * @param list 请求端口得到的数据
     */
    public void analysis(List<UnitEntitys> list) {
        unitService = SpringContextHolder.getBean(UnitService.class);


        List<String> stringList = new ArrayList<>();
        //将接口数据拿到后判断接口数据里面是否包含某字符串,如果没有,则更新状态为CLOSE
        for (UnitEntitys ue : list) {
            String deptId = ue.getDeptId();
            stringList.add(deptId);
        }
        //关闭未更新的状态
        for (Unit u : unitService.selectAll()) {
            boolean b = ifInclude(stringList, u.getUnitCode());
            //存在或管护机构状态更新
            if (!b || !u.getUnitCode().contains("ZZ_Delegation") || !u.getUnitCode().contains("ZZHK")) {
                Unit unit = new Unit();
                unit.setUnitCode(unit.getUnitCode());
                unit.setStatus("CLOSE");
                unitService.updateSelective(unit);
            }

        }

        for (UnitEntitys unitEntitys : list) {

            System.out.print(unitEntitys.getDeptId() + "---");
            System.out.print(unitEntitys.getDeptName() + "---");
            System.out.println(unitEntitys.getParentId());
            Unit unit = new Unit();
            //节点编码
            unit.setUnitCode(unitEntitys.getDeptId());
            //节点名
            unit.setUnitName(unitEntitys.getDeptName());
            //父级节点名称
            unit.setParentId(unitEntitys.getParentId());
            //状态  CLOSE
            unit.setStatus("OPEN");
            try {
                boolean exists = unitService.exists(unitEntitys.getDeptId());
                if (exists) {

                    unitService.updateSelective(unit);
                } else {
                    //机构层级
                    unit.setHierarchy("30");
                    //机构类型
                    unit.setKind("DEPT");
                    //法人编码
                    unit.setTenantId("0101");
                    unitService.updateUnit(unit);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e.getMessage());
            }
            List<UnitEntitys> sysDeptInfos = unitEntitys.getSysDeptInfos();
            analysis(sysDeptInfos);
        }

        Unit unit = unitService.get("00");
        if (unit != null) {
            unit.setParentId(null);
            unitService.update(unit);
        }

    }

    /**
     * 判断List<String>里是否包含某字符串
     *
     * @param list
     * @param str
     * @return
     */
    public boolean ifInclude(List<String> list, String str) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).indexOf(str) != -1) {
                return true;
            }
        }
        return false;
    }


}
