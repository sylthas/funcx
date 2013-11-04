package funcx.kit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * ID生成器
 * 
 * <p>
 * 以前写的不知道放哪儿做纪念...
 * 
 * @author Sylthas
 *
 */
public abstract class IDBuilder {
    /** 参照UTC日期 */
    public static final int RF_UTC_DATE = 0;

    /** 参照UTC毫秒 */
    public static final int RF_UTC_MILLIS = 1;

    /** 步长 */
    private static final long ONE_STEP = 100;

    /** 日期格式化 */
    private static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyyMMddHHmmssSSS");

    private IDBuilder() {
    }

    /**
     * 获取毫秒级UID
     * 
     * <p>
     * 产生形如 : 13537394803431
     * 
     * @return 毫秒UID
     */
    public static synchronized String getMillisUid() {
        StringBuilder sb = new StringBuilder();
        long lastTime = System.currentTimeMillis();
        short count = 0;
        try {
            if (count == ONE_STEP) {
                boolean done = true;
                while (done) {
                    long now = System.currentTimeMillis();
                    if (now == lastTime) {
                        Thread.sleep(1);
                        continue;
                    } else {
                        lastTime = now;
                        done = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append(lastTime);
        sb.append(count++);
        return sb.toString();
    }

    /**
     * 获取日期级UID
     * 
     * <p>
     * 形如:20121124144523312
     * 
     * @return 日期UID
     */
    public static synchronized String getDateUid() {
        StringBuilder sb = new StringBuilder();
        long lastTime = System.currentTimeMillis();
        try {
            boolean done = true;
            while (done) {
                long now = System.currentTimeMillis();
                if (now == lastTime) {
                    Thread.sleep(1);
                    continue;
                } else {
                    lastTime = now;
                    done = false;
                    sb.append(sdf.format(new Date()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 指定标识生成UID
     * 
     * @param identity 前缀标识
     * @param rf 参照(0：日期 1：UTC毫秒 )
     * @return 生成UID
     */
    public static synchronized String buildUid(String identity, int rf) {
        StringBuilder sb = new StringBuilder();
        sb.append(identity);
        switch (rf) {
        case RF_UTC_DATE:
            sb.append(getDateUid());
            break;
        case RF_UTC_MILLIS:
            sb.append(getMillisUid());
            break;
        default:
            break;
        }
        return sb.toString();
    }

    /**
     * 获取通用唯一标识
     * 
     * <p>
     * 形如:B89F0752FA4748EEAB7716D0E56BED78
     * 
     * @return 32位字符串
     */
    public static String getRandomUUID() {
        String idkey = UUID.randomUUID().toString();
        return idkey.replace("-", "").toUpperCase();
    }
}
