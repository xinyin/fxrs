package com.yzj.jep.app.design.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.design.model.*;
import com.yzj.jep.app.design.service.EquityStructureChartService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.TraUtil;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author duanliqiang
 */
@Service
public class EquityStructureChartServiceImpl implements EquityStructureChartService {
    private static final Logger logger = LoggerFactory.getLogger(EquityStructureChartServiceImpl.class);

    @Resource
    public RelPsnService relPsnService;

    @Resource
    private RelOrgService relOrgService;

    @Resource
    private RelProService relProService;

    @Resource
    public RelService relService;

    @Resource
    DictItemService dictItemService;

    private static final Integer HIERARCHY_LIMIT = 10;


    @Override
    public RootNode selectBypid(String relId, String relType) {
        if (CharSequenceUtil.isBlank(relId)) {
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            if (userProfile == null) {
                logger.info("当前登录用户为空！");
                return null;
            }
            /* 多租户不适用单一pid，此处根据当前登录用户取获取*/
            List<Filter> filters = new Filter.Builder()
                    .eq("delflag", "1")
                    .eq("aligCode", "00")
                    .build();
            List<RelOrg> relOrgList = relOrgService.select(filters);
            if (CollUtil.isEmpty(relOrgList)) {
                logger.info("无数据！");
                return null;
            }
            RelOrg rootOrg = relOrgList.get(0);
            relId = rootOrg.getPid().toString();
            relType = "ORG";
        }
        /*
         * 字典由  关系类型 BUS-RelationType
         * 变为 与前一层的关联关系 BUS-RelZYD
         *  字段 relation_Type --> REL_PSN_ORG
         */

        List<DictItem> dictItemMap = TraUtil.getDictItems("BUS-RelZYD");
        String name = null;
        if ("PRO".equals(relType)) {
            RelPro relPro = relProService.get(Integer.valueOf(relId));
            if (relPro != null) {
                name = relPro.getProName();
            }
        } else if ("PSN".equals(relType)) {
            RelPsn relPsn = relPsnService.get(Integer.valueOf(relId));
            if (relPsn != null) {
                name = relPsn.getName();
            }
        } else {
            RelOrg relOrg = relOrgService.get(Integer.valueOf(relId));
            if (relOrg != null) {
                name = relOrg.getOrgName();
            }
        }
        // 构造构造左右两边的数据集
        DirectionData directionData = new DirectionData();
        // ****************************** 1、构造左边数据集 ******************************
        DirectionNode leftNode = new DirectionNode();
        List<RelationTypeNode> leftRtnList = genLeftData(relId, relType, dictItemMap);
        leftNode.setChildren(leftRtnList);
        directionData.setLeft(leftNode);
        // ****************************** 2、构造右边数据集 ******************************
        DirectionNode rightNode = new DirectionNode();
        // 添加右边数据集
        List<RelationTypeNode> rightRtnList = genRightData(relId, relType, dictItemMap);
        rightNode.setChildren(rightRtnList);

        directionData.setRight(rightNode);

        RootNode rootNode = new RootNode();
        rootNode.setRootName(name);
        rootNode.setDirectionData(directionData);
        rootNode.setRelId(relId);
        rootNode.setRelType(relType);
        return rootNode;
    }


    /**
     * 构造左边数据集方法
     *
     * @param rootId  根节点ID
     * @param relType
     * @return
     */
    private List<RelationTypeNode> genLeftData(String rootId, String relType, List<DictItem> dictItemMap) {
        Map<String, RelationTypeNode> rtnMap = new HashMap<>(16);
        /* 查询上层关联方*/
        RelInfoVo param = new RelInfoVo();
        param.setToPid(rootId);
        param.setToType(relType);
        List<RelInfoVo> list = relService.queryRelationsByParams(param);
        list.forEach(this::relationOrder);
        List<RelInfoVo> orderList = CollUtil.sortByProperty(list, "order");
        orderList.forEach(o -> fillRelationTypeMap(o.getRelCategorySub(), o.getFromName(), o.getHoldings(), o.getFromPid(), o.getFromType(), rtnMap, dictItemMap, null, null));
        return new ArrayList<>(rtnMap.values());

    }

