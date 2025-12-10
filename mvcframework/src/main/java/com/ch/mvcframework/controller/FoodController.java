package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.food.model.FoodManager;

/* 음식에 대한 판단 요청을 처리하는 컨트롤러
 * MVC - 개발 이론, 방법론
 * Model2 - 그 이론을 JavaEE 기술로 구현해 놓은 모델
 * 				M - java의 순수 클래스
 * 				V - jsp, html
 * 				C - 1) 웹서버에서 실행될 수 있어야 한다.
 * 					 2) 클라이언트의 요청을 받을 수 있어야 한다.
 * 					 결론 - 서블릿밖에 없음
 * 모델2의 컨트롤러의 요건 dnl 1), 2)번
 * */
public class FoodController implements Controller{
	FoodManager manager = new FoodManager();
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클라이언트의 요청 파라미터 받기
		request.setCharacterEncoding("utf-8");
		
		String food = request.getParameter("food");
		
		String msg = manager.getAdvice(food);		// 모델 객체에 일 시키기 !!
		request.setAttribute("msg", msg);				// 요청에 대한 응답이 완료되기 전까지는 서버에서 살아있음.
		
		RequestDispatcher dis = request.getRequestDispatcher("/food/result.jsp");		// 포워딩하고 싶은 URL
		dis.forward(request, response);		// 포워딩 발생!!
		
		
	}

	@Override
	public String getViewName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isForward() {
		// TODO Auto-generated method stub
		return false;
	}
}
