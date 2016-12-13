package cn.com.egova.wx.editor.ueditor.hunter;

import cn.com.egova.wx.editor.ueditor.PathFormat;
import cn.com.egova.wx.editor.ueditor.define.BaseState;
import cn.com.egova.wx.editor.ueditor.define.MIMEType;
import cn.com.egova.wx.editor.ueditor.define.MultiState;
import cn.com.egova.wx.editor.ueditor.define.State;
import cn.com.egova.wx.editor.ueditor.upload.StorageManager;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ImageHunter {
    private String filename = null;
    private String savePath = null;
    private String rootPath = null;
    private List<String> allowTypes = null;
    private long maxSize = -1L;
    private List<String> filters = null;

    public ImageHunter(Map<String, Object> conf) {
        this.filename = (String)conf.get("filename");
        this.savePath = (String)conf.get("savePath");
        this.rootPath = (String)conf.get("rootPath");
        this.maxSize = ((Long)conf.get("maxSize")).longValue();
        this.allowTypes = Arrays.asList((String[])conf.get("allowFiles"));
        this.filters = Arrays.asList((String[])conf.get("filter"));
    }

    public State capture(String[] list) {
        MultiState state = new MultiState(true);
        String[] var6 = list;
        int var5 = list.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String source = var6[var4];
            state.addState(this.captureRemoteData(source));
        }

        return state;
    }

    public State captureRemoteData(String urlStr) {
        HttpURLConnection connection = null;
        URL url = null;
        String suffix = null;

        try {
            url = new URL(urlStr);
            if(!this.validHost(url.getHost())) {
                return new BaseState(false, 201);
            } else {
                connection = (HttpURLConnection)url.openConnection();
                connection.setInstanceFollowRedirects(true);
                connection.setUseCaches(true);
                if(!this.validContentState(connection.getResponseCode())) {
                    return new BaseState(false, 202);
                } else {
                    suffix = MIMEType.getSuffix(connection.getContentType());
                    if(!this.validFileType(suffix)) {
                        return new BaseState(false, 8);
                    } else if(!this.validFileSize(connection.getContentLength())) {
                        return new BaseState(false, 1);
                    } else {
                        String e = this.getPath(this.savePath, this.filename, suffix);
                        String physicalPath = this.rootPath + e;
                        State state = StorageManager.saveFileByInputStream(connection.getInputStream(), physicalPath);
                        if(state.isSuccess()) {
                            state.putInfo("url", PathFormat.format(e));
                            state.putInfo("source", urlStr);
                        }

                        return state;
                    }
                }
            }
        } catch (Exception var8) {
            return new BaseState(false, 203);
        }
    }

    private String getPath(String savePath, String filename, String suffix) {
        return PathFormat.parse(savePath + suffix, filename);
    }

    private boolean validHost(String hostname) {
        try {
            InetAddress e = InetAddress.getByName(hostname);
            if(e.isSiteLocalAddress()) {
                return false;
            }
        } catch (UnknownHostException var3) {
            return false;
        }

        return !this.filters.contains(hostname);
    }

    private boolean validContentState(int code) {
        return 200 == code;
    }

    private boolean validFileType(String type) {
        return this.allowTypes.contains(type);
    }

    private boolean validFileSize(int size) {
        return (long)size < this.maxSize;
    }
}
