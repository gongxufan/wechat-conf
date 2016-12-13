package cn.com.egova.wx.web.service.impl;

import cn.com.egova.wx.sdk.msg.out.News;
import cn.com.egova.wx.util.DBUtils;
import cn.com.egova.wx.util.ImageUtil;
import cn.com.egova.wx.util.SQLiteUtils;
import cn.com.egova.wx.web.bean.SysConfig;
import cn.com.egova.wx.web.entity.Article;
import cn.com.egova.wx.web.entity.AutoReply;
import cn.com.egova.wx.web.entity.InMessage;
import cn.com.egova.wx.web.entity.Keyword;
import cn.com.egova.wx.web.entity.Material;
import cn.com.egova.wx.web.entity.Message;
import cn.com.egova.wx.web.entity.RegularInfo;
import cn.com.egova.wx.web.entity.RegularMessage;
import cn.com.egova.wx.web.entity.ReplyMessage;
import cn.com.egova.wx.web.exception.CommonException;
import cn.com.egova.wx.web.service.IMessageService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by gongxufan on 2016/9/22.
 */
@Service("messageService")
public class MessageServiceImpl implements IMessageService{
    private final Logger logger = Logger.getLogger(MessageServiceImpl.class);

    @Override
    public int getTotalMessages(String key,String isFavorite) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            return sqLiteUtils.getTableCount(SysConfig.inMessageTableName, key, isFavorite);
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
    }

    @Override
    public boolean starMessage(String msg, int action) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            ok = sqLiteUtils.executeSql("update " + SysConfig.inMessageTableName +" set isFavorite=" + action +" where" +
                    " msgId='" +msg + "'");
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean saveMessage(ReplyMessage message) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            if (sqLiteUtils.getTableCount(SysConfig.messageTableName, "msgId", message.getMsgId()) > 0) {
                ok = sqLiteUtils.update(SysConfig.messageTableName, message.getMsgId(), "msgId",
                        new String[]{"msgId", "msgType", "content", "media_id"},
                        new Object[]{message.getMsgId(),message.getMsgType(),message.getContent(),message.getMedia_id()});
            } else {
                ok = sqLiteUtils.insert(SysConfig.messageTableName,
                        new Object[]{message.getMsgId(),message.getMsgType(),message.getContent(),message.getMedia_id(),message.getCreateTime()} );
            }
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean saveAutoReply(AutoReply autoReply) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            if (sqLiteUtils.getTableCount(SysConfig.autoReplyTableName, "replyType", String.valueOf(autoReply.getReplyType())) > 0) {
                ok = sqLiteUtils.update(SysConfig.autoReplyTableName, String.valueOf(autoReply.getReplyType()), "replyType",
                        new String[]{"replyId", "replyType", "msgId"},
                        new Object[]{autoReply.getReplyId(),autoReply.getReplyType(),autoReply.getMsgId()});
            } else {
                ok = sqLiteUtils.insert(SysConfig.autoReplyTableName,
                        new Object[]{autoReply.getReplyType(),autoReply.getReplyType(),autoReply.getMsgId()} );
            }
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public ReplyMessage getReplyMessage(int replyType) throws Exception {
        String sql = "select a.replyId,a.replyType,a.msgId,m.msgType,m.content,m.media_id,m.createTime from tcAutoReply a,tcMessage m " +
                " where a.msgId=m.msgId and a.replyType="+replyType;
        ReplyMessage replyMessage = new ReplyMessage();
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            Vector<Vector<Object>> result = sqLiteUtils.selectVector(sql);
            if (result != null && result.size() > 0) {
                for (Vector<Object> m : result) {
                    replyMessage.setReplyId(String.valueOf(m.get(0)));
                    replyMessage.setReplyType((Integer) m.get(1));
                    replyMessage.setMsgId(String.valueOf(m.get(2)));
                    replyMessage.setMsgType((Integer) m.get(3));
                    replyMessage.setContent(String.valueOf(m.get(4)));
                    replyMessage.setMedia_id(String.valueOf(m.get(5)));
                    replyMessage.setCreateTime(String.valueOf(m.get(6)));
                }
            }
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return replyMessage;
    }

    @Override
    public int getMessageCountViaContent(boolean isKeyword,boolean isFavorite,String content) throws Exception {
        String sql = "select count(*)  from " +
                " tcInMessage im ,tcMessage m,tcUser u where im.msgId=m.msgId and u.openid=im.openid " ;
        if(isFavorite)
            sql += " and im.isFavorite=1";
        if(isKeyword)
            sql += " and im.isKeyword=0";
        if(content != null){
            sql += " and m.content like '%" + content + "%'";
        }
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        return sqLiteUtils.getTableCountViaSql(sql);
    }

    @Override
    public List<InMessage> getInMessageList(boolean isKeyword,boolean isFavorite, int offset, int count,String content) throws Exception {
        String sql = "select distinct m.msgId,m.msgType,m.content,m.media_id,m.createTime," +
                " im.openid,im.hasReply,im.isFavorite,im.isKeyword,u.nickname,u.headimgurl,u.remark  from " +
                " tcInMessage im ,tcMessage m,tcUser u where im.msgId=m.msgId and u.openid=im.openid " ;
        if(isFavorite)
            sql += " and im.isFavorite=1";
        if(isKeyword)
            sql += " and im.isKeyword=0";
        if(content != null){
            sql += " and m.content like '%" + content + "%'";
        }
        sql += " order by m.createTime desc limit "+count+" offset " + offset;
        List<InMessage> inMessages = new ArrayList<InMessage>();
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            Vector<Vector<Object>> result = sqLiteUtils.selectVector(sql);
            if (result != null && result.size() > 0) {
                for (Vector<Object> m : result) {
                    InMessage inMessage = new InMessage();
                    inMessage.setMsgId(String.valueOf(m.get(0)));
                    inMessage.setMsgType((Integer)m.get(1));
                    inMessage.setContent(String.valueOf(m.get(2)));
                    inMessage.setMedia_id(String.valueOf(m.get(3)));
                    Date date = new Date(Long.valueOf(String.valueOf(m.get(4))));
                    inMessage.setCreateTime(DateFormatUtils.format(date,"yyyy-MM-dd HH:mm"));
                    inMessage.setOpenid(String.valueOf(m.get(5)));
                    inMessage.setHasReply((Integer)m.get(6));
                    inMessage.setIsFavorite((Integer)m.get(7));
                    inMessage.setIsKeyword((Integer)m.get(8));
                    inMessage.setNickname(String.valueOf(m.get(9)));
                    inMessage.setHeadimgurl(String.valueOf(m.get(10)));
                    inMessage.setRemark(String.valueOf(m.get(11)));
                    inMessages.add(inMessage);
                }
            }
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return inMessages;
    }

    @Override
    public boolean deleteMessage(String msgId) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            ok = sqLiteUtils.delete(SysConfig.messageTableName,"msgId",msgId);
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean deleteRegular(String regularId) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        boolean ok2 = false;
        boolean ok3 = false;
        boolean ok4 = false;
        boolean ok5 = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            //删除规则
            ok = sqLiteUtils.delete(SysConfig.regularTableName,"regularId",regularId);
            if(!ok) throw new RuntimeException("保存规则失败！");
            //删除规则关联的消息
            ok2 = sqLiteUtils.executeSql("delete from tcMessage  where msgId in(select msgId from tcRegularMessage rm where rm.regularId='"+regularId+"')");
            if(!ok2) throw new RuntimeException("保存规则失败！");
            //删除规则关联关键字
            ok3 = sqLiteUtils.executeSql("delete from tcKeyword  where keywordId in(select keywordId from tcRegularKeyword rm where rm.regularId='"+regularId+"')");
            if(!ok3) throw new RuntimeException("保存规则失败！");
            //删除消息和关键字关联表
            ok4 = sqLiteUtils.delete(SysConfig.regularKeywordTableName,"regularId",regularId);
            if(!ok4) throw new RuntimeException("保存规则失败！");
            ok5 = sqLiteUtils.delete(SysConfig.regularMessageTableName,"regularId",regularId);
            if(!ok5) throw new RuntimeException("保存规则失败！");
            if (ok&&ok2&&ok3&&ok4&&ok5)
                sqLiteUtils.getConnection().commit();
        }catch (Exception e){
            logger.error("删除规则:"+regularId+"失败！");
            sqLiteUtils.getConnection().rollback();
            return false;
        }finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public List<Article> getArticleViaMediaId(String mediaId) throws Exception {
        List<Article> articleList = new ArrayList<Article>();
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        Object[][] result = sqLiteUtils.selectObject(SysConfig.newsTableName,"media_id",mediaId);
        if(result != null && result.length > 0){
            for(int i = 0 ; i < result.length ; i++){
                Object[] o = result[i];
                Article news = new Article();
                news.setThumb_media_id((String)o[0]);
                news.setTitle(String.valueOf((String)o[1]));
                news.setUrl(String.valueOf((String)o[2]));
                news.setThumb_url(String.valueOf((String)o[3]));
                news.setDigest(String.valueOf((String)o[4]));
                articleList.add(news);
            }
        }
        return articleList;
    }

    @Override
    public List<RegularMessage> getRegularMessageList(boolean needBase64) throws Exception {
        List<RegularMessage> regularMessages = new ArrayList<RegularMessage>();
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            //查询规则
            String sql = "select r.regularId,r.regularName,r.replyAll from tcReplyRegular r order by createTime desc";
            Vector<Vector<Object>> result = sqLiteUtils.selectVector(sql);
            if (result != null && result.size() > 0) {
                for (Vector<Object> m : result) {
                    RegularMessage regularMessage = new RegularMessage();
                    regularMessage.setRegularId(String.valueOf(m.get(0)));
                    regularMessage.setRegularName(String.valueOf(m.get(1)));
                    regularMessage.setReplyAll((Integer) m.get(2));
                    regularMessages.add(regularMessage);
                }
            }

            if(regularMessages.size() > 0) {
                for (RegularMessage msg : regularMessages) {
                    List<Keyword> keywords = new ArrayList<Keyword>();
                    List<Message> messages = new ArrayList<Message>();
                    //查询关键字
                    sql = "select k.keywordId,k.keywordName,k.mode from tcRegularKeyword rk,tcKeyword k where rk.keywordId=k.keywordId and rk.regularId = '" + msg.getRegularId() + "'";
                    result = sqLiteUtils.selectVector(sql);
                    if (result != null && result.size() > 0) {
                        for (Vector<Object> m : result) {
                            Keyword keyword = new Keyword();
                            keyword.setKeywordId(String.valueOf(m.get(0)));
                            keyword.setKeywordName(String.valueOf(m.get(1)));
                            keyword.setMode((Integer)m.get(2));
                            keywords.add(keyword);
                        }
                    }
                    msg.setKeywordList(keywords);
                    //查询关联消息
                    sql = "select k.msgId,k.msgType,k.content,k.media_id,k.createTime from tcRegularMessage rk,tcMessage k where rk.msgId=k.msgId and rk.regularId = '" + msg.getRegularId() + "' order by k.createTime";
                    result = sqLiteUtils.selectVector(sql);
                    if (result != null && result.size() > 0) {
                        for (Vector<Object> m : result) {
                            Message message = new Message();
                            message.setMsgId(String.valueOf(m.get(0)));
                            message.setMsgType((Integer) (m.get(1)));
                            message.setContent(String.valueOf(m.get(2)));
                            message.setMedia_id(String.valueOf(m.get(3)));
                            messages.add(message);
                        }
                    }
                    msg.setMessageList(messages);
                    //查找图文信息
                    sql = "select n.media_id, n.title,n.url,n.thumb_url,n.digest from tcNews n where n.media_id in(";
                    List<Article> newsList = new ArrayList<Article>();
                    List<Message> msgList = msg.getMessageList();
                    if (msgList != null && msgList.size() > 0) {
                        boolean hasNews = false;
                        for (Message s : msgList) {
                            if (s.getMsgType() == 3) {
                                hasNews = true;
                                sql += ("'" + s.getMedia_id() + "',");
                            }
                        }
                        if(!hasNews) continue;
                        sql = new StringBuffer(sql).deleteCharAt(sql.length() - 1).append(")").toString();
                        result = sqLiteUtils.selectVector(sql);
                        if (result != null && result.size() > 0) {
                            for (int i = 0; i < result.size();i++) {
                                Vector<Object> m = result.get(i);
                                String media_id = String.valueOf(m.get(0));
                                boolean isMulti = false;
                                //多图文只展示第一篇
                                for(int j = 0 ; j < i;j++){
                                    Vector<Object> mp = result.get(j);
                                    String media_idp = String.valueOf(mp.get(0));
                                    if(media_id.equals(media_idp)){
                                        isMulti = true;
                                        break;
                                    }
                                }
                                if(isMulti)
                                    continue;
                                Article news = new Article();
                                news.setThumb_media_id(media_id);
                                news.setTitle(String.valueOf(m.get(1)));
                                news.setUrl(String.valueOf(m.get(2)));
                                news.setThumb_url(String.valueOf(m.get(3)));
                                news.setDigest(String.valueOf(m.get(4)));
                                newsList.add(news);
                            }
                        }
                        msg.setNewsList(newsList);
                    }
                }
            }
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return regularMessages;
    }

    @Override
    public boolean saveRegularMessage(RegularInfo regularInfo) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        String regularId = regularInfo.getRegularId();
        boolean ok = false;
        boolean ok2 = false;
        boolean ok3 = false;
        boolean ok4 = false;
        boolean ok5 = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            //删除规则
            //先删除该规则
            if( deleteRegular(regularId)){
                //插入新的规则
                ok = sqLiteUtils.insert(SysConfig.regularTableName,new Object[]{regularInfo.getRegularId(),regularInfo.getRegularName(),String.valueOf(new Date().getTime()),regularInfo.getReplyAll()});
                //插入关键字和关联表
                List<Keyword> keywords = regularInfo.getKeywordList();
                if(keywords != null && keywords.size() > 0){
                    for(Keyword keyword : keywords){
                        ok2 = sqLiteUtils.insert(SysConfig.keywordTableName,new Object[]{keyword.getKeywordId(),keyword.getKeywordName(),keyword.getMode()});
                        if(!ok2) throw new RuntimeException("保存规则失败！");
                        ok3 = sqLiteUtils.insert(SysConfig.regularKeywordTableName,new Object[]{regularId,keyword.getKeywordId()});
                        if(!ok3) throw new RuntimeException("保存规则失败！");
                    }
                }
                //插入消息和关联表
                List<Message> messages = regularInfo.getMessageList();
                if(messages != null && messages.size() > 0){
                    for(Message message : messages){
                        ok4 = sqLiteUtils.insert(SysConfig.messageTableName,new Object[]{message.getMsgId(),message.getMsgType(),message.getContent(),message.getMedia_id(),String.valueOf(new Date().getTime())});
                        if(!ok4) throw new RuntimeException("保存规则失败！");
                        ok5 = sqLiteUtils.insert(SysConfig.regularMessageTableName,new Object[]{regularId,message.getMsgId()});
                        if(!ok5) throw new RuntimeException("保存规则失败！");
                    }
                }
            }
            if (ok&&ok2&&ok3&&ok4&&ok5)
                sqLiteUtils.getConnection().commit();
        }catch (Exception e){
            logger.error("添加规则:"+regularId+"失败！");
            sqLiteUtils.getConnection().rollback();
            return false;
        }finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean saveInMessage(InMessage inMessage) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            //插入一条tcInMessage
            ok = sqLiteUtils.insert(SysConfig.inMessageTableName,new Object[]{inMessage.getMsgId(),inMessage.getOpenid(),
            inMessage.getHasReply(),inMessage.getIsFavorite(),inMessage.getIsKeyword()});
            if(!ok) throw new CommonException("保存用户:"+inMessage.getOpenid()+"消息失败");
            //保存消息
            ok = sqLiteUtils.insert(SysConfig.messageTableName,new Object[]{inMessage.getMsgId(),inMessage.getMsgType(),
            inMessage.getContent(),inMessage.getMedia_id(),inMessage.getCreateTime()});
            if(ok)
                sqLiteUtils.getConnection().commit();
        }catch (Exception  e){
            logger.error(e.getMessage());
        }finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }
}
