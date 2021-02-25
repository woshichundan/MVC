package controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//部署项目:将项目添加tomcat指定的加载目录
//访问项目:http://localhost:8080/CGB-SPRING-MVC-01/doSayHello.do

@Controller
@RequestMapping("/") //spring mvc借助此注解定义映射关系
//@Lazy(false)
//@Scope("singleton")
public class HelloController {
	public HelloController() {
		System.out.println("HelloController()");
	}
	/**
	 * ModelAndView 为Spring MVC提供的一个API对象
	 * 此对象主要用户封装视图对象以及这个视图对象要
	 * 呈现的数据信息.
	 * @return
	 */
	@RequestMapping("doSayHello")//url对应的方法对象会存储到map
	public ModelAndView doSayHello(){
		//1.构建ModelAndView对象(作用封装响应数据
		//和视图信息)
		ModelAndView mv=new ModelAndView();
		//2.封装客户端页面要呈现的数据
		//mvc 底层会将model中存储的key/value存储在
		//request作用域
		mv.addObject("message", "hello cgb1806");//存model接口对应的map对象
		//3.封装要呈现的页面视图信息
		mv.setViewName("hello");//此名字会交给视图解析器进行解析
		return mv;
		//底层会对返回的view进行解析,然后转发到对应的JSP页面
	}
}








