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
	//���������ͻ���
	@Override
	public void run() {
		InputStreamReader in = null;
		BufferedReader br = null;
		Operation operation = new Operation(this, windows);
		
		try {
			in = new InputStreamReader(socket.getInputStream(), "UTF-8");
			br = new BufferedReader(in);
			//��������
			//����������ȡ�ͻ�����Ϣ
			while(true) {
				String message = br.readLine();
				if(null != message) {
					if(message.equals("byebye")) {
						//�û��˳�
						User user = operation.userLogout();
						if(user != null)
							System.out.println("�û�: " + user.getQq() + " �˳�������!");
						else
							System.out.println("�û��˳�...");
						//�����û��˿���ֹͣ������
						sendMessage("stop");
						break;
					}else {
						//������Ϣ����ִ�ж�Ӧ�Ĳ���
						operation.doOper(message);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			User user = operation.userLogout();
			if(user != null)
				System.out.println(user.getQq() + " �Ͽ�����!");
			else
				System.out.println("�û��˳�...");
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
	//������Ϣ����
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
