<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// page 지식 영역에서 contentType() 명시한 것은, 이 jsp가 서블릿으로 변환되어질 때
	// response 객체의 메서드 중 setContentType("text/html;charset=utf-8")
%>
<%!
	/*
		C:\Workspace\javaee_workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\work\Catalina\localhost\ROOT\org\apache\j
		jsp는 사실상 서블릿이다.
		결론: jsp의 개발목적 - 서블릿이 디자인을 표현하는 데 너무나 비효율적이므로, 개발자 대신 디자인 컨텐츠를
		시스템인 웹컨테이너가 대신 작성해주기 위한 스크립트 언어
	*/
	// 선언부라 한다...
	int x = 7;	// 멤버 변수
	
	public int getX(){	// 멤버 메서드
		return x;
	}
%>
<%
	// 이 영역을 scriptlet이라 하며, 추후 고양이에 의해 이 jsp가 서블릿으로 변환되어질 때
	// 이 영역에 작성한 코드는 service() 안에 작성한 것과 같아진다.
	// 선언한 적도 없는 레퍼런스 변수를 사용할 수 있는 이유?
	// jsp는 총 9가지 정도의 내장객체를 지원함 (Built-in Object)
	// 문자 기반의 출력스트림 객체를 미리 변수명(out)까지 지정해놓음
	out.print(getX());
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

tr:nth-child(even) {
  background-color: #f2f2f2;
}
</style>
</head>
<body>
<pre>
	1)JSP란? Java Server Page (자바 기반의 서버에서 실행되는 페이지)
	오직 javaEE 기반의 서버에서만 해석 및 실행됨
	장점 - 서블릿과 달리 HTML을 혼용하여 사용이 가능(서블릿의 디자인 표현의 취약점 보완하기 위한 기술)
	
	2) JSP의 코드 작성 영역
		- jsp는 다음의 3가지 영역에 코드를 작성할 수 있다.
		
		(1)지시영역- @ 붙은 영역의 의미
			현재 jsp 페이지의 인코딩, 파일 유형, 다른 클래스 import 등을 위한 영역
			
		(2) 선언부 - !가 붙은 영역
					멤버 영역(멤버 변수나 메서드를 선언할 수 있는 영역)
		
		(3) 스크립틀릿 - 실행 영역
						실질적으로 로직을 작성하게 될 영역
						
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
