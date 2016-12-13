package cn.com.egova.wx.web.entity;

/**
 * Created by gongxufan on 2016/8/29.
 */
public class Image {
    private String media_id;
    private String url;
    private String url_base64;
    private long update_time;
    private String name;
    private String groupName;
    private String group_id;

    public String getUrl_base64() {
        return url_base64;
    }

    public void setUrl_base64(String url_base64) {
        this.url_base64 = url_base64;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
