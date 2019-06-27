package com.taokexia.jpetstore.ui;

import com.taokexia.jpetstore.domain.Account;

// 启动类
public class MainApp {
	// 用户登录成功后，保存当前用户信息
	public static Account account; 
	public static void main(String[] args) {
		LoginFrame frame = new LoginFrame();
		frame.setVisible(true);
	}

}
