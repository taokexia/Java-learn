package com.taokexia.jpetstore.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.taokexia.jpetstore.dao.OrderDao;
import com.taokexia.jpetstore.dao.OrderDetailDao;
import com.taokexia.jpetstore.dao.ProductDao;
import com.taokexia.jpetstore.dao.mysql.OrderDaoImp;
import com.taokexia.jpetstore.dao.mysql.OrderDetailDaoImp;
import com.taokexia.jpetstore.dao.mysql.ProductDaoImp;
import com.taokexia.jpetstore.domain.Order;
import com.taokexia.jpetstore.domain.OrderDetail;
import com.taokexia.jpetstore.domain.Product;

// ��Ʒ���ﳵ����
public class CartFrame extends MyFrame {
	private JTable table;
	// ���ﳵ����
	private Object[][] data = null;
	// ������ƷDao����
	private ProductDao dao = new ProductDaoImp();
	// ���ﳵ������ѡ����ƷId��ֵ����Ʒ������
	private Map<String, Integer> cart;
	// ���õ��ϼ�Frame(ProductListFrame)
	private ProductListFrame productListFrame;
	
	public CartFrame(Map<String, Integer> cart, ProductListFrame productListFrame) {
		super("��Ʒ���ﳵ", 1000, 700);
		this.cart = cart;
		this.productListFrame = productListFrame;
		
		JPanel topPanel = new JPanel();
		FlowLayout fl_topPanel = (FlowLayout) topPanel.getLayout();
		fl_topPanel.setVgap(10);
		fl_topPanel.setHgap(20);
		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JButton btnReturn = new JButton("������Ʒ�б�");
		btnReturn.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		topPanel.add(btnReturn);
		
		JButton btuSubmit = new JButton("�ύ����");
		topPanel.add(btuSubmit);
		btuSubmit.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(getTable());
		
		// ע�᡾�ύ��������ť��ActionEvent�¼�������
		btuSubmit.addActionListener(e -> { 
			// ���ɶ���
			generateOrders();
			JLabel label = new JLabel("�����Ѿ����ɣ��ȴ����");
			label.setFont(new Font("΢���ź�", Font.PLAIN, 15));
			if (JOptionPane.showConfirmDialog(this, label, "��Ϣ",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				// TODO ����
				System.exit(0);
			} else {
				System.exit(0);
			}
		});

		// ע�᡾������Ʒ�б���ť��ActionEvent�¼�������
		btnReturn.addActionListener(e -> {
			// ���¹��ﳵ
			for (int i = 0; i < data.length; i++) {
				// ��Ʒ���
				String productid = (String) data[i][0];
				// ����
				Integer quantity = (Integer) data[i][3];
				cart.put(productid, quantity);
			}
			this.productListFrame.setVisible(true);
			setVisible(false);
		});
	}
	
	// ��ʼ���������еı��ؼ�
	private JTable getTable() {
		// ׼����������
		data = new Object[cart.size()][5];
		Set<String> keys = this.cart.keySet();
		int indx = 0;
		for (String productid : keys) {
			Product p = dao.findById(productid);
			data[indx][0] = p.getProductid();// ��Ʒ���
			data[indx][1] = p.getCname();// ��Ʒ��
			data[indx][2] = new Double(p.getUnitcost());// ��Ʒ����
			data[indx][3] = new Integer(cart.get(productid));// ����
			// ������ƷӦ�����
			double amount = (double) data[indx][2] * (int) data[indx][3];
			data[indx][4] = new Double(amount);
			indx++;
;		}
		
		// ����������ģ��
		TableModel model = new CartTableModel(data);
		
		if (table == null) {
			// ������
			table = new JTable(model);
			// ���ñ�����������
			table.setFont(new Font("΢���ź�", Font.PLAIN, 16));
			// ���ñ��б�������
			table.getTableHeader().setFont(new Font("΢���ź�", Font.BOLD, 16));
			// ���ñ��и�
			table.setRowHeight(51);
			table.setRowSelectionAllowed(false);
		} else {
			table.setModel(model);
		}
		return table;
	}
	
	// ���ɶ���
	private void generateOrders() {
		OrderDao orderDao = new OrderDaoImp();
		OrderDetailDao orderDetailDao = new OrderDetailDaoImp();
		
		Order order = new Order();
		order.setUserid(MainApp.account.getUserid());
		// 0������
		order.setStatus(0);
		// ����Id�ǵ�ǰʱ��
		Date now = new Date();
		long orderId = now.getTime();
		order.setOrderid(orderId);
		order.setOrderdate(now);
		order.setAmount(getOrderTotalAmount());
		
		// �¶���ʱ�������ݿ��Զ����ɲ�������
		// ��������
		orderDao.create(order);
		
		for (int i = 0; i < data.length; i++) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderid(orderId);
			orderDetail.setProductid((String) data[i][0]);
			orderDetail.setQuantity((int) data[i][3]);
			orderDetail.setUnitcost((double) data[i][2]);
			// ����������ϸ
			orderDetailDao.create(orderDetail);
		}
	}
	
	// ���㶩��Ӧ���ܽ��
	private double getOrderTotalAmount() {
		double totalAmount = 0.0;
		for (int i = 0; i < data.length; i++) {
			// ������ƷӦ�����
			totalAmount += (Double) data[i][4];
		}
		return totalAmount;
	}
}
