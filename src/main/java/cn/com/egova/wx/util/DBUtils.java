package cn.com.egova.wx.util;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by gongxufan on 2016/7/27.
 */
public class DBUtils {

    public static SQLiteUtils getDBUtil(String dbfile) throws Exception {
        return new SQLiteUtils(new SQLiteConn(dbfile).getConnection());
    }

    public static void close(Connection connection, Statement st) throws Exception {
        if (st != null) st.close();
        if (connection != null) connection.close();
    }
}
