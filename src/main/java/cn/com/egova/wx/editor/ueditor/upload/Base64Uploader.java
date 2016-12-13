package cn.com.egova.wx.editor.ueditor.upload;

import cn.com.egova.wx.editor.ueditor.PathFormat;
import cn.com.egova.wx.editor.ueditor.define.BaseState;
import cn.com.egova.wx.editor.ueditor.define.FileType;
import cn.com.egova.wx.editor.ueditor.define.State;
import org.apache.commons.codec.binary.Base64;

import java.util.Map;

public final class Base64Uploader {
    public Base64Uploader() {
    }

    public static State save(String content, Map<String, Object> conf) {
        byte[] data = decode(content);
        long maxSize = ((Long)conf.get("maxSize")).longValue();
        if(!validSize(data, maxSize)) {
            return new BaseState(false, 1);
        } else {
            String suffix = FileType.getSuffix("JPG");
            String savePath = PathFormat.parse((String)conf.get("savePath"), (String)conf.get("filename"));
            savePath = savePath + suffix;
            String physicalPath = (String)conf.get("rootPath") + savePath;
            State storageState = StorageManager.saveBinaryFile(data, physicalPath);
            if(storageState.isSuccess()) {
                storageState.putInfo("url", PathFormat.format(savePath));
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", "");
            }

            return storageState;
        }
    }

    private static byte[] decode(String content) {
        return Base64.decodeBase64(content);
    }

    private static boolean validSize(byte[] data, long length) {
        return (long)data.length <= length;
    }
}