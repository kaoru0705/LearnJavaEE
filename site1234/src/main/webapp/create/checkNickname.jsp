<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	String nickname;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "myjava";
	String pass = "1234";
%>

<%
	nickname = request.getParameter("nickname");
	Class.forName("oracle.jdbc.driver.OracleDriver");
	con = DriverManager.getConnection(url, user, pass);
	
    String sql = "SELECT COUNT(*) FROM member WHERE nickname = ?";
    pstmt = con.prepareStatement(sql);
    pstmt.setString(1, nickname);

    rs = pstmt.executeQuery();

    if(rs.next() && rs.getInt(1) > 0) {
        out.print("invalid");
    }else {
		out.print("valid");
    }
%>