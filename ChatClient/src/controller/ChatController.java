package controller;

import javax.swing.JFrame;

import net.Client;
import utils.Protocol;

public class ChatController {
	//获取qq
	public void getQQ() {
		//发送获取qq请求
		Protocol protocol = new Protocol();
		String msg = protocol.getQQMessage();
		Client.sendMessage(msg);
	}
	//用户注册
	public void userRegister(String qq, String password) {
		Protocol protocol = new Protocol();
		String msg = protocol.getRegisterMessage(qq, password);
		Client.sendMessage(msg);
	}
	//验证qq号是否存在
	public void checkQQIsExist(String qq) {
		//判断qq是否能转成整型
		try {
			Integer.parseInt(qq);
		} catch (NumberFormatException e) {
			qq = 0+"";
		}
		Protocol protocol = new Protocol();
		String msg = protocol.getCheckQQMessage(qq);
		Client.sendMessage(msg);
	}
	//找回密码
	public void findPassword(String qq) {
		Protocol protocol = new Protocol();
		String msg = protocol.getFindPasswordMessage(qq);
		Client.sendMessage(msg);
	}
	//用户登录验证
	public void userLogin(String qq, String password) {
		Protocol protocol = new Protocol();
		String msg = protocol.getLoginMessage(qq, password);
		Client.sendMessage(msg);
	}
	//向服务请求好友和在线用户列表
	public void findUsers(String qq, String username) {
		Protocol protocol = new Protocol();
		String msg = protocol.getFindUsersMessage(qq, username);
		Client.sendMessage(msg);
	}
	
	//用户设置用户名，发送用户名给服务器
	public void setName(String qq, String name) {
		Protocol protocol = new Protocol();
		String msg = protocol.getSetNameMessage(qq, name);
		//发送消息给服务器端
		Client.sendMessage(msg);
	}

	//用户修改用户名，发送老用户名和新用户名给服务器
	public void changeName(String qq, String oldName, String newName) {
		Protocol protocol = new Protocol();
		String msg = protocol.getChangeNameMessage(qq, oldName, newName);
		//发送消息给服务器端
		Client.sendMessage(msg);
		
	}
	
	//发送群发消息
	public void sendToAllMessage(String qq, String from, String message) {
		Protocol protocol = new Protocol();
		String msg = protocol.getToAllMessage(qq, from, message);
		//发送消息给服务器端
		Client.sendMessage(msg);
	}

	//发送私发消息
	public void sendToOneMessage(String qq, String from, String to, String message) {
		Protocol protocol = new Protocol();
		String msg = protocol.getToOneMessage(qq, from, to, message);
		//发送消息给服务器端
		Client.sendMessage(msg);
	}
	
	//发送添加好友申请
	public void addFriend(String qq_from, String qq_to, String username) {
		Protocol protocol = new Protocol();
		String msg = protocol.getAddFriendMessage(qq_from, qq_to, username);
		//发送消息给服务器端
		Client.sendMessage(msg);
	}
	//删除好友
	public void deleteFriend(String qq_from, String qq_to, String username) {
		Protocol protocol = new Protocol();
		String msg = protocol.getDeleteFriendMessage(qq_from, qq_to, username);
		//发送消息给服务器端
		Client.sendMessage(msg);
	}
	
	//发送好友申请处理回复
	public void addFriendReply(String qs, String qq, String username, boolean agree) {
		Protocol protocol = new Protocol();
		String msg = protocol.getAddFriendReplyMessage(qs, qq, username, agree);
		//发送消息给服务器端
		Client.sendMessage(msg);
	}
	
	//启动主界面监听线程
	public void startReceiver(JFrame windows) {
		//启动读线程
		Client.startReceiver(windows);
	}
	
	//设置操作窗口
	public void setOperateWindows(JFrame windows) {
		Client.setOperateWindows(windows);
	}

	//程序关闭，释放资源，发送关闭信息给服务器
	public void close() {
		//停掉监听线程
		//发送byebye给服务器
		Client.sendMessage("byebye");
		//释放资源
		Client.releaseResources();
	}
}
