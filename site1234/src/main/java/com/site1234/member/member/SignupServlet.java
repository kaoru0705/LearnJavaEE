package com.site1234.member.member;

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

import com.site1234.member.dto.Member;
import com.site1234.member.repository.MemberDAO;

public class SignupServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		request.setCharacterEncoding("utf-8");

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		String phone = request.getParameter("phone");

		Member member = new Member();

		member.setEmail(email);
		member.setPassword(password);
		member.setNickname(nickname);
		member.setPhone(phone);

		MemberDAO memberDAO = new MemberDAO();

		int result = memberDAO.signup(member);

		StringBuffer tag = new StringBuffer();
		tag.append("<script>");
		
		if (result < 1) {
			tag.append("alert('가입 실패');");
			tag.append("history.back();");
		} else {
			tag.append("alert('가입 성공');");
			tag.append("location.href='/member/upload/list.jsp'");
		}
		tag.append("</script>");
		
		out.print(tag.toString());
	}
}
