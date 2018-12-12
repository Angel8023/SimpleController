package entity;

import java.util.ArrayList;
import java.util.List;

public class ActionXml {
	private String name;
	private String classLocation;
	private String method;
	private List<ResultXml> resultList;
	
	public ActionXml() {
		// TODO Auto-generated constructor stub
		resultList = new ArrayList<ResultXml>();
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
}
