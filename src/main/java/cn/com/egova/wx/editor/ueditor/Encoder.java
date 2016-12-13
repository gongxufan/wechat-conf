package cn.com.egova.wx.editor.ueditor;

public class Encoder {
    public Encoder() {
    }

    public static String toUnicode(String input) {
        StringBuilder builder = new StringBuilder();
        char[] chars = input.toCharArray();
        char[] var6 = chars;
        int var5 = chars.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            char ch = var6[var4];
            if(ch < 256) {
                builder.append(ch);
            } else {
                builder.append("\\u" + Integer.toHexString(ch & '\uffff'));
            }
        }

        return builder.toString();
    }
}
