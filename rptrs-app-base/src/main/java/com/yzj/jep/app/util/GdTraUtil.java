package com.yzj.jep.app.util;

import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.web.code.WebCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;

public class GdTraUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(GdTraUtil.class);

    private static RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
    private static RelPsnService relPsnService = SpringContextHolder.getBean(RelPsnService.class);
    private static RootOrgInfoService rootOrgInfoService = SpringContextHolder.getBean(RootOrgInfoService.class);

    /**
     * 检查股东可用股数
     *
     * @param fromShareholdersType
     * @param fromShareholdersCode
     * @param fromShareholdersName
     * @param operationAmount
     * @return
     */
    public static boolean checkUseAmount(String fromShareholdersType, String fromShareholdersCode, String fromShareholdersName, String operationAmount) {
        LOGGER.debug("检查股东可用股数...fromShareholdersType[{}],fromShareholdersCode[{}],fromShareholdersName[{}],operationAmount[{}]", fromShareholdersType, fromShareholdersCode, fromShareholdersName, operationAmount);
        String holeShareAmount = null;
        String frozenShareAmount = null;
        String pledgedShareAmount = null;
        if ("PSN".equals(fromShareholdersType)) {
            RelPsn relPsn = new RelPsn();
            relPsn.setName(fromShareholdersName);
            relPsn.setShareholdersCode(fromShareholdersCode);
            RelPsn psn = relPsnService.get(relPsn);
            holeShareAmount = psn.getHoleShareAmount();
            frozenShareAmount = psn.getFrozenShareAmount();
            pledgedShareAmount = psn.getPledgedShareAmount();
        } else {
            RelOrg relOrg = new RelOrg();
            relOrg.setOrgName(fromShareholdersName);
            relOrg.setShareholdersCode(fromShareholdersCode);
            RelOrg org = relOrgService.get(relOrg);
            holeShareAmount = org.getHoleShareAmount();
            frozenShareAmount = org.getFrozenShare();
            pledgedShareAmount = org.getPledgedShare();
        }
        LOGGER.debug("holeShareAmount:[{}],frozenShareAmount:[{}],pledgedShareAmount:[{}]", holeShareAmount, frozenShareAmount, pledgedShareAmount);
        Integer holeShareAmountInt = holeShareAmount == null ? 0 : Integer.parseInt(holeShareAmount);
        Integer frozenShareAmountInt = frozenShareAmount == null ? 0 : Integer.parseInt(frozenShareAmount);
        Integer pledgedShareAmountInt = pledgedShareAmount == null ? 0 : Integer.parseInt(pledgedShareAmount);
        Integer useAmount = holeShareAmountInt - frozenShareAmountInt - pledgedShareAmountInt;
        if (useAmount < Integer.parseInt(operationAmount)) {
            return false;
        }
        return true;
    }

    /**
     * 股东信息查询
     *
     * @param code
     * @return
     */
    public static Map<String, Object> getShareholder(String code) {
        LOGGER.debug("股东信息查询...code[{}]", code);
        Map<String, Object> map = Maps.newHashMap();

        RelPsn relPsn = relPsnService.getGdInfo(code);
        if (relPsn != null) {
            map.put("name", relPsn.getName());
            map.put("holeShareAmount", relPsn.getHoleShareAmount());
            map.put("shareholdingProportion", relPsn.getShareholdingProportion());
            map.put("shareholdersType", "PSN");
            map.put("frozenShareAmount", relPsn.getFrozenShareAmount());
            map.put("pledgedShareAmount", relPsn.getPledgedShareAmount());
            map.put("domesticOrForeign", relPsn.getDomesticOrForeign());
            map.put("stateOwned", null);
            map.put(WebCode.SUCCESS, true);
            return map;
        } else {
            RelOrg relOrg = relOrgService.getGdInfo(code);
            if (relOrg != null) {
                map.put("name", relOrg.getOrgName());
                map.put("holeShareAmount", relOrg.getHoleShareAmount());
                map.put("shareholdingProportion", relOrg.getShareholdingProportion());
                map.put("shareholdersType", "ORG");
                map.put("frozenShareAmount", relOrg.getFrozenShare());
                map.put("pledgedShareAmount", relOrg.getPledgedShare());
                map.put("domesticOrForeign", relOrg.getDomesticOrForeign());
                map.put("stateOwned", relOrg.getStateOwned());
                map.put(WebCode.SUCCESS, true);
                return map;
            }
        }
        map.put(WebCode.SUCCESS, false);
        map.put(WebCode.MSG, "无此股东");
        return map;
    }

    /**
     * 算持股占比
     *
     * @param amount
     * @return
     */
    public static Double getProportion(String amount) {
        LOGGER.debug("算持股占比...amount[{}]", amount);
        RootOrgInfo orgInfo = rootOrgInfoService.get(1);
        String totalShareAmount = orgInfo.getTotalShareAmount();
        LOGGER.debug("totalShareAmount:[{}]", totalShareAmount);
        Double shareAmount = Double.parseDouble(totalShareAmount);
        Double amountDou = Double.parseDouble(amount);
        BigDecimal proportion = new BigDecimal(amountDou / shareAmount);
        return proportion.multiply(new BigDecimal(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
