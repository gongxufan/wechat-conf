package cn.com.egova.wx.web.entity;

/**
 * Created by gongxufan on 2016/9/2.
 */
public class ImageState {
    private String state;
    private String urlBase64;
    private String url;
    private String media_id;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrlBase64() {
        return urlBase64;
    }

    public void setUrlBase64(String urlBase64) {
        this.urlBase64 = urlBase64;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
