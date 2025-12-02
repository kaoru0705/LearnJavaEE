package com.ch.model1.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 수정 요청을 처리하는 서블릿
public class EditServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 파라미터 4개를 넘겨받아 쿼리를 직접 실행하지 말고, DAO에게 시키자 */
		response.setContentType("text/html; charset=UTF-8");		// 응답 페이지에 대한 인코딩
		request.setCharacterEncoding("UTF-8");		// 파라미터 값에 대한 인코딩
		
		String boardId = request.getParameter("board_id");
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		System.out.println(boardId);	// tomcat의 콘솔에 출력(우리의 경우 내부 톰켓이므로 이클립스 콘솔에 출력)
		System.out.println(title);
		System.out.println(writer);
		System.out.println(content);
		
	}
}
