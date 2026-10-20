package com.yzj.jep.app.fPaas.server;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.vo.RelatedVo;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * 提供根据关联方名称模糊查询关联方接口
 */
public class RelatedQueryHandler implements IFPaaS1Handler<Message> {

    private RelPsnService relPsnService;
    private RelOrgService relOrgService;
    private DictItemService dictItemService;

    private static final Logger logger = LoggerFactory.getLogger(RelatedQueryHandler.class);

    private static final String DICTCODE = "dictCode";

    @Override
    public Message handle(Message message) throws Exception {
        relPsnService = SpringHolder.getBean(RelPsnService.class);
        relOrgService = SpringHolder.getBean(RelOrgService.class);
        dictItemService = SpringHolder.getBean(DictItemService.class);
        Map tips = new HashMap<String,Object>(16);
        Map map = (Map) message.getTarget().getBodys();
        List<RelatedVo> list = new ArrayList<>();
        try {
            List< Filter > filters = new ArrayList<>();
            JSONObject data = new JSONObject(map);
            //传入参数
            logger.info("根据关联方名称模糊查询关联方接口入参：" + data.toString());
            String str =  data.getString("name");
            //模糊查询自然人关联方
            filters.add(new Filter("name", Operator.LIKE, str));
            filters.add(new Filter("status", Operator.EQ, "6"));
            filters.add(new Filter("delflag", Operator.EQ, "1"));
            List<RelPsn> psn =  relPsnService.select(filters);
            //获取字典码
            Map<String, Map<String, String>> dictCodeMap = dictInfo(DICTCODE);
            if(psn.size() >0){
                for (RelPsn l:psn){
                    RelatedVo vo = new RelatedVo();
                    vo.setIdentifyType(dictCodeMap.get("relIdTypeMap").get(l.getIdentifyType()));
                    vo.setName(l.getName());
                    vo.setIdentifyNumber(l.getIdentifyNumber());
                    vo.setType("自然人");
                    list.add(vo);
                }
            }
            filters.remove(0);
            //模糊查询法人或组织关联方
            filters.add(new Filter("orgName", Operator.LIKE, str));
            List<RelOrg> org = relOrgService.select(filters);
            if(org.size() >0){
                for (RelOrg l:org){
                    RelatedVo vo = new RelatedVo();
                    vo.setName(l.getOrgName());
                    vo.setIdentifyType(dictCodeMap.get("idTypeMap").get(l.getIdentifyType()));
                    vo.setIdentifyNumber(l.getUniscCode());
                    vo.setType("法人或组织");
                    list.add(vo);
                }
            }
            tips.put("relatedList",list);
            tips.put("CHNL_ID",map.get("chnlId"));
            tips.put("TRANS_CODE","relatedQuery");
            tips.put("resultCode","0000");
            tips.put("msg","成功");
        } catch (NullPointerException e){
            e.printStackTrace();
            tips.put("resultCode","1001");
            tips.put("msg","失败");
            tips.put("relatedList",list);
        } catch (JSONException e){
            tips.put("resultCode","1001");
            tips.put("msg","失败");
            tips.put("relatedList",list);
        } catch (Exception e) {
            tips.put("resultCode", "9999");
            tips.put("msg", "失败");
            tips.put("relatedList",list);
        } finally {
            message.getTarget().setBody(tips);
            return message;
        }
    }

    /**
     * @param dictCode
     * @return
     * @desc 获取字典信息
     */
    public Map<String, Map<String, String>> dictInfo(String dictCode) {
        Map<String, Map<String, String>> dictCodeMap = new HashMap<>(16);
        //自然人证件类型
        List<DictItem> dictItems0 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_IDENTIFYTYPE.getCode()));
        Map relIdTypeMap = dictItems0.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("relIdTypeMap", relIdTypeMap);
        //关联方证件类型
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ORGIDENTIFYTYPE.getCode()));
        Map idTypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("idTypeMap", idTypeMap);
        return dictCodeMap;
    }
    /**
     * @param property1
     * @param value1
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(String property1, String value1) {
        List<Filter> filters = new Filter.Builder().eq(property1, value1).build();
        return filters;
    }
}
