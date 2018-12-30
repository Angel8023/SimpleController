package sc.ustc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.UserBean;

public class BaseDAO {
	protected String driver;
	protected String url;
	protected String userName;
	protected String userPassword;
	protected Connection connection;
	protected PreparedStatement ps;
	protected ResultSet rs;

	public Connection openDBCpnnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, userName, userPassword);
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

	Object query(String sql) {
		UserBean userBean = new UserBean();
		connection = openDBCpnnection();
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				userBean.setUserId(rs.getString("userID").trim());
				userBean.setUserName(rs.getString("userName").trim());
				userBean.setUserPass(rs.getString("userPass").trim());								
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(rs, ps, connection);
		}
		return userBean;
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

	public String getDriver() {
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
	}
}
