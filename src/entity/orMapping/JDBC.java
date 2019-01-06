package entity.orMapping;

public class JDBC {
	private String driver_class;
	private String url_path;
	private String db_userName;
	private String db_userPassword;

	public String getDriver_class() {
		return driver_class;
	}

	public void setDriver_class(String driver_class) {
		this.driver_class = driver_class;
	}

	public String getUrl_path() {
		return url_path;
	}

	public void setUrl_path(String url_path) {
		this.url_path = url_path;
	}

	public String getDb_userName() {
		return db_userName;
	}

	public void setDb_userName(String db_userName) {
		this.db_userName = db_userName;
	}

	public String getDb_userPassword() {
		return db_userPassword;
	}

	public void setDb_userPassword(String db_userPassword) {
		this.db_userPassword = db_userPassword;
	}

	@Override
	public String toString() {
		return "JDBC [driver_class=" + driver_class + ", url_path=" + url_path + ", db_userName=" + db_userName
				+ ", db_userPassword=" + db_userPassword + "]";
	}
	
	
}
