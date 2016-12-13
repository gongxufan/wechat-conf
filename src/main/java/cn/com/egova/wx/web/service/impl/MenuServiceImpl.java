package cn.com.egova.wx.web.service.impl;

import cn.com.egova.wx.util.ImageUtil;
import cn.com.egova.wx.web.bean.SysConfig;
import cn.com.egova.wx.web.entity.Material;
import cn.com.egova.wx.web.entity.Node;
import cn.com.egova.wx.web.service.IMenuService;
import cn.com.egova.wx.util.SQLiteUtils;
import cn.com.egova.wx.util.DBUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 菜单管理服务类
 * 事物控制在service层手动开启和关闭
 * Created by gongxufan on 2016/7/27.
 */
@Service("menuService")
public class MenuServiceImpl implements IMenuService {

    @Override
    public List<Material> getMaterialByMenuKey(String key) throws Exception {
        String sql = "select t.title,t.digest,t.thumb_url,t.url " +
                " from "+SysConfig.menuTableName+" m ,"+SysConfig.newsTableName+" t where exists(select 1 from  "+SysConfig.menuNewsTableName +
                " mt where mt.menuId=m.id and mt.media_id = t.media_id) and m.keycode='" + key +"'";
        List<Material> nodes = new ArrayList<Material>();
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            Vector<Vector<Object>> result = sqLiteUtils.selectVector(sql);
            if (result != null && result.size() > 0) {
                for (Vector<Object> m : result) {
                    Material n = new Material();
                    n.setTitle(String.valueOf(m.get(0)));
                    n.setDigest(String.valueOf(m.get(1)));
                    n.setThumb_url(String.valueOf(m.get(2)));
                    n.setUrl(String.valueOf(m.get(3)));
                    nodes.add(n);
                }
            }
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return nodes;
    }

