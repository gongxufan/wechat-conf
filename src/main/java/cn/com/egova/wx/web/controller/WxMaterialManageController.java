package cn.com.egova.wx.web.controller;

import cn.com.egova.wx.sdk.api.MediaArticles;
import cn.com.egova.wx.util.ImageUtil;
import cn.com.egova.wx.web.entity.ImageGroup;
import cn.com.egova.wx.web.entity.Material;
import cn.com.egova.wx.web.service.IMaterialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gongxufan on 2016/8/24.
 */
@Controller
@RequestMapping("/wx/material")
public class WxMaterialManageController {

    @Autowired
    @Qualifier("materialService")
    private IMaterialService materialService;

    @RequestMapping("/initMaterial")
    public ModelAndView initMaterial(String media_id) {
        ModelAndView mv = new ModelAndView("initMaterial");
        mv.addObject("media_id",media_id);
        return mv;
    }

    @RequestMapping("/initImageManage")
    public ModelAndView initImageManage(String media_id) {
        ModelAndView mv = new ModelAndView("imageManage.jsp");
        mv.addObject("media_id",media_id);
        return mv;
    }

    /**
     * 移动分组
     * @param media_id
     * @param group_id
     * @return
     */
    @RequestMapping("/moveGroup")
    public  @ResponseBody Map<String,Object>  moveGroup(String media_id,String group_id) {
        Map<String,Object> map = null;
        boolean ok = false;
        try {
            map = new HashMap<String,Object>();
            ok = materialService.moveGroup(media_id,group_id);
            if(ok) map.put("ok","ok");
            else   map.put("errmsg","移动分组失败");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("errmsg",e.getMessage());
        }
        return map;
    }

    @RequestMapping("/renameImage")
    public  @ResponseBody Map<String,Object>  renameImage(String media_id,String name) {
        Map<String,Object> map = null;
        boolean ok = false;
        try {
            map = new HashMap<String,Object>();
            ok = materialService.renameImage(media_id,name);
            if(ok) map.put("ok","ok");
            else   map.put("errmsg","图片重命名失败");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("errmsg",e.getMessage());
        }
        return map;
    }

    @RequestMapping("/getImageBase64Data")
    public  @ResponseBody Map<String,Object>  getImageBase64Data(@RequestParam("url") String url) {
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            map.put("base64",ImageUtil.encodeImgageToBase64(URLDecoder.decode(url,"utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("errorInfo",e.getMessage());
        }
        return map;
    }
    @RequestMapping("/delGroup")
    public @ResponseBody Map<String,Object> delGroup(String group_id) {
        Map<String,Object> map = null;
        try {
            map = new HashMap<String,Object>();
            boolean ok = false;
            ok = materialService.deleteImgGroup(group_id);
            if(ok) map.put("ok","ok");
            else   map.put("errmsg","删除分组失败");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("errmsg",e.getMessage());
        }
        return map;
    }
    @RequestMapping("/addGroup")
    public @ResponseBody Map<String,Object> addGroup(String group_id,String groupName,String action) {
        Map<String,Object> map = null;
        try {
            if(groupName == null) throw new RuntimeException("分组名不能为空");
            map = new HashMap<String,Object>();
            boolean ok = false;
            if("add".equals(action))
                ok = materialService.createImgGroup(groupName);
            if("update".equals(action)){
                ImageGroup imageGroup = new ImageGroup();
                imageGroup.setGroupName(groupName);
                imageGroup.setGroup_id(group_id);
                ok = materialService.updateGroup(imageGroup);
            }
            if(ok) map.put("ok","ok");
            else   map.put("errmsg","分组修改失败");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("errmsg",e.getMessage());
        }
        return map;
    }
    @RequestMapping("/editor")
    public ModelAndView initMenuManage(String materialJson,String action) {
        ModelAndView mv = null;
        try {
            mv = new ModelAndView("editor");
            if("update".equals(action)){
                List<LinkedHashMap> mapList = new ArrayList<LinkedHashMap >();
                ObjectMapper objectMapper = new ObjectMapper();
                mapList = objectMapper.readValue(URLDecoder.decode(materialJson,"utf-8"),List.class);
                if(mapList != null && mapList.size()>0 ){
                    for(Map m : mapList){
                        m.put("thumb_url",(String)m.get("thumb_url"));
                        m.put("content",((String) m.get("content")).replaceAll("<@@>","\""));
                    }
                }
                mv.addObject("articles",mapList);
            }else{
                List<Material> mediaArticlesList = new ArrayList<Material>();
                Material article = new Material();
                article.setTitle("标题");
                article.setAuthor("");
                article.setShow_cover_pic(0);
                article.setContent("");
                mediaArticlesList.add(article);
                mv.addObject("articles",mediaArticlesList);
            }
            mv.addObject("action",action);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mv;
    }
}
