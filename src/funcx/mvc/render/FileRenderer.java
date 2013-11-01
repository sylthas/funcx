package funcx.mvc.render;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import funcx.comm.Const;

/**
 * 文件渲染器
 * 
 * <p>
 * 执行文件下载什么的...
 * 
 * @author Sylthas
 * 
 */
public class FileRenderer extends Renderer {

    private File file;

    public FileRenderer() {
    }

    public FileRenderer(File file) {
        this.file = file;
    }

    public FileRenderer(String file) {
        this.file = new File(file);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void render(ServletContext context, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        if (file == null || !file.isFile() || file.length() > Integer.MAX_VALUE) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String mime = contentType;
        if (mime == null) {
            mime = context.getMimeType(file.getName());
            if (mime == null) {
                mime = Const.DEFALUT_FILE_CONTENT_TYPE;
            }
        }
        response.setContentType(mime);
        response.setContentLength((int) file.length());
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

}
