package com.ch.gallery.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.gallery.util.StringUtil;
import com.oreilly.servlet.MultipartRequest;

// 클라이언트의 업로드를 처리할 서블릿
public class UploadServlet extends HttpServlet{
	
	// 클라이언트의 post 요청을 처리할 메서드
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();		// 응답 객체가 보유한 스트림
		
		// 업로드를 처리할 cos 컴포넌트를 사용해보자
		// MultipartRequest 객체는 일반클래스이므로, 개발자가 new 연산자를 이용하여 인스턴스를 직접
		// 생성할 수 있다. 따라서 이 객체가 지원하는 생성자를 조사하여 사용하자!
		
		// MultipartRequest는 생성자에서 업로드 처리를 하는 객체이다!
		// API에 의하면 4번째 생성자는 용량 뿐만 아니라, 파일명에 한글이포함되어 있어도 깨지지 않도록 처리가 되어 있다.
		// 용량은 기본인 바이트 단위이다...(최소단위 - bit, 기본단위 - byte)
		int maxSize = 1024 * 1024 * 3;
		MultipartRequest multi = new MultipartRequest(req, "C:\\upload", maxSize, "utf-8");
		
		// 이미 업로드된 파일은, 사용자가 정한 파일명이므로, 웹브라우저에서 표현 시 불안할 수 있음
		// 해결책? 파일명을 개발자가 정한 규칙, 또는 알고르짐으로 변경한다
		// 방법) 예 - 현재 시간(밀리세컨드까지 표현), 해시-16진수 문자열..
		long time = System.currentTimeMillis();
		
		out.print(time);
		
		out.print("<br>");
		
		out.print("업로드 성공");
		
		// 방금 업로드된 파일명을 조사하여, 현재 시간과 확장자를 조합하여 새로운 파일명 만들기
		// 이미 업로드된 파일 정보는 파일컴포넌트 스스로 알고 있다. 우리의 경우 multi이다.
		String oriName = multi.getOriginalFileName("photo");
		out.print("<br>");
		out.print(oriName);
		
		String extend = StringUtil.getExtendFrom(oriName);
		
		out.print("<br>");
		out.print("추출된 확장자는 " + extend);
		
		// 파일명과 확장자를 구했으니, 업로드된 파일의 이름을 변경하자
		// 자바에서는 파일명을 변경하거나, 삭제 등을 처리하면 java.io.File 클래스를 이용해야 한다..
		File file = multi.getFile("photo");		// 서버에 업로드된 파일을 반환해줌!!
		out.print("<br>");
		out.print(file);
		
		// File 클래스 메서드 중 파일명을 바꾸는 메서드 사용
		// renameTo() 메서드의 매개변수에는 새롭게 생성될 파일의 경로를 넣어야 한다
		boolean result = file.renameTo(new File("C:/upload/" + time + "." + extend));
		out.print("<br>");
		if(result) {
			out.print("업로드 성공");
		}else {
			out.print("업로드 실패");
		}
	}
}
