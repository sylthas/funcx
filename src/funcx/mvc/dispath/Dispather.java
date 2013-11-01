package funcx.mvc.dispath;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import funcx.comm.Converter;
import funcx.ioc.ClassApplicationContext;
import funcx.log.Logger;
import funcx.mvc.Config;
import funcx.mvc.annotation.ExceptionHandle;
import funcx.mvc.annotation.Intercept;
import funcx.mvc.annotation.RequestMapping;
import funcx.mvc.contrl.Controller;
import funcx.mvc.contrl.ControllerContext;
import funcx.mvc.exception.URLMatchException;
import funcx.mvc.execution.DefaultExceptionHandler;
import funcx.mvc.execution.ExceptionHandler;
import funcx.mvc.execution.Execution;
import funcx.mvc.intercept.Interceptor;
import funcx.mvc.intercept.InterceptorChainImpl;
import funcx.mvc.render.Renderer;
import funcx.mvc.render.TextRenderer;
import funcx.mvc.template.TemplateFactory;
import funcx.mvc.template.impl.JspTemplateFactory;

/**
 * FuncX 分发器
 * 
 * <p>
 * MVC 核心<br/>
 * 分发器的具体实现...
 * 
 * @author Sylthas
 * 
 */
public class Dispather {

	private static Logger log = Logger.getLogger(Dispather.class);
	private ClassApplicationContext act = ClassApplicationContext.getInstance();
	private ServletContext servletContext = null;
	private ExceptionHandler exceptionHandler = null;
	private Interceptor[] interceptors = null;
	private URLMatcher[] urlMatchers = null;
	private Map<URLMatcher, Controller> urlCtrl = new HashMap<URLMatcher, Controller>();

	public void init(Config config) {
		log.info("Init dispather...");
		servletContext = config.getServletContext();
		initComponents();
		initTemplateFactory(config);
	}

