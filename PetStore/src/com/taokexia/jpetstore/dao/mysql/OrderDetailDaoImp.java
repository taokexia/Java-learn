package com.taokexia.jpetstore.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.taokexia.jpetstore.dao.OrderDetailDao;
import com.taokexia.jpetstore.domain.OrderDetail;

public class OrderDetailDaoImp implements OrderDetailDao {

	@Override
	public List<OrderDetail> findAll() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public OrderDetail findByPK(int orderid, String productid) {
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        OrderDetail orderDetail = null;
        try {
            // 2.创建数据库连接
            conn = DBHelper.getConnection();
            // 3. 创建语句对象
            String sql = "select orderid,productid,quantity,unitprice "
                + "from ordersdetail where orderid = ? and productid = ?";
            pstmt = conn.prepareStatement(sql);
            // 4. 绑定参数
            pstmt.setInt(1, orderid);
            pstmt.setString(2, productid);
            // 5. 执行查询（R）
            rs = pstmt.executeQuery();
            // 6. 遍历结果集
            if (rs.next()) {
                orderDetail = new OrderDetail();
                orderDetail.setOrderid(rs.getInt("orderid"));
                orderDetail.setProductid(rs.getString("productid"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setUnitcost(rs.getDouble("unitcost"));
                return orderDetail;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally { // 释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return null;
	}

	@Override
	public int create(OrderDetail orderDetail) {
		try ( 
			// 2.创建数据库连接
            Connection conn = DBHelper.getConnection();
            // 3. 创建语句对象
            PreparedStatement pstmt = conn
            .prepareStatement("insert into ordersdetail "
                              + "(orderid, productid,quantity,unitcost) values (?,?,?,?)")) {
            // 4. 绑定参数
            pstmt.setLong(1, orderDetail.getOrderid());
            pstmt.setString(2, orderDetail.getProductid());
            pstmt.setInt(3, orderDetail.getQuantity());
            pstmt.setDouble(4, orderDetail.getUnitcost());
            // 5. 执行修改（C、U、D）
            int affectedRows = pstmt.executeUpdate();
            System.out.printf("成功插入%d条数据。\n", affectedRows);
        } catch (SQLException e) {
            return -1;
        }
        return 0;
	}

	@Override
	public int modify(OrderDetail orderDetail) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int remove(OrderDetail orderDetail) {
		// TODO 自动生成的方法存根
		return 0;
	}

}
