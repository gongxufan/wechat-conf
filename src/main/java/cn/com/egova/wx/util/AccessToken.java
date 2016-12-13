package cn.com.egova.wx.util;

/**
 * Created by gongxufan on 2016/7/26.
 */

public class AccessToken {
    private String token;
    private int expiresIn;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}