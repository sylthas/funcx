package funcx.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 包扫描辅助工具
 * 
 * <p>
 * 扫描指定包下的class文件
 * 
 * @author Sylthas
 * 
 */
public class PackageUtils {

    /**
     * 扫描指定包下的所有class <br/>
     * 
     * @param packageName 包名
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Class<?>[] scan(String packageName) throws IOException,
            ClassNotFoundException {
        Class<PackageUtils> clazz = PackageUtils.class;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = clazz.getClassLoader().getResources(path);
        List<File> dirs = new ArrayList<File>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File dir : dirs) {
            classes.addAll(findClasses(dir, packageName));
        }
        return classes.toArray(new Class<?>[classes.size()]);
    }

    /**
     * 查找指定目录下的Class <br/>
     * 
     * @param dir 目录
     * @param pack 包名
     * @return
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(File dir, String pack)
            throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();

        if (!dir.exists()) {
            return classes;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, pack + '.' + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(pack
                        + '.'
                        + file.getName().substring(0,
                                file.getName().length() - 6)));
            }
        }
        return classes;
    }

}