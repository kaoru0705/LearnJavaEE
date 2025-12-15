package com.site1234.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServlet extends HttpServlet{
	
	FileInputStream fis;
	Properties props;
	
	public void init(ServletConfig config) {
		
		try {
			ServletContext application = config.getServletContext();
			
			/* web.xml에 추가한 init-param-name contextConfigLocation */
			String paramValue = config.getInitParameter("contextConfigLocation");
			System.out.println(paramValue);
			String realPath = application.getRealPath(paramValue);
			System.out.println(realPath);
			
			fis = new FileInputStream(realPath);
			props = new Properties();
			props.load(fis);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("클라이언트의 요청 감지");
		
		String uri = request.getRequestURI();
		System.out.println("클라이언트가 요청 시 사용한 uri는 " + uri);
		
		
	}
}
