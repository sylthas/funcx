package funcx.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import funcx.comm.Const;
import funcx.log.Logger;
import funcx.mvc.Config;
import funcx.mvc.dispath.Dispatcher;

/**
 * 核心分发器
 * 
 * <p>
 * 最主要的功能就是这个咯...
 * 
 * @author Sylthas
 * 
 */
public class DispatcherFilter implements Filter {

    private final Logger logger = Logger.getLogger(DispatcherFilter.class);
    private Dispatcher dispatcher;
    private String encoding = null;

    public void init(final FilterConfig filterConfig) throws ServletException {
        logger.info("Init DispatcherFilter...");
        this.dispatcher = new Dispatcher();
        this.encoding = filterConfig.getInitParameter("encoding");
        this.dispatcher.init(new Config() {
            public String getInitParameter(String name) {
                return filterConfig.getInitParameter(name);
            }

            public ServletContext getServletContext() {
                return filterConfig.getServletContext();
            }
        });
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding(encoding != null ? encoding
                : Const.DEFAULT_ENCODING);
        String method = request.getMethod();
        if (Const.GET.equals(method) || Const.POST.equals(method)) {
            if (!dispatcher.service(request, response))
                chain.doFilter(req, resp);
            return;
        }
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    public void destroy() {
        logger.info("Destroy  DispatcherFilter...");
        this.dispatcher.destroy();
    }
}
