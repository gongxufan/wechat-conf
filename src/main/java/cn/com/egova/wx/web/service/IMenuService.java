package cn.com.egova.wx.web.service;

import cn.com.egova.wx.web.entity.Material;
import cn.com.egova.wx.web.entity.Node;

import java.util.List;

/**
 * Created by gongxufan on 2016/7/27.
 */
public interface IMenuService {
    public boolean insertNode(Node n) throws Exception;
    public List<Node> getAllNodes() throws Exception;

    public  Node getNodeByKey(String key) throws Exception;

    public List<Node> getNodesByPId(String pId) throws Exception;
    public List<Material> getMaterial() throws Exception;

    public List<Material> getMaterialByMenuKey(String key) throws Exception;

    public boolean delteNodeById(String id) throws Exception;
    public boolean deleteMenuRel(String id) throws Exception;
    public boolean addMenuRel(String menuId, String media_id) throws Exception;

    public boolean delteNodeByIds(String ids) throws Exception;

    public boolean insertNodeById(String id) throws Exception;

    public boolean updateNodeById(Node n) throws Exception;

    public boolean delteALl() throws Exception;

    public boolean insertNodes(List<Node> nodes) throws Exception;
}

