package com.taokexia.qq.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

public class LoginFrame extends JFrame {
	// private Client client;
	// ��õ�ǰ��Ļ��͸�
	private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	// ��¼���ڿ�͸�
	private int frameWidth = 329;
	private int frameHeight = 250;
	
	// QQ�����ı���
	private JTextField txtUserId = null;
	// QQ�����
	private JPasswordField txtUserPwd = null;
	
	public LoginFrame() {
		JLabel lblImage = new JLabel();
		lblImage.setIcon(new ImageIcon(LoginFrame.class.getResource("/resource/img/QQ11.JPG")));
		lblImage.setBounds(0, 0, 325, 48);
		getContentPane().add(lblImage);
		
		// ����������
		getContentPane().add(getPaneLine());
		
		// ��ʼ����¼��ť
		JButton btnLogin = new JButton();
		btnLogin.setBounds(152, 181, 63, 19);
		btnLogin.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnLogin.setText("��¼");
		getContentPane().add(btnLogin);
		// ע���¼��ť�¼�������
		btnLogin.addActionListener(e -> {
			// TODO ��¼����
			// �Ƚ����û�������֤����֤ͨ���ٵ�¼
			String userId = txtUserId.getText();
			String password = new String(txtUserPwd.getPassword());
			
			Map user = login(userId, password);
			if (user != null) { 
                // ��¼�ɹ���ת����
                System.out.println("��¼�ɹ���ת����");
                // ���õ�¼���ڿɼ�
                this.setVisible(false);
                FriendsFrame frame = new FriendsFrame(user);
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "��QQ��������벻��ȷ");
            }
		});
		
		// ��ʼ��ȡ����ť
		JButton btnCancel = new JButton();
		btnCancel.setBounds(233, 181, 63, 19);
		btnCancel.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnCancel.setText("ȡ��");
		getContentPane().add(btnCancel);
		btnCancel.addActionListener(e -> {
			// �˳�ϵͳ
			System.exit(0);
		});
		
		// ��ʼ����������������ť
		JButton btnSetup = new JButton();
		btnSetup.setBounds(14, 179, 99, 22);
		btnSetup.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnSetup.setText("��������");
		getContentPane().add(btnSetup);
		
		/// ��ʼ����ǰ����
		setIconImage(Toolkit.getDefaultToolkit().getImage(Client.class.getResource("/resource/img/QQ.png")));
		setTitle("QQ��¼");
		setResizable(false);
		getContentPane().setLayout(null);
		// ���ô��ڴ�С
		setSize(frameWidth, frameHeight);
		// ���㴰��λ����Ļ���ĵ�����
		int x = (int) (screenWidth - frameWidth) / 2;
		int y = (int) (screenHeight - frameHeight) / 2;
		// ���ô���λ����Ļ����
		setLocation(x, y);
		
		// ע�ᴰ���¼�
		addWindowListener(new WindowAdapter() {
			// �������ڹرհ�ťʱ����
			public void windowClosing(WindowEvent e) {
				// �˳�ϵͳ
				System.exit(0);
			}
		});
	}
	
	// �ͻ�������������͵�¼����
	public Map login(String userId, String password) {
		// ׼��һ��������
		byte[] buffer = new byte[1024];
		InetAddress address;
		
		try {
			address = InetAddress.getByName(Client.SERVER_IP);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("command", Client.COMMAND_LOGIN);
			jsonObj.put("user_id", userId);
			jsonObj.put("user_pwd", password);
			// �ֽ�����
			byte[] b = jsonObj.toString().getBytes();
			// ���� DatagramPacket ����
			DatagramPacket packet = new DatagramPacket(b, b.length, address, Client.SERVER_PORT);
			// ����
			Client.socket.send(packet);
			
			/* �������ݱ� */
			packet = new DatagramPacket(buffer, buffer.length, address, Client.SERVER_PORT);
			Client.socket.receive(packet); 
			// �������ݳ���
			int len = packet.getLength();
			String str = new String(buffer, 0, len);
			System.out.println("receivedjsonObj = " + str);
			JSONObject receivedjsonObj = new JSONObject(str);
			if ((Integer) receivedjsonObj.get("result") == 0) { 
				Map user = receivedjsonObj.toMap(); 
				return user;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// �������
	private JPanel getPaneLine() {
		JPanel paneLine = new JPanel();
		paneLine.setLayout(null);
		paneLine.setBounds(7, 54, 308, 118);
		// �߿���ɫ����Ϊ��ɫ
		paneLine.setBorder(BorderFactory.createLineBorder(new Color(102, 153, 255), 1));
		
		// ��ʼ�����������룿����ǩ
		JLabel lblHelp = new JLabel();
		lblHelp.setBounds(227, 47, 67, 21);
		lblHelp.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblHelp.setForeground(new Color(51, 51, 255));
		lblHelp.setText("�������룿");
		paneLine.add(lblHelp);
		
		// ��ʼ����QQ���롿��ǩ
		JLabel lblUserPwd = new JLabel();
		lblUserPwd.setText("QQ����");
		lblUserPwd.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUserPwd.setBounds(21, 48, 54, 18);
		paneLine.add(lblUserPwd);
		
		// ��ʼ����QQ���������ǩ
		JLabel lblUserId = new JLabel();
		lblUserId.setText("QQ�����");
		lblUserId.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUserId.setBounds(21, 14, 55, 18);
		paneLine.add(lblUserId);
		
		// ��ʼ����QQ���롿�ı���
		this.txtUserId = new JTextField();
		this.txtUserId.setBounds(84, 14, 132, 18);
		paneLine.add(this.txtUserId);
		
		// ��ʼ����QQ���롿�����
		this.txtUserPwd = new JPasswordField();
		this.txtUserPwd.setBounds(84, 48, 132, 18);
		paneLine.add(this.txtUserPwd);
		
		// ��ʼ�����Զ���¼����ѡ��
		JCheckBox chbAutoLogin = new JCheckBox();
		chbAutoLogin.setText("�Զ���¼");
		chbAutoLogin.setFont(new Font("Dialog", Font.PLAIN, 12));
		chbAutoLogin.setBounds(79, 77, 73, 19);
		paneLine.add(chbAutoLogin);
		
		// ��ʼ���������¼����ѡ��
		JCheckBox chbHideLogin = new JCheckBox();
		chbHideLogin.setText("�����¼");
		chbHideLogin.setFont(new Font("Dialog", Font.PLAIN, 12));
		chbHideLogin.setBounds(155, 77, 73, 19);
		paneLine.add(chbHideLogin);
		return paneLine;
	}
}
