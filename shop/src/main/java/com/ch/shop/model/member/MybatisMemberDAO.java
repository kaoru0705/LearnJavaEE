package com.ch.shop.model.member;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ch.shop.dto.Member;
import com.ch.shop.exception.MemberException;

@Repository
public class MybatisMemberDAO implements MemberDAO{

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public void insert(Member member) throws MemberException{		// 서비스에게 예외 전달
		try {
			sqlSessionTemplate.insert("Member.insert", member);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MemberException("회원 insert 실패", e);
		}
	}
	
}
