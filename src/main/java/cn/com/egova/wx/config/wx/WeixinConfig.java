package cn.com.egova.wx.config.wx;

/**
 * 微信公众号相关配置
 * Created by gongxufan on 2016/8/2.
 */

import cn.com.egova.wx.web.handler.WeixinApiInvokeHandler;
import cn.com.egova.wx.web.handler.WeixinMsgMonitorHandler;
import cn.com.egova.wx.sdk.api.ApiConfigKit;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.render.ViewType;

public class WeixinConfig extends JFinalConfig {
    /**
     * 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
     *
     * @param pro 生产环境配置文件
     * @param dev 开发环境配置文件
     */
    public void loadProp(String pro, String dev) {
        try {
            PropKit.use(pro);
        } catch (Exception e) {
            PropKit.use(dev);
        }
    }

    @Override
    public void configConstant(Constants me) {
        loadProp("/sysinfo.properties", "/sysinfo.properties");
        me.setDevMode(PropKit.getBoolean("devMode", false));
        me.setEncoding("utf-8");
        me.setViewType(ViewType.JSP);
        me.setError500View("/500.html");
        me.setError404View("/404.html");
        // ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
        ApiConfigKit.setDevMode(me.getDevMode());
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/weixin/msg", WeixinMsgMonitorHandler.class,"/");
        me.add("/weixin/api", WeixinApiInvokeHandler.class,"/");
    }

    @Override
    public void configPlugin(Plugins me) {
        // C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
        // me.add(c3p0Plugin);

        // EhCachePlugin ecp = new EhCachePlugin();
        // me.add(ecp);

        // RedisPlugin redisPlugin = new RedisPlugin("weixin", "127.0.0.1");
        // me.add(redisPlugin);
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {

    }

    @Override
    public void afterJFinalStart() {
        // 1.5 之后支持redis存储access_token、js_ticket，需要先启动RedisPlugin
//		ApiConfigKit.setAccessTokenCache(new RedisAccessTokenCache());
        // 1.6新增的2种初始化
//		ApiConfigKit.setAccessTokenCache(new RedisAccessTokenCache(Redis.use("weixin")));
//		ApiConfigKit.setAccessTokenCache(new RedisAccessTokenCache("weixin"));
    }

}
