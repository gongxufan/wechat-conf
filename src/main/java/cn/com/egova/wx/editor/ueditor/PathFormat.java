package cn.com.egova.wx.editor.ueditor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathFormat {
    private static final String TIME = "time";
    private static final String FULL_YEAR = "yyyy";
    private static final String YEAR = "yy";
    private static final String MONTH = "mm";
    private static final String DAY = "dd";
    private static final String HOUR = "hh";
    private static final String MINUTE = "ii";
    private static final String SECOND = "ss";
    private static final String RAND = "rand";
    private static Date currentDate = null;

    public PathFormat() {
    }

    public static String parse(String input) {
        Pattern pattern = Pattern.compile("\\{([^\\}]+)\\}", 2);
        Matcher matcher = pattern.matcher(input);
        currentDate = new Date();
        StringBuffer sb = new StringBuffer();

        while(matcher.find()) {
            matcher.appendReplacement(sb, getString(matcher.group(1)));
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String format(String input) {
        return input.replace("\\", "/");
    }

    public static String parse(String input, String filename) {
        Pattern pattern = Pattern.compile("\\{([^\\}]+)\\}", 2);
        Matcher matcher = pattern.matcher(input);
        String matchStr = null;
        currentDate = new Date();
        StringBuffer sb = new StringBuffer();

        while(matcher.find()) {
            matchStr = matcher.group(1);
            if(matchStr.indexOf("filename") != -1) {
                filename = filename.replace("$", "\\$").replaceAll("[\\/:*?\"<>|]", "");
                matcher.appendReplacement(sb, filename);
            } else {
                matcher.appendReplacement(sb, getString(matchStr));
            }
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String getString(String pattern) {
        pattern = pattern.toLowerCase();
        return pattern.indexOf("time") != -1?getTimestamp():(pattern.indexOf("yyyy") != -1?getFullYear():(pattern.indexOf("yy") != -1?getYear():(pattern.indexOf("mm") != -1?getMonth():(pattern.indexOf("dd") != -1?getDay():(pattern.indexOf("hh") != -1?getHour():(pattern.indexOf("ii") != -1?getMinute():(pattern.indexOf("ss") != -1?getSecond():(pattern.indexOf("rand") != -1?getRandom(pattern):pattern))))))));
    }

    private static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    private static String getFullYear() {
        return (new SimpleDateFormat("yyyy")).format(currentDate);
    }

    private static String getYear() {
        return (new SimpleDateFormat("yy")).format(currentDate);
    }

    private static String getMonth() {
        return (new SimpleDateFormat("MM")).format(currentDate);
    }

    private static String getDay() {
        return (new SimpleDateFormat("dd")).format(currentDate);
    }

    private static String getHour() {
        return (new SimpleDateFormat("HH")).format(currentDate);
    }

    private static String getMinute() {
        return (new SimpleDateFormat("mm")).format(currentDate);
    }

    private static String getSecond() {
        return (new SimpleDateFormat("ss")).format(currentDate);
    }

    private static String getRandom(String pattern) {
        boolean length = false;
        pattern = pattern.split(":")[1].trim();
        int length1 = Integer.parseInt(pattern);
        return String.valueOf(Math.random()).replace(".", "").substring(0, length1);
    }

    public static void main(String[] args) {
    }
}