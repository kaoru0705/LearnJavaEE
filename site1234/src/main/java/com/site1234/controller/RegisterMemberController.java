package com.site1234.controller;

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
// 이거 디자인때문에 jsp로 만드는 게 맞다.
public class RegisterMemberController extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			out.print("드라이버 로드 성공<br>");
			
			String url = "jdbc:mysql://localhost:3306/myjava";
			String user = "myuser";
			String password = "1234";
			
			con = DriverManager.getConnection(url, user, password);
			if(con == null) {
				out.print("접속 실패<br>");
			} else {
				out.print("접속 성공<br>");
				
				String sql = "insert into member(email, password, nickname, phone)";
				sql+= " values(?, ?, ?, ?)";
				pstmt = con.prepareStatement(sql);
				
				String email = req.getParameter("email");
				String pwd = req.getParameter("password");
				String nickname = req.getParameter("nickname");
				String phone = req.getParameter("phone");
				
				pstmt.setString(1, email);
				pstmt.setString(2,  pwd);
				pstmt.setString(3, nickname);
				pstmt.setString(4,  phone);
				
				int result = pstmt.executeUpdate();
				
				if(result != 0) {
					out.print("가입 성공<br>");
					out.print("<button onClick=\"location.href='/upload/list.jsp'\">다음</button>");
				}else {
					out.print("가입 실패<br>");
				}
			}
			
		} catch (ClassNotFoundException e) {
			out.print("드라이버 로드 실패<br>");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
