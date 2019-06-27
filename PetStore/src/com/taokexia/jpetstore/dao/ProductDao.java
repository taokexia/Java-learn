package com.taokexia.jpetstore.dao;

import java.util.List;
import com.taokexia.jpetstore.domain.Product;

//��Ʒ����DAO
public interface ProductDao {
	// ��ѯ���е���Ʒ��Ϣ
	List<Product> findAll();
	// ����������ѯ��Ʒ��Ϣ
	Product findById(String productid);
	// ���ݰ����б��ѯ��Ʒ��Ϣ
	List<Product> findByCategory(String category);
	// ������Ʒ��Ϣ
	int create(Product product);
	// �޸���Ʒ��Ϣ
	int modify(Product product);
	// ɾ����Ʒ��Ϣ
	int remove(Product product);
}
