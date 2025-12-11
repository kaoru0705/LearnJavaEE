package com.ch.mvcframework.emp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.controller.Controller;
import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.repository.DeptDAO;
import com.ch.mvcframework.repository.EmpDAO;

/*
 * 사원 등록 요청을 처리하는 하위 컨트롤러
 * 3단계: 일 시키기
 * 4단계: DML이므로 4단계는 생략
 */
public class RegistController implements Controller {
	DeptDAO deptDAO = new DeptDAO();
	EmpDAO empDAO = new EmpDAO();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 부서 관련 정보 --> Dept2에 등록
		String deptno = request.getParameter("deptno");
		String dname = request.getParameter("dname");
		String loc =request.getParameter("loc");
		
		Dept dept = new Dept();
		dept.setDeptno(Integer.parseInt(deptno));
		dept.setDname(dname);
		dept.setLoc(loc);
		
		
		// 사원 관련 정보 --> Emp2에 등록
		String empno = request.getParameter("empno");
		String ename = request.getParameter("ename");
		String sal = request.getParameter("sal");
		
		Emp emp = new Emp();
		emp.setEmpno(Integer.parseInt(empno));
		emp.setEname(ename);
		emp.setSal(Integer.parseInt(sal));
		
		try {
			deptDAO.insert(dept);
			empDAO.insert(emp);
		} catch (EmpException e) {
			e.printStackTrace();
			
		}
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
