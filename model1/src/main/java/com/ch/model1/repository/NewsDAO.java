package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ch.model1.dto.News;
import com.ch.model1.util.PoolManager;

public class NewsDAO {
	PoolManager poolManager = PoolManager.getInstance();
	
	// 게시물 한 건 넣기!
	public int insert(News news) {
		Connection con = poolManager.getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "insert into news(title, writer, content) values(?, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, news.getTitle());
			pstmt.setString(2, news.getWriter());
			pstmt.setString(3,  news.getContent());
			
			result= pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			poolManager.freeConnection(con, pstmt);
		}
		
		return result;
	}
	
	public List<News> selectAll() {
		Connection con = poolManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<News> newsList = new ArrayList<News>();
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select n.news_id as news_id, title, writer, regdate, hit, count(comment_id) as cnt");
		sb.append(" from news n left outer join comment c");
		sb.append(" on n.news_id = c.news_id");
		sb.append(" group by n.news_id, title, writer, regdate, hit");
		sb.append(" order by n.news_id desc");
		
		System.out.println(sb.toString());
		
		try {
			pstmt = con.prepareStatement(sb.toString());	
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				News news = new News();
				news.setNewsId(rs.getInt("news_id"));
				news.setTitle(rs.getString("title"));
				news.setWriter(rs.getString("writer"));
				news.setRegdate(rs.getString("regdate"));
				news.setHit(rs.getInt("hit"));
				news.setCnt(rs.getInt("cnt"));
				
				newsList.add(news);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			poolManager.freeConnection(con, pstmt, rs);
		}
		
		return newsList;
	}
	
	public News select(int news_id) {
		Connection con = poolManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		News news = null;
		
		String sql = "select * from news where news_id = ?";	// 내림차순
		try {
			pstmt = con.prepareStatement(sql);	
			pstmt.setInt(1, news_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				news = new News();
				news.setNewsId(rs.getInt("news_id"));
				news.setTitle(rs.getString("title"));
				news.setWriter(rs.getString("writer"));
				news.setContent(rs.getString("content"));
				news.setRegdate(rs.getString("regdate"));
				news.setHit(rs.getInt("hit"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			poolManager.freeConnection(con, pstmt, rs);
		}
		
		return news;
	}
}
