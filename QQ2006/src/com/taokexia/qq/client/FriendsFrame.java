package com.taokexia.qq.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.json.JSONObject;

public class FriendsFrame extends JFrame implements Runnable {

	// �߳�����״̬
	private boolean isRunning = true;
	// �û���Ϣ
	private Map user;
	// �����б�
	private List<Map<String, String>> friends;
	// ���ѱ�ǩ�ؼ��б�
	private List<JLabel> lblFriendList;
	// ��õ�ǰ��Ļ�Ŀ�
	private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	// ��¼���ڿ��
	private int frameWidth = 260;
	private int frameHeight = 600;

	public FriendsFrame(Map user) {
		setTitle("QQ2006");

		// ��ʼ����Ա����
		this.user = user;
		/// ��ʼ���û��б�
		this.friends = (List<Map<String, String>>) user.get("friends");

		// ���ò���
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setVgap(5);

		String userId = (String) user.get("user_id");
		String userName = (String) user.get("user_name");
		String userIcon = (String) user.get("user_icon");

		JLabel lblLabel = new JLabel(userName);
		lblLabel.setHorizontalAlignment(SwingConstants.CENTER);
		String iconFile = String.format("/resource/img/%s.jpg", userIcon);
		lblLabel.setIcon(new ImageIcon(FriendsFrame.class.getResource(iconFile)));
		getContentPane().add(lblLabel, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel panel1 = new JPanel();
		scrollPane.setViewportView(panel1);
		panel1.setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel("�ҵĺ���");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel1.add(label, BorderLayout.NORTH);

		// �����б����
		JPanel friendListPanel = new JPanel();
		panel1.add(friendListPanel);
		friendListPanel.setLayout(new GridLayout(50, 0, 0, 5));

		lblFriendList = new ArrayList<JLabel>();
		// ��ʼ�������б�
		for (int i = 0; i < friends.size(); i++) {
			Map<String, String> friend = this.friends.get(i);
			String friendUserId = friend.get("user_id");
			String friendUserName = friend.get("user_name");
			String friendUserIcon = friend.get("user_icon");
			// ��ú�������״̬
			String friendUserOnline = friend.get("online");

			JLabel lblFriend = new JLabel(friendUserName);
			lblFriend.setToolTipText(friendUserId);
			String friendIconFile = String.format("/resource/img/%s.jpg", friendUserIcon);
			lblFriend.setIcon(new ImageIcon(FriendsFrame.class.getClass().getResource(friendIconFile)));
			// �������ÿ��ã��������ò�����
			if (friendUserOnline.equals("0")) {
				lblFriend.setEnabled(false);
			} else {
				lblFriend.setEnabled(true);
			}

			lblFriend.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// �û�ͼ��˫�����ʱ��ʾ�Ի���
					if (e.getClickCount() == 2) {
						ChatFrame chatFrame = new ChatFrame(FriendsFrame.this, user, friend);
						chatFrame.setVisible(true);
						isRunning = false;
					}
				}
			});
			// ��ӵ��б���
			lblFriendList.add(lblFriend);
			// ��ӵ����
			friendListPanel.add(lblFriend);
		}
		/// ��ʼ����ǰFrame
		setBounds((int) screenWidth - 300, 10, frameWidth, frameHeight);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FriendsFrame.class.getResource("/resource/img/QQ.png")));
		// ע�ᴰ���¼�
		addWindowListener(new WindowAdapter() {
			// �������ڹرհ�ťʱ����
			public void windowClosing(WindowEvent e) {
				// TODO �û�����
				// ��ǰ�û�����
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("command", Client.COMMAND_LOGOUT);
				jsonObj.put("user_id", userId);
				byte[] b = jsonObj.toString().getBytes();
				InetAddress address;
				try {
					address = InetAddress.getByName(Client.SERVER_IP);
					// ����DatagramPacket����
					DatagramPacket packet = new DatagramPacket(b, b.length, address, Client.SERVER_PORT);
					// ����
					Client.socket.send(packet);
				} catch (IOException e1) {
				}
				// �˳�ϵͳ
				System.exit(0);
			}
		});

		// ����������Ϣ���߳�
		resetThread();
	}

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
				System.out.println("�ͻ��ˣ� " + str);

				JSONObject jsonObj = new JSONObject(str);
				String userId = (String) jsonObj.get("user_id");
				String online = (String) jsonObj.get("online");

				// ˢ�º����б�
				refreshFriendList(userId, online);

			} catch (Exception e) {
			}
		}
	}

	// ˢ�º����б�
	public void refreshFriendList(String userId, String online) {
		// ��ʼ�������б�
		for (JLabel lblFriend : lblFriendList) {
			// �ж��û�Id�Ƿ�һ��
			if (userId.equals(lblFriend.getToolTipText())) {
				if (online.equals("1")) {
					lblFriend.setEnabled(true);
				} else {
					lblFriend.setEnabled(false);
				}
			}
		}
	}

	// ��������������Ϣ���߳�
	public void resetThread() {
		isRunning = true;
		// ������Ϣ���߳�
		Thread receiveMessageThread = new Thread(this);
		// ����������Ϣ�߳�
		receiveMessageThread.start();
	}
}
