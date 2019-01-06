package service;

//UserService 是代理对象，代替RealUser执行方法
import bean.UserBean;

public class UserService implements UserInterface {
	RealUser realUser; // 被代理的对象
	UserBean userBean; // 传入的数据对象

	public UserService(UserBean userBean) {
		// TODO Auto-generated constructor stub
		this.userBean = userBean;
	}

	@Override
	public boolean signIn() {	
		if (realUser == null) {			
			realUser = new RealUser(userBean);			
		}
		return realUser.signIn();
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

}
