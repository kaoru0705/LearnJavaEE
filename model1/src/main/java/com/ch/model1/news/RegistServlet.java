package com.ch.model1.news;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.News;
import com.ch.model1.repository.NewsDAO;

/* 뉴스 기사 등록 요청을 처리할 서블릿 */
public class RegistServlet extends HttpServlet{
	NewsDAO newsDAO = new NewsDAO();
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // 클라이언트가 동기방식으로 전송한 파라미터를 받아서 데이터베이스(DAO 이용하여 간접적으로 = 시키자)에 넣자
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		PrintWriter out = response.getWriter();
		
		News news = new News();
		
		news.setTitle(title);
		news.setWriter(writer);
		news.setContent(content);
		
		int result = newsDAO.insert(news);
		
		// 클라이언트가 동기 방식의 요청을 했기 때문에 서버는 화면전환을 염두해 두고,
		// 순수 데이터보다는 페이지 전환 처리가 요구됨..
		// 글 등록 후, 클라이언트의 브라우저로 하여금 다시 목록 페이지를 재요청하도록 만들자!!
		
		// response.sendRedirect("");
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<script>");
		if(result > 0) {
			sb.append("alert('성공');");
			sb.append("location.href='/news/list.jsp';");
		} else {
			sb.append("alert('실패');");
			sb.append("history.back();");
		}
		sb.append("</script>");
		
		out.print(sb.toString());
	}
}
