package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import database.DBOperation;
import model.User;
import ui.MainView;

public class ChatBoxesServer {
	//�߳�����
	private static Hashtable<User, MyThread> clientThreads = new Hashtable<>();
	//δ����Ϣ����
	private static Hashtable<String, List<String>> unreadMsgs = new Hashtable<>();
	//��ȡ
	public static MyThread findClientThread(User user) {
		return clientThreads.get(user);
	}
	//���
	public static void addClientThread(User user, MyThread holder) {
		clientThreads.put(user, holder);
	}
	//��ȡ�����û���
	public static Set<User> getClientSet(){
		return clientThreads.keySet();
	}
	//�Ƴ�
	public static User removeClientThread(MyThread holder) {
		Iterator<User> iterator = clientThreads.keySet().iterator();
		User key = null;
		while (iterator.hasNext()) {
			key = (User) iterator.next();
			MyThread thread = clientThreads.get(key);
			if(holder.getName().equals(thread.getName())) {
				break;
			}else {
				key = null;
			}
		}
		if(null != key) {
			clientThreads.remove(key);
		}
		return key;
	}
	//���δ����Ϣ
	public static void addUnreadMsg(String userInfo, String msg) {
		List<String> msgs = unreadMsgs.get(userInfo);
		if(msgs == null) {
			msgs = new ArrayList<String>();
			msgs.add(msg);
			unreadMsgs.put(userInfo, msgs);
		}else {
			msgs.add(msg);
		}
	}
	//ȡ��δ����Ϣ
	public static List<String> getUnreadMsgs(String userInfo){
		List<String> msgs = unreadMsgs.get(userInfo);
		unreadMsgs.remove(userInfo);
		return msgs;
	}
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		MainView windows = new MainView();
		windows.setVisible(true);
		DBOperation.start();
		//�򿪼����������˿ڣ����տͻ�������
		ServerSocket server = null;
		try {
			System.out.println("����ϵͳ����...");
			server = new ServerSocket(50000);
			while(true) {
				Socket client = server.accept();
				System.out.println("�û�����...");
				//���������߳�
				new MyThread(client, windows).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
