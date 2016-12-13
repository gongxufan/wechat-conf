package cn.com.egova.wx.web.entity;

import java.util.List;

/**
 * Created by gongxufan on 2016/10/1.
 */
public class RegularInfo {
    private String regularId;
    private String regularName;
    private int replyAll;
    private List<Keyword> keywordList;
    private List<Message> messageList;

    public int getReplyAll() {
        return replyAll;
    }

    public void setReplyAll(int replyAll) {
        this.replyAll = replyAll;
    }

    public String getRegularId() {
        return regularId;
    }

    public void setRegularId(String regularId) {
        this.regularId = regularId;
    }

    public String getRegularName() {
        return regularName;
    }

    public void setRegularName(String regularName) {
        this.regularName = regularName;
    }

    public List<Keyword> getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(List<Keyword> keywordList) {
        this.keywordList = keywordList;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
