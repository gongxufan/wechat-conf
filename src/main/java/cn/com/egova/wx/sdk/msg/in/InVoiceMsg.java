/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (handler@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package cn.com.egova.wx.sdk.msg.in;

/**
 * <pre>
 接收语音消息
 &lt;xml&gt;
 &lt;ToUserName&gt;&lt;![CDATA[toUser]]&gt;&lt;/ToUserName&gt;
 &lt;FromUserName&gt;&lt;![CDATA[fromUser]]&gt;&lt;/FromUserName&gt;
 &lt;CreateTime&gt;1357290913&lt;/CreateTime&gt;
 &lt;MsgType&gt;&lt;![CDATA[voice]]&gt;&lt;/MsgType&gt;
 &lt;MediaId&gt;&lt;![CDATA[media_id]]&gt;&lt;/MediaId&gt;
 &lt;Format&gt;&lt;![CDATA[Format]]&gt;&lt;/Format&gt;
 &lt;MsgId&gt;1234567890123456&lt;/MsgId&gt;
 &lt;/xml&gt;
 </pre>
 */
public class InVoiceMsg extends InMsg {

    private String mediaId;
    private String format;
    private String msgId;

    public InVoiceMsg(String toUserName, String fromUserName, Integer createTime, String msgType) {
        super(toUserName, fromUserName, createTime, msgType);
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}





