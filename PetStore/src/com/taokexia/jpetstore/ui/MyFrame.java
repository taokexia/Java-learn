package com.taokexia.jpetstore.ui;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class MyFrame extends JFrame {
	// ��õ�ǰ��Ļ��
	private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	// ��õ�ǰ��Ļ�ĸ�
	private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public MyFrame(String title, int width, int height) {
		super(title);
		// ���ô��ڴ�С
		setSize(width, height);
		// ���㴰��λ����Ļ���ĵ�����
		int x = (int)(screenWidth - width) / 2;
		int y = (int)(screenHeight - height) / 2;
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
	
}
