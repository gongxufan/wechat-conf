package cn.com.egova.wx.editor.ueditor.define;

import java.util.HashMap;
import java.util.Map;

public final class ActionMap {
    public static final Map<String, Integer> mapping = new HashMap() {
        {
            this.put("config", Integer.valueOf(0));
            this.put("uploadimage", Integer.valueOf(1));
            this.put("uploadscrawl", Integer.valueOf(2));
            this.put("uploadvideo", Integer.valueOf(3));
            this.put("uploadfile", Integer.valueOf(4));
            this.put("catchimage", Integer.valueOf(5));
            this.put("listfile", Integer.valueOf(6));
            this.put("listimage", Integer.valueOf(7));
        }
    };
    public static final int CONFIG = 0;
    public static final int UPLOAD_IMAGE = 1;
    public static final int UPLOAD_SCRAWL = 2;
    public static final int UPLOAD_VIDEO = 3;
    public static final int UPLOAD_FILE = 4;
    public static final int CATCH_IMAGE = 5;
    public static final int LIST_FILE = 6;
    public static final int LIST_IMAGE = 7;

    public ActionMap() {
    }

    public static int getType(String key) {
        return ((Integer)mapping.get(key)).intValue();
    }
}
