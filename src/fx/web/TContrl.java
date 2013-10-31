package fx.web;

import funcx.ioc.annotation.Controller;
import funcx.ioc.annotation.Inject;

@Controller()
public class TContrl {

	@Inject
	private TService tSer;

	public void execute() {
		tSer.execute(5);
	}

	public TService getTSer() {
		return tSer;
	}

	public void setTSer(TService tSer) {
		this.tSer = tSer;
	}

}
