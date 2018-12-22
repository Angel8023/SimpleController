package water.ustc.action;

public class LoginAction {
	private String result;

	public String handleLogin() {
		result = "success";
		return result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
