package cn.com.egova.wx.web.controller;

import cn.com.egova.wx.web.bean.SysInitBean;
import cn.com.egova.wx.web.service.ISysconfigService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gongxufan on 2016/9/1.
 */
@Controller
@RequestMapping("/wx/sysconfig")
public class SysConfigController {

    @Autowired
    @Qualifier("syconfigService")
    ISysconfigService syconfigService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String sysconfig(HttpServletRequest request) {
        request.setAttribute("appId", SysInitBean.globConfig.get("appId"));
        request.setAttribute("appSecret", SysInitBean.globConfig.get("appSecret"));
        request.setAttribute("token", SysInitBean.globConfig.get("token"));
        String encodingAesKey = SysInitBean.globConfig.get("encodingAesKey");
        request.setAttribute("encodingAesKey", encodingAesKey);
        if(encodingAesKey != null)
            request.setAttribute("aesSize",encodingAesKey.length());
        else
            request.setAttribute("aesSize",0);
        request.setAttribute("encryptMessage", SysInitBean.globConfig.get("encryptMessage"));
        return "sysconfig";
    }

    @RequestMapping(value = "/randomKey", method = RequestMethod.GET)
    public @ResponseBody
    Map<String,Object> randomKey() {
        Map<String,Object> map = new HashMap<String,Object>();
        //消息加密密钥由43位字符组成，可随机修改，字符范围为A-Z，a-z，0-9
        map.put("encoding_aeskey",RandomStringUtils.randomAlphanumeric(43));
        return map;
    }

    @RequestMapping(value = "/commitSyscofig", method = RequestMethod.GET)
    public @ResponseBody
    Map<String,Object> commitSyscofig(String data) {
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            if(syconfigService.saveOrUpdateConfig(data)){
                map.put("ok","ok");
            }else {
                map.put("errorInfo","保存配置出现异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("errorInfo","保存配置出现异常");
        }
        return map;
    }

    @RequestMapping(value = "/saveConfig", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> saveConfig(@RequestParam("key")String key,@RequestParam("value")String value) {
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            if(syconfigService.saveConfig(key,value)){
                map.put("ok","ok");
            }else {
                map.put("errorInfo","保存配置出现异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("errorInfo","保存配置出现异常");
        }
        return map;
    }
}
