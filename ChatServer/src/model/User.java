package model;

public class User {
	private int qq;
	
	private String username;
	
	private String password;
	
	public int getQq() {
		return qq;
	}
	public void setQq(int qq) {
		this.qq = qq;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User() {
	}
	
	public User(int qq, String username) {
		this.qq = qq;
		this.username = username;
	}
	
	public String show() {
		return username+"("+qq+")";
	}
	
	@Override
	public String toString() {
		return "User [qq=" + qq + ", username=" + username + ", password=" + password + "]";
	}
}
