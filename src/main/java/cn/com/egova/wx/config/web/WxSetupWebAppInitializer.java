package cn.com.egova.wx.config.web;

import com.jfinal.core.JFinalFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.Log4jConfigListener;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.Map;

/**
 * web.xml的编码方式，各种组件（监听器，过滤器等）在此初始化和注册
 * <p>
 * 注解方式的配置，需求容器支持Servlet3.0规范
 * Created by gongxufan on 2016/7/30.
 */
public class WxSetupWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //配置全局参数
        servletContext.setInitParameter("log4jConfigLocation", "/WEB-INF/log4j.xml");
        servletContext.setInitParameter("webAppRootKey", "com.egova.wx");

        //添加编码过滤器
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("characterEncodingFilter",
                CharacterEncodingFilter.class);
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
        Map<String, String> params = new HashMap<String, String>();
        params.put("encoding", "UTF-8");
        params.put("forceEncoding", "true");
        encodingFilter.setInitParameters(params);

        //启动JFinal微信
        FilterRegistration.Dynamic wxFilter = servletContext.addFilter("handler", JFinalFilter.class);
        wxFilter.addMappingForUrlPatterns(null, false, "/weixin/*");
        params = new HashMap<String, String>();
        params.put("configClass", "cn.com.egova.wx.config.wx.WeixinConfig");
        wxFilter.setInitParameters(params);

        //log4j监听器
        servletContext.addListener(Log4jConfigListener.class);

        super.onStartup(servletContext);
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        //DispatcherServlet映射到/请求地址
        return new String[]{"/"};
    }
}
