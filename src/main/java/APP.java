import Config.Config;
import Utils.HttpMethods;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.util.EntityUtils;

import java.util.HashMap;

public class APP {
    public static void main(String[] args) {
        HashMap<String,Object> params = new HashMap<>();
        Config.doConfig();
        params.put("username","怀柔区管理员");
        params.put("password","jiRBGA+3+EKa+V7RcdAOXg==");
        String uri = Config.HOST + "/zkserver_api/login";
        HttpMethods httpMethods = new HttpMethods();
        try (CloseableHttpResponse response = httpMethods.DoPostReturnResponse(uri, params)) {
            JSONObject parse = JSON.parseObject(EntityUtils.toString(response.getEntity()));
            System.out.println(parse.get("msg"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
