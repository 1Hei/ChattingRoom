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
		setTitle("˽�Ĵ���");
		setBounds(x, y, 400, 300);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		setModal(true);
		
		this.userInfo = userInfo;
		JLabel l_info = new JLabel();
		l_info.setText("�� "+userInfo + " ������...");
		l_info.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		getContentPane().add(l_info, BorderLayout.NORTH);
		
		t_msgArea = new JTextArea();
		t_msgArea.setFont(new Font("���Ŀ���", Font.PLAIN, 16));
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
		label.setFont(new Font("���Ŀ���", Font.PLAIN, 16));
		panel.add(label, BorderLayout.WEST);
		
		t_msg = new JTextField();
		t_msg.setFont(new Font("���Ŀ���", Font.PLAIN, 16));
		panel.add(t_msg, BorderLayout.CENTER);
		t_msg.setColumns(10);
		
		JButton btn_send = new JButton("\u53D1\u9001");
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//���������Ϣ
				String msg = t_msg.getText();
				if(msg.length() > 0) {
					ChatController chatController = new ChatController();
					//����˽����Ϣ��ȥ
					chatController.sendToOneMessage(qq, from, userInfo, msg);
					addMessage(from + ": "+msg);
				}
			}
		});
		btn_send.setFont(new Font("���Ŀ���", Font.PLAIN, 16));
		panel.add(btn_send, BorderLayout.EAST);
	}
	
	public void addMessage(String msg) {
		//��ʾ��Ϣ
		t_msgArea.append(msg +"\n");
		t_msgArea.setCaretPosition(t_msgArea.getText().length());
		//���������е���Ϣ
		t_msg.setText("");
	}
	//��ȡ����ǰ��˽���û�
	public String getCurrentUser() {
		return this.userInfo;
	}

}
