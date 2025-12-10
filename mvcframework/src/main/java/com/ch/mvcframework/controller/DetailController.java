package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.repository.BoardDAO;

// 게시물 1건 요청을 처리하는 하위 컨트롤러
public class DetailController implements Controller{
	BoardDAO boardDAO = new BoardDAO();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 3단계: 알맞는 로직 객체에 일 시킨다...
		String board_id = request.getParameter("board_id");
		Board board = boardDAO.select(Integer.parseInt(board_id));
		
		System.out.println("게시물이 들어 있는 DTO " + board);
		
		// board를 결과 페이지인 (MVC 중 View)까지 살려서 가져가려면,
		// request에 담고
		// 포워딩해야 한다..
		
		request.setAttribute("board", board);
	}

	@Override
	public String getViewName() {
		return "/board/detail/result";	// /board/detail.jap를 직접 적으면 안됨!!
	}

	@Override
	public boolean isForward() {
		return true;
	}

}
