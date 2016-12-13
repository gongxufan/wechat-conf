package cn.com.egova.wx.editor.ueditor;

import cn.com.egova.wx.editor.ueditor.define.ActionMap;
import cn.com.egova.wx.editor.ueditor.define.BaseState;
import cn.com.egova.wx.editor.ueditor.define.MultiState;
import cn.com.egova.wx.editor.ueditor.define.State;
import cn.com.egova.wx.editor.ueditor.hunter.FileManager;
import cn.com.egova.wx.editor.ueditor.hunter.ImageHunter;
import cn.com.egova.wx.editor.ueditor.upload.Uploader;
import cn.com.egova.wx.sdk.api.ApiResult;
import cn.com.egova.wx.sdk.api.MenuApi;
import cn.com.egova.wx.util.ImageUtil;
import cn.com.egova.wx.web.bean.SpringUtil;
import cn.com.egova.wx.web.entity.Image;
import cn.com.egova.wx.web.entity.ImageState;
import cn.com.egova.wx.web.entity.OnlineImage;
import cn.com.egova.wx.web.service.IMaterialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActionEnter {
    private HttpServletRequest request = null;
    private String rootPath = null;
    private String contextPath = null;
    private String actionType = null;
    private ConfigManager configManager = null;

    public ActionEnter(HttpServletRequest request, String rootPath) {
        this.request = request;
        this.rootPath = rootPath;
        this.actionType = request.getParameter("action");
        this.contextPath = request.getContextPath();
        this.configManager = ConfigManager.getInstance(this.rootPath, this.contextPath, request.getRequestURI());
    }

    public String exec() {
        String callbackName = this.request.getParameter("callback");
        return callbackName != null?(!this.validCallbackName(callbackName)?(new BaseState(false, 401)).toJSONString():callbackName + "(" + this.invoke() + ");"):this.invoke();
    }

    public String invoke(){
        if(this.actionType != null && ActionMap.mapping.containsKey(this.actionType)) {
            if(this.configManager != null && this.configManager.valid()) {
                State state = null;
                int actionCode = ActionMap.getType(this.actionType);
                Map conf = null;
                switch(actionCode) {
                    case ActionMap.CONFIG:
                        return this.configManager.getAllConfig().toString();
                    case ActionMap.UPLOAD_IMAGE:
                        conf = this.configManager.getConfig(actionCode);
                        state = (new Uploader(this.request, conf)).doExec();
                        break;
                    case ActionMap.UPLOAD_SCRAWL:
                    case ActionMap.UPLOAD_VIDEO:
                    case ActionMap.UPLOAD_FILE:
                        break;
                    case ActionMap.CATCH_IMAGE:
                        conf = this.configManager.getConfig(actionCode);
                        String[] list = this.request.getParameterValues((String)conf.get("fieldName"));
                        state = (new ImageHunter(conf)).capture(list);
                        break;
                    case ActionMap.LIST_FILE:
                        //列出在线图片
                    case ActionMap.LIST_IMAGE:
                        conf = this.configManager.getConfig(actionCode);
                        int start = this.getStartIndex();
                        int size = (Integer) conf.get("count");
                        IMaterialService materialService = (IMaterialService) SpringUtil.getBean("materialService");
                        OnlineImage onlineImage = new OnlineImage();
                        int totalImages = 0;
                        try {
                            List<Image> images = materialService.getAllImages(String.valueOf(size),String.valueOf(start));
                            //图片总数
                            totalImages = materialService.getImageNums();
                            List<ImageState> imageStates = new ArrayList<ImageState>();
                            if(images != null && images.size()>0){
                                for(int i = 0 ; i < images.size();i++){
                                    ImageState base = new ImageState();
                                    String url = images.get(i).getUrl();
                                    base.setMedia_id(images.get(i).getMedia_id());
                                    base.setUrl(url);
                                   /* if(url != null)
                                        base.setUrlBase64(ImageUtil.encodeImgageToBase64(url));*/
                                    imageStates.add(base);
                                    base.setState("SUCCESS");
                                }
                            }
                            onlineImage.setState("SUCCESS");
                            onlineImage.setList(imageStates);
                            onlineImage.setStart(start);
                            onlineImage.setTotal(totalImages);
                            return  new ObjectMapper().writeValueAsString(onlineImage);
                        } catch (Exception e) {
                            state = new BaseState(false);
                            e.printStackTrace();
                        }
                }

                return state.toJSONString();
            } else {
                return (new BaseState(false, 102)).toJSONString();
            }
        } else {
            return (new BaseState(false, 101)).toJSONString();
        }
    }

    public int getStartIndex() {
        String start = this.request.getParameter("start");

        try {
            return Integer.parseInt(start);
        } catch (Exception var3) {
            return 0;
        }
    }

    public boolean validCallbackName(String name) {
        return name.matches("^[a-zA-Z_]+[\\w0-9_]*$");
    }
}
