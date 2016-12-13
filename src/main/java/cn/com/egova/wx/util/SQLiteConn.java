package cn.com.egova.wx.util;


import cn.com.egova.wx.web.bean.SysConfig;
import cn.com.egova.wx.web.bean.SysInitBean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteConn implements Serializable {

    private String dbfile;

    public SQLiteConn(String dbfile) {
        super();
        this.dbfile = dbfile;
    }

    /**
     * 与SQLite嵌入式数据库建立连接
     *
     * @return Connection
     * @throws Exception
     */
    public Connection getConnection() throws Exception {
        Connection connection = null;
        String driver = "";
        try {
            if("mysql".equals(SysConfig.sqlDialect)){
                driver = "com.mysql.jdbc.Driver";
                Class.forName(driver);
                connection = (Connection) DriverManager.getConnection(SysConfig.url, SysConfig.username, SysConfig.passowrd);
            }
            if("sqlite".equals(SysConfig.sqlDialect)){
                driver = "org.sqlite.JDBC";
                Class.forName(driver);
                connection = DriverManager.getConnection("jdbc:sqlite:" + dbfile);
            }
        } catch (Exception e) {
            throw new Exception("" + e.getLocalizedMessage(), new Throwable("可能由于数据库文件受到非法修改或删除。"));
        }
        return connection;
    }
}
