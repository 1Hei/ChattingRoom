package controller;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ui.LoginView;
import ui.MainView;
import ui.RegisterView;
import utils.Parser;
import utils.Protocol;

//���յ���������Ϣ�������
public class Operation {
	//��¼����
	private LoginView loginView;
	//ע�ᴰ��
	private RegisterView registerView;
	//��Ϣ����
	private MainView mainView;
	
	public Operation(JFrame windows) {
		loginView = (LoginView) windows;
	}
	//���µ�ǰ����
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
		//������Ϣ
		Parser parser = new Parser();
		//�õ���Ϣͷ
		String header = parser.parseGetHeader(message);
		if(header.equals("GetQQ")) {
			//����ע��qq
			String qq = parser.parseGetQQ(message);
			registerView.setQQ(qq);
		}else if(header.equals("CheckQQ")) {
			//���qq�Ƿ����
			boolean isExist = parser.parseIsQQExistMessage(message);
			String failReason = parser.parseGetFailReason(message);
			checkQQ(isExist, failReason);
		}else if(header.equals("FindPassword")) {
			//�һ�������Ӧ
			String password = parser.parseGetPassword(message);
			findPassword(password);
		}else if (header.equals("Login")) {
			//�жϵ�¼�Ƿ�ɹ�
			boolean login = parser.parseLoginMessage(message);
			String username = parser.parseGetUsername(message);
			userLogin(login, username);
		}else if(header.equals("FindUsers")) {
			//�û�����
			String qq = parser.parseGetQQ(message);
			String name = parser.parseGetUsername(message);
			boolean isFriend = parser.parseIsFriend(message);
			userLogin(qq, name, isFriend);
		}else if(header.equals("SetName")) {
			//�û������û���
			String qq = parser.parseGetQQ(message);
			String username = parser.parseGetUsername(message);
			setName(qq, username);
		}else if(header.equals("ChangeName")) {
			//�û��޸����û���
			String qq = parser.parseGetQQ(message);
			String oldName = parser.parseGetOldUsername(message);
			String newName = parser.parseGetNewUsername(message);
			changeName(qq, oldName,newName);
		}else if(header.equals("ToOne")) {
			//�û���˽����Ϣ��˽����Ϣ�ķ�������Ҫ��������������
			String from = parser.parseGetFrom(message);
			String qq = parser.parseGetQQ(message);
			String msg = parser.parseGetMessage(message);
			haveMessge(from, qq, msg);
		}else if(header.equals("ToAll")) {
			//�û���Ⱥ����Ϣ
			String from = parser.parseGetFrom(message);
			String msg = parser.parseGetMessage(message);
			toAllMessage(from, msg);
		}else if(header.equals("AddFriend")){
			//��Ӻ�������
			String qs = parser.parseGetSourceQQ(message);
			String name = parser.parseGetUsername(message);
			addFriend(qs, name);
		}else if (header.equals("DeleteFriend")) {
			String qs = parser.parseGetSourceQQ(message);
			String name = parser.parseGetUsername(message);
			deleteFriend(qs, name);
		}else if(header.equals("Reply")){
			//��Ӻ��ѻظ�
			String qq = parser.parseGetQQ(message);
			String name = parser.parseGetUsername(message);
			boolean agree = parser.parseIsAgree(message);
			reply(qq, name, agree);
		}else if(header.equals("Leave")){
			//�û��˳�
			String qq = parser.parseGetQQ(message);
			String name = parser.parseGetUsername(message);
			userLogout(qq, name);
		}else {
			//�޷�ʶ�����Ϣ��ֱ����ҳ������ʾ
			mainView.callback(message);
		}
	}
	//�û���¼�����ж�
	private void userLogin(boolean login, String username) {
		loginView.login(login, username);
	}
	private void findPassword(String password) {
		//������ʾ����
		JOptionPane.showMessageDialog(loginView, "��������Ϊ:"+password+" ���μ�!", "�һ�����", JOptionPane.INFORMATION_MESSAGE);
	}
	//��֤qq�������Ƿ���ȷ
	private void checkQQ(boolean isExist, String failReason) {
		//���ݷ��ؽ��ҳ����ʾ
		loginView.checkQQ(isExist, failReason);
	}
	//���û�����
	private void userLogin(String qq, String name, boolean isFriend) {
		Protocol protocol = new Protocol();
		String u = protocol.getUserInfo(qq, name);
		//�����û��б�
		mainView.initUserList(u, isFriend);
	}
	//���û������û���
	private void setName(String qq, String username) {
		Protocol protocol = new Protocol();
		String oldInfo = protocol.getUserInfo(qq, "default");
		String newInfo = protocol.getUserInfo(qq, username);
		//�����û��б�
		mainView.changeName(oldInfo, newInfo);
	}
	//�û��޸��û���
	private void changeName(String qq, String oldName, String newName) {
		Protocol protocol = new Protocol();
		String oldInfo = protocol.getUserInfo(qq, oldName);
		String newInfo = protocol.getUserInfo(qq, newName);
		//�����û��б�
		mainView.changeName(oldInfo, newInfo);
	}
	//�����û���˽����Ϣ
	private void haveMessge(String from, String qq, String message) {
		//��ҳ������ʾ����Ϣ
		Protocol protocol = new Protocol();
		String userInfo = protocol.getUserInfo(qq, from);
		mainView.haveMessage(userInfo, message);
	}
	//��Ӻ�������
	private void addFriend(String qs, String name) {
		mainView.addFriend(qs, name);
	}
	//������ѹ�ϵ
	private void deleteFriend(String qs, String name) {
		mainView.deleteFriend(qs, name);
	}
	//�����û���Ⱥ����Ϣ
	private void toAllMessage(String from, String message) {
		//��ҳ������ʾ����Ϣ
		Protocol protocol = new Protocol();
		mainView.callback(protocol.getTextMessage(from, null, message));
	}
	//������Ӻ��ѻ�Ӧ
	private void reply(String qq, String name, boolean agree) {
		Protocol protocol = new Protocol();
		String userInfo = protocol.getUserInfo(qq, name);
		if(agree) {
			//ͬ��ص�
			JOptionPane.showMessageDialog(mainView, userInfo + "ͬ������ĺ������룡", "agree", JOptionPane.INFORMATION_MESSAGE);
			mainView.setFriend(userInfo, "friend.png", true);
		}else {
			//�ܾ�
			JOptionPane.showMessageDialog(mainView, userInfo + "�ܾ�����ĺ������룡", "deny", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	//���û��˳�
	private void userLogout(String qq, String name) {
		Protocol protocol = new Protocol();
		String userInfo = protocol.getUserInfo(qq, name);
		//�ص�ҳ�棬�Ƴ���Ӧ���û���
		mainView.userLeave(userInfo);
	}
}
