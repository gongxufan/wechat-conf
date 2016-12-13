package cn.com.egova.wx.editor.ueditor.define;

import java.util.HashMap;
import java.util.Map;

public class MIMEType {
    public static final Map<String, String> types = new HashMap() {
        {
            this.put("image/gif", ".gif");
            this.put("image/jpeg", ".jpg");
            this.put("image/jpg", ".jpg");
            this.put("image/png", ".png");
            this.put("image/bmp", ".bmp");
        }
    };

    public MIMEType() {
    }

    public static String getSuffix(String mime) {
        return (String)types.get(mime);
    }
}
