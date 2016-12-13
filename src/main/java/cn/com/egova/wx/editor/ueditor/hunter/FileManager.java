package cn.com.egova.wx.editor.ueditor.hunter;

import cn.com.egova.wx.editor.ueditor.ConfigManager;
import cn.com.egova.wx.editor.ueditor.PathFormat;
import cn.com.egova.wx.editor.ueditor.define.BaseState;
import cn.com.egova.wx.editor.ueditor.define.MultiState;
import cn.com.egova.wx.editor.ueditor.define.State;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class FileManager {
    private String dir = null;
    private String rootPath = null;
    private String[] allowFiles = null;
    private int count = 0;

    public FileManager(Map<String, Object> conf) {
        this.rootPath = (String)conf.get("rootPath");
        this.dir = this.rootPath + (String)conf.get("dir");
        this.allowFiles = this.getAllowFiles(conf.get("allowFiles"));
        this.count = ((Integer)conf.get("count")).intValue();
    }

    public State listFile(int index) {
        //读取本地图片数据库
        File dir = new File(this.dir);
        Object state = null;
        if(!dir.exists()) {
            return new BaseState(false, 302);
        } else if(!dir.isDirectory()) {
            return new BaseState(false, 301);
        } else {
            Collection list = FileUtils.listFiles(dir, this.allowFiles, true);
            if(index >= 0 && index <= list.size()) {
                Object[] fileList = Arrays.copyOfRange(list.toArray(), index, index + this.count);
                state = this.getState(fileList);
            } else {
                state = new MultiState(true);
            }

            ((State)state).putInfo("start", (long)index);
            ((State)state).putInfo("total", (long)list.size());
            return (State)state;
        }
    }

    private State getState(Object[] files) {
        MultiState state = new MultiState(true);
        BaseState fileState = null;
        File file = null;
        Object[] var8 = files;
        int var7 = files.length;

        for(int var6 = 0; var6 < var7; ++var6) {
            Object obj = var8[var6];
            if(obj == null) {
                break;
            }

            file = (File)obj;
            fileState = new BaseState(true);
            fileState.putInfo("url", PathFormat.format(this.getPath(file)));
            state.addState(fileState);
        }

        return state;
    }

    private String getPath(File file) {
        String path = file.getAbsolutePath();
        path = path.replace("\\", "/");
        System.out.println(path.substring(this.rootPath.length()));
        return ConfigManager.contextPath + path.replace(this.rootPath, "/");
    }

    private String[] getAllowFiles(Object fileExt) {
        String[] exts = null;
        String ext = null;
        if(fileExt == null) {
            return new String[0];
        } else {
            exts = (String[])fileExt;
            int i = 0;

            for(int len = exts.length; i < len; ++i) {
                ext = exts[i];
                exts[i] = ext.replace(".", "");
            }

            return exts;
        }
    }
}