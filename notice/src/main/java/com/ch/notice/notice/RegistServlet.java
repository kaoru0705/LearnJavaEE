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

import com.ch.notice.domain.Notice;
import com.ch.notice.repository.NoticeDAO;

// html로부터 글쓰기 요청을 받는 서블릿 정의
// jsp는 사실 서블릿이므로, 현재 이 서블릿의 역할을 대신할 수도 있다.
// 하지만, jsp 자체가 서블릿의 디자인 능력을 보완하기 위해 나온 기술이므로,
// 현재 이 서블릿에서는 디자인이 필요 없기 때문에, 굳이 jsp를 사용할 필요가 없다.
public class RegistServlet extends HttpServlet{
	
	NoticeDAO noticeDAO = new NoticeDAO();		// 다른 로직은 포함되어 있지 않고, 오직 DB와 관련 CRUD만을 담당하는 중립적 객체를 사용하보자
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("요청 감지");	// 서버의 톰캣 콘솔에 출력
		
		// 아래의 두 줄을 jsp로 구현할 경우 page ContentType = "text/html; charset=utf-8" 에 해당한다.
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");		// tomcat이 응답정보를 이용하여 html화 시킬 때 한글에 대한 처리
		request.setCharacterEncoding("utf-8");
		
		// 클라이언트가 전송한 파라미터를 받자
		
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		PrintWriter out = response.getWriter();
		
		out.print("클라이언트가 전송한 제목은 " + title + "<br>");
		out.print("클라이언트가 전송한 작성자는 " + writer + "<br>");
		out.print("클라이언트가 전송한 내용은 " + content + "<br>");
		
		// mysql의 java db 안에 notice에 insert !!!
		// 앞으로 필요한 라이브러리(jar)가 있을 경우, 일일이 개발자가 손수 다운로드 받아
		// WEB-INF/lib에 옮기지 말고, maven 빌드툴을 이용하자!!
		// Build - 실행할 수 있는 상태로 구축하는 것을 말함
		// 				src/animal.Dog.java 작성 후, bin/animal.Dog.class에 대해
		//				직접 javac -d 경로 대상클래스
		
		Notice notice = new Notice();
		
		notice.setTitle(title);
		notice.setWriter(writer);
		notice.setContent(content);
		
		int result = noticeDAO.regist(notice);
		
		out.print("<script>");
		if(result < 1) {
			out.print("alert('등록실패');");
			out.print("history.back();");
		}else {
			out.print("alert('등록성공');");
			out.print("location.href='/notice/list.jsp'");
		}
		out.print("</script>");
	}
}
