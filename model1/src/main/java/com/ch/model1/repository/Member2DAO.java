package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ch.model1.util.PoolManager;

/* 이 클래스는 오직 데이터베이스 관련된 로직만 담당하는 DAO 클래스임 */
public class Member2DAO {
	PoolManager pool = new PoolManager();
	
	// insert - 레코드 1건
	public void insert() {
		Connection con = pool.getConnection();
		PreparedStatement pstmt = null;
		try {
			String sql = "insert into member2(id, name, email) values(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}

	}
}
