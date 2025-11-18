package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MessageController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("감지는 되나?");
		req.setCharacterEncoding("utf-8");
		String name = req.getParameter("name");
		String contactNumber = req.getParameter("contactNumber");
		String message = req.getParameter("message");
		
		System.out.println("이름: "+name);
		System.out.println("상대 연락처: " + contactNumber);
		System.out.println("내용: " + message);
	}
}
