package cn.com.egova.wx.web.service;

import cn.com.egova.wx.web.entity.Article;
import cn.com.egova.wx.web.entity.AutoReply;
import cn.com.egova.wx.web.entity.InMessage;
import cn.com.egova.wx.web.entity.Message;
import cn.com.egova.wx.web.entity.RegularInfo;
import cn.com.egova.wx.web.entity.RegularMessage;
import cn.com.egova.wx.web.entity.ReplyMessage;

import java.util.List;

/**
 * Created by gongxufan on 2016/9/22.
 */
public interface IMessageService {
    public boolean starMessage(String msg ,int action) throws Exception;
    public boolean saveMessage(ReplyMessage message) throws Exception;
    public boolean deleteMessage(String msgId) throws Exception;
    public boolean deleteRegular(String regularId) throws Exception;
    public boolean saveAutoReply(AutoReply autoReply) throws Exception;
    public ReplyMessage getReplyMessage(int replyType) throws Exception;
    public List<RegularMessage> getRegularMessageList(boolean needBase64) throws Exception;
    public boolean saveRegularMessage(RegularInfo regularInfo) throws Exception;
    public boolean saveInMessage(InMessage inMessage) throws Exception;
    public List<InMessage> getInMessageList(boolean isKeyword,boolean isFavorite, int offset, int count,String content) throws Exception;
    public int getTotalMessages(String key,String isFavorite) throws Exception;
    public int getMessageCountViaContent(boolean isKeyword,boolean isFavorite,String content) throws Exception;
    public List<Article> getArticleViaMediaId(String mediaId) throws Exception;
}
