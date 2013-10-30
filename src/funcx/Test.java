package funcx;

import funcx.mvc.annotation.RequestMapping;
import funcx.mvc.dispath.URLMatcher;

public class Test {
	public static void main(String[] args) {
		Object o = new D();
		D d = D.class.cast(o);
		System.out.println(d.getId());

	}
}

class D {
	int id = 0;
	String name = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
