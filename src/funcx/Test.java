package funcx;

import funcx.log.Logger;
import funcx.mvc.dispath.Dispatcher;



public class Test {
	static Logger log = Logger.getLogger(Test.class);
	public static void main(String[] args) {
		new Dispatcher().init(null);
	}

	static void test() {
		
		log.info("xxx", new RuntimeException("这是测试异常"));
	}
}
