package com.taokexia.qq.client;

import java.io.IOException;
import java.net.DatagramSocket;

public class Client {
	// 命令代码
	public static final int COMMAND_LOGIN = 1; // 登录命令 
	public static final int COMMAND_LOGOUT = 2; // 注销命令
	public static final int COMMAND_SENDMSG = 3; // 发消息命令
	
	public static DatagramSocket socket;
	// 服务器端IP
	public static String SERVER_IP = "192.168.1.113";
	// 服务器端口号
	public static int SERVER_PORT = 7788;
	
	public static void main(String[] args) {
		if (args.length == 2) {
			SERVER_IP = args[0];
			SERVER_PORT = Integer.parseInt(args[1]);
		}
		
		try {// 创建DatagramSocket对象，由系统分配可以使用的端口
			socket = new DatagramSocket();
			// 设置超时5秒，不再等待接收数据
			socket.setSoTimeout(5000);
			System.out.println("客户端运行...");
			LoginFrame frame = new LoginFrame();
			frame.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
