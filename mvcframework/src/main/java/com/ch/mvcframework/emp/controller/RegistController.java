package com.ch.mvcframework.emp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.controller.Controller;
import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.emp.model.EmpService;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.mybatis.MybatisConfig;
import com.ch.mvcframework.repository.DeptDAO;
import com.ch.mvcframework.repository.EmpDAO;

/*
 * 사원 등록 요청을 처리하는 하위 컨트롤러
 * 3단계: 일 시키기
 * 4단계: DML이므로 4단계는 생략
 */
public class RegistController implements Controller {
	private EmpService empService = new EmpService();
	private String viewName;
	
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
		
		//Emp가 Dept를 has a 관계로 보유하고 있으므로
		// 낱개로 전달하지 말고, 모아서 전달하자
		emp.setDept(dept);
		
		//모델 영역에 일 시키기(주의 구체적으로 직접 일하지 말자 = 일 직접 하는 순간 모델이 됨...)
		// 코드가 혼재되므로, 모델 영역을 분리 시킬 수 없으므로 재사용성이 떨어짐
		
		// 아래의 regist() 메서드에는 호출자에게 예외를 전가시키는(=떠넘기는) throws가 처리되어 있음에도 불구하고
		// 컴파일 에러가 나지 않는 이유는? 여기서의 예외가 개발자에게 처리를 강요하지 않는 RuntimeException이기 때문이다.
		// 하지만 개발자는 강요하지 않는다고 하여 예외 처리를 하지 않으면, 프로그램을 올바르게 실행될 수 없을 것이다.
		try {
			empService.regist(emp);
			viewName = "/emp/regist/result";
		} catch (EmpException e) {
			viewName = "/emp/error";
		}
		
	}

	@Override
	public String getViewName() {
		return viewName;	// 
	}

	@Override
	public boolean isForward() {
		return false;
	}
	
}
