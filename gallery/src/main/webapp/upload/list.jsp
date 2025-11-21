<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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
</style>
</head>
<body>
<pre>
	JSP란? Java Server Page (자바 기반의 서버에서 실행되는 페이지)
	오직 javaEE 기반의 서버에서만 해석 및 실행됨
	장점 - 서블릿과 달리 HTML을 혼용하여 사용이 가능(서블릿의 디자인 표현의 취약점 보완하기 위한 기술)
</pre>
<h2>Zebra Striped Table</h2>
<p>For zebra-striped tables, use the nth-child() selector and add a background-color to all even (or odd) table rows:</p>

<table>
  <tr>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Points</th>
  </tr>

<%for(int i = 0; i < 10; i++){%>
  <tr>
    <td>Jill</td>
    <td>Smith</td>
    <td>50</td>
  </tr>
<%} %>
</table>

</body>
</html>
