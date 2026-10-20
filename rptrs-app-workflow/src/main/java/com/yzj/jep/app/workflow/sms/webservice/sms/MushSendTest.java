package com.yzj.jep.app.workflow.sms.webservice.sms;


import com.yzj.jep.app.workflow.sms.webservice.pack.Fml32CpgwintuxAuthWSIn;
import com.yzj.jep.app.workflow.sms.webservice.pack.Fml32CpgwintuxAuthWSOut;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 群发测试类
 */
public class MushSendTest {

    public static void main(String[] args) {

        URL url = null;
        try {
            url = new URL("请用实际地址替换");//需要在本机hosts中配置实际地址指向域名 smscp.11185.cn
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //多个发送
        mushSend(url);
    }

    /**
     * 多个发送
     */
    private static void mushSend(URL url) {

        //调用服务
        TuxedoWebService tuxedoWebService = new TuxedoWebService(url);

        SMSPortType smshttpPort = tuxedoWebService.getSMSHTTPPort();
        //创建对象
        Fml32CpgwintuxAuthWSIn fml32CpgwintuxAuthWSIn = new Fml32CpgwintuxAuthWSIn();

        //要发送手机号集合
        List<String> phoneList = new ArrayList<>();
        //将手机号添加到集合中
        phoneList.add("要接收的手机号");
        //通过手机号集合发送短信
        for (String phone : phoneList) {
            //接受手机号
            fml32CpgwintuxAuthWSIn.getFDestTermId().add(phone);
            //短信内容
            fml32CpgwintuxAuthWSIn.getFMsgContent().add("请使用自己的短信内容替换");//先转换成gbk字节,然后转换成base64字符串;

            fml32CpgwintuxAuthWSIn.getFCPServiceCode().add("请使用自己的cpServiceCode替换");
            fml32CpgwintuxAuthWSIn.getFCPCorpId().add("请使用自己的CPCorpId替换");
            fml32CpgwintuxAuthWSIn.getFPassWd().add("请使用自己的FPassWd替换");
            fml32CpgwintuxAuthWSIn.getFMsgType().add((short) 11);
            fml32CpgwintuxAuthWSIn.getFSrcTermId().add("请用自己的FSrcTermId替换");
            fml32CpgwintuxAuthWSIn.getFChargeTermType().add((short) 0);
            fml32CpgwintuxAuthWSIn.getFDestTermCount().add((short) 1);
            fml32CpgwintuxAuthWSIn.getFDestTermType().add((short) 0);
            fml32CpgwintuxAuthWSIn.getFMsgFmt().add((short) 16);
            fml32CpgwintuxAuthWSIn.getFMsgContentType().add((short) 0);
            fml32CpgwintuxAuthWSIn.getFSignature().add("请使用自己的签名替换");//先转换成gbk字节,然后转换成base64字符串;
        }
        //调用获取结果
        Fml32CpgwintuxAuthWSOut fml32CpgwintuxAuthWSOut = smshttpPort.cpgwintuxAuthWS(fml32CpgwintuxAuthWSIn);

        if (fml32CpgwintuxAuthWSOut != null) {
            for (int i = 0; i < fml32CpgwintuxAuthWSOut.getFResult().size(); i++) {
                String msgId = "";//msgId
                String result = fml32CpgwintuxAuthWSOut.getFResult().get(i);
                if (result != null && result.equals("0")) {
                    msgId = fml32CpgwintuxAuthWSOut.getFMsgId().get(i);
                }
                //输出结果 result + msgId
                System.out.println(result + "," + msgId);
            }
        }
    }
}
