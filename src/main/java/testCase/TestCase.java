package testCase;
import Config.Config;
import Utils.ExcelToObjectMapper;
import Utils.Headers;
import Utils.HttpMethods;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import model.RequestDTO;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Objects;

public class TestCase{

    public static HttpMethods httpMethods;
    @BeforeSuite
    public void doConfig(){
        Config.doConfig();
        getHttpMethods();
    }
    @AfterSuite
    public void closeClient(){
        httpMethods.closeClient();
    }
    public void getHttpMethods(){
        try {
            httpMethods = new HttpMethods();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @DataProvider(name = "requestData")
    public Object[][] requestData() throws Exception {
        List<RequestDTO> requestDTOS = ExcelToObjectMapper.readExcel(Config.excelPath);
        Object[][] data = new Object[requestDTOS.size()][1];
        for (int i = 0; i < requestDTOS.size(); i++) {
            data[i][0] = requestDTOS.get(i);
        }
        return data;
    }
    @Test(dataProvider = "requestData")
    public void doRequest(RequestDTO requestDTO) {
        JSONObject jsonObject = JSONObject.parseObject(requestDTO.getParameters());
        JSONObject resp;
        String parameter = null;
        try (CloseableHttpResponse response = httpMethods.DoPostReturnResponse(Config.HOST + requestDTO.getUrl(), jsonObject)) {
            resp = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            if (!requestDTO.getExtractParameter().equals("")){
                parameter = extractParameter(requestDTO, resp);
            }
            if (requestDTO.getOwnModel().equals("登录")){
                Headers.getInstance().setCookie(parameter);
                Headers.getInstance().setAuthorization(parameter);
                Assert.assertEquals(resp.get("code"), 200);
            }else {
                JSONAssert.assertEquals(requestDTO.getExceptString(), resp.toJSONString(), false);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String extractParameter(RequestDTO requestDTO,JSONObject jsonObject) {
        try {
            // 1. 编译JSONPath表达式
            JSONPath jsonPath = JSONPath.compile(requestDTO.getExtractParameter());

            // 2. 执行路径查询
            Object result = jsonPath.eval(jsonObject);

            // 3. 处理不同类型的返回值
            if (result == null) {
                return null;
            } else if (result instanceof String) {
                return (String) result;
            } else if (result instanceof Number) {
                return String.valueOf(result);
            } else if (result instanceof Boolean) {
                return Boolean.toString((Boolean) result);
            } else {
                // 处理复杂对象类型，转换为JSON字符串
                return JSONObject.toJSONString(result);
            }
        } catch (Exception e) {
            // 4. 异常处理（可根据需要记录日志或抛出自定义异常）
            System.err.println("JSONPath提取失败: " + e.getMessage());
            throw new IllegalArgumentException("无效的JSONPath表达式: " + requestDTO.getExtractParameter(), e);
        }
    }
}
