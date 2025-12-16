package com.site1234.member.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;

import com.site1234.exception.MemberException;
import com.site1234.member.dto.Member;
import com.site1234.mybatis.MybatisConfig;
import com.site1234.util.PoolManager;

public class MemberDAO {
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	
	public void insert(SqlSession sqlSession, Member member) throws MemberException{
		int result = 0;
		try {
			result = sqlSession.insert("Member.insert", member);			
		} catch (Exception e) {
			System.out.println("멤버가 등록되지 않았습니다.");
			throw new MemberException("멤버 등록 실패", e);
		}
	}

	public List selectAll() {
		List list = null;
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		list = sqlSession.selectList("Member.selectAll");
		mybatisConfig.release(sqlSession);
		
		return list;
	}
}
