package com.yzj.jep.app.workflow.sms.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.workflow.sms.model.SmsMessage;
import com.yzj.jep.app.workflow.sms.webservice.pack.Fml32CpgwintuxAuthWSIn;
import com.yzj.jep.app.workflow.sms.webservice.pack.Fml32CpgwintuxAuthWSOut;
import com.yzj.jep.app.workflow.sms.webservice.sms.SMSPortType;
import com.yzj.jep.app.workflow.sms.webservice.sms.TuxedoWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @title: SmsSendUtil
 * @Author Whx
 * @Date: 2021/8/24 15:06
 * @Version 1.0
 * 不返回执行结果
 */
public class SmsSendUtil {

    private static Logger logger = LoggerFactory.getLogger(SmsSendUtil.class);

    public static void SingleSend(SmsMessage smsMessage) {
        if (StrUtil.isBlank(smsMessage.getPhone()) || StrUtil.isBlank(smsMessage.getContent())) {
            return;
        }
        URL url = null;
        try {
            url = new URL("http://192.168.1.252:9999/wsdl");//需要在本机hosts中配置实际地址指向域名 smscp.11185.cn
            singleSend(url, smsMessage);
        } catch (MalformedURLException e) {
            logger.error("短信发送异常，异常原因{}", e);
        } catch (Exception e) {
            logger.error("短信发送异常，异常原因{}", e);
        }
    }

    /**
     * 单个发送
     *
     * @param url
     * @param smsMessage
     */
    private static void singleSend(URL url, SmsMessage smsMessage) throws UnsupportedEncodingException {

        //调用服务
        TuxedoWebService tuxedoWebService = new TuxedoWebService(url);

        SMSPortType smshttpPort = tuxedoWebService.getSMSHTTPPort();
        //创建对象
        Fml32CpgwintuxAuthWSIn fml32CpgwintuxAuthWSIn = new Fml32CpgwintuxAuthWSIn();
        //接受手机号
        fml32CpgwintuxAuthWSIn.getFDestTermId().add(smsMessage.getPhone());
        //短信内容
        String content = smsMessage.getContent();
        String gbkContent = new String(content.getBytes(), "GBK");
        fml32CpgwintuxAuthWSIn.getFMsgContent().add(Base64.encode(gbkContent));////先转换成gbk字节,然后转换成base64字符串;
        /**CP业务代码*/
        fml32CpgwintuxAuthWSIn.getFCPServiceCode().add("请使用自己的cpServiceCode替换");
        /**企业用户名*/
        fml32CpgwintuxAuthWSIn.getFCPCorpId().add("请使用自己的CPCorpId替换");
        /**密码*/
        fml32CpgwintuxAuthWSIn.getFPassWd().add("请使用自己的FPassWd替换");
        /**消息类型 11 为普通消息*/
        fml32CpgwintuxAuthWSIn.getFMsgType().add((short) 11);
        /**源号码（CP填写扩展号码，比如2001）非必需字段*/
        fml32CpgwintuxAuthWSIn.getFSrcTermId().add("请用自己的FSrcTermId替换");
        /**计费号码类型，0—真实手机号 1—伪码*/
        fml32CpgwintuxAuthWSIn.getFChargeTermType().add((short) 0);
        /**目的手机号码个数，目前最多支持50个  单条发送设置为1*/
        fml32CpgwintuxAuthWSIn.getFDestTermCount().add((short) 1);
        /**目的手机号码类型，0—真实手机号 1—伪码*/
        fml32CpgwintuxAuthWSIn.getFDestTermType().add((short) 0);
        /**短消息的编码格式。
         目前字段FMsgFmt值只能是16。
         16：BASE64编码*/
        fml32CpgwintuxAuthWSIn.getFMsgFmt().add((short) 16);
        /**内容类型，0文本；1二进制需要BASE64编解码*/
        fml32CpgwintuxAuthWSIn.getFMsgContentType().add((short) 0);
        /**签名，如果FMsgFmt为16，则签名为Base64编码（如果内容为非GBK，必须先转GBK，然后Base64）*/
        String signature = smsMessage.getSignature();
        if (StrUtil.isNotBlank(signature)) {
            String gbkSignature = new String(signature.getBytes(), "GBK");
            fml32CpgwintuxAuthWSIn.getFSignature().add(Base64.encode(gbkSignature));//先转换成gbk字节,然后转换成base64字符串;
        }

        Fml32CpgwintuxAuthWSOut fml32CpgwintuxAuthWSOut = smshttpPort.cpgwintuxAuthWS(fml32CpgwintuxAuthWSIn);

        if (fml32CpgwintuxAuthWSOut != null) {
            for (int i = 0; i < fml32CpgwintuxAuthWSOut.getFResult().size(); i++) {
                String msgId = "";//msgId
                String result = fml32CpgwintuxAuthWSOut.getFResult().get(i);
                if (result != null && result.equals("0")) {
                    msgId = fml32CpgwintuxAuthWSOut.getFMsgId().get(i);
                }
                logger.info("短信调用返回结果result=[{}],msgId=[{}]", result, msgId);
                //输出结果 result + msgId
//                System.out.println(result + "," + msgId);
            }
        }
    }
}
