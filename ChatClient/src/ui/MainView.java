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
	//用户名(用户保存修改前的用户名)
	private String name="";
	private JButton btn_setName;
	//私聊窗口
	private ChatView chat;
	
	public MainView(String qq, String username) {
		getContentPane().setBackground(Color.WHITE);
		setTitle("聊天室客户端");
		setBounds(500, 200, 620, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		
		clients = new DefaultListModel<>();
		clientList = new JList<ListItem>();
		
		clientList.setCellRenderer(new ListView<ListItem>());
		clientList.setModel(clients);
		clientList.setFont(new Font("华文楷体", Font.PLAIN, 18));
		
		JScrollPane scrollPane_list = new JScrollPane(clientList);
		
		JPopupMenu M_option = new JPopupMenu();
		M_option.setFont(new Font("华文楷体", Font.PLAIN, 18));
		addPopup(clientList, M_option);
		
		JMenuItem m_addFriend = new JMenuItem("\u6DFB\u52A0\u597D\u53CB");
		m_addFriend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(name == "") {
					JOptionPane.showMessageDialog(MainView.this, "请先设置用户名，才能使用哦！");
					return;
				}
				if(!clientList.getSelectedValue().isFriend()) {
					//所选用户是非好友
					String to = clientList.getSelectedValue().getText();
					//点击添加好友
					ChatController chatController = new ChatController();
					//发送添加好友申请给服务器
					chatController.addFriend(qq, to, t_name.getText());
//					JOptionPane.showMessageDialog(MainView.this, "您的好友申请已发出，请耐心等待!", "添加好友", JOptionPane.INFORMATION_MESSAGE);
				}else {
					//已经是好友了
					JOptionPane.showMessageDialog(MainView.this, "你们已经是好友了!", "添加好友", JOptionPane.INFORMATION_MESSAGE);
				}
				clientList.clearSelection();
			}
		});

		m_addFriend.setIcon(new ImageIcon(MainView.class.getResource("/recourses/add.png")));
		m_addFriend.setFont(new Font("华文楷体", Font.PLAIN, 18));
		M_option.add(m_addFriend);
		
		JMenuItem m_sendMessage = new JMenuItem("\u79C1\u4FE1");
		m_sendMessage.setIcon(new ImageIcon(MainView.class.getResource("/recourses/message.png")));
		m_sendMessage.setFont(new Font("华文楷体", Font.PLAIN, 18));
		m_sendMessage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(name == "") {
					JOptionPane.showMessageDialog(MainView.this, "请先设置用户名，才能使用哦！");
					return;
				}
				if(chat == null || chat.isVisible() == false) {
					//点击私信，打开私信窗口,把私信目标信息传过去
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
					JOptionPane.showMessageDialog(MainView.this, "请先设置用户名，才能使用哦！");
					return;
				}
				if(clientList.getSelectedValue().isFriend()) {
					//所选用户是好友
					String to = clientList.getSelectedValue().getText();
					//点击添加好友
					ChatController chatController = new ChatController();
					//发送添加好友申请给服务器
					chatController.deleteFriend(qq, to, t_name.getText());
					//把好友图标改回来
					setFriend(to, "inline.png", false);
				}else {
					//不是好友
					JOptionPane.showMessageDialog(MainView.this, "你们还不是好友!", "解除好友关系", JOptionPane.INFORMATION_MESSAGE);
				}
				clientList.clearSelection();
			}
		});
		m_deleteFriend.setIcon(new ImageIcon(MainView.class.getResource("/recourses/delete.png")));
		m_deleteFriend.setFont(new Font("华文楷体", Font.PLAIN, 18));
		M_option.add(m_deleteFriend);
		M_option.add(m_sendMessage);
		
		JMenuItem m_cancel = new JMenuItem("\u53D6\u6D88\u9009\u62E9");
		m_cancel.setIcon(new ImageIcon(MainView.class.getResource("/recourses/cancel.png")));
		m_cancel.setFont(new Font("华文楷体", Font.PLAIN, 18));
		m_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//点击取消选择
				clientList.clearSelection();
			}
		});
		
		JMenuItem m_checkMsg = new JMenuItem("\u67E5\u770B\u6D88\u606F");
		m_checkMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//点击查看消息
				ListItem item = clientList.getSelectedValue();
				if(item.getMsgs().size() > 0) {
					//有未读消息，打开未读消息窗口,把所有未读消息传过去
					String userInfo = clientList.getSelectedValue().getText();
					String from = t_name.getText();
					String qq = t_qq.getText();
					chat = new ChatView(from, qq, userInfo, getX(), getY());
					//把消息传给私聊窗口
					Iterator<String> iterator = item.getMsgs().iterator();
					while (iterator.hasNext()) {
						String message = (String) iterator.next();
						chat.addMessage(userInfo + ": " + message);
					}
					chat.setVisible(true);
					//清空未读消息
					item.recover();
					clientList.clearSelection();
				}else {
					JOptionPane.showMessageDialog(MainView.this, "暂时没有未读消息!");
				}
				clientList.clearSelection();
			}
		});
		m_checkMsg.setIcon(new ImageIcon(MainView.class.getResource("/recourses/checkMsg.png")));
		m_checkMsg.setFont(new Font("华文楷体", Font.PLAIN, 18));
		M_option.add(m_checkMsg);
		M_option.add(m_cancel);
		
		scrollPane_list.setOpaque(false);
		scrollPane_list.setBorder(new TitledBorder("用户列表"));
		scrollPane_list.setPreferredSize(new Dimension(200, 0));
		getContentPane().add(scrollPane_list, BorderLayout.WEST);
		
		textArea_message = new JTextArea();
		textArea_message.setBackground(Color.WHITE);
		textArea_message.setFont(new Font("华文楷体", Font.PLAIN, 18));
		textArea_message.setEditable(false);
		
		JScrollPane scrollPane_message = new JScrollPane(textArea_message);
		scrollPane_message.setOpaque(false);
		scrollPane_message.setBorder(new TitledBorder("消息记录"));
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
		lblQq.setFont(new Font("华文楷体", Font.PLAIN, 18));
		panel_topLeft.add(lblQq);
		t_qq = new JTextField();
		t_qq.setFont(new Font("华文楷体", Font.PLAIN, 18));
		t_qq.setText(qq);
		t_qq.setEditable(false);
		panel_topLeft.add(t_qq);
		
		JButton btn_logout = new JButton("\u6CE8\u9500");
		btn_logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//点击注销按钮，退出登录，先提示是否确定退出
				int option = JOptionPane.showConfirmDialog(MainView.this, "是否确认退出？", "Logout", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION) {
					//关闭程序，告诉服务端，释放资源
					ChatController chatController = new ChatController();
					chatController.close();
					System.exit(0);
				}
			}
		});
		btn_logout.setFont(new Font("华文楷体", Font.PLAIN, 16));
		panel_topLeft.add(btn_logout);
		
		JPanel panel_topRight = new JPanel();
		panel_topRight.setOpaque(false);
		panel_top.add(panel_topRight, BorderLayout.CENTER);
		panel_topRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JLabel l_setUsername = new JLabel("\u8BBE\u7F6E\u7528\u6237\u540D\uFF1A");
		panel_topRight.add(l_setUsername);
		l_setUsername.setFont(new Font("华文楷体", Font.PLAIN, 18));
		
		t_name = new JTextField();
		panel_topRight.add(t_name);
		t_name.setFont(new Font("华文楷体", Font.PLAIN, 18));
		t_name.setColumns(12);
		
		btn_setName = new JButton("\u786E\u8BA4");
		btn_setName.addMouseListener(new MouseAdapter() {
			//设置用户名点击事件
			@Override
			public void mouseClicked(MouseEvent e) {
				ChatController chatController = new ChatController();
				//记录当前的用户名
				String newName = t_name.getText();		
				if(newName.length() > 0)
					if(btn_setName.getText().equals("确认")) {
						if(name.length()==0) {
							//用户第一次点击，第一次发送用户名给服务器
							chatController.setName(t_qq.getText(), newName);
							//记录下修改的名字
							name = newName;
						}else if(name.equals(newName)) {
							JOptionPane.showMessageDialog(MainView.this, "未作任何修改！");
							t_name.setText(name);
						}else {
							//发送修改姓名指令
							chatController.changeName(t_qq.getText(), name, newName);
							//记录下修改的名字
							name = newName;
						}
						t_name.setEditable(false);
						btn_setName.setText("修改");
					}else {
						t_name.setText("");
						t_name.setEditable(true);
						btn_setName.setText("确认");
					}
			}
		});
		panel_topRight.add(btn_setName);
		btn_setName.setFont(new Font("华文楷体", Font.PLAIN, 16));
		
		JPanel panel_bottom = new JPanel();
		panel_bottom.setOpaque(false);
		getContentPane().add(panel_bottom, BorderLayout.SOUTH);
		panel_bottom.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("\u952E\u5165\u6D88\u606F\uFF1A");
		lblNewLabel_2.setFont(new Font("华文楷体", Font.PLAIN, 20));
		panel_bottom.add(lblNewLabel_2, BorderLayout.WEST);
		
		JLabel l1 = new JLabel(" ");
		panel_bottom.add(l1, BorderLayout.SOUTH);
		
		t_message = new JTextField();
		t_message.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					//按下Enter键发送消息
					ChatController chatController = new ChatController();
					//获取用户键入的消息
					String message = t_message.getText();
					if(message.trim() != "" && message.trim().length() > 0) {
						//判断用户是否设置用户名
						if(name != "") {
							chatController.sendToAllMessage(t_qq.getText(), name, message);
							//取消上一级选择
							clientList.clearSelection();
							//把自己的消息显示到消息记录里
							textArea_message.append(name + ": " + t_message.getText()+"\n");
							//清空输入框
							t_message.setText("");
							textArea_message.setCaretPosition(textArea_message.getText().length());
						}else {
							//用户还未初始化(设置用户名)
							JOptionPane.showMessageDialog(MainView.this, "要先设置用户名才能开始聊天哦!", "NoName", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		t_message.setFont(new Font("华文楷体", Font.PLAIN, 22));
		panel_bottom.add(t_message, BorderLayout.CENTER);
		t_message.setColumns(10);
		
		JButton btn_sendMsg = new JButton("\u53D1\u9001");
		btn_sendMsg.addMouseListener(new MouseAdapter() {
			//发送消息按钮点击事件
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ChatController chatController = new ChatController();
				//获取用户键入的消息
				String message = t_message.getText();
				if(message.trim() != "" && message.trim().length() > 0) {
					//判断用户是否设置用户名
					if(name != "") {
						chatController.sendToAllMessage(t_qq.getText(), name, message);
						//取消上一级选择
						clientList.clearSelection();
						//把自己的消息显示到消息记录里
						textArea_message.append(name + ": " + t_message.getText()+"\n");
						//清空输入框
						t_message.setText("");
						textArea_message.setCaretPosition(textArea_message.getText().length());
					}else {
						//用户还未初始化(设置用户名)
						JOptionPane.showMessageDialog(MainView.this, "要先设置用户名才能开始聊天哦!", "NoName", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		btn_sendMsg.setFont(new Font("华文楷体", Font.PLAIN, 22));
		panel_bottom.add(btn_sendMsg, BorderLayout.EAST);
		
		//窗体关闭事件
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
				//关闭程序，告诉服务端，释放资源
				ChatController chatController = new ChatController();
				chatController.close();
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
		});
		
		if(username.equals("default")) {
			//说明该用户为新用户
			t_name.setText("");
		}else {
			name = username;
			t_name.setText(username);
			btn_setName.setText("修改");
			t_name.setEditable(false);
		}
		ChatController chatController = new ChatController();
		//设置操作窗口为自己
		chatController.setOperateWindows(this);
		//获取用户列表（好友，以及其他所有在线用户）
		chatController.findUsers(qq, username);
	}
	
	//初始化用户列表
	public void initUserList(String userInfo, boolean isFriend) {
		//待插入的用户信息
		ListItem newItem = new ListItem(userInfo, "inline.png");
		//用户列表中老的用户信息
		ListItem oldItem = isExistUser(userInfo);
		if(!isFriend) {
			if(oldItem != null) {
				//离线好友上线
				clients.removeElement(oldItem);
				newItem.setIcon("friend.png");
				//设置好友标志位
				newItem.setFriend(true);
			}
			clients.addElement(newItem);
		}else {
			//好友上线
			if(oldItem == null) {
				//原本没有，说明是离线好友
				newItem.setIcon("outlineFriend.png");
			}else {
				//原来有,先移除再添加
				clients.removeElement(oldItem);
				newItem.setIcon("friend.png");
			}
			newItem.setFriend(true);
			clients.addElement(newItem);
		}
	}
	//修改用户名
	public void changeName(String oldInfo, String newInfo) {
		Enumeration<ListItem> elements = clients.elements();
		//遍历好友列表
		while (elements.hasMoreElements()) {
			ListItem listItem = (ListItem) elements.nextElement();
			if(listItem.getText().equals(oldInfo)) {
				listItem.setText(newInfo);
				repaint();
				break;
			}
		}
	}
	//用户退出修改页面
	public void userLeave(String userInfo) {
		Enumeration<ListItem> elements = clients.elements();
		//遍历好友列表
		while (elements.hasMoreElements()) {
			ListItem listItem = (ListItem) elements.nextElement();
			if(listItem.getText().equals(userInfo)) {
				if(listItem.isFriend()) {
					//好友下线，不移除，修改成下线图标
					setFriend(userInfo, "outlineFriend.png", true);
					repaint();
				}else {
					//非好友下线，移除该项
					clients.removeElement(listItem);
				}
				break;
			}
		}
	}
	//私发消息
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
			//窗口已经打开了
			if(chat.isVisible()) {
				//判断打开的窗口与当前消息来源是否是同一个用户
				if(userInfo.equals(chat.getCurrentUser())) {
					chat.addMessage(userInfo + ": " + message);
				}else {
					//不是同一个用户
					ListItem item = isExistUser(userInfo);
					//设置该用户头像为有未读消息
					item.havaMessage(message);
					repaint();
				}
			}else {
				//窗口没有打开
				chat = null;
				haveMessage(userInfo, message);
			}
		}
	}
	//接收到用户消息回调
	public void callback(String message) {
		//群聊消息
		textArea_message.append(message+"\n");
		textArea_message.setCaretPosition(textArea_message.getText().length());
	}
	
	//右键弹出选项
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

	//处理添加好友申请
	public void addFriend(String qs, String name) {
		ChatController chatController = new ChatController();
		Protocol protocol = new Protocol();
		//打包申请用户信息
		String userInfo = protocol.getUserInfo(qs, name);
		boolean agree = false;
		int option = JOptionPane.showConfirmDialog(this, userInfo + " 向你发来好友申请！", "好友申请", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			//同意
			agree = true;
			//将用户的图标设为好友图标
			setFriend(userInfo, "friend.png", true);
		}else {
			//不同意
			agree = false;
		}
		//答复
		chatController.addFriendReply(qs, t_qq.getText(), t_name.getText(), agree);
	}
	//处理解除好友关系
	public void deleteFriend(String qs, String name) {
		Protocol protocol = new Protocol();
		//打包申请用户信息
		String userInfo = protocol.getUserInfo(qs, name);
		JOptionPane.showMessageDialog(this, userInfo + " 与你解除了好友关系!", "解除好友关系", JOptionPane.INFORMATION_MESSAGE);
		//设置图标为普通在线用户
		setFriend(userInfo, "inline.png", false);
	}
	
	//设置好友图标
	public void setFriend(String userInfo, String icon, boolean isFriend) {
		//找到该好友
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
	//查找列表中是否存在该用户,如果存在就返回这个用户项
	public ListItem isExistUser(String userInfo) {
		//遍历用户列表
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
