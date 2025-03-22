package com.wxl.test.testCases;

import com.wxl.test.Cases.Login;
import com.wxl.test.baseCase.Base;
import Utils.ExcelToObjectMapper;
import com.alibaba.fastjson.JSONObject;
import model.RequestDTO;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.List;

public class LoginTest extends Base {
    @DataProvider
    public Object[][] getData(){
        Object[][] objects;
        try {
            List<RequestDTO> dtos = ExcelToObjectMapper.readExcel("E:\\javacode\\AutoTestDemo\\AUTOTEST\\APITest\\src\\main\\resources\\datas\\testInfo.xlsx");
            // 将list转为二维数组
            objects = new Object[dtos.size()][1];
            for (int i = 0; i < dtos.size(); i++) {
                objects[i][0] = dtos.get(i);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return objects;
    }
    @Test(dataProvider="getData",timeOut = 3000 )
    public void loginTestSuccess(RequestDTO requestDTO){
        JSONObject responseJson = new Login(requestDTO, httpMethods).login();
        Assert.assertTrue(requestDTO.getExceptString().contains(responseJson.getString("msg")),"用例id" + requestDTO.getId().toString());
    }
}
