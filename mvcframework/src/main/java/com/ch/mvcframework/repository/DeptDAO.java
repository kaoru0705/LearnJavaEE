package com.ch.mvcframework.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.mybatis.MybatisConfig;

public class DeptDAO {
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	
	// 1건 등록
	public void insert(Dept dept) {
		SqlSession sqlSession = mybatisConfig.getSqlSession(); 
		int result = sqlSession.insert("Dept.insert", dept);
		
		System.out.println("부서 등록 결과: " + result);
		
		sqlSession.commit();		// 트랜잭션의 확정
		mybatisConfig.release(sqlSession);
		
	}
}
