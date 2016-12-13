package cn.com.egova.wx.web.controller;

import cn.com.egova.wx.web.entity.Material;
import cn.com.egova.wx.web.entity.Node;
import cn.com.egova.wx.web.service.IMenuService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理逻辑处理
 * Created by gongxufan on 2016/7/26.
 */
@Controller
@RequestMapping("/wx")
public class WxMenuManagerController {

    @Autowired
    @Qualifier("menuService")
    private IMenuService menuService;

    @RequestMapping("/initMenuManage")
    public ModelAndView initMenuManage() {
        ModelAndView mv = new ModelAndView("menu");
        return mv;
    }

    @RequestMapping("/getNodesData")
    public
    @ResponseBody
    Map<String,Object> getNodesData() {
        Map<String,Object> datas = new HashMap<String,Object>();
        List<Node> menus = new ArrayList<Node>();
        List<Material> material = new ArrayList<Material>();
        try {
            menus = menuService.getAllNodes();
            material = menuService.getMaterial();
            datas.put("menus",menus);
            datas.put("material",material);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    @RequestMapping("/getMaterial")
    public
    @ResponseBody
    List<Material> getMaterial() {
        List<Material> material = new ArrayList<Material>();
        try {
            material = menuService.getMaterial();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return material;
    }

    @RequestMapping("/deleteRel")
    public
    @ResponseBody
    boolean deleteRel(String menuId) {
        boolean ok = false;
        try {
            ok  = menuService.deleteMenuRel(menuId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @RequestMapping("/addRel")
    public
    @ResponseBody
    boolean addRel(String menuId,String media_id) {
        boolean ok = false;
        try {
            ok  = menuService.addMenuRel(menuId,media_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @RequestMapping("/saveNodesData")
    public
    @ResponseBody
    Map<String, Object> saveNodesData(String nodes) {
        Map<String, Object> map = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = new HashMap<String, Object>();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Node.class);
            List<Node> listNodes = (List<Node>) mapper.readValue(nodes, javaType);
            boolean ok = menuService.insertNodes(listNodes);
            if (ok)
                map.put("ok", "菜单保存成功");
            else
                map.put("errorInfo", "菜单保存失败");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("errorInfo", "菜单保存失败");
        }
        return map;
    }

    @RequestMapping("/saveNode")
    public
    @ResponseBody
    Map<String, Object> saveNode(String id, String pId, String type, String name, String url,
                                 String key, String media_id,String orderId,String msgText) {
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            Node node = new Node();
            node.setId(id);
            node.setpId(pId);
            node.setType(type);
            node.setName(name);
            node.setUrl(url);
            node.setKey(key);
            node.setMedia_id(media_id);
            node.setOrderId(Integer.valueOf(orderId));
            node.setMsgText(msgText);
            boolean ok = menuService.insertNode(node);
            if (ok)
                map.put("ok", "菜单编辑成功");
            else
                map.put("errorInfo", "菜单编辑失败");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("errorInfo", "菜单编辑失败");
        }
        return map;
    }

    @RequestMapping("/deleteNode")
    public
    @ResponseBody
    Map<String, Object> deleteNode(String ids) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<String, Object>();
            boolean ok = menuService.delteNodeByIds(ids);
            if (ok)
                map.put("ok", "菜单删除成功");
            else
                map.put("errorInfo", "菜单删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("errorInfo", "菜单删除失败");
        }
        return map;
    }
}
