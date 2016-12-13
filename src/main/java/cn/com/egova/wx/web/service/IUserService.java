package cn.com.egova.wx.web.service;

import cn.com.egova.wx.web.entity.User;
import cn.com.egova.wx.web.entity.UserGroup;

import java.util.List;

/**
 * Created by gongxufan on 2016/9/13.
 */
public interface IUserService {
    public boolean addUser(User user) throws Exception;
    public List<User> fetchUserList(String size, String offset, String orderBy,
                                    String key,String keyValue,String key2,String value2) throws Exception;
    public boolean deSubscribe(String openId) throws Exception;
    public boolean updateRemark(String openId,String remark) throws Exception;
    public int getTotalUsers(String groupId) throws Exception;
    public boolean batchUpdateUser(List<String> openidList,String groupid) throws Exception;
    public boolean batchUpdateUserViaGroupId(String deletedGroupId,String newGroupId) throws Exception;

    public boolean addUserGroup(UserGroup userGroup) throws Exception;
    public boolean updateUserGroup(int id,String name) throws Exception;
    public boolean deleteGroup(String groupId) throws Exception;
    public List<UserGroup> getUserGroups() throws Exception;
}
