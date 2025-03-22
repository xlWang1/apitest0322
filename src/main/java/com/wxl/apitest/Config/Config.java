package com.wxl.apitest.Config;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static String HOST;
    public static boolean isConfig;
    public static String excelPath;
    public static void doConfig(){
        if (!isConfig) {
            try(InputStream resourceAsStream = Config.class.getClassLoader().getResourceAsStream("configs/config.properties")){
                Properties properties = new Properties();
                properties.load(resourceAsStream);
                HOST = properties.getProperty("HOST");
                excelPath = properties.getProperty("excelPath");
                isConfig = true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        doConfig();
        System.out.println(HOST);
    }
}
