package water.ustc.interceptor;

import java.text.DateFormat;
import java.util.Date;

import entity.LogXml;
import entity.SingleActionLog;

public class LogInterceptor {

	DateFormat dateFormat = DateFormat.getDateTimeInstance();
	
	//创建某个action的单例
	SingleActionLog singleActionLog = SingleActionLog.INSTANCE;

	public void preAction() {
		String startTime = dateFormat.format(new Date());
		singleActionLog.setStime(startTime);   //记录访问开始时间
		System.out.println("Action被拦截器拦截前LogInterceptor preAction:" + startTime);
	}

	public void afterAction() {
		String endTime = dateFormat.format(new Date());
		singleActionLog.setEtime(endTime);   //记录访问结束时间
		System.out.println("Action被拦截器拦截后LogInterceptor afterAction:" + endTime);
	}
}
