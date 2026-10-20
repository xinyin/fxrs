package com.yzj.jep.app.util;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 证件号码/手机号/姓名*******************
 * </p>
 *
 * @author yls
 * @since 2020/11/3
 */
public class ConfidentialInfoUtil {
    /**
     * 隐藏并替换字符串中所有的手机号
     *
     * @param contentStr
     * @return
     */
    public String hideAllPhoneNum(String contentStr) {
        if (contentStr == null || "".equals(contentStr)) {
            contentStr = "";
            return contentStr;
        }
        int length = contentStr.length();
        String substring = contentStr.substring(3, length - 4);
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < (length - 4) - 3; i++) {
            str.append("*");
        }
        contentStr = contentStr.replace(substring, str.toString());
        return contentStr;

    }

    /**
     * 隐藏并替换所有证件号码
     *
     * @param contentStr
     * @return
     */
    public static String hideAllIdCardNum(String contentStr) {
        if (contentStr == null || "".equals(contentStr)) {
            contentStr = "";
            return contentStr;
        }
        if (contentStr.length() > 7) {
            int length = contentStr.length();
            String substring = contentStr.substring(0, length - 4);
            StringBuffer str = new StringBuffer();
            for (int i = 0; i < length - 4; i++) {
                str.append("*");
            }
            contentStr = contentStr.replace(substring, str.toString());
            return contentStr;
        }
        return contentStr;
    }

    public  String desensitizedIdNumber(String idNumber){
        if (!Strings.isNullOrEmpty(idNumber)) {
            if (idNumber.length() == 15){
                idNumber= idNumber.replaceAll("(\\w{6})\\w*(\\w{3})", "$1******$2");
            }
            if (idNumber.length() == 18){
                idNumber= idNumber.replaceAll("(\\w{6})\\w*(\\w{3})", "$1*********$2");
            }
        }
        return idNumber;

    }

    /**
     * 姓名处理工具方法
     *
     * @param str
     * @return
     */
    public String replaceNameX(String str) {
        String reg = ".{1}";
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        int i = 0;
        while (m.find()) {
            i++;
            if (i == 1)
                continue;
            m.appendReplacement(sb, "*");
        }
        m.appendTail(sb);
        return sb.toString();
    }


    //身份证前六后四脱敏
    public static String idEncrypt(String id) {
        if (StringUtils.isEmpty(id)) {
            return id;
        }

        if (id.length() <= 10) {
            return id.replaceAll("(?<=.).(?=.)", "*");
        }
        return id.replaceAll("(?<=\\w{6})\\w(?=\\w{4})", "*");
    }
    //自然人非身份证脱敏
    public static String otherIdEncrypt(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{2})", "*");
    }



    /**
     * 自然人关联方名称，名脱敏
     *
     * @param name
     * @return
     */
    public static String nameEncrypt(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        return name.replaceAll("(?<!^).", "*");
    }

    /**
     * 保单号脱敏
     *
     * @param contractNo
     * @return
     */
    public static String contractNoEncrypt(String contractNo, int keepLength) {
        if (contractNo == null || contractNo.isEmpty() || contractNo.length() <= keepLength) {
            return contractNo;
        }
        StringBuilder sb = new StringBuilder(contractNo.length());
        sb.append(contractNo.substring(0, keepLength));
        for (int i = keepLength; i < contractNo.length(); i++) {
            sb.append('*');
        }
        return sb.toString();
    }

    /**
     * 电子邮箱脱敏
     *
     * @param email
     * @return
     */
    public static String emailDesensitization(String email) {
        // 电子邮箱隐藏@前面的3个字符
        if (StringUtils.isEmpty(email)) {
            return email;
        }
        String encrypt = email.replaceAll("(\\w+)\\w{3}@(\\w+)", "$1***@$2");
        if (email.equalsIgnoreCase(encrypt)) {
            encrypt = email.replaceAll("(\\w*)\\w{1}@(\\w+)", "$1*@$2");
        }
        return encrypt;
    }

    /**
     * 银行账号脱敏
     *
     * @param acctNo
     * @return
     */
    public static String acctNoDesensitization(String acctNo) {
        // 银行账号保留前六后四
        if (StringUtils.isNotEmpty(acctNo)) {
            String regex = "(\\w{6})(.*)(\\w{4})";
            Matcher m = Pattern.compile(regex).matcher(acctNo);
            if (m.find()) {
                String rep = m.group(2);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < rep.length(); i++) {
                    sb.append("*");
                }
                acctNo = acctNo.replaceAll(rep, sb.toString());
            }
        }
        return acctNo;
    }

    /**
     * 客户名称脱敏
     *
     * @param custName
     * @return
     */
    public static String custNameDesensitization(String custName) {
        // 规则说明：
        // 姓名：字符长度小于5位；企业名称：字符长度大于等于5位。
        // 姓名规则
        // 规则一：1个字则不脱敏，如"张"-->"张"
        // 规则二：2个字则脱敏第二个字，如"张三"-->"张*"
        // 规则三：3个字则脱敏第二个字，如"张三丰"-->"张*丰"
        // 规则四：4个字则脱敏中间两个字，如"易烊千玺"-->"易**玺"
        // 企业名称规则：
        // 从第4位开始隐藏，最多隐藏6位。

        if (StringUtils.isNotEmpty(custName)) {
            char[] chars = custName.toCharArray();
            if (chars.length < 5) {// 表示姓名
                if (chars.length > 1) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < chars.length - 2; i++) {
                        sb.append("*");
                    }
                    custName = custName.replaceAll(custName.substring(1, chars.length - 1), sb.toString());
                }
            } else {// 企业名称
                int start = 4;
                // 第一部分
                String str1 = custName.substring(0, start);
                // 第二部分
                String str2 = "";
                if (chars.length == 5) {
                    str2 = "*";
                } else if (chars.length == 6) {
                    str2 = "**";
                } else if (chars.length == 7) {
                    str2 = "***";
                } else if (chars.length == 8) {
                    str2 = "****";
                } else if (chars.length == 9) {
                    str2 = "*****";
                } else {
                    str2 = "******";
                }
                // 通过计算得到第三部分需要从第几个字符截取
                int subIndex = start + str2.length();
                // 第三部分
                String str3 = custName.substring(subIndex);

                StringBuffer sb = new StringBuffer();
                sb.append(str1);
                sb.append(str2);
                sb.append(str3);
                custName = sb.toString();
            }
        }
        return custName;
    }

    /**
     * 家庭地址脱敏
     *
     * @param address
     * @return
     */
    public static String addressDesensitization(String address) {
        // 规则说明：从第4位开始隐藏，隐藏8位。
        if (StringUtils.isNotEmpty(address)) {
            char[] chars = address.toCharArray();
            if (chars.length > 11) {// 由于需要从第4位开始，隐藏8位，因此数据长度必须大于11位
                // 获取第一部分内容
                String str1 = address.substring(0, 4);
                // 获取第二部分
                String str2 = "********";
                // 获取第三部分
                String str3 = address.substring(12);
                StringBuffer sb = new StringBuffer();
                sb.append(str1);
                sb.append(str2);
                sb.append(str3);
                address = sb.toString();
            }
        }
        return address;
    }


    public static void main(String[] args) {
        String id = "H2334566";
        String s = idEncrypt(id);
        System.out.println(s);
    }

}
