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

public class MessageController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String name = req.getParameter("name");
		String contactNumber = req.getParameter("contactNumber");
		String message = req.getParameter("message");
		
		System.out.println("이름: "+name);
		System.out.println("상대 연락처: " + contactNumber);
		System.out.println("내용: " + message);
		
		resp.setContentType("text/html;");
		resp.setCharacterEncoding("utf-8");
		
		PrintWriter out = resp.getWriter();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}
		
		String url = "jdbc:mysql://localhost:3306/java";
		String user = "servlet";
		String pass = "1234";
				Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, user, pass);
			
			if(con == null) {
				System.out.println("접속 실패");
			}else {
				System.out.println("접속 성공");
			}
			
			pstmt = con.prepareStatement("insert into message(name, contactNumber, message) values ('"+name+"', '"+contactNumber+"', '"+message+"')");
			
			int result = pstmt.executeUpdate();
			
			if(result < 1) {
				System.out.println("등록 실패");
				out.print("<script>");
				out.print("alert('등록 실패');");
				out.print("</script>");
			}else {
				System.out.println("등록 성공");
				out.print("<script>");
				out.print("alert('등록 성공');");
				out.print("</script>");
			}
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
// ctrl + m 코드 창만 크게