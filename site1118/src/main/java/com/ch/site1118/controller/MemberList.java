package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*오라클에 들어있는 회원의 목록을 가져와서 화면에 출력*/
public class MemberList extends HttpServlet{
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "servlet";
	String pass = "1234";
	
	// 클라이언트인 브라우저가 목록을 달라고 요청할 것이기 때문에, doXXX형 메서드 중 doGet()을 재정의하자
	// 클라이언트가 목록을 원하기 때문에...
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");	// 이렇게 세미콜론으로 연결하면 아래 메서드 작성불필요
		// response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		Connection con = null; // 접속 후 그 정보를 가진 객체, 따라서 이 객체가 null 인 경우 접속은 실패
		PreparedStatement pstmt = null;	// 쿼리문 수행 객체, 오직 Connection 객체로부터 인스턴스 얻음
														// 쿼리문이란, 접속을 전제로 하기 때문..
		ResultSet rs = null; 		// select문의 결과인 표를 가진 객체
		// shift f2 도움말 단축키
		try {
			// 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");	// 드라이버 로드
			out.print("드라이버 로드 성공");
			
			// 오라클에 접속
			con = DriverManager.getConnection(url, user, pass);
			if(con == null) {
				out.print("접속 실패<br>");
			}else {
				out.print("접속 성공<br>");
				
				String sql= "select * from member order by member_id asc"; // 오름차순
				pstmt = con.prepareStatement(sql);		// 쿼리 수행 객체 생성
				
				// DML인 경우 executeUpdate()였지만, select문 인 경우 원격지 서버의 레코드(표)를 네트워크로
				// 가져와야 하므로, 그 표 결과를 그대로 반영할 객체가 필요한데, 이 객체를 가리켜 ResultSet이라 한다.
				
				rs = pstmt.executeQuery();
				
				// rs를 그냥 표 그자체로 생각해도 무방.. 하지만, rs 내에 존재하는 레코드들을 접근하기 위해서는
				// 레코드를 가리키는 포인터 역할을 해주는 커서를 제어해야 한다.. 이 커서는 rs가 생성되자 마자
				// 즉 생성 즉시엔 어떠한 레코드도 가리키지 않은 상태이므로, 개발자가 첫 번째 레코드로 접근하려면 포인터를
				// 한칸 내려야 한다.
				
			}
			
		} catch (ClassNotFoundException e) {
			out.print("드라이버 로드 실패<br>");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
