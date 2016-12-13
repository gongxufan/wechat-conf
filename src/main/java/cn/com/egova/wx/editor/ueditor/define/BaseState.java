package cn.com.egova.wx.editor.ueditor.define;

import cn.com.egova.wx.editor.ueditor.Encoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BaseState implements State {
    private boolean state = false;
    private String info = null;
    private Map<String, String> infoMap = new HashMap();

    public BaseState() {
        this.state = true;
    }

    public BaseState(boolean state) {
        this.setState(state);
    }

    public BaseState(boolean state, String info) {
        this.setState(state);
        this.info = info;
    }

    public BaseState(boolean state, int infoCode) {
        this.setState(state);
        this.info = AppInfo.getStateInfo(infoCode);
    }

    public boolean isSuccess() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setInfo(int infoCode) {
        this.info = AppInfo.getStateInfo(infoCode);
    }

    public String toJSONString() {
        return this.toString();
    }

    public String toString() {
        String key = null;
        String stateVal = this.isSuccess()? AppInfo.getStateInfo(0):this.info;
        StringBuilder builder = new StringBuilder();
        builder.append("{\"state\": \"" + stateVal + "\"");
        Iterator iterator = this.infoMap.keySet().iterator();

        while(iterator.hasNext()) {
            key = (String)iterator.next();
            builder.append(",\"" + key + "\": \"" + (String)this.infoMap.get(key) + "\"");
        }

        builder.append("}");
        return Encoder.toUnicode(builder.toString());
    }

    public void putInfo(String name, String val) {
        this.infoMap.put(name, val);
    }

    public void putInfo(String name, long val) {
        this.putInfo(name, String.valueOf(val));
    }
}