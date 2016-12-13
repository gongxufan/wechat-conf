package cn.com.egova.wx.web.handler;

/**
 * Created by gongxufan on 2016/8/2.
 */

import cn.com.egova.wx.sdk.api.ApiConfig;
import cn.com.egova.wx.sdk.api.ApiResult;
import cn.com.egova.wx.sdk.api.CustomServiceApi;
import cn.com.egova.wx.sdk.api.UserApi;
import cn.com.egova.wx.sdk.handler.WxMsgHandlerAdapter;
import cn.com.egova.wx.sdk.msg.in.InImageMsg;
import cn.com.egova.wx.sdk.msg.in.InMsg;
import cn.com.egova.wx.sdk.msg.in.InTextMsg;
import cn.com.egova.wx.sdk.msg.in.event.InFollowEvent;
import cn.com.egova.wx.sdk.msg.in.event.InMenuEvent;
import cn.com.egova.wx.sdk.msg.out.News;
import cn.com.egova.wx.sdk.msg.out.OutImageMsg;
import cn.com.egova.wx.sdk.msg.out.OutNewsMsg;
import cn.com.egova.wx.sdk.msg.out.OutTextMsg;
import cn.com.egova.wx.util.ImageUtil;
import cn.com.egova.wx.util.WeChatUtil;
import cn.com.egova.wx.web.bean.SpringUtil;
import cn.com.egova.wx.web.bean.SysInitBean;
import cn.com.egova.wx.web.entity.Article;
import cn.com.egova.wx.web.entity.InMessage;
import cn.com.egova.wx.web.entity.Keyword;
import cn.com.egova.wx.web.entity.Material;
import cn.com.egova.wx.web.entity.Message;
import cn.com.egova.wx.web.entity.Node;
import cn.com.egova.wx.web.entity.RegularMessage;
import cn.com.egova.wx.web.entity.ReplyMessage;
import cn.com.egova.wx.web.entity.User;
import cn.com.egova.wx.web.service.IMenuService;
import cn.com.egova.wx.web.service.IMessageService;
import cn.com.egova.wx.web.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.log.Log;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 跟微信后台服务器交互的逻辑，属于被动响应微信后台服务发出的请求
 * 包括消息接收，事件推送监听等
 */
public class WeixinMsgMonitorHandler extends WxMsgHandlerAdapter {

    private final static Log logger = Log.getLog(WeixinMsgMonitorHandler.class);

    /**
     * 如果要支持多公众账号，只需要在此返回各个公众号对应的 ApiConfig 对象即可 可以通过在请求 url 中挂参数来动态从数据库中获取
     * <p>
     * ApiConfig 属性值
     */
    @Override
    public ApiConfig getApiConfig() {
        return WeChatUtil.getApiConfig();
    }

