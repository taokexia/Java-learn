package com.taokexia.qq.server;

import java.net.InetAddress;

public class ClientInfo {
	// 用户Id
	private String userId;
	// 客户端IP地址
	private InetAddress address;
	// 客户端端口号
	private int port;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public InetAddress getAddress() {
		return address;
	}
	public void setAddress(InetAddress address) {
		this.address = address;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
