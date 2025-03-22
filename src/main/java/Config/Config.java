package Config;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static String HOST;
    public static String LOGIN_URL;
    public static String codeUrl;
    public static boolean isConfig;
    public static void doConfig(){
        if (!isConfig) {
            try(InputStream resourceAsStream = Config.class.getClassLoader().getResourceAsStream("configs/config.properties")){
                Properties properties = new Properties();
                properties.load(resourceAsStream);
                HOST = properties.getProperty("HOST");
                LOGIN_URL = HOST + properties.getProperty("loginUrl");
                codeUrl = HOST + properties.getProperty("codeUrl");
                isConfig = true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
