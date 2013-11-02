package funcx.mvc.render;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 普通的文本渲染
 * 
 * <p>
 * 像json字符串什么的就可以用这个了...
 * 
 * @author Sylthas
 * 
 */
public class TextRenderer extends Renderer {

    private String characterEncoding;
    private String text;

    public TextRenderer() {
    }

    public TextRenderer(String text) {
        this.text = text;
    }

    public TextRenderer(String text, String characterEncoding) {
        this.text = text;
        this.characterEncoding = characterEncoding;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void render(ServletContext context, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        StringBuilder sb = new StringBuilder(64);
        sb.append(contentType == null ? "text/html" : contentType)
                .append(";charset=")
                .append(characterEncoding == null ? "UTF-8" : characterEncoding);
        response.setContentType(sb.toString());
        PrintWriter pw = response.getWriter();
        pw.write(text);
        pw.flush();
        pw.close();
    }

}
