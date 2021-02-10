package database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtils {
	private static String jdbcUrl;
	private static String user;
	private static String password;
	
	static {
		Properties props = new Properties();
		//通过类加载器拿到配置文件的输入流
		InputStream is = ConnectionUtils.class.getClassLoader().getResourceAsStream("db.properties");
		//加载配置文件
		try {
			props.load(is);
			Class.forName(props.getProperty("driverClass"));
			jdbcUrl = props.getProperty("jdbcUrl");
			user = props.getProperty("user");
			password = props.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				//关闭输入流
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//获取连接
	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(jdbcUrl, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	//释放连接
		public static void release(Connection connection) {
			release(connection, null, null);
		}
		public static void release(Connection connection, PreparedStatement ps) {
			release(connection, ps, null);
		}
		public static void release(Connection connection, PreparedStatement ps, ResultSet result) {
			try {
				if(null != connection && !connection.isClosed()) {
					connection.close();
				}
				if(null != ps && !ps.isClosed()) {
					ps.close();
				}
				if(null != result && !result.isClosed()) {
					result.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

}