    @Override
    public boolean addMenuRel(String menuId,String media_id) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            boolean ok = false;
            ok = sqLiteUtils.insert(SysConfig.menuNewsTableName, new Object[]{menuId, media_id});
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return true;
    }

    @Override
    public boolean deleteMenuRel(String id) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            boolean ok = false;
                if (!(ok = sqLiteUtils.delete(SysConfig.menuNewsTableName, "menuId", id))) return false;
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return true;
    }

    @Override
    public List<Material> getMaterial() throws Exception {
        String sql = "SELECT m.id , t.title,t.url,t.thumb_url,t.digest,t.update_time " +
                "from  "+SysConfig.menuNewsTableName+" mt LEFT JOIN  "+SysConfig.menuTableName+" m on mt.menuId=m.id " +
                "LEFT JOIN  "+SysConfig.newsTableName+ " t on t.media_id = mt.media_id order by t.update_time";
        List<Material> nodes = new ArrayList<Material>();
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            Vector<Vector<Object>> result = sqLiteUtils.selectVector(sql);
            if (result != null && result.size() > 0) {
                for (int l = 0 ;l<result.size();l++) {
                    Vector<Object> m = result.get(l);
                    Material n = new Material();
                    n.setMenuId(String.valueOf(m.get(0)));
                    n.setTitle(String.valueOf(m.get(1)));
                    n.setUrl(String.valueOf(m.get(2)));
                    n.setThumb_url(String.valueOf(m.get(3)));
                    n.setThumb_url_base64(n.getThumb_url());
                    n.setDigest(String.valueOf(m.get(4)));
                    n.setUpdate_time(Long.valueOf((String) m.get(5)));
                    nodes.add(n);
                }
            }
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return nodes;
    }

    @Override
    public List<Node> getAllNodes() throws Exception {
        List<Node> nodes = new ArrayList<Node>();
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            Object[][] result = sqLiteUtils.selectObject(SysConfig.menuTableName);
            if (result != null && result.length > 0) {
                for (Object[] oa : result) {
                    Node n = new Node();
                    n.setId(String.valueOf(oa[0]));
                    n.setpId(String.valueOf(oa[1]));
                    n.setName(String.valueOf(oa[2]));
                    n.setUrl(String.valueOf(oa[3]));
                    n.setKey(String.valueOf(oa[4]));
                    n.setMedia_id(String.valueOf(oa[6]));
                    n.setType(String.valueOf(oa[5]));
                    n.setOrderId((Integer)oa[7]);
                    n.setMsgText(String.valueOf(oa[8]));
                    nodes.add(n);
                }
            }
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return nodes;
    }

    @Override
    public Node getNodeByKey(String key) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        String textMsg = (String)sqLiteUtils.selectColumnById(SysConfig.menuTableName,"keycode",key,"msgText");
        Node n = new Node();
        n.setMsgText(textMsg);
        return n;
    }

    @Override
    public List<Node> getNodesByPId(String pId) throws Exception {
        return null;
    }

    @Override
    public boolean delteNodeById(String id) throws Exception {
        return false;
    }

    @Override
    public boolean delteNodeByIds(String ids) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        String[] idArry = ids.split(",");
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            boolean ok = false;
            for (String s : idArry){
                ok = sqLiteUtils.delete(SysConfig.menuTableName, "id", s);
                ok = sqLiteUtils.delete(SysConfig.menuNewsTableName,"menuId",s);
            }
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return true;
    }

    @Override
    public boolean insertNodeById(String id) throws Exception {
        return false;
    }

    @Override
    public boolean updateNodeById(Node n) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            if (sqLiteUtils.getTableCount(SysConfig.menuTableName, "id", String.valueOf(n.getId())) > 0) {
                sqLiteUtils.getConnection().setAutoCommit(false);
                boolean ok = sqLiteUtils.update(SysConfig.menuTableName, String.valueOf(n.getId()), "id"
                        , new String[]{"id", "pId", "name", "url", "keycode", "media_id", "type","orderId","msgText"}
                        , new Object[]{n.getId(), n.getpId(),
                                n.getName(), n.getUrl(), n.getKey(), n.getMedia_id(), n.getType(),n.getOrderId(),n.getMsgText()});
                if (ok)
                    sqLiteUtils.getConnection().commit();
                return ok;
            }
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return false;
    }

    @Override
    public boolean delteALl() throws Exception {
        return false;
    }

    @Override
    public boolean insertNodes(List<Node> nodes) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            boolean ok = false;
            for (Node n : nodes) {
                //菜单是否存在,存在则更新该记录
                if (sqLiteUtils.getTableCount(SysConfig.menuTableName, "id", String.valueOf(n.getId())) > 0) {
                    ok = sqLiteUtils.update(SysConfig.menuTableName, String.valueOf(n.getId()), "id"
                            , new String[]{"id", "pId", "name", "url", "keycode", "type", "media_id","orderId","msgText"}
                            , new Object[]{n.getId(), n.getpId(),
                                    n.getName(), n.getUrl(), n.getKey(), n.getType(), n.getMedia_id(),n.getOrderId(),n.getMsgText()});
                } else {//插入菜单项
                    ok = sqLiteUtils.insert(SysConfig.menuTableName, new Object[]{n.getId(), n.getpId(),
                            n.getName(), n.getUrl(), n.getKey(), n.getType(), n.getMedia_id(),n.getOrderId()});
                }
                if (!ok) return false;
            }
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return true;
    }

    @Override
    public boolean insertNode(Node n) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            //菜单是否存在,存在则更新该记录
            if (sqLiteUtils.getTableCount(SysConfig.menuTableName, "id", String.valueOf(n.getId())) > 0) {
                ok = sqLiteUtils.update(SysConfig.menuTableName, String.valueOf(n.getId()), "id"
                        , new String[]{"id", "pId", "name", "url", "keycode", "media_id", "type","orderId","msgText"}
                        , new Object[]{n.getId(), n.getpId(), n.getName(), n.getUrl(), n.getKey(), n.getMedia_id(), n.getType(),n.getOrderId(),n.getMsgText()});
            } else {//插入菜单项
                ok = sqLiteUtils.insert(SysConfig.menuTableName, new Object[]{n.getId(), n.getpId(),
                        n.getName(), n.getUrl(), n.getKey(), n.getType(),n.getMedia_id(),n.getOrderId(),n.getMsgText()});
            }
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }
}
