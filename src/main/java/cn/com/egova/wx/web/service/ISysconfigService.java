package cn.com.egova.wx.web.service;

import java.util.Map;

/**
 * Created by gongxufan on 2016/9/1.
 */
public interface ISysconfigService {
    public boolean saveOrUpdateConfig(String values) throws Exception;
    public String getKey(String key) throws Exception;
    public boolean saveConfig(String key,String value) throws Exception;
    public Map<String,String> getAllConfig() throws Exception;
}
