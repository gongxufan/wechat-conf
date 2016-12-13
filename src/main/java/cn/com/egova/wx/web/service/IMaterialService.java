package cn.com.egova.wx.web.service;

import cn.com.egova.wx.sdk.api.MediaArticles;
import cn.com.egova.wx.sdk.msg.out.News;
import cn.com.egova.wx.web.entity.Image;
import cn.com.egova.wx.web.entity.ImageGroup;
import cn.com.egova.wx.web.entity.Material;

import java.util.List;

/**
 * Created by gongxufan on 2016/8/24.
 */
public interface IMaterialService {
    public boolean saveMaterial(List<Material> materialList) throws Exception;
    public boolean createImgGroup(String groupName) throws Exception;
    public boolean deleteImgGroup(String groupId) throws Exception;
    public boolean deleteImg(String media_id) throws Exception;
    public boolean addImage(Image image) throws Exception;
    public boolean renameImage(String media_id, String name) throws Exception;
    public boolean updateGroup(ImageGroup group) throws Exception;

    public String  getImageGroup(String id)  throws Exception;
    public String  getGroupName(String group_id) throws Exception;

    public List<ImageGroup> getGroups() throws Exception;
    public List<Image> getImages(String keyvalue, String size, String offset) throws Exception;
    public List<Image> getAllImages(String size, String offset) throws Exception;
    public int getImageNums() throws Exception;
    public int getTotalImages(String group_id) throws Exception;

    public boolean moveGroup(String media_id, String group_id) throws Exception;

    public boolean updateMaterial(String oldMedia_id, String newMedia_id) throws Exception;
    public boolean updateMaterialUpdateTime(String mediaId,List<MediaArticles> mediaArticles) throws Exception;
    public boolean updateMaterialCover(String media_id, String newUrl, String thumb_url) throws Exception;

    public boolean deleteMaterial(String media_id) throws Exception;
    public String getImgViaMediaId(String media_id) throws Exception;
    public String getUpdateTimeViaMediaId(String media_id) throws Exception;
}
