<%@page import="com.ch.mvcframework.movie.model.MovieManager"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function request(){
		document.querySelector("form").action = "/food";
		document.querySelector("form").method = "post";
		document.querySelector("form").submit();
	}
	
	addEventListener("load", ()=>{
		document.querySelector("button").addEventListener("click", ()=>{
			request();	
		});
	});
</script>
</head>
<body>
	<form>
		<select name="food">
			<option value="경양식 돈까스">경양식 돈까스</option>
			<option value="뼈해장국">뼈해장국</option>
			<option value="오마카세">오마카세</option>
			<option value="수제버거">수제버거</option>
		</select>
		<button type="button">피드백 요청</button>
	</form>
</body>
</html>