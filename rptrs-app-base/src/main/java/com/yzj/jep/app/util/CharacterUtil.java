package com.yzj.jep.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author by Liqiang
 * @Date 2023/2/14
 * @Description TODO
 */
public class CharacterUtil {
    /**
     * @author by Liqiang
     * @Date 2022/11/28
     * @Description 特殊字符转换成""
     */

    public static String changeCharacter(String str) {
        if(org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return null;
        }

        String replace =  str.replaceAll("\"(.*?)\"", "“$1”");

        Pattern p = compile(
                "[`\\s* \t \r \n \"]");
        Matcher m = p.matcher(replace);
        return m.replaceAll( "");
    }
}
