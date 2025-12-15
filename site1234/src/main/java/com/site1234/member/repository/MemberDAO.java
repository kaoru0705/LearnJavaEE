package com.site1234.member.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;

import com.site1234.member.dto.Member;
import com.site1234.mybatis.MybatisConfig;
import com.site1234.util.PoolManager;

public class MemberDAO {
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	
	public int insert(SqlSession sqlSession, Member member) {
		int result = 0;
		result = sqlSession.insert("Member.insert", member);
		
		sqlSession.commit();
		
		return result;
	}

	public List selectAll() {
		List list = null;
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		list = sqlSession.selectList("Member.selectAll");
		mybatisConfig.release(sqlSession);
		
		return list;
	}
}
