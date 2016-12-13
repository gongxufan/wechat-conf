package cn.com.egova.wx.editor.ueditor.upload;

import cn.com.egova.wx.editor.ueditor.PathFormat;
import cn.com.egova.wx.editor.ueditor.define.BaseState;
import cn.com.egova.wx.editor.ueditor.define.FileType;
import cn.com.egova.wx.editor.ueditor.define.State;
import cn.com.egova.wx.sdk.api.ApiResult;
import cn.com.egova.wx.sdk.api.MediaApi;
import cn.com.egova.wx.web.bean.SpringUtil;
import cn.com.egova.wx.web.entity.Image;
import cn.com.egova.wx.web.service.IMaterialService;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BinaryUploader {
    public BinaryUploader() {
    }

    public static final State save(HttpServletRequest request, Map<String, Object> conf) {
        InputStream is = null;
        FileItemStream fileStream = null;
        boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;
        if(!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, 5);
        } else {
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            if(isAjaxUpload) {
                upload.setHeaderEncoding("UTF-8");
            }

            try {
                for(FileItemIterator e = upload.getItemIterator(request); e.hasNext(); fileStream = null) {
                    fileStream = e.next();
                    if(!fileStream.isFormField()) {
                        break;
                    }
                }

                if(fileStream == null) {
                    return new BaseState(false, 7);
                } else {
                    String savePath = (String)conf.get("savePath");
                    String originFileName = fileStream.getName();
                    String suffix = FileType.getSuffixByFilename(originFileName);
                    originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
                    savePath = savePath + suffix;
                    long maxSize = ((Long)conf.get("maxSize")).longValue();
                    //图片仅支持jpg/png格式
                    if(!validType(suffix, (String[])conf.get("allowFiles"))) {
                        return new BaseState(false, 8);
                    } else {
                        savePath = PathFormat.parse(savePath, originFileName);
                        String physicalPath = (String)conf.get("rootPath") + savePath;
                        is = fileStream.openStream();
                        //大小必须在1MB以下
                        if(is.available() > maxSize){
                            return new BaseState(false, 1);
                        }
                        //State storageState = StorageManager.saveFileByInputStream(is, physicalPath, maxSize);
                        State storageState = new BaseState();
                        ApiResult apiResult = MediaApi.addMaterial("image",is,originFileName + suffix);
                        if(apiResult.isSucceed()){
                            JSONObject jsonObject =  JSONObject.fromObject(apiResult.getJson());
                            storageState.putInfo("url", jsonObject.getString("url"));
                            storageState.putInfo("type", suffix);
                            storageState.putInfo("size", is.available());
                            storageState.putInfo("title", String.valueOf(Math.random() * 10000.0D).replace(".", ""));
                            storageState.putInfo("original", originFileName + suffix);
                            //本地图片入库,默认进入“未分组"
                            Image image = new Image();
                            image.setMedia_id(jsonObject.getString("media_id"));
                            image.setUrl(jsonObject.getString("url"));
                            image.setName(originFileName+ suffix);
                            image.setUpdate_time(System.currentTimeMillis());
                            image.setGroup_id("1");
                            IMaterialService materialService = (IMaterialService) SpringUtil.getBean("materialService");
                            materialService.addImage(image);
                        }else{
                            return new BaseState(false, 3);
                        }
                        return storageState;
                    }
                }
            } catch (FileUploadException var14) {
                return new BaseState(false, 6);
            } catch (Exception var15) {
                return new BaseState(false, 4);
            }finally {
                if(is != null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static boolean validType(String type, String[] allowTypes) {
        List list = Arrays.asList(allowTypes);
        return list.contains(type);
    }
}