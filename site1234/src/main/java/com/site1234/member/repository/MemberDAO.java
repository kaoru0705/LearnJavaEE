package com.site1234.member.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.site1234.member.domain.Member;
import com.site1234.util.PoolManager;

public class MemberDAO {
	PoolManager pool = PoolManager.getInstance();
	
	public int signup(Member member) {
		int result = 0; // insert 후 성공인지 실패인지를 판단할 수 있는 반환값
		Connection con = pool.getConnection();
		PreparedStatement pstmt = null;
		
		String sql = "insert into member(email, password, nickname, phone) values(?, ?, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getNickname());

			String phone = member.getPhone() == "" ? null : member.getPhone();
			pstmt.setString(4, phone);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return result;
	}

	public List<Member> findAll() {
		Connection con = pool.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Member> memberList = new ArrayList<>();
		
		String sql = "select * from member";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Member member = new Member();
				member.setMemberId(rs.getInt("member_id"));
				member.setEmail(rs.getString("email"));
				member.setPassword(rs.getString("password"));
				member.setNickname(rs.getString("nickname"));
				member.setPhone(rs.getString("phone"));
				member.setCreatedAt(rs.getString("created_at"));

				memberList.add(member);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return memberList;
	}
}
