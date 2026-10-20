package com.yzj.jep.app.fPaas.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.AppCtxFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.fPaaS1.core.factory.FaultFactory;
import com.fPaaS1.core.utils.Constants;
import com.fPaaS1.core.utils.PacketUtil;
import com.fPaaS1.exception.FPaaS1Exception;
import com.fPaaS1.exception.SysErrCode;
import com.fPaaS1.presys.cp.xml.definition.ChannelBeanDefinition;
import com.fPaaS1.presys.cp.xml.definition.ParamBeanDefinition;
import com.fPaaS1.utils.constants.NetConstants;
import com.fPaaS1.utils.net.ExceptionUtils;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 支持post和get得client
 *
 * @author
 */
public class HttpClientCommClientDownFileHandler extends AppCtxFPaaS1Handler {

    private final static Logger LOG = LoggerFactory
            .getLogger(HttpClientCommClientDownFileHandler.class);

    protected static final int OK = 200;

    protected static final String HTTPS = "https";

    public Message handle(Message m) throws Exception {
        byte[] bytes1 = (byte[]) m.getTarget().getBodys();
        JSON json = (JSON) JSONObject.parse(bytes1);
        Map<String, Object> map = (Map<String, Object>) json;
        Map<String, Object> tips = new HashMap<>();
        String reqUrl = PacketUtil.getStringValueOfTargetHeadersByKey(m,
                Constants.Channel._REQ_URL);
        ChannelBeanDefinition def = (ChannelBeanDefinition) getApplicationContext()
                .getBean(m.getChannel());
        String uri = def.getUrlsBeanDefinition().getUrlBeanDefinitionList()
                .get(0).getValue();
        if (StringUtils.isNotBlank(reqUrl)) {
            uri = uri.concat(reqUrl);
        }
        boolean bool = false;
        List<ParamBeanDefinition> paramBeanDefinitionList = def
                .getParamsBeanDefinition().getParamBeanDefinitionList();
        int connecttimeout = 10000;
        int sotimeout = 30000;
        for (ParamBeanDefinition paramBeanDefinition : paramBeanDefinitionList) {
            if (NetConstants.CONNECTTIMEOUT.equals(paramBeanDefinition
                    .getName())) {
                connecttimeout = Integer.parseInt(paramBeanDefinition
                        .getValue());
                bool = true;
            } else if (NetConstants.SOTIMEOUT.equals(paramBeanDefinition
                    .getName())) {
                sotimeout = Integer.parseInt(paramBeanDefinition.getValue());
            }
        }
        if (!bool) {
            throw new IllegalArgumentException("请配置tcp客户端连接参数[".concat(
                    NetConstants.CONNECTTIMEOUT).concat("]"));
        }
        // 获取请求类型
        String methodType = null;
        methodType = map.get("methodType").toString();
        /*判断请求类型是否为空*/
        if (StringUtils.isBlank(methodType)) {
            throw new IllegalArgumentException("请在Header中配置METHOD_TYPE类型！");
        }

        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = createHttpClient(uri);
            boolean getMethodFlag = false;
            HttpGet httpGet = null;
            HttpPost httpPost = null;
            /*判断请求类型是否是get请求*/
            if (methodType.startsWith(HttpConstans.METHOD_GET)) {
                getMethodFlag = true;
                if (HttpConstans.GET_WITH_PARM.equals(methodType)) {
                    // 带参数的get请求
                    URIBuilder uriBuilder = new URIBuilder(uri);
                    URI getUri = setParm(uriBuilder, m);
                    httpGet = new HttpGet(getUri);
                } else if (HttpConstans.GET_WITHOUT_PARM.equals(methodType)) {
                    // 不带参数 get请求
                    httpGet = new HttpGet(uri);
                } else {
                    throw new IllegalArgumentException(
                            "METHOD_TYPE配置有误，请参考HttpConstans配置！");
                }
            } else if (methodType.startsWith(HttpConstans.METHOD_POST)) {
                /*请求类型为post请求*/
                httpPost = new HttpPost(uri);
            } else {
                throw new IllegalArgumentException(
                        "METHOD_TYPE配置有误，请参考HttpConstans配置！");
            }

            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(connecttimeout)
                    .setSocketTimeout(sotimeout).build();
            if (getMethodFlag) {
                httpGet.setConfig(config);
                response = httpclient.execute(httpGet);
            } else {
                httpPost.setConfig(config);
                /*获取body里面的信息*/
                byte[] bytes = (byte[]) m.getTarget().getBodys();
                JSONObject json1 = (JSONObject) JSONObject.parse(bytes);
                /*不是第三方请求体的内容 移除*/
                json1.remove("nonce");
                json1.remove("appId");
                json1.remove("timestamp");
                json1.remove("sig");
                json1.remove("methodType");
                StringEntity entity = new StringEntity(json1.toJSONString(),
                        Charsets.UTF_8);
                httpPost.setEntity(entity);
                /*获取原有headers里面的信息*/
                Map<String, Object> header = m.getTarget().getHeaders();
                /*重新封装headers信息 存入map集合*/
                tips.put("_IS_CLIENT", header.get("_IS_CLIENT").toString());
                tips.put("PRESYS", header.get("PRESYS").toString());
                tips.put("_TRAN_CODE", header.get("_TRAN_CODE").toString());
                tips.put("nonce", map.get("nonce").toString());
                tips.put("appId", map.get("appId").toString());
                tips.put("timestamp", map.get("timestamp").toString());
                tips.put("sig", map.get("sig").toString());
                tips.put("Content-Type", "application/json;charset=".concat("UTF-8"));
                /*将封装好的headers信息，放入message里*/
                m.getTarget().setHeaders(tips);
                httpPost.setHeader("Content-Type",
                        "application/json;charset=".concat("UTF-8"));
                /*执行调用第三方请求接口*/
                response = httpclient.execute(httpPost);
            }
            int statusCode = response.getStatusLine().getStatusCode();

            HttpEntity resEntity = response.getEntity();
            if (OK == statusCode) {
                InputStream inputStream = resEntity.getContent();

                receiveHandle(resEntity, m);
            } else {
                throw new FPaaS1Exception(FaultFactory.create(
                        String.valueOf(statusCode), ""));
            }
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            m.getFault().setCodeAll(SysErrCode.SYS003);
            m.getFault().setMsgAll(
                    ExceptionUtils.convertCN(
                            e,
                            m.getChannel(),
                            "渠道[".concat(m.getChannel()).concat("]")
                                    .concat(SysErrCode.SYS003)));
        } finally {
            if (null != response) {
                response.close();
            }
            if (null != httpclient) {
                httpclient.close();
            }
        }
        return m;
    }

    protected void receiveHandle(HttpEntity resEntity, Message m)
            throws Exception {
        byte[] bytes = EntityUtils.toByteArray(resEntity);
        m.getTarget().setBody(bytes);
    }

    protected URI setParm(URIBuilder uriBuilder, Message m) throws Exception {
        byte[] bytes = (byte[]) m.getTarget().getBodys();
        JSONObject parse = (JSONObject) JSONObject.parse(bytes);
        Set<Entry<String, Object>> entrySet = parse.entrySet();
        for (Entry<String, Object> entry : entrySet) {
            uriBuilder.setParameter(entry.getKey(), (String) entry.getValue());
        }
        return uriBuilder.build();
    }

    protected CloseableHttpClient createHttpClient(String uri) throws Exception {
        CloseableHttpClient chc = null;
        if (uri.indexOf(HTTPS) >= 0) {
            SSLContext sslcontext = new SSLContextBuilder().loadTrustMaterial(
                    null, new TrustStrategy() {
                        public boolean isTrusted(X509Certificate[] chain,
                                                 String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            chc = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } else {
            chc = HttpClients.createDefault();
        }
        return chc;
    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws Exception
     */
    public boolean readInputStream(InputStream inputStream,String savePath,String fileName) throws Exception {
        boolean isDownSuccess = false;
        ByteArrayOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] buffer = new byte[1024];
            int len = 0;

            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            //获取自己数组
            byte[] getData = bos.toByteArray();

            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }

            File file = new File(saveDir + File.separator + fileName);
            fos = new FileOutputStream(file);
            fos.write(getData);
            isDownSuccess = true;

        }finally {
            if(bos != null){
                bos.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return isDownSuccess;
    }

}