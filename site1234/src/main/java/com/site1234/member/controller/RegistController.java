package com.site1234.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.site1234.controller.Controller;
import com.site1234.member.dto.Member;
import com.site1234.member.model.MemService;

public class RegistController implements Controller{
	private MemService memService = new MemService();
	private String viewName;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		String phone = request.getParameter("phone");
		if(phone.equals("")) phone=null;

		Member member = new Member();

		member.setEmail(email);
		member.setPassword(password);
		member.setNickname(nickname);
		member.setPhone(phone);
		
		memService.regist(member);
		viewName = "/member/regist/result";
	}

	@Override
	public String getViewName() {
		// TODO Auto-generated method stub
		return viewName;
	}

	@Override
	public boolean isForward() {
		return false;
	}

}
