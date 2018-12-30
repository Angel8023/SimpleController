package sc.ustc.dao;

public class UserDAO extends BaseDAO {	
	//初始化数据库配置信息
	
	//配置mysql数据库驱动
	public UserDAO() {
		setDriver("com.mysql.jdbc.Driver");
		setUrl("jdbc:mysql://localhost:3306/j2eedbs?useUnicode=true&amp;characterEncoding=UTF-8");
		setUserName("root");
		setUserPassword("");
	}	
	
	public Object queryByName(String userName){
		String sql = "select * from Users where userName = "+"\""+userName+"\"";
		return query(sql);
	}
}
