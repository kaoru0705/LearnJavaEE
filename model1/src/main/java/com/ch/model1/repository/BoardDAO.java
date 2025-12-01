package com.ch.model1.repository;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ch.model1.dto.Board;
import com.ch.model1.util.PoolManager;

// 데이터베이스의 Board table에 대한 CRUD를 수행하는 객체
public class BoardDAO {
	
	PoolManager pool = new PoolManager();
	
	public int insert(Board board) {	// 개발 시 파라미터 수가 많을 때는 낱개로 처리하지 않음,
													// 특히 데이터베이스 연동 로직에서는 DTO를 이용.
		// 이 메서드 호출 시마다, 접속을 일으키는 것이 아니라, Tomcat이 접속자가 없더라도
		// 미리 Connection들을 확보해 놓은 커넥션풀(Connection Pool)로부터 대여해보자
		// 또한 쿼리문 수행이 완료되더라도, 얻어온 Connection 절대로 닫지 말아야 한다. 반납해야 한다.
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			InitialContext context = new InitialContext();
			DataSource pool = (DataSource)context.lookup("java:comp/env/jndi/mysql");
			con = pool.getConnection();
			
			String sql = "insert into board(title, writer, content) values (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2,  board.getWriter());
			pstmt.setString(3,  board.getContent());
			
			result = pstmt.executeUpdate();		// 쿼리 수행
			
			/*
			 * 아래의 코드를 작성하면 안되는 이유? out을 쓰려고 하는 순간부터 BoardDAO의 중립성이 사라짐...
			 * 해결책, DAO는 디자인 영역과는 분리된 오직 DB만을 전담하므로, 절대로 디자인코드를 넣어서는 안됨!!
			 * 	따라서 디자인 처리는 이 메서드를 호출한 자가 처리하도록, 여기서는 결과만 반환하면 됨...
			 * */
//			if(result<1) {
//				out.println("실패");
//			}
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();		// 이건 닫음
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					// 주의 기존 JDBC코드는 다 사용한 커넥션을 닫았지만, 풀로부터 얻어온 커넥션은 닫으면 안 됨..
					// 이 객체는 DataSource 구현체로부터 얻어온 Connection이기 때문에 일반적 JDBC의 닫는 close()가 아님. 닫는 게 아니라 반납
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}		
				
			}
		}
		return result;
	}
	
	public List selectAll() {
		// 커넥션 얻는 코드를 이 메서드에서 손수하지 말자 PoolManager가 대신 해주무로...
		Connection con = pool.getConnection();		// 풀 매니저로부터 커넥션 객체를 얻어옴!!, 여기서 직접 검색하면 jndi 검색코드 중복되므로
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select board_id, title, writer, content, regdate, hit from board";
		
		List<Board> boardList = new ArrayList<Board>();
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// rs는 무조건 이 메서드에서 닫아야 하므로, 외부의 jsp는 디자인을 담당하는 코드이지, ResultSet의 존재를 알 필요도 없고,
			// 또한 ResultSet db연동기술이므로, 오직 DAO에서만 제어해야 한다.. 따라서 finally에서 rs를 닫는 것은 DAO의 의무이다!!
			// 모순 - rs를 닫아버린 상태에서 외부 객체에게 전달해주면 외부객체를 이 rs를 사용할 수 없다. close되어 있으므로,
			// 해결책?? rs가 죽어도 상관없는 비슷한 유형의 객체로 데이터를 표현하면 된다...
			// 		이 문제를 해결하기 위해 필요한 객체들의 조건
			//		1) 현실에 존재하는 사물을 표현할 수 있는 객체가 필요하다..(예 - 게시물 1건을 담을 수 있는 존재) - BoardDTO
			//		2) Board DTO로부터 생성된 게시물을 표현한 인스턴스들을 모아놓을 객체가 필요하다.(순서 O, 객체를 담을 수 있어야 함)
			//			이 조건을 만족하는 객체는? 자바의 컬렉션 프레임웍 중 List이다!!
			// collection framework이란? java.util에서 지원하는 라이브러리로서, 오직 객체만을 모아서 처리할 때 유용한 api
			
			
			while(rs.next()) {
				Board board = new Board();
				board.setBoardId(rs.getInt("board_id"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setRegdate(rs.getString("regdate"));
				board.setHit(rs.getInt("hit"));
				
				boardList.add(board);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();		// 이건 닫음
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();		// 이건 닫음
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					// 주의 기존 JDBC코드는 다 사용한 커넥션을 닫았지만, 풀로부터 얻어온 커넥션은 닫으면 안 됨..
					// 이 객체는 DataSource 구현체로부터 얻어온 Connection이기 때문에 일반적 JDBC의 닫는 close()가 아님. 닫는 게 아니라 반납
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}		
				
			}
		}
		
		return boardList;
	}
}