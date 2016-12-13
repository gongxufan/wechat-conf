package cn.com.egova.wx.web.controller;

import cn.com.egova.wx.util.AccessToken;
import cn.com.egova.wx.util.WeChatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gongxufan on 2016/7/30.
 */
@Controller
@RequestMapping("/wx")
public class AccessTokenController {
    @RequestMapping("/initAccessToken")
    public ModelAndView initAccessToken() {
        ModelAndView mv = new ModelAndView("initAccessToken");
        mv.addObject("msg", "test");
        return mv;
    }

    /**
     * 获取微信公众号accessToken
     *
     * @param appId
     * @param appSecret
     * @return
     */
    @RequestMapping("/getAccessToken")
    public
    @ResponseBody
    Map<String, Object> getAccessToken(String appId, String appSecret) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<String, Object>();
            AccessToken at = WeChatUtil.getAccessToken(appId, appSecret);
            if ((at != null) && (at.getToken() != null)) {
                map.put("appId", at.getToken());
            } else {
                map.put("appId", "APPID获取失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("appId", "APPID获取失败");
        }
        return map;
    }
}
