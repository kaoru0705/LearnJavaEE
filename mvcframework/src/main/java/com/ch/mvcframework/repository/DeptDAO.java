package com.ch.mvcframework.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.mybatis.MybatisConfig;

public class DeptDAO {
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	
	// 1건 등록
	public void insert(Dept dept) {
		SqlSession sqlSession = mybatisConfig.getSqlSession(); 
		sqlSession.insert("Dept.insert", dept);
	}
}
