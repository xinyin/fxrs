package com.yzj.jep.app.fPaas.client;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.fPaaS1.core.factory.FaultFactory;
import com.fPaaS1.core.factory.IdGenerateFactory;
import com.fPaaS1.core.factory.MessageFactory;
import com.fPaaS1.presys.cp.httpsvr.run.Bootstrap;


/**
 * 这个类也是你们代码依赖的jar包中的
 * <p>
 * 模拟这个类在写一个controller，但是要看下配置文件中包扫描，能不能扫描到你新写的类
 * 这个类是原有的一个controller，不要它也可以，就是把配置文件中的内容改成你新写的就可以了，
 * 然后服务端你们要接受json，返回流文件，那就按照下面我写的注释来做就行，下面标注 **** 的注释
 *
 * @author hongwen.li
 */
@Controller
@RequestMapping("/contrl")
public class GatewaySvrController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GatewaySvrController.class);

    private static final String channelid = "gatewaysvr";

    private static final int STATE_DEAULT = 0;
    private static final int STATE_SVR = 1;
    private static final int STATE_FUR = 2;

    @RequestMapping(value = "frand", method = RequestMethod.POST)
    public void executePost(HttpServletRequest request, HttpServletResponse response) {

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("接收post请求成功!");
        }
        // 响应报文
        byte[] resPayload = null;
        // 编码格式
        String charset = request.getCharacterEncoding();
        int state = STATE_SVR;
        try {
            InputStream in = request.getInputStream();
            int length = request.getContentLength();
            byte[] buf = null;
            if (length == -1) {
                buf = new byte[256];
            } else {
                buf = new byte[length];
            }
            byte[] payload = new byte[0];
            while ((length = in.read(buf)) != -1) {
                int destPos = payload.length;
                payload = Arrays.copyOf(payload, destPos + length);
                System.arraycopy(buf, 0, payload, destPos, length);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("request before trans[{}]", new String(payload, charset));
            }
            //****.这个位置是接受完毕请求内容体，payload字段就是请求发来的内容，然后你们按照json解析
            //你们可以让传递过来的报文内容加上某个标志，如果有该标志则认为走你们的接受json返回流逻辑，
            //如果没有这个字段，则走原有的逻辑，即下面这个代码，如果走你们自己的逻辑在自己添加自己逻辑即可，即不走下面这些代码
            //这个类我只是添加了注释，其他代码所有内容全是原有的，你们自己看着适配，这个位置是接受json，
            //然后通过请求内容字段判断，
            //if(xxx(报文里的字段你们自己定义让人家传)){
            //		走你们自己逻辑，当你们自己逻辑走完后，返回流
            //		返回流  response.getOutputStream();拿到流了，拿到流后想怎么返回都行，
            //		我看了下原有代码，原有代码也是返回流，只不过内容是json，你们看你们需要自行更改就行
            //}else{
            //		就是下面这些原有代码了，你们看着适配
            //
            //}
            String strTmp = new String(payload, charset);
            if (StringUtils.isBlank(strTmp) && !strTmp.contains("_TRAN_CODE")) {
                state = STATE_DEAULT;
                return;
            }
            payload = strTmp.getBytes(charset);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("request before trans[{}]", new String(payload, charset));
            }
            Map<String, Object> headers = new HashMap<String, Object>();
            IFPaaS1Handler<Message> flowHandler = null;
            Message message = null;
            if (strTmp.contains("getRelTranSubmitZipFile")) {
                state = STATE_FUR;
                message = MessageFactory.create(IdGenerateFactory.generateId(), channelid, "JSON", charset,
                        MessageFactory.createSimpleMessage(headers, payload), FaultFactory.create(
                                com.fPaaS1.core.utils.Constants.ResponseCode.SUCCESS, "sucess"));
                flowHandler = Bootstrap.getApplicationContext().getBean("SI_gatewayfurFlow", IFPaaS1Handler.class);
            }else if(strTmp.contains("confirmRelTranApproval")){
                message = MessageFactory.create(IdGenerateFactory.generateId(), channelid, "JSON", charset,
                        MessageFactory.createSimpleMessage(headers, payload), FaultFactory.create(
                                com.fPaaS1.core.utils.Constants.ResponseCode.SUCCESS, "sucess"));
                flowHandler = Bootstrap.getApplicationContext().getBean("SI_gatewayCcaFlow", IFPaaS1Handler.class);
            } else {
                message = MessageFactory.create(IdGenerateFactory.generateId(), channelid, "JSON", charset,
                        MessageFactory.createSimpleMessage(headers, payload), FaultFactory.create(
                                com.fPaaS1.core.utils.Constants.ResponseCode.SUCCESS, "sucess"));
                flowHandler = Bootstrap.getApplicationContext().getBean("SI_gatewaysvrFlow", IFPaaS1Handler.class);
            }
            message = flowHandler.handle(message);

            Object obj = message.getTarget().getBodys();
            if (obj != null && obj instanceof byte[])
                resPayload = (byte[]) obj;
            else {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("响应结果为空 !!");
                }
            }

        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Error: ", e);
            }

            if (e.getMessage() != null)
                resPayload = e.getMessage().getBytes();
            else
                resPayload = "System Error !".getBytes();
        } finally {
            if (state == STATE_DEAULT) {
                LOGGER.debug("=========================服务正常启动=========================");
                response.setContentType("application/text; charset=".concat("UTF-8"));
                PrintWriter pw = null;
                try {
                    pw = response.getWriter();
                    pw.write("ok");
                    pw.flush();
                    pw.close();
                } catch (Exception e) {
                    LOGGER.error("Error: ", e);
                } finally {
                    if (null != pw) {
                        pw.close();
                    }
                }
            } else if (state == STATE_SVR) {
                response.setCharacterEncoding(charset);
                response.setContentType("application/json");
                response.setDateHeader("Expires", 0);
                OutputStream out = null;
                try {
                    out = response.getOutputStream();
                    if (resPayload != null) {
                        LOGGER.info("channel[{}] response:[{}]", "", new String(resPayload, charset));
                        out.write(resPayload);
                    }
                    out.flush();
                } catch (IOException e) {
                    LOGGER.error("Error: ", e);
                } finally {
                    if (out != null)
                        try {
                            out.close();
                        } catch (IOException e) {
                        }
                }
            } else if (state == STATE_FUR) {
                //FileInputStream fileInputStream = null;
                BufferedInputStream buffInputStream = null;
                OutputStream outputStream = null;
                String path = null;
                try {
                    JSONObject obj = JSONObject.parseObject(new String(resPayload, "UTF-8"));
                    String resultCode = obj.getJSONObject("SYSHEAD").getString("resultCode");
                    // 成功状态
                    if (resultCode.equals("0000")) {
                        byte[] decode = Base64Utils.decodeFromString(obj.getJSONObject("BODY").getString("zipFilePath"));
                        String zipFilePath = new String(decode);
                        String zipName = obj.getJSONObject("BODY").getString("zipName");
                        //文件地址
                        path = zipFilePath + File.separator + zipName + ".zip";
                        // 写出zip流
                        /*fileInputStream = new FileInputStream(path);
                        byte[] buffer = new byte[512];
                        int length = 0;
                        while ((length = fileInputStream.read(buffer)) != -1) {
                            response.getOutputStream().write(buffer, 0, length);
                        }*/
                        //response.getOutputStream().flush();

                        buffInputStream = new BufferedInputStream(new FileInputStream(path));
                        outputStream = response.getOutputStream();
                        byte[] buff = new byte[1024 * 1024]; //如果是稍微大的文件，这里配置的大一些
                        int len = 0;
                        while ((len = buffInputStream.read(buff)) > 0) {
                            //把文件流写入到response的输出流中，供请求端请求
                            outputStream.write(buff, 0, len);
                            outputStream.flush();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (buffInputStream != null) {
                            buffInputStream.close();
                        }
                    } catch (IOException e) {
                    }
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                    }
                    if (path != null) {
                        File file = new File(path);
                        File parentFile = file.getParentFile();
                        delete(parentFile);
                    }
                }
            }
        }
    }

    /**
     * 级联删除文件夹及其子文件
     *
     * @param file
     */
    public static void delete(File file) {
        try {
            //判断文件不为null或文件目录存在
            if (file == null || !file.exists()) {
                return;
            }
            //取得这个目录下的所有子文件对象
            File[] files = file.listFiles();
            //遍历该目录下的文件对象
            for (File f : files) {
                //判断子目录是否存在子目录,如果是文件则删除
                if (f.isDirectory()) {
                    delete(f);
                } else {
                    f.delete();
                }
            }
            //删除空文件夹  for循环已经把上一层节点的目录清空。
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
