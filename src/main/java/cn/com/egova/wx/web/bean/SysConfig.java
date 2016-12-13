package cn.com.egova.wx.web.bean;

import cn.com.egova.wx.util.ConfigurationManager;

/**
 * Created by gongxufan on 2016/8/1.
 */
public class SysConfig {
    private final static ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    //数据库名
    public final static String dbFile = configurationManager.getValue("dbFile");
    public final static String url = configurationManager.getValue("url");
    public final static String username = configurationManager.getValue("username");
    public final static String passowrd = configurationManager.getValue("password");
    public final static String sqlDialect = configurationManager.getValue("sqlDialect");
    //table name
    public final static String menuTableName = configurationManager.getValue("menuTableName");
    public final static String newsTableName = configurationManager.getValue("newsTableName");
    public final static String menuNewsTableName = configurationManager.getValue("menuNewsTableName");
    public final static String imageTableName = configurationManager.getValue("imageTableName");
    public final static String imageGroupTableName = configurationManager.getValue("imageGroupTableName");
    public final static String sysconfigTableName = configurationManager.getValue("sysconfigTableName");
    public final static String userTableName = configurationManager.getValue("userTableName");
    public final static String userGroupTableName = configurationManager.getValue("userGroupTableName");
    public final static String messageTableName = configurationManager.getValue("messageTableName");
    public final static String regularTableName = configurationManager.getValue("regularTableName");
    public final static String autoReplyTableName = configurationManager.getValue("autoReplyTableName");
    public final static String keywordTableName = configurationManager.getValue("keywordTableName");
    public final static String regularMessageTableName = configurationManager.getValue("regularMessageTableName");
    public final static String regularKeywordTableName = configurationManager.getValue("regularKeywordTableName");
    public final static String inMessageTableName = configurationManager.getValue("inMessageTableName");
    public final static String newsUpdateTimeTableName = configurationManager.getValue("newsUpdateTime");
    //ddl
    public final static String createMenuSQL = configurationManager.getValue("createMenuSQL");
    public final static String createNewsSQL = configurationManager.getValue("createNewsSQL");
    public final static String createMenuNewsSQL = configurationManager.getValue("createMenuNewsSQL");
    public final static String createImageSQL = configurationManager.getValue("createImageSQL");
    public final static String createImageGroupSQL = configurationManager.getValue("createImageGroupSQL");
    public final static String createSysconfigSQL = configurationManager.getValue("createSysconfigSQL");
    public final static String createUserSQL = configurationManager.getValue("createUserSQL");
    public final static String createUserGroupSQL = configurationManager.getValue("createUserGroupSQL");
    public final static String createMessageSQL = configurationManager.getValue("createMessageSQL");
    public final static String createAutoReplySQL = configurationManager.getValue("createAutoReplySQL");
    public final static String createRegularSQL = configurationManager.getValue("createRegularSQL");
    public final static String createKeywordSQL = configurationManager.getValue("createKeywordSQL");
    public final static String createRegularMessageSQL = configurationManager.getValue("createRegularMessageSQL");
    public final static String createRegularKeywordSQL = configurationManager.getValue("createRegularKeywordSQL");
    public final static String createInMessageSQL = configurationManager.getValue("createInMessageSQL");
    public final static String createNewsUpdateTimeSQL = configurationManager.getValue("createNewsUpdateTime");

    //微信接口地址
    public final static String access_token_url = configurationManager.getValue("access_token_url");
    public final static String menu_create_url = configurationManager.getValue("menu_create_url");
}
