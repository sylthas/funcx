package fx.web;

import funcx.ioc.annotation.Inject;
import funcx.ioc.annotation.Service;

@Service("tSer")
public class TService {

	@Inject
	private TDao tDao;

	public void execute(int i) {
		tDao.execute(i > 0 ? "Hello Java" : "Hello Kitty");
	}

	public TDao getTDao() {
		return tDao;
	}

	public void setTDao(TDao tDao) {
		this.tDao = tDao;
	}

}
