package funcx.jdbc.support;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BaseDao {

	/**
	 * 执行Sql语句
	 * 
	 * <p>
	 * 不支持事物回滚
	 * 
	 * @param sql
	 * @param params 不定项参数
	 * @throws SQLException
	 */
	void execute(String sql, Object... params) throws SQLException;

	/**
	 * 执行update操作
	 * 
	 * <p>
	 * 执行增、删、改操作，支持事物回滚。
	 * 
	 * @param sql
	 * @param params
	 * @return 受影响记录条数
	 * @throws SQLException
	 */
	int update(String sql, Object... params) throws SQLException;

	/**
	 * 执行查询操作
	 * 
	 * <p>
	 * 自动将结果转换为对象
	 * 
	 * @param clazz 要转换对象类型
	 * @param sql
	 * @param params
	 * @return 一条记录
	 * @throws SQLException
	 */
	<T> T query(Class<T> clazz, String sql, Object... params)
			throws SQLException;

	/**
	 * 执行查询操作
	 * 
	 * <p>
	 * 自动转换结果集为对象集合
	 * 
	 * @param clazz 要转换对象类型
	 * @param sql
	 * @param params
	 * @return 对象集合
	 * @throws SQLException
	 */
	<T> List<T> list(Class<T> clazz, String sql, Object... params)
			throws SQLException;

	/**
	 * 执行查询操作
	 * 
	 * <p>
	 * 返回一个Map形式的模型
	 * 
	 * @param sql
	 * @param params
	 * @return Map集合
	 * @throws SQLException
	 */
	Map<String, Object> model(String sql, Object... params) throws SQLException;

	/**
	 * 统计记录条数
	 * 
	 * @param sql
	 * @param params
	 * @return 存在记录条数
	 * @throws SQLException
	 */
	int count(String sql, Object... params) throws SQLException;
}
