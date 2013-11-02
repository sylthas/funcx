package funcx.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import funcx.comm.FuncXConfig;
import funcx.jdbc.exception.JdbcException;
import funcx.jdbc.util.DBUtils;

/**
 * 数据库链接控制类
 * 
 * <p>
 * 小框架嘛,这样才简单...
 * 
 * @author Sylthas
 * 
 */
public abstract class DBConnHandler {

    /** 链接池 **/
    private static Vector<Connection> pool = new Vector<Connection>();
    /** 当前线程链接 **/
    private static ThreadLocal<Connection> threadConn = new ThreadLocal<Connection>();

    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    static final int DB_TYPE_SQLITE = 0;

    static {
        driver = FuncXConfig.get("jdbc.driver", null);
        url = FuncXConfig.get("jdbc.url", null);
        user = FuncXConfig.get("jdbc.user", null);
        password = FuncXConfig.get("jdbc.password", null);
    }

    /**
     * 从池中获取链接<br/>
     * 
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        synchronized (pool) {
            if (pool.size() > 0) {
                conn = pool.firstElement();
                pool.remove(conn);
            } else {
                if (user == null && password == null)
                    conn = DBUtils.connect(driver, url);
                else
                    conn = DBUtils.connect(driver, url, user, password);
            }
        }
        return conn;
    }

    /**
     * 将链接放入池中<br/>
     * 
     * @param conn
     */
    public static void freeConnection(Connection conn) {
        synchronized (pool) {
            pool.add(conn);
        }
    }

    /**
     * 设置当前链接<br/>
     * 
     * 线程间不干扰
     */
    public static void setCurrentConnection() {
        threadConn.set(getConnection());
    }

    /**
     * 获取线程链接<br/>
     * 
     * @return
     */
    public static Connection getCurrentConnection() {
        return threadConn.get();
    }

    /**
     * 移除线程链接<br/>
     * 
     */
    public static void removeCurrentConnection() {
        Connection conn = threadConn.get();
        try {
            if (conn != null && !conn.isClosed()) {
                freeConnection(conn);
            }
        } catch (SQLException e) {
            throw new JdbcException(e);
        } finally {
            threadConn.remove();
        }
    }
}
