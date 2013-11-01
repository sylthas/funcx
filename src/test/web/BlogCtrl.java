package test.web;

import test.model.Blog;
import funcx.ioc.annotation.Controller;
import funcx.mvc.annotation.RequestMapping;
import funcx.mvc.annotation.ResponseBody;

@Controller("blogCtrl")
@RequestMapping("/blog")
public class BlogCtrl {

	@RequestMapping("/add")
	public void add() {
		System.out.println("execute add");
	}
	
	@RequestMapping("/del/$1")
	public void del(int id) {
		System.out.println("delete id - " + id);
	}

	@RequestMapping("/view/$1")
	@ResponseBody
	public Blog view(int id) {
		Blog b = new Blog();
		b.setId(id);
		b.setContent("content for id - " + id);
		return b;
	}
}
