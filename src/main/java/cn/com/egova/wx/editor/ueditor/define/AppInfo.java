package cn.com.egova.wx.editor.ueditor.define;

import java.util.HashMap;
import java.util.Map;

public final class AppInfo {
    public static final int SUCCESS = 0;
    public static final int MAX_SIZE = 1;
    public static final int PERMISSION_DENIED = 2;
    public static final int FAILED_CREATE_FILE = 3;
    public static final int IO_ERROR = 4;
    public static final int NOT_MULTIPART_CONTENT = 5;
    public static final int PARSE_REQUEST_ERROR = 6;
    public static final int NOTFOUND_UPLOAD_DATA = 7;
    public static final int NOT_ALLOW_FILE_TYPE = 8;
    public static final int INVALID_ACTION = 101;
    public static final int CONFIG_ERROR = 102;
    public static final int PREVENT_HOST = 201;
    public static final int CONNECTION_ERROR = 202;
    public static final int REMOTE_FAIL = 203;
    public static final int NOT_DIRECTORY = 301;
    public static final int NOT_EXIST = 302;
    public static final int ILLEGAL = 401;
    public static Map<Integer, String> info = new HashMap() {
        {
            this.put(Integer.valueOf(0), "SUCCESS");
            this.put(Integer.valueOf(101), "无效的Action");
            this.put(Integer.valueOf(102), "配置文件初始化失败");
            this.put(Integer.valueOf(203), "抓取远程图片失败");
            this.put(Integer.valueOf(201), "被阻止的远程主机");
            this.put(Integer.valueOf(202), "远程连接出错");
            this.put(Integer.valueOf(1), "文件大小超出限制");
            this.put(Integer.valueOf(2), "权限不足");
            this.put(Integer.valueOf(3), "创建文件失败");
            this.put(Integer.valueOf(4), "IO错误");
            this.put(Integer.valueOf(5), "上传表单不是multipart/form-data类型");
            this.put(Integer.valueOf(6), "解析上传表单错误");
            this.put(Integer.valueOf(7), "未找到上传数据");
            this.put(Integer.valueOf(8), "不允许的文件类型");
            this.put(Integer.valueOf(301), "指定路径不是目录");
            this.put(Integer.valueOf(302), "指定路径并不存在");
            this.put(Integer.valueOf(401), "Callback参数名不合法");
        }
    };

    public AppInfo() {
    }

    public static String getStateInfo(int key) {
        return (String)info.get(Integer.valueOf(key));
    }
}