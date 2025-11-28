package com.ch.notice.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ch.notice.domain.Notice;

/*
 * 이 클래스의 목적은?
 * javaEE 기반의 애플리케이션이든, javaSE 기반의 애플리케이션 데이터베이스를 연동하는 비즈니스 로직은 동일하다.
 * 따라서 유지보수성을 고려하여 여러 플랫폼에서 재사용할 수 있는 객체를 정의
 * 특히 로직 객체 중 오직 데이터베이스 연동을 전담하는 역할을 하는 객체를 가리켜 애플리케이션 설계 분야에서는
 * DAO (Data Access Object) - DB에 테이블이 만일 5개라면 DAO도 1:1 대응하여 5개를 만들어야 한다.
 * 특히 수행하는 데이터베이스의 테이블에 데이터를 처리하는 업무를 가리켜 Create(=insert) Read(select) Update Delete 
 * DTO(Data Transfer Object) - 오직 데이터만을 보유한 전달객체를 의미 따라서, 로직은 없다. (Dummy Object)
 * 
 */
public class NoticeDAO {
	// 게시물 등록!!
	public int regist(Notice notice) {
		int result = 0;	// insert 후 성공인지 실패인지를 판단할 수 있는 반환값
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "servlet", "1234");
			System.out.println(con);
			
			String sql = "insert into notice(title, writer, content) values(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, notice.getTitle());
			pstmt.setString(2,  notice.getWriter());
			pstmt.setString(3,  notice.getContent());
			
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

}
