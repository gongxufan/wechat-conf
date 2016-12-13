package cn.com.egova.wx.web.entity;

import java.util.List;

/**
 * Created by gongxufan on 2016/8/30.
 */
public class ArticlesWrapper {
    private List<Material> news_item;
    private long create_time;
    private long update_time;

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public List<Material> getNews_item() {
        return news_item;
    }

    public void setNews_item(List<Material> news_item) {
        this.news_item = news_item;
    }
}
