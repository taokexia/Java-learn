package com.taokexia.jpetstore.ui;

import com.taokexia.jpetstore.domain.Account;

// ������
public class MainApp {
	// �û���¼�ɹ��󣬱��浱ǰ�û���Ϣ
	public static Account account; 
	public static void main(String[] args) {
		LoginFrame frame = new LoginFrame();
		frame.setVisible(true);
	}

}
