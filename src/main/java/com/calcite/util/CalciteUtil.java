package com.calcite.util;

import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class CalciteUtil {
    /**
     * 根据给定的 model.json 文件获取 Connection
     *
     * @param filePath
     * @return
     */
    public static Connection getConnect(String filePath) {
        Connection connection = null;
        try {
            URL url = CalciteUtil.class.getResource(filePath);
            String str = URLDecoder.decode(url.toString(), "UTF-8");
            Properties info = new Properties();
            info.put("model", str.replace("file:", ""));
            connection = DriverManager.getConnection("jdbc:calcite:", info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
