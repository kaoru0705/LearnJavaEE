package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 클라이언트가 전송한 파라미터들을 받아서, 오라클에 넣기!
// 클라이언트의 요청이 웹브라우저이므로, 즉 웹상의 요청을 받을 수 있고, 오직 서버에서만 실행될 수 있는 클래스인
// 서블릿으로 정의하자!
public class JoinController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");		// 브라우저에게 전송할 데이터가 html 문서임을 알려줌
		response.setCharacterEncoding("utf-8");		// 이 html이 지원하는 인코딩 타입을 지정
		PrintWriter out = response.getWriter();		// 응답 객체가 보유한 출력스트림 얻기
		
		// 주의할 점 아래의 코드에 의해, 클라이언트의 브라우저에 곧바로 데이터가 전송되는 게 아니라,
		// 추후 응답을 마무리하는 시점에 Tomcat과 같은 컨테이너 서버가, out.print()에 의해 누적되어 있는
		// 문자열을 이용하여, 새로운 html 문서를 작성할 때 사용됨..
		out.print("<h1>강동훈</h1>");
		
		// JDBC를 오라클에 insert
		// 드라이버가 있어야 오라클을 제어할 수 있다. 따라서 드라이버 jar 파일을 클래스패스에 등록하자
		// 하지만, 현재 사용중인 IDE가 이클립스라면, 굳이 환경변수까지 등록할 필요 없고, 이클립스에 등록하면 된다.
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			out.print("드라이버 로드 성공");
			
			// 오라클에 접속
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "servlet";
			String password = "1234";
			
			// 접속 후, 접속이 성공했는지 알기 위해서는, Connection 인터페이스가 null인지 여부를 판단한다.
			// Connection 은 접속 성공 후 그 정보를 가진 객체이므로, 추후 그 접속을 끊을 수도 있다..
			con = DriverManager.getConnection(url, user, password);
			if(con == null) {
				out.print("접속실패");
			} else {
				out.print("접속성공");
			}
			
			// 쿼리수행
			// JDBC는 데이터베이스 제품의 종류가 무엇이든 상관없이 DB를 제어할 수 있는 코드가 동일함..(일관성 유지 가능)
			// 가능한 이유? 사실 JDBC 드라이버를 제작하는 주체는 벤더사이기 때문에... 모든 벤더사는 java언어를 제작한 오라클사에서 제시한
			// JDBD 기준 스펙을 따르기 때문에 가능하다..
			// 오라클 사는 javaEE에 대한 스펙만을 명시하고, 실제 서버는 개발하지 않는다. 결국 javaEE 스펙에 따라 서버를 개발하는 벤더사들
			// 모두가 각자 고유의 기술로 서버는 개발하지만, 반드시 javaEE에서 명시된 객체명을 즉 api를 유지해야 하므로, java 개발자들은
			// 어떠한 종류의 서버이던 상관없이 그 코드가 언제나 유지됨.
			
			request.setCharacterEncoding("utf-8");
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			String sql = "insert into member(member_id, id, pwd, name, email)";
			sql += " values(seq_member.nextval, ?, ?, ?, ?)";		// 바인드 변수
			pstmt = con.prepareStatement(sql);
			
			// 바인드 변수를 사용하게 되면, 물음표의 값이 무엇인지 개발자가 PreparedStatement에게 알려줘야함
			// 클라이언트가 전송한 파라미터 받기 
			pstmt.setString(1, id);
			pstmt.setString(1, pwd);
			pstmt.setString(1, name);
			pstmt.setString(1, email);
			
		} catch (ClassNotFoundException e) {
			out.print("드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
}