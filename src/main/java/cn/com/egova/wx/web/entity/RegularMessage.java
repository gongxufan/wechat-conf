package cn.com.egova.wx.web.entity;

import cn.com.egova.wx.sdk.msg.out.News;

import java.util.List;

/**
 * Created by gongxufan on 2016/9/29.
 */
public class RegularMessage {
    private String regularId;
    private String regularName;
    private int replyAll;
    private List<Keyword> keywordList;
    private List<Message> messageList;
    private List<Article> newsList;
    private int totalTextMsg;
    private int totalImgMsg;
    private int totalTxtImgMsg;

    public int getReplyAll() {
        return replyAll;
    }

    public void setReplyAll(int replyAll) {
        this.replyAll = replyAll;
    }

    public int getTotalTextMsg() {
        return totalTextMsg;
    }

    public void setTotalTextMsg(int totalTextMsg) {
        this.totalTextMsg = totalTextMsg;
    }

    public int getTotalImgMsg() {
        return totalImgMsg;
    }

    public void setTotalImgMsg(int totalImgMsg) {
        this.totalImgMsg = totalImgMsg;
    }

    public int getTotalTxtImgMsg() {
        return totalTxtImgMsg;
    }

    public void setTotalTxtImgMsg(int totalTxtImgMsg) {
        this.totalTxtImgMsg = totalTxtImgMsg;
    }

    public List<Article> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<Article> newsList) {
        this.newsList = newsList;
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
