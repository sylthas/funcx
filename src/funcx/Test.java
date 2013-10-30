package funcx;

import funcx.log.JdkLoggerFactory;
import funcx.log.Logger;


public class Test {
	public static void main(String[] args) {
		Logger log = new JdkLoggerFactory().getLogger(Test.class);
		log.info("dddd");
	}
}
