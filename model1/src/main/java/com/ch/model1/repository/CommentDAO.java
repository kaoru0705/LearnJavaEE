package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ch.model1.dto.Comment;
import com.ch.model1.dto.News;
import com.ch.model1.util.PoolManager;

// 오직 Comment 테이블에 대한 CRUD만을 수행하는 DAO
public class CommentDAO {
	PoolManager poolManager = PoolManager.getInstance();
	// 댓글 등록
	public int insert(Comment comment) {
		Connection con = poolManager.getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "insert into comment(msg, reader, news_id) values(?, ?, ?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, comment.getMsg());
			pstmt.setString(2,  comment.getReader());
			pstmt.setInt(3, comment.getNews().getNewsId()); // 객체지향이므로, 부모를 int 형이 아닌 객체 형태로 has a로 보유
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			poolManager.freeConnection(con,  pstmt);
		}
		
		return result;
	}
	
	// 특정 뉴스 기사에 딸려 있는 댓글 모두 가져오기
	public List<Comment> selectByNewsId(int newsId) {
		Connection con = poolManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Comment> list = new ArrayList<Comment>();
		
		String sql = "select * from comment where news_id = ? order by comment_id desc";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newsId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {	// 레코드 있는 만큼..
				// rs를 대신할 수 있는 데이터를 담는 용도의 DTO
				Comment comment = new Comment();
				comment.setCommentId(rs.getInt("comment_id"));
				comment.setMsg(rs.getString("msg"));
				comment.setReader(rs.getString("reader"));
				comment.setWritedate(rs.getString("writedate"));
				
				// 부모인 뉴스의 정보도 담기!!
				News news = new News();
				news.setNewsId(newsId);
				// 생성된 news 인스턴스를 Comment DTO에 has-a 관계로 밀어넣기!!
				comment.setNews(news);		// 자식 DTO가 부모 DTO를 보유하게 만듦
				
				list.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			poolManager.freeConnection(con, pstmt, rs);
		}
		
		return list;
	}
}
