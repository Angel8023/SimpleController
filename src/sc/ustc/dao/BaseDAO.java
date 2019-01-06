package sc.ustc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.javafx.sg.prism.web.NGWebView;

import entity.orMapping.ClassMapping;
import entity.orMapping.JDBC;
import util.ClassReflector;

public class BaseDAO {
	/*
	 * protected String driver; protected String url; protected String userName;
	 * protected String userPassword;
	 */
	protected JDBC jdbc;
	protected Connection connection;
	protected PreparedStatement ps;
	protected ResultSet rs;

	public Connection openDBConnection() {
		//从配置文件中获取数据库配置
		Configuration configuration = new Configuration();
		jdbc = configuration.getJdbc();				
		try {
			/*
			 * Class.forName(driver); 
			 * connection =DriverManager.getConnection(url, userName, userPassword);
			 */
			Class.forName(jdbc.getDriver_class());
			connection = DriverManager.getConnection(jdbc.getUrl_path(), jdbc.getDb_userName(),
					jdbc.getDb_userPassword());			
			System.out.println("DBS Connection Successful!"); // 如果连接成功 控制台输出
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public boolean closeDBCpnnection() throws SQLException {
		if (connection != null) {
			connection.close();
		}
		return connection.isClosed();
	}

	public Object query(String sql,ClassMapping clm) {			
		Class<?> cls = null;
		Object object = null;
		try {			
			cls = Class.forName(clm.getClassName());				
			//获取到指定类的对象后，获取到该类对象的一个实例，，，，这一句至关重要
			object = cls.newInstance();						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		connection = openDBConnection();
		try {			
			ps = connection.prepareStatement(sql);			
			rs = ps.executeQuery();					
			if (rs.next()) {	
				//设置id属性
				ClassReflector.setField(object, clm.getId(), rs.getObject(clm.getId()));				
				//把查询到的属性值，放入对应的属性中去				
				for(int i=0;i<clm.getPropertyList().size();i++){						
					ClassReflector.setField(object, clm.getPropertyList().get(i).getName(),
							rs.getObject(clm.getPropertyList().get(i).getColumn().trim()));
				}												
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(rs, ps, connection);
		}		
		return object;
	}

	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (ps != null)
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (conn != null)
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	boolean insert(String sql) {
		return false;
	}

	boolean update(String sql) {
		return false;
	}

	boolean delete(String sql) {
		return false;
	}

	/*public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}*/
}
