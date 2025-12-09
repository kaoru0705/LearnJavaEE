<%@page import="com.ch.mvcframework.movie.model.MovieManager"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function request(){
		document.querySelector("form").action = "/movie";
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
		<select name="movie">
			<option value="귀멸의 칼날">귀멸의 칼날</option>
			<option value="공각기동대">공각기동대</option>
			<option value="에이리언">에이리언</option>
			<option value="소울">소울</option>
		</select>
		<button type="button">피드백 요청</button>
	</form>
</body>
</html>