package com.wxl.test.baseCase;

import Config.Config;
import Utils.HttpMethods;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

public class Base {
    public static HttpMethods httpMethods;
    @BeforeSuite
    public void setUp(){
        Config.doConfig();
        httpMethods = new HttpMethods();
    }
    @AfterSuite
    public void tearDown(){
        if (httpMethods!=null) httpMethods.closeClient();
    }
//    @DataProvider
//    public Object[][] getData(){
//        return null;
//    }
}
