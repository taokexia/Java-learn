package com.taokexia.jpetstore.domain;

import java.util.Date;

public class Order {
	private long orderid; // 商品id
	private String userid; // 下订单的用户id
	private Date orderdate; // 下订单的时间
	private int status; // 订单付款状态 0待付款 1已付款
	private double amount; // 订单应付金额
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
