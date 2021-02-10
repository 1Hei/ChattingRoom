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
		//������Ϣ
		Parser parser = new Parser();
		String header = parser.parseHeader(message);
		if(header.equals("GetQQ")) {
			//�û������ȡqq��
			getQQ();
		}else if(header.equals("Register")) {
			//�û�ע��
			int qq = parser.parseGetQQ(message);
			String passwrod = parser.parseGetPassword(message);
			userRegister(qq, passwrod);
		}else if(header.equals("CheckQQ")) {
			//����Ϣ���õ�qq��
			int qq = parser.parseGetQQ(message);
			//���qq�Ƿ��qq�Ƿ����
			CheckQQ(qq);
		}else if(header.equals("Login")) {
			//�û���¼
			int qq = parser.parseGetQQ(message);
			String passwrod = parser.parseGetPassword(message);
			userLogin(qq, passwrod);
		}else if(header.equals("FindPassword")) {
			//�һ�����
			int qq = parser.parseGetQQ(message);
			FindPassword(qq);
		}else if(header.equals("FindUsers")) {
			//�û�����
			int qq = parser.parseGetQQ(message);
			String username = parser.parseGetUsername(message);
			findUsers(qq, username);
		}else if(header.equals("SetName")) {
			//�û������û���
			int qq = parser.parseGetQQ(message);
			String name = parser.parseGetUsername(message);
			setName(qq, name);
		}else if(header.equals("ChangeName")) {
			//�û��޸����û���
			String oldName = parser.parseGetOldUsername(message);
			String newName = parser.parseGetNewUsername(message);
			int qq = parser.parseGetQQ(message);
			changeName(qq,oldName,newName);
		}else if(header.equals("ToOne")) {
			//�û���˽����Ϣ
			int qq = parser.parseGetQQ(message);
			String from = parser.parseGetFrom(message);
			String to = parser.parseGetTo(message);
			String msg = parser.parseGetMessage(message);
			toOneMessage(qq, from, to, msg);
		}else if(header.equals("ToAll")) {
			//�û���Ⱥ����Ϣ
			int qq = parser.parseGetQQ(message);
			String from = parser.parseGetFrom(message);
			String msg = parser.parseGetMessage(message);
			toAllMessage(qq, from, msg);
		}else if(header.equals("AddFriend")) {
			//��Ӻ�������
			int qs = parser.parseGetSourceQQ(message);
			String qo = parser.parseGetOtherQQ(message);
			String username = parser.parseGetUsername(message);
			addFriend(qs, qo, username, message);
		}else if(header.equals("Reply")) {
			//��Ӻ��ѻ�Ӧ
			int qs = parser.parseGetSourceQQ(message);
			int qq = parser.parseGetQQ(message);
			boolean agree = parser.parseGetReply(message);
			reply(qs, qq, agree, message);
		}else if (header.equals("DeleteFriend")) {
			//ɾ����������
			int qs = parser.parseGetSourceQQ(message);
			String qo = parser.parseGetOtherQQ(message);
			deleteFriend(qs, qo, message);
		}else {
			//����Ϣԭ������ȥ
			holder.sendMessage(message);
		}
	}
	//�û���¼����
	private void userLogin(int qq, String passwrod) {
		Protocol protocol = new Protocol();
		User u = DBOperation.findUser(qq);
		String message = null;
		if(u != null && u.getPassword().equals(passwrod)) {
			//������ȷ
			message = protocol.getLoginMessage(true, u.getUsername());
		}else {
			message = protocol.getLoginMessage(false, "");	
		}
		holder.sendMessage(message);
	}
	//�û��������룬�����һ�
	private void FindPassword(int qq) {
		Protocol protocol = new Protocol();
		User user = DBOperation.findUser(qq);
		String password = user.getPassword();
		String message = protocol.getFindPasswordMessage(password);
		holder.sendMessage(message);
	}
	//�û�����qq�ţ��жϸ�qq���Ƿ����
	private void CheckQQ(int qq) {
		Protocol protocol = new Protocol();
		User result = DBOperation.findUser(qq);
		String message = null;
		if(null == result) {
			message = protocol.getQQExistMessage(false, "qq������!!!");
		}
		else {
			//�鿴���û��Ƿ��Ѿ���¼
			boolean flag = false;
			Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
			while (iterator.hasNext()) {
				User user = (User) iterator.next();
				if(user.getQq() == qq) {
					//˵�����û��Ѿ���¼
					flag = true;
					break;
				}
			}
			if(flag)
				message = protocol.getQQExistMessage(false, "���û����ڱ𴦵�¼!!!");
			else
				message = protocol.getQQExistMessage(true, "");
		}
		holder.sendMessage(message);
	}
	//�û�ע�ᣬ����qq��
	private void getQQ() {
		Protocol protocol = new Protocol();
		int qq = DBOperation.getQQ();
		String message = protocol.getRegisterQQMessge(qq);
		holder.sendMessage(message);
	}
	//�û�ע��
	private void userRegister(int qq, String password) {
		int r = DBOperation.addUser(qq, password);
		System.out.println(r>0?"�û�ע��":"ע��ʧ��");
	}
	//���û�����
	private void findUsers(int qq, String username) {
		Protocol protocol = new Protocol();
		//��ʼ���û�
		User user = new User();
		user.setQq(qq);
		user.setUsername(username);
		//�û��ͼ����̱߳��浽�߳�������
		ChatBoxesServer.addClientThread(user, holder);
		//�����û��б�
		windows.callback(null, user.show());
		//���������û�������֮����������������û�,��������Ϣ����������
		Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
		User key = null;
		while (iterator.hasNext()) {
			key = (User) iterator.next();
			if(key.getQq() != qq) {
				ChatBoxesServer.findClientThread(key).sendMessage(protocol.getFindUsersMessage(qq, username, false));
			}
		}
		//��ʼ��������
		iterator = ChatBoxesServer.getClientSet().iterator();
		//�ٰ��������ߵ��û���Ϣ���͸���
		while (iterator.hasNext()) {
			key = (User) iterator.next();
			if(key.getQq() != qq) {
				holder.sendMessage(protocol.getFindUsersMessage(key.getQq(), key.getUsername(), false));
			}
		}
		//�ٰѸ��û������к�����Ϣ������
		List<User> friends = DBOperation.findFriends(qq);
		iterator = friends.iterator();
		while (iterator.hasNext()) {
			User friend = (User) iterator.next();
			holder.sendMessage(protocol.getFindUsersMessage(friend.getQq(), friend.getUsername(), true));
		}
		//�ٰ���û���յ�����Ϣ������
		List<String> unreadMsgs = ChatBoxesServer.getUnreadMsgs(user.show());
		if(unreadMsgs != null) {
			//����δ����Ϣ
			Iterator<String> i = unreadMsgs.iterator();
			while (i.hasNext()) {
				String msg = (String) i.next();
				holder.sendMessage(msg);
			}
		}
	}
	//�û���һ�������û���
	public void setName(int qq, String name) {
		Protocol protocol = new Protocol();
		//�������ݿ�
		DBOperation.setUsernamne(qq, name);
		//���·������߳�����
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
		//�����û��б�
		windows.callback(oldInfo.show(), newInfo.show());
		//�ٰѸ��º����Ϣ�������������û�
		iterator = users.iterator();
		while (iterator.hasNext()) {
			User key = (User) iterator.next();
			if(key.getQq() != qq) {
				ChatBoxesServer.findClientThread(key).sendMessage(protocol.getSetNameMessage(qq, name));
			}
		}
	}
	
	//�û��޸��û���
	public void changeName(int qq, String oldName, String newName) {
		Protocol protocol = new Protocol();
		//�������ݿ�
		DBOperation.setUsernamne(qq, newName);
		//�޸��û���
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
		//�����û��б�
		windows.callback(oldUser.show(), newUser.show());
		//����֪ͨ�����˸��û���������û��������������û��޸����û���
		iterator = ChatBoxesServer.getClientSet().iterator();
		while (iterator.hasNext()) {
			User key = (User) iterator.next();
			if(key.getQq() != qq) {
				ChatBoxesServer.findClientThread(key).sendMessage(protocol.getChangeNameMessage(qq, oldName, newName));
			}
		}
	}
	//�����û���˽����Ϣ
	public void toOneMessage(int qq, String from, String to, String message) {
		Protocol protocol = new Protocol();
		//��ҳ������ʾ����Ϣ
		windows.callback(protocol.getTextMessage(from, to, message));
		//ת������Ӧ���û�
		boolean flag = false;	//˽�Ķ������߱�־
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
			//˽�Ķ�������,�ŵ�δ����Ϣ�����У��ȸö��������ٷ�����
			ChatBoxesServer.addUnreadMsg(to, protocol.getToOneMessage(qq, from, to, message));
		}
	}
	//�����û���Ⱥ����Ϣ
	public void toAllMessage(int qq, String from, String message) {
		Protocol protocol = new Protocol();
		//��ҳ������ʾ����Ϣ
		windows.callback(protocol.getTextMessage(from, null, message));
		//ת���������û�
		Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
		while (iterator.hasNext()) {
			User key = (User) iterator.next();
			if(key.getQq() != qq) {
				ChatBoxesServer.findClientThread(key).sendMessage(protocol.getToAllMessage(qq, from, message));
			}
		}
	}
	//��Ӻ���
	private void addFriend(int qs, String qo, String username, String message) {
//		�ҵ�Ŀ���û�
		User other = null;
		Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
		while (iterator.hasNext()) {
			other = (User) iterator.next();
			if(qo.equals(other.show())) {
				//����Ӻ���������Ϣת������
				ChatBoxesServer.findClientThread(other).sendMessage(message);
				break;
			}
		}
	}
	//��Ӻ��ѻ�Ӧ
	private void reply(int qs, int qq, boolean agree, String message) {
//		�ҵ�Ŀ���û�
		User user = null;
		Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
		while (iterator.hasNext()) {
			user = (User) iterator.next();
			if(qs == user.getQq()) {
				if(agree) {
					//ͬ�⣬�������ݿ�
					DBOperation.addFriendShip(qs, qq);
				}
				//����Ӻ���������Ϣת������
				ChatBoxesServer.findClientThread(user).sendMessage(message);
				break;
			}
		}
	}
	//ɾ������
	private void deleteFriend(int qs, String qo, String message) {
		Iterator<User> iterator = ChatBoxesServer.getClientSet().iterator();
		while (iterator.hasNext()) {
			User user = (User) iterator.next();
			if(qo.equals(user.show())) {
				//�������ݿ�
				DBOperation.deleteFriendShip(qs, user.getQq());
				//��ɾ��������Ϣת������
				ChatBoxesServer.findClientThread(user).sendMessage(message);
				break;
			}
		}
	}
	
	//�û��˳�
	public User userLogout() {
		//�Ƴ��û��б��ж�Ӧ���û��߳�
		User client = ChatBoxesServer.removeClientThread(holder);
		
		if(client != null) {
			//�ص�ҳ�棬�Ƴ���Ӧ���û���
			windows.callback(client.show(), null);
			//֪ͨ�����û������˳���
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
