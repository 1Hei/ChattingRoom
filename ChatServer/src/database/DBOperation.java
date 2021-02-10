package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import model.User;

public class DBOperation {
	
	public static void start() {
		Connection connection = ConnectionUtils.getConnection();
		String sql = "select * from users";
		PreparedStatement ps = null;
		ResultSet result = null;
		try {
			ps = connection.prepareStatement(sql);
			result = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionUtils.release(connection, ps, result);
		}
	}
	//添加用户
	public synchronized static int getQQ() {
		Connection connection = ConnectionUtils.getConnection();
		String sql = "select max(qq) from users";
		PreparedStatement ps = null;
		ResultSet result = null;
		int i = 100000;
		try {
			ps = connection.prepareStatement(sql);
			result = ps.executeQuery();
			result.next();
			int tmp = result.getInt(1);
			if(tmp >= i) {
				i=tmp+1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionUtils.release(connection, ps, result);
		}
		return i;
	}
	//查找用户
	//判断qq是否存在
	public static User findUser(int qq) {
		QueryRunner qr = new QueryRunner();
		Connection connection = ConnectionUtils.getConnection();
		String sql = "select * from users where qq = ?";
		User user = null;
		try {
			user = qr.query(connection, sql, new BeanHandler<>(User.class), qq);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionUtils.release(connection);
		}
		return user;
	}
	public synchronized static void setUsernamne(int qq, String username) {
		Connection connection = ConnectionUtils.getConnection();
		String sql = "update users set username=? where qq=?";
		PreparedStatement ps = null;
		ResultSet result = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			ps.setInt(2, qq);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionUtils.release(connection, ps, result);
		}
	}
	public synchronized static int addUser(int qq, String password) {
		Connection connection = ConnectionUtils.getConnection();
		String sql = "insert into users(qq, username, password) values (?,?,?)";
		PreparedStatement ps = null;
		ResultSet result = null;
		int r = 0;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, qq);
			ps.setString(2, "default");
			ps.setString(3, password);
			r = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionUtils.release(connection, ps, result);
		}
		return r;
	}
	//查找用户的所有好友信息
	public static List<User> findFriends(int qq) {
		QueryRunner qr = new QueryRunner();
		Connection connection = ConnectionUtils.getConnection();
		String sql = "SELECT users.qq AS qq, users.username AS username FROM users,ur WHERE (qs = ? OR qo = ?) AND (qq = qs OR qq = qo) AND qq != ?";
		List<User> friends = null;
		try {
			friends = qr.query(connection, sql, new BeanListHandler<>(User.class), qq, qq, qq);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionUtils.release(connection);
		}
		return friends;
	}
	
	//解除用户关系
	public synchronized static void deleteFriendShip(int qs, int qo) {
		Connection connection = ConnectionUtils.getConnection();
		String sql = "delete from ur where (qs=? and qo=?) or (qs=? and qo=?)";
		PreparedStatement ps = null;
		ResultSet result = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, qs);
			ps.setInt(2, qo);
			ps.setInt(3, qo);
			ps.setInt(4, qs);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionUtils.release(connection, ps, result);
		}
	}
	//建立用户关系
	public synchronized static void addFriendShip(int qs, int qo) {
		Connection connection = ConnectionUtils.getConnection();
		String sql = "insert into ur(qs, qo) values (?,?)";
		PreparedStatement ps = null;
		ResultSet result = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, qs);
			ps.setInt(2, qo);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionUtils.release(connection, ps, result);
		}
	}
}
