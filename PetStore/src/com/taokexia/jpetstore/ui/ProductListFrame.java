package com.taokexia.jpetstore.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;

import com.taokexia.jpetstore.dao.ProductDao;
import com.taokexia.jpetstore.dao.mysql.ProductDaoImp;
import com.taokexia.jpetstore.domain.Product;

public class ProductListFrame extends MyFrame {
	private JTable table;
	private JLabel lblImage;
	private JLabel lblListprice;
	private JLabel lblDescn;
	private JLabel lblUnitcost;
	// ��Ʒ�б���
	private List<Product> products = null;
	// ������ƷDao����
	private ProductDao dao = new ProductDaoImp();
	// ���ﳵ������ѡ����Ʒ��Id��ֵ����Ʒ����
	private Map<String, Integer> cart = new HashMap<String, Integer>();
	// ѡ����Ʒ����
	private int selectedRow = -1;
	
	public ProductListFrame() {
		super("��Ʒ�б�", 1000, 700);
		// ��ѯ������Ʒ
		products = dao.findAll();
		
		// ��Ӷ����������
		getContentPane().add(getSearchPanel(), BorderLayout.NORTH);
		
		// �����������
		JSplitPane splitPane = new JSplitPane();
		// ����ָ���ָ���λ�ã��Ӵ������ߵ��ָ��������
		splitPane.setDividerLocation(600);
		// ����������
		splitPane.setLeftComponent(getLeftPanel());
		// �����Ҳ����
		splitPane.setRightComponent(getRightPanel());
		// �ѷ��������ӵ��������
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
	}
	
	// ��ʼ���������
	private JPanel getSearchPanel() {
		JPanel searchPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) searchPanel.getLayout();
		flowLayout.setVgap(20);
		flowLayout.setHgap(40);
		
		JLabel lbl = new JLabel("ѡ����Ʒ���");
		lbl.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		searchPanel.add(lbl);
		
		String[] categorys = { "����", "����", "������", "è��", "����" };
		JComboBox comboBox = new JComboBox(categorys);
		comboBox.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		searchPanel.add(comboBox);
		
		JButton btnGo = new JButton("��ѯ");
		btnGo.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		searchPanel.add(btnGo);
		
		JButton btnReset = new JButton("����");
		btnReset.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		searchPanel.add(btnReset);
		
		// ע���ѯ��ť��ActionEvent�¼�������
		btnGo.addActionListener(e -> {
			// ��ѡ������
			String category = (String) comboBox.getSelectedItem();
			// ���������в�ѯ
			products = dao.findByCategory(category);
			TableModel model = new ProductTableModel(products);
			table.setModel(model);
		});
		// ע�����ð�ť��ActionEvent�¼�������
		btnReset.addActionListener(e -> {
			products = dao.findAll();
			TableModel model = new ProductTableModel(products);
			table.setModel(model);
		});
		return searchPanel;
	}
	
	// ��ʼ���Ҳ����
	private JPanel getRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		lblImage = new JLabel();
		rightPanel.add(lblImage);
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel detailPanel = new JPanel();
		detailPanel.setBackground(Color.WHITE);
		rightPanel.add(detailPanel);
		detailPanel.setLayout(new GridLayout(8, 1, 0, 5));
		
		JSeparator separator_1 = new JSeparator();
		detailPanel.add(separator_1);
		
		lblListprice = new JLabel();
		detailPanel.add(lblListprice);
		// ��������
		lblListprice.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		
		lblUnitcost = new JLabel();
		detailPanel.add(lblUnitcost);
		// ��������
		lblUnitcost.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		
		lblDescn = new JLabel();
		detailPanel.add(lblDescn);
		// ��������
		lblDescn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		
		JSeparator separator_2 = new JSeparator();
		detailPanel.add(separator_2);
		
		JButton btnAdd = new JButton("��ӵ����ﳵ");
		btnAdd.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		detailPanel.add(btnAdd);
		
		// ����ռλʹ��
		JLabel lb1 = new JLabel("");
		detailPanel.add(lb1);
		
		JButton btnCheck = new JButton("�鿴���ﳵ");
		btnCheck.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		detailPanel.add(btnCheck);
		
		// ע�᡾��ӵ����ﳵ����ť��ActionEvent�¼�������
		btnAdd.addActionListener(e -> {
			if (selectedRow < 0) {
				return;
			}
			// �����Ʒ�����ﳵ����
			Product selectProduct = products.get(selectedRow);
			String productid = selectProduct.getProductid();
			if (cart.containsKey(productid)) {// ���ﳵ���Ѿ��и���Ʒ
				// �����Ʒ����
				Integer quantity = cart.get(productid);
				cart.put(productid, ++quantity);
			} else {// ���ﳵ�л�û�и���Ʒ
				cart.put(productid, 1);
			}
			System.out.println(cart);
		});
		// ע�᡾�鿴���ﳵ����ť��ActionEvent�¼�������
		btnCheck.addActionListener(e -> {
			CartFrame cartFrame = new CartFrame(cart, this);
			cartFrame.setVisible(true);
			setVisible(false);
		});
		return rightPanel;
	}
	
	// ��ʼ��������
    private JScrollPane getLeftPanel() {
        JScrollPane leftScrollPane = new JScrollPane();
        // �������Ϊ�������ĸ����ӿ���ͼ
        leftScrollPane.setViewportView(getTable());
        return leftScrollPane;
    }
    
    // ��ʼ���������еı��ؼ�
    private JTable getTable() {
        TableModel model = new ProductTableModel(this.products);
        if (table == null) {
            table = new JTable(model);
            // ���ñ�����������
            table.setFont(new Font("΢���ź�", Font.PLAIN, 16));
            // ���ñ��б�������
            table.getTableHeader().setFont(new Font("΢���ź�", Font.BOLD, 16));
            // ���ñ��и�
            table.setRowHeight(51);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            ListSelectionModel rowSelectionModel = table.getSelectionModel();
            rowSelectionModel.addListSelectionListener(e -> { 
                //ֻ��������ͷ�
                if (e.getValueIsAdjusting()) {
                    return;
                }
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                selectedRow = lsm.getMinSelectionIndex();
                if (selectedRow < 0) {
                    return;
                }
                // �����Ҳ��������
                Product p = products.get(selectedRow);
                String petImage = String.format("/images/%s", p.getImage()); 
                // ProductListFrame.class.getResource(petImage) ���Ի��ͼƬ�ļ�����ʱ�ľ���·����
                ImageIcon icon = new ImageIcon(ProductListFrame.class.getResource(petImage)); 
                lblImage.setIcon(icon);
                String descn = p.getDescn();
                lblDescn.setText("��Ʒ������" + descn);
                double listprice = p.getListprice();
                String slistprice = String.format("��Ʒ�г��ۣ�%.2f", listprice);
                lblListprice.setText(slistprice);
                double unitcost = p.getUnitcost();
                String slblUnitcost = String.format("��Ʒ���ۣ�%.2f", unitcost);
                lblUnitcost.setText(slblUnitcost);
            });
        } else {
            table.setModel(model);
        }
        return table;
    }
}
