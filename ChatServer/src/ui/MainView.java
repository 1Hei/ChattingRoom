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
	//�û��б�
	private DefaultListModel<String> clientListModel;
	
	
	public MainView() {
		setBounds(500, 200, 550, 400);
		setTitle("�����ҷ����");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel_clientList = new JPanel();
		panel_clientList.setBackground(Color.WHITE);
		panel_clientList.setPreferredSize(null);
		getContentPane().add(panel_clientList, BorderLayout.WEST);
		panel_clientList.setLayout(new BorderLayout(0, 0));
		
		client_list = new JList<String>();
		client_list.setBorder(new TitledBorder("�û��б�"));
		client_list.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		JScrollPane scrollPane_list = new JScrollPane(client_list);
		scrollPane_list.setPreferredSize(new Dimension(200, 0));
		scrollPane_list.setOpaque(false);
		panel_clientList.add(scrollPane_list, BorderLayout.CENTER);
		
		//��ʼ���û��б�
		clientListModel = new DefaultListModel<>();
		client_list.setModel(clientListModel);
		
		JPanel panel_message = new JPanel();
		panel_message.setBackground(Color.WHITE);
		getContentPane().add(panel_message, BorderLayout.CENTER);
		panel_message.setLayout(new BorderLayout(0, 0));
		
		//�ı�����
		textArea_message = new JTextArea();
		textArea_message.setFont(new Font("���Ŀ���", Font.PLAIN, 20));
		textArea_message.setEditable(false);
		textArea_message.setBorder(new TitledBorder("��Ϣ��¼"));
		
		JScrollPane scrollPane = new JScrollPane(textArea_message);
		scrollPane.setOpaque(false);
		panel_message.add(scrollPane);
	}
	//���û����ص�,�����û��б�
	//ͨ��oldName��newName��ֵ�Ƿ�Ϊ���������û��б�
	//oldNameΪ����һ����Ϊ����ô˵����������newName��
	//newNameΪ����һ����Ϊ����ô˵������һ��oldName��
	//����Ϊ�գ���ô�����޸��û�����
	//�����ڶ�Ϊ�յ����
	public void callback(String oldName, String newName) {
		if(null != oldName) 
			clientListModel.removeElement(oldName);
		if(null != newName)
			clientListModel.addElement(newName);
	}
	//���յ��û���Ϣ�ص�
	public void callback(String message) {
		textArea_message.append(message+"\n");
		//����Ϣ��¼�Ľ�����Զ�����һ��
		textArea_message.setCaretPosition(textArea_message.getText().length());
	}
}
