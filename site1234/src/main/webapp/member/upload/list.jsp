<%@page import="com.site1234.member.dto.Member"%>
<%@page import="com.site1234.member.repository.MemberDAO"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	MemberDAO memberDAO;
	List<Member> memberList = new ArrayList<>();
%>
<%
	memberDAO = new MemberDAO();
	memberList = memberDAO.findAll();
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
    <th>CreatedAt</th>
  </tr>
 </thead>
 <tbody>
<%for(Member member : memberList) {%>
  <tr>
    <td><%out.print(member.getMember_id()); %></td>
    <td><%out.print(member.getEmail()); %></td>
    <td><%out.print(member.getPassword()); %></td>
    <td><%out.print(member.getNickname()); %></td>
    <td><%out.print(member.getPhone()); %></td>
    <td><%out.print(member.getCreatedAt()); %></td>
  </tr>
<%} %>
</tbody>
</table>
</body>
</html>