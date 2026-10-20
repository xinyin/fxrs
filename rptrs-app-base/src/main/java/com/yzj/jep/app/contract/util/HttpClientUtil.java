package com.yzj.jep.app.contract.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.contract.model.ApiRequest;
import com.yzj.jep.app.contract.model.ApiResponse;
import com.yzj.jep.app.ins.model.Email;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 发送http请求携带json参数把返回zip文件流落地
     *
     * @param request 请求报文内容
     * @param url     接口请求地址
     * @param path    文件保存路径
     */
    public static Map<String, String> httpPostByte(String request, String url, String path) {
        /**
         * "code","SUCCESS" | "FAIL"  是否成功
         *  "path"                    zip文件路径
         *  "name"                    zip包名
         */
        Map<String, String> result = new HashMap<>();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            // 设置 连接超时、 请求超时 、 读取超时  毫秒
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(30000)
                    .setSocketTimeout(30000)
                    .setConnectionRequestTimeout(30000)
                    .build();
            post.setConfig(config);
            // 构造消息头
            post.addHeader("Content-type", "application/json; charset=utf-8");
            post.setHeader("Connection", "Close");
            post.setHeader("Accept", "application/json");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");

            // 构建消息实体
            StringEntity entity = new StringEntity(request, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
            //接受返回请求
            HttpResponse response = httpClient.execute(post);
            //文件落到本地
            String name = ContractUtil.getUUID();
            String zipPath = path + File.separator + name + ".zip";
            File file = new File(zipPath);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                inputStream = response.getEntity().getContent();
                IOUtils.copy(inputStream, outputStream);
                //返回结果
                result.put("code", "SUCCESS");
                result.put("path", path);
                result.put("name", name);
            }
        } catch (Exception e) {
            logger.error("an exception occurred while the HTTP request was fetching the ZIP file:{}", e);
            result.put("code", "FAIL");
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(httpClient);
        }
        return result;
    }

    /**
     * 发送http请求携带json参数把返回json格式数据
     *
     * @param request
     * @param url
     * @return
     */
    public static ApiResponse httpPostJson(String request, String url) {

        CloseableHttpClient httpClient = null;
        ApiResponse apiResponse = new ApiResponse();
        try {
            httpClient = new DefaultHttpClient();

            HttpPost post = new HttpPost(url);
            // 设置 连接超时、 请求超时 、 读取超时  毫秒
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(30000)
                    .setSocketTimeout(30000)
                    .setConnectionRequestTimeout(30000)
                    .build();
            post.setConfig(config);
            // 构造消息头
            post.addHeader("Content-type", "application/json; charset=utf-8");
            post.setHeader("Connection", "Close");
            post.setHeader("Accept", "application/json");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");

            // 构建消息实体
            StringEntity entity = new StringEntity(request, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
            //接受返回请求
            HttpResponse response = httpClient.execute(post);
            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String resonseString = EntityUtils.toString(responseEntity);

                JSONObject jsonObject = JSON.parseObject(resonseString);
                System.out.println("================="+jsonObject);
                String str  = jsonObject.getString("SYSHEAD");
                ApiResponse data = JSON.parseObject(str, ApiResponse.class);
                System.out.println("================="+data);
                return data;
                //return JSONObject.parseObject(resonseString, ApiResponse.class);
            }else {
                apiResponse.setResultCode(String.valueOf(statusCode));
                apiResponse.setMsg("请求服务失败！");
            }
        } catch (Exception e) {
            apiResponse.setResultCode("9999");
            apiResponse.setMsg("请求服务异常");
            throw new RuntimeException  ("gets the json return data exception:{}", e);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }
        return apiResponse;
    }


    public static Email httpPostJsonEmail(String request, String url) {

        CloseableHttpClient httpClient = null;
        Email emailList = new Email();
        try {
            httpClient = new DefaultHttpClient();

            HttpPost post = new HttpPost(url);
            // 设置 连接超时、 请求超时 、 读取超时  毫秒
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(30000)
                    .setSocketTimeout(30000)
                    .setConnectionRequestTimeout(30000)
                    .build();
            post.setConfig(config);
            // 构造消息头
            post.addHeader("Content-type", "application/json; charset=utf-8");
            post.setHeader("Connection", "Close");
            post.setHeader("Accept", "application/json");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");

            // 构建消息实体
            StringEntity entity = new StringEntity(request, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
            //接受返回请求
            logger.info("HttpPost:{}",post);
            HttpResponse response = httpClient.execute(post);
            logger.info("邮件发送返回:{}",response);
            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String resonseString = EntityUtils.toString(responseEntity);
                return JSONObject.parseObject(resonseString, Email.class);
            }else {
                emailList.setResultCode(String.valueOf(statusCode));
                emailList.setMsg("请求服务失败！");
            }
        } catch (Exception e) {
            emailList.setResultCode("9999");
            emailList.setMsg("请求服务异常");
            logger.error("gets the json return data exception:{}", e);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }
        return emailList;
    }


    /**
     * 获取在线编辑完成的文件
     *
     * @param request
     * @param url
     * @param path
     * @return
     */
    public static boolean httpGetFile(ApiRequest request, String name, String url, String path) {
        Map<String, String> result = new HashMap<>();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            // 设置 连接超时、 请求超时 、 读取超时  毫秒
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(30000)
                    .setSocketTimeout(30000)
                    .setConnectionRequestTimeout(30000)
                    .build();
            post.setConfig(config);
            // 构造消息头
            post.addHeader("Content-type", "application/json; charset=utf-8");
            post.setHeader("Connection", "Close");
            post.setHeader("Accept", "application/json");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");

            // 构建消息实体
            String message = JSONObject.toJSONString(request);
            StringEntity entity = new StringEntity(message, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
            //接受返回请求
            HttpResponse response = httpClient.execute(post);
            //文件落到本地
            String filePath = path + File.separator + name;
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                inputStream = response.getEntity().getContent();
                IOUtils.copy(inputStream, outputStream);
                return true;
            }
        } catch (Exception e) {
            logger.error("failed to get file:{}", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(httpClient);
        }
        return false;
    }

    /**
     * 获取在线编辑页面
     *
     * @param request
     * @param url
     * @return
     */
    public static String httpGetHtml(String request, String url) {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = new DefaultHttpClient();

            HttpPost post = new HttpPost(url);
            // 设置 连接超时、 请求超时 、 读取超时  毫秒
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(30000)
                    .setSocketTimeout(30000)
                    .setConnectionRequestTimeout(30000)
                    .build();
            post.setConfig(config);
            // 构造消息头
            post.addHeader("Content-type", "application/json; charset=utf-8");
            post.setHeader("Connection", "Close");
            post.setHeader("Accept", "application/json");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");

            // 构建消息实体
            StringEntity entity = new StringEntity(request, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
            //接受返回请求
            HttpResponse response = httpClient.execute(post);
            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                return EntityUtils.toString(responseEntity);
            }
        } catch (Exception e) {
            logger.error("gets the json return data exception:{}", e);
        } finally {
            IOUtils.closeQuietly(httpClient);
        }
        return null;
    }


}
