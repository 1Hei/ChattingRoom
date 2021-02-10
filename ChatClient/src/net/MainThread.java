package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;

import controller.Operation;

public class MainThread extends Thread {
	private BufferedReader br;
	private Operation operation;
	
	public MainThread(BufferedReader br, JFrame windows) {
		this.br = br;
		operation = new Operation(windows);
	}
	public void setWindows(JFrame windows) {
		operation.updateWindows(windows);
	}
	@Override
	public void run() {
		try {
			//持续接收服务器消息
			while(true) {
				String line = br.readLine();
//				System.out.println(line);
				if(line == null || line.equals("stop")) break;
				
				if(line != null) {
					operation.doOper(line);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("读取服务器数据失败...");
		}
	}
}
