package com.taokexia.qq.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChatFrame extends JFrame implements Runnable{
	private boolean isRunning = true;
	
	// ��ǰ�û�Id
	private String userId;
	// ��������û�Id
	private String friendUserId;
	// ��������û�Id
	private String friendUserName;
	
	// ��õ�ǰ��Ļ�ĸߺͿ�
	private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	
	// ��¼���ڿ�͸�
	private int frameWidth = 345;
	private int frameHeight = 310;
	
	// �鿴��Ϣ�ı���
	private JTextArea txtMainInfo;
	// ������Ϣ�ı���
	private JTextArea txtInfo;
	// ��Ϣ��־
	private StringBuffer infoLog;
	
	// ������Ϣ���߳�
	private Thread receiveMessageThread;
	// ���ڸ�ʽ��
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// �����б�Frame
	private FriendsFrame friendsFrame;
	
	public ChatFrame(FriendsFrame friendsFrame, Map<String, String> user, Map<String, String> friend) {
		// ��ʼ����Ա����
		this.friendsFrame = friendsFrame;
		
		this.userId = user.get("user_id");
		String userIcon = user.get("user_icon");
		
		this.friendUserId = friend.get("user_id");
		this.friendUserName = friend.get("user_name");
		
		this.infoLog = new StringBuffer();
		
		// ��ʼ���鿴��Ϣ���
		getContentPane().add(getPanLine1());
		// ��ʼ��������Ϣ���
		getContentPane().add(getPanLine2());
		
		/// ��ʼ����ǰFrame
		String iconFile = String.format("/resource/img/%s.jpg", userIcon);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Client.class.getResource(iconFile)));
		String title = String.format("��%s������...", friendUserName);
		setTitle(title);
		setResizable(false);
		getContentPane().setLayout(null);
		
		// ����Frame��С
		setSize(frameWidth, frameHeight);
		// ����Frameλ����Ļ���ĵ�����
		int x = (int) (screenWidth - frameWidth) / 2;
		int y = (int) (screenHeight - frameHeight) / 2;
		// ����Frameλ����Ļ����
		setLocation(x, y);
		
		receiveMessageThread = new Thread(this);
		receiveMessageThread.start();
		
		// ע�ᴰ���¼�
		addWindowListener(new WindowAdapter() {
			// �������ڹرհ�ťʱ����
			public void windowClosing(WindowEvent e) {
				isRunning = false;
				setVisible(false);
				// ���������б��߳�
				friendsFrame.resetThread();
			}
		});
	}
	
	// �鿴��Ϣ���
	private JPanel getPanLine1() {
		txtMainInfo = new JTextArea();
		txtMainInfo.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 320, 200);
		scrollPane.setViewportView(txtMainInfo);
		
		JPanel panLine1 = new JPanel();
		panLine1.setLayout(null);
		panLine1.setBounds(new Rectangle(5, 5, 330, 210));
		panLine1.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		panLine1.add(scrollPane);
		return panLine1;
	}
	
	// ������Ϣ���
	private JPanel getPanLine2() {
		JPanel panLine2 = new JPanel();
		panLine2.setLayout(null);
		panLine2.setBounds(5, 220, 330, 50);
		panLine2.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		panLine2.add(getSendButton());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 222, 40);
		panLine2.add(scrollPane);
		
		txtInfo = new JTextArea();
		scrollPane.setViewportView(txtInfo);
		
		return panLine2;
	}
	
	private JButton getSendButton() {
		JButton button = new JButton("����");
		button.setBounds(232, 10, 90, 30);
		button.addActionListener(e -> {
			sendMessage();
			txtInfo.setText("");
		});
		return button;
	}
	
	private void sendMessage() {
		if (!txtInfo.getText().equals("")) {
			// ��õ�ǰʱ�䣬����ʽ��
			String date = dateFormat.format(new Date());
			
			String info = String.format("#%s#" + "\n" + "����%s˵��%s", date, friendUserName, txtInfo.getText());
			infoLog.append(info).append('\n');
			txtMainInfo.setText(infoLog.toString());
			
			Map<String, String> message = new HashMap<String, String>();
			message.put("receive_user_id", friendUserId);
			message.put("user_id", userId);
			message.put("message", txtInfo.getText());
			
			JSONObject jsonObj = new JSONObject(message);
			jsonObj.put("command", Client.COMMAND_SENDMSG);
			
			try {
				InetAddress address = InetAddress.getByName(Client.SERVER_IP);
				/* �������ݱ� */
				byte[] b = jsonObj.toString().getBytes();
				DatagramPacket packet = new DatagramPacket(b, b.length, address, Client.SERVER_PORT);
				Client.socket.send(packet);
				
			} catch (IOException e) {
			}
		}
	}
	// TODO ������Ϣ

	@Override
	public void run() {
		// TODO �Զ����ɵķ������
		// ׼��һ��������
		byte[] buffer = new byte[1024];
		while (isRunning) {
			try {
				InetAddress address = InetAddress.getByName(Client.SERVER_IP);
				/* �������ݱ� */
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, Client.SERVER_PORT);
				// ��ʼ����
				Client.socket.receive(packet);
				// �������ݳ���
				int len = packet.getLength();
				String str = new String(buffer, 0, len);
				
				// ��ӡ���յ�����
				System.out.printf("�ӷ��������յ����ݣ���%s��\n", str);
				JSONObject jsonObj = new JSONObject(str);
				
				// ��õ�ǰʱ�䣬����ʽ��
				String date = dateFormat.format(new Date());
				String message = (String) jsonObj.get("message");
				String info = String.format("#%s#" + "\n" + "%s����˵��%s", date, friendUserName, message);
				infoLog.append(info).append('\n');
				
				txtMainInfo.setText(infoLog.toString());
				txtMainInfo.setCaretPosition(txtMainInfo.getDocument().getLength());
				
				Thread.sleep(100);
				// ˢ�º����б�
				JSONArray userList = (JSONArray) jsonObj.get("OnlineUserList");
				
				for (Object item : userList) {
					JSONObject onlineUser = (JSONObject) item;
					String userId = (String) onlineUser.get("user_id");
					String online = (String) onlineUser.get("online");
					friendsFrame.refreshFriendList(userId, online);
				}
				
			} catch (Exception e) {
			}
		}
	}
}
