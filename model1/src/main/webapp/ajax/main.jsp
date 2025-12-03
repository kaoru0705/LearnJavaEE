<%@page import="com.ch.model1.repository.Member2DAO"%>
<%@page import="com.ch.model1.dto.Member2"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%!
	Member2DAO dao = new Member2DAO();
%>
<%
	List<Member2> memberList = dao.selectAll();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        .container{
            width: 650px;
            height: 500px;
            background-color: aliceblue;
            margin: auto;
        }
        .aside{
            width: 150px;
            height: 100%;
            background-color: antiquewhite;
            float: left;
        }
        .aside input{
            width: 90%;
        }
        .aside button{
            width: 40%;
        }
        .content{
            width: 500px;
            height: 100%;
            background-color: aqua;
            float: left;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
    
    	// 지금까지는 동기방식으로 서버에 요청을 시도했고, 그 결과로 html을 가져와서 브라우저 화면에 출력함으로써
    	// 유저가 보기엔 새로고침 현상이 발생하게 됨... 따라서 현재 페이지는 그대로 유지하고, 백그라운드에서
    	// 크롬과 같은 웹브라우저가 대신 서버와의 통신을 담당하고, 그 시간 동안은 자바스크립트는 원래 하고자 했던 로직을 그대로 수행하게 됨
    	// 추후, 서버로부터 응답이 오면, 크롬 브라우저는 자바스크립트에게 보고를 하게 되며, 이때 서버로부터 가져온 html이 아닌 순수한 데이터를
    	// 자바스크립트에게 전달한다..., 그러면 이 데이터를 자바스크립트는 순수 데이터를 이용하면 화면에 동적으로 출력한다..
    	// 새로고침 No
    	function sendAsync(){
    		// 비동기 방식의 핵심이 되는 자바스크립트 객체가 바로 XMLHttpRequest이다.
    		
    		let xhttp = new XMLHttpRequest();		// 주의 이 객체가 서버로 요청을 떠나는 것이 아니라, 크롬 브라우저가 요청을 시도하러 감...
    		
    		// 크롬 등의 브라우저가 서버로부터 응답을 받을 때 발생하는 이벤트를 처리하는 속성!!
    		// 브라우저가 서버로부터 응답을 받으면 onload에 지정한 콜백함수를 자동으로 호출하게 됨(이때 호출주체는 js)
    		xhttp.onload = function(){
    		}

    		
    		// 요청할 준비
    		xhttp.open("post", "/ajax/async_regist.jsp");		// 어떤 서버의 주소에 요청을 시도하고, 어떤 HTTP 메서드로 요청을 시도할지 결정하는 메서드
    		
    		// HTTP 메서드가 post인 경우 헤더값을 다음과 같이 설정해야 한다.. (평소엔 웹 브라우저가 대신 해줌..)
    		// 헤더에 대한 설정은 반드시 open 메서드 이후에 작성해야 한다!!
    		xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");	// 서버야 이 요청은 post야
    		
    		
    		// 브라우저에게 요청
    		xhttp.send("id="+$("input[name='id']").val()+"&name="+$("input[name='name']").val()+"&email="+$("input[name='email']").val());	// 요청 시작
    	}
    
    	/*
		    문서가 로드가 되면, 두 개의 버튼에 대해 이벤트 연결
		    화살표 함수 - 기존 함수정의 기법을 줄여서 표현...
		*/
        $(()=>{
            // 동기 버튼에 클릭 이벤트 연결
            $($("form button")[0]).click(()=>{
                //alert("동기 방식의 요청 시도");
                $("form").attr({
                    action: "/ajax/regist.jsp",
                    method: "POST"
                });
                $("form").submit();
            });

            // 비동기 버튼에 클릭 이벤트 연결
            // 동기 - 전통적으로 순서를 지키는 실행 방식을 의미
            //			1) 장점 - 순서에 의해 실행되므로, 이전 단계의 실행이 완료되어야 나중 순서로직이 실행되기 때문에
            //					순서가 엉키지 않으므로, 안정적...
            // 			2) 단점 - 만일 앞선 실행부가 반복문이나, 대기상태에 빠졌을 경우, 후순위로직은 실행이 지연되 거나
            //					계속 기다리는 현상 발생
            //	비동기 - 순서를 지키지 않는 방식
            //			1) 장점 - 순서를 지키지 않기 때문에, 앞선 실행부가 대기 상태에 빠지더라도, 후순위 실행이 영향을 받지 않음
            //			2) 단점 - 서버로부터 응답 받는 데이터 형식이 html이 아니므로 새로고침 현상을 발생하지 않겠으나,
            //					페이지 디자인을 동적으로 처리하는데 많은 시간과 노력이 필요함...(렌더링)
            //					참고) 페이지를 동적으로 처리하는 양이 너무 가혹하여 페이스북 개발자들이 만들어낸
            //						자바스크립트 기반의 프레임웍크가 React.js 이다.
            
            $($("form button")[1]).click(()=>{
            	sendAsync();    
            });
        });


    </script>
</head>
<body>
    <div class="container">
        <div class="aside">
            <form>
                <input type="text" placeholder="Your ID..." name="id">
                <input type="text" placeholder="Your name..." name="name">
                <input type="text" placeholder="Your email..." name="email">
                <button type="button">sync</button>
                <button type="button">async</button>
            </form>
        </div>
        <div class="content">
        	<table width = "100%" border="1px">
        		<thead>
        			<th>ID</th>
        			<th>Name</th>
        			<th>Email</th>
         		</thead>
         		<tbody>
         			<%for(int i = 0; i < memberList.size();i++){%>
         				<%Member2 dto = memberList.get(i);%>
         				<tr>
         					<td><%=dto.getId()%></td>
         					<td><%=dto.getName()%></td>	
         					<td><%=dto.getEmail()%></td>
      					</tr>
      				<%}%>
         		</tbody>
        	</table>
        </div>
    </div>
</body>
</html>