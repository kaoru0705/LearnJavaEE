package com.ch.notice.notice;

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

/*
 * 글 한 건 삭제 요청을 처리하는 서블릿 정의
 * pk값은 내용이 길지 않으며, 보안상 중요하지도 않기 때문에 get 방식으로 받자
 *
 */
public class DeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//jsp에서의 page 지시영역과 동일한 효과를 주기 위한 코드
		response.setContentType("text/html; charset=utf-8"); // MIME 타입(브라우저가 이해하는 형식을 작성해야 함)
																					// image/jpg, application/json
		PrintWriter out = response.getWriter();		// 고양이가 응답 시 참고할 문자열을 모아놓을 스트림
		
		// 클라이언트가 요청을 시도하면서 함께 지참해온, notice_id 파라미터 값을 받자
		String noticeId = request.getParameter("notice_id");
		System.out.println("넘겨받은 notice_id는 " + noticeId);
		
		String url = "jdbc:mysql://localhost:3306/java";
		String user = "servlet";
		String password = "1234";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			
			if(con != null) {
				pstmt = con.prepareStatement("delete from notice where notice_id = " + noticeId);
				
				int result = pstmt.executeUpdate();
				out.print("<script>");
				if(result < 1) {
					// out.print()로 js를 출력할 때는 반드시 종료 ; 찍어야 함
					out.print("alert('삭제 실패');");
					out.print("history.back();");	// 웹 브라우저의 뒤로가기 버튼을 누른 효과가 남
				}else {
					out.print("alert('삭제 성공');");
					out.print("location.href='/notice/list.jsp';");
				}
				out.print("</script>");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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
	}
}
