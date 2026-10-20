package com.yzj.jep.app.sub.util;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Locale;

/**
 * MD5加密
 *    用于报送时对自然人证件号码加密
 */
public class MD5Utils {

    /**
     * MD%加密-32位小写
     * @param text 需要加密的字符
     * @return 返回加密后的字符
     */
    public static String md5ToLowerCase(String text) {
        if(StringUtils.isBlank(text)){
            return text;
        }
        String encodeStr = DigestUtils.md5Hex(text.trim());
        return encodeStr.toLowerCase(Locale.ROOT);
    }
}
