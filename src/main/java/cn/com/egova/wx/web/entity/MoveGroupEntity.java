package cn.com.egova.wx.web.entity;

import java.util.List;

/**
 * Created by gongxufan on 2016/9/18.
 */
public class MoveGroupEntity {
    private String to_groupid;
    private List<String> openid_list;

    public String getTo_groupid() {
        return to_groupid;
    }

    public void setTo_groupid(String to_groupid) {
        this.to_groupid = to_groupid;
    }

    public List<String> getOpenid_list() {
        return openid_list;
    }

    public void setOpenid_list(List<String> openid_list) {
        this.openid_list = openid_list;
    }
}