    /**
     * 构造右边数据集方法
     *
     * @param
     * @param rootId  根节点ID
     * @param relType
     * @return
     */
    private List<RelationTypeNode> genRightData(String rootId, String relType, List<DictItem> dictItemMap) {
        Map<String, RelationTypeNode> rtnMap = new HashMap<>(16);
        /* 查询下层关联方*/
        RelInfoVo param = new RelInfoVo();
        param.setFromPid(rootId);
        param.setFromType(relType);
        List<RelInfoVo> list = relService.queryRelationsByParams(param);
        list.forEach(this::relationOrder);
        List<RelInfoVo> orderList = CollUtil.sortByProperty(list, "order");
        orderList.forEach(o -> fillRelationTypeMap(o.getRelCategorySub(), o.getToName(), o.getHoldings(), o.getToPid(), o.getToType(), rtnMap, dictItemMap, null, null));
        List<RelationTypeNode> valueList = new ArrayList<>(rtnMap.values());

        return new ArrayList(valueList);
    }

    /**
     * 设定排序
     *
     * @param item
     */
    private void relationOrder(RelInfoVo item) {
        String relCategorySub = item.getRelCategorySub();
        switch (relCategorySub) {
            case "106":
                item.setOrder(1);
                break;
            case "104":
                item.setOrder(2);
                break;
            case "107":
                item.setOrder(3);
                break;
            case "102":
                item.setOrder(4);
                break;
            case "100":
                item.setOrder(5);
                break;
            case "103":
                item.setOrder(6);
                break;
            case "109":
                item.setOrder(7);
                break;
            case "112":
                item.setOrder(8);
                break;
            case "111":
                item.setOrder(9);
                break;
            case "119":
                item.setOrder(10);
                break;
            default:
                item.setOrder(99);

        }
    }

    /**
     * 填充关系类型数据
     *
     * @param relCategorySub 关系小类
     * @param fromName       关联方名称
     * @param ratio          关系线上显示的文字
     * @param relId
     * @param relType
     * @param rtnMap         关系类型数据Map集合
     * @param hierarchy
     * @param relUnique
     */
    private void fillRelationTypeMap(String relCategorySub, String fromName, String ratio, String relId, String relType, Map<String, RelationTypeNode> rtnMap, List<DictItem> dictItemMap, Integer hierarchy, List<String> relUnique) {
        RelationTypeNode rtn = rtnMap.get(relCategorySub);
        if (rtn == null) {
            rtn = new RelationTypeNode();
            // TODO 关系类型需要转码，待处理
            String relationTypeName = TraUtil.getIdentifyType(dictItemMap, relCategorySub);
            rtn.setName(relationTypeName);
            rtn.setRelCategorySub(relCategorySub);
        }

        // 构造关系节点的子节点
        Children children = new Children();
        children.setRelId(relId);
        children.setRelType(relType);
        children.setName(fromName);
        hierarchy += 1;
        children.setHierarchy(hierarchy);
        children.setRelUnique(relUnique);
        // TODO 持股比例具体逻辑得后续根据业务情况调整
        if (ratio != null) {
            children.setRatio(ratio + "%");
        }
        // 将节点添加到 关系类型节点下面
        rtn.addChildren(children);
        rtnMap.put(relCategorySub, rtn);
    }


    @Override
    public RootNode selectAllJson(String relId, String relType) {
        if (CharSequenceUtil.isBlank(relId)) {
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            if (userProfile == null) {
                logger.info("当前登录用户为空！");
                return null;
            }
            /* 多租户不适用单一pid，此处根据当前登录用户取获取*/
            List<Filter> filters = new Filter.Builder()
                    .eq("delflag", "1")
                    .eq("orgName", "复星保德信人寿保险有限公司")
                    .build();
            List<RelOrg> relOrgList = relOrgService.select(filters);
            if (CollUtil.isEmpty(relOrgList)) {
                logger.info("无数据！");
                return null;
            }
            RelOrg rootOrg = relOrgList.get(0);
            relId = rootOrg.getPid().toString();
            relType = "ORG";
        }
        List<DictItem> dictItemMap = TraUtil.getDictItems("BUS-RelZZD");
        String name = null;
        if ("PRO".equals(relType)) {
            RelPro relPro = relProService.get(Integer.valueOf(relId));
            if (relPro != null) {
                name = relPro.getProName();
            }
        } else if ("PSN".equals(relType)) {
            RelPsn relPsn = relPsnService.get(Integer.valueOf(relId));
            if (relPsn != null) {
                name = relPsn.getName();
            }
        } else {
            RelOrg relOrg = relOrgService.get(Integer.valueOf(relId));
            if (relOrg != null) {
                name = relOrg.getOrgName();
            }
        }
        /* ******************************构造数据************************************/
        RootNode rootNode = new RootNode();
        rootNode.setRootName(name);
        rootNode.setRelId(relId);
        rootNode.setRelType(relType);
        //将所有关系加载到内存
        List<RelInfoVo> list = relService.queryRelationsByParams(new RelInfoVo());
        //处理两边的方向
        DirectionData directionData = new DirectionData();
        DirectionNode leftNode = new DirectionNode();
        List<RelationTypeNode> leftRtnList = genLeftDataRecursion(relId, relType, list, dictItemMap, 0, new ArrayList<>());

        //处理右边方向
        DirectionNode rightNode = new DirectionNode();
        List<RelationTypeNode> rightRtnList = genRightDataRecursion(relId, relType, list, dictItemMap, 0, new ArrayList<>());

        rightNode.setChildren(leftRtnList);
        leftNode.setChildren(rightRtnList);
        directionData.setLeft(leftNode);
        directionData.setRight(rightNode);
        rootNode.setDirectionData(directionData);
        return rootNode;
    }

