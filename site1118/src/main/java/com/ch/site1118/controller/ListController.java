package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * javaEE 기술이 서블릿 기반이므로, 디자인 결과물까지 out.print() 문자열로 처리해야 함..
 * 따라서 웹페이지의 양이 많아지거나, 디자인 코드량이 많아지면 유지보수성이 현저히 떨어짐...
 * 즉 디자인 표현에 취약함...
 */

public class ListController extends HttpServlet{
	// 웹브라우저로 요청이 들어올 때, 클라이언트가 Get 방식으로 들어올 경우 이 메서드가 동작
	// HTTP 통신에 의하면 클라이언트는 서버에 요청을 시도할 때 그 목적에 맞는 메서드를 선택하게 되어있음
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 클라이언트인 브라우저가 보게 될 컨텐츠를 작성해보기
		// 스트림을 얻기 전에, 한글 등의 다국어가 깨지지 않도록 하려면, 원하는 인코딩을 지정하자
		response.setContentType("text/html"); // 클라이언트에게 응답할 데이터의 유형을 명시
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		// String immutable 불변이므로, 수정이 안된다.
		// 따라서 수정 가능한 문자열 처리를 위한 객체인 StringBuffer(동기화 지원x), StringBuilder(동기화 지원)를 사용해야 한다.
		StringBuffer tag = new StringBuffer("<table border = \"1px\" width=\"600px\">");

		for(int i = 5; i >= 1; i--) {
			tag.append("<tr>");
			for(int j = 1; j <= 3; j++) {
				tag.append("<td>" + i + "0" + j + "호</td>");
			}
			tag.append("</tr>");
		}
		tag.append("</table>");
		
		out.print(tag.toString());
		
	}
}




