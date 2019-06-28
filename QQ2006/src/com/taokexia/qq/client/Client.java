package com.taokexia.qq.client;

import java.io.IOException;
import java.net.DatagramSocket;

public class Client {
	// �������
	public static final int COMMAND_LOGIN = 1; // ��¼���� 
	public static final int COMMAND_LOGOUT = 2; // ע������
	public static final int COMMAND_SENDMSG = 3; // ����Ϣ����
	
	public static DatagramSocket socket;
	// ��������IP
	public static String SERVER_IP = "192.168.1.113";
	// �������˿ں�
	public static int SERVER_PORT = 7788;
	
	public static void main(String[] args) {
		if (args.length == 2) {
			SERVER_IP = args[0];
			SERVER_PORT = Integer.parseInt(args[1]);
		}
		
		try {// ����DatagramSocket������ϵͳ�������ʹ�õĶ˿�
			socket = new DatagramSocket();
			// ���ó�ʱ5�룬���ٵȴ���������
			socket.setSoTimeout(5000);
			System.out.println("�ͻ�������...");
			LoginFrame frame = new LoginFrame();
			frame.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
