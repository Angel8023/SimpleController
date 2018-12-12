
package sc.ustc.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import entity.ActionXml;
import entity.ControllerXml;
import entity.ResultXml;
import util.XmlParser;

public class SimpleController extends HttpServlet{					
	private static final long serialVersionUID = 1L;
	private static final String XML_FILE_NAME = "controller.xml";
	private XmlParser xmlParser;
	
	
	//通过获取到uri地址，对uri进行分割，得到action的名称
	private String getActionName(HttpServletRequest request){
		String uri = request.getRequestURI();
		return uri.substring(uri.lastIndexOf("/")+1, uri.indexOf(".sc"));
	}
	
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		//第二次实验代码--------------------------
		//获取action名称
		String actionName = getActionName(request);
		System.out.println(actionName);
		//读取xml文件路径
		String xmlPath = 
				SimpleController.class.getClassLoader().getResource(XML_FILE_NAME).getPath();
		System.out.println(xmlPath);
				
		xmlParser = new XmlParser(xmlPath);   //用xml文件路径创建对象
		
		//用xml文件装配controller 对象
		try {
			xmlParser.setControllerXml();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ControllerXml controllerXml = xmlParser.getControllerXml();
		boolean isFoundAction = false;   //判断是否能找到指定action
		boolean isFoundResult = false;
		//输出xml文件中的数据测试
		for(ActionXml actionXml : controllerXml.getActionList()){
			if(actionName.equals(actionXml.getName())){
				isFoundAction = true;		//找到指定action						
				Class<?> theAction = null;   
				try{
					//通过java 反射机制，通过类名获取到java 类
					theAction = Class.forName(actionXml.getclassLocation());
					//通过java 反射机制，通过类名和方法名获取到对应方法并执行
					Method method=theAction.getMethod(actionXml.getMethod());
					//执行对应方法
					String resultName = (String) method.invoke(theAction.newInstance());
					//System.out.println(resultName);
					for(ResultXml resultXml : actionXml.getResultList()){
						//System.out.println(resultXml.getName());
						if(resultName!=null && resultName.equals(resultXml.getName())){
							isFoundResult = true;
							System.out.println(resultXml.getType()+" : "+resultXml.getValue());
							//判断请求类型是转发还是重定向
							if("forward".equals(resultXml.getType())){
								//转发请求								
								request.setCharacterEncoding("UTF-8");  //设置字符编码
								request.getRequestDispatcher(resultXml.getValue()).forward(request, response);
							}else{
								//对请求进行重定向
								response.setContentType("text/html;charset=utf-8"); //设置字符编码
								response.sendRedirect(resultXml.getValue());
							}
						}
					}					
				}catch(Exception e){
					e.printStackTrace();					
				} 															
			}		
		}
		if(!isFoundAction){
			System.out.println("不可识别的 action 请求");			
		}else {
			if(!isFoundResult) System.out.println("没有请求的资源");
		}																																											
		/*
		//第一次实验代码--------------------------
		//测试		
		//System.out.println("hello");			
		//获取页面输出流
	    PrintStream out = new PrintStream(response.getOutputStream());
		//获取要读取的html文件路径
		String filePath = 
				request.getSession().getServletContext().getRealPath("/welcome.html"); 		
		BufferedReader br = new BufferedReader(new FileReader(filePath));;		
		String line="";
		while((line = br.readLine())!= null){
			//以输出流的形式输出html页面
			out.println(line);
		}
		*/							    
	}		
			
	//测试用输出流输出页面
	private void printPage(HttpServletResponse response) throws IOException{		
		//获取页面输出流
		PrintStream out = new PrintStream(response.getOutputStream());				
		//输出HTML页面标签
		out.println("<html>");
		out.println("<head>");
		out.println("<title>SimpleController</title>");
		out.println("</head>");
		out.println("<body>欢迎使用SimpleContreller!</body>");
		out.println("</html>");				
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		doPost(request, response);		       
	}	
}
