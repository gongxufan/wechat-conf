package cn.com.egova.wx.web.service.impl;

import cn.com.egova.wx.util.DBUtils;
import cn.com.egova.wx.util.SQLiteUtils;
import cn.com.egova.wx.web.bean.SysConfig;
import cn.com.egova.wx.web.bean.SysInitBean;
import cn.com.egova.wx.web.service.ISysconfigService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gongxufan on 2016/9/1.
 */
@Service("syconfigService")
public class SysconfigServiceImpl implements ISysconfigService {
    @Override
    public boolean saveConfig(String key, String value) throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            //更新配置缓存
            SysInitBean.globConfig.put(key,value);
            if (sqLiteUtils.getTableCount(SysConfig.sysconfigTableName, "keycode", key) > 0) {
                ok = sqLiteUtils.update(SysConfig.sysconfigTableName, key, "keycode",
                        new String[]{"keycode", "value"}, new Object[]{key,value});
            } else {
                ok = sqLiteUtils.insert(SysConfig.sysconfigTableName, new Object[]{key,value});
            }
            if(!ok){
                sqLiteUtils.getConnection().rollback();
                return false;
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
    public boolean saveOrUpdateConfig(String values) throws Exception{
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        boolean ok = false;
        try {
            sqLiteUtils.getConnection().setAutoCommit(false);
            String[] set = values.split(";");
            if(set!=null && set.length>0){
               for(String s : set){
                   String[] ss = s.split(":");
                   String key = ss[0];
                   String value = ss[1];
                   //更新配置缓存
                   SysInitBean.globConfig.put(key,value);
                   if (sqLiteUtils.getTableCount(SysConfig.sysconfigTableName, "keycode", key) > 0) {
                       ok = sqLiteUtils.update(SysConfig.sysconfigTableName, key, "keycode",
                               new String[]{"keycode", "value"}, new Object[]{key,value});
                   } else {
                       ok = sqLiteUtils.insert(SysConfig.sysconfigTableName, new Object[]{key,value});
                   }
                   if(!ok){
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
    public String getKey(String key) throws Exception {
        return null;
    }

    @Override
    public Map<String, String> getAllConfig() throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        Object[][] result = sqLiteUtils.selectObject(SysConfig.sysconfigTableName);
        Map<String, String> map = new HashMap<String,String>();
        if(result != null && result.length > 0){
            for(Object[] oo: result){
                map.put((String)oo[0],(String)oo[1]);
            }
        }
        return map;
    }
}
