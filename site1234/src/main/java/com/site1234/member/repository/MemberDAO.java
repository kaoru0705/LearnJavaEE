package com.site1234.member.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.site1234.member.domain.Member;

public class MemberDAO {
	public int signup(Member member) {
		int result = 0;	// insert 후 성공인지 실패인지를 판단할 수 있는 반환값
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myjava", "myuser", "1234");
			System.out.println(con);
			
			String sql = "insert into member(email, password, nickname, phone) values(?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2,  member.getPassword());
			pstmt.setString(3,  member.getNickname());
			
			String phone = member.getPhone() == "" ? null : member.getPhone();
			pstmt.setString(4, phone);
			
			result = pstmt.executeUpdate();		// DML 수행
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public List<Member> findAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Member> memberList = new ArrayList<>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myjava", "myuser", "1234");
			System.out.println(con);
			
			String sql = "select * from member";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Member member = new Member();
				member.setMemberId(rs.getInt("member_id"));
				member.setEmail(rs.getString("email"));
				member.setPassword(rs.getString("password"));
				member.setNickname(rs.getString("nickname"));
				member.setPhone(rs.getString("phone"));
				member.setCreatedAt(rs.getString("created_at"));
				
				memberList.add(member);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return memberList;	
	}
}
