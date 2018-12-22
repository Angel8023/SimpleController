package entity;

public class ActionLog {
	private String name;
	private String stime;
	private String etime;
	private String result;
		
	@Override
	public String toString() {
		return "ActionLog [name=" + name + ", stime=" + stime + ", etime=" + etime + ", result=" + result + "]";
	}

	public ActionLog() {

	}

	// 使用单例对象，创建普通对象
	public ActionLog(SingleActionLog singleActionLog) {
		singleActionLog = SingleActionLog.INSTANCE;
		setAll(singleActionLog.getName(), singleActionLog.getStime(), singleActionLog.getEtime(),
				singleActionLog.getResult());
	}

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
