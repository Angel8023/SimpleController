package entity;

import java.util.ArrayList;
import java.util.List;
//一个ActionXml对象包含自身的属性值和一个ResultXml对象列表
//包含拦截器信息
public class ActionXml {
	private String name;
	private String classLocation;
	private String method;
	private List<Interceptorref> interceptorrefList;   //配置拦截器信息 对象
	private List<ResultXml> resultList;
	
	public ActionXml() {
		// TODO Auto-generated constructor stub
		resultList = new ArrayList<ResultXml>();
		interceptorrefList = new ArrayList<Interceptorref>();
	}			
		
	public void setAll(String name, String classLocation,String method){
		this.name = name;
		this.classLocation = classLocation;
		this.method = method;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getclassLocation() {
		return classLocation;
	}
	public void setClass(String classLocation) {
		this.classLocation = classLocation;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<ResultXml> getResultList() {
		return resultList;
	}
	public void setResultList(List<ResultXml> resultList) {
		for(ResultXml resultXml:resultList){
			this.resultList.add(resultXml);
		}
	}
	
	public List<Interceptorref> getInterceptorrefList(){
		return interceptorrefList;
	}
	public void setInterceptorrefList(List<Interceptorref> interceptorrefList){
		for(Interceptorref interceptorref : interceptorrefList){
			this.interceptorrefList.add(interceptorref);
		}		
	}
}
