package com.taokexia.qq.server;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {
	// �������ݿ�url
	static String url;
	// ���� Properties ����
	static Properties info = new Properties();
	
	// 1. �����������
	static {
		InputStream input = DBHelper.class.getResourceAsStream("com/taokexia/qq/server/config.properties");
		try {
			// ���������ļ����ݵ� Properties ����
			info.load(input);
			// �������ļ���ȡ�� url
			url = info.getProperty("url");
			// Class.forName("com.mysql.jdbc.Driver");
			// �������ļ���ȡ��driver
			String driverClassName = info.getProperty("driver");
			Class.forName(driverClassName);
			System.out.println("����������سɹ�...");
		} catch (ClassNotFoundException e) {
			System.out.println("�����������ʧ��...");
		} catch (IOException e) {
			System.out.println("���������ļ�ʧ��...");
		}
	}
	
	public static Connection getConnection() throws SQLException {
		// �������ݿ�����
		Connection conn = DriverManager.getConnection(url, info);
		return conn;
	}
}
