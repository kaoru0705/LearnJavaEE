package com.ch.memberapp.member;

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
import javax.servlet.http.HttpSession;

import com.ch.memberapp.util.ShaManager;

public class LoginServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 유저가 전송한 id, pwd 파라미터 값을 이용하여 db와 비교할 예정
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		String url = "jdbc:mysql://localhost:3306/java";
		String user= "servlet";
		String password = "1234";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			
			
			if(con == null) {
				System.out.println("접속실패");
			}else {
				System.out.println("접속성공");
				
				String sql = "select * from member where id = ? and pwd = ?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, id);
				pstmt.setString(2, ShaManager.getHash(pwd));
				
				rs = pstmt.executeQuery();
				StringBuffer tag = new StringBuffer();
				tag.append("<script>");

				if(rs.next()) {		// rs의 커서를 next() 했을 때 true를 반환하면 레코드가 존재한다는 뜻이므로, 이 회원은 로그인 성공으로 인정 
					tag.append("alert('로그인 성공');");
					tag.append("location.href='/';");		// 클라이언트 브라우저가 루트 페이지로 재접속하게 만듦 루트 페이지로 재접속
					
					// 로그인을 성공한 회원의 경우, 브라우저를 끄지 않는 한 계속 기억 효과를 내야 하므로,
					// 서버의 메모리에 회원정보를 저장할 수 있는 객체를 올려야 함, 이러한 목적의 객체를 가리켜 Session 객체라 한다.
					// 생성된 세션 객체에는 자동으로 고유값이 할당되어지는데, 이를 가리켜 session ID라 한다.
					// 지금 우리의 경우 로그인 성공 이후, 회원에게 회원정보를 기억한 효과를 내려면 회원 정보를 Session에 담아두면 된다.
					// 그리고 담아진 정보는 사용자가 브라우저를 닫기 전까지는 계속 사용할 수 있음
					// (예외 - 서버에서 정해놓은 시간 동안 재요청이 없을 경우 자동으로 세션을 소멸시켜 버림)
					
					// tomcat이 관리하므로 개발자가 직접 new 할 수 없는 인터페이스이다. 즉, 시스템으로부터 얻어오자
					// 주의할점 - 세션은 브라우저가 들어올 때 무조건 생성되는 것이 아니라, 개발자가 아래의 세션을 건드리는 코드가 실행될 때
					// 메모리에 올라옴? 아니다 --> 왜? 로그인을 의도하지 않은 브라우저의 요청마저 세션을 만들 필요는 없기 때문
					HttpSession session = request.getSession();	
					
					String sessionId = session.getId();	// 현재 생성된 세션에 자동으로 발급된 고유값
					System.out.println("이 요청에 의해 생성된 세션의 id는 " + sessionId);
					
					Member member = new Member();
					
					member.setMemberId(rs.getInt("member_id"));
					member.setId(rs.getString("id"));
					member.setPwd(rs.getString("pwd"));
					member.setName(rs.getString("name"));
					member.setRegdate(rs.getString("regdate"));
					
					// 회원 1명에 대한 정보가 채워진 DTO의 인스턴스를 세션에 담아두자(브라우저를 끌 때까지는 회원정보를 계속 보여줄 수 있다.)
					// HttpSession은 Map을 상속받음, 따라서 Map 형이다.
					// Map은 자바의 컬렉션 프레임웍이다.(자료구조) 컬렉션 프레임웍의 목적은? 다수의 데이터 중 오직 객체만을 대상으로
					// 효율적으로 데이터를 처리하기 위해 지원되는 자바의 라이브러리, java.util 패키지에서 지원...
					// 1) 순서 있는 객체를 다룰 때 사용되는 자료형(배열과 흡사) List
					// 2) 순서가 없는 객체를 다룰 때는 Set
					// 3) 순서가 없는 객체 중 특히 key-value의 쌍을 갖는 데이터 조합 - Map
					// 오전에 사용했던 js의 객체표기법 자체가 사실은 Map으로 구성됨
					session.setAttribute("member", member);
					
					
				}else {
					tag.append("alert('로그인 실패');");
					tag.append("history.back();");
				}
				
				tag.append("</script>");
				
				out.print(tag.toString());
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
}
