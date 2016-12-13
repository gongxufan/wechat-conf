/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (handler@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package cn.com.egova.wx.sdk.msg.out;

import cn.com.egova.wx.sdk.msg.in.InMsg;

/**
 * <pre>
 回复文本消息
 &lt;xml&gt;
 &lt;ToUserName&gt;&lt;![CDATA[toUser]]&gt;&lt;/ToUserName&gt;
 &lt;FromUserName&gt;&lt;![CDATA[fromUser]]&gt;&lt;/FromUserName&gt;
 &lt;CreateTime&gt;12345678&lt;/CreateTime&gt;
 &lt;MsgType&gt;&lt;![CDATA[text]]&gt;&lt;/MsgType&gt;
 &lt;Content&gt;&lt;![CDATA[你好]]&gt;&lt;/Content&gt;
 &lt;/xml&gt;
 </pre>
 */
public class OutTextMsg extends OutMsg {

    private String content;

    public OutTextMsg() {
        this.msgType = "text";
    }

    public OutTextMsg(InMsg inMsg) {
        super(inMsg);
        this.msgType = "text";
    }

    @Override
    protected void subXml(StringBuilder sb) {
        if (null == content) {
            throw new NullPointerException("content is null");
        }
        sb.append("<Content><![CDATA[").append(content).append("]]></Content>\n");
    }

    public String getContent() {
        return content;
    }

    public OutTextMsg setContent(String content) {
        this.content = content;
        return this;
    }

}


