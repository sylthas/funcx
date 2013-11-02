package funcx.jdbc.util;

/**
 * 数据库辅助类
 * 
 * <p>
 * 提供基本的数据持久化操作辅助
 * 
 * @version 1.0
 * @author Sylthas
 */
public abstract class DBUtils {

    /**
     * 链接数据库 <br/>
     * 
     * @param driver 驱动类
     * @param url 链接地址
     * @param user 用户
     * @param password 密码
     * @return
     */
    public static java.sql.Connection connect(String driver, String url,
            String user, String password) {
        try {
            Class.forName(driver);
            return java.sql.DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取数据库链接<br/>
     * 
     * @param driver 驱动
     * @param url 链接路径
     * @return
     */
    public static java.sql.Connection connect(String driver, String url) {
        try {
            Class.forName(driver);
            return java.sql.DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 绑定参数<br/>
     * 
     * @param pstmt 预编译SQL指令
     * @param params 参数
     * @throws java.sql.SQLException
     */
    public static void bind(java.sql.PreparedStatement pstmt, Object... params)
            throws java.sql.SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }

    /**
     * 执行统计语句<br/>
     * 
     * @param conn 数据库链接
     * @param sql 形如:select count(1) from table
     * @param params 参数
     * @return count(1)
     * @throws java.sql.SQLException
     */
    public static int count(java.sql.Connection conn, String sql,
            Object... params) throws java.sql.SQLException {
        java.sql.PreparedStatement pstmt = null;
        java.sql.ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            bind(pstmt, params);
            rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } finally {
            free(pstmt, rs);
        }
        return -1;
    }

    /**
     * 执行Sql语句<br/>
     * 
     * @param conn 数据库链接
     * @param sql Sql语句
     * @param params 参数
     * @throws java.sql.SQLException
     */
    public static void execute(java.sql.Connection conn, String sql,
            Object... params) throws java.sql.SQLException {
        java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
        bind(pstmt, params);
        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * 执行修改语句<br/>
     * 
     * @param conn 数据库链接
     * @param sql Sql语句
     * @param params 参数
     * @return 影响记录条数
     * @throws java.sql.SQLException
     */
    public static int update(java.sql.Connection conn, String sql,
            Object... params) throws java.sql.SQLException {
        java.sql.PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            bind(pstmt, params);
            return pstmt.executeUpdate();
        } finally {
            free(pstmt);
        }
    }

    /**
     * 查询单条记录<br/>
     * 
     * @param conn 数据库链接
     * @param sql Sql语句
     * @param params 参数
     * @return java.util.Map集合
     * @throws java.sql.SQLException
     */
    public static java.util.Map<String, Object> query(java.sql.Connection conn,
            String sql, Object... params) throws java.sql.SQLException {
        java.util.Map<String, Object> map = new java.util.HashMap<String, Object>();
        java.sql.PreparedStatement pstmt = null;
        java.sql.ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            bind(pstmt, params);
            rs = pstmt.executeQuery();
            if (rs.next())
                map.putAll(parse(rs));
        } finally {
            free(pstmt, rs);
        }
        return map;
    }

    /**
     * 查询多条记录<br/>
     * 
     * @param conn 数据库链接
     * @param sql Sql语句
     * @param params 参数
     * @return java.util.List集合
     * @throws java.sql.SQLException
     */
    public static java.util.List<java.util.Map<String, Object>> list(
            java.sql.Connection conn, String sql, Object... params)
            throws java.sql.SQLException {
        java.util.List<java.util.Map<String, Object>> list = new java.util.ArrayList<java.util.Map<String, Object>>();
        java.sql.PreparedStatement pstmt = null;
        java.sql.ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            bind(pstmt, params);
            rs= pstmt.executeQuery();
            while (rs.next()) {
                java.util.Map<String, Object> map = parse(rs);
                list.add(map);
            }
            return list;
        } finally {
            free(pstmt, rs);
        }
    }

    /**
     * 转换结果集<br/>
     * 
     * @param rs 查询结果集
     * @return java.util.Map集合
     * @throws java.sql.SQLException
     */
    public static java.util.Map<String, Object> parse(java.sql.ResultSet rs)
            throws java.sql.SQLException {
        java.util.Map<String, Object> map = new java.util.HashMap<String, Object>();
        java.sql.ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        for (int i = 0; i < count;) {
            String columnName = rsmd.getColumnName(++i);
            map.put(columnName.toUpperCase(), rs.getObject(columnName));
        }
        return map;
    }

    public static void free(java.sql.Connection conn) {
        try {
            if (conn != null && !conn.isClosed())
                conn.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public static void free(java.sql.PreparedStatement pstmt) {
        try {
            if (pstmt != null)
                pstmt.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public static void free(java.sql.ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public static void free(java.sql.PreparedStatement pstmt,
            java.sql.ResultSet rs) {
        try {
            if (pstmt != null)
                pstmt.close();
            if (rs != null)
                rs.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}
