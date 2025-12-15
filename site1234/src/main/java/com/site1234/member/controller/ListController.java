package com.site1234.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.site1234.controller.Controller;
import com.site1234.member.repository.MemberDAO;

public class ListController implements Controller{
	MemberDAO memberDAO = new MemberDAO();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List list = memberDAO.selectAll();
		
		request.setAttribute("list", list);
	}

	@Override
	public String getViewName() {
		return "/member/list/result";
	}

	@Override
	public boolean isForward() {
		return true;
	}

}
