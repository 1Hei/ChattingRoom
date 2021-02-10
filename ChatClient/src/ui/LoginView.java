package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.luna.LunaLookAndFeel;
import controller.ChatController;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

@SuppressWarnings("serial")
public class LoginView extends JFrame {
	private JTextField t_qq;
	private JPasswordField t_password;
	private JLabel l_qqError;
	private JLabel l_passwordError;
	
	public LoginView() {
		setTitle("�û���¼");
		setBounds(800, 300, 400, 300);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
		JPanel panel_Bg = new JPanel() {
			protected void paintComponent(Graphics g) {
				Image image = null;
				try {
					image = ImageIO.read(LoginView.class.getResource("/recourses/bg.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(image, 0, 0, LoginView.this.getWidth(), LoginView.this.getHeight(), this);
			};
		};
		getContentPane().add(panel_Bg);
		panel_Bg.setLayout(null);
		
		t_qq = new JTextField();
		t_qq.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				//ʧȥ������֤qq�Ƿ����
				String qq = t_qq.getText();
				if(qq.trim().length() > 0) {
					ChatController chatController = new ChatController();
					//�����ж�������Ϣ��������
					chatController.checkQQIsExist(qq);
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				//������ʱ��
				if(l_qqError.isVisible()) {
					t_qq.setText("");
					l_qqError.setVisible(false);
				}
			}
		});
		t_qq.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		t_qq.setBounds(117, 65, 160, 30);
		panel_Bg.add(t_qq);
		t_qq.setColumns(10);
		
		t_password = new JPasswordField();
		t_password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(l_passwordError.isVisible()) {
					l_passwordError.setVisible(false);
					t_password.setText("");
				}
			}
		});
		t_password.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		t_password.setBounds(117, 123, 160, 30);
		panel_Bg.add(t_password);
		
		JLabel l_forgetPassword = new JLabel("\u627E\u56DE\u5BC6\u7801");
		l_forgetPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//����һ�����
				String qq = t_qq.getText();
				if(qq.trim().length() > 0 && !l_qqError.isVisible()) {
					ChatController chatController = new ChatController();
					//�����һ���������
					chatController.findPassword(qq);
				}
			}
		});
		l_forgetPassword.setForeground(Color.BLUE);
		l_forgetPassword.setBounds(280, 129, 87, 18);
		panel_Bg.add(l_forgetPassword);
		
		JLabel l_toRegister = new JLabel("\u6CE8\u518C\u8D26\u53F7");
		l_toRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChatController chatController = new ChatController();
				//���ע�ᵽע��ҳ��
				LoginView.this.dispose();
				RegisterView view = new RegisterView();
				//�л���������
				chatController.setOperateWindows(view);
				view.setVisible(true);
			}
		});
		l_toRegister.setForeground(Color.BLUE);
		l_toRegister.setBounds(280, 71, 75, 18);
		panel_Bg.add(l_toRegister);
		
		JButton btn_login = new JButton("\u767B\u5F55");
		btn_login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//�����¼��֤
				String qq = t_qq.getText().trim();
				String password = new String(t_password.getPassword());
				if(!l_qqError.isVisible()) {
					//Ҫ��qq���ڵ�����²��ܽ�����һ����¼
					if(qq.length() > 0 && password.length() > 0) {
						//�û���¼��֤
						ChatController chatController = new ChatController();
						chatController.userLogin(qq, password);
					}
				}
			}
		});
		btn_login.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		btn_login.setBounds(117, 170, 160, 27);
		panel_Bg.add(btn_login);
		
		JLabel lblQq = new JLabel("qq\uFF1A");
		lblQq.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		lblQq.setBounds(54, 70, 59, 18);
		panel_Bg.add(lblQq);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801\uFF1A");
		label_1.setFont(new Font("���Ŀ���", Font.PLAIN, 18));
		label_1.setBounds(54, 130, 59, 18);
		panel_Bg.add(label_1);
		
		l_qqError = new JLabel("");
		l_qqError.setVisible(false);
		l_qqError.setForeground(Color.RED);
		l_qqError.setBounds(117, 42, 160, 18);
		panel_Bg.add(l_qqError);
		
		l_passwordError = new JLabel("\u5BC6\u7801\u9519\u8BEF\uFF01\uFF01\uFF01");
		l_passwordError.setVisible(false);
		l_passwordError.setForeground(Color.RED);
		l_passwordError.setBounds(117, 102, 160, 18);
		panel_Bg.add(l_passwordError);
		
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
		
		//����������Ϣ�߳�
		ChatController chatController = new ChatController();
		chatController.startReceiver(this);
		chatController.setOperateWindows(this);
	}
	
	//�����qq���Ƿ����
	public void checkQQ(boolean isExist, String failReason) {
		if(!isExist) {
			l_qqError.setText(failReason);
			l_qqError.setVisible(true);
		}
	}
	//��¼��֤
	public void login(boolean login, String username) {
		if(login) {
			LoginView.this.dispose();
			//��������
			MainView mainView = new MainView(t_qq.getText(), username);
			//�л���������
			mainView.setVisible(true);
		}else {
			//�������
			l_passwordError.setVisible(true);
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(new LunaLookAndFeel());
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				new LoginView().setVisible(true);
			}
		});
	}

}
