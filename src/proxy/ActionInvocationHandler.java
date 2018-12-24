package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
/*
 * 创建ActionInvocataionHandler类，实现InvocationHandler接口，这个类中持有一个被代理对象的实例target。
 * InvocationHandler中有一个invoke方法，所有执行代理对象的方法都会被替换成执行invoke方法。
 * 再在invoke方法中执行被代理对象target的相应方法。
 * */
import java.util.List;
import entity.InterceptorXml;
import entity.Interceptorref;
import util.ClassReflector;
import util.XmlParser;

public class ActionInvocationHandler implements InvocationHandler {
	// invocationHandler持有的被代理对象
	private Object object; // 传入被代理的对象
	private XmlParser xmlParser; // 传入对xml文件的解析
	private String actionName; // 传入请求名称

	// 通过构造函数将需要拿到的对象和数据传进来
	public ActionInvocationHandler(Object object, XmlParser xmlParser, String actionName) {
		this.object = object;
		this.xmlParser = xmlParser;
		this.actionName = actionName;				
	}

	/**
	 * proxy:代表动态代理对象 method：代表正在执行的方法 args：代表调用目标方法时传入的实参
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		preAction();
		System.out.println("代理执行" + method.getName() + "方法");
		Object result = method.invoke(object, args);
		afterAction();
		return result;
	}

	//获取到一个action中所配置的所有拦截器
	private List<InterceptorXml> getInterceptorXmlList(String actionName) {				
		List<InterceptorXml> interceptorXmlList = new ArrayList<InterceptorXml>();				
		for (Interceptorref interceptorref : xmlParser.getControllerXml().getActionByName(actionName)
				.getInterceptorrefList()) {						
			for (InterceptorXml interceptorXml : xmlParser.getInterceptorXmlList()) {				
				if (interceptorXml != null && interceptorXml.getName().equals(interceptorref.getName()))
					interceptorXmlList.add(interceptorXml);
			}
		}
		return interceptorXmlList;
	}

	/*
	 * 运用java 反射机制，通过InterceptorXml对象中获取到的类名和predo和afterdo方法名 执行predo和afterdo方法
	 */
	private void preAction() {
		// TODO Auto-generated method stub
		// 遍历action中的所有拦截器并执行
		if (getInterceptorXmlList(actionName) != null) {
			for (InterceptorXml interceptorXml : getInterceptorXmlList(actionName)) {				
				try {
					ClassReflector.executeMethod(interceptorXml.getClassLocation(), interceptorXml.getPredo());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("ActoinInvcationHandler类执行predo()方法出错");
					e.printStackTrace();
				}
			}
		}
	}

	private void afterAction() {
		// TODO Auto-generated method stub
		// 遍历action中的所有拦截器并执行
		if (getInterceptorXmlList(actionName) != null) {
			for (InterceptorXml interceptorXml : getInterceptorXmlList(actionName)) {
				try {
					ClassReflector.executeMethod(interceptorXml.getClassLocation(), interceptorXml.getAfterdo());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("ActoinInvcationHandler类执行afterdo()方法出错");
					e.printStackTrace();
				}
			}
		}
	}
}
