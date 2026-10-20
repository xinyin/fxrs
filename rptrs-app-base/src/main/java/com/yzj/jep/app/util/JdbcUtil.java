package com.yzj.jep.app.util;


import com.yzj.jep.commons.ResourceUtil;

import java.sql.*;
import java.util.Properties;


public class JdbcUtil {
    public static Connection getConnectUseProp() {
        Connection conn = null;
        Properties info = new Properties();
        try {
            info.load(ResourceUtil.getResourceAsStream("jep-jdbc.properties"));
            //加载驱动。
            Class.forName(info.get("driver").toString());
            //创建连接。
            conn = DriverManager.getConnection(info.get("url").toString(), info);
            System.out.println("Connection succeed!");
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
        return conn;
    };
    /**
     * 释放资源
     * @param rs
     * @param st
     * @param conn
     */
    public static void close(ResultSet rs, PreparedStatement st, Connection conn){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(st != null){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
