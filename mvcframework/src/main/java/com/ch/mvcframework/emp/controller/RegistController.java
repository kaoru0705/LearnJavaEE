package com.ch.mvcframework.emp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.controller.Controller;
import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.dto.Emp;
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
	/*
	 * DeptDAO와 EmpDAO가 같은 트랜잭션으로 묶이려면, 각각의 DAO는 공통의 SqlSession을 사용해야 한다.
	 * 따라서 이 컨트롤러에서 MybatisConfig으로부터 SqlSession을 하나 취득한 후 insert문 호출 시
	 * 같은 주소값을 갖는 공유된 SqlSessioin을 나눠주자 
	 * */
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
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
		
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		// mybatis는 default가 autocommit=false로 되어있으므로, 개발자가 별도로
		// 트랜잭션 시작을 알릴 필요 없음
		try {
			deptDAO.insert(sqlSession, dept);
			empDAO.insert(sqlSession, emp);
			sqlSession.commit();	// 트랜잭션 확정
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();	// 둘 중에 누가 잘못되었던 간에, 단 하나라도 문제가 발생하면 전체가 무효가 됨
		} finally {
			mybatisConfig.release(sqlSession);
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
