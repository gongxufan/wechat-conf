package cn.com.egova.wx.web.controller;

import cn.com.egova.wx.util.ImageUtil;
import cn.com.egova.wx.web.bean.SysInitBean;
import cn.com.egova.wx.web.entity.AutoReply;
import cn.com.egova.wx.web.entity.Message;
import cn.com.egova.wx.web.entity.RegularInfo;
import cn.com.egova.wx.web.entity.RegularMessage;
import cn.com.egova.wx.web.entity.ReplyMessage;
import cn.com.egova.wx.web.exception.CommonException;
import cn.com.egova.wx.web.handler.WeixinApiInvokeHandler;
import cn.com.egova.wx.web.service.IMaterialService;
import cn.com.egova.wx.web.service.IMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
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
 * Created by gongxufan on 2016/9/22.
 */
@Controller
@RequestMapping("/wx/autoReply")
public class AutoReplyController {

    private final static Logger logger = Logger.getLogger(AutoReplyController.class);

    @Autowired
    @Qualifier("messageService")
    IMessageService messageService;

    @Autowired
    @Qualifier("materialService")
    IMaterialService materialService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public ModelAndView initAutoReply(HttpServletRequest request) {
        String replyType = request.getParameter("replyType");
        ModelAndView modelAndView = new ModelAndView("autoReply");
        try {
            if("1".equals(replyType) || "2".equals(replyType)){//关注和自动回复消息
                ReplyMessage replyMessage = messageService.getReplyMessage(Integer.valueOf(replyType));
                modelAndView.addObject("replyMessage",replyMessage);
            }else{//关键字消息回复
                List<RegularMessage> regularMessageList = messageService.getRegularMessageList(true);
                modelAndView.addObject("regularMessageList",regularMessageList);
                //统计各类消息数量
                for(RegularMessage r: regularMessageList){
                    List<Message> msgList = r.getMessageList();
                    if(msgList != null && msgList.size() > 0){
                        int i=0,j=0,k=0;
                        for(Message s:msgList){
                            if(s.getMsgType() == 1){
                                i++;
                            }else if(s.getMsgType() == 2){
                                j++;
                            }else {
                                k++;
                            }
                        }
                        r.setTotalTextMsg(i);
                        r.setTotalImgMsg(j);
                        r.setTotalTxtImgMsg(k);
                    }
                }
            }
            modelAndView.addObject("replyType",Integer.valueOf(replyType));
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("autoReply", SysInitBean.globConfig.get("autoReply"));
        return modelAndView;
    }

    @RequestMapping(value = "/saveMessage",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> saveMessage(@ModelAttribute("message") ReplyMessage message){
        boolean ok = false;
        if(message == null){
            throw new CommonException("自动回复消息内容不完整...");
        }
        Map<String ,Object> map = new HashMap();
        message.setCreateTime(String.valueOf(new Date().getTime()));
        try {
            //更新autoReply表对应的记录
            AutoReply autoReply = new AutoReply();
            autoReply.setReplyId(message.getReplyId());
            autoReply.setMsgId(message.getMsgId());
            autoReply.setReplyType(message.getReplyType());
            if((ok = messageService.saveAutoReply(autoReply))){
                ok =  messageService.saveMessage(message);
            }
            if(!ok) throw new CommonException("自动回复消息保存失败");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            map.put("errorInfo",e.getMessage());
        }
        return  map;
    }

    @RequestMapping(value = "/deleteMessage",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> deleteMessage(@RequestParam("msgId") String msgId){
        boolean ok = false;
        Map<String ,Object> map = new HashMap();
        try {
            ok = messageService.deleteMessage(msgId);
            if(!ok) throw new CommonException("自动回复消息保存失败");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            map.put("errorInfo",e.getMessage());
        }
        return  map;
    }

    @RequestMapping(value = "/getImgViaMediaId",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> getImgViaMediaId(@RequestParam("media_id") String media_id){
        boolean ok = false;
        Map<String ,Object> map = new HashMap();
        try {
            String url  = materialService.getImgViaMediaId(media_id);
            if(url == null) throw new CommonException("获取消息图片失败...");
            map.put("url",ImageUtil.encodeImgageToBase64(url));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            map.put("errorInfo",e.getMessage());
        }
        return  map;
    }

    @RequestMapping(value = "/deleteRegular",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> deleteRegular(@RequestParam("regularId") String regularId){
        boolean ok = false;
        Map<String ,Object> map = new HashMap();
        try {
            ok = messageService.deleteRegular(regularId);
            if(!ok) throw new CommonException("删除规则失败");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            map.put("errorInfo",e.getMessage());
        }
        return  map;
    }

    @RequestMapping(value = "/saveRegular",method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> saveRegular(@RequestParam("regularStr") String regularStr){
        boolean ok = false;
        Map<String ,Object> map = new HashMap();
        try {
            RegularInfo regularInfo = new ObjectMapper().readValue(regularStr, RegularInfo.class);
            ok = messageService.saveRegularMessage(regularInfo);
            if(!ok) throw new CommonException("保存规则失败");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            map.put("errorInfo",e.getMessage());
        }
        return  map;
    }
}
