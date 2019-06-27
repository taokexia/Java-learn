package com.taokexia.jpetstore.dao;

import java.util.List;
import com.taokexia.jpetstore.domain.OrderDetail;
//������ϸ����DAO
public interface OrderDetailDao {
	// ��ѯ���еĶ�����ϸ��Ϣ
	List<OrderDetail> findAll();
	// ����������ѯ������ϸ��Ϣ
	OrderDetail findByPK(int orderid, String productid);
	// ����������ϸ��Ϣ
	int create(OrderDetail orderDetail);
	// �޸Ķ�����ϸ��Ϣ
	int modify(OrderDetail orderDetail);
	// ɾ��������ϸ��Ϣ
	int remove(OrderDetail orderDetail);
}
