package cn.com.egova.wx.web.entity;

import java.util.List;

/**
 * Created by gongxufan on 2016/9/2.
 */
public class OnlineImage {
    private String state;
    private int total;
    private int start;
    private List<ImageState> list;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<ImageState> getList() {
        return list;
    }

    public void setList(List<ImageState> list) {
        this.list = list;
    }
}
