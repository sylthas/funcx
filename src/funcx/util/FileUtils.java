package funcx.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

/**
 * 文件操作辅助
 * 
 * <p>
 * 简单的文件操作辅助类...
 * 
 * @author Sylthas
 *
 */
public abstract class FileUtils {

    /**
     * 下载网络资源文件
     * 
     * @param url 资源URL地址
     * @param path 保存路径
     * @return 成功标识
     */
    public static boolean downURLFile(String url, String path) {
        try {
            URL URL = new URL(url);
            DataInputStream dis = new DataInputStream(URL.openStream());
            File f = new File(path);
            if (!f.getParentFile().exists())
                createDir(f);
            FileOutputStream fos = new FileOutputStream(f);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            dis.close();
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读取文本文件内容
     * 
     * @param path 文件路径
     * @param encoding 字符编码
     * @return
     * @throws IOException
     */
    public static String readText(String path, String encoding)
            throws IOException {
        File f = new File(path);
        if (f.isFile() && f.exists()) {
            InputStreamReader read = new InputStreamReader(new FileInputStream(
                    f), encoding);
            BufferedReader buffer = new BufferedReader(read);
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
            buffer.close();
            read.close();
            return sb.toString();
        }
        return null;
    }

    /**
     * 将文本写入文件
     * 
     * @param s 文本
     * @param path 路径
     * @throws IOException
     */
    public static void writeText(String s, String path) throws IOException {
        File f = new File(path);
        OutputStream out = new FileOutputStream(f);
        byte[] b = s.getBytes();
        for (int i = 0; i < b.length; i++) {
            out.write(b[i]);
        }
        out.close();
    }

    /**
     * 创建文件
     * 
     * <p>
     * 不在在目录时自动创建
     * 
     * @param f 文件
     * @return 成功标识
     */
    public static boolean createFile(File f) {
        File p = f.getParentFile();
        if (p != null && !p.exists()) {
            p.mkdirs();
        }
        try {
            return f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除文件
     * 
     * @param f 文件
     * @return 成功标识
     */
    public static boolean deleteFile(File f) {
        return f.delete();
    }

    /**
     * 删除文件
     * 
     * @param path 文件路径
     * @return 成功标识
     */
    public static boolean deleteFile(String path) {
        return deleteFile(new File(path));
    }

    /**
     * 创建目录
     * 
     * <p>
     * 创建多级目录
     * 
     * @param dir 目录
     * @return 成功标识
     */
    public static boolean createDir(File dir) {
        File p = dir.getParentFile();
        if (p != null && !p.exists()) {
            return p.mkdirs();
        } else {
            return p.exists();
        }
    }

    /**
     * 创建目录
     * 
     * <p>
     * 创建多级目录
     * 
     * @param path 目录路径
     * @return 成功标识
     */
    public static boolean createDir(String path) {
        return createDir(new File(path));
    }

    /**
     * 删除指定目录
     * 
     * <p>
     * 递归调用删除目录下的所有文件
     * 
     * @param dir 目录
     * @return 成功标识
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] dirs = dir.list();
            for (String str : dirs) {
                boolean f = deleteDir(new File(dir, str));
                if (!f) {
                    return f;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 删除指定目录
     * 
     * <p>
     * 递归调用删除目录下的所有文件
     * 
     * @param path 目录路径
     * @return 成功标识
     */
    public static boolean deleteDir(String path) {
        return deleteDir(new File(path));
    }

    /**
     * 复制文件
     * 
     * @param path 文件路径
     * @param target 目标路径
     * @return
     */
    public static boolean copyFile(String path, String target) {
        try {
            java.io.File f = new java.io.File(path);
            String fileName = target + java.io.File.separator + f.getName();
            if (f.exists()) {
                java.io.InputStream in = new java.io.FileInputStream(f);
                java.io.File t = new java.io.File(fileName);
                java.io.FileOutputStream fos = new java.io.FileOutputStream(t);
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = in.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
                in.close();
                fos.close();
            }
            return new java.io.File(fileName).isFile();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
