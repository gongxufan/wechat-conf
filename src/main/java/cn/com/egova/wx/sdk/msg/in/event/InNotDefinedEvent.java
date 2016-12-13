package cn.com.egova.wx.sdk.msg.in.event;

/**
 * 没有找到适配类型时的事件
 */
public class InNotDefinedEvent extends EventInMsg {
    public InNotDefinedEvent(String toUserName, String fromUserName, Integer createTime, String msgType, String event) {
        super(toUserName, fromUserName, createTime, msgType, event);
    }
}
