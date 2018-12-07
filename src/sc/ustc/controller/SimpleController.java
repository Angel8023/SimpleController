
package sc.ustc.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleController extends HttpServlet{					
	private static final long serialVersionUID = 1L;		
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {	
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
