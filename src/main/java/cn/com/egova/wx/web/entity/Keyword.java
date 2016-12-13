package cn.com.egova.wx.web.entity;

/**
 * Created by gongxufan on 2016/9/29.
 */
public class Keyword {
    private String keywordId;
    //关键字是否完全匹配,1是完全匹配，0是不完全匹配
    private String keywordName;
    private int mode;

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(String keywordId) {
        this.keywordId = keywordId;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }
}
