package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class Client {
	private static Socket socket;
	private static OutputStreamWriter out;
	private static InputStreamReader in;
	private static BufferedReader br;
	private static PrintWriter pw;
	//消息接收线程
	private static MainThread receiver;
	
	static {
		try {
			socket = new Socket("127.0.0.1", 50000);
			out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			in = new InputStreamReader(socket.getInputStream(), "UTF-8");
			br = new BufferedReader(in);
			pw = new PrintWriter(out, true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("服务器关闭，连接失败!");
		}
	}
	//启动读取线程
	public static void startReceiver(JFrame windows) {
		if(receiver == null) {
			receiver = new MainThread(br, windows);
			receiver.start();
		}
	}
	public static void setOperateWindows(JFrame windows) {
		receiver.setWindows(windows);
	}
	
	public static void sendMessage(String message) {
		pw.println(message);
	}

	public static void releaseResources() {
		System.out.println("See you!");
		try {
			if(null != socket && !socket.isClosed()) {
				socket.close();
				out.close();
				in.close();
				br.close();
				pw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}