package com.yzj.jep.app.util;


import javax.xml.transform.Result;

public class GuuidUtil {
    private static long machineId = 0;
    private static long datacenterId = 0;
    /**
     * 单例模式创建学法算法对象
     * */
    private enum SnowFlakeSingleton{
        Singleton;
        private SnowFlake snowFlake;
        SnowFlakeSingleton(){
            snowFlake = new SnowFlake(datacenterId,machineId);
        }
        public SnowFlake getInstance(){
            return snowFlake;
        }
    }
    public static String getUUID(){
        Long l = SnowFlakeSingleton.Singleton.getInstance().nextId();
        String result =String.valueOf(l);
        return result;
    }
}
