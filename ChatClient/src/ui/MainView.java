package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import controller.ChatController;
import utils.Protocol;

@SuppressWarnings("serial")
public class MainView extends JFrame {
	
	private JList<ListItem> clientList;
	private DefaultListModel<ListItem> clients;
	private JTextArea textArea_message;
	private JTextField t_name;
	private JTextField t_message;
	private JTextField t_qq;
	//�û���(�û������޸�ǰ���û���)
	private String name="";
	private JButton btn_setName;
	//˽�Ĵ���
	private ChatView chat;
	
	public MainView(String qq, String username) {
		getContentPane().setBackground(Color.WHITE);
		setTitle("�����ҿͻ���");
		setBounds(500, 200, 620, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		
		clients = new DefaultListModel<>();
		clientList = new JList<ListItem>();
		
		clientList.setCellRenderer(new ListView<ListItem>());
		clientList.setModel(clients);
		clientList.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		
		JScrollPane scrollPane_list = new JScrollPane(clientList);
		
		JPopupMenu M_option = new JPopupMenu();
		M_option.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		addPopup(clientList, M_option);
		
		JMenuItem m_addFriend = new JMenuItem("\u6DFB\u52A0\u597D\u53CB");
		m_addFriend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(name == "") {
					JOptionPane.showMessageDialog(MainView.this, "���������û���������ʹ��Ŷ��");
					return;
				}
				if(!clientList.getSelectedValue().isFriend()) {
					//��ѡ�û��ǷǺ���
					String to = clientList.getSelectedValue().getText();
					//�����Ӻ���
					ChatController chatController = new ChatController();
					//������Ӻ��������������
					chatController.addFriend(qq, to, t_name.getText());
//					JOptionPane.showMessageDialog(MainView.this, "���ĺ��������ѷ����������ĵȴ�!", "��Ӻ���", JOptionPane.INFORMATION_MESSAGE);
				}else {
					//�Ѿ��Ǻ�����
					JOptionPane.showMessageDialog(MainView.this, "�����Ѿ��Ǻ�����!", "��Ӻ���", JOptionPane.INFORMATION_MESSAGE);
				}
				clientList.clearSelection();
			}
		});

		m_addFriend.setIcon(new ImageIcon(MainView.class.getResource("/recourses/add.png")));
		m_addFriend.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		M_option.add(m_addFriend);
		
		JMenuItem m_sendMessage = new JMenuItem("\u79C1\u4FE1");
		m_sendMessage.setIcon(new ImageIcon(MainView.class.getResource("/recourses/message.png")));
		m_sendMessage.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		m_sendMessage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(name == "") {
					JOptionPane.showMessageDialog(MainView.this, "���������û���������ʹ��Ŷ��");
					return;
				}
				if(chat == null || chat.isVisible() == false) {
					//���˽�ţ���˽�Ŵ���,��˽��Ŀ����Ϣ����ȥ
					String userInfo = clientList.getSelectedValue().getText();
					String from = t_name.getText();
					String qq = t_qq.getText();
					chat = new ChatView(from, qq, userInfo, getX(), getY());
					chat.setVisible(true);
				}
			}
		});
		
		JMenuItem m_deleteFriend = new JMenuItem("\u89E3\u9664\u597D\u53CB");
		m_deleteFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(name == "") {
					JOptionPane.showMessageDialog(MainView.this, "���������û���������ʹ��Ŷ��");
					return;
				}
				if(clientList.getSelectedValue().isFriend()) {
					//��ѡ�û��Ǻ���
					String to = clientList.getSelectedValue().getText();
					//�����Ӻ���
					ChatController chatController = new ChatController();
					//������Ӻ��������������
					chatController.deleteFriend(qq, to, t_name.getText());
					//�Ѻ���ͼ��Ļ���
					setFriend(to, "inline.png", false);
				}else {
					//���Ǻ���
					JOptionPane.showMessageDialog(MainView.this, "���ǻ����Ǻ���!", "������ѹ�ϵ", JOptionPane.INFORMATION_MESSAGE);
				}
				clientList.clearSelection();
			}
		});
		m_deleteFriend.setIcon(new ImageIcon(MainView.class.getResource("/recourses/delete.png")));
		m_deleteFriend.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		M_option.add(m_deleteFriend);
		M_option.add(m_sendMessage);
		
		JMenuItem m_cancel = new JMenuItem("\u53D6\u6D88\u9009\u62E9");
		m_cancel.setIcon(new ImageIcon(MainView.class.getResource("/recourses/cancel.png")));
		m_cancel.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		m_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//���ȡ��ѡ��
				clientList.clearSelection();
			}
		});
		
		JMenuItem m_checkMsg = new JMenuItem("\u67E5\u770B\u6D88\u606F");
		m_checkMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//����鿴��Ϣ
				ListItem item = clientList.getSelectedValue();
				if(item.getMsgs().size() > 0) {
					//��δ����Ϣ����δ����Ϣ����,������δ����Ϣ����ȥ
					String userInfo = clientList.getSelectedValue().getText();
					String from = t_name.getText();
					String qq = t_qq.getText();
					chat = new ChatView(from, qq, userInfo, getX(), getY());
					//����Ϣ����˽�Ĵ���
					Iterator<String> iterator = item.getMsgs().iterator();
					while (iterator.hasNext()) {
						String message = (String) iterator.next();
						chat.addMessage(userInfo + ": " + message);
					}
					chat.setVisible(true);
					//���δ����Ϣ
					item.recover();
					clientList.clearSelection();
				}else {
					JOptionPane.showMessageDialog(MainView.this, "��ʱû��δ����Ϣ!");
				}
				clientList.clearSelection();
			}
		});
		m_checkMsg.setIcon(new ImageIcon(MainView.class.getResource("/recourses/checkMsg.png")));
		m_checkMsg.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		M_option.add(m_checkMsg);
		M_option.add(m_cancel);
		
		scrollPane_list.setOpaque(false);
		scrollPane_list.setBorder(new TitledBorder("�û��б�"));
		scrollPane_list.setPreferredSize(new Dimension(200, 0));
		getContentPane().add(scrollPane_list, BorderLayout.WEST);
		
		textArea_message = new JTextArea();
		textArea_message.setBackground(Color.WHITE);
		textArea_message.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		textArea_message.setEditable(false);
		
		JScrollPane scrollPane_message = new JScrollPane(textArea_message);
		scrollPane_message.setOpaque(false);
		scrollPane_message.setBorder(new TitledBorder("��Ϣ��¼"));
		getContentPane().add(scrollPane_message, BorderLayout.CENTER);
		
		JPanel panel_top = new JPanel();
		panel_top.setOpaque(false);
		getContentPane().add(panel_top, BorderLayout.NORTH);
		panel_top.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_topLeft = new JPanel();
		panel_topLeft.setOpaque(false);
		panel_top.add(panel_topLeft, BorderLayout.WEST);
		panel_topLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblQq = new JLabel("qq:");
		lblQq.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		panel_topLeft.add(lblQq);
		t_qq = new JTextField();
		t_qq.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		t_qq.setText(qq);
		t_qq.setEditable(false);
		panel_topLeft.add(t_qq);
		
		JButton btn_logout = new JButton("\u6CE8\u9500");
		btn_logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//���ע����ť���˳���¼������ʾ�Ƿ�ȷ���˳�
				int option = JOptionPane.showConfirmDialog(MainView.this, "�Ƿ�ȷ���˳���", "Logout", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION) {
					//�رճ��򣬸��߷���ˣ��ͷ���Դ
					ChatController chatController = new ChatController();
					chatController.close();
					System.exit(0);
				}
			}
		});
		btn_logout.setFont(new Font("���Ŀ���", Font.PLAIN, 16));
		panel_topLeft.add(btn_logout);
		
		JPanel panel_topRight = new JPanel();
		panel_topRight.setOpaque(false);
		panel_top.add(panel_topRight, BorderLayout.CENTER);
		panel_topRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JLabel l_setUsername = new JLabel("\u8BBE\u7F6E\u7528\u6237\u540D\uFF1A");
		panel_topRight.add(l_setUsername);
		l_setUsername.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		
		t_name = new JTextField();
		panel_topRight.add(t_name);
		t_name.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		t_name.setColumns(12);
		
		btn_setName = new JButton("\u786E\u8BA4");
		btn_setName.addMouseListener(new MouseAdapter() {
			//�����û�������¼�
			@Override
			public void mouseClicked(MouseEvent e) {
				ChatController chatController = new ChatController();
				//��¼��ǰ���û���
				String newName = t_name.getText();		
				if(newName.length() > 0)
					if(btn_setName.getText().equals("ȷ��")) {
						if(name.length()==0) {
							//�û���һ�ε������һ�η����û�����������
							chatController.setName(t_qq.getText(), newName);
							//��¼���޸ĵ�����
							name = newName;
						}else if(name.equals(newName)) {
							JOptionPane.showMessageDialog(MainView.this, "δ���κ��޸ģ�");
							t_name.setText(name);
						}else {
							//�����޸�����ָ��
							chatController.changeName(t_qq.getText(), name, newName);
							//��¼���޸ĵ�����
							name = newName;
						}
						t_name.setEditable(false);
						btn_setName.setText("�޸�");
					}else {
						t_name.setText("");
						t_name.setEditable(true);
						btn_setName.setText("ȷ��");
					}
			}
		});
		panel_topRight.add(btn_setName);
		btn_setName.setFont(new Font("���Ŀ���", Font.PLAIN, 16));
		
		JPanel panel_bottom = new JPanel();
		panel_bottom.setOpaque(false);
		getContentPane().add(panel_bottom, BorderLayout.SOUTH);
		panel_bottom.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("\u952E\u5165\u6D88\u606F\uFF1A");
		lblNewLabel_2.setFont(new Font("���Ŀ���", Font.PLAIN, 20));
		panel_bottom.add(lblNewLabel_2, BorderLayout.WEST);
		
		JLabel l1 = new JLabel(" ");
		panel_bottom.add(l1, BorderLayout.SOUTH);
		
		t_message = new JTextField();
		t_message.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					//����Enter��������Ϣ
					ChatController chatController = new ChatController();
					//��ȡ�û��������Ϣ
					String message = t_message.getText();
					if(message.trim() != "" && message.trim().length() > 0) {
						//�ж��û��Ƿ������û���
						if(name != "") {
							chatController.sendToAllMessage(t_qq.getText(), name, message);
							//ȡ����һ��ѡ��
							clientList.clearSelection();
							//���Լ�����Ϣ��ʾ����Ϣ��¼��
							textArea_message.append(name + ": " + t_message.getText()+"\n");
							//��������
							t_message.setText("");
							textArea_message.setCaretPosition(textArea_message.getText().length());
						}else {
							//�û���δ��ʼ��(�����û���)
							JOptionPane.showMessageDialog(MainView.this, "Ҫ�������û������ܿ�ʼ����Ŷ!", "NoName", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		t_message.setFont(new Font("���Ŀ���", Font.PLAIN, 22));
		panel_bottom.add(t_message, BorderLayout.CENTER);
		t_message.setColumns(10);
		
		JButton btn_sendMsg = new JButton("\u53D1\u9001");
		btn_sendMsg.addMouseListener(new MouseAdapter() {
			//������Ϣ��ť����¼�
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ChatController chatController = new ChatController();
				//��ȡ�û��������Ϣ
				String message = t_message.getText();
				if(message.trim() != "" && message.trim().length() > 0) {
					//�ж��û��Ƿ������û���
					if(name != "") {
						chatController.sendToAllMessage(t_qq.getText(), name, message);
						//ȡ����һ��ѡ��
						clientList.clearSelection();
						//���Լ�����Ϣ��ʾ����Ϣ��¼��
						textArea_message.append(name + ": " + t_message.getText()+"\n");
						//��������
						t_message.setText("");
						textArea_message.setCaretPosition(textArea_message.getText().length());
					}else {
						//�û���δ��ʼ��(�����û���)
						JOptionPane.showMessageDialog(MainView.this, "Ҫ�������û������ܿ�ʼ����Ŷ!", "NoName", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		btn_sendMsg.setFont(new Font("���Ŀ���", Font.PLAIN, 22));
		panel_bottom.add(btn_sendMsg, BorderLayout.EAST);
		
		//����ر��¼�
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {
				//�رճ��򣬸��߷���ˣ��ͷ���Դ
				ChatController chatController = new ChatController();
				chatController.close();
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
		});
		
		if(username.equals("default")) {
			//˵�����û�Ϊ���û�
			t_name.setText("");
		}else {
			name = username;
			t_name.setText(username);
			btn_setName.setText("�޸�");
			t_name.setEditable(false);
		}
		ChatController chatController = new ChatController();
		//���ò�������Ϊ�Լ�
		chatController.setOperateWindows(this);
		//��ȡ�û��б����ѣ��Լ��������������û���
		chatController.findUsers(qq, username);
	}
	
	//��ʼ���û��б�
	public void initUserList(String userInfo, boolean isFriend) {
		//��������û���Ϣ
		ListItem newItem = new ListItem(userInfo, "inline.png");
		//�û��б����ϵ��û���Ϣ
		ListItem oldItem = isExistUser(userInfo);
		if(!isFriend) {
			if(oldItem != null) {
				//���ߺ�������
				clients.removeElement(oldItem);
				newItem.setIcon("friend.png");
				//���ú��ѱ�־λ
				newItem.setFriend(true);
			}
			clients.addElement(newItem);
		}else {
			//��������
			if(oldItem == null) {
				//ԭ��û�У�˵�������ߺ���
				newItem.setIcon("outlineFriend.png");
			}else {
				//ԭ����,���Ƴ������
				clients.removeElement(oldItem);
				newItem.setIcon("friend.png");
			}
			newItem.setFriend(true);
			clients.addElement(newItem);
		}
	}
	//�޸��û���
	public void changeName(String oldInfo, String newInfo) {
		Enumeration<ListItem> elements = clients.elements();
		//���������б�
		while (elements.hasMoreElements()) {
			ListItem listItem = (ListItem) elements.nextElement();
			if(listItem.getText().equals(oldInfo)) {
				listItem.setText(newInfo);
				repaint();
				break;
			}
		}
	}
	//�û��˳��޸�ҳ��
	public void userLeave(String userInfo) {
		Enumeration<ListItem> elements = clients.elements();
		//���������б�
		while (elements.hasMoreElements()) {
			ListItem listItem = (ListItem) elements.nextElement();
			if(listItem.getText().equals(userInfo)) {
				if(listItem.isFriend()) {
					//�������ߣ����Ƴ����޸ĳ�����ͼ��
					setFriend(userInfo, "outlineFriend.png", true);
					repaint();
				}else {
					//�Ǻ������ߣ��Ƴ�����
					clients.removeElement(listItem);
				}
				break;
			}
		}
	}
	//˽����Ϣ
	public void haveMessage(String userInfo, String message) {
		if(chat == null) {
			String from = "default";
			if(t_name.getText().length()>0)
				from = t_name.getText();
			String qq = t_qq.getText();
			chat = new ChatView(from, qq, userInfo, getX(), getY());
			chat.addMessage(userInfo + ": " + message);
			chat.setVisible(true);
		}else {
			//�����Ѿ�����
			if(chat.isVisible()) {
				//�жϴ򿪵Ĵ����뵱ǰ��Ϣ��Դ�Ƿ���ͬһ���û�
				if(userInfo.equals(chat.getCurrentUser())) {
					chat.addMessage(userInfo + ": " + message);
				}else {
					//����ͬһ���û�
					ListItem item = isExistUser(userInfo);
					//���ø��û�ͷ��Ϊ��δ����Ϣ
					item.havaMessage(message);
					repaint();
				}
			}else {
				//����û�д�
				chat = null;
				haveMessage(userInfo, message);
			}
		}
	}
	//���յ��û���Ϣ�ص�
	public void callback(String message) {
		//Ⱥ����Ϣ
		textArea_message.append(message+"\n");
		textArea_message.setCaretPosition(textArea_message.getText().length());
	}
	
	//�Ҽ�����ѡ��
	@SuppressWarnings("unchecked")
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			JList<String> list = (JList<String>) component;
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger() && !list.isSelectionEmpty()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger() && !list.isSelectionEmpty()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	//������Ӻ�������
	public void addFriend(String qs, String name) {
		ChatController chatController = new ChatController();
		Protocol protocol = new Protocol();
		//��������û���Ϣ
		String userInfo = protocol.getUserInfo(qs, name);
		boolean agree = false;
		int option = JOptionPane.showConfirmDialog(this, userInfo + " ���㷢���������룡", "��������", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			//ͬ��
			agree = true;
			//���û���ͼ����Ϊ����ͼ��
			setFriend(userInfo, "friend.png", true);
		}else {
			//��ͬ��
			agree = false;
		}
		//��
		chatController.addFriendReply(qs, t_qq.getText(), t_name.getText(), agree);
	}
	//���������ѹ�ϵ
	public void deleteFriend(String qs, String name) {
		Protocol protocol = new Protocol();
		//��������û���Ϣ
		String userInfo = protocol.getUserInfo(qs, name);
		JOptionPane.showMessageDialog(this, userInfo + " �������˺��ѹ�ϵ!", "������ѹ�ϵ", JOptionPane.INFORMATION_MESSAGE);
		//����ͼ��Ϊ��ͨ�����û�
		setFriend(userInfo, "inline.png", false);
	}
	
	//���ú���ͼ��
	public void setFriend(String userInfo, String icon, boolean isFriend) {
		//�ҵ��ú���
		Enumeration<ListItem> elements = clients.elements();
		while (elements.hasMoreElements()) {
			ListItem listItem = (ListItem) elements.nextElement();
			if(listItem.getText().equals(userInfo)) {
				listItem.setIcon(icon);
				listItem.setFriend(isFriend);
				repaint();
				break;
			}
		}
	}
	//�����б����Ƿ���ڸ��û�,������ھͷ�������û���
	public ListItem isExistUser(String userInfo) {
		//�����û��б�
		Enumeration<ListItem> elements = clients.elements();
		while (elements.hasMoreElements()) {
			ListItem listItem = (ListItem) elements.nextElement();
			if(listItem.getText().equals(userInfo)) {
				return listItem;
			}
		}
		return null;
	}
}
