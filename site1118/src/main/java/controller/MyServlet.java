package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 클래스 중 오직 javaEE의 서버에서만 해석 및 실행되어질 수 있는 클래스를 가리켜 Servlet이라 한다.
 * .현재 클래스를 Servlet으로 만들려면 HttpServlet을 상속 받으면 된다.
 * http://~~~~/main
 * 
 */
public class MyServlet extends HttpServlet{ // 상속관계는 is a
	// 이 서블릿이 컨테이너에 의해 최초로 인스턴스가 생성될 때 초기화를 위해 무조건 호출되는 메서드
	// 주의) 생성자도 아니며 그냥 일반 메서드인데, 단지 생성자 호출 직후에 초기화를 위해 이른 시점에 호출되는 것 뿐임
	// 서블릿의 생명주기 3가지 메서드 중 첫 번째 메서드 ( init(), service(), destroy())
	// 서블릿의 생성은 컨테이너(고양이 서버)가 담당하며, 이 서블릿의 초기화 정보를 넘겨줌
	public void init(ServletConfig config) throws ServletException {
		System.out.println("나 방금 태어나서 초기화 되었어요");
	}
	// 고양이 사이트에 우리가 만든 사이트를 배포함 
	// 아직 .class만 있음
	// 어떤 user가 주소를 치고 들어감
	// request과 response 객체가 동시에 만들어짐
	// 고양이는 요청을 처리해야 하는데 직접하지앟고 스레드에게 맡김
	// 고양이가 인스턴스를 만듦
	// 초기 담당은 init
	// service 스레드가 호출 여기서 request response가 필요함
	// service는 doGet이 자동으로 실행됨
	// doget 메소드 수행 완료
	// 고양이가 response로 html을 이용해서 서버로 보내서 출력 
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		resp.setCharacterEncoding("utf-8");
		// 현재 클래스를 웹브라우저로 요청하는 클라이언트에게 메시지 출력
		PrintWriter out = resp.getWriter();	// 문자 기반의 출력스트림 얻기
		// 개발자가 이 출력 스트림에 문자열을 저장해두면 고양이 서버가 알아서 웹브라우저에 출력해버림
		out.println("I'm kdh 안녕");
	}
	// 서블릿 인스턴스가 소멸될 때, 호출되는 메서드(called by tomcat)
	public void destroy() {
		// db, 스트림 연결 끊기..
	}
}
