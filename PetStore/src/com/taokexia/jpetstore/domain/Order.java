package com.taokexia.jpetstore.domain;

import java.util.Date;

public class Order {
	private long orderid; // ��Ʒid
	private String userid; // �¶������û�id
	private Date orderdate; // �¶�����ʱ��
	private int status; // ��������״̬ 0������ 1�Ѹ���
	private double amount; // ����Ӧ�����
	public long getOrderid() {
		return orderid;
	}
	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Date getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
