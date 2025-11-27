<%@page import="java.sql.DriverManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.site1234.dto.MemberDto"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "myjava";
	String pass = "1234";
	List<MemberDto> memberList = new ArrayList<>();
%>
<%
	Class.forName("oracle.jdbc.driver.OracleDriver");
	con = DriverManager.getConnection(url, user, pass);
	
	String sql = "select * from member";
	pstmt = con.prepareStatement(sql);
	rs = pstmt.executeQuery();
%>
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
tbody tr:nth-child(odd) {
  background-color: #f2f2f2;
}
</style>
</head>
<body>
<table>
<thead>
  <tr>
    <th>No</th>
    <th>Email</th>
    <th>Password</th>
    <th>Nickname</th>
    <th>Phone</th>
  </tr>
 </thead>
 <tbody>
<%while(rs.next()) {%>
  <tr>
    <td><%out.print(rs.getInt("member_id")); %></td>
    <td><%out.print(rs.getString("email")); %></td>
    <td><%out.print(rs.getString("password")); %></td>
    <td><%out.print(rs.getString("nickname")); %></td>
    <td><%out.print(rs.getString("phone")); %></td>
  </tr>
<%} %>
</tbody>
</table>
</body>
</html>
<%
	rs.close();
	pstmt.close();
	con.close();
%>