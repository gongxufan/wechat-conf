package cn.com.egova.wx.sdk.msg.in.event;

/**
 * 微信会员卡激活接口
 * <pre>
 * &lt;xml&gt;&lt;ToUserName&gt;&lt;![CDATA[gh_7638cbc70355]]&gt;&lt;/ToUserName&gt;
 * &lt;FromUserName&gt;&lt;![CDATA[o_CBes-OUGtQ4vxd_7r5-p5QRRXU]]&gt;&lt;/FromUserName&gt;
 * &lt;CreateTime&gt;1462420171&lt;/CreateTime&gt;
 * &lt;MsgType&gt;&lt;![CDATA[event]]&gt;&lt;/MsgType&gt;
 * &lt;Event&gt;&lt;![CDATA[submit_membercard_user_info]]&gt;&lt;/Event&gt;
 * &lt;CardId&gt;&lt;![CDATA[p_CBes55910LQGAOStjVKaTChpsg]]&gt;&lt;/CardId&gt;
 * &lt;UserCardCode&gt;&lt;![CDATA[777670435071]]&gt;&lt;/UserCardCode&gt;
 * &lt;/xml&gt;
 * </pre>
 */
public class InSubmitMemberCardEvent extends EventInMsg {
    public static final String EVENT = "submit_membercard_user_info";

    private String cardId;
    private String userCardCode;

    public InSubmitMemberCardEvent(String toUserName, String fromUserName, Integer createTime, String msgType, String event) {
        super(toUserName, fromUserName, createTime, msgType, event);
    }

    public String getUserCardCode() {
        return userCardCode;
    }

    public void setUserCardCode(String userCardCode) {
        this.userCardCode = userCardCode;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
