package cn.com.egova.wx.web.service.impl;

import cn.com.egova.wx.sdk.api.MediaArticles;
import cn.com.egova.wx.util.DBUtils;
import cn.com.egova.wx.util.ImageUtil;
import cn.com.egova.wx.util.SQLiteUtils;
import cn.com.egova.wx.web.bean.SysConfig;
import cn.com.egova.wx.web.entity.Image;
import cn.com.egova.wx.web.entity.ImageGroup;
import cn.com.egova.wx.web.entity.Material;
import cn.com.egova.wx.web.service.IMaterialService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by gongxufan on 2016/8/24.
 */
@Service("materialService")
public class MaterialServiceImpl implements IMaterialService {
    @Override
    public boolean saveMaterial(List<Material> materialList) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            boolean ok = false;
            for (int i = 0; i < materialList.size(); i++) {
                Material n = materialList.get(i);
                //菜单是否存在,存在则更新该记录。
                if (sqLiteUtils.getTableCount(SysConfig.newsTableName, "url", n.getUrl()) > 0) {
                    ok = sqLiteUtils.update(SysConfig.newsTableName, n.getUrl(), "url"
                            , new String[]{"media_id", "title", "thumb_url", "digest","update_time","thumb_media_id"}
                            , new Object[]{n.getMedia_id(), n.getTitle(), n.getThumb_url(), n.getDigest(), n.getUpdate_time(),n.getThumb_media_id()});
                } else {//插入菜单项
                    ok = sqLiteUtils.insert(SysConfig.newsTableName, new Object[]{n.getMedia_id(),
                            n.getTitle(), n.getUrl(), n.getThumb_url(), n.getDigest(), n.getUpdate_time(),n.getThumb_media_id()});
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
    public boolean updateMaterialUpdateTime(String mediaId,List<MediaArticles> mediaArticles) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            Long updateTime = new Date().getTime();
            if((sqLiteUtils.getTableCount(SysConfig.newsUpdateTimeTableName, "media_id", mediaId) > 0))
                ok = sqLiteUtils.update(SysConfig.newsUpdateTimeTableName,mediaId,"media_id",new String[]{"update_time"},new Object[]{updateTime});
            else
                ok = sqLiteUtils.insert(SysConfig.newsUpdateTimeTableName, new Object[]{mediaId,updateTime});
            if (ok) {
                sqLiteUtils.getConnection().commit();
            }
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean updateMaterial(String oldMedia_id, String newMedia_id) throws Exception {
        boolean ok = false;
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            boolean ok1 = false;
            boolean ok2 = false;
            boolean ok3 = false;
            ok1 = sqLiteUtils.delete(SysConfig.newsTableName,"media_id",oldMedia_id);
            //更新菜单关联表
            ok2 = sqLiteUtils.update(SysConfig.menuNewsTableName, oldMedia_id, "media_id",
                        new String[]{"media_id"}, new Object[]{newMedia_id});
            ok3 = sqLiteUtils.delete(SysConfig.newsUpdateTimeTableName,"media_id",oldMedia_id);
            if (ok1 && ok2 && ok3) {
                ok = true;
                sqLiteUtils.getConnection().commit();
            }
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean updateMaterialCover(String media_id,String newUrl,String thumb_url) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            boolean ok = false;
            ok = sqLiteUtils.update(SysConfig.newsTableName, newUrl, "url",
                    new String[]{"thumb_url"}, new Object[]{thumb_url});
            if (ok) {
                sqLiteUtils.getConnection().commit();
            }
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return true;
    }

    @Override
    public boolean createImgGroup(String groupName) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            //当前表中最大值的id
            int maxId = sqLiteUtils.getMaxCol(SysConfig.imageGroupTableName, "group_id");
            ok = sqLiteUtils.insert(SysConfig.imageGroupTableName, new Object[]{++maxId, groupName});
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean deleteImgGroup(String groupId) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        boolean ok2 = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            //删除分组记录
            ok = sqLiteUtils.delete(SysConfig.imageGroupTableName, "group_id", groupId);
            //讲该分组所有的图片移动到“未分组”
            ok2 = sqLiteUtils.update(SysConfig.imageTableName, groupId, "group_id", new String[]{"group_id"}, new Object[]{"1"});
            if (ok && ok2){
                sqLiteUtils.getConnection().commit();
                return true;
            }

        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean deleteMaterial(String media_id) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        boolean ok2 = false;
        boolean ok3 = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            ok = sqLiteUtils.delete(SysConfig.newsTableName, "media_id", media_id);
            ok2 = sqLiteUtils.delete(SysConfig.menuNewsTableName,"media_id",media_id);
            ok3 = sqLiteUtils.delete(SysConfig.newsUpdateTimeTableName,"media_id",media_id);
            if (ok && ok2 && ok3){
                sqLiteUtils.getConnection().commit();
                return true;
            }
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean moveGroup(String media_id, String group_id) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            String[] groups = media_id.split(";");
            if (groups != null && groups.length > 0) {
                for (String s : groups) {
                    ok = sqLiteUtils.executeSql("update " + SysConfig.imageTableName + " set group_id='" + group_id + "' where media_id='" + s + "';");
                    if (!ok) {
                        sqLiteUtils.getConnection().rollback();
                        break;
                    }
                }
            }
            if (ok) sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean deleteImg(String media_id) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            String[] imgs = media_id.split(";");
            if (imgs != null && imgs.length > 0) {
                for (String s : imgs) {
                    ok = sqLiteUtils.delete(SysConfig.imageTableName, "media_id", s);
                    if (!ok) {
                        sqLiteUtils.getConnection().rollback();
                        break;
                    }
                }
            }
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean addImage(Image image) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            if (image.getGroup_id() == null || "".equals(image.getGroup_id().trim()))
                image.setGroup_id("1");
            sqLiteUtils.getConnection().setAutoCommit(false);
            if (sqLiteUtils.getTableCount(SysConfig.imageTableName, "media_id", image.getMedia_id()) > 0) {
                ok = sqLiteUtils.update(SysConfig.imageTableName, image.getMedia_id(), "media_id",
                        new String[]{"media_id", "url", "updat_time", "name", "group_id"}, new Object[]{image.getMedia_id(),
                                image.getUrl(), image.getUpdate_time(), image.getName(), image.getGroup_id()});
            } else {
                ok = sqLiteUtils.insert(SysConfig.imageTableName, new Object[]{image.getMedia_id(),
                        image.getUrl(), image.getUpdate_time(), image.getName(), image.getGroup_id()});
            }
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean renameImage(String media_id, String name) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            ok = sqLiteUtils.executeSql("update " + SysConfig.imageTableName + " set name='"+name+"' where media_id='"+media_id+"'");
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean updateGroup(ImageGroup group) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            if (group.getGroup_id() == null || "".equals(group.getGroup_id().trim()))
                return false;
            sqLiteUtils.getConnection().setAutoCommit(false);
            if (sqLiteUtils.getTableCount(SysConfig.imageGroupTableName, "group_id", group.getGroup_id()) > 0) {
                ok = sqLiteUtils.update(SysConfig.imageGroupTableName, group.getGroup_id(), "group_id",
                        new String[]{"group_id", "groupName"}, new Object[]{group.getGroup_id(), group.getGroupName()});
            }
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public String getImageGroup(String id) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        Object o = null;
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            o = sqLiteUtils.selectColumnById(SysConfig.imageTableName, "media_id", id, "group_id");
            sqLiteUtils.getConnection().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return (String) o;
    }

    @Override
    public String getGroupName(String group_id) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        Object o = null;
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            o = sqLiteUtils.selectColumnById(SysConfig.imageGroupTableName, "group_id", group_id, "groupName");
            sqLiteUtils.getConnection().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return (String) o;
    }

