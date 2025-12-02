<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.ch.model1.dto.Board"%>
<%@page import="com.ch.model1.repository.BoardDAO"%>
<%!
	BoardDAO boardDAO=new BoardDAO();
%>
<%
	// list.jsp로부터 전송되어온 파라미터인 board_id의 값을 이용하여 DAO에게 일 시키자
	String boardId = request.getParameter("board_id");
	
	Board board = boardDAO.select(Integer.parseInt(boardId));
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
	$(function(){
		
		// summernote 연동
		$("#summernote").summernote({
			placeholder:"내용을 입력하세요",
			height: 250
		});
				
		$("#title").val("<%=board.getTitle()%>");
		$("#writer").val("<%=board.getWriter()%>");
		
		// 서머노트에 동적으로 데이터 넣기
		$("#summernote").summernote("code", "<%=board.getContent()%>");
		
		// 글수정 버튼에 이벤트 연결
		$("#bt_edit").click(function(){
			if(confirm("수정하시겠어요?")){
				$("form").attr({
					action: "/board/edit",
					method: "POST"
				});
				
				$("form").submit();
			}
		});

		// 글삭제 버튼에 이벤트 연결
		$("#bt_del").click(function(){
			if(confirm("삭제하시겠어요?")){
				$("form").attr({
					action: "/board/delete",
					method: "POST"	// 이미 form 태그의 hidden 값으로 파라미터가 있기 때문에, post로 전송하자
				});
				
				$("form").submit();
			}
		});

		// 목록 버튼에 이벤트 연결
		$("#bt_list").click(function(){
			location.href="/board/list.jsp";
		});

	});
</script>
</head>
<body>

<h3>상세보기</h3>

<div class="container">
  <form>
  	<!--  디자인적으로 보여지면 안되며, 오직 개발자에 필요에 의해 넘기는 파라미터일 경우 숨김 파라미터가 되어야 함 -->
  	<input type="hidden" id="board_id" name = "board_id" value="<%=board.getBoardId()%>" name="title" placeholder="Your title.." >
  	
    <label for="fname">제목</label>
    <input type="text" id="title" name="title" placeholder="Your title.." >

    <label for="lname">작성자</label>
    <input type="text" id="writer" name="writer" placeholder="Your name..">

    <label for="subject">내용</label>
    <textarea id="summernote" name="content" placeholder="Write something.." style="height:200px"></textarea>

    <input type="button" value="수정" id="bt_edit">
    <input type="button" value="삭제" id="bt_del">
    <input type="button" value="목록" id="bt_list">
  </form>
</div>

</body>
</html>
