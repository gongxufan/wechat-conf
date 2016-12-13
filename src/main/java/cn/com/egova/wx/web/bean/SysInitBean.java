package cn.com.egova.wx.web.bean;

import cn.com.egova.wx.util.SQLiteUtils;
import cn.com.egova.wx.util.DBUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统启动执行一些初始化操作，比如数据表的建立和初始数据
 * Created by gongxufan on 2016/7/28.
 */
@Component
public class SysInitBean implements InitializingBean {
    private final static Logger logger = Logger.getLogger(SysInitBean.class);
    public static Map<String,String> globConfig = new HashMap<String,String>();

    public void afterPropertiesSet() throws Exception {
        initTables();
        initSyscConfig();
    }

    public  void initSyscConfig() throws Exception{
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        Object[][] result = sqLiteUtils.selectObject(SysConfig.sysconfigTableName);
        if(result != null && result.length > 0){
            for(Object[] oo: result){
                globConfig.put((String)oo[0],(String)oo[1]);
            }
        }
        //默认不开启自动回复
        String autoReply = globConfig.get("autoReply");
        if(autoReply == null || "".equals(autoReply.trim()))
            globConfig.put("autoReply","0");
        //默认不开启消息加密
        String encryptMessage = globConfig.get("encryptMessage");
        if(encryptMessage == null || "".equals(encryptMessage.trim()))
            globConfig.put("encryptMessage","0");
    }
    public void initTables() throws Exception {
        SQLiteUtils sqLiteUtils = DBUtils.getDBUtil(SysConfig.dbFile);
        if (!sqLiteUtils.createTable(SysConfig.createMenuSQL)) {
            logger.debug("创建" + SysConfig.createMenuSQL + "表失败...");
        }
        if (!sqLiteUtils.createTable(SysConfig.createNewsSQL)) {
            logger.debug("创建" + SysConfig.newsTableName + "表失败...");
        }
        if (!sqLiteUtils.createTable(SysConfig.createMenuNewsSQL)) {
            logger.debug("创建" + SysConfig.menuNewsTableName + "表失败...");
        }
        if (!sqLiteUtils.createTable(SysConfig.createImageSQL)) {
            logger.debug("创建" + SysConfig.imageTableName + "表失败...");
        }
        if (!sqLiteUtils.createTable(SysConfig.createImageGroupSQL)) {
            logger.debug("创建" + SysConfig.imageGroupTableName + "表失败...");
        }else if(sqLiteUtils.getTableCount(SysConfig.imageGroupTableName) == 0){
            //初始化一条数据
            sqLiteUtils.insert(SysConfig.imageGroupTableName,new Object[]{"1","未分组"});
        }
        if (!sqLiteUtils.createTable(SysConfig.createUserSQL)) {
            logger.debug("创建" + SysConfig.userTableName + "表失败...");
        }
        if (!sqLiteUtils.createTable(SysConfig.createSysconfigSQL)) {
            logger.debug("创建" + SysConfig.sysconfigTableName + "表失败...");
        }
        if (!sqLiteUtils.createTable(SysConfig.createMessageSQL)) {
            logger.debug("创建" + SysConfig.messageTableName + "表失败...");
        }
        if (!sqLiteUtils.createTable(SysConfig.createAutoReplySQL)) {
            logger.debug("创建" + SysConfig.autoReplyTableName + "表失败...");
        }

        if (!sqLiteUtils.createTable(SysConfig.createRegularSQL)) {
            logger.debug("创建" + SysConfig.regularTableName + "表失败...");
        }
        if (!sqLiteUtils.createTable(SysConfig.createRegularKeywordSQL)) {
            logger.debug("创建" + SysConfig.regularKeywordTableName + "表失败...");
        }
        if (!sqLiteUtils.createTable(SysConfig.createRegularMessageSQL)) {
            logger.debug("创建" + SysConfig.regularMessageTableName + "表失败...");
        }
        if (!sqLiteUtils.createTable(SysConfig.createKeywordSQL)) {
            logger.debug("创建" + SysConfig.keywordTableName + "表失败...");
        }
        if (!sqLiteUtils.createTable(SysConfig.createInMessageSQL)) {
            logger.debug("创建" + SysConfig.inMessageTableName + "表失败...");
        }
        if (!sqLiteUtils.createTable(SysConfig.createNewsUpdateTimeSQL)) {
            logger.debug("创建" + SysConfig.newsUpdateTimeTableName + "表失败...");
        }
        if (!sqLiteUtils.createTable(SysConfig.createUserGroupSQL)) {
            logger.debug("创建" + SysConfig.userGroupTableName + "表失败...");
        }else if(sqLiteUtils.getTableCount(SysConfig.userGroupTableName) == 0){
            //初始化预定义用户组
            sqLiteUtils.insert(SysConfig.userGroupTableName, new Object[]{0, "未分组"});
            sqLiteUtils.insert(SysConfig.userGroupTableName, new Object[]{1, "黑名单"});
            sqLiteUtils.insert(SysConfig.userGroupTableName, new Object[]{2, "星标组"});
        }
    }
}
