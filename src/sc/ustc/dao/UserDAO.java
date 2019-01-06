package sc.ustc.dao;

import bean.UserBean;

public class UserDAO extends BaseDAO {	
	//初始化数据库配置信息
	
	/*//配置mysql数据库驱动
	public UserDAO() {				
		setDriver("com.mysql.jdbc.Driver");
		setUrl("jdbc:mysql://localhost:3306/j2eedbs?useUnicode=true&amp;characterEncoding=UTF-8");
		setUserName("root");
		setUserPassword("");
	}	*/
	
	//配置sqlite3数据库驱动
	/*public UserDAO(){
		setDriver("org.sqlite.JDBC");
		setUrl("jdbc:sqlite:E:\\MyProject\\SimpleController\\src\\sc\\ustc\\dao\\j2eedbs.db");
		setUserName("");
		setUserPassword("");		
	}*/		
	
	public Object queryById(String userId){
		//String sql = "select * from Users where userName = "+"\""+userName+"\"";
		UserBean userBean = new UserBean();
		userBean.setUserId(userId);
		return Conversation.getObject(userBean);
	}
}
