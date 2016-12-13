package cn.com.egova.wx.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 微信后台素材实体类
 * Created by gongxufan on 2016/8/10.
 */
public class Material {
    private String menuId;
    private String media_id;
    private String title;
    private String url;
    private String thumb_url;
    private String thumb_url_base64;
    private String digest;
    private long update_time;

    private int show_cover_pic;
    private String author;
    private String content;
    private String content_source_url;
    private String name;
    private String thumb_media_id;

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public String getThumb_url_base64() {
        return thumb_url_base64;
    }

    public void setThumb_url_base64(String thumb_url_base64) {
        this.thumb_url_base64 = thumb_url_base64;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }

    public int getShow_cover_pic() {
        return show_cover_pic;
    }

    public void setShow_cover_pic(int show_cover_pic) {
        this.show_cover_pic = show_cover_pic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent_source_url() {
        return content_source_url;
    }

    public void setContent_source_url(String content_source_url) {
        this.content_source_url = content_source_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }
}
