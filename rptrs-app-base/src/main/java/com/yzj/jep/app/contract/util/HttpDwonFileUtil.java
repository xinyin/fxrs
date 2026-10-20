package com.yzj.jep.app.contract.util;

import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.Message;

import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpDwonFileUtil {

    public static void  downLoadFromUrl1(String surl, JSONObject jsonObject,String fileName,String savePath) throws Exception{
            //创建连接
            URL url = new URL(surl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            // POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            String json = java.net.URLEncoder.encode(jsonObject.toString(), "utf-8");
            out.writeBytes(json);
            out.flush();
            out.close();
            // 读取响应
            //得到输入流
            InputStream inputStream = connection.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(savePath);
            if(!saveDir.exists()){
                saveDir.mkdir();
            }
            File file = new File(saveDir+File.separator+fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if(fos!=null){
                fos.close();
            }
            if(inputStream!=null){
                inputStream.close();
            }


            System.out.println("info:"+url+" download success");
    }

    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws Exception
     */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws Exception{
        System.out.println("****************to_downLoadFromUrl");
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        //conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //conn.setRequestProperty("lfwywxqyh_token",toekn);
        conn.setRequestMethod("POST");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }


        System.out.println("info:"+url+" download success");

    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static  byte[] readInputStream(InputStream inputStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void main(String[] args) {
        String urlStr = "http://localhost:20005/rptrs/contract/File";
        String fileName = "111.zip";
        String savePath = "E:\\testFile";

        JSONObject jsonObject = new JSONObject();
        Map<String,Object> tips = new HashMap<>();
        Message msg = null;
        Message resmsg = null;
        JSONObject json = null;
        String nonce = null;
        String secretkey = null;
        String appId = null;
        String timestamp = null;
        String msg1 = null;
        String sig = null;
        nonce =  UUID.randomUUID().toString();
        secretkey = "9VNbKeQQcWobU4Fg";
        appId = "cramcRelateTransactionSys";
        timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
        //timestamp = "1605859479480";
        tips.put("contractNo","XX2019000150");
        jsonObject.put("data",tips);
        String JSON = JSONObject.toJSONString(tips);

        /*校验签名*/
        String signString = nonce + JSON.trim() + secretkey;

        /*通过MD5进行加密*/
        sig = MD5FileUtil.getMD5String(signString);

        jsonObject.put("nonce",nonce);
        jsonObject.put("appId",appId);
        jsonObject.put("timestamp",timestamp);
        jsonObject.put("sig",sig);


        try {
            downLoadFromUrl1(urlStr,jsonObject,fileName,savePath);
        }catch(Exception e){
            e.printStackTrace();
        }


    }

}
