package com.yzj.jep.app.workflow.sms.webservice.sms;


import com.yzj.jep.app.workflow.sms.webservice.pack.Fml32CpgwintuxAuthWSIn;
import com.yzj.jep.app.workflow.sms.webservice.pack.Fml32CpgwintuxAuthWSOut;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 单个发送测试类
 */
public class SingleSendTest {

    public static void main(String[] args) {

        URL url = null;
        try {
            url = new URL("http://smscp.11185.cn/SMS");//需要在本机hosts中配置实际地址指向域名 smscp.11185.cn
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //单个发送
        singleSend(url);
    }

    /**
     * 单个发送
     *
     * @param url
     */
    private static void singleSend(URL url) {

        //调用服务
        TuxedoWebService tuxedoWebService = new TuxedoWebService(url);

        SMSPortType smshttpPort = tuxedoWebService.getSMSHTTPPort();
        //创建对象
        Fml32CpgwintuxAuthWSIn fml32CpgwintuxAuthWSIn = new Fml32CpgwintuxAuthWSIn();
        //接受手机号
        fml32CpgwintuxAuthWSIn.getFDestTermId().add("15033889803");
        //短信内容
        fml32CpgwintuxAuthWSIn.getFMsgContent().add("请使用自己的短信内容替换");////先转换成gbk字节,然后转换成base64字符串;

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
