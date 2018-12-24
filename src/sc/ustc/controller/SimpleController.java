package sc.ustc.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import entity.ActionLog;
import entity.ActionXml;
import entity.ControllerXml;
import entity.LogXml;
import entity.ResultXml;
import entity.SingleActionLog;
import proxy.ActionExecute;
import proxy.ActionInterface;
import proxy.ActionInvocationHandler;
import util.ClassReflector;
import util.LogUtil;
import util.XmlParser;
import util.XmlToHtml;

public class SimpleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String XML_FILE_NAME = "controller.xml";
	private XmlParser xmlParser;

	// 通过获取到uri地址，对uri进行分割，得到action的名称
	private String getActionName(HttpServletRequest request) {
		String uri = request.getRequestURI();
		return uri.substring(uri.lastIndexOf("/") + 1, uri.indexOf(".sc"));
	}

	private String getHtml(String resultValue) {
		String rear = "_view.xml";
		resultValue = resultValue.trim();
		if (resultValue.endsWith(rear)) {
			// 获取到resultValue 对应的文件路径
			String path = SimpleController.class.getClassLoader().getResource("../../").getPath();
			String xslFilePath = path + resultValue;
			// 创建生成html文件
			File file = new File(xslFilePath);
			return XmlToHtml.getHtmlByXml(file);
		} else {
			return null;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 第二次实验代码--------------------------
		// 获取action名称
		String actionName = getActionName(request);
		System.out.println(actionName);
		// 读取xml文件路径
		String xmlPath = SimpleController.class.getClassLoader().getResource(XML_FILE_NAME).getPath();
		System.out.println(xmlPath);

		xmlParser = new XmlParser(xmlPath); // 用xml文件路径创建对象

		// 用xml文件装配controller 对象
		try {
			xmlParser.setControllerXml();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 分别获取controller和interceptor对象
		ControllerXml controllerXml = xmlParser.getControllerXml();
		boolean isFoundAction = false; // 判断是否能找到指定action
		boolean isFoundResult = false;
		// 输出xml文件中的数据测试
		for (ActionXml actionXml : controllerXml.getActionList()) {
			if (actionName.equals(actionXml.getName())) {
				String resultName = null;
				isFoundAction = true; // 找到指定action
				try {
					// 拿到action后，首先判断是否配置了拦截器
					// 如果配置了拦截器，在 action 执行之前， 执行拦截器的 predo()方法，
					// 并在 action 执行之后执行拦截器的 afterdo()方法
					// 如果没有配置拦截器，则直接访问目标ation
					if (actionXml.getInterceptorrefList().size() != 0) {
						System.out.println("配置了拦截器，使用动态代理对action请求进行处理");
						/************** java 动态代理 ****************/
						// 创建一个实例对象，这个对象是被代理的对象
						ActionInterface theAction = new ActionExecute(actionXml);
						// 创建一个与代理对象相关联的InvocationHandler
						InvocationHandler actionHandler = new ActionInvocationHandler(theAction, xmlParser, actionName);
						// 创建一个代理对象actionProxy来代理zhangsan，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
						ActionInterface actionProxy = (ActionInterface) Proxy.newProxyInstance(
								ActionInterface.class.getClassLoader(), new Class<?>[] { ActionInterface.class },
								actionHandler);
						// 代理执行executeAction()的方法
						resultName = actionProxy.executeAction();
					} else {
						System.out.println("未配置拦截器，直接对请求进行处理");
						// 通过java 反射机制，通过类名和方法名，执行对应类的对应方法
						resultName = ClassReflector.executeMethod(actionXml.getclassLocation(), actionXml.getMethod());
					}

					// System.out.println(resultName);
					for (ResultXml resultXml : actionXml.getResultList()) {
						// System.out.println(resultXml.getName());
						if (resultName != null && resultName.equals(resultXml.getName())) {
							isFoundResult = true;
							System.out.println(resultXml.getType() + " : " + resultXml.getValue());
							String html = getHtml(resultXml.getValue());
							if (html != null) {
								System.out.println("获取到html");
								response.setContentType("text/html; charset=UTF-8");
								response.getWriter().write(html);
							} else {
								response.setContentType("text/html;charset=utf-8"); // 设置字符编码
								response.sendRedirect(resultXml.getValue());
							}							
							/*// 判断请求类型是转发还是重定向
							if ("success".equals(resultXml.getName())) {
								 实验四要求 
								// 如果result为success的话，则输出html页面

								// 实验三以前，是将请求转发，输出指定的jsp页面
								// 转发请求
								// request.setCharacterEncoding("UTF-8"); //
								// 设置字符编码
								// request.getRequestDispatcher(resultXml.getValue()).forward(request,
								// response);
							} else {
								// 对请求进行重定向
								response.setContentType("text/html;charset=utf-8"); // 设置字符编码
								response.sendRedirect(resultXml.getValue());
							}*/
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (!isFoundAction) {
			System.out.println("不可识别的 action 请求");
		} else {
			if (!isFoundResult)
				System.out.println("没有请求的资源");
		}
		/*
		 * //第一次实验代码-------------------------- //测试
		 * //System.out.println("hello"); //获取页面输出流 PrintStream out = new
		 * PrintStream(response.getOutputStream()); //获取要读取的html文件路径 String
		 * filePath =
		 * request.getSession().getServletContext().getRealPath("/welcome.html")
		 * ; BufferedReader br = new BufferedReader(new FileReader(filePath));;
		 * String line=""; while((line = br.readLine())!= null){
		 * //以输出流的形式输出html页面 out.println(line); }
		 */
	}

	// 测试用输出流输出页面
	private void printPage(HttpServletResponse response) throws IOException {
		// 获取页面输出流
		PrintStream out = new PrintStream(response.getOutputStream());
		// 输出HTML页面标签
		out.println("<html>");
		out.println("<head>");
		out.println("<title>SimpleController</title>");
		out.println("</head>");
		out.println("<body>欢迎使用SimpleContreller!</body>");
		out.println("</html>");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
