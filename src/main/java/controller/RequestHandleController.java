package controller;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/req/")
public class RequestHandleController {
	//===========请求url映射==============
	 /**
	  * 请求url映射:
	  * 多个URL映射为同一个方法
	  * @ResponseBody 注解的含义是告诉
	  * spring mvc 将方法返回值以字符串
	  * 形式进行呈现.
	  * @return
	  */
     @RequestMapping(value={"doReqUrlMap01","withReqUrlMap01"})
     @ResponseBody
	 public String doReqUrlMap01(){
		 return "request url mapping";
	 }
     /**
      * Rest风格URL映射
      * @return
      */
     @RequestMapping("doReqRestUrl{map}")
     @ResponseBody
     public String doReqRestUrlMap02(){
    	return "request rest url mapping";
     }
     //=====请求方式映射:例如Get,Post请求==========
  
     @RequestMapping(value="doReqMethodMap01",
    		         method={RequestMethod.POST,
    		        		 RequestMethod.GET})
     @ResponseBody
     public String doReqMethodMap01(){
    	 return "request rest method mapping";
     }
     //@PostMapping
     @GetMapping("doReqMethodMap02")//只支持GET请求
     @ResponseBody
     public String doReqMethodMap02(){
    	 return "request rest method mapping";
     }
     //请求参数处理
     //=========标准servlet API===============================
    
     @RequestMapping("doReqParam01")
     @ResponseBody
     public String doReqParam01(HttpServletRequest request){
    	 return "obtain parameter's value is "+
         request.getParameter("pageCurrent");
     }
     //====直接量(八种对象类型+String+Date)参数的获取====
     
     //1.此方法由谁调用?:DispatcherServlet
     //2.此方法如何调用?:反射
     //3.此方法调用时参数如何注入?(Spring MVC底层基于参数名进行值的注入)
     @RequestMapping("doReqParam02")
     @ResponseBody
     public String doReqParam02(
    		 String name,
    		 @RequestParam(value="page",required=true)
    		 Integer pageCurrent){//谁帮我们进行了类型转换
    	 return "obtain parameter's value is pageCurrent="+pageCurrent
    			 +",name="+name;
     }
     @RequestMapping("doReqParam03")
     @ResponseBody
     public String doReqParam03(
    		 @DateTimeFormat(pattern="yyyy-MM-dd") 
    		 Date birthday){//请求可以直接将日志字符串注入给此对象
    	 //说明:
    	 //1)spring mvc 默认可以处理的日期格式为yyyy/MM/dd格式
    	 //2)Spring MVC 可以借助@DateTimeFormat注解定义处理指定的日期格式
    	 //3)假如客户端请求中传递的日期格式不匹配会出现400异常
    	 return "obtain parameter's value is "+birthday;
     }
     @RequestMapping("doReqParam04")
     @ResponseBody
     public String doReqParam04(Integer... ids){//可变参数或者用Integer[]
    	 //客户端传参形式为ids=1,2,3,4,5
    	 return "obtain parameter's value is "+Arrays.toString(ids);
     }     
     //=====使用javabean(VO-值对象,pojo-持久化对象)接收数据=================
     @RequestMapping("doReqParam05")
     @ResponseBody
     public String doReqParam05(SysLog log){
       //说明:请求中的参数需要与pojo对象的set方法一致
       return "obtain parameter's value is "+log.toString();
     }
     @RequestMapping("doReqParam06")
     @ResponseBody
     public String doReqParam06(@RequestParam Map<String,Object> map){
    	 //说明:当使用map作为方法参数封装请求数据时,
    	 //方法参数必须使用@RequestParam注解进行修饰
    	 return "obtain parameter's value is "+map; 
     }
     //========获取rest风格url中的数据=========
     //http://ip:port/项目名称/doReqParam07.do?id=10&name=AAA
     //http://ip:port/项目名称/doReqParam07/10/AAA.do
     @RequestMapping("doReqParam07/{id}/{name}")
     @ResponseBody
     public String doReqParam07(@PathVariable Integer id,
    		                    @PathVariable String name){
         //说明从rest风格url中获取请求数据需要
    	 //使用@PathVariable注解对参数进行修饰
    	 return "obtain parameter's value is id="+id+",name="+name;
     }
     //===========获取请求头中的参数数据=========================
     @RequestMapping("doReqHeader01")
     @ResponseBody
     public String doReqHeader01(
    		 @RequestHeader String Accept){
    	 return "obtain parameter's value is Accept="+Accept;
     }
     @RequestMapping("doReqHeader02")
     @ResponseBody
     public String doReqHeader02(
    		 @RequestHeader("Accept-Encoding") 
    		 String AcceptEncoding){
    	 return "obtain parameter's value is Accept-Encoding="+AcceptEncoding;
     }
     //============获取cookie的值===========
     @RequestMapping("doReqCookie01")
     @ResponseBody
     public String doReqCookie01(
    	@CookieValue String JSESSIONID){
    	 return "obtain parameter's value is JSESSIONID="+JSESSIONID;
     }
     
     
     
     
     
     
     
     
     
     
     
}








