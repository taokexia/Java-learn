package com.taokexia.jpetstore.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.taokexia.jpetstore.domain.Product;

public class ProductTableModel extends AbstractTableModel {
	
	// ������� columnNames
	private String[] columnNames = {"��Ʒ���", "��Ʒ���", "��Ʒ������", "��ƷӢ����"};
	// ����е����ݱ�����List<Product>������
	private List<Product> data = null;
	public ProductTableModel(List<Product> data) {
		this.data = data;
	}
	// ��������
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	// ��������
	@Override
	public int getRowCount() {
		return data.size();
	}
	// ���ĳ��ĳ�е����ݣ������ݱ����ڶ�������data��
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// ÿһ�о���һ��Product��Ʒ����
		Product p = data.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return p.getProductid(); // ��һ����Ʒ���
			case 1:
				return p.getCategory(); // �ڶ�����Ʒ���
			case 2:
				return p.getCname(); // ��������Ʒ������
			default:
				return p.getEname(); // ��������ƷӢ����
		}
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

}
