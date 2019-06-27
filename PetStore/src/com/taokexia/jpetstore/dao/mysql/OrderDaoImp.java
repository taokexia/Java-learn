package com.taokexia.jpetstore.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.taokexia.jpetstore.dao.OrderDao;
import com.taokexia.jpetstore.domain.Order;

public class OrderDaoImp implements OrderDao {

	@Override
	public List<Order> findAll() {
		String sql = "select orderid,userid,orderdate from product";
		List<Order> orderList = new ArrayList<Order>();
		try (
				// 2.创建数据库连接
				Connection conn = DBHelper.getConnection();
				// 3. 创建语句对象
				PreparedStatement pstmt = conn.prepareStatement(sql);
				// 4. 绑定参数
				// 5. 执行查询（R）
				ResultSet rs = pstmt.executeQuery()) {
			// 6. 遍历结果集
			while (rs.next()) {
				Order order = new Order();
				order.setOrderid(rs.getInt("orderid"));
				order.setUserid(rs.getString("userid"));
				order.setOrderdate(rs.getDate("orderdate"));
				orderList.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	@Override
	public Order findById(int orderid) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public int create(Order order) {
		try ( 
			// 2.创建数据库连接
			Connection conn = DBHelper.getConnection();
			// 3. 创建语句对象
			PreparedStatement pstmt = conn.prepareStatement(
					"insert into orders (orderid,userid,orderdate,status,amount)" + "values (?,?,?,?,?)")) {
			// 4. 绑定参数
			pstmt.setLong(1, order.getOrderid());
			pstmt.setString(2, order.getUserid());
			java.util.Date date = order.getOrderdate();
			// pstmt.setDate(3, new java.sql.Date(date.getTime()));
			pstmt.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			pstmt.setInt(4, order.getStatus());
			pstmt.setDouble(5, order.getAmount());
			// 5. 执行修改（C、U、D）
			int affectedRows = pstmt.executeUpdate();
			System.out.printf("成功插入%d条数据。\n", affectedRows);
		} catch (SQLException e) {
			return -1;
		}
		return 0;
	}

	@Override
	public int modify(Order order) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int remove(Order order) {
		// TODO 自动生成的方法存根
		return 0;
	}

}
