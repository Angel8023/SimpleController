package bean;

import sc.ustc.dao.UserDAO;

public class UserBean {
	private String userId;
	private String userName;
	private String userPass;

	public boolean signIn() {			
		UserDAO userDAO = new UserDAO();
		UserBean userBean = (UserBean) userDAO.queryByName(userName);
		if (userBean != null) {
			return userPass.equals(userBean.getUserPass());
		} else
			return false;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
}
