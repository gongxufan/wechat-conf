package cn.com.egova.wx.util;

import cn.com.egova.wx.web.bean.SysConfig;
import cn.com.egova.wx.web.bean.SysInitBean;
import cn.com.egova.wx.web.entity.TrustManager;
import cn.com.egova.wx.sdk.api.ApiConfig;
import com.jfinal.kit.PropKit;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;

/**
 * 微信接口调用工具类
 */
public class WeChatUtil {

    public static HttpsURLConnection getConnection(String requestUrl, String requestMethod) throws Exception {
        TrustManager[] tm = {new TrustManager()};
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new SecureRandom());

        SSLSocketFactory ssf = sslContext.getSocketFactory();

        URL url = new URL(requestUrl);
        HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
                .openConnection();
        httpUrlConn.setSSLSocketFactory(ssf);

        httpUrlConn.setDoOutput(true);
        httpUrlConn.setDoInput(true);
        httpUrlConn.setUseCaches(false);

        httpUrlConn.setRequestMethod(requestMethod);
        httpUrlConn.connect();
        return httpUrlConn;
    }

    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) throws Exception {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        HttpsURLConnection httpUrlConn = getConnection(requestUrl, requestMethod);
        if (outputStr != null) {
            OutputStream outputStream = httpUrlConn.getOutputStream();

            outputStream.write(outputStr.getBytes("UTF-8"));
            outputStream.close();
        }
        InputStream inputStream = httpUrlConn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(
                inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(
                inputStreamReader);

        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();

        inputStream.close();
        inputStream = null;
        httpUrlConn.disconnect();
        jsonObject = JSONObject.fromObject(buffer.toString());

        return jsonObject;
    }

    public static AccessToken getAccessToken(String appid, String appsecret) throws Exception {
        AccessToken accessToken = null;
        String requestUrl = SysConfig.access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        if (jsonObject != null) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
            }
        }
        return accessToken;
    }

    public static int createMenu(String jsonMenu, String accessToken) throws Exception {
        int result = 0;
        String url = SysConfig.menu_create_url.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
        if ((jsonObject != null) &&
                (jsonObject.getInt("errcode") != 0)) {
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    public static ApiConfig getApiConfig() {
        ApiConfig ac = new ApiConfig();
        //配置微信API相关常量
        ac.setToken(SysInitBean.globConfig.get("token"));
        ac.setAppId(SysInitBean.globConfig.get("appId"));
        ac.setAppSecret(SysInitBean.globConfig.get("appSecret"));
        /**
         * 是否对消息进行加密，对应于微信平台的消息加解密方式：
         * 1：true进行加密且必须配置 encodingAesKey
         * 2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage((Integer.valueOf(SysInitBean.globConfig.get("encryptMessage")) == 2)?true:false);
        ac.setEncodingAesKey(SysInitBean.globConfig.get("encodingAesKey"));
        return ac;
    }

    public static String jsonStringReplace(String json,char target,char source){
        char[] temp = json.toCharArray();
        int n = temp.length;
        for(int i =0;i<n;i++){
            if(temp[i]==':'&&temp[i+1]=='"'){
                for(int j =i+2;j<n;j++){
                    if(temp[j]==source){
                        if(temp[j+1]!=',' &&  temp[j+1]!='}'){
                            temp[j]=target;
                        }else if(temp[j+1]==',' ||  temp[j+1]=='}'){
                            break ;
                        }
                    }
                }
            }
        }
        return new String(temp);
    }
}
