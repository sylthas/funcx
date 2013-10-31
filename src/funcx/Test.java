package funcx;

import funcx.log.JdkLoggerFactory;
import funcx.log.Logger;
import funcx.util.Utils;

public class Test {
	public static void main(String[] args) {
		System.out.println(Utils.getWebRootPath());
		test();
	}

	static void test() {
		Logger log = new JdkLoggerFactory().getLogger(Test.class);
		log.info("xxx", new RuntimeException("这是测试异常"));
	}
}
