package controller;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import database.DBOperation;
import model.User;
import net.ChatBoxesServer;
import net.MyThread;
import ui.MainView;
import utils.Parser;
import utils.Protocol;

public class Operation {
	private MyThread holder;
	private MainView windows;
	public Operation(MyThread myThread, MainView windows) {
		this.holder = myThread;
		this.windows = windows;
	}
	public void doOper(String message) {
		//解析消息
		Parser parser = new Parser();
		String header = parser.parseHeader(message);
		if(header.equals("GetQQ")) {
			//用户请求获取qq号
			getQQ();
		}else if(header.equals("Register")) {
			//用户注册
			int qq = parser.parseGetQQ(message);
			String passwrod = parser.parseGetPassword(message);
			userRegister(qq, passwrod);
		}else if(header.equals("CheckQQ")) {
			//从消息中拿到qq号
			int qq = parser.parseGetQQ(message);
			//检查qq是否该qq是否存在
			CheckQQ(qq);
		}else if(header.equals("Login")) {
			//用户登录
			int qq = parser.parseGetQQ(message);
			String passwrod = parser.parseGetPassword(message);
			userLogin(qq, passwrod);
		}else if(header.equals("FindPassword")) {
			//找回密码
			int qq = parser.parseGetQQ(message);
			FindPassword(qq);
		}else if(header.equals("FindUsers")) {
			//用户上线
			int qq = parser.parseGetQQ(message);
			String username = parser.parseGetUsername(message);
			findUsers(qq, username);
		}else if(header.equals("SetName")) {
			//用户设置用户名
			int qq = parser.parseGetQQ(message);
			String name = parser.parseGetUsername(message);
			setName(qq, name);
		}else if(header.equals("ChangeName")) {
			//用户修改了用户名
			String oldName = parser.parseGetOldUsername(message);
			String newName = parser.parseGetNewUsername(message);
			int qq = parser.parseGetQQ(message);
			changeName(qq,oldName,newName);
		}else if(header.equals("ToOne")) {
			//用户的私发消息
			int qq = parser.parseGetQQ(message);
			String from = parser.parseGetFrom(message);
			String to = parser.parseGetTo(message);
			String msg = parser.parseGetMessage(message);
			toOneMessage(qq, from, to, msg);
		}else if(header.equals("ToAll")) {
			//用户的群发消息
			int qq = parser.parseGetQQ(message);
			String from = parser.parseGetFrom(message);
			String msg = parser.parseGetMessage(message);
			toAllMessage(qq, from, msg);
		}else if(header.equals("AddFriend")) {
			//添加好友申请
			int qs = parser.parseGetSourceQQ(message);
			String qo = parser.parseGetOtherQQ(message);
			String username = parser.parseGetUsername(message);
			addFriend(qs, qo, username, message);
		}else if(header.equals("Reply")) {
			//添加好友回应
			int qs = parser.parseGetSourceQQ(message);
			int qq = parser.parseGetQQ(message);
			boolean agree = parser.parseGetReply(message);
			reply(qs, qq, agree, message);
		}else if (header.equals("DeleteFriend")) {
			//删除好友请求
			int qs = parser.parseGetSourceQQ(message);
			String qo = parser.parseGetOtherQQ(message);
			deleteFriend(qs, qo, message);
		}else {
			//把消息原样返回去
			holder.sendMessage(message);
		}
	}
	//用户登录检验
	private void userLogin(int qq, String passwrod) {
		Protocol protocol = new Protocol();
		User u = DBOperation.findUser(qq);
		String message = null;
		if(u != null && u.getPassword().equals(passwrod)) {
			//密码正确
			message = protocol.getLoginMessage(true, u.getUsername());
		}else {
			message = protocol.getLoginMessage(false, "");	
		}
		holder.sendMessage(message);
	}
	//用户忘记密码，请求找回
	private void FindPassword(int qq) {
		Protocol protocol = new Protocol();
		User user = DBOperation.findUser(qq);
		String password = user.getPassword();
		String message = protocol.getFindPasswordMessage(password);
		holder.sendMessage(message);
	}
	//用户输入qq号，判断该qq号是否存在
	private void CheckQQ(int qq) {
		Protocol protocol = new Protocol();
		User result = DBOperation.findUser(qq);
		String message = null;
		if(null == result) {
			message = protocol.getQQExistMessage(false, "qq不存在!!!");
		}
		else {
			//查看该用户是否已经登录
			boolean flag = false;
			Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
			while (iterator.hasNext()) {
				User user = (User) iterator.next();
				if(user.getQq() == qq) {
					//说明该用户已经登录
					flag = true;
					break;
				}
			}
			if(flag)
				message = protocol.getQQExistMessage(false, "该用户已在别处登录!!!");
			else
				message = protocol.getQQExistMessage(true, "");
		}
		holder.sendMessage(message);
	}
	//用户注册，请求qq号
	private void getQQ() {
		Protocol protocol = new Protocol();
		int qq = DBOperation.getQQ();
		String message = protocol.getRegisterQQMessge(qq);
		holder.sendMessage(message);
	}
	//用户注册
	private void userRegister(int qq, String password) {
		int r = DBOperation.addUser(qq, password);
		System.out.println(r>0?"用户注册":"注册失败");
	}
	//新用户连接
	private void findUsers(int qq, String username) {
		Protocol protocol = new Protocol();
		//初始化用户
		User user = new User();
		user.setQq(qq);
		user.setUsername(username);
		//用户和监听线程保存到线程容器中
		ChatBoxesServer.addClientThread(user, holder);
		//更新用户列表
		windows.callback(null, user.show());
		//告诉所有用户除了他之外的所有其他在线用户,把他的信息告诉其他人
		Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
		User key = null;
		while (iterator.hasNext()) {
			key = (User) iterator.next();
			if(key.getQq() != qq) {
				ChatBoxesServer.findClientThread(key).sendMessage(protocol.getFindUsersMessage(qq, username, false));
			}
		}
		//初始化迭代器
		iterator = ChatBoxesServer.getClientSet().iterator();
		//再把其他在线的用户信息发送给他
		while (iterator.hasNext()) {
			key = (User) iterator.next();
			if(key.getQq() != qq) {
				holder.sendMessage(protocol.getFindUsersMessage(key.getQq(), key.getUsername(), false));
			}
		}
		//再把该用户的所有好友信息发给他
		List<User> friends = DBOperation.findFriends(qq);
		iterator = friends.iterator();
		while (iterator.hasNext()) {
			User friend = (User) iterator.next();
			holder.sendMessage(protocol.getFindUsersMessage(friend.getQq(), friend.getUsername(), true));
		}
		//再把他没有收到的消息发给他
		List<String> unreadMsgs = ChatBoxesServer.getUnreadMsgs(user.show());
		if(unreadMsgs != null) {
			//存在未读消息
			Iterator<String> i = unreadMsgs.iterator();
			while (i.hasNext()) {
				String msg = (String) i.next();
				holder.sendMessage(msg);
			}
		}
	}
	//用户第一次设置用户名
	public void setName(int qq, String name) {
		Protocol protocol = new Protocol();
		//更新数据库
		DBOperation.setUsernamne(qq, name);
		//更新服务器线程容器
		Set<User> users = ChatBoxesServer.getClientSet();
		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
			User key = (User) iterator.next();
			if(key.getQq() == qq) {
				key.setUsername(name);
				break;
			}
		}
		User newInfo = new User(qq, name);
		User oldInfo = new User(qq, "default");
		//更新用户列表
		windows.callback(oldInfo.show(), newInfo.show());
		//再把更新后的信息发给其他在线用户
		iterator = users.iterator();
		while (iterator.hasNext()) {
			User key = (User) iterator.next();
			if(key.getQq() != qq) {
				ChatBoxesServer.findClientThread(key).sendMessage(protocol.getSetNameMessage(qq, name));
			}
		}
	}
	
	//用户修改用户名
	public void changeName(int qq, String oldName, String newName) {
		Protocol protocol = new Protocol();
		//更新数据库
		DBOperation.setUsernamne(qq, newName);
		//修改用户名
		Set<User> users = ChatBoxesServer.getClientSet();
		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
			User key = (User) iterator.next();
			if(key.getQq() == qq) {
				key.setUsername(newName);
				break;
			}
		}
		User oldUser = new User(qq, oldName);
		User newUser = new User(qq, newName);
		//更新用户列表
		windows.callback(oldUser.show(), newUser.show());
		//发送通知给除了该用户外的所有用户，提醒他们有用户修改了用户名
		iterator = ChatBoxesServer.getClientSet().iterator();
		while (iterator.hasNext()) {
			User key = (User) iterator.next();
			if(key.getQq() != qq) {
				ChatBoxesServer.findClientThread(key).sendMessage(protocol.getChangeNameMessage(qq, oldName, newName));
			}
		}
	}
	//处理用户的私发消息
	public void toOneMessage(int qq, String from, String to, String message) {
		Protocol protocol = new Protocol();
		//在页面中显示此消息
		windows.callback(protocol.getTextMessage(from, to, message));
		//转发给对应的用户
		boolean flag = false;	//私聊对象在线标志
		Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
		while (iterator.hasNext()) {
			User key = (User) iterator.next();
			if(key.show().equals(to)) {
				flag = true;
				ChatBoxesServer.findClientThread(key).sendMessage(protocol.getToOneMessage(qq, from, to, message));
				break;
			}
		}
		if(!flag) {
			//私聊对象不在线,放到未读消息容器中，等该对象上线再发给他
			ChatBoxesServer.addUnreadMsg(to, protocol.getToOneMessage(qq, from, to, message));
		}
	}
	//处理用户的群发消息
	public void toAllMessage(int qq, String from, String message) {
		Protocol protocol = new Protocol();
		//在页面中显示此消息
		windows.callback(protocol.getTextMessage(from, null, message));
		//转发给其他用户
		Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
		while (iterator.hasNext()) {
			User key = (User) iterator.next();
			if(key.getQq() != qq) {
				ChatBoxesServer.findClientThread(key).sendMessage(protocol.getToAllMessage(qq, from, message));
			}
		}
	}
	//添加好友
	private void addFriend(int qs, String qo, String username, String message) {
//		找到目的用户
		User other = null;
		Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
		while (iterator.hasNext()) {
			other = (User) iterator.next();
			if(qo.equals(other.show())) {
				//把添加好友申请消息转发给他
				ChatBoxesServer.findClientThread(other).sendMessage(message);
				break;
			}
		}
	}
	//添加好友回应
	private void reply(int qs, int qq, boolean agree, String message) {
//		找到目的用户
		User user = null;
		Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
		while (iterator.hasNext()) {
			user = (User) iterator.next();
			if(qs == user.getQq()) {
				if(agree) {
					//同意，更新数据库
					DBOperation.addFriendShip(qs, qq);
				}
				//把添加好友申请消息转发给他
				ChatBoxesServer.findClientThread(user).sendMessage(message);
				break;
			}
		}
	}
	//删除好友
	private void deleteFriend(int qs, String qo, String message) {
		Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
		while (iterator.hasNext()) {
			User user = (User) iterator.next();
			if(qo.equals(user.show())) {
				//更新数据库
				DBOperation.deleteFriendShip(qs, user.getQq());
				//把删除好友消息转发给他
				ChatBoxesServer.findClientThread(user).sendMessage(message);
				break;
			}
		}
	}
	
	//用户退出
	public User userLogout() {
		//移除用户列表中对应的用户线程
		User client = ChatBoxesServer.removeClientThread(holder);
		
		if(client != null) {
			//回调页面，移除对应的用户名
			windows.callback(client.show(), null);
			//通知其他用户有人退出了
			if(null != client) {
				Protocol protocol = new Protocol();
				Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
				while (iterator.hasNext()) {
					User key = (User) iterator.next();
					if(client.getQq() != key.getQq()) {
						ChatBoxesServer.findClientThread(key).sendMessage(protocol.getLeaveMessage(client.getQq(), client.getUsername()));
					}
				}
			}
		}
		return client;
	}
}
