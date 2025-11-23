<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String phone = "";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "myjava";
	String pass = "1234";
%>

<%
	phone = request.getParameter("phone");
	Class.forName("oracle.jdbc.driver.OracleDriver");
	con = DriverManager.getConnection(url, user, pass);
	
	pstmt = con.prepareStatement("select count(*) from member where phone = ?");
	pstmt.setString(1, phone);
	
	rs = pstmt.executeQuery();
	
	if(rs.next() && rs.getInt(1) > 0) {
		out.print("invalid");
	}else {
		out.print("valid");
	}
%>