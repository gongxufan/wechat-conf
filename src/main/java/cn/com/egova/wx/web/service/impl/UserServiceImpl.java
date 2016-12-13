package cn.com.egova.wx.web.service.impl;

import cn.com.egova.wx.util.DBUtils;
import cn.com.egova.wx.util.SQLiteUtils;
import cn.com.egova.wx.web.bean.SysConfig;
import cn.com.egova.wx.web.entity.Image;
import cn.com.egova.wx.web.entity.User;
import cn.com.egova.wx.web.entity.UserGroup;
import cn.com.egova.wx.web.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by gongxufan on 2016/9/13.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    @Override
    public List<UserGroup> getUserGroups() throws Exception {
        List<UserGroup> userGroups = new ArrayList<UserGroup>();
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            Vector<Vector<Object>> result = sqLiteUtils.select(SysConfig.userGroupTableName);
            if (result != null && result.size() > 0) {
                for (Vector<Object> oa : result) {
                    UserGroup userGroup = new UserGroup();
                    userGroup.setId((Integer)oa.get(0));
                    userGroup.setName((String)oa.get(1));
                    //分组数量统计
                    int count = sqLiteUtils.getTableCountViaSql("select count(*) from "+SysConfig.userTableName
                            + " where subscribe=1 and groupid=" + userGroup.getId());
                    userGroup.setCount(count);
                    userGroups.add(userGroup);
                }
            }
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return  userGroups;
    }

    @Override
    public boolean addUser(User user) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            if (sqLiteUtils.getTableCount(SysConfig.userTableName, "openid", user.getOpenid()) > 0) {
                ok = sqLiteUtils.update(SysConfig.userTableName, user.getOpenid(), "openid",
                        new String[]{"openid", "subscribe", "nickname", "sex", "city", "country",
                                "province", "language", "headimgurl", "subscribe_time", "unionid", "remark", "groupid"},
                        new Object[]{user.getOpenid(), user.getSubscribe(), user.getNickname(), user.getSex(),
                                user.getCity(), user.getCountry(), user.getProvince(), user.getLanguage(),
                                user.getHeadimgurl(), user.getSubscribe_time(), user.getUnionid(),
                                user.getRemark(), user.getGroupid()});
            } else {
                ok = sqLiteUtils.insert(SysConfig.userTableName, new Object[]{user.getOpenid(), user.getSubscribe(), user.getNickname(), user.getSex(),
                        user.getCity(), user.getCountry(), user.getProvince(), user.getLanguage(),
                        user.getHeadimgurl(), user.getSubscribe_time(), user.getUnionid(),
                        user.getRemark(), user.getGroupid()});
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
    public boolean addUserGroup(UserGroup userGroup) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            ok = sqLiteUtils.insert(SysConfig.userGroupTableName, new Object[]{userGroup.getId(), userGroup.getName()});
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean updateUserGroup(int id, String name) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            ok = sqLiteUtils.update(SysConfig.userGroupTableName,String.valueOf(id),"groupid",new String[]{"groupName"},new Object[]{name});
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean deleteGroup(String groupId) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            ok = sqLiteUtils.executeSql("delete from " + SysConfig.userGroupTableName + " where groupid=" + groupId);
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    /**
     * 用户分页查询
     *
     * @param size
     * @param offset
     * @param orderBy
     * @param key      过滤关键字
     * @param keyValue 为Null表示查询所有用户 非null则查询该用户组的用户
     * @return
     * @throws Exception
     */
    @Override
    public List<User> fetchUserList(String size, String offset, String orderBy,
                                    String key, String keyValue, String key2, String value2) throws Exception {
        if (keyValue == null) {
            keyValue = "1";
            key = "'1'";
        }
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            List<User> users = new ArrayList<User>();
            List<List<Object>> result = sqLiteUtils.selectWithPager(SysConfig.userTableName, new String[]{key, "subscribe", key2}, new String[]{keyValue, "1", value2}, size, offset, orderBy);
            if (result != null && result.size() > 0) {
                for (List<Object> oa : result) {
                    User user = new User();
                    user.setOpenid((String) oa.get(0));
                    user.setSubscribe((Integer) oa.get(1));
                    user.setNickname((String) oa.get(2));
                    user.setSex((Integer) oa.get(3));
                    user.setCity((String) oa.get(4));
                    user.setCountry((String) oa.get(5));
                    user.setProvince((String) oa.get(6));
                    user.setLanguage((String) oa.get(7));
                    user.setHeadimgurl((String) oa.get(8));
                    user.setSubscribe_time((String) oa.get(9));
                    user.setUnionid((String) oa.get(10));
                    user.setRemark((String) oa.get(11));
                    if (user.getRemark() != null) {
                        if (user.getRemark().trim().equals("")) {
                            user.setRemark(null);
                        }
                    }
                    user.setGroupid((Integer) oa.get(12));
                    users.add(user);
                }
            }
            return users;
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
    }

    /**
     * 取消订阅，更改订阅状态
     *
     * @param openId
     * @return
     */
    @Override
    public boolean deSubscribe(String openId) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            ok = sqLiteUtils.update(SysConfig.userTableName, openId, "openid", new String[]{"subscribe"}, new Object[]{0});
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public int getTotalUsers(String groupId) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        try {
            String key = "groupid";
            if (groupId == null) {
                key = "1";
                groupId = "1";
            }
            return sqLiteUtils.getTableCountViaSql("select count(*) from "
                    +SysConfig.userTableName + " where subscribe=1 and " + key + "=" +groupId);
        } finally {
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
    }

    @Override
    public boolean batchUpdateUser(List<String> openidList, String groupid) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            if (openidList != null && openidList.size() > 0) {
                for (String s : openidList) {
                    ok = sqLiteUtils.update(SysConfig.userTableName, s, "openid", new String[]{"groupid"}, new Object[]{groupid});
                    if (!ok) {
                        sqLiteUtils.getConnection().rollback();
                        return false;
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
    public boolean updateRemark(String openId, String remark) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            ok = sqLiteUtils.update(SysConfig.userTableName, openId, "openid", new String[]{"remark"}, new Object[]{remark});
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }

    @Override
    public boolean batchUpdateUserViaGroupId(String deletedGroupId, String newGroupId) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            ok = sqLiteUtils.update(SysConfig.userTableName, deletedGroupId, "groupid", new String[]{"groupid"}, new Object[]{newGroupId});
            if (ok)
                sqLiteUtils.getConnection().commit();
        } finally {
            sqLiteUtils.getConnection().setAutoCommit(true);
            DBUtils.close(sqLiteUtils.getConnection(), sqLiteUtils.getStmt());
        }
        return ok;
    }
}
