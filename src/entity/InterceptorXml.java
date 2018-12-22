package entity;

public class InterceptorXml {
	private String name;
	private String classLocation;
	private String predo;
	private String afterdo;
		
	public void setAll(String name,String classLocation,String predo,String afterdo){
		this.name = name;
		this.classLocation = classLocation;
		this.predo = predo;
		this.afterdo = afterdo;
	}
			
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassLocation() {
		return classLocation;
	}
	public void setClassLocation(String classLocation) {
		this.classLocation = classLocation;
	}
	public String getPredo() {
		return predo;
	}
	public void setPredo(String predo) {
		this.predo = predo;
	}
	public String getAfterdo() {
		return afterdo;
	}
	public void setAfterdo(String afterdo) {
		this.afterdo = afterdo;
	}
}