	/** 服务入口 **/
	public boolean service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI().substring(
				request.getContextPath().length());
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding("utf-8");
		}
		if (log.isDebugEnabled()) {
			log.debug("handler for uri : " + url);
		}
		Execution exec = null;
		// 匹配映射
		for (URLMatcher matcher : this.urlMatchers) {
			String[] args = matcher.getMatchParams(url);
			if (args != null && args.length > 0) {
				Controller contrl = this.urlCtrl.get(matcher);
				// URI参数注入方法
				Object[] arguments = new Object[args.length];
				for (int i = 0; i < args.length; i++) {
					Class<?> type = contrl.arguments[i];
					if (type.equals(String.class))
						arguments[i] = args[i];
					else
						arguments[i] = Converter.parse(args[i], type);
				}
				exec = new Execution(request, response, contrl, arguments);
				break;
			}
		}
		if (exec != null) {
			handleExecution(exec, request, response);
		}
		return exec != null;
	}

	/** 处理执行 **/
	void handleExecution(Execution exec, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 初始化控制器上下文
		ControllerContext.set(this.servletContext, request, response);
		try {
			// DataSourceFactory.setThreadConnection(); TODO JDBC
			InterceptorChainImpl chains = new InterceptorChainImpl(interceptors);
			chains.doChain(exec);
			Object result = chains.getResult();
			handleResult(result, request, response);
		} catch (Exception ex) {
			handleException(request, response, ex);
		} finally {
			ControllerContext.remove();
			// DataSourceFactory.freeThreadConnection(); TODO JDBC
		}
	}

	/** 处理异常 **/
	void handleException(HttpServletRequest request,
			HttpServletResponse response, Exception ex)
			throws ServletException, IOException {
		try {
			exceptionHandler.handle(request, response, ex);
		} catch (ServletException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/** 执行结果渲染 **/
	void handleResult(Object result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (result == null)
			return;
		if (result instanceof Renderer) {
			Renderer r = (Renderer) result;
			r.render(this.servletContext, request, response);
			return;
		}
		if (result instanceof String) {
			String s = (String) result;
			if (s.startsWith("redirect:")) {
				response.sendRedirect(request.getContextPath()
						+ s.substring("redirect:".length()));
				return;
			}
			new TextRenderer(s).render(servletContext, request, response);
			return;
		}
		throw new ServletException("Can't execute return type '"
				+ result.getClass().getName() + "'.");
	}

	/** 初始化组件 **/
	void initComponents() {
		Object[] beans = act.getBeans();
		// 初始化参数映射
		for (Object bean : beans) {
			initRequestMapping(bean);
		}
		// 初始化异常处理器
		Object handler = act.getBeans(ExceptionHandle.class)[0];
		if (handler == null) {
			this.exceptionHandler = new DefaultExceptionHandler();
		} else {
			if (handler instanceof ExceptionHandler)
				this.exceptionHandler = (ExceptionHandler) handler;
		}
		// 初始化拦截器
		List<Interceptor> interList = new ArrayList<Interceptor>();
		Object[] inters = act.getBeans(Intercept.class);
		for (Object inter : inters) {
			if (inter instanceof Interceptor)
				interList.add((Interceptor) inter);
		}
		this.interceptors = interList
				.toArray(new Interceptor[interList.size()]);
		// 拦截器排序
		Arrays.sort(this.interceptors, new Comparator<Interceptor>() {
			@Override
			public int compare(Interceptor o1, Interceptor o2) {
				Intercept i1 = o1.getClass().getAnnotation(Intercept.class);
				Intercept i2 = o2.getClass().getAnnotation(Intercept.class);
				int n1 = i1 == null ? Integer.MAX_VALUE : i1.value();
				int n2 = i2 == null ? Integer.MAX_VALUE : i2.value();
				if (n1 == n2)
					return o1.getClass().getName()
							.compareTo(o2.getClass().getName());
				return n1 - n2;
			}
		});
		// 映射关系
		this.urlCtrl = URLMapping.getMapping();
		this.urlMatchers = urlCtrl.keySet().toArray(
				new URLMatcher[urlCtrl.size()]);
		// 检查同一个URL是否映射到两个控制器方法
		Arrays.sort(this.urlMatchers, new Comparator<URLMatcher>() {
			public int compare(URLMatcher o1, URLMatcher o2) {
				String u1 = o1.url;
				String u2 = o2.url;
				int n = u1.compareTo(u2);
				if (n == 0)
					throw new URLMatchException("Cannot mapping one url '" + u1
							+ "' to more than one controller method");
				return n;
			}
		});
	}

	/** 初始化参数映射 **/
	void initRequestMapping(Object bean) {
		Class<?> clazz = bean.getClass();
		if (!clazz.isAnnotationPresent(funcx.ioc.annotation.Controller.class))
			return;
		String url = "";
		if (clazz.isAnnotationPresent(RequestMapping.class)) {
			url = clazz.getAnnotation(RequestMapping.class).value();
		}
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(RequestMapping.class)) {
				StringBuilder sb = new StringBuilder();
				sb.append(url);
				RequestMapping map = method.getAnnotation(RequestMapping.class);
				sb.append(map.value());
				String uri = sb.toString();
				URLMatcher matcher = new URLMatcher(uri);
				URLMapping.put(matcher, new Controller(bean, method));
				log.info("Mapping URL '" + uri + "' to method ["
						+ method.toGenericString() + "]");
			}
		}
	}

	/** 初始化模板 **/
	void initTemplateFactory(Config config) {
		String name = config.getInitParameter("template");
		if (name == null) {
			name = JspTemplateFactory.class.getName();
			log.info("No template factory specified. Default to '" + name
					+ "'.");
		}
		TemplateFactory tf = TemplateFactory.createTemplateFactory(name);
		tf.init(config);
		log.info("Template factory '" + tf.getClass().getName()
				+ "' init complete.");
		TemplateFactory.setTemplateFactory(tf);
	}

	/** 销毁 **/
	public void destroy() {
		log.info("Destroy Dispatcher ...");
		URLMapping.getMapping().clear();
		act.distroy();
	}
}
