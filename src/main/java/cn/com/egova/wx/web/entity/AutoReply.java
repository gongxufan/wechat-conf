package cn.com.egova.wx.web.entity;

/**
 * Created by gongxufan on 2016/9/22.
 */
public class AutoReply {
    private String replyId;
    private int replyType;
    private String msgId;

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public int getReplyType() {
        return replyType;
    }

    public void setReplyType(int replyType) {
        this.replyType = replyType;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
