package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ch.model1.dto.Member2;
import com.ch.model1.util.PoolManager;

/* 이 클래스는 오직 데이터베이스 관련된 로직만 담당하는 DAO 클래스임 */
public class Member2DAO {
	PoolManager pool = PoolManager.getInstance();
	
	// insert - 레코드 1건
	public int insert(Member2 member2) {
		Connection con = pool.getConnection();		// 커넥션 풀로부터 하나 대여
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "insert into member2(id, name, email) values(?, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member2.getId());
			pstmt.setString(2,  member2.getName());
			pstmt.setString(3, member2.getEmail());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return result;
	}
	
	// 모든 레코드 가져오기
	public List<Member2> selectAll() {
		
		Connection con = pool.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member2> list = new ArrayList<Member2>();
		
		String sql = "select * from member2 order by member2_id asc";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Member2 member2 = new Member2();
				member2.setMember2Id(rs.getInt("member2_id"));
				member2.setId(rs.getString("id"));
				member2.setName(rs.getString("name"));
				member2.setEmail(rs.getString("email"));
				
				list.add(member2);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return list;
	}
}
