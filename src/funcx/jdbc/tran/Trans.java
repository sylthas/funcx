package funcx.jdbc.tran;

import java.sql.Connection;
import java.sql.SQLException;

import funcx.jdbc.DBConnHandler;
import funcx.jdbc.exception.TransException;
import funcx.log.Logger;

/**
 * 简单事务处理<br/>
 * 
 * <P>
 * 借鉴Nutz的事务处理实现思路
 * 
 * @author Sylthas
 * 
 */
public class Trans {

    private static Logger log = Logger.getLogger(Trans.class);
    private Connection conn;
    private int old_level;

    private Trans() {
        this.conn = DBConnHandler.getCurrentConnection();
        try {
            this.conn.setAutoCommit(false);
            this.old_level = this.conn.getTransactionIsolation();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void begin(int level) {
        try {
            if (level != old_level)
                conn.setTransactionIsolation(level);
        } catch (SQLException e) {
            log.info("事务级别设置失败 - " + e.getMessage());
        }
    }

    public void commit() {
        try {
            conn.commit();
            if (this.conn.getTransactionIsolation() != old_level) {
                this.conn.setTransactionIsolation(old_level);
            }
        } catch (SQLException e) {
            log.info("提交失败 - " + e.getMessage());
        }
    }

    public void rollback() {
        try {
            conn.rollback();
        } catch (SQLException e) {
            log.info("回滚失败 - " + e.getMessage());
        }
    }

    public void close() {
        try {
            conn.setAutoCommit(true);
            DBConnHandler.removeCurrentConnection();
        } catch (SQLException e) {
            log.info("关闭失败 - " + e.getMessage());
        }
    }

    /**
     * 执行一组原子操作 <br/>
     * 默认的事务级别为: TRANSACTION_READ_COMMITTED<br/>
     * 各种数据库事务支持级别不一样,请事先了解数据库支持情况
     * 
     * @param runs
     */
    public static void exec(Runnable... runs) {
        exec(java.sql.Connection.TRANSACTION_READ_COMMITTED, runs);
    }

    /**
     * 执行原子对象<br/>
     * 
     * @param level
     *            事务的级别。
     *            <p>
     *            各种数据库事务支持级别不一样,请事先了解数据库支持情况<br/>
     *            <p>
     *            你可以设置的事务级别是：
     *            <ul>
     *            <li>java.sql.Connection.TRANSACTION_NONE
     *            <li>java.sql.Connection.TRANSACTION_READ_UNCOMMITTED
     *            <li>java.sql.Connection.TRANSACTION_READ_COMMITTED
     *            <li>java.sql.Connection.TRANSACTION_REPEATABLE_READ
     *            <li>java.sql.Connection.TRANSACTION_SERIALIZABLE
     *            </ul>
     * @param runs
     */
    public static void exec(int level, Runnable... runs) {
        if (runs == null)
            return;
        Trans trans = new Trans();
        try {
            trans.begin(level);
            for (Runnable run : runs) {
                run.run();
            }
            trans.commit();
        } catch (Throwable t) {
            trans.rollback();
            throw new TransException(t);
        } finally {
            trans.close();
        }
    }
}
