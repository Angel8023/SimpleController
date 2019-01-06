package service;

import bean.UserBean;
import sc.ustc.dao.UserDAO;

public class RealUser implements UserInterface {
	UserBean userBean;  //查询数据库得到的UserBean对象
	String userPass;
	
	public RealUser(UserBean userBean) {
		// TODO Auto-generated constructor stub		
		this.userBean = userBean;	
		userPass = userBean.getUserPass();
		loadUserFromDbs();
	}
	
	public void loadUserFromDbs(){
		System.out.println("未加载数据库之前： "+userBean);
		UserDAO userDAO = new UserDAO();
		userBean = ((UserBean) userDAO.queryById(userBean.getUserId()));	
		System.out.println("加载数据库之后 ： "+userBean);
	}

	@Override
	public boolean signIn() {
		// TODO Auto-generated method stub		
		return userPass.equals(userBean.getUserPass()); 							
	}
}
