package funcx.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * 日志输出格式
 * 
 * <p>
 * 定义的日志输出样式 [date] level [method] name - message \n exception
 * 
 * @author Sylthas
 * 
 */
public class FuncxFormatter extends Formatter {

    static final String LINE_SEPARATOR = System.getProperty("line.separator");
    static final String WHITE_SPACE = " ";
    static SimpleDateFormat fmt = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss,SSS");

    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        String date = fmt.format(Calendar.getInstance().getTime());
        // 日期
        sb.append('[').append(date).append(']').append(WHITE_SPACE);
        // 级别
        sb.append(record.getLevel().getLocalizedName()).append(WHITE_SPACE);
        // 方法
        if (record.getSourceMethodName() != null) {
            sb.append('[').append(record.getSourceMethodName()).append(']')
                    .append(WHITE_SPACE);
        }
        // 名字
        String loggerName = record.getLoggerName();
        if (loggerName == null) {
            loggerName = record.getSourceClassName();
        }
        sb.append(loggerName).append(" - ");
        // 消息
        sb.append(record.getMessage());
        // 换行
        sb.append(LINE_SEPARATOR);
        // 日志
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            record.getThrown().printStackTrace(pw);
            pw.flush();
            pw.close();
            sb.append(sw.toString());
        }
        return sb.toString();
    }

}
