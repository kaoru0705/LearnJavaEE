package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ch.mvcframework.movie.model.MovieManager;

/*
 * MVC란? Model, View, Controller를 의미하는 디자인 패턴 중 하나를 의미
 * 			MVC는 다운로드 받거나, 눈에 보이는 파일이나 소스가 아니라, 그냥 전산분야에서 예전부터 선배들로 내려오는 개발 방법이론일 뿐이다.
 * 
 * MVC 주요 내용?
 * 			디자인 영역과 로직(모델) 영역은 완전히 분리시켜야, 유지보수성이 좋아진다.
 * 
 * Model2란?
 * 			JavaEE 분야에서 MVC 패턴을 가리킴
 * 			즉, JavaEE 분야(웹)에서 애플리케이션을 개발할 때 디자인과 로직을 분리시키기 위해 사용하여야 할 클래스 유형은 아래와 같다.
 * 			M - 중립적인 모델이므로 순수 java 클래스로 작성
 * 			V - 웹상의 디자인을 표현해야 하므로, html, jsp로 작성
 * 			C - 클라이언트의 요청을 받아야 하고, 오직 JavaEE 서버에서만 실행될 수 있어야 하므로, servlet임
 * 				주의) jsp도 사실 서블릿이므로 Controller 역할을 수행할 수는 있지만, jsp가 주로 디자인에 사용되므로,
 * 					컨트롤러로서의 역할은 주로 서블릿으로 구현함
 */
public class MovieController extends HttpServlet{
	MovieManager manager = new MovieManager();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); 
		String movie = request.getParameter("movie");
		
		// 이 클래스의 목적은 컨트롤러이므로, 더 이상 디자인 영역을 침해해서는 안 됨. 즉, 여기서 out.print()를 시도한다는 것은
		// MVC 중 View 영역을 침범하게 됨(월권 행위)
		//out.print(movie);

		
		String msg = manager.getAdvice(movie);
		
		// 이때 이 요청과 연관된 세션이 드디어 생성되면서, 자동으로 session ID가 발급됨..
		// 또한 응답 정보 생성 시 클라이언트에게 쿠키로 session ID가 함께 전송됨
		// 쿠키 - 영구적(Persistence cookie = 하드디스크), 세션쿠키(메모리)
		HttpSession session = request.getSession();
		
		// 영화에 대한 판단 결과는, 세션이 죽을 때까지 함께 생존할 수 있으므로,
		// 이 요청이 종료되어도, 그 값을 유지할 수 있음
		// session.setAttribute("msg", msg);
		
		// 세션 말고도, 데이터를 전달하는 또 다른 방법이 있기 때문에, 포워딩을 이용해보자
		// 현재 들어온 요청에 대해 응답을 하지 않은 상태로, 또 다른 서블릿에게 요청을 전달함
		// 이때 지정된 result.jsp의 서블릿의 service()메서드가 호출!!
		request.setAttribute("msg", msg);		// 세션과 생명유지 시간만 틀릴 뿐 사용방법은 같다!!
		// 현재 서블릿에서 응답을 처리하지 않았기 때문에 request는 죽지 않고 result.jsp의 서블릿까지 생명이 유지됨..
		
		RequestDispatcher dis=request.getRequestDispatcher("/movie/model2/result.jsp");	// 포워딩하고 싶은 자원의 URL
		dis.forward(request, response);
		
		// 위의 판단 결과를 여기서 출력하면 MVC 위배됨.. 따라서 판단 결과를 별도의 디자인 영역에서 보여줘야 한다!!
		
		// 아래의 코드는 응답을 하면서 브라우저로 하여금 재접속하라는 명령이다.. 따라서 응답하게 됨.. 사용금지
		//response.sendRedirect("/movie/model2/result.jsp");
		
		
	}
}
