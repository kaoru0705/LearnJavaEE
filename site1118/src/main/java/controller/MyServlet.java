package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 클래스 중 오직 javaEE의 서버에서만 해석 및 실행되어질 수 있는 클래스를 가리켜
 * Servlet이라 한다.
 * .현재 클래스를 Servlet으로 만들려면 HttpServlet을 상속 받으면 된다.
 */
public class MyServlet extends HttpServlet{
							// 상속관계는 is a
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		resp.setCharacterEncoding("utf-8");
		// 현재 클래스를 웹브라우저로 요청하는 클라이언트에게 메시지 출력
		PrintWriter out = resp.getWriter();	// 문자 기반의 출력스트림 얻기
		// 개발자가 이 출력 스트림에 문자열을 저장해두면 고양이 서버가 알아서 웹브라우저에 출력해버림
		out.println("I'm kdh 안녕");
		
	}
}