    @Override
    public List<ImageGroup> getGroups() throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            List<ImageGroup> imageGroups = new ArrayList<ImageGroup>();
            Object[][] result = sqLiteUtils.selectObject(SysConfig.imageGroupTableName);
            if (result != null && result.length > 0) {
                for (Object[] oa : result) {
                    ImageGroup imageGroup = new ImageGroup();
                    imageGroup.setGroup_id((String) oa[0]);
                    imageGroup.setGroupName((String) oa[1]);
                    int count = sqLiteUtils.getTableCount(SysConfig.imageTableName, "group_id", imageGroup.getGroup_id());
                    imageGroup.setCount(count);
                    imageGroups.add(imageGroup);
                }
            }
            return imageGroups;
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
    }


    @Override
    public List<Image> getImages(String size, String offset, String group_id) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            List<Image> images = new ArrayList<Image>();
            List<List<Object>> result = sqLiteUtils.selectWithPager(SysConfig.imageTableName, new String[]{"group_id"}, new String[]{group_id}, size, offset,"update_time");
            if (result != null && result.size() > 0) {
                for (List<Object> oa : result) {
                    Image img = new Image();
                    img.setMedia_id((String) oa.get(0));
                    img.setUrl((String) oa.get(1));
                    img.setUpdate_time(Long.valueOf((String)(oa.get(2))));
                    img.setName((String) oa.get(3));
                    img.setGroup_id((String) oa.get(4));
                    img.setUrl(img.getUrl());
                    images.add(img);
                }
            }
            return images;
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
    }

    @Override
    public List<Image> getAllImages(String size,String offset) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            List<Image> images = new ArrayList<Image>();
            List<List<Object>> result = sqLiteUtils.selectWithPager(SysConfig.imageTableName, new String[]{"'1'"}
                    ,new String[]{ "1"}, size, offset,"update_time");
            if (result != null && result.size() > 0) {
                for (List<Object> oa : result) {
                    Image img = new Image();
                    img.setMedia_id((String) oa.get(0));
                    img.setUrl((String) oa.get(1));
                    img.setUpdate_time(Long.valueOf((String)(oa.get(2))));
                    img.setName((String) oa.get(3));
                    img.setGroup_id((String) oa.get(4));
                    /*if (img.getUrl() != null)
                        img.setUrl_base64(ImageUtil.encodeImgageToBase64(img.getUrl()));*/
                    images.add(img);
                }
            }
            return images;
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
    }

    @Override
    public int getTotalImages(String group_id) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            return sqLiteUtils.getTableCount(SysConfig.imageTableName, "group_id", group_id);
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
    }

    @Override
    public int getImageNums() throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            return sqLiteUtils.getTableCount(SysConfig.imageTableName);
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
    }

    @Override
    public String getImgViaMediaId(String media_id) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        Object o = null;
        try {
            o = sqLiteUtils.selectColumnById(SysConfig.imageTableName, "media_id", media_id, "url");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return (String) o;
    }

    @Override
    public String getUpdateTimeViaMediaId(String media_id) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        Object o = null;
        try {
            o = sqLiteUtils.selectColumnById(SysConfig.newsUpdateTimeTableName, "media_id", media_id, "update_time");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return (String) o;
    }
}
