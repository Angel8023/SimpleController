package entity;

/*
 * 使用单例模式对某一个action的信息进行同步
 * */
public enum SingleActionLog {
	INSTANCE;
	
	private  String name;
	private  String stime;
	private  String etime;
	private  String result;
	
	public void setAll(String name, String stime, String etime, String result) {
		this.name = name;
		this.stime = stime;
		this.etime = etime;
		this.result = result;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
