<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	String url = "jdbc:mysql://localhost:3306/java";
	String user = "servlet";
	String password = "1234";
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
%>
<%
	// 위의 페이지 지시 영역은 현재 jsp가 Tomcat에 의해 서블릿으로 코딩되어질 때
	// text/html 부분은 response.setContentTyp("text/html");
	// charset=UTF-8 response.setCharacterEncoding("utf-8");
	
	// select * from notice where notice_id = 2 쿼리를 수행하여 레코드를 화면에 보여주기
			
	// HTTP 통신에서 주고 받는 파라미터는 모두 문자열로 인식
	// request란? 서블릿의 service(요청객체, 응답객체) 중 HttpServletRequest가 가리키는 내장 객체,
	// 그러다 보니 개발자가 변수명을 정한 것이 아니라 jsp문법에서 정해진 이름이다. 
	String noticeId =request.getParameter("notice_id");
	
	String sql = "select * from notice where notice_id = " + noticeId;
	
	Class.forName("com.mysql.cj.jdbc.Driver");		// mysql 드라이버
	con = DriverManager.getConnection(url, user, password);
	
	pstmt = con.prepareStatement(sql);
	rs = pstmt.executeQuery();
	
	rs.next();
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

* {
	box-sizing: border-box;
}

input[type=text], select, textarea {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	margin-top: 6px;
	margin-bottom: 16px;
	resize: vertical;
}

input[type=button] {
	background-color: red;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

input[type=button]:hover {
	background-color: #45a049;
}

.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}
</style>
<script>
	// 사용자가 이력한 폼의 내용을 서버로 전송하자
	// JavaScript 언어는 DB에 직접적으로 통신 가능??
	// JS는 클라이언트 즉(Front 영역) 여기 때문에 원본 소스가 그냥 노출되어 있다.
	function del(){
		if(confirm("삭제하시겠어요?")){
			location.href = '/notice/delete?notice_id=<%=noticeId%>';
		}
		
	}
	function edit(){
		if(confirm("수정하시겠어요?")) {
			// 작성된 폼 양식을 서버로 전송~~~~~~~~
			let form1 = document.getElementById("form1");
			form1.action="/notice/edit";		// 서버의 url
			form1.method = "post";
			form1.submit();
		}
	}
</script>
</head>
<body>
	<div class="container">
		<form id="form1">
			<input type="hidden" name="notice_id" value="<%=rs.getString("notice_id")%>"></input>
			<input type="text" name="title" placeholder="제목입력..."  value = "<%=rs.getString("title") %>">
			<input type="text" name="writer" placeholder="작성자 입력.." value = "<%=rs.getString("writer") %>">
			<textarea id="subject" name="content" placeholder="내용을 입력하세요.." style="height: 200px"><%=rs.getString("content") %></textarea>
			<input type="button" value="수정" onClick="edit()">
			<input type="button" value="삭제" onClick="del()">
			<!-- js에서 링크를 표현한 내장객체를 location -->
			<input type="button" value="목록" onClick="location.href = '/notice/list.jsp'">
		</form>
	</div>
</body>
</html>
<%
	rs.close();
	pstmt.close();
	con.close();
%>
