package Utils;
import Config.Config;
import java.util.HashMap;
public class Headers{
    private static final HashMap<String,String> headers = new HashMap<>();
    private static String Cookie;
    private static String Authorization;
    private static final Headers instance = new Headers();
    public static Headers getInstance(){
        return instance;
    }
    public HashMap<String,String> getHeaders(){
        headers.put("accept","application/json, text/plain, */*");
        headers.put("accept-encoding","gzip, deflate");
        headers.put("accept-language","zh-CN,zh;q=0.9");
        headers.put("cache-control","no-cache");
//        headers.put("content-length","71");
        headers.put("content-type","application/json;charset=UTF-8");
        headers.put("cookie",Cookie);
        headers.put("host", Config.HOST.split("://")[1]);
        headers.put("istoken","false");
        headers.put("origin",Config.HOST);
        headers.put("pragma","no-cache");
        headers.put("proxy-connection","keep-alive");
        headers.put("referer",Config.HOST + "/web/login?redirect=%2Fwelcome");
        headers.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");
        headers.put("authorization",Authorization);
        return headers;
    }
    public void setCookie(String cookie){
        Cookie = cookie;
    }
    public void setAuthorization(String authorization){
        Authorization = "Bearer " + authorization;
    }
}

