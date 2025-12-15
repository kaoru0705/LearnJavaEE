package com.site1234.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Authenticator.RequestorType;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("클라이언트의 요청 감지");
		
		String uri = request.getRequestURI();
		System.out.println("클라이언트가 요청 시 사용한 uri는 " + uri);
		
		String controllerPath = props.getProperty(uri);
		System.out.println(uri + "에 동작할 하위 전문 컨트롤러는 " + controllerPath);
		
		try {
			Class clazz = Class.forName(controllerPath);
			Object obj = clazz.getConstructor().newInstance();
			
			Controller controller = (Controller)obj;
			controller.execute(request,  response);
			
			String viewName = controller.getViewName();
			
			String viewPage = props.getProperty(viewName);
			System.out.println("이 요청에 의해 보여질 응답페이지는 " + viewPage);
			
			if(controller.isForward()) {
				RequestDispatcher dis = request.getRequestDispatcher(viewPage);
				dis.forward(request, response);
			} else {
				response.sendRedirect(viewPage);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy() {
		if(fis!= null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
