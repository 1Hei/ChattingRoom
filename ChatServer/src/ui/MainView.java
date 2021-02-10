package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class MainView extends JFrame{
	private JTextArea textArea_message;
	private JList<String> client_list;
	//用户列表
	private DefaultListModel<String> clientListModel;
	
	
	public MainView() {
		setBounds(500, 200, 550, 400);
		setTitle("聊天室服务端");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel_clientList = new JPanel();
		panel_clientList.setBackground(Color.WHITE);
		panel_clientList.setPreferredSize(null);
		getContentPane().add(panel_clientList, BorderLayout.WEST);
		panel_clientList.setLayout(new BorderLayout(0, 0));
		
		client_list = new JList<String>();
		client_list.setBorder(new TitledBorder("用户列表"));
		client_list.setFont(new Font("华文楷体", Font.PLAIN, 18));
		JScrollPane scrollPane_list = new JScrollPane(client_list);
		scrollPane_list.setPreferredSize(new Dimension(200, 0));
		scrollPane_list.setOpaque(false);
		panel_clientList.add(scrollPane_list, BorderLayout.CENTER);
		
		//初始化用户列表
		clientListModel = new DefaultListModel<>();
		client_list.setModel(clientListModel);
		
		JPanel panel_message = new JPanel();
		panel_message.setBackground(Color.WHITE);
		getContentPane().add(panel_message, BorderLayout.CENTER);
		panel_message.setLayout(new BorderLayout(0, 0));
		
		//文本区域
		textArea_message = new JTextArea();
		textArea_message.setFont(new Font("华文楷体", Font.PLAIN, 20));
		textArea_message.setEditable(false);
		textArea_message.setBorder(new TitledBorder("消息记录"));
		
		JScrollPane scrollPane = new JScrollPane(textArea_message);
		scrollPane.setOpaque(false);
		panel_message.add(scrollPane);
	}
	//带用户名回调,更新用户列表
	//通过oldName和newName的值是否为空来更新用户列表
	//oldName为空另一个不为空那么说明是来新增newName的
	//newName为空另一个不为空那么说明是来一处oldName的
	//都不为空，那么是来修改用户名的
	//不存在都为空的情况
	public void callback(String oldName, String newName) {
		if(null != oldName) 
			clientListModel.removeElement(oldName);
		if(null != newName)
			clientListModel.addElement(newName);
	}
	//接收到用户消息回调
	public void callback(String message) {
		textArea_message.append(message+"\n");
		//设消息记录的焦点永远在最后一行
		textArea_message.setCaretPosition(textArea_message.getText().length());
	}
}
