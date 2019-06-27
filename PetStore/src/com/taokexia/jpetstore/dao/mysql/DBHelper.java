package com.taokexia.jpetstore.dao.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//���ݿ⸨����
public class DBHelper {
	// �������ݿ�url;
	static String url;
	// ���� Properties����
	static Properties info = new Properties();
	// 1.�������س���
	static {
		// ��������ļ�������
		InputStream input = DBHelper.class.getClassLoader().getResourceAsStream("com/taokexia/jpetstore/dao/mysql/config.properties");
		try {
			// ���������ļ����ݵ� Properties����
			info.load(input);
			// �������ļ�ȡ�� driver
			url = info.getProperty("url");
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
	// ������ݿ�����
	public static Connection getConnection() throws SQLException {
		// �������ݿ�����
		Connection conn = DriverManager.getConnection(url, info);
		return conn;
	}
}
