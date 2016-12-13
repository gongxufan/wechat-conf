package cn.com.egova.wx.web.controller;

import cn.com.egova.wx.sdk.api.CustomServiceApi;
import cn.com.egova.wx.web.entity.AutoReply;
import cn.com.egova.wx.web.entity.InMessage;
import cn.com.egova.wx.web.entity.ReplyMessage;
import cn.com.egova.wx.web.exception.CommonException;
import cn.com.egova.wx.web.service.IMaterialService;
import cn.com.egova.wx.web.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gongxufan on 2016/10/3.
 */
@Controller
@RequestMapping("/wx/messageManage")
public class MessageManageController {
    @Autowired
    @Qualifier("messageService")
    IMessageService messageService;

    @Autowired
    @Qualifier("materialService")
    IMaterialService materialService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public ModelAndView initMessageManage(HttpServletRequest request, @RequestParam("isFavorite") int isFavorite) {
        ModelAndView modelAndView = new ModelAndView("messageManage");
        try {
            String type = request.getParameter("type");
            //返回数量1-20 表示pageSize
            int count = Integer.valueOf(request.getParameter("count"));
            //0从第一个开始返回,表示页码
            int pageNow = Integer.valueOf(request.getParameter("pageNow"));
            int offset = (pageNow - 1) * count;
            int isKeyword  = Integer.valueOf(request.getParameter("isKeyword"));
            String content = request.getParameter("content");
            List<InMessage> inMessageList = messageService.getInMessageList(isKeyword == 1?true:false,isFavorite == 1?true:false,offset,count,content);
          /*  int total = messageService.getTotalMessages("'1'","1");
            if(isKeyword == 1)
                total = messageService.getTotalMessages("isKeyword","0");
            if(isFavorite == 1)
                total = messageService.getTotalMessages("isFavorite","1");
            if(content != null)
                total = messageService.getMessageCountViaContent(content);*/
            int total = messageService.getMessageCountViaContent(isKeyword == 1?true:false,isFavorite == 1?true:false,content);
            int allPages = total % count == 0 ?total/count:total/count+1;
            modelAndView.addObject("pages", allPages);
            modelAndView.addObject("pageNow",pageNow);
            modelAndView.addObject("isFavorite",isFavorite);
            modelAndView.addObject("isKeyword",isKeyword);
            modelAndView.addObject("total",total);
            modelAndView.addObject("content",content);
            modelAndView.addObject("inMessageList",inMessageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "/starMessage",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> starMessage(@RequestParam("msgId") String msgId,@RequestParam("action") String action){
        Map<String ,Object> map = new HashMap();
        try {
            boolean ok = messageService.starMessage(msgId,Integer.valueOf(action));
            if(ok) map.put("ok","ok");
            else throw new CommonException("消息操作失败");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("errorInfo",e.getMessage());
        }
        return  map;
    }
}
