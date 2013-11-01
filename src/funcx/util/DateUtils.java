package funcx.util;

/**
 * 日期辅助工具类 <br/>
 * 
 * @version 1.0
 * @author Sylthas
 */
public abstract class DateUtils {

    /** 默认日期格式 yyyy-MM-dd **/
    public static final String DATE_FMT_DEFAULT = "yyyy-MM-dd";

    /** 间隔枚举 **/
    public enum Interval {
        DAY(1000 * 24 * 60 * 60), HOUR(1000 * 60 * 60), MINUTE(1000 * 60), 
            SECOND(1000);
        long interval;

        private Interval(long interval) {
            this.interval = interval;
        }
    }

    /**
     * 两个日期之间的间隔<br/>
     * 
     * @param source 源日期
     * @param target 目标日期
     * @param interval 间隔类型
     *            <ul>
     *            <li>DAY 天数
     *            <li>HOUR 小时
     *            <li>MINUTE 分钟
     *            <li>SECOND 秒钟
     *            </ul>
     * @return
     */
    public static long diff(java.util.Date source, java.util.Date target,
            Interval interval) {
        long diff = target.getTime() - source.getTime();
        return diff / interval.interval;
    }

    /**
     * 两个日期之间的间隔<br/>
     * 
     * @param source 源日期
     * @param target 目标日期
     * @return
     */
    public static long diff(java.util.Date source, java.util.Date target) {
        return target.getTime() - source.getTime();
    }

    /**
     * 获取当前服务器日期 <br/>
     * 
     * @return 当前服务器日期对象
     */
    public static java.util.Date getSysDate() {
        return java.util.Calendar.getInstance().getTime();
    }

    /**
     * 获取日期格式化对象<br/>
     * 
     * @param fmt 日期格式
     * @return DateFormat对象
     */
    public static java.text.DateFormat dateFormat(String fmt) {
        return new java.text.SimpleDateFormat(fmt);
    }

    /**
     * 格式化日期为字符串<br/>
     * 
     * @param date 日期
     * @param fmt 日期格式
     * @return 日期字符串
     */
    public static String format(java.util.Date date, String fmt) {
        return dateFormat(fmt).format(date);
    }

    /**
     * 格式化日期为字符串 <br/>
     * 
     * @param date 日期
     * @return 日期字符串
     */
    public static String format(java.util.Date date) {
        return format(date, DATE_FMT_DEFAULT);
    }

    /**
     * 字符串转日期 <br/>
     * 
     * @param date 日期字符串
     * @param fmt 日期格式
     * @return 日期对象
     * @throws java.text.ParseException
     */
    public static java.util.Date parse(String date, String fmt)
            throws java.text.ParseException {
        return dateFormat(fmt).parse(date);
    }

    /**
     * 获取中文星期<br/>
     * 
     * @param date 日期
     * @return 星期
     */
    public static String week(java.util.Date date) {
        String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        return weeks[cal.get(java.util.Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 格式化日期为中文日期字符串<br/>
     * 
     * @param date 日期
     * @return 日期字符串 如:二〇一三年十月十日
     */
    public static String formatCnDate(java.util.Date date) {
        char[] chsNum = { '〇', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十' };
        char[] dateUnit = { '年', '月', '日' };
        String[] dateStr = format(date).split("-");
        java.lang.StringBuilder result = new java.lang.StringBuilder();
        for (int i = 0; i < dateStr.length; i++) {
            String temp = dateStr[i];
            if (temp.equals("10")) {
                result.append(chsNum[10] + "" + dateUnit[1]);
                continue;
            }
            for (int k = 0, len = temp.length(); k < len; k++) {
                String ch = String.valueOf(temp.charAt(k));
                if (len != 2 || k != 0) {
                    result.append(chsNum[Integer.parseInt(ch)]);
                } else {
                    if (ch.equals("0"))
                        result.append("");
                    else if (ch.equals("1"))
                        result.append(chsNum[10]);
                    else
                        result.append("" + chsNum[Integer.parseInt(ch)]
                                + chsNum[10]);
                }
            }
            result.append(dateUnit[i]);
        }
        return result.toString();
    }

    /**
     * 是否是周末<br/>
     * 
     * @param date 日期
     * @return
     */
    public static boolean isWeekend(java.util.Date date) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        return isWeekend(calendar);
    }

    /**
     * 是否是周末<br/>
     * 
     * @param calendar 日历
     * @return
     */
    public static boolean isWeekend(java.util.Calendar calendar) {
        int day = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        if (java.util.Calendar.SATURDAY != day && java.util.Calendar.SUNDAY != day)
            return false;
        return true;
    }

    /**
     * 统计工作日 <br/>
     * 
     * @param start 开始日期
     * @param end 结束日期
     * @return
     */
    public static long workday(java.util.Date start, java.util.Date end) {
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        java.util.Calendar c2 = java.util.Calendar.getInstance();
        c1.setTime(start);
        c2.setTime(end);
        return workday(c1, c2);
    }

    /**
     * 统计工作日 <br/>
     * 
     * @param start 开始日期
     * @param end 结束日期
     * @return
     */
    public static long workday(java.util.Calendar start, java.util.Calendar end) {
        long day = 0;
        while (start.compareTo(end) <= 0) {
            if (!isWeekend(start)) 
                day++;
            start.add(java.util.Calendar.DATE, 1);
        }
        return day;
    }
}
