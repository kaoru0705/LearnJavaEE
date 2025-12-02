<%@ page contentType="text/html; charset=UTF-8"%>
<%
	// 하나의 페이지에 많은 양의 데이터가 출력되면, 스크롤이 발생하므로 한 페이지당 보여질 레코드 수의 제한을 가하고,
	// 나머지 데이터에 대해서는 여러 페이지 링크를 지원해주려면, 총 게시물 수에 대해 산수계산이 요구됨..
	
	// 기본 전제 조건 - 총 레코드 수가 있어야 한다...
	int totalRecord = 26;	// 총 레코드 수
	int pageSize = 10;		// 페이지당 보여질 레코드 수
	int totalPage = (int)Math.ceil((double)totalRecord/pageSize);
	int blockSize = 10;		// 블럭당 보여질 페이지 수
	int currentPage = 1;			// 현재 유저가 보고 있는 페이지, 이 값은 클라이언트의 get 방식으로 전송된 파라미터로 대체 
	if(request.getParameter("currentPage") != null){
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int firstPage = currentPage - (currentPage-1) %  blockSize; 	// 블럭당 반복문의 시작 값
	int lastPage = firstPage + (blockSize - 1);	// 블럭당 반복문의 끝값
	int num = totalRecord - (currentPage - 1) * pageSize;		// 페이지당 시작 번호  예) 1page일 때는 26부터 차감... 2page일 때는 16부터 차감 3page일 때는 6부터 차감
%>
<%="totalRecord" + totalRecord + "<br>" %>
<%="pageSize" + pageSize + "<br>" %>
<%="totalPage" + totalPage + "<br>" %>
<%="currentPage" + currentPage + "<br>" %>
<%="firstPage" + firstPage+ "<br>" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
table {
	border-collapse: collapse;
	border-spacing: 0;
	width: 100%;
	border: 1px solid #ddd;
}

th, td {
	text-align: left;
	padding: 16px;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}
a{text-decoration:none;}

/* 유저가 현재 보고 있는 페이지에 대한 시각적 효과를 주기 위함.. */
.numStyle{
	font-size:30px;
	
	color:red;
}
</style>
</head>
<body>

	<table>
		<tr>
			<th>No</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr>
		<%for(int i = 1; i <= pageSize; i++) { %>
		<%if(num < 1) break;		// 게시물의 넘버는 1까지만 유효하므로, 1보다 작아지면 반복문 빠져나오기 %>
		<tr>
			<td><%=num--%></td>
			<td>허리 아프다</td>
			<td>김태호</td>
			<td>2025/12/02</td>
			<td>0</td>
		</tr>
		<%} %>
		<tr>
			<td colspan="5" align="center">
			<a href="/paging/list.jsp?currentPage=<%=Math.max(firstPage-1, 1)%>">prev</a>
			<%for(int i = firstPage; i <= lastPage; i++) { %>
			<%if(i > totalPage) break;	// 총 페이지 수를 넘어설 경우 더 이상 반복문 수행하면 안 됨..%>
			<a <%if(currentPage == i){ %>class="numStyle" <%} %> href="/paging/list.jsp?currentPage=<%=i%>">[<%=i%>]</a>
			<%} %>
			<a href="/paging/list.jsp?currentPage=<%=Math.min(lastPage+1, totalPage)%>">next</a>
			</td>
		</tr>
	</table>

</body>
</html>
