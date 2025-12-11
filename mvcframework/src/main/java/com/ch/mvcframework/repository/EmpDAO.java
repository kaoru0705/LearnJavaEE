package com.ch.mvcframework.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.mybatis.MybatisConfig;

public class EmpDAO {
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	
	// 1명 등록
	// throws가 명시된 메서드를 호출한 사람은 throws에 명시된 예외를 처리할 것을 강요받는다!!
	public void insert(SqlSession sqlSession, Emp emp) throws EmpException{

		try {
			int result = sqlSession.insert("Emp.insert", emp);
		}catch(Exception e) {
			e.printStackTrace();	// error의 정보를 개발자나, 시스템관리자가 알 수 있도록 로그
			// throw는 예외를 일으키는 코드!! 이기 때문에 개발자는 다음의 2가지 중 하나를 선택해야 한다.
			// 1) try ~ catch로 잡기
			// 2) 여기서 발생한 예외를 이 메서드 호출자에게 책임 전가
			throw new EmpException("사원 등록 실패",  e);
		}
	}
}
