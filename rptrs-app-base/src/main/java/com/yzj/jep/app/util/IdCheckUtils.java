package com.yzj.jep.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * 校验身份证号码是否合法
 */
public class IdCheckUtils {
    private static Logger	logger	= LoggerFactory.getLogger(IdCheckUtils.class);
    //验证证件号码格式
    public static boolean idNumCHK(String idNum) {
        String identifyNumber = idNum;
        if (identifyNumber.length() == 18) {
            try {
                // 格式的正则
                String idCardPatter = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                        "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
                // 正则判断正确后进行更加详细的判断
                if (identifyNumber.matches(idCardPatter)) {
                    char[] idArray = identifyNumber.toCharArray();
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2}; //将前17位加权因子保存在数组里
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"}; //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                    int idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(idArray[i]));
                        int count = current * idCardWi[i];
                        idCardWiSum += count;
                    }
                    String idCardLast = String.valueOf(idArray[17]);
                    int idCardMod = idCardWiSum % 11;
                    if (idCardMod == 2) {
                        //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                        if (!idCardLast.equals("X")) {
                            return false;
                        }
                    }
                    //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                    if(!idCardLast.equals(idCardY[idCardMod])) {
                        return false;
                    }
                    return true;
                }
            }catch (Exception e) {
                e.printStackTrace();
                logger.info("异常:" + identifyNumber);
                return false;
            }
            return false;
        }
        return false;
    }
}
