package com.taokexia.jpetstore.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.taokexia.jpetstore.dao.AccountDao;
import com.taokexia.jpetstore.domain.Account;

public class AccountDaoImp implements AccountDao {

	@Override
	public List<Account> findAll() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public Account findById(String userid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Account account = null;
		try {
			// 2. 创建数据库连接
			conn = DBHelper.getConnection();
			// 3. 创建语句对象
			String sql = "select userid,password,email,name,addr,city,country,phone"
							+ " from account where userid = ?";
			pstmt = conn.prepareStatement(sql);
			// 4. 绑定参数
			pstmt.setString(1, userid);
			// 5. 执行查询（R）
			rs = pstmt.executeQuery();
			// 6. 遍历结果集
			if (rs.next()) {
				account = new Account();
				account.setUserid(rs.getString("userid"));
				account.setPassword(rs.getString("password"));
				account.setEmail(rs.getString("email"));
				account.setUsername(rs.getString("name"));
				account.setAddr(rs.getString("addr"));
				account.setUserid(rs.getString("userid"));
				account.setCity(rs.getString("city"));
				account.setCountry(rs.getString("country"));
				account.setPhone(rs.getString("phone"));
				return account;
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
	public int create(Account account) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int modify(Account account) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int remove(Account account) {
		// TODO 自动生成的方法存根
		return 0;
	}

}
