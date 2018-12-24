package proxy;
import entity.ActionXml;
import entity.SingleActionLog;
import util.ClassReflector;

/*
 * 创建被代理对象实体类
 * */
public class ActionExecute implements ActionInterface {
	private ActionXml actionXml;

	public ActionExecute() {
		// TODO Auto-generated constructor stub
		this.actionXml = new ActionXml();
	}

	public ActionExecute(ActionXml actionXml) {
		this.actionXml = actionXml;
	}
		
	//实现executeAction()方法
	@Override
	public String executeAction() {
		// TODO Auto-generated method stub
		String resultName = null;		
		try {
			// 通过java 反射机制，通过类名和方法名获取到对应方法并执行
			resultName = ClassReflector.executeMethod(actionXml.getclassLocation(), actionXml.getMethod());
			// 创建保存单个action信息的 日志对象
			SingleActionLog singleActionLog = SingleActionLog.INSTANCE;
			// 将请求名称和返回结果记录到一个action中
			singleActionLog.setName(actionXml.getName());
			singleActionLog.setResult(resultName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultName;
	}
}
