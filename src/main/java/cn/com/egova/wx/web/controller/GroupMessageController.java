package cn.com.egova.wx.web.controller;

import cn.com.egova.wx.web.bean.SysInitBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gongxufan on 2016/9/5.
 */
@Controller
@RequestMapping("/wx/groupMessage")
public class GroupMessageController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String initGroupMessage(HttpServletRequest request) {
        return "groupMessage";
    }

    @RequestMapping(value = "/initMessageSent", method = RequestMethod.GET)
    public String initMessageSent(HttpServletRequest request) {
        return "messageSent";
    }
    @RequestMapping(value = "/msgSender", method = RequestMethod.GET)
    public String msgSender(HttpServletRequest request) {
        return "msgSender";
    }
}
