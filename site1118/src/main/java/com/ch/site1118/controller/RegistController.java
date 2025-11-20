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

import com.mysql.cj.xdevapi.Result;

// 회원 등록 요청을 처리할 서블릿 클래스
// HTTP 요청 방식 중, 클라이언트가 서버로 데이터를 전송해오는 방식은 POST 방식임
// 따라서 서블릿이 보유한 doXXX형 메서드 중 doPost를 재정의해야 함

public class RegistController extends HttpServlet{

	// post 요청을 처리하는 메서드!!
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("클라이언트의 post 요청 감지");		// 웹브라우저가 아닌 현재 톰캣의 콘솔에 출력
		// 클라이언트가 전송한 id, pwd, name을 받아서 출력해보기
		request.setCharacterEncoding("utf-8");		// 파라미터 받기 전에 세팅해야 함...
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		
		System.out.println("전송 받은 아이디는 "+ id);
		System.out.println("전송 받은 패스워드는 "+ pwd);
		System.out.println("전송 받은 이름은 "+ name);
		
		// 응답객체가 보유한 문자기반의 출력스트림에 개발자가 유저에게 전달하고 싶은 메시지를 보관하자
		response.setContentType("text/html;");		// 브라우저에게 이 문서의 형식이 html 임을 알린다.
		response.setCharacterEncoding("utf-8");		// 이 html에서 사용할 문자열에 대해 전세계 모든 언어를
																	// 깨지지 않도록, UTF-8;
		PrintWriter out = response.getWriter();
		
		// mysql에 넣어주기
		
		// java 언어가 해당 데이터베이스 서버를 제어하려면, 접속에 앞서 최우선으로 해당 DB 제품을 핸들링할 수 있는
		// 라이브러리인 일명 드라이버를 보유하고 있어야 한다..(jar 형태)
		// 보통 드라이버는 java가 자체적으로 보유할 수 없다..(java 입장에서는 어떠한 db가 존재하는지 알 수 없다.)
		// 따라서 드라이버 제작의 의무는 db제품을 판매한는 벤더사에게 있다.
		
		// JDBC 드라이버는 Class.forName() 메소드를 사용하여 동적 바인딩 방식으로 로드된다.
		// 이때 매개변수로 해당 JDBC 드라이버의 이름을 입력해야 한다.
		// jvm의 3가지 메모리 영역 중 Method 영역에 동적으로 클래스를 Load 시킴
		// 보통은 jvm 자동으로 로드해주지만, 개발자가 원하는 시점에 원하는 클래스를 로드시킬 경우 아래와 같은
		// Class 클래스가 static method인 forName() 메서드를 사용하기도 한다.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}
		
		// mysql 접속!!
		// 자바에서 데이터베이스를 다루는 기술을 가리켜 JDBC(Java Database Connectivity)라 한다.
		// 이 기술은 javaSE의 java.sql 패키지에서 주로 지원함
		// 현재 우리가 개발중인 분야가 javaEE라면 javaEE는 이미 javaSE를 포함하고 있다.
		
		// jdbc:mysql://서버주소:포트번호/DB이름
		String url = "jdbc:mysql://localhost:3306/java";
		String user = "servlet";
		String pass = "1234";
		
		// try catch block shift alt z
		Connection con = null;		// finally에서 보이도록
		PreparedStatement pstmt = null;		// finally에서 보이도록
		try {
			// getConnection() 매개변수로는 url, user, pass를 각각 설정한다.
			// getConnection() 메소드는 DB서버와 연결하고 연결 정보를 Connection 객체에 저장하여 반환
			con = DriverManager.getConnection(url, user, pass);
			// Connection이란? 접속 성공 후 그 정보를 가진 객체이므로, 접속을 끊고 싶은 경우 이 객체를 이용하면 됨
			// 예) con.close() 접속해제
			// 주의) jdbc에서 데이터베이스에 접속 성공 여부를 판단할 때는 절대로 try문이 성공, catch문이 실패라고 생각하면 안 됨
			// getConnection() 메서드가 반환해주는 Connection 인터페이스가 null인지 여부로 판단해야 한다.
			if(con==null) {
				System.out.println("접속 실패");
			} else {
				System.out.println("접속 성공");
				
				// insert 문 수행
				// JDBC 객체 중 쿼리수행을 담당하는 객체가 바로 PreparedStatement 인터페이스이다!
				// 그리고 이 객체는 접속을 성공을 해야, 얻을 수 있다.(당연 이치=접속해야 쿼리문 수행할 수 있으니)
				// PreparedStatement 객체(여기서 pstmt)는 SQL문을 미리 컴파일하여 실행하기 전의 상태로 만들어 둔 후 실제 실행은 나중에 필요에 따라 여러 번 실행시킬 수 있다.
				// 또한 PreparedStatement 객체로 생성되는 SQL문은 마치 함수처럼 매개변수를 설정하여 필요에 따라 매개변수의 값을 바꿔 실행되도록 할 수 있다.
				pstmt=con.prepareStatement("insert into member(id, pwd, name) values('"+ id +"', '"+pwd+"', '"+name+"')");		// 쿼리문 준비
				
				// 준비된 쿼리문을 실행하자
				int result = pstmt.executeUpdate();	// DML, 메서드 실행 후 반환되는 값은 이 메서드에 의해 영향을 받은 레코드 수가 반환됨..
				// 따라서 1보다 작은 수가 반환되면, 이 쿼리에 이해 영향을 받은 레코드가 없으므로 수행 실패
				if(result<1) {
					System.out.println("등록 실패");
					out.print("<script>"); 
					out.print("alert('등록실패');");
					out.print("</script>");
				} else {
					System.out.println("등록 성공");
					// 웹브라우저에 성공 메시지 출력하기
					out.print("<script>"); 
					out.print("alert('등록성공');");
					out.print("</script>");
				}
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		}finally {
			if(pstmt != null) {		// 존재할 때만 닫음
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
}
