package com.taokexia.jpetstore.ui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.taokexia.jpetstore.dao.AccountDao;
import com.taokexia.jpetstore.dao.mysql.AccountDaoImp;
import com.taokexia.jpetstore.domain.Account;

public class LoginFrame extends MyFrame {
	private JTextField txtAccountId;
	private JPasswordField txtPassword;

	public LoginFrame() {
		super("�û���¼", 400, 230);
		// ���ò���Ϊ���Բ���
		getContentPane().setLayout(null);

		JLabel label1 = new JLabel();
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setBounds(51, 33, 83, 30);
		getContentPane().add(label1);
		label1.setText("�˺�:");
		label1.setFont(new Font("΢���ź�", Font.PLAIN, 15));

		txtAccountId = new JTextField(10);
		txtAccountId.setText(null);
		txtAccountId.setBounds(158, 33, 157, 30);
		txtAccountId.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		getContentPane().add(txtAccountId);

		JLabel label2 = new JLabel();
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setBounds(51, 85, 83, 30);
		getContentPane().add(label2);
		label2.setText("����:");
		label2.setFont(new Font("΢���ź�", Font.PLAIN, 15));

		txtPassword = new JPasswordField(10);
		txtPassword.setText(null);
		txtPassword.setBounds(158, 85, 157, 30);
		getContentPane().add(txtPassword);

		JButton btnOk = new JButton();
		btnOk.setText("ȷ��");
		btnOk.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		btnOk.setBounds(61, 140, 100, 30);
		getContentPane().add(btnOk);
		JButton btnCancel = new JButton();
		btnCancel.setText("ȡ��");
		btnCancel.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		btnCancel.setBounds(225, 140, 100, 30);
		getContentPane().add(btnCancel);

		// ע��btnOk��ActionEvent�¼�������
		btnOk.addActionListener(e -> {
			AccountDao accountDao = new AccountDaoImp();
			Account account = accountDao.findById(txtAccountId.getText());
			String passwordText = new String(txtPassword.getPassword());
			if (account != null && passwordText.equals(account.getPassword())) {
				System.out.println("��¼�ɹ���");
				ProductListFrame form = new ProductListFrame();
				form.setVisible(true);
				setVisible(false);
				// �û���¼�ɹ��󣬽��û���Ϣ���浽MainApp.accout��̬������
				MainApp.account = account;
			} else {
				JLabel label = new JLabel("��������˺Ż������������������롣");
				label.setFont(new Font("΢���ź�", Font.PLAIN, 15));
				JOptionPane.showMessageDialog(null, label, "��¼ʧ��", JOptionPane.ERROR_MESSAGE);
			}
		});
		// ע��btnCancel��ActionEvent�¼�������
		btnCancel.addActionListener(e -> {
			// �˳�ϵͳ
			System.exit(0);
		});

	}

}
