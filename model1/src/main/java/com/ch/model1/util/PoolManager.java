package com.ch.model1.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

// DAO의 각 메서드마다 커넥션 풀로부터 커넥션을 얻어오는 코드를 중복 작성하 경우 유지보수성이 떨어짐...
// 예) JNDI 명칭이 바뀌거나, 연동할 db의 종륙 바뀌는 등.. 외부의 어떤 변화원인에 의해 코드가 영향을 많이 받으면 안 됨...
// 따라서 앞으로는 커넥션풀로부터 커넥션을 얻거나 반납하는 중복된 코드는 아래의 클래스로 처리하면 유지보수성이 올라감...


public class PoolManager {
	DataSource ds; 
	public PoolManager() {		 
		try {
			InitialContext context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jndi/mysql");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	// 외부의 DAO 들이 직접 Connection을 얻는 코드를 작성하게 하지 않으려면, 이 PoolManager 클래스에서
	// DAO 대신 Connection을 얻어와서 반환해주자
	
	public Connection getConnection() {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	// 빌려간 커넥션을 반납!! 오버로딩
	public void freeConnection(Connection con) {
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
	
	public void freeConnection(Connection con, PreparedStatement pstmt) {
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

	public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
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
}
