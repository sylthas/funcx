package funcx.util;

import java.security.MessageDigest;

/**
 * Hash计算工具类 <br/>
 * 
 * @version 1.0
 * @author Sylthas
 */
public abstract class Hash {

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 计算字符串MD5值
     * 
     * @param in 字符串
     * @return
     */
    public static String md5(String in) {
        return hash(in.getBytes(), "MD5");
    }

    /**
     * 计算文件MD5值
     * 
     * @param file 文件
     * @return
     */
    public static String md5(java.io.File file) {
        return hash(file, "MD5");
    }

    public static String md5(byte[] data) {
        return hash(data, "MD5");
    }

    /**
     * 计算字符串SHA1值
     * 
     * @param in 字符串
     * @return
     */
    public static String sha1(String in) {
        return hash(in.getBytes(), "SHA1");
    }

    /**
     * 计算文件SHA1值
     * 
     * @param file 文件
     * @return
     */
    public static String sha1(java.io.File file) {
        return hash(file, "SHA1");
    }

    public static String sha1(byte[] data) {
        return hash(data, "SHA1");
    }

    /**
     * 计算字符串HASH值<br/>
     * 
     * @param in 字符串
     * @param algorithm 算法
     * @return
     */
    private static String hash(byte[] data, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            byte[] mdByte = md.digest();
            int len = mdByte.length;
            StringBuilder sb = new StringBuilder(len * 2);
            for (int i = 0; i < len; i++) {
                sb.append(HEX_DIGITS[mdByte[i] >> 4 & 0x0f]);
                sb.append(HEX_DIGITS[mdByte[i] & 0x0f]);
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算文件的HASH值 <br/>
     * 
     * @param file 文件
     * @param algorithm 算法
     * @return
     */
    private static String hash(java.io.File file, String algorithm) {
        if (!file.exists() || !file.isFile())
            return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            java.io.FileInputStream in = new java.io.FileInputStream(file);
            byte[] buffer = new byte[4096];
            int length = -1;
            while ((length = in.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            in.close();
            return new java.math.BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
