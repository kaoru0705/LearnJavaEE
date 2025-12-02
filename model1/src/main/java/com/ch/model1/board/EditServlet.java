package com.ch.model1.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.Board;
import com.ch.model1.repository.BoardDAO;

// 수정 요청을 처리하는 서블릿
public class EditServlet extends HttpServlet{
	
	BoardDAO boardDAO = new BoardDAO();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 파라미터 4개를 넘겨받아 쿼리를 직접 실행하지 말고, DAO에게 시키자 */
		response.setContentType("text/html; charset=UTF-8");		// 응답 페이지에 대한 인코딩
		request.setCharacterEncoding("UTF-8");		// 파라미터 값에 대한 인코딩
		
		PrintWriter out = response.getWriter();
		
		String boardId = request.getParameter("board_id");
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		Board board = new Board();
		board.setBoardId(Integer.parseInt(boardId));
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);	

		int result = boardDAO.update(board);
		
		out.print("<script>");
		if(result < 1) {
			out.print("alert('실패');");
			out.print("history.back();");
		} else {
			out.print("alert('성공');");
			out.print("location.href='/board/detail.jsp?board_id=" +boardId+ "';");
		}
		out.print("</script>");
	}
}
