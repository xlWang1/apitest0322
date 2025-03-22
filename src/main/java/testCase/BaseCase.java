package testCase;
import Utils.HttpMethods;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import Config.Config;
public class BaseCase {
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
}
