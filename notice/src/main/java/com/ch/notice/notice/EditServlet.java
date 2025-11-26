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

public class EditServlet extends HttpServlet {
	@Override
	// 클라이언트의 수정 요청을 처리하는 서블릿
	// 수정 내용폼의 데이터가 규모가 크기 때문에 Http 전송 메서드 중 POST로 요청이 들어올 것임!!
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	// tomcat이 html 작성 시 사용할 내용을 담을 문자기반 시스템
		// 전송되는 데이터에 대한 인코딩 처리
		request.setCharacterEncoding("utf-8");
		String url = "jdbc:mysql://localhost:3306/java";
		String user = "servlet";
		String password = "1234";

		Connection con = null;
		PreparedStatement pstmt = null;
		
		

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);

			if (con != null) {
				// 클라이언트가 전송한 파라미터들을 이용하여 쿼리문 수행
				// DML 중 수정 SQL - update notice set title=넘겨받은 파라미터, writer=파라미터, content= parameter
				
				String noticeId = request.getParameter("notice_id");
				String title = request.getParameter("title");
				String writer = request.getParameter("writer");
				String content = request.getParameter("content");
				
				String sql = "update notice set title=?, writer = ?, content = ? where notice_id = ?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, title);
				pstmt.setString(2, writer);
				pstmt.setString(3, content);
				pstmt.setInt(4, Integer.valueOf(noticeId));

				int result = pstmt.executeUpdate();
				
				out.print("<script>");
				if (result < 1) {
					out.print("alert('업데이트 실패');");
					out.print("history.back();");
				} else {
					out.print("alert('업데이트 성공');");
					
					// 주의) detail.jsp는 반드시 notice_id 값을 필요로 하므로, 링크 사용 시 /notice/detail.jsp 만 적으면 에러남.
					out.print("location.href='/notice/detail.jsp?notice_id="+noticeId+"';");
				}
				out.print("</script>");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
