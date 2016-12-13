package cn.com.egova.wx.sdk.handler;

import cn.com.egova.wx.sdk.api.ApiConfig;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * 所有使用 Api 的 controller 需要继承此类
 */
@Before(WxApiInterceptor.class)
public abstract class WxApiHandler extends Controller {
    public abstract ApiConfig getApiConfig();
}
