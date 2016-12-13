package cn.com.egova.wx.util;


import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SQLiteUtils {
    final static Logger logger = Logger.getLogger(SQLiteUtils.class);
    private Connection connection;
    private Statement stmt = null;

    public Connection getConnection() {
        return connection;
    }

    public Statement getStmt() {
        return stmt;
    }

    public SQLiteUtils(Connection connection) {
        this.connection = connection;
        try {
            if (this.connection != null)
                this.stmt = this.connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建表。
     *
     * @param sql
     * @return boolean
     */
    public boolean createTable(String sql) throws Exception {
        logger.debug(sql);
        ////Statement stmt = null;
        try {
            //stmt = this.connection.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            logger.error("创建指定表时异常 : " + e.getLocalizedMessage());
            if (connection.getAutoCommit())
                connectionRollback(connection);
            return false;
        }
    }

    /**
     * 向指定表中插入一条数据。
     *
     * @param table  表名
     * @param params 参数数组
     * @return boolean
     */
    public boolean insert(String table, Object[] params) throws Exception {
        String sql = "insert into " + table + " values(";
        for (int i = 0; i < params.length; i++) {
            Object temp = params[i];
            if (temp != null && temp.getClass() == String.class) {
                temp = ("'" + temp + "'");
            }
            if (i == (params.length - 1)) {
                sql += (temp + ");");
            } else {
                sql += (temp + ", ");
            }
        }
        logger.debug(sql);
        try {
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            logger.error("向表插入" + table + "数据时异常 : " + e.getLocalizedMessage());
            if (connection.getAutoCommit())
                connectionRollback(connection);
            return false;
        }
    }

    /**
     * 修改表中一个元组的数据。
     *
     * @param table    表名
     * @param keyParam 要修改的元组的主键值
     * @param keyField 要修改的元组的主键字段名称
     * @param fields   要修改的元组的字段名称数组
     * @param params   要修改的元组的值数组
     * @return boolean
     */
    public boolean update(String table, String keyParam, String keyField, String[] fields, Object[] params) throws Exception {
        //Statement stmt = null;
        String sql = "update " + table + " set ";
        for (int i = 0; i < fields.length; i++) {
            if (i == (fields.length - 1)) {
                sql += (fields[i] + "='" + params[i] + "' where " + keyField + "='" + keyParam + "';");
            } else {
                sql += (fields[i] + "='" + params[i] + "', ");
            }
        }
        logger.debug(sql);
        try {
            //stmt = this.connection.createStatement();
            int rows = stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            logger.error("修改表" + table + "数据时异常 : " + e.getLocalizedMessage());
            if (connection.getAutoCommit())
                connectionRollback(connection);
            return false;
        }

    }

    /**
     * 删除指定表中指定键值的元组。
     *
     * @param table
     * @param key
     * @param keyValue
     * @return boolean
     */
    public boolean delete(String table, String key, String keyValue) throws Exception {
        //Statement stmt = null;
        String sql = "delete from " + table + " where " + key + "='" + keyValue + "';";
        logger.debug(sql);
        try {
            //stmt = this.connection.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            logger.error("删除表" + table + "数据时异常 : " + e.getLocalizedMessage());
            if (connection.getAutoCommit())
                connectionRollback(connection);
            return false;
        }
    }

    public boolean executeSql(String sql) throws Exception {
        logger.debug(sql);
        try {
            //stmt = this.connection.createStatement();
            int result = stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            logger.error(sql + "执行失败");
            if (connection.getAutoCommit())
                connectionRollback(connection);
            return false;
        }
    }

    /**
     * 将一个表中满足指定条件的所有元组以Vector<Vector<Object>>的形式返回
     *
     * @param table
     * @param key
     * @param keyValue
     * @return Vector<Vector<Object>>
     */
    public Vector<Vector<Object>> selectVector(String table, String key, String keyValue) throws Exception {
        //Statement stmt = null;
        ResultSet rs = null;

        Vector<Vector<Object>> value = new Vector<Vector<Object>>();

        String sql = "select * from " + table + " where " + key + "='" + keyValue + "';";
        logger.debug(sql);
        try {
            //stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            int columnCounts = getFieldsCounts(rs);
            while (rs.next()) {
                Vector<Object> valueVector = new Vector<Object>();
                for (int i = 1; i <= columnCounts; i++) {
                    valueVector.addElement(rs.getObject(i));
                }
                value.addElement(valueVector);
            }
            return value;
        } catch (Exception e) {
            logger.error("查询表" + table + "数据时异常 : " + e.getLocalizedMessage());
            return value;
        }
    }

    public Object selectColumnById(String table,String key,String  keyValue,String column){
        ResultSet rs = null;
        String sql = "SELECT "+column+" from " + table + " where "+ key + "='" + keyValue +"'";
        logger.debug(sql);
        Object o = null;
        try {
            //stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                o = rs.getObject(1);
            }
            return o;
        } catch (Exception e) {
            logger.error("查询表sql数据时异常 : " + e.getLocalizedMessage());
            return o;
        }
    }
    /**
     * 返回制定sql语句查询的Vector<Vector<Object>>结果集
     *
     * @param sql sql语句
     * @return Vector<Vector<Object>>
     */
    public Vector<Vector<Object>> selectVector(String sql) throws Exception {
        //Statement stmt = null;
        ResultSet rs = null;

        Vector<Vector<Object>> value = new Vector<Vector<Object>>();

        logger.debug(sql);
        try {
            //stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            int columnCounts = getFieldsCounts(rs);
            while (rs.next()) {
                Vector<Object> valueVector = new Vector<Object>();
                for (int i = 1; i <= columnCounts; i++) {
                    Object o = rs.getObject(i);
                    valueVector.addElement( o == null ?"null":o);
                }
                value.addElement(valueVector);
            }
            return value;
        } catch (Exception e) {
            logger.error("查询表sql数据时异常 : " + e.getLocalizedMessage());
            return value;
        }
    }

    /**
     * 将满足一定条件的指定表中所有元组数据以Object[][]形式返回
     *
     * @param table
     * @param key
     * @param keyValue
     * @return Object[][]
     */
    public Object[][] selectObject(String table, String key, String keyValue) throws Exception {
        //Statement stmt = null;
        ResultSet rs = null;

        int columns = getFieldsCounts(table);
        int rows = getTableCount(table, key, keyValue);

        Object[][] tableObject = new Object[rows][columns];

        String sql = "select * from " + table + " where " + key + "='" + keyValue + "';";
        logger.debug(sql);
        try {
            //stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                for (int j = 0; j < columns; j++) {
                    tableObject[i][j] = rs.getObject(j + 1);
                }
                i++;
            }
            return tableObject;
        } catch (Exception e) {
            logger.error("查询表" + table + "数据时异常 : " + e.getLocalizedMessage());
            return tableObject;
        }
    }

    /**
     * 将一个表中所有的元组以Vector<Vector<Object>>的形式返回
     *
     * @param table
     * @return Vector<Vector<Object>>
     */
    public Vector<Vector<Object>> select(String table) throws Exception {
        //Statement stmt = null;
        ResultSet rs = null;

        Vector<Vector<Object>> value = new Vector<Vector<Object>>();

        String sql = "select * from " + table + ";";
        logger.debug(sql);
        try {
            //stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            int columnCounts = getFieldsCounts(rs);
            while (rs.next()) {
                Vector<Object> valueVector = new Vector<Object>();
                for (int i = 1; i <= columnCounts; i++) {
                    valueVector.addElement(rs.getObject(i));
                }
                value.addElement(valueVector);
            }
            return value;
        } catch (Exception e) {
            logger.error("查询表" + table + "数据时异常 : " + e.getLocalizedMessage());
            return value;
        }
    }

    /**
     * 将一个表中所有的元组以Object[][]的形式返回
     *
     * @param table
     * @return Object[][]
     */
    public Object[][] selectObject(String table) throws Exception {
        //Statement stmt = null;
        ResultSet rs = null;

        int columns = getFieldsCounts(table);
        int rows = getTableCount(table);

        Object[][] tableObject = new Object[rows][columns];

        String sql = "select * from " + table + ";";
        logger.debug(sql);
        try {
            //stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                for (int j = 0; j < columns; j++) {
                    Object o = rs.getObject(j + 1);
                    tableObject[i][j] = (o == null? "null":o);
                }
                i++;
            }
            return tableObject;
        } catch (Exception e) {
            logger.error("查询表" + table + "数据时异常 : " + e.getLocalizedMessage());
            return tableObject;
        }
    }

    /**
     * 分页查询
     * @param table
     * @param key
     * @param keyValue
     * @param size
     * @param offset
     * @return
     * @throws Exception
     */
    public List<List<Object>> selectWithPager(String table, String[] key, String[] keyValue, String size, String offset,String orderBy) throws Exception {
        //Statement stmt = null;
        ResultSet rs = null;
        String sql = "select * from " + table +" where ";
        for(int i = 0 ; i < key.length ;i++){
            if(keyValue[i] != null){
                if("nickname".equals(key[i]))
                    sql += (key[i]+" like '%"+ keyValue[i] +"%' and ");
                else
                    sql += (key[i]+"='"+ keyValue[i] +"' and ");
            }
        }
        sql = sql.substring(0,sql.length()-4);
        sql += " order by "+orderBy+" desc limit "+ size +" offset "+offset+";";
        List<List<Object>> value = new ArrayList<List<Object>>();
        logger.debug(sql);
        try {
            //stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            int columnCounts = getFieldsCounts(rs);
            while (rs.next()) {
                List<Object> valueVector = new ArrayList<Object>();
                for (int i = 1; i <= columnCounts; i++) {
                    valueVector.add(rs.getObject(i));
                }
                value.add(valueVector);
            }
            return value;
        } catch (Exception e) {
            logger.error("查询表" + table + "数据时异常 : " + e.getLocalizedMessage());
            return value;
        }
    }

    /**
     * 将一个ResultSet结果集中的所有字段以List形式返回
     *
     * @param resultSet
     * @return List<String>
     */
    public List<String> getFields(ResultSet resultSet) throws Exception {
        List<String> fieldsList = new ArrayList<String>();
        try {
            int columnCounts = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCounts; i++) {
                fieldsList.add(resultSet.getMetaData().getColumnName(i));
            }
        } catch (SQLException e) {
            logger.error("加载表中字段异常 ：" + e.getLocalizedMessage());
            return null;
        }
        return fieldsList;
    }

    /**
     * 将一个表中的所有字段以List形式返回
     *
     * @param table
     * @return List<String>
     */
    public List<String> getFields(String table) throws Exception {
        List<String> fieldsList = new ArrayList<String>();
        //Statement stmt = null;
        ResultSet rs = null;
        String sql = "select * from " + table + ";";
        logger.debug(sql);
        try {
            //stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            fieldsList = getFields(rs);
        } catch (Exception e) {
            logger.error("查询表" + table + "数据时异常 : " + e.getLocalizedMessage());
        }
        return fieldsList;
    }

    /**
     * 将一个ResultSet结果集中的所有字段的数目返回
     *
     * @param resultSet
     * @return int
     */
    public int getFieldsCounts(ResultSet resultSet) throws Exception {
        try {
            return resultSet.getMetaData().getColumnCount();
        } catch (SQLException e) {
            logger.error("加载表中字段异常 ：" + e.getLocalizedMessage());
            return 0;
        }
    }

    /**
     * 返回一个表的所有字段数目
     *
     * @param table
     * @return int
     */
    public int getFieldsCounts(String table) throws Exception {
        int counts = 0;
        //Statement stmt = null;
        ResultSet rs = null;
        String sql = "select * from " + table + ";";
        logger.debug(sql);
        try {
            //stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            counts = getFieldsCounts(rs);
        } catch (Exception e) {
            logger.error("查询表" + table + "数据时异常 : " + e.getLocalizedMessage());
        }
        return counts;
    }

    /**
     * 查询一个表中的所有元组数目
     *
     * @param table
     * @return int
     */
    public int getTableCount(String table) throws Exception {
        String sql = "select count(*) from " + table + ";";
        //Statement stmt = null;
        ResultSet rs = null;
        int counts = 0;
        try {
            //stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                counts = rs.getInt(1);
            }
            return counts;
        } catch (Exception e) {
            logger.error("查询表" + table + "元组数时异常 : " + e.getLocalizedMessage());
            return counts;
        }
    }

    public int getTableCountViaSql(String sql) throws Exception {
        //Statement stmt = null;
        ResultSet rs = null;
        int counts = 0;
        try {
            //stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                counts = rs.getInt(1);
            }
            return counts;
        } catch (Exception e) {
            logger.error(sql +"：" + e.getLocalizedMessage());
            return counts;
        }
    }

    /**
     * 查询一个表中的满足一定条件的所有元组数目
     *
     * @param table    表名
     * @param key      字段名称
     * @param keyValue 字段值
     * @return int
     */
    public int getTableCount(String table, String key, String keyValue) throws Exception {
        String sql = "select count(*) from " + table + " where " + key + "='" + keyValue + "';";
        //Statement stmt = null;
        ResultSet rs = null;
        int counts = 0;
        try {
            //stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                counts = rs.getInt(1);
            }
            return counts;
        } catch (Exception e) {
            logger.error("查询表" + table + "元组数时异常 : " + e.getLocalizedMessage());
            return counts;
        }
    }

    public int getMaxCol(String table,String col){
        String sql = "select max("+col+") from " + table + ";";
        //Statement stmt = null;
        ResultSet rs = null;
        int counts = 0;
        try {
            //stmt = this.connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                counts = rs.getInt(1);
            }
            return counts;
        } catch (Exception e) {
            logger.error("查询表" + table + "元组数时异常 : " + e.getLocalizedMessage());
            return counts;
        }
    }
    private void connectionRollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("异常时回滚错误 : " + e.getLocalizedMessage());
        }
    }
}

