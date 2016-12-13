package cn.com.egova.wx.sdk.util;

import cn.com.egova.wx.sdk.api.MediaFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * JFinal-weixin Http请求工具类
 *
 * @author L.cm
 */
public final class HttpUtils {

    private HttpUtils() {
    }

    public static String get(String url) {
        return delegate.get(url);
    }

    public static String get(String url, Map<String, String> queryParas) {
        return delegate.get(url, queryParas);
    }

    public static String post(String url, String data) {
        return delegate.post(url, data);
    }

    public static String postSSL(String url, String data, String certPath, String certPass) {
        return delegate.postSSL(url, data, certPath, certPass);
    }

    public static MediaFile download(String url) {
        return delegate.download(url);
    }

    public static InputStream download(String url, String params) {
        return delegate.download(url, params);
    }

    public static String upload(String url, InputStream file, String fileName,String params) {
        return delegate.upload(url, file, fileName,params);
    }

    /**
     * http请求工具 委托
     * 优先使用OkHttp
     * 最后使用JFinal HttpKit
     */
    private interface HttpDelegate {
        String get(String url);

        String get(String url, Map<String, String> queryParas);

        String post(String url, String data);

        String postSSL(String url, String data, String certPath, String certPass);

        MediaFile download(String url);

        InputStream download(String url, String params);

        String upload(String url, InputStream file, String fileName, String params);
    }

    // http请求工具代理对象
    private static final HttpDelegate delegate;

    static {
        HttpDelegate delegateToUse = null;

        if (ClassUtils.isPresent("cn.com.egova.wx.sdk.util.HttpKitExt", HttpUtils.class.getClassLoader())) {
            delegateToUse = new HttpKitDelegate();
        }
        delegate = delegateToUse;
    }

    /**
     * HttpKit代理
     */
    private static class HttpKitDelegate implements HttpDelegate {

        @Override
        public String get(String url) {
            return com.jfinal.kit.HttpKit.get(url);
        }

        @Override
        public String get(String url, Map<String, String> queryParas) {
            return com.jfinal.kit.HttpKit.get(url, queryParas);
        }

        @Override
        public String post(String url, String data) {
            return com.jfinal.kit.HttpKit.post(url, data);
        }

        @Override
        public String postSSL(String url, String data, String certPath, String certPass) {
            return HttpKitExt.postSSL(url, data, certPath, certPass);
        }

        @Override
        public MediaFile download(String url) {
            try {
                return HttpKitExt.download(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public InputStream download(String url, String params) {
            try {
                return HttpKitExt.downloadMaterial(url, params);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String upload(String url, InputStream file, String fileName,String params) {
            try {
                return HttpKitExt.uploadMedia(url, file, fileName,params);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