    /**
     * 生成右边数据
     *
     * @param relId
     * @param relType
     * @param list
     * @param dictItemMap
     * @return
     */
    private List<RelationTypeNode> genRightDataRecursion(String relId, String relType, List<RelInfoVo> list, List<DictItem> dictItemMap, Integer hierarchy, List<String> relUnique) {
        //下一层关联方
        List<RelInfoVo> downList = list.stream().filter(vo -> relId.equals(vo.getFromPid()) && relType.equals(vo.getFromType())).collect(Collectors.toList());
        Map<String, RelationTypeNode> rtnMap = new LinkedHashMap<>();
        //需要构造两层节点，一层关系，一层基本信息
        downList.forEach(vo -> {
            //通过关系层数控制
            if (hierarchy < HIERARCHY_LIMIT) {
                if (!relUnique.contains(vo.getPidUnique())) {
                    ArrayList<String> listNew = new ArrayList<>(relUnique);
                    listNew.add(vo.getPidUnique());
                    fillRelationTypeMap(vo.getRelCategorySub(), vo.getToName(), vo.getHoldings(), vo.getToPid(), vo.getToType(), rtnMap, dictItemMap, hierarchy, listNew);
                }
            }
        });
        List<RelationTypeNode> valueList = new ArrayList<>(rtnMap.values());
        for (RelationTypeNode node : valueList) {
            List<Children> children = node.getChildren();
            for (Children child : children) {
                List<RelationTypeNode> nodeList = genRightDataRecursion(child.getRelId(), child.getRelType(), list, dictItemMap, child.getHierarchy(), child.getRelUnique());
                child.setChildren(nodeList);
            }
        }
        /* 在后台排序*/
        return CollUtil.sortByProperty(valueList, "order");

    }

    /**
     * 递归获取数据
     *
     * @param relId
     * @param relType
     * @param list
     * @param dictItemMap
     * @param hierarchy
     * @param relUnique
     * @return
     */
    private List<RelationTypeNode> genLeftDataRecursion(String relId, String relType, List<RelInfoVo> list, List<DictItem> dictItemMap, Integer hierarchy, List<String> relUnique) {
        //上一层关联方
        List<RelInfoVo> upList = list.stream().filter(vo -> relId.equals(vo.getToPid()) && relType.equals(vo.getToType())).collect(Collectors.toList());
        Map<String, RelationTypeNode> rtnMap = new LinkedHashMap<>();
        // 需要构造两层节点，一层关系，一层基本信息
        upList.forEach(vo -> {
            if (hierarchy < HIERARCHY_LIMIT) {
                if (!relUnique.contains(vo.getPidUnique())) {
                    ArrayList<String> listNew = new ArrayList<>(relUnique);
                    listNew.add(vo.getPidUnique());
                    fillRelationTypeMap(vo.getRelCategorySub(), vo.getFromName(), vo.getHoldings(), vo.getFromPid(), vo.getFromType(), rtnMap, dictItemMap, hierarchy, listNew);
                }
            }
        });
        List<RelationTypeNode> valueList = new ArrayList<>(rtnMap.values());
        for (RelationTypeNode node : valueList) {
            List<Children> children = node.getChildren();
            for (Children child : children) {
                List<RelationTypeNode> nodeList = genLeftDataRecursion(child.getRelId(), child.getRelType(), list, dictItemMap, child.getHierarchy(), child.getRelUnique());
                child.setChildren(nodeList);
            }
        }
        /* 排序*/
        return CollUtil.sortByProperty(valueList, "order");
    }
}
