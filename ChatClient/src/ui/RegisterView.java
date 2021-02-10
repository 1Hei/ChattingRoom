package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.ChatController;

@SuppressWarnings("serial")
public class RegisterView extends JFrame {
	private JTextField t_qq;
	private JPasswordField t_password;
	private JLabel l_warn;
	private JPasswordField t_confirm;
	
	public RegisterView() {
		getContentPane().setBackground(Color.WHITE);
		setTitle("�û�ע��");
		setBounds(800, 300, 400, 300);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		
		//���ô��ڹر��¼������ڹر����´򿪵�¼����
		this.addWindowListener(new WindowListener() {
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
				RegisterView.this.dispose();
				LoginView loginView = new LoginView();
				//�л���������
				loginView.setVisible(true);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
		});	
		
		JLabel l_qq = new JLabel("qq:");
		l_qq.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		l_qq.setBounds(76, 49, 76, 18);
		getContentPane().add(l_qq);
		
		t_qq = new JTextField();
		t_qq.setEditable(false);
		t_qq.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		t_qq.setBounds(166, 46, 163, 24);
		getContentPane().add(t_qq);
		t_qq.setColumns(10);
		
		JLabel label = new JLabel("\u5BC6\u7801\uFF1A");
		label.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		label.setBounds(76, 100, 76, 18);
		getContentPane().add(label);
		
		JLabel l_confirm = new JLabel("\u786E\u8BA4\u5BC6\u7801:");
		l_confirm.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		l_confirm.setBounds(76, 150, 76, 18);
		getContentPane().add(l_confirm);
		
		
		t_password = new JPasswordField();
		t_password.setEchoChar('*');
		t_password.setFont(new Font("Consolas", Font.PLAIN, 18));
		t_password.setBounds(166, 97, 163, 24);
		t_password.setColumns(1);
		getContentPane().add(t_password);
		
		t_confirm = new JPasswordField();
		t_confirm.setEchoChar('*');
		t_confirm.setFont(new Font("Consolas", Font.PLAIN, 18));
		t_confirm.setBounds(166, 148, 163, 24);
		getContentPane().add(t_confirm);
		t_confirm.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				//ʧȥ�����ж����������Ƿ���ͬ
				if(t_password.getPassword().length >0 && t_confirm.getPassword().length >0) {
					String password = new String(t_password.getPassword());
					String confirm = new String(t_confirm.getPassword());
					if(!confirm.equals(password)) {
						l_warn.setVisible(true);
					}
				}
			}
			public void focusGained(FocusEvent arg0) {
				//��ý���
				if(l_warn.isVisible()) {
					l_warn.setVisible(false);
					t_confirm.setText("");
				}
			}
		});
		
		JButton btn_register = new JButton("\u786E\u8BA4");
		btn_register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//���ȷ��ע��ɹ�
				if(t_password.getPassword().length >0 && t_confirm.getPassword().length >0) {
					//���������룬����ע��������������������������û�ע����Ϣ
					ChatController chatController = new ChatController();
					String qq = t_qq.getText();
					String password = new String(t_password.getPassword());
					chatController.userRegister(qq, password);
					RegisterView.this.dispose();
					LoginView loginView = new LoginView();
					loginView.setVisible(true);
				}
			}
		});
		btn_register.setBounds(76, 192, 253, 27);
		getContentPane().add(btn_register);
		
		l_warn = new JLabel("\u4E24\u6B21\u8F93\u5165\u4E0D\u76F8\u540C\uFF01\uFF01\uFF01");
		l_warn.setVisible(false);
		l_warn.setForeground(Color.RED);
		l_warn.setBounds(166, 128, 163, 18);
		getContentPane().add(l_warn);
		
		//�����������һ��QQ��
		ChatController chatController = new ChatController();
		//���ò�������Ϊ�Լ�
		chatController.setOperateWindows(this);
		chatController.getQQ();
	}
	//�õ�����˷��ص�qq
	public void setQQ(String qq) {
		if(null != qq)
			t_qq.setText(qq);
	}
	
}
