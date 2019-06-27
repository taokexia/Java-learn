package com.taokexia.jpetstore.dao;

import java.util.List;
import com.taokexia.jpetstore.domain.Account;

//�û�����DAO
public interface AccountDao {
	// ��ѯ���е��û���Ϣ
	List<Account> findAll();
	// ����������ѯ�û���Ϣ
	Account findById(String userid);
	// �����û���Ϣ
	int create(Account account);
	// �޸��û���Ϣ
	int modify(Account account);
	// ɾ���û���Ϣ
	int remove(Account account);
}
