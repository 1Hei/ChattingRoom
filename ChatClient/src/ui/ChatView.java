package ui;


import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ChatController;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ChatView extends JDialog {
	private JTextArea t_msgArea;
	private JTextField t_msg;
	private String userInfo;

	public ChatView(String from, String qq, String userInfo, int x, int y) {
		getContentPane().setBackground(Color.WHITE);
		setTitle("私聊窗口");
		setBounds(x, y, 400, 300);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		setModal(true);
		
		this.userInfo = userInfo;
		JLabel l_info = new JLabel();
		l_info.setText("与 "+userInfo + " 聊天中...");
		l_info.setFont(new Font("华文楷体", Font.PLAIN, 18));
		getContentPane().add(l_info, BorderLayout.NORTH);
		
		t_msgArea = new JTextArea();
		t_msgArea.setFont(new Font("华文楷体", Font.PLAIN, 16));
		t_msgArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(t_msgArea);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setOpaque(false);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel label = new JLabel("\u952E\u5165\u6D88\u606F\uFF1A");
		label.setFont(new Font("华文楷体", Font.PLAIN, 16));
		panel.add(label, BorderLayout.WEST);
		
		t_msg = new JTextField();
		t_msg.setFont(new Font("华文楷体", Font.PLAIN, 16));
		panel.add(t_msg, BorderLayout.CENTER);
		t_msg.setColumns(10);
		
		JButton btn_send = new JButton("\u53D1\u9001");
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//点击发送消息
				String msg = t_msg.getText();
				if(msg.length() > 0) {
					ChatController chatController = new ChatController();
					//发送私发消息出去
					chatController.sendToOneMessage(qq, from, userInfo, msg);
					addMessage(from + ": "+msg);
				}
			}
		});
		btn_send.setFont(new Font("华文楷体", Font.PLAIN, 16));
		panel.add(btn_send, BorderLayout.EAST);
	}
	
	public void addMessage(String msg) {
		//显示消息
		t_msgArea.append(msg +"\n");
		t_msgArea.setCaretPosition(t_msgArea.getText().length());
		//清除输入框中的消息
		t_msg.setText("");
	}
	//获取到当前的私聊用户
	public String getCurrentUser() {
		return this.userInfo;
	}

}
