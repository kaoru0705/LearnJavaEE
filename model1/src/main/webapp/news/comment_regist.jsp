<%@ page contentType="application/json; charset=UTF-8" %>
<%@page import="com.ch.model1.dto.News"%>
<%@page import="com.ch.model1.dto.Comment"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.ch.model1.repository.CommentDAO"%>
<%! CommentDAO commentDAO = new CommentDAO(); %>
<%
	// 클라이언트가 비동기적으로 요청을 시도하므로, 파라미터를 받고, DB에 넣은 후
	// 응답 정보는 html(X)? vs json(O)
			
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html; charset=utf-8");	

	String msg = request.getParameter("msg");	// 댓글 내용 
	String reader = request.getParameter("reader");		// 댓글 작성자
	String newsId = request.getParameter("newsId");		// 제일 중요 ~~~~ 부모의 pk	
	
	System.out.println("msg는 " + msg);
	System.out.println("reader는 " + reader);
	System.out.println("news_id는 " + newsId);
	
	// 파라미터를 하나의 DTO로 모으기
	Comment comment = new Comment();
	
	comment.setMsg(msg);
	comment.setReader(reader);
	// 부모를 숫자가 아닌 객체형태로 보유하고 있으므로, 
	News news = new News();
	news.setNewsId(Integer.parseInt(newsId));
	
	// 두 객체가 관련성이 전혀없는 상태이므로, comment 안으로 news를 보유시키자
	comment.setNews(news);		// 부모를 자식한테 밀어넣기!!
	
	// DAO에게 일 시키기
	int result = commentDAO.insert(comment);
	
	// 결과 처리~~~
	// 클라이언트는 비동기로 요청을 시도했기 때문에, 서버측에서 만일 완전한 html로 응답을 해버리면, 클라이언트의 의도와는 달리
	// 동기방식을 염두해 둔 응답 정보이므로, 서버측에서는 순수 데이터형태로 응답 정보를 보내야 한다.. 이때 압도적으로 많이 사용되는
	// 데이터 형태는 json이다. (이유? json은 그냥 문자열이기 때문에 모든 시스템(linux, mac, android, ios 상관없이 시스템 중립적이므로..))
	if(result < 1) {
		out.print("{\"resultMsg\": \"등록 실패\"}");
	}else {
		out.print("{\"resultMsg\": \"등록 성공\"}");
	}
%>