package com.ch.mvcframework.emp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.controller.Controller;

/*
 * 사원 등록 요청을 처리하는 하위 컨트롤러
 * 3단계: 일 시키기
 * 4단계: DML이므로 4단계는 생략
 */
public class RegistController implements Controller {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 부서 관련 정보 --> Dept2에 등록
		String deptno = request.getParameter("depno");
		String dname = request.getParameter("dname");
		String loc =request.getParameter("loc");
		
		// 사원 관련 정보 --> Emp2에 등록
		String empno = request.getParameter("empno");
		String ename = request.getParameter("ename");
		String sal = request.getParameter("sal");
	}

	@Override
	public String getViewName() {
		return null;
	}

	@Override
	public boolean isForward() {
		return false;
	}
	
}