    @Override
    protected void processInFollowEvent(InFollowEvent inFollowEvent) {
        try {
            //关注&取消关注事件
            String event = inFollowEvent.getEvent();
            String openId = inFollowEvent.getFromUserName();
            IUserService userService = (IUserService)SpringUtil.getBean("userService");
            //关注事件，讲OpenID关联的用户信息本地入库
            if("subscribe".equals(event)){
                //根据openid查询用户基本信息
                ApiResult result = UserApi.getUserInfo(openId);
                if(result.isSucceed()){
                    User user = new ObjectMapper().readValue(result.getJson(), User.class);
                    if(!userService.addUser(user)) throw new RuntimeException("用户信息保存失败，openid="+openId);
                }
                //如果开启了自动回复功能
                if("1".equals(SysInitBean.globConfig.get("autoReply")))
                    renderAutoReplyMsg(inFollowEvent,1,openId);
            }else if("unsubscribe".equals(event)){
                //取消关注，变更本地数据的状态，subscribe设置为0
                if(!userService.deSubscribe(openId)) throw new RuntimeException("用户取消订阅状态更新失败，openid="+openId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }


    /**
     * 消息发送逻辑如下
     * 1、根据autoReply =1 来决定是否进行自动回复
     * 2、是否设置了规则回复
     * 3、没有设置规则回复则进行自动回复内容
     *
     * 需要注意的是，最后一条回复的消息需要通过render进行发送，否则客户端会报服务器错误
     * @param inTextMsg
     */
    protected void processInTextMsg(InTextMsg inTextMsg) {
        String msgIn = inTextMsg.getContent().trim();
        String autoReply = SysInitBean.globConfig.get("autoReply");
        String openId = inTextMsg.getFromUserName();
        InMessage inMessage = new InMessage();
        inMessage.setOpenid(openId);
        inMessage.setMedia_id("");
        inMessage.setMsgId("inMsg-"+UUID.randomUUID().toString());
        inMessage.setContent(msgIn);
        inMessage.setMsgType(1);
        inMessage.setCreateTime(String.valueOf(new Date().getTime()));
        inMessage.setHasReply(0);
        inMessage.setIsFavorite(0);
        inMessage.setIsKeyword(0);
        try {
            IMessageService messageService = (IMessageService) SpringUtil.getBean("messageService");
            List<Message> messageList2Send = new ArrayList<Message>();
            //是否开启自动回复
            if("1".equals(autoReply)){
                inMessage.setHasReply(1);
                //是否设置了关键字回复
                List<RegularMessage> regularMessageList = messageService.getRegularMessageList(false);
                int matchKeywords = 0;
                if(regularMessageList != null && regularMessageList.size() > 0){//根据规则回复
                    //匹配关键字
                    out:
                    for(RegularMessage message : regularMessageList){
                        List<Keyword> keywordList = message.getKeywordList();
                        if(keywordList != null && keywordList.size() > 0){
                            for(Keyword keyword : keywordList){
                                //匹配规则
                                int mode = keyword.getMode();
                                boolean match = false;
                                //部分匹配
                                if(mode == 0)
                                    match = msgIn.contains(keyword.getKeywordName()) || keyword.getKeywordName().contains(msgIn);
                                //全匹配
                                if(mode == 1)
                                    match = msgIn.equals(keyword.getKeywordName());
                                if(match){
                                    inMessage.setIsKeyword(1);
                                    //有匹配的规则消息，不尽心自动答复了
                                    matchKeywords++;
                                    //是否发送全部消息
                                    List<Message> messageList = message.getMessageList();
                                    if(message.getReplyAll() == 1){
                                        messageList2Send.addAll(messageList);
                                    }
                                    else{
                                        if(messageList != null && messageList.size() > 0)
                                        renderLastMessage(inTextMsg,messageList.get(messageList.size() -1),openId);
                                    }
                                    continue out;
                                }
                            }
                        }
                    }
                }else{//直接自动回复
                    renderAutoReplyMsg(inTextMsg,2,openId);
                    messageService.saveInMessage(inMessage);
                    return;
                }
                //没有匹配到关键字，则进行默认的自动回复
                if(matchKeywords == 0){
                    renderAutoReplyMsg(inTextMsg,2,openId);
                    messageService.saveInMessage(inMessage);
                    return;
                }

                messageService.saveInMessage(inMessage);
                //顺序发送 前面的消息
                if(messageList2Send.size() > 1){
                    for(int ii = 0 ; ii < messageList2Send.size() -1;ii++){
                        sendCustomMessage(messageList2Send.get(ii),openId);
                    }
                    //最后一条消息通过render发送，以防客户端出现服务器错误信息
                    renderLastMessage(inTextMsg,messageList2Send.get(messageList2Send.size() -1),openId);
                }
            }

        } catch (Exception e) {
            logger.error("保存用户："+inMessage.getOpenid()+"消息失败");
            renderOutTextMsg("非常抱歉，公众号服务出现异常。请稍后重试!");
            e.printStackTrace();
        }
    }

    @Override
    protected void processInImageMsg(InImageMsg inImageMsg) {
        String openId = inImageMsg.getFromUserName(), media_id = inImageMsg.getMediaId(),
                imageUrl = inImageMsg.getPicUrl();
        InMessage inMessage = new InMessage();
        try {
            inMessage.setOpenid(openId);
            inMessage.setMedia_id(imageUrl);
            inMessage.setMsgId("inMsg-" + UUID.randomUUID().toString());
            inMessage.setContent("");
            inMessage.setMsgType(2);
            inMessage.setCreateTime(String.valueOf(new Date().getTime()));
            inMessage.setHasReply(0);
            inMessage.setIsFavorite(0);
            inMessage.setIsKeyword(0);
            IMessageService messageService = (IMessageService) SpringUtil.getBean("messageService");

            String autoReply = SysInitBean.globConfig.get("autoReply");
            if ("1".equals(autoReply)) {
                inMessage.setHasReply(1);
                inMessage.setIsKeyword(0);
                renderAutoReplyMsg(inImageMsg, 2, openId);
            }
            messageService.saveInMessage(inMessage);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存用户：" + inMessage.getOpenid() + "消息失败");
        }
    }

    protected  void renderLastMessage(InTextMsg inTextMsg, Message msg, String openId){
        //发送文字
        if(msg.getMsgType() == 1){
            String content = msg.getContent();
            if(StringUtils.isNotEmpty(msg.getContent())){
                renderOutTextMsg(content.replaceAll("<br/>","\n").replaceAll("<br>","\n"));
            }
        }
        //发送图片
        if(msg.getMsgType() == 2){
            OutImageMsg outImageMsg = new OutImageMsg(inTextMsg);
            outImageMsg.setMediaId(msg.getMedia_id());
            render(outImageMsg);
        }
        //发送图文
        if(msg.getMsgType() == 3){
            IMessageService messageService = (IMessageService) SpringUtil.getBean("messageService");
            OutNewsMsg outNewsMsg = new OutNewsMsg(inTextMsg);
            List<News> articles = new ArrayList<News>();
            List<Article> articleList = null;
            try {
                articleList = messageService.getArticleViaMediaId(msg.getMedia_id());
            } catch (Exception e) {
                renderText("消息发送异常，请稍后重试...");
                return;
            }
            if(articleList != null && articleList.size() > 0){
                for(Article article : articleList){
                    News news = new News();
                    news.setDescription(article.getDigest());
                    news.setPicUrl(article.getThumb_url());
                    news.setUrl(article.getUrl());
                    news.setTitle(article.getTitle());
                    articles.add(news);
                }
            }
            outNewsMsg.setArticles(articles);
            render(outNewsMsg);
        }

    }
    protected void sendCustomMessage(Message msg,String openid){
        if(msg.getMsgType() == 1){
            String content = msg.getContent();
            //文字消息
            if(StringUtils.isNotEmpty(msg.getContent())){
                CustomServiceApi.sendText(openid,content.replaceAll("<br/>","\n").replaceAll("<br>","\n"));
            }
        }
        if(msg.getMsgType() == 2){
            CustomServiceApi.sendImage(openid,msg.getMedia_id());
        }
        if(msg.getMsgType() == 3){
            CustomServiceApi.sendMpNews(openid,msg.getMedia_id());
        }
    }

    protected void renderAutoReplyMsg(InMsg inMsg, int replyType, String openId) throws Exception{
        //发送自动回复消息
        IMessageService messageService = (IMessageService) SpringUtil.getBean("messageService");
        ReplyMessage replyMessage = messageService.getReplyMessage(replyType);
        if(replyMessage != null && StringUtils.isNotEmpty(replyMessage.getMsgId())){
            String content = replyMessage.getContent();
            //文字消息
            if(StringUtils.isNotEmpty(replyMessage.getContent())){
                OutTextMsg outTextMsg = new OutTextMsg(inMsg);
                outTextMsg.setContent(content.replaceAll("<br/>","\n").replaceAll("<br>","\n"));
                render(outTextMsg);
            }
            if(StringUtils.isNotEmpty(replyMessage.getMedia_id())){
                OutImageMsg outImageMsg = new OutImageMsg(inMsg);
                outImageMsg.setMediaId(replyMessage.getMedia_id());
                render(outImageMsg);
            }
        }
    }
    /**
     * 处理自定义菜单事件
     */
    protected void processInMenuEvent(InMenuEvent inMenuEvent) {
        try {
            String eventKey = inMenuEvent.getEventKey();
            String event = inMenuEvent.getEvent();
            OutTextMsg outTextMsg = new OutTextMsg(inMenuEvent);
            IMenuService menuService = (IMenuService)SpringUtil.getBean("menuService");
            if(InMenuEvent.EVENT_INMENU_CLICK.equalsIgnoreCase(event)){//菜单点击事件推送处理
                //根据eventKey从数据表读取图文消息的文章列表，组成图文消息发送
                OutNewsMsg outMsg = new OutNewsMsg(inMenuEvent);
                //根据key查找菜单配置的图文消息
                List<Material> materials = (List<Material>)menuService.getMaterialByMenuKey(eventKey);
                if(materials != null && materials.size() > 0){
                    for(int m = 0 ; m < materials.size() ; m++){
                        News news = new News();
                        news.setUrl(materials.get(m).getUrl());
                        news.setTitle(materials.get(m).getTitle());
                        news.setDescription(materials.get(m).getDigest());
                        news.setPicUrl(materials.get(m).getThumb_url());
                        outMsg.addNews(news);
                    }
                render(outMsg);
                }else{//发送文字消息
                    Node n = menuService.getNodeByKey(eventKey);
                    String text = n.getMsgText();
                    if(text != null)
                        text = text.replaceAll("<br/>","\n").replaceAll("<br>","\n");
                    outTextMsg.setContent(text);
                    render(outTextMsg);
                }
            }else if(InMenuEvent.EVENT_INMENU_VIEW.equalsIgnoreCase(event)){//跳转链接事件推送处理
                outTextMsg.setContent(eventKey);
                render(outTextMsg);

            }else{
                OutTextMsg outMsg = new OutTextMsg(inMenuEvent);
                outMsg.setContent("暂时只支持VIEW和CLICK类型的菜单事件处理");
                render(outMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
