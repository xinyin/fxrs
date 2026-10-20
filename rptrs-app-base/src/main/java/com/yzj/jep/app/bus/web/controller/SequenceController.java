package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.SequenceService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/bus/sequence")
public class SequenceController {
    private static Logger logger = LoggerFactory.getLogger(SequenceController.class);
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private RelOrgService relOrgService;

    @RequestMapping(value = "/list", produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public String list(Model model) {
        Map<String, Object> infoMap = new HashMap<String, Object>();
        List<Map<String, Object>> info = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap();
        /*
         * 获取机构  - 股东信息
         * */
        List<Sequence> listOrgShareOrg = sequenceService.getOrgShareOrg(1);
//        /*
//         * 获取机构  - 自然人股东信息
//         * */
//        List<Sequence> listOrgSharePsn = sequenceService.getOrgSharePsn(1);

        /*
         * 获取机构投资信息
         * */
        List<Sequence> listOrgInvest = sequenceService.getOrgInvest(1);

        /*
         * 获取机构附属-员工信息
         * */
        List<Sequence> listOrgSubsidiary = sequenceService.getOrgSubsidiary(1);
        /*
         * 获取自然人投资信息
         * */
        List<Sequence> listPsnInvest = sequenceService.getPsnInvest(1);

        /*
         * 获取自然人亲属信息
         * */
        List<Sequence> listPsnRelation = sequenceService.getPsnRelation(1);

        /*
         * 获取自然人单位信息
         * */
        List<Sequence> listPsnUnit = sequenceService.getPsnUnit(1);

        /*
         * 获取机构的法定代表人
         * */
        RelOrg relOrg = relOrgService.get(1);
        map1.put("name", "成都农商银行");
        //存储机构投资情况
        List<ChildInfo> mapPa2 = new ArrayList<>();
        ChildInfo parentInfo1 = new ChildInfo();
        parentInfo1.setName("投资信息");
        parentInfo1.setId(UUID.randomUUID().toString());
        List<ChildInfo> parentInfoList = new ArrayList<ChildInfo>();
        ChildInfo parentInfo;
        for (int i = 0; i < listOrgInvest.size(); i++) {
            parentInfo = new ChildInfo();
            parentInfo.setName(listOrgInvest.get(i).getOrgName());
            parentInfo.setRatio(listOrgInvest.get(i).getShareholdingRatio().toString());
            parentInfo.setAmount("1000");
            parentInfo.setHasChildren("true");
            parentInfo.setHasHumanholding("true");
            parentInfo.setIsExpand("true");
            parentInfo.setId(UUID.randomUUID().toString());
            parentInfoList.add(parentInfo);
        }
        parentInfo1.setChildren(parentInfoList);
        mapPa2.add(parentInfo1);

        //map1 里面装的是第一个对象信息
        ChildInfo childInfo1 = new ChildInfo();
        childInfo1.setChildren(mapPa2);
        childInfo1.setDirection("downward");
        childInfo1.setName("origin");
        childInfo1.setIsExpand("true");
        childInfo1.setAmount("");
        map1.put("downward", childInfo1);

        //存储  股东信息
//        List<Object> mapCh2 = new ArrayList<>();
//        ChildInfo childInfo1 = new ChildInfo();
//        childInfo1.setName("股东");
//        childInfo1.setId(UUID.randomUUID().toString());
//        List<ChildInfo> childInfoList = new ArrayList<>();
//        for (int i = 0; i < listOrgShareOrg.size(); i++) {
//            ChildInfo childInfo = new ChildInfo();
//            childInfo.setName(listOrgShareOrg.get(i).getOrgName());
//            childInfo.setId(UUID.randomUUID().toString());
//            childInfoList.add(childInfo);
//        }
//        childInfo1.set_children(childInfoList);
//
//        //存储  法定代表人
//        ChildInfo childInfoLegal = new ChildInfo();
//        childInfoLegal.setName("法定代表人");
//        childInfoLegal.setId(UUID.randomUUID().toString());
//        List<ChildInfo> childInfoListLegal = new ArrayList<>();
//        ChildInfo childInfo = new ChildInfo();
//        childInfo.setName(relOrg.getLegalPerson());
//        childInfo.setId(UUID.randomUUID().toString());
//        childInfoListLegal.add(childInfo);
//        childInfoLegal.set_children(childInfoListLegal);
//
//        //存储高管信息
//        ChildInfo childInfoSubsidiary = new ChildInfo();
//        childInfoSubsidiary.setName("高管信息");
//        childInfoSubsidiary.setId(UUID.randomUUID().toString());
//        List<ChildInfo> childInfoListSubsidiary = new ArrayList<>();
//        for (int i = 0; i < listOrgSubsidiary.size(); i++) {
//            ChildInfo childInfo2 = new ChildInfo();
//            childInfo2.setName(listOrgSubsidiary.get(i).getName());
//            childInfo2.setId(UUID.randomUUID().toString());
//            childInfoListSubsidiary.add(childInfo2);
//        }
//        childInfoSubsidiary.set_children(childInfoListSubsidiary);
//
//
//        // 存储右侧信息
//        mapCh2.add(childInfo1);
//        mapCh2.add(childInfoLegal);
//        mapCh2.add(childInfoSubsidiary);
//        map1.put("_children", mapCh2);

//        System.out.println("success");
//        map.put("ShareOrg",listOrgShareOrg);
//        map.put("LegalPerson",relOrg.getLegalPerson());
//        map1.put("_children",listOrgInvest);
//        info.add(map);
//        infoMap.put("name","成都农商银行");
//        infoMap.put("orgInfo",map);
        JSONObject jsonObject = JSONObject.fromObject(map1);
        String result = jsonObject.toString();
        return result;
    }
}
