package cn.com.egova.wx.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gongxufan on 2016/7/30.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    //是否调用微信API接口
    public static boolean isInvokeApi = true;
    //群发接口测试用的微信号
    public static String test_weixin_hao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(HttpServletRequest request) {
        request.setAttribute("test_weixin_hao",test_weixin_hao);
        return "index";
    }

    @RequestMapping(value = "/triggerDataSource", method = RequestMethod.POST)
    public void triggerDataSource(@RequestParam("trigger") String trigger, HttpServletResponse res) throws IOException {
        if("1".equals(trigger))
            isInvokeApi = true;
        else
            isInvokeApi = false;
        res.getWriter().print("1");
    }
    @RequestMapping(value = "/setWeinXinHao", method = RequestMethod.POST)
    public void setWeinXinHao(@RequestParam("test_weixin_hao") String test_weixin, HttpServletResponse res) throws IOException {
        test_weixin_hao = test_weixin;
        res.getWriter().print("1");
    }
}
