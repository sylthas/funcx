package funcx.mvc.dispath;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import funcx.ioc.ClassApplicationContext;
import funcx.log.Logger;
import funcx.mvc.Config;
import funcx.mvc.annotation.ExceptionHandle;
import funcx.mvc.annotation.Intercept;
import funcx.mvc.annotation.RequestMapping;
import funcx.mvc.contrl.Controller;
import funcx.mvc.exception.URLMatchException;
import funcx.mvc.execution.DefaultExceptionHandler;
import funcx.mvc.execution.ExceptionHandler;
import funcx.mvc.intercept.Interceptor;

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
		// servletContext = config.getServletContext();
		initComponents();
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
}
