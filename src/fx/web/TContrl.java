package fx.web;

import funcx.ioc.annotation.Controller;
import funcx.ioc.annotation.Inject;
import funcx.mvc.annotation.RequestMapping;

@Controller()
@RequestMapping("/tt")
public class TContrl {

	@Inject
	private TService tSer;

	@RequestMapping("/execut")
	public void execute() {
		tSer.execute(5);
	}

	@RequestMapping("/execut")
	public void execute2() {
		tSer.execute(5);
	}
	public TService getTSer() {
		return tSer;
	}

	public void setTSer(TService tSer) {
		this.tSer = tSer;
	}

}
