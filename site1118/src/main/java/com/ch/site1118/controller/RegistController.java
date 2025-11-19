package com.ch.site1118.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		// mysql에 넣어주기
		
		// java 언어가 해당 데이터베이스 서버를 제어하려면, 접속에 앞서 최우선으로 해당 DB 제품을 핸들링할 수 있는
		// 라이브러리인 일명 드라이버를 보유하고 있어야 한다..(jar 형태)
		// 보통 드라이버는 java가 자체적으로 보유할 수 없다..(java 입장에서는 어떠한 db가 존재하는지 알 수 없다.)
		// 따라서 드라이버 제작의 의무는 db제품을 판매한는 벤더사에게 있다.
		
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
		
		String url = "jdbc:mysql://localhost:3306/";
		String user = "servlet";
		String pass = "1234";
		
		// try catch block shift alt z
		try {
			Connection con = DriverManager.getConnection(url, user, pass);
			// Connection이란? 접속 성공 후 그 정보를 가진 객체이므로, 접속을 끊고 싶은 경우 이 객체를 이용하면 됨
			// 예) con.close() 접속해제
			// 주의) jdbc에서 데이터베이스에 접속 성공 여부를 판단할 때는 절대로 try문이 성공, catch문이 실패라고 생각하면 안 됨
			// getConnection() 메서드가 반환해주는 Connection 인터페이스가 null인지 여부로 판단해야 한다.
			if(con==null) {
				System.out.println("접속 실패");
			} else {
				System.out.println("접속 성공");
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
}
