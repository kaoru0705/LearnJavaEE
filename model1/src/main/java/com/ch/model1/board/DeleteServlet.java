package com.ch.model1.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.repository.BoardDAO;

// 삭제 요청
public class DeleteServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 파라미터 받기 (파라미터값에 한글이 포함되지 않았으므로, 굳이 request.setCharacterEncoding() 처리는 불필요!!
		String boardId = request.getParameter("board_id");
		
		BoardDAO boardDAO = new BoardDAO();
		int result = boardDAO.delete(Integer.parseInt(boardId));
		
		StringBuffer tag = new StringBuffer();
		
		tag.append("<script>");
		if(result < 1) {
			tag.append("alert('삭제 실패');");
			tag.append("history.back();");
		}else {
			tag.append("alert('삭제 성공');");
			tag.append("location.href='/board/list.jsp';");
		}
		tag.append("</script>");
		
		out.print(tag.toString());
	}
}
