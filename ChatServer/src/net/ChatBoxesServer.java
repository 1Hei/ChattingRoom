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
	//线程容器
	private static Hashtable<User, MyThread> clientThreads = new Hashtable<>();
	//未读消息容器
	private static Hashtable<String, List<String>> unreadMsgs = new Hashtable<>();
	//获取
	public static MyThread findClientThread(User user) {
		return clientThreads.get(user);
	}
	//添加
	public static void addClientThread(User user, MyThread holder) {
		clientThreads.put(user, holder);
	}
	//获取所有用户名
	public static Set<User> getClientSet(){
		return clientThreads.keySet();
	}
	//移除
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
	//添加未读消息
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
	//取出未读消息
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
		//打开监听服务器端口，接收客户端连接
		ServerSocket server = null;
		try {
			System.out.println("聊天系统启动...");
			server = new ServerSocket(50000);
			while(true) {
				Socket client = server.accept();
				System.out.println("用户连接...");
				//启动监听线程
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
