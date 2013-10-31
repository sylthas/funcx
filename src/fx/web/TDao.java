package fx.web;

import funcx.ioc.annotation.Repository;

@Repository("tDao")
public class TDao {

	public void execute(String msg) {
		System.out.println(msg);
	}
}
