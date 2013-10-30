package funcx;

import funcx.ioc.ClassApplicationContext;
import fx.web.TContrl;


public class Test {
	public static void main(String[] args) {
		ClassApplicationContext act = ClassApplicationContext.getInstance();
		TContrl t = (TContrl) act.getBean("tContrl");
		TContrl t2 = (TContrl) act.getBean("tContrl");
		TContrl t3 = (TContrl) act.getBean("tContrl", true);
		TContrl t4 = (TContrl) act.getBean("tContrl", true);
		System.out.println(t.hashCode());
		System.out.println(t2.hashCode());
		System.out.println(t3.hashCode());
		System.out.println(t4.hashCode());
		t3.execute();
		t4.execute();

	}
}
