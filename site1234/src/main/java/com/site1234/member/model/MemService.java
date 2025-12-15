package com.site1234.member.model;


import org.apache.ibatis.session.SqlSession;

import com.site1234.member.dto.Member;
import com.site1234.member.repository.MemberDAO;
import com.site1234.mybatis.MybatisConfig;

public class MemService {
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	
	MemberDAO memberDAO = new MemberDAO();
	public void regist(Member member) {
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		try {
			int result = memberDAO.insert(sqlSession, member);
			if(result < 1) throw new Exception();
		} catch (Exception e) {
			System.out.println("등록실패");
			e.printStackTrace();
		}finally {
			mybatisConfig.release(sqlSession);
		}

	}
}
