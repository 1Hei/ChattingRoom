package controller;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ui.LoginView;
import ui.MainView;
import ui.RegisterView;
import utils.Parser;
import utils.Protocol;

//接收到服务器消息处理对象
public class Operation {
	//登录窗口
	private LoginView loginView;
	//注册窗口
	private RegisterView registerView;
	//消息窗口
	private MainView mainView;
	
	public Operation(JFrame windows) {
		loginView = (LoginView) windows;
	}
	//更新当前窗口
	public void updateWindows(JFrame windows) {
		if(windows instanceof LoginView) {
			loginView = (LoginView) windows;
		}else if(windows instanceof RegisterView) {
			registerView = (RegisterView) windows;
		}else {
			mainView = (MainView) windows;
		}
	}
	public void doOper(String message) {
		//解析消息
		Parser parser = new Parser();
		//拿到消息头
		String header = parser.parseGetHeader(message);
		if(header.equals("GetQQ")) {
			//请求注册qq
			String qq = parser.parseGetQQ(message);
			registerView.setQQ(qq);
		}else if(header.equals("CheckQQ")) {
			//检测qq是否存在
			boolean isExist = parser.parseIsQQExistMessage(message);
			String failReason = parser.parseGetFailReason(message);
			checkQQ(isExist, failReason);
		}else if(header.equals("FindPassword")) {
			//找回密码响应
			String password = parser.parseGetPassword(message);
			findPassword(password);
		}else if (header.equals("Login")) {
			//判断登录是否成功
			boolean login = parser.parseLoginMessage(message);
			String username = parser.parseGetUsername(message);
			userLogin(login, username);
		}else if(header.equals("FindUsers")) {
			//用户上线
			String qq = parser.parseGetQQ(message);
			String name = parser.parseGetUsername(message);
			boolean isFriend = parser.parseIsFriend(message);
			userLogin(qq, name, isFriend);
		}else if(header.equals("SetName")) {
			//用户设置用户名
			String qq = parser.parseGetQQ(message);
			String username = parser.parseGetUsername(message);
			setName(qq, username);
		}else if(header.equals("ChangeName")) {
			//用户修改了用户名
			String qq = parser.parseGetQQ(message);
			String oldName = parser.parseGetOldUsername(message);
			String newName = parser.parseGetNewUsername(message);
			changeName(qq, oldName,newName);
		}else if(header.equals("ToOne")) {
			//用户的私发消息（私发消息的发往对象要单独解析出来）
			String from = parser.parseGetFrom(message);
			String qq = parser.parseGetQQ(message);
			String msg = parser.parseGetMessage(message);
			haveMessge(from, qq, msg);
		}else if(header.equals("ToAll")) {
			//用户的群发消息
			String from = parser.parseGetFrom(message);
			String msg = parser.parseGetMessage(message);
			toAllMessage(from, msg);
		}else if(header.equals("AddFriend")){
			//添加好友申请
			String qs = parser.parseGetSourceQQ(message);
			String name = parser.parseGetUsername(message);
			addFriend(qs, name);
		}else if (header.equals("DeleteFriend")) {
			String qs = parser.parseGetSourceQQ(message);
			String name = parser.parseGetUsername(message);
			deleteFriend(qs, name);
		}else if(header.equals("Reply")){
			//添加好友回复
			String qq = parser.parseGetQQ(message);
			String name = parser.parseGetUsername(message);
			boolean agree = parser.parseIsAgree(message);
			reply(qq, name, agree);
		}else if(header.equals("Leave")){
			//用户退出
			String qq = parser.parseGetQQ(message);
			String name = parser.parseGetUsername(message);
			userLogout(qq, name);
		}else {
			//无法识别的消息，直接在页面中显示
			mainView.callback(message);
		}
	}
	//用户登录返回判断
	private void userLogin(boolean login, String username) {
		loginView.login(login, username);
	}
	private void findPassword(String password) {
		//弹窗显示密码
		JOptionPane.showMessageDialog(loginView, "您的密码为:"+password+" 请牢记!", "找回密码", JOptionPane.INFORMATION_MESSAGE);
	}
	//验证qq和密码是否正确
	private void checkQQ(boolean isExist, String failReason) {
		//根据返回结果页面显示
		loginView.checkQQ(isExist, failReason);
	}
	//新用户连接
	private void userLogin(String qq, String name, boolean isFriend) {
		Protocol protocol = new Protocol();
		String u = protocol.getUserInfo(qq, name);
		//更新用户列表
		mainView.initUserList(u, isFriend);
	}
	//新用户设置用户名
	private void setName(String qq, String username) {
		Protocol protocol = new Protocol();
		String oldInfo = protocol.getUserInfo(qq, "default");
		String newInfo = protocol.getUserInfo(qq, username);
		//更新用户列表
		mainView.changeName(oldInfo, newInfo);
	}
	//用户修改用户名
	private void changeName(String qq, String oldName, String newName) {
		Protocol protocol = new Protocol();
		String oldInfo = protocol.getUserInfo(qq, oldName);
		String newInfo = protocol.getUserInfo(qq, newName);
		//更新用户列表
		mainView.changeName(oldInfo, newInfo);
	}
	//处理用户的私发消息
	private void haveMessge(String from, String qq, String message) {
		//在页面中显示此消息
		Protocol protocol = new Protocol();
		String userInfo = protocol.getUserInfo(qq, from);
		mainView.haveMessage(userInfo, message);
	}
	//添加好友申请
	private void addFriend(String qs, String name) {
		mainView.addFriend(qs, name);
	}
	//解除好友关系
	private void deleteFriend(String qs, String name) {
		mainView.deleteFriend(qs, name);
	}
	//处理用户的群发消息
	private void toAllMessage(String from, String message) {
		//在页面中显示此消息
		Protocol protocol = new Protocol();
		mainView.callback(protocol.getTextMessage(from, null, message));
	}
	//处理添加好友回应
	private void reply(String qq, String name, boolean agree) {
		Protocol protocol = new Protocol();
		String userInfo = protocol.getUserInfo(qq, name);
		if(agree) {
			//同意回调
			JOptionPane.showMessageDialog(mainView, userInfo + "同意了你的好友申请！", "agree", JOptionPane.INFORMATION_MESSAGE);
			mainView.setFriend(userInfo, "friend.png", true);
		}else {
			//拒绝
			JOptionPane.showMessageDialog(mainView, userInfo + "拒绝了你的好友申请！", "deny", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	//有用户退出
	private void userLogout(String qq, String name) {
		Protocol protocol = new Protocol();
		String userInfo = protocol.getUserInfo(qq, name);
		//回调页面，移除对应的用户名
		mainView.userLeave(userInfo);
	}
}
