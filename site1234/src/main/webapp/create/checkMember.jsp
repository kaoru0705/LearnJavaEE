<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
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
	
	String email = "";
	String nickname = "";
	String phone = "";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "myjava";
	String pass = "1234";
	String check = "";
%>

<%
	email = request.getParameter("email");
	nickname = request.getParameter("nickname");
	phone = request.getParameter("phone");
	Map<String, String> member = new HashMap();
	
	if(email != null) {
		check = "email";
		member.put(check, email);
	};
	if(nickname != null) {
		check = "nickname";
		member.put(check, nickname);
	}
	if(phone != null) {
		check = "phone";
		member.put(check, phone);
	}
	
	Class.forName("oracle.jdbc.driver.OracleDriver");
	con = DriverManager.getConnection(url, user, pass);
	
	String query = "select count(*) from member where " + check + " = ?";
	pstmt = con.prepareStatement(query);
	pstmt.setString(1, member.get(check));
	rs = pstmt.executeQuery();
	
	if(rs.next() && rs.getInt(1) > 0) {
		out.print("invalid");
	}else {
		out.print("valid");
	}
%>