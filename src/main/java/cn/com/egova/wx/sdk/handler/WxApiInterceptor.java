package cn.com.egova.wx.sdk.handler;

import cn.com.egova.wx.sdk.api.ApiConfigKit;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * WxApiHandler 为 WxApiHandler 绑定 ApiConfig 对象到当前线程，
 * 以便在后续的操作中可以使用 ApiConfigKit.getApiConfig() 获取到该对象
 */
public class WxApiInterceptor implements Interceptor {

    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        if (controller instanceof WxApiHandler == false)
            throw new RuntimeException("控制器需要继承 WxApiHandler");

        try {
            ApiConfigKit.setThreadLocalApiConfig(((WxApiHandler) controller).getApiConfig());
            inv.invoke();
        } finally {
            ApiConfigKit.removeThreadLocalApiConfig();
        }
    }
}

