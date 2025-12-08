<%@page import="com.ch.model1.dto.News"%>
<%@page import="com.ch.model1.repository.NewsDAO"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%!
	NewsDAO newsDAO = new NewsDAO();
%>
<%
	// 게시물 한 건을 구분할 수 있는 news_id 파라미터를 넘겨받아 DAO 일 시키자
	String newsId = request.getParameter("news_id");
	News news =newsDAO.select(Integer.parseInt(newsId));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {font-family: Arial, Helvetica, sans-serif;}
* {box-sizing: border-box;}

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
  background-color: #04AA6D;
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
<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.js"></script>
<script>
	
	// 댓글의 목록을 출력할 함수
	function printList(commentList) {
		let tag="<table width='100%' border='1px'>";
		tag +="<thead>";
		tag +="<tr>";
		tag +="<th>No</th>";
		tag +="<th>댓글내용</th>";
		tag +="<th>작성자</th>";
		tag +="<th>작성일</th>";
		tag +="</tr>";
		tag +="</thead>";
		tag +="<tbody>";
		
		let num = commentList.length;		// 게시물 수를 담아놓고, 아래 반복문에서 -- 처리할 예정
		
/* 		for(let i = 0; i < commentList.length; i++){
			let obj = commentList[i];		// i번째에서 게시물 1건 꺼내기 (comment DTO > News DTO)
			tag +="<tr>";
			tag +="<td>"+(num--)+"</td>";
			tag +="<td>"+obj.msg+"</td>";
			tag +="<td>"+obj.reader+"</td>";
			tag +="<td>"+obj.writedate+"</td>";
			tag +="</tr>";			
		} */
		// 향상된 배열 for문
		for(let obj of commentList){
			tag +="<tr>";
			tag +="<td>"+(num--)+"</td>";
			tag +="<td>"+obj.msg+"</td>";
			tag +="<td>"+obj.reader+"</td>";
			tag +="<td>"+obj.writedate+"</td>";
			tag +="</tr>";			
		}
		tag +="</tbody>";
		tag +="</table>";
		
		$(".commentList").html(tag);
	}
	
	// 댓글의 목록을 비동기로 가져오기
	// 상세페이지 들어왔을 때도 호출, 실시간 댓글을 등록할 때도 호출
	function getList(){
		let xhttp = new XMLHttpRequest();
		
		// 서버가 전송한 데이터를 받아보자
		xhttp.onload = function (){
			// 서버에서 네트워크를 타고 전송되어온 데이터는 무조건 문자열이고, 현재 문자열 상태에서는 직접 사용할 수가 없기 때문에
			// 프로그래밍 언어에서 사용하기 편한 형태인 객체로 변환하자, JSON 내장객체를 이용하여 문자열을 --> 객체로 바꾸자
			let commentList = JSON.parse(this.responseText);
			console.log("변환된 객체결과는 ", commentList);
			printList(commentList);
		}
		
		xhttp.open("GET", "/news/comment_list.jsp?newsId=<%=newsId%>");
		
		xhttp.send();	// 목록 요청 출발!!
		
	}
	
	// 댓글을 비동기적으로 등록하자!!
	function registComment(){
		let xhttp = new XMLHttpRequest();
		
		xhttp.open("POST", "/news/comment_regist.jsp");	// 시간 관계상 jsp
		
		let msg = $("input[name='msg']").val();
		let reader = $("input[name='reader']").val();
		let newsId = $("input[name='newsId']").val();
		
		// 서버로부터 응답 정보가 도착했을 때 우측의 익명함수를 호출하게 되는 ajax의 XMLHttpRequest의 속성
		xhttp.onload = function() {
			//alert(this.responseText);		// {\"resultMsg\":\"등록실패\"}	전송되어진 형태는 문자열이지만,
													// 특히 js의 객체리터럴과 흡사하므로, 객체리터럴로 변환할 수 있는 json 문자열이다.
													// 만일 이 문자열이 json 표기법을 준수했을 경우, JSON.parse()에 의해 자동으로 객체 리터럴이 될 수 있다. 
			let obj = JSON.parse(this.responseText);
			//alert(obj.resultMsg);
			getList();	// 등록된 결과를 마쳐도 비동기로 요청하자(이유? 화면 갱신을 새로고침 없이 처리하려고)
		}
		
		// 비동기적으로 POST 요청을 하려면, 기존에 브라우저가 자동으로 해 주었던 x-www-form-urlencoded를 직접 지정해야 한다..
		// 아래의 헤더 속성은 Form 전송 시 브라우저가 원래 자동으로 해줬던 것임...
		xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		xhttp.send("msg="+msg+"&reader="+reader+"&newsId=" + newsId );// 서버에 요청 시작
	}
	$(function(){
		
		// summernote 연동
		$("#summernote").summernote({
			placeholder:"내용을 입력하세요",
			height: 250
		});
		
		$("#summernote").summernote("code", "<%=news.getContent()%>");
		
		$("#bt_list").click(()=>location.href="/news/list.jsp");
		getList();
	});
	
</script>
</head>
<body>

<h3>Contact Form</h3>

<div class="container">
  <form>
    <label for="fname">제목</label>
    <input type="text" id="fname" name="title" value="<%=news.getTitle()%>">

    <label for="lname">작성자</label>
    <input type="text" id="lname" name="writer" value="<%=news.getWriter()%>">

    <label for="subject">내용</label>
    <textarea id="summernote" name="content" placeholder="Write something.." style="height:200px"></textarea>

    <input type="button" value="글등록" id="bt_regist">
    <input type="button" value="목록" id="bt_list">
  </form>
  <div>
  <!--  hidden은 개발자의 목적에 의해 존재하는 것이고, 일반 유저가 직접 입력하기 위한 용도가 아님, 그래서 숨겨놓아야 함 
  		노출시키면, 사용자가 혼란스러우며, 사용자가 손댈 경우, 개발자가 의도한 값이 망가져 버림
  -->
  	<input type="hidden" name="newsId" value="<%=news.getNewsId() %>">
  	<input type="text" style="width: 65%; background:#cccccc;" name="msg" placeholder = "댓글 내용">
  	<input type="text" style="width: 20%; background:green;" name="reader" placeholder = "작성자">
  	<input type="button" value="댓글 등록" onClick="registComment()">
  </div>
  
  <div class="commentList">
  
  </div>
</div>

</body>
</html>
