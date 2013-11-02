package funcx.jdbc.support;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import funcx.jdbc.DBConnHandler;
import funcx.jdbc.util.DBUtils;
import funcx.util.BeanUtils;

/**
 * DAO支持
 * 
 * <p>
 * 简单的数据持久化支持...
 * 
 * @author Sylthas
 * 
 */
public class DaoSupport implements BaseDao {

    @Override
    public void execute(String sql, Object... params) throws SQLException {
        Connection conn = DBConnHandler.getConnection();
        DBUtils.execute(conn, sql, params);
        DBConnHandler.freeConnection(conn);
    }

    @Override
    public int update(String sql, Object... params) throws SQLException {
        Connection conn = DBConnHandler.getCurrentConnection();
        return DBUtils.update(conn, sql, params);
    }

    @Override
    public <T> T query(Class<T> clazz, String sql, Object... params)
            throws SQLException {
        Connection conn = DBConnHandler.getConnection();
        Map<String, Object> map = DBUtils.query(conn, sql, params);
        DBConnHandler.freeConnection(conn);
        if (map.isEmpty())
            return null;
        return BeanUtils.parse(map, clazz);
    }

    @Override
    public <T> List<T> list(Class<T> clazz, String sql, Object... params)
            throws SQLException {
        Connection conn = DBConnHandler.getConnection();
        List<Map<String, Object>> temp = DBUtils.list(conn, sql, params);
        List<T> list = new ArrayList<T>();
        if (temp != null && temp.size() > 0) {
            for (int i = 0; i < temp.size(); i++) {
                T t = BeanUtils.getInstance(clazz);
                t = BeanUtils.parse(temp.get(i), clazz);
                list.add(t);
            }
        }
        DBConnHandler.freeConnection(conn);
        return list;
    }

    @Override
    public Map<String, Object> model(String sql, Object... params)
            throws SQLException {
        Connection conn = DBConnHandler.getConnection();
        Map<String, Object> model = DBUtils.query(conn, sql, params);
        DBConnHandler.freeConnection(conn);
        return model;
    }

    @Override
    public int count(String sql, Object... params) throws SQLException {
        Connection conn = DBConnHandler.getConnection();
        int r = DBUtils.count(conn, sql, params);
        DBConnHandler.freeConnection(conn);
        return r;
    }

}
