package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import controller.Operation;
import model.User;
import ui.MainView;

public class MyThread extends Thread {
	private Socket socket;
	private MainView windows;
	
	public MyThread(Socket socket, MainView windows) {
		this.socket = socket;
		this.windows = windows;
	}
	//持续监听客户端
	@Override
	public void run() {
		InputStreamReader in = null;
		BufferedReader br = null;
		Operation operation = new Operation(this, windows);
		
		try {
			in = new InputStreamReader(socket.getInputStream(), "UTF-8");
			br = new BufferedReader(in);
			//操作对象
			//自旋持续读取客户端消息
			while(true) {
				String message = br.readLine();
				if(null != message) {
					if(message.equals("byebye")) {
						//用户退出
						User user = operation.userLogout();
						if(user != null)
							System.out.println("用户: " + user.getQq() + " 退出聊天室!");
						else
							System.out.println("用户退出...");
						//告诉用户端可以停止监听了
						sendMessage("stop");
						break;
					}else {
						//根据消息内容执行对应的操作
						operation.doOper(message);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			User user = operation.userLogout();
			if(user != null)
				System.out.println(user.getQq() + " 断开连接!");
			else
				System.out.println("用户退出...");
		}finally {
			try {
				socket.close();
				in.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	//发送消息方法
	public void sendMessage(String message) {
		OutputStreamWriter out = null;
		PrintWriter pw = null;
		try {
			out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			pw = new PrintWriter(out, true);
			pw.println(message);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
