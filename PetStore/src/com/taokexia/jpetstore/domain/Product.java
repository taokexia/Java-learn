package com.taokexia.jpetstore.domain;

public class Product {
	private String productid; // ��ƷId
    private String category; // ��Ʒ���
    private String cname; // ��Ʒ������
    private String ename; // ��ƷӢ����
    private String image; // ��ƷͼƬ
    private String descn; // ��Ʒ����
    private double listprice; // ��Ʒ�г���
    private double unitcost; // ��Ʒ����
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public double getListprice() {
		return listprice;
	}
	public void setListprice(double listprice) {
		this.listprice = listprice;
	}
	public double getUnitcost() {
		return unitcost;
	}
	public void setUnitcost(double unitcost) {
		this.unitcost = unitcost;
	}
    
}
