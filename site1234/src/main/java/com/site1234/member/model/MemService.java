package com.site1234.member.model;


import org.apache.ibatis.session.SqlSession;

import com.site1234.exception.MemberException;
import com.site1234.member.dto.Member;
import com.site1234.member.repository.MemberDAO;
import com.site1234.mybatis.MybatisConfig;

public class MemService {
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	
	MemberDAO memberDAO = new MemberDAO();
	public void regist(Member member) throws MemberException {
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		memberDAO.insert(sqlSession, member);
		sqlSession.commit();
		mybatisConfig.release(sqlSession);
	}
}
