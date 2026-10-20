package com.yzj.jep.app.workflow.sms.webservice.sms;


import com.yzj.jep.app.workflow.sms.webservice.pack.Fml32CpgwouttuxAuthWSIn;
import com.yzj.jep.app.workflow.sms.webservice.pack.Fml32CpgwouttuxAuthWSOut;

import java.net.MalformedURLException;
import java.net.URL;

public class GetReportTest {

    public static void main(String[] args) {
        URL url = null;
        try {
            url = new URL("请用实际地址替换");//需要在本机hosts中配置实际地址指向域名 smscp.11185.cn
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //获取状态报告
        getReport(url);
    }


    /**
     * 获取状态报告
     *
     * @param url
     */
    private static void getReport(URL url) {

        //调用服务
        TuxedoWebService tuxedoWebService = new TuxedoWebService(url);

        SMSPortType smshttpPort = tuxedoWebService.getSMSHTTPPort();
        //创建对象
        Fml32CpgwouttuxAuthWSIn fml32CpgwintuxAuthWSIn = new Fml32CpgwouttuxAuthWSIn();
        //将要传的参数
        fml32CpgwintuxAuthWSIn.setFCPCorpId("请使用自己的CPCorpId替换");
        fml32CpgwintuxAuthWSIn.setFPassWd("请使用自己的FPassWd替换");

        Fml32CpgwouttuxAuthWSOut fml32CpgwintuxAuthWSOut = smshttpPort.cpgwouttuxAuthWS(fml32CpgwintuxAuthWSIn);

        if (fml32CpgwintuxAuthWSOut != null) {

            for (int i = 0; i < fml32CpgwintuxAuthWSOut.getFResult().size(); i++) {

                String result = fml32CpgwintuxAuthWSOut.getFResult().get(i);
                String msgType = "";
                String msgId = "";
                String mtMsgId = "";
                String destTerMid = "";
                String stat = "";
                String statDesc = "";

                if (result != null && result.equals("0")) {
                    msgType = fml32CpgwintuxAuthWSOut.getFMsgType().get(i).toString();
                    msgId = fml32CpgwintuxAuthWSOut.getFMsgId().get(i);
                    mtMsgId = fml32CpgwintuxAuthWSOut.getFMTMsgId().get(i);
                    destTerMid = fml32CpgwintuxAuthWSOut.getFDestTermId().get(i);
                    stat = fml32CpgwintuxAuthWSOut.getFStat().get(i);
                    statDesc = fml32CpgwintuxAuthWSOut.getFStatDesc().get(i);
                }
                //输出结果
                System.out.println("result : " + result + "," + "msgId : " + msgId + "," + "msgType : " + msgType + "," + "mtMsgId : " + mtMsgId + "," +
                        "destTerMid : " + destTerMid + "," + "stat : " + stat + "," + "statDesc : " + statDesc);
            }
        }
    }
}
