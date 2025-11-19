package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 클라이언트가 전송한 파라미터들을 받아서, 오라클에 넣기!
// 클라이언트의 요청이 웹브라우저이므로, 즉 웹상의 요청을 받을 수 있고, 오직 서버에서만 실행될 수 있는 클래스인
// 서블릿으로 정의하자!
public class JoinController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");		// 브라우저에게 전송할 데이터가 html 문서임을 알려줌
		response.setCharacterEncoding("utf-8");		// 이 html이 지원하는 인코딩 타입을 지정
		PrintWriter out = response.getWriter();		// 응답 객체가 보유한 출력스트림 얻기
		
		// 주의할 점 아래의 코드에 의해, 클라이언트의 브라우저에 곧바로 데이터가 전송되는 게 아니라,
		// 추후 응답을 마무리하는 시점에 Tomcat과 같은 컨테이너 서버가, out.print()에 의해 누적되어 있는
		// 문자열을 이용하여, 새로운 html 문서를 작성할 때 사용됨..
		out.print("<h1>강동훈</h1>");
		
		// JDBC를 오라클에 insert
		// 드라이버가 있어야 오라클을 제어할 수 있다. 따라서 드라이버 jar 파일을 클래스패스에 등록하자
		// 하지만, 현재 사용중인 IDE가 이클립스라면, 굳이 환경변수까지 등록할 필요 없고, 이클립스에 등록하면 된다.
		
		
	}
}