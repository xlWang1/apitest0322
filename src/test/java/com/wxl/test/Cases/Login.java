package com.wxl.test.Cases;

import Config.Config;
import Utils.HttpMethods;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import model.RequestDTO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import top.gcszhn.d4ocr.OCREngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Login {
    public RequestDTO requestDTO;
    public HttpMethods httpMethods;

    public Login(RequestDTO requestDTO, HttpMethods httpMethods) {
        this.requestDTO = requestDTO;
        this.httpMethods = httpMethods;
    }
    public Map<String,Object> testGetCaptchaSuccess(){
        long currentTimeMillis = System.currentTimeMillis();
        Map<String,Object> randomStr = new HashMap<>();
        randomStr.put("randomStr",currentTimeMillis + "test");
        try (CloseableHttpResponse response = httpMethods.DoGetReturnResponse(Config.codeUrl, randomStr)) {
            BufferedImage image = ImageIO.read(response.getEntity().getContent());
            OCREngine engine = OCREngine.instance();
            String predict = engine.recognize(image);
            char[] chars = predict.toCharArray();
            int num1 = Character.getNumericValue(chars[0]);
            int num2 = Character.getNumericValue(chars[2]);
            String fuHao = String.valueOf(chars[1]);
            int code;
            code = switch (fuHao) {
                case "+", "4" -> num1 + num2;
                case "-" -> num1 - num2;
                case "x", "X" -> num1 * num2;
                default -> 0;
            };
            randomStr.put("code",code);

        }catch (Exception e){
            e.printStackTrace();

        }
        return randomStr;
    }
    public JSONObject login(){
        JSONObject parseObject = null;
        Map<String, Object> map = testGetCaptchaSuccess();
        int code = (int) map.get("code");
        HashMap<String, Object> params = new HashMap<>();
        String requestMethod = requestDTO.getRequestMethod().toUpperCase();
        String contentType = requestDTO.getContentType();
        String url = Config.HOST + requestDTO.getUrl();
        String parameters = requestDTO.getParameters();
        HashMap<String, Object> parametersMap = null;
        if (parameters != null) {
            parametersMap = JSONObject.parseObject(parameters, new TypeReference<HashMap<String, Object>>() {});
            parametersMap.put("code", code);
            parametersMap.put("randomStr", map.get("randomStr"));
        }
        CloseableHttpResponse closeableHttpResponse = null;
        switch (requestMethod) {
            case "GET" -> {
                closeableHttpResponse = httpMethods.DoGetReturnResponse(url, parametersMap);
            }
            case "POST" -> closeableHttpResponse = httpMethods.DoPostReturnResponse(url, parametersMap);
            default -> System.out.println("请求方法错误");
        }
        try {
            if (closeableHttpResponse != null){
            HttpEntity entity = closeableHttpResponse.getEntity();
                parseObject = JSONObject.parseObject(EntityUtils.toString(entity));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parseObject;
    }
}
