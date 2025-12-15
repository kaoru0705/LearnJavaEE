package com.site1234.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisConfig {
	
	private static MybatisConfig instance;
	private SqlSessionFactory sqlSessionFactory;
	
	private MybatisConfig() {
		try {
			String resource = "com/site1234/mybatis/config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static MybatisConfig getInstance() {
		if(instance == null) {
			instance = new MybatisConfig();
		}
		
		return instance;
	}
	
	public SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
	
	public void release(SqlSession sqlSession) {
		if(sqlSession != null) {
			sqlSession.close();
		}
	}
}
