package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping("/resp/")
@Controller
public class ResponseHandleController{
	//==========响应数据的封装============
	@RequestMapping("doRespData01")
	public String doRespData01(HttpServletRequest request){
		//直接将响应数据封装到标准servlet api对象中
		request.setAttribute("data1", "sys log message");
		return "response";//view name
		//返回的view会通过视图解析器进行解析,获取具体页面
		//前端控制器基于视图解析器返回的页面,进行请求转发
	}
	@RequestMapping("doRespData02")
	public String doRespData02(HttpServletRequest request){
		request.setAttribute("data2", "request redirect message");
		//当需要重定向到某个url时,需要添加redirect前缀.
		return "redirect:doRespData01.do";
		//访问此方法时客户端向服务端底层发起了两次请求
	}
	
	@RequestMapping("doRespData03")
	public ModelAndView doRespData03(ModelAndView mv){
		//系统底层会将数据存储到request作用域
		mv.addObject("data1", "model and view data");
		//当系统返回mv时,经过视图解析获取view页面,
		//并将请求转发到此页面
		mv.setViewName("response");
		return mv;
		//此方法的返回值给了谁?调用方(DispatcherServlet)
		//前端控制器会调用视图解析,对mv中的view进行解析
		//前端控制器会将请求数据转发到视图解析器返回view页面
	}
	@RequestMapping("doRespData04")
	public String doRespData04(Model m){//ModelMap
		System.out.println(m.getClass().getName());
		//将数据添加到请求作用域
		m.addAttribute("data1",
				"model message");
		return "response";//view
	}
    //=======================================
	//JSON:一种数据格式
	//JSON:格式的字符串"{id:10,name:AAA,...}"
	
	@RequestMapping("doRespJson01")
	@ResponseBody
	public Map<String,Object> doRespJson01(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id",10);
		map.put("name", "涛哥哥");
		return map;//SPRING MVC系统底层可以调用
	    //第三方的API将map对象转换为JSON格式的字符串
	    //例如:
		//jackson (SPRING 默认支持,只需要添加依赖)
		//fastjson(SPRING 默认不支持,想用就需要添加完再进行配置)
	    
	   //说明:对于此返回转换为json格式字符串.需要
	   //在配置文件中添加<mvc:annotation-driven/>配置
	   //假如没有这个配置系统底层可能会出现406的问题
	
	}

	@RequestMapping("doRespJson02")
	@ResponseBody
	public List<Map<String,Object>> doRespJson02(){
		List<Map<String,Object>> list=
		new ArrayList<Map<String, Object>>();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id",10);
		map.put("name", "涛哥哥");
		list.add(map);
		map=new HashMap<String, Object>();
		map.put("id",100);
		map.put("name", "陈枢枢");
		list.add(map);
		return list;
	}
	@RequestMapping("doRespJson03")
	@ResponseBody
	public String doRespJson03(){
		return "{\"id\":100,\"name\"=\"AAA\"}";
	}
	@RequestMapping("doRespJson04")
	@ResponseBody
	public SysLog doRespJson04(){
		SysLog log=new SysLog();
		log.setId(100);
		log.setUsername("admin");
		log.setMethod("login");
		log.setIp("192.168.100.123");
		log.setOperation("登录");
		log.setTime(System.currentTimeMillis());
		log.setCreatedTime(new Date());
		return log;//底层将此对象转换为JSON串时会调用
		//对象的get方法获取值
	}
	@RequestMapping(value="doRespJson05",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String doRespJson05()throws Exception{
		SysLog log=new SysLog();
		log.setId(100);
		log.setUsername("admin");
		log.setMethod("login");
		log.setIp("192.168.100.123");
		log.setOperation("登录");
		log.setTime(System.currentTimeMillis());
		log.setCreatedTime(new Date());
		//借助第三方API(例如jackson)自己将对象转换为串
		ObjectMapper om=new ObjectMapper();//jackson API
		String s=om.writeValueAsString(log);
		System.out.println("s="+s);
		return s;
	}
}






