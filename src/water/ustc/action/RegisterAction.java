package water.ustc.action;

public class RegisterAction {
	private String result;
					
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String handleRegister(){
		result = "success";
		return result;
	}
}
