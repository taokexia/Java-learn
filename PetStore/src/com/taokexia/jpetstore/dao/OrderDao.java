package com.taokexia.jpetstore.dao;

import java.util.List;
import com.taokexia.jpetstore.domain.Order;

//��������DAO
public interface OrderDao {
	// ��ѯ���еĶ�����Ϣ
	List<Order> findAll();
	// ����������ѯ������Ϣ
	Order findById(int orderid);
	// ����������Ϣ
	int create(Order order);
	// �޸Ķ�����Ϣ
	int modify(Order order);
	// ɾ��������Ϣ
	int remove(Order order);
}
