package controller;

import javax.swing.JFrame;

import net.Client;
import utils.Protocol;

public class ChatController {
	//��ȡqq
	public void getQQ() {
		//���ͻ�ȡqq����
		Protocol protocol = new Protocol();
		String msg = protocol.getQQMessage();
		Client.sendMessage(msg);
	}
	//�û�ע��
	public void userRegister(String qq, String password) {
		Protocol protocol = new Protocol();
		String msg = protocol.getRegisterMessage(qq, password);
		Client.sendMessage(msg);
	}
	//��֤qq���Ƿ����
	public void checkQQIsExist(String qq) {
		//�ж�qq�Ƿ���ת������
		try {
			Integer.parseInt(qq);
		} catch (NumberFormatException e) {
			qq = 0+"";
		}
		Protocol protocol = new Protocol();
		String msg = protocol.getCheckQQMessage(qq);
		Client.sendMessage(msg);
	}
	//�һ�����
	public void findPassword(String qq) {
		Protocol protocol = new Protocol();
		String msg = protocol.getFindPasswordMessage(qq);
		Client.sendMessage(msg);
	}
	//�û���¼��֤
	public void userLogin(String qq, String password) {
		Protocol protocol = new Protocol();
		String msg = protocol.getLoginMessage(qq, password);
		Client.sendMessage(msg);
	}
	//�����������Ѻ������û��б�
	public void findUsers(String qq, String username) {
		Protocol protocol = new Protocol();
		String msg = protocol.getFindUsersMessage(qq, username);
		Client.sendMessage(msg);
	}
	
	//�û������û����������û�����������
	public void setName(String qq, String name) {
		Protocol protocol = new Protocol();
		String msg = protocol.getSetNameMessage(qq, name);
		//������Ϣ����������
		Client.sendMessage(msg);
	}

	//�û��޸��û������������û��������û�����������
	public void changeName(String qq, String oldName, String newName) {
		Protocol protocol = new Protocol();
		String msg = protocol.getChangeNameMessage(qq, oldName, newName);
		//������Ϣ����������
		Client.sendMessage(msg);
		
	}
	
	//����Ⱥ����Ϣ
	public void sendToAllMessage(String qq, String from, String message) {
		Protocol protocol = new Protocol();
		String msg = protocol.getToAllMessage(qq, from, message);
		//������Ϣ����������
		Client.sendMessage(msg);
	}

	//����˽����Ϣ
	public void sendToOneMessage(String qq, String from, String to, String message) {
		Protocol protocol = new Protocol();
		String msg = protocol.getToOneMessage(qq, from, to, message);
		//������Ϣ����������
		Client.sendMessage(msg);
	}
	
	//������Ӻ�������
	public void addFriend(String qq_from, String qq_to, String username) {
		Protocol protocol = new Protocol();
		String msg = protocol.getAddFriendMessage(qq_from, qq_to, username);
		//������Ϣ����������
		Client.sendMessage(msg);
	}
	//ɾ������
	public void deleteFriend(String qq_from, String qq_to, String username) {
		Protocol protocol = new Protocol();
		String msg = protocol.getDeleteFriendMessage(qq_from, qq_to, username);
		//������Ϣ����������
		Client.sendMessage(msg);
	}
	
	//���ͺ������봦��ظ�
	public void addFriendReply(String qs, String qq, String username, boolean agree) {
		Protocol protocol = new Protocol();
		String msg = protocol.getAddFriendReplyMessage(qs, qq, username, agree);
		//������Ϣ����������
		Client.sendMessage(msg);
	}
	
	//��������������߳�
	public void startReceiver(JFrame windows) {
		//�������߳�
		Client.startReceiver(windows);
	}
	
	//���ò�������
	public void setOperateWindows(JFrame windows) {
		Client.setOperateWindows(windows);
	}

	//����رգ��ͷ���Դ�����͹ر���Ϣ��������
	public void close() {
		//ͣ�������߳�
		//����byebye��������
		Client.sendMessage("byebye");
		//�ͷ���Դ
		Client.releaseResources();
	}
}
